package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.internal.ScopeCoroutine

internal fun SafeCollector<*>.checkContext(currentContext: CoroutineContext) {
   if (var1.fold(
            0,
            (
               new Function2<Integer, CoroutineContext.Element, Integer>(var0) {
                  final SafeCollector<?> $this_checkContext;

                  {
                     super(2);
                     this.$this_checkContext = var1;
                  }

                  public final Integer invoke(int var1, CoroutineContext.Element var2) {
                     val var4: CoroutineContext.Key = var2.getKey();
                     val var3: CoroutineContext.Element = this.$this_checkContext.collectContext.get(var4);
                     if (var4 != Job.Key) {
                        if (var2 != var3) {
                           var1 = Integer.MIN_VALUE;
                        } else {
                           var1++;
                        }

                        return var1;
                     } else {
                        val var7: Job = var3 as Job;
                        val var8: Job = SafeCollector_commonKt.transitiveCoroutineParent(var2 as Job, var7);
                        if (var8 === var7) {
                           if (var7 != null) {
                              var1++;
                           }

                           return var1;
                        } else {
                           val var6: StringBuilder = new StringBuilder(
                              "Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of "
                           );
                           var6.append(var8);
                           var6.append(", expected child of ");
                           var6.append(var7);
                           var6.append(
                              ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'"
                           );
                           throw new IllegalStateException(var6.toString().toString());
                        }
                     }
                  }
               }
            ) as (Int?, CoroutineContext.Element?) -> Int
         )
         .intValue()
      != var0.collectContextSize) {
      val var2: StringBuilder = new StringBuilder("Flow invariant is violated:\n\t\tFlow was collected in ");
      var2.append(var0.collectContext);
      var2.append(",\n\t\tbut emission happened in ");
      var2.append(var1);
      var2.append(".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead");
      throw new IllegalStateException(var2.toString().toString());
   }
}

internal tailrec fun Job?.transitiveCoroutineParent(collectJob: Job?): Job? {
   while (var0 != null) {
      if (var0 === var1) {
         return var0;
      }

      if (var0 !is ScopeCoroutine) {
         return var0;
      }

      var0 = var0.getParent();
   }

   return null;
}

internal inline fun <T> unsafeFlow(crossinline block: (FlowCollector<T>, Continuation<Unit>) -> Any?): Flow<T> {
   return new Flow<T>(var0) {
      final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> $block;

      {
         this.$block = var1;
      }

      @Override
      public Object collect(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
         val var3: Any = this.$block.invoke(var1, var2);
         return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
      }

      public Object collect$$forInline(FlowCollector<? super T> var1, Continuation<? super Unit> var2) {
         new ContinuationImpl(this, var2) {
            int label;
            Object result;
            final <unrepresentable> this$0;

            {
               super(var2x);
               this.this$0 = var1;
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return this.this$0.collect(null, this);
            }
         };
         this.$block.invoke(var1, var2);
         return Unit.INSTANCE;
      }
   };
}
