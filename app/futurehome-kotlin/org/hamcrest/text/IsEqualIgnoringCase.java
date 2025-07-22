package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringCase extends TypeSafeMatcher<String> {
   private final String string;

   public IsEqualIgnoringCase(String var1) {
      if (var1 != null) {
         this.string = var1;
      } else {
         throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
      }
   }

   @Factory
   public static Matcher<String> equalToIgnoringCase(String var0) {
      return new IsEqualIgnoringCase(var0);
   }

   public void describeMismatchSafely(String var1, Description var2) {
      var2.appendText("was ").appendText(var1);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("equalToIgnoringCase(").appendValue(this.string).appendText(")");
   }

   public boolean matchesSafely(String var1) {
      return this.string.equalsIgnoreCase(var1);
   }
}
