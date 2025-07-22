package kotlinx.coroutines.channels

import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.JobCancellationException
import kotlinx.coroutines.JobSupport
import kotlinx.coroutines.selects.SelectClause1
import kotlinx.coroutines.selects.SelectClause2

internal open class ChannelCoroutine<E>(parentContext: CoroutineContext, _channel: Channel<Any>, initParentJob: Boolean, active: Boolean) : AbstractCoroutine(
         var1, var3, var4
      ),
   Channel<E> {
   protected final val _channel: Channel<Any>

   public final val channel: Channel<Any>
      public final get() {
         return this;
      }


   public open val isClosedForReceive: Boolean
   public open val isClosedForSend: Boolean
   public open val isEmpty: Boolean
   public open val onReceive: SelectClause1<Any>
   public open val onReceiveCatching: SelectClause1<ChannelResult<Any>>

   public open val onReceiveOrNull: SelectClause1<Any?>
      public open get() {
         return this._channel.getOnReceiveOrNull();
      }


   public open val onSend: SelectClause2<Any, SendChannel<Any>>

   init {
      this._channel = var2;
   }

   public override fun cancel(cause: CancellationException?) {
      if (!this.isCancelled()) {
         var var2: CancellationException = var1;
         if (var1 == null) {
            var2 = new JobCancellationException(JobSupport.access$cancellationExceptionMessage(this), null, this);
         }

         this.cancelInternal(var2);
      }
   }

   public override fun cancelInternal(cause: Throwable) {
      val var2: CancellationException = JobSupport.toCancellationException$default(this, var1, null, 1, null);
      this._channel.cancel(var2);
      this.cancelCoroutine(var2);
   }

   public override fun close(cause: Throwable?): Boolean {
      return this._channel.close(var1);
   }

   public override fun invokeOnClose(handler: (Throwable?) -> Unit) {
      this._channel.invokeOnClose(var1);
   }

   public override operator fun iterator(): ChannelIterator<Any> {
      return this._channel.iterator();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
   public override fun offer(element: Any): Boolean {
      return this._channel.offer((E)var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = []))
   public override fun poll(): Any? {
      return this._channel.poll();
   }

   public override suspend fun receive(): Any {
      return this._channel.receive(var1);
   }

   public override suspend fun receiveCatching(): ChannelResult<Any> {
      val var2: Any = this._channel.receiveCatching-JP2dKIU(var1);
      IntrinsicsKt.getCOROUTINE_SUSPENDED();
      return var2;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = []))
   public override suspend fun receiveOrNull(): Any? {
      return this._channel.receiveOrNull(var1);
   }

   public override suspend fun send(element: Any) {
      return this._channel.send((E)var1, var2);
   }

   public override fun tryReceive(): ChannelResult<Any> {
      return this._channel.tryReceive-PtdJZtk();
   }

   public override fun trySend(element: Any): ChannelResult<Unit> {
      return this._channel.trySend-JP2dKIU((E)var1);
   }
}
