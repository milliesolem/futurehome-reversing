package kotlin.coroutines

public interface Continuation<T> {
   public val context: CoroutineContext

   public abstract fun resumeWith(result: Result<Any>) {
   }
}
