package kotlinx.coroutines

import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt

@JvmSynthetic
internal class JobKt__JobKt {
   public final val isActive: Boolean
      public final get() {
         val var2: Job = var0.get(Job.Key);
         val var1: Boolean;
         if (var2 != null) {
            var1 = var2.isActive();
         } else {
            var1 = true;
         }

         return var1;
      }


   public final val job: Job
      public final get() {
         val var1: Job = var0.get(Job.Key);
         if (var1 != null) {
            return var1;
         } else {
            val var2: StringBuilder = new StringBuilder("Current context doesn't contain Job in it: ");
            var2.append(var0);
            throw new IllegalStateException(var2.toString().toString());
         }
      }


   @JvmStatic
   public fun Job(parent: Job? = null): CompletableJob {
      return new JobImpl(var0);
   }

   @JvmStatic
   public fun CoroutineContext.cancel(cause: CancellationException? = null) {
      val var2: Job = var0.get(Job.Key);
      if (var2 != null) {
         var2.cancel(var1);
      }
   }

   @JvmStatic
   public fun Job.cancel(message: String, cause: Throwable? = null) {
      var0.cancel(ExceptionsKt.CancellationException(var1, var2));
   }

   @JvmStatic
   public suspend fun Job.cancelAndJoin() {
      Job.DefaultImpls.cancel$default(var0, null, 1, null);
      val var2: Any = var0.join(var1);
      return if (var2 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var2 else Unit.INSTANCE;
   }

   @JvmStatic
   public fun CoroutineContext.cancelChildren(cause: CancellationException? = null) {
      val var2: Job = var0.get(Job.Key);
      if (var2 != null) {
         val var3: Sequence = var2.getChildren();
         if (var3 != null) {
            val var4: java.util.Iterator = var3.iterator();

            while (var4.hasNext()) {
               (var4.next() as Job).cancel(var1);
            }
         }
      }
   }

   @JvmStatic
   public fun Job.cancelChildren(cause: CancellationException? = null) {
      val var2: java.util.Iterator = var0.getChildren().iterator();

      while (var2.hasNext()) {
         (var2.next() as Job).cancel(var1);
      }
   }

   @JvmStatic
   internal fun Job.disposeOnCompletion(handle: DisposableHandle): DisposableHandle {
      return var0.invokeOnCompletion(new DisposeOnCompletion(var1));
   }

   @JvmStatic
   public fun CoroutineContext.ensureActive() {
      val var1: Job = var0.get(Job.Key);
      if (var1 != null) {
         JobKt.ensureActive(var1);
      }
   }

   @JvmStatic
   public fun Job.ensureActive() {
      if (!var0.isActive()) {
         throw var0.getCancellationException();
      }
   }

   @JvmStatic
   private fun Throwable?.orCancellation(job: Job): Throwable {
      var var2: java.lang.Throwable = var0;
      if (var0 == null) {
         var2 = new JobCancellationException("Job was cancelled", null, var1);
      }

      return var2;
   }
}
