package org.junit;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.internal.ArrayComparisonFailure;
import org.junit.internal.ExactComparisonCriteria;
import org.junit.internal.InexactComparisonCriteria;

public class Assert {
   protected Assert() {
   }

   public static void assertArrayEquals(String var0, byte[] var1, byte[] var2) throws ArrayComparisonFailure {
      internalArrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, char[] var1, char[] var2) throws ArrayComparisonFailure {
      internalArrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, double[] var1, double[] var2, double var3) throws ArrayComparisonFailure {
      new InexactComparisonCriteria(var3).arrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, float[] var1, float[] var2, float var3) throws ArrayComparisonFailure {
      new InexactComparisonCriteria(var3).arrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, int[] var1, int[] var2) throws ArrayComparisonFailure {
      internalArrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, long[] var1, long[] var2) throws ArrayComparisonFailure {
      internalArrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, Object[] var1, Object[] var2) throws ArrayComparisonFailure {
      internalArrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, short[] var1, short[] var2) throws ArrayComparisonFailure {
      internalArrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(String var0, boolean[] var1, boolean[] var2) throws ArrayComparisonFailure {
      internalArrayEquals(var0, var1, var2);
   }

   public static void assertArrayEquals(byte[] var0, byte[] var1) {
      assertArrayEquals(null, var0, var1);
   }

   public static void assertArrayEquals(char[] var0, char[] var1) {
      assertArrayEquals(null, var0, var1);
   }

   public static void assertArrayEquals(double[] var0, double[] var1, double var2) {
      assertArrayEquals(null, var0, var1, var2);
   }

   public static void assertArrayEquals(float[] var0, float[] var1, float var2) {
      assertArrayEquals(null, var0, var1, var2);
   }

   public static void assertArrayEquals(int[] var0, int[] var1) {
      assertArrayEquals(null, var0, var1);
   }

   public static void assertArrayEquals(long[] var0, long[] var1) {
      assertArrayEquals(null, var0, var1);
   }

   public static void assertArrayEquals(Object[] var0, Object[] var1) {
      assertArrayEquals(null, var0, var1);
   }

   public static void assertArrayEquals(short[] var0, short[] var1) {
      assertArrayEquals(null, var0, var1);
   }

   public static void assertArrayEquals(boolean[] var0, boolean[] var1) {
      assertArrayEquals(null, var0, var1);
   }

   @Deprecated
   public static void assertEquals(double var0, double var2) {
      assertEquals(null, var0, var2);
   }

   public static void assertEquals(double var0, double var2, double var4) {
      assertEquals(null, var0, var2, var4);
   }

   public static void assertEquals(float var0, float var1, float var2) {
      assertEquals(null, var0, var1, var2);
   }

   public static void assertEquals(long var0, long var2) {
      assertEquals(null, var0, var2);
   }

   public static void assertEquals(Object var0, Object var1) {
      assertEquals(null, var0, var1);
   }

   @Deprecated
   public static void assertEquals(String var0, double var1, double var3) {
      fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
   }

   public static void assertEquals(String var0, double var1, double var3, double var5) {
      if (doubleIsDifferent(var1, var3, var5)) {
         failNotEquals(var0, var1, var3);
      }
   }

   public static void assertEquals(String var0, float var1, float var2, float var3) {
      if (floatIsDifferent(var1, var2, var3)) {
         failNotEquals(var0, var1, var2);
      }
   }

   public static void assertEquals(String var0, long var1, long var3) {
      if (var1 != var3) {
         failNotEquals(var0, var1, var3);
      }
   }

   public static void assertEquals(String var0, Object var1, Object var2) {
      if (!equalsRegardingNull(var1, var2)) {
         if (var1 instanceof String && var2 instanceof String) {
            String var3 = var0;
            if (var0 == null) {
               var3 = "";
            }

            throw new ComparisonFailure(var3, (String)var1, (String)var2);
         } else {
            failNotEquals(var0, var1, var2);
         }
      }
   }

   @Deprecated
   public static void assertEquals(String var0, Object[] var1, Object[] var2) {
      assertArrayEquals(var0, var1, var2);
   }

   @Deprecated
   public static void assertEquals(Object[] var0, Object[] var1) {
      assertArrayEquals(var0, var1);
   }

   public static void assertFalse(String var0, boolean var1) {
      assertTrue(var0, var1 ^ true);
   }

   public static void assertFalse(boolean var0) {
      assertFalse(null, var0);
   }

   public static void assertNotEquals(double var0, double var2, double var4) {
      assertNotEquals(null, var0, var2, var4);
   }

   public static void assertNotEquals(float var0, float var1, float var2) {
      assertNotEquals(null, var0, var1, var2);
   }

   public static void assertNotEquals(long var0, long var2) {
      assertNotEquals(null, var0, var2);
   }

   public static void assertNotEquals(Object var0, Object var1) {
      assertNotEquals(null, var0, var1);
   }

   public static void assertNotEquals(String var0, double var1, double var3, double var5) {
      if (!doubleIsDifferent(var1, var3, var5)) {
         failEquals(var0, var3);
      }
   }

   public static void assertNotEquals(String var0, float var1, float var2, float var3) {
      if (!floatIsDifferent(var1, var2, var3)) {
         failEquals(var0, var2);
      }
   }

   public static void assertNotEquals(String var0, long var1, long var3) {
      if (var1 == var3) {
         failEquals(var0, var3);
      }
   }

   public static void assertNotEquals(String var0, Object var1, Object var2) {
      if (equalsRegardingNull(var1, var2)) {
         failEquals(var0, var2);
      }
   }

   public static void assertNotNull(Object var0) {
      assertNotNull(null, var0);
   }

   public static void assertNotNull(String var0, Object var1) {
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      assertTrue(var0, var2);
   }

   public static void assertNotSame(Object var0, Object var1) {
      assertNotSame(null, var0, var1);
   }

   public static void assertNotSame(String var0, Object var1, Object var2) {
      if (var1 == var2) {
         failSame(var0);
      }
   }

   public static void assertNull(Object var0) {
      assertNull(null, var0);
   }

   public static void assertNull(String var0, Object var1) {
      if (var1 != null) {
         failNotNull(var0, var1);
      }
   }

   public static void assertSame(Object var0, Object var1) {
      assertSame(null, var0, var1);
   }

   public static void assertSame(String var0, Object var1, Object var2) {
      if (var1 != var2) {
         failNotSame(var0, var1, var2);
      }
   }

   public static <T> void assertThat(T var0, Matcher<? super T> var1) {
      assertThat("", var0, var1);
   }

   public static <T> void assertThat(String var0, T var1, Matcher<? super T> var2) {
      MatcherAssert.assertThat(var0, var1, var2);
   }

   public static void assertTrue(String var0, boolean var1) {
      if (!var1) {
         fail(var0);
      }
   }

   public static void assertTrue(boolean var0) {
      assertTrue(null, var0);
   }

   private static boolean doubleIsDifferent(double var0, double var2, double var4) {
      return Double.compare(var0, var2) == 0 ? false : !(Math.abs(var0 - var2) <= var4);
   }

   private static boolean equalsRegardingNull(Object var0, Object var1) {
      if (var0 == null) {
         boolean var2;
         if (var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      } else {
         return isEquals(var0, var1);
      }
   }

   public static void fail() {
      fail(null);
   }

   public static void fail(String var0) {
      if (var0 == null) {
         throw new AssertionError();
      } else {
         throw new AssertionError(var0);
      }
   }

   private static void failEquals(String var0, Object var1) {
      if (var0 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(". ");
         var0 = var2.toString();
      } else {
         var0 = "Values should be different. ";
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var0);
      var4.append("Actual: ");
      var4.append(var1);
      fail(var4.toString());
   }

   private static void failNotEquals(String var0, Object var1, Object var2) {
      fail(format(var0, var1, var2));
   }

   private static void failNotNull(String var0, Object var1) {
      if (var0 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(" ");
         var0 = var2.toString();
      } else {
         var0 = "";
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var0);
      var4.append("expected null, but was:<");
      var4.append(var1);
      var4.append(">");
      fail(var4.toString());
   }

   private static void failNotSame(String var0, Object var1, Object var2) {
      if (var0 != null) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" ");
         var0 = var3.toString();
      } else {
         var0 = "";
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(var0);
      var5.append("expected same:<");
      var5.append(var1);
      var5.append("> was not:<");
      var5.append(var2);
      var5.append(">");
      fail(var5.toString());
   }

   private static void failSame(String var0) {
      if (var0 != null) {
         StringBuilder var1 = new StringBuilder();
         var1.append(var0);
         var1.append(" ");
         var0 = var1.toString();
      } else {
         var0 = "";
      }

      StringBuilder var3 = new StringBuilder();
      var3.append(var0);
      var3.append("expected not same");
      fail(var3.toString());
   }

   private static boolean floatIsDifferent(float var0, float var1, float var2) {
      return Float.compare(var0, var1) == 0 ? false : !(Math.abs(var0 - var1) <= var2);
   }

   static String format(String var0, Object var1, Object var2) {
      String var4 = "";
      String var3 = var4;
      if (var0 != null) {
         var3 = var4;
         if (!var0.equals("")) {
            StringBuilder var8 = new StringBuilder();
            var8.append(var0);
            var8.append(" ");
            var3 = var8.toString();
         }
      }

      var0 = String.valueOf(var1);
      var4 = String.valueOf(var2);
      if (var0.equals(var4)) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var3);
         var5.append("expected: ");
         var5.append(formatClassAndValue(var1, var0));
         var5.append(" but was: ");
         var5.append(formatClassAndValue(var2, var4));
         return var5.toString();
      } else {
         var1 = new StringBuilder();
         var1.append(var3);
         var1.append("expected:<");
         var1.append(var0);
         var1.append("> but was:<");
         var1.append(var4);
         var1.append(">");
         return var1.toString();
      }
   }

   private static String formatClassAndValue(Object var0, String var1) {
      if (var0 == null) {
         var0 = "null";
      } else {
         var0 = var0.getClass().getName();
      }

      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append("<");
      var2.append(var1);
      var2.append(">");
      return var2.toString();
   }

   private static void internalArrayEquals(String var0, Object var1, Object var2) throws ArrayComparisonFailure {
      new ExactComparisonCriteria().arrayEquals(var0, var1, var2);
   }

   private static boolean isEquals(Object var0, Object var1) {
      return var0.equals(var1);
   }
}
