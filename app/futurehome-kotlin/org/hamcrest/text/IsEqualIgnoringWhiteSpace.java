package org.hamcrest.text;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEqualIgnoringWhiteSpace extends TypeSafeMatcher<String> {
   private final String string;

   public IsEqualIgnoringWhiteSpace(String var1) {
      if (var1 != null) {
         this.string = var1;
      } else {
         throw new IllegalArgumentException("Non-null value required by IsEqualIgnoringCase()");
      }
   }

   @Factory
   public static Matcher<String> equalToIgnoringWhiteSpace(String var0) {
      return new IsEqualIgnoringWhiteSpace(var0);
   }

   public void describeMismatchSafely(String var1, Description var2) {
      var2.appendText("was  ").appendText(this.stripSpace(var1));
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("equalToIgnoringWhiteSpace(").appendValue(this.string).appendText(")");
   }

   public boolean matchesSafely(String var1) {
      return this.stripSpace(this.string).equalsIgnoreCase(this.stripSpace(var1));
   }

   public String stripSpace(String var1) {
      StringBuilder var5 = new StringBuilder();
      int var4 = 0;

      for (boolean var3 = true; var4 < var1.length(); var4++) {
         char var2 = var1.charAt(var4);
         if (Character.isWhitespace(var2)) {
            if (!var3) {
               var5.append(' ');
            }

            var3 = true;
         } else {
            var5.append(var2);
            var3 = false;
         }
      }

      return var5.toString().trim();
   }
}
