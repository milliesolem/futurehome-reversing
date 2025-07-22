package kotlin.io.encoding

import java.io.IOException
import java.io.OutputStream

private class EncodeOutputStream(output: OutputStream, base64: Base64) : OutputStream {
   private final val output: OutputStream
   private final val base64: Base64
   private final var isClosed: Boolean
   private final var lineLength: Int
   private final val symbolBuffer: ByteArray
   private final val byteBuffer: ByteArray
   private final var byteBufferLength: Int

   init {
      this.output = var1;
      this.base64 = var2;
      val var3: Byte;
      if (var2.isMimeScheme$kotlin_stdlib()) {
         var3 = 76;
      } else {
         var3 = -1;
      }

      this.lineLength = var3;
      this.symbolBuffer = new byte[1024];
      this.byteBuffer = new byte[3];
   }

   private fun checkOpen() {
      if (this.isClosed) {
         throw new IOException("The output stream is closed.");
      }
   }

   private fun copyIntoByteBuffer(source: ByteArray, startIndex: Int, endIndex: Int): Int {
      var3 = Math.min(3 - this.byteBufferLength, var3 - var2);
      ArraysKt.copyInto(var1, this.byteBuffer, this.byteBufferLength, var2, var2 + var3);
      var2 = this.byteBufferLength + var3;
      this.byteBufferLength += var3;
      if (var2 == 3) {
         this.encodeByteBufferIntoOutput();
      }

      return var3;
   }

   private fun encodeByteBufferIntoOutput() {
      if (this.encodeIntoOutput(this.byteBuffer, 0, this.byteBufferLength) == 4) {
         this.byteBufferLength = 0;
      } else {
         throw new IllegalStateException("Check failed.");
      }
   }

   private fun encodeIntoOutput(source: ByteArray, startIndex: Int, endIndex: Int): Int {
      var2 = this.base64.encodeIntoByteArray(var1, this.symbolBuffer, 0, var2, var3);
      if (this.lineLength == 0) {
         this.output.write(Base64.Default.getMimeLineSeparatorSymbols$kotlin_stdlib());
         this.lineLength = 76;
         if (var2 > 76) {
            throw new IllegalStateException("Check failed.");
         }
      }

      this.output.write(this.symbolBuffer, 0, var2);
      this.lineLength -= var2;
      return var2;
   }

   public override fun close() {
      if (!this.isClosed) {
         this.isClosed = true;
         if (this.byteBufferLength != 0) {
            this.encodeByteBufferIntoOutput();
         }

         this.output.close();
      }
   }

   public override fun flush() {
      this.checkOpen();
      this.output.flush();
   }

   public override fun write(b: Int) {
      this.checkOpen();
      val var4: ByteArray = this.byteBuffer;
      val var3: Int = this.byteBufferLength;
      val var2: Int = this.byteBufferLength + 1;
      this.byteBufferLength++;
      var4[var3] = (byte)var1;
      if (var2 == 3) {
         this.encodeByteBufferIntoOutput();
      }
   }

   public override fun write(source: ByteArray, offset: Int, length: Int) {
      this.checkOpen();
      if (var2 >= 0 && var3 >= 0) {
         val var4: Int = var2 + var3;
         if (var2 + var3 <= var1.length) {
            if (var3 == 0) {
               return;
            }

            if (this.byteBufferLength < 3) {
               var3 = var2;
               if (this.byteBufferLength != 0) {
                  var3 = var2 + this.copyIntoByteBuffer(var1, var2, var4);
                  if (this.byteBufferLength != 0) {
                     return;
                  }
               }

               while (var3 + 3 <= var4) {
                  if (this.base64.isMimeScheme$kotlin_stdlib()) {
                     var2 = this.lineLength;
                  } else {
                     var2 = this.symbolBuffer.length;
                  }

                  val var10: Int = Math.min(var2 / 4, (var4 - var3) / 3);
                  var2 = var10 * 3 + var3;
                  if (this.encodeIntoOutput(var1, var3, var10 * 3 + var3) != var10 * 4) {
                     throw new IllegalStateException("Check failed.");
                  }

                  var3 = var2;
               }

               ArraysKt.copyInto(var1, this.byteBuffer, 0, var3, var4);
               this.byteBufferLength = var4 - var3;
               return;
            }

            throw new IllegalStateException("Check failed.");
         }
      }

      val var6: StringBuilder = new StringBuilder("offset: ");
      var6.append(var2);
      var6.append(", length: ");
      var6.append(var3);
      var6.append(", source size: ");
      var6.append(var1.length);
      throw new IndexOutOfBoundsException(var6.toString());
   }
}
