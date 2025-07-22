package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public abstract class AbstractParser<MessageType extends MessageLite> implements Parser<MessageType> {
   private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();

   private MessageType checkMessageInitialized(MessageType var1) throws InvalidProtocolBufferException {
      if (var1 != null && !var1.isInitialized()) {
         throw this.newUninitializedMessageException((MessageType)var1).asInvalidProtocolBufferException().setUnfinishedMessage(var1);
      } else {
         return (MessageType)var1;
      }
   }

   private UninitializedMessageException newUninitializedMessageException(MessageType var1) {
      return var1 instanceof AbstractMessageLite ? ((AbstractMessageLite)var1).newUninitializedMessageException() : new UninitializedMessageException(var1);
   }

   public MessageType parseDelimitedFrom(InputStream var1) throws InvalidProtocolBufferException {
      return this.parseDelimitedFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parseDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return this.checkMessageInitialized(this.parsePartialDelimitedFrom(var1, var2));
   }

   public MessageType parseFrom(ByteString var1) throws InvalidProtocolBufferException {
      return this.parseFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parseFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return this.checkMessageInitialized(this.parsePartialFrom(var1, var2));
   }

   public MessageType parseFrom(CodedInputStream var1) throws InvalidProtocolBufferException {
      return this.parseFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parseFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return this.checkMessageInitialized(this.parsePartialFrom(var1, var2));
   }

   public MessageType parseFrom(InputStream var1) throws InvalidProtocolBufferException {
      return this.parseFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parseFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return this.checkMessageInitialized(this.parsePartialFrom(var1, var2));
   }

   public MessageType parseFrom(ByteBuffer var1) throws InvalidProtocolBufferException {
      return this.parseFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parseFrom(ByteBuffer var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      CodedInputStream var4 = CodedInputStream.newInstance(var1);
      MessageLite var5 = this.parsePartialFrom(var4, var2);

      try {
         var4.checkLastTagWas(0);
      } catch (InvalidProtocolBufferException var3) {
         throw var3.setUnfinishedMessage(var5);
      }

      return this.checkMessageInitialized((MessageType)var5);
   }

   public MessageType parseFrom(byte[] var1) throws InvalidProtocolBufferException {
      return this.parseFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parseFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException {
      return this.parseFrom(var1, var2, var3, EMPTY_REGISTRY);
   }

   public MessageType parseFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException {
      return this.checkMessageInitialized(this.parsePartialFrom(var1, var2, var3, var4));
   }

   public MessageType parseFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return this.parseFrom(var1, 0, var1.length, var2);
   }

   public MessageType parsePartialDelimitedFrom(InputStream var1) throws InvalidProtocolBufferException {
      return this.parsePartialDelimitedFrom(var1, EMPTY_REGISTRY);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public MessageType parsePartialDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      int var3;
      try {
         var3 = var1.read();
      } catch (IOException var5) {
         throw new InvalidProtocolBufferException(var5);
      }

      if (var3 == -1) {
         return null;
      } else {
         try {
            var3 = CodedInputStream.readRawVarint32(var3, var1);
         } catch (IOException var4) {
            throw new InvalidProtocolBufferException(var4);
         }

         return this.parsePartialFrom(new AbstractMessageLite.Builder.LimitedInputStream(var1, var3), var2);
      }
   }

   public MessageType parsePartialFrom(ByteString var1) throws InvalidProtocolBufferException {
      return this.parsePartialFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parsePartialFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      CodedInputStream var3 = var1.newCodedInput();
      MessageLite var5 = this.parsePartialFrom(var3, var2);

      try {
         var3.checkLastTagWas(0);
         return (MessageType)var5;
      } catch (InvalidProtocolBufferException var4) {
         throw var4.setUnfinishedMessage(var5);
      }
   }

   public MessageType parsePartialFrom(CodedInputStream var1) throws InvalidProtocolBufferException {
      return this.parsePartialFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parsePartialFrom(InputStream var1) throws InvalidProtocolBufferException {
      return this.parsePartialFrom(var1, EMPTY_REGISTRY);
   }

   public MessageType parsePartialFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      CodedInputStream var3 = CodedInputStream.newInstance(var1);
      MessageLite var5 = this.parsePartialFrom(var3, var2);

      try {
         var3.checkLastTagWas(0);
         return (MessageType)var5;
      } catch (InvalidProtocolBufferException var4) {
         throw var4.setUnfinishedMessage(var5);
      }
   }

   public MessageType parsePartialFrom(byte[] var1) throws InvalidProtocolBufferException {
      return this.parsePartialFrom(var1, 0, var1.length, EMPTY_REGISTRY);
   }

   public MessageType parsePartialFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException {
      return this.parsePartialFrom(var1, var2, var3, EMPTY_REGISTRY);
   }

   public MessageType parsePartialFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException {
      CodedInputStream var6 = CodedInputStream.newInstance(var1, var2, var3);
      MessageLite var7 = this.parsePartialFrom(var6, var4);

      try {
         var6.checkLastTagWas(0);
         return (MessageType)var7;
      } catch (InvalidProtocolBufferException var5) {
         throw var5.setUnfinishedMessage(var7);
      }
   }

   public MessageType parsePartialFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException {
      return this.parsePartialFrom(var1, 0, var1.length, var2);
   }
}
