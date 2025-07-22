package okio

import java.io.EOFException
import java.io.IOException
import java.util.Arrays
import java.util.zip.CRC32
import java.util.zip.Inflater

public class GzipSource(source: Source) : Source {
   private final val crc: CRC32
   private final val inflater: Inflater
   private final val inflaterSource: InflaterSource
   private final var section: Byte
   private final val source: RealBufferedSource

   init {
      val var3: RealBufferedSource = new RealBufferedSource(var1);
      this.source = var3;
      val var2: Inflater = new Inflater(true);
      this.inflater = var2;
      this.inflaterSource = new InflaterSource(var3, var2);
      this.crc = new CRC32();
   }

   private fun checkEqual(name: String, expected: Int, actual: Int) {
      if (var3 != var2) {
         var1 = java.lang.String.format("%s: actual 0x%08x != expected 0x%08x", Arrays.copyOf(new Object[]{var1, var3, var2}, 3));
         throw new IOException(var1);
      }
   }

   @Throws(java/io/IOException::class)
   private fun consumeHeader() {
      this.source.require(10L);
      val var2: Byte = this.source.bufferField.getByte(3L);
      val var1: Boolean;
      if ((var2 shr 1 and 1) == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         this.updateCrc(this.source.bufferField, 0L, 10L);
      }

      this.checkEqual("ID1ID2", 8075, this.source.readShort());
      this.source.skip(8L);
      if ((var2 shr 2 and 1) == 1) {
         this.source.require(2L);
         if (var1) {
            this.updateCrc(this.source.bufferField, 0L, 2L);
         }

         val var3: Long = this.source.bufferField.readShortLe() and '\uffff';
         this.source.require(var3);
         if (var1) {
            this.updateCrc(this.source.bufferField, 0L, var3);
         }

         this.source.skip(var3);
      }

      if ((var2 shr 3 and 1) == 1) {
         val var5: Long = this.source.indexOf((byte)0);
         if (var5 == -1L) {
            throw new EOFException();
         }

         if (var1) {
            this.updateCrc(this.source.bufferField, 0L, var5 + 1L);
         }

         this.source.skip(var5 + 1L);
      }

      if ((var2 shr 4 and 1) == 1) {
         val var6: Long = this.source.indexOf((byte)0);
         if (var6 == -1L) {
            throw new EOFException();
         }

         if (var1) {
            this.updateCrc(this.source.bufferField, 0L, var6 + 1L);
         }

         this.source.skip(var6 + 1L);
      }

      if (var1) {
         this.checkEqual("FHCRC", this.source.readShortLe(), (short)((int)this.crc.getValue()));
         this.crc.reset();
      }
   }

   @Throws(java/io/IOException::class)
   private fun consumeTrailer() {
      this.checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
      this.checkEqual("ISIZE", this.source.readIntLe(), (int)this.inflater.getBytesWritten());
   }

   private fun updateCrc(buffer: Buffer, offset: Long, byteCount: Long) {
      var var13: Segment = var1.head;

      while (true) {
         var var12: Segment = var13;
         var var8: Long = var2;
         var var10: Long = var4;
         if (var2 < var13.limit - var13.pos) {
            while (var10 > 0L) {
               val var6: Int = (int)(var12.pos + var8);
               val var7: Int = (int)Math.min((long)(var12.limit - (int)((long)var12.pos + var8)), var10);
               this.crc.update(var12.data, var6, var7);
               var10 -= var7;
               var12 = var12.next;
               var8 = 0L;
            }

            return;
         }

         var2 -= var13.limit - var13.pos;
         var13 = var13.next;
      }
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      this.inflaterSource.close();
   }

   @Throws(java/io/IOException::class)
   public override fun read(sink: Buffer, byteCount: Long): Long {
      val var9: Long;
      val var4: Byte = (byte)(if ((var9 = var2 - 0L) == 0L) 0 else (if (var9 < 0L) -1 else 1));
      if (var2 >= 0L) {
         if (var4 == 0) {
            return 0L;
         } else {
            if (this.section == 0) {
               this.consumeHeader();
               this.section = 1;
            }

            if (this.section == 1) {
               val var5: Long = var1.size();
               var2 = this.inflaterSource.read(var1, var2);
               if (var2 != -1L) {
                  this.updateCrc(var1, var5, var2);
                  return var2;
               }

               this.section = 2;
            }

            if (this.section == 2) {
               this.consumeTrailer();
               this.section = 3;
               if (!this.source.exhausted()) {
                  throw new IOException("gzip finished without exhausting source");
               }
            }

            return -1L;
         }
      } else {
         val var7: StringBuilder = new StringBuilder("byteCount < 0: ");
         var7.append(var2);
         throw new IllegalArgumentException(var7.toString().toString());
      }
   }

   public override fun timeout(): Timeout {
      return this.source.timeout();
   }
}
