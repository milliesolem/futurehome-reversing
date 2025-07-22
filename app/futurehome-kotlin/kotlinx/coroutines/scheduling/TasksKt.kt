package kotlinx.coroutines.scheduling

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.internal.SystemPropsKt

internal final val BlockingContext: TaskContext = (new TaskContextImpl(1)) as TaskContext
internal final val CORE_POOL_SIZE: Int =
   SystemPropsKt.systemProp$default(
      "kotlinx.coroutines.scheduler.core.pool.size", RangesKt.coerceAtLeast(SystemPropsKt.getAVAILABLE_PROCESSORS(), 2), 1, 0, 8, null
   )
   internal final val DEFAULT_SCHEDULER_NAME: String = SystemPropsKt.systemProp("kotlinx.coroutines.scheduler.default.name", "DefaultDispatcher")
internal final val IDLE_WORKER_KEEP_ALIVE_NS: Long =
   TimeUnit.SECONDS.toNanos(SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.keep.alive.sec", 60L, 0L, 0L, 12, null))
   internal final val MAX_POOL_SIZE: Int = SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.max.pool.size", 2097150, 0, 2097150, 4, null)
internal final val NonBlockingContext: TaskContext = (new TaskContextImpl(0)) as TaskContext
internal const val TASK_NON_BLOCKING: Int = 0
internal const val TASK_PROBABLY_BLOCKING: Int = 1
internal final val WORK_STEALING_TIME_RESOLUTION_NS: Long =
   SystemPropsKt.systemProp$default("kotlinx.coroutines.scheduler.resolution.ns", 100000L, 0L, 0L, 12, null)

internal final var schedulerTimeSource: SchedulerTimeSource = NanoTimeSource.INSTANCE as SchedulerTimeSource
   private set

internal final val isBlocking: Boolean
   internal final inline get() {
      val var1: Int = var0.taskContext.getTaskMode();
      var var2: Boolean = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

