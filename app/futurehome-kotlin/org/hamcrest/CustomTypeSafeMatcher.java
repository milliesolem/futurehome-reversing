package org.hamcrest;

public abstract class CustomTypeSafeMatcher<T> extends TypeSafeMatcher<T> {
   private final String fixedDescription;

   public CustomTypeSafeMatcher(String var1) {
      if (var1 != null) {
         this.fixedDescription = var1;
      } else {
         throw new IllegalArgumentException("Description must be non null!");
      }
   }

   @Override
   public final void describeTo(Description var1) {
      var1.appendText(this.fixedDescription);
   }
}
