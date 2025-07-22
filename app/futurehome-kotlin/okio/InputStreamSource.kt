package okio

import java.io.IOException
import java.io.InputStream

private open class InputStreamSource(input: InputStream, timeout: Timeout) : Source {
   private final val input: InputStream
   private final val timeout: Timeout

   init {
      this.input = var1;
      this.timeout = var2;
   }

   public override fun close() {
      this.input.close();
   }

   public override fun read(sink: Buffer, byteCount: Long): Long {
      val var17: Long;
      var var4: Int = if ((var17 = var2 - 0L) == 0L) 0 else (if (var17 < 0L) -1 else 1);
      if (var2 == 0L) {
         return 0L;
      } else if (var4 >= 0) {
         var var7: Segment;
         try {
            this.timeout.throwIfReached();
            var7 = var1.writableSegment$okio(1);
            var4 = this.input.read(var7.data, var7.limit, (int)Math.min(var2, (long)(8192 - var7.limit)));
         } catch (var11: AssertionError) {
            if (Okio.isAndroidGetsocknameError(var11)) {
               throw new IOException(var11);
            }

            throw var11;
         }

         val var13: AssertionError;
         if (var4 == -1) {
            try {
               if (var7.pos == var7.limit) {
                  var1.head = var7.pop();
                  SegmentPool.recycle(var7);
               }

               return -1L;
            } catch (var8: AssertionError) {
               var13 = var8;
               if (Okio.isAndroidGetsocknameError(var8)) {
                  throw new IOException(var8);
               }
            }
         } else {
            var var5: Long;
            try {
               var7.limit += var4;
               var5 = var1.size();
            } catch (var10: AssertionError) {
               if (Okio.isAndroidGetsocknameError(var10)) {
                  throw new IOException(var10);
               }

               throw var10;
            }

            var2 = var4;

            try {
               var1.setSize$okio(var5 + var2);
               return var2;
            } catch (var9: AssertionError) {
               var13 = var9;
               if (Okio.isAndroidGetsocknameError(var9)) {
                  throw new IOException(var9);
               }
            }
         }

         throw var13;
      } else {
         val var12: StringBuilder = new StringBuilder("byteCount < 0: ");
         var12.append(var2);
         throw new IllegalArgumentException(var12.toString().toString());
      }
   }

   public override fun timeout(): Timeout {
      return this.timeout;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("source(");
      var1.append(this.input);
      var1.append(')');
      return var1.toString();
   }
}
