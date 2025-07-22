package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class ListValue extends GeneratedMessageLite<ListValue, ListValue.Builder> implements ListValueOrBuilder {
   private static final ListValue DEFAULT_INSTANCE;
   private static volatile Parser<ListValue> PARSER;
   public static final int VALUES_FIELD_NUMBER = 1;
   private Internal.ProtobufList<Value> values_ = emptyProtobufList();

   static {
      ListValue var0 = new ListValue();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(ListValue.class, var0);
   }

   private ListValue() {
   }

   private void addAllValues(Iterable<? extends Value> var1) {
      this.ensureValuesIsMutable();
      AbstractMessageLite.addAll(var1, this.values_);
   }

   private void addValues(int var1, Value var2) {
      var2.getClass();
      this.ensureValuesIsMutable();
      this.values_.add(var1, var2);
   }

   private void addValues(Value var1) {
      var1.getClass();
      this.ensureValuesIsMutable();
      this.values_.add(var1);
   }

   private void clearValues() {
      this.values_ = emptyProtobufList();
   }

   private void ensureValuesIsMutable() {
      Internal.ProtobufList var1 = this.values_;
      if (!var1.isModifiable()) {
         this.values_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   public static ListValue getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static ListValue.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static ListValue.Builder newBuilder(ListValue var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static ListValue parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static ListValue parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static ListValue parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static ListValue parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static ListValue parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static ListValue parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static ListValue parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static ListValue parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static ListValue parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static ListValue parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static ListValue parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static ListValue parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<ListValue> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void removeValues(int var1) {
      this.ensureValuesIsMutable();
      this.values_.remove(var1);
   }

   private void setValues(int var1, Value var2) {
      var2.getClass();
      this.ensureValuesIsMutable();
      this.values_.set(var1, var2);
   }

   @Override
   protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke param1, Object param2, Object param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: getstatic com/google/protobuf/ListValue$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 147 138 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/ListValue.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/ListValue
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/ListValue.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/ListValue.DEFAULT_INSTANCE Lcom/google/protobuf/ListValue;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/ListValue.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/ListValue
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/ListValue
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/ListValue.DEFAULT_INSTANCE Lcom/google/protobuf/ListValue;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/ListValue.DEFAULT_INSTANCE Lcom/google/protobuf/ListValue;
      // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b"
      // 80: bipush 2
      // 81: anewarray 89
      // 84: dup
      // 85: bipush 0
      // 86: ldc "values_"
      // 88: aastore
      // 89: dup
      // 8a: bipush 1
      // 8b: ldc com/google/protobuf/Value
      // 8d: aastore
      // 8e: invokestatic com/google/protobuf/ListValue.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 91: areturn
      // 92: new com/google/protobuf/ListValue$Builder
      // 95: dup
      // 96: aconst_null
      // 97: invokespecial com/google/protobuf/ListValue$Builder.<init> (Lcom/google/protobuf/ListValue$1;)V
      // 9a: areturn
      // 9b: new com/google/protobuf/ListValue
      // 9e: dup
      // 9f: invokespecial com/google/protobuf/ListValue.<init> ()V
      // a2: areturn
   }

   @Override
   public Value getValues(int var1) {
      return this.values_.get(var1);
   }

   @Override
   public int getValuesCount() {
      return this.values_.size();
   }

   @Override
   public List<Value> getValuesList() {
      return this.values_;
   }

   public ValueOrBuilder getValuesOrBuilder(int var1) {
      return this.values_.get(var1);
   }

   public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
      return this.values_;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<ListValue, ListValue.Builder> implements ListValueOrBuilder {
      private Builder() {
         super(ListValue.DEFAULT_INSTANCE);
      }

      public ListValue.Builder addAllValues(Iterable<? extends Value> var1) {
         this.copyOnWrite();
         this.instance.addAllValues(var1);
         return this;
      }

      public ListValue.Builder addValues(int var1, Value.Builder var2) {
         this.copyOnWrite();
         this.instance.addValues(var1, var2.build());
         return this;
      }

      public ListValue.Builder addValues(int var1, Value var2) {
         this.copyOnWrite();
         this.instance.addValues(var1, var2);
         return this;
      }

      public ListValue.Builder addValues(Value.Builder var1) {
         this.copyOnWrite();
         this.instance.addValues(var1.build());
         return this;
      }

      public ListValue.Builder addValues(Value var1) {
         this.copyOnWrite();
         this.instance.addValues(var1);
         return this;
      }

      public ListValue.Builder clearValues() {
         this.copyOnWrite();
         this.instance.clearValues();
         return this;
      }

      @Override
      public Value getValues(int var1) {
         return this.instance.getValues(var1);
      }

      @Override
      public int getValuesCount() {
         return this.instance.getValuesCount();
      }

      @Override
      public List<Value> getValuesList() {
         return Collections.unmodifiableList(this.instance.getValuesList());
      }

      public ListValue.Builder removeValues(int var1) {
         this.copyOnWrite();
         this.instance.removeValues(var1);
         return this;
      }

      public ListValue.Builder setValues(int var1, Value.Builder var2) {
         this.copyOnWrite();
         this.instance.setValues(var1, var2.build());
         return this;
      }

      public ListValue.Builder setValues(int var1, Value var2) {
         this.copyOnWrite();
         this.instance.setValues(var1, var2);
         return this;
      }
   }
}
