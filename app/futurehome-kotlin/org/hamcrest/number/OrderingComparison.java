package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class OrderingComparison<T extends Comparable<T>> extends TypeSafeMatcher<T> {
   private static final int EQUAL = 0;
   private static final int GREATER_THAN = 1;
   private static final int LESS_THAN = -1;
   private static final String[] comparisonDescriptions = new String[]{"less than", "equal to", "greater than"};
   private final T expected;
   private final int maxCompare;
   private final int minCompare;

   private OrderingComparison(T var1, int var2, int var3) {
      this.expected = (T)var1;
      this.minCompare = var2;
      this.maxCompare = var3;
   }

   private static String asText(int var0) {
      return comparisonDescriptions[Integer.signum(var0) + 1];
   }

   @Factory
   public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T var0) {
      return new OrderingComparison<>((T)var0, 0, 0);
   }

   @Factory
   public static <T extends Comparable<T>> Matcher<T> greaterThan(T var0) {
      return new OrderingComparison<>((T)var0, 1, 1);
   }

   @Factory
   public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T var0) {
      return new OrderingComparison<>((T)var0, 0, 1);
   }

   @Factory
   public static <T extends Comparable<T>> Matcher<T> lessThan(T var0) {
      return new OrderingComparison<>((T)var0, -1, -1);
   }

   @Factory
   public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T var0) {
      return new OrderingComparison<>((T)var0, -1, 0);
   }

   public void describeMismatchSafely(T var1, Description var2) {
      var2.appendValue(var1).appendText(" was ").appendText(asText(var1.compareTo(this.expected))).appendText(" ").appendValue(this.expected);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("a value ").appendText(asText(this.minCompare));
      if (this.minCompare != this.maxCompare) {
         var1.appendText(" or ").appendText(asText(this.maxCompare));
      }

      var1.appendText(" ").appendValue(this.expected);
   }

   public boolean matchesSafely(T var1) {
      int var2 = Integer.signum(var1.compareTo(this.expected));
      boolean var3;
      if (this.minCompare <= var2 && var2 <= this.maxCompare) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }
}
