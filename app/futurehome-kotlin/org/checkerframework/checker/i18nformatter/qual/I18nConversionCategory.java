package org.checkerframework.checker.i18nformatter.qual;

import j..util.StringJoiner;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public enum I18nConversionCategory {
   DATE,
   GENERAL,
   NUMBER,
   UNUSED;

   private static final I18nConversionCategory[] $VALUES;
   static I18nConversionCategory[] namedCategories;
   public final String[] strings;
   public final Class<?>[] types;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      I18nConversionCategory var1 = new I18nConversionCategory(null, null);
      UNUSED = var1;
      I18nConversionCategory var3 = new I18nConversionCategory(null, null);
      GENERAL = var3;
      I18nConversionCategory var0 = new I18nConversionCategory(new Class[]{Date.class, Number.class}, new String[]{"date", "time"});
      DATE = var0;
      I18nConversionCategory var2 = new I18nConversionCategory(new Class[]{Number.class}, new String[]{"number", "choice"});
      NUMBER = var2;
      $VALUES = new I18nConversionCategory[]{var1, var3, var0, var2};
      namedCategories = new I18nConversionCategory[]{var0, var2};
   }

   private I18nConversionCategory(Class<?>[] var3, String[] var4) {
      this.types = var3;
      this.strings = var4;
   }

   private static <E> Set<E> arrayToSet(E[] var0) {
      return new HashSet<>(Arrays.asList((E[])var0));
   }

   public static I18nConversionCategory intersect(I18nConversionCategory var0, I18nConversionCategory var1) {
      I18nConversionCategory var3 = UNUSED;
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
            Set var5 = arrayToSet(var0.types);
            var5.retainAll(arrayToSet(var1.types));
            var3 = DATE;
            int var2 = 0;

            for (I18nConversionCategory var6 = NUMBER; var2 < 2; var2++) {
               I18nConversionCategory var4 = new I18nConversionCategory[]{var3, var6}[var2];
               if (arrayToSet(var4.types).equals(var5)) {
                  return var4;
               }
            }

            throw new RuntimeException();
         }
      }
   }

   public static boolean isSubsetOf(I18nConversionCategory var0, I18nConversionCategory var1) {
      boolean var2;
      if (intersect(var0, var1) == var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static I18nConversionCategory stringToI18nConversionCategory(String var0) {
      var0 = var0.toLowerCase();

      for (I18nConversionCategory var6 : namedCategories) {
         String[] var5 = var6.strings;
         int var4 = var5.length;

         for (int var2 = 0; var2 < var4; var2++) {
            if (var5[var2].equals(var0)) {
               return var6;
            }
         }
      }

      StringBuilder var9 = new StringBuilder("Invalid format type ");
      var9.append(var0);
      throw new IllegalArgumentException(var9.toString());
   }

   public static I18nConversionCategory union(I18nConversionCategory var0, I18nConversionCategory var1) {
      I18nConversionCategory var3 = UNUSED;
      I18nConversionCategory var2 = var3;
      if (var0 != var3) {
         if (var1 == var3) {
            var2 = var3;
         } else {
            var3 = GENERAL;
            var2 = var3;
            if (var0 != var3) {
               if (var1 == var3) {
                  var2 = var3;
               } else {
                  var3 = DATE;
                  var2 = var3;
                  if (var0 != var3) {
                     if (var1 != var3) {
                        return NUMBER;
                     }

                     var2 = var3;
                  }
               }
            }
         }
      }

      return var2;
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

   @Override
   public String toString() {
      StringBuilder var3 = new StringBuilder(this.name());
      if (this.types == null) {
         var3.append(" conversion category (all types)");
      } else {
         StringJoiner var5 = new StringJoiner(", ", " conversion category (one of: ", ")");
         Class[] var4 = this.types;
         int var2 = var4.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var5.add(var4[var1].getCanonicalName());
         }

         var3.append(var5);
      }

      return var3.toString();
   }
}
