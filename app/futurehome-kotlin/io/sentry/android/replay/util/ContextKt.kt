package io.sentry.android.replay.util

import android.content.Context

internal fun Context.appContext(): Context {
   val var1: Context = var0.getApplicationContext();
   if (var1 != null) {
      var0 = var1;
   }

   return var0;
}
