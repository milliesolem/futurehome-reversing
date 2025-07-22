package io.sentry.util;

public final class Pair<A, B> {
   private final A first;
   private final B second;

   public Pair(A var1, B var2) {
      this.first = (A)var1;
      this.second = (B)var2;
   }

   public A getFirst() {
      return this.first;
   }

   public B getSecond() {
      return this.second;
   }
}
