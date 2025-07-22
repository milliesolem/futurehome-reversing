package kotlin.coroutines

public interface CoroutineContext {
   public abstract fun <R> fold(initial: R, operation: (R, kotlin.coroutines.CoroutineContext.Element) -> R): R {
   }

   public abstract operator fun <E : kotlin.coroutines.CoroutineContext.Element> get(key: kotlin.coroutines.CoroutineContext.Key<E>): E? {
   }

   public abstract fun minusKey(key: kotlin.coroutines.CoroutineContext.Key<*>): CoroutineContext {
   }

   public open operator fun plus(context: CoroutineContext): CoroutineContext {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun plus(var0: CoroutineContext, var1: CoroutineContext): CoroutineContext {
         if (var1 != EmptyCoroutineContext.INSTANCE) {
            var0 = var1.fold(var0, new CoroutineContext$DefaultImpls$$ExternalSyntheticLambda0());
         }

         return var0;
      }

      @JvmStatic
      fun `plus$lambda$0`(var0: CoroutineContext, var1: CoroutineContext.Element): CoroutineContext {
         var var2: CoroutineContext = var0.minusKey(var1.getKey());
         if (var2 === EmptyCoroutineContext.INSTANCE) {
            var0 = var1;
         } else {
            val var4: ContinuationInterceptor = var2.get(ContinuationInterceptor.Key);
            val var5: CombinedContext;
            if (var4 == null) {
               var5 = new CombinedContext(var2, var1);
            } else {
               var2 = var2.minusKey(ContinuationInterceptor.Key);
               if (var2 === EmptyCoroutineContext.INSTANCE) {
                  var5 = new CombinedContext(var1, var4);
               } else {
                  var5 = new CombinedContext(new CombinedContext(var2, var1), var4);
               }
            }

            var0 = var5;
         }

         return var0;
      }
   }

   public interface Element : CoroutineContext {
      public val key: kotlin.coroutines.CoroutineContext.Key<*>

      public override fun <R> fold(initial: R, operation: (R, kotlin.coroutines.CoroutineContext.Element) -> R): R {
      }

      public override operator fun <E : kotlin.coroutines.CoroutineContext.Element> get(key: kotlin.coroutines.CoroutineContext.Key<E>): E? {
      }

      public override fun minusKey(key: kotlin.coroutines.CoroutineContext.Key<*>): CoroutineContext {
      }

      // $VF: Class flags could not be determined
      internal class DefaultImpls {
         @JvmStatic
         fun <R> fold(var0: CoroutineContext.Element, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
            return (R)var2.invoke(var1, var0);
         }

         @JvmStatic
         fun <E extends CoroutineContext.Element> get(var0: CoroutineContext.Element, var1: CoroutineContextKey<E>): E {
            if (var0.getKey() == var1) {
               ;
            } else {
               var0 = null;
            }

            return (E)var0;
         }

         @JvmStatic
         fun minusKey(var0: CoroutineContext.Element, var1: CoroutineContextKey<?>): CoroutineContext {
            var var2: Any = var0;
            if (var0.getKey() == var1) {
               var2 = EmptyCoroutineContext.INSTANCE;
            }

            return var2 as CoroutineContext;
         }

         @JvmStatic
         fun plus(var0: CoroutineContext.Element, var1: CoroutineContext): CoroutineContext {
            return CoroutineContext.DefaultImpls.plus(var0, var1);
         }
      }
   }

   public interface Key<E extends CoroutineContext.Element>
}
