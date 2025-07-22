package org.hamcrest;

public abstract class DiagnosingMatcher<T> extends BaseMatcher<T> {
   @Override
   public final void describeMismatch(Object var1, Description var2) {
      this.matches(var1, var2);
   }

   @Override
   public final boolean matches(Object var1) {
      return this.matches(var1, Description.NONE);
   }

   protected abstract boolean matches(Object var1, Description var2);
}
