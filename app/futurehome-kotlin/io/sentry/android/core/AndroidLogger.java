package io.sentry.android.core;

import android.util.Log;
import io.sentry.ILogger;
import io.sentry.SentryLevel;

public final class AndroidLogger implements ILogger {
   private final String tag;

   public AndroidLogger() {
      this("Sentry");
   }

   public AndroidLogger(String var1) {
      this.tag = var1;
   }

   private int toLogcatLevel(SentryLevel var1) {
      int var2 = <unrepresentable>.$SwitchMap$io$sentry$SentryLevel[var1.ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            return var2 != 4 ? 3 : 7;
         } else {
            return 5;
         }
      } else {
         return 4;
      }
   }

   @Override
   public boolean isEnabled(SentryLevel var1) {
      return true;
   }

   @Override
   public void log(SentryLevel var1, String var2, Throwable var3) {
      int var4 = <unrepresentable>.$SwitchMap$io$sentry$SentryLevel[var1.ordinal()];
      if (var4 != 1) {
         if (var4 != 2) {
            if (var4 != 3) {
               if (var4 != 4) {
                  Log.d(this.tag, var2, var3);
               } else {
                  Log.wtf(this.tag, var2, var3);
               }
            } else {
               Log.e(this.tag, var2, var3);
            }
         } else {
            Log.w(this.tag, var2, var3);
         }
      } else {
         Log.i(this.tag, var2, var3);
      }
   }

   @Override
   public void log(SentryLevel var1, String var2, Object... var3) {
      if (var3 != null && var3.length != 0) {
         Log.println(this.toLogcatLevel(var1), this.tag, String.format(var2, var3));
      } else {
         Log.println(this.toLogcatLevel(var1), this.tag, var2);
      }
   }

   @Override
   public void log(SentryLevel var1, Throwable var2, String var3, Object... var4) {
      if (var4 != null && var4.length != 0) {
         this.log(var1, String.format(var3, var4), var2);
      } else {
         this.log(var1, var3, var2);
      }
   }
}
