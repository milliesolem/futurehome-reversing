package io.sentry.util.thread;

import io.sentry.protocol.SentryThread;

public final class MainThreadChecker implements IMainThreadChecker {
   private static final MainThreadChecker instance = new MainThreadChecker();
   private static final long mainThreadId = Thread.currentThread().getId();

   private MainThreadChecker() {
   }

   public static MainThreadChecker getInstance() {
      return instance;
   }

   @Override
   public boolean isMainThread() {
      return this.isMainThread(Thread.currentThread());
   }

   @Override
   public boolean isMainThread(long var1) {
      boolean var3;
      if (mainThreadId == var1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   @Override
   public boolean isMainThread(SentryThread var1) {
      Long var3 = var1.getId();
      boolean var2;
      if (var3 != null && this.isMainThread(var3)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public boolean isMainThread(Thread var1) {
      return this.isMainThread(var1.getId());
   }
}
