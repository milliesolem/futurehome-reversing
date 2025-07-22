package kotlinx.coroutines.scheduling

import kotlinx.coroutines.DebugStringsKt

internal class TaskImpl(block: Runnable, submissionTime: Long, taskContext: TaskContext) : Task(var2, var4) {
   public final val block: Runnable

   init {
      this.block = var1;
   }

   public override fun run() {
      label10: {
         try {
            this.block.run();
         } catch (var2: java.lang.Throwable) {
            this.taskContext.afterTask();
         }

         this.taskContext.afterTask();
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("Task[");
      var1.append(DebugStringsKt.getClassSimpleName(this.block));
      var1.append('@');
      var1.append(DebugStringsKt.getHexAddress(this.block));
      var1.append(", ");
      var1.append(this.submissionTime);
      var1.append(", ");
      var1.append(this.taskContext);
      var1.append(']');
      return var1.toString();
   }
}
