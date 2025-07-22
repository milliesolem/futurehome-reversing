package kotlinx.coroutines.scheduling

internal abstract class Task : Runnable {
   internal final val mode: Int
      internal final inline get() {
         return this.taskContext.getTaskMode();
      }


   public final var submissionTime: Long
      private set

   internal final var taskContext: TaskContext
      private set

   open fun Task() {
      this(0L, TasksKt.NonBlockingContext);
   }

   open fun Task(var1: Long, var3: TaskContext) {
      this.submissionTime = var1;
      this.taskContext = var3;
   }
}
