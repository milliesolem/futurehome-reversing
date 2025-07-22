package okio

import java.util.zip.Deflater

public class DeflaterSink internal constructor(sink: BufferedSink, deflater: Deflater) : Sink {
   private final var closed: Boolean
   private final val deflater: Deflater
   private final val sink: BufferedSink

   init {
      this.sink = var1;
      this.deflater = var2;
   }

   public constructor(sink: Sink, deflater: Deflater) : this(Okio.buffer(var1), var2)
   private fun deflate(syncFlush: Boolean) {
      val var3: Buffer = this.sink.getBuffer();

      while (true) {
         val var4: Segment = var3.writableSegment$okio(1);
         val var2: Int;
         if (var1) {
            var2 = this.deflater.deflate(var4.data, var4.limit, 8192 - var4.limit, 2);
         } else {
            var2 = this.deflater.deflate(var4.data, var4.limit, 8192 - var4.limit);
         }

         if (var2 > 0) {
            var4.limit += var2;
            var3.setSize$okio(var3.size() + (long)var2);
            this.sink.emitCompleteSegments();
         } else if (this.deflater.needsInput()) {
            if (var4.pos == var4.limit) {
               var3.head = var4.pop();
               SegmentPool.recycle(var4);
            }

            return;
         }
      }
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      if (!this.closed) {
         label41:
         try {
            this.finishDeflate$okio();
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

   internal fun finishDeflate() {
      this.deflater.finish();
      this.deflate(false);
   }

   @Throws(java/io/IOException::class)
   public override fun flush() {
      this.deflate(true);
      this.sink.flush();
   }

   public override fun timeout(): Timeout {
      return this.sink.timeout();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("DeflaterSink(");
      var1.append(this.sink);
      var1.append(')');
      return var1.toString();
   }

   @Throws(java/io/IOException::class)
   public override fun write(source: Buffer, byteCount: Long) {
      _SegmentedByteString.checkOffsetAndCount(var1.size(), 0L, var2);

      while (var2 > 0L) {
         val var9: Segment = var1.head;
         val var4: Int = (int)Math.min(var2, (long)(var9.limit - var9.pos));
         this.deflater.setInput(var9.data, var9.pos, var4);
         this.deflate(false);
         val var7: Long = var1.size();
         val var5: Long = var4;
         var1.setSize$okio(var7 - (long)var4);
         var9.pos += var4;
         if (var9.pos == var9.limit) {
            var1.head = var9.pop();
            SegmentPool.recycle(var9);
         }

         var2 -= var5;
      }
   }
}
