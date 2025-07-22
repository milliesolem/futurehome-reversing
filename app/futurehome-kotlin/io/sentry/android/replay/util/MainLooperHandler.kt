package io.sentry.android.replay.util

import android.os.Handler
import android.os.Looper

internal class MainLooperHandler(looper: Looper = Looper.getMainLooper()) {
   public final val handler: Handler

   fun MainLooperHandler() {
      this(null, 1, null);
   }

   init {
      this.handler = new Handler(var1);
   }

   public fun post(runnable: Runnable) {
      this.handler.post(var1);
   }
}
