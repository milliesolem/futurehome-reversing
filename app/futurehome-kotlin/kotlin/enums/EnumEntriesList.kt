package kotlin.enums

import java.io.Serializable

private class EnumEntriesList<T extends java.lang.Enum<T>>(vararg entries: Any) : AbstractList<T>, EnumEntries<T>, Serializable {
   private final val entries: Array<Any>

   public open val size: Int
      public open get() {
         return this.entries.length;
      }


   init {
      this.entries = (T[])var1;
   }

   private fun writeReplace(): Any {
      return new EnumEntriesSerializationProxy<>(this.entries);
   }

   public open operator fun contains(element: Any): Boolean {
      val var2: Boolean;
      if (ArraysKt.getOrNull(this.entries, var1.ordinal()) as java.lang.Enum === var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public open operator fun get(index: Int): Any {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.entries.length);
      return this.entries[var1];
   }

   public open fun indexOf(element: Any): Int {
      var var2: Int = var1.ordinal();
      if (ArraysKt.getOrNull(this.entries, var2) as java.lang.Enum != var1) {
         var2 = -1;
      }

      return var2;
   }

   public open fun lastIndexOf(element: Any): Int {
      return this.indexOf(var1);
   }
}
