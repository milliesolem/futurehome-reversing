package kotlinx.coroutines

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.internal.LockFreeTaskQueueCore
import kotlinx.coroutines.internal.ThreadSafeHeap
import kotlinx.coroutines.internal.ThreadSafeHeapNode

internal abstract class EventLoopImplBase : EventLoopImplPlatform, Delay {
   private final val _delayed: AtomicRef<kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue?>
   private final val _isCompleted: AtomicBoolean
   private final val _queue: AtomicRef<Any?>

   private final var isCompleted: Boolean
      private final get() {
         val var1: Boolean;
         if (_isCompleted$FU.get(this) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private final set(value) {
         _isCompleted$FU.set(this, var1);
      }


   protected open val isEmpty: Boolean
      protected open get() {
         if (!this.isUnconfinedQueueEmpty()) {
            return false;
         } else {
            var var3: EventLoopImplBase.DelayedTaskQueue = _delayed$FU.get(this) as EventLoopImplBase.DelayedTaskQueue;
            if (var3 != null && !var3.isEmpty()) {
               return false;
            } else {
               var3 = (EventLoopImplBase.DelayedTaskQueue)_queue$FU.get(this);
               if (var3 != null) {
                  if (var3 is LockFreeTaskQueueCore) {
                     return (var3 as LockFreeTaskQueueCore).isEmpty();
                  }

                  if (var3 != EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
                     return false;
                  }
               }

               return true;
            }
         }
      }


   protected open val nextTime: Long
      protected open get() {
         if (super.getNextTime() == 0L) {
            return 0L;
         } else {
            var var5: EventLoopImplBase.DelayedTaskQueue = (EventLoopImplBase.DelayedTaskQueue)_queue$FU.get(this);
            if (var5 != null) {
               if (var5 !is LockFreeTaskQueueCore) {
                  if (var5 === EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
                     return java.lang.Long.MAX_VALUE;
                  }

                  return 0L;
               }

               if (!(var5 as LockFreeTaskQueueCore).isEmpty()) {
                  return 0L;
               }
            }

            var5 = _delayed$FU.get(this) as EventLoopImplBase.DelayedTaskQueue;
            if (var5 != null) {
               val var7: EventLoopImplBase.DelayedTask = var5.peek();
               if (var7 != null) {
                  val var3: Long = var7.nanoTime;
                  val var8: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
                  val var1: Long;
                  if (var8 != null) {
                     var1 = var8.nanoTime();
                  } else {
                     var1 = System.nanoTime();
                  }

                  return RangesKt.coerceAtLeast(var3 - var1, 0L);
               }
            }

            return java.lang.Long.MAX_VALUE;
         }
      }


   private fun closeQueue() {
      if (DebugKt.getASSERTIONS_ENABLED() && !this.isCompleted()) {
         throw new AssertionError();
      } else {
         val var2: AtomicReferenceFieldUpdater = _queue$FU;

         while (true) {
            val var3: Any = var2.get(this);
            if (var3 == null) {
               if (ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, null, EventLoop_commonKt.access$getCLOSED_EMPTY$p())) {
                  return;
               }
            } else {
               if (var3 is LockFreeTaskQueueCore) {
                  (var3 as LockFreeTaskQueueCore).close();
                  return;
               }

               if (var3 === EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
                  return;
               }

               val var1: LockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
               var1.addLast(var3 as Runnable);
               if (ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, var3, var1)) {
                  return;
               }
            }
         }
      }
   }

   private fun dequeue(): Runnable? {
      val var2: AtomicReferenceFieldUpdater = _queue$FU;

      while (true) {
         val var1: Any = var2.get(this);
         if (var1 == null) {
            return null;
         }

         if (var1 is LockFreeTaskQueueCore) {
            val var4: LockFreeTaskQueueCore = var1 as LockFreeTaskQueueCore;
            val var3: Any = (var1 as LockFreeTaskQueueCore).removeFirstOrNull();
            if (var3 != LockFreeTaskQueueCore.REMOVE_FROZEN) {
               return var3 as Runnable;
            }

            ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, var1, var4.next());
         } else {
            if (var1 === EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
               return null;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, var1, null)) {
               return var1 as Runnable;
            }
         }
      }
   }

   private fun enqueueImpl(task: Runnable): Boolean {
      val var3: AtomicReferenceFieldUpdater = _queue$FU;

      while (true) {
         val var4: Any = var3.get(this);
         if (this.isCompleted()) {
            return false;
         }

         if (var4 == null) {
            if (ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, null, var1)) {
               return true;
            }
         } else if (var4 is LockFreeTaskQueueCore) {
            val var5: LockFreeTaskQueueCore = var4 as LockFreeTaskQueueCore;
            val var2: Int = (var4 as LockFreeTaskQueueCore).addLast(var1);
            if (var2 == 0) {
               return true;
            }

            if (var2 != 1) {
               if (var2 == 2) {
                  return false;
               }
            } else {
               ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, var4, var5.next());
            }
         } else {
            if (var4 === EventLoop_commonKt.access$getCLOSED_EMPTY$p()) {
               return false;
            }

            val var6: LockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
            var6.addLast(var4 as Runnable);
            var6.addLast(var1);
            if (ExternalSyntheticBackportWithForwarding0.m(_queue$FU, this, var4, var6)) {
               return true;
            }
         }
      }
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private fun rescheduleAllDelayed() {
      val var3: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
      val var1: Long;
      if (var3 != null) {
         var1 = var3.nanoTime();
      } else {
         var1 = System.nanoTime();
      }

      while (true) {
         val var4: EventLoopImplBase.DelayedTaskQueue = _delayed$FU.get(this) as EventLoopImplBase.DelayedTaskQueue;
         if (var4 == null) {
            break;
         }

         val var5: EventLoopImplBase.DelayedTask = var4.removeFirstOrNull();
         if (var5 == null) {
            break;
         }

         this.reschedule(var1, var5);
      }
   }

   private fun scheduleImpl(now: Long, delayedTask: kotlinx.coroutines.EventLoopImplBase.DelayedTask): Int {
      if (this.isCompleted()) {
         return 1;
      } else {
         val var6: AtomicReferenceFieldUpdater = _delayed$FU;
         val var5: EventLoopImplBase.DelayedTaskQueue = _delayed$FU.get(this) as EventLoopImplBase.DelayedTaskQueue;
         var var4: EventLoopImplBase.DelayedTaskQueue = var5;
         if (var5 == null) {
            val var7: EventLoopImplBase = this;
            ExternalSyntheticBackportWithForwarding0.m(var6, this, null, new EventLoopImplBase.DelayedTaskQueue(var1));
            var4 = (EventLoopImplBase.DelayedTaskQueue)var6.get(this);
            var4 = var4;
         }

         return var3.scheduleTask(var1, var4, this);
      }
   }

   private fun shouldUnpark(task: kotlinx.coroutines.EventLoopImplBase.DelayedTask): Boolean {
      val var3: EventLoopImplBase.DelayedTaskQueue = _delayed$FU.get(this) as EventLoopImplBase.DelayedTaskQueue;
      val var4: EventLoopImplBase.DelayedTask;
      if (var3 != null) {
         var4 = var3.peek();
      } else {
         var4 = null;
      }

      val var2: Boolean;
      if (var4 === var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
   override fun delay(var1: Long, var3: Continuation<? super Unit>): Any {
      return Delay.DefaultImpls.delay(this, var1, var3);
   }

   public override fun dispatch(context: CoroutineContext, block: Runnable) {
      this.enqueue(var2);
   }

   public open fun enqueue(task: Runnable) {
      if (this.enqueueImpl(var1)) {
         this.unpark();
      } else {
         DefaultExecutor.INSTANCE.enqueue(var1);
      }
   }

   override fun invokeOnTimeout(var1: Long, var3: Runnable, var4: CoroutineContext): DisposableHandle {
      return Delay.DefaultImpls.invokeOnTimeout(this, var1, var3, var4);
   }

   public override fun processNextEvent(): Long {
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
      // 01: invokevirtual kotlinx/coroutines/EventLoopImplBase.processUnconfinedEvent ()Z
      // 04: ifeq 09
      // 07: lconst_0
      // 08: lreturn
      // 09: getstatic kotlinx/coroutines/EventLoopImplBase._delayed$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 0c: aload 0
      // 0d: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 10: checkcast kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue
      // 13: astore 6
      // 15: aload 6
      // 17: ifnull a0
      // 1a: aload 6
      // 1c: invokevirtual kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue.isEmpty ()Z
      // 1f: ifne a0
      // 22: invokestatic kotlinx/coroutines/AbstractTimeSourceKt.getTimeSource ()Lkotlinx/coroutines/AbstractTimeSource;
      // 25: astore 4
      // 27: aload 4
      // 29: ifnull 35
      // 2c: aload 4
      // 2e: invokevirtual kotlinx/coroutines/AbstractTimeSource.nanoTime ()J
      // 31: lstore 2
      // 32: goto 39
      // 35: invokestatic java/lang/System.nanoTime ()J
      // 38: lstore 2
      // 39: aload 6
      // 3b: checkcast kotlinx/coroutines/internal/ThreadSafeHeap
      // 3e: astore 7
      // 40: aload 7
      // 42: monitorenter
      // 43: aload 7
      // 45: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.firstImpl ()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 48: astore 8
      // 4a: aconst_null
      // 4b: astore 5
      // 4d: aconst_null
      // 4e: astore 4
      // 50: aload 8
      // 52: ifnonnull 5f
      // 55: aload 7
      // 57: monitorexit
      // 58: aload 5
      // 5a: astore 4
      // 5c: goto 8d
      // 5f: aload 8
      // 61: checkcast kotlinx/coroutines/EventLoopImplBase$DelayedTask
      // 64: astore 5
      // 66: aload 5
      // 68: lload 2
      // 69: invokevirtual kotlinx/coroutines/EventLoopImplBase$DelayedTask.timeToExecute (J)Z
      // 6c: ifeq 7c
      // 6f: aload 0
      // 70: aload 5
      // 72: checkcast java/lang/Runnable
      // 75: invokespecial kotlinx/coroutines/EventLoopImplBase.enqueueImpl (Ljava/lang/Runnable;)Z
      // 78: istore 1
      // 79: goto 7e
      // 7c: bipush 0
      // 7d: istore 1
      // 7e: iload 1
      // 7f: ifeq 8a
      // 82: aload 7
      // 84: bipush 0
      // 85: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.removeAtImpl (I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 88: astore 4
      // 8a: aload 7
      // 8c: monitorexit
      // 8d: aload 4
      // 8f: checkcast kotlinx/coroutines/EventLoopImplBase$DelayedTask
      // 92: ifnonnull 39
      // 95: goto a0
      // 98: astore 4
      // 9a: aload 7
      // 9c: monitorexit
      // 9d: aload 4
      // 9f: athrow
      // a0: aload 0
      // a1: invokespecial kotlinx/coroutines/EventLoopImplBase.dequeue ()Ljava/lang/Runnable;
      // a4: astore 4
      // a6: aload 4
      // a8: ifnull b4
      // ab: aload 4
      // ad: invokeinterface java/lang/Runnable.run ()V 1
      // b2: lconst_0
      // b3: lreturn
      // b4: aload 0
      // b5: invokevirtual kotlinx/coroutines/EventLoopImplBase.getNextTime ()J
      // b8: lreturn
   }

   protected fun resetAll() {
      _queue$FU.set(this, null);
      _delayed$FU.set(this, null);
   }

   public fun schedule(now: Long, delayedTask: kotlinx.coroutines.EventLoopImplBase.DelayedTask) {
      val var4: Int = this.scheduleImpl(var1, var3);
      if (var4 != 0) {
         if (var4 != 1) {
            if (var4 != 2) {
               throw new IllegalStateException("unexpected result".toString());
            }
         } else {
            this.reschedule(var1, var3);
         }
      } else if (this.shouldUnpark(var3)) {
         this.unpark();
      }
   }

   protected fun scheduleInvokeOnTimeout(timeMillis: Long, block: Runnable): DisposableHandle {
      val var4: Long = EventLoop_commonKt.delayToNanos(var1);
      val var9: DisposableHandle;
      if (var4 < 4611686018427387903L) {
         val var6: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
         if (var6 != null) {
            var1 = var6.nanoTime();
         } else {
            var1 = System.nanoTime();
         }

         val var8: EventLoopImplBase.DelayedRunnableTask = new EventLoopImplBase.DelayedRunnableTask(var4 + var1, var3);
         this.schedule(var1, var8);
         var9 = var8;
      } else {
         var9 = NonDisposableHandle.INSTANCE;
      }

      return var9;
   }

   public override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
      val var4: Long = EventLoop_commonKt.delayToNanos(var1);
      if (var4 < 4611686018427387903L) {
         val var6: AbstractTimeSource = AbstractTimeSourceKt.getTimeSource();
         if (var6 != null) {
            var1 = var6.nanoTime();
         } else {
            var1 = System.nanoTime();
         }

         val var8: EventLoopImplBase.DelayedResumeTask = new EventLoopImplBase.DelayedResumeTask((long)this, var4 + var1, var3);
         this.schedule(var1, var8);
         CancellableContinuationKt.disposeOnCancellation(var3, var8);
      }
   }

   public override fun shutdown() {
      ThreadLocalEventLoop.INSTANCE.resetEventLoop$kotlinx_coroutines_core();
      this.setCompleted(true);
      this.closeQueue();

      while (this.processNextEvent() <= 0L) {
      }

      this.rescheduleAllDelayed();
   }

   private inner class DelayedResumeTask(nanoTime: Long, cont: CancellableContinuation<Unit>) : EventLoopImplBase.DelayedTask(var2) {
      private final val cont: CancellableContinuation<Unit>

      init {
         this.this$0 = var1;
         this.cont = var4;
      }

      public override fun run() {
         this.cont.resumeUndispatched(this.this$0, Unit.INSTANCE);
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder();
         var1.append(super.toString());
         var1.append(this.cont);
         return var1.toString();
      }
   }

   private class DelayedRunnableTask(nanoTime: Long, block: Runnable) : EventLoopImplBase.DelayedTask(var1) {
      private final val block: Runnable

      init {
         this.block = var3;
      }

      public override fun run() {
         this.block.run();
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder();
         var1.append(super.toString());
         var1.append(this.block);
         return var1.toString();
      }
   }

   internal abstract class DelayedTask : Runnable, java.lang.Comparable<EventLoopImplBase.DelayedTask>, DisposableHandle, ThreadSafeHeapNode {
      private final var _heap: Any?

      public open var heap: ThreadSafeHeap<*>?
         public open get() {
            val var2: ThreadSafeHeap;
            if (this._heap is ThreadSafeHeap) {
               var2 = this._heap as ThreadSafeHeap;
            } else {
               var2 = null;
            }

            return var2;
         }

         public open set(value) {
            if (this._heap != EventLoop_commonKt.access$getDISPOSED_TASK$p()) {
               this._heap = var1;
            } else {
               throw new IllegalArgumentException("Failed requirement.".toString());
            }
         }


      public open var index: Int
         internal final set

      public final var nanoTime: Long
         private set

      open fun DelayedTask(var1: Long) {
         this.nanoTime = var1;
         this.index = -1;
      }

      public open operator fun compareTo(other: kotlinx.coroutines.EventLoopImplBase.DelayedTask): Int {
         val var4: Long;
         var var2: Byte = (byte)(if ((var4 = this.nanoTime - var1.nanoTime - 0L) == 0L) 0 else (if (var4 < 0L) -1 else 1));
         if (this.nanoTime - var1.nanoTime > 0L) {
            var2 = 1;
         } else if (var2 < 0) {
            var2 = -1;
         } else {
            var2 = 0;
         }

         return var2;
      }

      public override fun dispose() {
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
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield kotlinx/coroutines/EventLoopImplBase$DelayedTask._heap Ljava/lang/Object;
         // 06: astore 1
         // 07: invokestatic kotlinx/coroutines/EventLoop_commonKt.access$getDISPOSED_TASK$p ()Lkotlinx/coroutines/internal/Symbol;
         // 0a: astore 2
         // 0b: aload 1
         // 0c: aload 2
         // 0d: if_acmpne 13
         // 10: aload 0
         // 11: monitorexit
         // 12: return
         // 13: aload 1
         // 14: instanceof kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue
         // 17: ifeq 22
         // 1a: aload 1
         // 1b: checkcast kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue
         // 1e: astore 1
         // 1f: goto 24
         // 22: aconst_null
         // 23: astore 1
         // 24: aload 1
         // 25: ifnull 31
         // 28: aload 1
         // 29: aload 0
         // 2a: checkcast kotlinx/coroutines/internal/ThreadSafeHeapNode
         // 2d: invokevirtual kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue.remove (Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)Z
         // 30: pop
         // 31: aload 0
         // 32: invokestatic kotlinx/coroutines/EventLoop_commonKt.access$getDISPOSED_TASK$p ()Lkotlinx/coroutines/internal/Symbol;
         // 35: putfield kotlinx/coroutines/EventLoopImplBase$DelayedTask._heap Ljava/lang/Object;
         // 38: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 3b: astore 1
         // 3c: aload 0
         // 3d: monitorexit
         // 3e: return
         // 3f: astore 1
         // 40: aload 0
         // 41: monitorexit
         // 42: aload 1
         // 43: athrow
      }

      public fun scheduleTask(now: Long, delayed: kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue, eventLoop: EventLoopImplBase): Int {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.RuntimeException: parsing failure!
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.createStatement(DomHelper.java:27)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:157)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield kotlinx/coroutines/EventLoopImplBase$DelayedTask._heap Ljava/lang/Object;
         // 06: astore 9
         // 08: invokestatic kotlinx/coroutines/EventLoop_commonKt.access$getDISPOSED_TASK$p ()Lkotlinx/coroutines/internal/Symbol;
         // 0b: astore 8
         // 0d: aload 9
         // 0f: aload 8
         // 11: if_acmpne 18
         // 14: aload 0
         // 15: monitorexit
         // 16: bipush 2
         // 17: ireturn
         // 18: aload 3
         // 19: checkcast kotlinx/coroutines/internal/ThreadSafeHeap
         // 1c: astore 8
         // 1e: aload 8
         // 20: monitorenter
         // 21: aload 8
         // 23: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.firstImpl ()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
         // 26: checkcast kotlinx/coroutines/EventLoopImplBase$DelayedTask
         // 29: astore 9
         // 2b: aload 4
         // 2d: invokestatic kotlinx/coroutines/EventLoopImplBase.access$isCompleted (Lkotlinx/coroutines/EventLoopImplBase;)Z
         // 30: istore 7
         // 32: iload 7
         // 34: ifeq 3e
         // 37: aload 8
         // 39: monitorexit
         // 3a: aload 0
         // 3b: monitorexit
         // 3c: bipush 1
         // 3d: ireturn
         // 3e: aload 9
         // 40: ifnonnull 4b
         // 43: aload 3
         // 44: lload 1
         // 45: putfield kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue.timeNow J
         // 48: goto 71
         // 4b: aload 9
         // 4d: getfield kotlinx/coroutines/EventLoopImplBase$DelayedTask.nanoTime J
         // 50: lstore 5
         // 52: lload 5
         // 54: lload 1
         // 55: lsub
         // 56: lconst_0
         // 57: lcmp
         // 58: iflt 5e
         // 5b: goto 61
         // 5e: lload 5
         // 60: lstore 1
         // 61: lload 1
         // 62: aload 3
         // 63: getfield kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue.timeNow J
         // 66: lsub
         // 67: lconst_0
         // 68: lcmp
         // 69: ifle 71
         // 6c: aload 3
         // 6d: lload 1
         // 6e: putfield kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue.timeNow J
         // 71: aload 0
         // 72: getfield kotlinx/coroutines/EventLoopImplBase$DelayedTask.nanoTime J
         // 75: aload 3
         // 76: getfield kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue.timeNow J
         // 79: lsub
         // 7a: lconst_0
         // 7b: lcmp
         // 7c: ifge 87
         // 7f: aload 0
         // 80: aload 3
         // 81: getfield kotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue.timeNow J
         // 84: putfield kotlinx/coroutines/EventLoopImplBase$DelayedTask.nanoTime J
         // 87: aload 8
         // 89: aload 0
         // 8a: checkcast kotlinx/coroutines/internal/ThreadSafeHeapNode
         // 8d: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.addImpl (Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V
         // 90: aload 8
         // 92: monitorexit
         // 93: aload 0
         // 94: monitorexit
         // 95: bipush 0
         // 96: ireturn
         // 97: astore 3
         // 98: aload 8
         // 9a: monitorexit
         // 9b: aload 3
         // 9c: athrow
         // 9d: astore 3
         // 9e: aload 0
         // 9f: monitorexit
         // a0: aload 3
         // a1: athrow
      }

      public fun timeToExecute(now: Long): Boolean {
         val var3: Boolean;
         if (var1 - this.nanoTime >= 0L) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("Delayed[nanos=");
         var1.append(this.nanoTime);
         var1.append(']');
         return var1.toString();
      }
   }

   internal class DelayedTaskQueue(timeNow: Long) : ThreadSafeHeap<EventLoopImplBase.DelayedTask> {
      public final var timeNow: Long
         private set

      init {
         this.timeNow = var1;
      }
   }
}
