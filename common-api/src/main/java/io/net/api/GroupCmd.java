// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: base.proto

package io.net.api;

/**
 * Protobuf type {@code GroupCmd}
 */
public final class GroupCmd extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:GroupCmd)
    GroupCmdOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GroupCmd.newBuilder() to construct.
  private GroupCmd(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GroupCmd() {
    ats_ = emptyLongList();
    cmd_ = "";
    args_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new GroupCmd();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private GroupCmd(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              ats_ = newLongList();
              mutable_bitField0_ |= 0x00000001;
            }
            ats_.addLong(input.readUInt64());
            break;
          }
          case 10: {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
              ats_ = newLongList();
              mutable_bitField0_ |= 0x00000001;
            }
            while (input.getBytesUntilLimit() > 0) {
              ats_.addLong(input.readUInt64());
            }
            input.popLimit(limit);
            break;
          }
          case 16: {

            groupId_ = input.readUInt64();
            break;
          }
          case 24: {

            botId_ = input.readUInt64();
            break;
          }
          case 32: {

            senderId_ = input.readUInt64();
            break;
          }
          case 42: {
            String s = input.readStringRequireUtf8();

            cmd_ = s;
            break;
          }
          case 50: {
            String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000002) != 0)) {
              args_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000002;
            }
            args_.add(s);
            break;
          }
          default: {
            if (!parseUnknownField(
                    input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        ats_.makeImmutable(); // C
      }
      if (((mutable_bitField0_ & 0x00000002) != 0)) {
        args_ = args_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor
  getDescriptor() {
    return BaseProto.internal_static_GroupCmd_descriptor;
  }

  @Override
  protected FieldAccessorTable
  internalGetFieldAccessorTable() {
    return BaseProto.internal_static_GroupCmd_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                    GroupCmd.class, Builder.class);
  }

  public static final int ATS_FIELD_NUMBER = 1;
  private com.google.protobuf.Internal.LongList ats_;

  /**
   * <code>repeated uint64 ats = 1;</code>
   *
   * @return A list containing the ats.
   */
  @Override
  public java.util.List<Long>
  getAtsList() {
    return ats_;
  }

  /**
   * <code>repeated uint64 ats = 1;</code>
   *
   * @return The count of ats.
   */
  public int getAtsCount() {
    return ats_.size();
  }
  /**
   * <code>repeated uint64 ats = 1;</code>
   * @param index The index of the element to return.
   * @return The ats at the given index.
   */
  public long getAts(int index) {
    return ats_.getLong(index);
  }
  private int atsMemoizedSerializedSize = -1;

  public static final int GROUPID_FIELD_NUMBER = 2;
  private long groupId_;
  /**
   * <code>uint64 groupId = 2;</code>
   * @return The groupId.
   */
  @Override
  public long getGroupId() {
    return groupId_;
  }

  public static final int BOTID_FIELD_NUMBER = 3;
  private long botId_;
  /**
   * <code>uint64 botId = 3;</code>
   * @return The botId.
   */
  @Override
  public long getBotId() {
    return botId_;
  }

  public static final int SENDERID_FIELD_NUMBER = 4;
  private long senderId_;
  /**
   * <code>uint64 senderId = 4;</code>
   * @return The senderId.
   */
  @Override
  public long getSenderId() {
    return senderId_;
  }

  public static final int CMD_FIELD_NUMBER = 5;
  private volatile Object cmd_;
  /**
   * <code>string cmd = 5;</code>
   * @return The cmd.
   */
  @Override
  public String getCmd() {
    Object ref = cmd_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      cmd_ = s;
      return s;
    }
  }

  /**
   * <code>string cmd = 5;</code>
   *
   * @return The bytes for cmd.
   */
  @Override
  public com.google.protobuf.ByteString
  getCmdBytes() {
    Object ref = cmd_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                      (String) ref);
      cmd_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ARGS_FIELD_NUMBER = 6;
  private com.google.protobuf.LazyStringList args_;

  /**
   * <code>repeated string args = 6;</code>
   *
   * @return A list containing the args.
   */
  public com.google.protobuf.ProtocolStringList
  getArgsList() {
    return args_;
  }

  /**
   * <code>repeated string args = 6;</code>
   *
   * @return The count of args.
   */
  public int getArgsCount() {
    return args_.size();
  }
  /**
   * <code>repeated string args = 6;</code>
   * @param index The index of the element to return.
   * @return The args at the given index.
   */
  public String getArgs(int index) {
    return args_.get(index);
  }

  /**
   * <code>repeated string args = 6;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the args at the given index.
   */
  public com.google.protobuf.ByteString
  getArgsBytes(int index) {
    return args_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
          throws java.io.IOException {
    getSerializedSize();
    if (getAtsList().size() > 0) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(atsMemoizedSerializedSize);
    }
    for (int i = 0; i < ats_.size(); i++) {
      output.writeUInt64NoTag(ats_.getLong(i));
    }
    if (groupId_ != 0L) {
      output.writeUInt64(2, groupId_);
    }
    if (botId_ != 0L) {
      output.writeUInt64(3, botId_);
    }
    if (senderId_ != 0L) {
      output.writeUInt64(4, senderId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(cmd_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, cmd_);
    }
    for (int i = 0; i < args_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, args_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < ats_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeUInt64SizeNoTag(ats_.getLong(i));
      }
      size += dataSize;
      if (!getAtsList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      atsMemoizedSerializedSize = dataSize;
    }
    if (groupId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
              .computeUInt64Size(2, groupId_);
    }
    if (botId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
              .computeUInt64Size(3, botId_);
    }
    if (senderId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(4, senderId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(cmd_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, cmd_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < args_.size(); i++) {
        dataSize += computeStringSizeNoTag(args_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getArgsList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof GroupCmd)) {
      return super.equals(obj);
    }
    GroupCmd other = (GroupCmd) obj;

    if (!getAtsList()
            .equals(other.getAtsList())) return false;
    if (getGroupId()
            != other.getGroupId()) return false;
    if (getBotId()
            != other.getBotId()) return false;
    if (getSenderId()
            != other.getSenderId()) return false;
    if (!getCmd()
            .equals(other.getCmd())) return false;
    if (!getArgsList()
            .equals(other.getArgsList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getAtsCount() > 0) {
      hash = (37 * hash) + ATS_FIELD_NUMBER;
      hash = (53 * hash) + getAtsList().hashCode();
    }
    hash = (37 * hash) + GROUPID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
            getGroupId());
    hash = (37 * hash) + BOTID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
            getBotId());
    hash = (37 * hash) + SENDERID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getSenderId());
    hash = (37 * hash) + CMD_FIELD_NUMBER;
    hash = (53 * hash) + getCmd().hashCode();
    if (getArgsCount() > 0) {
      hash = (37 * hash) + ARGS_FIELD_NUMBER;
      hash = (53 * hash) + getArgsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static GroupCmd parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static GroupCmd parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GroupCmd parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GroupCmd parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GroupCmd parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GroupCmd parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GroupCmd parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GroupCmd parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static GroupCmd parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static GroupCmd parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static GroupCmd parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GroupCmd parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(GroupCmd prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
            ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
          BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }

  /**
   * Protobuf type {@code GroupCmd}
   */
  public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:GroupCmd)
          GroupCmdOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return BaseProto.internal_static_GroupCmd_descriptor;
    }

    @Override
    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
      return BaseProto.internal_static_GroupCmd_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
              GroupCmd.class, Builder.class);
    }

    // Construct using io.net.api.GroupCmd.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
            BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      ats_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      groupId_ = 0L;

      botId_ = 0L;

      senderId_ = 0L;

      cmd_ = "";

      args_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000002);
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
    getDescriptorForType() {
      return BaseProto.internal_static_GroupCmd_descriptor;
    }

    @Override
    public GroupCmd getDefaultInstanceForType() {
      return GroupCmd.getDefaultInstance();
    }

    @Override
    public GroupCmd build() {
      GroupCmd result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public GroupCmd buildPartial() {
      GroupCmd result = new GroupCmd(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        ats_.makeImmutable();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.ats_ = ats_;
      result.groupId_ = groupId_;
      result.botId_ = botId_;
      result.senderId_ = senderId_;
      result.cmd_ = cmd_;
      if (((bitField0_ & 0x00000002) != 0)) {
        args_ = args_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000002);
      }
      result.args_ = args_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }

    @Override
    public Builder clearField(
            com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    @Override
    public Builder clearOneof(
            com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    @Override
    public Builder setRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }

    @Override
    public Builder addRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            Object value) {
      return super.addRepeatedField(field, value);
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof GroupCmd) {
        return mergeFrom((GroupCmd)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(GroupCmd other) {
      if (other == GroupCmd.getDefaultInstance()) return this;
      if (!other.ats_.isEmpty()) {
        if (ats_.isEmpty()) {
          ats_ = other.ats_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureAtsIsMutable();
          ats_.addAll(other.ats_);
        }
        onChanged();
      }
      if (other.getGroupId() != 0L) {
        setGroupId(other.getGroupId());
      }
      if (other.getBotId() != 0L) {
        setBotId(other.getBotId());
      }
      if (other.getSenderId() != 0L) {
        setSenderId(other.getSenderId());
      }
      if (!other.getCmd().isEmpty()) {
        cmd_ = other.cmd_;
        onChanged();
      }
      if (!other.args_.isEmpty()) {
        if (args_.isEmpty()) {
          args_ = other.args_;
          bitField0_ = (bitField0_ & ~0x00000002);
        } else {
          ensureArgsIsMutable();
          args_.addAll(other.args_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      GroupCmd parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (GroupCmd) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.Internal.LongList ats_ = emptyLongList();

    private void ensureAtsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        ats_ = mutableCopy(ats_);
        bitField0_ |= 0x00000001;
      }
    }

    /**
     * <code>repeated uint64 ats = 1;</code>
     *
     * @return A list containing the ats.
     */
    public java.util.List<Long>
    getAtsList() {
      return ((bitField0_ & 0x00000001) != 0) ?
              java.util.Collections.unmodifiableList(ats_) : ats_;
    }

    /**
     * <code>repeated uint64 ats = 1;</code>
     * @return The count of ats.
     */
    public int getAtsCount() {
      return ats_.size();
    }
    /**
     * <code>repeated uint64 ats = 1;</code>
     * @param index The index of the element to return.
     * @return The ats at the given index.
     */
    public long getAts(int index) {
      return ats_.getLong(index);
    }

    /**
     * <code>repeated uint64 ats = 1;</code>
     *
     * @param index The index to set the value at.
     * @param value The ats to set.
     * @return This builder for chaining.
     */
    public Builder setAts(
            int index, long value) {
      ensureAtsIsMutable();
      ats_.setLong(index, value);
      onChanged();
      return this;
    }

    /**
     * <code>repeated uint64 ats = 1;</code>
     * @param value The ats to add.
     * @return This builder for chaining.
     */
    public Builder addAts(long value) {
      ensureAtsIsMutable();
      ats_.addLong(value);
      onChanged();
      return this;
    }

    /**
     * <code>repeated uint64 ats = 1;</code>
     *
     * @param values The ats to add.
     * @return This builder for chaining.
     */
    public Builder addAllAts(
            Iterable<? extends Long> values) {
      ensureAtsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, ats_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated uint64 ats = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearAts() {
      ats_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }

    private long groupId_ ;
    /**
     * <code>uint64 groupId = 2;</code>
     * @return The groupId.
     */
    @Override
    public long getGroupId() {
      return groupId_;
    }
    /**
     * <code>uint64 groupId = 2;</code>
     * @param value The groupId to set.
     * @return This builder for chaining.
     */
    public Builder setGroupId(long value) {
      
      groupId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 groupId = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearGroupId() {
      
      groupId_ = 0L;
      onChanged();
      return this;
    }

    private long botId_ ;
    /**
     * <code>uint64 botId = 3;</code>
     * @return The botId.
     */
    @Override
    public long getBotId() {
      return botId_;
    }
    /**
     * <code>uint64 botId = 3;</code>
     * @param value The botId to set.
     * @return This builder for chaining.
     */
    public Builder setBotId(long value) {
      
      botId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 botId = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearBotId() {
      
      botId_ = 0L;
      onChanged();
      return this;
    }

    private long senderId_ ;
    /**
     * <code>uint64 senderId = 4;</code>
     * @return The senderId.
     */
    @Override
    public long getSenderId() {
      return senderId_;
    }
    /**
     * <code>uint64 senderId = 4;</code>
     * @param value The senderId to set.
     * @return This builder for chaining.
     */
    public Builder setSenderId(long value) {
      
      senderId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 senderId = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearSenderId() {
      
      senderId_ = 0L;
      onChanged();
      return this;
    }

    private Object cmd_ = "";
    /**
     * <code>string cmd = 5;</code>
     * @return The cmd.
     */
    public String getCmd() {
      Object ref = cmd_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
                (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        cmd_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }

    /**
     * <code>string cmd = 5;</code>
     *
     * @return The bytes for cmd.
     */
    public com.google.protobuf.ByteString
    getCmdBytes() {
      Object ref = cmd_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
                com.google.protobuf.ByteString.copyFromUtf8(
                        (String) ref);
        cmd_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     * <code>string cmd = 5;</code>
     *
     * @param value The cmd to set.
     * @return This builder for chaining.
     */
    public Builder setCmd(
            String value) {
      if (value == null) {
    throw new NullPointerException();
      }

      cmd_ = value;
      onChanged();
      return this;
    }

    /**
     * <code>string cmd = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCmd() {

      cmd_ = getDefaultInstance().getCmd();
      onChanged();
      return this;
    }

    /**
     * <code>string cmd = 5;</code>
     *
     * @param value The bytes for cmd to set.
     * @return This builder for chaining.
     */
    public Builder setCmdBytes(
            com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);

      cmd_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringList args_ = com.google.protobuf.LazyStringArrayList.EMPTY;

    private void ensureArgsIsMutable() {
      if (!((bitField0_ & 0x00000002) != 0)) {
        args_ = new com.google.protobuf.LazyStringArrayList(args_);
        bitField0_ |= 0x00000002;
      }
    }

    /**
     * <code>repeated string args = 6;</code>
     *
     * @return A list containing the args.
     */
    public com.google.protobuf.ProtocolStringList
        getArgsList() {
      return args_.getUnmodifiableView();
    }
    /**
     * <code>repeated string args = 6;</code>
     * @return The count of args.
     */
    public int getArgsCount() {
      return args_.size();
    }

    /**
     * <code>repeated string args = 6;</code>
     *
     * @param index The index of the element to return.
     * @return The args at the given index.
     */
    public String getArgs(int index) {
      return args_.get(index);
    }

    /**
     * <code>repeated string args = 6;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the args at the given index.
     */
    public com.google.protobuf.ByteString
    getArgsBytes(int index) {
      return args_.getByteString(index);
    }

    /**
     * <code>repeated string args = 6;</code>
     *
     * @param index The index to set the value at.
     * @param value The args to set.
     * @return This builder for chaining.
     */
    public Builder setArgs(
            int index, String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureArgsIsMutable();
      args_.set(index, value);
      onChanged();
      return this;
    }

    /**
     * <code>repeated string args = 6;</code>
     *
     * @param value The args to add.
     * @return This builder for chaining.
     */
    public Builder addArgs(
            String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureArgsIsMutable();
      args_.add(value);
      onChanged();
      return this;
    }

    /**
     * <code>repeated string args = 6;</code>
     *
     * @param values The args to add.
     * @return This builder for chaining.
     */
    public Builder addAllArgs(
            Iterable<String> values) {
      ensureArgsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, args_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string args = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearArgs() {
      args_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string args = 6;</code>
     * @param value The bytes of the args to add.
     * @return This builder for chaining.
     */
    public Builder addArgsBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureArgsIsMutable();
      args_.add(value);
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:GroupCmd)
  }

  // @@protoc_insertion_point(class_scope:GroupCmd)
  private static final GroupCmd DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new GroupCmd();
  }

  public static GroupCmd getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GroupCmd>
      PARSER = new com.google.protobuf.AbstractParser<GroupCmd>() {
    @Override
    public GroupCmd parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new GroupCmd(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<GroupCmd> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<GroupCmd> getParserForType() {
    return PARSER;
  }

  @Override
  public GroupCmd getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

