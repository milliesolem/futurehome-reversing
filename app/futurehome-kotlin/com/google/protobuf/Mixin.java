package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Mixin extends GeneratedMessageLite<Mixin, Mixin.Builder> implements MixinOrBuilder {
   private static final Mixin DEFAULT_INSTANCE;
   public static final int NAME_FIELD_NUMBER = 1;
   private static volatile Parser<Mixin> PARSER;
   public static final int ROOT_FIELD_NUMBER = 2;
   private String name_ = "";
   private String root_ = "";

   static {
      Mixin var0 = new Mixin();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Mixin.class, var0);
   }

   private Mixin() {
   }

   private void clearName() {
      this.name_ = getDefaultInstance().getName();
   }

   private void clearRoot() {
      this.root_ = getDefaultInstance().getRoot();
   }

   public static Mixin getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static Mixin.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Mixin.Builder newBuilder(Mixin var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Mixin parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Mixin parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Mixin parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Mixin parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Mixin parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Mixin parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Mixin parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Mixin parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Mixin parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Mixin parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Mixin parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Mixin parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Mixin> parser() {
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

   private void setRoot(String var1) {
      var1.getClass();
      this.root_ = var1;
   }

   private void setRootBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.root_ = var1.toStringUtf8();
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
      // 00: getstatic com/google/protobuf/Mixin$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
      // 43: getstatic com/google/protobuf/Mixin.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Mixin
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Mixin.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Mixin.DEFAULT_INSTANCE Lcom/google/protobuf/Mixin;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Mixin.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Mixin
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Mixin
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Mixin.DEFAULT_INSTANCE Lcom/google/protobuf/Mixin;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Mixin.DEFAULT_INSTANCE Lcom/google/protobuf/Mixin;
      // 7e: ldc "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ"
      // 80: bipush 2
      // 81: anewarray 155
      // 84: dup
      // 85: bipush 0
      // 86: ldc "name_"
      // 88: aastore
      // 89: dup
      // 8a: bipush 1
      // 8b: ldc "root_"
      // 8d: aastore
      // 8e: invokestatic com/google/protobuf/Mixin.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 91: areturn
      // 92: new com/google/protobuf/Mixin$Builder
      // 95: dup
      // 96: aconst_null
      // 97: invokespecial com/google/protobuf/Mixin$Builder.<init> (Lcom/google/protobuf/Mixin$1;)V
      // 9a: areturn
      // 9b: new com/google/protobuf/Mixin
      // 9e: dup
      // 9f: invokespecial com/google/protobuf/Mixin.<init> ()V
      // a2: areturn
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
   public String getRoot() {
      return this.root_;
   }

   @Override
   public ByteString getRootBytes() {
      return ByteString.copyFromUtf8(this.root_);
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Mixin, Mixin.Builder> implements MixinOrBuilder {
      private Builder() {
         super(Mixin.DEFAULT_INSTANCE);
      }

      public Mixin.Builder clearName() {
         this.copyOnWrite();
         this.instance.clearName();
         return this;
      }

      public Mixin.Builder clearRoot() {
         this.copyOnWrite();
         this.instance.clearRoot();
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
      public String getRoot() {
         return this.instance.getRoot();
      }

      @Override
      public ByteString getRootBytes() {
         return this.instance.getRootBytes();
      }

      public Mixin.Builder setName(String var1) {
         this.copyOnWrite();
         this.instance.setName(var1);
         return this;
      }

      public Mixin.Builder setNameBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setNameBytes(var1);
         return this;
      }

      public Mixin.Builder setRoot(String var1) {
         this.copyOnWrite();
         this.instance.setRoot(var1);
         return this;
      }

      public Mixin.Builder setRootBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setRootBytes(var1);
         return this;
      }
   }
}
