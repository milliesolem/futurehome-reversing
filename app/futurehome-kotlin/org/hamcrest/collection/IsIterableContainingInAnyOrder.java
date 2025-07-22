package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class IsIterableContainingInAnyOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<? extends T>> {
   private final Collection<Matcher<? super T>> matchers;

   public IsIterableContainingInAnyOrder(Collection<Matcher<? super T>> var1) {
      this.matchers = var1;
   }

   @Factory
   public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> var0) {
      return new IsIterableContainingInAnyOrder<>(var0);
   }

   @Deprecated
   @Factory
   public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(Matcher<? super E> var0) {
      return containsInAnyOrder(new ArrayList<>(Arrays.asList(var0)));
   }

   @Factory
   public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... var0) {
      ArrayList var3 = new ArrayList();
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(IsEqual.equalTo(var0[var1]));
      }

      return new IsIterableContainingInAnyOrder<>(var3);
   }

   @Factory
   public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... var0) {
      return containsInAnyOrder(Arrays.asList(var0));
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("iterable over ").appendList("[", ", ", "]", this.matchers).appendText(" in any order");
   }

   protected boolean matchesSafely(Iterable<? extends T> var1, Description var2) {
      IsIterableContainingInAnyOrder.Matching var4 = new IsIterableContainingInAnyOrder.Matching<>(this.matchers, var2);
      Iterator var3 = var1.iterator();

      while (var3.hasNext()) {
         if (!var4.matches(var3.next())) {
            return false;
         }
      }

      return var4.isFinished(var1);
   }

   private static class Matching<S> {
      private final Collection<Matcher<? super S>> matchers;
      private final Description mismatchDescription;

      public Matching(Collection<Matcher<? super S>> var1, Description var2) {
         this.matchers = new ArrayList<>(var1);
         this.mismatchDescription = var2;
      }

      private boolean isMatched(S var1) {
         for (Matcher var2 : this.matchers) {
            if (var2.matches(var1)) {
               this.matchers.remove(var2);
               return true;
            }
         }

         this.mismatchDescription.appendText("Not matched: ").appendValue(var1);
         return false;
      }

      private boolean isNotSurplus(S var1) {
         if (this.matchers.isEmpty()) {
            this.mismatchDescription.appendText("Not matched: ").appendValue(var1);
            return false;
         } else {
            return true;
         }
      }

      public boolean isFinished(Iterable<? extends S> var1) {
         if (this.matchers.isEmpty()) {
            return true;
         } else {
            this.mismatchDescription
               .appendText("No item matches: ")
               .appendList("", ", ", "", this.matchers)
               .appendText(" in ")
               .appendValueList("[", ", ", "]", var1);
            return false;
         }
      }

      public boolean matches(S var1) {
         boolean var2;
         if (this.isNotSurplus((S)var1) && this.isMatched((S)var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }
}
