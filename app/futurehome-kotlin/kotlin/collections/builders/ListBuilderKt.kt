package kotlin.collections.builders

import java.util.Arrays

@JvmSynthetic
fun `access$subarrayContentEquals`(var0: Array<Any>, var1: Int, var2: Int, var3: java.util.List): Boolean {
   return subarrayContentEquals(var0, var1, var2, var3);
}

@JvmSynthetic
fun `access$subarrayContentHashCode`(var0: Array<Any>, var1: Int, var2: Int): Int {
   return subarrayContentHashCode(var0, var1, var2);
}

@JvmSynthetic
fun `access$subarrayContentToString`(var0: Array<Any>, var1: Int, var2: Int, var3: java.util.Collection): java.lang.String {
   return subarrayContentToString(var0, var1, var2, var3);
}

internal fun <E> arrayOfUninitializedElements(size: Int): Array<E> {
   if (var0 >= 0) {
      return (E[])(new Object[var0]);
   } else {
      throw new IllegalArgumentException("capacity must be non-negative.".toString());
   }
}

internal fun <T> Array<T>.copyOfUninitializedElements(newSize: Int): Array<T> {
   var0 = Arrays.copyOf(var0, var1);
   return (T[])var0;
}

internal fun <E> Array<E>.resetAt(index: Int) {
   var0[var1] = null;
}

internal fun <E> Array<E>.resetRange(fromIndex: Int, toIndex: Int) {
   while (var1 < var2) {
      resetAt(var0, var1);
      var1++;
   }
}

private fun <T> Array<T>.subarrayContentEquals(offset: Int, length: Int, other: List<*>): Boolean {
   if (var2 != var3.size()) {
      return false;
   } else {
      for (int var4 = 0; var4 < var2; var4++) {
         if (!(var0[var1 + var4] == var3.get(var4))) {
            return false;
         }
      }

      return true;
   }
}

private fun <T> Array<T>.subarrayContentHashCode(offset: Int, length: Int): Int {
   var var4: Int = 1;

   for (int var3 = 0; var3 < var2; var3++) {
      val var6: Any = var0[var1 + var3];
      val var5: Int;
      if (var0[var1 + var3] != null) {
         var5 = var6.hashCode();
      } else {
         var5 = 0;
      }

      var4 = var4 * 31 + var5;
   }

   return var4;
}

private fun <T> Array<out T>.subarrayContentToString(offset: Int, length: Int, thisCollection: Collection<T>): String {
   val var5: StringBuilder = new StringBuilder(var2 * 3 + 2);
   var5.append("[");

   for (int var4 = 0; var4 < var2; var4++) {
      if (var4 > 0) {
         var5.append(", ");
      }

      val var6: Any = var0[var1 + var4];
      if (var0[var1 + var4] === var3) {
         var5.append("(this Collection)");
      } else {
         var5.append(var6);
      }
   }

   var5.append("]");
   val var7: java.lang.String = var5.toString();
   return var7;
}
