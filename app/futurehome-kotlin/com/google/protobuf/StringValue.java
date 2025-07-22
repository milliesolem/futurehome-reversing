package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class StringValue extends GeneratedMessageLite<StringValue, StringValue.Builder> implements StringValueOrBuilder {
   private static final StringValue DEFAULT_INSTANCE;
   private static volatile Parser<StringValue> PARSER;
   public static final int VALUE_FIELD_NUMBER = 1;
   private String value_ = "";

   static {
      StringValue var0 = new StringValue();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(StringValue.class, var0);
   }

   private StringValue() {
   }

   private void clearValue() {
      this.value_ = getDefaultInstance().getValue();
   }

   public static StringValue getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static StringValue.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static StringValue.Builder newBuilder(StringValue var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static StringValue of(String var0) {
      return newBuilder().setValue(var0).build();
   }

   public static StringValue parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static StringValue parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static StringValue parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static StringValue parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static StringValue parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static StringValue parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static StringValue parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static StringValue parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static StringValue parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static StringValue parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static StringValue parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static StringValue parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<StringValue> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void setValue(String var1) {
      var1.getClass();
      this.value_ = var1;
   }

   private void setValueBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.value_ = var1.toStringUtf8();
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
      // 00: getstatic com/google/protobuf/StringValue$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 142 133 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/StringValue.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/StringValue
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/StringValue.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/StringValue.DEFAULT_INSTANCE Lcom/google/protobuf/StringValue;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/StringValue.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/StringValue
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/StringValue
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/StringValue.DEFAULT_INSTANCE Lcom/google/protobuf/StringValue;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/StringValue.DEFAULT_INSTANCE Lcom/google/protobuf/StringValue;
      // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ"
      // 80: bipush 1
      // 81: anewarray 146
      // 84: dup
      // 85: bipush 0
      // 86: ldc "value_"
      // 88: aastore
      // 89: invokestatic com/google/protobuf/StringValue.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 8c: areturn
      // 8d: new com/google/protobuf/StringValue$Builder
      // 90: dup
      // 91: aconst_null
      // 92: invokespecial com/google/protobuf/StringValue$Builder.<init> (Lcom/google/protobuf/StringValue$1;)V
      // 95: areturn
      // 96: new com/google/protobuf/StringValue
      // 99: dup
      // 9a: invokespecial com/google/protobuf/StringValue.<init> ()V
      // 9d: areturn
   }

   @Override
   public String getValue() {
      return this.value_;
   }

   @Override
   public ByteString getValueBytes() {
      return ByteString.copyFromUtf8(this.value_);
   }

   public static final class Builder extends GeneratedMessageLite.Builder<StringValue, StringValue.Builder> implements StringValueOrBuilder {
      private Builder() {
         super(StringValue.DEFAULT_INSTANCE);
      }

      public StringValue.Builder clearValue() {
         this.copyOnWrite();
         this.instance.clearValue();
         return this;
      }

      @Override
      public String getValue() {
         return this.instance.getValue();
      }

      @Override
      public ByteString getValueBytes() {
         return this.instance.getValueBytes();
      }

      public StringValue.Builder setValue(String var1) {
         this.copyOnWrite();
         this.instance.setValue(var1);
         return this;
      }

      public StringValue.Builder setValueBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setValueBytes(var1);
         return this;
      }
   }
}
