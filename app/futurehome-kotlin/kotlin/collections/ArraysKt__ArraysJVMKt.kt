package kotlin.collections

import java.nio.charset.Charset
import java.util.Arrays

internal class ArraysKt__ArraysJVMKt {
   open fun ArraysKt__ArraysJVMKt() {
   }

   @JvmStatic
   internal fun <T> arrayOfNulls(reference: Array<T>, size: Int): Array<T> {
      var0 = java.lang.reflect.Array.newInstance(var0.getClass().getComponentType(), var1);
      return (T[])(var0 as Array<Any>);
   }

   @JvmStatic
   internal fun <T> Array<out T>?.contentDeepHashCodeImpl(): Int {
      return Arrays.deepHashCode(var0);
   }

   @JvmStatic
   internal fun copyOfRangeToIndexCheck(toIndex: Int, size: Int) {
      if (var0 > var1) {
         val var2: StringBuilder = new StringBuilder("toIndex (");
         var2.append(var0);
         var2.append(") is greater than size (");
         var2.append(var1);
         var2.append(").");
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }

   @JvmStatic
   public inline fun ByteArray.toString(charset: Charset): String {
      return new java.lang.String(var0, var1);
   }
}
