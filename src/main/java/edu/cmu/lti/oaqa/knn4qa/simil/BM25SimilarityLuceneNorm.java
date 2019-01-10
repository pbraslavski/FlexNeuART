package edu.cmu.lti.oaqa.knn4qa.simil;

import java.util.*;

import edu.cmu.lti.oaqa.knn4qa.memdb.DocEntry;
import edu.cmu.lti.oaqa.knn4qa.memdb.ForwardIndex;
import edu.cmu.lti.oaqa.knn4qa.memdb.WordEntry;

/**
 * A re-implementation of the Lucene/SOLR BM25 similarity,
 * which is normalized using the sum of query term IDFs. 
 *
 * <p>Unlike
 * the original implementation, though, we don't rely on a coarse
 * version of the document normalization factor. Our approach
 * might be a tad slower, but
 * (1) it's easier to implement;
 * (2) there is a small (about 1%) increase in accuracy. 
 * Note that IDF values are cached (without evicting from the cache).  
 * </p>
 * 
 * @author Leonid Boytsov
 *
 */
public class BM25SimilarityLuceneNorm extends BM25SimilarityLucene {
  
  public BM25SimilarityLuceneNorm(float k1, float b, ForwardIndex fieldIndex) {
    super(k1, b, fieldIndex);
  }
  
  /**
   * Computes the similarity between the query (represented by
   * a DocEntry object) and the document (also represented by a DocEntry object)
   * 
   * @param query
   * @param document
   * @return
   */
  @Override
  public float compute(DocEntry query, DocEntry doc) {
    float score = 0;
    
    int   docTermQty = doc.mWordIds.length;
    int   queryTermQty = query.mWordIds.length;

    float normIDF = 0;
    for (int i = 0; i < queryTermQty; ++i) {
      final int queryWordId = query.mWordIds[i];
      if (queryWordId >= 0) {
        float idf = getIDF(mFieldIndex, queryWordId);
        normIDF += idf; // IDF normalization
      }
    }
    
    int   iQuery = 0, iDoc = 0;
    
    float docLen = doc.mDocLen;
    
    while (iQuery < queryTermQty && iDoc < docTermQty) {
      final int queryWordId = query.mWordIds[iQuery];
      final int docWordId   = doc.mWordIds[iDoc];
      
      if (queryWordId < docWordId) ++iQuery;
      else if (queryWordId > docWordId) ++iDoc;
      else {
        float tf = doc.mQtys[iDoc];
        
        float normTf = (tf * (mBM25_k1 + 1)) / ( tf + mBM25_k1 * (1 - mBM25_b + mBM25_b * docLen * mInvAvgDl));
        
        float idf = getIDF(mFieldIndex, query.mWordIds[iQuery]);
        score +=  idf * // IDF 
                  query.mQtys[iQuery] *           // query frequency
                  normTf;                         // Normalized term frequency        
        ++iQuery; ++iDoc;
      }
    }

    if (normIDF > 0) score /= normIDF;
    
    return score;
  }
  
  @Override
  public String getName() {
    return "BM25";
  }  
}
