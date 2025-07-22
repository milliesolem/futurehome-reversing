package com.google.protobuf;

import java.nio.ByteBuffer;

@CheckReturnValue
abstract class AllocatedBuffer {
   public static AllocatedBuffer wrap(ByteBuffer var0) {
      Internal.checkNotNull(var0, "buffer");
      return new AllocatedBuffer(var0) {
         final ByteBuffer val$buffer;

         {
            this.val$buffer = var1;
         }

         @Override
         public byte[] array() {
            return this.val$buffer.array();
         }

         @Override
         public int arrayOffset() {
            return this.val$buffer.arrayOffset();
         }

         @Override
         public boolean hasArray() {
            return this.val$buffer.hasArray();
         }

         @Override
         public boolean hasNioBuffer() {
            return true;
         }

         @Override
         public int limit() {
            return this.val$buffer.limit();
         }

         @Override
         public ByteBuffer nioBuffer() {
            return this.val$buffer;
         }

         @Override
         public int position() {
            return this.val$buffer.position();
         }

         @Override
         public AllocatedBuffer position(int var1) {
            Java8Compatibility.position(this.val$buffer, var1);
            return this;
         }

         @Override
         public int remaining() {
            return this.val$buffer.remaining();
         }
      };
   }

   public static AllocatedBuffer wrap(byte[] var0) {
      return wrapNoCheck(var0, 0, var0.length);
   }

   public static AllocatedBuffer wrap(byte[] var0, int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 + var2 <= var0.length) {
         return wrapNoCheck(var0, var1, var2);
      } else {
         throw new IndexOutOfBoundsException(String.format("bytes.length=%d, offset=%d, length=%d", var0.length, var1, var2));
      }
   }

   private static AllocatedBuffer wrapNoCheck(byte[] var0, int var1, int var2) {
      return new AllocatedBuffer(var0, var1, var2) {
         private int position;
         final byte[] val$bytes;
         final int val$length;
         final int val$offset;

         {
            this.val$bytes = var1;
            this.val$offset = var2x;
            this.val$length = var3;
         }

         @Override
         public byte[] array() {
            return this.val$bytes;
         }

         @Override
         public int arrayOffset() {
            return this.val$offset;
         }

         @Override
         public boolean hasArray() {
            return true;
         }

         @Override
         public boolean hasNioBuffer() {
            return false;
         }

         @Override
         public int limit() {
            return this.val$length;
         }

         @Override
         public ByteBuffer nioBuffer() {
            throw new UnsupportedOperationException();
         }

         @Override
         public int position() {
            return this.position;
         }

         @Override
         public AllocatedBuffer position(int var1) {
            if (var1 >= 0 && var1 <= this.val$length) {
               this.position = var1;
               return this;
            } else {
               StringBuilder var2x = new StringBuilder("Invalid position: ");
               var2x.append(var1);
               throw new IllegalArgumentException(var2x.toString());
            }
         }

         @Override
         public int remaining() {
            return this.val$length - this.position;
         }
      };
   }

   public abstract byte[] array();

   public abstract int arrayOffset();

   public abstract boolean hasArray();

   public abstract boolean hasNioBuffer();

   public abstract int limit();

   public abstract ByteBuffer nioBuffer();

   public abstract int position();

   public abstract AllocatedBuffer position(int var1);

   public abstract int remaining();
}
