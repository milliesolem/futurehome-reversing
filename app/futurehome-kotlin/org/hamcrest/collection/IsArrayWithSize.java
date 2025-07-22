package org.hamcrest.collection;

import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.IsEqual;

public class IsArrayWithSize<E> extends FeatureMatcher<E[], Integer> {
   public IsArrayWithSize(Matcher<? super Integer> var1) {
      super(var1, "an array with size", "array size");
   }

   @Factory
   public static <E> Matcher<E[]> arrayWithSize(int var0) {
      return arrayWithSize(IsEqual.equalTo(var0));
   }

   @Factory
   public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> var0) {
      return new IsArrayWithSize<>(var0);
   }

   @Factory
   public static <E> Matcher<E[]> emptyArray() {
      return DescribedAs.describedAs("an empty array", arrayWithSize(0));
   }

   protected Integer featureValueOf(E[] var1) {
      return var1.length;
   }
}
