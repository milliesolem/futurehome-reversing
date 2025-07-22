package org.junit.experimental.results;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ResultMatchers {
   public static Matcher<PrintableResult> failureCountIs(int var0) {
      return new TypeSafeMatcher<PrintableResult>(var0) {
         final int val$count;

         {
            this.val$count = var1;
         }

         @Override
         public void describeTo(Description var1) {
            StringBuilder var2 = new StringBuilder("has ");
            var2.append(this.val$count);
            var2.append(" failures");
            var1.appendText(var2.toString());
         }

         public boolean matchesSafely(PrintableResult var1) {
            boolean var2;
            if (var1.failureCount() == this.val$count) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      };
   }

   public static Matcher<PrintableResult> hasFailureContaining(String var0) {
      return new BaseMatcher<PrintableResult>(var0) {
         final String val$string;

         {
            this.val$string = var1;
         }

         @Override
         public void describeTo(Description var1) {
            StringBuilder var2 = new StringBuilder("has failure containing ");
            var2.append(this.val$string);
            var1.appendText(var2.toString());
         }

         @Override
         public boolean matches(Object var1) {
            return var1.toString().contains(this.val$string);
         }
      };
   }

   public static Matcher<Object> hasSingleFailureContaining(String var0) {
      return new BaseMatcher<Object>(var0) {
         final String val$string;

         {
            this.val$string = var1;
         }

         @Override
         public void describeTo(Description var1) {
            StringBuilder var2 = new StringBuilder("has single failure containing ");
            var2.append(this.val$string);
            var1.appendText(var2.toString());
         }

         @Override
         public boolean matches(Object var1) {
            if (var1.toString().contains(this.val$string)) {
               boolean var2 = true;
               if (ResultMatchers.failureCountIs(1).matches(var1)) {
                  return var2;
               }
            }

            return false;
         }
      };
   }

   public static Matcher<PrintableResult> isSuccessful() {
      return failureCountIs(0);
   }
}
