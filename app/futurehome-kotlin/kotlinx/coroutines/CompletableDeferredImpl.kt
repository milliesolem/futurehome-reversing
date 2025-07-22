package kotlinx.coroutines

import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.selects.SelectClause1

private class CompletableDeferredImpl<T>(parent: Job?) : JobSupport(true), CompletableDeferred<T> {
   public open val onAwait: SelectClause1<Any>
      public open get() {
         val var1: SelectClause1 = this.getOnAwaitInternal();
         return var1;
      }


   internal open val onCancelComplete: Boolean
      internal open get() {
         return true;
      }


   init {
      this.initParentJob(var1);
   }

   public override suspend fun await(): Any {
      val var2: Any = this.awaitInternal(var1);
      IntrinsicsKt.getCOROUTINE_SUSPENDED();
      return var2;
   }

   public override fun complete(value: Any): Boolean {
      return this.makeCompleting$kotlinx_coroutines_core(var1);
   }

   public override fun completeExceptionally(exception: Throwable): Boolean {
      return this.makeCompleting$kotlinx_coroutines_core(new CompletedExceptionally(var1, false, 2, null));
   }

   public override fun getCompleted(): Any {
      return (T)this.getCompletedInternal$kotlinx_coroutines_core();
   }
}
