package io.sentry.android.core.internal.util;

import android.content.Context;
import android.os.Process;
import io.sentry.util.Objects;

public final class Permissions {
   private Permissions() {
   }

   public static boolean hasPermission(Context var0, String var1) {
      Objects.requireNonNull(var0, "The application context is required.");
      boolean var2;
      if (var0.checkPermission(var1, Process.myPid(), Process.myUid()) == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
