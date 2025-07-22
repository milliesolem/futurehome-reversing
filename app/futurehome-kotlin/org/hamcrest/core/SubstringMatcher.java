package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public abstract class SubstringMatcher extends TypeSafeMatcher<String> {
   protected final String substring;

   protected SubstringMatcher(String var1) {
      this.substring = var1;
   }

   public void describeMismatchSafely(String var1, Description var2) {
      var2.appendText("was \"").appendText(var1).appendText("\"");
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("a string ").appendText(this.relationship()).appendText(" ").appendValue(this.substring);
   }

   protected abstract boolean evalSubstringOf(String var1);

   public boolean matchesSafely(String var1) {
      return this.evalSubstringOf(var1);
   }

   protected abstract String relationship();
}
