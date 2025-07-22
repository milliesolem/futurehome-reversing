package io.sentry.android.core;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import io.sentry.Breadcrumb;
import io.sentry.Hint;
import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.util.DeviceOrientations;
import io.sentry.protocol.Device;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;
import java.util.Locale;

public final class AppComponentsBreadcrumbsIntegration implements Integration, Closeable, ComponentCallbacks2 {
   private final Context context;
   private IHub hub;
   private SentryAndroidOptions options;

   public AppComponentsBreadcrumbsIntegration(Context var1) {
      this.context = Objects.requireNonNull(ContextUtils.getApplicationContext(var1), "Context is required");
   }

   private void captureConfigurationChangedBreadcrumb(long var1, Configuration var3) {
      if (this.hub != null) {
         Device.DeviceOrientation var4 = DeviceOrientations.getOrientation(this.context.getResources().getConfiguration().orientation);
         String var6;
         if (var4 != null) {
            var6 = var4.name().toLowerCase(Locale.ROOT);
         } else {
            var6 = "undefined";
         }

         Breadcrumb var5 = new Breadcrumb(var1);
         var5.setType("navigation");
         var5.setCategory("device.orientation");
         var5.setData("position", var6);
         var5.setLevel(SentryLevel.INFO);
         Hint var7 = new Hint();
         var7.set("android:configuration", var3);
         this.hub.addBreadcrumb(var5, var7);
      }
   }

   private void captureLowMemoryBreadcrumb(long var1, Integer var3) {
      if (this.hub != null) {
         Breadcrumb var4 = new Breadcrumb(var1);
         if (var3 != null) {
            if (var3 < 40) {
               return;
            }

            var4.setData("level", var3);
         }

         var4.setType("system");
         var4.setCategory("device.event");
         var4.setMessage("Low memory");
         var4.setData("action", "LOW_MEMORY");
         var4.setLevel(SentryLevel.WARNING);
         this.hub.addBreadcrumb(var4);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void executeInBackground(Runnable var1) {
      SentryAndroidOptions var2 = this.options;
      if (var2 != null) {
         try {
            var2.getExecutorService().submit(var1);
         } catch (Throwable var4) {
            this.options.getLogger().log(SentryLevel.ERROR, var4, "Failed to submit app components breadcrumb task");
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void close() throws IOException {
      label31:
      try {
         this.context.unregisterComponentCallbacks(this);
      } catch (Throwable var4) {
         SentryAndroidOptions var1 = this.options;
         if (var1 != null) {
            var1.getLogger().log(SentryLevel.DEBUG, var4, "It was not possible to unregisterComponentCallbacks");
         }
         break label31;
      }

      SentryAndroidOptions var5 = this.options;
      if (var5 != null) {
         var5.getLogger().log(SentryLevel.DEBUG, "AppComponentsBreadcrumbsIntegration removed.");
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      this.executeInBackground(new AppComponentsBreadcrumbsIntegration$$ExternalSyntheticLambda1(this, System.currentTimeMillis(), var1));
   }

   public void onLowMemory() {
      this.executeInBackground(new AppComponentsBreadcrumbsIntegration$$ExternalSyntheticLambda0(this, System.currentTimeMillis()));
   }

   public void onTrimMemory(int var1) {
      this.executeInBackground(new AppComponentsBreadcrumbsIntegration$$ExternalSyntheticLambda2(this, System.currentTimeMillis(), var1));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void register(IHub var1, SentryOptions var2) {
      this.hub = Objects.requireNonNull(var1, "Hub is required");
      SentryAndroidOptions var5;
      if (var2 instanceof SentryAndroidOptions) {
         var5 = (SentryAndroidOptions)var2;
      } else {
         var5 = null;
      }

      SentryAndroidOptions var6 = Objects.requireNonNull(var5, "SentryAndroidOptions is required");
      this.options = var6;
      var6.getLogger().log(SentryLevel.DEBUG, "AppComponentsBreadcrumbsIntegration enabled: %s", this.options.isEnableAppComponentBreadcrumbs());
      if (this.options.isEnableAppComponentBreadcrumbs()) {
         try {
            this.context.registerComponentCallbacks(this);
            var2.getLogger().log(SentryLevel.DEBUG, "AppComponentsBreadcrumbsIntegration installed.");
            IntegrationUtils.addIntegrationToSdkVersion("AppComponentsBreadcrumbs");
         } catch (Throwable var4) {
            this.options.setEnableAppComponentBreadcrumbs(false);
            var2.getLogger().log(SentryLevel.INFO, var4, "ComponentCallbacks2 is not available.");
            return;
         }
      }
   }
}
