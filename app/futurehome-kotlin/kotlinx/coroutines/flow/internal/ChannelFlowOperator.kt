package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

internal abstract class ChannelFlowOperator<S, T> : ChannelFlow<T> {
   protected final val flow: Flow<Any>

   open fun ChannelFlowOperator(var1: Flow<? extends S>, var2: CoroutineContext, var3: Int, var4: BufferOverflow) {
      super(var2, var3, var4);
      this.flow = var1;
   }

   private suspend fun collectWithContextUndispatched(collector: FlowCollector<Any>, newContext: CoroutineContext) {
      val var4: Any = ChannelFlowKt.withContextUndispatched$default(
         var2,
         ChannelFlowKt.access$withUndispatchedContextCollector(var1, var3.getContext()),
         null,
         (new Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object>(this, null) {
            Object L$0;
            int label;
            final ChannelFlowOperator<S, T> this$0;

            {
               super(2, var2x);
               this.this$0 = var1;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               val var3: Function2 = new <anonymous constructor>(this.this$0, var2);
               var3.L$0 = var1;
               return var3 as Continuation<Unit>;
            }

            public final Object invoke(FlowCollector<? super T> var1, Continuation<? super Unit> var2x) {
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
                  val var4: FlowCollector = this.L$0 as FlowCollector;
                  val var5: ChannelFlowOperator = this.this$0;
                  var1 = this;
                  this.label = 1;
                  if (var5.flowCollect(var4, var1) === var3x) {
                     return var3x;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as Function2,
         var3,
         4,
         null
      );
      return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
   }

   public override suspend fun collect(collector: FlowCollector<Any>) {
      return collect$suspendImpl(this, var1, var2);
   }

   protected override suspend fun collectTo(scope: ProducerScope<Any>) {
      return collectTo$suspendImpl(this, var1, var2);
   }

   protected abstract suspend fun flowCollect(collector: FlowCollector<Any>) {
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.flow);
      var1.append(" -> ");
      var1.append(super.toString());
      return var1.toString();
   }
}
