package org.hamcrest.number;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCloseTo extends TypeSafeMatcher<Double> {
   private final double delta;
   private final double value;

   public IsCloseTo(double var1, double var3) {
      this.delta = var3;
      this.value = var1;
   }

   private double actualDelta(Double var1) {
      return Math.abs(var1 - this.value) - this.delta;
   }

   @Factory
   public static Matcher<Double> closeTo(double var0, double var2) {
      return new IsCloseTo(var0, var2);
   }

   public void describeMismatchSafely(Double var1, Description var2) {
      var2.appendValue(var1).appendText(" differed by ").appendValue(this.actualDelta(var1));
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("a numeric value within ").appendValue(this.delta).appendText(" of ").appendValue(this.value);
   }

   public boolean matchesSafely(Double var1) {
      boolean var2;
      if (this.actualDelta(var1) <= 0.0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
