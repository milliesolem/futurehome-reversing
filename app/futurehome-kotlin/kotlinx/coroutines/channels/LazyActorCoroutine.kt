package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.TypeIntrinsics
import kotlinx.coroutines.intrinsics.CancellableKt
import kotlinx.coroutines.selects.SelectClause2
import kotlinx.coroutines.selects.SelectClause2Impl
import kotlinx.coroutines.selects.SelectInstance

private class LazyActorCoroutine<E>(parentContext: CoroutineContext, channel: Channel<Any>, block: (ActorScope<Any>, Continuation<Unit>) -> Any?) : ActorCoroutine(
      var1, var2, false
   ) {
   private final var continuation: Continuation<Unit>

   public open val onSend: SelectClause2<Any, SendChannel<Any>>
      public open get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause2Impl<>(
            this, TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3, super.getOnSend().getProcessResFunc(), null, 8, null
         );
      }


   init {
      this.continuation = IntrinsicsKt.createCoroutineUnintercepted(var3, this, this);
   }

   private fun onSendRegFunction(select: SelectInstance<*>, element: Any?) {
      this.onStart();
      super.getOnSend().getRegFunc().invoke(this, var1, var2);
   }

   public override fun close(cause: Throwable?): Boolean {
      val var2: Boolean = super.close(var1);
      this.start();
      return var2;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
   public override fun offer(element: Any): Boolean {
      this.start();
      return super.offer((E)var1);
   }

   protected override fun onStart() {
      CancellableKt.startCoroutineCancellable(this.continuation, this);
   }

   public override suspend fun send(element: Any) {
      this.start();
      var1 = super.send((E)var1, var2);
      return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
   }

   public override fun trySend(element: Any): ChannelResult<Unit> {
      this.start();
      return super.trySend-JP2dKIU((E)var1);
   }
}
