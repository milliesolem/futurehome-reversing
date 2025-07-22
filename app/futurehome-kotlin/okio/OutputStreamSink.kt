package okio

import java.io.OutputStream

private class OutputStreamSink(out: OutputStream, timeout: Timeout) : Sink {
   private final val out: OutputStream
   private final val timeout: Timeout

   init {
      this.out = var1;
      this.timeout = var2;
   }

   public override fun close() {
      this.out.close();
   }

   public override fun flush() {
      this.out.flush();
   }

   public override fun timeout(): Timeout {
      return this.timeout;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("sink(");
      var1.append(this.out);
      var1.append(')');
      return var1.toString();
   }

   public override fun write(source: Buffer, byteCount: Long) {
      _SegmentedByteString.checkOffsetAndCount(var1.size(), 0L, var2);

      while (var2 > 0L) {
         this.timeout.throwIfReached();
         val var9: Segment = var1.head;
         val var4: Int = (int)Math.min(var2, (long)(var9.limit - var9.pos));
         this.out.write(var9.data, var9.pos, var4);
         var9.pos += var4;
         val var5: Long = var2 - var4;
         var1.setSize$okio(var1.size() - (long)var4);
         var2 = var5;
         if (var9.pos == var9.limit) {
            var1.head = var9.pop();
            SegmentPool.recycle(var9);
            var2 = var5;
         }
      }
   }
}
