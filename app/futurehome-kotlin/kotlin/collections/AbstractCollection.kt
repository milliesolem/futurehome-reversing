package kotlin.collections

import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.markers.KMappedMarker

public abstract class AbstractCollection<E> : java.util.Collection<E>, KMappedMarker {
   public abstract val size: Int

   open fun AbstractCollection() {
   }

   @JvmStatic
   fun `toString$lambda$2`(var0: AbstractCollection, var1: Any): java.lang.CharSequence {
      val var2: java.lang.String;
      if (var1 === var0) {
         var2 = "(this Collection)";
      } else {
         var2 = java.lang.String.valueOf(var1);
      }

      return var2;
   }

   override fun add(var1: E): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun addAll(var1: MutableCollection<E>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override operator fun contains(element: Any): Boolean {
      val var4: java.lang.Iterable = this;
      var var2: Boolean = this is java.util.Collection;
      val var3: Boolean = false;
      if (var2 && (var4 as java.util.Collection).isEmpty()) {
         var2 = false;
      } else {
         val var6: java.util.Iterator = var4.iterator();

         while (true) {
            var2 = var3;
            if (!var6.hasNext()) {
               break;
            }

            if (var6.next() == var1) {
               var2 = true;
               break;
            }
         }
      }

      return var2;
   }

   public override fun containsAll(elements: Collection<Any>): Boolean {
      val var4: java.lang.Iterable = var1;
      var var2: Boolean = (var1 as java.util.Collection).isEmpty();
      val var3: Boolean = true;
      if (var2) {
         var2 = true;
      } else {
         val var5: java.util.Iterator = var4.iterator();

         while (true) {
            var2 = var3;
            if (!var5.hasNext()) {
               break;
            }

            if (!this.contains((E)var5.next())) {
               var2 = false;
               break;
            }
         }
      }

      return var2;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract override operator fun iterator(): Iterator<Any> {
   }

   override fun remove(var1: Any): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun removeAll(var1: MutableCollection<Any>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun retainAll(var1: MutableCollection<Any>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   protected override fun toArray(): Array<Any?> {
      return CollectionToArray.toArray(this);
   }

   protected override fun <T> toArray(array: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(this, var1);
   }

   public override fun toString(): String {
      return CollectionsKt.joinToString$default(this, ", ", "[", "]", 0, null, new AbstractCollection$$ExternalSyntheticLambda0(this), 24, null);
   }
}
