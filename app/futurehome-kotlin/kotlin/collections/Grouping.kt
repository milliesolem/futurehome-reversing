package kotlin.collections

public interface Grouping<T, K> {
   public abstract fun keyOf(element: Any): Any {
   }

   public abstract fun sourceIterator(): Iterator<Any> {
   }
}
