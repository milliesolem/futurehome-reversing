package io.sentry.android.replay

import android.view.Window

internal final val phoneWindow: Window?
   internal final get() {
      val var1: WindowSpy = WindowSpy.INSTANCE;
      var0 = var0.getRootView();
      return var1.pullWindow(var0);
   }

