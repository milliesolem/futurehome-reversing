package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsAnything<T> extends BaseMatcher<T> {
   private final String message;

   public IsAnything() {
      this("ANYTHING");
   }

   public IsAnything(String var1) {
      this.message = var1;
   }

   @Factory
   public static Matcher<Object> anything() {
      return new IsAnything<>();
   }

   @Factory
   public static Matcher<Object> anything(String var0) {
      return new IsAnything<>(var0);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText(this.message);
   }

   @Override
   public boolean matches(Object var1) {
      return true;
   }
}
