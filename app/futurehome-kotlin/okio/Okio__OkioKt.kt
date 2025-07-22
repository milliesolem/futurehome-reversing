package okio

import java.io.Closeable

@JvmSynthetic
internal class Okio__OkioKt {
   @JvmStatic
   public fun blackholeSink(): Sink {
      return new BlackholeSink();
   }

   @JvmStatic
   public fun Sink.buffer(): BufferedSink {
      return new RealBufferedSink(var0);
   }

   @JvmStatic
   public fun Source.buffer(): BufferedSource {
      return new RealBufferedSource(var0);
   }

   @JvmStatic
   public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
      var var2: Any;
      label35: {
         var var3: Any;
         try {
            var3 = var1.invoke(var0);
         } catch (var7: java.lang.Throwable) {
            var17 = var7;
            if (var0 != null) {
               label25:
               try {
                  var0.close();
               } catch (var5: java.lang.Throwable) {
                  ExceptionsKt.addSuppressed(var7, var5);
                  break label25;
               }
            }

            var2 = null;
            break label35;
         }

         var17 = null;
         var2 = var3;
         label32:
         if (var0 != null) {
            try {
               var0.close();
            } catch (var6: java.lang.Throwable) {
               break label32;
            }

            var17 = null;
            var2 = var3;
         }
      }

      if (var17 == null) {
         return (R)var2;
      } else {
         throw var17;
      }
   }
}
