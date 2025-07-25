package kotlin.jvm.internal;

import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.UninitializedPropertyAccessException;

public class Intrinsics {
   private Intrinsics() {
   }

   public static boolean areEqual(double var0, Double var2) {
      boolean var3;
      if (var2 != null && var0 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static boolean areEqual(float var0, Float var1) {
      boolean var2;
      if (var1 != null && var0 == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(Double var0, double var1) {
      boolean var3;
      if (var0 != null && var0 == var1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static boolean areEqual(Double var0, Double var1) {
      boolean var2 = true;
      if (var0 == null ? var1 != null : var1 == null || var0 != var1) {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(Float var0, float var1) {
      boolean var2;
      if (var0 != null && var0 == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(Float var0, Float var1) {
      boolean var2 = true;
      if (var0 == null ? var1 != null : var1 == null || var0 != var1) {
         var2 = false;
      }

      return var2;
   }

   public static boolean areEqual(Object var0, Object var1) {
      boolean var2;
      if (var0 == null) {
         if (var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = var0.equals(var1);
      }

      return var2;
   }

   public static void checkExpressionValueIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         var0 = new StringBuilder();
         var0.append(var1);
         var0.append(" must not be null");
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var0.toString()));
      }
   }

   public static void checkFieldIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1));
      }
   }

   public static void checkFieldIsNotNull(Object var0, String var1, String var2) {
      if (var0 == null) {
         var0 = new StringBuilder("Field specified as non-null is null: ");
         var0.append(var1);
         var0.append(".");
         var0.append(var2);
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var0.toString()));
      }
   }

   public static void checkHasClass(String var0) throws ClassNotFoundException {
      var0 = var0.replace('/', '.');

      try {
         Class.forName(var0);
      } catch (ClassNotFoundException var3) {
         StringBuilder var2 = new StringBuilder("Class ");
         var2.append(var0);
         var2.append(" is not found. Please update the Kotlin runtime to the latest version");
         throw (ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException(var2.toString(), var3));
      }
   }

   public static void checkHasClass(String var0, String var1) throws ClassNotFoundException {
      var0 = var0.replace('/', '.');

      try {
         Class.forName(var0);
      } catch (ClassNotFoundException var4) {
         StringBuilder var3 = new StringBuilder("Class ");
         var3.append(var0);
         var3.append(" is not found: this code requires the Kotlin runtime of version at least ");
         var3.append(var1);
         throw (ClassNotFoundException)sanitizeStackTrace(new ClassNotFoundException(var3.toString(), var4));
      }
   }

   public static void checkNotNull(Object var0) {
      if (var0 == null) {
         throwJavaNpe();
      }
   }

   public static void checkNotNull(Object var0, String var1) {
      if (var0 == null) {
         throwJavaNpe(var1);
      }
   }

   public static void checkNotNullExpressionValue(Object var0, String var1) {
      if (var0 == null) {
         var0 = new StringBuilder();
         var0.append(var1);
         var0.append(" must not be null");
         throw (NullPointerException)sanitizeStackTrace(new NullPointerException(var0.toString()));
      }
   }

   public static void checkNotNullParameter(Object var0, String var1) {
      if (var0 == null) {
         throwParameterIsNullNPE(var1);
      }
   }

   public static void checkParameterIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throwParameterIsNullIAE(var1);
      }
   }

   public static void checkReturnedValueIsNotNull(Object var0, String var1) {
      if (var0 == null) {
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var1));
      }
   }

   public static void checkReturnedValueIsNotNull(Object var0, String var1, String var2) {
      if (var0 == null) {
         var0 = new StringBuilder("Method specified as non-null returned null: ");
         var0.append(var1);
         var0.append(".");
         var0.append(var2);
         throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var0.toString()));
      }
   }

   public static int compare(int var0, int var1) {
      byte var2;
      if (var0 < var1) {
         var2 = -1;
      } else if (var0 == var1) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      return var2;
   }

   public static int compare(long var0, long var2) {
      long var6;
      int var4 = (var6 = var0 - var2) == 0L ? 0 : (var6 < 0L ? -1 : 1);
      byte var5;
      if (var4 < 0) {
         var5 = -1;
      } else if (var4 == 0) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      return var5;
   }

   private static String createParameterIsNullExceptionMessage(String var0) {
      StackTraceElement[] var4 = Thread.currentThread().getStackTrace();
      String var3 = Intrinsics.class.getName();
      int var1 = 0;

      while (true) {
         int var2 = var1;
         if (var4[var1].getClassName().equals(var3)) {
            while (var4[var2].getClassName().equals(var3)) {
               var2++;
            }

            StackTraceElement var7 = var4[var2];
            var3 = var7.getClassName();
            String var5 = var7.getMethodName();
            StringBuilder var8 = new StringBuilder("Parameter specified as non-null is null: method ");
            var8.append(var3);
            var8.append(".");
            var8.append(var5);
            var8.append(", parameter ");
            var8.append(var0);
            return var8.toString();
         }

         var1++;
      }
   }

   public static void needClassReification() {
      throwUndefinedForReified();
   }

   public static void needClassReification(String var0) {
      throwUndefinedForReified(var0);
   }

   public static void reifiedOperationMarker(int var0, String var1) {
      throwUndefinedForReified();
   }

   public static void reifiedOperationMarker(int var0, String var1, String var2) {
      throwUndefinedForReified(var2);
   }

   private static <T extends Throwable> T sanitizeStackTrace(T var0) {
      return sanitizeStackTrace((T)var0, Intrinsics.class.getName());
   }

   static <T extends Throwable> T sanitizeStackTrace(T var0, String var1) {
      StackTraceElement[] var5 = var0.getStackTrace();
      int var4 = var5.length;
      int var3 = -1;

      for (int var2 = 0; var2 < var4; var2++) {
         if (var1.equals(var5[var2].getClassName())) {
            var3 = var2;
         }
      }

      var0.setStackTrace(Arrays.copyOfRange(var5, var3 + 1, var4));
      return (T)var0;
   }

   public static String stringPlus(String var0, Object var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static void throwAssert() {
      throw (AssertionError)sanitizeStackTrace(new AssertionError());
   }

   public static void throwAssert(String var0) {
      throw (AssertionError)sanitizeStackTrace(new AssertionError(var0));
   }

   public static void throwIllegalArgument() {
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException());
   }

   public static void throwIllegalArgument(String var0) {
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(var0));
   }

   public static void throwIllegalState() {
      throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException());
   }

   public static void throwIllegalState(String var0) {
      throw (IllegalStateException)sanitizeStackTrace(new IllegalStateException(var0));
   }

   public static void throwJavaNpe() {
      throw (NullPointerException)sanitizeStackTrace(new NullPointerException());
   }

   public static void throwJavaNpe(String var0) {
      throw (NullPointerException)sanitizeStackTrace(new NullPointerException(var0));
   }

   public static void throwNpe() {
      throw (KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException());
   }

   public static void throwNpe(String var0) {
      throw (KotlinNullPointerException)sanitizeStackTrace(new KotlinNullPointerException(var0));
   }

   private static void throwParameterIsNullIAE(String var0) {
      throw (IllegalArgumentException)sanitizeStackTrace(new IllegalArgumentException(createParameterIsNullExceptionMessage(var0)));
   }

   private static void throwParameterIsNullNPE(String var0) {
      throw (NullPointerException)sanitizeStackTrace(new NullPointerException(createParameterIsNullExceptionMessage(var0)));
   }

   public static void throwUndefinedForReified() {
      throwUndefinedForReified("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
   }

   public static void throwUndefinedForReified(String var0) {
      throw new UnsupportedOperationException(var0);
   }

   public static void throwUninitializedProperty(String var0) {
      throw (UninitializedPropertyAccessException)sanitizeStackTrace(new UninitializedPropertyAccessException(var0));
   }

   public static void throwUninitializedPropertyAccessException(String var0) {
      StringBuilder var1 = new StringBuilder("lateinit property ");
      var1.append(var0);
      var1.append(" has not been initialized");
      throwUninitializedProperty(var1.toString());
   }

   public static class Kotlin {
      private Kotlin() {
      }
   }
}
