package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class AllOf<T> extends DiagnosingMatcher<T> {
   private final Iterable<Matcher<? super T>> matchers;

   public AllOf(Iterable<Matcher<? super T>> var1) {
      this.matchers = var1;
   }

   @Factory
   public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> var0) {
      return new AllOf<>(var0);
   }

   @Factory
   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1) {
      ArrayList var2 = new ArrayList(2);
      var2.add(var0);
      var2.add(var1);
      return allOf(var2);
   }

   @Factory
   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2) {
      ArrayList var3 = new ArrayList(3);
      var3.add(var0);
      var3.add(var1);
      var3.add(var2);
      return allOf(var3);
   }

   @Factory
   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3) {
      ArrayList var4 = new ArrayList(4);
      var4.add(var0);
      var4.add(var1);
      var4.add(var2);
      var4.add(var3);
      return allOf(var4);
   }

   @Factory
   public static <T> Matcher<T> allOf(
      Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4
   ) {
      ArrayList var5 = new ArrayList(5);
      var5.add(var0);
      var5.add(var1);
      var5.add(var2);
      var5.add(var3);
      var5.add(var4);
      return allOf(var5);
   }

   @Factory
   public static <T> Matcher<T> allOf(
      Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4, Matcher<? super T> var5
   ) {
      ArrayList var6 = new ArrayList(6);
      var6.add(var0);
      var6.add(var1);
      var6.add(var2);
      var6.add(var3);
      var6.add(var4);
      var6.add(var5);
      return allOf(var6);
   }

   @Factory
   public static <T> Matcher<T> allOf(Matcher<? super T>... var0) {
      return allOf(Arrays.asList(var0));
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendList("(", " and ", ")", this.matchers);
   }

   @Override
   public boolean matches(Object var1, Description var2) {
      for (Matcher var3 : this.matchers) {
         if (!var3.matches(var1)) {
            var2.appendDescriptionOf(var3).appendText(" ");
            var3.describeMismatch(var1, var2);
            return false;
         }
      }

      return true;
   }
}
