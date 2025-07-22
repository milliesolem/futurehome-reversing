package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.internal.Ref
import kotlinx.coroutines.flow.internal.NullSurrogateKt

private class DistinctFlowImpl<T>(upstream: Flow<Any>, keySelector: (Any) -> Any?, areEquivalent: (Any?, Any?) -> Boolean) : Flow<T> {
   public final val areEquivalent: (Any?, Any?) -> Boolean
   public final val keySelector: (Any) -> Any?
   private final val upstream: Flow<Any>

   init {
      this.upstream = var1;
      this.keySelector = var2;
      this.areEquivalent = var3;
   }

   public override suspend fun collect(collector: FlowCollector<Any>) {
      val var3: Ref.ObjectRef = new Ref.ObjectRef();
      var3.element = (T)NullSurrogateKt.NULL;
      val var4: Any = this.upstream.collect(new FlowCollector(this, var3, var1) {
         final FlowCollector<T> $collector;
         final Ref.ObjectRef<Object> $previousKey;
         final DistinctFlowImpl<T> this$0;

         {
            this.this$0 = var1;
            this.$previousKey = var2;
            this.$collector = var3;
         }

         @Override
         public final Object emit(T var1, Continuation<? super Unit> var2) {
            label30: {
               if (var2 is <unrepresentable>) {
                  val var4: <unrepresentable> = var2 as <unrepresentable>;
                  if ((var2.label and Integer.MIN_VALUE) != 0) {
                     var4.label += Integer.MIN_VALUE;
                     var2 = var4;
                     break label30;
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
               var5 = this.this$0.keySelector.invoke((T)var1);
               if (this.$previousKey.element != NullSurrogateKt.NULL && this.this$0.areEquivalent.invoke(this.$previousKey.element, var5)) {
                  return Unit.INSTANCE;
               }

               this.$previousKey.element = var5;
               var5 = this.$collector;
               var2.label = 1;
               if (((FlowCollector<Object>)var5).emit(var1, var2) === var7) {
                  return var7;
               }
            }

            return Unit.INSTANCE;
         }
      }, var2);
      return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
   }
}
