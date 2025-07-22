package kotlinx.coroutines

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import kotlin.jvm.functions.Function1
import kotlinx.atomicfu.AtomicInt

private class ThreadState(job: Job) : Function1<java.lang.Throwable, Unit> {
   private final val _state: AtomicInt
   private final var cancelHandle: DisposableHandle?
   private final val job: Job
   private final val targetThread: Thread

   init {
      this.job = var1;
      this.targetThread = Thread.currentThread();
   }

   private fun invalidState(state: Int): Nothing {
      val var2: StringBuilder = new StringBuilder("Illegal state ");
      var2.append(var1);
      throw new IllegalStateException(var2.toString().toString());
   }

   fun `loop$atomicfu`(var1: AtomicIntegerFieldUpdater, var2: (Int?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   public fun clearInterrupt() {
      val var2: AtomicIntegerFieldUpdater = _state$FU;

      while (true) {
         val var1: Int = var2.get(this);
         if (var1 != 0) {
            if (var1 != 2) {
               if (var1 == 3) {
                  Thread.interrupted();
                  return;
               }

               this.invalidState(var1);
               throw new KotlinNothingValueException();
            }
         } else if (_state$FU.compareAndSet(this, var1, 1)) {
            if (this.cancelHandle != null) {
               this.cancelHandle.dispose();
            }

            return;
         }
      }
   }

   public open operator fun invoke(cause: Throwable?) {
      val var3: AtomicIntegerFieldUpdater = _state$FU;

      val var2: Int;
      do {
         var2 = var3.get(this);
         if (var2 != 0) {
            if (var2 != 1 && var2 != 2 && var2 != 3) {
               this.invalidState(var2);
               throw new KotlinNothingValueException();
            } else {
               return;
            }
         }

         var4 = _state$FU;
      } while (!_state$FU.compareAndSet(this, var2, 2));

      this.targetThread.interrupt();
      var4.set(this, 3);
   }

   public fun setup() {
      this.cancelHandle = this.job.invokeOnCompletion(true, true, this as (java.lang.Throwable?) -> Unit);
      val var2: AtomicIntegerFieldUpdater = _state$FU;

      val var1: Int;
      do {
         var1 = var2.get(this);
         if (var1 != 0) {
            if (var1 != 2 && var1 != 3) {
               this.invalidState(var1);
               throw new KotlinNothingValueException();
            } else {
               return;
            }
         }
      } while (!_state$FU.compareAndSet(this, var1, 0));
   }
}
