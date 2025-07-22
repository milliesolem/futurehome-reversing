package com.google.protobuf;

import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.Builder<MessageType, BuilderType>>
   extends AbstractMessageLite<MessageType, BuilderType> {
   private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
   private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
   static final int UNINITIALIZED_HASH_CODE = 0;
   static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
   private static Map<Object, GeneratedMessageLite<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
   private int memoizedSerializedSize = -1;
   protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();

   private static <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>, T> GeneratedMessageLite.GeneratedExtension<MessageType, T> checkIsLite(
      ExtensionLite<MessageType, T> var0
   ) {
      if (var0.isLite()) {
         return (GeneratedMessageLite.GeneratedExtension<MessageType, T>)var0;
      } else {
         throw new IllegalArgumentException("Expected a lite extension.");
      }
   }

   private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(T var0) throws InvalidProtocolBufferException {
      if (var0 != null && !var0.isInitialized()) {
         throw var0.newUninitializedMessageException().asInvalidProtocolBufferException().setUnfinishedMessage(var0);
      } else {
         return (T)var0;
      }
   }

   private int computeSerializedSize(Schema<?> var1) {
      return var1 == null ? Protobuf.getInstance().schemaFor(this).getSerializedSize(this) : var1.getSerializedSize(this);
   }

   protected static Internal.BooleanList emptyBooleanList() {
      return BooleanArrayList.emptyList();
   }

   protected static Internal.DoubleList emptyDoubleList() {
      return DoubleArrayList.emptyList();
   }

   protected static Internal.FloatList emptyFloatList() {
      return FloatArrayList.emptyList();
   }

   protected static Internal.IntList emptyIntList() {
      return IntArrayList.emptyList();
   }

   protected static Internal.LongList emptyLongList() {
      return LongArrayList.emptyList();
   }

   protected static <E> Internal.ProtobufList<E> emptyProtobufList() {
      return ProtobufArrayList.emptyList();
   }

   private void ensureUnknownFieldsInitialized() {
      if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
         this.unknownFields = UnknownFieldSetLite.newInstance();
      }
   }

   static <T extends GeneratedMessageLite<?, ?>> T getDefaultInstance(Class<T> var0) {
      GeneratedMessageLite var2 = defaultInstanceMap.get(var0);
      GeneratedMessageLite var1 = var2;
      if (var2 == null) {
         try {
            Class.forName(var0.getName(), true, var0.getClassLoader());
         } catch (ClassNotFoundException var3) {
            throw new IllegalStateException("Class initialization cannot fail.", var3);
         }

         var1 = defaultInstanceMap.get(var0);
      }

      var2 = var1;
      if (var1 == null) {
         var2 = UnsafeUtil.<GeneratedMessageLite>allocateInstance(var0).getDefaultInstanceForType();
         if (var2 == null) {
            throw new IllegalStateException();
         }

         defaultInstanceMap.put(var0, var2);
      }

      return (T)var2;
   }

   static java.lang.reflect.Method getMethodOrDie(Class var0, String var1, Class... var2) {
      try {
         return var0.getMethod(var1, var2);
      } catch (NoSuchMethodException var4) {
         StringBuilder var5 = new StringBuilder("Generated message class \"");
         var5.append(var0.getName());
         var5.append("\" missing method \"");
         var5.append(var1);
         var5.append("\".");
         throw new RuntimeException(var5.toString(), var4);
      }
   }

   static Object invokeOrDie(java.lang.reflect.Method var0, Object var1, Object... var2) {
      try {
         return var0.invoke(var1, var2);
      } catch (IllegalAccessException var3) {
         throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", var3);
      } catch (InvocationTargetException var4) {
         Throwable var5 = var4.getCause();
         if (!(var5 instanceof RuntimeException)) {
            if (var5 instanceof Error) {
               throw (Error)var5;
            } else {
               throw new RuntimeException("Unexpected exception thrown by generated accessor method.", var5);
            }
         } else {
            throw (RuntimeException)var5;
         }
      }
   }

   protected static final <T extends GeneratedMessageLite<T, ?>> boolean isInitialized(T var0, boolean var1) {
      byte var2 = (Byte)var0.dynamicMethod(GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED);
      if (var2 == 1) {
         return true;
      } else if (var2 == 0) {
         return false;
      } else {
         boolean var3 = Protobuf.getInstance().schemaFor(var0).isInitialized(var0);
         if (var1) {
            GeneratedMessageLite.MethodToInvoke var5 = GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED;
            GeneratedMessageLite var4;
            if (var3) {
               var4 = var0;
            } else {
               var4 = null;
            }

            var0.dynamicMethod(var5, var4);
         }

         return var3;
      }
   }

   protected static Internal.BooleanList mutableCopy(Internal.BooleanList var0) {
      int var1 = var0.size();
      if (var1 == 0) {
         var1 = 10;
      } else {
         var1 *= 2;
      }

      return var0.mutableCopyWithCapacity(var1);
   }

   protected static Internal.DoubleList mutableCopy(Internal.DoubleList var0) {
      int var1 = var0.size();
      if (var1 == 0) {
         var1 = 10;
      } else {
         var1 *= 2;
      }

      return var0.mutableCopyWithCapacity(var1);
   }

   protected static Internal.FloatList mutableCopy(Internal.FloatList var0) {
      int var1 = var0.size();
      if (var1 == 0) {
         var1 = 10;
      } else {
         var1 *= 2;
      }

      return var0.mutableCopyWithCapacity(var1);
   }

   protected static Internal.IntList mutableCopy(Internal.IntList var0) {
      int var1 = var0.size();
      if (var1 == 0) {
         var1 = 10;
      } else {
         var1 *= 2;
      }

      return var0.mutableCopyWithCapacity(var1);
   }

   protected static Internal.LongList mutableCopy(Internal.LongList var0) {
      int var1 = var0.size();
      if (var1 == 0) {
         var1 = 10;
      } else {
         var1 *= 2;
      }

      return var0.mutableCopyWithCapacity(var1);
   }

   protected static <E> Internal.ProtobufList<E> mutableCopy(Internal.ProtobufList<E> var0) {
      int var1 = var0.size();
      if (var1 == 0) {
         var1 = 10;
      } else {
         var1 *= 2;
      }

      return var0.mutableCopyWithCapacity(var1);
   }

   protected static Object newMessageInfo(MessageLite var0, String var1, Object[] var2) {
      return new RawMessageInfo(var0, var1, var2);
   }

   public static <ContainingType extends MessageLite, Type> GeneratedMessageLite.GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(
      ContainingType var0, MessageLite var1, Internal.EnumLiteMap<?> var2, int var3, WireFormat.FieldType var4, boolean var5, Class var6
   ) {
      return new GeneratedMessageLite.GeneratedExtension<>(
         (ContainingType)var0, (Type)Collections.emptyList(), var1, new GeneratedMessageLite.ExtensionDescriptor(var2, var3, var4, true, var5), var6
      );
   }

   public static <ContainingType extends MessageLite, Type> GeneratedMessageLite.GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(
      ContainingType var0, Type var1, MessageLite var2, Internal.EnumLiteMap<?> var3, int var4, WireFormat.FieldType var5, Class var6
   ) {
      return new GeneratedMessageLite.GeneratedExtension<>(
         (ContainingType)var0, (Type)var1, var2, new GeneratedMessageLite.ExtensionDescriptor(var3, var4, var5, false, false), var6
      );
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T var0, InputStream var1) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialDelimitedFrom((T)var0, var1, ExtensionRegistryLite.getEmptyRegistry()));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T var0, InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialDelimitedFrom((T)var0, var1, var2));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, ByteString var1) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parseFrom((T)var0, var1, ExtensionRegistryLite.getEmptyRegistry()));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialFrom((T)var0, var1, var2));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, CodedInputStream var1) throws InvalidProtocolBufferException {
      return parseFrom((T)var0, var1, ExtensionRegistryLite.getEmptyRegistry());
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialFrom((T)var0, var1, var2));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, InputStream var1) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialFrom((T)var0, CodedInputStream.newInstance(var1), ExtensionRegistryLite.getEmptyRegistry()));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialFrom((T)var0, CodedInputStream.newInstance(var1), var2));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, ByteBuffer var1) throws InvalidProtocolBufferException {
      return parseFrom((T)var0, var1, ExtensionRegistryLite.getEmptyRegistry());
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, ByteBuffer var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parseFrom((T)var0, CodedInputStream.newInstance(var1), var2));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, byte[] var1) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialFrom((T)var0, var1, 0, var1.length, ExtensionRegistryLite.getEmptyRegistry()));
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T var0, byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return checkMessageInitialized(parsePartialFrom((T)var0, var1, 0, var1.length, var2));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T var0, InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      int var13;
      label50: {
         label44: {
            try {
               var13 = var1.read();
            } catch (InvalidProtocolBufferException var7) {
               var11 = var7;
               if (!var7.getThrownFromInputStream()) {
                  throw var7;
               }
               break label44;
            } catch (IOException var8) {
               throw new InvalidProtocolBufferException(var8);
            }

            if (var13 == -1) {
               return null;
            }

            try {
               var13 = CodedInputStream.readRawVarint32(var13, var1);
               break label50;
            } catch (InvalidProtocolBufferException var5) {
               var11 = var5;
               if (!var5.getThrownFromInputStream()) {
                  throw var5;
               }
            } catch (IOException var6) {
               throw new InvalidProtocolBufferException(var6);
            }
         }

         InvalidProtocolBufferException var9 = new InvalidProtocolBufferException((IOException)var11);
         throw var9;
      }

      CodedInputStream var12 = CodedInputStream.newInstance(new AbstractMessageLite.Builder.LimitedInputStream(var1, var13));
      var0 = parsePartialFrom(var0, var12, var2);

      try {
         var12.checkLastTagWas(0);
         return (T)var0;
      } catch (InvalidProtocolBufferException var4) {
         throw var4.setUnfinishedMessage(var0);
      }
   }

   private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T var0, ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      CodedInputStream var5 = var1.newCodedInput();
      var0 = parsePartialFrom(var0, var5, var2);

      try {
         var5.checkLastTagWas(0);
         return (T)var0;
      } catch (InvalidProtocolBufferException var3) {
         throw var3.setUnfinishedMessage(var0);
      }
   }

   protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T var0, CodedInputStream var1) throws InvalidProtocolBufferException {
      return parsePartialFrom((T)var0, var1, ExtensionRegistryLite.getEmptyRegistry());
   }

   static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T var0, CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      GeneratedMessageLite var3 = var0.newMutableInstance();

      try {
         Schema var8 = Protobuf.getInstance().schemaFor(var3);
         var8.mergeFrom(var3, CodedInputStreamReader.forCodedInput(var1), var2);
         var8.makeImmutable(var3);
         return (T)var3;
      } catch (InvalidProtocolBufferException var4) {
         InvalidProtocolBufferException var9 = var4;
         if (var4.getThrownFromInputStream()) {
            var9 = new InvalidProtocolBufferException((IOException)var4);
         }

         throw var9.setUnfinishedMessage(var3);
      } catch (UninitializedMessageException var5) {
         throw var5.asInvalidProtocolBufferException().setUnfinishedMessage(var3);
      } catch (IOException var6) {
         if (var6.getCause() instanceof InvalidProtocolBufferException) {
            throw (InvalidProtocolBufferException)var6.getCause();
         } else {
            throw new InvalidProtocolBufferException(var6).setUnfinishedMessage(var3);
         }
      } catch (RuntimeException var7) {
         if (var7.getCause() instanceof InvalidProtocolBufferException) {
            throw (InvalidProtocolBufferException)var7.getCause();
         } else {
            throw var7;
         }
      }
   }

   private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T var0, byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException {
      GeneratedMessageLite var5 = var0.newMutableInstance();

      try {
         Schema var6 = Protobuf.getInstance().schemaFor(var5);
         ArrayDecoders.Registers var12 = new ArrayDecoders.Registers(var4);
         var6.mergeFrom(var5, var1, var2, var2 + var3, var12);
         var6.makeImmutable(var5);
         return (T)var5;
      } catch (InvalidProtocolBufferException var7) {
         InvalidProtocolBufferException var11 = var7;
         if (var7.getThrownFromInputStream()) {
            var11 = new InvalidProtocolBufferException((IOException)var7);
         }

         throw var11.setUnfinishedMessage(var5);
      } catch (UninitializedMessageException var8) {
         throw var8.asInvalidProtocolBufferException().setUnfinishedMessage(var5);
      } catch (IOException var9) {
         if (var9.getCause() instanceof InvalidProtocolBufferException) {
            throw (InvalidProtocolBufferException)var9.getCause();
         } else {
            throw new InvalidProtocolBufferException(var9).setUnfinishedMessage(var5);
         }
      } catch (IndexOutOfBoundsException var10) {
         throw InvalidProtocolBufferException.truncatedMessage().setUnfinishedMessage(var5);
      }
   }

   protected static <T extends GeneratedMessageLite<?, ?>> void registerDefaultInstance(Class<T> var0, T var1) {
      var1.markImmutable();
      defaultInstanceMap.put(var0, var1);
   }

   Object buildMessageInfo() throws Exception {
      return this.dynamicMethod(GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO);
   }

   void clearMemoizedHashCode() {
      this.memoizedHashCode = 0;
   }

   void clearMemoizedSerializedSize() {
      this.setMemoizedSerializedSize(Integer.MAX_VALUE);
   }

   int computeHashCode() {
      return Protobuf.getInstance().schemaFor(this).hashCode(this);
   }

   protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.Builder<MessageType, BuilderType>> BuilderType createBuilder() {
      return (BuilderType)this.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER);
   }

   protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.Builder<MessageType, BuilderType>> BuilderType createBuilder(
      MessageType var1
   ) {
      return (BuilderType)this.createBuilder().mergeFrom((MessageType)var1);
   }

   protected Object dynamicMethod(GeneratedMessageLite.MethodToInvoke var1) {
      return this.dynamicMethod(var1, null, null);
   }

   protected Object dynamicMethod(GeneratedMessageLite.MethodToInvoke var1, Object var2) {
      return this.dynamicMethod(var1, var2, null);
   }

   protected abstract Object dynamicMethod(GeneratedMessageLite.MethodToInvoke var1, Object var2, Object var3);

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return this.getClass() != var1.getClass()
            ? false
            : Protobuf.getInstance().schemaFor(this).equals(this, (GeneratedMessageLite<MessageType, BuilderType>)var1);
      }
   }

   public final MessageType getDefaultInstanceForType() {
      return (MessageType)this.dynamicMethod(GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE);
   }

   int getMemoizedHashCode() {
      return this.memoizedHashCode;
   }

   @Override
   int getMemoizedSerializedSize() {
      return this.memoizedSerializedSize & 2147483647;
   }

   @Override
   public final Parser<MessageType> getParserForType() {
      return (Parser<MessageType>)this.dynamicMethod(GeneratedMessageLite.MethodToInvoke.GET_PARSER);
   }

   @Override
   public int getSerializedSize() {
      return this.getSerializedSize(null);
   }

   @Override
   int getSerializedSize(Schema var1) {
      if (this.isMutable()) {
         int var4 = this.computeSerializedSize(var1);
         if (var4 >= 0) {
            return var4;
         } else {
            StringBuilder var3 = new StringBuilder("serialized size must be non-negative, was ");
            var3.append(var4);
            throw new IllegalStateException(var3.toString());
         }
      } else if (this.getMemoizedSerializedSize() != Integer.MAX_VALUE) {
         return this.getMemoizedSerializedSize();
      } else {
         int var2 = this.computeSerializedSize(var1);
         this.setMemoizedSerializedSize(var2);
         return var2;
      }
   }

   @Override
   public int hashCode() {
      if (this.isMutable()) {
         return this.computeHashCode();
      } else {
         if (this.hashCodeIsNotMemoized()) {
            this.setMemoizedHashCode(this.computeHashCode());
         }

         return this.getMemoizedHashCode();
      }
   }

   boolean hashCodeIsNotMemoized() {
      boolean var1;
      if (this.getMemoizedHashCode() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public final boolean isInitialized() {
      return isInitialized(this, Boolean.TRUE);
   }

   boolean isMutable() {
      boolean var1;
      if ((this.memoizedSerializedSize & -2147483648) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void makeImmutable() {
      Protobuf.getInstance().schemaFor(this).makeImmutable(this);
      this.markImmutable();
   }

   void markImmutable() {
      this.memoizedSerializedSize &= Integer.MAX_VALUE;
   }

   protected void mergeLengthDelimitedField(int var1, ByteString var2) {
      this.ensureUnknownFieldsInitialized();
      this.unknownFields.mergeLengthDelimitedField(var1, var2);
   }

   protected final void mergeUnknownFields(UnknownFieldSetLite var1) {
      this.unknownFields = UnknownFieldSetLite.mutableCopyOf(this.unknownFields, var1);
   }

   protected void mergeVarintField(int var1, int var2) {
      this.ensureUnknownFieldsInitialized();
      this.unknownFields.mergeVarintField(var1, var2);
   }

   public final BuilderType newBuilderForType() {
      return (BuilderType)this.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER);
   }

   MessageType newMutableInstance() {
      return (MessageType)this.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE);
   }

   protected boolean parseUnknownField(int var1, CodedInputStream var2) throws IOException {
      if (WireFormat.getTagWireType(var1) == 4) {
         return false;
      } else {
         this.ensureUnknownFieldsInitialized();
         return this.unknownFields.mergeFieldFrom(var1, var2);
      }
   }

   void setMemoizedHashCode(int var1) {
      this.memoizedHashCode = var1;
   }

   @Override
   void setMemoizedSerializedSize(int var1) {
      if (var1 >= 0) {
         this.memoizedSerializedSize = var1 & 2147483647 | this.memoizedSerializedSize & -2147483648;
      } else {
         StringBuilder var2 = new StringBuilder("serialized size must be non-negative, was ");
         var2.append(var1);
         throw new IllegalStateException(var2.toString());
      }
   }

   public final BuilderType toBuilder() {
      return (BuilderType)((GeneratedMessageLite.Builder)this.dynamicMethod(GeneratedMessageLite.MethodToInvoke.NEW_BUILDER)).mergeFrom(this);
   }

   @Override
   public String toString() {
      return MessageLiteToString.toString(this, super.toString());
   }

   @Override
   public void writeTo(CodedOutputStream var1) throws IOException {
      Protobuf.getInstance().schemaFor(this).writeTo(this, CodedOutputStreamWriter.forCodedOutput(var1));
   }

   public abstract static class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.Builder<MessageType, BuilderType>>
      extends AbstractMessageLite.Builder<MessageType, BuilderType> {
      private final MessageType defaultInstance;
      protected MessageType instance;

      protected Builder(MessageType var1) {
         this.defaultInstance = (MessageType)var1;
         if (!var1.isMutable()) {
            this.instance = this.newMutableInstance();
         } else {
            throw new IllegalArgumentException("Default instance must be immutable.");
         }
      }

      private static <MessageType> void mergeFromInstance(MessageType var0, MessageType var1) {
         Protobuf.getInstance().schemaFor(var0).mergeFrom(var0, var1);
      }

      private MessageType newMutableInstance() {
         return this.defaultInstance.newMutableInstance();
      }

      public final MessageType build() {
         GeneratedMessageLite var1 = this.buildPartial();
         if (var1.isInitialized()) {
            return (MessageType)var1;
         } else {
            throw newUninitializedMessageException(var1);
         }
      }

      public MessageType buildPartial() {
         if (!this.instance.isMutable()) {
            return this.instance;
         } else {
            this.instance.makeImmutable();
            return this.instance;
         }
      }

      public final BuilderType clear() {
         if (!this.defaultInstance.isMutable()) {
            this.instance = this.newMutableInstance();
            return (BuilderType)this;
         } else {
            throw new IllegalArgumentException("Default instance must be immutable.");
         }
      }

      public BuilderType clone() {
         GeneratedMessageLite.Builder var1 = this.getDefaultInstanceForType().newBuilderForType();
         var1.instance = this.buildPartial();
         return (BuilderType)var1;
      }

      protected final void copyOnWrite() {
         if (!this.instance.isMutable()) {
            this.copyOnWriteInternal();
         }
      }

      protected void copyOnWriteInternal() {
         GeneratedMessageLite var1 = this.newMutableInstance();
         mergeFromInstance(var1, this.instance);
         this.instance = (MessageType)var1;
      }

      public MessageType getDefaultInstanceForType() {
         return this.defaultInstance;
      }

      protected BuilderType internalMergeFrom(MessageType var1) {
         return this.mergeFrom((MessageType)var1);
      }

      @Override
      public final boolean isInitialized() {
         return GeneratedMessageLite.isInitialized(this.instance, false);
      }

      public BuilderType mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException {
         this.copyOnWrite();

         try {
            Protobuf.getInstance().schemaFor(this.instance).mergeFrom(this.instance, CodedInputStreamReader.forCodedInput(var1), var2);
            return (BuilderType)this;
         } catch (RuntimeException var3) {
            if (var3.getCause() instanceof IOException) {
               throw (IOException)var3.getCause();
            } else {
               throw var3;
            }
         }
      }

      public BuilderType mergeFrom(MessageType var1) {
         if (this.getDefaultInstanceForType().equals(var1)) {
            return (BuilderType)this;
         } else {
            this.copyOnWrite();
            mergeFromInstance(this.instance, var1);
            return (BuilderType)this;
         }
      }

      public BuilderType mergeFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException {
         return this.mergeFrom(var1, var2, var3, ExtensionRegistryLite.getEmptyRegistry());
      }

      public BuilderType mergeFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException {
         this.copyOnWrite();

         try {
            Schema var6 = Protobuf.getInstance().schemaFor(this.instance);
            GeneratedMessageLite var5 = this.instance;
            ArrayDecoders.Registers var7 = new ArrayDecoders.Registers(var4);
            var6.mergeFrom(var5, var1, var2, var2 + var3, var7);
            return (BuilderType)this;
         } catch (InvalidProtocolBufferException var8) {
            throw var8;
         } catch (IndexOutOfBoundsException var9) {
            throw InvalidProtocolBufferException.truncatedMessage();
         } catch (IOException var10) {
            throw new RuntimeException("Reading from byte array should not throw IOException.", var10);
         }
      }
   }

   protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T> {
      private final T defaultInstance;

      public DefaultInstanceBasedParser(T var1) {
         this.defaultInstance = (T)var1;
      }

      public T parsePartialFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, var1, var2);
      }

      public T parsePartialFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, var1, var2, var3, var4);
      }
   }

   public abstract static class ExtendableBuilder<MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>>
      extends GeneratedMessageLite.Builder<MessageType, BuilderType>
      implements GeneratedMessageLite.ExtendableMessageOrBuilder<MessageType, BuilderType> {
      protected ExtendableBuilder(MessageType var1) {
         super((MessageType)var1);
      }

      private FieldSet<GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable() {
         FieldSet var2 = ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions;
         FieldSet var1 = var2;
         if (var2.isImmutable()) {
            var1 = var2.clone();
            ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions = var1;
         }

         return var1;
      }

      private void verifyExtensionContainingType(GeneratedMessageLite.GeneratedExtension<MessageType, ?> var1) {
         if (var1.getContainingTypeDefaultInstance() != this.getDefaultInstanceForType()) {
            throw new IllegalArgumentException(
               "This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings."
            );
         }
      }

      public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> var1, Type var2) {
         GeneratedMessageLite.GeneratedExtension var3 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var3);
         this.copyOnWrite();
         this.ensureExtensionsAreMutable().addRepeatedField(var3.descriptor, var3.singularToFieldSetType(var2));
         return (BuilderType)this;
      }

      public final MessageType buildPartial() {
         if (!((GeneratedMessageLite.ExtendableMessage)this.instance).isMutable()) {
            return (MessageType)((GeneratedMessageLite.ExtendableMessage)this.instance);
         } else {
            ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions.makeImmutable();
            return (MessageType)((GeneratedMessageLite.ExtendableMessage)super.buildPartial());
         }
      }

      public final BuilderType clearExtension(ExtensionLite<MessageType, ?> var1) {
         GeneratedMessageLite.GeneratedExtension var2 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var2);
         this.copyOnWrite();
         this.ensureExtensionsAreMutable().clearField(var2.descriptor);
         return (BuilderType)this;
      }

      @Override
      protected void copyOnWriteInternal() {
         super.copyOnWriteInternal();
         if (((GeneratedMessageLite.ExtendableMessage)this.instance).extensions != FieldSet.emptySet()) {
            ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions = ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions.clone();
         }
      }

      @Override
      public final <Type> Type getExtension(ExtensionLite<MessageType, Type> var1) {
         return ((GeneratedMessageLite.ExtendableMessage)this.instance).getExtension(var1);
      }

      @Override
      public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> var1, int var2) {
         return ((GeneratedMessageLite.ExtendableMessage)this.instance).getExtension(var1, var2);
      }

      @Override
      public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> var1) {
         return ((GeneratedMessageLite.ExtendableMessage)this.instance).getExtensionCount(var1);
      }

      @Override
      public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> var1) {
         return ((GeneratedMessageLite.ExtendableMessage)this.instance).hasExtension(var1);
      }

      void internalSetExtensionSet(FieldSet<GeneratedMessageLite.ExtensionDescriptor> var1) {
         this.copyOnWrite();
         ((GeneratedMessageLite.ExtendableMessage)this.instance).extensions = var1;
      }

      public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> var1, int var2, Type var3) {
         GeneratedMessageLite.GeneratedExtension var4 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var4);
         this.copyOnWrite();
         this.ensureExtensionsAreMutable().setRepeatedField(var4.descriptor, var2, var4.singularToFieldSetType(var3));
         return (BuilderType)this;
      }

      public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> var1, Type var2) {
         GeneratedMessageLite.GeneratedExtension var3 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var3);
         this.copyOnWrite();
         this.ensureExtensionsAreMutable().setField(var3.descriptor, var3.toFieldSetType(var2));
         return (BuilderType)this;
      }
   }

   public abstract static class ExtendableMessage<MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>>
      extends GeneratedMessageLite<MessageType, BuilderType>
      implements GeneratedMessageLite.ExtendableMessageOrBuilder<MessageType, BuilderType> {
      protected FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = FieldSet.emptySet();

      private void eagerlyMergeMessageSetExtension(
         CodedInputStream var1, GeneratedMessageLite.GeneratedExtension<?, ?> var2, ExtensionRegistryLite var3, int var4
      ) throws IOException {
         this.parseExtension(var1, var3, var2, WireFormat.makeTag(var4, 2), var4);
      }

      private void mergeMessageSetExtensionFromBytes(ByteString var1, ExtensionRegistryLite var2, GeneratedMessageLite.GeneratedExtension<?, ?> var3) throws IOException {
         MessageLite var4 = (MessageLite)this.extensions.getField(var3.descriptor);
         MessageLite.Builder var7;
         if (var4 != null) {
            var7 = var4.toBuilder();
         } else {
            var7 = null;
         }

         MessageLite.Builder var5 = var7;
         if (var7 == null) {
            var5 = var3.getMessageDefaultInstance().newBuilderForType();
         }

         var5.mergeFrom(var1, var2);
         MessageLite var6 = var5.build();
         this.ensureExtensionsAreMutable().setField(var3.descriptor, var3.singularToFieldSetType(var6));
      }

      private <MessageType extends MessageLite> void mergeMessageSetExtensionFromCodedStream(
         MessageType var1, CodedInputStream var2, ExtensionRegistryLite var3
      ) throws IOException {
         int var4 = 0;
         ByteString var6 = null;
         GeneratedMessageLite.GeneratedExtension var7 = null;

         while (true) {
            int var5 = var2.readTag();
            if (var5 == 0) {
               break;
            }

            if (var5 == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
               var5 = var2.readUInt32();
               var4 = var5;
               if (var5 != 0) {
                  var7 = var3.findLiteExtensionByNumber(var1, var5);
                  var4 = var5;
               }
            } else if (var5 == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
               if (var4 != 0 && var7 != null) {
                  this.eagerlyMergeMessageSetExtension(var2, var7, var3, var4);
                  var6 = null;
               } else {
                  var6 = var2.readBytes();
               }
            } else if (!var2.skipField(var5)) {
               break;
            }
         }

         var2.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
         if (var6 != null && var4 != 0) {
            if (var7 != null) {
               this.mergeMessageSetExtensionFromBytes(var6, var3, var7);
            } else if (var6 != null) {
               this.mergeLengthDelimitedField(var4, var6);
            }
         }
      }

      private boolean parseExtension(CodedInputStream var1, ExtensionRegistryLite var2, GeneratedMessageLite.GeneratedExtension<?, ?> var3, int var4, int var5) throws IOException {
         boolean var7;
         int var17;
         label85: {
            label84: {
               var17 = WireFormat.getTagWireType(var4);
               if (var3 != null) {
                  if (var17 == FieldSet.getWireFormatForFieldType(var3.descriptor.getLiteType(), false)) {
                     var17 = 0;
                     break label84;
                  }

                  if (var3.descriptor.isRepeated
                     && var3.descriptor.type.isPackable()
                     && var17 == FieldSet.getWireFormatForFieldType(var3.descriptor.getLiteType(), true)) {
                     var17 = 0;
                     var7 = true;
                     break label85;
                  }
               }

               var17 = 1;
            }

            var7 = false;
         }

         if (var17) {
            return this.parseUnknownField(var4, var1);
         } else {
            this.ensureExtensionsAreMutable();
            if (var7) {
               var4 = var1.pushLimit(var1.readRawVarint32());
               if (var3.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
                  while (var1.getBytesUntilLimit() > 0) {
                     var5 = var1.readEnum();
                     Internal.EnumLite var12 = var3.descriptor.getEnumType().findValueByNumber(var5);
                     if (var12 == null) {
                        return true;
                     }

                     this.extensions.addRepeatedField(var3.descriptor, var3.singularToFieldSetType(var12));
                  }
               } else {
                  while (var1.getBytesUntilLimit() > 0) {
                     Object var11 = FieldSet.readPrimitiveField(var1, var3.descriptor.getLiteType(), false);
                     this.extensions.addRepeatedField(var3.descriptor, var11);
                  }
               }

               var1.popLimit(var4);
            } else {
               var4 = <unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$JavaType[var3.descriptor.getLiteJavaType().ordinal()];
               Object var10;
               if (var4 != 1) {
                  if (var4 != 2) {
                     var10 = FieldSet.readPrimitiveField(var1, var3.descriptor.getLiteType(), false);
                  } else {
                     var4 = var1.readEnum();
                     var10 = var3.descriptor.getEnumType().findValueByNumber(var4);
                     if (var10 == null) {
                        this.mergeVarintField(var5, var4);
                        return true;
                     }
                  }
               } else {
                  MessageLite.Builder var18;
                  label59: {
                     if (!var3.descriptor.isRepeated()) {
                        MessageLite var8 = (MessageLite)this.extensions.getField(var3.descriptor);
                        if (var8 != null) {
                           var18 = var8.toBuilder();
                           break label59;
                        }
                     }

                     var18 = null;
                  }

                  MessageLite.Builder var9 = var18;
                  if (var18 == null) {
                     var9 = var3.getMessageDefaultInstance().newBuilderForType();
                  }

                  if (var3.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
                     var1.readGroup(var3.getNumber(), var9, var2);
                  } else {
                     var1.readMessage(var9, var2);
                  }

                  var10 = var9.build();
               }

               if (var3.descriptor.isRepeated()) {
                  this.extensions.addRepeatedField(var3.descriptor, var3.singularToFieldSetType(var10));
               } else {
                  this.extensions.setField(var3.descriptor, var3.singularToFieldSetType(var10));
               }
            }

            return true;
         }
      }

      private void verifyExtensionContainingType(GeneratedMessageLite.GeneratedExtension<MessageType, ?> var1) {
         if (var1.getContainingTypeDefaultInstance() != this.getDefaultInstanceForType()) {
            throw new IllegalArgumentException(
               "This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings."
            );
         }
      }

      FieldSet<GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable() {
         if (this.extensions.isImmutable()) {
            this.extensions = this.extensions.clone();
         }

         return this.extensions;
      }

      protected boolean extensionsAreInitialized() {
         return this.extensions.isInitialized();
      }

      protected int extensionsSerializedSize() {
         return this.extensions.getSerializedSize();
      }

      protected int extensionsSerializedSizeAsMessageSet() {
         return this.extensions.getMessageSetSerializedSize();
      }

      @Override
      public final <Type> Type getExtension(ExtensionLite<MessageType, Type> var1) {
         GeneratedMessageLite.GeneratedExtension var2 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var2);
         Object var3 = this.extensions.getField(var2.descriptor);
         return (Type)(var3 == null ? var2.defaultValue : var2.fromFieldSetType(var3));
      }

      @Override
      public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> var1, int var2) {
         GeneratedMessageLite.GeneratedExtension var3 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var3);
         return (Type)var3.singularFromFieldSetType(this.extensions.getRepeatedField(var3.descriptor, var2));
      }

      @Override
      public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> var1) {
         GeneratedMessageLite.GeneratedExtension var2 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var2);
         return this.extensions.getRepeatedFieldCount(var2.descriptor);
      }

      @Override
      public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> var1) {
         GeneratedMessageLite.GeneratedExtension var2 = GeneratedMessageLite.checkIsLite(var1);
         this.verifyExtensionContainingType(var2);
         return this.extensions.hasField(var2.descriptor);
      }

      protected final void mergeExtensionFields(MessageType var1) {
         if (this.extensions.isImmutable()) {
            this.extensions = this.extensions.clone();
         }

         this.extensions.mergeFrom(var1.extensions);
      }

      protected GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newExtensionWriter() {
         return new GeneratedMessageLite.ExtendableMessage.ExtensionWriter(this, false);
      }

      protected GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newMessageSetExtensionWriter() {
         return new GeneratedMessageLite.ExtendableMessage.ExtensionWriter(this, true);
      }

      protected <MessageType extends MessageLite> boolean parseUnknownField(MessageType var1, CodedInputStream var2, ExtensionRegistryLite var3, int var4) throws IOException {
         int var5 = WireFormat.getTagFieldNumber(var4);
         return this.parseExtension(var2, var3, var3.findLiteExtensionByNumber(var1, var5), var4, var5);
      }

      protected <MessageType extends MessageLite> boolean parseUnknownFieldAsMessageSet(
         MessageType var1, CodedInputStream var2, ExtensionRegistryLite var3, int var4
      ) throws IOException {
         if (var4 == WireFormat.MESSAGE_SET_ITEM_TAG) {
            this.mergeMessageSetExtensionFromCodedStream(var1, var2, var3);
            return true;
         } else {
            return WireFormat.getTagWireType(var4) == 2 ? this.parseUnknownField(var1, var2, var3, var4) : var2.skipField(var4);
         }
      }

      protected class ExtensionWriter {
         private final Iterator<Entry<GeneratedMessageLite.ExtensionDescriptor, Object>> iter;
         private final boolean messageSetWireFormat;
         private Entry<GeneratedMessageLite.ExtensionDescriptor, Object> next;
         final GeneratedMessageLite.ExtendableMessage this$0;

         private ExtensionWriter(GeneratedMessageLite.ExtendableMessage var1, boolean var2) {
            this.this$0 = var1;
            Iterator var3 = var1.extensions.iterator();
            this.iter = var3;
            if (var3.hasNext()) {
               this.next = (Entry<GeneratedMessageLite.ExtensionDescriptor, Object>)var3.next();
            }

            this.messageSetWireFormat = var2;
         }

         public void writeUntil(int var1, CodedOutputStream var2) throws IOException {
            while (true) {
               Entry var3 = this.next;
               if (var3 == null || ((GeneratedMessageLite.ExtensionDescriptor)var3.getKey()).getNumber() >= var1) {
                  return;
               }

               GeneratedMessageLite.ExtensionDescriptor var4 = this.next.getKey();
               if (this.messageSetWireFormat && var4.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !var4.isRepeated()) {
                  var2.writeMessageSetExtension(var4.getNumber(), (MessageLite)this.next.getValue());
               } else {
                  FieldSet.writeField(var4, this.next.getValue(), var2);
               }

               if (this.iter.hasNext()) {
                  this.next = this.iter.next();
               } else {
                  this.next = null;
               }
            }
         }
      }
   }

   public interface ExtendableMessageOrBuilder<MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>>
      extends MessageLiteOrBuilder {
      <Type> Type getExtension(ExtensionLite<MessageType, Type> var1);

      <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> var1, int var2);

      <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> var1);

      <Type> boolean hasExtension(ExtensionLite<MessageType, Type> var1);
   }

   static final class ExtensionDescriptor implements FieldSet.FieldDescriptorLite<GeneratedMessageLite.ExtensionDescriptor> {
      final Internal.EnumLiteMap<?> enumTypeMap;
      final boolean isPacked;
      final boolean isRepeated;
      final int number;
      final WireFormat.FieldType type;

      ExtensionDescriptor(Internal.EnumLiteMap<?> var1, int var2, WireFormat.FieldType var3, boolean var4, boolean var5) {
         this.enumTypeMap = var1;
         this.number = var2;
         this.type = var3;
         this.isRepeated = var4;
         this.isPacked = var5;
      }

      public int compareTo(GeneratedMessageLite.ExtensionDescriptor var1) {
         return this.number - var1.number;
      }

      @Override
      public Internal.EnumLiteMap<?> getEnumType() {
         return this.enumTypeMap;
      }

      @Override
      public WireFormat.JavaType getLiteJavaType() {
         return this.type.getJavaType();
      }

      @Override
      public WireFormat.FieldType getLiteType() {
         return this.type;
      }

      @Override
      public int getNumber() {
         return this.number;
      }

      @Override
      public MessageLite.Builder internalMergeFrom(MessageLite.Builder var1, MessageLite var2) {
         return ((GeneratedMessageLite.Builder)var1).mergeFrom((MessageType)var2);
      }

      @Override
      public boolean isPacked() {
         return this.isPacked;
      }

      @Override
      public boolean isRepeated() {
         return this.isRepeated;
      }
   }

   public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {
      final ContainingType containingTypeDefaultInstance;
      final Type defaultValue;
      final GeneratedMessageLite.ExtensionDescriptor descriptor;
      final MessageLite messageDefaultInstance;

      GeneratedExtension(ContainingType var1, Type var2, MessageLite var3, GeneratedMessageLite.ExtensionDescriptor var4, Class var5) {
         if (var1 != null) {
            if (var4.getLiteType() == WireFormat.FieldType.MESSAGE && var3 == null) {
               throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
               this.containingTypeDefaultInstance = (ContainingType)var1;
               this.defaultValue = (Type)var2;
               this.messageDefaultInstance = var3;
               this.descriptor = var4;
            }
         } else {
            throw new IllegalArgumentException("Null containingTypeDefaultInstance");
         }
      }

      Object fromFieldSetType(Object var1) {
         if (!this.descriptor.isRepeated()) {
            return this.singularFromFieldSetType(var1);
         } else if (this.descriptor.getLiteJavaType() != WireFormat.JavaType.ENUM) {
            return var1;
         } else {
            ArrayList var2 = new ArrayList();
            var1 = ((List)var1).iterator();

            while (var1.hasNext()) {
               var2.add(this.singularFromFieldSetType(var1.next()));
            }

            return var2;
         }
      }

      public ContainingType getContainingTypeDefaultInstance() {
         return this.containingTypeDefaultInstance;
      }

      @Override
      public Type getDefaultValue() {
         return this.defaultValue;
      }

      @Override
      public WireFormat.FieldType getLiteType() {
         return this.descriptor.getLiteType();
      }

      @Override
      public MessageLite getMessageDefaultInstance() {
         return this.messageDefaultInstance;
      }

      @Override
      public int getNumber() {
         return this.descriptor.getNumber();
      }

      @Override
      public boolean isRepeated() {
         return this.descriptor.isRepeated;
      }

      Object singularFromFieldSetType(Object var1) {
         Object var2 = var1;
         if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
            var2 = this.descriptor.enumTypeMap.findValueByNumber((Integer)var1);
         }

         return var2;
      }

      Object singularToFieldSetType(Object var1) {
         Object var2 = var1;
         if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
            var2 = ((Internal.EnumLite)var1).getNumber();
         }

         return var2;
      }

      Object toFieldSetType(Object var1) {
         if (!this.descriptor.isRepeated()) {
            return this.singularToFieldSetType(var1);
         } else if (this.descriptor.getLiteJavaType() != WireFormat.JavaType.ENUM) {
            return var1;
         } else {
            ArrayList var2 = new ArrayList();
            var1 = ((List)var1).iterator();

            while (var1.hasNext()) {
               var2.add(this.singularToFieldSetType(var1.next()));
            }

            return var2;
         }
      }
   }

   public static enum MethodToInvoke {
      BUILD_MESSAGE_INFO,
      GET_DEFAULT_INSTANCE,
      GET_MEMOIZED_IS_INITIALIZED,
      GET_PARSER,
      NEW_BUILDER,
      NEW_MUTABLE_INSTANCE,
      SET_MEMOIZED_IS_INITIALIZED;
      private static final GeneratedMessageLite.MethodToInvoke[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         GeneratedMessageLite.MethodToInvoke var3 = new GeneratedMessageLite.MethodToInvoke();
         GET_MEMOIZED_IS_INITIALIZED = var3;
         GeneratedMessageLite.MethodToInvoke var4 = new GeneratedMessageLite.MethodToInvoke();
         SET_MEMOIZED_IS_INITIALIZED = var4;
         GeneratedMessageLite.MethodToInvoke var0 = new GeneratedMessageLite.MethodToInvoke();
         BUILD_MESSAGE_INFO = var0;
         GeneratedMessageLite.MethodToInvoke var2 = new GeneratedMessageLite.MethodToInvoke();
         NEW_MUTABLE_INSTANCE = var2;
         GeneratedMessageLite.MethodToInvoke var5 = new GeneratedMessageLite.MethodToInvoke();
         NEW_BUILDER = var5;
         GeneratedMessageLite.MethodToInvoke var6 = new GeneratedMessageLite.MethodToInvoke();
         GET_DEFAULT_INSTANCE = var6;
         GeneratedMessageLite.MethodToInvoke var1 = new GeneratedMessageLite.MethodToInvoke();
         GET_PARSER = var1;
         $VALUES = new GeneratedMessageLite.MethodToInvoke[]{var3, var4, var0, var2, var5, var6, var1};
      }
   }

   protected static final class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      private final byte[] asBytes;
      private final Class<?> messageClass;
      private final String messageClassName;

      SerializedForm(MessageLite var1) {
         this.messageClass = var1.getClass();
         this.messageClassName = var1.getClass().getName();
         this.asBytes = var1.toByteArray();
      }

      public static GeneratedMessageLite.SerializedForm of(MessageLite var0) {
         return new GeneratedMessageLite.SerializedForm(var0);
      }

      @Deprecated
      private Object readResolveFallback() throws ObjectStreamException {
         try {
            java.lang.reflect.Field var8 = this.resolveMessageClass().getDeclaredField("defaultInstance");
            var8.setAccessible(true);
            return ((MessageLite)var8.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
         } catch (ClassNotFoundException var3) {
            StringBuilder var1 = new StringBuilder("Unable to find proto buffer class: ");
            var1.append(this.messageClassName);
            throw new RuntimeException(var1.toString(), var3);
         } catch (NoSuchFieldException var4) {
            StringBuilder var9 = new StringBuilder("Unable to find defaultInstance in ");
            var9.append(this.messageClassName);
            throw new RuntimeException(var9.toString(), var4);
         } catch (SecurityException var5) {
            StringBuilder var2 = new StringBuilder("Unable to call defaultInstance in ");
            var2.append(this.messageClassName);
            throw new RuntimeException(var2.toString(), var5);
         } catch (IllegalAccessException var6) {
            throw new RuntimeException("Unable to call parsePartialFrom", var6);
         } catch (InvalidProtocolBufferException var7) {
            throw new RuntimeException("Unable to understand proto buffer", var7);
         }
      }

      private Class<?> resolveMessageClass() throws ClassNotFoundException {
         Class var1 = this.messageClass;
         if (var1 == null) {
            var1 = Class.forName(this.messageClassName);
         }

         return var1;
      }

      protected Object readResolve() throws ObjectStreamException {
         try {
            java.lang.reflect.Field var1 = this.resolveMessageClass().getDeclaredField("DEFAULT_INSTANCE");
            var1.setAccessible(true);
            return ((MessageLite)var1.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
         } catch (ClassNotFoundException var3) {
            StringBuilder var8 = new StringBuilder("Unable to find proto buffer class: ");
            var8.append(this.messageClassName);
            throw new RuntimeException(var8.toString(), var3);
         } catch (NoSuchFieldException var4) {
            return this.readResolveFallback();
         } catch (SecurityException var5) {
            StringBuilder var2 = new StringBuilder("Unable to call DEFAULT_INSTANCE in ");
            var2.append(this.messageClassName);
            throw new RuntimeException(var2.toString(), var5);
         } catch (IllegalAccessException var6) {
            throw new RuntimeException("Unable to call parsePartialFrom", var6);
         } catch (InvalidProtocolBufferException var7) {
            throw new RuntimeException("Unable to understand proto buffer", var7);
         }
      }
   }
}
