package org.hamcrest.core;

import java.util.ArrayList;
import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class AnyOf<T> extends ShortcutCombination<T> {
   public AnyOf(Iterable<Matcher<? super T>> var1) {
      super(var1);
   }

   @Factory
   public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> var0) {
      return new AnyOf<>(var0);
   }

   @Factory
   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1) {
      ArrayList var2 = new ArrayList();
      var2.add(var0);
      var2.add(var1);
      return anyOf(var2);
   }

   @Factory
   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2) {
      ArrayList var3 = new ArrayList();
      var3.add(var0);
      var3.add(var1);
      var3.add(var2);
      return anyOf(var3);
   }

   @Factory
   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3) {
      ArrayList var4 = new ArrayList();
      var4.add(var0);
      var4.add(var1);
      var4.add(var2);
      var4.add(var3);
      return anyOf(var4);
   }

   @Factory
   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4) {
      ArrayList var5 = new ArrayList();
      var5.add(var0);
      var5.add(var1);
      var5.add(var2);
      var5.add(var3);
      var5.add(var4);
      return anyOf(var5);
   }

   @Factory
   public static <T> AnyOf<T> anyOf(
      Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4, Matcher<? super T> var5
   ) {
      ArrayList var6 = new ArrayList();
      var6.add(var0);
      var6.add(var1);
      var6.add(var2);
      var6.add(var3);
      var6.add(var4);
      var6.add(var5);
      return anyOf(var6);
   }

   @Factory
   public static <T> AnyOf<T> anyOf(Matcher<? super T>... var0) {
      return anyOf(Arrays.asList(var0));
   }

   @Override
   public void describeTo(Description var1) {
      this.describeTo(var1, "or");
   }

   @Override
   public boolean matches(Object var1) {
      return this.matches(var1, true);
   }
}
