package kotlinx.coroutines.channels

import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineExceptionHandlerKt
import kotlinx.coroutines.Job
import kotlinx.coroutines.JobCancellationException
import kotlinx.coroutines.JobSupport
import kotlinx.coroutines.selects.SelectClause2

private open class BroadcastCoroutine<E>(parentContext: CoroutineContext, _channel: BroadcastChannel<Any>, active: Boolean) : AbstractCoroutine(
         var1, false, var3
      ),
   ProducerScope<E>,
   BroadcastChannel<E> {
   protected final val _channel: BroadcastChannel<Any>

   public open val channel: SendChannel<Any>
      public open get() {
         return this;
      }


   public open val isActive: Boolean
      public open get() {
         return super.isActive();
      }


   public open val isClosedForSend: Boolean
   public open val onSend: SelectClause2<Any, SendChannel<Any>>

   init {
      this._channel = var2;
      this.initParentJob(var1.get(Job.Key));
   }

   public override fun cancel(cause: CancellationException?) {
      var var2: CancellationException = var1;
      if (var1 == null) {
         var2 = new JobCancellationException(JobSupport.access$cancellationExceptionMessage(this), null, this);
      }

      this.cancelInternal(var2);
   }

   public override fun cancelInternal(cause: Throwable) {
      val var2: CancellationException = JobSupport.toCancellationException$default(this, var1, null, 1, null);
      this._channel.cancel(var2);
      this.cancelCoroutine(var2);
   }

   public override fun close(cause: Throwable?): Boolean {
      val var2: Boolean = this._channel.close(var1);
      this.start();
      return var2;
   }

   public override fun invokeOnClose(handler: (Throwable?) -> Unit) {
      this._channel.invokeOnClose(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
   public override fun offer(element: Any): Boolean {
      return this._channel.offer((E)var1);
   }

   protected override fun onCancelled(cause: Throwable, handled: Boolean) {
      if (!this._channel.close(var1) && !var2) {
         CoroutineExceptionHandlerKt.handleCoroutineException(this.getContext(), var1);
      }
   }

   protected open fun onCompleted(value: Unit) {
      SendChannel.DefaultImpls.close$default(this._channel, null, 1, null);
   }

   public override fun openSubscription(): ReceiveChannel<Any> {
      return this._channel.openSubscription();
   }

   public override suspend fun send(element: Any) {
      return this._channel.send((E)var1, var2);
   }

   public override fun trySend(element: Any): ChannelResult<Unit> {
      return this._channel.trySend-JP2dKIU((E)var1);
   }
}
