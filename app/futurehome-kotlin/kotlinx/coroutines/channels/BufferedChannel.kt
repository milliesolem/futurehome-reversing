package kotlinx.coroutines.channels

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.ArrayList
import java.util.NoSuchElementException
import java.util.concurrent.CancellationException
import java.util.concurrent.atomic.AtomicLongFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlin.jvm.functions.Function4
import kotlin.jvm.internal.TypeIntrinsics
import kotlinx.atomicfu.AtomicLong
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DebugStringsKt
import kotlinx.coroutines.Waiter
import kotlinx.coroutines.internal.ConcurrentLinkedListKt
import kotlinx.coroutines.internal.ConcurrentLinkedListNode
import kotlinx.coroutines.internal.InlineList
import kotlinx.coroutines.internal.OnUndeliveredElementKt
import kotlinx.coroutines.internal.Segment
import kotlinx.coroutines.internal.SegmentOrClosed
import kotlinx.coroutines.internal.StackTraceRecoveryKt
import kotlinx.coroutines.internal.Symbol
import kotlinx.coroutines.internal.UndeliveredElementException
import kotlinx.coroutines.selects.SelectClause1
import kotlinx.coroutines.selects.SelectClause1Impl
import kotlinx.coroutines.selects.SelectClause2
import kotlinx.coroutines.selects.SelectClause2Impl
import kotlinx.coroutines.selects.SelectImplementation
import kotlinx.coroutines.selects.SelectInstance
import kotlinx.coroutines.selects.TrySelectDetailedResult

internal open class BufferedChannel<E>(capacity: Int, onUndeliveredElement: ((Any) -> Unit)? = null) : Channel<E> {
   private final val _closeCause: AtomicRef<Any?>
   private final val bufferEnd: AtomicLong

   private final val bufferEndCounter: Long
      private final get() {
         return bufferEnd$FU.get(this);
      }


   private final val bufferEndSegment: AtomicRef<ChannelSegment<Any>>
   private final val capacity: Int

   protected final val closeCause: Throwable?
      protected final get() {
         return _closeCause$FU.get(this) as java.lang.Throwable;
      }


   private final val closeHandler: AtomicRef<Any?>
   private final val completedExpandBuffersAndPauseFlag: AtomicLong

   public open val isClosedForReceive: Boolean
      public open get() {
         return this.isClosedForReceive0(sendersAndCloseStatus$FU.get(this));
      }


   public open val isClosedForSend: Boolean
      public open get() {
         return this.isClosedForSend0(sendersAndCloseStatus$FU.get(this));
      }


   protected open val isConflatedDropOldest: Boolean
      protected open get() {
         return false;
      }


   public open val isEmpty: Boolean
      public open get() {
         if (this.isClosedForReceive()) {
            return false;
         } else {
            return !this.hasElements$kotlinx_coroutines_core() && this.isClosedForReceive() xor true;
         }
      }


   private final val isRendezvousOrUnlimited: Boolean
      private final get() {
         val var1: Long = this.getBufferEndCounter();
         val var3: Boolean;
         if (var1 != 0L && var1 != java.lang.Long.MAX_VALUE) {
            var3 = false;
         } else {
            var3 = true;
         }

         return var3;
      }


   public open val onReceive: SelectClause1<Any>
      public open get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         val var2: Function3 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3;
         val var3: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause1Impl<>(
            this,
            var2,
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(var3, 3) as (Any?, Any?, Any?) -> Any,
            this.onUndeliveredElementReceiveCancellationConstructor
         );
      }


   public open val onReceiveCatching: SelectClause1<ChannelResult<Any>>
      public open get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         val var3: Function3 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3;
         val var2: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause1Impl<>(
            this,
            var3,
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 3) as (Any?, Any?, Any?) -> Any,
            this.onUndeliveredElementReceiveCancellationConstructor
         );
      }


   public open val onReceiveOrNull: SelectClause1<Any?>
      public open get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         val var3: Function3 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3;
         val var2: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause1Impl<>(
            this,
            var3,
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 3) as (Any?, Any?, Any?) -> Any,
            this.onUndeliveredElementReceiveCancellationConstructor
         );
      }


   public open val onSend: SelectClause2<Any, BufferedChannel<Any>>
      public open get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         val var3: Function3 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3;
         val var2: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause2Impl<>(this, var3, TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 3) as Function3, null, 8, null);
      }


   internal final val onUndeliveredElement: ((Any) -> Unit)?
   private final val onUndeliveredElementReceiveCancellationConstructor: ((SelectInstance<*>, Any?, Any?) -> (Throwable) -> Unit)?

   private final val receiveException: Throwable
      private final get() {
         val var2: java.lang.Throwable = this.getCloseCause();
         var var1: java.lang.Throwable = var2;
         if (var2 == null) {
            var1 = new ClosedReceiveChannelException("Channel was closed");
         }

         return var1;
      }


   private final val receiveSegment: AtomicRef<ChannelSegment<Any>>
   private final val receivers: AtomicLong

   internal final val receiversCounter: Long
      internal final get() {
         return receivers$FU.get(this);
      }


   protected final val sendException: Throwable
      protected final get() {
         val var2: java.lang.Throwable = this.getCloseCause();
         var var1: java.lang.Throwable = var2;
         if (var2 == null) {
            var1 = new ClosedSendChannelException("Channel was closed");
         }

         return var1;
      }


   private final val sendSegment: AtomicRef<ChannelSegment<Any>>
   private final val sendersAndCloseStatus: AtomicLong

   internal final val sendersCounter: Long
      internal final get() {
         return sendersAndCloseStatus$FU.get(this) and 1152921504606846975L;
      }


   private final val isClosedForReceive0: Boolean
      private final get() {
         return this.isClosed(var1, true);
      }


   private final val isClosedForSend0: Boolean
      private final get() {
         return this.isClosed(var1, false);
      }


   init {
      this.capacity = var1;
      this.onUndeliveredElement = var2;
      if (var1 >= 0) {
         this.bufferEnd = BufferedChannelKt.access$initialBufferEnd(var1);
         this.completedExpandBuffersAndPauseFlag = this.getBufferEndCounter();
         var var3: ChannelSegment = new ChannelSegment<>(0L, null, this, 3);
         this.sendSegment = var3;
         this.receiveSegment = var3;
         if (this.isRendezvousOrUnlimited()) {
            var3 = BufferedChannelKt.access$getNULL_SEGMENT$p();
         }

         this.bufferEndSegment = var3;
         val var5: Function3;
         if (var2 != null) {
            var5 = (new Function3<SelectInstance<?>, Object, Object, Function1<? super java.lang.Throwable, ? extends Unit>>(this) {
               final BufferedChannel<E> this$0;

               {
                  super(3);
                  this.this$0 = var1;
               }

               public final Function1<java.lang.Throwable, Unit> invoke(SelectInstance<?> var1, Object var2, Object var3) {
                  return (new Function1<java.lang.Throwable, Unit>(var3, this.this$0, var1) {
                     final Object $element;
                     final SelectInstance<?> $select;
                     final BufferedChannel<E> this$0;

                     {
                        super(1);
                        this.$element = var1;
                        this.this$0 = var2x;
                        this.$select = var3x;
                     }

                     public final void invoke(java.lang.Throwable var1) {
                        if (this.$element != BufferedChannelKt.getCHANNEL_CLOSED()) {
                           OnUndeliveredElementKt.callUndeliveredElement(this.this$0.onUndeliveredElement, (E)this.$element, this.$select.getContext());
                        }
                     }
                  }) as (java.lang.Throwable?) -> Unit;
               }
            }) as Function3;
         } else {
            var5 = null;
         }

         this.onUndeliveredElementReceiveCancellationConstructor = var5;
         this._closeCause = BufferedChannelKt.access$getNO_CLOSE_CAUSE$p();
      } else {
         val var4: StringBuilder = new StringBuilder("Invalid channel capacity: ");
         var4.append(var1);
         var4.append(", should be >=0");
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   private fun bufferOrRendezvousSend(curSenders: Long): Boolean {
      val var3: Boolean;
      if (var1 >= this.getBufferEndCounter() && var1 >= this.getReceiversCounter$kotlinx_coroutines_core() + this.capacity) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   private fun cancelSuspendedReceiveRequests(lastSegment: ChannelSegment<Any>, sendersCounter: Long) {
      var var5: Any = InlineList.constructor-impl$default(null, 1, null);
      var var6: ChannelSegment = var1;
      var var8: Any = var5;

      label61:
      while (true) {
         var5 = var8;
         if (var6 == null) {
            break;
         }

         var var4: Int = BufferedChannelKt.SEGMENT_SIZE - 1;

         while (-1 < var4) {
            var5 = var8;
            if (var6.id * BufferedChannelKt.SEGMENT_SIZE + var4 < var2) {
               break label61;
            }

            while (true) {
               val var7: Any = var6.getState$kotlinx_coroutines_core(var4);
               if (var7 != null && var7 != BufferedChannelKt.access$getIN_BUFFER$p()) {
                  if (var7 is WaiterEB) {
                     if (var6.casState$kotlinx_coroutines_core(var4, var7, BufferedChannelKt.getCHANNEL_CLOSED())) {
                        var5 = InlineList.plus-FjFbRPM(var8, (var7 as WaiterEB).waiter);
                        var6.onCancelledRequest(var4, true);
                        break;
                     }
                  } else {
                     var5 = var8;
                     if (var7 !is Waiter) {
                        break;
                     }

                     if (var6.casState$kotlinx_coroutines_core(var4, var7, BufferedChannelKt.getCHANNEL_CLOSED())) {
                        var5 = InlineList.plus-FjFbRPM(var8, var7);
                        var6.onCancelledRequest(var4, true);
                        break;
                     }
                  }
               } else if (var6.casState$kotlinx_coroutines_core(var4, var7, BufferedChannelKt.getCHANNEL_CLOSED())) {
                  var6.onSlotCleaned();
                  var5 = var8;
                  break;
               }
            }

            var4--;
            var8 = var5;
         }

         var6 = var6.getPrev();
      }

      if (var5 != null) {
         if (var5 !is ArrayList) {
            this.resumeReceiverOnClosedChannel(var5 as Waiter);
         } else {
            var8 = var5 as ArrayList;

            for (int var10 = ((ArrayList)var5).size() - 1; -1 < var10; var10--) {
               this.resumeReceiverOnClosedChannel(var8.get(var10) as Waiter);
            }
         }
      }
   }

   private fun closeLinkedList(): ChannelSegment<Any> {
      var var2: Any = bufferEndSegment$FU.get(this);
      var var3: ChannelSegment = sendSegment$FU.get(this) as ChannelSegment;
      var var1: Any = var2;
      if (var3.id > (var2 as ChannelSegment).id) {
         var1 = var3;
      }

      var3 = receiveSegment$FU.get(this) as ChannelSegment;
      var2 = var1;
      if (var3.id > (var1 as ChannelSegment).id) {
         var2 = var3;
      }

      return ConcurrentLinkedListKt.close(var2 as ConcurrentLinkedListNode);
   }

   private fun completeCancel(sendersCur: Long) {
      this.removeUnprocessedElements(this.completeClose(var1));
   }

   private fun completeClose(sendersCur: Long): ChannelSegment<Any> {
      val var5: ChannelSegment = this.closeLinkedList();
      if (this.isConflatedDropOldest()) {
         val var3: Long = this.markAllEmptyCellsAsClosed(var5);
         if (var3 != -1L) {
            this.dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(var3);
         }
      }

      this.cancelSuspendedReceiveRequests(var5, var1);
      return var5;
   }

   private fun completeCloseOrCancel() {
      this.isClosedForSend();
   }

   private fun expandBuffer() {
      if (!this.isRendezvousOrUnlimited()) {
         var var5: ChannelSegment = bufferEndSegment$FU.get(this) as ChannelSegment;

         while (true) {
            val var1: Long = bufferEnd$FU.getAndIncrement(this);
            val var3: Long = var1 / BufferedChannelKt.SEGMENT_SIZE;
            if (this.getSendersCounter$kotlinx_coroutines_core() <= var1) {
               if (var5.id < var3 && var5.getNext() != null) {
                  this.moveSegmentBufferEndToSpecifiedOrLast(var3, var5);
               }

               incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
               return;
            }

            var var6: ChannelSegment = var5;
            if (var5.id != var3) {
               var6 = this.findSegmentBufferEnd(var3, var5, var1);
               if (var6 == null) {
                  continue;
               }
            }

            if (this.updateCellExpandBuffer(var6, (int)(var1 % (long)BufferedChannelKt.SEGMENT_SIZE), var1)) {
               incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
               return;
            }

            incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
            var5 = var6;
         }
      }
   }

   private fun findSegmentBufferEnd(id: Long, startFrom: ChannelSegment<Any>, currentBufferEndCounter: Long): ChannelSegment<Any>? {
      val var7: AtomicReferenceFieldUpdater = bufferEndSegment$FU;
      val var9: Function2 = BufferedChannelKt.createSegmentFunction() as Function2;

      val var8: Any;
      label46:
      while (true) {
         var8 = ConcurrentLinkedListKt.findSegmentInternal(var3, var1, var9);
         if (SegmentOrClosed.isClosed-impl(var8)) {
            break;
         }

         val var10: Segment = SegmentOrClosed.getSegment-impl(var8);

         while (true) {
            val var11: Segment = var7.get(this) as Segment;
            if (var11.id >= var10.id) {
               break label46;
            }

            if (!var10.tryIncPointers$kotlinx_coroutines_core()) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(var7, this, var11, var10)) {
               if (var11.decPointers$kotlinx_coroutines_core()) {
                  var11.remove();
               }
               break label46;
            }

            if (var10.decPointers$kotlinx_coroutines_core()) {
               var10.remove();
            }
         }
      }

      if (SegmentOrClosed.isClosed-impl(var8)) {
         this.completeCloseOrCancel();
         this.moveSegmentBufferEndToSpecifiedOrLast(var1, var3);
         incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
         var3 = null;
      } else {
         var3 = SegmentOrClosed.getSegment-impl(var8) as ChannelSegment;
         if (var3.id > var1) {
            if (bufferEnd$FU.compareAndSet(this, var4 + 1L, var3.id * (long)BufferedChannelKt.SEGMENT_SIZE)) {
               this.incCompletedExpandBufferAttempts(var3.id * (long)BufferedChannelKt.SEGMENT_SIZE - var4);
               var3 = null;
            } else {
               incCompletedExpandBufferAttempts$default(this, 0L, 1, null);
               var3 = null;
            }
         } else if (DebugKt.getASSERTIONS_ENABLED() && var3.id != var1) {
            throw new AssertionError();
         }
      }

      return var3;
   }

   private fun findSegmentReceive(id: Long, startFrom: ChannelSegment<Any>): ChannelSegment<Any>? {
      val var11: AtomicReferenceFieldUpdater = receiveSegment$FU;
      val var10: Function2 = BufferedChannelKt.createSegmentFunction() as Function2;

      var var7: Any;
      label75:
      while (true) {
         var7 = (ChannelSegment)ConcurrentLinkedListKt.findSegmentInternal(var3, var1, var10);
         if (SegmentOrClosed.isClosed-impl(var7)) {
            break;
         }

         val var8: Segment = SegmentOrClosed.getSegment-impl(var7);

         while (true) {
            val var9: Segment = var11.get(this) as Segment;
            if (var9.id >= var8.id) {
               break label75;
            }

            if (!var8.tryIncPointers$kotlinx_coroutines_core()) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(var11, this, var9, var8)) {
               if (var9.decPointers$kotlinx_coroutines_core()) {
                  var9.remove();
               }
               break label75;
            }

            if (var8.decPointers$kotlinx_coroutines_core()) {
               var8.remove();
            }
         }
      }

      if (SegmentOrClosed.isClosed-impl(var7)) {
         this.completeCloseOrCancel();
         var7 = null;
         if (var3.id * BufferedChannelKt.SEGMENT_SIZE < this.getSendersCounter$kotlinx_coroutines_core()) {
            var3.cleanPrev();
            var7 = null;
         }
      } else {
         var3 = SegmentOrClosed.getSegment-impl(var7) as ChannelSegment;
         if (!this.isRendezvousOrUnlimited() && var1 <= this.getBufferEndCounter() / BufferedChannelKt.SEGMENT_SIZE) {
            val var16: AtomicReferenceFieldUpdater = bufferEndSegment$FU;

            while (true) {
               val var17: Segment = var16.get(this) as Segment;
               val var14: Segment = var3;
               if (var17.id >= var3.id || !var3.tryIncPointers$kotlinx_coroutines_core()) {
                  break;
               }

               if (ExternalSyntheticBackportWithForwarding0.m(var16, this, var17, var14)) {
                  if (var17.decPointers$kotlinx_coroutines_core()) {
                     var17.remove();
                  }
                  break;
               }

               if (var14.decPointers$kotlinx_coroutines_core()) {
                  var14.remove();
               }
            }
         }

         if (var3.id > var1) {
            this.updateReceiversCounterIfLower(var3.id * (long)BufferedChannelKt.SEGMENT_SIZE);
            var7 = null;
            if (var3.id * BufferedChannelKt.SEGMENT_SIZE < this.getSendersCounter$kotlinx_coroutines_core()) {
               var3.cleanPrev();
               var7 = null;
            }
         } else {
            if (DebugKt.getASSERTIONS_ENABLED() && var3.id != var1) {
               throw new AssertionError();
            }

            var7 = var3;
         }
      }

      return var7;
   }

   private fun findSegmentSend(id: Long, startFrom: ChannelSegment<Any>): ChannelSegment<Any>? {
      val var6: AtomicReferenceFieldUpdater = sendSegment$FU;
      val var9: Function2 = BufferedChannelKt.createSegmentFunction() as Function2;

      var var5: Any;
      label49:
      while (true) {
         var5 = (ChannelSegment)ConcurrentLinkedListKt.findSegmentInternal(var3, var1, var9);
         if (SegmentOrClosed.isClosed-impl(var5)) {
            break;
         }

         val var8: Segment = SegmentOrClosed.getSegment-impl(var5);

         while (true) {
            val var7: Segment = var6.get(this) as Segment;
            if (var7.id >= var8.id) {
               break label49;
            }

            if (!var8.tryIncPointers$kotlinx_coroutines_core()) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(var6, this, var7, var8)) {
               if (var7.decPointers$kotlinx_coroutines_core()) {
                  var7.remove();
               }
               break label49;
            }

            if (var8.decPointers$kotlinx_coroutines_core()) {
               var8.remove();
            }
         }
      }

      if (SegmentOrClosed.isClosed-impl(var5)) {
         this.completeCloseOrCancel();
         var5 = null;
         if (var3.id * BufferedChannelKt.SEGMENT_SIZE < this.getReceiversCounter$kotlinx_coroutines_core()) {
            var3.cleanPrev();
            var5 = null;
         }
      } else {
         var3 = SegmentOrClosed.getSegment-impl(var5) as ChannelSegment;
         if (var3.id > var1) {
            this.updateSendersCounterIfLower(var3.id * (long)BufferedChannelKt.SEGMENT_SIZE);
            var5 = null;
            if (var3.id * BufferedChannelKt.SEGMENT_SIZE < this.getReceiversCounter$kotlinx_coroutines_core()) {
               var3.cleanPrev();
               var5 = null;
            }
         } else {
            if (DebugKt.getASSERTIONS_ENABLED() && var3.id != var1) {
               throw new AssertionError();
            }

            var5 = var3;
         }
      }

      return var5;
   }

   fun `getAndUpdate$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Any, var3: Any): Any {
      val var4: Any;
      do {
         var4 = var1.get(var3);
      } while (!ExternalSyntheticBackportWithForwarding0.m(var1, var3, var4, var2.invoke(var4)));

      return var4;
   }

   private fun incCompletedExpandBufferAttempts(nAttempts: Long = 1L) {
      if ((completedExpandBuffersAndPauseFlag$FU.addAndGet(this, var1) and 4611686018427387904L) != 0L) {
         while ((completedExpandBuffersAndPauseFlag$FU.get(this) & 4611686018427387904L) != 0L) {
         }
      }
   }

   private fun invokeCloseHandler() {
      val var3: AtomicReferenceFieldUpdater = closeHandler$FU;

      val var1: Symbol;
      val var2: Any;
      do {
         var2 = var3.get(this);
         if (var2 == null) {
            var1 = BufferedChannelKt.access$getCLOSE_HANDLER_CLOSED$p();
         } else {
            var1 = BufferedChannelKt.access$getCLOSE_HANDLER_INVOKED$p();
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(var3, this, var2, var1));

      if (var2 != null) {
         val var4: Function1 = TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 1) as Function1;
         (var2 as Function1).invoke(this.getCloseCause());
      }
   }

   private fun isCellNonEmpty(segment: ChannelSegment<Any>, index: Int, globalIndex: Long): Boolean {
      val var6: Any;
      do {
         var6 = var1.getState$kotlinx_coroutines_core(var2);
         var var5: Boolean = false;
         if (var6 != null && var6 != BufferedChannelKt.access$getIN_BUFFER$p()) {
            if (var6 === BufferedChannelKt.BUFFERED) {
               return true;
            }

            if (var6 === BufferedChannelKt.access$getINTERRUPTED_SEND$p()) {
               return false;
            }

            if (var6 === BufferedChannelKt.getCHANNEL_CLOSED()) {
               return false;
            }

            if (var6 === BufferedChannelKt.access$getDONE_RCV$p()) {
               return false;
            }

            if (var6 === BufferedChannelKt.access$getPOISONED$p()) {
               return false;
            }

            if (var6 === BufferedChannelKt.access$getRESUMING_BY_EB$p()) {
               return true;
            }

            if (var6 === BufferedChannelKt.access$getRESUMING_BY_RCV$p()) {
               return false;
            }

            if (var3 == this.getReceiversCounter$kotlinx_coroutines_core()) {
               var5 = true;
            }

            return var5;
         }
      } while (!var1.casState$kotlinx_coroutines_core(var2, var6, BufferedChannelKt.access$getPOISONED$p()));

      this.expandBuffer();
      return false;
   }

   private fun isClosed(sendersAndCloseStatusCur: Long, isClosedForReceive: Boolean): Boolean {
      val var4: Int = (int)(var1 shr 60);
      var var5: Boolean = false;
      if (var4 != 0) {
         var5 = false;
         if (var4 != 1) {
            if (var4 != 2) {
               if (var4 != 3) {
                  val var7: StringBuilder = new StringBuilder("unexpected close status: ");
                  var7.append(var4);
                  throw new IllegalStateException(var7.toString().toString());
               }

               this.completeCancel(var1 and 1152921504606846975L);
            } else {
               this.completeClose(var1 and 1152921504606846975L);
               if (var3 && this.hasElements$kotlinx_coroutines_core()) {
                  return false;
               }
            }

            var5 = true;
         }
      }

      return var5;
   }

   fun `loop$atomicfu`(var1: AtomicLongFieldUpdater, var2: (java.lang.Long?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private fun markAllEmptyCellsAsClosed(lastSegment: ChannelSegment<Any>): Long {
      var var6: ChannelSegment;
      do {
         label34:
         for (int var2 = BufferedChannelKt.SEGMENT_SIZE - 1; -1 < var2; var2--) {
            val var3: Long = var1.id * BufferedChannelKt.SEGMENT_SIZE + var2;
            if (var1.id * BufferedChannelKt.SEGMENT_SIZE + var2 < this.getReceiversCounter$kotlinx_coroutines_core()) {
               return -1L;
            }

            do {
               var6 = (ChannelSegment)var1.getState$kotlinx_coroutines_core(var2);
               if (var6 != null && var6 != BufferedChannelKt.access$getIN_BUFFER$p()) {
                  if (var6 === BufferedChannelKt.BUFFERED) {
                     return var3;
                  }
                  continue label34;
               }
            } while (!var1.casState$kotlinx_coroutines_core(var2, var6, BufferedChannelKt.getCHANNEL_CLOSED()));

            var1.onSlotCleaned();
         }

         var6 = var1.getPrev();
         var1 = var6;
      } while (var6 != null);

      return -1L;
   }

   private fun markCancellationStarted() {
      val var3: AtomicLongFieldUpdater = sendersAndCloseStatus$FU;

      val var1: Long;
      do {
         var1 = var3.get(this);
      } while (
         (int)(var1 >> 60) == 0 && !var3.compareAndSet(this, var1, BufferedChannelKt.access$constructSendersAndCloseStatus(1152921504606846975L & var1, 1))
      );
   }

   private fun markCancelled() {
      val var3: AtomicLongFieldUpdater = sendersAndCloseStatus$FU;

      val var1: Long;
      do {
         var1 = var3.get(this);
      } while (!var3.compareAndSet(this, var1, BufferedChannelKt.access$constructSendersAndCloseStatus(1152921504606846975L & var1, 3)));
   }

   private fun markClosed() {
      val var6: AtomicLongFieldUpdater = sendersAndCloseStatus$FU;

      val var2: Long;
      val var4: Long;
      do {
         var4 = var6.get(this);
         val var1: Int = (int)(var4 shr 60);
         if ((int)(var4 shr 60) != 0) {
            if (var1 != 1) {
               return;
            }

            var2 = BufferedChannelKt.access$constructSendersAndCloseStatus(var4 and 1152921504606846975L, 3);
         } else {
            var2 = BufferedChannelKt.access$constructSendersAndCloseStatus(var4 and 1152921504606846975L, 2);
         }
      } while (!var6.compareAndSet(this, var4, var2));
   }

   private fun moveSegmentBufferEndToSpecifiedOrLast(id: Long, startFrom: ChannelSegment<Any>) {
      while (true) {
         var var4: ChannelSegment = var3;
         if (var3.id < var1) {
            var4 = var3.getNext();
            if (var4 != null) {
               var3 = var4;
               continue;
            }

            var4 = var3;
         }

         while (true) {
            while (var4.isRemoved()) {
               var3 = var4.getNext();
               if (var3 == null) {
                  break;
               }

               var4 = var3;
            }

            val var5: AtomicReferenceFieldUpdater = bufferEndSegment$FU;

            while (true) {
               val var9: Segment = var5.get(this) as Segment;
               val var6: Segment = var4;
               if (var9.id >= var4.id) {
                  return;
               }

               if (!var6.tryIncPointers$kotlinx_coroutines_core()) {
                  break;
               }

               if (ExternalSyntheticBackportWithForwarding0.m(var5, this, var9, var6)) {
                  if (var9.decPointers$kotlinx_coroutines_core()) {
                     var9.remove();
                  }

                  return;
               }

               if (var6.decPointers$kotlinx_coroutines_core()) {
                  var6.remove();
               }
            }
         }
      }
   }

   private fun onClosedReceiveCatchingOnNoWaiterSuspend(cont: CancellableContinuation<ChannelResult<Any>>) {
      val var2: Continuation = var1;
      val var3: Result.Companion = Result.Companion;
      var2.resumeWith(Result.constructor-impl(ChannelResult.box-impl(ChannelResult.Companion.closed-JP2dKIU(this.getCloseCause()))));
   }

   private fun onClosedReceiveOnNoWaiterSuspend(cont: CancellableContinuation<Any>) {
      val var2: Continuation = var1;
      val var3: Result.Companion = Result.Companion;
      var2.resumeWith(Result.constructor-impl(ResultKt.createFailure(this.getReceiveException())));
   }

   private fun onClosedSelectOnReceive(select: SelectInstance<*>) {
      var1.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
   }

   private fun onClosedSelectOnSend(element: Any, select: SelectInstance<*>) {
      if (this.onUndeliveredElement != null) {
         OnUndeliveredElementKt.callUndeliveredElement(this.onUndeliveredElement, (E)var1, var2.getContext());
      }

      var2.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
   }

   private suspend fun onClosedSend(element: Any) {
      var var4: CancellableContinuationImpl;
      label37: {
         var4 = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var2), 1);
         var4.initCancellability();
         val var5: CancellableContinuation = var4;
         if (this.onUndeliveredElement != null) {
            var1 = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(this.onUndeliveredElement, var1, null, 2, null);
            if (var1 != null) {
               val var13: java.lang.Throwable = var1;
               ExceptionsKt.addSuppressed(var1, this.getSendException());
               val var15: Continuation = var5;
               var1 = Result.Companion;
               var var10: java.lang.Throwable = var13;
               if (DebugKt.getRECOVER_STACK_TRACES()) {
                  if (var15 !is CoroutineStackFrame) {
                     var10 = var13;
                  } else {
                     var10 = StackTraceRecoveryKt.access$recoverFromStackFrame(var13, var15 as CoroutineStackFrame);
                  }
               }

               var15.resumeWith(Result.constructor-impl(ResultKt.createFailure(var10)));
               break label37;
            }
         }

         val var14: Continuation = var5;
         val var12: java.lang.Throwable = this.getSendException();
         var1 = Result.Companion;
         var var8: java.lang.Throwable = var12;
         if (DebugKt.getRECOVER_STACK_TRACES()) {
            if (var14 !is CoroutineStackFrame) {
               var8 = var12;
            } else {
               var8 = StackTraceRecoveryKt.access$recoverFromStackFrame(var12, var14 as CoroutineStackFrame);
            }
         }

         var14.resumeWith(Result.constructor-impl(ResultKt.createFailure(var8)));
      }

      var1 = var4.getResult();
      if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var2);
      }

      return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
   }

   private fun onClosedSendOnNoWaiterSuspend(element: Any, cont: CancellableContinuation<Unit>) {
      if (this.onUndeliveredElement != null) {
         OnUndeliveredElementKt.callUndeliveredElement(this.onUndeliveredElement, (E)var1, var2.getContext());
      }

      val var7: Continuation = var2;
      val var5: java.lang.Throwable = this.getSendException();
      var1 = var5;
      if (DebugKt.getRECOVER_STACK_TRACES()) {
         if (var7 !is CoroutineStackFrame) {
            var1 = var5;
         } else {
            var1 = StackTraceRecoveryKt.access$recoverFromStackFrame(var5, var7 as CoroutineStackFrame);
         }
      }

      val var6: Result.Companion = Result.Companion;
      var7.resumeWith(Result.constructor-impl(ResultKt.createFailure(var1)));
   }

   private fun Waiter.prepareReceiverForSuspension(segment: ChannelSegment<Any>, index: Int) {
      this.onReceiveEnqueued();
      var1.invokeOnCancellation(var2, var3);
   }

   private fun Waiter.prepareSenderForSuspension(segment: ChannelSegment<Any>, index: Int) {
      var1.invokeOnCancellation(var2, var3 + BufferedChannelKt.SEGMENT_SIZE);
   }

   private fun processResultSelectReceive(ignoredParam: Any?, selectResult: Any?): Any? {
      if (var2 != BufferedChannelKt.getCHANNEL_CLOSED()) {
         return var2;
      } else {
         throw this.getReceiveException();
      }
   }

   private fun processResultSelectReceiveCatching(ignoredParam: Any?, selectResult: Any?): Any? {
      if (var2 === BufferedChannelKt.getCHANNEL_CLOSED()) {
         var1 = ChannelResult.Companion.closed-JP2dKIU(this.getCloseCause());
      } else {
         var1 = ChannelResult.Companion.success-JP2dKIU(var2);
      }

      return ChannelResult.box-impl(var1);
   }

   private fun processResultSelectReceiveOrNull(ignoredParam: Any?, selectResult: Any?): Any? {
      var1 = var2;
      if (var2 === BufferedChannelKt.getCHANNEL_CLOSED()) {
         if (this.getCloseCause() != null) {
            throw this.getReceiveException();
         }

         var1 = null;
      }

      return var1;
   }

   private fun processResultSelectSend(ignoredParam: Any?, selectResult: Any?): Any? {
      if (var2 != BufferedChannelKt.getCHANNEL_CLOSED()) {
         return this;
      } else {
         throw this.getSendException();
      }
   }

   private suspend fun receiveCatchingOnNoWaiterSuspend(segment: ChannelSegment<Any>, index: Int, r: Long): ChannelResult<Any> {
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
      // 000: aload 5
      // 002: instanceof kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1
      // 005: ifeq 030
      // 008: aload 5
      // 00a: checkcast kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1
      // 00d: astore 9
      // 00f: aload 9
      // 011: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label I
      // 014: ldc_w -2147483648
      // 017: iand
      // 018: ifeq 030
      // 01b: aload 9
      // 01d: aload 9
      // 01f: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label I
      // 022: ldc_w -2147483648
      // 025: iadd
      // 026: putfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label I
      // 029: aload 9
      // 02b: astore 5
      // 02d: goto 03c
      // 030: new kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1
      // 033: dup
      // 034: aload 0
      // 035: aload 5
      // 037: invokespecial kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.<init> (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlin/coroutines/Continuation;)V
      // 03a: astore 5
      // 03c: aload 5
      // 03e: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.result Ljava/lang/Object;
      // 041: astore 9
      // 043: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 046: astore 11
      // 048: aload 5
      // 04a: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label I
      // 04d: istore 6
      // 04f: iload 6
      // 051: ifeq 08e
      // 054: iload 6
      // 056: bipush 1
      // 057: if_icmpne 083
      // 05a: aload 5
      // 05c: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.J$0 J
      // 05f: lstore 3
      // 060: aload 5
      // 062: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.I$0 I
      // 065: istore 2
      // 066: aload 5
      // 068: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.L$1 Ljava/lang/Object;
      // 06b: checkcast kotlinx/coroutines/channels/ChannelSegment
      // 06e: astore 1
      // 06f: aload 5
      // 071: getfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.L$0 Ljava/lang/Object;
      // 074: checkcast kotlinx/coroutines/channels/BufferedChannel
      // 077: astore 1
      // 078: aload 9
      // 07a: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 07d: aload 9
      // 07f: astore 1
      // 080: goto 279
      // 083: new java/lang/IllegalStateException
      // 086: dup
      // 087: ldc_w "call to 'resume' before 'invoke' with coroutine"
      // 08a: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 08d: athrow
      // 08e: aload 9
      // 090: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 093: aload 5
      // 095: aload 0
      // 096: putfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.L$0 Ljava/lang/Object;
      // 099: aload 5
      // 09b: aload 1
      // 09c: putfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.L$1 Ljava/lang/Object;
      // 09f: aload 5
      // 0a1: iload 2
      // 0a2: putfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.I$0 I
      // 0a5: aload 5
      // 0a7: lload 3
      // 0a8: putfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.J$0 J
      // 0ab: aload 5
      // 0ad: bipush 1
      // 0ae: putfield kotlinx/coroutines/channels/BufferedChannel$receiveCatchingOnNoWaiterSuspend$1.label I
      // 0b1: aload 5
      // 0b3: checkcast kotlin/coroutines/Continuation
      // 0b6: astore 13
      // 0b8: aload 13
      // 0ba: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
      // 0bd: invokestatic kotlinx/coroutines/CancellableContinuationKt.getOrCreateCancellableContinuation (Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/CancellableContinuationImpl;
      // 0c0: astore 12
      // 0c2: new kotlinx/coroutines/channels/ReceiveCatching
      // 0c5: astore 14
      // 0c7: aload 12
      // 0c9: ldc_w "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuationImpl<kotlinx.coroutines.channels.ChannelResult<E of kotlinx.coroutines.channels.BufferedChannel.receiveCatchingOnNoWaiterSuspend_GKJJFZk$lambda$35>>"
      // 0cc: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 0cf: aload 14
      // 0d1: aload 12
      // 0d3: invokespecial kotlinx/coroutines/channels/ReceiveCatching.<init> (Lkotlinx/coroutines/CancellableContinuationImpl;)V
      // 0d6: aload 0
      // 0d7: aload 1
      // 0d8: iload 2
      // 0d9: lload 3
      // 0da: aload 14
      // 0dc: checkcast kotlinx/coroutines/Waiter
      // 0df: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellReceive (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;IJLjava/lang/Object;)Ljava/lang/Object;
      // 0e2: astore 15
      // 0e4: aload 15
      // 0e6: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND$p ()Lkotlinx/coroutines/internal/Symbol;
      // 0e9: if_acmpne 0fa
      // 0ec: aload 0
      // 0ed: aload 14
      // 0ef: checkcast kotlinx/coroutines/Waiter
      // 0f2: aload 1
      // 0f3: iload 2
      // 0f4: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareReceiverForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
      // 0f7: goto 258
      // 0fa: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getFAILED$p ()Lkotlinx/coroutines/internal/Symbol;
      // 0fd: astore 5
      // 0ff: aconst_null
      // 100: astore 9
      // 102: aconst_null
      // 103: astore 10
      // 105: aload 15
      // 107: aload 5
      // 109: if_acmpne 221
      // 10c: lload 3
      // 10d: aload 0
      // 10e: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getSendersCounter$kotlinx_coroutines_core ()J
      // 111: lcmp
      // 112: ifge 119
      // 115: aload 1
      // 116: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 119: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getReceiveSegment$FU$p ()Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 11c: aload 0
      // 11d: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 120: checkcast kotlinx/coroutines/channels/ChannelSegment
      // 123: astore 5
      // 125: aload 0
      // 126: invokevirtual kotlinx/coroutines/channels/BufferedChannel.isClosedForReceive ()Z
      // 129: ifeq 138
      // 12c: aload 0
      // 12d: aload 12
      // 12f: checkcast kotlinx/coroutines/CancellableContinuation
      // 132: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$onClosedReceiveCatchingOnNoWaiterSuspend (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/CancellableContinuation;)V
      // 135: goto 258
      // 138: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getReceivers$FU$p ()Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
      // 13b: aload 0
      // 13c: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.getAndIncrement (Ljava/lang/Object;)J
      // 13f: lstore 7
      // 141: lload 7
      // 143: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
      // 146: i2l
      // 147: ldiv
      // 148: lstore 3
      // 149: lload 7
      // 14b: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
      // 14e: i2l
      // 14f: lrem
      // 150: l2i
      // 151: istore 2
      // 152: aload 5
      // 154: astore 1
      // 155: aload 5
      // 157: getfield kotlinx/coroutines/channels/ChannelSegment.id J
      // 15a: lload 3
      // 15b: lcmp
      // 15c: ifeq 16e
      // 15f: aload 0
      // 160: lload 3
      // 161: aload 5
      // 163: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$findSegmentReceive (Lkotlinx/coroutines/channels/BufferedChannel;JLkotlinx/coroutines/channels/ChannelSegment;)Lkotlinx/coroutines/channels/ChannelSegment;
      // 166: astore 1
      // 167: aload 1
      // 168: ifnonnull 16e
      // 16b: goto 125
      // 16e: aload 0
      // 16f: aload 1
      // 170: iload 2
      // 171: lload 7
      // 173: aload 14
      // 175: checkcast kotlinx/coroutines/Waiter
      // 178: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellReceive (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;IJLjava/lang/Object;)Ljava/lang/Object;
      // 17b: astore 15
      // 17d: aload 15
      // 17f: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND$p ()Lkotlinx/coroutines/internal/Symbol;
      // 182: if_acmpne 1ab
      // 185: aload 10
      // 187: astore 5
      // 189: aload 14
      // 18b: checkcast kotlinx/coroutines/Waiter
      // 18e: instanceof kotlinx/coroutines/Waiter
      // 191: ifeq 19b
      // 194: aload 14
      // 196: checkcast kotlinx/coroutines/Waiter
      // 199: astore 5
      // 19b: aload 5
      // 19d: ifnull 258
      // 1a0: aload 0
      // 1a1: aload 5
      // 1a3: aload 1
      // 1a4: iload 2
      // 1a5: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareReceiverForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
      // 1a8: goto 258
      // 1ab: aload 15
      // 1ad: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getFAILED$p ()Lkotlinx/coroutines/internal/Symbol;
      // 1b0: if_acmpne 1ca
      // 1b3: aload 1
      // 1b4: astore 5
      // 1b6: lload 7
      // 1b8: aload 0
      // 1b9: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getSendersCounter$kotlinx_coroutines_core ()J
      // 1bc: lcmp
      // 1bd: ifge 125
      // 1c0: aload 1
      // 1c1: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 1c4: aload 1
      // 1c5: astore 5
      // 1c7: goto 125
      // 1ca: aload 15
      // 1cc: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND_NO_WAITER$p ()Lkotlinx/coroutines/internal/Symbol;
      // 1cf: if_acmpeq 211
      // 1d2: aload 1
      // 1d3: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 1d6: getstatic kotlinx/coroutines/channels/ChannelResult.Companion Lkotlinx/coroutines/channels/ChannelResult$Companion;
      // 1d9: aload 15
      // 1db: invokevirtual kotlinx/coroutines/channels/ChannelResult$Companion.success-JP2dKIU (Ljava/lang/Object;)Ljava/lang/Object;
      // 1de: invokestatic kotlinx/coroutines/channels/ChannelResult.box-impl (Ljava/lang/Object;)Lkotlinx/coroutines/channels/ChannelResult;
      // 1e1: astore 10
      // 1e3: aload 0
      // 1e4: getfield kotlinx/coroutines/channels/BufferedChannel.onUndeliveredElement Lkotlin/jvm/functions/Function1;
      // 1e7: astore 14
      // 1e9: aload 10
      // 1eb: astore 5
      // 1ed: aload 9
      // 1ef: astore 1
      // 1f0: aload 14
      // 1f2: ifnull 206
      // 1f5: aload 14
      // 1f7: aload 15
      // 1f9: aload 12
      // 1fb: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 1fe: invokestatic kotlinx/coroutines/internal/OnUndeliveredElementKt.bindCancellationFun (Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;
      // 201: astore 1
      // 202: aload 10
      // 204: astore 5
      // 206: aload 12
      // 208: aload 5
      // 20a: aload 1
      // 20b: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.resume (Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V
      // 20e: goto 258
      // 211: new java/lang/IllegalStateException
      // 214: astore 1
      // 215: aload 1
      // 216: ldc_w "unexpected"
      // 219: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 21c: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 21f: aload 1
      // 220: athrow
      // 221: aload 1
      // 222: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 225: getstatic kotlinx/coroutines/channels/ChannelResult.Companion Lkotlinx/coroutines/channels/ChannelResult$Companion;
      // 228: aload 15
      // 22a: invokevirtual kotlinx/coroutines/channels/ChannelResult$Companion.success-JP2dKIU (Ljava/lang/Object;)Ljava/lang/Object;
      // 22d: invokestatic kotlinx/coroutines/channels/ChannelResult.box-impl (Ljava/lang/Object;)Lkotlinx/coroutines/channels/ChannelResult;
      // 230: astore 10
      // 232: aload 0
      // 233: getfield kotlinx/coroutines/channels/BufferedChannel.onUndeliveredElement Lkotlin/jvm/functions/Function1;
      // 236: astore 14
      // 238: aload 10
      // 23a: astore 5
      // 23c: aload 9
      // 23e: astore 1
      // 23f: aload 14
      // 241: ifnull 206
      // 244: aload 14
      // 246: aload 15
      // 248: aload 12
      // 24a: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 24d: invokestatic kotlinx/coroutines/internal/OnUndeliveredElementKt.bindCancellationFun (Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;
      // 250: astore 1
      // 251: aload 10
      // 253: astore 5
      // 255: goto 206
      // 258: aload 12
      // 25a: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
      // 25d: astore 5
      // 25f: aload 5
      // 261: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 264: if_acmpne 26c
      // 267: aload 13
      // 269: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
      // 26c: aload 5
      // 26e: astore 1
      // 26f: aload 5
      // 271: aload 11
      // 273: if_acmpne 279
      // 276: aload 11
      // 278: areturn
      // 279: aload 1
      // 27a: checkcast kotlinx/coroutines/channels/ChannelResult
      // 27d: invokevirtual kotlinx/coroutines/channels/ChannelResult.unbox-impl ()Ljava/lang/Object;
      // 280: areturn
      // 281: astore 1
      // 282: aload 12
      // 284: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.releaseClaimedReusableContinuation$kotlinx_coroutines_core ()V
      // 287: aload 1
      // 288: athrow
   }

   private inline fun <R> receiveImpl(
      waiter: Any?,
      onElementRetrieved: (Any) -> R,
      onSuspend: (ChannelSegment<Any>, Int, Long) -> R,
      onClosed: () -> R,
      onNoWaiterSuspend: (ChannelSegment<Any>, Int, Long) -> R = <unrepresentable>.INSTANCE as Function3
   ): R {
      var var11: ChannelSegment = access$getReceiveSegment$FU$p().get(this) as ChannelSegment;

      while (true) {
         val var6: Int;
         val var9: Long;
         while (true) {
            if (this.isClosedForReceive()) {
               return (R)var4.invoke();
            }

            var9 = access$getReceivers$FU$p().getAndIncrement(this);
            val var7: Long = var9 / BufferedChannelKt.SEGMENT_SIZE;
            var6 = (int)(var9 % BufferedChannelKt.SEGMENT_SIZE);
            if (var11.id == var7) {
               break;
            }

            val var12: ChannelSegment = access$findSegmentReceive(this, var7, var11);
            if (var12 != null) {
               var11 = var12;
               break;
            }
         }

         val var15: Any = access$updateCellReceive(this, var11, var6, var9, var1);
         if (var15 === BufferedChannelKt.access$getSUSPEND$p()) {
            if (var1 is Waiter) {
               var1 = var1;
            } else {
               var1 = null;
            }

            if (var1 != null) {
               access$prepareReceiverForSuspension(this, var1, var11, var6);
            }

            var1 = var3.invoke(var11, var6, var9);
            break;
         }

         if (var15 != BufferedChannelKt.access$getFAILED$p()) {
            if (var15 === BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()) {
               var1 = var5.invoke(var11, var6, var9);
            } else {
               var11.cleanPrev();
               var1 = var2.invoke(var15);
            }
            break;
         }

         if (var9 < this.getSendersCounter$kotlinx_coroutines_core()) {
            var11.cleanPrev();
         }
      }

      return (R)var1;
   }

   private inline fun receiveImplOnNoWaiter(
      segment: ChannelSegment<Any>,
      index: Int,
      r: Long,
      waiter: Waiter,
      onElementRetrieved: (Any) -> Unit,
      onClosed: () -> Unit
   ) {
      var var10: Any = access$updateCellReceive(this, var1, var2, var3, var5);
      if (var10 === BufferedChannelKt.access$getSUSPEND$p()) {
         access$prepareReceiverForSuspension(this, var5, var1, var2);
      } else if (var10 === BufferedChannelKt.access$getFAILED$p()) {
         if (var3 < this.getSendersCounter$kotlinx_coroutines_core()) {
            var1.cleanPrev();
         }

         var10 = access$getReceiveSegment$FU$p().get(this) as ChannelSegment;

         while (true) {
            if (this.isClosedForReceive()) {
               var7.invoke();
               break;
            }

            val var8: Long = access$getReceivers$FU$p().getAndIncrement(this);
            var3 = var8 / BufferedChannelKt.SEGMENT_SIZE;
            var2 = (int)(var8 % BufferedChannelKt.SEGMENT_SIZE);
            var1 = (ChannelSegment)var10;
            if (((ChannelSegment)var10).id != var3) {
               var1 = access$findSegmentReceive(this, var3, (ChannelSegment)var10);
               if (var1 == null) {
                  continue;
               }
            }

            var10 = access$updateCellReceive(this, var1, var2, var8, var5);
            if (var10 === BufferedChannelKt.access$getSUSPEND$p()) {
               if (var5 !is Waiter) {
                  var5 = null;
               }

               if (var5 != null) {
                  access$prepareReceiverForSuspension(this, var5, var1, var2);
               }
            }

            if (var10 != BufferedChannelKt.access$getFAILED$p()) {
               if (var10 === BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()) {
                  throw new IllegalStateException("unexpected".toString());
               }

               var1.cleanPrev();
               var6.invoke(var10);
               break;
            }

            var10 = var1;
            if (var8 < this.getSendersCounter$kotlinx_coroutines_core()) {
               var1.cleanPrev();
               var10 = var1;
            }
         }
      } else {
         var1.cleanPrev();
         var6.invoke(var10);
      }
   }

   private suspend fun receiveOnNoWaiterSuspend(segment: ChannelSegment<Any>, index: Int, r: Long): Any {
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
      // 000: aload 5
      // 002: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
      // 005: invokestatic kotlinx/coroutines/CancellableContinuationKt.getOrCreateCancellableContinuation (Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/CancellableContinuationImpl;
      // 008: astore 12
      // 00a: aload 0
      // 00b: aload 1
      // 00c: iload 2
      // 00d: lload 3
      // 00e: aload 12
      // 010: checkcast kotlinx/coroutines/Waiter
      // 013: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellReceive (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;IJLjava/lang/Object;)Ljava/lang/Object;
      // 016: astore 11
      // 018: aload 11
      // 01a: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND$p ()Lkotlinx/coroutines/internal/Symbol;
      // 01d: if_acmpne 02e
      // 020: aload 0
      // 021: aload 12
      // 023: checkcast kotlinx/coroutines/Waiter
      // 026: aload 1
      // 027: iload 2
      // 028: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareReceiverForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
      // 02b: goto 172
      // 02e: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getFAILED$p ()Lkotlinx/coroutines/internal/Symbol;
      // 031: astore 8
      // 033: aconst_null
      // 034: astore 9
      // 036: aconst_null
      // 037: astore 10
      // 039: aload 11
      // 03b: aload 8
      // 03d: if_acmpne 148
      // 040: lload 3
      // 041: aload 0
      // 042: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getSendersCounter$kotlinx_coroutines_core ()J
      // 045: lcmp
      // 046: ifge 04d
      // 049: aload 1
      // 04a: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 04d: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getReceiveSegment$FU$p ()Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 050: aload 0
      // 051: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 054: checkcast kotlinx/coroutines/channels/ChannelSegment
      // 057: astore 8
      // 059: aload 0
      // 05a: invokevirtual kotlinx/coroutines/channels/BufferedChannel.isClosedForReceive ()Z
      // 05d: ifeq 06c
      // 060: aload 0
      // 061: aload 12
      // 063: checkcast kotlinx/coroutines/CancellableContinuation
      // 066: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$onClosedReceiveOnNoWaiterSuspend (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/CancellableContinuation;)V
      // 069: goto 172
      // 06c: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getReceivers$FU$p ()Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
      // 06f: aload 0
      // 070: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.getAndIncrement (Ljava/lang/Object;)J
      // 073: lstore 6
      // 075: lload 6
      // 077: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
      // 07a: i2l
      // 07b: ldiv
      // 07c: lstore 3
      // 07d: lload 6
      // 07f: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
      // 082: i2l
      // 083: lrem
      // 084: l2i
      // 085: istore 2
      // 086: aload 8
      // 088: astore 1
      // 089: aload 8
      // 08b: getfield kotlinx/coroutines/channels/ChannelSegment.id J
      // 08e: lload 3
      // 08f: lcmp
      // 090: ifeq 0a2
      // 093: aload 0
      // 094: lload 3
      // 095: aload 8
      // 097: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$findSegmentReceive (Lkotlinx/coroutines/channels/BufferedChannel;JLkotlinx/coroutines/channels/ChannelSegment;)Lkotlinx/coroutines/channels/ChannelSegment;
      // 09a: astore 1
      // 09b: aload 1
      // 09c: ifnonnull 0a2
      // 09f: goto 059
      // 0a2: aload 0
      // 0a3: aload 1
      // 0a4: iload 2
      // 0a5: lload 6
      // 0a7: aload 12
      // 0a9: checkcast kotlinx/coroutines/Waiter
      // 0ac: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellReceive (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;IJLjava/lang/Object;)Ljava/lang/Object;
      // 0af: astore 11
      // 0b1: aload 11
      // 0b3: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND$p ()Lkotlinx/coroutines/internal/Symbol;
      // 0b6: if_acmpne 0df
      // 0b9: aload 10
      // 0bb: astore 8
      // 0bd: aload 12
      // 0bf: checkcast kotlinx/coroutines/Waiter
      // 0c2: instanceof kotlinx/coroutines/Waiter
      // 0c5: ifeq 0cf
      // 0c8: aload 12
      // 0ca: checkcast kotlinx/coroutines/Waiter
      // 0cd: astore 8
      // 0cf: aload 8
      // 0d1: ifnull 172
      // 0d4: aload 0
      // 0d5: aload 8
      // 0d7: aload 1
      // 0d8: iload 2
      // 0d9: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareReceiverForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
      // 0dc: goto 172
      // 0df: aload 11
      // 0e1: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getFAILED$p ()Lkotlinx/coroutines/internal/Symbol;
      // 0e4: if_acmpne 0fe
      // 0e7: aload 1
      // 0e8: astore 8
      // 0ea: lload 6
      // 0ec: aload 0
      // 0ed: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getSendersCounter$kotlinx_coroutines_core ()J
      // 0f0: lcmp
      // 0f1: ifge 059
      // 0f4: aload 1
      // 0f5: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 0f8: aload 1
      // 0f9: astore 8
      // 0fb: goto 059
      // 0fe: aload 11
      // 100: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND_NO_WAITER$p ()Lkotlinx/coroutines/internal/Symbol;
      // 103: if_acmpeq 138
      // 106: aload 1
      // 107: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 10a: aload 0
      // 10b: getfield kotlinx/coroutines/channels/BufferedChannel.onUndeliveredElement Lkotlin/jvm/functions/Function1;
      // 10e: astore 10
      // 110: aload 11
      // 112: astore 8
      // 114: aload 9
      // 116: astore 1
      // 117: aload 10
      // 119: ifnull 12d
      // 11c: aload 10
      // 11e: aload 11
      // 120: aload 12
      // 122: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 125: invokestatic kotlinx/coroutines/internal/OnUndeliveredElementKt.bindCancellationFun (Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;
      // 128: astore 1
      // 129: aload 11
      // 12b: astore 8
      // 12d: aload 12
      // 12f: aload 8
      // 131: aload 1
      // 132: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.resume (Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V
      // 135: goto 172
      // 138: new java/lang/IllegalStateException
      // 13b: astore 1
      // 13c: aload 1
      // 13d: ldc_w "unexpected"
      // 140: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 143: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 146: aload 1
      // 147: athrow
      // 148: aload 1
      // 149: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 14c: aload 0
      // 14d: getfield kotlinx/coroutines/channels/BufferedChannel.onUndeliveredElement Lkotlin/jvm/functions/Function1;
      // 150: astore 10
      // 152: aload 11
      // 154: astore 8
      // 156: aload 9
      // 158: astore 1
      // 159: aload 10
      // 15b: ifnull 12d
      // 15e: aload 10
      // 160: aload 11
      // 162: aload 12
      // 164: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 167: invokestatic kotlinx/coroutines/internal/OnUndeliveredElementKt.bindCancellationFun (Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;
      // 16a: astore 1
      // 16b: aload 11
      // 16d: astore 8
      // 16f: goto 12d
      // 172: aload 12
      // 174: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
      // 177: astore 1
      // 178: aload 1
      // 179: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 17c: if_acmpne 184
      // 17f: aload 5
      // 181: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
      // 184: aload 1
      // 185: areturn
      // 186: astore 1
      // 187: aload 12
      // 189: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.releaseClaimedReusableContinuation$kotlinx_coroutines_core ()V
      // 18c: aload 1
      // 18d: athrow
   }

   private fun registerSelectForReceive(select: SelectInstance<*>, ignoredParam: Any?) {
      var var8: ChannelSegment = access$getReceiveSegment$FU$p().get(this) as ChannelSegment;

      while (true) {
         if (this.isClosedForReceive()) {
            this.onClosedSelectOnReceive(var1);
            break;
         }

         val var4: Long = access$getReceivers$FU$p().getAndIncrement(this);
         val var6: Long = var4 / BufferedChannelKt.SEGMENT_SIZE;
         val var3: Int = (int)(var4 % BufferedChannelKt.SEGMENT_SIZE);
         var2 = var8;
         if (var8.id != var6) {
            var2 = access$findSegmentReceive(this, var6, var8);
            if (var2 == null) {
               continue;
            }
         }

         var8 = (ChannelSegment)access$updateCellReceive(this, var2, var3, var4, var1);
         if (var8 === BufferedChannelKt.access$getSUSPEND$p()) {
            val var9: Waiter;
            if (var1 is Waiter) {
               var9 = var1 as Waiter;
            } else {
               var9 = null;
            }

            if (var9 != null) {
               access$prepareReceiverForSuspension(this, var9, var2, var3);
            }
            break;
         }

         if (var8 != BufferedChannelKt.access$getFAILED$p()) {
            if (var8 === BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()) {
               throw new IllegalStateException("unexpected".toString());
            }

            var2.cleanPrev();
            var1.selectInRegistrationPhase(var8);
            break;
         }

         var8 = var2;
         if (var4 < this.getSendersCounter$kotlinx_coroutines_core()) {
            var2.cleanPrev();
            var8 = var2;
         }
      }
   }

   private fun removeUnprocessedElements(lastSegment: ChannelSegment<Any>) {
      val var10: Function1 = this.onUndeliveredElement;
      var var8: UndeliveredElementException = null;
      var var5: Any = InlineList.constructor-impl$default(null, 1, null);
      var var6: ChannelSegment = var1;

      var var7: Any;
      val var9: ChannelSegment;
      label96:
      do {
         var var2: Int = BufferedChannelKt.SEGMENT_SIZE - 1;
         var7 = var5;

         for (var11 = var8; -1 < var2; var2--) {
            val var3: Long = var6.id * BufferedChannelKt.SEGMENT_SIZE + var2;

            while (true) {
               var5 = var6.getState$kotlinx_coroutines_core(var2);
               if (var5 === BufferedChannelKt.access$getDONE_RCV$p()) {
                  break label96;
               }

               if (var5 === BufferedChannelKt.BUFFERED) {
                  if (var3 < this.getReceiversCounter$kotlinx_coroutines_core()) {
                     break label96;
                  }

                  if (var6.casState$kotlinx_coroutines_core(var2, var5, BufferedChannelKt.getCHANNEL_CLOSED())) {
                     var5 = var11;
                     if (var10 != null) {
                        var5 = OnUndeliveredElementKt.callUndeliveredElementCatchingException(var10, var6.getElement$kotlinx_coroutines_core(var2), var11);
                     }

                     var6.cleanElement$kotlinx_coroutines_core(var2);
                     var6.onSlotCleaned();
                     var11 = (UndeliveredElementException)var5;
                     break;
                  }
               } else if (var5 != BufferedChannelKt.access$getIN_BUFFER$p() && var5 != null) {
                  if (var5 !is Waiter && var5 !is WaiterEB) {
                     if (var5 === BufferedChannelKt.access$getRESUMING_BY_EB$p() || var5 === BufferedChannelKt.access$getRESUMING_BY_RCV$p()) {
                        break label96;
                     }

                     if (var5 != BufferedChannelKt.access$getRESUMING_BY_EB$p()) {
                        break;
                     }
                  } else {
                     if (var3 < this.getReceiversCounter$kotlinx_coroutines_core()) {
                        break label96;
                     }

                     val var17: Waiter;
                     if (var5 is WaiterEB) {
                        var17 = (var5 as WaiterEB).waiter;
                     } else {
                        var17 = var5 as Waiter;
                     }

                     if (var6.casState$kotlinx_coroutines_core(var2, var5, BufferedChannelKt.getCHANNEL_CLOSED())) {
                        var5 = var11;
                        if (var10 != null) {
                           var5 = OnUndeliveredElementKt.callUndeliveredElementCatchingException(var10, var6.getElement$kotlinx_coroutines_core(var2), var11);
                        }

                        var7 = InlineList.plus-FjFbRPM(var7, var17);
                        var6.cleanElement$kotlinx_coroutines_core(var2);
                        var6.onSlotCleaned();
                        var11 = (UndeliveredElementException)var5;
                        break;
                     }
                  }
               } else if (var6.casState$kotlinx_coroutines_core(var2, var5, BufferedChannelKt.getCHANNEL_CLOSED())) {
                  var6.onSlotCleaned();
                  break;
               }
            }
         }

         var9 = var6.getPrev();
         var8 = var11;
         var5 = var7;
         var6 = var9;
      } while (var9 != null);

      if (var7 != null) {
         if (var7 !is ArrayList) {
            this.resumeSenderOnCancelledChannel(var7 as Waiter);
         } else {
            var5 = var7 as ArrayList;

            for (int var12 = ((ArrayList)var7).size() - 1; -1 < var12; var12--) {
               this.resumeSenderOnCancelledChannel(var5.get(var12) as Waiter);
            }
         }
      }

      if (var11 != null) {
         throw var11;
      }
   }

   private fun Waiter.resumeReceiverOnClosedChannel() {
      this.resumeWaiterOnClosedChannel(var1, true);
   }

   private fun Waiter.resumeSenderOnCancelledChannel() {
      this.resumeWaiterOnClosedChannel(var1, false);
   }

   private fun Waiter.resumeWaiterOnClosedChannel(receiver: Boolean) {
      if (var1 is BufferedChannel.SendBroadcast) {
         val var4: Continuation = (var1 as BufferedChannel.SendBroadcast).getCont();
         val var3: Result.Companion = Result.Companion;
         var4.resumeWith(Result.constructor-impl(false));
      } else if (var1 is CancellableContinuation) {
         val var8: Continuation = var1 as Continuation;
         val var5: Result.Companion = Result.Companion;
         val var6: java.lang.Throwable;
         if (var2) {
            var6 = this.getReceiveException();
         } else {
            var6 = this.getSendException();
         }

         var8.resumeWith(Result.constructor-impl(ResultKt.createFailure(var6)));
      } else if (var1 is ReceiveCatching) {
         val var7: Continuation = (var1 as ReceiveCatching).cont;
         val var9: Result.Companion = Result.Companion;
         var7.resumeWith(Result.constructor-impl(ChannelResult.box-impl(ChannelResult.Companion.closed-JP2dKIU(this.getCloseCause()))));
      } else if (var1 is BufferedChannel.BufferedChannelIterator) {
         (var1 as BufferedChannel.BufferedChannelIterator).tryResumeHasNextOnClosedChannel();
      } else {
         if (var1 !is SelectInstance) {
            val var10: StringBuilder = new StringBuilder("Unexpected waiter: ");
            var10.append(var1);
            throw new IllegalStateException(var10.toString().toString());
         }

         (var1 as SelectInstance).trySelect(this, BufferedChannelKt.getCHANNEL_CLOSED());
      }
   }

   private inline fun sendImplOnNoWaiter(
      segment: ChannelSegment<Any>,
      index: Int,
      element: Any,
      s: Long,
      waiter: Waiter,
      onRendezvousOrBuffered: () -> Unit,
      onClosed: () -> Unit
   ) {
      var var13: Waiter = var6;
      var var9: Int = access$updateCellSend(this, var1, var2, var3, var4, var6, false);
      if (var9 != 0) {
         if (var9 != 1) {
            if (var9 != 2) {
               if (var9 != 4) {
                  if (var9 != 5) {
                     throw new IllegalStateException("unexpected".toString());
                  }

                  var1.cleanPrev();
                  var1 = access$getSendSegment$FU$p().get(this) as ChannelSegment;

                  while (true) {
                     var var10: Long = access$getSendersAndCloseStatus$FU$p().getAndIncrement(this);
                     var4 = var10 and 1152921504606846975L;
                     val var12: Boolean = access$isClosedForSend0(this, var10);
                     var10 = var4 / BufferedChannelKt.SEGMENT_SIZE;
                     var2 = (int)(var4 % BufferedChannelKt.SEGMENT_SIZE);
                     if (var1.id != var10) {
                        val var14: ChannelSegment = access$findSegmentSend(this, var10, var1);
                        if (var14 == null) {
                           if (var12) {
                              var8.invoke();
                              break;
                           }
                           continue;
                        }

                        var1 = var14;
                     }

                     var9 = access$updateCellSend(this, var1, var2, var3, var4, var6, var12);
                     if (var9 == 0) {
                        var1.cleanPrev();
                        var7.invoke();
                        break;
                     }

                     if (var9 == 1) {
                        var7.invoke();
                        break;
                     }

                     if (var9 == 2) {
                        if (var12) {
                           var1.onSlotCleaned();
                           var8.invoke();
                        } else {
                           if (var6 !is Waiter) {
                              var13 = null;
                           }

                           if (var13 != null) {
                              access$prepareSenderForSuspension(this, var13, var1, var2);
                           }
                        }
                        break;
                     }

                     if (var9 == 3) {
                        throw new IllegalStateException("unexpected".toString());
                     }

                     if (var9 == 4) {
                        if (var4 < this.getReceiversCounter$kotlinx_coroutines_core()) {
                           var1.cleanPrev();
                        }

                        var8.invoke();
                        break;
                     }

                     if (var9 == 5) {
                        var1.cleanPrev();
                     }
                  }
               } else {
                  if (var4 < this.getReceiversCounter$kotlinx_coroutines_core()) {
                     var1.cleanPrev();
                  }

                  var8.invoke();
               }
            } else {
               access$prepareSenderForSuspension(this, var6, var1, var2);
            }
         } else {
            var7.invoke();
         }
      } else {
         var1.cleanPrev();
         var7.invoke();
      }
   }

   private suspend fun sendOnNoWaiterSuspend(segment: ChannelSegment<Any>, index: Int, element: Any, s: Long) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //
      // Bytecode:
      // 000: aload 6
      // 002: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
      // 005: invokestatic kotlinx/coroutines/CancellableContinuationKt.getOrCreateCancellableContinuation (Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/CancellableContinuationImpl;
      // 008: astore 12
      // 00a: aload 0
      // 00b: aload 1
      // 00c: iload 2
      // 00d: aload 3
      // 00e: lload 4
      // 010: aload 12
      // 012: checkcast kotlinx/coroutines/Waiter
      // 015: bipush 0
      // 016: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellSend (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLjava/lang/Object;Z)I
      // 019: istore 7
      // 01b: iload 7
      // 01d: ifeq 1bc
      // 020: iload 7
      // 022: bipush 1
      // 023: if_icmpeq 1a8
      // 026: iload 7
      // 028: bipush 2
      // 029: if_icmpeq 19a
      // 02c: iload 7
      // 02e: bipush 4
      // 02f: if_icmpeq 183
      // 032: iload 7
      // 034: bipush 5
      // 035: if_icmpne 173
      // 038: aload 1
      // 039: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 03c: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getSendSegment$FU$p ()Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 03f: aload 0
      // 040: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 043: checkcast kotlinx/coroutines/channels/ChannelSegment
      // 046: astore 1
      // 047: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getSendersAndCloseStatus$FU$p ()Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
      // 04a: aload 0
      // 04b: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.getAndIncrement (Ljava/lang/Object;)J
      // 04e: lstore 8
      // 050: lload 8
      // 052: ldc2_w 1152921504606846975
      // 055: land
      // 056: lstore 4
      // 058: aload 0
      // 059: lload 8
      // 05b: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$isClosedForSend0 (Lkotlinx/coroutines/channels/BufferedChannel;J)Z
      // 05e: istore 10
      // 060: lload 4
      // 062: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
      // 065: i2l
      // 066: ldiv
      // 067: lstore 8
      // 069: lload 4
      // 06b: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
      // 06e: i2l
      // 06f: lrem
      // 070: l2i
      // 071: istore 2
      // 072: aload 1
      // 073: getfield kotlinx/coroutines/channels/ChannelSegment.id J
      // 076: lload 8
      // 078: lcmp
      // 079: ifeq 0a4
      // 07c: aload 0
      // 07d: lload 8
      // 07f: aload 1
      // 080: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$findSegmentSend (Lkotlinx/coroutines/channels/BufferedChannel;JLkotlinx/coroutines/channels/ChannelSegment;)Lkotlinx/coroutines/channels/ChannelSegment;
      // 083: astore 11
      // 085: aload 11
      // 087: ifnonnull 09e
      // 08a: iload 10
      // 08c: ifeq 047
      // 08f: aload 12
      // 091: checkcast kotlinx/coroutines/CancellableContinuation
      // 094: astore 1
      // 095: aload 0
      // 096: aload 3
      // 097: aload 1
      // 098: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$onClosedSendOnNoWaiterSuspend (Lkotlinx/coroutines/channels/BufferedChannel;Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V
      // 09b: goto 1d4
      // 09e: aload 11
      // 0a0: astore 1
      // 0a1: goto 0a4
      // 0a4: aload 0
      // 0a5: aload 1
      // 0a6: iload 2
      // 0a7: aload 3
      // 0a8: lload 4
      // 0aa: aload 12
      // 0ac: checkcast kotlinx/coroutines/Waiter
      // 0af: iload 10
      // 0b1: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellSend (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;ILjava/lang/Object;JLjava/lang/Object;Z)I
      // 0b4: istore 7
      // 0b6: iload 7
      // 0b8: ifeq 15b
      // 0bb: iload 7
      // 0bd: bipush 1
      // 0be: if_icmpeq 140
      // 0c1: iload 7
      // 0c3: bipush 2
      // 0c4: if_icmpeq 10a
      // 0c7: iload 7
      // 0c9: bipush 3
      // 0ca: if_icmpeq 0fa
      // 0cd: iload 7
      // 0cf: bipush 4
      // 0d0: if_icmpeq 0e3
      // 0d3: iload 7
      // 0d5: bipush 5
      // 0d6: if_icmpeq 0dc
      // 0d9: goto 0e0
      // 0dc: aload 1
      // 0dd: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 0e0: goto 047
      // 0e3: lload 4
      // 0e5: aload 0
      // 0e6: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getReceiversCounter$kotlinx_coroutines_core ()J
      // 0e9: lcmp
      // 0ea: ifge 0f1
      // 0ed: aload 1
      // 0ee: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 0f1: aload 12
      // 0f3: checkcast kotlinx/coroutines/CancellableContinuation
      // 0f6: astore 1
      // 0f7: goto 095
      // 0fa: new java/lang/IllegalStateException
      // 0fd: astore 1
      // 0fe: aload 1
      // 0ff: ldc_w "unexpected"
      // 102: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 105: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 108: aload 1
      // 109: athrow
      // 10a: iload 10
      // 10c: ifeq 11c
      // 10f: aload 1
      // 110: invokevirtual kotlinx/coroutines/channels/ChannelSegment.onSlotCleaned ()V
      // 113: aload 12
      // 115: checkcast kotlinx/coroutines/CancellableContinuation
      // 118: astore 1
      // 119: goto 095
      // 11c: aload 12
      // 11e: checkcast kotlinx/coroutines/Waiter
      // 121: instanceof kotlinx/coroutines/Waiter
      // 124: ifeq 130
      // 127: aload 12
      // 129: checkcast kotlinx/coroutines/Waiter
      // 12c: astore 3
      // 12d: goto 132
      // 130: aconst_null
      // 131: astore 3
      // 132: aload 3
      // 133: ifnull 1d4
      // 136: aload 0
      // 137: aload 3
      // 138: aload 1
      // 139: iload 2
      // 13a: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareSenderForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
      // 13d: goto 1d4
      // 140: aload 12
      // 142: checkcast kotlin/coroutines/Continuation
      // 145: astore 1
      // 146: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 149: astore 3
      // 14a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 14d: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 150: astore 3
      // 151: aload 1
      // 152: aload 3
      // 153: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 158: goto 1d4
      // 15b: aload 1
      // 15c: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 15f: aload 12
      // 161: checkcast kotlin/coroutines/Continuation
      // 164: astore 1
      // 165: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 168: astore 3
      // 169: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 16c: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 16f: astore 3
      // 170: goto 151
      // 173: new java/lang/IllegalStateException
      // 176: astore 1
      // 177: aload 1
      // 178: ldc_w "unexpected"
      // 17b: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 17e: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 181: aload 1
      // 182: athrow
      // 183: lload 4
      // 185: aload 0
      // 186: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getReceiversCounter$kotlinx_coroutines_core ()J
      // 189: lcmp
      // 18a: ifge 191
      // 18d: aload 1
      // 18e: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 191: aload 12
      // 193: checkcast kotlinx/coroutines/CancellableContinuation
      // 196: astore 1
      // 197: goto 095
      // 19a: aload 0
      // 19b: aload 12
      // 19d: checkcast kotlinx/coroutines/Waiter
      // 1a0: aload 1
      // 1a1: iload 2
      // 1a2: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareSenderForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
      // 1a5: goto 1d4
      // 1a8: aload 12
      // 1aa: checkcast kotlin/coroutines/Continuation
      // 1ad: astore 1
      // 1ae: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 1b1: astore 3
      // 1b2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1b5: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1b8: astore 3
      // 1b9: goto 151
      // 1bc: aload 1
      // 1bd: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
      // 1c0: aload 12
      // 1c2: checkcast kotlin/coroutines/Continuation
      // 1c5: astore 1
      // 1c6: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 1c9: astore 3
      // 1ca: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1cd: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1d0: astore 3
      // 1d1: goto 151
      // 1d4: aload 12
      // 1d6: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
      // 1d9: astore 1
      // 1da: aload 1
      // 1db: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 1de: if_acmpne 1e6
      // 1e1: aload 6
      // 1e3: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
      // 1e6: aload 1
      // 1e7: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 1ea: if_acmpne 1ef
      // 1ed: aload 1
      // 1ee: areturn
      // 1ef: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 1f2: areturn
      // 1f3: astore 1
      // 1f4: aload 12
      // 1f6: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.releaseClaimedReusableContinuation$kotlinx_coroutines_core ()V
      // 1f9: aload 1
      // 1fa: athrow
   }

   private fun shouldSendSuspend(curSendersAndCloseStatus: Long): Boolean {
      return !this.isClosedForSend0(var1) && this.bufferOrRendezvousSend(var1 and 1152921504606846975L) xor true;
   }

   private fun Any.tryResumeReceiver(element: Any): Boolean {
      val var3: Boolean;
      if (var1 is SelectInstance) {
         var3 = (var1 as SelectInstance).trySelect(this, var2);
      } else if (var1 is ReceiveCatching) {
         val var8: ReceiveCatching = var1 as ReceiveCatching;
         val var6: CancellableContinuation = (var1 as ReceiveCatching).cont;
         val var14: ChannelResult = ChannelResult.box-impl(ChannelResult.Companion.success-JP2dKIU(var2));
         var1 = null;
         if (this.onUndeliveredElement != null) {
            var1 = OnUndeliveredElementKt.bindCancellationFun(this.onUndeliveredElement, (E)var2, var8.cont.getContext());
         }

         var3 = BufferedChannelKt.access$tryResume0(var6, var14, var1);
      } else if (var1 is BufferedChannel.BufferedChannelIterator) {
         var3 = (var1 as BufferedChannel.BufferedChannelIterator).tryResumeHasNext((E)var2);
      } else {
         if (var1 !is CancellableContinuation) {
            var2 = new StringBuilder("Unexpected receiver type: ");
            var2.append(var1);
            throw new IllegalStateException(var2.toString().toString());
         }

         val var13: CancellableContinuation = var1 as CancellableContinuation;
         var1 = null;
         if (this.onUndeliveredElement != null) {
            var1 = OnUndeliveredElementKt.bindCancellationFun(this.onUndeliveredElement, (E)var2, var13.getContext());
         }

         var3 = BufferedChannelKt.access$tryResume0(var13, var2, var1);
      }

      return var3;
   }

   private fun Any.tryResumeSender(segment: ChannelSegment<Any>, index: Int): Boolean {
      val var4: Boolean;
      if (var1 is CancellableContinuation) {
         var4 = BufferedChannelKt.tryResume0$default(var1 as CancellableContinuation, Unit.INSTANCE, null, 2, null);
      } else if (var1 is SelectInstance) {
         var1 = (var1 as SelectImplementation).trySelectDetailed(this, Unit.INSTANCE);
         if (var1 === TrySelectDetailedResult.REREGISTER) {
            var2.cleanElement$kotlinx_coroutines_core(var3);
         }

         if (var1 === TrySelectDetailedResult.SUCCESSFUL) {
            var4 = true;
         } else {
            var4 = false;
         }
      } else {
         if (var1 !is BufferedChannel.SendBroadcast) {
            val var6: StringBuilder = new StringBuilder("Unexpected waiter: ");
            var6.append(var1);
            throw new IllegalStateException(var6.toString().toString());
         }

         var4 = BufferedChannelKt.tryResume0$default((var1 as BufferedChannel.SendBroadcast).getCont(), true, null, 2, null);
      }

      return var4;
   }

   fun `update$atomicfu`(var1: AtomicLongFieldUpdater, var2: (java.lang.Long?) -> java.lang.Long, var3: Any) {
      val var4: Long;
      do {
         var4 = var1.get(var3);
      } while (!var1.compareAndSet(var3, var4, ((java.lang.Number)var2.invoke(var4)).longValue()));
   }

   private fun updateCellExpandBuffer(segment: ChannelSegment<Any>, index: Int, b: Long): Boolean {
      val var6: Any = var1.getState$kotlinx_coroutines_core(var2);
      if (var6 is Waiter && var3 >= receivers$FU.get(this) && var1.casState$kotlinx_coroutines_core(var2, var6, BufferedChannelKt.access$getRESUMING_BY_EB$p())
         )
       {
         val var5: Boolean;
         if (this.tryResumeSender(var6, var1, var2)) {
            var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.BUFFERED);
            var5 = true;
         } else {
            var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getINTERRUPTED_SEND$p());
            var1.onCancelledRequest(var2, false);
            var5 = false;
         }

         return var5;
      } else {
         return this.updateCellExpandBufferSlow(var1, var2, var3);
      }
   }

   private fun updateCellExpandBufferSlow(segment: ChannelSegment<Any>, index: Int, b: Long): Boolean {
      while (true) {
         val var7: Any = var1.getState$kotlinx_coroutines_core(var2);
         val var6: Boolean = var7 is Waiter;
         var var5: Boolean = false;
         if (var6) {
            if (var3 < receivers$FU.get(this)) {
               if (var1.casState$kotlinx_coroutines_core(var2, var7, new WaiterEB(var7 as Waiter))) {
                  return true;
               }
            } else if (var1.casState$kotlinx_coroutines_core(var2, var7, BufferedChannelKt.access$getRESUMING_BY_EB$p())) {
               if (this.tryResumeSender(var7, var1, var2)) {
                  var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.BUFFERED);
                  var5 = true;
               } else {
                  var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getINTERRUPTED_SEND$p());
                  var1.onCancelledRequest(var2, false);
               }

               return var5;
            }
         } else {
            if (var7 === BufferedChannelKt.access$getINTERRUPTED_SEND$p()) {
               return false;
            }

            if (var7 == null) {
               if (var1.casState$kotlinx_coroutines_core(var2, var7, BufferedChannelKt.access$getIN_BUFFER$p())) {
                  return true;
               }
            } else {
               if (var7 === BufferedChannelKt.BUFFERED) {
                  return true;
               }

               if (var7 != BufferedChannelKt.access$getPOISONED$p()
                  && var7 != BufferedChannelKt.access$getDONE_RCV$p()
                  && var7 != BufferedChannelKt.access$getINTERRUPTED_RCV$p()) {
                  if (var7 === BufferedChannelKt.getCHANNEL_CLOSED()) {
                     return true;
                  }

                  if (var7 === BufferedChannelKt.access$getRESUMING_BY_RCV$p()) {
                     continue;
                  }

                  val var8: StringBuilder = new StringBuilder("Unexpected cell state: ");
                  var8.append(var7);
                  throw new IllegalStateException(var8.toString().toString());
               }

               return true;
            }
         }
      }
   }

   private fun updateCellReceive(segment: ChannelSegment<Any>, index: Int, r: Long, waiter: Any?): Any? {
      val var6: Any = var1.getState$kotlinx_coroutines_core(var2);
      if (var6 == null) {
         if (var3 >= (sendersAndCloseStatus$FU.get(this) and 1152921504606846975L)) {
            if (var5 == null) {
               return BufferedChannelKt.access$getSUSPEND_NO_WAITER$p();
            }

            if (var1.casState$kotlinx_coroutines_core(var2, var6, var5)) {
               this.expandBuffer();
               return BufferedChannelKt.access$getSUSPEND$p();
            }
         }
      } else if (var6 === BufferedChannelKt.BUFFERED && var1.casState$kotlinx_coroutines_core(var2, var6, BufferedChannelKt.access$getDONE_RCV$p())) {
         this.expandBuffer();
         return var1.retrieveElement$kotlinx_coroutines_core(var2);
      }

      return this.updateCellReceiveSlow(var1, var2, var3, var5);
   }

   private fun updateCellReceiveSlow(segment: ChannelSegment<Any>, index: Int, r: Long, waiter: Any?): Any? {
      while (true) {
         val var7: Any = var1.getState$kotlinx_coroutines_core(var2);
         if (var7 != null && var7 != BufferedChannelKt.access$getIN_BUFFER$p()) {
            if (var7 === BufferedChannelKt.BUFFERED) {
               if (var1.casState$kotlinx_coroutines_core(var2, var7, BufferedChannelKt.access$getDONE_RCV$p())) {
                  this.expandBuffer();
                  return var1.retrieveElement$kotlinx_coroutines_core(var2);
               }
            } else {
               if (var7 === BufferedChannelKt.access$getINTERRUPTED_SEND$p()) {
                  return BufferedChannelKt.access$getFAILED$p();
               }

               if (var7 === BufferedChannelKt.access$getPOISONED$p()) {
                  return BufferedChannelKt.access$getFAILED$p();
               }

               if (var7 === BufferedChannelKt.getCHANNEL_CLOSED()) {
                  this.expandBuffer();
                  return BufferedChannelKt.access$getFAILED$p();
               }

               if (var7 != BufferedChannelKt.access$getRESUMING_BY_EB$p()
                  && var1.casState$kotlinx_coroutines_core(var2, var7, BufferedChannelKt.access$getRESUMING_BY_RCV$p())) {
                  val var6: Boolean = var7 is WaiterEB;
                  var5 = var7;
                  if (var6) {
                     var5 = (var7 as WaiterEB).waiter;
                  }

                  val var8: Any;
                  if (this.tryResumeSender(var5, var1, var2)) {
                     var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getDONE_RCV$p());
                     this.expandBuffer();
                     var8 = var1.retrieveElement$kotlinx_coroutines_core(var2);
                  } else {
                     var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getINTERRUPTED_SEND$p());
                     var1.onCancelledRequest(var2, false);
                     if (var6) {
                        this.expandBuffer();
                     }

                     var8 = BufferedChannelKt.access$getFAILED$p();
                  }

                  return var8;
               }
            }
         } else if (var3 < (sendersAndCloseStatus$FU.get(this) and 1152921504606846975L)) {
            if (var1.casState$kotlinx_coroutines_core(var2, var7, BufferedChannelKt.access$getPOISONED$p())) {
               this.expandBuffer();
               return BufferedChannelKt.access$getFAILED$p();
            }
         } else {
            if (var5 == null) {
               return BufferedChannelKt.access$getSUSPEND_NO_WAITER$p();
            }

            if (var1.casState$kotlinx_coroutines_core(var2, var7, var5)) {
               this.expandBuffer();
               return BufferedChannelKt.access$getSUSPEND$p();
            }
         }
      }
   }

   private fun updateCellSend(segment: ChannelSegment<Any>, index: Int, element: Any, s: Long, waiter: Any?, closed: Boolean): Int {
      var1.storeElement$kotlinx_coroutines_core(var2, var3);
      if (var7) {
         return this.updateCellSendSlow(var1, var2, (E)var3, var4, var6, var7);
      } else {
         val var8: Any = var1.getState$kotlinx_coroutines_core(var2);
         if (var8 == null) {
            if (this.bufferOrRendezvousSend(var4)) {
               if (var1.casState$kotlinx_coroutines_core(var2, null, BufferedChannelKt.BUFFERED)) {
                  return 1;
               }
            } else {
               if (var6 == null) {
                  return 3;
               }

               if (var1.casState$kotlinx_coroutines_core(var2, null, var6)) {
                  return 2;
               }
            }
         } else if (var8 is Waiter) {
            var1.cleanElement$kotlinx_coroutines_core(var2);
            val var9: Byte;
            if (this.tryResumeReceiver(var8, (E)var3)) {
               var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getDONE_RCV$p());
               this.onReceiveDequeued();
               var9 = 0;
            } else {
               if (var1.getAndSetState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getINTERRUPTED_RCV$p())
                  != BufferedChannelKt.access$getINTERRUPTED_RCV$p()) {
                  var1.onCancelledRequest(var2, true);
               }

               var9 = 5;
            }

            return var9;
         }

         return this.updateCellSendSlow(var1, var2, (E)var3, var4, var6, var7);
      }
   }

   private fun updateCellSendSlow(segment: ChannelSegment<Any>, index: Int, element: Any, s: Long, waiter: Any?, closed: Boolean): Int {
      while (true) {
         val var9: Any = var1.getState$kotlinx_coroutines_core(var2);
         if (var9 == null) {
            if (this.bufferOrRendezvousSend(var4) && !var7) {
               if (var1.casState$kotlinx_coroutines_core(var2, null, BufferedChannelKt.BUFFERED)) {
                  return 1;
               }
            } else if (var7) {
               if (var1.casState$kotlinx_coroutines_core(var2, null, BufferedChannelKt.access$getINTERRUPTED_SEND$p())) {
                  var1.onCancelledRequest(var2, false);
                  return 4;
               }
            } else {
               if (var6 == null) {
                  return 3;
               }

               if (var1.casState$kotlinx_coroutines_core(var2, null, var6)) {
                  return 2;
               }
            }
         } else {
            if (var9 === BufferedChannelKt.access$getIN_BUFFER$p()) {
               if (!var1.casState$kotlinx_coroutines_core(var2, var9, BufferedChannelKt.BUFFERED)) {
                  continue;
               }

               return 1;
            }

            if (var9 === BufferedChannelKt.access$getINTERRUPTED_RCV$p()) {
               var1.cleanElement$kotlinx_coroutines_core(var2);
               return 5;
            }

            if (var9 === BufferedChannelKt.access$getPOISONED$p()) {
               var1.cleanElement$kotlinx_coroutines_core(var2);
               return 5;
            }

            if (var9 === BufferedChannelKt.getCHANNEL_CLOSED()) {
               var1.cleanElement$kotlinx_coroutines_core(var2);
               this.completeCloseOrCancel();
               return 4;
            }

            if (DebugKt.getASSERTIONS_ENABLED() && var9 !is Waiter && var9 !is WaiterEB) {
               throw new AssertionError();
            }

            var1.cleanElement$kotlinx_coroutines_core(var2);
            var6 = var9;
            if (var9 is WaiterEB) {
               var6 = (var9 as WaiterEB).waiter;
            }

            val var10: Byte;
            if (this.tryResumeReceiver(var6, (E)var3)) {
               var1.setState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getDONE_RCV$p());
               this.onReceiveDequeued();
               var10 = 0;
            } else {
               if (var1.getAndSetState$kotlinx_coroutines_core(var2, BufferedChannelKt.access$getINTERRUPTED_RCV$p())
                  != BufferedChannelKt.access$getINTERRUPTED_RCV$p()) {
                  var1.onCancelledRequest(var2, true);
               }

               var10 = 5;
            }

            return var10;
         }
      }
   }

   private fun updateReceiversCounterIfLower(value: Long) {
      val var5: AtomicLongFieldUpdater = receivers$FU;

      val var3: Long;
      do {
         var3 = var5.get(this);
         if (var3 >= var1) {
            return;
         }
      } while (!receivers$FU.compareAndSet(this, var3, var1));
   }

   private fun updateSendersCounterIfLower(value: Long) {
      val var7: AtomicLongFieldUpdater = sendersAndCloseStatus$FU;

      val var3: Long;
      val var5: Long;
      do {
         var3 = var7.get(this);
         var5 = 1152921504606846975L and var3;
         if ((1152921504606846975L and var3) >= var1) {
            return;
         }
      } while (!sendersAndCloseStatus$FU.compareAndSet(this, var3, BufferedChannelKt.access$constructSendersAndCloseStatus(var5, (int)(var3 >> 60))));
   }

   public override fun cancel() {
      this.cancelImpl$kotlinx_coroutines_core(null);
   }

   public override fun cancel(cause: CancellationException?) {
      this.cancelImpl$kotlinx_coroutines_core(var1);
   }

   public override fun cancel(cause: Throwable?): Boolean {
      return this.cancelImpl$kotlinx_coroutines_core(var1);
   }

   internal open fun cancelImpl(cause: Throwable?): Boolean {
      var var2: java.lang.Throwable = var1;
      if (var1 == null) {
         var2 = new CancellationException("Channel was cancelled");
      }

      return this.closeOrCancelImpl(var2, true);
   }

   public fun checkSegmentStructureInvariants() {
      if (this.isRendezvousOrUnlimited()) {
         if (bufferEndSegment$FU.get(this) != BufferedChannelKt.access$getNULL_SEGMENT$p()) {
            val var12: StringBuilder = new StringBuilder(
               "bufferEndSegment must be NULL_SEGMENT for rendezvous and unlimited channels; they do not manipulate it.\nChannel state: "
            );
            var12.append(this);
            throw new IllegalStateException(var12.toString().toString());
         }
      } else if ((receiveSegment$FU.get(this) as ChannelSegment).id > (bufferEndSegment$FU.get(this) as ChannelSegment).id) {
         val var28: StringBuilder = new StringBuilder("bufferEndSegment should not have lower id than receiveSegment.\nChannel state: ");
         var28.append(this);
         throw new IllegalStateException(var28.toString().toString());
      }

      var var13: java.lang.Iterable = CollectionsKt.listOf(
         new ChannelSegment[]{
            (ChannelSegment)receiveSegment$FU.get(this), (ChannelSegment)sendSegment$FU.get(this), (ChannelSegment)bufferEndSegment$FU.get(this)
         }
      );
      val var20: java.util.Collection = new ArrayList();

      for (var13 : var13) {
         if (var13 as ChannelSegment != BufferedChannelKt.access$getNULL_SEGMENT$p()) {
            var20.add(var13);
         }
      }

      val var15: java.util.Iterator = (var20 as java.util.List).iterator();
      if (!var15.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var13 = (java.lang.Iterable)var15.next();
         var var21: Any;
         if (!var15.hasNext()) {
            var21 = var13;
         } else {
            var var8: Long = (var13 as ChannelSegment).id;

            do {
               val var34: Any = var15.next();
               var21 = var13;
               var var6: Long = var8;
               if (var8 > (var34 as ChannelSegment).id) {
                  var21 = (ChannelSegment)var34;
                  var6 = (var34 as ChannelSegment).id;
               }

               var13 = var21;
               var8 = var6;
            } while (var15.hasNext());
         }

         var21 = var21;
         if (var21.getPrev() != null) {
            val var27: StringBuilder = new StringBuilder(
               "All processed segments should be unreachable from the data structure, but the `prev` link of the leftmost segment is non-null.\nChannel state: "
            );
            var27.append(this);
            throw new IllegalStateException(var27.toString().toString());
         } else {
            while (var21.getNext() != null) {
               val var31: ConcurrentLinkedListNode = var21.getNext();
               if ((var31 as ChannelSegment).getPrev() != null) {
                  val var32: ConcurrentLinkedListNode = var21.getNext();
                  if ((var32 as ChannelSegment).getPrev() != var21) {
                     val var26: StringBuilder = new StringBuilder("The `segment.next.prev === segment` invariant is violated.\nChannel state: ");
                     var26.append(this);
                     throw new IllegalStateException(var26.toString().toString());
                  }
               }

               val var4: Int = BufferedChannelKt.SEGMENT_SIZE;
               var var2: Int = 0;
               var var1: Int = 0;

               while (var2 < var4) {
                  var13 = (java.lang.Iterable)var21.getState$kotlinx_coroutines_core(var2);
                  var var3: Int = var1;
                  if (!(var13 == BufferedChannelKt.BUFFERED)) {
                     var3 = var1;
                     if (var13 !is Waiter) {
                        var var5: Boolean;
                        if (var13 == BufferedChannelKt.access$getINTERRUPTED_RCV$p()) {
                           var5 = true;
                        } else {
                           var5 = var13 == BufferedChannelKt.access$getINTERRUPTED_SEND$p();
                        }

                        if (var5) {
                           var5 = true;
                        } else {
                           var5 = var13 == BufferedChannelKt.getCHANNEL_CLOSED();
                        }

                        if (var5) {
                           val var16: Boolean;
                           if (var21.getElement$kotlinx_coroutines_core(var2) == null) {
                              var16 = true;
                           } else {
                              var16 = false;
                           }

                           if (!var16) {
                              throw new IllegalStateException("Check failed.".toString());
                           }

                           var3 = var1 + 1;
                        } else {
                           if (var13 == BufferedChannelKt.access$getPOISONED$p()) {
                              var5 = true;
                           } else {
                              var5 = var13 == BufferedChannelKt.access$getDONE_RCV$p();
                           }

                           if (!var5) {
                              val var23: StringBuilder = new StringBuilder("Unexpected segment cell state: ");
                              var23.append(var13);
                              var23.append(".\nChannel state: ");
                              var23.append(this);
                              throw new IllegalStateException(var23.toString().toString());
                           }

                           val var17: Boolean;
                           if (var21.getElement$kotlinx_coroutines_core(var2) == null) {
                              var17 = true;
                           } else {
                              var17 = false;
                           }

                           if (!var17) {
                              throw new IllegalStateException("Check failed.".toString());
                           }

                           var3 = var1;
                        }
                     }
                  }

                  var2++;
                  var1 = var3;
               }

               if (var1 == BufferedChannelKt.SEGMENT_SIZE
                  && var21 != receiveSegment$FU.get(this)
                  && var21 != sendSegment$FU.get(this)
                  && var21 != bufferEndSegment$FU.get(this)) {
                  val var25: StringBuilder = new StringBuilder("Logically removed segment is reachable.\nChannel state: ");
                  var25.append(this);
                  throw new IllegalStateException(var25.toString().toString());
               }

               val var24: ConcurrentLinkedListNode = var21.getNext();
               var21 = var24 as ChannelSegment;
            }
         }
      }
   }

   public override fun close(cause: Throwable?): Boolean {
      return this.closeOrCancelImpl(var1, false);
   }

   protected open fun closeOrCancelImpl(cause: Throwable?, cancel: Boolean): Boolean {
      if (var2) {
         this.markCancellationStarted();
      }

      val var3: Boolean = ExternalSyntheticBackportWithForwarding0.m(_closeCause$FU, this, BufferedChannelKt.access$getNO_CLOSE_CAUSE$p(), var1);
      if (var2) {
         this.markCancelled();
      } else {
         this.markClosed();
      }

      this.completeCloseOrCancel();
      this.onClosedIdempotent();
      if (var3) {
         this.invokeCloseHandler();
      }

      return var3;
   }

   protected fun dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(globalCellIndex: Long) {
      if (DebugKt.getASSERTIONS_ENABLED() && !this.isConflatedDropOldest()) {
         throw new AssertionError();
      } else {
         var var8: ChannelSegment = receiveSegment$FU.get(this) as ChannelSegment;

         while (true) {
            val var9: AtomicLongFieldUpdater = receivers$FU;
            val var6: Long = receivers$FU.get(this);
            if (var1 < Math.max((long)this.capacity + var6, this.getBufferEndCounter())) {
               return;
            }

            if (var9.compareAndSet(this, var6, var6 + 1L)) {
               val var4: Long = var6 / BufferedChannelKt.SEGMENT_SIZE;
               val var3: Int = (int)(var6 % BufferedChannelKt.SEGMENT_SIZE);
               var var13: ChannelSegment = var8;
               if (var8.id != var4) {
                  var13 = this.findSegmentReceive(var4, var8);
                  if (var13 == null) {
                     continue;
                  }
               }

               val var10: Any = this.updateCellReceive(var13, var3, var6, null);
               if (var10 === BufferedChannelKt.access$getFAILED$p()) {
                  var8 = var13;
                  if (var6 < this.getSendersCounter$kotlinx_coroutines_core()) {
                     var13.cleanPrev();
                     var8 = var13;
                  }
               } else {
                  var13.cleanPrev();
                  var8 = var13;
                  if (this.onUndeliveredElement != null) {
                     val var12: UndeliveredElementException = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(
                        this.onUndeliveredElement, var10, null, 2, null
                     );
                     if (var12 != null) {
                        throw var12;
                     }

                     var8 = var13;
                  }
               }
            }
         }
      }
   }

   internal fun hasElements(): Boolean {
      while (true) {
         val var7: AtomicReferenceFieldUpdater = receiveSegment$FU;
         var var6: ChannelSegment = receiveSegment$FU.get(this) as ChannelSegment;
         val var1: Long = this.getReceiversCounter$kotlinx_coroutines_core();
         if (this.getSendersCounter$kotlinx_coroutines_core() <= var1) {
            return false;
         }

         val var3: Long = var1 / BufferedChannelKt.SEGMENT_SIZE;
         var var5: ChannelSegment = var6;
         if (var6.id != var3) {
            var6 = this.findSegmentReceive(var3, var6);
            var5 = var6;
            if (var6 == null) {
               if ((var7.get(this) as ChannelSegment).id >= var3) {
                  continue;
               }

               return false;
            }
         }

         var5.cleanPrev();
         if (this.isCellNonEmpty(var5, (int)(var1 % (long)BufferedChannelKt.SEGMENT_SIZE), var1)) {
            return true;
         }

         receivers$FU.compareAndSet(this, var1, var1 + 1L);
      }
   }

   public override fun invokeOnClose(handler: (Throwable?) -> Unit) {
      val var3: AtomicReferenceFieldUpdater = closeHandler$FU;
      if (!ExternalSyntheticBackportWithForwarding0.m(closeHandler$FU, this, null, var1)) {
         do {
            val var2: Any = var3.get(this);
            if (var2 != BufferedChannelKt.access$getCLOSE_HANDLER_CLOSED$p()) {
               if (var2 === BufferedChannelKt.access$getCLOSE_HANDLER_INVOKED$p()) {
                  throw new IllegalStateException("Another handler was already registered and successfully invoked".toString());
               }

               val var4: StringBuilder = new StringBuilder("Another handler is already registered: ");
               var4.append(var2);
               throw new IllegalStateException(var4.toString().toString());
            }
         } while (
            !ExternalSyntheticBackportWithForwarding0.m(
               closeHandler$FU, this, BufferedChannelKt.access$getCLOSE_HANDLER_CLOSED$p(), BufferedChannelKt.access$getCLOSE_HANDLER_INVOKED$p()
            )
         );

         var1.invoke(this.getCloseCause());
      }
   }

   public override operator fun iterator(): ChannelIterator<Any> {
      return new BufferedChannel.BufferedChannelIterator(this);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
   override fun offer(var1: E): Boolean {
      return Channel.DefaultImpls.offer(this, (E)var1);
   }

   protected open fun onClosedIdempotent() {
   }

   protected open fun onReceiveDequeued() {
   }

   protected open fun onReceiveEnqueued() {
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = []))
   override fun poll(): E {
      return Channel.DefaultImpls.poll(this);
   }

   public override suspend fun receive(): Any {
      return receive$suspendImpl(this, var1);
   }

   public override suspend fun receiveCatching(): ChannelResult<Any> {
      return receiveCatching-JP2dKIU$suspendImpl(this, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = []))
   override fun receiveOrNull(var1: Continuation<? super E>): Any {
      return Channel.DefaultImpls.receiveOrNull(this, var1);
   }

   protected open fun registerSelectForSend(select: SelectInstance<*>, element: Any?) {
      var var11: ChannelSegment = access$getSendSegment$FU$p().get(this) as ChannelSegment;

      while (true) {
         var var8: Long = access$getSendersAndCloseStatus$FU$p().getAndIncrement(this);
         val var6: Long = 1152921504606846975L and var8;
         val var5: Boolean = access$isClosedForSend0(this, var8);
         var8 = var6 / BufferedChannelKt.SEGMENT_SIZE;
         val var4: Int = (int)(var6 % BufferedChannelKt.SEGMENT_SIZE);
         var var10: ChannelSegment = var11;
         if (var11.id != var8) {
            var10 = access$findSegmentSend(this, var8, var11);
            if (var10 == null) {
               if (!var5) {
                  continue;
               }
               break;
            }
         }

         val var3: Int = access$updateCellSend(this, var10, var4, var2, var6, var1, var5);
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 == 3) {
                     throw new IllegalStateException("unexpected".toString());
                  }

                  if (var3 != 4) {
                     if (var3 != 5) {
                        var11 = var10;
                     } else {
                        var10.cleanPrev();
                        var11 = var10;
                     }
                     continue;
                  }

                  if (var6 < this.getReceiversCounter$kotlinx_coroutines_core()) {
                     var10.cleanPrev();
                  }
               } else {
                  if (!var5) {
                     val var12: Waiter;
                     if (var1 is Waiter) {
                        var12 = var1 as Waiter;
                     } else {
                        var12 = null;
                     }

                     if (var12 != null) {
                        access$prepareSenderForSuspension(this, var12, var10, var4);
                     }

                     return;
                  }

                  var10.onSlotCleaned();
               }
               break;
            }
         } else {
            var10.cleanPrev();
         }

         var1.selectInRegistrationPhase(Unit.INSTANCE);
         return;
      }

      this.onClosedSelectOnSend((E)var2, var1);
   }

   public override suspend fun send(element: Any) {
      return send$suspendImpl(this, (E)var1, var2);
   }

   internal open suspend fun sendBroadcast(element: Any): Boolean {
      return sendBroadcast$suspendImpl(this, (E)var1, var2);
   }

   protected inline fun <R> sendImpl(
      element: Any,
      waiter: Any?,
      onRendezvousOrBuffered: () -> R,
      onSuspend: (ChannelSegment<Any>, Int) -> R,
      onClosed: () -> R,
      onNoWaiterSuspend: (ChannelSegment<Any>, Int, Any, Long) -> R = <unrepresentable>.INSTANCE as Function4
   ): R {
      var var14: ChannelSegment = access$getSendSegment$FU$p().get(this) as ChannelSegment;

      while (true) {
         var var11: Long = access$getSendersAndCloseStatus$FU$p().getAndIncrement(this);
         val var9: Long = var11 and 1152921504606846975L;
         val var13: Boolean = access$isClosedForSend0(this, var11);
         var11 = var9 / BufferedChannelKt.SEGMENT_SIZE;
         val var7: Int = (int)(var9 % BufferedChannelKt.SEGMENT_SIZE);
         if (var14.id != var11) {
            val var15: ChannelSegment = access$findSegmentSend(this, var11, var14);
            if (var15 == null) {
               if (var13) {
                  return (R)var5.invoke();
               }
               continue;
            }

            var14 = var15;
         }

         val var8: Int = access$updateCellSend(this, var14, var7, var1, var9, var2, var13);
         if (var8 == 0) {
            var14.cleanPrev();
            return (R)var3.invoke();
         }

         if (var8 == 1) {
            return (R)var3.invoke();
         }

         if (var8 == 2) {
            if (var13) {
               var14.onSlotCleaned();
               return (R)var5.invoke();
            }

            if (var2 is Waiter) {
               var1 = var2 as Waiter;
            } else {
               var1 = null;
            }

            if (var1 != null) {
               access$prepareSenderForSuspension(this, var1, var14, var7);
            }

            return (R)var4.invoke(var14, var7);
         }

         if (var8 == 3) {
            return (R)var6.invoke(var14, var7, var1, var9);
         }

         if (var8 == 4) {
            if (var9 < this.getReceiversCounter$kotlinx_coroutines_core()) {
               var14.cleanPrev();
            }

            return (R)var5.invoke();
         }

         if (var8 == 5) {
            var14.cleanPrev();
         }
      }
   }

   internal open fun shouldSendSuspend(): Boolean {
      return this.shouldSendSuspend(sendersAndCloseStatus$FU.get(this));
   }

   public override fun toString(): String {
      val var15: StringBuilder = new StringBuilder();
      val var1: Int = (int)(sendersAndCloseStatus$FU.get(this) shr 60);
      if (var1 != 2) {
         if (var1 == 3) {
            var15.append("cancelled,");
         }
      } else {
         var15.append("closed,");
      }

      val var12: StringBuilder = new StringBuilder("capacity=");
      var12.append(this.capacity);
      var12.append(',');
      var15.append(var12.toString());
      var15.append("data=[");
      var var13: java.lang.Iterable = CollectionsKt.listOf(
         new ChannelSegment[]{
            (ChannelSegment)receiveSegment$FU.get(this), (ChannelSegment)sendSegment$FU.get(this), (ChannelSegment)bufferEndSegment$FU.get(this)
         }
      );
      val var28: java.util.Collection = new ArrayList();

      for (Object var14 : var13) {
         if (var14 as ChannelSegment != BufferedChannelKt.access$getNULL_SEGMENT$p()) {
            var28.add(var14);
         }
      }

      val var16: java.util.Iterator = (var28 as java.util.List).iterator();
      if (!var16.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var13 = (java.lang.Iterable)var16.next();
         var var29: Any;
         if (!var16.hasNext()) {
            var29 = var13;
         } else {
            var var6: Long = (var13 as ChannelSegment).id;

            do {
               val var35: Any = var16.next();
               var29 = var13;
               var var8: Long = var6;
               if (var6 > (var35 as ChannelSegment).id) {
                  var29 = (StringBuilder)var35;
                  var8 = (var35 as ChannelSegment).id;
               }

               var13 = var29;
               var6 = var8;
            } while (var16.hasNext());
         }

         var var34: ChannelSegment = var29 as ChannelSegment;
         val var26: Long = this.getReceiversCounter$kotlinx_coroutines_core();
         val var27: Long = this.getSendersCounter$kotlinx_coroutines_core();

         label142:
         do {
            val var2: Int = BufferedChannelKt.SEGMENT_SIZE;

            for (int var17 = 0; var17 < var2; var17++) {
               val var25: Long = var34.id * BufferedChannelKt.SEGMENT_SIZE + var17;
               var var40: Long;
               val var3: Byte = (byte)(if ((var40 = var34.id * BufferedChannelKt.SEGMENT_SIZE + var17 - var27) == 0L) 0 else (if (var40 < 0L) -1 else 1));
               if (var34.id * BufferedChannelKt.SEGMENT_SIZE + var17 >= var27 && var25 >= var26) {
                  break label142;
               }

               var var38: StringBuilder = (StringBuilder)var34.getState$kotlinx_coroutines_core(var17);
               var var36: StringBuilder = (StringBuilder)var34.getElement$kotlinx_coroutines_core(var17);
               if (var38 is CancellableContinuation) {
                  val var4: Byte = (byte)(if ((var40 = var25 - var26) == 0L) 0 else (if (var40 < 0L) -1 else 1));
                  if (var25 < var26 && var3 >= 0) {
                     var29 = "receive";
                  } else if (var3 < 0 && var4 >= 0) {
                     var29 = "send";
                  } else {
                     var29 = "cont";
                  }
               } else if (var38 is SelectInstance) {
                  val var18: Byte = (byte)(if ((var40 = var25 - var26) == 0L) 0 else (if (var40 < 0L) -1 else 1));
                  if (var25 < var26 && var3 >= 0) {
                     var29 = "onReceive";
                  } else if (var3 < 0 && var18 >= 0) {
                     var29 = "onSend";
                  } else {
                     var29 = "select";
                  }
               } else if (var38 is ReceiveCatching) {
                  var29 = "receiveCatching";
               } else if (var38 is BufferedChannel.SendBroadcast) {
                  var29 = "sendBroadcast";
               } else if (var38 is WaiterEB) {
                  var29 = new StringBuilder("EB(");
                  var29.append((Object)var38);
                  var29.append(')');
                  var29 = var29.toString();
               } else {
                  var var5: Boolean;
                  if (var38 == BufferedChannelKt.access$getRESUMING_BY_RCV$p()) {
                     var5 = true;
                  } else {
                     var5 = var38 == BufferedChannelKt.access$getRESUMING_BY_EB$p();
                  }

                  if (var5) {
                     var29 = "resuming_sender";
                  } else {
                     if (var38 == null) {
                        var5 = true;
                     } else {
                        var5 = var38 == BufferedChannelKt.access$getIN_BUFFER$p();
                     }

                     if (var5) {
                        var5 = true;
                     } else {
                        var5 = var38 == BufferedChannelKt.access$getDONE_RCV$p();
                     }

                     if (var5) {
                        var5 = true;
                     } else {
                        var5 = var38 == BufferedChannelKt.access$getPOISONED$p();
                     }

                     if (var5) {
                        var5 = true;
                     } else {
                        var5 = var38 == BufferedChannelKt.access$getINTERRUPTED_RCV$p();
                     }

                     if (var5) {
                        var5 = true;
                     } else {
                        var5 = var38 == BufferedChannelKt.access$getINTERRUPTED_SEND$p();
                     }

                     if (var5) {
                        var5 = true;
                     } else {
                        var5 = var38 == BufferedChannelKt.getCHANNEL_CLOSED();
                     }

                     if (var5) {
                        continue;
                     }

                     var29 = var38.toString();
                  }
               }

               if (var36 != null) {
                  var38 = new StringBuilder("(");
                  var38.append((java.lang.String)var29);
                  var38.append(',');
                  var38.append((Object)var36);
                  var38.append("),");
                  var15.append(var38.toString());
               } else {
                  var36 = new StringBuilder();
                  var36.append((java.lang.String)var29);
                  var36.append(',');
                  var15.append(var36.toString());
               }
            }

            var34 = var34.getNext();
         } while (var34 != null);

         if (StringsKt.last(var15) == ',') {
            ;
         }

         var15.append("]");
         return var15.toString();
      }
   }

   internal fun toStringDebug(): String {
      val var12: StringBuilder = new StringBuilder();
      val var10: StringBuilder = new StringBuilder("S=");
      var10.append(this.getSendersCounter$kotlinx_coroutines_core());
      var10.append(",R=");
      var10.append(this.getReceiversCounter$kotlinx_coroutines_core());
      var10.append(",B=");
      var10.append(this.getBufferEndCounter());
      var10.append(",B'=");
      var10.append(completedExpandBuffersAndPauseFlag$FU.get(this));
      var10.append(",C=");
      val var9: AtomicLongFieldUpdater = sendersAndCloseStatus$FU;
      var10.append((int)(sendersAndCloseStatus$FU.get(this) shr 60));
      var10.append(',');
      var12.append(var10.toString());
      val var1: Int = (int)(var9.get(this) shr 60);
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 == 3) {
               var12.append("CANCELLED,");
            }
         } else {
            var12.append("CLOSED,");
         }
      } else {
         var12.append("CANCELLATION_STARTED,");
      }

      var var11: StringBuilder = new StringBuilder("SEND_SEGM=");
      val var16: AtomicReferenceFieldUpdater = sendSegment$FU;
      var11.append(DebugStringsKt.getHexAddress(sendSegment$FU.get(this)));
      var11.append(",RCV_SEGM=");
      val var26: AtomicReferenceFieldUpdater = receiveSegment$FU;
      var11.append(DebugStringsKt.getHexAddress(receiveSegment$FU.get(this)));
      var12.append(var11.toString());
      if (!this.isRendezvousOrUnlimited()) {
         var11 = new StringBuilder(",EB_SEGM=");
         var11.append(DebugStringsKt.getHexAddress(bufferEndSegment$FU.get(this)));
         var12.append(var11.toString());
      }

      var12.append("  ");
      val var27: java.lang.Iterable = CollectionsKt.listOf(
         new ChannelSegment[]{(ChannelSegment)var26.get(this), (ChannelSegment)var16.get(this), (ChannelSegment)bufferEndSegment$FU.get(this)}
      );
      val var17: java.util.Collection = new ArrayList();

      for (Object var28 : var27) {
         if (var28 as ChannelSegment != BufferedChannelKt.access$getNULL_SEGMENT$p()) {
            var17.add(var28);
         }
      }

      val var13: java.util.Iterator = (var17 as java.util.List).iterator();
      if (!var13.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var29: ChannelSegment = (ChannelSegment)var13.next();
         var var18: Any;
         if (!var13.hasNext()) {
            var18 = var29;
         } else {
            var var5: Long = var29.id;

            do {
               val var33: Any = var13.next();
               var18 = var29;
               var var3: Long = var5;
               if (var5 > (var33 as ChannelSegment).id) {
                  var18 = (StringBuilder)var33;
                  var3 = (var33 as ChannelSegment).id;
               }

               var29 = var18;
               var5 = var3;
            } while (var13.hasNext());
         }

         var29 = var18 as ChannelSegment;

         do {
            val var35: StringBuilder = new StringBuilder();
            var35.append(DebugStringsKt.getHexAddress(var29));
            var35.append("=[");
            if (var29.isRemoved()) {
               var18 = "*";
            } else {
               var18 = "";
            }

            var35.append((java.lang.String)var18);
            var35.append(var29.id);
            var35.append(",prev=");
            var18 = var29.getPrev();
            if (var18 != null) {
               var18 = DebugStringsKt.getHexAddress(var18);
            } else {
               var18 = null;
            }

            var35.append((java.lang.String)var18);
            var35.append(',');
            var12.append(var35.toString());
            val var2: Int = BufferedChannelKt.SEGMENT_SIZE;

            for (int var15 = 0; var15 < var2; var15++) {
               var var14: StringBuilder = (StringBuilder)var29.getState$kotlinx_coroutines_core(var15);
               val var36: Any = var29.getElement$kotlinx_coroutines_core(var15);
               if (var14 is CancellableContinuation) {
                  var18 = "cont";
               } else if (var14 is SelectInstance) {
                  var18 = "select";
               } else if (var14 is ReceiveCatching) {
                  var18 = "receiveCatching";
               } else if (var14 is BufferedChannel.SendBroadcast) {
                  var18 = "send(broadcast)";
               } else if (var14 is WaiterEB) {
                  var18 = new StringBuilder("EB(");
                  var18.append((Object)var14);
                  var18.append(')');
                  var18 = var18.toString();
               } else {
                  var18 = java.lang.String.valueOf(var14);
               }

               var14 = new StringBuilder("[");
               var14.append(var15);
               var14.append("]=(");
               var14.append((java.lang.String)var18);
               var14.append(',');
               var14.append(var36);
               var14.append("),");
               var12.append(var14.toString());
            }

            val var37: StringBuilder = new StringBuilder("next=");
            val var39: ChannelSegment = var29.getNext();
            var var24: java.lang.String = null;
            if (var39 != null) {
               var24 = DebugStringsKt.getHexAddress(var39);
            }

            var37.append(var24);
            var37.append("]  ");
            var12.append(var37.toString());
            var18 = var29.getNext();
            var29 = (ChannelSegment)var18;
         } while (var18 != null);

         return var12.toString();
      }
   }

   public override fun tryReceive(): ChannelResult<Any> {
      var var2: Long = receivers$FU.get(this);
      var var4: Long = sendersAndCloseStatus$FU.get(this);
      if (this.isClosedForReceive0(var4)) {
         return ChannelResult.Companion.closed-JP2dKIU(this.getCloseCause());
      } else if (var2 >= (var4 and 1152921504606846975L)) {
         return ChannelResult.Companion.failure-PtdJZtk();
      } else {
         val var8: Symbol = BufferedChannelKt.access$getINTERRUPTED_RCV$p();
         var var6: ChannelSegment = access$getReceiveSegment$FU$p().get(this) as ChannelSegment;

         while (true) {
            if (this.isClosedForReceive()) {
               var11 = ChannelResult.Companion.closed-JP2dKIU(this.getCloseCause());
               break;
            }

            var4 = access$getReceivers$FU$p().getAndIncrement(this);
            var2 = var4 / BufferedChannelKt.SEGMENT_SIZE;
            val var1: Int = (int)(var4 % BufferedChannelKt.SEGMENT_SIZE);
            if (var6.id != var2) {
               val var7: ChannelSegment = access$findSegmentReceive(this, var2, var6);
               if (var7 == null) {
                  continue;
               }

               var6 = var7;
            }

            var var12: Any = access$updateCellReceive(this, var6, var1, var4, var8);
            if (var12 === BufferedChannelKt.access$getSUSPEND$p()) {
               if (var8 is Waiter) {
                  var12 = var8 as Waiter;
               } else {
                  var12 = null;
               }

               if (var12 != null) {
                  access$prepareReceiverForSuspension(this, (Waiter)var12, var6, var1);
               }

               this.waitExpandBufferCompletion$kotlinx_coroutines_core(var4);
               var6.onSlotCleaned();
               var11 = ChannelResult.Companion.failure-PtdJZtk();
               break;
            }

            if (var12 != BufferedChannelKt.access$getFAILED$p()) {
               if (var12 === BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()) {
                  throw new IllegalStateException("unexpected".toString());
               }

               var6.cleanPrev();
               var11 = ChannelResult.Companion.success-JP2dKIU(var12);
               break;
            }

            if (var4 < this.getSendersCounter$kotlinx_coroutines_core()) {
               var6.cleanPrev();
            }
         }

         return var11;
      }
   }

   public override fun trySend(element: Any): ChannelResult<Unit> {
      if (this.shouldSendSuspend(sendersAndCloseStatus$FU.get(this))) {
         return ChannelResult.Companion.failure-PtdJZtk();
      } else {
         val var11: Symbol = BufferedChannelKt.access$getINTERRUPTED_SEND$p();
         var var9: ChannelSegment = access$getSendSegment$FU$p().get(this) as ChannelSegment;

         while (true) {
            var var6: Long = access$getSendersAndCloseStatus$FU$p().getAndIncrement(this);
            val var4: Long = var6 and 1152921504606846975L;
            val var8: Boolean = access$isClosedForSend0(this, var6);
            var6 = var4 / BufferedChannelKt.SEGMENT_SIZE;
            val var2: Int = (int)(var4 % BufferedChannelKt.SEGMENT_SIZE);
            if (var9.id != var6) {
               val var10: ChannelSegment = access$findSegmentSend(this, var6, var9);
               if (var10 == null) {
                  if (!var8) {
                     continue;
                  }
                  break;
               }

               var9 = var10;
            }

            val var3: Int = access$updateCellSend(this, var9, var2, var1, var4, var11, var8);
            if (var3 != 0) {
               if (var3 != 1) {
                  if (var3 != 2) {
                     if (var3 == 3) {
                        throw new IllegalStateException("unexpected".toString());
                     }

                     if (var3 != 4) {
                        if (var3 == 5) {
                           var9.cleanPrev();
                        }
                        continue;
                     }

                     if (var4 < this.getReceiversCounter$kotlinx_coroutines_core()) {
                        var9.cleanPrev();
                     }
                  } else {
                     if (!var8) {
                        if (var11 is Waiter) {
                           var1 = var11 as Waiter;
                        } else {
                           var1 = null;
                        }

                        if (var1 != null) {
                           access$prepareSenderForSuspension(this, var1, var9, var2);
                        }

                        var9.onSlotCleaned();
                        return ChannelResult.Companion.failure-PtdJZtk();
                     }

                     var9.onSlotCleaned();
                  }
                  break;
               }
            } else {
               var9.cleanPrev();
            }

            return ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
         }

         return ChannelResult.Companion.closed-JP2dKIU(this.getSendException());
      }
   }

   internal fun waitExpandBufferCompletion(globalIndex: Long) {
      if (!this.isRendezvousOrUnlimited()) {
         while (this.getBufferEndCounter() <= var1) {
         }

         val var4: Int = BufferedChannelKt.access$getEXPAND_BUFFER_COMPLETION_WAIT_ITERATIONS$p();

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = this.getBufferEndCounter();
            if (var1 == (completedExpandBuffersAndPauseFlag$FU.get(this) and 4611686018427387903L) && var1 == this.getBufferEndCounter()) {
               return;
            }
         }

         var var9: AtomicLongFieldUpdater = completedExpandBuffersAndPauseFlag$FU;

         do {
            var1 = var9.get(this);
         } while (!var9.compareAndSet(this, var1, BufferedChannelKt.access$constructEBCompletedAndPauseFlag(var1 & 4611686018427387903L, true)));

         while (true) {
            val var5: Long = this.getBufferEndCounter();
            var9 = completedExpandBuffersAndPauseFlag$FU;
            val var7: Long = completedExpandBuffersAndPauseFlag$FU.get(this);
            var1 = var7 and 4611686018427387903L;
            val var14: Boolean;
            if ((4611686018427387904L and var7) != 0L) {
               var14 = true;
            } else {
               var14 = false;
            }

            if (var5 == var1 && var5 == this.getBufferEndCounter()) {
               do {
                  var1 = var9.get(this);
               } while (!var9.compareAndSet(this, var1, BufferedChannelKt.access$constructEBCompletedAndPauseFlag(var1 & 4611686018427387903L, false)));

               return;
            }

            if (!var14) {
               var9.compareAndSet(this, var7, BufferedChannelKt.access$constructEBCompletedAndPauseFlag(var1, true));
            }
         }
      }
   }

   private inner class BufferedChannelIterator : ChannelIterator<E>, Waiter {
      private final var continuation: CancellableContinuationImpl<Boolean>?
      private final var receiveResult: Any?

      init {
         this.this$0 = var1;
         this.receiveResult = BufferedChannelKt.access$getNO_RECEIVE_RESULT$p();
      }

      private suspend fun hasNextOnNoWaiterSuspend(segment: ChannelSegment<Any>, index: Int, r: Long): Boolean {
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
         // 000: aload 0
         // 001: getfield kotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator.this$0 Lkotlinx/coroutines/channels/BufferedChannel;
         // 004: astore 12
         // 006: aload 5
         // 008: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.intercepted (Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
         // 00b: invokestatic kotlinx/coroutines/CancellableContinuationKt.getOrCreateCancellableContinuation (Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/CancellableContinuationImpl;
         // 00e: astore 11
         // 010: aload 0
         // 011: aload 11
         // 013: invokestatic kotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator.access$setContinuation$p (Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;Lkotlinx/coroutines/CancellableContinuationImpl;)V
         // 016: aload 12
         // 018: aload 1
         // 019: iload 2
         // 01a: lload 3
         // 01b: aload 0
         // 01c: checkcast kotlinx/coroutines/Waiter
         // 01f: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellReceive (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;IJLjava/lang/Object;)Ljava/lang/Object;
         // 022: astore 13
         // 024: aload 13
         // 026: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND$p ()Lkotlinx/coroutines/internal/Symbol;
         // 029: if_acmpne 03a
         // 02c: aload 12
         // 02e: aload 0
         // 02f: checkcast kotlinx/coroutines/Waiter
         // 032: aload 1
         // 033: iload 2
         // 034: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareReceiverForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
         // 037: goto 1a1
         // 03a: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getFAILED$p ()Lkotlinx/coroutines/internal/Symbol;
         // 03d: astore 8
         // 03f: aconst_null
         // 040: astore 9
         // 042: aconst_null
         // 043: astore 10
         // 045: aload 13
         // 047: aload 8
         // 049: if_acmpne 165
         // 04c: lload 3
         // 04d: aload 12
         // 04f: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getSendersCounter$kotlinx_coroutines_core ()J
         // 052: lcmp
         // 053: ifge 05a
         // 056: aload 1
         // 057: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
         // 05a: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getReceiveSegment$FU$p ()Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
         // 05d: aload 12
         // 05f: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
         // 062: checkcast kotlinx/coroutines/channels/ChannelSegment
         // 065: astore 8
         // 067: aload 12
         // 069: invokevirtual kotlinx/coroutines/channels/BufferedChannel.isClosedForReceive ()Z
         // 06c: ifeq 076
         // 06f: aload 0
         // 070: invokestatic kotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator.access$onClosedHasNextNoWaiterSuspend (Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;)V
         // 073: goto 1a1
         // 076: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$getReceivers$FU$p ()Ljava/util/concurrent/atomic/AtomicLongFieldUpdater;
         // 079: aload 12
         // 07b: invokevirtual java/util/concurrent/atomic/AtomicLongFieldUpdater.getAndIncrement (Ljava/lang/Object;)J
         // 07e: lstore 6
         // 080: lload 6
         // 082: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
         // 085: i2l
         // 086: ldiv
         // 087: lstore 3
         // 088: lload 6
         // 08a: getstatic kotlinx/coroutines/channels/BufferedChannelKt.SEGMENT_SIZE I
         // 08d: i2l
         // 08e: lrem
         // 08f: l2i
         // 090: istore 2
         // 091: aload 8
         // 093: astore 1
         // 094: aload 8
         // 096: getfield kotlinx/coroutines/channels/ChannelSegment.id J
         // 099: lload 3
         // 09a: lcmp
         // 09b: ifeq 0ae
         // 09e: aload 12
         // 0a0: lload 3
         // 0a1: aload 8
         // 0a3: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$findSegmentReceive (Lkotlinx/coroutines/channels/BufferedChannel;JLkotlinx/coroutines/channels/ChannelSegment;)Lkotlinx/coroutines/channels/ChannelSegment;
         // 0a6: astore 1
         // 0a7: aload 1
         // 0a8: ifnonnull 0ae
         // 0ab: goto 067
         // 0ae: aload 12
         // 0b0: aload 1
         // 0b1: iload 2
         // 0b2: lload 6
         // 0b4: aload 0
         // 0b5: checkcast kotlinx/coroutines/Waiter
         // 0b8: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$updateCellReceive (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/channels/ChannelSegment;IJLjava/lang/Object;)Ljava/lang/Object;
         // 0bb: astore 13
         // 0bd: aload 13
         // 0bf: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND$p ()Lkotlinx/coroutines/internal/Symbol;
         // 0c2: if_acmpne 0ea
         // 0c5: aload 10
         // 0c7: astore 8
         // 0c9: aload 0
         // 0ca: checkcast kotlinx/coroutines/Waiter
         // 0cd: instanceof kotlinx/coroutines/Waiter
         // 0d0: ifeq 0d9
         // 0d3: aload 0
         // 0d4: checkcast kotlinx/coroutines/Waiter
         // 0d7: astore 8
         // 0d9: aload 8
         // 0db: ifnull 1a1
         // 0de: aload 12
         // 0e0: aload 8
         // 0e2: aload 1
         // 0e3: iload 2
         // 0e4: invokestatic kotlinx/coroutines/channels/BufferedChannel.access$prepareReceiverForSuspension (Lkotlinx/coroutines/channels/BufferedChannel;Lkotlinx/coroutines/Waiter;Lkotlinx/coroutines/channels/ChannelSegment;I)V
         // 0e7: goto 1a1
         // 0ea: aload 13
         // 0ec: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getFAILED$p ()Lkotlinx/coroutines/internal/Symbol;
         // 0ef: if_acmpne 10a
         // 0f2: aload 1
         // 0f3: astore 8
         // 0f5: lload 6
         // 0f7: aload 12
         // 0f9: invokevirtual kotlinx/coroutines/channels/BufferedChannel.getSendersCounter$kotlinx_coroutines_core ()J
         // 0fc: lcmp
         // 0fd: ifge 067
         // 100: aload 1
         // 101: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
         // 104: aload 1
         // 105: astore 8
         // 107: goto 067
         // 10a: aload 13
         // 10c: invokestatic kotlinx/coroutines/channels/BufferedChannelKt.access$getSUSPEND_NO_WAITER$p ()Lkotlinx/coroutines/internal/Symbol;
         // 10f: if_acmpeq 156
         // 112: aload 1
         // 113: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
         // 116: aload 0
         // 117: aload 13
         // 119: invokestatic kotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator.access$setReceiveResult$p (Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;Ljava/lang/Object;)V
         // 11c: aload 0
         // 11d: aconst_null
         // 11e: invokestatic kotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator.access$setContinuation$p (Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;Lkotlinx/coroutines/CancellableContinuationImpl;)V
         // 121: bipush 1
         // 122: invokestatic kotlin/coroutines/jvm/internal/Boxing.boxBoolean (Z)Ljava/lang/Boolean;
         // 125: astore 10
         // 127: aload 12
         // 129: getfield kotlinx/coroutines/channels/BufferedChannel.onUndeliveredElement Lkotlin/jvm/functions/Function1;
         // 12c: astore 12
         // 12e: aload 9
         // 130: astore 1
         // 131: aload 10
         // 133: astore 8
         // 135: aload 12
         // 137: ifnull 14b
         // 13a: aload 12
         // 13c: aload 13
         // 13e: aload 11
         // 140: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getContext ()Lkotlin/coroutines/CoroutineContext;
         // 143: invokestatic kotlinx/coroutines/internal/OnUndeliveredElementKt.bindCancellationFun (Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;
         // 146: astore 1
         // 147: aload 10
         // 149: astore 8
         // 14b: aload 11
         // 14d: aload 8
         // 14f: aload 1
         // 150: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.resume (Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V
         // 153: goto 1a1
         // 156: new java/lang/IllegalStateException
         // 159: astore 1
         // 15a: aload 1
         // 15b: ldc "unexpected"
         // 15d: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 160: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // 163: aload 1
         // 164: athrow
         // 165: aload 1
         // 166: invokevirtual kotlinx/coroutines/channels/ChannelSegment.cleanPrev ()V
         // 169: aload 0
         // 16a: aload 13
         // 16c: invokestatic kotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator.access$setReceiveResult$p (Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;Ljava/lang/Object;)V
         // 16f: aload 0
         // 170: aconst_null
         // 171: invokestatic kotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator.access$setContinuation$p (Lkotlinx/coroutines/channels/BufferedChannel$BufferedChannelIterator;Lkotlinx/coroutines/CancellableContinuationImpl;)V
         // 174: bipush 1
         // 175: invokestatic kotlin/coroutines/jvm/internal/Boxing.boxBoolean (Z)Ljava/lang/Boolean;
         // 178: astore 10
         // 17a: aload 12
         // 17c: getfield kotlinx/coroutines/channels/BufferedChannel.onUndeliveredElement Lkotlin/jvm/functions/Function1;
         // 17f: astore 12
         // 181: aload 9
         // 183: astore 1
         // 184: aload 10
         // 186: astore 8
         // 188: aload 12
         // 18a: ifnull 14b
         // 18d: aload 12
         // 18f: aload 13
         // 191: aload 11
         // 193: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getContext ()Lkotlin/coroutines/CoroutineContext;
         // 196: invokestatic kotlinx/coroutines/internal/OnUndeliveredElementKt.bindCancellationFun (Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;
         // 199: astore 1
         // 19a: aload 10
         // 19c: astore 8
         // 19e: goto 14b
         // 1a1: aload 11
         // 1a3: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.getResult ()Ljava/lang/Object;
         // 1a6: astore 1
         // 1a7: aload 1
         // 1a8: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
         // 1ab: if_acmpne 1b3
         // 1ae: aload 5
         // 1b0: invokestatic kotlin/coroutines/jvm/internal/DebugProbesKt.probeCoroutineSuspended (Lkotlin/coroutines/Continuation;)V
         // 1b3: aload 1
         // 1b4: areturn
         // 1b5: astore 1
         // 1b6: aload 11
         // 1b8: invokevirtual kotlinx/coroutines/CancellableContinuationImpl.releaseClaimedReusableContinuation$kotlinx_coroutines_core ()V
         // 1bb: aload 1
         // 1bc: athrow
      }

      private fun onClosedHasNext(): Boolean {
         this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
         val var1: java.lang.Throwable = this.this$0.getCloseCause();
         if (var1 == null) {
            return false;
         } else {
            throw StackTraceRecoveryKt.recoverStackTrace(var1);
         }
      }

      private fun onClosedHasNextNoWaiterSuspend() {
         val var1: CancellableContinuationImpl = this.continuation;
         this.continuation = null;
         this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
         val var2: java.lang.Throwable = this.this$0.getCloseCause();
         if (var2 == null) {
            val var4: Continuation = var1;
            val var6: Result.Companion = Result.Companion;
            var4.resumeWith(Result.constructor-impl(false));
         } else {
            val var3: Continuation = var1;
            var var5: java.lang.Throwable = var2;
            if (DebugKt.getRECOVER_STACK_TRACES()) {
               if (var3 !is CoroutineStackFrame) {
                  var5 = var2;
               } else {
                  var5 = StackTraceRecoveryKt.access$recoverFromStackFrame(var2, var3 as CoroutineStackFrame);
               }
            }

            val var7: Result.Companion = Result.Companion;
            var3.resumeWith(Result.constructor-impl(ResultKt.createFailure(var5)));
         }
      }

      public override suspend operator fun hasNext(): Boolean {
         val var9: BufferedChannel = this.this$0;
         var var7: ChannelSegment = BufferedChannel.access$getReceiveSegment$FU$p().get(var9) as ChannelSegment;

         while (true) {
            if (var9.isClosedForReceive()) {
               var10 = Boxing.boxBoolean(this.onClosedHasNext());
               break;
            }

            val var3: Long = BufferedChannel.access$getReceivers$FU$p().getAndIncrement(var9);
            val var5: Long = var3 / BufferedChannelKt.SEGMENT_SIZE;
            val var2: Int = (int)(var3 % BufferedChannelKt.SEGMENT_SIZE);
            if (var7.id != var5) {
               val var8: ChannelSegment = BufferedChannel.access$findSegmentReceive(var9, var5, var7);
               if (var8 == null) {
                  continue;
               }

               var7 = var8;
            }

            val var11: Any = BufferedChannel.access$updateCellReceive(var9, var7, var2, var3, null);
            if (var11 === BufferedChannelKt.access$getSUSPEND$p()) {
               throw new IllegalStateException("unreachable".toString());
            }

            if (var11 != BufferedChannelKt.access$getFAILED$p()) {
               if (var11 === BufferedChannelKt.access$getSUSPEND_NO_WAITER$p()) {
                  return this.hasNextOnNoWaiterSuspend(var7, var2, var3, var1);
               }

               var7.cleanPrev();
               this.receiveResult = var11;
               var10 = Boxing.boxBoolean(true);
               break;
            }

            if (var3 < var9.getSendersCounter$kotlinx_coroutines_core()) {
               var7.cleanPrev();
            }
         }

         return var10;
      }

      public override fun invokeOnCancellation(segment: Segment<*>, index: Int) {
         if (this.continuation != null) {
            this.continuation.invokeOnCancellation(var1, var2);
         }
      }

      public override operator fun next(): Any {
         val var1: Any = this.receiveResult;
         if (this.receiveResult != BufferedChannelKt.access$getNO_RECEIVE_RESULT$p()) {
            this.receiveResult = BufferedChannelKt.access$getNO_RECEIVE_RESULT$p();
            if (var1 != BufferedChannelKt.getCHANNEL_CLOSED()) {
               return (E)var1;
            } else {
               throw StackTraceRecoveryKt.recoverStackTrace(BufferedChannel.access$getReceiveException(this.this$0));
            }
         } else {
            throw new IllegalStateException("`hasNext()` has not been invoked".toString());
         }
      }

      public fun tryResumeHasNext(element: Any): Boolean {
         val var4: CancellableContinuationImpl = this.continuation;
         var var2: Function1 = null;
         this.continuation = null;
         this.receiveResult = var1;
         val var3: CancellableContinuation = var4;
         if (this.this$0.onUndeliveredElement != null) {
            var2 = OnUndeliveredElementKt.bindCancellationFun(this.this$0.onUndeliveredElement, (E)var1, var4.getContext());
         }

         return BufferedChannelKt.access$tryResume0(var3, true, var2);
      }

      public fun tryResumeHasNextOnClosedChannel() {
         val var1: CancellableContinuationImpl = this.continuation;
         this.continuation = null;
         this.receiveResult = BufferedChannelKt.getCHANNEL_CLOSED();
         val var2: java.lang.Throwable = this.this$0.getCloseCause();
         if (var2 == null) {
            val var6: Continuation = var1;
            val var4: Result.Companion = Result.Companion;
            var6.resumeWith(Result.constructor-impl(false));
         } else {
            val var3: Continuation = var1;
            var var5: java.lang.Throwable = var2;
            if (DebugKt.getRECOVER_STACK_TRACES()) {
               if (var3 !is CoroutineStackFrame) {
                  var5 = var2;
               } else {
                  var5 = StackTraceRecoveryKt.access$recoverFromStackFrame(var2, var3 as CoroutineStackFrame);
               }
            }

            val var7: Result.Companion = Result.Companion;
            var3.resumeWith(Result.constructor-impl(ResultKt.createFailure(var5)));
         }
      }
   }

   private class SendBroadcast(cont: CancellableContinuation<Boolean>) : Waiter {
      public final val cont: CancellableContinuation<Boolean>

      init {
         this.cont = var1;
         this.$$delegate_0 = var1 as CancellableContinuationImpl<java.lang.Boolean>;
      }

      public override fun invokeOnCancellation(segment: Segment<*>, index: Int) {
         this.$$delegate_0.invokeOnCancellation(var1, var2);
      }
   }
}
