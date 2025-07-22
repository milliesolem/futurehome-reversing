package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.Boxing
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref

@JvmSynthetic
internal class FlowKt__CountKt {
   @JvmStatic
   public suspend fun <T> Flow<T>.count(): Int {
      label25: {
         if (var1 is <unrepresentable>) {
            val var3: <unrepresentable> = var1 as <unrepresentable>;
            if ((var1.label and Integer.MIN_VALUE) != 0) {
               var3.label += Integer.MIN_VALUE;
               var1 = var3;
               break label25;
            }
         }

         var1 = new ContinuationImpl(var1) {
            Object L$0;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return FlowKt.count(null, this);
            }
         };
      }

      var var8: Any = var1.result;
      val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var6: Ref.IntRef;
      if (var1.label != 0) {
         if (var1.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var6 = var1.L$0 as Ref.IntRef;
         ResultKt.throwOnFailure(var8);
      } else {
         ResultKt.throwOnFailure(var8);
         var8 = new Ref.IntRef();
         val var5: FlowCollector = new FlowCollector((Ref.IntRef)var8) {
            final Ref.IntRef $i;

            {
               this.$i = var1;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               this.$i.element++;
               val var3: Int = this.$i.element;
               return Unit.INSTANCE;
            }
         };
         var1.L$0 = var8;
         var1.label = 1;
         if (var0.collect(var5, var1) === var4) {
            return var4;
         }

         var6 = (Ref.IntRef)var8;
      }

      return Boxing.boxInt(var6.element);
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.count(predicate: (T, Continuation<Boolean>) -> Any?): Int {
      label25: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label25;
            }
         }

         var2 = new ContinuationImpl(var2) {
            Object L$0;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return FlowKt.count(null, null, this);
            }
         };
      }

      var var9: Any = var2.result;
      val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var6: Ref.IntRef;
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var6 = var2.L$0 as Ref.IntRef;
         ResultKt.throwOnFailure(var9);
      } else {
         ResultKt.throwOnFailure(var9);
         var9 = new Ref.IntRef();
         val var7: FlowCollector = new FlowCollector(var1, (Ref.IntRef)var9) {
            final Ref.IntRef $i;
            final Function2<T, Continuation<? super java.lang.Boolean>, Object> $predicate;

            {
               this.$predicate = var1;
               this.$i = var2;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               label29: {
                  if (var2 is <unrepresentable>) {
                     val var4: <unrepresentable> = var2 as <unrepresentable>;
                     if ((var2.label and Integer.MIN_VALUE) != 0) {
                        var4.label += Integer.MIN_VALUE;
                        var2 = var4;
                        break label29;
                     }
                  }

                  var2 = new ContinuationImpl(this, var2) {
                     Object L$0;
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

               var var11: Function2 = (Function2)var2.result;
               val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               val var8: Any;
               if (var2.label != 0) {
                  if (var2.label != 1) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  var1 = var2.L$0 as <unrepresentable>;
                  ResultKt.throwOnFailure(var11);
                  var8 = var11;
               } else {
                  ResultKt.throwOnFailure(var11);
                  var11 = this.$predicate;
                  var2.L$0 = this;
                  var2.label = 1;
                  var8 = var11.invoke(var1, var2);
                  if (var8 === var5) {
                     return var5;
                  }

                  var1 = this;
               }

               if (var8 as java.lang.Boolean) {
                  var1.$i.element++;
                  val var10: Int = var1.$i.element;
               }

               return Unit.INSTANCE;
            }
         };
         var2.L$0 = var9;
         var2.label = 1;
         if (var0.collect(var7, var2) === var5) {
            return var5;
         }

         var6 = (Ref.IntRef)var9;
      }

      return Boxing.boxInt(var6.element);
   }
}
