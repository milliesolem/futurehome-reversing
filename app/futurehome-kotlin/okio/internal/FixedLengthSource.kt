package okio.internal

import java.io.IOException
import okio.Buffer
import okio.ForwardingSource
import okio.Source

internal class FixedLengthSource(delegate: Source, size: Long, truncate: Boolean) : ForwardingSource(var1) {
   private final var bytesReceived: Long
   private final val size: Long
   private final val truncate: Boolean

   init {
      this.size = var2;
      this.truncate = var4;
   }

   private fun Buffer.truncateToSize(newSize: Long) {
      val var4: Buffer = new Buffer();
      var4.writeAll(var1);
      var1.write(var4, var2);
      var4.clear();
   }

   public override fun read(sink: Buffer, byteCount: Long): Long {
      var var5: Long;
      if (this.bytesReceived > this.size) {
         var5 = 0L;
      } else {
         var5 = var2;
         if (this.truncate) {
            var5 = this.size - this.bytesReceived;
            if (this.size - this.bytesReceived == 0L) {
               return -1L;
            }

            var5 = Math.min(var2, var5);
         }
      }

      val var15: Long = super.read(var1, var5);
      val var16: Long;
      val var4: Boolean = (boolean)(if ((var16 = var15 - -1L) == 0L) 0 else (if (var16 < 0L) -1 else 1));
      if (var15 != -1L) {
         this.bytesReceived += var15;
      }

      var2 = this.bytesReceived;
      var5 = this.size;
      if ((this.bytesReceived >= this.size || var4) && this.bytesReceived <= this.size) {
         return var15;
      } else {
         if (var15 > 0L && this.bytesReceived > this.size) {
            this.truncateToSize(var1, var1.size() - (this.bytesReceived - this.size));
         }

         val var11: StringBuilder = new StringBuilder("expected ");
         var11.append(this.size);
         var11.append(" bytes but got ");
         var11.append(this.bytesReceived);
         throw new IOException(var11.toString());
      }
   }
}
