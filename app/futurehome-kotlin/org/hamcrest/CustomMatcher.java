package org.hamcrest;

public abstract class CustomMatcher<T> extends BaseMatcher<T> {
   private final String fixedDescription;

   public CustomMatcher(String var1) {
      if (var1 != null) {
         this.fixedDescription = var1;
      } else {
         throw new IllegalArgumentException("Description should be non null!");
      }
   }

   @Override
   public final void describeTo(Description var1) {
      var1.appendText(this.fixedDescription);
   }
}
