package org.hamcrest.collection;

import java.util.Collection;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

public class IsCollectionWithSize<E> extends FeatureMatcher<Collection<? extends E>, Integer> {
   public IsCollectionWithSize(Matcher<? super Integer> var1) {
      super(var1, "a collection with size", "collection size");
   }

   @Factory
   public static <E> Matcher<Collection<? extends E>> hasSize(int var0) {
      return hasSize(IsEqual.equalTo(var0));
   }

   @Factory
   public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> var0) {
      return new IsCollectionWithSize<>(var0);
   }

   protected Integer featureValueOf(Collection<? extends E> var1) {
      return var1.size();
   }
}
