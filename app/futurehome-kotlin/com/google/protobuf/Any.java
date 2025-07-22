package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Any extends GeneratedMessageLite<Any, Any.Builder> implements AnyOrBuilder {
   private static final Any DEFAULT_INSTANCE;
   private static volatile Parser<Any> PARSER;
   public static final int TYPE_URL_FIELD_NUMBER = 1;
   public static final int VALUE_FIELD_NUMBER = 2;
   private String typeUrl_ = "";
   private ByteString value_ = ByteString.EMPTY;

   static {
      Any var0 = new Any();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Any.class, var0);
   }

   private Any() {
   }

   private void clearTypeUrl() {
      this.typeUrl_ = getDefaultInstance().getTypeUrl();
   }

   private void clearValue() {
      this.value_ = getDefaultInstance().getValue();
   }

   public static Any getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static Any.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Any.Builder newBuilder(Any var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Any parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Any parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Any parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Any parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Any parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Any parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Any parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Any parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Any parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Any parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Any parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Any parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Any> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void setTypeUrl(String var1) {
      var1.getClass();
      this.typeUrl_ = var1;
   }

   private void setTypeUrlBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.typeUrl_ = var1.toStringUtf8();
   }

   private void setValue(ByteString var1) {
      var1.getClass();
      this.value_ = var1;
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
      // 00: getstatic com/google/protobuf/Any$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
      // 43: getstatic com/google/protobuf/Any.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Any
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Any.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Any.DEFAULT_INSTANCE Lcom/google/protobuf/Any;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Any.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Any
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Any
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Any.DEFAULT_INSTANCE Lcom/google/protobuf/Any;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Any.DEFAULT_INSTANCE Lcom/google/protobuf/Any;
      // 7e: ldc "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\n"
      // 80: bipush 2
      // 81: anewarray 158
      // 84: dup
      // 85: bipush 0
      // 86: ldc "typeUrl_"
      // 88: aastore
      // 89: dup
      // 8a: bipush 1
      // 8b: ldc "value_"
      // 8d: aastore
      // 8e: invokestatic com/google/protobuf/Any.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 91: areturn
      // 92: new com/google/protobuf/Any$Builder
      // 95: dup
      // 96: aconst_null
      // 97: invokespecial com/google/protobuf/Any$Builder.<init> (Lcom/google/protobuf/Any$1;)V
      // 9a: areturn
      // 9b: new com/google/protobuf/Any
      // 9e: dup
      // 9f: invokespecial com/google/protobuf/Any.<init> ()V
      // a2: areturn
   }

   @Override
   public String getTypeUrl() {
      return this.typeUrl_;
   }

   @Override
   public ByteString getTypeUrlBytes() {
      return ByteString.copyFromUtf8(this.typeUrl_);
   }

   @Override
   public ByteString getValue() {
      return this.value_;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Any, Any.Builder> implements AnyOrBuilder {
      private Builder() {
         super(Any.DEFAULT_INSTANCE);
      }

      public Any.Builder clearTypeUrl() {
         this.copyOnWrite();
         this.instance.clearTypeUrl();
         return this;
      }

      public Any.Builder clearValue() {
         this.copyOnWrite();
         this.instance.clearValue();
         return this;
      }

      @Override
      public String getTypeUrl() {
         return this.instance.getTypeUrl();
      }

      @Override
      public ByteString getTypeUrlBytes() {
         return this.instance.getTypeUrlBytes();
      }

      @Override
      public ByteString getValue() {
         return this.instance.getValue();
      }

      public Any.Builder setTypeUrl(String var1) {
         this.copyOnWrite();
         this.instance.setTypeUrl(var1);
         return this;
      }

      public Any.Builder setTypeUrlBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setTypeUrlBytes(var1);
         return this;
      }

      public Any.Builder setValue(ByteString var1) {
         this.copyOnWrite();
         this.instance.setValue(var1);
         return this;
      }
   }
}
