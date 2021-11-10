/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package edu.cmu.lti.oaqa.flexneuart.letor.external;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)", date = "2019-08-15")
public class WordEntryInfo implements org.apache.thrift.TBase<WordEntryInfo, WordEntryInfo._Fields>, java.io.Serializable, Cloneable, Comparable<WordEntryInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WordEntryInfo");

  private static final org.apache.thrift.protocol.TField WORD_FIELD_DESC = new org.apache.thrift.protocol.TField("word", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField IDF_FIELD_DESC = new org.apache.thrift.protocol.TField("IDF", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField QTY_FIELD_DESC = new org.apache.thrift.protocol.TField("qty", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WordEntryInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WordEntryInfoTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.lang.String word; // required
  public double IDF; // required
  public int qty; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    WORD((short)1, "word"),
    IDF((short)2, "IDF"),
    QTY((short)3, "qty");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // WORD
          return WORD;
        case 2: // IDF
          return IDF;
        case 3: // QTY
          return QTY;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __IDF_ISSET_ID = 0;
  private static final int __QTY_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.WORD, new org.apache.thrift.meta_data.FieldMetaData("word", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IDF, new org.apache.thrift.meta_data.FieldMetaData("IDF", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.QTY, new org.apache.thrift.meta_data.FieldMetaData("qty", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WordEntryInfo.class, metaDataMap);
  }

  public WordEntryInfo() {
  }

  public WordEntryInfo(
    java.lang.String word,
    double IDF,
    int qty)
  {
    this();
    this.word = word;
    this.IDF = IDF;
    setIDFIsSet(true);
    this.qty = qty;
    setQtyIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WordEntryInfo(WordEntryInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetWord()) {
      this.word = other.word;
    }
    this.IDF = other.IDF;
    this.qty = other.qty;
  }

  public WordEntryInfo deepCopy() {
    return new WordEntryInfo(this);
  }

  @Override
  public void clear() {
    this.word = null;
    setIDFIsSet(false);
    this.IDF = 0.0;
    setQtyIsSet(false);
    this.qty = 0;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getWord() {
    return this.word;
  }

  public WordEntryInfo setWord(@org.apache.thrift.annotation.Nullable java.lang.String word) {
    this.word = word;
    return this;
  }

  public void unsetWord() {
    this.word = null;
  }

  /** Returns true if field word is set (has been assigned a value) and false otherwise */
  public boolean isSetWord() {
    return this.word != null;
  }

  public void setWordIsSet(boolean value) {
    if (!value) {
      this.word = null;
    }
  }

  public double getIDF() {
    return this.IDF;
  }

  public WordEntryInfo setIDF(double IDF) {
    this.IDF = IDF;
    setIDFIsSet(true);
    return this;
  }

  public void unsetIDF() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IDF_ISSET_ID);
  }

  /** Returns true if field IDF is set (has been assigned a value) and false otherwise */
  public boolean isSetIDF() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IDF_ISSET_ID);
  }

  public void setIDFIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IDF_ISSET_ID, value);
  }

  public int getQty() {
    return this.qty;
  }

  public WordEntryInfo setQty(int qty) {
    this.qty = qty;
    setQtyIsSet(true);
    return this;
  }

  public void unsetQty() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __QTY_ISSET_ID);
  }

  /** Returns true if field qty is set (has been assigned a value) and false otherwise */
  public boolean isSetQty() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __QTY_ISSET_ID);
  }

  public void setQtyIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __QTY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case WORD:
      if (value == null) {
        unsetWord();
      } else {
        setWord((java.lang.String)value);
      }
      break;

    case IDF:
      if (value == null) {
        unsetIDF();
      } else {
        setIDF((java.lang.Double)value);
      }
      break;

    case QTY:
      if (value == null) {
        unsetQty();
      } else {
        setQty((java.lang.Integer)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case WORD:
      return getWord();

    case IDF:
      return getIDF();

    case QTY:
      return getQty();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case WORD:
      return isSetWord();
    case IDF:
      return isSetIDF();
    case QTY:
      return isSetQty();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof WordEntryInfo)
      return this.equals((WordEntryInfo)that);
    return false;
  }

  public boolean equals(WordEntryInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_word = true && this.isSetWord();
    boolean that_present_word = true && that.isSetWord();
    if (this_present_word || that_present_word) {
      if (!(this_present_word && that_present_word))
        return false;
      if (!this.word.equals(that.word))
        return false;
    }

    boolean this_present_IDF = true;
    boolean that_present_IDF = true;
    if (this_present_IDF || that_present_IDF) {
      if (!(this_present_IDF && that_present_IDF))
        return false;
      if (this.IDF != that.IDF)
        return false;
    }

    boolean this_present_qty = true;
    boolean that_present_qty = true;
    if (this_present_qty || that_present_qty) {
      if (!(this_present_qty && that_present_qty))
        return false;
      if (this.qty != that.qty)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetWord()) ? 131071 : 524287);
    if (isSetWord())
      hashCode = hashCode * 8191 + word.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(IDF);

    hashCode = hashCode * 8191 + qty;

    return hashCode;
  }

  @Override
  public int compareTo(WordEntryInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetWord()).compareTo(other.isSetWord());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWord()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.word, other.word);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIDF()).compareTo(other.isSetIDF());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIDF()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.IDF, other.IDF);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetQty()).compareTo(other.isSetQty());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQty()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.qty, other.qty);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WordEntryInfo(");
    boolean first = true;

    sb.append("word:");
    if (this.word == null) {
      sb.append("null");
    } else {
      sb.append(this.word);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("IDF:");
    sb.append(this.IDF);
    first = false;
    if (!first) sb.append(", ");
    sb.append("qty:");
    sb.append(this.qty);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (word == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'word' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'IDF' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'qty' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class WordEntryInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WordEntryInfoStandardScheme getScheme() {
      return new WordEntryInfoStandardScheme();
    }
  }

  private static class WordEntryInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<WordEntryInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WordEntryInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // WORD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.word = iprot.readString();
              struct.setWordIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IDF
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.IDF = iprot.readDouble();
              struct.setIDFIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // QTY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.qty = iprot.readI32();
              struct.setQtyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetIDF()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'IDF' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetQty()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'qty' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, WordEntryInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.word != null) {
        oprot.writeFieldBegin(WORD_FIELD_DESC);
        oprot.writeString(struct.word);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IDF_FIELD_DESC);
      oprot.writeDouble(struct.IDF);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(QTY_FIELD_DESC);
      oprot.writeI32(struct.qty);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WordEntryInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WordEntryInfoTupleScheme getScheme() {
      return new WordEntryInfoTupleScheme();
    }
  }

  private static class WordEntryInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<WordEntryInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WordEntryInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.word);
      oprot.writeDouble(struct.IDF);
      oprot.writeI32(struct.qty);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WordEntryInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.word = iprot.readString();
      struct.setWordIsSet(true);
      struct.IDF = iprot.readDouble();
      struct.setIDFIsSet(true);
      struct.qty = iprot.readI32();
      struct.setQtyIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
