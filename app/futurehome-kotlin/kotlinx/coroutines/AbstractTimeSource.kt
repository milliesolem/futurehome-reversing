package kotlinx.coroutines

internal abstract class AbstractTimeSource {
   public abstract fun currentTimeMillis(): Long {
   }

   public abstract fun nanoTime(): Long {
   }

   public abstract fun parkNanos(blocker: Any, nanos: Long) {
   }

   public abstract fun registerTimeLoopThread() {
   }

   public abstract fun trackTask() {
   }

   public abstract fun unTrackTask() {
   }

   public abstract fun unpark(thread: Thread) {
   }

   public abstract fun unregisterTimeLoopThread() {
   }

   public abstract fun wrapTask(block: Runnable): Runnable {
   }
}
