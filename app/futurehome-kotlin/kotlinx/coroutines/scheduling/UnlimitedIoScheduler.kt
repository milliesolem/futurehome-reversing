package kotlinx.coroutines.scheduling

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.internal.LimitedDispatcherKt

private object UnlimitedIoScheduler : CoroutineDispatcher {
   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      DefaultScheduler.INSTANCE.dispatchWithContext$kotlinx_coroutines_core(var2, TasksKt.BlockingContext, false);
   }

   public override fun dispatchYield(context: CoroutineContext, block: Runnable) {
      DefaultScheduler.INSTANCE.dispatchWithContext$kotlinx_coroutines_core(var2, TasksKt.BlockingContext, true);
   }

   public override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
      LimitedDispatcherKt.checkParallelism(var1);
      return if (var1 >= TasksKt.MAX_POOL_SIZE) this else super.limitedParallelism(var1);
   }
}
