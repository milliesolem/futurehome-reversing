package io.reactivex.internal.functions;

import io.reactivex.functions.BiPredicate;

public final class ObjectHelper {
   static final BiPredicate<Object, Object> EQUALS = new ObjectHelper.BiObjectPredicate();

   private ObjectHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static int compare(int var0, int var1) {
      byte var2;
      if (var0 < var1) {
         var2 = -1;
      } else if (var0 > var1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      return var2;
   }

   public static int compare(long var0, long var2) {
      long var6;
      int var4 = (var6 = var0 - var2) == 0L ? 0 : (var6 < 0L ? -1 : 1);
      byte var5;
      if (var4 < 0) {
         var5 = -1;
      } else if (var4 > 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      return var5;
   }

   public static boolean equals(Object var0, Object var1) {
      boolean var2;
      if (var0 == var1 || var0 != null && var0.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static <T> BiPredicate<T, T> equalsPredicate() {
      return (BiPredicate<T, T>)EQUALS;
   }

   public static int hashCode(Object var0) {
      int var1;
      if (var0 != null) {
         var1 = var0.hashCode();
      } else {
         var1 = 0;
      }

      return var1;
   }

   @Deprecated
   public static long requireNonNull(long var0, String var2) {
      StringBuilder var3 = new StringBuilder("Null check on a primitive: ");
      var3.append(var2);
      throw new InternalError(var3.toString());
   }

   public static <T> T requireNonNull(T var0, String var1) {
      if (var0 != null) {
         return (T)var0;
      } else {
         throw new NullPointerException(var1);
      }
   }

   public static int verifyPositive(int var0, String var1) {
      if (var0 > 0) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" > 0 required but it was ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public static long verifyPositive(long var0, String var2) {
      if (var0 > 0L) {
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var2);
         var3.append(" > 0 required but it was ");
         var3.append(var0);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   static final class BiObjectPredicate implements BiPredicate<Object, Object> {
      @Override
      public boolean test(Object var1, Object var2) {
         return ObjectHelper.equals(var1, var2);
      }
   }
}
