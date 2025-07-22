package org.hamcrest.collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyIterable<E> extends TypeSafeMatcher<Iterable<? extends E>> {
   @Factory
   public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
      return new IsEmptyIterable<>();
   }

   @Factory
   public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> var0) {
      return (Matcher<Iterable<E>>)emptyIterable();
   }

   public void describeMismatchSafely(Iterable<? extends E> var1, Description var2) {
      var2.appendValueList("[", ",", "]", var1);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("an empty iterable");
   }

   public boolean matchesSafely(Iterable<? extends E> var1) {
      return var1.iterator().hasNext() ^ true;
   }
}
