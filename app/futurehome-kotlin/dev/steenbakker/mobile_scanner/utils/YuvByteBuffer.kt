package dev.steenbakker.mobile_scanner.utils

import android.media.Image
import android.media.Image.Plane
import java.nio.Buffer
import java.nio.ByteBuffer

public class YuvByteBuffer(image: Image, dstBuffer: ByteBuffer? = null) {
   public final val type: Int
   public final val buffer: ByteBuffer

   init {
      val var4: YuvByteBuffer.ImageWrapper = new YuvByteBuffer.ImageWrapper(var1);
      var var3: Byte;
      if (var4.getU().getPixelStride() == 1) {
         var3 = 35;
      } else {
         var3 = 17;
      }

      label19: {
         this.type = var3;
         var3 = var1.getWidth() * var1.getHeight() * 3 / 2;
         if (var2 != null && var2.capacity() >= var3 && !var2.isReadOnly()) {
            var5 = var2;
            if (var2.isDirect()) {
               break label19;
            }
         }

         var5 = ByteBuffer.allocateDirect(var3);
      }

      this.buffer = var5;
      ((Buffer)var5).rewind();
      this.removePadding(var4);
   }

   private fun clipBuffer(buffer: ByteBuffer, start: Int, size: Int): ByteBuffer {
      var1 = var1.duplicate();
      ((Buffer)var1).position(var2);
      ((Buffer)var1).limit(var2 + var3);
      var1 = var1.slice();
      return var1;
   }

   private fun removePadding(image: dev.steenbakker.mobile_scanner.utils.YuvByteBuffer.ImageWrapper) {
      var var3: Int = var1.getY().getWidth() * var1.getY().getHeight();
      val var4: Int = var1.getU().getWidth() * var1.getU().getHeight();
      if (var1.getY().getRowStride() > var1.getY().getWidth()) {
         this.removePaddingCompact(var1.getY(), this.buffer, 0);
      } else {
         ((Buffer)this.buffer).position(0);
         this.buffer.put(var1.getY().getBuffer());
      }

      if (this.type == 35) {
         if (var1.getU().getRowStride() > var1.getU().getWidth()) {
            this.removePaddingCompact(var1.getU(), this.buffer, var3);
            this.removePaddingCompact(var1.getV(), this.buffer, var3 + var4);
         } else {
            ((Buffer)this.buffer).position(var3);
            this.buffer.put(var1.getU().getBuffer());
            ((Buffer)this.buffer).position(var3 + var4);
            this.buffer.put(var1.getV().getBuffer());
         }
      } else if (var1.getU().getRowStride() > var1.getU().getWidth() * 2) {
         this.removePaddingNotCompact(var1, this.buffer, var3);
      } else {
         ((Buffer)this.buffer).position(var3);
         val var6: ByteBuffer = var1.getV().getBuffer();
         var3 = var1.getV().getHeight() * var1.getV().getRowStride() - 1;
         var var5: ByteBuffer = var6;
         if (var6.capacity() > var3) {
            var5 = this.clipBuffer(var1.getV().getBuffer(), 0, var3);
         }

         this.buffer.put(var5);
         this.buffer.put(this.buffer.capacity() - 1, var1.getU().getBuffer().get(var1.getU().getBuffer().capacity() - 1));
      }

      ((Buffer)this.buffer).rewind();
   }

   private fun removePaddingCompact(plane: dev.steenbakker.mobile_scanner.utils.YuvByteBuffer.PlaneWrapper, dst: ByteBuffer, offset: Int) {
      if (var1.getPixelStride() != 1) {
         throw new IllegalArgumentException("use removePaddingCompact with pixelStride == 1".toString());
      } else {
         val var6: ByteBuffer = var1.getBuffer();
         val var4: Int = var1.getRowStride();
         ((Buffer)var2).position(var3);
         val var5: Int = var1.getHeight();

         for (int var7 = 0; var7 < var5; var7++) {
            var2.put(this.clipBuffer(var6, var7 * var4, var1.getWidth()));
         }
      }
   }

   private fun removePaddingNotCompact(image: dev.steenbakker.mobile_scanner.utils.YuvByteBuffer.ImageWrapper, dst: ByteBuffer, offset: Int) {
      if (var1.getU().getPixelStride() != 2) {
         throw new IllegalArgumentException("use removePaddingNotCompact pixelStride == 2".toString());
      } else {
         val var5: Int = var1.getU().getWidth();
         var var6: Int = var1.getU().getHeight();
         val var4: Int = var1.getU().getRowStride();
         ((Buffer)var2).position(var3);
         var6--;

         for (int var7 = 0; var7 < var6; var7++) {
            var2.put(this.clipBuffer(var1.getV().getBuffer(), var7 * var4, var5 * 2));
         }

         var2.put(this.clipBuffer(var1.getU().getBuffer(), var6 * var4 - 1, var5 * 2));
      }
   }

   private class ImageWrapper(image: Image) {
      public final val width: Int
      public final val height: Int
      public final val y: dev.steenbakker.mobile_scanner.utils.YuvByteBuffer.PlaneWrapper
      public final val u: dev.steenbakker.mobile_scanner.utils.YuvByteBuffer.PlaneWrapper
      public final val v: dev.steenbakker.mobile_scanner.utils.YuvByteBuffer.PlaneWrapper

      init {
         val var4: Int = var1.getWidth();
         this.width = var4;
         var var2: Int = var1.getHeight();
         this.height = var2;
         val var6: Plane = var1.getPlanes()[0];
         val var13: YuvByteBuffer.PlaneWrapper = new YuvByteBuffer.PlaneWrapper(var4, var2, var6);
         this.y = var13;
         var var3: Int = var4 / 2;
         val var5: Int = var2 / 2;
         val var7: Plane = var1.getPlanes()[1];
         val var15: YuvByteBuffer.PlaneWrapper = new YuvByteBuffer.PlaneWrapper(var3, var5, var7);
         this.u = var15;
         var3 = var4 / 2;
         var2 = var2 / 2;
         val var8: Plane = var1.getPlanes()[2];
         val var9: YuvByteBuffer.PlaneWrapper = new YuvByteBuffer.PlaneWrapper(var3, var2, var8);
         this.v = var9;
         if (var13.getPixelStride() == 1) {
            if (var15.getPixelStride() != var9.getPixelStride() || var15.getRowStride() != var9.getRowStride()) {
               val var14: StringBuilder = new StringBuilder("U and V planes must have the same pixel and row strides but got pixel=");
               var14.append(var15.getPixelStride());
               var14.append(" row=");
               var14.append(var15.getRowStride());
               var14.append(" for U and pixel=");
               var14.append(var9.getPixelStride());
               var14.append(" and row=");
               var14.append(var9.getRowStride());
               var14.append(" for V");
               throw new IllegalArgumentException(var14.toString().toString());
            } else if (var15.getPixelStride() != 1 && var15.getPixelStride() != 2) {
               throw new IllegalArgumentException("Supported pixel strides for U and V planes are 1 and 2".toString());
            }
         } else {
            val var10: StringBuilder = new StringBuilder("Pixel stride for Y plane must be 1 but got ");
            var10.append(var13.getPixelStride());
            var10.append(" instead.");
            throw new IllegalArgumentException(var10.toString().toString());
         }
      }
   }

   private class PlaneWrapper(width: Int, height: Int, plane: Plane) {
      public final val width: Int
      public final val height: Int
      public final val buffer: ByteBuffer
      public final val rowStride: Int
      public final val pixelStride: Int

      init {
         this.width = var1;
         this.height = var2;
         val var4: ByteBuffer = var3.getBuffer();
         this.buffer = var4;
         this.rowStride = var3.getRowStride();
         this.pixelStride = var3.getPixelStride();
      }
   }
}
