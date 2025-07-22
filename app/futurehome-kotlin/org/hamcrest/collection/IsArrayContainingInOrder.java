package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContainingInOrder<E> extends TypeSafeMatcher<E[]> {
   private final IsIterableContainingInOrder<E> iterableMatcher;
   private final Collection<Matcher<? super E>> matchers;

   public IsArrayContainingInOrder(List<Matcher<? super E>> var1) {
      this.iterableMatcher = new IsIterableContainingInOrder<>(var1);
      this.matchers = var1;
   }

   @Factory
   public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> var0) {
      return new IsArrayContainingInOrder<>(var0);
   }

   @Factory
   public static <E> Matcher<E[]> arrayContaining(E... var0) {
      ArrayList var3 = new ArrayList();
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(IsEqual.equalTo(var0[var1]));
      }

      return arrayContaining(var3);
   }

   @Factory
   public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... var0) {
      return arrayContaining(Arrays.asList(var0));
   }

   public void describeMismatchSafely(E[] var1, Description var2) {
      this.iterableMatcher.describeMismatch(Arrays.asList(var1), var2);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendList("[", ", ", "]", this.matchers);
   }

   public boolean matchesSafely(E[] var1) {
      return this.iterableMatcher.matches(Arrays.asList(var1));
   }
}
