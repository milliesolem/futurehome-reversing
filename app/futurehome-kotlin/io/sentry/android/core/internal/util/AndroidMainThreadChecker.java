package io.sentry.android.core.internal.util;

import android.os.Looper;
import io.sentry.protocol.SentryThread;
import io.sentry.util.thread.IMainThreadChecker;

public final class AndroidMainThreadChecker implements IMainThreadChecker {
   private static final AndroidMainThreadChecker instance = new AndroidMainThreadChecker();

   private AndroidMainThreadChecker() {
   }

   public static AndroidMainThreadChecker getInstance() {
      return instance;
   }

   @Override
   public boolean isMainThread() {
      return this.isMainThread(Thread.currentThread());
   }

   @Override
   public boolean isMainThread(long var1) {
      boolean var3;
      if (Looper.getMainLooper().getThread().getId() == var1) {
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
