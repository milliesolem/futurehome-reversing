package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref

private class StartedLazily : SharingStarted {
   public override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> {
      return FlowKt.flow((new Function2<FlowCollector<? super SharingCommand>, Continuation<? super Unit>, Object>(var1, null) {
         final StateFlow<Integer> $subscriptionCount;
         private Object L$0;
         int label;

         {
            super(2, var2x);
            this.$subscriptionCount = var1;
         }

         @Override
         public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
            val var3: Function2 = new <anonymous constructor>(this.$subscriptionCount, var2);
            var3.L$0 = var1;
            return var3 as Continuation<Unit>;
         }

         public final Object invoke(FlowCollector<? super SharingCommand> var1, Continuation<? super Unit> var2x) {
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
               var var4: FlowCollector = this.L$0 as FlowCollector;
               val var5: Ref.BooleanRef = new Ref.BooleanRef();
               var1 = this.$subscriptionCount;
               var4 = new FlowCollector(var5, var4) {
                  final FlowCollector<SharingCommand> $$this$flow;
                  final Ref.BooleanRef $started;

                  {
                     this.$started = var1;
                     this.$$this$flow = var2x;
                  }

                  public final Object emit(int var1, Continuation<? super Unit> var2x) {
                     label32: {
                        if (var2x is <unrepresentable>) {
                           val var4x: <unrepresentable> = var2x as <unrepresentable>;
                           if ((var2x.label and Integer.MIN_VALUE) != 0) {
                              var4x.label += Integer.MIN_VALUE;
                              var2x = var4x;
                              break label32;
                           }
                        }

                        var2x = new ContinuationImpl(this, var2x) {
                           int label;
                           Object result;
                           final <unrepresentable><T> this$0;

                           {
                              super(var2x);
                              this.this$0 = var1;
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              this.result = var1;
                              this.label |= Integer.MIN_VALUE;
                              return this.this$0.emit(0, this);
                           }
                        };
                     }

                     var var5x: Any = var2x.result;
                     val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (var2x.label != 0) {
                        if (var2x.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var5x);
                     } else {
                        ResultKt.throwOnFailure(var5x);
                        if (var1 <= 0 || this.$started.element) {
                           return Unit.INSTANCE;
                        }

                        this.$started.element = true;
                        val var6: FlowCollector = this.$$this$flow;
                        var5x = SharingCommand.START;
                        var2x.label = 1;
                        if (var6.emit(var5x, var2x) === var8) {
                           return var8;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               };
               val var8: Continuation = this;
               this.label = 1;
               if (var1.collect(var4, var8) === var3x) {
                  return var3x;
               }
            }

            throw new KotlinNothingValueException();
         }
      }) as (FlowCollector<? super SharingCommand>?, Continuation<? super Unit>?) -> Any);
   }

   public override fun toString(): String {
      return "SharingStarted.Lazily";
   }
}
