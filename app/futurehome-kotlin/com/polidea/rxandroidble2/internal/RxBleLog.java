package com.polidea.rxandroidble2.internal;

import android.util.Log;
import com.polidea.rxandroidble2.LogOptions;
import com.polidea.rxandroidble2.internal.logger.LoggerSetup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RxBleLog {
   private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
   @Deprecated
   public static final int DEBUG = 3;
   @Deprecated
   public static final int ERROR = 6;
   @Deprecated
   public static final int INFO = 4;
   private static final LogOptions.Logger LOGCAT_LOGGER;
   private static final ThreadLocal<String> NEXT_TAG = new ThreadLocal<>();
   @Deprecated
   public static final int NONE = Integer.MAX_VALUE;
   @Deprecated
   public static final int VERBOSE = 2;
   @Deprecated
   public static final int WARN = 5;
   private static LoggerSetup loggerSetup;

   static {
      LogOptions.Logger var0 = new LogOptions.Logger() {
         @Override
         public void log(int var1, String var2, String var3) {
            Log.println(var1, var2, var3);
         }
      };
      LOGCAT_LOGGER = var0;
      loggerSetup = new LoggerSetup(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, false, true, var0);
   }

   private RxBleLog() {
   }

   private static String createTag() {
      ThreadLocal var2 = NEXT_TAG;
      String var1 = (String)var2.get();
      if (var1 != null) {
         var2.remove();
         return var1;
      } else {
         StackTraceElement[] var3 = new Throwable().getStackTrace();
         if (var3.length >= 5) {
            var1 = var3[4].getClassName();
            Matcher var7 = ANONYMOUS_CLASS.matcher(var1);
            if (var7.find()) {
               var1 = var7.replaceAll("");
            }

            var1 = var1.replace("Impl", "").replace("RxBle", "");
            int var0 = var1.indexOf(36);
            if (var0 <= 0) {
               var1 = var1.substring(var1.lastIndexOf(46) + 1);
            } else {
               var1 = var1.substring(var1.lastIndexOf(46) + 1, var0);
            }

            StringBuilder var8 = new StringBuilder("RxBle#");
            var8.append(var1);
            return var8.toString();
         } else {
            throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
         }
      }
   }

   public static void d(String var0, Object... var1) {
      throwShade(3, null, var0, var1);
   }

   public static void d(Throwable var0, String var1, Object... var2) {
      throwShade(3, var0, var1, var2);
   }

   public static void e(String var0, Object... var1) {
      throwShade(6, null, var0, var1);
   }

   public static void e(Throwable var0, String var1, Object... var2) {
      throwShade(6, var0, var1, var2);
   }

   private static String formatString(String var0, Object... var1) {
      if (var1.length != 0) {
         var0 = String.format(var0, var1);
      }

      return var0;
   }

   public static int getMacAddressLogSetting() {
      return loggerSetup.macAddressLogSetting;
   }

   public static boolean getShouldLogAttributeValues() {
      return loggerSetup.shouldLogAttributeValues;
   }

   public static boolean getShouldLogScannedPeripherals() {
      return loggerSetup.shouldLogScannedPeripherals;
   }

   public static int getUuidLogSetting() {
      return loggerSetup.uuidLogSetting;
   }

   public static void i(String var0, Object... var1) {
      throwShade(4, null, var0, var1);
   }

   public static void i(Throwable var0, String var1, Object... var2) {
      throwShade(4, var0, var1, var2);
   }

   public static boolean isAtLeast(int var0) {
      boolean var1;
      if (loggerSetup.logLevel <= var0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static void println(int var0, String var1, String var2) {
      if (var2.length() < 4000) {
         loggerSetup.logger.log(var0, var1, var2);
      } else {
         for (var2 : var2.split("\n")) {
            loggerSetup.logger.log(var0, var1, var2);
         }
      }
   }

   @Deprecated
   public static void setLogLevel(int var0) {
      updateLogOptions(new LogOptions.Builder().setLogLevel(var0).build());
   }

   @Deprecated
   public static void setLogger(RxBleLog.Logger var0) {
      LogOptions.Logger var1;
      if (var0 == null) {
         var1 = LOGCAT_LOGGER;
      } else {
         var1 = new LogOptions.Logger(var0) {
            final RxBleLog.Logger val$logger;

            {
               this.val$logger = var1;
            }

            @Override
            public void log(int var1, String var2, String var3) {
               this.val$logger.log(var1, var2, var3);
            }
         };
      }

      updateLogOptions(new LogOptions.Builder().setLogger(var1).build());
   }

   private static void throwShade(int var0, Throwable var1, String var2, Object... var3) {
      if (var0 >= loggerSetup.logLevel) {
         String var6 = formatString(var2, var3);
         if (var6 != null && var6.length() != 0) {
            var2 = var6;
            if (var1 != null) {
               StringBuilder var5 = new StringBuilder();
               var5.append(var6);
               var5.append("\n");
               var5.append(Log.getStackTraceString(var1));
               var2 = var5.toString();
            }
         } else {
            if (var1 == null) {
               return;
            }

            var2 = Log.getStackTraceString(var1);
         }

         println(var0, createTag(), var2);
      }
   }

   public static void updateLogOptions(LogOptions var0) {
      LoggerSetup var2 = loggerSetup;
      LoggerSetup var1 = var2.merge(var0);
      d("Received new options (%s) and merged with old setup: %s. New setup: %s", var0, var2, var1);
      loggerSetup = var1;
   }

   public static void v(String var0, Object... var1) {
      throwShade(2, null, var0, var1);
   }

   public static void v(Throwable var0, String var1, Object... var2) {
      throwShade(2, var0, var1, var2);
   }

   public static void w(String var0, Object... var1) {
      throwShade(5, null, var0, var1);
   }

   public static void w(Throwable var0, String var1, Object... var2) {
      throwShade(5, var0, var1, var2);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface LogLevel {
   }

   public interface Logger {
      void log(int var1, String var2, String var3);
   }
}
