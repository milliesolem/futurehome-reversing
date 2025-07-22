package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsEmptyCollection<E> extends TypeSafeMatcher<Collection<? extends E>> {
   @Factory
   public static <E> Matcher<Collection<? extends E>> empty() {
      return new IsEmptyCollection<>();
   }

   @Factory
   public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> var0) {
      return (Matcher<Collection<E>>)empty();
   }

   public void describeMismatchSafely(Collection<? extends E> var1, Description var2) {
      var2.appendValue(var1);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("an empty collection");
   }

   public boolean matchesSafely(Collection<? extends E> var1) {
      return var1.isEmpty();
   }
}
