package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.intrinsics.UndispatchedKt

internal suspend fun <R> flowScope(block: (CoroutineScope, Continuation<R>) -> Any?): R {
   val var2: FlowCoroutine = new FlowCoroutine(var1.getContext(), var1);
   val var3: Any = UndispatchedKt.startUndispatchedOrReturn(var2, var2, var0);
   if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var3;
}

internal fun <R> scopedFlow(block: (CoroutineScope, FlowCollector<R>, Continuation<Unit>) -> Any?): Flow<R> {
   return new Flow<R>(var0) {
      final Function3 $block$inlined;

      {
         this.$block$inlined = var1;
      }

      @Override
      public Object collect(FlowCollector<? super R> var1, Continuation<? super Unit> var2) {
         val var3: Any = FlowCoroutineKt.flowScope((new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this.$block$inlined, var1, null) {
            final Function3<CoroutineScope, FlowCollector<? super R>, Continuation<? super Unit>, Object> $block;
            final FlowCollector<R> $this_unsafeFlow;
            private Object L$0;
            int label;

            {
               super(2, var3x);
               this.$block = var1;
               this.$this_unsafeFlow = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2x) {
               val var3x: Function2 = new <anonymous constructor>(this.$block, this.$this_unsafeFlow, var2x);
               var3x.L$0 = var1;
               return var3x as Continuation<Unit>;
            }

            public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
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
                  var1 = this.L$0 as CoroutineScope;
                  val var5: Function3 = this.$block;
                  val var4: FlowCollector = this.$this_unsafeFlow;
                  this.label = 1;
                  if (var5.invoke(var1, var4, this) === var3x) {
                     return var3x;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as Function2, var2);
         return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
      }
   };
}
