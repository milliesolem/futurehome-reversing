package io.flutter.plugin.common;

import io.flutter.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class BasicMessageChannel<T> {
   public static final String CHANNEL_BUFFERS_CHANNEL = "dev.flutter/channel-buffers";
   private static final String TAG = "BasicMessageChannel#";
   private final MessageCodec<T> codec;
   private final BinaryMessenger messenger;
   private final String name;
   private final BinaryMessenger.TaskQueue taskQueue;

   public BasicMessageChannel(BinaryMessenger var1, String var2, MessageCodec<T> var3) {
      this(var1, var2, var3, null);
   }

   public BasicMessageChannel(BinaryMessenger var1, String var2, MessageCodec<T> var3, BinaryMessenger.TaskQueue var4) {
      this.messenger = var1;
      this.name = var2;
      this.codec = var3;
      this.taskQueue = var4;
   }

   private static ByteBuffer packetFromEncodedMessage(ByteBuffer var0) {
      ((Buffer)var0).flip();
      int var1 = var0.remaining();
      byte[] var2 = new byte[var1];
      var0.get(var2);
      var0 = ByteBuffer.allocateDirect(var1);
      var0.put(var2);
      return var0;
   }

   public static void resizeChannelBuffer(BinaryMessenger var0, String var1, int var2) {
      var0.send(
         "dev.flutter/channel-buffers",
         packetFromEncodedMessage(StandardMethodCodec.INSTANCE.encodeMethodCall(new MethodCall("resize", Arrays.asList(var1, var2))))
      );
   }

   public static void setWarnsOnChannelOverflow(BinaryMessenger var0, String var1, boolean var2) {
      var0.send(
         "dev.flutter/channel-buffers",
         packetFromEncodedMessage(StandardMethodCodec.INSTANCE.encodeMethodCall(new MethodCall("overflow", Arrays.asList(var1, var2 ^ true))))
      );
   }

   public void resizeChannelBuffer(int var1) {
      resizeChannelBuffer(this.messenger, this.name, var1);
   }

   public void send(T var1) {
      this.send((T)var1, null);
   }

   public void send(T var1, BasicMessageChannel.Reply<T> var2) {
      BinaryMessenger var4 = this.messenger;
      String var3 = this.name;
      ByteBuffer var5 = this.codec.encodeMessage((T)var1);
      var1 = null;
      if (var2 != null) {
         var1 = new BasicMessageChannel.IncomingReplyHandler(this, var2);
      }

      var4.send(var3, var5, var1);
   }

   public void setMessageHandler(BasicMessageChannel.MessageHandler<T> var1) {
      BinaryMessenger.TaskQueue var4 = this.taskQueue;
      BinaryMessenger var3 = null;
      Object var2 = null;
      if (var4 != null) {
         var3 = this.messenger;
         String var9 = this.name;
         BasicMessageChannel.IncomingMessageHandler var5;
         if (var1 == null) {
            var5 = (BasicMessageChannel.IncomingMessageHandler)var2;
         } else {
            var5 = new BasicMessageChannel.IncomingMessageHandler(this, var1);
         }

         var3.setMessageHandler(var9, var5, this.taskQueue);
      } else {
         BinaryMessenger var10 = this.messenger;
         var2 = this.name;
         BasicMessageChannel.IncomingMessageHandler var6;
         if (var1 == null) {
            var6 = var3;
         } else {
            var6 = new BasicMessageChannel.IncomingMessageHandler(this, var1);
         }

         var10.setMessageHandler((String)var2, var6);
      }
   }

   public void setWarnsOnChannelOverflow(boolean var1) {
      setWarnsOnChannelOverflow(this.messenger, this.name, var1);
   }

   private final class IncomingMessageHandler implements BinaryMessenger.BinaryMessageHandler {
      private final BasicMessageChannel.MessageHandler<T> handler;
      final BasicMessageChannel this$0;

      private IncomingMessageHandler(BasicMessageChannel.MessageHandler<T> var1, BasicMessageChannel.MessageHandler var2) {
         this.this$0 = var1;
         this.handler = var2;
      }

      @Override
      public void onMessage(ByteBuffer var1, BinaryMessenger.BinaryReply var2) {
         try {
            BasicMessageChannel.MessageHandler var3 = this.handler;
            Object var4 = this.this$0.codec.decodeMessage(var1);
            BasicMessageChannel.Reply var7 = new BasicMessageChannel.Reply<T>(this, var2) {
               final BasicMessageChannel.IncomingMessageHandler this$1;
               final BinaryMessenger.BinaryReply val$callback;

               {
                  this.this$1 = var1;
                  this.val$callback = var2x;
               }

               @Override
               public void reply(T var1) {
                  this.val$callback.reply(this.this$1.this$0.codec.encodeMessage((T)var1));
               }
            };
            var3.onMessage(var4, var7);
         } catch (RuntimeException var5) {
            StringBuilder var6 = new StringBuilder("BasicMessageChannel#");
            var6.append(this.this$0.name);
            Log.e(var6.toString(), "Failed to handle message", var5);
            var2.reply(null);
         }
      }
   }

   private final class IncomingReplyHandler implements BinaryMessenger.BinaryReply {
      private final BasicMessageChannel.Reply<T> callback;
      final BasicMessageChannel this$0;

      private IncomingReplyHandler(BasicMessageChannel.Reply<T> var1, BasicMessageChannel.Reply var2) {
         this.this$0 = var1;
         this.callback = var2;
      }

      @Override
      public void reply(ByteBuffer var1) {
         try {
            this.callback.reply(this.this$0.codec.decodeMessage(var1));
         } catch (RuntimeException var3) {
            StringBuilder var4 = new StringBuilder("BasicMessageChannel#");
            var4.append(this.this$0.name);
            Log.e(var4.toString(), "Failed to handle message reply", var3);
         }
      }
   }

   public interface MessageHandler<T> {
      void onMessage(T var1, BasicMessageChannel.Reply<T> var2);
   }

   public interface Reply<T> {
      void reply(T var1);
   }
}
