package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

public abstract class AbstractCoroutine<T> : JobSupport, Job, Continuation<T>, CoroutineScope {
   public final val context: CoroutineContext

   public open val coroutineContext: CoroutineContext
      public open get() {
         return this.context;
      }


   public open val isActive: Boolean
      public open get() {
         return super.isActive();
      }


   open fun AbstractCoroutine(var1: CoroutineContext, var2: Boolean, var3: Boolean) {
      super(var3);
      if (var2) {
         this.initParentJob(var1.get(Job.Key));
      }

      this.context = var1.plus(this);
   }

   protected open fun afterResume(state: Any?) {
      this.afterCompletion(var1);
   }

   protected override fun cancellationExceptionMessage(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(DebugStringsKt.getClassSimpleName(this));
      var1.append(" was cancelled");
      return var1.toString();
   }

   internal override fun handleOnCompletionException(exception: Throwable) {
      CoroutineExceptionHandlerKt.handleCoroutineException(this.context, var1);
   }

   internal override fun nameString(): String {
      val var1: java.lang.String = CoroutineContextKt.getCoroutineName(this.context);
      if (var1 == null) {
         return super.nameString$kotlinx_coroutines_core();
      } else {
         val var2: StringBuilder = new StringBuilder("\"");
         var2.append(var1);
         var2.append("\":");
         var2.append(super.nameString$kotlinx_coroutines_core());
         return var2.toString();
      }
   }

   protected open fun onCancelled(cause: Throwable, handled: Boolean) {
   }

   protected open fun onCompleted(value: Any) {
   }

   protected override fun onCompletionInternal(state: Any?) {
      if (var1 is CompletedExceptionally) {
         this.onCancelled((var1 as CompletedExceptionally).cause, (var1 as CompletedExceptionally).getHandled());
      } else {
         this.onCompleted((T)var1);
      }
   }

   public override fun resumeWith(result: Result<Any>) {
      var1 = this.makeCompletingOnce$kotlinx_coroutines_core(CompletionStateKt.toState$default(var1, null, 1, null));
      if (var1 != JobSupportKt.COMPLETING_WAITING_CHILDREN) {
         this.afterResume(var1);
      }
   }

   public fun <R> start(start: CoroutineStart, receiver: R, block: (R, Continuation<Any>) -> Any?) {
      var1.invoke(var3, var2, this);
   }
}
