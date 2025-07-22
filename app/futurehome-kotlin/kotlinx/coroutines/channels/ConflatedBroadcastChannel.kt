package kotlinx.coroutines.channels

import java.util.concurrent.CancellationException
import kotlinx.coroutines.selects.SelectClause2

@Deprecated(level = DeprecationLevel.WARNING, message = "ConflatedBroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
public class ConflatedBroadcastChannel<E> private constructor(broadcast: BroadcastChannelImpl<Any>) : BroadcastChannel<E> {
   private final val broadcast: BroadcastChannelImpl<Any>
   public open val isClosedForSend: Boolean
   public open val onSend: SelectClause2<Any, SendChannel<Any>>

   public final val value: Any
      public final get() {
         return this.broadcast.getValue();
      }


   public final val valueOrNull: Any?
      public final get() {
         return this.broadcast.getValueOrNull();
      }


   public constructor() : this(new BroadcastChannelImpl<>(-1))
   public constructor(value: Any) : this() {
      this.trySend-JP2dKIU((E)var1);
   }

   init {
      this.broadcast = var1;
   }

   public override fun cancel(cause: CancellationException? = ...) {
      this.broadcast.cancel(var1);
   }

   public override fun close(cause: Throwable?): Boolean {
      return this.broadcast.close(var1);
   }

   public override fun invokeOnClose(handler: (Throwable?) -> Unit) {
      this.broadcast.invokeOnClose(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
   public override fun offer(element: Any): Boolean {
      return this.broadcast.offer((E)var1);
   }

   public override fun openSubscription(): ReceiveChannel<Any> {
      return this.broadcast.openSubscription();
   }

   public override suspend fun send(element: Any) {
      return this.broadcast.send((E)var1, var2);
   }

   public override fun trySend(element: Any): ChannelResult<Unit> {
      return this.broadcast.trySend-JP2dKIU((E)var1);
   }
}
