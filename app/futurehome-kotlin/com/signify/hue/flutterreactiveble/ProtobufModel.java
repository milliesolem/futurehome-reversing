package com.signify.hue.flutterreactiveble;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class ProtobufModel {
   private ProtobufModel() {
   }

   public static void registerAllExtensions(ExtensionRegistryLite var0) {
   }

   public static final class BleStatusInfo
      extends GeneratedMessageLite<ProtobufModel.BleStatusInfo, ProtobufModel.BleStatusInfo.Builder>
      implements ProtobufModel.BleStatusInfoOrBuilder {
      private static final ProtobufModel.BleStatusInfo DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.BleStatusInfo> PARSER;
      public static final int STATUS_FIELD_NUMBER = 1;
      private int status_;

      static {
         ProtobufModel.BleStatusInfo var0 = new ProtobufModel.BleStatusInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.BleStatusInfo.class, var0);
      }

      private BleStatusInfo() {
      }

      private void clearStatus() {
         this.status_ = 0;
      }

      public static ProtobufModel.BleStatusInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.BleStatusInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.BleStatusInfo.Builder newBuilder(ProtobufModel.BleStatusInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.BleStatusInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.BleStatusInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.BleStatusInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.BleStatusInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setStatus(int var1) {
         this.status_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004"
         // 80: bipush 1
         // 81: anewarray 153
         // 84: dup
         // 85: bipush 0
         // 86: ldc "status_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$BleStatusInfo.<init> ()V
         // 9d: areturn
      }

      @Override
      public int getStatus() {
         return this.status_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.BleStatusInfo, ProtobufModel.BleStatusInfo.Builder>
         implements ProtobufModel.BleStatusInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.BleStatusInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.BleStatusInfo.Builder clearStatus() {
            this.copyOnWrite();
            this.instance.clearStatus();
            return this;
         }

         @Override
         public int getStatus() {
            return this.instance.getStatus();
         }

         public ProtobufModel.BleStatusInfo.Builder setStatus(int var1) {
            this.copyOnWrite();
            this.instance.setStatus(var1);
            return this;
         }
      }
   }

   public interface BleStatusInfoOrBuilder extends MessageLiteOrBuilder {
      int getStatus();
   }

   public static final class ChangeConnectionPriorityInfo
      extends GeneratedMessageLite<ProtobufModel.ChangeConnectionPriorityInfo, ProtobufModel.ChangeConnectionPriorityInfo.Builder>
      implements ProtobufModel.ChangeConnectionPriorityInfoOrBuilder {
      private static final ProtobufModel.ChangeConnectionPriorityInfo DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      public static final int FAILURE_FIELD_NUMBER = 2;
      private static volatile Parser<ProtobufModel.ChangeConnectionPriorityInfo> PARSER;
      private int bitField0_;
      private String deviceId_ = "";
      private ProtobufModel.GenericFailure failure_;

      static {
         ProtobufModel.ChangeConnectionPriorityInfo var0 = new ProtobufModel.ChangeConnectionPriorityInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ChangeConnectionPriorityInfo.class, var0);
      }

      private ChangeConnectionPriorityInfo() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      private void clearFailure() {
         this.failure_ = null;
         this.bitField0_ &= -2;
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         ProtobufModel.GenericFailure var2 = this.failure_;
         if (var2 != null && var2 != ProtobufModel.GenericFailure.getDefaultInstance()) {
            this.failure_ = ProtobufModel.GenericFailure.newBuilder(this.failure_).mergeFrom(var1).buildPartial();
         } else {
            this.failure_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo.Builder newBuilder(ProtobufModel.ChangeConnectionPriorityInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ChangeConnectionPriorityInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
      }

      private void setFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         this.failure_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 152 143 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo;
         // 7e: ldc "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002ဉ\u0000"
         // 80: bipush 3
         // 81: anewarray 91
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "deviceId_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "failure_"
         // 92: aastore
         // 93: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 96: areturn
         // 97: new com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo$Builder
         // 9a: dup
         // 9b: aconst_null
         // 9c: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9f: areturn
         // a0: new com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo
         // a3: dup
         // a4: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityInfo.<init> ()V
         // a7: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      @Override
      public ProtobufModel.GenericFailure getFailure() {
         ProtobufModel.GenericFailure var2 = this.failure_;
         ProtobufModel.GenericFailure var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.GenericFailure.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasFailure() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ChangeConnectionPriorityInfo, ProtobufModel.ChangeConnectionPriorityInfo.Builder>
         implements ProtobufModel.ChangeConnectionPriorityInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.ChangeConnectionPriorityInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ChangeConnectionPriorityInfo.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityInfo.Builder clearFailure() {
            this.copyOnWrite();
            this.instance.clearFailure();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         @Override
         public ProtobufModel.GenericFailure getFailure() {
            return this.instance.getFailure();
         }

         @Override
         public boolean hasFailure() {
            return this.instance.hasFailure();
         }

         public ProtobufModel.ChangeConnectionPriorityInfo.Builder mergeFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.mergeFailure(var1);
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityInfo.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityInfo.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityInfo.Builder setFailure(ProtobufModel.GenericFailure.Builder var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1.build());
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityInfo.Builder setFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1);
            return this;
         }
      }
   }

   public interface ChangeConnectionPriorityInfoOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();

      ProtobufModel.GenericFailure getFailure();

      boolean hasFailure();
   }

   public static final class ChangeConnectionPriorityRequest
      extends GeneratedMessageLite<ProtobufModel.ChangeConnectionPriorityRequest, ProtobufModel.ChangeConnectionPriorityRequest.Builder>
      implements ProtobufModel.ChangeConnectionPriorityRequestOrBuilder {
      private static final ProtobufModel.ChangeConnectionPriorityRequest DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.ChangeConnectionPriorityRequest> PARSER;
      public static final int PRIORITY_FIELD_NUMBER = 2;
      private String deviceId_ = "";
      private int priority_;

      static {
         ProtobufModel.ChangeConnectionPriorityRequest var0 = new ProtobufModel.ChangeConnectionPriorityRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ChangeConnectionPriorityRequest.class, var0);
      }

      private ChangeConnectionPriorityRequest() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      private void clearPriority() {
         this.priority_ = 0;
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest.Builder newBuilder(ProtobufModel.ChangeConnectionPriorityRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ChangeConnectionPriorityRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ChangeConnectionPriorityRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
      }

      private void setPriority(int var1) {
         this.priority_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest;
         // 7e: ldc "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\u0004"
         // 80: bipush 2
         // 81: anewarray 151
         // 84: dup
         // 85: bipush 0
         // 86: ldc "deviceId_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "priority_"
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ChangeConnectionPriorityRequest.<init> ()V
         // a2: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      @Override
      public int getPriority() {
         return this.priority_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ChangeConnectionPriorityRequest, ProtobufModel.ChangeConnectionPriorityRequest.Builder>
         implements ProtobufModel.ChangeConnectionPriorityRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.ChangeConnectionPriorityRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ChangeConnectionPriorityRequest.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityRequest.Builder clearPriority() {
            this.copyOnWrite();
            this.instance.clearPriority();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         @Override
         public int getPriority() {
            return this.instance.getPriority();
         }

         public ProtobufModel.ChangeConnectionPriorityRequest.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityRequest.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }

         public ProtobufModel.ChangeConnectionPriorityRequest.Builder setPriority(int var1) {
            this.copyOnWrite();
            this.instance.setPriority(var1);
            return this;
         }
      }
   }

   public interface ChangeConnectionPriorityRequestOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();

      int getPriority();
   }

   public static final class CharacteristicAddress
      extends GeneratedMessageLite<ProtobufModel.CharacteristicAddress, ProtobufModel.CharacteristicAddress.Builder>
      implements ProtobufModel.CharacteristicAddressOrBuilder {
      public static final int CHARACTERISTICINSTANCEID_FIELD_NUMBER = 5;
      public static final int CHARACTERISTICUUID_FIELD_NUMBER = 3;
      private static final ProtobufModel.CharacteristicAddress DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.CharacteristicAddress> PARSER;
      public static final int SERVICEINSTANCEID_FIELD_NUMBER = 4;
      public static final int SERVICEUUID_FIELD_NUMBER = 2;
      private int bitField0_;
      private String characteristicInstanceId_;
      private ProtobufModel.Uuid characteristicUuid_;
      private String deviceId_ = "";
      private String serviceInstanceId_ = "";
      private ProtobufModel.Uuid serviceUuid_;

      static {
         ProtobufModel.CharacteristicAddress var0 = new ProtobufModel.CharacteristicAddress();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.CharacteristicAddress.class, var0);
      }

      private CharacteristicAddress() {
         this.characteristicInstanceId_ = "";
      }

      private void clearCharacteristicInstanceId() {
         this.characteristicInstanceId_ = getDefaultInstance().getCharacteristicInstanceId();
      }

      private void clearCharacteristicUuid() {
         this.characteristicUuid_ = null;
         this.bitField0_ &= -3;
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      private void clearServiceInstanceId() {
         this.serviceInstanceId_ = getDefaultInstance().getServiceInstanceId();
      }

      private void clearServiceUuid() {
         this.serviceUuid_ = null;
         this.bitField0_ &= -2;
      }

      public static ProtobufModel.CharacteristicAddress getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristicUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         ProtobufModel.Uuid var2 = this.characteristicUuid_;
         if (var2 != null && var2 != ProtobufModel.Uuid.getDefaultInstance()) {
            this.characteristicUuid_ = ProtobufModel.Uuid.newBuilder(this.characteristicUuid_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristicUuid_ = var1;
         }

         this.bitField0_ |= 2;
      }

      private void mergeServiceUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         ProtobufModel.Uuid var2 = this.serviceUuid_;
         if (var2 != null && var2 != ProtobufModel.Uuid.getDefaultInstance()) {
            this.serviceUuid_ = ProtobufModel.Uuid.newBuilder(this.serviceUuid_).mergeFrom(var1).buildPartial();
         } else {
            this.serviceUuid_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.CharacteristicAddress.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.CharacteristicAddress.Builder newBuilder(ProtobufModel.CharacteristicAddress var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.CharacteristicAddress parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicAddress parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicAddress parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.CharacteristicAddress> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristicInstanceId(String var1) {
         var1.getClass();
         this.characteristicInstanceId_ = var1;
      }

      private void setCharacteristicInstanceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.characteristicInstanceId_ = var1.toStringUtf8();
      }

      private void setCharacteristicUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.characteristicUuid_ = var1;
         this.bitField0_ |= 2;
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
      }

      private void setServiceInstanceId(String var1) {
         var1.getClass();
         this.serviceInstanceId_ = var1;
      }

      private void setServiceInstanceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.serviceInstanceId_ = var1.toStringUtf8();
      }

      private void setServiceUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.serviceUuid_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 175 166 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress;
         // 7e: ldc_w "\u0000\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002ဉ\u0000\u0003ဉ\u0001\u0004Ȉ\u0005Ȉ"
         // 81: bipush 6
         // 83: anewarray 148
         // 86: dup
         // 87: bipush 0
         // 88: ldc_w "bitField0_"
         // 8b: aastore
         // 8c: dup
         // 8d: bipush 1
         // 8e: ldc_w "deviceId_"
         // 91: aastore
         // 92: dup
         // 93: bipush 2
         // 94: ldc_w "serviceUuid_"
         // 97: aastore
         // 98: dup
         // 99: bipush 3
         // 9a: ldc_w "characteristicUuid_"
         // 9d: aastore
         // 9e: dup
         // 9f: bipush 4
         // a0: ldc_w "serviceInstanceId_"
         // a3: aastore
         // a4: dup
         // a5: bipush 5
         // a6: ldc_w "characteristicInstanceId_"
         // a9: aastore
         // aa: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // ad: areturn
         // ae: new com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress$Builder
         // b1: dup
         // b2: aconst_null
         // b3: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // b6: areturn
         // b7: new com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress
         // ba: dup
         // bb: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicAddress.<init> ()V
         // be: areturn
      }

      @Override
      public String getCharacteristicInstanceId() {
         return this.characteristicInstanceId_;
      }

      @Override
      public ByteString getCharacteristicInstanceIdBytes() {
         return ByteString.copyFromUtf8(this.characteristicInstanceId_);
      }

      @Override
      public ProtobufModel.Uuid getCharacteristicUuid() {
         ProtobufModel.Uuid var2 = this.characteristicUuid_;
         ProtobufModel.Uuid var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.Uuid.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      @Override
      public String getServiceInstanceId() {
         return this.serviceInstanceId_;
      }

      @Override
      public ByteString getServiceInstanceIdBytes() {
         return ByteString.copyFromUtf8(this.serviceInstanceId_);
      }

      @Override
      public ProtobufModel.Uuid getServiceUuid() {
         ProtobufModel.Uuid var2 = this.serviceUuid_;
         ProtobufModel.Uuid var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.Uuid.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasCharacteristicUuid() {
         boolean var1;
         if ((this.bitField0_ & 2) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public boolean hasServiceUuid() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.CharacteristicAddress, ProtobufModel.CharacteristicAddress.Builder>
         implements ProtobufModel.CharacteristicAddressOrBuilder {
         private Builder() {
            super(ProtobufModel.CharacteristicAddress.DEFAULT_INSTANCE);
         }

         public ProtobufModel.CharacteristicAddress.Builder clearCharacteristicInstanceId() {
            this.copyOnWrite();
            this.instance.clearCharacteristicInstanceId();
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder clearCharacteristicUuid() {
            this.copyOnWrite();
            this.instance.clearCharacteristicUuid();
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder clearServiceInstanceId() {
            this.copyOnWrite();
            this.instance.clearServiceInstanceId();
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder clearServiceUuid() {
            this.copyOnWrite();
            this.instance.clearServiceUuid();
            return this;
         }

         @Override
         public String getCharacteristicInstanceId() {
            return this.instance.getCharacteristicInstanceId();
         }

         @Override
         public ByteString getCharacteristicInstanceIdBytes() {
            return this.instance.getCharacteristicInstanceIdBytes();
         }

         @Override
         public ProtobufModel.Uuid getCharacteristicUuid() {
            return this.instance.getCharacteristicUuid();
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         @Override
         public String getServiceInstanceId() {
            return this.instance.getServiceInstanceId();
         }

         @Override
         public ByteString getServiceInstanceIdBytes() {
            return this.instance.getServiceInstanceIdBytes();
         }

         @Override
         public ProtobufModel.Uuid getServiceUuid() {
            return this.instance.getServiceUuid();
         }

         @Override
         public boolean hasCharacteristicUuid() {
            return this.instance.hasCharacteristicUuid();
         }

         @Override
         public boolean hasServiceUuid() {
            return this.instance.hasServiceUuid();
         }

         public ProtobufModel.CharacteristicAddress.Builder mergeCharacteristicUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristicUuid(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder mergeServiceUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.mergeServiceUuid(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setCharacteristicInstanceId(String var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicInstanceId(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setCharacteristicInstanceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicInstanceIdBytes(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setCharacteristicUuid(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicUuid(var1.build());
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setCharacteristicUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicUuid(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setServiceInstanceId(String var1) {
            this.copyOnWrite();
            this.instance.setServiceInstanceId(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setServiceInstanceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setServiceInstanceIdBytes(var1);
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setServiceUuid(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.setServiceUuid(var1.build());
            return this;
         }

         public ProtobufModel.CharacteristicAddress.Builder setServiceUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.setServiceUuid(var1);
            return this;
         }
      }
   }

   public interface CharacteristicAddressOrBuilder extends MessageLiteOrBuilder {
      String getCharacteristicInstanceId();

      ByteString getCharacteristicInstanceIdBytes();

      ProtobufModel.Uuid getCharacteristicUuid();

      String getDeviceId();

      ByteString getDeviceIdBytes();

      String getServiceInstanceId();

      ByteString getServiceInstanceIdBytes();

      ProtobufModel.Uuid getServiceUuid();

      boolean hasCharacteristicUuid();

      boolean hasServiceUuid();
   }

   public static final class CharacteristicValueInfo
      extends GeneratedMessageLite<ProtobufModel.CharacteristicValueInfo, ProtobufModel.CharacteristicValueInfo.Builder>
      implements ProtobufModel.CharacteristicValueInfoOrBuilder {
      public static final int CHARACTERISTIC_FIELD_NUMBER = 1;
      private static final ProtobufModel.CharacteristicValueInfo DEFAULT_INSTANCE;
      public static final int FAILURE_FIELD_NUMBER = 3;
      private static volatile Parser<ProtobufModel.CharacteristicValueInfo> PARSER;
      public static final int VALUE_FIELD_NUMBER = 2;
      private int bitField0_;
      private ProtobufModel.CharacteristicAddress characteristic_;
      private ProtobufModel.GenericFailure failure_;
      private ByteString value_ = ByteString.EMPTY;

      static {
         ProtobufModel.CharacteristicValueInfo var0 = new ProtobufModel.CharacteristicValueInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.CharacteristicValueInfo.class, var0);
      }

      private CharacteristicValueInfo() {
      }

      private void clearCharacteristic() {
         this.characteristic_ = null;
         this.bitField0_ &= -2;
      }

      private void clearFailure() {
         this.failure_ = null;
         this.bitField0_ &= -3;
      }

      private void clearValue() {
         this.value_ = getDefaultInstance().getValue();
      }

      public static ProtobufModel.CharacteristicValueInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         if (var2 != null && var2 != ProtobufModel.CharacteristicAddress.getDefaultInstance()) {
            this.characteristic_ = ProtobufModel.CharacteristicAddress.newBuilder(this.characteristic_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristic_ = var1;
         }

         this.bitField0_ |= 1;
      }

      private void mergeFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         ProtobufModel.GenericFailure var2 = this.failure_;
         if (var2 != null && var2 != ProtobufModel.GenericFailure.getDefaultInstance()) {
            this.failure_ = ProtobufModel.GenericFailure.newBuilder(this.failure_).mergeFrom(var1).buildPartial();
         } else {
            this.failure_ = var1;
         }

         this.bitField0_ |= 2;
      }

      public static ProtobufModel.CharacteristicValueInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.CharacteristicValueInfo.Builder newBuilder(ProtobufModel.CharacteristicValueInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.CharacteristicValueInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicValueInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.CharacteristicValueInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.CharacteristicValueInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         this.characteristic_ = var1;
         this.bitField0_ |= 1;
      }

      private void setFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         this.failure_ = var1;
         this.bitField0_ |= 2;
      }

      private void setValue(ByteString var1) {
         var1.getClass();
         this.value_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 157 148 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo;
         // 7e: ldc "\u0000\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002\n\u0003ဉ\u0001"
         // 80: bipush 4
         // 81: anewarray 108
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "characteristic_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "value_"
         // 92: aastore
         // 93: dup
         // 94: bipush 3
         // 95: ldc "failure_"
         // 97: aastore
         // 98: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 9b: areturn
         // 9c: new com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo$Builder
         // 9f: dup
         // a0: aconst_null
         // a1: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // a4: areturn
         // a5: new com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo
         // a8: dup
         // a9: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$CharacteristicValueInfo.<init> ()V
         // ac: areturn
      }

      @Override
      public ProtobufModel.CharacteristicAddress getCharacteristic() {
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         ProtobufModel.CharacteristicAddress var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.CharacteristicAddress.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public ProtobufModel.GenericFailure getFailure() {
         ProtobufModel.GenericFailure var2 = this.failure_;
         ProtobufModel.GenericFailure var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.GenericFailure.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public ByteString getValue() {
         return this.value_;
      }

      @Override
      public boolean hasCharacteristic() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      @Override
      public boolean hasFailure() {
         boolean var1;
         if ((this.bitField0_ & 2) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.CharacteristicValueInfo, ProtobufModel.CharacteristicValueInfo.Builder>
         implements ProtobufModel.CharacteristicValueInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.CharacteristicValueInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.CharacteristicValueInfo.Builder clearCharacteristic() {
            this.copyOnWrite();
            this.instance.clearCharacteristic();
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder clearFailure() {
            this.copyOnWrite();
            this.instance.clearFailure();
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder clearValue() {
            this.copyOnWrite();
            this.instance.clearValue();
            return this;
         }

         @Override
         public ProtobufModel.CharacteristicAddress getCharacteristic() {
            return this.instance.getCharacteristic();
         }

         @Override
         public ProtobufModel.GenericFailure getFailure() {
            return this.instance.getFailure();
         }

         @Override
         public ByteString getValue() {
            return this.instance.getValue();
         }

         @Override
         public boolean hasCharacteristic() {
            return this.instance.hasCharacteristic();
         }

         @Override
         public boolean hasFailure() {
            return this.instance.hasFailure();
         }

         public ProtobufModel.CharacteristicValueInfo.Builder mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristic(var1);
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder mergeFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.mergeFailure(var1);
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder setCharacteristic(ProtobufModel.CharacteristicAddress.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1.build());
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1);
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder setFailure(ProtobufModel.GenericFailure.Builder var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1.build());
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder setFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1);
            return this;
         }

         public ProtobufModel.CharacteristicValueInfo.Builder setValue(ByteString var1) {
            this.copyOnWrite();
            this.instance.setValue(var1);
            return this;
         }
      }
   }

   public interface CharacteristicValueInfoOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.CharacteristicAddress getCharacteristic();

      ProtobufModel.GenericFailure getFailure();

      ByteString getValue();

      boolean hasCharacteristic();

      boolean hasFailure();
   }

   public static final class ClearGattCacheInfo
      extends GeneratedMessageLite<ProtobufModel.ClearGattCacheInfo, ProtobufModel.ClearGattCacheInfo.Builder>
      implements ProtobufModel.ClearGattCacheInfoOrBuilder {
      private static final ProtobufModel.ClearGattCacheInfo DEFAULT_INSTANCE;
      public static final int FAILURE_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.ClearGattCacheInfo> PARSER;
      private int bitField0_;
      private ProtobufModel.GenericFailure failure_;

      static {
         ProtobufModel.ClearGattCacheInfo var0 = new ProtobufModel.ClearGattCacheInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ClearGattCacheInfo.class, var0);
      }

      private ClearGattCacheInfo() {
      }

      private void clearFailure() {
         this.failure_ = null;
         this.bitField0_ &= -2;
      }

      public static ProtobufModel.ClearGattCacheInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         ProtobufModel.GenericFailure var2 = this.failure_;
         if (var2 != null && var2 != ProtobufModel.GenericFailure.getDefaultInstance()) {
            this.failure_ = ProtobufModel.GenericFailure.newBuilder(this.failure_).mergeFrom(var1).buildPartial();
         } else {
            this.failure_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.ClearGattCacheInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ClearGattCacheInfo.Builder newBuilder(ProtobufModel.ClearGattCacheInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ClearGattCacheInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ClearGattCacheInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         this.failure_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo;
         // 7e: ldc "\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000"
         // 80: bipush 2
         // 81: anewarray 61
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "failure_"
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheInfo.<init> ()V
         // a2: areturn
      }

      @Override
      public ProtobufModel.GenericFailure getFailure() {
         ProtobufModel.GenericFailure var2 = this.failure_;
         ProtobufModel.GenericFailure var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.GenericFailure.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasFailure() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ClearGattCacheInfo, ProtobufModel.ClearGattCacheInfo.Builder>
         implements ProtobufModel.ClearGattCacheInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.ClearGattCacheInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ClearGattCacheInfo.Builder clearFailure() {
            this.copyOnWrite();
            this.instance.clearFailure();
            return this;
         }

         @Override
         public ProtobufModel.GenericFailure getFailure() {
            return this.instance.getFailure();
         }

         @Override
         public boolean hasFailure() {
            return this.instance.hasFailure();
         }

         public ProtobufModel.ClearGattCacheInfo.Builder mergeFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.mergeFailure(var1);
            return this;
         }

         public ProtobufModel.ClearGattCacheInfo.Builder setFailure(ProtobufModel.GenericFailure.Builder var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1.build());
            return this;
         }

         public ProtobufModel.ClearGattCacheInfo.Builder setFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1);
            return this;
         }
      }
   }

   public interface ClearGattCacheInfoOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.GenericFailure getFailure();

      boolean hasFailure();
   }

   public static final class ClearGattCacheRequest
      extends GeneratedMessageLite<ProtobufModel.ClearGattCacheRequest, ProtobufModel.ClearGattCacheRequest.Builder>
      implements ProtobufModel.ClearGattCacheRequestOrBuilder {
      private static final ProtobufModel.ClearGattCacheRequest DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.ClearGattCacheRequest> PARSER;
      private String deviceId_ = "";

      static {
         ProtobufModel.ClearGattCacheRequest var0 = new ProtobufModel.ClearGattCacheRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ClearGattCacheRequest.class, var0);
      }

      private ClearGattCacheRequest() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      public static ProtobufModel.ClearGattCacheRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.ClearGattCacheRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ClearGattCacheRequest.Builder newBuilder(ProtobufModel.ClearGattCacheRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ClearGattCacheRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ClearGattCacheRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ClearGattCacheRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ"
         // 80: bipush 1
         // 81: anewarray 136
         // 84: dup
         // 85: bipush 0
         // 86: ldc "deviceId_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ClearGattCacheRequest.<init> ()V
         // 9d: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ClearGattCacheRequest, ProtobufModel.ClearGattCacheRequest.Builder>
         implements ProtobufModel.ClearGattCacheRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.ClearGattCacheRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ClearGattCacheRequest.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         public ProtobufModel.ClearGattCacheRequest.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.ClearGattCacheRequest.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }
      }
   }

   public interface ClearGattCacheRequestOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();
   }

   public static final class ConnectToDeviceRequest
      extends GeneratedMessageLite<ProtobufModel.ConnectToDeviceRequest, ProtobufModel.ConnectToDeviceRequest.Builder>
      implements ProtobufModel.ConnectToDeviceRequestOrBuilder {
      private static final ProtobufModel.ConnectToDeviceRequest DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.ConnectToDeviceRequest> PARSER;
      public static final int SERVICESWITHCHARACTERISTICSTODISCOVER_FIELD_NUMBER = 2;
      public static final int TIMEOUTINMS_FIELD_NUMBER = 3;
      private int bitField0_;
      private String deviceId_ = "";
      private ProtobufModel.ServicesWithCharacteristics servicesWithCharacteristicsToDiscover_;
      private int timeoutInMs_;

      static {
         ProtobufModel.ConnectToDeviceRequest var0 = new ProtobufModel.ConnectToDeviceRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ConnectToDeviceRequest.class, var0);
      }

      private ConnectToDeviceRequest() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      private void clearServicesWithCharacteristicsToDiscover() {
         this.servicesWithCharacteristicsToDiscover_ = null;
         this.bitField0_ &= -2;
      }

      private void clearTimeoutInMs() {
         this.timeoutInMs_ = 0;
      }

      public static ProtobufModel.ConnectToDeviceRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeServicesWithCharacteristicsToDiscover(ProtobufModel.ServicesWithCharacteristics var1) {
         var1.getClass();
         ProtobufModel.ServicesWithCharacteristics var2 = this.servicesWithCharacteristicsToDiscover_;
         if (var2 != null && var2 != ProtobufModel.ServicesWithCharacteristics.getDefaultInstance()) {
            this.servicesWithCharacteristicsToDiscover_ = ProtobufModel.ServicesWithCharacteristics.newBuilder(this.servicesWithCharacteristicsToDiscover_)
               .mergeFrom(var1)
               .buildPartial();
         } else {
            this.servicesWithCharacteristicsToDiscover_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.ConnectToDeviceRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ConnectToDeviceRequest.Builder newBuilder(ProtobufModel.ConnectToDeviceRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ConnectToDeviceRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ConnectToDeviceRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
      }

      private void setServicesWithCharacteristicsToDiscover(ProtobufModel.ServicesWithCharacteristics var1) {
         var1.getClass();
         this.servicesWithCharacteristicsToDiscover_ = var1;
         this.bitField0_ |= 1;
      }

      private void setTimeoutInMs(int var1) {
         this.timeoutInMs_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 157 148 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest;
         // 7e: ldc "\u0000\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002ဉ\u0000\u0003\u0004"
         // 80: bipush 4
         // 81: anewarray 106
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "deviceId_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "servicesWithCharacteristicsToDiscover_"
         // 92: aastore
         // 93: dup
         // 94: bipush 3
         // 95: ldc "timeoutInMs_"
         // 97: aastore
         // 98: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 9b: areturn
         // 9c: new com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest$Builder
         // 9f: dup
         // a0: aconst_null
         // a1: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // a4: areturn
         // a5: new com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest
         // a8: dup
         // a9: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ConnectToDeviceRequest.<init> ()V
         // ac: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      @Override
      public ProtobufModel.ServicesWithCharacteristics getServicesWithCharacteristicsToDiscover() {
         ProtobufModel.ServicesWithCharacteristics var2 = this.servicesWithCharacteristicsToDiscover_;
         ProtobufModel.ServicesWithCharacteristics var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.ServicesWithCharacteristics.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public int getTimeoutInMs() {
         return this.timeoutInMs_;
      }

      @Override
      public boolean hasServicesWithCharacteristicsToDiscover() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ConnectToDeviceRequest, ProtobufModel.ConnectToDeviceRequest.Builder>
         implements ProtobufModel.ConnectToDeviceRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.ConnectToDeviceRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder clearServicesWithCharacteristicsToDiscover() {
            this.copyOnWrite();
            this.instance.clearServicesWithCharacteristicsToDiscover();
            return this;
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder clearTimeoutInMs() {
            this.copyOnWrite();
            this.instance.clearTimeoutInMs();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         @Override
         public ProtobufModel.ServicesWithCharacteristics getServicesWithCharacteristicsToDiscover() {
            return this.instance.getServicesWithCharacteristicsToDiscover();
         }

         @Override
         public int getTimeoutInMs() {
            return this.instance.getTimeoutInMs();
         }

         @Override
         public boolean hasServicesWithCharacteristicsToDiscover() {
            return this.instance.hasServicesWithCharacteristicsToDiscover();
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder mergeServicesWithCharacteristicsToDiscover(ProtobufModel.ServicesWithCharacteristics var1) {
            this.copyOnWrite();
            this.instance.mergeServicesWithCharacteristicsToDiscover(var1);
            return this;
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder setServicesWithCharacteristicsToDiscover(ProtobufModel.ServicesWithCharacteristics.Builder var1) {
            this.copyOnWrite();
            this.instance.setServicesWithCharacteristicsToDiscover(var1.build());
            return this;
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder setServicesWithCharacteristicsToDiscover(ProtobufModel.ServicesWithCharacteristics var1) {
            this.copyOnWrite();
            this.instance.setServicesWithCharacteristicsToDiscover(var1);
            return this;
         }

         public ProtobufModel.ConnectToDeviceRequest.Builder setTimeoutInMs(int var1) {
            this.copyOnWrite();
            this.instance.setTimeoutInMs(var1);
            return this;
         }
      }
   }

   public interface ConnectToDeviceRequestOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();

      ProtobufModel.ServicesWithCharacteristics getServicesWithCharacteristicsToDiscover();

      int getTimeoutInMs();

      boolean hasServicesWithCharacteristicsToDiscover();
   }

   public static final class DeviceInfo
      extends GeneratedMessageLite<ProtobufModel.DeviceInfo, ProtobufModel.DeviceInfo.Builder>
      implements ProtobufModel.DeviceInfoOrBuilder {
      public static final int CONNECTIONSTATE_FIELD_NUMBER = 2;
      private static final ProtobufModel.DeviceInfo DEFAULT_INSTANCE;
      public static final int FAILURE_FIELD_NUMBER = 3;
      public static final int ID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.DeviceInfo> PARSER;
      private int bitField0_;
      private int connectionState_;
      private ProtobufModel.GenericFailure failure_;
      private String id_ = "";

      static {
         ProtobufModel.DeviceInfo var0 = new ProtobufModel.DeviceInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.DeviceInfo.class, var0);
      }

      private DeviceInfo() {
      }

      private void clearConnectionState() {
         this.connectionState_ = 0;
      }

      private void clearFailure() {
         this.failure_ = null;
         this.bitField0_ &= -2;
      }

      private void clearId() {
         this.id_ = getDefaultInstance().getId();
      }

      public static ProtobufModel.DeviceInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         ProtobufModel.GenericFailure var2 = this.failure_;
         if (var2 != null && var2 != ProtobufModel.GenericFailure.getDefaultInstance()) {
            this.failure_ = ProtobufModel.GenericFailure.newBuilder(this.failure_).mergeFrom(var1).buildPartial();
         } else {
            this.failure_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.DeviceInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.DeviceInfo.Builder newBuilder(ProtobufModel.DeviceInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.DeviceInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.DeviceInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setConnectionState(int var1) {
         this.connectionState_ = var1;
      }

      private void setFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         this.failure_ = var1;
         this.bitField0_ |= 1;
      }

      private void setId(String var1) {
         var1.getClass();
         this.id_ = var1;
      }

      private void setIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.id_ = var1.toStringUtf8();
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 157 148 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo;
         // 7e: ldc "\u0000\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002\u0004\u0003ဉ\u0000"
         // 80: bipush 4
         // 81: anewarray 106
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "id_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "connectionState_"
         // 92: aastore
         // 93: dup
         // 94: bipush 3
         // 95: ldc "failure_"
         // 97: aastore
         // 98: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 9b: areturn
         // 9c: new com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo$Builder
         // 9f: dup
         // a0: aconst_null
         // a1: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // a4: areturn
         // a5: new com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo
         // a8: dup
         // a9: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DeviceInfo.<init> ()V
         // ac: areturn
      }

      @Override
      public int getConnectionState() {
         return this.connectionState_;
      }

      @Override
      public ProtobufModel.GenericFailure getFailure() {
         ProtobufModel.GenericFailure var2 = this.failure_;
         ProtobufModel.GenericFailure var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.GenericFailure.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public String getId() {
         return this.id_;
      }

      @Override
      public ByteString getIdBytes() {
         return ByteString.copyFromUtf8(this.id_);
      }

      @Override
      public boolean hasFailure() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.DeviceInfo, ProtobufModel.DeviceInfo.Builder>
         implements ProtobufModel.DeviceInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.DeviceInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.DeviceInfo.Builder clearConnectionState() {
            this.copyOnWrite();
            this.instance.clearConnectionState();
            return this;
         }

         public ProtobufModel.DeviceInfo.Builder clearFailure() {
            this.copyOnWrite();
            this.instance.clearFailure();
            return this;
         }

         public ProtobufModel.DeviceInfo.Builder clearId() {
            this.copyOnWrite();
            this.instance.clearId();
            return this;
         }

         @Override
         public int getConnectionState() {
            return this.instance.getConnectionState();
         }

         @Override
         public ProtobufModel.GenericFailure getFailure() {
            return this.instance.getFailure();
         }

         @Override
         public String getId() {
            return this.instance.getId();
         }

         @Override
         public ByteString getIdBytes() {
            return this.instance.getIdBytes();
         }

         @Override
         public boolean hasFailure() {
            return this.instance.hasFailure();
         }

         public ProtobufModel.DeviceInfo.Builder mergeFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.mergeFailure(var1);
            return this;
         }

         public ProtobufModel.DeviceInfo.Builder setConnectionState(int var1) {
            this.copyOnWrite();
            this.instance.setConnectionState(var1);
            return this;
         }

         public ProtobufModel.DeviceInfo.Builder setFailure(ProtobufModel.GenericFailure.Builder var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1.build());
            return this;
         }

         public ProtobufModel.DeviceInfo.Builder setFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1);
            return this;
         }

         public ProtobufModel.DeviceInfo.Builder setId(String var1) {
            this.copyOnWrite();
            this.instance.setId(var1);
            return this;
         }

         public ProtobufModel.DeviceInfo.Builder setIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setIdBytes(var1);
            return this;
         }
      }
   }

   public interface DeviceInfoOrBuilder extends MessageLiteOrBuilder {
      int getConnectionState();

      ProtobufModel.GenericFailure getFailure();

      String getId();

      ByteString getIdBytes();

      boolean hasFailure();
   }

   public static final class DeviceScanInfo
      extends GeneratedMessageLite<ProtobufModel.DeviceScanInfo, ProtobufModel.DeviceScanInfo.Builder>
      implements ProtobufModel.DeviceScanInfoOrBuilder {
      private static final ProtobufModel.DeviceScanInfo DEFAULT_INSTANCE;
      public static final int FAILURE_FIELD_NUMBER = 3;
      public static final int ID_FIELD_NUMBER = 1;
      public static final int ISCONNECTABLE_FIELD_NUMBER = 8;
      public static final int MANUFACTURERDATA_FIELD_NUMBER = 6;
      public static final int NAME_FIELD_NUMBER = 2;
      private static volatile Parser<ProtobufModel.DeviceScanInfo> PARSER;
      public static final int RSSI_FIELD_NUMBER = 5;
      public static final int SERVICEDATA_FIELD_NUMBER = 4;
      public static final int SERVICEUUIDS_FIELD_NUMBER = 7;
      private int bitField0_;
      private ProtobufModel.GenericFailure failure_;
      private String id_ = "";
      private ProtobufModel.IsConnectable isConnectable_;
      private ByteString manufacturerData_;
      private String name_ = "";
      private int rssi_;
      private Internal.ProtobufList<ProtobufModel.ServiceDataEntry> serviceData_ = emptyProtobufList();
      private Internal.ProtobufList<ProtobufModel.Uuid> serviceUuids_;

      static {
         ProtobufModel.DeviceScanInfo var0 = new ProtobufModel.DeviceScanInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.DeviceScanInfo.class, var0);
      }

      private DeviceScanInfo() {
         this.manufacturerData_ = ByteString.EMPTY;
         this.serviceUuids_ = emptyProtobufList();
      }

      private void addAllServiceData(Iterable<? extends ProtobufModel.ServiceDataEntry> var1) {
         this.ensureServiceDataIsMutable();
         AbstractMessageLite.addAll(var1, this.serviceData_);
      }

      private void addAllServiceUuids(Iterable<? extends ProtobufModel.Uuid> var1) {
         this.ensureServiceUuidsIsMutable();
         AbstractMessageLite.addAll(var1, this.serviceUuids_);
      }

      private void addServiceData(int var1, ProtobufModel.ServiceDataEntry var2) {
         var2.getClass();
         this.ensureServiceDataIsMutable();
         this.serviceData_.add(var1, var2);
      }

      private void addServiceData(ProtobufModel.ServiceDataEntry var1) {
         var1.getClass();
         this.ensureServiceDataIsMutable();
         this.serviceData_.add(var1);
      }

      private void addServiceUuids(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.add(var1, var2);
      }

      private void addServiceUuids(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.add(var1);
      }

      private void clearFailure() {
         this.failure_ = null;
         this.bitField0_ &= -2;
      }

      private void clearId() {
         this.id_ = getDefaultInstance().getId();
      }

      private void clearIsConnectable() {
         this.isConnectable_ = null;
         this.bitField0_ &= -3;
      }

      private void clearManufacturerData() {
         this.manufacturerData_ = getDefaultInstance().getManufacturerData();
      }

      private void clearName() {
         this.name_ = getDefaultInstance().getName();
      }

      private void clearRssi() {
         this.rssi_ = 0;
      }

      private void clearServiceData() {
         this.serviceData_ = emptyProtobufList();
      }

      private void clearServiceUuids() {
         this.serviceUuids_ = emptyProtobufList();
      }

      private void ensureServiceDataIsMutable() {
         Internal.ProtobufList var1 = this.serviceData_;
         if (!var1.isModifiable()) {
            this.serviceData_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      private void ensureServiceUuidsIsMutable() {
         Internal.ProtobufList var1 = this.serviceUuids_;
         if (!var1.isModifiable()) {
            this.serviceUuids_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      public static ProtobufModel.DeviceScanInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         ProtobufModel.GenericFailure var2 = this.failure_;
         if (var2 != null && var2 != ProtobufModel.GenericFailure.getDefaultInstance()) {
            this.failure_ = ProtobufModel.GenericFailure.newBuilder(this.failure_).mergeFrom(var1).buildPartial();
         } else {
            this.failure_ = var1;
         }

         this.bitField0_ |= 1;
      }

      private void mergeIsConnectable(ProtobufModel.IsConnectable var1) {
         var1.getClass();
         ProtobufModel.IsConnectable var2 = this.isConnectable_;
         if (var2 != null && var2 != ProtobufModel.IsConnectable.getDefaultInstance()) {
            this.isConnectable_ = ProtobufModel.IsConnectable.newBuilder(this.isConnectable_).mergeFrom(var1).buildPartial();
         } else {
            this.isConnectable_ = var1;
         }

         this.bitField0_ |= 2;
      }

      public static ProtobufModel.DeviceScanInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.DeviceScanInfo.Builder newBuilder(ProtobufModel.DeviceScanInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.DeviceScanInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceScanInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DeviceScanInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.DeviceScanInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void removeServiceData(int var1) {
         this.ensureServiceDataIsMutable();
         this.serviceData_.remove(var1);
      }

      private void removeServiceUuids(int var1) {
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.remove(var1);
      }

      private void setFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         this.failure_ = var1;
         this.bitField0_ |= 1;
      }

      private void setId(String var1) {
         var1.getClass();
         this.id_ = var1;
      }

      private void setIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.id_ = var1.toStringUtf8();
      }

      private void setIsConnectable(ProtobufModel.IsConnectable var1) {
         var1.getClass();
         this.isConnectable_ = var1;
         this.bitField0_ |= 2;
      }

      private void setManufacturerData(ByteString var1) {
         var1.getClass();
         this.manufacturerData_ = var1;
      }

      private void setName(String var1) {
         var1.getClass();
         this.name_ = var1;
      }

      private void setNameBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.name_ = var1.toStringUtf8();
      }

      private void setRssi(int var1) {
         this.rssi_ = var1;
      }

      private void setServiceData(int var1, ProtobufModel.ServiceDataEntry var2) {
         var2.getClass();
         this.ensureServiceDataIsMutable();
         this.serviceData_.set(var1, var2);
      }

      private void setServiceUuids(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.set(var1, var2);
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo;
         // 7e: ldc_w "\u0000\b\u0000\u0001\u0001\b\b\u0000\u0002\u0000\u0001Ȉ\u0002Ȉ\u0003ဉ\u0000\u0004\u001b\u0005\u0004\u0006\n\u0007\u001b\bဉ\u0001"
         // 81: bipush 11
         // 83: anewarray 235
         // 86: dup
         // 87: bipush 0
         // 88: ldc_w "bitField0_"
         // 8b: aastore
         // 8c: dup
         // 8d: bipush 1
         // 8e: ldc_w "id_"
         // 91: aastore
         // 92: dup
         // 93: bipush 2
         // 94: ldc_w "name_"
         // 97: aastore
         // 98: dup
         // 99: bipush 3
         // 9a: ldc_w "failure_"
         // 9d: aastore
         // 9e: dup
         // 9f: bipush 4
         // a0: ldc_w "serviceData_"
         // a3: aastore
         // a4: dup
         // a5: bipush 5
         // a6: ldc_w com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry
         // a9: aastore
         // aa: dup
         // ab: bipush 6
         // ad: ldc_w "rssi_"
         // b0: aastore
         // b1: dup
         // b2: bipush 7
         // b4: ldc_w "manufacturerData_"
         // b7: aastore
         // b8: dup
         // b9: bipush 8
         // bb: ldc_w "serviceUuids_"
         // be: aastore
         // bf: dup
         // c0: bipush 9
         // c2: ldc_w com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // c5: aastore
         // c6: dup
         // c7: bipush 10
         // c9: ldc_w "isConnectable_"
         // cc: aastore
         // cd: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // d0: areturn
         // d1: new com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo$Builder
         // d4: dup
         // d5: aconst_null
         // d6: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // d9: areturn
         // da: new com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo
         // dd: dup
         // de: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DeviceScanInfo.<init> ()V
         // e1: areturn
      }

      @Override
      public ProtobufModel.GenericFailure getFailure() {
         ProtobufModel.GenericFailure var2 = this.failure_;
         ProtobufModel.GenericFailure var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.GenericFailure.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public String getId() {
         return this.id_;
      }

      @Override
      public ByteString getIdBytes() {
         return ByteString.copyFromUtf8(this.id_);
      }

      @Override
      public ProtobufModel.IsConnectable getIsConnectable() {
         ProtobufModel.IsConnectable var2 = this.isConnectable_;
         ProtobufModel.IsConnectable var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.IsConnectable.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public ByteString getManufacturerData() {
         return this.manufacturerData_;
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
      public int getRssi() {
         return this.rssi_;
      }

      @Override
      public ProtobufModel.ServiceDataEntry getServiceData(int var1) {
         return this.serviceData_.get(var1);
      }

      @Override
      public int getServiceDataCount() {
         return this.serviceData_.size();
      }

      @Override
      public List<ProtobufModel.ServiceDataEntry> getServiceDataList() {
         return this.serviceData_;
      }

      public ProtobufModel.ServiceDataEntryOrBuilder getServiceDataOrBuilder(int var1) {
         return this.serviceData_.get(var1);
      }

      public List<? extends ProtobufModel.ServiceDataEntryOrBuilder> getServiceDataOrBuilderList() {
         return this.serviceData_;
      }

      @Override
      public ProtobufModel.Uuid getServiceUuids(int var1) {
         return this.serviceUuids_.get(var1);
      }

      @Override
      public int getServiceUuidsCount() {
         return this.serviceUuids_.size();
      }

      @Override
      public List<ProtobufModel.Uuid> getServiceUuidsList() {
         return this.serviceUuids_;
      }

      public ProtobufModel.UuidOrBuilder getServiceUuidsOrBuilder(int var1) {
         return this.serviceUuids_.get(var1);
      }

      public List<? extends ProtobufModel.UuidOrBuilder> getServiceUuidsOrBuilderList() {
         return this.serviceUuids_;
      }

      @Override
      public boolean hasFailure() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      @Override
      public boolean hasIsConnectable() {
         boolean var1;
         if ((this.bitField0_ & 2) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.DeviceScanInfo, ProtobufModel.DeviceScanInfo.Builder>
         implements ProtobufModel.DeviceScanInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.DeviceScanInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.DeviceScanInfo.Builder addAllServiceData(Iterable<? extends ProtobufModel.ServiceDataEntry> var1) {
            this.copyOnWrite();
            this.instance.addAllServiceData(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addAllServiceUuids(Iterable<? extends ProtobufModel.Uuid> var1) {
            this.copyOnWrite();
            this.instance.addAllServiceUuids(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceData(int var1, ProtobufModel.ServiceDataEntry.Builder var2) {
            this.copyOnWrite();
            this.instance.addServiceData(var1, var2.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceData(int var1, ProtobufModel.ServiceDataEntry var2) {
            this.copyOnWrite();
            this.instance.addServiceData(var1, var2);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceData(ProtobufModel.ServiceDataEntry.Builder var1) {
            this.copyOnWrite();
            this.instance.addServiceData(var1.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceData(ProtobufModel.ServiceDataEntry var1) {
            this.copyOnWrite();
            this.instance.addServiceData(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceUuids(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1, var2.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceUuids(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1, var2);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceUuids(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder addServiceUuids(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearFailure() {
            this.copyOnWrite();
            this.instance.clearFailure();
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearId() {
            this.copyOnWrite();
            this.instance.clearId();
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearIsConnectable() {
            this.copyOnWrite();
            this.instance.clearIsConnectable();
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearManufacturerData() {
            this.copyOnWrite();
            this.instance.clearManufacturerData();
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearName() {
            this.copyOnWrite();
            this.instance.clearName();
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearRssi() {
            this.copyOnWrite();
            this.instance.clearRssi();
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearServiceData() {
            this.copyOnWrite();
            this.instance.clearServiceData();
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder clearServiceUuids() {
            this.copyOnWrite();
            this.instance.clearServiceUuids();
            return this;
         }

         @Override
         public ProtobufModel.GenericFailure getFailure() {
            return this.instance.getFailure();
         }

         @Override
         public String getId() {
            return this.instance.getId();
         }

         @Override
         public ByteString getIdBytes() {
            return this.instance.getIdBytes();
         }

         @Override
         public ProtobufModel.IsConnectable getIsConnectable() {
            return this.instance.getIsConnectable();
         }

         @Override
         public ByteString getManufacturerData() {
            return this.instance.getManufacturerData();
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
         public int getRssi() {
            return this.instance.getRssi();
         }

         @Override
         public ProtobufModel.ServiceDataEntry getServiceData(int var1) {
            return this.instance.getServiceData(var1);
         }

         @Override
         public int getServiceDataCount() {
            return this.instance.getServiceDataCount();
         }

         @Override
         public List<ProtobufModel.ServiceDataEntry> getServiceDataList() {
            return Collections.unmodifiableList(this.instance.getServiceDataList());
         }

         @Override
         public ProtobufModel.Uuid getServiceUuids(int var1) {
            return this.instance.getServiceUuids(var1);
         }

         @Override
         public int getServiceUuidsCount() {
            return this.instance.getServiceUuidsCount();
         }

         @Override
         public List<ProtobufModel.Uuid> getServiceUuidsList() {
            return Collections.unmodifiableList(this.instance.getServiceUuidsList());
         }

         @Override
         public boolean hasFailure() {
            return this.instance.hasFailure();
         }

         @Override
         public boolean hasIsConnectable() {
            return this.instance.hasIsConnectable();
         }

         public ProtobufModel.DeviceScanInfo.Builder mergeFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.mergeFailure(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder mergeIsConnectable(ProtobufModel.IsConnectable var1) {
            this.copyOnWrite();
            this.instance.mergeIsConnectable(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder removeServiceData(int var1) {
            this.copyOnWrite();
            this.instance.removeServiceData(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder removeServiceUuids(int var1) {
            this.copyOnWrite();
            this.instance.removeServiceUuids(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setFailure(ProtobufModel.GenericFailure.Builder var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setId(String var1) {
            this.copyOnWrite();
            this.instance.setId(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setIdBytes(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setIsConnectable(ProtobufModel.IsConnectable.Builder var1) {
            this.copyOnWrite();
            this.instance.setIsConnectable(var1.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setIsConnectable(ProtobufModel.IsConnectable var1) {
            this.copyOnWrite();
            this.instance.setIsConnectable(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setManufacturerData(ByteString var1) {
            this.copyOnWrite();
            this.instance.setManufacturerData(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setName(String var1) {
            this.copyOnWrite();
            this.instance.setName(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setNameBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setNameBytes(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setRssi(int var1) {
            this.copyOnWrite();
            this.instance.setRssi(var1);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setServiceData(int var1, ProtobufModel.ServiceDataEntry.Builder var2) {
            this.copyOnWrite();
            this.instance.setServiceData(var1, var2.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setServiceData(int var1, ProtobufModel.ServiceDataEntry var2) {
            this.copyOnWrite();
            this.instance.setServiceData(var1, var2);
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setServiceUuids(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.setServiceUuids(var1, var2.build());
            return this;
         }

         public ProtobufModel.DeviceScanInfo.Builder setServiceUuids(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.setServiceUuids(var1, var2);
            return this;
         }
      }
   }

   public interface DeviceScanInfoOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.GenericFailure getFailure();

      String getId();

      ByteString getIdBytes();

      ProtobufModel.IsConnectable getIsConnectable();

      ByteString getManufacturerData();

      String getName();

      ByteString getNameBytes();

      int getRssi();

      ProtobufModel.ServiceDataEntry getServiceData(int var1);

      int getServiceDataCount();

      List<ProtobufModel.ServiceDataEntry> getServiceDataList();

      ProtobufModel.Uuid getServiceUuids(int var1);

      int getServiceUuidsCount();

      List<ProtobufModel.Uuid> getServiceUuidsList();

      boolean hasFailure();

      boolean hasIsConnectable();
   }

   public static final class DisconnectFromDeviceRequest
      extends GeneratedMessageLite<ProtobufModel.DisconnectFromDeviceRequest, ProtobufModel.DisconnectFromDeviceRequest.Builder>
      implements ProtobufModel.DisconnectFromDeviceRequestOrBuilder {
      private static final ProtobufModel.DisconnectFromDeviceRequest DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.DisconnectFromDeviceRequest> PARSER;
      private String deviceId_ = "";

      static {
         ProtobufModel.DisconnectFromDeviceRequest var0 = new ProtobufModel.DisconnectFromDeviceRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.DisconnectFromDeviceRequest.class, var0);
      }

      private DisconnectFromDeviceRequest() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      public static ProtobufModel.DisconnectFromDeviceRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.DisconnectFromDeviceRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.DisconnectFromDeviceRequest.Builder newBuilder(ProtobufModel.DisconnectFromDeviceRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DisconnectFromDeviceRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.DisconnectFromDeviceRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ"
         // 80: bipush 1
         // 81: anewarray 136
         // 84: dup
         // 85: bipush 0
         // 86: ldc "deviceId_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DisconnectFromDeviceRequest.<init> ()V
         // 9d: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.DisconnectFromDeviceRequest, ProtobufModel.DisconnectFromDeviceRequest.Builder>
         implements ProtobufModel.DisconnectFromDeviceRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.DisconnectFromDeviceRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.DisconnectFromDeviceRequest.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         public ProtobufModel.DisconnectFromDeviceRequest.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.DisconnectFromDeviceRequest.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }
      }
   }

   public interface DisconnectFromDeviceRequestOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();
   }

   public static final class DiscoverServicesInfo
      extends GeneratedMessageLite<ProtobufModel.DiscoverServicesInfo, ProtobufModel.DiscoverServicesInfo.Builder>
      implements ProtobufModel.DiscoverServicesInfoOrBuilder {
      private static final ProtobufModel.DiscoverServicesInfo DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.DiscoverServicesInfo> PARSER;
      public static final int SERVICES_FIELD_NUMBER = 2;
      private String deviceId_ = "";
      private Internal.ProtobufList<ProtobufModel.DiscoveredService> services_ = emptyProtobufList();

      static {
         ProtobufModel.DiscoverServicesInfo var0 = new ProtobufModel.DiscoverServicesInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.DiscoverServicesInfo.class, var0);
      }

      private DiscoverServicesInfo() {
      }

      private void addAllServices(Iterable<? extends ProtobufModel.DiscoveredService> var1) {
         this.ensureServicesIsMutable();
         AbstractMessageLite.addAll(var1, this.services_);
      }

      private void addServices(int var1, ProtobufModel.DiscoveredService var2) {
         var2.getClass();
         this.ensureServicesIsMutable();
         this.services_.add(var1, var2);
      }

      private void addServices(ProtobufModel.DiscoveredService var1) {
         var1.getClass();
         this.ensureServicesIsMutable();
         this.services_.add(var1);
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      private void clearServices() {
         this.services_ = emptyProtobufList();
      }

      private void ensureServicesIsMutable() {
         Internal.ProtobufList var1 = this.services_;
         if (!var1.isModifiable()) {
            this.services_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      public static ProtobufModel.DiscoverServicesInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.DiscoverServicesInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.DiscoverServicesInfo.Builder newBuilder(ProtobufModel.DiscoverServicesInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.DiscoverServicesInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.DiscoverServicesInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void removeServices(int var1) {
         this.ensureServicesIsMutable();
         this.services_.remove(var1);
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
      }

      private void setServices(int var1, ProtobufModel.DiscoveredService var2) {
         var2.getClass();
         this.ensureServicesIsMutable();
         this.services_.set(var1, var2);
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 156 147 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo;
         // 7e: ldc_w "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001Ȉ\u0002\u001b"
         // 81: bipush 3
         // 82: anewarray 114
         // 85: dup
         // 86: bipush 0
         // 87: ldc_w "deviceId_"
         // 8a: aastore
         // 8b: dup
         // 8c: bipush 1
         // 8d: ldc_w "services_"
         // 90: aastore
         // 91: dup
         // 92: bipush 2
         // 93: ldc_w com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService
         // 96: aastore
         // 97: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo$Builder
         // 9e: dup
         // 9f: aconst_null
         // a0: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // a3: areturn
         // a4: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo
         // a7: dup
         // a8: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesInfo.<init> ()V
         // ab: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      @Override
      public ProtobufModel.DiscoveredService getServices(int var1) {
         return this.services_.get(var1);
      }

      @Override
      public int getServicesCount() {
         return this.services_.size();
      }

      @Override
      public List<ProtobufModel.DiscoveredService> getServicesList() {
         return this.services_;
      }

      public ProtobufModel.DiscoveredServiceOrBuilder getServicesOrBuilder(int var1) {
         return this.services_.get(var1);
      }

      public List<? extends ProtobufModel.DiscoveredServiceOrBuilder> getServicesOrBuilderList() {
         return this.services_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.DiscoverServicesInfo, ProtobufModel.DiscoverServicesInfo.Builder>
         implements ProtobufModel.DiscoverServicesInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.DiscoverServicesInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.DiscoverServicesInfo.Builder addAllServices(Iterable<? extends ProtobufModel.DiscoveredService> var1) {
            this.copyOnWrite();
            this.instance.addAllServices(var1);
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder addServices(int var1, ProtobufModel.DiscoveredService.Builder var2) {
            this.copyOnWrite();
            this.instance.addServices(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder addServices(int var1, ProtobufModel.DiscoveredService var2) {
            this.copyOnWrite();
            this.instance.addServices(var1, var2);
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder addServices(ProtobufModel.DiscoveredService.Builder var1) {
            this.copyOnWrite();
            this.instance.addServices(var1.build());
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder addServices(ProtobufModel.DiscoveredService var1) {
            this.copyOnWrite();
            this.instance.addServices(var1);
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder clearServices() {
            this.copyOnWrite();
            this.instance.clearServices();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         @Override
         public ProtobufModel.DiscoveredService getServices(int var1) {
            return this.instance.getServices(var1);
         }

         @Override
         public int getServicesCount() {
            return this.instance.getServicesCount();
         }

         @Override
         public List<ProtobufModel.DiscoveredService> getServicesList() {
            return Collections.unmodifiableList(this.instance.getServicesList());
         }

         public ProtobufModel.DiscoverServicesInfo.Builder removeServices(int var1) {
            this.copyOnWrite();
            this.instance.removeServices(var1);
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder setServices(int var1, ProtobufModel.DiscoveredService.Builder var2) {
            this.copyOnWrite();
            this.instance.setServices(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoverServicesInfo.Builder setServices(int var1, ProtobufModel.DiscoveredService var2) {
            this.copyOnWrite();
            this.instance.setServices(var1, var2);
            return this;
         }
      }
   }

   public interface DiscoverServicesInfoOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();

      ProtobufModel.DiscoveredService getServices(int var1);

      int getServicesCount();

      List<ProtobufModel.DiscoveredService> getServicesList();
   }

   public static final class DiscoverServicesRequest
      extends GeneratedMessageLite<ProtobufModel.DiscoverServicesRequest, ProtobufModel.DiscoverServicesRequest.Builder>
      implements ProtobufModel.DiscoverServicesRequestOrBuilder {
      private static final ProtobufModel.DiscoverServicesRequest DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.DiscoverServicesRequest> PARSER;
      private String deviceId_ = "";

      static {
         ProtobufModel.DiscoverServicesRequest var0 = new ProtobufModel.DiscoverServicesRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.DiscoverServicesRequest.class, var0);
      }

      private DiscoverServicesRequest() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      public static ProtobufModel.DiscoverServicesRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.DiscoverServicesRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.DiscoverServicesRequest.Builder newBuilder(ProtobufModel.DiscoverServicesRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.DiscoverServicesRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoverServicesRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.DiscoverServicesRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ"
         // 80: bipush 1
         // 81: anewarray 136
         // 84: dup
         // 85: bipush 0
         // 86: ldc "deviceId_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoverServicesRequest.<init> ()V
         // 9d: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.DiscoverServicesRequest, ProtobufModel.DiscoverServicesRequest.Builder>
         implements ProtobufModel.DiscoverServicesRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.DiscoverServicesRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.DiscoverServicesRequest.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         public ProtobufModel.DiscoverServicesRequest.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.DiscoverServicesRequest.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }
      }
   }

   public interface DiscoverServicesRequestOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();
   }

   public static final class DiscoveredCharacteristic
      extends GeneratedMessageLite<ProtobufModel.DiscoveredCharacteristic, ProtobufModel.DiscoveredCharacteristic.Builder>
      implements ProtobufModel.DiscoveredCharacteristicOrBuilder {
      public static final int CHARACTERISTICID_FIELD_NUMBER = 1;
      public static final int CHARACTERISTICINSTANCEID_FIELD_NUMBER = 8;
      private static final ProtobufModel.DiscoveredCharacteristic DEFAULT_INSTANCE;
      public static final int ISINDICATABLE_FIELD_NUMBER = 7;
      public static final int ISNOTIFIABLE_FIELD_NUMBER = 6;
      public static final int ISREADABLE_FIELD_NUMBER = 3;
      public static final int ISWRITABLEWITHOUTRESPONSE_FIELD_NUMBER = 5;
      public static final int ISWRITABLEWITHRESPONSE_FIELD_NUMBER = 4;
      private static volatile Parser<ProtobufModel.DiscoveredCharacteristic> PARSER;
      public static final int SERVICEID_FIELD_NUMBER = 2;
      private int bitField0_;
      private ProtobufModel.Uuid characteristicId_;
      private String characteristicInstanceId_ = "";
      private boolean isIndicatable_;
      private boolean isNotifiable_;
      private boolean isReadable_;
      private boolean isWritableWithResponse_;
      private boolean isWritableWithoutResponse_;
      private ProtobufModel.Uuid serviceId_;

      static {
         ProtobufModel.DiscoveredCharacteristic var0 = new ProtobufModel.DiscoveredCharacteristic();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.DiscoveredCharacteristic.class, var0);
      }

      private DiscoveredCharacteristic() {
      }

      private void clearCharacteristicId() {
         this.characteristicId_ = null;
         this.bitField0_ &= -2;
      }

      private void clearCharacteristicInstanceId() {
         this.characteristicInstanceId_ = getDefaultInstance().getCharacteristicInstanceId();
      }

      private void clearIsIndicatable() {
         this.isIndicatable_ = false;
      }

      private void clearIsNotifiable() {
         this.isNotifiable_ = false;
      }

      private void clearIsReadable() {
         this.isReadable_ = false;
      }

      private void clearIsWritableWithResponse() {
         this.isWritableWithResponse_ = false;
      }

      private void clearIsWritableWithoutResponse() {
         this.isWritableWithoutResponse_ = false;
      }

      private void clearServiceId() {
         this.serviceId_ = null;
         this.bitField0_ &= -3;
      }

      public static ProtobufModel.DiscoveredCharacteristic getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristicId(ProtobufModel.Uuid var1) {
         var1.getClass();
         ProtobufModel.Uuid var2 = this.characteristicId_;
         if (var2 != null && var2 != ProtobufModel.Uuid.getDefaultInstance()) {
            this.characteristicId_ = ProtobufModel.Uuid.newBuilder(this.characteristicId_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristicId_ = var1;
         }

         this.bitField0_ |= 1;
      }

      private void mergeServiceId(ProtobufModel.Uuid var1) {
         var1.getClass();
         ProtobufModel.Uuid var2 = this.serviceId_;
         if (var2 != null && var2 != ProtobufModel.Uuid.getDefaultInstance()) {
            this.serviceId_ = ProtobufModel.Uuid.newBuilder(this.serviceId_).mergeFrom(var1).buildPartial();
         } else {
            this.serviceId_ = var1;
         }

         this.bitField0_ |= 2;
      }

      public static ProtobufModel.DiscoveredCharacteristic.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.DiscoveredCharacteristic.Builder newBuilder(ProtobufModel.DiscoveredCharacteristic var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredCharacteristic parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.DiscoveredCharacteristic> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristicId(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.characteristicId_ = var1;
         this.bitField0_ |= 1;
      }

      private void setCharacteristicInstanceId(String var1) {
         var1.getClass();
         this.characteristicInstanceId_ = var1;
      }

      private void setCharacteristicInstanceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.characteristicInstanceId_ = var1.toStringUtf8();
      }

      private void setIsIndicatable(boolean var1) {
         this.isIndicatable_ = var1;
      }

      private void setIsNotifiable(boolean var1) {
         this.isNotifiable_ = var1;
      }

      private void setIsReadable(boolean var1) {
         this.isReadable_ = var1;
      }

      private void setIsWritableWithResponse(boolean var1) {
         this.isWritableWithResponse_ = var1;
      }

      private void setIsWritableWithoutResponse(boolean var1) {
         this.isWritableWithoutResponse_ = var1;
      }

      private void setServiceId(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.serviceId_ = var1;
         this.bitField0_ |= 2;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 196 187 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic;
         // 7e: ldc_w "\u0000\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u0007\u0004\u0007\u0005\u0007\u0006\u0007\u0007\u0007\bȈ"
         // 81: bipush 9
         // 83: anewarray 176
         // 86: dup
         // 87: bipush 0
         // 88: ldc_w "bitField0_"
         // 8b: aastore
         // 8c: dup
         // 8d: bipush 1
         // 8e: ldc_w "characteristicId_"
         // 91: aastore
         // 92: dup
         // 93: bipush 2
         // 94: ldc_w "serviceId_"
         // 97: aastore
         // 98: dup
         // 99: bipush 3
         // 9a: ldc_w "isReadable_"
         // 9d: aastore
         // 9e: dup
         // 9f: bipush 4
         // a0: ldc_w "isWritableWithResponse_"
         // a3: aastore
         // a4: dup
         // a5: bipush 5
         // a6: ldc_w "isWritableWithoutResponse_"
         // a9: aastore
         // aa: dup
         // ab: bipush 6
         // ad: ldc_w "isNotifiable_"
         // b0: aastore
         // b1: dup
         // b2: bipush 7
         // b4: ldc_w "isIndicatable_"
         // b7: aastore
         // b8: dup
         // b9: bipush 8
         // bb: ldc_w "characteristicInstanceId_"
         // be: aastore
         // bf: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // c2: areturn
         // c3: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic$Builder
         // c6: dup
         // c7: aconst_null
         // c8: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // cb: areturn
         // cc: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic
         // cf: dup
         // d0: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic.<init> ()V
         // d3: areturn
      }

      @Override
      public ProtobufModel.Uuid getCharacteristicId() {
         ProtobufModel.Uuid var2 = this.characteristicId_;
         ProtobufModel.Uuid var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.Uuid.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public String getCharacteristicInstanceId() {
         return this.characteristicInstanceId_;
      }

      @Override
      public ByteString getCharacteristicInstanceIdBytes() {
         return ByteString.copyFromUtf8(this.characteristicInstanceId_);
      }

      @Override
      public boolean getIsIndicatable() {
         return this.isIndicatable_;
      }

      @Override
      public boolean getIsNotifiable() {
         return this.isNotifiable_;
      }

      @Override
      public boolean getIsReadable() {
         return this.isReadable_;
      }

      @Override
      public boolean getIsWritableWithResponse() {
         return this.isWritableWithResponse_;
      }

      @Override
      public boolean getIsWritableWithoutResponse() {
         return this.isWritableWithoutResponse_;
      }

      @Override
      public ProtobufModel.Uuid getServiceId() {
         ProtobufModel.Uuid var2 = this.serviceId_;
         ProtobufModel.Uuid var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.Uuid.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasCharacteristicId() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      @Override
      public boolean hasServiceId() {
         boolean var1;
         if ((this.bitField0_ & 2) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.DiscoveredCharacteristic, ProtobufModel.DiscoveredCharacteristic.Builder>
         implements ProtobufModel.DiscoveredCharacteristicOrBuilder {
         private Builder() {
            super(ProtobufModel.DiscoveredCharacteristic.DEFAULT_INSTANCE);
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearCharacteristicId() {
            this.copyOnWrite();
            this.instance.clearCharacteristicId();
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearCharacteristicInstanceId() {
            this.copyOnWrite();
            this.instance.clearCharacteristicInstanceId();
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearIsIndicatable() {
            this.copyOnWrite();
            this.instance.clearIsIndicatable();
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearIsNotifiable() {
            this.copyOnWrite();
            this.instance.clearIsNotifiable();
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearIsReadable() {
            this.copyOnWrite();
            this.instance.clearIsReadable();
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearIsWritableWithResponse() {
            this.copyOnWrite();
            this.instance.clearIsWritableWithResponse();
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearIsWritableWithoutResponse() {
            this.copyOnWrite();
            this.instance.clearIsWritableWithoutResponse();
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder clearServiceId() {
            this.copyOnWrite();
            this.instance.clearServiceId();
            return this;
         }

         @Override
         public ProtobufModel.Uuid getCharacteristicId() {
            return this.instance.getCharacteristicId();
         }

         @Override
         public String getCharacteristicInstanceId() {
            return this.instance.getCharacteristicInstanceId();
         }

         @Override
         public ByteString getCharacteristicInstanceIdBytes() {
            return this.instance.getCharacteristicInstanceIdBytes();
         }

         @Override
         public boolean getIsIndicatable() {
            return this.instance.getIsIndicatable();
         }

         @Override
         public boolean getIsNotifiable() {
            return this.instance.getIsNotifiable();
         }

         @Override
         public boolean getIsReadable() {
            return this.instance.getIsReadable();
         }

         @Override
         public boolean getIsWritableWithResponse() {
            return this.instance.getIsWritableWithResponse();
         }

         @Override
         public boolean getIsWritableWithoutResponse() {
            return this.instance.getIsWritableWithoutResponse();
         }

         @Override
         public ProtobufModel.Uuid getServiceId() {
            return this.instance.getServiceId();
         }

         @Override
         public boolean hasCharacteristicId() {
            return this.instance.hasCharacteristicId();
         }

         @Override
         public boolean hasServiceId() {
            return this.instance.hasServiceId();
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder mergeCharacteristicId(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristicId(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder mergeServiceId(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.mergeServiceId(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setCharacteristicId(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicId(var1.build());
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setCharacteristicId(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicId(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setCharacteristicInstanceId(String var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicInstanceId(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setCharacteristicInstanceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setCharacteristicInstanceIdBytes(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setIsIndicatable(boolean var1) {
            this.copyOnWrite();
            this.instance.setIsIndicatable(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setIsNotifiable(boolean var1) {
            this.copyOnWrite();
            this.instance.setIsNotifiable(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setIsReadable(boolean var1) {
            this.copyOnWrite();
            this.instance.setIsReadable(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setIsWritableWithResponse(boolean var1) {
            this.copyOnWrite();
            this.instance.setIsWritableWithResponse(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setIsWritableWithoutResponse(boolean var1) {
            this.copyOnWrite();
            this.instance.setIsWritableWithoutResponse(var1);
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setServiceId(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.setServiceId(var1.build());
            return this;
         }

         public ProtobufModel.DiscoveredCharacteristic.Builder setServiceId(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.setServiceId(var1);
            return this;
         }
      }
   }

   public interface DiscoveredCharacteristicOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.Uuid getCharacteristicId();

      String getCharacteristicInstanceId();

      ByteString getCharacteristicInstanceIdBytes();

      boolean getIsIndicatable();

      boolean getIsNotifiable();

      boolean getIsReadable();

      boolean getIsWritableWithResponse();

      boolean getIsWritableWithoutResponse();

      ProtobufModel.Uuid getServiceId();

      boolean hasCharacteristicId();

      boolean hasServiceId();
   }

   public static final class DiscoveredService
      extends GeneratedMessageLite<ProtobufModel.DiscoveredService, ProtobufModel.DiscoveredService.Builder>
      implements ProtobufModel.DiscoveredServiceOrBuilder {
      public static final int CHARACTERISTICS_FIELD_NUMBER = 4;
      public static final int CHARACTERISTICUUIDS_FIELD_NUMBER = 2;
      private static final ProtobufModel.DiscoveredService DEFAULT_INSTANCE;
      public static final int INCLUDEDSERVICES_FIELD_NUMBER = 3;
      private static volatile Parser<ProtobufModel.DiscoveredService> PARSER;
      public static final int SERVICEINSTANCEID_FIELD_NUMBER = 5;
      public static final int SERVICEUUID_FIELD_NUMBER = 1;
      private int bitField0_;
      private Internal.ProtobufList<ProtobufModel.Uuid> characteristicUuids_ = emptyProtobufList();
      private Internal.ProtobufList<ProtobufModel.DiscoveredCharacteristic> characteristics_;
      private Internal.ProtobufList<ProtobufModel.DiscoveredService> includedServices_ = emptyProtobufList();
      private String serviceInstanceId_;
      private ProtobufModel.Uuid serviceUuid_;

      static {
         ProtobufModel.DiscoveredService var0 = new ProtobufModel.DiscoveredService();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.DiscoveredService.class, var0);
      }

      private DiscoveredService() {
         this.characteristics_ = emptyProtobufList();
         this.serviceInstanceId_ = "";
      }

      private void addAllCharacteristicUuids(Iterable<? extends ProtobufModel.Uuid> var1) {
         this.ensureCharacteristicUuidsIsMutable();
         AbstractMessageLite.addAll(var1, this.characteristicUuids_);
      }

      private void addAllCharacteristics(Iterable<? extends ProtobufModel.DiscoveredCharacteristic> var1) {
         this.ensureCharacteristicsIsMutable();
         AbstractMessageLite.addAll(var1, this.characteristics_);
      }

      private void addAllIncludedServices(Iterable<? extends ProtobufModel.DiscoveredService> var1) {
         this.ensureIncludedServicesIsMutable();
         AbstractMessageLite.addAll(var1, this.includedServices_);
      }

      private void addCharacteristicUuids(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureCharacteristicUuidsIsMutable();
         this.characteristicUuids_.add(var1, var2);
      }

      private void addCharacteristicUuids(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.ensureCharacteristicUuidsIsMutable();
         this.characteristicUuids_.add(var1);
      }

      private void addCharacteristics(int var1, ProtobufModel.DiscoveredCharacteristic var2) {
         var2.getClass();
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.add(var1, var2);
      }

      private void addCharacteristics(ProtobufModel.DiscoveredCharacteristic var1) {
         var1.getClass();
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.add(var1);
      }

      private void addIncludedServices(int var1, ProtobufModel.DiscoveredService var2) {
         var2.getClass();
         this.ensureIncludedServicesIsMutable();
         this.includedServices_.add(var1, var2);
      }

      private void addIncludedServices(ProtobufModel.DiscoveredService var1) {
         var1.getClass();
         this.ensureIncludedServicesIsMutable();
         this.includedServices_.add(var1);
      }

      private void clearCharacteristicUuids() {
         this.characteristicUuids_ = emptyProtobufList();
      }

      private void clearCharacteristics() {
         this.characteristics_ = emptyProtobufList();
      }

      private void clearIncludedServices() {
         this.includedServices_ = emptyProtobufList();
      }

      private void clearServiceInstanceId() {
         this.serviceInstanceId_ = getDefaultInstance().getServiceInstanceId();
      }

      private void clearServiceUuid() {
         this.serviceUuid_ = null;
         this.bitField0_ &= -2;
      }

      private void ensureCharacteristicUuidsIsMutable() {
         Internal.ProtobufList var1 = this.characteristicUuids_;
         if (!var1.isModifiable()) {
            this.characteristicUuids_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      private void ensureCharacteristicsIsMutable() {
         Internal.ProtobufList var1 = this.characteristics_;
         if (!var1.isModifiable()) {
            this.characteristics_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      private void ensureIncludedServicesIsMutable() {
         Internal.ProtobufList var1 = this.includedServices_;
         if (!var1.isModifiable()) {
            this.includedServices_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      public static ProtobufModel.DiscoveredService getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeServiceUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         ProtobufModel.Uuid var2 = this.serviceUuid_;
         if (var2 != null && var2 != ProtobufModel.Uuid.getDefaultInstance()) {
            this.serviceUuid_ = ProtobufModel.Uuid.newBuilder(this.serviceUuid_).mergeFrom(var1).buildPartial();
         } else {
            this.serviceUuid_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.DiscoveredService.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.DiscoveredService.Builder newBuilder(ProtobufModel.DiscoveredService var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.DiscoveredService parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredService parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredService parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredService parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredService parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredService parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredService parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredService parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredService parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredService parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.DiscoveredService parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.DiscoveredService parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.DiscoveredService> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void removeCharacteristicUuids(int var1) {
         this.ensureCharacteristicUuidsIsMutable();
         this.characteristicUuids_.remove(var1);
      }

      private void removeCharacteristics(int var1) {
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.remove(var1);
      }

      private void removeIncludedServices(int var1) {
         this.ensureIncludedServicesIsMutable();
         this.includedServices_.remove(var1);
      }

      private void setCharacteristicUuids(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureCharacteristicUuidsIsMutable();
         this.characteristicUuids_.set(var1, var2);
      }

      private void setCharacteristics(int var1, ProtobufModel.DiscoveredCharacteristic var2) {
         var2.getClass();
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.set(var1, var2);
      }

      private void setIncludedServices(int var1, ProtobufModel.DiscoveredService var2) {
         var2.getClass();
         this.ensureIncludedServicesIsMutable();
         this.includedServices_.set(var1, var2);
      }

      private void setServiceInstanceId(String var1) {
         var1.getClass();
         this.serviceInstanceId_ = var1;
      }

      private void setServiceInstanceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.serviceInstanceId_ = var1.toStringUtf8();
      }

      private void setServiceUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.serviceUuid_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 194 185 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService;
         // 7e: ldc_w "\u0000\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0003\u0000\u0001ဉ\u0000\u0002\u001b\u0003\u001b\u0004\u001b\u0005Ȉ"
         // 81: bipush 9
         // 83: anewarray 204
         // 86: dup
         // 87: bipush 0
         // 88: ldc_w "bitField0_"
         // 8b: aastore
         // 8c: dup
         // 8d: bipush 1
         // 8e: ldc_w "serviceUuid_"
         // 91: aastore
         // 92: dup
         // 93: bipush 2
         // 94: ldc_w "characteristicUuids_"
         // 97: aastore
         // 98: dup
         // 99: bipush 3
         // 9a: ldc com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // 9c: aastore
         // 9d: dup
         // 9e: bipush 4
         // 9f: ldc_w "includedServices_"
         // a2: aastore
         // a3: dup
         // a4: bipush 5
         // a5: ldc com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService
         // a7: aastore
         // a8: dup
         // a9: bipush 6
         // ab: ldc_w "characteristics_"
         // ae: aastore
         // af: dup
         // b0: bipush 7
         // b2: ldc_w com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredCharacteristic
         // b5: aastore
         // b6: dup
         // b7: bipush 8
         // b9: ldc_w "serviceInstanceId_"
         // bc: aastore
         // bd: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // c0: areturn
         // c1: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService$Builder
         // c4: dup
         // c5: aconst_null
         // c6: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // c9: areturn
         // ca: new com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService
         // cd: dup
         // ce: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$DiscoveredService.<init> ()V
         // d1: areturn
      }

      @Override
      public ProtobufModel.Uuid getCharacteristicUuids(int var1) {
         return this.characteristicUuids_.get(var1);
      }

      @Override
      public int getCharacteristicUuidsCount() {
         return this.characteristicUuids_.size();
      }

      @Override
      public List<ProtobufModel.Uuid> getCharacteristicUuidsList() {
         return this.characteristicUuids_;
      }

      public ProtobufModel.UuidOrBuilder getCharacteristicUuidsOrBuilder(int var1) {
         return this.characteristicUuids_.get(var1);
      }

      public List<? extends ProtobufModel.UuidOrBuilder> getCharacteristicUuidsOrBuilderList() {
         return this.characteristicUuids_;
      }

      @Override
      public ProtobufModel.DiscoveredCharacteristic getCharacteristics(int var1) {
         return this.characteristics_.get(var1);
      }

      @Override
      public int getCharacteristicsCount() {
         return this.characteristics_.size();
      }

      @Override
      public List<ProtobufModel.DiscoveredCharacteristic> getCharacteristicsList() {
         return this.characteristics_;
      }

      public ProtobufModel.DiscoveredCharacteristicOrBuilder getCharacteristicsOrBuilder(int var1) {
         return this.characteristics_.get(var1);
      }

      public List<? extends ProtobufModel.DiscoveredCharacteristicOrBuilder> getCharacteristicsOrBuilderList() {
         return this.characteristics_;
      }

      @Override
      public ProtobufModel.DiscoveredService getIncludedServices(int var1) {
         return this.includedServices_.get(var1);
      }

      @Override
      public int getIncludedServicesCount() {
         return this.includedServices_.size();
      }

      @Override
      public List<ProtobufModel.DiscoveredService> getIncludedServicesList() {
         return this.includedServices_;
      }

      public ProtobufModel.DiscoveredServiceOrBuilder getIncludedServicesOrBuilder(int var1) {
         return this.includedServices_.get(var1);
      }

      public List<? extends ProtobufModel.DiscoveredServiceOrBuilder> getIncludedServicesOrBuilderList() {
         return this.includedServices_;
      }

      @Override
      public String getServiceInstanceId() {
         return this.serviceInstanceId_;
      }

      @Override
      public ByteString getServiceInstanceIdBytes() {
         return ByteString.copyFromUtf8(this.serviceInstanceId_);
      }

      @Override
      public ProtobufModel.Uuid getServiceUuid() {
         ProtobufModel.Uuid var2 = this.serviceUuid_;
         ProtobufModel.Uuid var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.Uuid.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasServiceUuid() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.DiscoveredService, ProtobufModel.DiscoveredService.Builder>
         implements ProtobufModel.DiscoveredServiceOrBuilder {
         private Builder() {
            super(ProtobufModel.DiscoveredService.DEFAULT_INSTANCE);
         }

         public ProtobufModel.DiscoveredService.Builder addAllCharacteristicUuids(Iterable<? extends ProtobufModel.Uuid> var1) {
            this.copyOnWrite();
            this.instance.addAllCharacteristicUuids(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addAllCharacteristics(Iterable<? extends ProtobufModel.DiscoveredCharacteristic> var1) {
            this.copyOnWrite();
            this.instance.addAllCharacteristics(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addAllIncludedServices(Iterable<? extends ProtobufModel.DiscoveredService> var1) {
            this.copyOnWrite();
            this.instance.addAllIncludedServices(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristicUuids(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.addCharacteristicUuids(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristicUuids(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.addCharacteristicUuids(var1, var2);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristicUuids(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.addCharacteristicUuids(var1.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristicUuids(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.addCharacteristicUuids(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristics(int var1, ProtobufModel.DiscoveredCharacteristic.Builder var2) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristics(int var1, ProtobufModel.DiscoveredCharacteristic var2) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1, var2);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristics(ProtobufModel.DiscoveredCharacteristic.Builder var1) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addCharacteristics(ProtobufModel.DiscoveredCharacteristic var1) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addIncludedServices(int var1, ProtobufModel.DiscoveredService.Builder var2) {
            this.copyOnWrite();
            this.instance.addIncludedServices(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addIncludedServices(int var1, ProtobufModel.DiscoveredService var2) {
            this.copyOnWrite();
            this.instance.addIncludedServices(var1, var2);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addIncludedServices(ProtobufModel.DiscoveredService.Builder var1) {
            this.copyOnWrite();
            this.instance.addIncludedServices(var1.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder addIncludedServices(ProtobufModel.DiscoveredService var1) {
            this.copyOnWrite();
            this.instance.addIncludedServices(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder clearCharacteristicUuids() {
            this.copyOnWrite();
            this.instance.clearCharacteristicUuids();
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder clearCharacteristics() {
            this.copyOnWrite();
            this.instance.clearCharacteristics();
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder clearIncludedServices() {
            this.copyOnWrite();
            this.instance.clearIncludedServices();
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder clearServiceInstanceId() {
            this.copyOnWrite();
            this.instance.clearServiceInstanceId();
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder clearServiceUuid() {
            this.copyOnWrite();
            this.instance.clearServiceUuid();
            return this;
         }

         @Override
         public ProtobufModel.Uuid getCharacteristicUuids(int var1) {
            return this.instance.getCharacteristicUuids(var1);
         }

         @Override
         public int getCharacteristicUuidsCount() {
            return this.instance.getCharacteristicUuidsCount();
         }

         @Override
         public List<ProtobufModel.Uuid> getCharacteristicUuidsList() {
            return Collections.unmodifiableList(this.instance.getCharacteristicUuidsList());
         }

         @Override
         public ProtobufModel.DiscoveredCharacteristic getCharacteristics(int var1) {
            return this.instance.getCharacteristics(var1);
         }

         @Override
         public int getCharacteristicsCount() {
            return this.instance.getCharacteristicsCount();
         }

         @Override
         public List<ProtobufModel.DiscoveredCharacteristic> getCharacteristicsList() {
            return Collections.unmodifiableList(this.instance.getCharacteristicsList());
         }

         @Override
         public ProtobufModel.DiscoveredService getIncludedServices(int var1) {
            return this.instance.getIncludedServices(var1);
         }

         @Override
         public int getIncludedServicesCount() {
            return this.instance.getIncludedServicesCount();
         }

         @Override
         public List<ProtobufModel.DiscoveredService> getIncludedServicesList() {
            return Collections.unmodifiableList(this.instance.getIncludedServicesList());
         }

         @Override
         public String getServiceInstanceId() {
            return this.instance.getServiceInstanceId();
         }

         @Override
         public ByteString getServiceInstanceIdBytes() {
            return this.instance.getServiceInstanceIdBytes();
         }

         @Override
         public ProtobufModel.Uuid getServiceUuid() {
            return this.instance.getServiceUuid();
         }

         @Override
         public boolean hasServiceUuid() {
            return this.instance.hasServiceUuid();
         }

         public ProtobufModel.DiscoveredService.Builder mergeServiceUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.mergeServiceUuid(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder removeCharacteristicUuids(int var1) {
            this.copyOnWrite();
            this.instance.removeCharacteristicUuids(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder removeCharacteristics(int var1) {
            this.copyOnWrite();
            this.instance.removeCharacteristics(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder removeIncludedServices(int var1) {
            this.copyOnWrite();
            this.instance.removeIncludedServices(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setCharacteristicUuids(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.setCharacteristicUuids(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setCharacteristicUuids(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.setCharacteristicUuids(var1, var2);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setCharacteristics(int var1, ProtobufModel.DiscoveredCharacteristic.Builder var2) {
            this.copyOnWrite();
            this.instance.setCharacteristics(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setCharacteristics(int var1, ProtobufModel.DiscoveredCharacteristic var2) {
            this.copyOnWrite();
            this.instance.setCharacteristics(var1, var2);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setIncludedServices(int var1, ProtobufModel.DiscoveredService.Builder var2) {
            this.copyOnWrite();
            this.instance.setIncludedServices(var1, var2.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setIncludedServices(int var1, ProtobufModel.DiscoveredService var2) {
            this.copyOnWrite();
            this.instance.setIncludedServices(var1, var2);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setServiceInstanceId(String var1) {
            this.copyOnWrite();
            this.instance.setServiceInstanceId(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setServiceInstanceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setServiceInstanceIdBytes(var1);
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setServiceUuid(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.setServiceUuid(var1.build());
            return this;
         }

         public ProtobufModel.DiscoveredService.Builder setServiceUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.setServiceUuid(var1);
            return this;
         }
      }
   }

   public interface DiscoveredServiceOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.Uuid getCharacteristicUuids(int var1);

      int getCharacteristicUuidsCount();

      List<ProtobufModel.Uuid> getCharacteristicUuidsList();

      ProtobufModel.DiscoveredCharacteristic getCharacteristics(int var1);

      int getCharacteristicsCount();

      List<ProtobufModel.DiscoveredCharacteristic> getCharacteristicsList();

      ProtobufModel.DiscoveredService getIncludedServices(int var1);

      int getIncludedServicesCount();

      List<ProtobufModel.DiscoveredService> getIncludedServicesList();

      String getServiceInstanceId();

      ByteString getServiceInstanceIdBytes();

      ProtobufModel.Uuid getServiceUuid();

      boolean hasServiceUuid();
   }

   public static final class GenericFailure
      extends GeneratedMessageLite<ProtobufModel.GenericFailure, ProtobufModel.GenericFailure.Builder>
      implements ProtobufModel.GenericFailureOrBuilder {
      public static final int CODE_FIELD_NUMBER = 1;
      private static final ProtobufModel.GenericFailure DEFAULT_INSTANCE;
      public static final int MESSAGE_FIELD_NUMBER = 2;
      private static volatile Parser<ProtobufModel.GenericFailure> PARSER;
      private int code_;
      private String message_ = "";

      static {
         ProtobufModel.GenericFailure var0 = new ProtobufModel.GenericFailure();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.GenericFailure.class, var0);
      }

      private GenericFailure() {
      }

      private void clearCode() {
         this.code_ = 0;
      }

      private void clearMessage() {
         this.message_ = getDefaultInstance().getMessage();
      }

      public static ProtobufModel.GenericFailure getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.GenericFailure.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.GenericFailure.Builder newBuilder(ProtobufModel.GenericFailure var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.GenericFailure parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.GenericFailure parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.GenericFailure parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.GenericFailure parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.GenericFailure parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.GenericFailure parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.GenericFailure parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.GenericFailure parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.GenericFailure parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.GenericFailure parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.GenericFailure parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.GenericFailure parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.GenericFailure> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCode(int var1) {
         this.code_ = var1;
      }

      private void setMessage(String var1) {
         var1.getClass();
         this.message_ = var1;
      }

      private void setMessageBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.message_ = var1.toStringUtf8();
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure;
         // 7e: ldc "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ"
         // 80: bipush 2
         // 81: anewarray 151
         // 84: dup
         // 85: bipush 0
         // 86: ldc "code_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "message_"
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$GenericFailure.<init> ()V
         // a2: areturn
      }

      @Override
      public int getCode() {
         return this.code_;
      }

      @Override
      public String getMessage() {
         return this.message_;
      }

      @Override
      public ByteString getMessageBytes() {
         return ByteString.copyFromUtf8(this.message_);
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.GenericFailure, ProtobufModel.GenericFailure.Builder>
         implements ProtobufModel.GenericFailureOrBuilder {
         private Builder() {
            super(ProtobufModel.GenericFailure.DEFAULT_INSTANCE);
         }

         public ProtobufModel.GenericFailure.Builder clearCode() {
            this.copyOnWrite();
            this.instance.clearCode();
            return this;
         }

         public ProtobufModel.GenericFailure.Builder clearMessage() {
            this.copyOnWrite();
            this.instance.clearMessage();
            return this;
         }

         @Override
         public int getCode() {
            return this.instance.getCode();
         }

         @Override
         public String getMessage() {
            return this.instance.getMessage();
         }

         @Override
         public ByteString getMessageBytes() {
            return this.instance.getMessageBytes();
         }

         public ProtobufModel.GenericFailure.Builder setCode(int var1) {
            this.copyOnWrite();
            this.instance.setCode(var1);
            return this;
         }

         public ProtobufModel.GenericFailure.Builder setMessage(String var1) {
            this.copyOnWrite();
            this.instance.setMessage(var1);
            return this;
         }

         public ProtobufModel.GenericFailure.Builder setMessageBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setMessageBytes(var1);
            return this;
         }
      }
   }

   public interface GenericFailureOrBuilder extends MessageLiteOrBuilder {
      int getCode();

      String getMessage();

      ByteString getMessageBytes();
   }

   public static final class IsConnectable
      extends GeneratedMessageLite<ProtobufModel.IsConnectable, ProtobufModel.IsConnectable.Builder>
      implements ProtobufModel.IsConnectableOrBuilder {
      public static final int CODE_FIELD_NUMBER = 1;
      private static final ProtobufModel.IsConnectable DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.IsConnectable> PARSER;
      private int code_;

      static {
         ProtobufModel.IsConnectable var0 = new ProtobufModel.IsConnectable();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.IsConnectable.class, var0);
      }

      private IsConnectable() {
      }

      private void clearCode() {
         this.code_ = 0;
      }

      public static ProtobufModel.IsConnectable getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.IsConnectable.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.IsConnectable.Builder newBuilder(ProtobufModel.IsConnectable var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.IsConnectable parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.IsConnectable parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.IsConnectable parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.IsConnectable parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.IsConnectable parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.IsConnectable parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.IsConnectable parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.IsConnectable parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.IsConnectable parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.IsConnectable parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.IsConnectable parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.IsConnectable parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.IsConnectable> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCode(int var1) {
         this.code_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004"
         // 80: bipush 1
         // 81: anewarray 153
         // 84: dup
         // 85: bipush 0
         // 86: ldc "code_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$IsConnectable.<init> ()V
         // 9d: areturn
      }

      @Override
      public int getCode() {
         return this.code_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.IsConnectable, ProtobufModel.IsConnectable.Builder>
         implements ProtobufModel.IsConnectableOrBuilder {
         private Builder() {
            super(ProtobufModel.IsConnectable.DEFAULT_INSTANCE);
         }

         public ProtobufModel.IsConnectable.Builder clearCode() {
            this.copyOnWrite();
            this.instance.clearCode();
            return this;
         }

         @Override
         public int getCode() {
            return this.instance.getCode();
         }

         public ProtobufModel.IsConnectable.Builder setCode(int var1) {
            this.copyOnWrite();
            this.instance.setCode(var1);
            return this;
         }
      }
   }

   public interface IsConnectableOrBuilder extends MessageLiteOrBuilder {
      int getCode();
   }

   public static final class NegotiateMtuInfo
      extends GeneratedMessageLite<ProtobufModel.NegotiateMtuInfo, ProtobufModel.NegotiateMtuInfo.Builder>
      implements ProtobufModel.NegotiateMtuInfoOrBuilder {
      private static final ProtobufModel.NegotiateMtuInfo DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      public static final int FAILURE_FIELD_NUMBER = 3;
      public static final int MTUSIZE_FIELD_NUMBER = 2;
      private static volatile Parser<ProtobufModel.NegotiateMtuInfo> PARSER;
      private int bitField0_;
      private String deviceId_ = "";
      private ProtobufModel.GenericFailure failure_;
      private int mtuSize_;

      static {
         ProtobufModel.NegotiateMtuInfo var0 = new ProtobufModel.NegotiateMtuInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.NegotiateMtuInfo.class, var0);
      }

      private NegotiateMtuInfo() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      private void clearFailure() {
         this.failure_ = null;
         this.bitField0_ &= -2;
      }

      private void clearMtuSize() {
         this.mtuSize_ = 0;
      }

      public static ProtobufModel.NegotiateMtuInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         ProtobufModel.GenericFailure var2 = this.failure_;
         if (var2 != null && var2 != ProtobufModel.GenericFailure.getDefaultInstance()) {
            this.failure_ = ProtobufModel.GenericFailure.newBuilder(this.failure_).mergeFrom(var1).buildPartial();
         } else {
            this.failure_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.NegotiateMtuInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.NegotiateMtuInfo.Builder newBuilder(ProtobufModel.NegotiateMtuInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.NegotiateMtuInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.NegotiateMtuInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
      }

      private void setFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         this.failure_ = var1;
         this.bitField0_ |= 1;
      }

      private void setMtuSize(int var1) {
         this.mtuSize_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 157 148 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo;
         // 7e: ldc "\u0000\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002\u0004\u0003ဉ\u0000"
         // 80: bipush 4
         // 81: anewarray 106
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "deviceId_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "mtuSize_"
         // 92: aastore
         // 93: dup
         // 94: bipush 3
         // 95: ldc "failure_"
         // 97: aastore
         // 98: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 9b: areturn
         // 9c: new com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo$Builder
         // 9f: dup
         // a0: aconst_null
         // a1: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // a4: areturn
         // a5: new com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo
         // a8: dup
         // a9: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuInfo.<init> ()V
         // ac: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      @Override
      public ProtobufModel.GenericFailure getFailure() {
         ProtobufModel.GenericFailure var2 = this.failure_;
         ProtobufModel.GenericFailure var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.GenericFailure.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public int getMtuSize() {
         return this.mtuSize_;
      }

      @Override
      public boolean hasFailure() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.NegotiateMtuInfo, ProtobufModel.NegotiateMtuInfo.Builder>
         implements ProtobufModel.NegotiateMtuInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.NegotiateMtuInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.NegotiateMtuInfo.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         public ProtobufModel.NegotiateMtuInfo.Builder clearFailure() {
            this.copyOnWrite();
            this.instance.clearFailure();
            return this;
         }

         public ProtobufModel.NegotiateMtuInfo.Builder clearMtuSize() {
            this.copyOnWrite();
            this.instance.clearMtuSize();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         @Override
         public ProtobufModel.GenericFailure getFailure() {
            return this.instance.getFailure();
         }

         @Override
         public int getMtuSize() {
            return this.instance.getMtuSize();
         }

         @Override
         public boolean hasFailure() {
            return this.instance.hasFailure();
         }

         public ProtobufModel.NegotiateMtuInfo.Builder mergeFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.mergeFailure(var1);
            return this;
         }

         public ProtobufModel.NegotiateMtuInfo.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.NegotiateMtuInfo.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }

         public ProtobufModel.NegotiateMtuInfo.Builder setFailure(ProtobufModel.GenericFailure.Builder var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1.build());
            return this;
         }

         public ProtobufModel.NegotiateMtuInfo.Builder setFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1);
            return this;
         }

         public ProtobufModel.NegotiateMtuInfo.Builder setMtuSize(int var1) {
            this.copyOnWrite();
            this.instance.setMtuSize(var1);
            return this;
         }
      }
   }

   public interface NegotiateMtuInfoOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();

      ProtobufModel.GenericFailure getFailure();

      int getMtuSize();

      boolean hasFailure();
   }

   public static final class NegotiateMtuRequest
      extends GeneratedMessageLite<ProtobufModel.NegotiateMtuRequest, ProtobufModel.NegotiateMtuRequest.Builder>
      implements ProtobufModel.NegotiateMtuRequestOrBuilder {
      private static final ProtobufModel.NegotiateMtuRequest DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      public static final int MTUSIZE_FIELD_NUMBER = 2;
      private static volatile Parser<ProtobufModel.NegotiateMtuRequest> PARSER;
      private String deviceId_ = "";
      private int mtuSize_;

      static {
         ProtobufModel.NegotiateMtuRequest var0 = new ProtobufModel.NegotiateMtuRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.NegotiateMtuRequest.class, var0);
      }

      private NegotiateMtuRequest() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      private void clearMtuSize() {
         this.mtuSize_ = 0;
      }

      public static ProtobufModel.NegotiateMtuRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.NegotiateMtuRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.NegotiateMtuRequest.Builder newBuilder(ProtobufModel.NegotiateMtuRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.NegotiateMtuRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NegotiateMtuRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.NegotiateMtuRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
      }

      private void setMtuSize(int var1) {
         this.mtuSize_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest;
         // 7e: ldc "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\u0004"
         // 80: bipush 2
         // 81: anewarray 151
         // 84: dup
         // 85: bipush 0
         // 86: ldc "deviceId_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "mtuSize_"
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NegotiateMtuRequest.<init> ()V
         // a2: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      @Override
      public int getMtuSize() {
         return this.mtuSize_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.NegotiateMtuRequest, ProtobufModel.NegotiateMtuRequest.Builder>
         implements ProtobufModel.NegotiateMtuRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.NegotiateMtuRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.NegotiateMtuRequest.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         public ProtobufModel.NegotiateMtuRequest.Builder clearMtuSize() {
            this.copyOnWrite();
            this.instance.clearMtuSize();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         @Override
         public int getMtuSize() {
            return this.instance.getMtuSize();
         }

         public ProtobufModel.NegotiateMtuRequest.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.NegotiateMtuRequest.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }

         public ProtobufModel.NegotiateMtuRequest.Builder setMtuSize(int var1) {
            this.copyOnWrite();
            this.instance.setMtuSize(var1);
            return this;
         }
      }
   }

   public interface NegotiateMtuRequestOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();

      int getMtuSize();
   }

   public static final class NotifyCharacteristicRequest
      extends GeneratedMessageLite<ProtobufModel.NotifyCharacteristicRequest, ProtobufModel.NotifyCharacteristicRequest.Builder>
      implements ProtobufModel.NotifyCharacteristicRequestOrBuilder {
      public static final int CHARACTERISTIC_FIELD_NUMBER = 1;
      private static final ProtobufModel.NotifyCharacteristicRequest DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.NotifyCharacteristicRequest> PARSER;
      private int bitField0_;
      private ProtobufModel.CharacteristicAddress characteristic_;

      static {
         ProtobufModel.NotifyCharacteristicRequest var0 = new ProtobufModel.NotifyCharacteristicRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.NotifyCharacteristicRequest.class, var0);
      }

      private NotifyCharacteristicRequest() {
      }

      private void clearCharacteristic() {
         this.characteristic_ = null;
         this.bitField0_ &= -2;
      }

      public static ProtobufModel.NotifyCharacteristicRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         if (var2 != null && var2 != ProtobufModel.CharacteristicAddress.getDefaultInstance()) {
            this.characteristic_ = ProtobufModel.CharacteristicAddress.newBuilder(this.characteristic_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristic_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.NotifyCharacteristicRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.NotifyCharacteristicRequest.Builder newBuilder(ProtobufModel.NotifyCharacteristicRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyCharacteristicRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.NotifyCharacteristicRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         this.characteristic_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest;
         // 7e: ldc "\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000"
         // 80: bipush 2
         // 81: anewarray 61
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "characteristic_"
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NotifyCharacteristicRequest.<init> ()V
         // a2: areturn
      }

      @Override
      public ProtobufModel.CharacteristicAddress getCharacteristic() {
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         ProtobufModel.CharacteristicAddress var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.CharacteristicAddress.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasCharacteristic() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.NotifyCharacteristicRequest, ProtobufModel.NotifyCharacteristicRequest.Builder>
         implements ProtobufModel.NotifyCharacteristicRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.NotifyCharacteristicRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.NotifyCharacteristicRequest.Builder clearCharacteristic() {
            this.copyOnWrite();
            this.instance.clearCharacteristic();
            return this;
         }

         @Override
         public ProtobufModel.CharacteristicAddress getCharacteristic() {
            return this.instance.getCharacteristic();
         }

         @Override
         public boolean hasCharacteristic() {
            return this.instance.hasCharacteristic();
         }

         public ProtobufModel.NotifyCharacteristicRequest.Builder mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristic(var1);
            return this;
         }

         public ProtobufModel.NotifyCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1.build());
            return this;
         }

         public ProtobufModel.NotifyCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1);
            return this;
         }
      }
   }

   public interface NotifyCharacteristicRequestOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.CharacteristicAddress getCharacteristic();

      boolean hasCharacteristic();
   }

   public static final class NotifyNoMoreCharacteristicRequest
      extends GeneratedMessageLite<ProtobufModel.NotifyNoMoreCharacteristicRequest, ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder>
      implements ProtobufModel.NotifyNoMoreCharacteristicRequestOrBuilder {
      public static final int CHARACTERISTIC_FIELD_NUMBER = 1;
      private static final ProtobufModel.NotifyNoMoreCharacteristicRequest DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.NotifyNoMoreCharacteristicRequest> PARSER;
      private int bitField0_;
      private ProtobufModel.CharacteristicAddress characteristic_;

      static {
         ProtobufModel.NotifyNoMoreCharacteristicRequest var0 = new ProtobufModel.NotifyNoMoreCharacteristicRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.NotifyNoMoreCharacteristicRequest.class, var0);
      }

      private NotifyNoMoreCharacteristicRequest() {
      }

      private void clearCharacteristic() {
         this.characteristic_ = null;
         this.bitField0_ &= -2;
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         if (var2 != null && var2 != ProtobufModel.CharacteristicAddress.getDefaultInstance()) {
            this.characteristic_ = ProtobufModel.CharacteristicAddress.newBuilder(this.characteristic_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristic_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder newBuilder(ProtobufModel.NotifyNoMoreCharacteristicRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.NotifyNoMoreCharacteristicRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.NotifyNoMoreCharacteristicRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         this.characteristic_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest;
         // 7e: ldc "\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000"
         // 80: bipush 2
         // 81: anewarray 61
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "characteristic_"
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$NotifyNoMoreCharacteristicRequest.<init> ()V
         // a2: areturn
      }

      @Override
      public ProtobufModel.CharacteristicAddress getCharacteristic() {
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         ProtobufModel.CharacteristicAddress var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.CharacteristicAddress.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasCharacteristic() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.NotifyNoMoreCharacteristicRequest, ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder>
         implements ProtobufModel.NotifyNoMoreCharacteristicRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.NotifyNoMoreCharacteristicRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder clearCharacteristic() {
            this.copyOnWrite();
            this.instance.clearCharacteristic();
            return this;
         }

         @Override
         public ProtobufModel.CharacteristicAddress getCharacteristic() {
            return this.instance.getCharacteristic();
         }

         @Override
         public boolean hasCharacteristic() {
            return this.instance.hasCharacteristic();
         }

         public ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristic(var1);
            return this;
         }

         public ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1.build());
            return this;
         }

         public ProtobufModel.NotifyNoMoreCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1);
            return this;
         }
      }
   }

   public interface NotifyNoMoreCharacteristicRequestOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.CharacteristicAddress getCharacteristic();

      boolean hasCharacteristic();
   }

   public static final class ReadCharacteristicRequest
      extends GeneratedMessageLite<ProtobufModel.ReadCharacteristicRequest, ProtobufModel.ReadCharacteristicRequest.Builder>
      implements ProtobufModel.ReadCharacteristicRequestOrBuilder {
      public static final int CHARACTERISTIC_FIELD_NUMBER = 1;
      private static final ProtobufModel.ReadCharacteristicRequest DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.ReadCharacteristicRequest> PARSER;
      private int bitField0_;
      private ProtobufModel.CharacteristicAddress characteristic_;

      static {
         ProtobufModel.ReadCharacteristicRequest var0 = new ProtobufModel.ReadCharacteristicRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ReadCharacteristicRequest.class, var0);
      }

      private ReadCharacteristicRequest() {
      }

      private void clearCharacteristic() {
         this.characteristic_ = null;
         this.bitField0_ &= -2;
      }

      public static ProtobufModel.ReadCharacteristicRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         if (var2 != null && var2 != ProtobufModel.CharacteristicAddress.getDefaultInstance()) {
            this.characteristic_ = ProtobufModel.CharacteristicAddress.newBuilder(this.characteristic_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristic_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.ReadCharacteristicRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ReadCharacteristicRequest.Builder newBuilder(ProtobufModel.ReadCharacteristicRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadCharacteristicRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ReadCharacteristicRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         this.characteristic_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest;
         // 7e: ldc "\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000"
         // 80: bipush 2
         // 81: anewarray 61
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "characteristic_"
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ReadCharacteristicRequest.<init> ()V
         // a2: areturn
      }

      @Override
      public ProtobufModel.CharacteristicAddress getCharacteristic() {
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         ProtobufModel.CharacteristicAddress var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.CharacteristicAddress.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasCharacteristic() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ReadCharacteristicRequest, ProtobufModel.ReadCharacteristicRequest.Builder>
         implements ProtobufModel.ReadCharacteristicRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.ReadCharacteristicRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ReadCharacteristicRequest.Builder clearCharacteristic() {
            this.copyOnWrite();
            this.instance.clearCharacteristic();
            return this;
         }

         @Override
         public ProtobufModel.CharacteristicAddress getCharacteristic() {
            return this.instance.getCharacteristic();
         }

         @Override
         public boolean hasCharacteristic() {
            return this.instance.hasCharacteristic();
         }

         public ProtobufModel.ReadCharacteristicRequest.Builder mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristic(var1);
            return this;
         }

         public ProtobufModel.ReadCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1.build());
            return this;
         }

         public ProtobufModel.ReadCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1);
            return this;
         }
      }
   }

   public interface ReadCharacteristicRequestOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.CharacteristicAddress getCharacteristic();

      boolean hasCharacteristic();
   }

   public static final class ReadRssiRequest
      extends GeneratedMessageLite<ProtobufModel.ReadRssiRequest, ProtobufModel.ReadRssiRequest.Builder>
      implements ProtobufModel.ReadRssiRequestOrBuilder {
      private static final ProtobufModel.ReadRssiRequest DEFAULT_INSTANCE;
      public static final int DEVICEID_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.ReadRssiRequest> PARSER;
      private String deviceId_ = "";

      static {
         ProtobufModel.ReadRssiRequest var0 = new ProtobufModel.ReadRssiRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ReadRssiRequest.class, var0);
      }

      private ReadRssiRequest() {
      }

      private void clearDeviceId() {
         this.deviceId_ = getDefaultInstance().getDeviceId();
      }

      public static ProtobufModel.ReadRssiRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.ReadRssiRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ReadRssiRequest.Builder newBuilder(ProtobufModel.ReadRssiRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ReadRssiRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ReadRssiRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setDeviceId(String var1) {
         var1.getClass();
         this.deviceId_ = var1;
      }

      private void setDeviceIdBytes(ByteString var1) {
         checkByteStringIsUtf8(var1);
         this.deviceId_ = var1.toStringUtf8();
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ"
         // 80: bipush 1
         // 81: anewarray 136
         // 84: dup
         // 85: bipush 0
         // 86: ldc "deviceId_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiRequest.<init> ()V
         // 9d: areturn
      }

      @Override
      public String getDeviceId() {
         return this.deviceId_;
      }

      @Override
      public ByteString getDeviceIdBytes() {
         return ByteString.copyFromUtf8(this.deviceId_);
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ReadRssiRequest, ProtobufModel.ReadRssiRequest.Builder>
         implements ProtobufModel.ReadRssiRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.ReadRssiRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ReadRssiRequest.Builder clearDeviceId() {
            this.copyOnWrite();
            this.instance.clearDeviceId();
            return this;
         }

         @Override
         public String getDeviceId() {
            return this.instance.getDeviceId();
         }

         @Override
         public ByteString getDeviceIdBytes() {
            return this.instance.getDeviceIdBytes();
         }

         public ProtobufModel.ReadRssiRequest.Builder setDeviceId(String var1) {
            this.copyOnWrite();
            this.instance.setDeviceId(var1);
            return this;
         }

         public ProtobufModel.ReadRssiRequest.Builder setDeviceIdBytes(ByteString var1) {
            this.copyOnWrite();
            this.instance.setDeviceIdBytes(var1);
            return this;
         }
      }
   }

   public interface ReadRssiRequestOrBuilder extends MessageLiteOrBuilder {
      String getDeviceId();

      ByteString getDeviceIdBytes();
   }

   public static final class ReadRssiResult
      extends GeneratedMessageLite<ProtobufModel.ReadRssiResult, ProtobufModel.ReadRssiResult.Builder>
      implements ProtobufModel.ReadRssiResultOrBuilder {
      private static final ProtobufModel.ReadRssiResult DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.ReadRssiResult> PARSER;
      public static final int RSSI_FIELD_NUMBER = 1;
      private int rssi_;

      static {
         ProtobufModel.ReadRssiResult var0 = new ProtobufModel.ReadRssiResult();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ReadRssiResult.class, var0);
      }

      private ReadRssiResult() {
      }

      private void clearRssi() {
         this.rssi_ = 0;
      }

      public static ProtobufModel.ReadRssiResult getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.ReadRssiResult.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ReadRssiResult.Builder newBuilder(ProtobufModel.ReadRssiResult var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ReadRssiResult parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiResult parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ReadRssiResult parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ReadRssiResult> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setRssi(int var1) {
         this.rssi_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004"
         // 80: bipush 1
         // 81: anewarray 153
         // 84: dup
         // 85: bipush 0
         // 86: ldc "rssi_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ReadRssiResult.<init> ()V
         // 9d: areturn
      }

      @Override
      public int getRssi() {
         return this.rssi_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ReadRssiResult, ProtobufModel.ReadRssiResult.Builder>
         implements ProtobufModel.ReadRssiResultOrBuilder {
         private Builder() {
            super(ProtobufModel.ReadRssiResult.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ReadRssiResult.Builder clearRssi() {
            this.copyOnWrite();
            this.instance.clearRssi();
            return this;
         }

         @Override
         public int getRssi() {
            return this.instance.getRssi();
         }

         public ProtobufModel.ReadRssiResult.Builder setRssi(int var1) {
            this.copyOnWrite();
            this.instance.setRssi(var1);
            return this;
         }
      }
   }

   public interface ReadRssiResultOrBuilder extends MessageLiteOrBuilder {
      int getRssi();
   }

   public static final class ScanForDevicesRequest
      extends GeneratedMessageLite<ProtobufModel.ScanForDevicesRequest, ProtobufModel.ScanForDevicesRequest.Builder>
      implements ProtobufModel.ScanForDevicesRequestOrBuilder {
      private static final ProtobufModel.ScanForDevicesRequest DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.ScanForDevicesRequest> PARSER;
      public static final int REQUIRELOCATIONSERVICESENABLED_FIELD_NUMBER = 3;
      public static final int SCANMODE_FIELD_NUMBER = 2;
      public static final int SERVICEUUIDS_FIELD_NUMBER = 1;
      private boolean requireLocationServicesEnabled_;
      private int scanMode_;
      private Internal.ProtobufList<ProtobufModel.Uuid> serviceUuids_ = emptyProtobufList();

      static {
         ProtobufModel.ScanForDevicesRequest var0 = new ProtobufModel.ScanForDevicesRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ScanForDevicesRequest.class, var0);
      }

      private ScanForDevicesRequest() {
      }

      private void addAllServiceUuids(Iterable<? extends ProtobufModel.Uuid> var1) {
         this.ensureServiceUuidsIsMutable();
         AbstractMessageLite.addAll(var1, this.serviceUuids_);
      }

      private void addServiceUuids(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.add(var1, var2);
      }

      private void addServiceUuids(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.add(var1);
      }

      private void clearRequireLocationServicesEnabled() {
         this.requireLocationServicesEnabled_ = false;
      }

      private void clearScanMode() {
         this.scanMode_ = 0;
      }

      private void clearServiceUuids() {
         this.serviceUuids_ = emptyProtobufList();
      }

      private void ensureServiceUuidsIsMutable() {
         Internal.ProtobufList var1 = this.serviceUuids_;
         if (!var1.isModifiable()) {
            this.serviceUuids_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      public static ProtobufModel.ScanForDevicesRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.ScanForDevicesRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ScanForDevicesRequest.Builder newBuilder(ProtobufModel.ScanForDevicesRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ScanForDevicesRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ScanForDevicesRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ScanForDevicesRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ScanForDevicesRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void removeServiceUuids(int var1) {
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.remove(var1);
      }

      private void setRequireLocationServicesEnabled(boolean var1) {
         this.requireLocationServicesEnabled_ = var1;
      }

      private void setScanMode(int var1) {
         this.scanMode_ = var1;
      }

      private void setServiceUuids(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureServiceUuidsIsMutable();
         this.serviceUuids_.set(var1, var2);
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 157 148 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest;
         // 7e: ldc "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u001b\u0002\u0004\u0003\u0007"
         // 80: bipush 4
         // 81: anewarray 115
         // 84: dup
         // 85: bipush 0
         // 86: ldc "serviceUuids_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "scanMode_"
         // 92: aastore
         // 93: dup
         // 94: bipush 3
         // 95: ldc "requireLocationServicesEnabled_"
         // 97: aastore
         // 98: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 9b: areturn
         // 9c: new com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest$Builder
         // 9f: dup
         // a0: aconst_null
         // a1: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // a4: areturn
         // a5: new com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest
         // a8: dup
         // a9: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ScanForDevicesRequest.<init> ()V
         // ac: areturn
      }

      @Override
      public boolean getRequireLocationServicesEnabled() {
         return this.requireLocationServicesEnabled_;
      }

      @Override
      public int getScanMode() {
         return this.scanMode_;
      }

      @Override
      public ProtobufModel.Uuid getServiceUuids(int var1) {
         return this.serviceUuids_.get(var1);
      }

      @Override
      public int getServiceUuidsCount() {
         return this.serviceUuids_.size();
      }

      @Override
      public List<ProtobufModel.Uuid> getServiceUuidsList() {
         return this.serviceUuids_;
      }

      public ProtobufModel.UuidOrBuilder getServiceUuidsOrBuilder(int var1) {
         return this.serviceUuids_.get(var1);
      }

      public List<? extends ProtobufModel.UuidOrBuilder> getServiceUuidsOrBuilderList() {
         return this.serviceUuids_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ScanForDevicesRequest, ProtobufModel.ScanForDevicesRequest.Builder>
         implements ProtobufModel.ScanForDevicesRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.ScanForDevicesRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ScanForDevicesRequest.Builder addAllServiceUuids(Iterable<? extends ProtobufModel.Uuid> var1) {
            this.copyOnWrite();
            this.instance.addAllServiceUuids(var1);
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder addServiceUuids(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1, var2.build());
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder addServiceUuids(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1, var2);
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder addServiceUuids(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1.build());
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder addServiceUuids(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.addServiceUuids(var1);
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder clearRequireLocationServicesEnabled() {
            this.copyOnWrite();
            this.instance.clearRequireLocationServicesEnabled();
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder clearScanMode() {
            this.copyOnWrite();
            this.instance.clearScanMode();
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder clearServiceUuids() {
            this.copyOnWrite();
            this.instance.clearServiceUuids();
            return this;
         }

         @Override
         public boolean getRequireLocationServicesEnabled() {
            return this.instance.getRequireLocationServicesEnabled();
         }

         @Override
         public int getScanMode() {
            return this.instance.getScanMode();
         }

         @Override
         public ProtobufModel.Uuid getServiceUuids(int var1) {
            return this.instance.getServiceUuids(var1);
         }

         @Override
         public int getServiceUuidsCount() {
            return this.instance.getServiceUuidsCount();
         }

         @Override
         public List<ProtobufModel.Uuid> getServiceUuidsList() {
            return Collections.unmodifiableList(this.instance.getServiceUuidsList());
         }

         public ProtobufModel.ScanForDevicesRequest.Builder removeServiceUuids(int var1) {
            this.copyOnWrite();
            this.instance.removeServiceUuids(var1);
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder setRequireLocationServicesEnabled(boolean var1) {
            this.copyOnWrite();
            this.instance.setRequireLocationServicesEnabled(var1);
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder setScanMode(int var1) {
            this.copyOnWrite();
            this.instance.setScanMode(var1);
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder setServiceUuids(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.setServiceUuids(var1, var2.build());
            return this;
         }

         public ProtobufModel.ScanForDevicesRequest.Builder setServiceUuids(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.setServiceUuids(var1, var2);
            return this;
         }
      }
   }

   public interface ScanForDevicesRequestOrBuilder extends MessageLiteOrBuilder {
      boolean getRequireLocationServicesEnabled();

      int getScanMode();

      ProtobufModel.Uuid getServiceUuids(int var1);

      int getServiceUuidsCount();

      List<ProtobufModel.Uuid> getServiceUuidsList();
   }

   public static final class ServiceDataEntry
      extends GeneratedMessageLite<ProtobufModel.ServiceDataEntry, ProtobufModel.ServiceDataEntry.Builder>
      implements ProtobufModel.ServiceDataEntryOrBuilder {
      public static final int DATA_FIELD_NUMBER = 2;
      private static final ProtobufModel.ServiceDataEntry DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.ServiceDataEntry> PARSER;
      public static final int SERVICEUUID_FIELD_NUMBER = 1;
      private int bitField0_;
      private ByteString data_ = ByteString.EMPTY;
      private ProtobufModel.Uuid serviceUuid_;

      static {
         ProtobufModel.ServiceDataEntry var0 = new ProtobufModel.ServiceDataEntry();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ServiceDataEntry.class, var0);
      }

      private ServiceDataEntry() {
      }

      private void clearData() {
         this.data_ = getDefaultInstance().getData();
      }

      private void clearServiceUuid() {
         this.serviceUuid_ = null;
         this.bitField0_ &= -2;
      }

      public static ProtobufModel.ServiceDataEntry getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeServiceUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         ProtobufModel.Uuid var2 = this.serviceUuid_;
         if (var2 != null && var2 != ProtobufModel.Uuid.getDefaultInstance()) {
            this.serviceUuid_ = ProtobufModel.Uuid.newBuilder(this.serviceUuid_).mergeFrom(var1).buildPartial();
         } else {
            this.serviceUuid_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.ServiceDataEntry.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ServiceDataEntry.Builder newBuilder(ProtobufModel.ServiceDataEntry var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ServiceDataEntry parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceDataEntry parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceDataEntry parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ServiceDataEntry> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setData(ByteString var1) {
         var1.getClass();
         this.data_ = var1;
      }

      private void setServiceUuid(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.serviceUuid_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 152 143 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry;
         // 7e: ldc "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002\n"
         // 80: bipush 3
         // 81: anewarray 88
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "serviceUuid_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "data_"
         // 92: aastore
         // 93: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 96: areturn
         // 97: new com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry$Builder
         // 9a: dup
         // 9b: aconst_null
         // 9c: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9f: areturn
         // a0: new com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry
         // a3: dup
         // a4: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ServiceDataEntry.<init> ()V
         // a7: areturn
      }

      @Override
      public ByteString getData() {
         return this.data_;
      }

      @Override
      public ProtobufModel.Uuid getServiceUuid() {
         ProtobufModel.Uuid var2 = this.serviceUuid_;
         ProtobufModel.Uuid var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.Uuid.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasServiceUuid() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ServiceDataEntry, ProtobufModel.ServiceDataEntry.Builder>
         implements ProtobufModel.ServiceDataEntryOrBuilder {
         private Builder() {
            super(ProtobufModel.ServiceDataEntry.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ServiceDataEntry.Builder clearData() {
            this.copyOnWrite();
            this.instance.clearData();
            return this;
         }

         public ProtobufModel.ServiceDataEntry.Builder clearServiceUuid() {
            this.copyOnWrite();
            this.instance.clearServiceUuid();
            return this;
         }

         @Override
         public ByteString getData() {
            return this.instance.getData();
         }

         @Override
         public ProtobufModel.Uuid getServiceUuid() {
            return this.instance.getServiceUuid();
         }

         @Override
         public boolean hasServiceUuid() {
            return this.instance.hasServiceUuid();
         }

         public ProtobufModel.ServiceDataEntry.Builder mergeServiceUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.mergeServiceUuid(var1);
            return this;
         }

         public ProtobufModel.ServiceDataEntry.Builder setData(ByteString var1) {
            this.copyOnWrite();
            this.instance.setData(var1);
            return this;
         }

         public ProtobufModel.ServiceDataEntry.Builder setServiceUuid(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.setServiceUuid(var1.build());
            return this;
         }

         public ProtobufModel.ServiceDataEntry.Builder setServiceUuid(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.setServiceUuid(var1);
            return this;
         }
      }
   }

   public interface ServiceDataEntryOrBuilder extends MessageLiteOrBuilder {
      ByteString getData();

      ProtobufModel.Uuid getServiceUuid();

      boolean hasServiceUuid();
   }

   public static final class ServiceWithCharacteristics
      extends GeneratedMessageLite<ProtobufModel.ServiceWithCharacteristics, ProtobufModel.ServiceWithCharacteristics.Builder>
      implements ProtobufModel.ServiceWithCharacteristicsOrBuilder {
      public static final int CHARACTERISTICS_FIELD_NUMBER = 2;
      private static final ProtobufModel.ServiceWithCharacteristics DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.ServiceWithCharacteristics> PARSER;
      public static final int SERVICEID_FIELD_NUMBER = 1;
      private int bitField0_;
      private Internal.ProtobufList<ProtobufModel.Uuid> characteristics_ = emptyProtobufList();
      private ProtobufModel.Uuid serviceId_;

      static {
         ProtobufModel.ServiceWithCharacteristics var0 = new ProtobufModel.ServiceWithCharacteristics();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ServiceWithCharacteristics.class, var0);
      }

      private ServiceWithCharacteristics() {
      }

      private void addAllCharacteristics(Iterable<? extends ProtobufModel.Uuid> var1) {
         this.ensureCharacteristicsIsMutable();
         AbstractMessageLite.addAll(var1, this.characteristics_);
      }

      private void addCharacteristics(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.add(var1, var2);
      }

      private void addCharacteristics(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.add(var1);
      }

      private void clearCharacteristics() {
         this.characteristics_ = emptyProtobufList();
      }

      private void clearServiceId() {
         this.serviceId_ = null;
         this.bitField0_ &= -2;
      }

      private void ensureCharacteristicsIsMutable() {
         Internal.ProtobufList var1 = this.characteristics_;
         if (!var1.isModifiable()) {
            this.characteristics_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      public static ProtobufModel.ServiceWithCharacteristics getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeServiceId(ProtobufModel.Uuid var1) {
         var1.getClass();
         ProtobufModel.Uuid var2 = this.serviceId_;
         if (var2 != null && var2 != ProtobufModel.Uuid.getDefaultInstance()) {
            this.serviceId_ = ProtobufModel.Uuid.newBuilder(this.serviceId_).mergeFrom(var1).buildPartial();
         } else {
            this.serviceId_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.ServiceWithCharacteristics.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ServiceWithCharacteristics.Builder newBuilder(ProtobufModel.ServiceWithCharacteristics var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServiceWithCharacteristics parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ServiceWithCharacteristics> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void removeCharacteristics(int var1) {
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.remove(var1);
      }

      private void setCharacteristics(int var1, ProtobufModel.Uuid var2) {
         var2.getClass();
         this.ensureCharacteristicsIsMutable();
         this.characteristics_.set(var1, var2);
      }

      private void setServiceId(ProtobufModel.Uuid var1) {
         var1.getClass();
         this.serviceId_ = var1;
         this.bitField0_ |= 1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 161 152 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics;
         // 7e: ldc_w "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဉ\u0000\u0002\u001b"
         // 81: bipush 4
         // 82: anewarray 107
         // 85: dup
         // 86: bipush 0
         // 87: ldc_w "bitField0_"
         // 8a: aastore
         // 8b: dup
         // 8c: bipush 1
         // 8d: ldc_w "serviceId_"
         // 90: aastore
         // 91: dup
         // 92: bipush 2
         // 93: ldc_w "characteristics_"
         // 96: aastore
         // 97: dup
         // 98: bipush 3
         // 99: ldc com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // 9b: aastore
         // 9c: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 9f: areturn
         // a0: new com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics$Builder
         // a3: dup
         // a4: aconst_null
         // a5: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // a8: areturn
         // a9: new com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics
         // ac: dup
         // ad: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics.<init> ()V
         // b0: areturn
      }

      @Override
      public ProtobufModel.Uuid getCharacteristics(int var1) {
         return this.characteristics_.get(var1);
      }

      @Override
      public int getCharacteristicsCount() {
         return this.characteristics_.size();
      }

      @Override
      public List<ProtobufModel.Uuid> getCharacteristicsList() {
         return this.characteristics_;
      }

      public ProtobufModel.UuidOrBuilder getCharacteristicsOrBuilder(int var1) {
         return this.characteristics_.get(var1);
      }

      public List<? extends ProtobufModel.UuidOrBuilder> getCharacteristicsOrBuilderList() {
         return this.characteristics_;
      }

      @Override
      public ProtobufModel.Uuid getServiceId() {
         ProtobufModel.Uuid var2 = this.serviceId_;
         ProtobufModel.Uuid var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.Uuid.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasServiceId() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ServiceWithCharacteristics, ProtobufModel.ServiceWithCharacteristics.Builder>
         implements ProtobufModel.ServiceWithCharacteristicsOrBuilder {
         private Builder() {
            super(ProtobufModel.ServiceWithCharacteristics.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder addAllCharacteristics(Iterable<? extends ProtobufModel.Uuid> var1) {
            this.copyOnWrite();
            this.instance.addAllCharacteristics(var1);
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder addCharacteristics(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1, var2.build());
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder addCharacteristics(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1, var2);
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder addCharacteristics(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1.build());
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder addCharacteristics(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.addCharacteristics(var1);
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder clearCharacteristics() {
            this.copyOnWrite();
            this.instance.clearCharacteristics();
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder clearServiceId() {
            this.copyOnWrite();
            this.instance.clearServiceId();
            return this;
         }

         @Override
         public ProtobufModel.Uuid getCharacteristics(int var1) {
            return this.instance.getCharacteristics(var1);
         }

         @Override
         public int getCharacteristicsCount() {
            return this.instance.getCharacteristicsCount();
         }

         @Override
         public List<ProtobufModel.Uuid> getCharacteristicsList() {
            return Collections.unmodifiableList(this.instance.getCharacteristicsList());
         }

         @Override
         public ProtobufModel.Uuid getServiceId() {
            return this.instance.getServiceId();
         }

         @Override
         public boolean hasServiceId() {
            return this.instance.hasServiceId();
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder mergeServiceId(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.mergeServiceId(var1);
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder removeCharacteristics(int var1) {
            this.copyOnWrite();
            this.instance.removeCharacteristics(var1);
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder setCharacteristics(int var1, ProtobufModel.Uuid.Builder var2) {
            this.copyOnWrite();
            this.instance.setCharacteristics(var1, var2.build());
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder setCharacteristics(int var1, ProtobufModel.Uuid var2) {
            this.copyOnWrite();
            this.instance.setCharacteristics(var1, var2);
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder setServiceId(ProtobufModel.Uuid.Builder var1) {
            this.copyOnWrite();
            this.instance.setServiceId(var1.build());
            return this;
         }

         public ProtobufModel.ServiceWithCharacteristics.Builder setServiceId(ProtobufModel.Uuid var1) {
            this.copyOnWrite();
            this.instance.setServiceId(var1);
            return this;
         }
      }
   }

   public interface ServiceWithCharacteristicsOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.Uuid getCharacteristics(int var1);

      int getCharacteristicsCount();

      List<ProtobufModel.Uuid> getCharacteristicsList();

      ProtobufModel.Uuid getServiceId();

      boolean hasServiceId();
   }

   public static final class ServicesWithCharacteristics
      extends GeneratedMessageLite<ProtobufModel.ServicesWithCharacteristics, ProtobufModel.ServicesWithCharacteristics.Builder>
      implements ProtobufModel.ServicesWithCharacteristicsOrBuilder {
      private static final ProtobufModel.ServicesWithCharacteristics DEFAULT_INSTANCE;
      public static final int ITEMS_FIELD_NUMBER = 1;
      private static volatile Parser<ProtobufModel.ServicesWithCharacteristics> PARSER;
      private Internal.ProtobufList<ProtobufModel.ServiceWithCharacteristics> items_ = emptyProtobufList();

      static {
         ProtobufModel.ServicesWithCharacteristics var0 = new ProtobufModel.ServicesWithCharacteristics();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.ServicesWithCharacteristics.class, var0);
      }

      private ServicesWithCharacteristics() {
      }

      private void addAllItems(Iterable<? extends ProtobufModel.ServiceWithCharacteristics> var1) {
         this.ensureItemsIsMutable();
         AbstractMessageLite.addAll(var1, this.items_);
      }

      private void addItems(int var1, ProtobufModel.ServiceWithCharacteristics var2) {
         var2.getClass();
         this.ensureItemsIsMutable();
         this.items_.add(var1, var2);
      }

      private void addItems(ProtobufModel.ServiceWithCharacteristics var1) {
         var1.getClass();
         this.ensureItemsIsMutable();
         this.items_.add(var1);
      }

      private void clearItems() {
         this.items_ = emptyProtobufList();
      }

      private void ensureItemsIsMutable() {
         Internal.ProtobufList var1 = this.items_;
         if (!var1.isModifiable()) {
            this.items_ = GeneratedMessageLite.mutableCopy(var1);
         }
      }

      public static ProtobufModel.ServicesWithCharacteristics getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.ServicesWithCharacteristics.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.ServicesWithCharacteristics.Builder newBuilder(ProtobufModel.ServicesWithCharacteristics var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.ServicesWithCharacteristics parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.ServicesWithCharacteristics> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void removeItems(int var1) {
         this.ensureItemsIsMutable();
         this.items_.remove(var1);
      }

      private void setItems(int var1, ProtobufModel.ServiceWithCharacteristics var2) {
         var2.getClass();
         this.ensureItemsIsMutable();
         this.items_.set(var1, var2);
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b"
         // 80: bipush 2
         // 81: anewarray 90
         // 84: dup
         // 85: bipush 0
         // 86: ldc "items_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc com/signify/hue/flutterreactiveble/ProtobufModel$ServiceWithCharacteristics
         // 8d: aastore
         // 8e: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 91: areturn
         // 92: new com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics$Builder
         // 95: dup
         // 96: aconst_null
         // 97: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9a: areturn
         // 9b: new com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics
         // 9e: dup
         // 9f: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$ServicesWithCharacteristics.<init> ()V
         // a2: areturn
      }

      @Override
      public ProtobufModel.ServiceWithCharacteristics getItems(int var1) {
         return this.items_.get(var1);
      }

      @Override
      public int getItemsCount() {
         return this.items_.size();
      }

      @Override
      public List<ProtobufModel.ServiceWithCharacteristics> getItemsList() {
         return this.items_;
      }

      public ProtobufModel.ServiceWithCharacteristicsOrBuilder getItemsOrBuilder(int var1) {
         return this.items_.get(var1);
      }

      public List<? extends ProtobufModel.ServiceWithCharacteristicsOrBuilder> getItemsOrBuilderList() {
         return this.items_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.ServicesWithCharacteristics, ProtobufModel.ServicesWithCharacteristics.Builder>
         implements ProtobufModel.ServicesWithCharacteristicsOrBuilder {
         private Builder() {
            super(ProtobufModel.ServicesWithCharacteristics.DEFAULT_INSTANCE);
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder addAllItems(Iterable<? extends ProtobufModel.ServiceWithCharacteristics> var1) {
            this.copyOnWrite();
            this.instance.addAllItems(var1);
            return this;
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder addItems(int var1, ProtobufModel.ServiceWithCharacteristics.Builder var2) {
            this.copyOnWrite();
            this.instance.addItems(var1, var2.build());
            return this;
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder addItems(int var1, ProtobufModel.ServiceWithCharacteristics var2) {
            this.copyOnWrite();
            this.instance.addItems(var1, var2);
            return this;
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder addItems(ProtobufModel.ServiceWithCharacteristics.Builder var1) {
            this.copyOnWrite();
            this.instance.addItems(var1.build());
            return this;
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder addItems(ProtobufModel.ServiceWithCharacteristics var1) {
            this.copyOnWrite();
            this.instance.addItems(var1);
            return this;
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder clearItems() {
            this.copyOnWrite();
            this.instance.clearItems();
            return this;
         }

         @Override
         public ProtobufModel.ServiceWithCharacteristics getItems(int var1) {
            return this.instance.getItems(var1);
         }

         @Override
         public int getItemsCount() {
            return this.instance.getItemsCount();
         }

         @Override
         public List<ProtobufModel.ServiceWithCharacteristics> getItemsList() {
            return Collections.unmodifiableList(this.instance.getItemsList());
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder removeItems(int var1) {
            this.copyOnWrite();
            this.instance.removeItems(var1);
            return this;
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder setItems(int var1, ProtobufModel.ServiceWithCharacteristics.Builder var2) {
            this.copyOnWrite();
            this.instance.setItems(var1, var2.build());
            return this;
         }

         public ProtobufModel.ServicesWithCharacteristics.Builder setItems(int var1, ProtobufModel.ServiceWithCharacteristics var2) {
            this.copyOnWrite();
            this.instance.setItems(var1, var2);
            return this;
         }
      }
   }

   public interface ServicesWithCharacteristicsOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.ServiceWithCharacteristics getItems(int var1);

      int getItemsCount();

      List<ProtobufModel.ServiceWithCharacteristics> getItemsList();
   }

   public static final class Uuid extends GeneratedMessageLite<ProtobufModel.Uuid, ProtobufModel.Uuid.Builder> implements ProtobufModel.UuidOrBuilder {
      public static final int DATA_FIELD_NUMBER = 1;
      private static final ProtobufModel.Uuid DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.Uuid> PARSER;
      private ByteString data_ = ByteString.EMPTY;

      static {
         ProtobufModel.Uuid var0 = new ProtobufModel.Uuid();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.Uuid.class, var0);
      }

      private Uuid() {
      }

      private void clearData() {
         this.data_ = getDefaultInstance().getData();
      }

      public static ProtobufModel.Uuid getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      public static ProtobufModel.Uuid.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.Uuid.Builder newBuilder(ProtobufModel.Uuid var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.Uuid parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.Uuid parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.Uuid parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.Uuid parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.Uuid parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.Uuid parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.Uuid parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.Uuid parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.Uuid parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.Uuid parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.Uuid parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.Uuid parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.Uuid> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setData(ByteString var1) {
         var1.getClass();
         this.data_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
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
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$Uuid;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$Uuid;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$Uuid;
         // 7e: ldc "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\n"
         // 80: bipush 1
         // 81: anewarray 133
         // 84: dup
         // 85: bipush 0
         // 86: ldc "data_"
         // 88: aastore
         // 89: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 8c: areturn
         // 8d: new com/signify/hue/flutterreactiveble/ProtobufModel$Uuid$Builder
         // 90: dup
         // 91: aconst_null
         // 92: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$Uuid$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 95: areturn
         // 96: new com/signify/hue/flutterreactiveble/ProtobufModel$Uuid
         // 99: dup
         // 9a: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$Uuid.<init> ()V
         // 9d: areturn
      }

      @Override
      public ByteString getData() {
         return this.data_;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.Uuid, ProtobufModel.Uuid.Builder>
         implements ProtobufModel.UuidOrBuilder {
         private Builder() {
            super(ProtobufModel.Uuid.DEFAULT_INSTANCE);
         }

         public ProtobufModel.Uuid.Builder clearData() {
            this.copyOnWrite();
            this.instance.clearData();
            return this;
         }

         @Override
         public ByteString getData() {
            return this.instance.getData();
         }

         public ProtobufModel.Uuid.Builder setData(ByteString var1) {
            this.copyOnWrite();
            this.instance.setData(var1);
            return this;
         }
      }
   }

   public interface UuidOrBuilder extends MessageLiteOrBuilder {
      ByteString getData();
   }

   public static final class WriteCharacteristicInfo
      extends GeneratedMessageLite<ProtobufModel.WriteCharacteristicInfo, ProtobufModel.WriteCharacteristicInfo.Builder>
      implements ProtobufModel.WriteCharacteristicInfoOrBuilder {
      public static final int CHARACTERISTIC_FIELD_NUMBER = 1;
      private static final ProtobufModel.WriteCharacteristicInfo DEFAULT_INSTANCE;
      public static final int FAILURE_FIELD_NUMBER = 3;
      private static volatile Parser<ProtobufModel.WriteCharacteristicInfo> PARSER;
      private int bitField0_;
      private ProtobufModel.CharacteristicAddress characteristic_;
      private ProtobufModel.GenericFailure failure_;

      static {
         ProtobufModel.WriteCharacteristicInfo var0 = new ProtobufModel.WriteCharacteristicInfo();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.WriteCharacteristicInfo.class, var0);
      }

      private WriteCharacteristicInfo() {
      }

      private void clearCharacteristic() {
         this.characteristic_ = null;
         this.bitField0_ &= -2;
      }

      private void clearFailure() {
         this.failure_ = null;
         this.bitField0_ &= -3;
      }

      public static ProtobufModel.WriteCharacteristicInfo getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         if (var2 != null && var2 != ProtobufModel.CharacteristicAddress.getDefaultInstance()) {
            this.characteristic_ = ProtobufModel.CharacteristicAddress.newBuilder(this.characteristic_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristic_ = var1;
         }

         this.bitField0_ |= 1;
      }

      private void mergeFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         ProtobufModel.GenericFailure var2 = this.failure_;
         if (var2 != null && var2 != ProtobufModel.GenericFailure.getDefaultInstance()) {
            this.failure_ = ProtobufModel.GenericFailure.newBuilder(this.failure_).mergeFrom(var1).buildPartial();
         } else {
            this.failure_ = var1;
         }

         this.bitField0_ |= 2;
      }

      public static ProtobufModel.WriteCharacteristicInfo.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.WriteCharacteristicInfo.Builder newBuilder(ProtobufModel.WriteCharacteristicInfo var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicInfo parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.WriteCharacteristicInfo> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         this.characteristic_ = var1;
         this.bitField0_ |= 1;
      }

      private void setFailure(ProtobufModel.GenericFailure var1) {
         var1.getClass();
         this.failure_ = var1;
         this.bitField0_ |= 2;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 152 143 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo;
         // 7e: ldc "\u0000\u0002\u0000\u0001\u0001\u0003\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0003ဉ\u0001"
         // 80: bipush 3
         // 81: anewarray 81
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "characteristic_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "failure_"
         // 92: aastore
         // 93: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 96: areturn
         // 97: new com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo$Builder
         // 9a: dup
         // 9b: aconst_null
         // 9c: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9f: areturn
         // a0: new com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo
         // a3: dup
         // a4: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicInfo.<init> ()V
         // a7: areturn
      }

      @Override
      public ProtobufModel.CharacteristicAddress getCharacteristic() {
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         ProtobufModel.CharacteristicAddress var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.CharacteristicAddress.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public ProtobufModel.GenericFailure getFailure() {
         ProtobufModel.GenericFailure var2 = this.failure_;
         ProtobufModel.GenericFailure var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.GenericFailure.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public boolean hasCharacteristic() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      @Override
      public boolean hasFailure() {
         boolean var1;
         if ((this.bitField0_ & 2) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.WriteCharacteristicInfo, ProtobufModel.WriteCharacteristicInfo.Builder>
         implements ProtobufModel.WriteCharacteristicInfoOrBuilder {
         private Builder() {
            super(ProtobufModel.WriteCharacteristicInfo.DEFAULT_INSTANCE);
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder clearCharacteristic() {
            this.copyOnWrite();
            this.instance.clearCharacteristic();
            return this;
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder clearFailure() {
            this.copyOnWrite();
            this.instance.clearFailure();
            return this;
         }

         @Override
         public ProtobufModel.CharacteristicAddress getCharacteristic() {
            return this.instance.getCharacteristic();
         }

         @Override
         public ProtobufModel.GenericFailure getFailure() {
            return this.instance.getFailure();
         }

         @Override
         public boolean hasCharacteristic() {
            return this.instance.hasCharacteristic();
         }

         @Override
         public boolean hasFailure() {
            return this.instance.hasFailure();
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristic(var1);
            return this;
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder mergeFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.mergeFailure(var1);
            return this;
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder setCharacteristic(ProtobufModel.CharacteristicAddress.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1.build());
            return this;
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1);
            return this;
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder setFailure(ProtobufModel.GenericFailure.Builder var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1.build());
            return this;
         }

         public ProtobufModel.WriteCharacteristicInfo.Builder setFailure(ProtobufModel.GenericFailure var1) {
            this.copyOnWrite();
            this.instance.setFailure(var1);
            return this;
         }
      }
   }

   public interface WriteCharacteristicInfoOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.CharacteristicAddress getCharacteristic();

      ProtobufModel.GenericFailure getFailure();

      boolean hasCharacteristic();

      boolean hasFailure();
   }

   public static final class WriteCharacteristicRequest
      extends GeneratedMessageLite<ProtobufModel.WriteCharacteristicRequest, ProtobufModel.WriteCharacteristicRequest.Builder>
      implements ProtobufModel.WriteCharacteristicRequestOrBuilder {
      public static final int CHARACTERISTIC_FIELD_NUMBER = 1;
      private static final ProtobufModel.WriteCharacteristicRequest DEFAULT_INSTANCE;
      private static volatile Parser<ProtobufModel.WriteCharacteristicRequest> PARSER;
      public static final int VALUE_FIELD_NUMBER = 2;
      private int bitField0_;
      private ProtobufModel.CharacteristicAddress characteristic_;
      private ByteString value_ = ByteString.EMPTY;

      static {
         ProtobufModel.WriteCharacteristicRequest var0 = new ProtobufModel.WriteCharacteristicRequest();
         DEFAULT_INSTANCE = var0;
         GeneratedMessageLite.registerDefaultInstance(ProtobufModel.WriteCharacteristicRequest.class, var0);
      }

      private WriteCharacteristicRequest() {
      }

      private void clearCharacteristic() {
         this.characteristic_ = null;
         this.bitField0_ &= -2;
      }

      private void clearValue() {
         this.value_ = getDefaultInstance().getValue();
      }

      public static ProtobufModel.WriteCharacteristicRequest getDefaultInstance() {
         return DEFAULT_INSTANCE;
      }

      private void mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         if (var2 != null && var2 != ProtobufModel.CharacteristicAddress.getDefaultInstance()) {
            this.characteristic_ = ProtobufModel.CharacteristicAddress.newBuilder(this.characteristic_).mergeFrom(var1).buildPartial();
         } else {
            this.characteristic_ = var1;
         }

         this.bitField0_ |= 1;
      }

      public static ProtobufModel.WriteCharacteristicRequest.Builder newBuilder() {
         return DEFAULT_INSTANCE.createBuilder();
      }

      public static ProtobufModel.WriteCharacteristicRequest.Builder newBuilder(ProtobufModel.WriteCharacteristicRequest var0) {
         return DEFAULT_INSTANCE.createBuilder(var0);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseDelimitedFrom(InputStream var0) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseDelimitedFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return parseDelimitedFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(ByteString var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(ByteString var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(CodedInputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(CodedInputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(InputStream var0) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(InputStream var0, ExtensionRegistryLite var1) throws IOException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(ByteBuffer var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(ByteBuffer var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(byte[] var0) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0);
      }

      public static ProtobufModel.WriteCharacteristicRequest parseFrom(byte[] var0, ExtensionRegistryLite var1) throws InvalidProtocolBufferException {
         return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, var0, var1);
      }

      public static Parser<ProtobufModel.WriteCharacteristicRequest> parser() {
         return DEFAULT_INSTANCE.getParserForType();
      }

      private void setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
         var1.getClass();
         this.characteristic_ = var1;
         this.bitField0_ |= 1;
      }

      private void setValue(ByteString var1) {
         var1.getClass();
         this.value_ = var1;
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
         // 00: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke [I
         // 03: aload 1
         // 04: invokevirtual com/google/protobuf/GeneratedMessageLite$MethodToInvoke.ordinal ()I
         // 07: iaload
         // 08: tableswitch 44 1 7 152 143 115 111 59 54 52
         // 34: new java/lang/UnsupportedOperationException
         // 37: dup
         // 38: invokespecial java/lang/UnsupportedOperationException.<init> ()V
         // 3b: athrow
         // 3c: aconst_null
         // 3d: areturn
         // 3e: bipush 1
         // 3f: invokestatic java/lang/Byte.valueOf (B)Ljava/lang/Byte;
         // 42: areturn
         // 43: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 46: astore 2
         // 47: aload 2
         // 48: astore 1
         // 49: aload 2
         // 4a: ifnonnull 75
         // 4d: ldc com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest
         // 4f: monitorenter
         // 50: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 53: astore 2
         // 54: aload 2
         // 55: astore 1
         // 56: aload 2
         // 57: ifnonnull 69
         // 5a: new com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser
         // 5d: astore 1
         // 5e: aload 1
         // 5f: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest;
         // 62: invokespecial com/google/protobuf/GeneratedMessageLite$DefaultInstanceBasedParser.<init> (Lcom/google/protobuf/GeneratedMessageLite;)V
         // 65: aload 1
         // 66: putstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.PARSER Lcom/google/protobuf/Parser;
         // 69: ldc com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest
         // 6b: monitorexit
         // 6c: goto 75
         // 6f: astore 1
         // 70: ldc com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: aload 1
         // 76: areturn
         // 77: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest;
         // 7a: areturn
         // 7b: getstatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.DEFAULT_INSTANCE Lcom/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest;
         // 7e: ldc "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002\n"
         // 80: bipush 3
         // 81: anewarray 88
         // 84: dup
         // 85: bipush 0
         // 86: ldc "bitField0_"
         // 88: aastore
         // 89: dup
         // 8a: bipush 1
         // 8b: ldc "characteristic_"
         // 8d: aastore
         // 8e: dup
         // 8f: bipush 2
         // 90: ldc "value_"
         // 92: aastore
         // 93: invokestatic com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.newMessageInfo (Lcom/google/protobuf/MessageLite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
         // 96: areturn
         // 97: new com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest$Builder
         // 9a: dup
         // 9b: aconst_null
         // 9c: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest$Builder.<init> (Lcom/signify/hue/flutterreactiveble/ProtobufModel$1;)V
         // 9f: areturn
         // a0: new com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest
         // a3: dup
         // a4: invokespecial com/signify/hue/flutterreactiveble/ProtobufModel$WriteCharacteristicRequest.<init> ()V
         // a7: areturn
      }

      @Override
      public ProtobufModel.CharacteristicAddress getCharacteristic() {
         ProtobufModel.CharacteristicAddress var2 = this.characteristic_;
         ProtobufModel.CharacteristicAddress var1 = var2;
         if (var2 == null) {
            var1 = ProtobufModel.CharacteristicAddress.getDefaultInstance();
         }

         return var1;
      }

      @Override
      public ByteString getValue() {
         return this.value_;
      }

      @Override
      public boolean hasCharacteristic() {
         int var1 = this.bitField0_;
         boolean var2 = true;
         if ((var1 & 1) == 0) {
            var2 = false;
         }

         return var2;
      }

      public static final class Builder
         extends GeneratedMessageLite.Builder<ProtobufModel.WriteCharacteristicRequest, ProtobufModel.WriteCharacteristicRequest.Builder>
         implements ProtobufModel.WriteCharacteristicRequestOrBuilder {
         private Builder() {
            super(ProtobufModel.WriteCharacteristicRequest.DEFAULT_INSTANCE);
         }

         public ProtobufModel.WriteCharacteristicRequest.Builder clearCharacteristic() {
            this.copyOnWrite();
            this.instance.clearCharacteristic();
            return this;
         }

         public ProtobufModel.WriteCharacteristicRequest.Builder clearValue() {
            this.copyOnWrite();
            this.instance.clearValue();
            return this;
         }

         @Override
         public ProtobufModel.CharacteristicAddress getCharacteristic() {
            return this.instance.getCharacteristic();
         }

         @Override
         public ByteString getValue() {
            return this.instance.getValue();
         }

         @Override
         public boolean hasCharacteristic() {
            return this.instance.hasCharacteristic();
         }

         public ProtobufModel.WriteCharacteristicRequest.Builder mergeCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.mergeCharacteristic(var1);
            return this;
         }

         public ProtobufModel.WriteCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress.Builder var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1.build());
            return this;
         }

         public ProtobufModel.WriteCharacteristicRequest.Builder setCharacteristic(ProtobufModel.CharacteristicAddress var1) {
            this.copyOnWrite();
            this.instance.setCharacteristic(var1);
            return this;
         }

         public ProtobufModel.WriteCharacteristicRequest.Builder setValue(ByteString var1) {
            this.copyOnWrite();
            this.instance.setValue(var1);
            return this;
         }
      }
   }

   public interface WriteCharacteristicRequestOrBuilder extends MessageLiteOrBuilder {
      ProtobufModel.CharacteristicAddress getCharacteristic();

      ByteString getValue();

      boolean hasCharacteristic();
   }
}
