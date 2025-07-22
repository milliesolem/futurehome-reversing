package io.flutter.plugin.common;

import java.nio.ByteBuffer;

public interface BinaryMessenger {
   void disableBufferingIncomingMessages();

   void enableBufferingIncomingMessages();

   BinaryMessenger.TaskQueue makeBackgroundTaskQueue();

   BinaryMessenger.TaskQueue makeBackgroundTaskQueue(BinaryMessenger.TaskQueueOptions var1);

   void send(String var1, ByteBuffer var2);

   void send(String var1, ByteBuffer var2, BinaryMessenger.BinaryReply var3);

   void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2);

   void setMessageHandler(String var1, BinaryMessenger.BinaryMessageHandler var2, BinaryMessenger.TaskQueue var3);

   public interface BinaryMessageHandler {
      void onMessage(ByteBuffer var1, BinaryMessenger.BinaryReply var2);
   }

   public interface BinaryReply {
      void reply(ByteBuffer var1);
   }

   public interface TaskQueue {
   }

   public static class TaskQueueOptions {
      private boolean isSerial = true;

      public boolean getIsSerial() {
         return this.isSerial;
      }

      public BinaryMessenger.TaskQueueOptions setIsSerial(boolean var1) {
         this.isSerial = var1;
         return this;
      }
   }
}
