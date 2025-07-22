package io.sentry.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import io.sentry.Breadcrumb;
import io.sentry.IHub;
import io.sentry.ISentryExecutorService;
import io.sentry.Integration;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.util.AndroidCurrentDateProvider;
import io.sentry.android.core.internal.util.Debouncer;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import io.sentry.util.StringUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class SystemEventsBreadcrumbsIntegration implements Integration, Closeable {
   private final List<String> actions;
   private final Context context;
   private boolean isClosed = false;
   private SentryAndroidOptions options;
   SystemEventsBreadcrumbsIntegration.SystemEventsBroadcastReceiver receiver;
   private final Object startLock = new Object();

   public SystemEventsBreadcrumbsIntegration(Context var1) {
      this(var1, getDefaultActions());
   }

   public SystemEventsBreadcrumbsIntegration(Context var1, List<String> var2) {
      this.context = Objects.requireNonNull(ContextUtils.getApplicationContext(var1), "Context is required");
      this.actions = Objects.requireNonNull(var2, "Actions list is required");
   }

   public static List<String> getDefaultActions() {
      ArrayList var0 = new ArrayList();
      var0.add("android.intent.action.ACTION_SHUTDOWN");
      var0.add("android.intent.action.AIRPLANE_MODE");
      var0.add("android.intent.action.BATTERY_CHANGED");
      var0.add("android.intent.action.CAMERA_BUTTON");
      var0.add("android.intent.action.CONFIGURATION_CHANGED");
      var0.add("android.intent.action.DATE_CHANGED");
      var0.add("android.intent.action.DEVICE_STORAGE_LOW");
      var0.add("android.intent.action.DEVICE_STORAGE_OK");
      var0.add("android.intent.action.DOCK_EVENT");
      var0.add("android.intent.action.DREAMING_STARTED");
      var0.add("android.intent.action.DREAMING_STOPPED");
      var0.add("android.intent.action.INPUT_METHOD_CHANGED");
      var0.add("android.intent.action.LOCALE_CHANGED");
      var0.add("android.intent.action.SCREEN_OFF");
      var0.add("android.intent.action.SCREEN_ON");
      var0.add("android.intent.action.TIMEZONE_CHANGED");
      var0.add("android.intent.action.TIME_SET");
      var0.add("android.os.action.DEVICE_IDLE_MODE_CHANGED");
      var0.add("android.os.action.POWER_SAVE_MODE_CHANGED");
      return var0;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void startSystemEventsReceiver(IHub var1, SentryAndroidOptions var2) {
      this.receiver = new SystemEventsBreadcrumbsIntegration.SystemEventsBroadcastReceiver(var1, var2);
      IntentFilter var6 = new IntentFilter();
      Iterator var3 = this.actions.iterator();

      while (var3.hasNext()) {
         var6.addAction((String)var3.next());
      }

      try {
         ContextUtils.registerReceiver(this.context, var2, this.receiver, var6);
         var2.getLogger().log(SentryLevel.DEBUG, "SystemEventsBreadcrumbsIntegration installed.");
         IntegrationUtils.addIntegrationToSdkVersion("SystemEventsBreadcrumbs");
      } catch (Throwable var5) {
         var2.setEnableSystemEventBreadcrumbs(false);
         var2.getLogger().log(SentryLevel.ERROR, "Failed to initialize SystemEventsBreadcrumbsIntegration.", var5);
         return;
      }
   }

   @Override
   public void close() throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/android/core/SystemEventsBreadcrumbsIntegration.startLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield io/sentry/android/core/SystemEventsBreadcrumbsIntegration.isClosed Z
      // 0c: aload 2
      // 0d: monitorexit
      // 0e: aload 0
      // 0f: getfield io/sentry/android/core/SystemEventsBreadcrumbsIntegration.receiver Lio/sentry/android/core/SystemEventsBreadcrumbsIntegration$SystemEventsBroadcastReceiver;
      // 12: astore 1
      // 13: aload 1
      // 14: ifnull 3f
      // 17: aload 0
      // 18: getfield io/sentry/android/core/SystemEventsBreadcrumbsIntegration.context Landroid/content/Context;
      // 1b: aload 1
      // 1c: invokevirtual android/content/Context.unregisterReceiver (Landroid/content/BroadcastReceiver;)V
      // 1f: aload 0
      // 20: aconst_null
      // 21: putfield io/sentry/android/core/SystemEventsBreadcrumbsIntegration.receiver Lio/sentry/android/core/SystemEventsBreadcrumbsIntegration$SystemEventsBroadcastReceiver;
      // 24: aload 0
      // 25: getfield io/sentry/android/core/SystemEventsBreadcrumbsIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 28: astore 1
      // 29: aload 1
      // 2a: ifnull 3f
      // 2d: aload 1
      // 2e: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 31: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 34: ldc "SystemEventsBreadcrumbsIntegration remove."
      // 36: bipush 0
      // 37: anewarray 4
      // 3a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 3f: return
      // 40: astore 1
      // 41: aload 2
      // 42: monitorexit
      // 43: aload 1
      // 44: athrow
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void register(IHub var1, SentryOptions var2) {
      Objects.requireNonNull(var1, "Hub is required");
      SentryAndroidOptions var3;
      if (var2 instanceof SentryAndroidOptions) {
         var3 = (SentryAndroidOptions)var2;
      } else {
         var3 = null;
      }

      var3 = Objects.requireNonNull(var3, "SentryAndroidOptions is required");
      this.options = var3;
      var3.getLogger().log(SentryLevel.DEBUG, "SystemEventsBreadcrumbsIntegration enabled: %s", this.options.isEnableSystemEventBreadcrumbs());
      if (this.options.isEnableSystemEventBreadcrumbs()) {
         try {
            ISentryExecutorService var8 = var2.getExecutorService();
            SystemEventsBreadcrumbsIntegration$$ExternalSyntheticLambda0 var4 = new SystemEventsBreadcrumbsIntegration$$ExternalSyntheticLambda0(
               this, var1, var2
            );
            var8.submit(var4);
         } catch (Throwable var6) {
            var2.getLogger().log(SentryLevel.DEBUG, "Failed to start SystemEventsBreadcrumbsIntegration on executor thread.", var6);
            return;
         }
      }
   }

   static final class SystemEventsBroadcastReceiver extends BroadcastReceiver {
      private static final long DEBOUNCE_WAIT_TIME_MS = 60000L;
      private final Debouncer batteryChangedDebouncer = new Debouncer(AndroidCurrentDateProvider.getInstance(), 60000L, 0);
      private final IHub hub;
      private final SentryAndroidOptions options;

      SystemEventsBroadcastReceiver(IHub var1, SentryAndroidOptions var2) {
         this.hub = var1;
         this.options = var2;
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private Breadcrumb createBreadcrumb(long var1, Intent var3, String var4, boolean var5) {
         Breadcrumb var6 = new Breadcrumb(var1);
         var6.setType("system");
         var6.setCategory("device.event");
         String var7 = StringUtils.getStringAfterDot(var4);
         if (var7 != null) {
            var6.setData("action", var7);
         }

         if (var5) {
            Float var19 = DeviceInfoUtil.getBatteryLevel(var3, this.options);
            if (var19 != null) {
               var6.setData("level", var19);
            }

            Boolean var17 = DeviceInfoUtil.isCharging(var3, this.options);
            if (var17 != null) {
               var6.setData("charging", var17);
            }
         } else {
            Bundle var9 = var3.getExtras();
            HashMap var18 = new HashMap();
            if (var9 != null && !var9.isEmpty()) {
               Iterator var8 = var9.keySet().iterator();

               label109:
               while (true) {
                  Object var10;
                  while (true) {
                     if (!var8.hasNext()) {
                        var6.setData("extras", var18);
                        break label109;
                     }

                     var7 = (String)var8.next();

                     try {
                        var10 = var9.get(var7);
                        break;
                     } catch (Throwable var15) {
                        this.options.getLogger().log(SentryLevel.ERROR, var15, "%s key of the %s action threw an error.", var7, var4);
                        continue;
                     }
                  }

                  if (var10 != null) {
                     try {
                        var18.put(var7, var10.toString());
                     } catch (Throwable var16) {
                        this.options.getLogger().log(SentryLevel.ERROR, var16, "%s key of the %s action threw an error.", var7, var4);
                        continue;
                     }
                  }
               }
            }
         }

         var6.setLevel(SentryLevel.INFO);
         return var6;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onReceive(Context var1, Intent var2) {
         String var7 = var2.getAction();
         boolean var5 = "android.intent.action.BATTERY_CHANGED".equals(var7);
         if (!var5 || !this.batteryChangedDebouncer.checkForDebounce()) {
            long var3 = System.currentTimeMillis();

            try {
               ISentryExecutorService var10 = this.options.getExecutorService();
               SystemEventsBreadcrumbsIntegration$SystemEventsBroadcastReceiver$$ExternalSyntheticLambda0 var6 = new SystemEventsBreadcrumbsIntegration$SystemEventsBroadcastReceiver$$ExternalSyntheticLambda0(
                  this, var3, var2, var7, var5
               );
               var10.submit(var6);
            } catch (Throwable var9) {
               this.options.getLogger().log(SentryLevel.ERROR, var9, "Failed to submit system event breadcrumb action.");
               return;
            }
         }
      }
   }
}
