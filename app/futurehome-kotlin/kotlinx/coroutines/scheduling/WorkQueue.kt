package kotlinx.coroutines.scheduling

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceArray
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.jvm.internal.Ref.ObjectRef
import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.DebugKt

internal class WorkQueue {
   private final val blockingTasksInBuffer: AtomicInt
   private final val buffer: AtomicReferenceArray<Task?> = new AtomicReferenceArray(128)

   private final val bufferSize: Int
      private final get() {
         return producerIndex$FU.get(this) - consumerIndex$FU.get(this);
      }


   private final val consumerIndex: AtomicInt
   private final val lastScheduledTask: AtomicRef<Task?>
   private final val producerIndex: AtomicInt

   internal final val size: Int
      internal final get() {
         val var1: Int;
         if (lastScheduledTask$FU.get(this) != null) {
            var1 = this.getBufferSize() + 1;
         } else {
            var1 = this.getBufferSize();
         }

         return var1;
      }


   private fun addLast(task: Task): Task? {
      if (this.getBufferSize() == 127) {
         return var1;
      } else {
         if (var1.taskContext.getTaskMode() == 1) {
            blockingTasksInBuffer$FU.incrementAndGet(this);
         }

         val var2: Int = producerIndex$FU.get(this) and 127;

         while (this.buffer.get(var2) != null) {
            Thread.yield();
         }

         this.buffer.lazySet(var2, var1);
         producerIndex$FU.incrementAndGet(this);
         return null;
      }
   }

   private fun Task?.decrementIfBlocking() {
      if (var1 != null && var1.taskContext.getTaskMode() == 1 && DebugKt.getASSERTIONS_ENABLED() && blockingTasksInBuffer$FU.decrementAndGet(this) < 0) {
         throw new AssertionError();
      }
   }

   private fun pollBuffer(): Task? {
      while (true) {
         val var2: AtomicIntegerFieldUpdater = consumerIndex$FU;
         val var1: Int = consumerIndex$FU.get(this);
         if (var1 - producerIndex$FU.get(this) == 0) {
            return null;
         }

         if (var2.compareAndSet(this, var1, var1 + 1)) {
            val var3: Task = this.buffer.getAndSet(var1 and 127, null);
            if (var3 != null) {
               this.decrementIfBlocking(var3);
               return var3;
            }
         }
      }
   }

   private fun pollTo(queue: GlobalQueue): Boolean {
      val var2: Task = this.pollBuffer();
      if (var2 == null) {
         return false;
      } else {
         var1.addLast(var2);
         return true;
      }
   }

   private fun pollWithExclusiveMode(onlyBlocking: Boolean): Task? {
      while (true) {
         val var5: AtomicReferenceFieldUpdater = lastScheduledTask$FU;
         val var6: Task = lastScheduledTask$FU.get(this) as Task;
         if (var6 != null) {
            val var2: Int = var6.taskContext.getTaskMode();
            var var4: Boolean = true;
            if (var2 != 1) {
               var4 = false;
            }

            if (var4 == var1) {
               if (!ExternalSyntheticBackportWithForwarding0.m(var5, this, var6, null)) {
                  continue;
               }

               return var6;
            }
         }

         val var3: Int = consumerIndex$FU.get(this);
         var var7: Int = producerIndex$FU.get(this);

         while (var3 != var7) {
            if (var1 && blockingTasksInBuffer$FU.get(this) == 0) {
               return null;
            }

            val var8: Task = this.tryExtractFromTheMiddle(--var7, var1);
            if (var8 != null) {
               return var8;
            }
         }

         return null;
      }
   }

   private fun stealWithExclusiveMode(stealingMode: Int): Task? {
      val var2: Int = consumerIndex$FU.get(this);
      val var3: Int = producerIndex$FU.get(this);
      var var4: Boolean = true;
      if (var1 == 1) {
         var1 = var2;
      } else {
         var4 = false;
         var1 = var2;
      }

      while (var1 != var3) {
         if (var4 && blockingTasksInBuffer$FU.get(this) == 0) {
            return null;
         }

         val var5: Task = this.tryExtractFromTheMiddle(var1, var4);
         if (var5 != null) {
            return var5;
         }

         var1++;
      }

      return null;
   }

   private fun tryExtractFromTheMiddle(index: Int, onlyBlocking: Boolean): Task? {
      val var6: Int = var1 and 127;
      val var5: Task = this.buffer.get(var1 and 127);
      if (var5 != null) {
         val var3: Int = var5.taskContext.getTaskMode();
         var var4: Boolean = true;
         if (var3 != 1) {
            var4 = false;
         }

         if (var4 == var2
            && com.google.common.util.concurrent.Striped.SmallLazyStriped..ExternalSyntheticBackportWithForwarding0.m(this.buffer, var6, var5, null)) {
            if (var2) {
               blockingTasksInBuffer$FU.decrementAndGet(this);
            }

            return var5;
         }
      }

      return null;
   }

   private fun tryStealLastScheduled(stealingMode: Int, stolenTaskRef: ObjectRef<Task?>): Long {
      val var7: Task;
      val var8: AtomicReferenceFieldUpdater;
      do {
         var8 = lastScheduledTask$FU;
         var7 = lastScheduledTask$FU.get(this) as Task;
         if (var7 == null) {
            return -2L;
         }

         val var4: Int = var7.taskContext.getTaskMode();
         var var3: Byte = 1;
         if (var4 != 1) {
            var3 = 2;
         }

         if ((var3 and var1) == 0) {
            return -2L;
         }

         val var5: Long = TasksKt.schedulerTimeSource.nanoTime() - var7.submissionTime;
         if (var5 < TasksKt.WORK_STEALING_TIME_RESOLUTION_NS) {
            return TasksKt.WORK_STEALING_TIME_RESOLUTION_NS - var5;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var8, this, var7, null));

      var2.element = (T)var7;
      return -1L;
   }

   public fun add(task: Task, fair: Boolean = false): Task? {
      if (var2) {
         return this.addLast(var1);
      } else {
         var1 = lastScheduledTask$FU.getAndSet(this, var1);
         return if (var1 == null) null else this.addLast(var1);
      }
   }

   public fun offloadAllWorkTo(globalQueue: GlobalQueue) {
      val var2: Task = lastScheduledTask$FU.getAndSet(this, null) as Task;
      if (var2 != null) {
         var1.addLast(var2);
      }

      while (this.pollTo(var1)) {
      }
   }

   public fun poll(): Task? {
      val var2: Task = lastScheduledTask$FU.getAndSet(this, null) as Task;
      var var1: Task = var2;
      if (var2 == null) {
         var1 = this.pollBuffer();
      }

      return var1;
   }

   public fun pollBlocking(): Task? {
      return this.pollWithExclusiveMode(true);
   }

   public fun pollCpu(): Task? {
      return this.pollWithExclusiveMode(false);
   }

   public fun trySteal(stealingMode: Int, stolenTaskRef: ObjectRef<Task?>): Long {
      val var3: Task;
      if (var1 == 3) {
         var3 = this.pollBuffer();
      } else {
         var3 = this.stealWithExclusiveMode(var1);
      }

      if (var3 != null) {
         var2.element = (T)var3;
         return -1L;
      } else {
         return this.tryStealLastScheduled(var1, var2);
      }
   }
}
