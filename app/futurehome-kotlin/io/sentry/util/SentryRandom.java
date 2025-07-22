package io.sentry.util;

public final class SentryRandom {
   private static final SentryRandom.SentryRandomThreadLocal instance = new SentryRandom.SentryRandomThreadLocal();

   public static Random current() {
      return instance.get();
   }

   private static class SentryRandomThreadLocal extends ThreadLocal<Random> {
      private SentryRandomThreadLocal() {
      }

      protected Random initialValue() {
         return new Random();
      }
   }
}
