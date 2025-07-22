package org.hamcrest.object;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsCompatibleType<T> extends TypeSafeMatcher<Class<?>> {
   private final Class<T> type;

   public IsCompatibleType(Class<T> var1) {
      this.type = var1;
   }

   @Factory
   public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> var0) {
      return new IsCompatibleType(var0);
   }

   public void describeMismatchSafely(Class<?> var1, Description var2) {
      var2.appendValue(var1.getName());
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("type < ").appendText(this.type.getName());
   }

   public boolean matchesSafely(Class<?> var1) {
      return this.type.isAssignableFrom(var1);
   }
}
