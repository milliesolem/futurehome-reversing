package org.hamcrest;

public interface Matcher<T> extends SelfDescribing {
   @Deprecated
   void _dont_implement_Matcher___instead_extend_BaseMatcher_();

   void describeMismatch(Object var1, Description var2);

   boolean matches(Object var1);
}
