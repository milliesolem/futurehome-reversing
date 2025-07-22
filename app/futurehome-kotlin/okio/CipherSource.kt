package okio

import javax.crypto.Cipher

public class CipherSource(source: BufferedSource, cipher: Cipher) : Source {
   private final val blockSize: Int
   private final val buffer: Buffer
   public final val cipher: Cipher
   private final var closed: Boolean
   private final var final: Boolean
   private final val source: BufferedSource

   init {
      this.source = var1;
      this.cipher = var2;
      val var3: Int = var2.getBlockSize();
      this.blockSize = var3;
      this.buffer = new Buffer();
      if (var3 <= 0) {
         val var4: StringBuilder = new StringBuilder("Block cipher required ");
         var4.append(var2);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   private fun doFinal() {
      var var1: Int = this.cipher.getOutputSize(0);
      if (var1 != 0) {
         val var3: Segment = this.buffer.writableSegment$okio(var1);
         var1 = this.cipher.doFinal(var3.data, var3.pos);
         var3.limit += var1;
         this.buffer.setSize$okio(this.buffer.size() + (long)var1);
         if (var3.pos == var3.limit) {
            this.buffer.head = var3.pop();
            SegmentPool.recycle(var3);
         }
      }
   }

   private fun refill() {
      while (this.buffer.size() == 0L && !this.final) {
         if (this.source.exhausted()) {
            this.final = true;
            this.doFinal();
            break;
         }

         this.update();
      }
   }

   private fun update() {
      val var4: Segment = this.source.getBuffer().head;
      var var1: Int = var4.limit - var4.pos;

      var var2: Int;
      for (var2 = this.cipher.getOutputSize(var4.limit - var4.pos); var2 > 8192; var2 = this.cipher.getOutputSize(var1)) {
         if (var1 <= this.blockSize) {
            this.final = true;
            val var8: Buffer = this.buffer;
            val var3: ByteArray = this.cipher.doFinal(this.source.readByteArray());
            var8.write(var3);
            return;
         }

         var1 -= this.blockSize;
      }

      val var7: Segment = this.buffer.writableSegment$okio(var2);
      var2 = this.cipher.update(var4.data, var4.pos, var1, var7.data, var7.pos);
      this.source.skip((long)var1);
      var7.limit += var2;
      this.buffer.setSize$okio(this.buffer.size() + (long)var2);
      if (var7.pos == var7.limit) {
         this.buffer.head = var7.pop();
         SegmentPool.recycle(var7);
      }
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      this.closed = true;
      this.source.close();
   }

   @Throws(java/io/IOException::class)
   public override fun read(sink: Buffer, byteCount: Long): Long {
      val var6: Long;
      val var4: Byte = (byte)(if ((var6 = var2 - 0L) == 0L) 0 else (if (var6 < 0L) -1 else 1));
      if (var2 >= 0L) {
         if (!this.closed) {
            if (var4 == 0) {
               return 0L;
            } else {
               this.refill();
               return this.buffer.read(var1, var2);
            }
         } else {
            throw new IllegalStateException("closed".toString());
         }
      } else {
         val var5: StringBuilder = new StringBuilder("byteCount < 0: ");
         var5.append(var2);
         throw new IllegalArgumentException(var5.toString().toString());
      }
   }

   public override fun timeout(): Timeout {
      return this.source.timeout();
   }
}
