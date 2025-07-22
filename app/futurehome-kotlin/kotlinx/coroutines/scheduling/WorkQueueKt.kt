package kotlinx.coroutines.scheduling

internal const val BUFFER_CAPACITY: Int = 128
internal const val BUFFER_CAPACITY_BASE: Int = 7
internal const val MASK: Int = 127
internal const val NOTHING_TO_STEAL: Long = -2L
internal const val STEAL_ANY: Int = 3
internal const val STEAL_BLOCKING_ONLY: Int = 1
internal const val STEAL_CPU_ONLY: Int = 2
internal const val TASK_STOLEN: Long = -1L

internal final val maskForStealingMode: Int
   internal final inline get() {
      val var2: Int = var0.taskContext.getTaskMode();
      var var1: Byte = 1;
      if (var2 != 1) {
         var1 = 2;
      }

      return var1;
   }

