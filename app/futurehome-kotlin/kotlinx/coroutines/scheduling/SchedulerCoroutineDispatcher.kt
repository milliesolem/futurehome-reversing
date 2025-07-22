package kotlinx.coroutines.scheduling

import java.util.concurrent.Executor
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.ExecutorCoroutineDispatcher

internal open class SchedulerCoroutineDispatcher(corePoolSize: Int = TasksKt.CORE_POOL_SIZE,
      maxPoolSize: Int = TasksKt.MAX_POOL_SIZE,
      idleWorkerKeepAliveNs: Long = TasksKt.IDLE_WORKER_KEEP_ALIVE_NS,
      schedulerName: String = "CoroutineScheduler"
   )
   : ExecutorCoroutineDispatcher {
   private final val corePoolSize: Int
   private final var coroutineScheduler: CoroutineScheduler

   public open val executor: Executor
      public open get() {
         return this.coroutineScheduler;
      }


   private final val idleWorkerKeepAliveNs: Long
   private final val maxPoolSize: Int
   private final val schedulerName: String

   open fun SchedulerCoroutineDispatcher() {
      this(0, 0, 0L, null, 15, null);
   }

   init {
      this.corePoolSize = var1;
      this.maxPoolSize = var2;
      this.idleWorkerKeepAliveNs = var3;
      this.schedulerName = var5;
      this.coroutineScheduler = this.createScheduler();
   }

   private fun createScheduler(): CoroutineScheduler {
      return new CoroutineScheduler(this.corePoolSize, this.maxPoolSize, this.idleWorkerKeepAliveNs, this.schedulerName);
   }

   public override fun close() {
      this.coroutineScheduler.close();
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      CoroutineScheduler.dispatch$default(this.coroutineScheduler, var2, null, false, 6, null);
   }

   internal fun dispatchWithContext(block: Runnable, context: TaskContext, tailDispatch: Boolean) {
      this.coroutineScheduler.dispatch(var1, var2, var3);
   }

   public override fun dispatchYield(context: CoroutineContext, block: Runnable) {
      CoroutineScheduler.dispatch$default(this.coroutineScheduler, var2, null, true, 2, null);
   }

   internal fun restore() {
      this.usePrivateScheduler$kotlinx_coroutines_core();
   }

   internal fun shutdown(timeout: Long) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield kotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher.coroutineScheduler Lkotlinx/coroutines/scheduling/CoroutineScheduler;
      // 06: lload 1
      // 07: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler.shutdown (J)V
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: return
      // 0d: astore 3
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 3
      // 11: athrow
   }

   internal fun usePrivateScheduler() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield kotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher.coroutineScheduler Lkotlinx/coroutines/scheduling/CoroutineScheduler;
      // 06: ldc2_w 1000
      // 09: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler.shutdown (J)V
      // 0c: aload 0
      // 0d: aload 0
      // 0e: invokespecial kotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher.createScheduler ()Lkotlinx/coroutines/scheduling/CoroutineScheduler;
      // 11: putfield kotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher.coroutineScheduler Lkotlinx/coroutines/scheduling/CoroutineScheduler;
      // 14: aload 0
      // 15: monitorexit
      // 16: return
      // 17: astore 1
      // 18: aload 0
      // 19: monitorexit
      // 1a: aload 1
      // 1b: athrow
   }
}
