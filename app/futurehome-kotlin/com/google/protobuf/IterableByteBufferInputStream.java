package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

class IterableByteBufferInputStream extends InputStream {
   private long currentAddress;
   private byte[] currentArray;
   private int currentArrayOffset;
   private ByteBuffer currentByteBuffer;
   private int currentByteBufferPos;
   private int currentIndex;
   private int dataSize;
   private boolean hasArray;
   private Iterator<ByteBuffer> iterator;

   IterableByteBufferInputStream(Iterable<ByteBuffer> var1) {
      this.iterator = var1.iterator();
      this.dataSize = 0;

      for (ByteBuffer var2 : var1) {
         this.dataSize++;
      }

      this.currentIndex = -1;
      if (!this.getNextByteBuffer()) {
         this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
         this.currentIndex = 0;
         this.currentByteBufferPos = 0;
         this.currentAddress = 0L;
      }
   }

   private boolean getNextByteBuffer() {
      this.currentIndex++;
      if (!this.iterator.hasNext()) {
         return false;
      } else {
         ByteBuffer var1 = this.iterator.next();
         this.currentByteBuffer = var1;
         this.currentByteBufferPos = var1.position();
         if (this.currentByteBuffer.hasArray()) {
            this.hasArray = true;
            this.currentArray = this.currentByteBuffer.array();
            this.currentArrayOffset = this.currentByteBuffer.arrayOffset();
         } else {
            this.hasArray = false;
            this.currentAddress = UnsafeUtil.addressOffset(this.currentByteBuffer);
            this.currentArray = null;
         }

         return true;
      }
   }

   private void updateCurrentByteBufferPos(int var1) {
      var1 = this.currentByteBufferPos + var1;
      this.currentByteBufferPos = var1;
      if (var1 == this.currentByteBuffer.limit()) {
         this.getNextByteBuffer();
      }
   }

   @Override
   public int read() throws IOException {
      if (this.currentIndex == this.dataSize) {
         return -1;
      } else if (this.hasArray) {
         byte var2 = this.currentArray[this.currentByteBufferPos + this.currentArrayOffset];
         this.updateCurrentByteBufferPos(1);
         return var2 & 0xFF;
      } else {
         byte var1 = UnsafeUtil.getByte(this.currentByteBufferPos + this.currentAddress);
         this.updateCurrentByteBufferPos(1);
         return var1 & 0xFF;
      }
   }

   @Override
   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this.currentIndex == this.dataSize) {
         return -1;
      } else {
         int var4 = this.currentByteBuffer.limit();
         int var6 = this.currentByteBufferPos;
         int var5 = var4 - var6;
         var4 = var3;
         if (var3 > var5) {
            var4 = var5;
         }

         if (this.hasArray) {
            System.arraycopy(this.currentArray, var6 + this.currentArrayOffset, var1, var2, var4);
            this.updateCurrentByteBufferPos(var4);
         } else {
            var3 = this.currentByteBuffer.position();
            Java8Compatibility.position(this.currentByteBuffer, this.currentByteBufferPos);
            this.currentByteBuffer.get(var1, var2, var4);
            Java8Compatibility.position(this.currentByteBuffer, var3);
            this.updateCurrentByteBufferPos(var4);
         }

         return var4;
      }
   }
}
