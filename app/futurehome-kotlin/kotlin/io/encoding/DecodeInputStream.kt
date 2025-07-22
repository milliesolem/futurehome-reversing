package kotlin.io.encoding

import java.io.IOException
import java.io.InputStream

private class DecodeInputStream(input: InputStream, base64: Base64) : InputStream {
   private final val input: InputStream
   private final val base64: Base64
   private final var isClosed: Boolean
   private final var isEOF: Boolean
   private final val singleByteBuffer: ByteArray
   private final val symbolBuffer: ByteArray
   private final val byteBuffer: ByteArray
   private final var byteBufferStartIndex: Int
   private final var byteBufferEndIndex: Int

   private final val byteBufferLength: Int
      private final get() {
         return this.byteBufferEndIndex - this.byteBufferStartIndex;
      }


   init {
      this.input = var1;
      this.base64 = var2;
      this.singleByteBuffer = new byte[1];
      this.symbolBuffer = new byte[1024];
      this.byteBuffer = new byte[1024];
   }

   private fun copyByteBufferInto(dst: ByteArray, dstOffset: Int, length: Int) {
      ArraysKt.copyInto(this.byteBuffer, var1, var2, this.byteBufferStartIndex, this.byteBufferStartIndex + var3);
      this.byteBufferStartIndex += var3;
      this.resetByteBufferIfEmpty();
   }

   private fun decodeSymbolBufferInto(dst: ByteArray, dstOffset: Int, dstEndIndex: Int, symbolBufferLength: Int): Int {
      this.byteBufferEndIndex = this.byteBufferEndIndex + this.base64.decodeIntoByteArray(this.symbolBuffer, this.byteBuffer, this.byteBufferEndIndex, 0, var4);
      var3 = Math.min(this.getByteBufferLength(), var3 - var2);
      this.copyByteBufferInto(var1, var2, var3);
      this.shiftByteBufferToStartIfNeeded();
      return var3;
   }

   private fun handlePaddingSymbol(symbolBufferLength: Int): Int {
      this.symbolBuffer[var1] = 61;
      if ((var1 and 3) == 2) {
         val var2: Int = this.readNextSymbol();
         if (var2 >= 0) {
            this.symbolBuffer[var1 + 1] = (byte)var2;
         }

         var1 += 2;
      } else {
         var1++;
      }

      return var1;
   }

   private fun readNextSymbol(): Int {
      if (!this.base64.isMimeScheme$kotlin_stdlib()) {
         return this.input.read();
      } else {
         val var1: Int;
         do {
            var1 = this.input.read();
         } while (var1 != -1 && !Base64Kt.isInMimeAlphabet(var1));

         return var1;
      }
   }

   private fun resetByteBufferIfEmpty() {
      if (this.byteBufferStartIndex == this.byteBufferEndIndex) {
         this.byteBufferStartIndex = 0;
         this.byteBufferEndIndex = 0;
      }
   }

   private fun shiftByteBufferToStartIfNeeded() {
      if (this.symbolBuffer.length / 4 * 3 > this.byteBuffer.length - this.byteBufferEndIndex) {
         ArraysKt.copyInto(this.byteBuffer, this.byteBuffer, 0, this.byteBufferStartIndex, this.byteBufferEndIndex);
         this.byteBufferEndIndex = this.byteBufferEndIndex - this.byteBufferStartIndex;
         this.byteBufferStartIndex = 0;
      }
   }

   public override fun close() {
      if (!this.isClosed) {
         this.isClosed = true;
         this.input.close();
      }
   }

   public override fun read(): Int {
      if (this.byteBufferStartIndex < this.byteBufferEndIndex) {
         val var4: Byte = this.byteBuffer[this.byteBufferStartIndex];
         this.byteBufferStartIndex++;
         this.resetByteBufferIfEmpty();
         return var4 and 255;
      } else {
         val var2: Int = this.read(this.singleByteBuffer, 0, 1);
         var var3: Int = -1;
         if (var2 != -1) {
            if (var2 != 1) {
               throw new IllegalStateException("Unreachable".toString());
            }

            var3 = this.singleByteBuffer[0] and 255;
         }

         return var3;
      }
   }

   public override fun read(destination: ByteArray, offset: Int, length: Int): Int {
      if (var2 >= 0 && var3 >= 0) {
         val var7: Int = var2 + var3;
         if (var2 + var3 <= var1.length) {
            if (this.isClosed) {
               throw new IOException("The input stream is closed.");
            }

            if (this.isEOF) {
               return -1;
            }

            if (var3 == 0) {
               return 0;
            }

            if (this.getByteBufferLength() >= var3) {
               this.copyByteBufferInto(var1, var2, var3);
               return var3;
            }

            var var4: Int = (var3 - this.getByteBufferLength() + 2) / 3 * 4;
            var var5: Int = var2;

            while (true) {
               if (this.isEOF || var4 <= 0) {
                  if (var5 == var2 && this.isEOF) {
                     var2 = -1;
                  } else {
                     var2 = var5 - var2;
                  }

                  return var2;
               }

               val var8: Int = Math.min(this.symbolBuffer.length, var4);
               var3 = 0;

               while (true) {
                  if (this.isEOF || var3 >= var8) {
                     if (!this.isEOF && var3 != var8) {
                        throw new IllegalStateException("Check failed.");
                     }

                     var4 -= var3;
                     var5 += this.decodeSymbolBufferInto(var1, var5, var7, var3);
                     break;
                  }

                  val var9: Int = this.readNextSymbol();
                  if (var9 != -1) {
                     if (var9 != 61) {
                        this.symbolBuffer[var3] = (byte)var9;
                        var3++;
                     } else {
                        var3 = this.handlePaddingSymbol(var3);
                        this.isEOF = true;
                     }
                  } else {
                     this.isEOF = true;
                  }
               }
            }
         }
      }

      val var11: StringBuilder = new StringBuilder("offset: ");
      var11.append(var2);
      var11.append(", length: ");
      var11.append(var3);
      var11.append(", buffer size: ");
      var11.append(var1.length);
      throw new IndexOutOfBoundsException(var11.toString());
   }
}
