package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class DoubleValue extends GeneratedMessageLite<DoubleValue, DoubleValue.Builder> implements DoubleValueOrBuilder {
   private static final DoubleValue DEFAULT_INSTANCE;
   private static volatile Parser<DoubleValue> PARSER;
   public static final int VALUE_FIELD_NUMBER = 1;
   private double value_;

   static {
      DoubleValue var0 = new DoubleValue();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(DoubleValue.class, var0);
   }

   private DoubleValue() {
   }

   private void clearValue() {
      this.value_ = 0.0;
   }

   public static DoubleValue getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static DoubleValue.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static DoubleValue.Builder newBuilder(DoubleValue var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static DoubleValue of(double var0) {
      return newBuilder().setValue(var0).build();
   }

   public static DoubleValue parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static DoubleValue parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static DoubleValue parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static DoubleValue parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static DoubleValue parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static DoubleValue parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static DoubleValue parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static DoubleValue parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static DoubleValue parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static DoubleValue parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static DoubleValue parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static DoubleValue parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<DoubleValue> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void setValue(double var1) {
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
      // 00: getstatic com/google/protobuf/DoubleValue$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
      // 43: getstatic com/google/protobuf/DoubleValue.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/DoubleValue
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/DoubleValue.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/DoubleValue.DEFAULT_INSTANCE Lcom/google/protobuf/DoubleValue;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/DoubleValue.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/DoubleValue
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/DoubleValue
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/DoubleValue.DEFAULT_INSTANCE Lcom/google/protobuf/DoubleValue;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/DoubleValue.DEFAULT_INSTANCE Lcom/google/protobuf/DoubleValue;
      // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0000"
      // 80: bipush 1
      // 81: anewarray 162
      // 84: dup
      // 85: bipush 0
      // 86: ldc "value_"
      // 88: aastore
      // 89: invokestatic com/google/protobuf/DoubleValue.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 8c: areturn
      // 8d: new com/google/protobuf/DoubleValue$Builder
      // 90: dup
      // 91: aconst_null
      // 92: invokespecial com/google/protobuf/DoubleValue$Builder.<init> (Lcom/google/protobuf/DoubleValue$1;)V
      // 95: areturn
      // 96: new com/google/protobuf/DoubleValue
      // 99: dup
      // 9a: invokespecial com/google/protobuf/DoubleValue.<init> ()V
      // 9d: areturn
   }

   @Override
   public double getValue() {
      return this.value_;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<DoubleValue, DoubleValue.Builder> implements DoubleValueOrBuilder {
      private Builder() {
         super(DoubleValue.DEFAULT_INSTANCE);
      }

      public DoubleValue.Builder clearValue() {
         this.copyOnWrite();
         this.instance.clearValue();
         return this;
      }

      @Override
      public double getValue() {
         return this.instance.getValue();
      }

      public DoubleValue.Builder setValue(double var1) {
         this.copyOnWrite();
         this.instance.setValue(var1);
         return this;
      }
   }
}
