package io.flutter.plugin.common;

import io.flutter.Log;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class EventChannel {
   private static final String TAG = "EventChannel#";
   private final MethodCodec codec;
   private final BinaryMessenger messenger;
   private final String name;
   private final BinaryMessenger.TaskQueue taskQueue;

   public EventChannel(BinaryMessenger var1, String var2) {
      this(var1, var2, StandardMethodCodec.INSTANCE);
   }

   public EventChannel(BinaryMessenger var1, String var2, MethodCodec var3) {
      this(var1, var2, var3, null);
   }

   public EventChannel(BinaryMessenger var1, String var2, MethodCodec var3, BinaryMessenger.TaskQueue var4) {
      this.messenger = var1;
      this.name = var2;
      this.codec = var3;
      this.taskQueue = var4;
   }

   public void setStreamHandler(EventChannel.StreamHandler var1) {
      BinaryMessenger.TaskQueue var4 = this.taskQueue;
      BinaryMessenger var3 = null;
      Object var2 = null;
      if (var4 != null) {
         var3 = this.messenger;
         String var9 = this.name;
         EventChannel.IncomingStreamRequestHandler var5;
         if (var1 == null) {
            var5 = (EventChannel.IncomingStreamRequestHandler)var2;
         } else {
            var5 = new EventChannel.IncomingStreamRequestHandler(this, var1);
         }

         var3.setMessageHandler(var9, var5, this.taskQueue);
      } else {
         BinaryMessenger var10 = this.messenger;
         var2 = this.name;
         EventChannel.IncomingStreamRequestHandler var6;
         if (var1 == null) {
            var6 = var3;
         } else {
            var6 = new EventChannel.IncomingStreamRequestHandler(this, var1);
         }

         var10.setMessageHandler((String)var2, var6);
      }
   }

   public interface EventSink {
      void endOfStream();

      void error(String var1, String var2, Object var3);

      void success(Object var1);
   }

   private final class IncomingStreamRequestHandler implements BinaryMessenger.BinaryMessageHandler {
      private final AtomicReference<EventChannel.EventSink> activeSink;
      private final EventChannel.StreamHandler handler;
      final EventChannel this$0;

      IncomingStreamRequestHandler(EventChannel var1, EventChannel.StreamHandler var2) {
         this.this$0 = var1;
         this.activeSink = new AtomicReference<>(null);
         this.handler = var2;
      }

      private void onCancel(Object var1, BinaryMessenger.BinaryReply var2) {
         if (this.activeSink.getAndSet(null) != null) {
            try {
               this.handler.onCancel(var1);
               var2.reply(this.this$0.codec.encodeSuccessEnvelope(null));
            } catch (RuntimeException var4) {
               StringBuilder var3 = new StringBuilder("EventChannel#");
               var3.append(this.this$0.name);
               Log.e(var3.toString(), "Failed to close event stream", var4);
               var2.reply(this.this$0.codec.encodeErrorEnvelope("error", var4.getMessage(), null));
            }
         } else {
            var2.reply(this.this$0.codec.encodeErrorEnvelope("error", "No active stream to cancel", null));
         }
      }

      private void onListen(Object var1, BinaryMessenger.BinaryReply var2) {
         EventChannel.IncomingStreamRequestHandler.EventSinkImplementation var4 = new EventChannel.IncomingStreamRequestHandler.EventSinkImplementation(this);
         if (this.activeSink.getAndSet(var4) != null) {
            try {
               this.handler.onCancel(null);
            } catch (RuntimeException var7) {
               StringBuilder var3 = new StringBuilder("EventChannel#");
               var3.append(this.this$0.name);
               Log.e(var3.toString(), "Failed to close existing event stream", var7);
            }
         }

         try {
            this.handler.onListen(var1, var4);
            var2.reply(this.this$0.codec.encodeSuccessEnvelope(null));
         } catch (RuntimeException var6) {
            this.activeSink.set(null);
            var1 = new StringBuilder("EventChannel#");
            var1.append(this.this$0.name);
            Log.e(var1.toString(), "Failed to open event stream", var6);
            var2.reply(this.this$0.codec.encodeErrorEnvelope("error", var6.getMessage(), null));
         }
      }

      @Override
      public void onMessage(ByteBuffer var1, BinaryMessenger.BinaryReply var2) {
         MethodCall var3 = this.this$0.codec.decodeMethodCall(var1);
         if (var3.method.equals("listen")) {
            this.onListen(var3.arguments, var2);
         } else if (var3.method.equals("cancel")) {
            this.onCancel(var3.arguments, var2);
         } else {
            var2.reply(null);
         }
      }

      private final class EventSinkImplementation implements EventChannel.EventSink {
         final AtomicBoolean hasEnded;
         final EventChannel.IncomingStreamRequestHandler this$1;

         private EventSinkImplementation(EventChannel.IncomingStreamRequestHandler var1) {
            this.this$1 = var1;
            this.hasEnded = new AtomicBoolean(false);
         }

         @Override
         public void endOfStream() {
            if (!this.hasEnded.getAndSet(true) && this.this$1.activeSink.get() == this) {
               this.this$1.this$0.messenger.send(this.this$1.this$0.name, null);
            }
         }

         @Override
         public void error(String var1, String var2, Object var3) {
            if (!this.hasEnded.get() && this.this$1.activeSink.get() == this) {
               this.this$1.this$0.messenger.send(this.this$1.this$0.name, this.this$1.this$0.codec.encodeErrorEnvelope(var1, var2, var3));
            }
         }

         @Override
         public void success(Object var1) {
            if (!this.hasEnded.get() && this.this$1.activeSink.get() == this) {
               this.this$1.this$0.messenger.send(this.this$1.this$0.name, this.this$1.this$0.codec.encodeSuccessEnvelope(var1));
            }
         }
      }
   }

   public interface StreamHandler {
      void onCancel(Object var1);

      void onListen(Object var1, EventChannel.EventSink var2);
   }
}
