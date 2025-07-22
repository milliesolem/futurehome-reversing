package org.hamcrest;

public abstract class BaseMatcher<T> implements Matcher<T> {
   @Deprecated
   @Override
   public final void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
   }

   @Override
   public void describeMismatch(Object var1, Description var2) {
      var2.appendText("was ").appendValue(var1);
   }

   @Override
   public String toString() {
      return StringDescription.toString(this);
   }
}
