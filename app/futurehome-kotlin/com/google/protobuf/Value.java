package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Value extends GeneratedMessageLite<Value, Value.Builder> implements ValueOrBuilder {
   public static final int BOOL_VALUE_FIELD_NUMBER = 4;
   private static final Value DEFAULT_INSTANCE;
   public static final int LIST_VALUE_FIELD_NUMBER = 6;
   public static final int NULL_VALUE_FIELD_NUMBER = 1;
   public static final int NUMBER_VALUE_FIELD_NUMBER = 2;
   private static volatile Parser<Value> PARSER;
   public static final int STRING_VALUE_FIELD_NUMBER = 3;
   public static final int STRUCT_VALUE_FIELD_NUMBER = 5;
   private int kindCase_ = 0;
   private Object kind_;

   static {
      Value var0 = new Value();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Value.class, var0);
   }

   private Value() {
   }

   private void clearBoolValue() {
      if (this.kindCase_ == 4) {
         this.kindCase_ = 0;
         this.kind_ = null;
      }
   }

   private void clearKind() {
      this.kindCase_ = 0;
      this.kind_ = null;
   }

   private void clearListValue() {
      if (this.kindCase_ == 6) {
         this.kindCase_ = 0;
         this.kind_ = null;
      }
   }

   private void clearNullValue() {
      if (this.kindCase_ == 1) {
         this.kindCase_ = 0;
         this.kind_ = null;
      }
   }

   private void clearNumberValue() {
      if (this.kindCase_ == 2) {
         this.kindCase_ = 0;
         this.kind_ = null;
      }
   }

   private void clearStringValue() {
      if (this.kindCase_ == 3) {
         this.kindCase_ = 0;
         this.kind_ = null;
      }
   }

   private void clearStructValue() {
      if (this.kindCase_ == 5) {
         this.kindCase_ = 0;
         this.kind_ = null;
      }
   }

   public static Value getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   private void mergeListValue(ListValue var1) {
      var1.getClass();
      if (this.kindCase_ == 6 && this.kind_ != ListValue.getDefaultInstance()) {
         this.kind_ = ListValue.newBuilder((ListValue)this.kind_).mergeFrom(var1).buildPartial();
      } else {
         this.kind_ = var1;
      }

      this.kindCase_ = 6;
   }

   private void mergeStructValue(Struct var1) {
      var1.getClass();
      if (this.kindCase_ == 5 && this.kind_ != Struct.getDefaultInstance()) {
         this.kind_ = Struct.newBuilder((Struct)this.kind_).mergeFrom(var1).buildPartial();
      } else {
         this.kind_ = var1;
      }

      this.kindCase_ = 5;
   }

   public static Value.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Value.Builder newBuilder(Value var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Value parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Value parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Value parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Value parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Value parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Value parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Value parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Value parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Value parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Value parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Value parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Value parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Value> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   private void setBoolValue(boolean var1) {
      this.kindCase_ = 4;
      this.kind_ = var1;
   }

   private void setListValue(ListValue var1) {
      var1.getClass();
      this.kind_ = var1;
      this.kindCase_ = 6;
   }

   private void setNullValue(NullValue var1) {
      this.kind_ = var1.getNumber();
      this.kindCase_ = 1;
   }

   private void setNullValueValue(int var1) {
      this.kindCase_ = 1;
      this.kind_ = var1;
   }

   private void setNumberValue(double var1) {
      this.kindCase_ = 2;
      this.kind_ = var1;
   }

   private void setStringValue(String var1) {
      var1.getClass();
      this.kindCase_ = 3;
      this.kind_ = var1;
   }

   private void setStringValueBytes(ByteString var1) {
      checkByteStringIsUtf8(var1);
      this.kind_ = var1.toStringUtf8();
      this.kindCase_ = 3;
   }

   private void setStructValue(Struct var1) {
      var1.getClass();
      this.kind_ = var1;
      this.kindCase_ = 5;
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
      // 00: getstatic com/google/protobuf/Value$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 160 151 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/Value.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Value
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Value.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Value.DEFAULT_INSTANCE Lcom/google/protobuf/Value;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Value.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Value
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Value
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Value.DEFAULT_INSTANCE Lcom/google/protobuf/Value;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Value.DEFAULT_INSTANCE Lcom/google/protobuf/Value;
      // 7e: ldc_w "\u0000\u0006\u0001\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001?\u0000\u00023\u0000\u0003Ȼ\u0000\u0004:\u0000\u0005<\u0000\u0006<\u0000"
      // 81: bipush 4
      // 82: anewarray 143
      // 85: dup
      // 86: bipush 0
      // 87: ldc_w "kind_"
      // 8a: aastore
      // 8b: dup
      // 8c: bipush 1
      // 8d: ldc_w "kindCase_"
      // 90: aastore
      // 91: dup
      // 92: bipush 2
      // 93: ldc com/google/protobuf/Struct
      // 95: aastore
      // 96: dup
      // 97: bipush 3
      // 98: ldc com/google/protobuf/ListValue
      // 9a: aastore
      // 9b: invokestatic com/google/protobuf/Value.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 9e: areturn
      // 9f: new com/google/protobuf/Value$Builder
      // a2: dup
      // a3: aconst_null
      // a4: invokespecial com/google/protobuf/Value$Builder.<init> (Lcom/google/protobuf/Value$1;)V
      // a7: areturn
      // a8: new com/google/protobuf/Value
      // ab: dup
      // ac: invokespecial com/google/protobuf/Value.<init> ()V
      // af: areturn
   }

   @Override
   public boolean getBoolValue() {
      return this.kindCase_ == 4 ? (Boolean)this.kind_ : false;
   }

   @Override
   public Value.KindCase getKindCase() {
      return Value.KindCase.forNumber(this.kindCase_);
   }

   @Override
   public ListValue getListValue() {
      return this.kindCase_ == 6 ? (ListValue)this.kind_ : ListValue.getDefaultInstance();
   }

   @Override
   public NullValue getNullValue() {
      if (this.kindCase_ == 1) {
         NullValue var2 = NullValue.forNumber((Integer)this.kind_);
         NullValue var1 = var2;
         if (var2 == null) {
            var1 = NullValue.UNRECOGNIZED;
         }

         return var1;
      } else {
         return NullValue.NULL_VALUE;
      }
   }

   @Override
   public int getNullValueValue() {
      return this.kindCase_ == 1 ? (Integer)this.kind_ : 0;
   }

   @Override
   public double getNumberValue() {
      return this.kindCase_ == 2 ? (Double)this.kind_ : 0.0;
   }

   @Override
   public String getStringValue() {
      String var1;
      if (this.kindCase_ == 3) {
         var1 = (String)this.kind_;
      } else {
         var1 = "";
      }

      return var1;
   }

   @Override
   public ByteString getStringValueBytes() {
      String var1;
      if (this.kindCase_ == 3) {
         var1 = (String)this.kind_;
      } else {
         var1 = "";
      }

      return ByteString.copyFromUtf8(var1);
   }

   @Override
   public Struct getStructValue() {
      return this.kindCase_ == 5 ? (Struct)this.kind_ : Struct.getDefaultInstance();
   }

   @Override
   public boolean hasBoolValue() {
      boolean var1;
      if (this.kindCase_ == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasListValue() {
      boolean var1;
      if (this.kindCase_ == 6) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasNullValue() {
      int var1 = this.kindCase_;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   @Override
   public boolean hasNumberValue() {
      boolean var1;
      if (this.kindCase_ == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasStringValue() {
      boolean var1;
      if (this.kindCase_ == 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasStructValue() {
      boolean var1;
      if (this.kindCase_ == 5) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Value, Value.Builder> implements ValueOrBuilder {
      private Builder() {
         super(Value.DEFAULT_INSTANCE);
      }

      public Value.Builder clearBoolValue() {
         this.copyOnWrite();
         this.instance.clearBoolValue();
         return this;
      }

      public Value.Builder clearKind() {
         this.copyOnWrite();
         this.instance.clearKind();
         return this;
      }

      public Value.Builder clearListValue() {
         this.copyOnWrite();
         this.instance.clearListValue();
         return this;
      }

      public Value.Builder clearNullValue() {
         this.copyOnWrite();
         this.instance.clearNullValue();
         return this;
      }

      public Value.Builder clearNumberValue() {
         this.copyOnWrite();
         this.instance.clearNumberValue();
         return this;
      }

      public Value.Builder clearStringValue() {
         this.copyOnWrite();
         this.instance.clearStringValue();
         return this;
      }

      public Value.Builder clearStructValue() {
         this.copyOnWrite();
         this.instance.clearStructValue();
         return this;
      }

      @Override
      public boolean getBoolValue() {
         return this.instance.getBoolValue();
      }

      @Override
      public Value.KindCase getKindCase() {
         return this.instance.getKindCase();
      }

      @Override
      public ListValue getListValue() {
         return this.instance.getListValue();
      }

      @Override
      public NullValue getNullValue() {
         return this.instance.getNullValue();
      }

      @Override
      public int getNullValueValue() {
         return this.instance.getNullValueValue();
      }

      @Override
      public double getNumberValue() {
         return this.instance.getNumberValue();
      }

      @Override
      public String getStringValue() {
         return this.instance.getStringValue();
      }

      @Override
      public ByteString getStringValueBytes() {
         return this.instance.getStringValueBytes();
      }

      @Override
      public Struct getStructValue() {
         return this.instance.getStructValue();
      }

      @Override
      public boolean hasBoolValue() {
         return this.instance.hasBoolValue();
      }

      @Override
      public boolean hasListValue() {
         return this.instance.hasListValue();
      }

      @Override
      public boolean hasNullValue() {
         return this.instance.hasNullValue();
      }

      @Override
      public boolean hasNumberValue() {
         return this.instance.hasNumberValue();
      }

      @Override
      public boolean hasStringValue() {
         return this.instance.hasStringValue();
      }

      @Override
      public boolean hasStructValue() {
         return this.instance.hasStructValue();
      }

      public Value.Builder mergeListValue(ListValue var1) {
         this.copyOnWrite();
         this.instance.mergeListValue(var1);
         return this;
      }

      public Value.Builder mergeStructValue(Struct var1) {
         this.copyOnWrite();
         this.instance.mergeStructValue(var1);
         return this;
      }

      public Value.Builder setBoolValue(boolean var1) {
         this.copyOnWrite();
         this.instance.setBoolValue(var1);
         return this;
      }

      public Value.Builder setListValue(ListValue.Builder var1) {
         this.copyOnWrite();
         this.instance.setListValue(var1.build());
         return this;
      }

      public Value.Builder setListValue(ListValue var1) {
         this.copyOnWrite();
         this.instance.setListValue(var1);
         return this;
      }

      public Value.Builder setNullValue(NullValue var1) {
         this.copyOnWrite();
         this.instance.setNullValue(var1);
         return this;
      }

      public Value.Builder setNullValueValue(int var1) {
         this.copyOnWrite();
         this.instance.setNullValueValue(var1);
         return this;
      }

      public Value.Builder setNumberValue(double var1) {
         this.copyOnWrite();
         this.instance.setNumberValue(var1);
         return this;
      }

      public Value.Builder setStringValue(String var1) {
         this.copyOnWrite();
         this.instance.setStringValue(var1);
         return this;
      }

      public Value.Builder setStringValueBytes(ByteString var1) {
         this.copyOnWrite();
         this.instance.setStringValueBytes(var1);
         return this;
      }

      public Value.Builder setStructValue(Struct.Builder var1) {
         this.copyOnWrite();
         this.instance.setStructValue(var1.build());
         return this;
      }

      public Value.Builder setStructValue(Struct var1) {
         this.copyOnWrite();
         this.instance.setStructValue(var1);
         return this;
      }
   }

   public static enum KindCase {
      BOOL_VALUE,
      KIND_NOT_SET,
      LIST_VALUE,
      NULL_VALUE,
      NUMBER_VALUE,
      STRING_VALUE,
      STRUCT_VALUE;

      private static final Value.KindCase[] $VALUES;
      private final int value;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         Value.KindCase var5 = new Value.KindCase(1);
         NULL_VALUE = var5;
         Value.KindCase var4 = new Value.KindCase(2);
         NUMBER_VALUE = var4;
         Value.KindCase var1 = new Value.KindCase(3);
         STRING_VALUE = var1;
         Value.KindCase var0 = new Value.KindCase(4);
         BOOL_VALUE = var0;
         Value.KindCase var2 = new Value.KindCase(5);
         STRUCT_VALUE = var2;
         Value.KindCase var6 = new Value.KindCase(6);
         LIST_VALUE = var6;
         Value.KindCase var3 = new Value.KindCase(0);
         KIND_NOT_SET = var3;
         $VALUES = new Value.KindCase[]{var5, var4, var1, var0, var2, var6, var3};
      }

      private KindCase(int var3) {
         this.value = var3;
      }

      public static Value.KindCase forNumber(int var0) {
         switch (var0) {
            case 0:
               return KIND_NOT_SET;
            case 1:
               return NULL_VALUE;
            case 2:
               return NUMBER_VALUE;
            case 3:
               return STRING_VALUE;
            case 4:
               return BOOL_VALUE;
            case 5:
               return STRUCT_VALUE;
            case 6:
               return LIST_VALUE;
            default:
               return null;
         }
      }

      @Deprecated
      public static Value.KindCase valueOf(int var0) {
         return forNumber(var0);
      }

      public int getNumber() {
         return this.value;
      }
   }
}
