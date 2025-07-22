package kotlinx.coroutines.sync

public interface Semaphore {
   public val availablePermits: Int

   public abstract suspend fun acquire() {
   }

   public abstract fun release() {
   }

   public abstract fun tryAcquire(): Boolean {
   }
}
