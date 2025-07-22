package kotlin.coroutines.jvm.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal abstract class RestrictedContinuationImpl : BaseContinuationImpl {
   public open val context: CoroutineContext
      public open get() {
         return EmptyCoroutineContext.INSTANCE;
      }


   open fun RestrictedContinuationImpl(var1: Continuation<Object>) {
      super(var1);
      if (var1 != null && var1.getContext() != EmptyCoroutineContext.INSTANCE) {
         throw new IllegalArgumentException("Coroutines with restricted suspension must have EmptyCoroutineContext".toString());
      }
   }
}
