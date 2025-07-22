package io.flutter.plugin.common;

import io.flutter.Log;
import java.nio.ByteBuffer;

public class MethodChannel {
   private static final String TAG = "MethodChannel#";
   private final MethodCodec codec;
   private final BinaryMessenger messenger;
   private final String name;
   private final BinaryMessenger.TaskQueue taskQueue;

   public MethodChannel(BinaryMessenger var1, String var2) {
      this(var1, var2, StandardMethodCodec.INSTANCE);
   }

   public MethodChannel(BinaryMessenger var1, String var2, MethodCodec var3) {
      this(var1, var2, var3, null);
   }

   public MethodChannel(BinaryMessenger var1, String var2, MethodCodec var3, BinaryMessenger.TaskQueue var4) {
      this.messenger = var1;
      this.name = var2;
      this.codec = var3;
      this.taskQueue = var4;
   }

   public void invokeMethod(String var1, Object var2) {
      this.invokeMethod(var1, var2, null);
   }

   public void invokeMethod(String var1, Object var2, MethodChannel.Result var3) {
      BinaryMessenger var4 = this.messenger;
      String var5 = this.name;
      var2 = this.codec.encodeMethodCall(new MethodCall(var1, var2));
      MethodChannel.IncomingResultHandler var6;
      if (var3 == null) {
         var6 = null;
      } else {
         var6 = new MethodChannel.IncomingResultHandler(this, var3);
      }

      var4.send(var5, var2, var6);
   }

   public void resizeChannelBuffer(int var1) {
      BasicMessageChannel.resizeChannelBuffer(this.messenger, this.name, var1);
   }

   public void setMethodCallHandler(MethodChannel.MethodCallHandler var1) {
      BinaryMessenger.TaskQueue var4 = this.taskQueue;
      BinaryMessenger var2 = null;
      Object var3 = null;
      if (var4 != null) {
         var2 = this.messenger;
         String var9 = this.name;
         MethodChannel.IncomingMethodCallHandler var5;
         if (var1 == null) {
            var5 = (MethodChannel.IncomingMethodCallHandler)var3;
         } else {
            var5 = new MethodChannel.IncomingMethodCallHandler(this, var1);
         }

         var2.setMessageHandler(var9, var5, this.taskQueue);
      } else {
         BinaryMessenger var10 = this.messenger;
         var3 = this.name;
         MethodChannel.IncomingMethodCallHandler var6;
         if (var1 == null) {
            var6 = var2;
         } else {
            var6 = new MethodChannel.IncomingMethodCallHandler(this, var1);
         }

         var10.setMessageHandler((String)var3, var6);
      }
   }

   public void setWarnsOnChannelOverflow(boolean var1) {
      BasicMessageChannel.setWarnsOnChannelOverflow(this.messenger, this.name, var1);
   }

   private final class IncomingMethodCallHandler implements BinaryMessenger.BinaryMessageHandler {
      private final MethodChannel.MethodCallHandler handler;
      final MethodChannel this$0;

      IncomingMethodCallHandler(MethodChannel var1, MethodChannel.MethodCallHandler var2) {
         this.this$0 = var1;
         this.handler = var2;
      }

      @Override
      public void onMessage(ByteBuffer var1, BinaryMessenger.BinaryReply var2) {
         MethodCall var4 = this.this$0.codec.decodeMethodCall(var1);

         try {
            MethodChannel.MethodCallHandler var3 = this.handler;
            MethodChannel.Result var7 = new MethodChannel.Result(this, var2) {
               final MethodChannel.IncomingMethodCallHandler this$1;
               final BinaryMessenger.BinaryReply val$reply;

               {
                  this.this$1 = var1;
                  this.val$reply = var2x;
               }

               @Override
               public void error(String var1, String var2x, Object var3x) {
                  this.val$reply.reply(this.this$1.this$0.codec.encodeErrorEnvelope(var1, var2x, var3x));
               }

               @Override
               public void notImplemented() {
                  this.val$reply.reply(null);
               }

               @Override
               public void success(Object var1) {
                  this.val$reply.reply(this.this$1.this$0.codec.encodeSuccessEnvelope(var1));
               }
            };
            var3.onMethodCall(var4, var7);
         } catch (RuntimeException var5) {
            StringBuilder var6 = new StringBuilder("MethodChannel#");
            var6.append(this.this$0.name);
            Log.e(var6.toString(), "Failed to handle method call", var5);
            var2.reply(this.this$0.codec.encodeErrorEnvelopeWithStacktrace("error", var5.getMessage(), null, Log.getStackTraceString(var5)));
         }
      }
   }

   private final class IncomingResultHandler implements BinaryMessenger.BinaryReply {
      private final MethodChannel.Result callback;
      final MethodChannel this$0;

      IncomingResultHandler(MethodChannel var1, MethodChannel.Result var2) {
         this.this$0 = var1;
         this.callback = var2;
      }

      @Override
      public void reply(ByteBuffer param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: parsing failure!
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
         //
         // Bytecode:
         // 00: aload 1
         // 01: ifnonnull 10
         // 04: aload 0
         // 05: getfield io/flutter/plugin/common/MethodChannel$IncomingResultHandler.callback Lio/flutter/plugin/common/MethodChannel$Result;
         // 08: invokeinterface io/flutter/plugin/common/MethodChannel$Result.notImplemented ()V 1
         // 0d: goto 66
         // 10: aload 0
         // 11: getfield io/flutter/plugin/common/MethodChannel$IncomingResultHandler.callback Lio/flutter/plugin/common/MethodChannel$Result;
         // 14: aload 0
         // 15: getfield io/flutter/plugin/common/MethodChannel$IncomingResultHandler.this$0 Lio/flutter/plugin/common/MethodChannel;
         // 18: invokestatic io/flutter/plugin/common/MethodChannel.access$000 (Lio/flutter/plugin/common/MethodChannel;)Lio/flutter/plugin/common/MethodCodec;
         // 1b: aload 1
         // 1c: invokeinterface io/flutter/plugin/common/MethodCodec.decodeEnvelope (Ljava/nio/ByteBuffer;)Ljava/lang/Object; 2
         // 21: invokeinterface io/flutter/plugin/common/MethodChannel$Result.success (Ljava/lang/Object;)V 2
         // 26: goto 66
         // 29: astore 1
         // 2a: goto 46
         // 2d: astore 1
         // 2e: aload 0
         // 2f: getfield io/flutter/plugin/common/MethodChannel$IncomingResultHandler.callback Lio/flutter/plugin/common/MethodChannel$Result;
         // 32: aload 1
         // 33: getfield io/flutter/plugin/common/FlutterException.code Ljava/lang/String;
         // 36: aload 1
         // 37: invokevirtual io/flutter/plugin/common/FlutterException.getMessage ()Ljava/lang/String;
         // 3a: aload 1
         // 3b: getfield io/flutter/plugin/common/FlutterException.details Ljava/lang/Object;
         // 3e: invokeinterface io/flutter/plugin/common/MethodChannel$Result.error (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V 4
         // 43: goto 66
         // 46: new java/lang/StringBuilder
         // 49: dup
         // 4a: ldc "MethodChannel#"
         // 4c: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 4f: astore 2
         // 50: aload 2
         // 51: aload 0
         // 52: getfield io/flutter/plugin/common/MethodChannel$IncomingResultHandler.this$0 Lio/flutter/plugin/common/MethodChannel;
         // 55: invokestatic io/flutter/plugin/common/MethodChannel.access$100 (Lio/flutter/plugin/common/MethodChannel;)Ljava/lang/String;
         // 58: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 5b: pop
         // 5c: aload 2
         // 5d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 60: ldc "Failed to handle method call result"
         // 62: aload 1
         // 63: invokestatic io/flutter/Log.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
         // 66: return
      }
   }

   public interface MethodCallHandler {
      void onMethodCall(MethodCall var1, MethodChannel.Result var2);
   }

   public interface Result {
      void error(String var1, String var2, Object var3);

      void notImplemented();

      void success(Object var1);
   }
}
