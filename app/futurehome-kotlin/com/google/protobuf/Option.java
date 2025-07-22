package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Option extends GeneratedMessageLite<Option, Option.Builder> implements OptionOrBuilder {
   private static final Option DEFAULT_INSTANCE;
   public static final int NAME_FIELD_NUMBER = 1;
   private static volatile Parser<Option> PARSER;
   public static final int VALUE_FIELD_NUMBER = 2;
   private int bitField0_;
   private String name_ = "";
   private Any value_;

   static {
      Option var0 = new Option();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Option.class, var0);
   }

   private Option() {
   }

   private void clearName() {
      this.name_ = getDefaultInstance().getName();
   }

   private void clearValue() {
      this.value_ = null;
      this.bitField0_ &= -2;
   }

   public static Option getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   private void mergeValue(Any var1) {
      var1.getClass();
      Any var2 = this.value_;
      if (var2 != null && var2 != Any.getDefaultInstance()) {
         this.value_ = Any.newBuilder(this.value_).mergeFrom(var1).buildPartial();
      } else {
         this.value_ = var1;
      }

      this.bitField0_ |= 1;
   }

   public static Option.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Option.Builder newBuilder(Option var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Option parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Option parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Option parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Option parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Option parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Option parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Option parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Option parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Option parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Option parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Option parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Option parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Option> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void setName(String var1) {
      var1.getClass();
      this.name_ = var1;
   }

   private void setNameBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.name_ = var1.toStringUtf8();
   }

   private void setValue(Any var1) {
      var1.getClass();
      this.value_ = var1;
      this.bitField0_ |= 1;
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
      // 00: getstatic com/google/protobuf/Option$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 152 143 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/Option.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Option
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Option.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Option.DEFAULT_INSTANCE Lcom/google/protobuf/Option;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Option.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Option
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Option
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Option.DEFAULT_INSTANCE Lcom/google/protobuf/Option;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Option.DEFAULT_INSTANCE Lcom/google/protobuf/Option;
      // 7e: ldc "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002ဉ\u0000"
      // 80: bipush 3
      // 81: anewarray 90
      // 84: dup
      // 85: bipush 0
      // 86: ldc "bitField0_"
      // 88: aastore
      // 89: dup
      // 8a: bipush 1
      // 8b: ldc "name_"
      // 8d: aastore
      // 8e: dup
      // 8f: bipush 2
      // 90: ldc "value_"
      // 92: aastore
      // 93: invokestatic com/google/protobuf/Option.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 96: areturn
      // 97: new com/google/protobuf/Option$Builder
      // 9a: dup
      // 9b: aconst_null
      // 9c: invokespecial com/google/protobuf/Option$Builder.<init> (Lcom/google/protobuf/Option$1;)V
      // 9f: areturn
      // a0: new com/google/protobuf/Option
      // a3: dup
      // a4: invokespecial com/google/protobuf/Option.<init> ()V
      // a7: areturn
   }

   @Override
   public String getName() {
      return this.name_;
   }

   @Override
   public ByteString getNameBytes() {
      return ByteString.copyFromUtf8(this.name_);
   }

   @Override
   public Any getValue() {
      Any var2 = this.value_;
      Any var1 = var2;
      if (var2 == null) {
         var1 = Any.getDefaultInstance();
      }

      return var1;
   }

   @Override
   public boolean hasValue() {
      int var1 = this.bitField0_;
      boolean var2 = true;
      if ((var1 & 1) == 0) {
         var2 = false;
      }

      return var2;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Option, Option.Builder> implements OptionOrBuilder {
      private Builder() {
         super(Option.DEFAULT_INSTANCE);
      }

      public Option.Builder clearName() {
         this.copyOnWrite();
         this.instance.clearName();
         return this;
      }

      public Option.Builder clearValue() {
         this.copyOnWrite();
         this.instance.clearValue();
         return this;
      }

      @Override
      public String getName() {
         return this.instance.getName();
      }

      @Override
      public ByteString getNameBytes() {
         return this.instance.getNameBytes();
      }

      @Override
      public Any getValue() {
         return this.instance.getValue();
      }

      @Override
      public boolean hasValue() {
         return this.instance.hasValue();
      }

      public Option.Builder mergeValue(Any var1) {
         this.copyOnWrite();
         this.instance.mergeValue(var1);
         return this;
      }

      public Option.Builder setName(String var1) {
         this.copyOnWrite();
         this.instance.setName(var1);
         return this;
      }

      public Option.Builder setNameBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setNameBytes(var1);
         return this;
      }

      public Option.Builder setValue(Any.Builder var1) {
         this.copyOnWrite();
         this.instance.setValue(var1.build());
         return this;
      }

      public Option.Builder setValue(Any var1) {
         this.copyOnWrite();
         this.instance.setValue(var1);
         return this;
      }
   }
}
