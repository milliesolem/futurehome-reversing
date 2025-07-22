package org.hamcrest.core;

import java.util.regex.Pattern;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class DescribedAs<T> extends BaseMatcher<T> {
   private static final Pattern ARG_PATTERN = Pattern.compile("%([0-9]+)");
   private final String descriptionTemplate;
   private final Matcher<T> matcher;
   private final Object[] values;

   public DescribedAs(String var1, Matcher<T> var2, Object[] var3) {
      this.descriptionTemplate = var1;
      this.matcher = var2;
      this.values = (Object[])var3.clone();
   }

   @Factory
   public static <T> Matcher<T> describedAs(String var0, Matcher<T> var1, Object... var2) {
      return new DescribedAs<>(var0, var1, var2);
   }

   @Override
   public void describeMismatch(Object var1, Description var2) {
      this.matcher.describeMismatch(var1, var2);
   }

   @Override
   public void describeTo(Description var1) {
      java.util.regex.Matcher var3 = ARG_PATTERN.matcher(this.descriptionTemplate);

      int var2;
      for (var2 = 0; var3.find(); var2 = var3.end()) {
         var1.appendText(this.descriptionTemplate.substring(var2, var3.start()));
         var1.appendValue(this.values[Integer.parseInt(var3.group(1))]);
      }

      if (var2 < this.descriptionTemplate.length()) {
         var1.appendText(this.descriptionTemplate.substring(var2));
      }
   }

   @Override
   public boolean matches(Object var1) {
      return this.matcher.matches(var1);
   }
}
