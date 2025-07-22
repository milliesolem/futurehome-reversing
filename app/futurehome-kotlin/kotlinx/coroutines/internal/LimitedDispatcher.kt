package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.atomicfu.AtomicInt
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandlerKt
import kotlinx.coroutines.DefaultExecutorKt
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DisposableHandle

internal class LimitedDispatcher(dispatcher: CoroutineDispatcher, parallelism: Int) : CoroutineDispatcher, Delay {
   private final val dispatcher: CoroutineDispatcher
   private final val parallelism: Int
   private final val queue: LockFreeTaskQueue<Runnable>
   private final val runningWorkers: AtomicInt
   private final val workerAllocationLock: Any

   init {
      this.dispatcher = var1;
      this.parallelism = var2;
      val var4: Delay;
      if (var1 is Delay) {
         var4 = var1 as Delay;
      } else {
         var4 = null;
      }

      var var3: Delay = var4;
      if (var4 == null) {
         var3 = DefaultExecutorKt.getDefaultDelay();
      }

      this.$$delegate_0 = var3;
      this.queue = new LockFreeTaskQueue<>(false);
      this.workerAllocationLock = new Object();
   }

   private inline fun dispatchInternal(block: Runnable, startWorker: (kotlinx.coroutines.internal.LimitedDispatcher.Worker) -> Unit) {
      this.queue.addLast(var1);
      if (runningWorkers$FU.get(this) < this.parallelism) {
         if (this.tryAllocateWorker()) {
            var1 = this.obtainTaskOrDeallocateWorker();
            if (var1 != null) {
               var2.invoke(new LimitedDispatcher.Worker(this, var1));
            }
         }
      }
   }

   private fun obtainTaskOrDeallocateWorker(): Runnable? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield kotlinx/coroutines/internal/LimitedDispatcher.queue Lkotlinx/coroutines/internal/LockFreeTaskQueue;
      // 04: invokevirtual kotlinx/coroutines/internal/LockFreeTaskQueue.removeFirstOrNull ()Ljava/lang/Object;
      // 07: checkcast java/lang/Runnable
      // 0a: astore 2
      // 0b: aload 2
      // 0c: ifnonnull 40
      // 0f: aload 0
      // 10: getfield kotlinx/coroutines/internal/LimitedDispatcher.workerAllocationLock Ljava/lang/Object;
      // 13: astore 2
      // 14: aload 2
      // 15: monitorenter
      // 16: getstatic kotlinx/coroutines/internal/LimitedDispatcher.runningWorkers$FU Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
      // 19: astore 3
      // 1a: aload 3
      // 1b: aload 0
      // 1c: invokevirtual java/util/concurrent/atomic/AtomicIntegerFieldUpdater.decrementAndGet (Ljava/lang/Object;)I
      // 1f: pop
      // 20: aload 0
      // 21: getfield kotlinx/coroutines/internal/LimitedDispatcher.queue Lkotlinx/coroutines/internal/LockFreeTaskQueue;
      // 24: invokevirtual kotlinx/coroutines/internal/LockFreeTaskQueue.getSize ()I
      // 27: istore 1
      // 28: iload 1
      // 29: ifne 30
      // 2c: aload 2
      // 2d: monitorexit
      // 2e: aconst_null
      // 2f: areturn
      // 30: aload 3
      // 31: aload 0
      // 32: invokevirtual java/util/concurrent/atomic/AtomicIntegerFieldUpdater.incrementAndGet (Ljava/lang/Object;)I
      // 35: pop
      // 36: aload 2
      // 37: monitorexit
      // 38: goto 00
      // 3b: astore 3
      // 3c: aload 2
      // 3d: monitorexit
      // 3e: aload 3
      // 3f: athrow
      // 40: aload 2
      // 41: areturn
   }

   private fun tryAllocateWorker(): Boolean {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield kotlinx/coroutines/internal/LimitedDispatcher.workerAllocationLock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: getstatic kotlinx/coroutines/internal/LimitedDispatcher.runningWorkers$FU Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
      // 0a: astore 4
      // 0c: aload 4
      // 0e: aload 0
      // 0f: invokevirtual java/util/concurrent/atomic/AtomicIntegerFieldUpdater.get (Ljava/lang/Object;)I
      // 12: istore 1
      // 13: aload 0
      // 14: getfield kotlinx/coroutines/internal/LimitedDispatcher.parallelism I
      // 17: istore 2
      // 18: iload 1
      // 19: iload 2
      // 1a: if_icmplt 21
      // 1d: aload 3
      // 1e: monitorexit
      // 1f: bipush 0
      // 20: ireturn
      // 21: aload 4
      // 23: aload 0
      // 24: invokevirtual java/util/concurrent/atomic/AtomicIntegerFieldUpdater.incrementAndGet (Ljava/lang/Object;)I
      // 27: pop
      // 28: aload 3
      // 29: monitorexit
      // 2a: bipush 1
      // 2b: ireturn
      // 2c: astore 4
      // 2e: aload 3
      // 2f: monitorexit
      // 30: aload 4
      // 32: athrow
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
   public override suspend fun delay(time: Long) {
      return this.$$delegate_0.delay(var1, var3);
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      this.queue.addLast(var2);
      if (runningWorkers$FU.get(this) < this.parallelism && this.tryAllocateWorker()) {
         val var3: Runnable = this.obtainTaskOrDeallocateWorker();
         if (var3 != null) {
            this.dispatcher.dispatch(this, new LimitedDispatcher.Worker(this, var3));
         }
      }
   }

   public override fun dispatchYield(context: CoroutineContext, block: Runnable) {
      this.queue.addLast(var2);
      if (runningWorkers$FU.get(this) < this.parallelism && this.tryAllocateWorker()) {
         val var3: Runnable = this.obtainTaskOrDeallocateWorker();
         if (var3 != null) {
            this.dispatcher.dispatchYield(this, new LimitedDispatcher.Worker(this, var3));
         }
      }
   }

   public override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
      return this.$$delegate_0.invokeOnTimeout(var1, var3, var4);
   }

   public override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
      LimitedDispatcherKt.checkParallelism(var1);
      return if (var1 >= this.parallelism) this else super.limitedParallelism(var1);
   }

   public override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
      this.$$delegate_0.scheduleResumeAfterDelay(var1, var3);
   }

   private inner class Worker(currentTask: Runnable) : Runnable {
      private final var currentTask: Runnable

      init {
         this.this$0 = var1;
         this.currentTask = var2;
      }

      public override fun run() {
         var var1: Int = 0;

         while (true) {
            label20:
            try {
               this.currentTask.run();
            } catch (var4: java.lang.Throwable) {
               CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, var4);
               break label20;
            }

            val var3: Runnable = LimitedDispatcher.access$obtainTaskOrDeallocateWorker(this.this$0);
            if (var3 == null) {
               return;
            }

            this.currentTask = var3;
            val var2: Int = var1 + 1;
            var1 += 1;
            if (var2 >= 16) {
               var1 = var2;
               if (LimitedDispatcher.access$getDispatcher$p(this.this$0).isDispatchNeeded(this.this$0)) {
                  LimitedDispatcher.access$getDispatcher$p(this.this$0).dispatch(this.this$0, this);
                  return;
               }
            }
         }
      }
   }
}
