package kotlin.coroutines.jvm.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

internal abstract class ContinuationImpl : BaseContinuationImpl {
   private final val _context: CoroutineContext?

   public open val context: CoroutineContext
      public open get() {
         val var1: CoroutineContext = this._context;
         return var1;
      }


   private final var intercepted: Continuation<Any?>?

   open fun ContinuationImpl(var1: Continuation<Object>) {
      val var2: CoroutineContext;
      if (var1 != null) {
         var2 = var1.getContext();
      } else {
         var2 = null;
      }

      this(var1, var2);
   }

   open fun ContinuationImpl(var1: Continuation<Object>, var2: CoroutineContext) {
      super(var1);
      this._context = var2;
   }

   public fun intercepted(): Continuation<Any?> {
      var var1: Continuation = this.intercepted;
      if (this.intercepted == null) {
         label13: {
            val var3: ContinuationInterceptor = this.getContext().get(ContinuationInterceptor.Key);
            if (var3 != null) {
               val var4: Continuation = var3.interceptContinuation(this);
               var1 = var4;
               if (var4 != null) {
                  break label13;
               }
            }

            var1 = this;
         }

         this.intercepted = var1;
      }

      return var1;
   }

   protected override fun releaseIntercepted() {
      val var1: Continuation = this.intercepted;
      if (this.intercepted != null && this.intercepted != this) {
         val var2: CoroutineContext.Element = this.getContext().get(ContinuationInterceptor.Key);
         (var2 as ContinuationInterceptor).releaseInterceptedContinuation(var1);
      }

      this.intercepted = CompletedContinuation.INSTANCE;
   }
}
