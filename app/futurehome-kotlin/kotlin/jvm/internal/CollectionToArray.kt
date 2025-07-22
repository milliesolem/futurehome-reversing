package kotlin.jvm.internal

import java.util.Arrays

private final val EMPTY: Array<Any?>
private const val MAX_SIZE: Int = 2147483645
private Object[] EMPTY = new Object[0];

@Deprecated(message = "This function will be made internal in a future release")
@DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.9")
public fun collectionToArray(collection: Collection<*>): Array<Any?> {
   var var1: Int = var0.size();
   if (var1 != 0) {
      val var5: java.util.Iterator = var0.iterator();
      if (var5.hasNext()) {
         var var6: Array<Any> = new Object[var1];
         var1 = 0;

         while (true) {
            val var2: Int = var1 + 1;
            var6[var1] = var5.next();
            val var4: Array<Any>;
            if (var2 >= var6.length) {
               if (!var5.hasNext()) {
                  return var6;
               }

               val var3: Int = var2 * 3 + 1 ushr 1;
               var1 = var2 * 3 + 1 ushr 1;
               if (var3 <= var2) {
                  var1 = 2147483645;
                  if (var2 >= 2147483645) {
                     throw new OutOfMemoryError();
                  }
               }

               var4 = Arrays.copyOf(var6, var1);
            } else {
               var4 = var6;
               if (!var5.hasNext()) {
                  val var9: Array<Any> = Arrays.copyOf(var6, var2);
                  return var9;
               }
            }

            var1 = var2;
            var6 = var4;
         }
      }
   }

   return EMPTY;
}

@Deprecated(message = "This function will be made internal in a future release")
@DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.9")
public fun collectionToArray(collection: Collection<*>, a: Array<Any?>?): Array<Any?> {
   var1.getClass();
   var var3: Int = var0.size();
   var var2: Int = 0;
   var var7: Array<Any>;
   if (var3 == 0) {
      var7 = var1;
      if (var1.length > 0) {
         var1[0] = null;
         var7 = var1;
      }
   } else {
      val var6: java.util.Iterator = var0.iterator();
      if (!var6.hasNext()) {
         var7 = var1;
         if (var1.length > 0) {
            var1[0] = null;
            var7 = var1;
         }
      } else {
         if (var3 <= var1.length) {
            var7 = var1;
         } else {
            val var8: Any = java.lang.reflect.Array.newInstance(var1.getClass().getComponentType(), var3);
            var7 = var8 as Array<Any>;
         }

         while (true) {
            var3 = var2 + 1;
            var7[var2] = var6.next();
            val var5: Array<Any>;
            if (var3 >= var7.length) {
               if (!var6.hasNext()) {
                  break;
               }

               val var4: Int = var3 * 3 + 1 ushr 1;
               var2 = var3 * 3 + 1 ushr 1;
               if (var4 <= var3) {
                  var2 = 2147483645;
                  if (var3 >= 2147483645) {
                     throw new OutOfMemoryError();
                  }
               }

               var5 = Arrays.copyOf(var7, var2);
            } else {
               var5 = var7;
               if (!var6.hasNext()) {
                  if (var7 === var1) {
                     var1[var3] = null;
                     var7 = var1;
                  } else {
                     var7 = Arrays.copyOf(var7, var3);
                  }
                  break;
               }
            }

            var2 = var3;
            var7 = var5;
         }
      }
   }

   return var7;
}

private inline fun toArrayImpl(collection: Collection<*>, empty: () -> Array<Any?>, alloc: (Int) -> Array<Any?>, trim: (Array<Any?>, Int) -> Array<Any?>): Array<
      Any?
   > {
   var var4: Int = var0.size();
   if (var4 == 0) {
      return var1.invoke() as Array<Any>;
   } else {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         return var1.invoke() as Array<Any>;
      } else {
         var var8: Array<Any> = var2.invoke(var4) as Array<Any>;
         var4 = 0;

         while (true) {
            val var5: Int = var4 + 1;
            var8[var4] = var7.next();
            val var9: Array<Any>;
            if (var5 >= var8.length) {
               if (!var7.hasNext()) {
                  return var8;
               }

               val var6: Int = var5 * 3 + 1 ushr 1;
               var4 = var5 * 3 + 1 ushr 1;
               if (var6 <= var5) {
                  var4 = 2147483645;
                  if (var5 >= 2147483645) {
                     throw new OutOfMemoryError();
                  }
               }

               var9 = Arrays.copyOf(var8, var4);
            } else {
               var9 = var8;
               if (!var7.hasNext()) {
                  return var3.invoke(var8, var5) as Array<Any>;
               }
            }

            var4 = var5;
            var8 = var9;
         }
      }
   }
}
