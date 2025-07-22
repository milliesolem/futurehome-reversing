package com.mixpanel.android.util;

import android.util.Log;

public class MPLog {
   public static final int DEBUG = 3;
   public static final int ERROR = 6;
   public static final int INFO = 4;
   public static final int NONE = Integer.MAX_VALUE;
   public static final int VERBOSE = 2;
   public static final int WARN = 5;
   private static int sMinLevel;

   public static void d(String var0, String var1) {
      if (shouldLog(3)) {
         Log.d(var0, var1);
      }
   }

   public static void d(String var0, String var1, Throwable var2) {
      if (shouldLog(3)) {
         Log.d(var0, var1, var2);
      }
   }

   public static void e(String var0, String var1) {
      if (shouldLog(6)) {
         Log.e(var0, var1);
      }
   }

   public static void e(String var0, String var1, Throwable var2) {
      if (shouldLog(6)) {
         Log.e(var0, var1, var2);
      }
   }

   public static int getLevel() {
      return sMinLevel;
   }

   public static void i(String var0, String var1) {
      if (shouldLog(4)) {
         Log.i(var0, var1);
      }
   }

   public static void i(String var0, String var1, Throwable var2) {
      if (shouldLog(4)) {
         Log.i(var0, var1, var2);
      }
   }

   public static void setLevel(int var0) {
      sMinLevel = var0;
   }

   private static boolean shouldLog(int var0) {
      boolean var1;
      if (sMinLevel <= var0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static void v(String var0, String var1) {
      if (shouldLog(2)) {
         Log.v(var0, var1);
      }
   }

   public static void v(String var0, String var1, Throwable var2) {
      if (shouldLog(2)) {
         Log.v(var0, var1, var2);
      }
   }

   public static void w(String var0, String var1) {
      if (shouldLog(5)) {
         Log.w(var0, var1);
      }
   }

   public static void w(String var0, String var1, Throwable var2) {
      if (shouldLog(5)) {
         Log.w(var0, var1, var2);
      }
   }
}
