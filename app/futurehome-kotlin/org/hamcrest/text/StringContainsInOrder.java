package org.hamcrest.text;

import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class StringContainsInOrder extends TypeSafeMatcher<String> {
   private final Iterable<String> substrings;

   public StringContainsInOrder(Iterable<String> var1) {
      this.substrings = var1;
   }

   @Factory
   public static Matcher<String> stringContainsInOrder(Iterable<String> var0) {
      return new StringContainsInOrder(var0);
   }

   public void describeMismatchSafely(String var1, Description var2) {
      var2.appendText("was \"").appendText(var1).appendText("\"");
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendText("a string containing ").appendValueList("", ", ", "", this.substrings).appendText(" in order");
   }

   public boolean matchesSafely(String var1) {
      Iterator var4 = this.substrings.iterator();
      int var2 = 0;

      while (var4.hasNext()) {
         int var3 = var1.indexOf((String)var4.next(), var2);
         var2 = var3;
         if (var3 == -1) {
            return false;
         }
      }

      return true;
   }
}
