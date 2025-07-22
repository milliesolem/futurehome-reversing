package org.hamcrest;

import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.CombinableMatcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.Every;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;

public class CoreMatchers {
   public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> var0) {
      return AllOf.allOf(var0);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1) {
      return AllOf.allOf(var0, var1);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2) {
      return AllOf.allOf(var0, var1, var2);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3) {
      return AllOf.allOf(var0, var1, var2, var3);
   }

   public static <T> Matcher<T> allOf(
      Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4
   ) {
      return AllOf.allOf(var0, var1, var2, var3, var4);
   }

   public static <T> Matcher<T> allOf(
      Matcher<? super T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4, Matcher<? super T> var5
   ) {
      return AllOf.allOf(var0, var1, var2, var3, var4, var5);
   }

   public static <T> Matcher<T> allOf(Matcher<? super T>... var0) {
      return AllOf.allOf(var0);
   }

   public static <T> Matcher<T> any(Class<T> var0) {
      return IsInstanceOf.any(var0);
   }

   public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> var0) {
      return AnyOf.anyOf(var0);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1) {
      return AnyOf.anyOf(var0, var1);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2) {
      return AnyOf.anyOf(var0, var1, var2);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3) {
      return AnyOf.anyOf(var0, var1, var2, var3);
   }

   public static <T> AnyOf<T> anyOf(Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4) {
      return AnyOf.anyOf(var0, var1, var2, var3, var4);
   }

   public static <T> AnyOf<T> anyOf(
      Matcher<T> var0, Matcher<? super T> var1, Matcher<? super T> var2, Matcher<? super T> var3, Matcher<? super T> var4, Matcher<? super T> var5
   ) {
      return AnyOf.anyOf(var0, var1, var2, var3, var4, var5);
   }

   public static <T> AnyOf<T> anyOf(Matcher<? super T>... var0) {
      return AnyOf.anyOf(var0);
   }

   public static Matcher<Object> anything() {
      return IsAnything.anything();
   }

   public static Matcher<Object> anything(String var0) {
      return IsAnything.anything(var0);
   }

   public static <LHS> CombinableMatcher.CombinableBothMatcher<LHS> both(Matcher<? super LHS> var0) {
      return CombinableMatcher.both(var0);
   }

   public static Matcher<String> containsString(String var0) {
      return StringContains.containsString(var0);
   }

   public static <T> Matcher<T> describedAs(String var0, Matcher<T> var1, Object... var2) {
      return DescribedAs.describedAs(var0, var1, var2);
   }

   public static <LHS> CombinableMatcher.CombinableEitherMatcher<LHS> either(Matcher<? super LHS> var0) {
      return CombinableMatcher.either(var0);
   }

   public static Matcher<String> endsWith(String var0) {
      return StringEndsWith.endsWith(var0);
   }

   public static <T> Matcher<T> equalTo(T var0) {
      return IsEqual.equalTo((T)var0);
   }

   public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> var0) {
      return Every.everyItem(var0);
   }

   public static <T> Matcher<Iterable<? super T>> hasItem(T var0) {
      return IsCollectionContaining.hasItem((T)var0);
   }

   public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> var0) {
      return IsCollectionContaining.hasItem(var0);
   }

   public static <T> Matcher<Iterable<T>> hasItems(T... var0) {
      return IsCollectionContaining.hasItems((T[])var0);
   }

   public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... var0) {
      return IsCollectionContaining.hasItems(var0);
   }

   public static <T> Matcher<T> instanceOf(Class<?> var0) {
      return IsInstanceOf.instanceOf(var0);
   }

   public static <T> Matcher<T> is(Class<T> var0) {
      return Is.is(var0);
   }

   public static <T> Matcher<T> is(T var0) {
      return Is.is((T)var0);
   }

   public static <T> Matcher<T> is(Matcher<T> var0) {
      return Is.is(var0);
   }

   public static <T> Matcher<T> isA(Class<T> var0) {
      return Is.isA(var0);
   }

   public static <T> Matcher<T> not(T var0) {
      return IsNot.not((T)var0);
   }

   public static <T> Matcher<T> not(Matcher<T> var0) {
      return IsNot.not(var0);
   }

   public static Matcher<Object> notNullValue() {
      return IsNull.notNullValue();
   }

   public static <T> Matcher<T> notNullValue(Class<T> var0) {
      return IsNull.notNullValue(var0);
   }

   public static Matcher<Object> nullValue() {
      return IsNull.nullValue();
   }

   public static <T> Matcher<T> nullValue(Class<T> var0) {
      return IsNull.nullValue(var0);
   }

   public static <T> Matcher<T> sameInstance(T var0) {
      return IsSame.sameInstance((T)var0);
   }

   public static Matcher<String> startsWith(String var0) {
      return StringStartsWith.startsWith(var0);
   }

   public static <T> Matcher<T> theInstance(T var0) {
      return IsSame.theInstance((T)var0);
   }
}
