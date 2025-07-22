package kotlin

public sealed class DeepRecursiveScope<T, R> protected constructor() {
   public abstract suspend fun callRecursive(value: Any): Any {
   }

   public abstract suspend fun <U, S> DeepRecursiveFunction<U, S>.callRecursive(value: U): S {
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "'invoke' should not be called from DeepRecursiveScope. Use 'callRecursive' to do recursion in the heap instead of the call stack.", replaceWith = @ReplaceWith(expression = "this.callRecursive(value)", imports = []))
   public operator fun DeepRecursiveFunction<*, *>.invoke(value: Any?): Nothing {
      throw new UnsupportedOperationException("Should not be called from DeepRecursiveScope");
   }
}
