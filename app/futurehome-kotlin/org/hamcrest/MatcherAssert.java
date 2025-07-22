package org.hamcrest;

public class MatcherAssert {
   public static <T> void assertThat(T var0, Matcher<? super T> var1) {
      assertThat("", var0, var1);
   }

   public static <T> void assertThat(String var0, T var1, Matcher<? super T> var2) {
      if (!var2.matches(var1)) {
         StringDescription var3 = new StringDescription();
         var3.appendText(var0).appendText("\nExpected: ").appendDescriptionOf(var2).appendText("\n     but: ");
         var2.describeMismatch(var1, var3);
         throw new AssertionError(var3.toString());
      }
   }

   public static void assertThat(String var0, boolean var1) {
      if (!var1) {
         throw new AssertionError(var0);
      }
   }
}
