package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProduceKt
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

internal class ChannelLimitedFlowMerge<T>(flows: Iterable<Flow<Any>>,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = -2,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ChannelFlow(var2, var3, var4) {
   private final val flows: Iterable<Flow<Any>>

   init {
      this.flows = var1;
   }

   protected override suspend fun collectTo(scope: ProducerScope<Any>) {
      val var4: SendingCollector = new SendingCollector(var1);

      for (Flow var3 : this.flows) {
         BuildersKt.launch$default(var1, null, null, (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var3, var4, null) {
            final SendingCollector<T> $collector;
            final Flow<T> $flow;
            int label;

            {
               super(2, var3);
               this.$flow = var1;
               this.$collector = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               return new <anonymous constructor>(this.$flow, this.$collector, var2);
            }

            public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label != 0) {
                  if (this.label != 1) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var1);
               } else {
                  ResultKt.throwOnFailure(var1);
                  val var4: Flow = this.$flow;
                  val var5: FlowCollector = this.$collector;
                  var1 = this;
                  this.label = 1;
                  if (var4.collect(var5, var1) === var3) {
                     return var3;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as Function2, 3, null);
      }

      return Unit.INSTANCE;
   }

   protected override fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
      return new ChannelLimitedFlowMerge<>(this.flows, var1, var2, var3);
   }

   public override fun produceImpl(scope: CoroutineScope): ReceiveChannel<Any> {
      return ProduceKt.produce(var1, this.context, this.capacity, this.getCollectToFun$kotlinx_coroutines_core());
   }
}
