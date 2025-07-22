package kotlinx.coroutines.channels

import com.google.common.util.concurrent.Striped.SmallLazyStriped..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceArray
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.functions.Function1
import kotlinx.atomicfu.AtomicArray
import kotlinx.coroutines.Waiter
import kotlinx.coroutines.internal.OnUndeliveredElementKt
import kotlinx.coroutines.internal.Segment
import kotlinx.coroutines.internal.Symbol

internal class ChannelSegment<E>(id: Long, prev: ChannelSegment<Any>?, channel: BufferedChannel<Any>?, pointers: Int) : Segment(var1, var3, var5) {
   private final val _channel: BufferedChannel<Any>?

   public final val channel: BufferedChannel<Any>
      public final get() {
         val var1: BufferedChannel = this._channel;
         return var1;
      }


   private final val data: AtomicArray<Any?>

   public open val numberOfSlots: Int
      public open get() {
         return BufferedChannelKt.SEGMENT_SIZE;
      }


   init {
      this._channel = var4;
      this.data = new AtomicReferenceArray(BufferedChannelKt.SEGMENT_SIZE * 2);
   }

   private fun setElementLazy(index: Int, value: Any?) {
      this.data.lazySet(var1 * 2, var2);
   }

   internal fun casState(index: Int, from: Any?, to: Any?): Boolean {
      return ExternalSyntheticBackportWithForwarding0.m(this.data, var1 * 2 + 1, var2, var3);
   }

   internal fun cleanElement(index: Int) {
      this.setElementLazy(var1, null);
   }

   internal fun getAndSetState(index: Int, update: Any?): Any? {
      return this.data.getAndSet(var1 * 2 + 1, var2);
   }

   internal fun getElement(index: Int): Any {
      return (E)this.data.get(var1 * 2);
   }

   internal fun getState(index: Int): Any? {
      return this.data.get(var1 * 2 + 1);
   }

   public override fun onCancellation(index: Int, cause: Throwable?, context: CoroutineContext) {
      val var4: Boolean;
      if (var1 >= BufferedChannelKt.SEGMENT_SIZE) {
         var4 = true;
      } else {
         var4 = false;
      }

      var var5: Int = var1;
      if (var4) {
         var5 = var1 - BufferedChannelKt.SEGMENT_SIZE;
      }

      val var6: Any = this.getElement$kotlinx_coroutines_core(var5);

      while (true) {
         val var7: Any = this.getState$kotlinx_coroutines_core(var5);
         if (var7 !is Waiter && var7 !is WaiterEB) {
            if (var7 === BufferedChannelKt.access$getINTERRUPTED_SEND$p() || var7 === BufferedChannelKt.access$getINTERRUPTED_RCV$p()) {
               this.cleanElement$kotlinx_coroutines_core(var5);
               if (var4) {
                  val var11: Function1 = this.getChannel().onUndeliveredElement;
                  if (var11 != null) {
                     OnUndeliveredElementKt.callUndeliveredElement(var11, var6, var3);
                  }
               }

               return;
            }

            if (var7 != BufferedChannelKt.access$getRESUMING_BY_EB$p() && var7 != BufferedChannelKt.access$getRESUMING_BY_RCV$p()) {
               if (var7 != BufferedChannelKt.access$getDONE_RCV$p() && var7 != BufferedChannelKt.BUFFERED) {
                  if (var7 === BufferedChannelKt.getCHANNEL_CLOSED()) {
                     return;
                  }

                  val var10: StringBuilder = new StringBuilder("unexpected state: ");
                  var10.append(var7);
                  throw new IllegalStateException(var10.toString().toString());
               }

               return;
            }
         } else {
            val var8: Symbol;
            if (var4) {
               var8 = BufferedChannelKt.access$getINTERRUPTED_SEND$p();
            } else {
               var8 = BufferedChannelKt.access$getINTERRUPTED_RCV$p();
            }

            if (this.casState$kotlinx_coroutines_core(var5, var7, var8)) {
               this.cleanElement$kotlinx_coroutines_core(var5);
               this.onCancelledRequest(var5, var4 xor true);
               if (var4) {
                  val var9: Function1 = this.getChannel().onUndeliveredElement;
                  if (var9 != null) {
                     OnUndeliveredElementKt.callUndeliveredElement(var9, var6, var3);
                  }
               }

               return;
            }
         }
      }
   }

   public fun onCancelledRequest(index: Int, receiver: Boolean) {
      if (var2) {
         this.getChannel().waitExpandBufferCompletion$kotlinx_coroutines_core(this.id * (long)BufferedChannelKt.SEGMENT_SIZE + (long)var1);
      }

      this.onSlotCleaned();
   }

   internal fun retrieveElement(index: Int): Any {
      val var2: Any = this.getElement$kotlinx_coroutines_core(var1);
      this.cleanElement$kotlinx_coroutines_core(var1);
      return (E)var2;
   }

   internal fun setState(index: Int, value: Any?) {
      this.data.set(var1 * 2 + 1, var2);
   }

   internal fun storeElement(index: Int, element: Any) {
      this.setElementLazy(var1, var2);
   }
}
