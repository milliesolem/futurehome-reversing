package kotlinx.coroutines

import kotlinx.coroutines.scheduling.TaskContext

internal final val taskContext: TaskContext
   internal final get() {
      return var0.taskContext;
   }


internal inline fun TaskContext.afterTask() {
   var0.afterTask();
}
