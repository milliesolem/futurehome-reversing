package kotlinx.coroutines.sync

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.jvm.functions.Function1
import kotlin.reflect.KFunction
import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.AtomicLong
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.Waiter
import kotlinx.coroutines.internal.ConcurrentLinkedListKt
import kotlinx.coroutines.internal.Segment
import kotlinx.coroutines.internal.SegmentOrClosed
import kotlinx.coroutines.selects.SelectInstance

internal open class SemaphoreImpl(permits: Int, acquiredPermits: Int) : Semaphore {
   private final val _availablePermits: AtomicInt

   public open val availablePermits: Int
      public open get() {
         return Math.max(_availablePermits$FU.get(this), 0);
      }


   private final val deqIdx: AtomicLong
   private final val enqIdx: AtomicLong
   private final val head: AtomicRef<SemaphoreSegment>
   private final val onCancellationRelease: (Throwable) -> Unit
   private final val permits: Int
   private final val tail: AtomicRef<SemaphoreSegment>

   init {
      this.permits = var1;
      if (var1 > 0) {
         if (var2 >= 0 && var2 <= var1) {
            val var5: SemaphoreSegment = new SemaphoreSegment(0L, null, 2);
            this.head = var5;
            this.tail = var5;
            this._availablePermits = var1 - var2;
            this.onCancellationRelease = (new Function1<java.lang.Throwable, Unit>(this) {
               final SemaphoreImpl this$0;

               {
                  super(1);
                  this.this$0 = var1;
               }

               public final void invoke(java.lang.Throwable var1) {
                  this.this$0.release();
               }
            }) as (java.lang.Throwable?) -> Unit;
         } else {
            val var4: StringBuilder = new StringBuilder("The number of acquired permits should be in 0..");
            var4.append(var1);
            throw new IllegalArgumentException(var4.toString().toString());
         }
      } else {
         val var3: StringBuilder = new StringBuilder("Semaphore should have at least 1 permit, but had ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString().toString());
      }
   }

   private inline fun <W> acquire(waiter: W, suspend: (W) -> Boolean, onAcquired: (W) -> Unit) {
      while (this.decPermits() <= 0) {
         if (var2.invoke(var1) as java.lang.Boolean) {
            return;
         }
      }

      var3.invoke(var1);
   }

   private suspend fun acquireSlowPath() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
      // 04: invokestatic kotlinx/coroutines/CancellableContinuationKt.getOrCreateCancellableContinuation (Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/CancellableContinuationImpl;
      // 07: astore 2
      // 08: aload 0
      // 09: aload 2
      // 0a: checkcast kotlinx/coroutines/Waiter
      // 0d: invokestatic kotlinx/coroutines/sync/SemaphoreImpl.access$addAcquireToQueue (Lkotlinx/coroutines/sync/SemaphoreImpl;Lkotlinx/coroutines/Waiter;)Z
      // 10: ifne 1b
      // 13: aload 0
      // 14: aload 2
      // 15: checkcast kotlinx/coroutines/CancellableContinuation
      // 18: invokevirtual kotlinx/coroutines/sync/SemaphoreImpl.acquire (Lkotlinx/coroutines/CancellableContinuation;)V
      // 1b: aload 2
      // 1c: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
      // 1f: astore 2
      // 20: aload 2
      // 21: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 24: if_acmpne 2b
      // 27: aload 1
      // 28: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
      // 2b: aload 2
      // 2c: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 2f: if_acmpne 34
      // 32: aload 2
      // 33: areturn
      // 34: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 37: areturn
      // 38: astore 1
      // 39: aload 2
      // 3a: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.releaseClaimedReusableContinuation$kotlinx_coroutines_core ()V
      // 3d: aload 1
      // 3e: athrow
   }

   private fun addAcquireToQueue(waiter: Waiter): Boolean {
      val var10: AtomicReferenceFieldUpdater = tail$FU;
      val var8: SemaphoreSegment = tail$FU.get(this) as SemaphoreSegment;
      val var3: Long = enqIdx$FU.getAndIncrement(this);
      val var12: KFunction = <unrepresentable>.INSTANCE;
      val var5: Long = var3 / SemaphoreKt.access$getSEGMENT_SIZE$p();

      val var9: Any;
      label54:
      while (true) {
         var9 = ConcurrentLinkedListKt.findSegmentInternal(var8, var5, var12 as (java.lang.Long?, SemaphoreSegment?) -> SemaphoreSegment);
         if (SegmentOrClosed.isClosed-impl(var9)) {
            break;
         }

         val var7: Segment = SegmentOrClosed.getSegment-impl(var9);

         while (true) {
            val var11: Segment = var10.get(this) as Segment;
            if (var11.id >= var7.id) {
               break label54;
            }

            if (!var7.tryIncPointers$kotlinx_coroutines_core()) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(var10, this, var11, var7)) {
               if (var11.decPointers$kotlinx_coroutines_core()) {
                  var11.remove();
               }
               break label54;
            }

            if (var7.decPointers$kotlinx_coroutines_core()) {
               var7.remove();
            }
         }
      }

      val var13: SemaphoreSegment = SegmentOrClosed.getSegment-impl(var9) as SemaphoreSegment;
      val var2: Int = (int)(var3 % SemaphoreKt.access$getSEGMENT_SIZE$p());
      if (com.google.common.util.concurrent.Striped.SmallLazyStriped..ExternalSyntheticBackportWithForwarding0.m(var13.getAcquirers(), var2, null, var1)) {
         var1.invokeOnCancellation(var13, var2);
         return true;
      } else if (com.google.common.util.concurrent.Striped.SmallLazyStriped..ExternalSyntheticBackportWithForwarding0.m(
         var13.getAcquirers(), var2, SemaphoreKt.access$getPERMIT$p(), SemaphoreKt.access$getTAKEN$p()
      )) {
         if (var1 is CancellableContinuation) {
            (var1 as CancellableContinuation).resume(Unit.INSTANCE, this.onCancellationRelease);
         } else {
            if (var1 !is SelectInstance) {
               val var14: StringBuilder = new StringBuilder("unexpected: ");
               var14.append(var1);
               throw new IllegalStateException(var14.toString().toString());
            }

            (var1 as SelectInstance).selectInRegistrationPhase(Unit.INSTANCE);
         }

         return true;
      } else if (DebugKt.getASSERTIONS_ENABLED() && var13.getAcquirers().get(var2) != SemaphoreKt.access$getBROKEN$p()) {
         throw new AssertionError();
      } else {
         return false;
      }
   }

   private fun coerceAvailablePermitsAtMaximum() {
      val var1: Int;
      val var3: AtomicIntegerFieldUpdater;
      do {
         var3 = _availablePermits$FU;
         var1 = _availablePermits$FU.get(this);
      } while (var1 > this.permits && !var3.compareAndSet(this, var1, this.permits));
   }

   private fun decPermits(): Int {
      val var1: Int;
      do {
         var1 = _availablePermits$FU.getAndDecrement(this);
      } while (var1 > this.permits);

      return var1;
   }

   private fun Any.tryResumeAcquire(): Boolean {
      val var2: Boolean;
      if (var1 is CancellableContinuation) {
         val var3: CancellableContinuation = var1 as CancellableContinuation;
         var1 = (var1 as CancellableContinuation).tryResume(Unit.INSTANCE, null, this.onCancellationRelease);
         if (var1 != null) {
            var3.completeResume(var1);
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         if (var1 !is SelectInstance) {
            val var5: StringBuilder = new StringBuilder("unexpected: ");
            var5.append(var1);
            throw new IllegalStateException(var5.toString().toString());
         }

         var2 = (var1 as SelectInstance).trySelect(this, Unit.INSTANCE);
      }

      return var2;
   }

   private fun tryResumeNextFromQueue(): Boolean {
      val var10: AtomicReferenceFieldUpdater = head$FU;
      val var13: SemaphoreSegment = head$FU.get(this) as SemaphoreSegment;
      val var4: Long = deqIdx$FU.getAndIncrement(this);
      val var6: Long = var4 / SemaphoreKt.access$getSEGMENT_SIZE$p();
      val var12: KFunction = <unrepresentable>.INSTANCE;

      val var14: Any;
      label53:
      while (true) {
         var14 = ConcurrentLinkedListKt.findSegmentInternal(var13, var6, var12 as (java.lang.Long?, SemaphoreSegment?) -> SemaphoreSegment);
         if (SegmentOrClosed.isClosed-impl(var14)) {
            break;
         }

         val var15: Segment = SegmentOrClosed.getSegment-impl(var14);

         while (true) {
            val var11: Segment = var10.get(this) as Segment;
            if (var11.id >= var15.id) {
               break label53;
            }

            if (!var15.tryIncPointers$kotlinx_coroutines_core()) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(var10, this, var11, var15)) {
               if (var11.decPointers$kotlinx_coroutines_core()) {
                  var11.remove();
               }
               break label53;
            }

            if (var15.decPointers$kotlinx_coroutines_core()) {
               var15.remove();
            }
         }
      }

      val var16: SemaphoreSegment = SegmentOrClosed.getSegment-impl(var14) as SemaphoreSegment;
      var16.cleanPrev();
      var var1: Int = 0;
      if (var16.id > var6) {
         return false;
      } else {
         val var2: Int = (int)(var4 % SemaphoreKt.access$getSEGMENT_SIZE$p());
         val var18: Any = var16.getAcquirers().getAndSet(var2, SemaphoreKt.access$getPERMIT$p());
         if (var18 == null) {
            for (int var3 = SemaphoreKt.access$getMAX_SPIN_CYCLES$p(); var1 < var3; var1++) {
               if (var16.getAcquirers().get(var2) === SemaphoreKt.access$getTAKEN$p()) {
                  return true;
               }
            }

            return com.google.common.util.concurrent.Striped.SmallLazyStriped..ExternalSyntheticBackportWithForwarding0.m(
               var16.getAcquirers(), var2, SemaphoreKt.access$getPERMIT$p(), SemaphoreKt.access$getBROKEN$p()
            ) xor true;
         } else {
            return var18 != SemaphoreKt.access$getCANCELLED$p() && this.tryResumeAcquire(var18);
         }
      }
   }

   public override suspend fun acquire() {
      return acquire$suspendImpl(this, var1);
   }

   protected fun acquire(waiter: CancellableContinuation<Unit>) {
      while (true) {
         if (this.decPermits() > 0) {
            var1.resume(Unit.INSTANCE, this.onCancellationRelease);
         } else if (!this.addAcquireToQueue(var1 as Waiter)) {
            continue;
         }

         return;
      }
   }

   protected fun onAcquireRegFunction(select: SelectInstance<*>, ignoredParam: Any?) {
      while (true) {
         if (this.decPermits() > 0) {
            var1.selectInRegistrationPhase(Unit.INSTANCE);
         } else if (!this.addAcquireToQueue(var1 as Waiter)) {
            continue;
         }

         return;
      }
   }

   public override fun release() {
      while (true) {
         val var1: Int = _availablePermits$FU.getAndIncrement(this);
         if (var1 < this.permits) {
            if (var1 >= 0) {
               return;
            }

            if (!this.tryResumeNextFromQueue()) {
               continue;
            }

            return;
         }

         this.coerceAvailablePermitsAtMaximum();
         val var2: StringBuilder = new StringBuilder("The number of released permits cannot be greater than ");
         var2.append(this.permits);
         throw new IllegalStateException(var2.toString().toString());
      }
   }

   public override fun tryAcquire(): Boolean {
      while (true) {
         val var2: AtomicIntegerFieldUpdater = _availablePermits$FU;
         val var1: Int = _availablePermits$FU.get(this);
         if (var1 > this.permits) {
            this.coerceAvailablePermitsAtMaximum();
         } else {
            if (var1 <= 0) {
               return false;
            }

            if (var2.compareAndSet(this, var1, var1 - 1)) {
               return true;
            }
         }
      }
   }
}
