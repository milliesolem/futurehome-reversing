package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsInstanceOf extends DiagnosingMatcher<Object> {
   private final Class<?> expectedClass;
   private final Class<?> matchableClass;

   public IsInstanceOf(Class<?> var1) {
      this.expectedClass = var1;
      this.matchableClass = matchableClass(var1);
   }

   @Factory
   public static <T> Matcher<T> any(Class<T> var0) {
      return new IsInstanceOf(var0);
   }

   @Factory
   public static <T> Matcher<T> instanceOf(Class<?> var0) {
      return new IsInstanceOf(var0);
   }

   private static Class<?> matchableClass(Class<?> var0) {
      if (boolean.class.equals(var0)) {
         return Boolean.class;
      } else if (byte.class.equals(var0)) {
         return Byte.class;
      } else if (char.class.equals(var0)) {
         return Character.class;
      } else if (double.class.equals(var0)) {
         return Double.class;
      } else if (float.class.equals(var0)) {
         return Float.class;
      } else if (int.class.equals(var0)) {
         return Integer.class;
      } else if (long.class.equals(var0)) {
         return Long.class;
      } else {
         Class var1 = var0;
         if (short.class.equals(var0)) {
            var1 = Short.class;
         }

         return var1;
      }
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("an instance of ").appendText(this.expectedClass.getName());
   }

   @Override
   protected boolean matches(Object var1, Description var2) {
      if (var1 == null) {
         var2.appendText("null");
         return false;
      } else if (!this.matchableClass.isInstance(var1)) {
         var2 = var2.appendValue(var1);
         StringBuilder var3 = new StringBuilder(" is a ");
         var3.append(var1.getClass().getName());
         var2.appendText(var3.toString());
         return false;
      } else {
         return true;
      }
   }
}
