package org.hamcrest.text;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsNull;

public final class IsEmptyString extends BaseMatcher<String> {
   private static final IsEmptyString INSTANCE;
   private static final Matcher<String> NULL_OR_EMPTY_INSTANCE;

   static {
      IsEmptyString var0 = new IsEmptyString();
      INSTANCE = var0;
      NULL_OR_EMPTY_INSTANCE = AnyOf.anyOf(IsNull.nullValue(), var0);
   }

   @Factory
   public static Matcher<String> isEmptyOrNullString() {
      return NULL_OR_EMPTY_INSTANCE;
   }

   @Factory
   public static Matcher<String> isEmptyString() {
      return INSTANCE;
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("an empty string");
   }

   @Override
   public boolean matches(Object var1) {
      boolean var2;
      if (var1 != null && var1 instanceof String && ((String)var1).equals("")) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
