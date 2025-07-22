package okio

internal class PeekSource(upstream: BufferedSource) : Source {
   private final val buffer: Buffer
   private final var closed: Boolean
   private final var expectedPos: Int
   private final var expectedSegment: Segment?
   private final var pos: Long
   private final val upstream: BufferedSource

   init {
      this.upstream = var1;
      val var3: Buffer = var1.getBuffer();
      this.buffer = var3;
      this.expectedSegment = var3.head;
      val var2: Int;
      if (var3.head != null) {
         var2 = var3.head.pos;
      } else {
         var2 = -1;
      }

      this.expectedPos = var2;
   }

   public override fun close() {
      this.closed = true;
   }

   public override fun read(sink: Buffer, byteCount: Long): Long {
      val var11: Long;
      val var4: Byte = (byte)(if ((var11 = var2 - 0L) == 0L) 0 else (if (var11 < 0L) -1 else 1));
      if (var2 >= 0L) {
         if (this.closed) {
            throw new IllegalStateException("closed".toString());
         } else {
            if (this.expectedSegment != null) {
               if (this.expectedSegment != this.buffer.head) {
                  throw new IllegalStateException("Peek source is invalid because upstream source was used".toString());
               }

               val var5: Int = this.expectedPos;
               val var9: Segment = this.buffer.head;
               if (var5 != var9.pos) {
                  throw new IllegalStateException("Peek source is invalid because upstream source was used".toString());
               }
            }

            if (var4 == 0) {
               return 0L;
            } else if (!this.upstream.request(this.pos + 1L)) {
               return -1L;
            } else {
               if (this.expectedSegment == null && this.buffer.head != null) {
                  this.expectedSegment = this.buffer.head;
                  val var10: Segment = this.buffer.head;
                  this.expectedPos = var10.pos;
               }

               var2 = Math.min(var2, this.buffer.size() - this.pos);
               this.buffer.copyTo(var1, this.pos, var2);
               this.pos += var2;
               return var2;
            }
         }
      } else {
         val var7: StringBuilder = new StringBuilder("byteCount < 0: ");
         var7.append(var2);
         throw new IllegalArgumentException(var7.toString().toString());
      }
   }

   public override fun timeout(): Timeout {
      return this.upstream.timeout();
   }
}
