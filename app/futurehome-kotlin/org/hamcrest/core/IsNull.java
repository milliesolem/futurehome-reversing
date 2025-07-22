package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsNull<T> extends BaseMatcher<T> {
   @Factory
   public static Matcher<Object> notNullValue() {
      return IsNot.not(nullValue());
   }

   @Factory
   public static <T> Matcher<T> notNullValue(Class<T> var0) {
      return IsNot.not(nullValue(var0));
   }

   @Factory
   public static Matcher<Object> nullValue() {
      return new IsNull<>();
   }

   @Factory
   public static <T> Matcher<T> nullValue(Class<T> var0) {
      return new IsNull<>();
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("null");
   }

   @Override
   public boolean matches(Object var1) {
      boolean var2;
      if (var1 == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
