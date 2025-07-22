package io.sentry.android.core;

import android.os.Handler;
import android.os.Looper;

final class MainLooperHandler {
   private final Handler handler;

   MainLooperHandler() {
      this(Looper.getMainLooper());
   }

   MainLooperHandler(Looper var1) {
      this.handler = new Handler(var1);
   }

   public Thread getThread() {
      return this.handler.getLooper().getThread();
   }

   public void post(Runnable var1) {
      this.handler.post(var1);
   }
}
