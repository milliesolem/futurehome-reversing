package okhttp3.internal.ws

import java.io.Closeable
import java.util.zip.Deflater
import kotlin.jvm.internal.Intrinsics
import okio.Buffer
import okio.ByteString
import okio.DeflaterSink

public class MessageDeflater(noContextTakeover: Boolean) : Closeable {
   private final val deflatedBytes: Buffer
   private final val deflater: Deflater
   private final val deflaterSink: DeflaterSink
   private final val noContextTakeover: Boolean

   init {
      this.noContextTakeover = var1;
      val var3: Buffer = new Buffer();
      this.deflatedBytes = var3;
      val var2: Deflater = new Deflater(-1, true);
      this.deflater = var2;
      this.deflaterSink = new DeflaterSink(var3, var2);
   }

   private fun Buffer.endsWith(suffix: ByteString): Boolean {
      return var1.rangeEquals(var1.size() - (long)var2.size(), var2);
   }

   @Throws(java/io/IOException::class)
   public override fun close() {
      this.deflaterSink.close();
   }

   @Throws(java/io/IOException::class)
   public fun deflate(buffer: Buffer) {
      Intrinsics.checkParameterIsNotNull(var1, "buffer");
      val var2: Boolean;
      if (this.deflatedBytes.size() == 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      label32:
      if (var2) {
         if (this.noContextTakeover) {
            this.deflater.reset();
         }

         this.deflaterSink.write(var1, var1.size());
         this.deflaterSink.flush();
         if (this.endsWith(this.deflatedBytes, MessageDeflaterKt.access$getEMPTY_DEFLATE_BLOCK$p())) {
            val var3: Long = this.deflatedBytes.size();
            val var5: Long = 4;
            val var7: Closeable = Buffer.readAndWriteUnsafe$default(this.deflatedBytes, null, 1, null);
            val var8: java.lang.Throwable = null as java.lang.Throwable;

            try {
               (var7 as Buffer.UnsafeCursor).resizeBuffer(var3 - var5);
            } catch (var10: java.lang.Throwable) {
               val var15: java.lang.Throwable = var10;

               try {
                  throw var15;
               } catch (var9: java.lang.Throwable) {
                  CloseableKt.closeFinally(var7, var10);
               }
            }

            CloseableKt.closeFinally(var7, null);
         } else {
            this.deflatedBytes.writeByte(0);
         }

         var1.write(this.deflatedBytes, this.deflatedBytes.size());
      } else {
         throw (new IllegalArgumentException("Failed requirement.".toString())) as java.lang.Throwable;
      }
   }
}
