package okhttp3.internal.concurrent

import kotlin.jvm.internal.Intrinsics

public abstract class Task {
   public final val cancelable: Boolean
   public final val name: String
   internal final var nextExecuteNanoTime: Long
   internal final var queue: TaskQueue?

   open fun Task(var1: java.lang.String, var2: Boolean) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      super();
      this.name = var1;
      this.cancelable = var2;
      this.nextExecuteNanoTime = -1L;
   }

   internal fun initQueue(queue: TaskQueue) {
      Intrinsics.checkParameterIsNotNull(var1, "queue");
      if (this.queue != var1) {
         val var2: Boolean;
         if (this.queue == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            this.queue = var1;
         } else {
            throw (new IllegalStateException("task is in multiple queues".toString())) as java.lang.Throwable;
         }
      }
   }

   public abstract fun runOnce(): Long {
   }

   public override fun toString(): String {
      return this.name;
   }
}
