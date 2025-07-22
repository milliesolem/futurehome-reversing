package kotlinx.coroutines.internal

import java.util.ArrayList
import kotlinx.coroutines.DebugKt

@JvmInline
internal inline class InlineList<E> {
   private final val holder: Any?

   @JvmStatic
   fun <E> `constructor-impl`(var0: Any): Any {
      return var0;
   }

   @JvmStatic
   public open operator fun equals(other: Any?): Boolean {
      if (var1 !is InlineList) {
         return false;
      } else {
         return var0 == (var1 as InlineList).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: Any, var1: Any): Boolean {
      return var0 == var1;
   }

   @JvmStatic
   public inline fun forEachReversed(action: (Any) -> Unit) {
      if (var0 != null) {
         if (var0 !is ArrayList) {
            var1.invoke(var0);
         } else {
            var0 = var0;

            for (int var2 = var0.size() - 1; -1 < var2; var2--) {
               var1.invoke(var0.get(var2));
            }
         }
      }
   }

   @JvmStatic
   public open fun hashCode(): Int {
      val var1: Int;
      if (var0 == null) {
         var1 = 0;
      } else {
         var1 = var0.hashCode();
      }

      return var1;
   }

   @JvmStatic
   public operator fun plus(element: Any): InlineList<Any> {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 is java.util.List) {
         throw new AssertionError();
      } else {
         if (var0 == null) {
            var0 = constructor-impl(var1);
         } else if (var0 is ArrayList) {
            (var0 as ArrayList).add(var1);
            var0 = constructor-impl(var0);
         } else {
            val var2: ArrayList = new ArrayList(4);
            var2.add(var0);
            var2.add(var1);
            var0 = constructor-impl(var2);
         }

         return var0;
      }
   }

   @JvmStatic
   public open fun toString(): String {
      val var1: StringBuilder = new StringBuilder("InlineList(holder=");
      var1.append(var0);
      var1.append(')');
      return var1.toString();
   }

   override fun equals(var1: Any): Boolean {
      return equals-impl(this.holder, var1);
   }

   override fun hashCode(): Int {
      return hashCode-impl(this.holder);
   }

   override fun toString(): java.lang.String {
      return toString-impl(this.holder);
   }
}
