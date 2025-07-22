package kotlinx.coroutines.scheduling

import java.util.concurrent.Executor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.internal.SystemPropsKt

internal object DefaultIoScheduler : ExecutorCoroutineDispatcher, Executor {
   private final val default: CoroutineDispatcher =
      UnlimitedIoScheduler.INSTANCE
         .limitedParallelism(
            SystemPropsKt.systemProp$default(
               "kotlinx.coroutines.io.parallelism", RangesKt.coerceAtLeast(64, SystemPropsKt.getAVAILABLE_PROCESSORS()), 0, 0, 12, null
            )
         )

   public open val executor: Executor
      public open get() {
         return this;
      }


   public override fun close() {
      throw new IllegalStateException("Cannot be invoked on Dispatchers.IO".toString());
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      default.dispatch(var1, var2);
   }

   public override fun dispatchYield(context: CoroutineContext, block: Runnable) {
      default.dispatchYield(var1, var2);
   }

   public override fun execute(command: Runnable) {
      this.dispatch(EmptyCoroutineContext.INSTANCE, var1);
   }

   public override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
      return UnlimitedIoScheduler.INSTANCE.limitedParallelism(var1);
   }

   public override fun toString(): String {
      return "Dispatchers.IO";
   }
}
