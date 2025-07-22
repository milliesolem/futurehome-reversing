package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class FeatureMatcher<T, U> extends TypeSafeDiagnosingMatcher<T> {
   private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0);
   private final String featureDescription;
   private final String featureName;
   private final Matcher<? super U> subMatcher;

   public FeatureMatcher(Matcher<? super U> var1, String var2, String var3) {
      super(TYPE_FINDER);
      this.subMatcher = var1;
      this.featureDescription = var2;
      this.featureName = var3;
   }

   @Override
   public final void describeTo(Description var1) {
      var1.appendText(this.featureDescription).appendText(" ").appendDescriptionOf(this.subMatcher);
   }

   protected abstract U featureValueOf(T var1);

   @Override
   protected boolean matchesSafely(T var1, Description var2) {
      var1 = this.featureValueOf((T)var1);
      if (!this.subMatcher.matches(var1)) {
         var2.appendText(this.featureName).appendText(" ");
         this.subMatcher.describeMismatch(var1, var2);
         return false;
      } else {
         return true;
      }
   }
}
