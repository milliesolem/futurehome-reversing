package kotlinx.coroutines.scheduling

private class TaskContextImpl(taskMode: Int) : TaskContext {
   public open val taskMode: Int

   init {
      this.taskMode = var1;
   }

   public override fun afterTask() {
   }
}
