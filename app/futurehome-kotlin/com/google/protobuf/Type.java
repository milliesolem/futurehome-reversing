package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class Type extends GeneratedMessageLite<Type, Type.Builder> implements TypeOrBuilder {
   private static final Type DEFAULT_INSTANCE;
   public static final int EDITION_FIELD_NUMBER = 7;
   public static final int FIELDS_FIELD_NUMBER = 2;
   public static final int NAME_FIELD_NUMBER = 1;
   public static final int ONEOFS_FIELD_NUMBER = 3;
   public static final int OPTIONS_FIELD_NUMBER = 4;
   private static volatile Parser<Type> PARSER;
   public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
   public static final int SYNTAX_FIELD_NUMBER = 6;
   private int bitField0_;
   private String edition_;
   private Internal.ProtobufList<Field> fields_;
   private String name_ = "";
   private Internal.ProtobufList<String> oneofs_;
   private Internal.ProtobufList<Option> options_;
   private SourceContext sourceContext_;
   private int syntax_;

   static {
      Type var0 = new Type();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Type.class, var0);
   }

   private Type() {
      this.fields_ = emptyProtobufList();
      this.oneofs_ = GeneratedMessageLite.emptyProtobufList();
      this.options_ = emptyProtobufList();
      this.edition_ = "";
   }

   private void addAllFields(Iterable<? extends Field> var1) {
      this.ensureFieldsIsMutable();
      AbstractMessageLite.addAll(var1, this.fields_);
   }

   private void addAllOneofs(Iterable<String> var1) {
      this.ensureOneofsIsMutable();
      AbstractMessageLite.addAll(var1, this.oneofs_);
   }

   private void addAllOptions(Iterable<? extends Option> var1) {
      this.ensureOptionsIsMutable();
      AbstractMessageLite.addAll(var1, this.options_);
   }

   private void addFields(int var1, Field var2) {
      var2.getClass();
      this.ensureFieldsIsMutable();
      this.fields_.add(var1, var2);
   }

   private void addFields(Field var1) {
      var1.getClass();
      this.ensureFieldsIsMutable();
      this.fields_.add(var1);
   }

   private void addOneofs(String var1) {
      var1.getClass();
      this.ensureOneofsIsMutable();
      this.oneofs_.add(var1);
   }

   private void addOneofsBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.ensureOneofsIsMutable();
      this.oneofs_.add(var1.toStringUtf8());
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

   private void clearEdition() {
      this.edition_ = getDefaultInstance().getEdition();
   }

   private void clearFields() {
      this.fields_ = emptyProtobufList();
   }

   private void clearName() {
      this.name_ = getDefaultInstance().getName();
   }

   private void clearOneofs() {
      this.oneofs_ = GeneratedMessageLite.emptyProtobufList();
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

   private void ensureFieldsIsMutable() {
      Internal.ProtobufList var1 = this.fields_;
      if (!var1.isModifiable()) {
         this.fields_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   private void ensureOneofsIsMutable() {
      Internal.ProtobufList var1 = this.oneofs_;
      if (!var1.isModifiable()) {
         this.oneofs_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   private void ensureOptionsIsMutable() {
      Internal.ProtobufList var1 = this.options_;
      if (!var1.isModifiable()) {
         this.options_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   public static Type getDefaultInstance() {
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

   public static Type.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Type.Builder newBuilder(Type var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Type parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Type parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Type parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Type parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Type parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Type parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Type parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Type parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Type parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Type parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Type parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Type parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Type> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void removeFields(int var1) {
      this.ensureFieldsIsMutable();
      this.fields_.remove(var1);
   }

   private void removeOptions(int var1) {
      this.ensureOptionsIsMutable();
      this.options_.remove(var1);
   }

   private void setEdition(String var1) {
      var1.getClass();
      this.edition_ = var1;
   }

   private void setEditionBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.edition_ = var1.toStringUtf8();
   }

   private void setFields(int var1, Field var2) {
      var2.getClass();
      this.ensureFieldsIsMutable();
      this.fields_.set(var1, var2);
   }

   private void setName(String var1) {
      var1.getClass();
      this.name_ = var1;
   }

   private void setNameBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.name_ = var1.toStringUtf8();
   }

   private void setOneofs(int var1, String var2) {
      var2.getClass();
      this.ensureOneofsIsMutable();
      this.oneofs_.set(var1, var2);
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
      // 00: getstatic com/google/protobuf/Type$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 203 194 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/Type.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Type
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Type.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Type.DEFAULT_INSTANCE Lcom/google/protobuf/Type;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Type.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Type
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Type
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Type.DEFAULT_INSTANCE Lcom/google/protobuf/Type;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Type.DEFAULT_INSTANCE Lcom/google/protobuf/Type;
      // 7e: ldc_w "\u0000\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0003\u0000\u0001Ȉ\u0002\u001b\u0003Ț\u0004\u001b\u0005ဉ\u0000\u0006\f\u0007Ȉ"
      // 81: bipush 10
      // 83: anewarray 236
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
      // 94: ldc_w "fields_"
      // 97: aastore
      // 98: dup
      // 99: bipush 3
      // 9a: ldc_w com/google/protobuf/Field
      // 9d: aastore
      // 9e: dup
      // 9f: bipush 4
      // a0: ldc_w "oneofs_"
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
      // b4: ldc_w "sourceContext_"
      // b7: aastore
      // b8: dup
      // b9: bipush 8
      // bb: ldc_w "syntax_"
      // be: aastore
      // bf: dup
      // c0: bipush 9
      // c2: ldc_w "edition_"
      // c5: aastore
      // c6: invokestatic com/google/protobuf/Type.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // c9: areturn
      // ca: new com/google/protobuf/Type$Builder
      // cd: dup
      // ce: aconst_null
      // cf: invokespecial com/google/protobuf/Type$Builder.<init> (Lcom/google/protobuf/Type$1;)V
      // d2: areturn
      // d3: new com/google/protobuf/Type
      // d6: dup
      // d7: invokespecial com/google/protobuf/Type.<init> ()V
      // da: areturn
   }

   @Override
   public String getEdition() {
      return this.edition_;
   }

   @Override
   public ByteString getEditionBytes() {
      return ByteString.copyFromUtf8(this.edition_);
   }

   @Override
   public Field getFields(int var1) {
      return this.fields_.get(var1);
   }

   @Override
   public int getFieldsCount() {
      return this.fields_.size();
   }

   @Override
   public List<Field> getFieldsList() {
      return this.fields_;
   }

   public FieldOrBuilder getFieldsOrBuilder(int var1) {
      return this.fields_.get(var1);
   }

   public List<? extends FieldOrBuilder> getFieldsOrBuilderList() {
      return this.fields_;
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
   public String getOneofs(int var1) {
      return this.oneofs_.get(var1);
   }

   @Override
   public ByteString getOneofsBytes(int var1) {
      return ByteString.copyFromUtf8(this.oneofs_.get(var1));
   }

   @Override
   public int getOneofsCount() {
      return this.oneofs_.size();
   }

   @Override
   public List<String> getOneofsList() {
      return this.oneofs_;
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
   public boolean hasSourceContext() {
      int var1 = this.bitField0_;
      boolean var2 = true;
      if ((var1 & 1) == 0) {
         var2 = false;
      }

      return var2;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Type, Type.Builder> implements TypeOrBuilder {
      private Builder() {
         super(Type.DEFAULT_INSTANCE);
      }

      public Type.Builder addAllFields(Iterable<? extends Field> var1) {
         this.copyOnWrite();
         this.instance.addAllFields(var1);
         return this;
      }

      public Type.Builder addAllOneofs(Iterable<String> var1) {
         this.copyOnWrite();
         this.instance.addAllOneofs(var1);
         return this;
      }

      public Type.Builder addAllOptions(Iterable<? extends Option> var1) {
         this.copyOnWrite();
         this.instance.addAllOptions(var1);
         return this;
      }

      public Type.Builder addFields(int var1, Field.Builder var2) {
         this.copyOnWrite();
         this.instance.addFields(var1, var2.build());
         return this;
      }

      public Type.Builder addFields(int var1, Field var2) {
         this.copyOnWrite();
         this.instance.addFields(var1, var2);
         return this;
      }

      public Type.Builder addFields(Field.Builder var1) {
         this.copyOnWrite();
         this.instance.addFields(var1.build());
         return this;
      }

      public Type.Builder addFields(Field var1) {
         this.copyOnWrite();
         this.instance.addFields(var1);
         return this;
      }

      public Type.Builder addOneofs(String var1) {
         this.copyOnWrite();
         this.instance.addOneofs(var1);
         return this;
      }

      public Type.Builder addOneofsBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.addOneofsBytes(var1);
         return this;
      }

      public Type.Builder addOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2.build());
         return this;
      }

      public Type.Builder addOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2);
         return this;
      }

      public Type.Builder addOptions(Option.Builder var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1.build());
         return this;
      }

      public Type.Builder addOptions(Option var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1);
         return this;
      }

      public Type.Builder clearEdition() {
         this.copyOnWrite();
         this.instance.clearEdition();
         return this;
      }

      public Type.Builder clearFields() {
         this.copyOnWrite();
         this.instance.clearFields();
         return this;
      }

      public Type.Builder clearName() {
         this.copyOnWrite();
         this.instance.clearName();
         return this;
      }

      public Type.Builder clearOneofs() {
         this.copyOnWrite();
         this.instance.clearOneofs();
         return this;
      }

      public Type.Builder clearOptions() {
         this.copyOnWrite();
         this.instance.clearOptions();
         return this;
      }

      public Type.Builder clearSourceContext() {
         this.copyOnWrite();
         this.instance.clearSourceContext();
         return this;
      }

      public Type.Builder clearSyntax() {
         this.copyOnWrite();
         this.instance.clearSyntax();
         return this;
      }

      @Override
      public String getEdition() {
         return this.instance.getEdition();
      }

      @Override
      public ByteString getEditionBytes() {
         return this.instance.getEditionBytes();
      }

      @Override
      public Field getFields(int var1) {
         return this.instance.getFields(var1);
      }

      @Override
      public int getFieldsCount() {
         return this.instance.getFieldsCount();
      }

      @Override
      public List<Field> getFieldsList() {
         return Collections.unmodifiableList(this.instance.getFieldsList());
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
      public String getOneofs(int var1) {
         return this.instance.getOneofs(var1);
      }

      @Override
      public ByteString getOneofsBytes(int var1) {
         return this.instance.getOneofsBytes(var1);
      }

      @Override
      public int getOneofsCount() {
         return this.instance.getOneofsCount();
      }

      @Override
      public List<String> getOneofsList() {
         return Collections.unmodifiableList(this.instance.getOneofsList());
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
      public boolean hasSourceContext() {
         return this.instance.hasSourceContext();
      }

      public Type.Builder mergeSourceContext(SourceContext var1) {
         this.copyOnWrite();
         this.instance.mergeSourceContext(var1);
         return this;
      }

      public Type.Builder removeFields(int var1) {
         this.copyOnWrite();
         this.instance.removeFields(var1);
         return this;
      }

      public Type.Builder removeOptions(int var1) {
         this.copyOnWrite();
         this.instance.removeOptions(var1);
         return this;
      }

      public Type.Builder setEdition(String var1) {
         this.copyOnWrite();
         this.instance.setEdition(var1);
         return this;
      }

      public Type.Builder setEditionBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setEditionBytes(var1);
         return this;
      }

      public Type.Builder setFields(int var1, Field.Builder var2) {
         this.copyOnWrite();
         this.instance.setFields(var1, var2.build());
         return this;
      }

      public Type.Builder setFields(int var1, Field var2) {
         this.copyOnWrite();
         this.instance.setFields(var1, var2);
         return this;
      }

      public Type.Builder setName(String var1) {
         this.copyOnWrite();
         this.instance.setName(var1);
         return this;
      }

      public Type.Builder setNameBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setNameBytes(var1);
         return this;
      }

      public Type.Builder setOneofs(int var1, String var2) {
         this.copyOnWrite();
         this.instance.setOneofs(var1, var2);
         return this;
      }

      public Type.Builder setOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2.build());
         return this;
      }

      public Type.Builder setOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2);
         return this;
      }

      public Type.Builder setSourceContext(SourceContext.Builder var1) {
         this.copyOnWrite();
         this.instance.setSourceContext(var1.build());
         return this;
      }

      public Type.Builder setSourceContext(SourceContext var1) {
         this.copyOnWrite();
         this.instance.setSourceContext(var1);
         return this;
      }

      public Type.Builder setSyntax(Syntax var1) {
         this.copyOnWrite();
         this.instance.setSyntax(var1);
         return this;
      }

      public Type.Builder setSyntaxValue(int var1) {
         this.copyOnWrite();
         this.instance.setSyntaxValue(var1);
         return this;
      }
   }
}
