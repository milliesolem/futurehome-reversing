package kotlin.collections

import kotlin.jvm.internal.markers.KMappedMarker

public abstract class AbstractSet<E> : AbstractCollection<E>, java.util.Set<E>, KMappedMarker {
   open fun AbstractSet() {
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 === this) {
         return true;
      } else {
         return var1 is java.util.Set && Companion.setEquals$kotlin_stdlib(this, var1 as MutableSet<*>);
      }
   }

   public override fun hashCode(): Int {
      return Companion.unorderedHashCode$kotlin_stdlib(this);
   }

   override fun iterator(): MutableIterator<E> {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   internal companion object {
      internal fun setEquals(c: Set<*>, other: Set<*>): Boolean {
         return var1.size() == var2.size() && var1.containsAll(var2);
      }

      internal fun unorderedHashCode(c: Collection<*>): Int {
         val var4: java.util.Iterator = var1.iterator();
         var var2: Int = 0;

         while (var4.hasNext()) {
            val var5: Any = var4.next();
            val var3: Int;
            if (var5 != null) {
               var3 = var5.hashCode();
            } else {
               var3 = 0;
            }

            var2 += var3;
         }

         return var2;
      }
   }
}
