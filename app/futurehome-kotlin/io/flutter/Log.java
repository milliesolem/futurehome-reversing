package io.flutter;

public class Log {
   public static int ASSERT;
   public static int DEBUG;
   public static int ERROR;
   public static int INFO;
   public static int VERBOSE;
   public static int WARN;
   private static int logLevel;

   public static void d(String var0, String var1) {
   }

   public static void d(String var0, String var1, Throwable var2) {
   }

   public static void e(String var0, String var1) {
      android.util.Log.e(var0, var1);
   }

   public static void e(String var0, String var1, Throwable var2) {
      android.util.Log.e(var0, var1, var2);
   }

   public static String getStackTraceString(Throwable var0) {
      return android.util.Log.getStackTraceString(var0);
   }

   public static void i(String var0, String var1) {
   }

   public static void i(String var0, String var1, Throwable var2) {
   }

   public static void println(int var0, String var1, String var2) {
   }

   public static void setLogLevel(int var0) {
      logLevel = var0;
   }

   public static void v(String var0, String var1) {
   }

   public static void v(String var0, String var1, Throwable var2) {
   }

   public static void w(String var0, String var1) {
      android.util.Log.w(var0, var1);
   }

   public static void w(String var0, String var1, Throwable var2) {
      android.util.Log.w(var0, var1, var2);
   }

   public static void wtf(String var0, String var1) {
      android.util.Log.wtf(var0, var1);
   }

   public static void wtf(String var0, String var1, Throwable var2) {
      android.util.Log.wtf(var0, var1, var2);
   }
}
