package kotlinx.coroutines.future

import j..util.function.BiFunction
import java.util.concurrent.CompletionException
import kotlin.coroutines.Continuation
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

private class ContinuationHandler<T>(cont: Continuation<Any>?) : BiFunction<T, java.lang.Throwable, Unit> {
   public final var cont: Continuation<Any>?
      private set

   init {
      this.cont = var1;
   }

   public open fun apply(result: Any?, exception: Throwable?) {
      val var4: Continuation = this.cont;
      if (this.cont != null) {
         if (var2 == null) {
            val var6: Result.Companion = Result.Companion;
            this.cont.resumeWith(Result.constructor-impl(var1));
         } else {
            val var3: CompletionException;
            if (NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var2)) {
               var3 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var2);
            } else {
               var3 = null;
            }

            var1 = var2;
            if (var3 != null) {
               var1 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var3);
               if (var1 == null) {
                  var1 = var2;
               }
            }

            val var7: Result.Companion = Result.Companion;
            var4.resumeWith(Result.constructor-impl(ResultKt.createFailure(var1)));
         }
      }
   }
}
