package org.junit;

import java.util.Arrays;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

public class Assume {
   public static void assumeFalse(String var0, boolean var1) {
      assumeTrue(var0, var1 ^ true);
   }

   public static void assumeFalse(boolean var0) {
      assumeTrue(var0 ^ true);
   }

   public static void assumeNoException(String var0, Throwable var1) {
      assumeThat(var0, var1, CoreMatchers.nullValue());
   }

   public static void assumeNoException(Throwable var0) {
      assumeThat(var0, CoreMatchers.nullValue());
   }

   public static void assumeNotNull(Object... var0) {
      assumeThat(Arrays.asList(var0), CoreMatchers.everyItem(CoreMatchers.notNullValue()));
   }

   public static <T> void assumeThat(T var0, Matcher<T> var1) {
      if (!var1.matches(var0)) {
         throw new AssumptionViolatedException(var0, var1);
      }
   }

   public static <T> void assumeThat(String var0, T var1, Matcher<T> var2) {
      if (!var2.matches(var1)) {
         throw new AssumptionViolatedException(var0, var1, var2);
      }
   }

   public static void assumeTrue(String var0, boolean var1) {
      if (!var1) {
         throw new AssumptionViolatedException(var0);
      }
   }

   public static void assumeTrue(boolean var0) {
      assumeThat(var0, CoreMatchers.is(true));
   }
}
