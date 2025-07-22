package io.sentry.util.thread;

import io.sentry.protocol.SentryThread;

public final class NoOpMainThreadChecker implements IMainThreadChecker {
   private static final NoOpMainThreadChecker instance = new NoOpMainThreadChecker();

   public static NoOpMainThreadChecker getInstance() {
      return instance;
   }

   @Override
   public boolean isMainThread() {
      return false;
   }

   @Override
   public boolean isMainThread(long var1) {
      return false;
   }

   @Override
   public boolean isMainThread(SentryThread var1) {
      return false;
   }

   @Override
   public boolean isMainThread(Thread var1) {
      return false;
   }
}
