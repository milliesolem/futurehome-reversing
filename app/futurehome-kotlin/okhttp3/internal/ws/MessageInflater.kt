package okhttp3.internal.ws

import java.io.Closeable
import java.util.zip.Inflater
import kotlin.jvm.internal.Intrinsics
import okio.Buffer
import okio.InflaterSource

public class MessageInflater(noContextTakeover: Boolean) : Closeable {
   private final val deflatedBytes: Buffer
   private final val inflater: Inflater
   private final val inflaterSource: InflaterSource
   private final val noContextTakeover: Boolean

   init {
      this.noContextTakeover = var1;
      val var2: Buffer = new Buffer();
      this.deflatedBytes = var2;
      val var3: Inflater = new Inflater(true);
      this.inflater = var3;
      this.inflaterSource = new InflaterSource(var2, var3);
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      this.inflaterSource.close();
   }

   @Throws(java/io/IOException::class)
   public fun inflate(buffer: Buffer) {
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      val var2: Boolean;
      if (this.deflatedBytes.size() == 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
      } else {
         if (this.noContextTakeover) {
            this.inflater.reset();
         }

         this.deflatedBytes.writeAll(var1);
         this.deflatedBytes.writeInt(65535);
         val var3: Long = this.inflater.getBytesRead();
         val var5: Long = this.deflatedBytes.size();

         do {
            this.inflaterSource.readOrInflate(var1, java.lang.Long.MAX_VALUE);
         } while (this.inflater.getBytesRead() < var3 + var5);
      }
   }
}
