package kotlin.collections

internal class CollectionsKt__ReversedViewsKt : CollectionsKt__MutableCollectionsKt {
   open fun CollectionsKt__ReversedViewsKt() {
   }

   @JvmStatic
   public fun <T> List<T>.asReversed(): List<T> {
      return new ReversedListReadOnly(var0);
   }

   @JvmStatic
   public fun <T> MutableList<T>.asReversed(): MutableList<T> {
      return new ReversedList(var0);
   }

   @JvmStatic
   private fun List<*>.reverseElementIndex(index: Int): Int {
      if (var1 >= 0 && var1 <= CollectionsKt.getLastIndex(var0)) {
         return CollectionsKt.getLastIndex(var0) - var1;
      } else {
         val var2: StringBuilder = new StringBuilder("Element index ");
         var2.append(var1);
         var2.append(" must be in range [");
         var2.append(new IntRange(0, CollectionsKt.getLastIndex(var0)));
         var2.append("].");
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }

   @JvmStatic
   private fun List<*>.reverseIteratorIndex(index: Int): Int {
      return CollectionsKt.getLastIndex(var0) - var1;
   }

   @JvmStatic
   private fun List<*>.reversePositionIndex(index: Int): Int {
      if (var1 >= 0 && var1 <= var0.size()) {
         return var0.size() - var1;
      } else {
         val var2: StringBuilder = new StringBuilder("Position index ");
         var2.append(var1);
         var2.append(" must be in range [");
         var2.append(new IntRange(0, var0.size()));
         var2.append("].");
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }
}
