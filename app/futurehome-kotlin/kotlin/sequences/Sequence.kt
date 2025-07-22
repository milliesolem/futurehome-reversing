package kotlin.sequences

public interface Sequence<T> {
   public abstract operator fun iterator(): Iterator<Any> {
   }
}
