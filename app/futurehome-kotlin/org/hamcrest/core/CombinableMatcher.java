package org.hamcrest.core;

import java.util.ArrayList;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class CombinableMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
   private final Matcher<? super T> matcher;

   public CombinableMatcher(Matcher<? super T> var1) {
      this.matcher = var1;
   }

   @Factory
   public static <LHS> CombinableMatcher.CombinableBothMatcher<LHS> both(Matcher<? super LHS> var0) {
      return new CombinableMatcher.CombinableBothMatcher<>(var0);
   }

   @Factory
   public static <LHS> CombinableMatcher.CombinableEitherMatcher<LHS> either(Matcher<? super LHS> var0) {
      return new CombinableMatcher.CombinableEitherMatcher<>(var0);
   }

   private ArrayList<Matcher<? super T>> templatedListWith(Matcher<? super T> var1) {
      ArrayList var2 = new ArrayList();
      var2.add(this.matcher);
      var2.add(var1);
      return var2;
   }

   public CombinableMatcher<T> and(Matcher<? super T> var1) {
      return new CombinableMatcher<>(new AllOf<>(this.templatedListWith(var1)));
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendDescriptionOf(this.matcher);
   }

   @Override
   protected boolean matchesSafely(T var1, Description var2) {
      if (!this.matcher.matches(var1)) {
         this.matcher.describeMismatch(var1, var2);
         return false;
      } else {
         return true;
      }
   }

   public CombinableMatcher<T> or(Matcher<? super T> var1) {
      return new CombinableMatcher<>(new AnyOf<>(this.templatedListWith(var1)));
   }

   public static final class CombinableBothMatcher<X> {
      private final Matcher<? super X> first;

      public CombinableBothMatcher(Matcher<? super X> var1) {
         this.first = var1;
      }

      public CombinableMatcher<X> and(Matcher<? super X> var1) {
         return new CombinableMatcher<>(this.first).and(var1);
      }
   }

   public static final class CombinableEitherMatcher<X> {
      private final Matcher<? super X> first;

      public CombinableEitherMatcher(Matcher<? super X> var1) {
         this.first = var1;
      }

      public CombinableMatcher<X> or(Matcher<? super X> var1) {
         return new CombinableMatcher<>(this.first).or(var1);
      }
   }
}
