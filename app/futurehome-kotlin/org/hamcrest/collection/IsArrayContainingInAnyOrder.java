package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

public class IsArrayContainingInAnyOrder<E> extends TypeSafeMatcher<E[]> {
   private final IsIterableContainingInAnyOrder<E> iterableMatcher;
   private final Collection<Matcher<? super E>> matchers;

   public IsArrayContainingInAnyOrder(Collection<Matcher<? super E>> var1) {
      this.iterableMatcher = new IsIterableContainingInAnyOrder<>(var1);
      this.matchers = var1;
   }

   @Factory
   public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> var0) {
      return new IsArrayContainingInAnyOrder<>(var0);
   }

   @Factory
   public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... var0) {
      ArrayList var3 = new ArrayList();
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(IsEqual.equalTo(var0[var1]));
      }

      return new IsArrayContainingInAnyOrder<>(var3);
   }

   @Factory
   public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... var0) {
      return arrayContainingInAnyOrder(Arrays.asList(var0));
   }

   public void describeMismatchSafely(E[] var1, Description var2) {
      this.iterableMatcher.describeMismatch(Arrays.asList(var1), var2);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendList("[", ", ", "]", this.matchers).appendText(" in any order");
   }

   public boolean matchesSafely(E[] var1) {
      return this.iterableMatcher.matches(Arrays.asList(var1));
   }
}
