package io.sentry.android.replay.util

import io.sentry.util.Random

internal fun Random.sample(rate: Double?): Boolean {
   var var2: Boolean = false;
   if (var1 != null) {
      var2 = false;
      if (!(var1 < var0.nextDouble())) {
         var2 = true;
      }
   }

   return var2;
}
