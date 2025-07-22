package org.hamcrest.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;

public class IsIterableContainingInOrder<E> extends TypeSafeDiagnosingMatcher<Iterable<? extends E>> {
   private final List<Matcher<? super E>> matchers;

   public IsIterableContainingInOrder(List<Matcher<? super E>> var1) {
      this.matchers = var1;
   }

   @Factory
   public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> var0) {
      return new IsIterableContainingInOrder<>(var0);
   }

   @Factory
   public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> var0) {
      return contains(new ArrayList<>(Arrays.asList(var0)));
   }

   @Factory
   public static <E> Matcher<Iterable<? extends E>> contains(E... var0) {
      ArrayList var3 = new ArrayList();
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(IsEqual.equalTo(var0[var1]));
      }

      return contains(var3);
   }

   @Factory
   public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... var0) {
      return contains(Arrays.asList(var0));
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("iterable containing ").appendList("[", ", ", "]", this.matchers);
   }

   protected boolean matchesSafely(Iterable<? extends E> var1, Description var2) {
      IsIterableContainingInOrder.MatchSeries var4 = new IsIterableContainingInOrder.MatchSeries<>(this.matchers, var2);
      Iterator var3 = var1.iterator();

      while (var3.hasNext()) {
         if (!var4.matches(var3.next())) {
            return false;
         }
      }

      return var4.isFinished();
   }

   private static class MatchSeries<F> {
      public final List<Matcher<? super F>> matchers;
      private final Description mismatchDescription;
      public int nextMatchIx = 0;

      public MatchSeries(List<Matcher<? super F>> var1, Description var2) {
         this.mismatchDescription = var2;
         if (!var1.isEmpty()) {
            this.matchers = var1;
         } else {
            throw new IllegalArgumentException("Should specify at least one expected element");
         }
      }

      private void describeMismatch(Matcher<? super F> var1, F var2) {
         Description var3 = this.mismatchDescription;
         StringBuilder var4 = new StringBuilder("item ");
         var4.append(this.nextMatchIx);
         var4.append(": ");
         var3.appendText(var4.toString());
         var1.describeMismatch(var2, this.mismatchDescription);
      }

      private boolean isMatched(F var1) {
         Matcher var2 = this.matchers.get(this.nextMatchIx);
         if (!var2.matches(var1)) {
            this.describeMismatch(var2, (F)var1);
            return false;
         } else {
            this.nextMatchIx++;
            return true;
         }
      }

      private boolean isNotSurplus(F var1) {
         if (this.matchers.size() <= this.nextMatchIx) {
            this.mismatchDescription.appendText("Not matched: ").appendValue(var1);
            return false;
         } else {
            return true;
         }
      }

      public boolean isFinished() {
         if (this.nextMatchIx < this.matchers.size()) {
            this.mismatchDescription.appendText("No item matched: ").appendDescriptionOf(this.matchers.get(this.nextMatchIx));
            return false;
         } else {
            return true;
         }
      }

      public boolean matches(F var1) {
         boolean var2;
         if (this.isNotSurplus((F)var1) && this.isMatched((F)var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }
}
