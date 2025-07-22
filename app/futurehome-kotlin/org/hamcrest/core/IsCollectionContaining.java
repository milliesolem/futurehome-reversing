package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsCollectionContaining<T> extends TypeSafeDiagnosingMatcher<Iterable<? super T>> {
   private final Matcher<? super T> elementMatcher;

   public IsCollectionContaining(Matcher<? super T> var1) {
      this.elementMatcher = var1;
   }

   @Factory
   public static <T> Matcher<Iterable<? super T>> hasItem(T var0) {
      return new IsCollectionContaining<>(IsEqual.equalTo((T)var0));
   }

   @Factory
   public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> var0) {
      return new IsCollectionContaining<>(var0);
   }

   @Factory
   public static <T> Matcher<Iterable<T>> hasItems(T... var0) {
      ArrayList var3 = new ArrayList(var0.length);
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(hasItem(var0[var1]));
      }

      return AllOf.allOf(var3);
   }

   @Factory
   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... var0) {
      ArrayList var3 = new ArrayList(var0.length);
      int var2 = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(new IsCollectionContaining(var0[var1]));
      }

      return AllOf.allOf(var3);
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("a collection containing ").appendDescriptionOf(this.elementMatcher);
   }

   protected boolean matchesSafely(Iterable<? super T> var1, Description var2) {
      Iterator var5 = var1.iterator();

      for (boolean var3 = false; var5.hasNext(); var3 = true) {
         Object var4 = var5.next();
         if (this.elementMatcher.matches(var4)) {
            return true;
         }

         if (var3) {
            var2.appendText(", ");
         }

         this.elementMatcher.describeMismatch(var4, var2);
      }

      return false;
   }
}
