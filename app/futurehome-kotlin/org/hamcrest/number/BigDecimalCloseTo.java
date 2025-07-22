package org.hamcrest.number;

import com.google.android.gms.internal.measurement.zzai..ExternalSyntheticBackportWithForwarding0;
import java.math.BigDecimal;
import java.math.MathContext;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class BigDecimalCloseTo extends TypeSafeMatcher<BigDecimal> {
   private final BigDecimal delta;
   private final BigDecimal value;

   public BigDecimalCloseTo(BigDecimal var1, BigDecimal var2) {
      this.delta = var2;
      this.value = var1;
   }

   private BigDecimal actualDelta(BigDecimal var1) {
      return ExternalSyntheticBackportWithForwarding0.m(var1.subtract(this.value, MathContext.DECIMAL128).abs().subtract(this.delta, MathContext.DECIMAL128));
   }

   @Factory
   public static Matcher<BigDecimal> closeTo(BigDecimal var0, BigDecimal var1) {
      return new BigDecimalCloseTo(var0, var1);
   }

   public void describeMismatchSafely(BigDecimal var1, Description var2) {
      var2.appendValue(var1).appendText(" differed by ").appendValue(this.actualDelta(var1));
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("a numeric value within ").appendValue(this.delta).appendText(" of ").appendValue(this.value);
   }

   public boolean matchesSafely(BigDecimal var1) {
      boolean var2;
      if (this.actualDelta(var1).compareTo(BigDecimal.ZERO) <= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
