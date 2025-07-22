package kotlin.coroutines

import kotlin.coroutines.CoroutineContext.Element

public interface ContinuationInterceptor : CoroutineContext.Element {
   public override operator fun <E : Element> get(key: kotlin.coroutines.CoroutineContext.Key<E>): E? {
   }

   public abstract fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
   }

   public override fun minusKey(key: kotlin.coroutines.CoroutineContext.Key<*>): CoroutineContext {
   }

   public open fun releaseInterceptedContinuation(continuation: Continuation<*>) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <R> fold(var0: ContinuationInterceptor, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return CoroutineContext.Element.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <E extends CoroutineContext.Element> get(var0: ContinuationInterceptor, var1: CoroutineContextKey<E>): E {
         if (var1 is AbstractCoroutineContextKey) {
            val var8: AbstractCoroutineContextKey = var1 as AbstractCoroutineContextKey;
            var var6: CoroutineContext.Element = null;
            if (var8.isSubKey$kotlin_stdlib(var0.getKey())) {
               val var5: CoroutineContext.Element = var8.tryCast$kotlin_stdlib(var0);
               var6 = null;
               if (var5 is CoroutineContext.Element) {
                  var6 = var5;
               }
            }

            return (E)var6;
         } else {
            var var7: CoroutineContext.Element = null;
            if (ContinuationInterceptor.Key === var1) {
               var7 = var0;
            }

            return (E)var7;
         }
      }

      @JvmStatic
      fun minusKey(var0: ContinuationInterceptor, var1: CoroutineContextKey<?>): CoroutineContext {
         if (var1 is AbstractCoroutineContextKey) {
            val var2: AbstractCoroutineContextKey = var1 as AbstractCoroutineContextKey;
            var var3: Any = var0;
            if (var2.isSubKey$kotlin_stdlib(((ContinuationInterceptor)var0).getKey())) {
               var3 = var0;
               if (var2.tryCast$kotlin_stdlib(var0 as CoroutineContext.Element) != null) {
                  var3 = EmptyCoroutineContext.INSTANCE;
               }
            }

            return var3 as CoroutineContext;
         } else {
            if (ContinuationInterceptor.Key === var1) {
               var0 = EmptyCoroutineContext.INSTANCE;
            }

            return var0 as CoroutineContext;
         }
      }

      @JvmStatic
      fun plus(var0: ContinuationInterceptor, var1: CoroutineContext): CoroutineContext {
         return CoroutineContext.Element.DefaultImpls.plus(var0, var1);
      }

      @JvmStatic
      fun releaseInterceptedContinuation(var0: ContinuationInterceptor, var1: Continuation<?>) {
      }
   }

   public companion object Key : CoroutineContext.Key<ContinuationInterceptor>
}
