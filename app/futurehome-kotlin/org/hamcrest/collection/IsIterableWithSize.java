package org.hamcrest.collection;

import java.util.Iterator;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsIterableWithSize<E> extends FeatureMatcher<Iterable<E>, Integer> {
   public IsIterableWithSize(Matcher<? super Integer> var1) {
      super(var1, "an iterable with size", "iterable size");
   }

   @Factory
   public static <E> Matcher<Iterable<E>> iterableWithSize(int var0) {
      return iterableWithSize(IsEqual.equalTo(var0));
   }

   @Factory
   public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> var0) {
      return new IsIterableWithSize<>(var0);
   }

   protected Integer featureValueOf(Iterable<E> var1) {
      Iterator var3 = var1.iterator();
      int var2 = 0;

      while (var3.hasNext()) {
         var2++;
         var3.next();
      }

      return var2;
   }
}
