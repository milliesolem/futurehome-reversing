package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.internal.ChannelFlow

private class CallbackFlowBuilder<T>(block: (ProducerScope<Any>, Continuation<Unit>) -> Any?,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = -2,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ChannelFlowBuilder(var1, var2, var3, var4) {
   private final val block: (ProducerScope<Any>, Continuation<Unit>) -> Any?

   init {
      this.block = var1;
   }

   protected override suspend fun collectTo(scope: ProducerScope<Any>) {
      label27: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label27;
            }
         }

         var2 = new ContinuationImpl(this, var2) {
            Object L$0;
            int label;
            Object result;
            final CallbackFlowBuilder<T> this$0;

            {
               super(var2);
               this.this$0 = var1;
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return this.this$0.collectTo(null, this);
            }
         };
      }

      val var5: Any = var2.result;
      val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var8: ProducerScope;
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var8 = var2.L$0 as ProducerScope;
         ResultKt.throwOnFailure(var5);
      } else {
         ResultKt.throwOnFailure(var5);
         var2.L$0 = var1;
         var2.label = 1;
         var8 = var1;
         if (super.collectTo(var1, var2) === var6) {
            return var6;
         }
      }

      if (var8.isClosedForSend()) {
         return Unit.INSTANCE;
      } else {
         throw new IllegalStateException(
            "'awaitClose { yourCallbackOrListener.cancel() }' should be used in the end of callbackFlow block.\nOtherwise, a callback/listener may leak in case of external cancellation.\nSee callbackFlow API documentation for the details."
         );
      }
   }

   protected override fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
      return new CallbackFlowBuilder<>(this.block, var1, var2, var3);
   }
}
