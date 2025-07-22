package io.flutter.plugin.common;

// $VF: synthetic class
public final class BinaryMessenger$_CC {
   public static void $default$disableBufferingIncomingMessages(BinaryMessenger var0) {
      throw new UnsupportedOperationException("disableBufferingIncomingMessages not implemented.");
   }

   public static void $default$enableBufferingIncomingMessages(BinaryMessenger var0) {
      throw new UnsupportedOperationException("enableBufferingIncomingMessages not implemented.");
   }

   public static BinaryMessenger.TaskQueue $default$makeBackgroundTaskQueue(BinaryMessenger var0) {
      return var0.makeBackgroundTaskQueue(new BinaryMessenger.TaskQueueOptions());
   }

   public static BinaryMessenger.TaskQueue $default$makeBackgroundTaskQueue(BinaryMessenger var0, BinaryMessenger.TaskQueueOptions var1) {
      throw new UnsupportedOperationException("makeBackgroundTaskQueue not implemented.");
   }

   public static void $default$setMessageHandler(BinaryMessenger var0, String var1, BinaryMessenger.BinaryMessageHandler var2, BinaryMessenger.TaskQueue var3) {
      if (var3 == null) {
         var0.setMessageHandler(var1, var2);
      } else {
         throw new UnsupportedOperationException("setMessageHandler called with nonnull taskQueue is not supported.");
      }
   }
}
