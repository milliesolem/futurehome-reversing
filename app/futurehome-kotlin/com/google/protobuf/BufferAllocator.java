package com.google.protobuf;

import java.nio.ByteBuffer;

@CheckReturnValue
abstract class BufferAllocator {
   private static final BufferAllocator UNPOOLED = new BufferAllocator() {
      @Override
      public AllocatedBuffer allocateDirectBuffer(int var1) {
         return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(var1));
      }

      @Override
      public AllocatedBuffer allocateHeapBuffer(int var1) {
         return AllocatedBuffer.wrap(new byte[var1]);
      }
   };

   public static BufferAllocator unpooled() {
      return UNPOOLED;
   }

   public abstract AllocatedBuffer allocateDirectBuffer(int var1);

   public abstract AllocatedBuffer allocateHeapBuffer(int var1);
}
