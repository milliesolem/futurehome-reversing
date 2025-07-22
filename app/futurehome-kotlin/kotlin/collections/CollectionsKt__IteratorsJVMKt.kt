package kotlin.collections

import java.util.Enumeration

internal class CollectionsKt__IteratorsJVMKt : CollectionsKt__IterablesKt {
   open fun CollectionsKt__IteratorsJVMKt() {
   }

   @JvmStatic
   public operator fun <T> Enumeration<T>.iterator(): Iterator<T> {
      return new java.util.Iterator<T>(var0) {
         final Enumeration<T> $this_iterator;

         {
            this.$this_iterator = var1;
         }

         @Override
         public boolean hasNext() {
            return this.$this_iterator.hasMoreElements();
         }

         @Override
         public T next() {
            return (T)this.$this_iterator.nextElement();
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }
}
