package okhttp3.internal.cache2

import java.nio.channels.FileChannel
import kotlin.jvm.internal.Intrinsics
import okio.Buffer

internal class FileOperator(fileChannel: FileChannel) {
   private final val fileChannel: FileChannel

   init {
      Intrinsics.checkParameterIsNotNull(var1, "fileChannel");
      super();
      this.fileChannel = var1;
   }

   public fun read(pos: Long, sink: Buffer, byteCount: Long) {
      Intrinsics.checkParameterIsNotNull(var3, "sink");
      if (var4 < 0L) {
         throw (new IndexOutOfBoundsException()) as java.lang.Throwable;
      } else {
         while (var4 > 0L) {
            val var6: Long = this.fileChannel.transferTo(var1, var4, var3);
            var1 += var6;
            var4 -= var6;
         }
      }
   }

   @Throws(java/io/IOException::class)
   public fun write(pos: Long, source: Buffer, byteCount: Long) {
      Intrinsics.checkParameterIsNotNull(var3, "source");
      if (var4 >= 0L && var4 <= var3.size()) {
         var var6: Long = var1;
         var1 = var4;

         while (var1 > 0L) {
            var4 = this.fileChannel.transferFrom(var3, var6, var1);
            var6 += var4;
            var1 -= var4;
         }
      } else {
         throw (new IndexOutOfBoundsException()) as java.lang.Throwable;
      }
   }
}
