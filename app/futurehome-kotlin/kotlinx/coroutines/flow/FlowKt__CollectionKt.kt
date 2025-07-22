package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl

@JvmSynthetic
internal class FlowKt__CollectionKt {
   @JvmStatic
   public suspend fun <T, C : MutableCollection<in T>> Flow<T>.toCollection(destination: C): C {
      label23: {
         if (var2 is <unrepresentable>) {
            val var4: <unrepresentable> = var2 as <unrepresentable>;
            if ((var2.label and Integer.MIN_VALUE) != 0) {
               var4.label += Integer.MIN_VALUE;
               var2 = var4;
               break label23;
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
               return FlowKt.toCollection(null, null, this as Continuation<? super C>);
            }
         };
      }

      var var5: Any = var2.result;
      val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      if (var2.label != 0) {
         if (var2.label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var1 = var2.L$0 as java.util.Collection;
         ResultKt.throwOnFailure(var5);
      } else {
         ResultKt.throwOnFailure(var5);
         var5 = new FlowCollector(var1) {
            final C $destination;

            {
               this.$destination = (C)var1;
            }

            @Override
            public final Object emit(T var1, Continuation<? super Unit> var2) {
               this.$destination.add(var1);
               return Unit.INSTANCE;
            }
         };
         var2.L$0 = var1;
         var2.label = 1;
         if (var0.collect((FlowCollector)var5, var2) === var7) {
            return var7;
         }
      }

      return var1;
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.toList(destination: MutableList<T> = ...): List<T> {
      return FlowKt.toCollection(var0, var1, var2);
   }

   @JvmStatic
   public suspend fun <T> Flow<T>.toSet(destination: MutableSet<T> = ...): Set<T> {
      return FlowKt.toCollection(var0, var1, var2);
   }
}
