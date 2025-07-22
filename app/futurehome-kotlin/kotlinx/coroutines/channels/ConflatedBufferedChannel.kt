package kotlinx.coroutines.channels

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlinx.coroutines.Waiter
import kotlinx.coroutines.internal.OnUndeliveredElementKt
import kotlinx.coroutines.internal.Symbol
import kotlinx.coroutines.selects.SelectInstance

internal open class ConflatedBufferedChannel<E>(capacity: Int, onBufferOverflow: BufferOverflow, onUndeliveredElement: ((Any) -> Unit)? = null) : BufferedChannel(
      var1, var3
   ) {
   private final val capacity: Int

   protected open val isConflatedDropOldest: Boolean
      protected open get() {
         val var1: Boolean;
         if (this.onBufferOverflow === BufferOverflow.DROP_OLDEST) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   private final val onBufferOverflow: BufferOverflow

   init {
      this.capacity = var1;
      this.onBufferOverflow = var2;
      if (var2 != BufferOverflow.SUSPEND) {
         if (var1 < 1) {
            val var5: StringBuilder = new StringBuilder("Buffered channel capacity must be at least 1, but ");
            var5.append(var1);
            var5.append(" was specified");
            throw new IllegalArgumentException(var5.toString().toString());
         }
      } else {
         val var4: StringBuilder = new StringBuilder("This implementation does not support suspension for senders, use ");
         var4.append((BufferedChannel::class).getSimpleName());
         var4.append(" instead");
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   private fun trySendDropLatest(element: Any, isSendOp: Boolean): ChannelResult<Unit> {
      val var3: Any = super.trySend-JP2dKIU((E)var1);
      if (!ChannelResult.isSuccess-impl(var3) && !ChannelResult.isClosed-impl(var3)) {
         if (var2 && this.onUndeliveredElement != null) {
            var1 = OnUndeliveredElementKt.callUndeliveredElementCatchingException$default(this.onUndeliveredElement, var1, null, 2, null);
            if (var1 != null) {
               throw var1;
            }
         }

         return ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
      } else {
         return var3;
      }
   }

   private fun trySendDropOldest(element: Any): ChannelResult<Unit> {
      val var12: Symbol = BufferedChannelKt.BUFFERED;
      val var9: AtomicReferenceFieldUpdater = BufferedChannel.access$getSendSegment$FU$p();
      val var11: BufferedChannel = this;
      var var15: ChannelSegment = var9.get(this) as ChannelSegment;

      while (true) {
         var var6: Long = BufferedChannel.access$getSendersAndCloseStatus$FU$p().getAndIncrement(var11);
         val var4: Long = var6 and 1152921504606846975L;
         val var8: Boolean = BufferedChannel.access$isClosedForSend0(var11, var6);
         var6 = var4 / BufferedChannelKt.SEGMENT_SIZE;
         val var3: Int = (int)(var4 % BufferedChannelKt.SEGMENT_SIZE);
         if (var15.id != var6) {
            val var10: ChannelSegment = BufferedChannel.access$findSegmentSend(var11, var6, var15);
            if (var10 == null) {
               if (var8) {
                  return ChannelResult.Companion.closed-JP2dKIU(this.getSendException());
               }
               continue;
            }

            var15 = var10;
         }

         val var2: Int = BufferedChannel.access$updateCellSend(var11, var15, var3, var1, var4, var12, var8);
         if (var2 == 0) {
            var15.cleanPrev();
            return ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
         }

         if (var2 == 1) {
            return ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
         }

         if (var2 == 2) {
            if (var8) {
               var15.onSlotCleaned();
               return ChannelResult.Companion.closed-JP2dKIU(this.getSendException());
            }

            if (var12 is Waiter) {
               var1 = var12 as Waiter;
            } else {
               var1 = null;
            }

            if (var1 != null) {
               BufferedChannel.access$prepareSenderForSuspension(var11, var1, var15, var3);
            }

            this.dropFirstElementUntilTheSpecifiedCellIsInTheBuffer(var15.id * (long)BufferedChannelKt.SEGMENT_SIZE + (long)var3);
            return ChannelResult.Companion.success-JP2dKIU(Unit.INSTANCE);
         }

         if (var2 == 3) {
            throw new IllegalStateException("unexpected".toString());
         }

         if (var2 == 4) {
            if (var4 < var11.getReceiversCounter$kotlinx_coroutines_core()) {
               var15.cleanPrev();
            }

            return ChannelResult.Companion.closed-JP2dKIU(this.getSendException());
         }

         if (var2 == 5) {
            var15.cleanPrev();
         }
      }
   }

   private fun trySendImpl(element: Any, isSendOp: Boolean): ChannelResult<Unit> {
      if (this.onBufferOverflow === BufferOverflow.DROP_LATEST) {
         var1 = this.trySendDropLatest-Mj0NB7M((E)var1, var2);
      } else {
         var1 = this.trySendDropOldest-JP2dKIU((E)var1);
      }

      return var1;
   }

   protected override fun registerSelectForSend(select: SelectInstance<*>, element: Any?) {
      var2 = this.trySend-JP2dKIU((E)var2);
      if (var2 !is ChannelResult.Failed) {
         var2 = var2 as Unit;
         var1.selectInRegistrationPhase(Unit.INSTANCE);
      } else if (var2 is ChannelResult.Closed) {
         ChannelResult.exceptionOrNull-impl(var2);
         var1.selectInRegistrationPhase(BufferedChannelKt.getCHANNEL_CLOSED());
      } else {
         throw new IllegalStateException("unreachable".toString());
      }
   }

   public override suspend fun send(element: Any) {
      return send$suspendImpl(this, (E)var1, var2);
   }

   internal override suspend fun sendBroadcast(element: Any): Boolean {
      return sendBroadcast$suspendImpl(this, (E)var1, var2);
   }

   internal override fun shouldSendSuspend(): Boolean {
      return false;
   }

   public override fun trySend(element: Any): ChannelResult<Unit> {
      return this.trySendImpl-Mj0NB7M((E)var1, false);
   }
}
