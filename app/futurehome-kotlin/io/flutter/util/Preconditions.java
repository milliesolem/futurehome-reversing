package io.flutter.util;

public final class Preconditions {
   private Preconditions() {
   }

   public static <T> T checkNotNull(T var0) {
      var0.getClass();
      return (T)var0;
   }

   public static void checkState(boolean var0) {
      if (!var0) {
         throw new IllegalStateException();
      }
   }

   public static void checkState(boolean var0, Object var1) {
      if (!var0) {
         throw new IllegalStateException(String.valueOf(var1));
      }
   }
}
