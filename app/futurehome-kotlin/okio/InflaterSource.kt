package okio

import java.io.EOFException
import java.io.IOException
import java.util.zip.DataFormatException
import java.util.zip.Inflater

public class InflaterSource internal constructor(source: BufferedSource, inflater: Inflater) : Source {
   private final var bufferBytesHeldByInflater: Int
   private final var closed: Boolean
   private final val inflater: Inflater
   private final val source: BufferedSource

   init {
      this.source = var1;
      this.inflater = var2;
   }

   public constructor(source: Source, inflater: Inflater) : this(Okio.buffer(var1), var2)
   private fun releaseBytesAfterInflate() {
      if (this.bufferBytesHeldByInflater != 0) {
         val var2: Int = this.bufferBytesHeldByInflater - this.inflater.getRemaining();
         this.bufferBytesHeldByInflater -= var2;
         this.source.skip((long)var2);
      }
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      if (!this.closed) {
         this.inflater.end();
         this.closed = true;
         this.source.close();
      }
   }

   @Throws(java/io/IOException::class)
   public override fun read(sink: Buffer, byteCount: Long): Long {
      do {
         val var4: Long = this.readOrInflate(var1, var2);
         if (var4 > 0L) {
            return var4;
         }

         if (this.inflater.finished() || this.inflater.needsDictionary()) {
            return -1L;
         }
      } while (!this.source.exhausted());

      throw new EOFException("source exhausted prematurely");
   }

   @Throws(java/io/IOException::class)
   public fun readOrInflate(sink: Buffer, byteCount: Long): Long {
      val var16: Long;
      var var4: Int = if ((var16 = var2 - 0L) == 0L) 0 else (if (var16 < 0L) -1 else 1);
      if (var2 >= 0L) {
         if (!this.closed) {
            if (var4 == 0) {
               return 0L;
            } else {
               var var7: Segment;
               try {
                  var7 = var1.writableSegment$okio(1);
                  var4 = (int)Math.min(var2, (long)(8192 - var7.limit));
                  this.refill();
                  var4 = this.inflater.inflate(var7.data, var7.limit, var4);
                  this.releaseBytesAfterInflate();
               } catch (var11: DataFormatException) {
                  throw new IOException(var11);
               }

               if (var4 > 0) {
                  try {
                     var7.limit += var4;
                     var2 = var1.size();
                  } catch (var9: DataFormatException) {
                     throw new IOException(var9);
                  }

                  val var5: Long = var4;

                  try {
                     var1.setSize$okio(var2 + var5);
                     return var5;
                  } catch (var8: DataFormatException) {
                     throw new IOException(var8);
                  }
               } else {
                  try {
                     if (var7.pos == var7.limit) {
                        var1.head = var7.pop();
                        SegmentPool.recycle(var7);
                     }

                     return 0L;
                  } catch (var10: DataFormatException) {
                     throw new IOException(var10);
                  }
               }
            }
         } else {
            throw new IllegalStateException("closed".toString());
         }
      } else {
         val var12: StringBuilder = new StringBuilder("byteCount < 0: ");
         var12.append(var2);
         throw new IllegalArgumentException(var12.toString().toString());
      }
   }

   @Throws(java/io/IOException::class)
   public fun refill(): Boolean {
      if (!this.inflater.needsInput()) {
         return false;
      } else if (this.source.exhausted()) {
         return true;
      } else {
         val var1: Segment = this.source.getBuffer().head;
         this.bufferBytesHeldByInflater = var1.limit - var1.pos;
         this.inflater.setInput(var1.data, var1.pos, this.bufferBytesHeldByInflater);
         return false;
      }
   }

   public override fun timeout(): Timeout {
      return this.source.timeout();
   }
}
