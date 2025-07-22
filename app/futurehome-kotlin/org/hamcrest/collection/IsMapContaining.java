package org.hamcrest.collection;

import java.util.Map;
import java.util.Map.Entry;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;

public class IsMapContaining<K, V> extends TypeSafeMatcher<Map<? extends K, ? extends V>> {
   private final Matcher<? super K> keyMatcher;
   private final Matcher<? super V> valueMatcher;

   public IsMapContaining(Matcher<? super K> var1, Matcher<? super V> var2) {
      this.keyMatcher = var1;
      this.valueMatcher = var2;
   }

   @Factory
   public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K var0, V var1) {
      return new IsMapContaining<>(IsEqual.equalTo((K)var0), IsEqual.equalTo((V)var1));
   }

   @Factory
   public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> var0, Matcher<? super V> var1) {
      return new IsMapContaining<>(var0, var1);
   }

   @Factory
   public static <K> Matcher<Map<? extends K, ?>> hasKey(K var0) {
      return new IsMapContaining<>(IsEqual.equalTo((K)var0), IsAnything.anything());
   }

   @Factory
   public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> var0) {
      return new IsMapContaining<>(var0, IsAnything.anything());
   }

   @Factory
   public static <V> Matcher<Map<?, ? extends V>> hasValue(V var0) {
      return new IsMapContaining<>(IsAnything.anything(), IsEqual.equalTo((V)var0));
   }

   @Factory
   public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> var0) {
      return new IsMapContaining<>(IsAnything.anything(), var0);
   }

   public void describeMismatchSafely(Map<? extends K, ? extends V> var1, Description var2) {
      var2.appendText("map was ").appendValueList("[", ", ", "]", var1.entrySet());
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("map containing [").appendDescriptionOf(this.keyMatcher).appendText("->").appendDescriptionOf(this.valueMatcher).appendText("]");
   }

   public boolean matchesSafely(Map<? extends K, ? extends V> var1) {
      for (Entry var3 : var1.entrySet()) {
         if (this.keyMatcher.matches(var3.getKey()) && this.valueMatcher.matches(var3.getValue())) {
            return true;
         }
      }

      return false;
   }
}
