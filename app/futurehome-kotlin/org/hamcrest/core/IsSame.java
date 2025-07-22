package org.hamcrest.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsSame<T> extends BaseMatcher<T> {
   private final T object;

   public IsSame(T var1) {
      this.object = (T)var1;
   }

   @Factory
   public static <T> Matcher<T> sameInstance(T var0) {
      return new IsSame<>((T)var0);
   }

   @Factory
   public static <T> Matcher<T> theInstance(T var0) {
      return new IsSame<>((T)var0);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("sameInstance(").appendValue(this.object).appendText(")");
   }

   @Override
   public boolean matches(Object var1) {
      boolean var2;
      if (var1 == this.object) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
