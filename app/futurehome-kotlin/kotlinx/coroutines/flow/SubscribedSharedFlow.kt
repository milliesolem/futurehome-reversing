package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl

private class SubscribedSharedFlow<T>(sharedFlow: SharedFlow<Any>, action: (FlowCollector<Any>, Continuation<Unit>) -> Any?) : SharedFlow<T> {
   private final val action: (FlowCollector<Any>, Continuation<Unit>) -> Any?
   public open val replayCache: List<Any>
   private final val sharedFlow: SharedFlow<Any>

   init {
      this.sharedFlow = var1;
      this.action = var2;
   }

   public override suspend fun collect(collector: FlowCollector<Any>): Nothing {
      label23: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label23;
            }
         }

         var2 = new ContinuationImpl(this, var2) {
            int label;
            Object result;
            final SubscribedSharedFlow<T> this$0;

            {
               super(var2);
               this.this$0 = var1;
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return this.this$0.collect(null, this);
            }
         };
      }

      var var5: Any = var2.result;
      val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         ResultKt.throwOnFailure(var5);
      } else {
         ResultKt.throwOnFailure(var5);
         var5 = this.sharedFlow;
         var1 = new SubscribedFlowCollector<>(var1, this.action);
         var2.label = 1;
         if (((SharedFlow)var5).collect(var1, var2) === var8) {
            return var8;
         }
      }

      throw new KotlinNothingValueException();
   }
}
