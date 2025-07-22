package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class Api extends GeneratedMessageLite<Api, Api.Builder> implements ApiOrBuilder {
   private static final Api DEFAULT_INSTANCE;
   public static final int METHODS_FIELD_NUMBER = 2;
   public static final int MIXINS_FIELD_NUMBER = 6;
   public static final int NAME_FIELD_NUMBER = 1;
   public static final int OPTIONS_FIELD_NUMBER = 3;
   private static volatile Parser<Api> PARSER;
   public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
   public static final int SYNTAX_FIELD_NUMBER = 7;
   public static final int VERSION_FIELD_NUMBER = 4;
   private int bitField0_;
   private Internal.ProtobufList<Method> methods_;
   private Internal.ProtobufList<Mixin> mixins_;
   private String name_ = "";
   private Internal.ProtobufList<Option> options_;
   private SourceContext sourceContext_;
   private int syntax_;
   private String version_;

   static {
      Api var0 = new Api();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Api.class, var0);
   }

   private Api() {
      this.methods_ = emptyProtobufList();
      this.options_ = emptyProtobufList();
      this.version_ = "";
      this.mixins_ = emptyProtobufList();
   }

   private void addAllMethods(Iterable<? extends Method> var1) {
      this.ensureMethodsIsMutable();
      AbstractMessageLite.addAll(var1, this.methods_);
   }

   private void addAllMixins(Iterable<? extends Mixin> var1) {
      this.ensureMixinsIsMutable();
      AbstractMessageLite.addAll(var1, this.mixins_);
   }

   private void addAllOptions(Iterable<? extends Option> var1) {
      this.ensureOptionsIsMutable();
      AbstractMessageLite.addAll(var1, this.options_);
   }

   private void addMethods(int var1, Method var2) {
      var2.getClass();
      this.ensureMethodsIsMutable();
      this.methods_.add(var1, var2);
   }

   private void addMethods(Method var1) {
      var1.getClass();
      this.ensureMethodsIsMutable();
      this.methods_.add(var1);
   }

   private void addMixins(int var1, Mixin var2) {
      var2.getClass();
      this.ensureMixinsIsMutable();
      this.mixins_.add(var1, var2);
   }

   private void addMixins(Mixin var1) {
      var1.getClass();
      this.ensureMixinsIsMutable();
      this.mixins_.add(var1);
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

   private void clearMethods() {
      this.methods_ = emptyProtobufList();
   }

   private void clearMixins() {
      this.mixins_ = emptyProtobufList();
   }

   private void clearName() {
      this.name_ = getDefaultInstance().getName();
   }

   private void clearOptions() {
      this.options_ = emptyProtobufList();
   }

   private void clearSourceContext() {
      this.sourceContext_ = null;
      this.bitField0_ &= -2;
   }

   private void clearSyntax() {
      this.syntax_ = 0;
   }

   private void clearVersion() {
      this.version_ = getDefaultInstance().getVersion();
   }

   private void ensureMethodsIsMutable() {
      Internal.ProtobufList var1 = this.methods_;
      if (!var1.isModifiable()) {
         this.methods_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   private void ensureMixinsIsMutable() {
      Internal.ProtobufList var1 = this.mixins_;
      if (!var1.isModifiable()) {
         this.mixins_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   private void ensureOptionsIsMutable() {
      Internal.ProtobufList var1 = this.options_;
      if (!var1.isModifiable()) {
         this.options_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   public static Api getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   private void mergeSourceContext(SourceContext var1) {
      var1.getClass();
      SourceContext var2 = this.sourceContext_;
      if (var2 != null && var2 != SourceContext.getDefaultInstance()) {
         this.sourceContext_ = SourceContext.newBuilder(this.sourceContext_).mergeFrom(var1).buildPartial();
      } else {
         this.sourceContext_ = var1;
      }

      this.bitField0_ |= 1;
   }

   public static Api.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Api.Builder newBuilder(Api var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Api parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Api parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Api parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Api parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Api parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Api parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Api parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Api parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Api parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Api parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Api parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Api parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Api> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void removeMethods(int var1) {
      this.ensureMethodsIsMutable();
      this.methods_.remove(var1);
   }

   private void removeMixins(int var1) {
      this.ensureMixinsIsMutable();
      this.mixins_.remove(var1);
   }

   private void removeOptions(int var1) {
      this.ensureOptionsIsMutable();
      this.options_.remove(var1);
   }

   private void setMethods(int var1, Method var2) {
      var2.getClass();
      this.ensureMethodsIsMutable();
      this.methods_.set(var1, var2);
   }

   private void setMixins(int var1, Mixin var2) {
      var2.getClass();
      this.ensureMixinsIsMutable();
      this.mixins_.set(var1, var2);
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

   private void setSourceContext(SourceContext var1) {
      var1.getClass();
      this.sourceContext_ = var1;
      this.bitField0_ |= 1;
   }

   private void setSyntax(Syntax var1) {
      this.syntax_ = var1.getNumber();
   }

   private void setSyntaxValue(int var1) {
      this.syntax_ = var1;
   }

   private void setVersion(String var1) {
      var1.getClass();
      this.version_ = var1;
   }

   private void setVersionBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.version_ = var1.toStringUtf8();
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
      // 00: getstatic com/google/protobuf/Api$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 210 201 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/Api.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Api
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Api.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Api.DEFAULT_INSTANCE Lcom/google/protobuf/Api;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Api.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Api
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Api
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Api.DEFAULT_INSTANCE Lcom/google/protobuf/Api;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Api.DEFAULT_INSTANCE Lcom/google/protobuf/Api;
      // 7e: ldc_w "\u0000\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0003\u0000\u0001Ȉ\u0002\u001b\u0003\u001b\u0004Ȉ\u0005ဉ\u0000\u0006\u001b\u0007\f"
      // 81: bipush 11
      // 83: anewarray 240
      // 86: dup
      // 87: bipush 0
      // 88: ldc_w "bitField0_"
      // 8b: aastore
      // 8c: dup
      // 8d: bipush 1
      // 8e: ldc_w "name_"
      // 91: aastore
      // 92: dup
      // 93: bipush 2
      // 94: ldc_w "methods_"
      // 97: aastore
      // 98: dup
      // 99: bipush 3
      // 9a: ldc_w com/google/protobuf/Method
      // 9d: aastore
      // 9e: dup
      // 9f: bipush 4
      // a0: ldc_w "options_"
      // a3: aastore
      // a4: dup
      // a5: bipush 5
      // a6: ldc_w com/google/protobuf/Option
      // a9: aastore
      // aa: dup
      // ab: bipush 6
      // ad: ldc_w "version_"
      // b0: aastore
      // b1: dup
      // b2: bipush 7
      // b4: ldc_w "sourceContext_"
      // b7: aastore
      // b8: dup
      // b9: bipush 8
      // bb: ldc_w "mixins_"
      // be: aastore
      // bf: dup
      // c0: bipush 9
      // c2: ldc_w com/google/protobuf/Mixin
      // c5: aastore
      // c6: dup
      // c7: bipush 10
      // c9: ldc_w "syntax_"
      // cc: aastore
      // cd: invokestatic com/google/protobuf/Api.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // d0: areturn
      // d1: new com/google/protobuf/Api$Builder
      // d4: dup
      // d5: aconst_null
      // d6: invokespecial com/google/protobuf/Api$Builder.<init> (Lcom/google/protobuf/Api$1;)V
      // d9: areturn
      // da: new com/google/protobuf/Api
      // dd: dup
      // de: invokespecial com/google/protobuf/Api.<init> ()V
      // e1: areturn
   }

   @Override
   public Method getMethods(int var1) {
      return this.methods_.get(var1);
   }

   @Override
   public int getMethodsCount() {
      return this.methods_.size();
   }

   @Override
   public List<Method> getMethodsList() {
      return this.methods_;
   }

   public MethodOrBuilder getMethodsOrBuilder(int var1) {
      return this.methods_.get(var1);
   }

   public List<? extends MethodOrBuilder> getMethodsOrBuilderList() {
      return this.methods_;
   }

   @Override
   public Mixin getMixins(int var1) {
      return this.mixins_.get(var1);
   }

   @Override
   public int getMixinsCount() {
      return this.mixins_.size();
   }

   @Override
   public List<Mixin> getMixinsList() {
      return this.mixins_;
   }

   public MixinOrBuilder getMixinsOrBuilder(int var1) {
      return this.mixins_.get(var1);
   }

   public List<? extends MixinOrBuilder> getMixinsOrBuilderList() {
      return this.mixins_;
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
   public SourceContext getSourceContext() {
      SourceContext var2 = this.sourceContext_;
      SourceContext var1 = var2;
      if (var2 == null) {
         var1 = SourceContext.getDefaultInstance();
      }

      return var1;
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

   @Override
   public String getVersion() {
      return this.version_;
   }

   @Override
   public ByteString getVersionBytes() {
      return ByteString.copyFromUtf8(this.version_);
   }

   @Override
   public boolean hasSourceContext() {
      int var1 = this.bitField0_;
      boolean var2 = true;
      if ((var1 & 1) == 0) {
         var2 = false;
      }

      return var2;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Api, Api.Builder> implements ApiOrBuilder {
      private Builder() {
         super(Api.DEFAULT_INSTANCE);
      }

      public Api.Builder addAllMethods(Iterable<? extends Method> var1) {
         this.copyOnWrite();
         this.instance.addAllMethods(var1);
         return this;
      }

      public Api.Builder addAllMixins(Iterable<? extends Mixin> var1) {
         this.copyOnWrite();
         this.instance.addAllMixins(var1);
         return this;
      }

      public Api.Builder addAllOptions(Iterable<? extends Option> var1) {
         this.copyOnWrite();
         this.instance.addAllOptions(var1);
         return this;
      }

      public Api.Builder addMethods(int var1, Method.Builder var2) {
         this.copyOnWrite();
         this.instance.addMethods(var1, var2.build());
         return this;
      }

      public Api.Builder addMethods(int var1, Method var2) {
         this.copyOnWrite();
         this.instance.addMethods(var1, var2);
         return this;
      }

      public Api.Builder addMethods(Method.Builder var1) {
         this.copyOnWrite();
         this.instance.addMethods(var1.build());
         return this;
      }

      public Api.Builder addMethods(Method var1) {
         this.copyOnWrite();
         this.instance.addMethods(var1);
         return this;
      }

      public Api.Builder addMixins(int var1, Mixin.Builder var2) {
         this.copyOnWrite();
         this.instance.addMixins(var1, var2.build());
         return this;
      }

      public Api.Builder addMixins(int var1, Mixin var2) {
         this.copyOnWrite();
         this.instance.addMixins(var1, var2);
         return this;
      }

      public Api.Builder addMixins(Mixin.Builder var1) {
         this.copyOnWrite();
         this.instance.addMixins(var1.build());
         return this;
      }

      public Api.Builder addMixins(Mixin var1) {
         this.copyOnWrite();
         this.instance.addMixins(var1);
         return this;
      }

      public Api.Builder addOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2.build());
         return this;
      }

      public Api.Builder addOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2);
         return this;
      }

      public Api.Builder addOptions(Option.Builder var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1.build());
         return this;
      }

      public Api.Builder addOptions(Option var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1);
         return this;
      }

      public Api.Builder clearMethods() {
         this.copyOnWrite();
         this.instance.clearMethods();
         return this;
      }

      public Api.Builder clearMixins() {
         this.copyOnWrite();
         this.instance.clearMixins();
         return this;
      }

      public Api.Builder clearName() {
         this.copyOnWrite();
         this.instance.clearName();
         return this;
      }

      public Api.Builder clearOptions() {
         this.copyOnWrite();
         this.instance.clearOptions();
         return this;
      }

      public Api.Builder clearSourceContext() {
         this.copyOnWrite();
         this.instance.clearSourceContext();
         return this;
      }

      public Api.Builder clearSyntax() {
         this.copyOnWrite();
         this.instance.clearSyntax();
         return this;
      }

      public Api.Builder clearVersion() {
         this.copyOnWrite();
         this.instance.clearVersion();
         return this;
      }

      @Override
      public Method getMethods(int var1) {
         return this.instance.getMethods(var1);
      }

      @Override
      public int getMethodsCount() {
         return this.instance.getMethodsCount();
      }

      @Override
      public List<Method> getMethodsList() {
         return Collections.unmodifiableList(this.instance.getMethodsList());
      }

      @Override
      public Mixin getMixins(int var1) {
         return this.instance.getMixins(var1);
      }

      @Override
      public int getMixinsCount() {
         return this.instance.getMixinsCount();
      }

      @Override
      public List<Mixin> getMixinsList() {
         return Collections.unmodifiableList(this.instance.getMixinsList());
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
      public SourceContext getSourceContext() {
         return this.instance.getSourceContext();
      }

      @Override
      public Syntax getSyntax() {
         return this.instance.getSyntax();
      }

      @Override
      public int getSyntaxValue() {
         return this.instance.getSyntaxValue();
      }

      @Override
      public String getVersion() {
         return this.instance.getVersion();
      }

      @Override
      public ByteString getVersionBytes() {
         return this.instance.getVersionBytes();
      }

      @Override
      public boolean hasSourceContext() {
         return this.instance.hasSourceContext();
      }

      public Api.Builder mergeSourceContext(SourceContext var1) {
         this.copyOnWrite();
         this.instance.mergeSourceContext(var1);
         return this;
      }

      public Api.Builder removeMethods(int var1) {
         this.copyOnWrite();
         this.instance.removeMethods(var1);
         return this;
      }

      public Api.Builder removeMixins(int var1) {
         this.copyOnWrite();
         this.instance.removeMixins(var1);
         return this;
      }

      public Api.Builder removeOptions(int var1) {
         this.copyOnWrite();
         this.instance.removeOptions(var1);
         return this;
      }

      public Api.Builder setMethods(int var1, Method.Builder var2) {
         this.copyOnWrite();
         this.instance.setMethods(var1, var2.build());
         return this;
      }

      public Api.Builder setMethods(int var1, Method var2) {
         this.copyOnWrite();
         this.instance.setMethods(var1, var2);
         return this;
      }

      public Api.Builder setMixins(int var1, Mixin.Builder var2) {
         this.copyOnWrite();
         this.instance.setMixins(var1, var2.build());
         return this;
      }

      public Api.Builder setMixins(int var1, Mixin var2) {
         this.copyOnWrite();
         this.instance.setMixins(var1, var2);
         return this;
      }

      public Api.Builder setName(String var1) {
         this.copyOnWrite();
         this.instance.setName(var1);
         return this;
      }

      public Api.Builder setNameBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setNameBytes(var1);
         return this;
      }

      public Api.Builder setOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2.build());
         return this;
      }

      public Api.Builder setOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2);
         return this;
      }

      public Api.Builder setSourceContext(SourceContext.Builder var1) {
         this.copyOnWrite();
         this.instance.setSourceContext(var1.build());
         return this;
      }

      public Api.Builder setSourceContext(SourceContext var1) {
         this.copyOnWrite();
         this.instance.setSourceContext(var1);
         return this;
      }

      public Api.Builder setSyntax(Syntax var1) {
         this.copyOnWrite();
         this.instance.setSyntax(var1);
         return this;
      }

      public Api.Builder setSyntaxValue(int var1) {
         this.copyOnWrite();
         this.instance.setSyntaxValue(var1);
         return this;
      }

      public Api.Builder setVersion(String var1) {
         this.copyOnWrite();
         this.instance.setVersion(var1);
         return this;
      }

      public Api.Builder setVersionBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setVersionBytes(var1);
         return this;
      }
   }
}
