package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {
   private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 2, 0);
   private final Class<?> expectedType;

   protected TypeSafeDiagnosingMatcher() {
      this(TYPE_FINDER);
   }

   protected TypeSafeDiagnosingMatcher(Class<?> var1) {
      this.expectedType = var1;
   }

   protected TypeSafeDiagnosingMatcher(ReflectiveTypeFinder var1) {
      this.expectedType = var1.findExpectedType(this.getClass());
   }

   @Override
   public final void describeMismatch(Object var1, Description var2) {
      if (var1 != null && this.expectedType.isInstance(var1)) {
         this.matchesSafely((T)var1, var2);
      } else {
         super.describeMismatch(var1, var2);
      }
   }

   @Override
   public final boolean matches(Object var1) {
      boolean var2;
      if (var1 != null && this.expectedType.isInstance(var1) && this.matchesSafely((T)var1, new Description.NullDescription())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected abstract boolean matchesSafely(T var1, Description var2);
}
