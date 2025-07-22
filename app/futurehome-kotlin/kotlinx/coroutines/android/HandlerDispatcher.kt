package kotlinx.coroutines.android

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.MainCoroutineDispatcher

public sealed class HandlerDispatcher protected constructor() : MainCoroutineDispatcher, Delay {
   public abstract val immediate: HandlerDispatcher

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
   override fun delay(var1: Long, var3: Continuation<? super Unit>): Any {
      return Delay.DefaultImpls.delay(this, var1, var3);
   }

   override fun invokeOnTimeout(var1: Long, var3: Runnable, var4: CoroutineContext): DisposableHandle {
      return Delay.DefaultImpls.invokeOnTimeout(this, var1, var3, var4);
   }
}
