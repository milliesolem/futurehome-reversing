package kotlinx.coroutines.scheduling

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import kotlin.coroutines.CoroutineContext
import kotlinx.atomicfu.AtomicInt
import kotlinx.coroutines.ExecutorCoroutineDispatcher

private class LimitingDispatcher(dispatcher: ExperimentalCoroutineDispatcher, parallelism: Int, name: String?, taskMode: Int)
   : ExecutorCoroutineDispatcher,
   TaskContext,
   Executor {
   private final val dispatcher: ExperimentalCoroutineDispatcher

   public open val executor: Executor
      public open get() {
         return this;
      }


   private final val inFlightTasks: AtomicInt
   private final val name: String?
   private final val parallelism: Int
   private final val queue: ConcurrentLinkedQueue<Runnable>
   public open val taskMode: Int

   init {
      this.dispatcher = var1;
      this.parallelism = var2;
      this.name = var3;
      this.taskMode = var4;
      this.queue = new ConcurrentLinkedQueue<>();
   }

   private fun dispatch(block: Runnable, tailDispatch: Boolean) {
      val var4: Runnable;
      do {
         val var3: AtomicIntegerFieldUpdater = inFlightTasks$FU;
         if (inFlightTasks$FU.incrementAndGet(this) <= this.parallelism) {
            this.dispatcher.dispatchWithContext$kotlinx_coroutines_core(var1, this, var2);
            return;
         }

         this.queue.add(var1);
         if (var3.decrementAndGet(this) >= this.parallelism) {
            return;
         }

         var4 = this.queue.poll();
         var1 = var4;
      } while (var4 != null);
   }

   public override fun afterTask() {
      var var1: Runnable = this.queue.poll();
      if (var1 != null) {
         this.dispatcher.dispatchWithContext$kotlinx_coroutines_core(var1, this, true);
      } else {
         inFlightTasks$FU.decrementAndGet(this);
         var1 = this.queue.poll();
         if (var1 != null) {
            this.dispatch(var1, true);
         }
      }
   }

   public override fun close() {
      throw new IllegalStateException("Close cannot be invoked on LimitingBlockingDispatcher".toString());
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      this.dispatch(var2, false);
   }

   public override fun dispatchYield(context: CoroutineContext, block: Runnable) {
      this.dispatch(var2, true);
   }

   public override fun execute(command: Runnable) {
      this.dispatch(var1, false);
   }

   public override fun toString(): String {
      var var1: java.lang.String = this.name;
      if (this.name == null) {
         val var3: StringBuilder = new StringBuilder();
         var3.append(super.toString());
         var3.append("[dispatcher = ");
         var3.append(this.dispatcher);
         var3.append(']');
         var1 = var3.toString();
      }

      return var1;
   }
}
