package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class Method extends GeneratedMessageLite<Method, Method.Builder> implements MethodOrBuilder {
   private static final Method DEFAULT_INSTANCE;
   public static final int NAME_FIELD_NUMBER = 1;
   public static final int OPTIONS_FIELD_NUMBER = 6;
   private static volatile Parser<Method> PARSER;
   public static final int REQUEST_STREAMING_FIELD_NUMBER = 3;
   public static final int REQUEST_TYPE_URL_FIELD_NUMBER = 2;
   public static final int RESPONSE_STREAMING_FIELD_NUMBER = 5;
   public static final int RESPONSE_TYPE_URL_FIELD_NUMBER = 4;
   public static final int SYNTAX_FIELD_NUMBER = 7;
   private String name_ = "";
   private Internal.ProtobufList<Option> options_;
   private boolean requestStreaming_;
   private String requestTypeUrl_ = "";
   private boolean responseStreaming_;
   private String responseTypeUrl_ = "";
   private int syntax_;

   static {
      Method var0 = new Method();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Method.class, var0);
   }

   private Method() {
      this.options_ = emptyProtobufList();
   }

   private void addAllOptions(Iterable<? extends Option> var1) {
      this.ensureOptionsIsMutable();
      AbstractMessageLite.addAll(var1, this.options_);
   }

   private void addOptions(int var1, Option var2) {
      var2.getClass();
      this.ensureOptionsIsMutable();
      this.options_.add(var1, var2);
   }

   private void addOptions(Option var1) {
      var1.getClass();
      this.ensureOptionsIsMutable();
      this.options_.add(var1);
   }

   private void clearName() {
      this.name_ = getDefaultInstance().getName();
   }

   private void clearOptions() {
      this.options_ = emptyProtobufList();
   }

   private void clearRequestStreaming() {
      this.requestStreaming_ = false;
   }

   private void clearRequestTypeUrl() {
      this.requestTypeUrl_ = getDefaultInstance().getRequestTypeUrl();
   }

   private void clearResponseStreaming() {
      this.responseStreaming_ = false;
   }

   private void clearResponseTypeUrl() {
      this.responseTypeUrl_ = getDefaultInstance().getResponseTypeUrl();
   }

   private void clearSyntax() {
      this.syntax_ = 0;
   }

   private void ensureOptionsIsMutable() {
      Internal.ProtobufList var1 = this.options_;
      if (!var1.isModifiable()) {
         this.options_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   public static Method getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static Method.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Method.Builder newBuilder(Method var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Method parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Method parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Method parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Method parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Method parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Method parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Method parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Method parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Method parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Method parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Method parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Method parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Method> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void removeOptions(int var1) {
      this.ensureOptionsIsMutable();
      this.options_.remove(var1);
   }

   private void setName(String var1) {
      var1.getClass();
      this.name_ = var1;
   }

   private void setNameBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.name_ = var1.toStringUtf8();
   }

   private void setOptions(int var1, Option var2) {
      var2.getClass();
      this.ensureOptionsIsMutable();
      this.options_.set(var1, var2);
   }

   private void setRequestStreaming(boolean var1) {
      this.requestStreaming_ = var1;
   }

   private void setRequestTypeUrl(String var1) {
      var1.getClass();
      this.requestTypeUrl_ = var1;
   }

   private void setRequestTypeUrlBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.requestTypeUrl_ = var1.toStringUtf8();
   }

   private void setResponseStreaming(boolean var1) {
      this.responseStreaming_ = var1;
   }

   private void setResponseTypeUrl(String var1) {
      var1.getClass();
      this.responseTypeUrl_ = var1;
   }

   private void setResponseTypeUrlBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.responseTypeUrl_ = var1.toStringUtf8();
   }

   private void setSyntax(Syntax var1) {
      this.syntax_ = var1.getNumber();
   }

   private void setSyntaxValue(int var1) {
      this.syntax_ = var1;
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
      // 00: getstatic com/google/protobuf/Method$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 189 180 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/Method.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Method
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Method.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Method.DEFAULT_INSTANCE Lcom/google/protobuf/Method;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Method.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Method
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Method
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Method.DEFAULT_INSTANCE Lcom/google/protobuf/Method;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Method.DEFAULT_INSTANCE Lcom/google/protobuf/Method;
      // 7e: ldc_w "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003\u0007\u0004Ȉ\u0005\u0007\u0006\u001b\u0007\f"
      // 81: bipush 8
      // 83: anewarray 189
      // 86: dup
      // 87: bipush 0
      // 88: ldc_w "name_"
      // 8b: aastore
      // 8c: dup
      // 8d: bipush 1
      // 8e: ldc_w "requestTypeUrl_"
      // 91: aastore
      // 92: dup
      // 93: bipush 2
      // 94: ldc_w "requestStreaming_"
      // 97: aastore
      // 98: dup
      // 99: bipush 3
      // 9a: ldc_w "responseTypeUrl_"
      // 9d: aastore
      // 9e: dup
      // 9f: bipush 4
      // a0: ldc_w "responseStreaming_"
      // a3: aastore
      // a4: dup
      // a5: bipush 5
      // a6: ldc_w "options_"
      // a9: aastore
      // aa: dup
      // ab: bipush 6
      // ad: ldc_w com/google/protobuf/Option
      // b0: aastore
      // b1: dup
      // b2: bipush 7
      // b4: ldc_w "syntax_"
      // b7: aastore
      // b8: invokestatic com/google/protobuf/Method.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // bb: areturn
      // bc: new com/google/protobuf/Method$Builder
      // bf: dup
      // c0: aconst_null
      // c1: invokespecial com/google/protobuf/Method$Builder.<init> (Lcom/google/protobuf/Method$1;)V
      // c4: areturn
      // c5: new com/google/protobuf/Method
      // c8: dup
      // c9: invokespecial com/google/protobuf/Method.<init> ()V
      // cc: areturn
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
   public Option getOptions(int var1) {
      return this.options_.get(var1);
   }

   @Override
   public int getOptionsCount() {
      return this.options_.size();
   }

   @Override
   public List<Option> getOptionsList() {
      return this.options_;
   }

   public OptionOrBuilder getOptionsOrBuilder(int var1) {
      return this.options_.get(var1);
   }

   public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
      return this.options_;
   }

   @Override
   public boolean getRequestStreaming() {
      return this.requestStreaming_;
   }

   @Override
   public String getRequestTypeUrl() {
      return this.requestTypeUrl_;
   }

   @Override
   public ByteString getRequestTypeUrlBytes() {
      return ByteString.copyFromUtf8(this.requestTypeUrl_);
   }

   @Override
   public boolean getResponseStreaming() {
      return this.responseStreaming_;
   }

   @Override
   public String getResponseTypeUrl() {
      return this.responseTypeUrl_;
   }

   @Override
   public ByteString getResponseTypeUrlBytes() {
      return ByteString.copyFromUtf8(this.responseTypeUrl_);
   }

   @Override
   public Syntax getSyntax() {
      Syntax var2 = Syntax.forNumber(this.syntax_);
      Syntax var1 = var2;
      if (var2 == null) {
         var1 = Syntax.UNRECOGNIZED;
      }

      return var1;
   }

   @Override
   public int getSyntaxValue() {
      return this.syntax_;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Method, Method.Builder> implements MethodOrBuilder {
      private Builder() {
         super(Method.DEFAULT_INSTANCE);
      }

      public Method.Builder addAllOptions(Iterable<? extends Option> var1) {
         this.copyOnWrite();
         this.instance.addAllOptions(var1);
         return this;
      }

      public Method.Builder addOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2.build());
         return this;
      }

      public Method.Builder addOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2);
         return this;
      }

      public Method.Builder addOptions(Option.Builder var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1.build());
         return this;
      }

      public Method.Builder addOptions(Option var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1);
         return this;
      }

      public Method.Builder clearName() {
         this.copyOnWrite();
         this.instance.clearName();
         return this;
      }

      public Method.Builder clearOptions() {
         this.copyOnWrite();
         this.instance.clearOptions();
         return this;
      }

      public Method.Builder clearRequestStreaming() {
         this.copyOnWrite();
         this.instance.clearRequestStreaming();
         return this;
      }

      public Method.Builder clearRequestTypeUrl() {
         this.copyOnWrite();
         this.instance.clearRequestTypeUrl();
         return this;
      }

      public Method.Builder clearResponseStreaming() {
         this.copyOnWrite();
         this.instance.clearResponseStreaming();
         return this;
      }

      public Method.Builder clearResponseTypeUrl() {
         this.copyOnWrite();
         this.instance.clearResponseTypeUrl();
         return this;
      }

      public Method.Builder clearSyntax() {
         this.copyOnWrite();
         this.instance.clearSyntax();
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
      public Option getOptions(int var1) {
         return this.instance.getOptions(var1);
      }

      @Override
      public int getOptionsCount() {
         return this.instance.getOptionsCount();
      }

      @Override
      public List<Option> getOptionsList() {
         return Collections.unmodifiableList(this.instance.getOptionsList());
      }

      @Override
      public boolean getRequestStreaming() {
         return this.instance.getRequestStreaming();
      }

      @Override
      public String getRequestTypeUrl() {
         return this.instance.getRequestTypeUrl();
      }

      @Override
      public ByteString getRequestTypeUrlBytes() {
         return this.instance.getRequestTypeUrlBytes();
      }

      @Override
      public boolean getResponseStreaming() {
         return this.instance.getResponseStreaming();
      }

      @Override
      public String getResponseTypeUrl() {
         return this.instance.getResponseTypeUrl();
      }

      @Override
      public ByteString getResponseTypeUrlBytes() {
         return this.instance.getResponseTypeUrlBytes();
      }

      @Override
      public Syntax getSyntax() {
         return this.instance.getSyntax();
      }

      @Override
      public int getSyntaxValue() {
         return this.instance.getSyntaxValue();
      }

      public Method.Builder removeOptions(int var1) {
         this.copyOnWrite();
         this.instance.removeOptions(var1);
         return this;
      }

      public Method.Builder setName(String var1) {
         this.copyOnWrite();
         this.instance.setName(var1);
         return this;
      }

      public Method.Builder setNameBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setNameBytes(var1);
         return this;
      }

      public Method.Builder setOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2.build());
         return this;
      }

      public Method.Builder setOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2);
         return this;
      }

      public Method.Builder setRequestStreaming(boolean var1) {
         this.copyOnWrite();
         this.instance.setRequestStreaming(var1);
         return this;
      }

      public Method.Builder setRequestTypeUrl(String var1) {
         this.copyOnWrite();
         this.instance.setRequestTypeUrl(var1);
         return this;
      }

      public Method.Builder setRequestTypeUrlBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setRequestTypeUrlBytes(var1);
         return this;
      }

      public Method.Builder setResponseStreaming(boolean var1) {
         this.copyOnWrite();
         this.instance.setResponseStreaming(var1);
         return this;
      }

      public Method.Builder setResponseTypeUrl(String var1) {
         this.copyOnWrite();
         this.instance.setResponseTypeUrl(var1);
         return this;
      }

      public Method.Builder setResponseTypeUrlBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setResponseTypeUrlBytes(var1);
         return this;
      }

      public Method.Builder setSyntax(Syntax var1) {
         this.copyOnWrite();
         this.instance.setSyntax(var1);
         return this;
      }

      public Method.Builder setSyntaxValue(int var1) {
         this.copyOnWrite();
         this.instance.setSyntaxValue(var1);
         return this;
      }
   }
}
