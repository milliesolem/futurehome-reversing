package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

public final class Struct extends GeneratedMessageLite<Struct, Struct.Builder> implements StructOrBuilder {
   private static final Struct DEFAULT_INSTANCE;
   public static final int FIELDS_FIELD_NUMBER = 1;
   private static volatile Parser<Struct> PARSER;
   private MapFieldLite<String, Value> fields_ = MapFieldLite.emptyMapField();

   static {
      Struct var0 = new Struct();
      DEFAULT_INSTANCE = var0;
      GeneratedMessageLite.registerDefaultInstance(Struct.class, var0);
   }

   private Struct() {
   }

   public static Struct getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   private Map<String, Value> getMutableFieldsMap() {
      return this.internalGetMutableFields();
   }

   private MapFieldLite<String, Value> internalGetFields() {
      return this.fields_;
   }

   private MapFieldLite<String, Value> internalGetMutableFields() {
      if (!this.fields_.isMutable()) {
         this.fields_ = this.fields_.mutableCopy();
      }

      return this.fields_;
   }

   public static Struct.Builder newBuilder() {
      return DEFAULT_INSTANCE.createBuilder();
   }

   public static Struct.Builder newBuilder(Struct var0) {
      return DEFAULT_INSTANCE.createBuilder(var0);
   }

   public static Struct parseDelimitedFrom(InputStream var0) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
   }

   public static Struct parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Struct parseFrom(ByteString var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Struct parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Struct parseFrom(CodedInputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Struct parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Struct parseFrom(InputStream var0) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Struct parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Struct parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Struct parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Struct parseFrom(byte[] var0) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
   }

   public static Struct parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
      return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
   }

   public static Parser<Struct> parser() {
      return DEFAULT_INSTANCE.getParserForType();
   }

   @Override
   public boolean containsFields(String var1) {
      var1.getClass();
      return this.internalGetFields().containsKey(var1);
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
      // 00: getstatic com/google/protobuf/Struct$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
      // 03: aload 1
      // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
      // 07: iaload
      // 08: tableswitch 44 1 7 150 141 115 111 59 54 52
      // 34: new java/lang/UnsupportedOperationException
      // 37: dup
      // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
      // 3b: athrow
      // 3c: aconst_null
      // 3d: areturn
      // 3e: bipush 1
      // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
      // 42: areturn
      // 43: getstatic com/google/protobuf/Struct.PARSER Lcom/google/protobuf/Parser;
      // 46: astore 2
      // 47: aload 2
      // 48: astore 1
      // 49: aload 2
      // 4a: ifnonnull 75
      // 4d: ldc com/google/protobuf/Struct
      // 4f: monitorenter
      // 50: getstatic com/google/protobuf/Struct.PARSER Lcom/google/protobuf/Parser;
      // 53: astore 2
      // 54: aload 2
      // 55: astore 1
      // 56: aload 2
      // 57: ifnonnull 69
      // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
      // 5d: astore 1
      // 5e: aload 1
      // 5f: getstatic com/google/protobuf/Struct.DEFAULT_INSTANCE Lcom/google/protobuf/Struct;
      // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
      // 65: aload 1
      // 66: putstatic com/google/protobuf/Struct.PARSER Lcom/google/protobuf/Parser;
      // 69: ldc com/google/protobuf/Struct
      // 6b: monitorexit
      // 6c: goto 75
      // 6f: astore 1
      // 70: ldc com/google/protobuf/Struct
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
      // 75: aload 1
      // 76: areturn
      // 77: getstatic com/google/protobuf/Struct.DEFAULT_INSTANCE Lcom/google/protobuf/Struct;
      // 7a: areturn
      // 7b: getstatic com/google/protobuf/Struct$FieldsDefaultEntryHolder.defaultEntry Lcom/google/protobuf/MapEntryLite;
      // 7e: astore 1
      // 7f: getstatic com/google/protobuf/Struct.DEFAULT_INSTANCE Lcom/google/protobuf/Struct;
      // 82: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012"
      // 84: bipush 2
      // 85: anewarray 141
      // 88: dup
      // 89: bipush 0
      // 8a: ldc "fields_"
      // 8c: aastore
      // 8d: dup
      // 8e: bipush 1
      // 8f: aload 1
      // 90: aastore
      // 91: invokestatic com/google/protobuf/Struct.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
      // 94: areturn
      // 95: new com/google/protobuf/Struct$Builder
      // 98: dup
      // 99: aconst_null
      // 9a: invokespecial com/google/protobuf/Struct$Builder.<init> (Lcom/google/protobuf/Struct$1;)V
      // 9d: areturn
      // 9e: new com/google/protobuf/Struct
      // a1: dup
      // a2: invokespecial com/google/protobuf/Struct.<init> ()V
      // a5: areturn
   }

   @Deprecated
   @Override
   public Map<String, Value> getFields() {
      return this.getFieldsMap();
   }

   @Override
   public int getFieldsCount() {
      return this.internalGetFields().size();
   }

   @Override
   public Map<String, Value> getFieldsMap() {
      return Collections.unmodifiableMap(this.internalGetFields());
   }

   @Override
   public Value getFieldsOrDefault(String var1, Value var2) {
      var1.getClass();
      MapFieldLite var3 = this.internalGetFields();
      if (var3.containsKey(var1)) {
         var2 = (Value)var3.get(var1);
      }

      return var2;
   }

   @Override
   public Value getFieldsOrThrow(String var1) {
      var1.getClass();
      MapFieldLite var2 = this.internalGetFields();
      if (var2.containsKey(var1)) {
         return (Value)var2.get(var1);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static final class Builder extends GeneratedMessageLite.Builder<Struct, Struct.Builder> implements StructOrBuilder {
      private Builder() {
         super(Struct.DEFAULT_INSTANCE);
      }

      public Struct.Builder clearFields() {
         this.copyOnWrite();
         this.instance.getMutableFieldsMap().clear();
         return this;
      }

      @Override
      public boolean containsFields(String var1) {
         var1.getClass();
         return this.instance.getFieldsMap().containsKey(var1);
      }

      @Deprecated
      @Override
      public Map<String, Value> getFields() {
         return this.getFieldsMap();
      }

      @Override
      public int getFieldsCount() {
         return this.instance.getFieldsMap().size();
      }

      @Override
      public Map<String, Value> getFieldsMap() {
         return Collections.unmodifiableMap(this.instance.getFieldsMap());
      }

      @Override
      public Value getFieldsOrDefault(String var1, Value var2) {
         var1.getClass();
         Map var3 = this.instance.getFieldsMap();
         if (var3.containsKey(var1)) {
            var2 = (Value)var3.get(var1);
         }

         return var2;
      }

      @Override
      public Value getFieldsOrThrow(String var1) {
         var1.getClass();
         Map var2 = this.instance.getFieldsMap();
         if (var2.containsKey(var1)) {
            return (Value)var2.get(var1);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public Struct.Builder putAllFields(Map<String, Value> var1) {
         this.copyOnWrite();
         this.instance.getMutableFieldsMap().putAll(var1);
         return this;
      }

      public Struct.Builder putFields(String var1, Value var2) {
         var1.getClass();
         var2.getClass();
         this.copyOnWrite();
         this.instance.getMutableFieldsMap().put(var1, var2);
         return this;
      }

      public Struct.Builder removeFields(String var1) {
         var1.getClass();
         this.copyOnWrite();
         this.instance.getMutableFieldsMap().remove(var1);
         return this;
      }
   }

   private static final class FieldsDefaultEntryHolder {
      static final MapEntryLite<String, Value> defaultEntry = MapEntryLite.newDefaultInstance(
         WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance()
      );
   }
}
