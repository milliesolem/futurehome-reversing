package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContaining<T> extends TypeSafeMatcher<T[]> {
   private final Matcher<? super T> elementMatcher;

   public IsArrayContaining(Matcher<? super T> var1) {
      this.elementMatcher = var1;
   }

   @Factory
   public static <T> Matcher<T[]> hasItemInArray(T var0) {
      return hasItemInArray(IsEqual.equalTo((T)var0));
   }

   @Factory
   public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> var0) {
      return new IsArrayContaining<>(var0);
   }

   public void describeMismatchSafely(T[] var1, Description var2) {
      super.describeMismatch(Arrays.asList(var1), var2);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("an array containing ").appendDescriptionOf(this.elementMatcher);
   }

   public boolean matchesSafely(T[] var1) {
      for (Object var4 : var1) {
         if (this.elementMatcher.matches(var4)) {
            return true;
         }
      }

      return false;
   }
}
