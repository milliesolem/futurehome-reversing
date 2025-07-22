package kotlin.collections

internal class CollectionsKt__IteratorsKt : CollectionsKt__IteratorsJVMKt {
   open fun CollectionsKt__IteratorsKt() {
   }

   @JvmStatic
   public inline fun <T> Iterator<T>.forEach(operation: (T) -> Unit) {
      while (var0.hasNext()) {
         var1.invoke(var0.next());
      }
   }

   @JvmStatic
   public inline operator fun <T> Iterator<T>.iterator(): Iterator<T> {
      return var0;
   }

   @JvmStatic
   public fun <T> Iterator<T>.withIndex(): Iterator<IndexedValue<T>> {
      return new IndexingIterator(var0);
   }
}
