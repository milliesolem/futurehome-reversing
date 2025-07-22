package kotlinx.coroutines

internal class ChildHandleNode(childJob: ChildJob) : JobCancellingNode, ChildHandle {
   public final val childJob: ChildJob

   public open val parent: Job
      public open get() {
         return this.getJob();
      }


   init {
      this.childJob = var1;
   }

   public override fun childCancelled(cause: Throwable): Boolean {
      return this.getJob().childCancelled(var1);
   }

   public override operator fun invoke(cause: Throwable?) {
      this.childJob.parentCancelled(this.getJob());
   }
}
