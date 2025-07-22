package kotlinx.coroutines.selects

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function1

public suspend inline fun whileSelect(crossinline builder: (SelectBuilder<Boolean>) -> Unit) {
   label24: {
      if (var1 is <unrepresentable>) {
         val var3: <unrepresentable> = var1 as <unrepresentable>;
         if ((var1.label and Integer.MIN_VALUE) != 0) {
            var3.label += Integer.MIN_VALUE;
            var1 = var3;
            break label24;
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
            return WhileSelectKt.whileSelect(null, this);
         }
      };
   }

   var var7: SelectImplementation = (SelectImplementation)var1.result;
   val var5: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   if (var1.label != 0) {
      if (var1.label != 1) {
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      var0 = var1.L$0 as Function1;
      ResultKt.throwOnFailure(var7);
      if (!var7 as java.lang.Boolean) {
         return Unit.INSTANCE;
      }
   } else {
      ResultKt.throwOnFailure(var7);
   }

   val var4: Any;
   do {
      var7 = new SelectImplementation(var1.getContext());
      var0.invoke(var7);
      var1.L$0 = var0;
      var1.label = 1;
      var4 = var7.doSelect(var1);
      if (var4 === var5) {
         return var5;
      }
   } while ((java.lang.Boolean)var4);

   return Unit.INSTANCE;
}

fun `whileSelect$$forInline`(var0: (SelectBuilder<? super java.lang.Boolean>?) -> Unit, var1: Continuation<? super Unit>): Any {
   val var2: SelectImplementation = new SelectImplementation;
   throw new NullPointerException();
}
