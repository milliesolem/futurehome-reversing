package kotlinx.coroutines

import kotlinx.coroutines.internal.LimitedDispatcherKt

internal abstract class EventLoop : CoroutineDispatcher {
   public final val isActive: Boolean
      public final get() {
         val var1: Boolean;
         if (this.useCount > 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   protected open val isEmpty: Boolean
      protected open get() {
         return this.isUnconfinedQueueEmpty();
      }


   public final val isUnconfinedLoopActive: Boolean
      public final get() {
         var var1: Boolean = true;
         if (this.useCount < this.delta(true)) {
            var1 = false;
         }

         return var1;
      }


   public final val isUnconfinedQueueEmpty: Boolean
      public final get() {
         val var1: Boolean;
         if (this.unconfinedQueue != null) {
            var1 = this.unconfinedQueue.isEmpty();
         } else {
            var1 = true;
         }

         return var1;
      }


   protected open val nextTime: Long
      protected open get() {
         var var1: Long = java.lang.Long.MAX_VALUE;
         if (this.unconfinedQueue == null) {
            return java.lang.Long.MAX_VALUE;
         } else {
            if (!this.unconfinedQueue.isEmpty()) {
               var1 = 0L;
            }

            return var1;
         }
      }


   private final var shared: Boolean
   private final var unconfinedQueue: ArrayDeque<DispatchedTask<*>>?
   private final var useCount: Long

   private fun delta(unconfined: Boolean): Long {
      val var2: Long;
      if (var1) {
         var2 = 4294967296L;
      } else {
         var2 = 1L;
      }

      return var2;
   }

   public fun decrementUseCount(unconfined: Boolean = false) {
      val var2: Long = this.useCount - this.delta(var1);
      this.useCount = var2;
      if (var2 <= 0L) {
         if (DebugKt.getASSERTIONS_ENABLED() && this.useCount != 0L) {
            throw new AssertionError();
         } else {
            if (this.shared) {
               this.shutdown();
            }
         }
      }
   }

   public fun dispatchUnconfined(task: DispatchedTask<*>) {
      var var2: ArrayDeque = this.unconfinedQueue;
      if (this.unconfinedQueue == null) {
         var2 = new ArrayDeque();
         this.unconfinedQueue = var2;
      }

      var2.addLast(var1);
   }

   public fun incrementUseCount(unconfined: Boolean = false) {
      this.useCount = this.useCount + this.delta(var1);
      if (!var1) {
         this.shared = true;
      }
   }

   public override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
      LimitedDispatcherKt.checkParallelism(var1);
      return this;
   }

   public open fun processNextEvent(): Long {
      return if (!this.processUnconfinedEvent()) java.lang.Long.MAX_VALUE else 0L;
   }

   public fun processUnconfinedEvent(): Boolean {
      if (this.unconfinedQueue == null) {
         return false;
      } else {
         val var2: DispatchedTask = this.unconfinedQueue.removeFirstOrNull();
         if (var2 == null) {
            return false;
         } else {
            var2.run();
            return true;
         }
      }
   }

   public open fun shouldBeProcessedFromContext(): Boolean {
      return false;
   }

   public open fun shutdown() {
   }
}
