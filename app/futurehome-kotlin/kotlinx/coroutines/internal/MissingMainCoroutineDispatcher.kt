package kotlinx.coroutines.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.MainCoroutineDispatcher

private class MissingMainCoroutineDispatcher(cause: Throwable?, errorHint: String? = null) : MainCoroutineDispatcher, Delay {
   private final val cause: Throwable?
   private final val errorHint: String?

   public open val immediate: MainCoroutineDispatcher
      public open get() {
         return this;
      }


   init {
      this.cause = var1;
      this.errorHint = var2;
   }

   private fun missing(): Nothing {
      if (this.cause == null) {
         MainDispatchersKt.throwMissingMainDispatcherException();
         throw new KotlinNothingValueException();
      } else {
         var var3: StringBuilder;
         var var4: java.lang.String;
         label14: {
            var3 = new StringBuilder("Module with the Main dispatcher had failed to initialize");
            var var2: java.lang.String = this.errorHint;
            if (this.errorHint != null) {
               val var1: StringBuilder = new StringBuilder(". ");
               var1.append(var2);
               var2 = var1.toString();
               var4 = var2;
               if (var2 != null) {
                  break label14;
               }
            }

            var4 = "";
         }

         var3.append(var4);
         throw new IllegalStateException(var3.toString(), this.cause);
      }
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
   override fun delay(var1: Long, var3: Continuation<? super Unit>): Any {
      return Delay.DefaultImpls.delay(this, var1, var3);
   }

   public open fun dispatch(context: CoroutineContext, block: Runnable): Nothing {
      this.missing();
      throw new KotlinNothingValueException();
   }

   public override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
      this.missing();
      throw new KotlinNothingValueException();
   }

   public override fun isDispatchNeeded(context: CoroutineContext): Boolean {
      this.missing();
      throw new KotlinNothingValueException();
   }

   public override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
      this.missing();
      throw new KotlinNothingValueException();
   }

   public open fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>): Nothing {
      this.missing();
      throw new KotlinNothingValueException();
   }

   public override fun toString(): String {
      val var2: StringBuilder = new StringBuilder("Dispatchers.Main[missing");
      val var3: java.lang.String;
      if (this.cause != null) {
         val var1: StringBuilder = new StringBuilder(", cause=");
         var1.append(this.cause);
         var3 = var1.toString();
      } else {
         var3 = "";
      }

      var2.append(var3);
      var2.append(']');
      return var2.toString();
   }
}
