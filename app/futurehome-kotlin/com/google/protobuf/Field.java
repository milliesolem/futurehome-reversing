package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class Field extends GeneratedMessageLite<Field, Field.Builder> implements FieldOrBuilder {
   public static final int CARDINALITY_FIELD_NUMBER = 2;
   private static final Field DEFAULT_INSTANCE;
   public static final int DEFAULT_VALUE_FIELD_NUMBER = 11;
   public static final int JSON_NAME_FIELD_NUMBER = 10;
   public static final int KIND_FIELD_NUMBER = 1;
   public static final int NAME_FIELD_NUMBER = 4;
   public static final int NUMBER_FIELD_NUMBER = 3;
   public static final int ONEOF_INDEX_FIELD_NUMBER = 7;
   public static final int OPTIONS_FIELD_NUMBER = 9;
   public static final int PACKED_FIELD_NUMBER = 8;
   private static volatile Parser<Field> PARSER;
   public static final int TYPE_URL_FIELD_NUMBER = 6;
   private int cardinality_;
   private String defaultValue_;
   private String jsonName_;
   private int kind_;
   private String name_ = "";
   private int number_;
   private int oneofIndex_;
   private Internal.ProtobufList<Option> options_;
   private boolean packed_;
   private String typeUrl_ = "";

   static {
      Field var0 = new Field();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Field.class, var0);
   }

   private Field() {
      this.options_ = emptyProtobufList();
      this.jsonName_ = "";
      this.defaultValue_ = "";
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

   private void clearCardinality() {
      this.cardinality_ = 0;
   }

   private void clearDefaultValue() {
      this.defaultValue_ = getDefaultInstance().getDefaultValue();
   }

   private void clearJsonName() {
      this.jsonName_ = getDefaultInstance().getJsonName();
   }

   private void clearKind() {
      this.kind_ = 0;
   }

   private void clearName() {
      this.name_ = getDefaultInstance().getName();
   }

   private void clearNumber() {
      this.number_ = 0;
   }

   private void clearOneofIndex() {
      this.oneofIndex_ = 0;
   }

   private void clearOptions() {
      this.options_ = emptyProtobufList();
   }

   private void clearPacked() {
      this.packed_ = false;
   }

   private void clearTypeUrl() {
      this.typeUrl_ = getDefaultInstance().getTypeUrl();
   }

   private void ensureOptionsIsMutable() {
      Internal.ProtobufList var1 = this.options_;
      if (!var1.isModifiable()) {
         this.options_ = GeneratedMessageLite.mutableCopy(var1);
      }
   }

   public static Field getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   public static Field.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Field.Builder newBuilder(Field var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Field parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Field parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Field parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Field parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Field parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Field parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Field parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Field parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Field parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Field parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Field parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Field parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Field> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void removeOptions(int var1) {
      this.ensureOptionsIsMutable();
      this.options_.remove(var1);
   }

   private void setCardinality(Field.Cardinality var1) {
      this.cardinality_ = var1.getNumber();
   }

   private void setCardinalityValue(int var1) {
      this.cardinality_ = var1;
   }

   private void setDefaultValue(String var1) {
      var1.getClass();
      this.defaultValue_ = var1;
   }

   private void setDefaultValueBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.defaultValue_ = var1.toStringUtf8();
   }

   private void setJsonName(String var1) {
      var1.getClass();
      this.jsonName_ = var1;
   }

   private void setJsonNameBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.jsonName_ = var1.toStringUtf8();
   }

   private void setKind(Field.Kind var1) {
      this.kind_ = var1.getNumber();
   }

   private void setKindValue(int var1) {
      this.kind_ = var1;
   }

   private void setName(String var1) {
      var1.getClass();
      this.name_ = var1;
   }

   private void setNameBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.name_ = var1.toStringUtf8();
   }

   private void setNumber(int var1) {
      this.number_ = var1;
   }

   private void setOneofIndex(int var1) {
      this.oneofIndex_ = var1;
   }

   private void setOptions(int var1, Option var2) {
      var2.getClass();
      this.ensureOptionsIsMutable();
      this.options_.set(var1, var2);
   }

   private void setPacked(boolean var1) {
      this.packed_ = var1;
   }

   private void setTypeUrl(String var1) {
      var1.getClass();
      this.typeUrl_ = var1;
   }

   private void setTypeUrlBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.typeUrl_ = var1.toStringUtf8();
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
      // 00: getstatic com/google/protobuf/Field$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
      // 43: getstatic com/google/protobuf/Field.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Field
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Field.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Field.DEFAULT_INSTANCE Lcom/google/protobuf/Field;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Field.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Field
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Field
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Field.DEFAULT_INSTANCE Lcom/google/protobuf/Field;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Field.DEFAULT_INSTANCE Lcom/google/protobuf/Field;
      // 7e: ldc_w "\u0000\n\u0000\u0000\u0001\u000b\n\u0000\u0001\u0000\u0001\f\u0002\f\u0003\u0004\u0004Ȉ\u0006Ȉ\u0007\u0004\b\u0007\t\u001b\nȈ\u000bȈ"
      // 81: bipush 11
      // 83: anewarray 250
      // 86: dup
      // 87: bipush 0
      // 88: ldc_w "kind_"
      // 8b: aastore
      // 8c: dup
      // 8d: bipush 1
      // 8e: ldc_w "cardinality_"
      // 91: aastore
      // 92: dup
      // 93: bipush 2
      // 94: ldc_w "number_"
      // 97: aastore
      // 98: dup
      // 99: bipush 3
      // 9a: ldc_w "name_"
      // 9d: aastore
      // 9e: dup
      // 9f: bipush 4
      // a0: ldc_w "typeUrl_"
      // a3: aastore
      // a4: dup
      // a5: bipush 5
      // a6: ldc_w "oneofIndex_"
      // a9: aastore
      // aa: dup
      // ab: bipush 6
      // ad: ldc_w "packed_"
      // b0: aastore
      // b1: dup
      // b2: bipush 7
      // b4: ldc_w "options_"
      // b7: aastore
      // b8: dup
      // b9: bipush 8
      // bb: ldc_w com/google/protobuf/Option
      // be: aastore
      // bf: dup
      // c0: bipush 9
      // c2: ldc_w "jsonName_"
      // c5: aastore
      // c6: dup
      // c7: bipush 10
      // c9: ldc_w "defaultValue_"
      // cc: aastore
      // cd: invokestatic com/google/protobuf/Field.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // d0: areturn
      // d1: new com/google/protobuf/Field$Builder
      // d4: dup
      // d5: aconst_null
      // d6: invokespecial com/google/protobuf/Field$Builder.<init> (Lcom/google/protobuf/Field$1;)V
      // d9: areturn
      // da: new com/google/protobuf/Field
      // dd: dup
      // de: invokespecial com/google/protobuf/Field.<init> ()V
      // e1: areturn
   }

   @Override
   public Field.Cardinality getCardinality() {
      Field.Cardinality var2 = Field.Cardinality.forNumber(this.cardinality_);
      Field.Cardinality var1 = var2;
      if (var2 == null) {
         var1 = Field.Cardinality.UNRECOGNIZED;
      }

      return var1;
   }

   @Override
   public int getCardinalityValue() {
      return this.cardinality_;
   }

   @Override
   public String getDefaultValue() {
      return this.defaultValue_;
   }

   @Override
   public ByteString getDefaultValueBytes() {
      return ByteString.copyFromUtf8(this.defaultValue_);
   }

   @Override
   public String getJsonName() {
      return this.jsonName_;
   }

   @Override
   public ByteString getJsonNameBytes() {
      return ByteString.copyFromUtf8(this.jsonName_);
   }

   @Override
   public Field.Kind getKind() {
      Field.Kind var2 = Field.Kind.forNumber(this.kind_);
      Field.Kind var1 = var2;
      if (var2 == null) {
         var1 = Field.Kind.UNRECOGNIZED;
      }

      return var1;
   }

   @Override
   public int getKindValue() {
      return this.kind_;
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
   public int getNumber() {
      return this.number_;
   }

   @Override
   public int getOneofIndex() {
      return this.oneofIndex_;
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
   public boolean getPacked() {
      return this.packed_;
   }

   @Override
   public String getTypeUrl() {
      return this.typeUrl_;
   }

   @Override
   public ByteString getTypeUrlBytes() {
      return ByteString.copyFromUtf8(this.typeUrl_);
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Field, Field.Builder> implements FieldOrBuilder {
      private Builder() {
         super(Field.DEFAULT_INSTANCE);
      }

      public Field.Builder addAllOptions(Iterable<? extends Option> var1) {
         this.copyOnWrite();
         this.instance.addAllOptions(var1);
         return this;
      }

      public Field.Builder addOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2.build());
         return this;
      }

      public Field.Builder addOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.addOptions(var1, var2);
         return this;
      }

      public Field.Builder addOptions(Option.Builder var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1.build());
         return this;
      }

      public Field.Builder addOptions(Option var1) {
         this.copyOnWrite();
         this.instance.addOptions(var1);
         return this;
      }

      public Field.Builder clearCardinality() {
         this.copyOnWrite();
         this.instance.clearCardinality();
         return this;
      }

      public Field.Builder clearDefaultValue() {
         this.copyOnWrite();
         this.instance.clearDefaultValue();
         return this;
      }

      public Field.Builder clearJsonName() {
         this.copyOnWrite();
         this.instance.clearJsonName();
         return this;
      }

      public Field.Builder clearKind() {
         this.copyOnWrite();
         this.instance.clearKind();
         return this;
      }

      public Field.Builder clearName() {
         this.copyOnWrite();
         this.instance.clearName();
         return this;
      }

      public Field.Builder clearNumber() {
         this.copyOnWrite();
         this.instance.clearNumber();
         return this;
      }

      public Field.Builder clearOneofIndex() {
         this.copyOnWrite();
         this.instance.clearOneofIndex();
         return this;
      }

      public Field.Builder clearOptions() {
         this.copyOnWrite();
         this.instance.clearOptions();
         return this;
      }

      public Field.Builder clearPacked() {
         this.copyOnWrite();
         this.instance.clearPacked();
         return this;
      }

      public Field.Builder clearTypeUrl() {
         this.copyOnWrite();
         this.instance.clearTypeUrl();
         return this;
      }

      @Override
      public Field.Cardinality getCardinality() {
         return this.instance.getCardinality();
      }

      @Override
      public int getCardinalityValue() {
         return this.instance.getCardinalityValue();
      }

      @Override
      public String getDefaultValue() {
         return this.instance.getDefaultValue();
      }

      @Override
      public ByteString getDefaultValueBytes() {
         return this.instance.getDefaultValueBytes();
      }

      @Override
      public String getJsonName() {
         return this.instance.getJsonName();
      }

      @Override
      public ByteString getJsonNameBytes() {
         return this.instance.getJsonNameBytes();
      }

      @Override
      public Field.Kind getKind() {
         return this.instance.getKind();
      }

      @Override
      public int getKindValue() {
         return this.instance.getKindValue();
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
      public int getNumber() {
         return this.instance.getNumber();
      }

      @Override
      public int getOneofIndex() {
         return this.instance.getOneofIndex();
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
      public boolean getPacked() {
         return this.instance.getPacked();
      }

      @Override
      public String getTypeUrl() {
         return this.instance.getTypeUrl();
      }

      @Override
      public ByteString getTypeUrlBytes() {
         return this.instance.getTypeUrlBytes();
      }

      public Field.Builder removeOptions(int var1) {
         this.copyOnWrite();
         this.instance.removeOptions(var1);
         return this;
      }

      public Field.Builder setCardinality(Field.Cardinality var1) {
         this.copyOnWrite();
         this.instance.setCardinality(var1);
         return this;
      }

      public Field.Builder setCardinalityValue(int var1) {
         this.copyOnWrite();
         this.instance.setCardinalityValue(var1);
         return this;
      }

      public Field.Builder setDefaultValue(String var1) {
         this.copyOnWrite();
         this.instance.setDefaultValue(var1);
         return this;
      }

      public Field.Builder setDefaultValueBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setDefaultValueBytes(var1);
         return this;
      }

      public Field.Builder setJsonName(String var1) {
         this.copyOnWrite();
         this.instance.setJsonName(var1);
         return this;
      }

      public Field.Builder setJsonNameBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setJsonNameBytes(var1);
         return this;
      }

      public Field.Builder setKind(Field.Kind var1) {
         this.copyOnWrite();
         this.instance.setKind(var1);
         return this;
      }

      public Field.Builder setKindValue(int var1) {
         this.copyOnWrite();
         this.instance.setKindValue(var1);
         return this;
      }

      public Field.Builder setName(String var1) {
         this.copyOnWrite();
         this.instance.setName(var1);
         return this;
      }

      public Field.Builder setNameBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setNameBytes(var1);
         return this;
      }

      public Field.Builder setNumber(int var1) {
         this.copyOnWrite();
         this.instance.setNumber(var1);
         return this;
      }

      public Field.Builder setOneofIndex(int var1) {
         this.copyOnWrite();
         this.instance.setOneofIndex(var1);
         return this;
      }

      public Field.Builder setOptions(int var1, Option.Builder var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2.build());
         return this;
      }

      public Field.Builder setOptions(int var1, Option var2) {
         this.copyOnWrite();
         this.instance.setOptions(var1, var2);
         return this;
      }

      public Field.Builder setPacked(boolean var1) {
         this.copyOnWrite();
         this.instance.setPacked(var1);
         return this;
      }

      public Field.Builder setTypeUrl(String var1) {
         this.copyOnWrite();
         this.instance.setTypeUrl(var1);
         return this;
      }

      public Field.Builder setTypeUrlBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setTypeUrlBytes(var1);
         return this;
      }
   }

   public static enum Cardinality implements Internal.EnumLite {
      CARDINALITY_OPTIONAL,
      CARDINALITY_REPEATED,
      CARDINALITY_REQUIRED,
      CARDINALITY_UNKNOWN,
      UNRECOGNIZED;

      private static final Field.Cardinality[] $VALUES;
      public static final int CARDINALITY_OPTIONAL_VALUE = 1;
      public static final int CARDINALITY_REPEATED_VALUE = 3;
      public static final int CARDINALITY_REQUIRED_VALUE = 2;
      public static final int CARDINALITY_UNKNOWN_VALUE = 0;
      private static final Internal.EnumLiteMap<Field.Cardinality> internalValueMap = new Internal.EnumLiteMap<Field.Cardinality>() {
         public Field.Cardinality findValueByNumber(int var1) {
            return Field.Cardinality.forNumber(var1);
         }
      };
      private final int value;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         Field.Cardinality var4 = new Field.Cardinality(0);
         CARDINALITY_UNKNOWN = var4;
         Field.Cardinality var1 = new Field.Cardinality(1);
         CARDINALITY_OPTIONAL = var1;
         Field.Cardinality var2 = new Field.Cardinality(2);
         CARDINALITY_REQUIRED = var2;
         Field.Cardinality var0 = new Field.Cardinality(3);
         CARDINALITY_REPEATED = var0;
         Field.Cardinality var3 = new Field.Cardinality(-1);
         UNRECOGNIZED = var3;
         $VALUES = new Field.Cardinality[]{var4, var1, var2, var0, var3};
      }

      private Cardinality(int var3) {
         this.value = var3;
      }

      public static Field.Cardinality forNumber(int var0) {
         if (var0 != 0) {
            if (var0 != 1) {
               if (var0 != 2) {
                  return var0 != 3 ? null : CARDINALITY_REPEATED;
               } else {
                  return CARDINALITY_REQUIRED;
               }
            } else {
               return CARDINALITY_OPTIONAL;
            }
         } else {
            return CARDINALITY_UNKNOWN;
         }
      }

      public static Internal.EnumLiteMap<Field.Cardinality> internalGetValueMap() {
         return internalValueMap;
      }

      public static Internal.EnumVerifier internalGetVerifier() {
         return Field.Cardinality.CardinalityVerifier.INSTANCE;
      }

      @Deprecated
      public static Field.Cardinality valueOf(int var0) {
         return forNumber(var0);
      }

      @Override
      public final int getNumber() {
         if (this != UNRECOGNIZED) {
            return this.value;
         } else {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
         }
      }

      private static final class CardinalityVerifier implements Internal.EnumVerifier {
         static final Internal.EnumVerifier INSTANCE = new Field.Cardinality.CardinalityVerifier();

         @Override
         public boolean isInRange(int var1) {
            boolean var2;
            if (Field.Cardinality.forNumber(var1) != null) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      }
   }

   public static enum Kind implements Internal.EnumLite {
      TYPE_BOOL,
      TYPE_BYTES,
      TYPE_DOUBLE,
      TYPE_ENUM,
      TYPE_FIXED32,
      TYPE_FIXED64,
      TYPE_FLOAT,
      TYPE_GROUP,
      TYPE_INT32,
      TYPE_INT64,
      TYPE_MESSAGE,
      TYPE_SFIXED32,
      TYPE_SFIXED64,
      TYPE_SINT32,
      TYPE_SINT64,
      TYPE_STRING,
      TYPE_UINT32,
      TYPE_UINT64,
      TYPE_UNKNOWN,
      UNRECOGNIZED;

      private static final Field.Kind[] $VALUES;
      public static final int TYPE_BOOL_VALUE = 8;
      public static final int TYPE_BYTES_VALUE = 12;
      public static final int TYPE_DOUBLE_VALUE = 1;
      public static final int TYPE_ENUM_VALUE = 14;
      public static final int TYPE_FIXED32_VALUE = 7;
      public static final int TYPE_FIXED64_VALUE = 6;
      public static final int TYPE_FLOAT_VALUE = 2;
      public static final int TYPE_GROUP_VALUE = 10;
      public static final int TYPE_INT32_VALUE = 5;
      public static final int TYPE_INT64_VALUE = 3;
      public static final int TYPE_MESSAGE_VALUE = 11;
      public static final int TYPE_SFIXED32_VALUE = 15;
      public static final int TYPE_SFIXED64_VALUE = 16;
      public static final int TYPE_SINT32_VALUE = 17;
      public static final int TYPE_SINT64_VALUE = 18;
      public static final int TYPE_STRING_VALUE = 9;
      public static final int TYPE_UINT32_VALUE = 13;
      public static final int TYPE_UINT64_VALUE = 4;
      public static final int TYPE_UNKNOWN_VALUE = 0;
      private static final Internal.EnumLiteMap<Field.Kind> internalValueMap = new Internal.EnumLiteMap<Field.Kind>() {
         public Field.Kind findValueByNumber(int var1) {
            return Field.Kind.forNumber(var1);
         }
      };
      private final int value;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         Field.Kind var13 = new Field.Kind(0);
         TYPE_UNKNOWN = var13;
         Field.Kind var5 = new Field.Kind(1);
         TYPE_DOUBLE = var5;
         Field.Kind var19 = new Field.Kind(2);
         TYPE_FLOAT = var19;
         Field.Kind var10 = new Field.Kind(3);
         TYPE_INT64 = var10;
         Field.Kind var11 = new Field.Kind(4);
         TYPE_UINT64 = var11;
         Field.Kind var4 = new Field.Kind(5);
         TYPE_INT32 = var4;
         Field.Kind var6 = new Field.Kind(6);
         TYPE_FIXED64 = var6;
         Field.Kind var14 = new Field.Kind(7);
         TYPE_FIXED32 = var14;
         Field.Kind var1 = new Field.Kind(8);
         TYPE_BOOL = var1;
         Field.Kind var16 = new Field.Kind(9);
         TYPE_STRING = var16;
         Field.Kind var8 = new Field.Kind(10);
         TYPE_GROUP = var8;
         Field.Kind var15 = new Field.Kind(11);
         TYPE_MESSAGE = var15;
         Field.Kind var17 = new Field.Kind(12);
         TYPE_BYTES = var17;
         Field.Kind var3 = new Field.Kind(13);
         TYPE_UINT32 = var3;
         Field.Kind var12 = new Field.Kind(14);
         TYPE_ENUM = var12;
         Field.Kind var18 = new Field.Kind(15);
         TYPE_SFIXED32 = var18;
         Field.Kind var9 = new Field.Kind(16);
         TYPE_SFIXED64 = var9;
         Field.Kind var7 = new Field.Kind(17);
         TYPE_SINT32 = var7;
         Field.Kind var0 = new Field.Kind(18);
         TYPE_SINT64 = var0;
         Field.Kind var2 = new Field.Kind(-1);
         UNRECOGNIZED = var2;
         $VALUES = new Field.Kind[]{
            var13, var5, var19, var10, var11, var4, var6, var14, var1, var16, var8, var15, var17, var3, var12, var18, var9, var7, var0, var2
         };
      }

      private Kind(int var3) {
         this.value = var3;
      }

      public static Field.Kind forNumber(int var0) {
         switch (var0) {
            case 0:
               return TYPE_UNKNOWN;
            case 1:
               return TYPE_DOUBLE;
            case 2:
               return TYPE_FLOAT;
            case 3:
               return TYPE_INT64;
            case 4:
               return TYPE_UINT64;
            case 5:
               return TYPE_INT32;
            case 6:
               return TYPE_FIXED64;
            case 7:
               return TYPE_FIXED32;
            case 8:
               return TYPE_BOOL;
            case 9:
               return TYPE_STRING;
            case 10:
               return TYPE_GROUP;
            case 11:
               return TYPE_MESSAGE;
            case 12:
               return TYPE_BYTES;
            case 13:
               return TYPE_UINT32;
            case 14:
               return TYPE_ENUM;
            case 15:
               return TYPE_SFIXED32;
            case 16:
               return TYPE_SFIXED64;
            case 17:
               return TYPE_SINT32;
            case 18:
               return TYPE_SINT64;
            default:
               return null;
         }
      }

      public static Internal.EnumLiteMap<Field.Kind> internalGetValueMap() {
         return internalValueMap;
      }

      public static Internal.EnumVerifier internalGetVerifier() {
         return Field.Kind.KindVerifier.INSTANCE;
      }

      @Deprecated
      public static Field.Kind valueOf(int var0) {
         return forNumber(var0);
      }

      @Override
      public final int getNumber() {
         if (this != UNRECOGNIZED) {
            return this.value;
         } else {
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
         }
      }

      private static final class KindVerifier implements Internal.EnumVerifier {
         static final Internal.EnumVerifier INSTANCE = new Field.Kind.KindVerifier();

         @Override
         public boolean isInRange(int var1) {
            boolean var2;
            if (Field.Kind.forNumber(var1) != null) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      }
   }
}
