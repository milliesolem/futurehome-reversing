package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class Every<T> extends TypeSafeDiagnosingMatcher<Iterable<T>> {
   private final Matcher<? super T> matcher;

   public Every(Matcher<? super T> var1) {
      this.matcher = var1;
   }

   @Factory
   public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> var0) {
      return new Every(var0);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("every item is ").appendDescriptionOf(this.matcher);
   }

   public boolean matchesSafely(Iterable<T> var1, Description var2) {
      for (Object var3 : var1) {
         if (!this.matcher.matches(var3)) {
            var2.appendText("an item ");
            this.matcher.describeMismatch(var3, var2);
            return false;
         }
      }

      return true;
   }
}
