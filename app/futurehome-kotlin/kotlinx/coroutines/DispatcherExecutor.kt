package kotlinx.coroutines

import java.util.concurrent.Executor
import kotlin.coroutines.EmptyCoroutineContext

private class DispatcherExecutor(dispatcher: CoroutineDispatcher) : Executor {
   public final val dispatcher: CoroutineDispatcher

   init {
      this.dispatcher = var1;
   }

   public override fun execute(block: Runnable) {
      if (this.dispatcher.isDispatchNeeded(EmptyCoroutineContext.INSTANCE)) {
         this.dispatcher.dispatch(EmptyCoroutineContext.INSTANCE, var1);
      } else {
         var1.run();
      }
   }

   public override fun toString(): String {
      return this.dispatcher.toString();
   }
}
