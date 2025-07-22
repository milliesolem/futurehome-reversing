package org.hamcrest;

import org.hamcrest.internal.ReflectiveTypeFinder;

public abstract class TypeSafeMatcher<T> extends BaseMatcher<T> {
   private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 1, 0);
   private final Class<?> expectedType;

   protected TypeSafeMatcher() {
      this(TYPE_FINDER);
   }

   protected TypeSafeMatcher(Class<?> var1) {
      this.expectedType = var1;
   }

   protected TypeSafeMatcher(ReflectiveTypeFinder var1) {
      this.expectedType = var1.findExpectedType(this.getClass());
   }

   @Override
   public final void describeMismatch(Object var1, Description var2) {
      if (var1 == null) {
         super.describeMismatch(var1, var2);
      } else if (!this.expectedType.isInstance(var1)) {
         var2.appendText("was a ").appendText(var1.getClass().getName()).appendText(" (").appendValue(var1).appendText(")");
      } else {
         this.describeMismatchSafely((T)var1, var2);
      }
   }

   protected void describeMismatchSafely(T var1, Description var2) {
      super.describeMismatch(var1, var2);
   }

   @Override
   public final boolean matches(Object var1) {
      boolean var2;
      if (var1 != null && this.expectedType.isInstance(var1) && this.matchesSafely((T)var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected abstract boolean matchesSafely(T var1);
}
