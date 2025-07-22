package kotlin.io

import java.io.Closeable
import kotlin.contracts.InvocationKind
import kotlin.internal.PlatformImplementationsKt

internal fun Closeable?.closeFinally(cause: Throwable?) {
   if (var0 != null) {
      if (var1 == null) {
         var0.close();
      } else {
         try {
            var0.close();
         } catch (var2: java.lang.Throwable) {
            kotlin.ExceptionsKt.addSuppressed(var1, var2);
            return;
         }
      }
   }
}

public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   label39: {
      try {
         var15 = var1.invoke(var0);
      } catch (var5: java.lang.Throwable) {
         val var2: java.lang.Throwable = var5;

         try {
            throw var2;
         } catch (var4: java.lang.Throwable) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               if (var0 != null) {
                  try {
                     var0.close();
                  } catch (var3: java.lang.Throwable) {
                     ;
                  }
               }
            } else {
               closeFinally(var0, var5);
            }
         }
      }

      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         closeFinally(var0, null);
      } else if (var0 != null) {
         var0.close();
      }

      return (R)var15;
   }
}
