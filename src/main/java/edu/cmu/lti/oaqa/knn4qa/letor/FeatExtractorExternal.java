/*
 *  Copyright 2019 Carnegie Mellon University
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cmu.lti.oaqa.knn4qa.letor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import edu.cmu.lti.oaqa.knn4qa.memdb.DocEntry;
import edu.cmu.lti.oaqa.knn4qa.memdb.ForwardIndex;
import edu.cmu.lti.oaqa.knn4qa.simil_func.BM25SimilarityLucene;
import edu.cmu.lti.oaqa.knn4qa.simil_func.BM25SimilarityLuceneNorm;

import no.uib.cipr.matrix.DenseVector;
import edu.cmu.lti.oaqa.knn4qa.letor.external.TextEntryInfo;
import edu.cmu.lti.oaqa.knn4qa.letor.external.WordEntryInfo;
import edu.cmu.lti.oaqa.knn4qa.letor.external.ExternalScorer.Client;

/**
 * A single-field feature extractor that calls an external code (via Apache Thrift) to compute 
 * one or more scores. The number of scores per entry is fixed and is specified as a parameter
 * for this extractor.
 * 
 * @author Leonid Boytsov
 *
 */
public class FeatExtractorExternal extends SingleFieldFeatExtractor {
  public static String FEAT_QTY = "featureQty";
  
  public static String BM25_SIMIL = "bm25";
  public static String K1_PARAM = "k1";
  public static String B_PARAM = "b";
  
  public static String HOST = "host";
  public static String PORT = "port";
  
  public static String POSITIONAL = "useWordSeq";

  public FeatExtractorExternal(FeatExtrResourceManager resMngr, OneFeatExtrConf conf) throws Exception {
    // getReqParamStr throws an exception if the parameter is not defined
    mFieldName = conf.getReqParamStr(FeatExtrConfig.FIELD_NAME);
    String similType = conf.getReqParamStr(FeatExtrConfig.SIMIL_TYPE);
    
    mFeatQty = conf.getReqParamInt(FEAT_QTY);

    mFieldIndex = resMngr.getFwdIndex(mFieldName);

    mPort = conf.getReqParamInt(PORT);
    mHost = conf.getReqParamStr(HOST);
    
    mUseWordSeq = conf.getParamBool(POSITIONAL);
    
    if (similType.equalsIgnoreCase(BM25_SIMIL))
      mSimilObj = new BM25SimilarityLuceneNorm(
                                          conf.getParam(K1_PARAM, BM25SimilarityLucene.DEFAULT_BM25_K1), 
                                          conf.getParam(B_PARAM, BM25SimilarityLucene.DEFAULT_BM25_B), 
                                          mFieldIndex);
    else
      throw new Exception("Unsupported field similarity: " + similType);
  }
  
  /**
   * getClient() retrieves the first unused client, or creates a new one.
   * Note that a single client cannot be re-used across threads, b/c
   * each client/thread needs to use a separate socket.
   */
  private synchronized Client getClient() throws TTransportException {
    if (!mFreeClients.isEmpty()) {
      int sz = mFreeClients.size();
      Client ret = mFreeClients.get(sz-1);
      mFreeClients.remove(sz-1);
      return ret;
    }
    TTransport serviceTransp = new TSocket(mHost, mPort);
    serviceTransp.open();
    return new Client(new TBinaryProtocol(serviceTransp));
  }
  
  private synchronized void releaseClient(Client c) {
    mFreeClients.add(c);
  }

  TextEntryInfo createTextEntryInfo(String entryId, DocEntry docEntry) {
    ArrayList<WordEntryInfo> wentries = new ArrayList<WordEntryInfo>();
    
    if (mUseWordSeq) {
      for (int wid : docEntry.mWordIdSeq) {
        float idf = mSimilObj.getIDF(mFieldIndex, wid);
        wentries.add(new WordEntryInfo(mFieldIndex.getWord(wid), idf, 1));
      }
    } else {
      for (int k = 0; k < docEntry.mWordIds.length; ++k) {
        int wid = docEntry.mWordIds[k];
        int qty = docEntry.mQtys[k];
        float idf = mSimilObj.getIDF(mFieldIndex, wid);
        wentries.add(new WordEntryInfo(mFieldIndex.getWord(wid), idf, qty));
      }
    }
    return new TextEntryInfo(entryId, wentries); 
  }
  
  @Override
  public Map<String, DenseVector> getFeatures(ArrayList<String> arrDocIds, Map<String, String> queryData)
      throws Exception {
    HashMap<String, DenseVector> res = new HashMap<String, DenseVector>();
    
    Client clnt = getClient();
    
    try {
      res = initResultSet(arrDocIds, getFeatureQty()); 
      DocEntry queryEntry = getQueryEntry(mFieldName, mFieldIndex, queryData);
      if (queryEntry == null) return res;
      
      TextEntryInfo queryTextEntry = createTextEntryInfo("", queryEntry);
      
      ArrayList<TextEntryInfo> docTextEntries = new ArrayList<TextEntryInfo>();
      
      for (String docId : arrDocIds) {
        DocEntry docEntry = mFieldIndex.getDocEntry(docId);
        
        if (docEntry == null) {
          throw new Exception("Inconsistent data or bug: can't find document with id ='" + docId + "'");
        }
        
        docTextEntries.add(createTextEntryInfo(docId, docEntry));
      }
      
      Map<String, List<Double>> scores = clnt.getScores(queryTextEntry, docTextEntries);
      
      for (String docId : arrDocIds) {
        List<Double> scoreList = scores.get(docId);

        if (scoreList == null) {
          throw new Exception("Inconsistent data or bug: can't find a score for doc id ='" + docId + "'");
        }
        if (scoreList.size() != mFeatQty) {
          throw new Exception("Inconsistent data or bug for doc id ='" + docId + "' expected " + mFeatQty + 
              " features, but got: " + scoreList.size());
        }
        
        DenseVector scoreVect = new DenseVector(mFeatQty);
        
        int idx = 0;
        
        for (double v : scoreList) {
          scoreVect.set(idx, v);
          idx++;
        }
        
        res.put(docId, scoreVect);
      }
    } catch (Exception e) {
      releaseClient(clnt);
      throw e;
    }    
    
    releaseClient(clnt);
    
    return res;
  }
  

  @Override
  public String getFieldName() {
    return mFieldName;
  }
  
  final String                       mFieldName;
  final BM25SimilarityLuceneNorm     mSimilObj;
  final ForwardIndex                 mFieldIndex;
  final String                       mHost;
  final int                          mPort;
  
  final int                          mFeatQty;
  final boolean                      mUseWordSeq;

  private ArrayList<Client>          mFreeClients;
  
  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public int getFeatureQty() {
    return mFeatQty;
  }
}

  