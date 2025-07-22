package kotlinx.coroutines.scheduling

import java.io.Closeable
import java.util.ArrayList
import java.util.concurrent.Executor
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicLongFieldUpdater
import java.util.concurrent.locks.LockSupport
import kotlin.jvm.internal.Ref
import kotlin.jvm.internal.Ref.ObjectRef
import kotlin.random.Random
import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.AtomicLong
import kotlinx.coroutines.AbstractTimeSource
import kotlinx.coroutines.AbstractTimeSourceKt
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DebugStringsKt
import kotlinx.coroutines.internal.ResizableAtomicArray
import kotlinx.coroutines.internal.Symbol

internal class CoroutineScheduler(corePoolSize: Int,
      maxPoolSize: Int,
      idleWorkerKeepAliveNs: Long = TasksKt.IDLE_WORKER_KEEP_ALIVE_NS,
      schedulerName: String = TasksKt.DEFAULT_SCHEDULER_NAME
   ) :
   Executor,
   Closeable {
   private final val _isTerminated: AtomicBoolean

   private final val availableCpuPermits: Int
      private final inline get() {
         return (int)((controlState$FU.get(this) and 9223367638808264704L) shr 42);
      }


   private final val controlState: AtomicLong
   public final val corePoolSize: Int

   private final val createdWorkers: Int
      private final inline get() {
         return (int)(access$getControlState$FU$p().get(this) and 2097151L);
      }


   public final val globalBlockingQueue: GlobalQueue
   public final val globalCpuQueue: GlobalQueue
   public final val idleWorkerKeepAliveNs: Long

   public final val isTerminated: Boolean
      public final get() {
         val var1: Boolean;
         if (_isTerminated$FU.get(this) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val maxPoolSize: Int
   private final val parkedWorkersStack: AtomicLong
   public final val schedulerName: String
   public final val workers: ResizableAtomicArray<kotlinx.coroutines.scheduling.CoroutineScheduler.Worker>

   init {
      this.corePoolSize = var1;
      this.maxPoolSize = var2;
      this.idleWorkerKeepAliveNs = var3;
      this.schedulerName = var5;
      if (var1 >= 1) {
         if (var2 >= var1) {
            if (var2 <= 2097150) {
               if (var3 > 0L) {
                  this.globalCpuQueue = new GlobalQueue();
                  this.globalBlockingQueue = new GlobalQueue();
                  this.workers = new ResizableAtomicArray<>((var1 + 1) * 2);
                  this.controlState = (long)var1 shl 42;
                  this._isTerminated = 0;
               } else {
                  val var9: StringBuilder = new StringBuilder("Idle worker keep alive time ");
                  var9.append(var3);
                  var9.append(" must be positive");
                  throw new IllegalArgumentException(var9.toString().toString());
               }
            } else {
               val var8: StringBuilder = new StringBuilder("Max pool size ");
               var8.append(var2);
               var8.append(" should not exceed maximal supported number of threads 2097150");
               throw new IllegalArgumentException(var8.toString().toString());
            }
         } else {
            val var7: StringBuilder = new StringBuilder("Max pool size ");
            var7.append(var2);
            var7.append(" should be greater than or equals to core pool size ");
            var7.append(var1);
            throw new IllegalArgumentException(var7.toString().toString());
         }
      } else {
         val var6: StringBuilder = new StringBuilder("Core pool size ");
         var6.append(var1);
         var6.append(" should be at least 1");
         throw new IllegalArgumentException(var6.toString().toString());
      }
   }

   private fun addToGlobalQueue(task: Task): Boolean {
      val var2: Boolean;
      if (var1.taskContext.getTaskMode() == 1) {
         var2 = this.globalBlockingQueue.addLast(var1);
      } else {
         var2 = this.globalCpuQueue.addLast(var1);
      }

      return var2;
   }

   private inline fun blockingTasks(state: Long): Int {
      return (int)((var1 and 4398044413952L) shr 21);
   }

   private fun createNewWorker(): Int {
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
      // 01: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.workers Lkotlinx/coroutines/internal/ResizableAtomicArray;
      // 04: astore 7
      // 06: aload 7
      // 08: monitorenter
      // 09: aload 0
      // 0a: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler.isTerminated ()Z
      // 0d: istore 4
      // 0f: iload 4
      // 11: ifeq 19
      // 14: aload 7
      // 16: monitorexit
      // 17: bipush -1
      // 18: ireturn
      // 19: getstatic kotlinx/coroutines/scheduling/CoroutineScheduler.controlState$FU Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
      // 1c: astore 9
      // 1e: aload 9
      // 20: aload 0
      // 21: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.get (Ljava/lang/Object;)J
      // 24: lstore 5
      // 26: lload 5
      // 28: ldc2_w 2097151
      // 2b: land
      // 2c: l2i
      // 2d: istore 2
      // 2e: iload 2
      // 2f: lload 5
      // 31: ldc2_w 4398044413952
      // 34: land
      // 35: bipush 21
      // 37: lshr
      // 38: l2i
      // 39: isub
      // 3a: bipush 0
      // 3b: invokestatic kotlin/ranges/RangesKt.coerceAtLeast (II)I
      // 3e: istore 1
      // 3f: aload 0
      // 40: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.corePoolSize I
      // 43: istore 3
      // 44: iload 1
      // 45: iload 3
      // 46: if_icmplt 4e
      // 49: aload 7
      // 4b: monitorexit
      // 4c: bipush 0
      // 4d: ireturn
      // 4e: aload 0
      // 4f: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.maxPoolSize I
      // 52: istore 3
      // 53: iload 2
      // 54: iload 3
      // 55: if_icmplt 5d
      // 58: aload 7
      // 5a: monitorexit
      // 5b: bipush 0
      // 5c: ireturn
      // 5d: invokestatic kotlinx/coroutines/scheduling/CoroutineScheduler.access$getControlState$FU$p ()Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
      // 60: aload 0
      // 61: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.get (Ljava/lang/Object;)J
      // 64: ldc2_w 2097151
      // 67: land
      // 68: l2i
      // 69: bipush 1
      // 6a: iadd
      // 6b: istore 2
      // 6c: iload 2
      // 6d: ifle c3
      // 70: aload 0
      // 71: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.workers Lkotlinx/coroutines/internal/ResizableAtomicArray;
      // 74: iload 2
      // 75: invokevirtual kotlinx/coroutines/internal/ResizableAtomicArray.get (I)Ljava/lang/Object;
      // 78: ifnonnull c3
      // 7b: new kotlinx/coroutines/scheduling/CoroutineScheduler$Worker
      // 7e: astore 8
      // 80: aload 8
      // 82: aload 0
      // 83: iload 2
      // 84: invokespecial kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.<init> (Lkotlinx/coroutines/scheduling/CoroutineScheduler;I)V
      // 87: aload 0
      // 88: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.workers Lkotlinx/coroutines/internal/ResizableAtomicArray;
      // 8b: iload 2
      // 8c: aload 8
      // 8e: invokevirtual kotlinx/coroutines/internal/ResizableAtomicArray.setSynchronized (ILjava/lang/Object;)V
      // 91: aload 9
      // 93: aload 0
      // 94: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.incrementAndGet (Ljava/lang/Object;)J
      // 97: lstore 5
      // 99: iload 2
      // 9a: ldc2_w 2097151
      // 9d: lload 5
      // 9f: land
      // a0: l2i
      // a1: if_icmpne b0
      // a4: aload 7
      // a6: monitorexit
      // a7: aload 8
      // a9: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.start ()V
      // ac: iload 1
      // ad: bipush 1
      // ae: iadd
      // af: ireturn
      // b0: new java/lang/IllegalArgumentException
      // b3: astore 8
      // b5: aload 8
      // b7: ldc_w "Failed requirement."
      // ba: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // bd: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // c0: aload 8
      // c2: athrow
      // c3: new java/lang/IllegalArgumentException
      // c6: astore 8
      // c8: aload 8
      // ca: ldc_w "Failed requirement."
      // cd: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // d0: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // d3: aload 8
      // d5: athrow
      // d6: astore 8
      // d8: aload 7
      // da: monitorexit
      // db: aload 8
      // dd: athrow
   }

   private inline fun createdWorkers(state: Long): Int {
      return (int)(var1 and 2097151L);
   }

   private fun currentWorker(): kotlinx.coroutines.scheduling.CoroutineScheduler.Worker? {
      val var2: Thread = Thread.currentThread();
      val var5: CoroutineScheduler.Worker;
      if (var2 is CoroutineScheduler.Worker) {
         var5 = var2 as CoroutineScheduler.Worker;
      } else {
         var5 = null;
      }

      var var3: CoroutineScheduler.Worker = null;
      if (var5 != null) {
         var3 = null;
         if (CoroutineScheduler.Worker.access$getThis$0$p(var5) == this) {
            var3 = var5;
         }
      }

      return var3;
   }

   private inline fun decrementBlockingTasks() {
      access$getControlState$FU$p().addAndGet(this, -2097152L);
   }

   private inline fun decrementCreatedWorkers(): Int {
      return (int)(access$getControlState$FU$p().getAndDecrement(this) and 2097151L);
   }

   private inline fun incrementBlockingTasks(): Long {
      return controlState$FU.addAndGet(this, 2097152L);
   }

   private inline fun incrementCreatedWorkers(): Int {
      return (int)(controlState$FU.incrementAndGet(this) and 2097151L);
   }

   fun `loop$atomicfu`(var1: AtomicLongFieldUpdater, var2: (java.lang.Long?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private fun parkedWorkersStackNextIndex(worker: kotlinx.coroutines.scheduling.CoroutineScheduler.Worker): Int {
      var var3: Any = var1.getNextParkedWorker();

      while (var3 != NOT_IN_STACK) {
         if (var3 == null) {
            return 0;
         }

         val var4: CoroutineScheduler.Worker = var3 as CoroutineScheduler.Worker;
         val var2: Int = (var3 as CoroutineScheduler.Worker).getIndexInArray();
         if (var2 != 0) {
            return var2;
         }

         var3 = var4.getNextParkedWorker();
      }

      return -1;
   }

   private fun parkedWorkersStackPop(): kotlinx.coroutines.scheduling.CoroutineScheduler.Worker? {
      val var5: AtomicLongFieldUpdater = parkedWorkersStack$FU;

      val var2: Long;
      val var4: CoroutineScheduler.Worker;
      val var6: Int;
      do {
         var2 = var5.get(this);
         var4 = this.workers.get((int)(2097151L and var2));
         if (var4 == null) {
            return null;
         }

         var6 = this.parkedWorkersStackNextIndex(var4);
      } while (var6 < 0 || !parkedWorkersStack$FU.compareAndSet(this, var2, var6 | 2097152L + var2 & -2097152L));

      var4.setNextParkedWorker(NOT_IN_STACK);
      return var4;
   }

   private inline fun releaseCpuPermit(): Long {
      return access$getControlState$FU$p().addAndGet(this, 4398046511104L);
   }

   private fun signalBlockingWork(stateSnapshot: Long, skipUnpark: Boolean) {
      if (!var3) {
         if (!this.tryUnpark()) {
            if (!this.tryCreateWorker(var1)) {
               this.tryUnpark();
            }
         }
      }
   }

   private fun kotlinx.coroutines.scheduling.CoroutineScheduler.Worker?.submitToLocalQueue(task: Task, tailDispatch: Boolean): Task? {
      if (var1 == null) {
         return var2;
      } else if (var1.state === CoroutineScheduler.WorkerState.TERMINATED) {
         return var2;
      } else if (var2.taskContext.getTaskMode() == 0 && var1.state === CoroutineScheduler.WorkerState.BLOCKING) {
         return var2;
      } else {
         var1.mayHaveLocalTasks = true;
         return var1.localQueue.add(var2, var3);
      }
   }

   private inline fun tryAcquireCpuPermit(): Boolean {
      val var3: AtomicLongFieldUpdater = access$getControlState$FU$p();

      val var1: Long;
      do {
         var1 = var3.get(this);
         if ((int)((9223367638808264704L and var1) shr 42) == 0) {
            return false;
         }
      } while (!access$getControlState$FU$p().compareAndSet(this, var1, var1 - 4398046511104L));

      return true;
   }

   private fun tryCreateWorker(state: Long = controlState$FU.get(var0)): Boolean {
      if (RangesKt.coerceAtLeast((int)(2097151L and var1) - (int)((var1 and 4398044413952L) shr 21), 0) < this.corePoolSize) {
         val var3: Int = this.createNewWorker();
         if (var3 == 1 && this.corePoolSize > 1) {
            this.createNewWorker();
         }

         if (var3 > 0) {
            return true;
         }
      }

      return false;
   }

   private fun tryUnpark(): Boolean {
      val var1: CoroutineScheduler.Worker;
      do {
         var1 = this.parkedWorkersStackPop();
         if (var1 == null) {
            return false;
         }
      } while (!CoroutineScheduler.Worker.getWorkerCtl$FU().compareAndSet(var1, -1, 0));

      LockSupport.unpark(var1);
      return true;
   }

   public inline fun availableCpuPermits(state: Long): Int {
      return (int)((var1 and 9223367638808264704L) shr 42);
   }

   public override fun close() {
      this.shutdown(10000L);
   }

   public fun createTask(block: Runnable, taskContext: TaskContext): Task {
      val var3: Long = TasksKt.schedulerTimeSource.nanoTime();
      if (var1 is Task) {
         val var5: Task = var1 as Task;
         (var1 as Task).submissionTime = var3;
         var5.taskContext = var2;
         return var5;
      } else {
         return new TaskImpl(var1, var3, var2);
      }
   }

   public fun dispatch(block: Runnable, taskContext: TaskContext = TasksKt.NonBlockingContext, tailDispatch: Boolean = false) {
      val var9: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
      if (var9 != null) {
         var9.trackTask();
      }

      val var12: Task = this.createTask(var1, var2);
      val var14: Boolean;
      if (var12.taskContext.getTaskMode() == 1) {
         var14 = true;
      } else {
         var14 = false;
      }

      val var5: Long;
      if (var14) {
         var5 = controlState$FU.addAndGet(this, 2097152L);
      } else {
         var5 = 0L;
      }

      val var10: CoroutineScheduler.Worker = this.currentWorker();
      val var13: Task = this.submitToLocalQueue(var10, var12, var3);
      if (var13 != null && !this.addToGlobalQueue(var13)) {
         val var11: StringBuilder = new StringBuilder();
         var11.append(this.schedulerName);
         var11.append(" was terminated");
         throw new RejectedExecutionException(var11.toString());
      } else {
         var var7: Boolean = false;
         if (var3) {
            var7 = false;
            if (var10 != null) {
               var7 = true;
            }
         }

         if (var14) {
            this.signalBlockingWork(var5, var7);
         } else {
            if (var7) {
               return;
            }

            this.signalCpuWork();
         }
      }
   }

   public override fun execute(command: Runnable) {
      dispatch$default(this, var1, null, false, 6, null);
   }

   public fun parkedWorkersStackPush(worker: kotlinx.coroutines.scheduling.CoroutineScheduler.Worker): Boolean {
      if (var1.getNextParkedWorker() != NOT_IN_STACK) {
         return false;
      } else {
         val var6: AtomicLongFieldUpdater = parkedWorkersStack$FU;

         val var2: Int;
         val var4: Long;
         do {
            var4 = var6.get(this);
            val var3: Int = (int)(2097151L and var4);
            var2 = var1.getIndexInArray();
            if (DebugKt.getASSERTIONS_ENABLED() && var2 == 0) {
               throw new AssertionError();
            }

            var1.setNextParkedWorker(this.workers.get(var3));
         } while (!parkedWorkersStack$FU.compareAndSet(this, var4, 2097152L + var4 & -2097152L | var2));

         return true;
      }
   }

   public fun parkedWorkersStackTopUpdate(worker: kotlinx.coroutines.scheduling.CoroutineScheduler.Worker, oldIndex: Int, newIndex: Int) {
      val var8: AtomicLongFieldUpdater = parkedWorkersStack$FU;

      var var4: Int;
      val var6: Long;
      do {
         var6 = var8.get(this);
         val var5: Int = (int)(2097151L and var6);
         var4 = (int)(2097151L and var6);
         if (var5 == var2) {
            if (var3 == 0) {
               var4 = this.parkedWorkersStackNextIndex(var1);
            } else {
               var4 = var3;
            }
         }
      } while (var4 < 0 || !parkedWorkersStack$FU.compareAndSet(this, var6, 2097152L + var6 & -2097152L | var4));
   }

   public fun runSafely(task: Task) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 1
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.get(ArrayList.java:428)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.<init>(IfStatement.java:74)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.isHead(IfStatement.java:187)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.detectStatement(DomHelper.java:796)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.findSimpleStatements(DomHelper.java:725)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.processStatement(DomHelper.java:379)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:208)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokevirtual kotlinx/coroutines/scheduling/Task.run ()V
      // 04: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 07: astore 1
      // 08: aload 1
      // 09: ifnull 2e
      // 0c: aload 1
      // 0d: invokevirtual kotlinx/coroutines/AbstractTimeSource.unTrackTask ()V
      // 10: goto 2e
      // 13: astore 1
      // 14: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
      // 17: astore 2
      // 18: aload 2
      // 19: invokevirtual java/lang/Thread.getUncaughtExceptionHandler ()Ljava/lang/Thread$UncaughtExceptionHandler;
      // 1c: aload 2
      // 1d: aload 1
      // 1e: invokeinterface java/lang/Thread$UncaughtExceptionHandler.uncaughtException (Ljava/lang/Thread;Ljava/lang/Throwable;)V 3
      // 23: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 26: astore 1
      // 27: aload 1
      // 28: ifnull 2e
      // 2b: goto 0c
      // 2e: return
      // 2f: astore 1
      // 30: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 33: astore 2
      // 34: aload 2
      // 35: ifnull 3c
      // 38: aload 2
      // 39: invokevirtual kotlinx/coroutines/AbstractTimeSource.unTrackTask ()V
      // 3c: aload 1
      // 3d: athrow
   }

   public fun shutdown(timeout: Long) {
      if (_isTerminated$FU.compareAndSet(this, 0, 1)) {
         label123: {
            val var9: CoroutineScheduler.Worker = this.currentWorker();
            synchronized (this.workers){} // $VF: monitorenter 

            try {
               ;
            } catch (var10: java.lang.Throwable) {
               // $VF: monitorexit
            }

            val var5: <unknown>;
            val var4: Int = (int)(var5 and 2097151L);
            // $VF: monitorexit
            if (1 <= var4) {
               var var3: Int = 1;

               while (true) {
                  var var13: Any = this.workers.get(var3);
                  val var8: CoroutineScheduler.Worker = var13 as CoroutineScheduler.Worker;
                  if (var13 as CoroutineScheduler.Worker != var9) {
                     while (var8.isAlive()) {
                        LockSupport.unpark(var8);
                        var8.join(var1);
                     }

                     var13 = var8.state;
                     if (DebugKt.getASSERTIONS_ENABLED() && var13 != CoroutineScheduler.WorkerState.TERMINATED) {
                        throw new AssertionError();
                     }

                     var8.localQueue.offloadAllWorkTo(this.globalBlockingQueue);
                  }

                  if (var3 == var4) {
                     break;
                  }

                  var3++;
               }
            }

            this.globalBlockingQueue.close();
            this.globalCpuQueue.close();

            while (true) {
               var var15: Task;
               label51: {
                  if (var9 != null) {
                     val var16: Task = var9.findTask(true);
                     var15 = var16;
                     if (var16 != null) {
                        break label51;
                     }
                  }

                  var var17: Task = this.globalCpuQueue.removeFirstOrNull();
                  var15 = var17;
                  if (var17 == null) {
                     var17 = this.globalBlockingQueue.removeFirstOrNull();
                     var15 = var17;
                     if (var17 == null) {
                        if (var9 != null) {
                           var9.tryReleaseCpu(CoroutineScheduler.WorkerState.TERMINATED);
                        }

                        if (DebugKt.getASSERTIONS_ENABLED() && (int)((controlState$FU.get(this) and 9223367638808264704L) shr 42) != this.corePoolSize) {
                           throw new AssertionError();
                        }

                        parkedWorkersStack$FU.set(this, 0L);
                        controlState$FU.set(this, 0L);
                        return;
                     }
                  }
               }

               this.runSafely(var15);
            }
         }
      }
   }

   public fun signalCpuWork() {
      if (!this.tryUnpark()) {
         if (!tryCreateWorker$default(this, 0L, 1, null)) {
            this.tryUnpark();
         }
      }
   }

   public override fun toString(): String {
      val var16: ArrayList = new ArrayList();
      val var12: Int = this.workers.currentLength();
      var var6: Int = 0;
      var var5: Int = 0;
      var var3: Int = 0;
      var var4: Int = 0;
      var var2: Int = 0;
      var var1: Int = 1;

      while (var1 < var12) {
         val var17: CoroutineScheduler.Worker = this.workers.get(var1);
         var var7: Int;
         var var8: Int;
         var var9: Int;
         var var10: Int;
         if (var17 == null) {
            var7 = var6;
            var9 = var5;
            var8 = var3;
            var10 = var2;
         } else {
            val var13: Int = var17.localQueue.getSize$kotlinx_coroutines_core();
            var7 = CoroutineScheduler.WhenMappings.$EnumSwitchMapping$0[var17.state.ordinal()];
            if (var7 != 1) {
               if (var7 != 2) {
                  if (var7 != 3) {
                     if (var7 != 4) {
                        if (var7 != 5) {
                           var7 = var6;
                           var9 = var5;
                           var8 = var3;
                           var10 = var2;
                        } else {
                           var10 = var2 + 1;
                           var7 = var6;
                           var9 = var5;
                           var8 = var3;
                        }
                     } else {
                        val var11: Int = var4 + 1;
                        var7 = var6;
                        var9 = var5;
                        var8 = var3;
                        var4 = var11;
                        var10 = var2;
                        if (var13 > 0) {
                           val var18: java.util.Collection = var16;
                           val var21: StringBuilder = new StringBuilder();
                           var21.append(var13);
                           var21.append('d');
                           var18.add(var21.toString());
                           var7 = var6;
                           var9 = var5;
                           var8 = var3;
                           var4 = var11;
                           var10 = var2;
                        }
                     }
                  } else {
                     var7 = var6 + 1;
                     val var22: java.util.Collection = var16;
                     val var25: StringBuilder = new StringBuilder();
                     var25.append(var13);
                     var25.append('c');
                     var22.add(var25.toString());
                     var9 = var5;
                     var8 = var3;
                     var10 = var2;
                  }
               } else {
                  var9 = var5 + 1;
                  val var26: java.util.Collection = var16;
                  val var23: StringBuilder = new StringBuilder();
                  var23.append(var13);
                  var23.append('b');
                  var26.add(var23.toString());
                  var7 = var6;
                  var8 = var3;
                  var10 = var2;
               }
            } else {
               var8 = var3 + 1;
               var10 = var2;
               var9 = var5;
               var7 = var6;
            }
         }

         var1++;
         var6 = var7;
         var5 = var9;
         var3 = var8;
         var2 = var10;
      }

      val var14: Long = controlState$FU.get(this);
      val var24: StringBuilder = new StringBuilder();
      var24.append(this.schedulerName);
      var24.append('@');
      var24.append(DebugStringsKt.getHexAddress(this));
      var24.append("[Pool Size {core = ");
      var24.append(this.corePoolSize);
      var24.append(", max = ");
      var24.append(this.maxPoolSize);
      var24.append("}, Worker States {CPU = ");
      var24.append(var6);
      var24.append(", blocking = ");
      var24.append(var5);
      var24.append(", parked = ");
      var24.append(var3);
      var24.append(", dormant = ");
      var24.append(var4);
      var24.append(", terminated = ");
      var24.append(var2);
      var24.append("}, running workers queues = ");
      var24.append(var16);
      var24.append(", global CPU queue size = ");
      var24.append(this.globalCpuQueue.getSize());
      var24.append(", global blocking queue size = ");
      var24.append(this.globalBlockingQueue.getSize());
      var24.append(", Control State {created workers= ");
      var24.append((int)(2097151L and var14));
      var24.append(", blocking tasks = ");
      var24.append((int)((4398044413952L and var14) shr 21));
      var24.append(", CPUs acquired = ");
      var24.append(this.corePoolSize - (int)((9223367638808264704L and var14) shr 42));
      var24.append("}]");
      return var24.toString();
   }

   public companion object {
      private const val BLOCKING_MASK: Long
      private const val BLOCKING_SHIFT: Int
      private const val CLAIMED: Int
      private const val CPU_PERMITS_MASK: Long
      private const val CPU_PERMITS_SHIFT: Int
      private const val CREATED_MASK: Long
      internal const val MAX_SUPPORTED_POOL_SIZE: Int
      internal const val MIN_SUPPORTED_POOL_SIZE: Int
      public final val NOT_IN_STACK: Symbol
      private const val PARKED: Int
      private const val PARKED_INDEX_MASK: Long
      private const val PARKED_VERSION_INC: Long
      private const val PARKED_VERSION_MASK: Long
      private const val TERMINATED: Int
   }

   internal inner class Worker private constructor() : Thread {
      public final var indexInArray: Int
         internal final set(index) {
            val var3: StringBuilder = new StringBuilder();
            var3.append(this.this$0.schedulerName);
            var3.append("-worker-");
            val var2: java.lang.String;
            if (var1 == 0) {
               var2 = "TERMINATED";
            } else {
               var2 = java.lang.String.valueOf(var1);
            }

            var3.append(var2);
            this.setName(var3.toString());
            this.indexInArray = var1;
         }


      public final val localQueue: WorkQueue

      public final var mayHaveLocalTasks: Boolean
         private set

      private final var minDelayUntilStealableTaskNs: Long

      public final var nextParkedWorker: Any?
         internal set

      private final var rngState: Int

      public final val scheduler: CoroutineScheduler
         public final inline get() {
            return access$getThis$0$p(this);
         }


      public final var state: kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState
         private set

      private final val stolenTask: ObjectRef<Task?>
      private final var terminationDeadline: Long
      public final val workerCtl: AtomicInt

      init {
         this.this$0 = var1;
         this.setDaemon(true);
         this.localQueue = new WorkQueue();
         this.stolenTask = new Ref.ObjectRef<>();
         this.state = CoroutineScheduler.WorkerState.DORMANT;
         this.nextParkedWorker = CoroutineScheduler.NOT_IN_STACK;
         this.rngState = Random.Default.nextInt();
      }

      public constructor(index: Int) : this(var1) {
         this.setIndexInArray(var2);
      }

      private fun afterTask(taskMode: Int) {
         if (var1 != 0) {
            val var2: CoroutineScheduler = this.this$0;
            CoroutineScheduler.access$getControlState$FU$p().addAndGet(var2, -2097152L);
            val var3: CoroutineScheduler.WorkerState = this.state;
            if (this.state != CoroutineScheduler.WorkerState.TERMINATED) {
               if (DebugKt.getASSERTIONS_ENABLED() && var3 != CoroutineScheduler.WorkerState.BLOCKING) {
                  throw new AssertionError();
               }

               this.state = CoroutineScheduler.WorkerState.DORMANT;
            }
         }
      }

      private fun beforeTask(taskMode: Int) {
         if (var1 != 0) {
            if (this.tryReleaseCpu(CoroutineScheduler.WorkerState.BLOCKING)) {
               this.this$0.signalCpuWork();
            }
         }
      }

      private fun executeTask(task: Task) {
         val var2: Int = var1.taskContext.getTaskMode();
         this.idleReset(var2);
         this.beforeTask(var2);
         this.this$0.runSafely(var1);
         this.afterTask(var2);
      }

      private fun findAnyTask(scanLocalQueue: Boolean): Task? {
         if (var1) {
            val var2: Boolean;
            if (this.nextInt(this.this$0.corePoolSize * 2) == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               val var3: Task = this.pollGlobalQueues();
               if (var3 != null) {
                  return var3;
               }
            }

            var var4: Task = this.localQueue.poll();
            if (var4 != null) {
               return var4;
            }

            if (!var2) {
               var4 = this.pollGlobalQueues();
               if (var4 != null) {
                  return var4;
               }
            }
         } else {
            val var6: Task = this.pollGlobalQueues();
            if (var6 != null) {
               return var6;
            }
         }

         return this.trySteal(3);
      }

      private fun findBlockingTask(): Task? {
         var var2: Task = this.localQueue.pollBlocking();
         var var1: Task = var2;
         if (var2 == null) {
            var2 = this.this$0.globalBlockingQueue.removeFirstOrNull();
            var1 = var2;
            if (var2 == null) {
               var1 = this.trySteal(1);
            }
         }

         return var1;
      }

      private fun findCpuTask(): Task? {
         var var2: Task = this.localQueue.pollCpu();
         var var1: Task = var2;
         if (var2 == null) {
            var2 = this.this$0.globalBlockingQueue.removeFirstOrNull();
            var1 = var2;
            if (var2 == null) {
               var1 = this.trySteal(2);
            }
         }

         return var1;
      }

      @JvmStatic
      fun `getWorkerCtl$FU`(): AtomicIntegerFieldUpdater {
         return workerCtl$FU;
      }

      private fun idleReset(mode: Int) {
         this.terminationDeadline = 0L;
         if (this.state === CoroutineScheduler.WorkerState.PARKING) {
            if (DebugKt.getASSERTIONS_ENABLED() && var1 != 1) {
               throw new AssertionError();
            }

            this.state = CoroutineScheduler.WorkerState.BLOCKING;
         }
      }

      private fun inStack(): Boolean {
         val var1: Boolean;
         if (this.nextParkedWorker != CoroutineScheduler.NOT_IN_STACK) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private fun park() {
         if (this.terminationDeadline == 0L) {
            this.terminationDeadline = System.nanoTime() + this.this$0.idleWorkerKeepAliveNs;
         }

         LockSupport.parkNanos(this.this$0.idleWorkerKeepAliveNs);
         if (System.nanoTime() - this.terminationDeadline >= 0L) {
            this.terminationDeadline = 0L;
            this.tryTerminateWorker();
         }
      }

      private fun pollGlobalQueues(): Task? {
         if (this.nextInt(2) == 0) {
            val var2: Task = this.this$0.globalCpuQueue.removeFirstOrNull();
            return var2 ?: this.this$0.globalBlockingQueue.removeFirstOrNull();
         } else {
            val var1: Task = this.this$0.globalBlockingQueue.removeFirstOrNull();
            return var1 ?: this.this$0.globalCpuQueue.removeFirstOrNull();
         }
      }

      private fun runWorker() {
         label28:
         while (true) {
            var var1: Boolean = false;

            while (!this.this$0.isTerminated() && this.state != CoroutineScheduler.WorkerState.TERMINATED) {
               val var2: Task = this.findTask(this.mayHaveLocalTasks);
               if (var2 != null) {
                  this.minDelayUntilStealableTaskNs = 0L;
                  this.executeTask(var2);
                  continue label28;
               }

               this.mayHaveLocalTasks = false;
               if (this.minDelayUntilStealableTaskNs != 0L) {
                  if (var1) {
                     this.tryReleaseCpu(CoroutineScheduler.WorkerState.PARKING);
                     Thread.interrupted();
                     LockSupport.parkNanos(this.minDelayUntilStealableTaskNs);
                     this.minDelayUntilStealableTaskNs = 0L;
                     continue label28;
                  }

                  var1 = true;
               } else {
                  this.tryPark();
               }
            }

            this.tryReleaseCpu(CoroutineScheduler.WorkerState.TERMINATED);
            return;
         }
      }

      private fun tryAcquireCpuPermit(): Boolean {
         var var1: Boolean = true;
         if (this.state != CoroutineScheduler.WorkerState.CPU_ACQUIRED) {
            val var6: CoroutineScheduler = this.this$0;
            val var7: AtomicLongFieldUpdater = CoroutineScheduler.access$getControlState$FU$p();

            while (true) {
               val var2: Long = var7.get(var6);
               if ((int)((9223367638808264704L and var2) shr 42) == 0) {
                  var1 = false;
                  break;
               }

               if (CoroutineScheduler.access$getControlState$FU$p().compareAndSet(var6, var2, var2 - 4398046511104L)) {
                  this.state = CoroutineScheduler.WorkerState.CPU_ACQUIRED;
                  break;
               }
            }
         }

         return var1;
      }

      private fun tryPark() {
         if (!this.inStack()) {
            this.this$0.parkedWorkersStackPush(this);
         } else {
            workerCtl$FU.set(this, -1);

            while (this.inStack() && workerCtl$FU.get(this) == -1 && !this.this$0.isTerminated() && this.state != CoroutineScheduler.WorkerState.TERMINATED) {
               this.tryReleaseCpu(CoroutineScheduler.WorkerState.PARKING);
               Thread.interrupted();
               this.park();
            }
         }
      }

      private fun trySteal(stealingMode: Int): Task? {
         var var12: CoroutineScheduler = this.this$0;
         val var5: Int = (int)(CoroutineScheduler.access$getControlState$FU$p().get(var12) and 2097151L);
         if (var5 < 2) {
            return null;
         } else {
            var var2: Int = this.nextInt(var5);
            var12 = this.this$0;
            var var3: Int = 0;
            var var6: Long = java.lang.Long.MAX_VALUE;

            while (var3 < var5) {
               val var4: Int = var2 + 1;
               var2 += 1;
               if (var4 > var5) {
                  var2 = 1;
               }

               val var13: CoroutineScheduler.Worker = var12.workers.get(var2);
               var var8: Long;
               if (var13 != null && var13 != this) {
                  val var10: Long = var13.localQueue.trySteal(var1, this.stolenTask);
                  if (var10 == -1L) {
                     val var15: Task = this.stolenTask.element;
                     this.stolenTask.element = null;
                     return var15;
                  }

                  var8 = var6;
                  if (var10 > 0L) {
                     var8 = Math.min(var6, var10);
                  }
               } else {
                  var8 = var6;
               }

               var3++;
               var6 = var8;
            }

            if (var6 == java.lang.Long.MAX_VALUE) {
               var6 = 0L;
            }

            this.minDelayUntilStealableTaskNs = var6;
            return null;
         }
      }

      private fun tryTerminateWorker() {
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
         // 01: getfield kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.this$0 Lkotlinx/coroutines/scheduling/CoroutineScheduler;
         // 04: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.workers Lkotlinx/coroutines/internal/ResizableAtomicArray;
         // 07: astore 4
         // 09: aload 0
         // 0a: getfield kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.this$0 Lkotlinx/coroutines/scheduling/CoroutineScheduler;
         // 0d: astore 5
         // 0f: aload 4
         // 11: monitorenter
         // 12: aload 5
         // 14: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler.isTerminated ()Z
         // 17: istore 3
         // 18: iload 3
         // 19: ifeq 20
         // 1c: aload 4
         // 1e: monitorexit
         // 1f: return
         // 20: invokestatic kotlinx/coroutines/scheduling/CoroutineScheduler.access$getControlState$FU$p ()Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
         // 23: aload 5
         // 25: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.get (Ljava/lang/Object;)J
         // 28: ldc2_w 2097151
         // 2b: land
         // 2c: l2i
         // 2d: istore 1
         // 2e: aload 5
         // 30: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.corePoolSize I
         // 33: istore 2
         // 34: iload 1
         // 35: iload 2
         // 36: if_icmpgt 3d
         // 39: aload 4
         // 3b: monitorexit
         // 3c: return
         // 3d: getstatic kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.workerCtl$FU Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
         // 40: aload 0
         // 41: bipush -1
         // 42: bipush 1
         // 43: invokevirtual java/util/concurrent/atomic/AtomicIntegerFieldUpdater.compareAndSet (Ljava/lang/Object;II)Z
         // 46: istore 3
         // 47: iload 3
         // 48: ifne 4f
         // 4b: aload 4
         // 4d: monitorexit
         // 4e: return
         // 4f: aload 0
         // 50: getfield kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.indexInArray I
         // 53: istore 2
         // 54: aload 0
         // 55: bipush 0
         // 56: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.setIndexInArray (I)V
         // 59: aload 5
         // 5b: aload 0
         // 5c: iload 2
         // 5d: bipush 0
         // 5e: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler.parkedWorkersStackTopUpdate (Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;II)V
         // 61: invokestatic kotlinx/coroutines/scheduling/CoroutineScheduler.access$getControlState$FU$p ()Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
         // 64: aload 5
         // 66: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.getAndDecrement (Ljava/lang/Object;)J
         // 69: ldc2_w 2097151
         // 6c: land
         // 6d: l2i
         // 6e: istore 1
         // 6f: iload 1
         // 70: iload 2
         // 71: if_icmpeq a5
         // 74: aload 5
         // 76: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.workers Lkotlinx/coroutines/internal/ResizableAtomicArray;
         // 79: iload 1
         // 7a: invokevirtual kotlinx/coroutines/internal/ResizableAtomicArray.get (I)Ljava/lang/Object;
         // 7d: astore 6
         // 7f: aload 6
         // 81: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 84: aload 6
         // 86: checkcast kotlinx/coroutines/scheduling/CoroutineScheduler$Worker
         // 89: astore 6
         // 8b: aload 5
         // 8d: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.workers Lkotlinx/coroutines/internal/ResizableAtomicArray;
         // 90: iload 2
         // 91: aload 6
         // 93: invokevirtual kotlinx/coroutines/internal/ResizableAtomicArray.setSynchronized (ILjava/lang/Object;)V
         // 96: aload 6
         // 98: iload 2
         // 99: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.setIndexInArray (I)V
         // 9c: aload 5
         // 9e: aload 6
         // a0: iload 1
         // a1: iload 2
         // a2: invokevirtual kotlinx/coroutines/scheduling/CoroutineScheduler.parkedWorkersStackTopUpdate (Lkotlinx/coroutines/scheduling/CoroutineScheduler$Worker;II)V
         // a5: aload 5
         // a7: getfield kotlinx/coroutines/scheduling/CoroutineScheduler.workers Lkotlinx/coroutines/internal/ResizableAtomicArray;
         // aa: iload 1
         // ab: aconst_null
         // ac: invokevirtual kotlinx/coroutines/internal/ResizableAtomicArray.setSynchronized (ILjava/lang/Object;)V
         // af: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // b2: astore 5
         // b4: aload 4
         // b6: monitorexit
         // b7: aload 0
         // b8: getstatic kotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState.TERMINATED Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;
         // bb: putfield kotlinx/coroutines/scheduling/CoroutineScheduler$Worker.state Lkotlinx/coroutines/scheduling/CoroutineScheduler$WorkerState;
         // be: return
         // bf: astore 5
         // c1: aload 4
         // c3: monitorexit
         // c4: aload 5
         // c6: athrow
      }

      public fun findTask(mayHaveLocalTasks: Boolean): Task? {
         return if (this.tryAcquireCpuPermit()) this.findAnyTask(var1) else this.findBlockingTask();
      }

      fun getWorkerCtl(): Int {
         return this.workerCtl;
      }

      public fun isIo(): Boolean {
         val var1: Boolean;
         if (this.state === CoroutineScheduler.WorkerState.BLOCKING) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public fun nextInt(upperBound: Int): Int {
         val var3: Int = this.rngState xor this.rngState shl 13 xor (this.rngState xor this.rngState shl 13) shr 17 xor (
            this.rngState xor this.rngState shl 13 xor (this.rngState xor this.rngState shl 13) shr 17
         ) shl 5;
         this.rngState = this.rngState xor this.rngState shl 13 xor (this.rngState xor this.rngState shl 13) shr 17 xor (
            this.rngState xor this.rngState shl 13 xor (this.rngState xor this.rngState shl 13) shr 17
         ) shl 5;
         return if ((var1 - 1 and var1) == 0) var3 and var1 - 1 else (var3 and Integer.MAX_VALUE) % var1;
      }

      public override fun run() {
         this.runWorker();
      }

      public fun runSingleTask(): Long {
         val var7: CoroutineScheduler.WorkerState = this.state;
         val var1: Boolean;
         if (this.state === CoroutineScheduler.WorkerState.CPU_ACQUIRED) {
            var1 = true;
         } else {
            var1 = false;
         }

         val var6: Task;
         if (var1) {
            var6 = this.findCpuTask();
         } else {
            var6 = this.findBlockingTask();
         }

         if (var6 == null) {
            var var2: Long = this.minDelayUntilStealableTaskNs;
            if (this.minDelayUntilStealableTaskNs == 0L) {
               var2 = -1L;
            }

            return var2;
         } else {
            this.this$0.runSafely(var6);
            if (!var1) {
               val var8: CoroutineScheduler = this.this$0;
               CoroutineScheduler.access$getControlState$FU$p().addAndGet(var8, -2097152L);
            }

            if (DebugKt.getASSERTIONS_ENABLED() && this.state != var7) {
               throw new AssertionError();
            } else {
               return 0L;
            }
         }
      }

      public fun tryReleaseCpu(newState: kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState): Boolean {
         val var2: Boolean;
         if (this.state === CoroutineScheduler.WorkerState.CPU_ACQUIRED) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            val var4: CoroutineScheduler = this.this$0;
            CoroutineScheduler.access$getControlState$FU$p().addAndGet(var4, 4398046511104L);
         }

         if (this.state != var1) {
            this.state = var1;
         }

         return var2;
      }
   }

   public enum class WorkerState {
      BLOCKING,
      CPU_ACQUIRED,
      DORMANT,
      PARKING,
      TERMINATED      @JvmStatic
      private CoroutineScheduler.WorkerState[] $VALUES = $values();
   }
}
