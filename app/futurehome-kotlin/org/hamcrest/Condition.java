package org.hamcrest;

public abstract class Condition<T> {
   public static final Condition.NotMatched<Object> NOT_MATCHED = new Condition.NotMatched<>();

   private Condition() {
   }

   public static <T> Condition<T> matched(T var0, Description var1) {
      return new Condition.Matched<>(var0, var1);
   }

   public static <T> Condition<T> notMatched() {
      return (Condition<T>)NOT_MATCHED;
   }

   public abstract <U> Condition<U> and(Condition.Step<? super T, U> var1);

   public final boolean matching(Matcher<T> var1) {
      return this.matching(var1, "");
   }

   public abstract boolean matching(Matcher<T> var1, String var2);

   public final <U> Condition<U> then(Condition.Step<? super T, U> var1) {
      return this.and(var1);
   }

   private static final class Matched<T> extends Condition<T> {
      private final Description mismatch;
      private final T theValue;

      private Matched(T var1, Description var2) {
         this.theValue = (T)var1;
         this.mismatch = var2;
      }

      @Override
      public <U> Condition<U> and(Condition.Step<? super T, U> var1) {
         return var1.apply(this.theValue, this.mismatch);
      }

      @Override
      public boolean matching(Matcher<T> var1, String var2) {
         if (var1.matches(this.theValue)) {
            return true;
         } else {
            this.mismatch.appendText(var2);
            var1.describeMismatch(this.theValue, this.mismatch);
            return false;
         }
      }
   }

   private static final class NotMatched<T> extends Condition<T> {
      private NotMatched() {
      }

      @Override
      public <U> Condition<U> and(Condition.Step<? super T, U> var1) {
         return notMatched();
      }

      @Override
      public boolean matching(Matcher<T> var1, String var2) {
         return false;
      }
   }

   public interface Step<I, O> {
      Condition<O> apply(I var1, Description var2);
   }
}
