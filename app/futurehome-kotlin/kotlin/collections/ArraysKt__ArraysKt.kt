package kotlin.collections

import java.util.ArrayList
import java.util.Arrays
import kotlin.collections.unsigned.UArraysKt
import kotlin.contracts.InvocationKind

internal class ArraysKt__ArraysKt : ArraysKt__ArraysJVMKt {
   open fun ArraysKt__ArraysKt() {
   }

   @JvmStatic
   internal fun <T> Array<out T>?.contentDeepEqualsImpl(other: Array<out T>?): Boolean {
      if (var0 === var1) {
         return true;
      } else if (var0 != null && var1 != null && var0.length == var1.length) {
         val var3: Int = var0.length;

         for (int var2 = 0; var2 < var3; var2++) {
            val var5: Any = var0[var2];
            val var4: Any = var1[var2];
            if (var5 != var1[var2]) {
               if (var5 == null || var4 == null) {
                  return false;
               }

               if (var5 is Array<Any> && var4 is Array<Any>) {
                  if (!ArraysKt.contentDeepEquals(var5 as Array<Any>, var4 as Array<Any>)) {
                     return false;
                  }
               } else if (var5 is ByteArray && var4 is ByteArray) {
                  if (!Arrays.equals(var5 as ByteArray, var4 as ByteArray)) {
                     return false;
                  }
               } else if (var5 is ShortArray && var4 is ShortArray) {
                  if (!Arrays.equals(var5 as ShortArray, var4 as ShortArray)) {
                     return false;
                  }
               } else if (var5 is IntArray && var4 is IntArray) {
                  if (!Arrays.equals(var5 as IntArray, var4 as IntArray)) {
                     return false;
                  }
               } else if (var5 is LongArray && var4 is LongArray) {
                  if (!Arrays.equals(var5 as LongArray, var4 as LongArray)) {
                     return false;
                  }
               } else if (var5 is FloatArray && var4 is FloatArray) {
                  if (!Arrays.equals(var5 as FloatArray, var4 as FloatArray)) {
                     return false;
                  }
               } else if (var5 is DoubleArray && var4 is DoubleArray) {
                  if (!Arrays.equals(var5 as DoubleArray, var4 as DoubleArray)) {
                     return false;
                  }
               } else if (var5 is CharArray && var4 is CharArray) {
                  if (!Arrays.equals(var5 as CharArray, var4 as CharArray)) {
                     return false;
                  }
               } else if (var5 is BooleanArray && var4 is BooleanArray) {
                  if (!Arrays.equals(var5 as BooleanArray, var4 as BooleanArray)) {
                     return false;
                  }
               } else if (var5 is UByteArray && var4 is UByteArray) {
                  if (!UArraysKt.contentEquals-kV0jMPg((var5 as UByteArray).unbox-impl(), (var4 as UByteArray).unbox-impl())) {
                     return false;
                  }
               } else if (var5 is UShortArray && var4 is UShortArray) {
                  if (!UArraysKt.contentEquals-FGO6Aew((var5 as UShortArray).unbox-impl(), (var4 as UShortArray).unbox-impl())) {
                     return false;
                  }
               } else if (var5 is UIntArray && var4 is UIntArray) {
                  if (!UArraysKt.contentEquals-KJPZfPQ((var5 as UIntArray).unbox-impl(), (var4 as UIntArray).unbox-impl())) {
                     return false;
                  }
               } else if (var5 is ULongArray && var4 is ULongArray) {
                  if (!UArraysKt.contentEquals-lec5QzE((var5 as ULongArray).unbox-impl(), (var4 as ULongArray).unbox-impl())) {
                     return false;
                  }
               } else if (!(var5 == var4)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @JvmStatic
   internal fun <T> Array<out T>?.contentDeepToStringImpl(): String {
      if (var0 == null) {
         return "null";
      } else {
         val var1: StringBuilder = new StringBuilder(RangesKt.coerceAtMost(var0.length, 429496729) * 5 + 2);
         contentDeepToStringInternal$ArraysKt__ArraysKt(var0, var1, new ArrayList<>());
         return var1.toString();
      }
   }

   @JvmStatic
   private fun <T> Array<out T>.contentDeepToStringInternal(result: StringBuilder, processed: MutableList<Array<*>>) {
      if (var2.contains(var0)) {
         var1.append("[...]");
      } else {
         var2.add(var0);
         var1.append('[');
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            if (var3 != 0) {
               var1.append(", ");
            }

            val var10: Any = var0[var3];
            if (var0[var3] == null) {
               var1.append("null");
            } else if (var10 is Array<Any>) {
               contentDeepToStringInternal$ArraysKt__ArraysKt(var10 as Array<Any>, var1, var2);
            } else if (var10 is ByteArray) {
               val var11: java.lang.String = Arrays.toString(var10 as ByteArray);
               var1.append(var11);
            } else if (var10 is ShortArray) {
               val var12: java.lang.String = Arrays.toString(var10 as ShortArray);
               var1.append(var12);
            } else if (var10 is IntArray) {
               val var13: java.lang.String = Arrays.toString(var10 as IntArray);
               var1.append(var13);
            } else if (var10 is LongArray) {
               val var14: java.lang.String = Arrays.toString(var10 as LongArray);
               var1.append(var14);
            } else if (var10 is FloatArray) {
               val var15: java.lang.String = Arrays.toString(var10 as FloatArray);
               var1.append(var15);
            } else if (var10 is DoubleArray) {
               val var16: java.lang.String = Arrays.toString(var10 as DoubleArray);
               var1.append(var16);
            } else if (var10 is CharArray) {
               val var17: java.lang.String = Arrays.toString(var10 as CharArray);
               var1.append(var17);
            } else if (var10 is BooleanArray) {
               val var18: java.lang.String = Arrays.toString(var10 as BooleanArray);
               var1.append(var18);
            } else {
               val var5: Boolean = var10 is UByteArray;
               var var19: LongArray = null;
               if (var5) {
                  val var26: UByteArray = var10 as UByteArray;
                  var var20: ByteArray = null;
                  if (var26 != null) {
                     var20 = var26.unbox-impl();
                  }

                  var1.append(UArraysKt.contentToString-2csIQuQ(var20));
               } else if (var10 is UShortArray) {
                  val var23: UShortArray = var10 as UShortArray;
                  var var21: ShortArray = null;
                  if (var23 != null) {
                     var21 = var23.unbox-impl();
                  }

                  var1.append(UArraysKt.contentToString-d-6D3K8(var21));
               } else if (var10 is UIntArray) {
                  val var24: UIntArray = var10 as UIntArray;
                  var var22: IntArray = null;
                  if (var24 != null) {
                     var22 = var24.unbox-impl();
                  }

                  var1.append(UArraysKt.contentToString-XUkPCBk(var22));
               } else if (var10 is ULongArray) {
                  val var25: ULongArray = var10 as ULongArray;
                  if (var10 as ULongArray != null) {
                     var19 = var25.unbox-impl();
                  }

                  var1.append(UArraysKt.contentToString-uLth9ew(var19));
               } else {
                  var1.append(var10.toString());
               }
            }
         }

         var1.append(']');
         var2.remove(CollectionsKt.getLastIndex(var2));
      }
   }

   @JvmStatic
   public fun <T> Array<out Array<out T>>.flatten(): List<T> {
      val var6: Array<Any> = var0 as Array<Any>;
      val var4: Int = (var0 as Array<Any>).length;
      var var2: Int = 0;

      var var1: Int;
      for (var1 = 0; var2 < var4; var2++) {
         var1 += (var6[var2] as Array<Any>).length;
      }

      val var5: ArrayList = new ArrayList(var1);
      var2 = var6.length;

      for (int var7 = 0; var7 < var2; var7++) {
         CollectionsKt.addAll(var5, var0[var7]);
      }

      return var5;
   }

   @JvmStatic
   public inline fun <C, R> C.ifEmpty(defaultValue: () -> R): R where C : Array<*>, C : R {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      var var2: Any = var0;
      if (var0.length == 0) {
         var2 = var1.invoke();
      }

      return (R)var2;
   }

   @JvmStatic
   public inline fun Array<*>?.isNullOrEmpty(): Boolean {
      contract {
         returns(false) implies (this != null)
      }

      val var1: Boolean;
      if (var0 != null && var0.length != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @JvmStatic
   public fun <T, R> Array<out Pair<T, R>>.unzip(): Pair<List<T>, List<R>> {
      val var4: ArrayList = new ArrayList(var0.length);
      val var5: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         val var3: Pair = var0[var1];
         var4.add(var0[var1].getFirst());
         var5.add(var3.getSecond());
      }

      return TuplesKt.to(var4, var5);
   }
}
