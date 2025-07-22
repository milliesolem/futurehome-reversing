package io.sentry.android.core;

import android.util.Log;
import io.sentry.Breadcrumb;
import io.sentry.Sentry;
import io.sentry.SentryLevel;

public final class SentryLogcatAdapter {
   private static void addAsBreadcrumb(String var0, SentryLevel var1, String var2) {
      addAsBreadcrumb(var0, var1, var2, null);
   }

   private static void addAsBreadcrumb(String var0, SentryLevel var1, String var2, Throwable var3) {
      Breadcrumb var4 = new Breadcrumb();
      var4.setCategory("Logcat");
      var4.setMessage(var2);
      var4.setLevel(var1);
      if (var0 != null) {
         var4.setData("tag", var0);
      }

      if (var3 != null && var3.getMessage() != null) {
         var4.setData("throwable", var3.getMessage());
      }

      Sentry.addBreadcrumb(var4);
   }

   private static void addAsBreadcrumb(String var0, SentryLevel var1, Throwable var2) {
      addAsBreadcrumb(var0, var1, null, var2);
   }

   public static int d(String var0, String var1) {
      addAsBreadcrumb(var0, SentryLevel.DEBUG, var1);
      return Log.d(var0, var1);
   }

   public static int d(String var0, String var1, Throwable var2) {
      addAsBreadcrumb(var0, SentryLevel.DEBUG, var1, var2);
      return Log.d(var0, var1, var2);
   }

   public static int e(String var0, String var1) {
      addAsBreadcrumb(var0, SentryLevel.ERROR, var1);
      return Log.e(var0, var1);
   }

   public static int e(String var0, String var1, Throwable var2) {
      addAsBreadcrumb(var0, SentryLevel.ERROR, var1, var2);
      return Log.e(var0, var1, var2);
   }

   public static int i(String var0, String var1) {
      addAsBreadcrumb(var0, SentryLevel.INFO, var1);
      return Log.i(var0, var1);
   }

   public static int i(String var0, String var1, Throwable var2) {
      addAsBreadcrumb(var0, SentryLevel.INFO, var1, var2);
      return Log.i(var0, var1, var2);
   }

   public static int v(String var0, String var1) {
      addAsBreadcrumb(var0, SentryLevel.DEBUG, var1);
      return Log.v(var0, var1);
   }

   public static int v(String var0, String var1, Throwable var2) {
      addAsBreadcrumb(var0, SentryLevel.DEBUG, var1, var2);
      return Log.v(var0, var1, var2);
   }

   public static int w(String var0, String var1) {
      addAsBreadcrumb(var0, SentryLevel.WARNING, var1);
      return Log.w(var0, var1);
   }

   public static int w(String var0, String var1, Throwable var2) {
      addAsBreadcrumb(var0, SentryLevel.WARNING, var1, var2);
      return Log.w(var0, var1, var2);
   }

   public static int w(String var0, Throwable var1) {
      addAsBreadcrumb(var0, SentryLevel.WARNING, var1);
      return Log.w(var0, var1);
   }

   public static int wtf(String var0, String var1) {
      addAsBreadcrumb(var0, SentryLevel.ERROR, var1);
      return Log.wtf(var0, var1);
   }

   public static int wtf(String var0, String var1, Throwable var2) {
      addAsBreadcrumb(var0, SentryLevel.ERROR, var1, var2);
      return Log.wtf(var0, var1, var2);
   }

   public static int wtf(String var0, Throwable var1) {
      addAsBreadcrumb(var0, SentryLevel.ERROR, var1);
      return Log.wtf(var0, var1);
   }
}
