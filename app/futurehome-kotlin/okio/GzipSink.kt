package okio

import java.util.zip.CRC32
import java.util.zip.Deflater

public class GzipSink(sink: Sink) : Sink {
   private final var closed: Boolean
   private final val crc: CRC32

   public final val deflater: Deflater
      public final get() {
         return this.deflater;
      }


   private final val deflaterSink: DeflaterSink
   private final val sink: RealBufferedSink

   init {
      val var2: RealBufferedSink = new RealBufferedSink(var1);
      this.sink = var2;
      val var3: Deflater = new Deflater(-1, true);
      this.deflater = var3;
      this.deflaterSink = new DeflaterSink(var2, var3);
      this.crc = new CRC32();
      val var4: Buffer = var2.bufferField;
      var2.bufferField.writeShort(8075);
      var4.writeByte(8);
      var4.writeByte(0);
      var4.writeInt(0);
      var4.writeByte(0);
      var4.writeByte(0);
   }

   private fun updateCrc(buffer: Buffer, byteCount: Long) {
      var var5: Segment = var1.head;

      while (var2 > 0L) {
         val var4: Int = (int)Math.min(var2, (long)(var5.limit - var5.pos));
         this.crc.update(var5.data, var5.pos, var4);
         var2 -= var4;
         var5 = var5.next;
      }
   }

   private fun writeFooter() {
      this.sink.writeIntLe((int)this.crc.getValue());
      this.sink.writeIntLe((int)this.deflater.getBytesRead());
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "deflater", imports = []))
   public fun deflater(): Deflater {
      return this.deflater;
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      if (!this.closed) {
         label41:
         try {
            this.deflaterSink.finishDeflate$okio();
            this.writeFooter();
         } catch (var6: java.lang.Throwable) {
            break label41;
         }

         var var1: java.lang.Throwable;
         label38: {
            try {
               this.deflater.end();
            } catch (var5: java.lang.Throwable) {
               val var2: Any;
               var1 = (java.lang.Throwable)var2;
               if (var2 == null) {
                  var1 = var5;
               }
               break label38;
            }

            val var16: Any;
            var1 = (java.lang.Throwable)var16;
         }

         var var17: java.lang.Throwable;
         label31: {
            try {
               this.sink.close();
            } catch (var4: java.lang.Throwable) {
               var17 = var1;
               if (var1 == null) {
                  var17 = var4;
               }
               break label31;
            }

            var17 = var1;
         }

         this.closed = true;
         if (var17 != null) {
            throw var17;
         }
      }
   }

   @Throws(java/io/IOException::class)
   public override fun flush() {
      this.deflaterSink.flush();
   }

   public override fun timeout(): Timeout {
      return this.sink.timeout();
   }

   @Throws(java/io/IOException::class)
   public override fun write(source: Buffer, byteCount: Long) {
      val var6: Long;
      val var4: Byte = (byte)(if ((var6 = var2 - 0L) == 0L) 0 else (if (var6 < 0L) -1 else 1));
      if (var2 >= 0L) {
         if (var4 != 0) {
            this.updateCrc(var1, var2);
            this.deflaterSink.write(var1, var2);
         }
      } else {
         val var5: StringBuilder = new StringBuilder("byteCount < 0: ");
         var5.append(var2);
         throw new IllegalArgumentException(var5.toString().toString());
      }
   }
}
