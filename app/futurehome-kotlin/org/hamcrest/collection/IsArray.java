package org.hamcrest.collection;

import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsArray<T> extends TypeSafeMatcher<T[]> {
   private final Matcher<? super T>[] elementMatchers;

   public IsArray(Matcher<? super T>[] var1) {
      this.elementMatchers = (Matcher<? super T>[])var1.clone();
   }

   @Factory
   public static <T> IsArray<T> array(Matcher<? super T>... var0) {
      return new IsArray<>(var0);
   }

   public void describeMismatchSafely(T[] var1, Description var2) {
      if (var1.length != this.elementMatchers.length) {
         StringBuilder var5 = new StringBuilder("array length was ");
         var5.append(var1.length);
         var2.appendText(var5.toString());
      } else {
         for (int var3 = 0; var3 < var1.length; var3++) {
            if (!this.elementMatchers[var3].matches(var1[var3])) {
               StringBuilder var4 = new StringBuilder("element ");
               var4.append(var3);
               var4.append(" was ");
               var2.appendText(var4.toString()).appendValue(var1[var3]);
               return;
            }
         }
      }
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendList(this.descriptionStart(), this.descriptionSeparator(), this.descriptionEnd(), Arrays.asList(this.elementMatchers));
   }

   protected String descriptionEnd() {
      return "]";
   }

   protected String descriptionSeparator() {
      return ", ";
   }

   protected String descriptionStart() {
      return "[";
   }

   public boolean matchesSafely(T[] var1) {
      if (var1.length != this.elementMatchers.length) {
         return false;
      } else {
         for (int var2 = 0; var2 < var1.length; var2++) {
            if (!this.elementMatchers[var2].matches(var1[var2])) {
               return false;
            }
         }

         return true;
      }
   }
}
