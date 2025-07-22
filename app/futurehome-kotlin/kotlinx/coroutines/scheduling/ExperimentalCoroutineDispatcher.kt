package kotlinx.coroutines.scheduling

import java.util.concurrent.Executor
import java.util.concurrent.RejectedExecutionException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DefaultExecutor
import kotlinx.coroutines.ExecutorCoroutineDispatcher

internal open class ExperimentalCoroutineDispatcher(corePoolSize: Int,
   maxPoolSize: Int,
   idleWorkerKeepAliveNs: Long,
   schedulerName: String = "CoroutineScheduler"
) : ExecutorCoroutineDispatcher() {
   private final val corePoolSize: Int
   private final var coroutineScheduler: CoroutineScheduler

   public open val executor: Executor
      public open get() {
         return this.coroutineScheduler;
      }


   private final val idleWorkerKeepAliveNs: Long
   private final val maxPoolSize: Int
   private final val schedulerName: String

   init {
      this.corePoolSize = var1;
      this.maxPoolSize = var2;
      this.idleWorkerKeepAliveNs = var3;
      this.schedulerName = var5;
      this.coroutineScheduler = this.createScheduler();
   }

   public constructor(corePoolSize: Int = TasksKt.CORE_POOL_SIZE,
      maxPoolSize: Int = TasksKt.MAX_POOL_SIZE,
      schedulerName: String = TasksKt.DEFAULT_SCHEDULER_NAME
   ) : this(var1, var2, TasksKt.IDLE_WORKER_KEEP_ALIVE_NS, var3)
   private fun createScheduler(): CoroutineScheduler {
      return new CoroutineScheduler(this.corePoolSize, this.maxPoolSize, this.idleWorkerKeepAliveNs, this.schedulerName);
   }

   public fun blocking(parallelism: Int = 16): CoroutineDispatcher {
      if (var1 > 0) {
         return new LimitingDispatcher(this, var1, null, 1);
      } else {
         val var2: StringBuilder = new StringBuilder("Expected positive parallelism level, but have ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   public override fun close() {
      this.coroutineScheduler.close();
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      try {
         CoroutineScheduler.dispatch$default(this.coroutineScheduler, var2, null, false, 6, null);
      } catch (var4: RejectedExecutionException) {
         DefaultExecutor.INSTANCE.dispatch(var1, var2);
      }
   }

   internal fun dispatchWithContext(block: Runnable, context: TaskContext, tailDispatch: Boolean) {
      try {
         this.coroutineScheduler.dispatch(var1, var2, var3);
      } catch (var5: RejectedExecutionException) {
         DefaultExecutor.INSTANCE.enqueue(this.coroutineScheduler.createTask(var1, var2));
      }
   }

   public override fun dispatchYield(context: CoroutineContext, block: Runnable) {
      try {
         CoroutineScheduler.dispatch$default(this.coroutineScheduler, var2, null, true, 2, null);
      } catch (var4: RejectedExecutionException) {
         DefaultExecutor.INSTANCE.dispatchYield(var1, var2);
      }
   }

   public fun limited(parallelism: Int): CoroutineDispatcher {
      if (var1 > 0) {
         if (var1 <= this.corePoolSize) {
            return new LimitingDispatcher(this, var1, null, 0);
         } else {
            val var3: StringBuilder = new StringBuilder("Expected parallelism level lesser than core pool size (");
            var3.append(this.corePoolSize);
            var3.append("), but have ");
            var3.append(var1);
            throw new IllegalArgumentException(var3.toString().toString());
         }
      } else {
         val var2: StringBuilder = new StringBuilder("Expected positive parallelism level, but have ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(super.toString());
      var1.append("[scheduler = ");
      var1.append(this.coroutineScheduler);
      var1.append(']');
      return var1.toString();
   }
}
