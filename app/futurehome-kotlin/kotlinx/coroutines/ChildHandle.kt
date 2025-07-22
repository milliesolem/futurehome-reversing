package kotlinx.coroutines

@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
public interface ChildHandle : DisposableHandle {
   public val parent: Job?

   public abstract fun childCancelled(cause: Throwable): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
