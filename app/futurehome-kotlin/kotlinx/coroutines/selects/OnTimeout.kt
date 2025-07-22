package kotlinx.coroutines.selects

import kotlin.coroutines.CoroutineContext
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.TypeIntrinsics
import kotlinx.coroutines.DelayKt

private class OnTimeout(timeMillis: Long) {
   public final val selectClause: SelectClause0
      public final get() {
         val var1: <unrepresentable> = <unrepresentable>.INSTANCE;
         return new SelectClause0Impl(this, TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 3) as Function3, null, 4, null);
      }


   private final val timeMillis: Long

   init {
      this.timeMillis = var1;
   }

   private fun register(select: SelectInstance<*>, ignoredParam: Any?) {
      if (this.timeMillis <= 0L) {
         var1.selectInRegistrationPhase(Unit.INSTANCE);
      } else {
         var2 = new Runnable(var1, this) {
            final SelectInstance $select$inlined;
            final OnTimeout this$0;

            {
               this.$select$inlined = var1;
               this.this$0 = var2;
            }

            @Override
            public final void run() {
               this.$select$inlined.trySelect(this.this$0, Unit.INSTANCE);
            }
         };
         val var3: SelectImplementation = var1 as SelectImplementation;
         val var5: CoroutineContext = var1.getContext();
         var1.disposeOnCompletion(DelayKt.getDelay(var5).invokeOnTimeout(this.timeMillis, var2, var5));
      }
   }
}
