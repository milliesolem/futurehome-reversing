package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsNot<T> extends BaseMatcher<T> {
   private final Matcher<T> matcher;

   public IsNot(Matcher<T> var1) {
      this.matcher = var1;
   }

   @Factory
   public static <T> Matcher<T> not(T var0) {
      return not(IsEqual.equalTo((T)var0));
   }

   @Factory
   public static <T> Matcher<T> not(Matcher<T> var0) {
      return new IsNot<>(var0);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("not ").appendDescriptionOf(this.matcher);
   }

   @Override
   public boolean matches(Object var1) {
      return this.matcher.matches(var1) ^ true;
   }
}
