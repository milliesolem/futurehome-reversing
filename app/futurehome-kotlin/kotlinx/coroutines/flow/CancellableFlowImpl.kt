package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlinx.coroutines.JobKt

private class CancellableFlowImpl<T>(flow: Flow<Any>) : CancellableFlow<T> {
   private final val flow: Flow<Any>

   init {
      this.flow = var1;
   }

   public override suspend fun collect(collector: FlowCollector<Any>) {
      val var3: Any = this.flow.collect(new FlowCollector(var1) {
         final FlowCollector<T> $collector;

         {
            this.$collector = var1;
         }

         @Override
         public final Object emit(T var1, Continuation<? super Unit> var2) {
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
                  final <unrepresentable><T> this$0;

                  {
                     super(var2x);
                     this.this$0 = var1;
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     this.result = var1;
                     this.label |= Integer.MIN_VALUE;
                     return this.this$0.emit(null, this);
                  }
               };
            }

            var var5: Any = var2.result;
            val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (var2.label != 0) {
               if (var2.label != 1) {
                  throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
               }

               ResultKt.throwOnFailure(var5);
            } else {
               ResultKt.throwOnFailure(var5);
               JobKt.ensureActive(var2.getContext());
               var5 = this.$collector;
               var2.label = 1;
               if (((FlowCollector<Object>)var5).emit(var1, var2) === var7) {
                  return var7;
               }
            }

            return Unit.INSTANCE;
         }
      }, var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }
}
