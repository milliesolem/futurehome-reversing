package kotlin

public interface Lazy<T> {
   public val value: Any

   public abstract fun isInitialized(): Boolean {
   }
}
