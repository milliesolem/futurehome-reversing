package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class FieldMask extends GeneratedMessageLite<FieldMask, FieldMask.Builder> implements FieldMaskOrBuilder {
   private static final FieldMask DEFAULT_INSTANCE;
   private static volatile Parser<FieldMask> PARSER;
   public static final int PATHS_FIELD_NUMBER = 1;
   private Internal.ProtobufList<String> paths_ = GeneratedMessageLite.emptyProtobufList();

   static {
      FieldMask var0 = new FieldMask();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(FieldMask.class, var0);
   }

   private FieldMask() {
   }

   private void addAllPaths(Iterable<String> var1) {
      this.ensurePathsIsMutable();
      AbstractMessageLite.addAll(var1, this.paths_);
   }

   private void addPaths(String var1) {
      var1.getClass();
      this.ensurePathsIsMutable();
      this.paths_.add(var1);
   }

   private void addPathsBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.ensurePathsIsMutable();
      this.paths_.add(var1.toStringUtf8());
   }

   private void clearPaths() {
      this.paths_ = GeneratedMessageLite.emptyProtobufList();
   }

   private void ensurePathsIsMutable() {
      Internal.ProtobufList var1 = this.paths_;
      if (!var1.isModifiable()) {
         this.paths_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   public static FieldMask getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static FieldMask.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static FieldMask.Builder newBuilder(FieldMask var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static FieldMask parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static FieldMask parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static FieldMask parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static FieldMask parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static FieldMask parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static FieldMask parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static FieldMask parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static FieldMask parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static FieldMask parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static FieldMask parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static FieldMask parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static FieldMask parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<FieldMask> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void setPaths(int var1, String var2) {
      var2.getClass();
      this.ensurePathsIsMutable();
      this.paths_.set(var1, var2);
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
      // 00: getstatic com/google/protobuf/FieldMask$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
      // 43: getstatic com/google/protobuf/FieldMask.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/FieldMask
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/FieldMask.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/FieldMask.DEFAULT_INSTANCE Lcom/google/protobuf/FieldMask;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/FieldMask.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/FieldMask
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/FieldMask
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/FieldMask.DEFAULT_INSTANCE Lcom/google/protobuf/FieldMask;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/FieldMask.DEFAULT_INSTANCE Lcom/google/protobuf/FieldMask;
      // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001Ț"
      // 80: bipush 1
      // 81: anewarray 86
      // 84: dup
      // 85: bipush 0
      // 86: ldc "paths_"
      // 88: aastore
      // 89: invokestatic com/google/protobuf/FieldMask.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 8c: areturn
      // 8d: new com/google/protobuf/FieldMask$Builder
      // 90: dup
      // 91: aconst_null
      // 92: invokespecial com/google/protobuf/FieldMask$Builder.<init> (Lcom/google/protobuf/FieldMask$1;)V
      // 95: areturn
      // 96: new com/google/protobuf/FieldMask
      // 99: dup
      // 9a: invokespecial com/google/protobuf/FieldMask.<init> ()V
      // 9d: areturn
   }

   @Override
   public String getPaths(int var1) {
      return this.paths_.get(var1);
   }

   @Override
   public ByteString getPathsBytes(int var1) {
      return ByteString.copyFromUtf8(this.paths_.get(var1));
   }

   @Override
   public int getPathsCount() {
      return this.paths_.size();
   }

   @Override
   public List<String> getPathsList() {
      return this.paths_;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<FieldMask, FieldMask.Builder> implements FieldMaskOrBuilder {
      private Builder() {
         super(FieldMask.DEFAULT_INSTANCE);
      }

      public FieldMask.Builder addAllPaths(Iterable<String> var1) {
         this.copyOnWrite();
         this.instance.addAllPaths(var1);
         return this;
      }

      public FieldMask.Builder addPaths(String var1) {
         this.copyOnWrite();
         this.instance.addPaths(var1);
         return this;
      }

      public FieldMask.Builder addPathsBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.addPathsBytes(var1);
         return this;
      }

      public FieldMask.Builder clearPaths() {
         this.copyOnWrite();
         this.instance.clearPaths();
         return this;
      }

      @Override
      public String getPaths(int var1) {
         return this.instance.getPaths(var1);
      }

      @Override
      public ByteString getPathsBytes(int var1) {
         return this.instance.getPathsBytes(var1);
      }

      @Override
      public int getPathsCount() {
         return this.instance.getPathsCount();
      }

      @Override
      public List<String> getPathsList() {
         return Collections.unmodifiableList(this.instance.getPathsList());
      }

      public FieldMask.Builder setPaths(int var1, String var2) {
         this.copyOnWrite();
         this.instance.setPaths(var1, var2);
         return this;
      }
   }
}
