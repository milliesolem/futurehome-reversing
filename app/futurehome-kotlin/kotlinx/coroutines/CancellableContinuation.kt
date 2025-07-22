package kotlinx.coroutines

import kotlin.coroutines.Continuation

public interface CancellableContinuation<T> : Continuation<T> {
   public val isActive: Boolean
   public val isCancelled: Boolean
   public val isCompleted: Boolean

   public abstract fun cancel(cause: Throwable? = ...): Boolean {
   }

   public abstract fun completeResume(token: Any) {
   }

   public abstract fun initCancellability() {
   }

   public abstract fun invokeOnCancellation(handler: (Throwable?) -> Unit) {
   }

   public abstract fun resume(value: Any, onCancellation: ((Throwable) -> Unit)?) {
   }

   public abstract fun CoroutineDispatcher.resumeUndispatched(value: Any) {
   }

   public abstract fun CoroutineDispatcher.resumeUndispatchedWithException(exception: Throwable) {
   }

   public abstract fun tryResume(value: Any, idempotent: Any? = ...): Any? {
   }

   public abstract fun tryResume(value: Any, idempotent: Any?, onCancellation: ((Throwable) -> Unit)?): Any? {
   }

   public abstract fun tryResumeWithException(exception: Throwable): Any? {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
