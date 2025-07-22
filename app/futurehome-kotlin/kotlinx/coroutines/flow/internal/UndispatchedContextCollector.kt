package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.internal.ThreadContextKt

private class UndispatchedContextCollector<T>(downstream: FlowCollector<Any>, emitContext: CoroutineContext) : FlowCollector<T> {
   private final val countOrElement: Any
   private final val emitContext: CoroutineContext
   private final val emitRef: (Any, Continuation<Unit>) -> Any?

   init {
      this.emitContext = var2;
      this.countOrElement = ThreadContextKt.threadContextElements(var2);
      this.emitRef = (new Function2<T, Continuation<? super Unit>, Object>(var1, null) {
         final FlowCollector<T> $downstream;
         Object L$0;
         int label;

         {
            super(2, var2x);
            this.$downstream = var1;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$downstream, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(T var1, Continuation<? super Unit> var2x) {
            return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            val var3x: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
               if (this.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var1);
            } else {
               ResultKt.throwOnFailure(var1);
               var1 = this.L$0;
               val var5: FlowCollector = this.$downstream;
               val var4: Continuation = this;
               this.label = 1;
               if (var5.emit(var1, var4) === var3x) {
                  return var3x;
               }
            }

            return Unit.INSTANCE;
         }
      }) as (T?, Continuation<? super Unit>?) -> Any;
   }

   public override suspend fun emit(value: Any) {
      var1 = ChannelFlowKt.withContextUndispatched(this.emitContext, var1, this.countOrElement, this.emitRef, var2);
      return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
   }
}
