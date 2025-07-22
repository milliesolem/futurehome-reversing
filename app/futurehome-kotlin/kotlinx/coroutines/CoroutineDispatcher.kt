package kotlinx.coroutines

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.AbstractCoroutineContextKey
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.internal.DispatchedContinuation
import kotlinx.coroutines.internal.LimitedDispatcher
import kotlinx.coroutines.internal.LimitedDispatcherKt

public abstract class CoroutineDispatcher : AbstractCoroutineContextElement, ContinuationInterceptor {
   open fun CoroutineDispatcher() {
      super(ContinuationInterceptor.Key);
   }

   public abstract fun dispatch(context: CoroutineContext, block: Runnable) {
   }

   public open fun dispatchYield(context: CoroutineContext, block: Runnable) {
      this.dispatch(var1, var2);
   }

   override fun <E extends CoroutineContext.Element> get(var1: CoroutineContextKey<E>): E {
      return ContinuationInterceptor.DefaultImpls.get(this, var1);
   }

   public override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
      return new DispatchedContinuation(this, var1);
   }

   public open fun isDispatchNeeded(context: CoroutineContext): Boolean {
      return true;
   }

   public open fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
      LimitedDispatcherKt.checkParallelism(var1);
      return new LimitedDispatcher(this, var1);
   }

   override fun minusKey(var1: CoroutineContextKey<?>): CoroutineContext {
      return ContinuationInterceptor.DefaultImpls.minusKey(this, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")
   public operator fun plus(other: CoroutineDispatcher): CoroutineDispatcher {
      return var1;
   }

   public override fun releaseInterceptedContinuation(continuation: Continuation<*>) {
      (var1 as DispatchedContinuation).release$kotlinx_coroutines_core();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(DebugStringsKt.getClassSimpleName(this));
      var1.append('@');
      var1.append(DebugStringsKt.getHexAddress(this));
      return var1.toString();
   }

   public companion object Key : AbstractCoroutineContextKey(ContinuationInterceptor.Key, <unrepresentable>.INSTANCE)
}
