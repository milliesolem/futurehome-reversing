package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class Is<T> extends BaseMatcher<T> {
   private final Matcher<T> matcher;

   public Is(Matcher<T> var1) {
      this.matcher = var1;
   }

   @Deprecated
   @Factory
   public static <T> Matcher<T> is(Class<T> var0) {
      return is(IsInstanceOf.instanceOf(var0));
   }

   @Factory
   public static <T> Matcher<T> is(T var0) {
      return is(IsEqual.equalTo((T)var0));
   }

   @Factory
   public static <T> Matcher<T> is(Matcher<T> var0) {
      return new Is<>(var0);
   }

   @Factory
   public static <T> Matcher<T> isA(Class<T> var0) {
      return is(IsInstanceOf.instanceOf(var0));
   }

   @Override
   public void describeMismatch(Object var1, Description var2) {
      this.matcher.describeMismatch(var1, var2);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("is ").appendDescriptionOf(this.matcher);
   }

   @Override
   public boolean matches(Object var1) {
      return this.matcher.matches(var1);
   }
}
