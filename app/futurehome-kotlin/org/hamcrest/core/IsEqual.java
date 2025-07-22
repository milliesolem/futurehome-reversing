package org.hamcrest.core;

import java.lang.reflect.Array;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsEqual<T> extends BaseMatcher<T> {
   private final Object expectedValue;

   public IsEqual(T var1) {
      this.expectedValue = var1;
   }

   private static boolean areArrayElementsEqual(Object var0, Object var1) {
      for (int var2 = 0; var2 < Array.getLength(var0); var2++) {
         if (!areEqual(Array.get(var0, var2), Array.get(var1, var2))) {
            return false;
         }
      }

      return true;
   }

   private static boolean areArrayLengthsEqual(Object var0, Object var1) {
      boolean var2;
      if (Array.getLength(var0) == Array.getLength(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static boolean areArraysEqual(Object var0, Object var1) {
      boolean var2;
      if (areArrayLengthsEqual(var0, var1) && areArrayElementsEqual(var0, var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static boolean areEqual(Object var0, Object var1) {
      boolean var2 = true;
      boolean var3 = true;
      if (var0 == null) {
         if (var1 == null) {
            var2 = var3;
         } else {
            var2 = false;
         }

         return var2;
      } else if (var1 != null && isArray(var0)) {
         if (!isArray(var1) || !areArraysEqual(var0, var1)) {
            var2 = false;
         }

         return var2;
      } else {
         return var0.equals(var1);
      }
   }

   @Factory
   public static <T> Matcher<T> equalTo(T var0) {
      return new IsEqual<>((T)var0);
   }

   private static boolean isArray(Object var0) {
      return var0.getClass().isArray();
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendValue(this.expectedValue);
   }

   @Override
   public boolean matches(Object var1) {
      return areEqual(var1, this.expectedValue);
   }
}
