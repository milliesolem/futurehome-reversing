package org.checkerframework.checker.formatter.qual;

import j..util.StringJoiner;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.checkerframework.dataflow.qual.Pure;

public enum ConversionCategory {
   CHAR,
   CHAR_AND_INT,
   FLOAT,
   GENERAL,
   INT,
   INT_AND_TIME,
   NULL,
   TIME,
   UNUSED;

   private static final ConversionCategory[] $VALUES;
   public final String chars;
   public final Class<?>[] types;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      Class[] var0 = (Class[])null;
      ConversionCategory var4 = new ConversionCategory("bBhHsS", null);
      GENERAL = var4;
      ConversionCategory var1 = new ConversionCategory("cC", Character.class, Byte.class, Short.class, Integer.class);
      CHAR = var1;
      ConversionCategory var7 = new ConversionCategory("doxX", Byte.class, Short.class, Integer.class, Long.class, BigInteger.class);
      INT = var7;
      ConversionCategory var5 = new ConversionCategory("eEfgGaA", Float.class, Double.class, BigDecimal.class);
      FLOAT = var5;
      ConversionCategory var8 = new ConversionCategory("tT", Long.class, Calendar.class, Date.class);
      TIME = var8;
      ConversionCategory var2 = new ConversionCategory(null, Byte.class, Short.class, Integer.class);
      CHAR_AND_INT = var2;
      ConversionCategory var9 = new ConversionCategory(null, Long.class);
      INT_AND_TIME = var9;
      ConversionCategory var6 = new ConversionCategory(null);
      NULL = var6;
      ConversionCategory var3 = new ConversionCategory(null, null);
      UNUSED = var3;
      $VALUES = new ConversionCategory[]{var4, var1, var7, var5, var8, var2, var9, var6, var3};
   }

   private ConversionCategory(String var3, Class<?>... var4) {
      this.chars = var3;
      if (var4 == null) {
         this.types = var4;
      } else {
         ArrayList var6 = new ArrayList(var4.length);

         for (Class var8 : var4) {
            var6.add(var8);
            Class var9 = unwrapPrimitive(var8);
            if (var9 != null) {
               var6.add(var9);
            }
         }

         this.types = var6.toArray(new Class[var6.size()]);
      }
   }

   private static <E> Set<E> arrayToSet(E[] var0) {
      return new HashSet<>(Arrays.asList((E[])var0));
   }

   public static ConversionCategory fromConversionChar(char var0) {
      ConversionCategory var6 = GENERAL;
      int var1 = 0;
      ConversionCategory var2 = CHAR;
      ConversionCategory var4 = INT;
      ConversionCategory var3 = FLOAT;

      for (ConversionCategory var7 = TIME; var1 < 5; var1++) {
         ConversionCategory var5 = new ConversionCategory[]{var6, var2, var4, var3, var7}[var1];
         if (var5.chars.contains(String.valueOf(var0))) {
            return var5;
         }
      }

      StringBuilder var8 = new StringBuilder("Bad conversion character ");
      var8.append(var0);
      throw new IllegalArgumentException(var8.toString());
   }

   public static ConversionCategory intersect(ConversionCategory var0, ConversionCategory var1) {
      ConversionCategory var3 = UNUSED;
      if (var0 == var3) {
         return var1;
      } else if (var1 == var3) {
         return var0;
      } else {
         var3 = GENERAL;
         if (var0 == var3) {
            return var1;
         } else if (var1 == var3) {
            return var0;
         } else {
            Set var10 = arrayToSet(var0.types);
            var10.retainAll(arrayToSet(var1.types));
            var1 = CHAR;
            int var2 = 0;
            ConversionCategory var8 = INT;
            ConversionCategory var5 = FLOAT;
            var3 = TIME;
            ConversionCategory var6 = CHAR_AND_INT;
            ConversionCategory var9 = INT_AND_TIME;

            for (ConversionCategory var4 = NULL; var2 < 7; var2++) {
               ConversionCategory var7 = new ConversionCategory[]{var1, var8, var5, var3, var6, var9, var4}[var2];
               if (arrayToSet(var7.types).equals(var10)) {
                  return var7;
               }
            }

            throw new RuntimeException();
         }
      }
   }

   public static boolean isSubsetOf(ConversionCategory var0, ConversionCategory var1) {
      boolean var2;
      if (intersect(var0, var1) == var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static ConversionCategory union(ConversionCategory var0, ConversionCategory var1) {
      ConversionCategory var4 = UNUSED;
      ConversionCategory var3 = var4;
      if (var0 != var4) {
         if (var1 == var4) {
            var3 = var4;
         } else {
            var4 = GENERAL;
            var3 = var4;
            if (var0 != var4) {
               if (var1 != var4) {
                  var4 = CHAR_AND_INT;
                  if (var0 != var4 || var1 != INT_AND_TIME) {
                     var3 = INT_AND_TIME;
                     if (var0 != var3 || var1 != var4) {
                        Set var10 = arrayToSet(var0.types);
                        var10.addAll(arrayToSet(var1.types));
                        ConversionCategory var7 = NULL;
                        int var2 = 0;
                        var1 = CHAR;
                        ConversionCategory var5 = INT;
                        ConversionCategory var6 = FLOAT;

                        for (ConversionCategory var8 = TIME; var2 < 7; var2++) {
                           ConversionCategory var9 = new ConversionCategory[]{var7, var4, var3, var1, var5, var6, var8}[var2];
                           if (arrayToSet(var9.types).equals(var10)) {
                              return var9;
                           }
                        }

                        return GENERAL;
                     }
                  }

                  return INT;
               }

               var3 = var4;
            }
         }
      }

      return var3;
   }

   private static Class<? extends Object> unwrapPrimitive(Class<?> var0) {
      if (var0 == Byte.class) {
         return byte.class;
      } else if (var0 == Character.class) {
         return char.class;
      } else if (var0 == Short.class) {
         return short.class;
      } else if (var0 == Integer.class) {
         return int.class;
      } else if (var0 == Long.class) {
         return long.class;
      } else if (var0 == Float.class) {
         return float.class;
      } else if (var0 == Double.class) {
         return double.class;
      } else {
         return var0 == Boolean.class ? boolean.class : null;
      }
   }

   public boolean isAssignableFrom(Class<?> var1) {
      if (this.types == null) {
         return true;
      } else if (var1 == void.class) {
         return true;
      } else {
         Class[] var4 = this.types;
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            if (var4[var2].isAssignableFrom(var1)) {
               return true;
            }
         }

         return false;
      }
   }

   @Pure
   @Override
   public String toString() {
      StringBuilder var3 = new StringBuilder();
      var3.append(this.name());
      var3.append(" conversion category");
      Class[] var4 = this.types;
      if (var4 != null && var4.length != 0) {
         StringJoiner var6 = new StringJoiner(", ", "(one of: ", ")");
         Class[] var5 = this.types;
         int var2 = var5.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var6.add(var5[var1].getSimpleName());
         }

         var3.append(" ");
         var3.append(var6);
         return var3.toString();
      } else {
         return var3.toString();
      }
   }
}
