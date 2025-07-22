package org.hamcrest.beans;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasProperty<T> extends TypeSafeMatcher<T> {
   private final String propertyName;

   public HasProperty(String var1) {
      this.propertyName = var1;
   }

   @Factory
   public static <T> Matcher<T> hasProperty(String var0) {
      return new HasProperty<>(var0);
   }

   @Override
   public void describeMismatchSafely(T var1, Description var2) {
      var2.appendText("no ").appendValue(this.propertyName).appendText(" in ").appendValue(var1);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("hasProperty(").appendValue(this.propertyName).appendText(")");
   }

   @Override
   public boolean matchesSafely(T var1) {
      boolean var2 = false;

      try {
         var1 = PropertyUtil.getPropertyDescriptor(this.propertyName, var1);
      } catch (IllegalArgumentException var3) {
         return var2;
      }

      if (var1 != null) {
         var2 = true;
      }

      return var2;
   }
}
