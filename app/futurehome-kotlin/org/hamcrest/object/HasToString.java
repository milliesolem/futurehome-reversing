package org.hamcrest.object;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class HasToString<T> extends FeatureMatcher<T, String> {
   public HasToString(Matcher<? super String> var1) {
      super(var1, "with toString()", "toString()");
   }

   @Factory
   public static <T> Matcher<T> hasToString(String var0) {
      return new HasToString<>(IsEqual.equalTo(var0));
   }

   @Factory
   public static <T> Matcher<T> hasToString(Matcher<? super String> var0) {
      return new HasToString<>(var0);
   }

   protected String featureValueOf(T var1) {
      return String.valueOf(var1);
   }
}
