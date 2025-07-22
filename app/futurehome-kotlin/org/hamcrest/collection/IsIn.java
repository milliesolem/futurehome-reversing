package org.hamcrest.collection;

import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsIn<T> extends BaseMatcher<T> {
   private final Collection<T> collection;

   public IsIn(Collection<T> var1) {
      this.collection = var1;
   }

   public IsIn(T[] var1) {
      this.collection = Arrays.asList((T[])var1);
   }

   @Factory
   public static <T> Matcher<T> isIn(Collection<T> var0) {
      return new IsIn<>(var0);
   }

   @Factory
   public static <T> Matcher<T> isIn(T[] var0) {
      return new IsIn<>((T[])var0);
   }

   @Factory
   public static <T> Matcher<T> isOneOf(T... var0) {
      return isIn((T[])var0);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("one of ");
      var1.appendValueList("{", ", ", "}", this.collection);
   }

   @Override
   public boolean matches(Object var1) {
      return this.collection.contains(var1);
   }
}
