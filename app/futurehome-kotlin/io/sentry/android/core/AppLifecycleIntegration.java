package io.sentry.android.core;

import androidx.lifecycle.ProcessLifecycleOwner;
import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.util.AndroidMainThreadChecker;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

public final class AppLifecycleIntegration implements Integration, Closeable {
   private final MainLooperHandler handler;
   private SentryAndroidOptions options;
   volatile LifecycleWatcher watcher;

   public AppLifecycleIntegration() {
      this(new MainLooperHandler());
   }

   AppLifecycleIntegration(MainLooperHandler var1) {
      this.handler = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void addObserver(IHub var1) {
      if (this.options != null) {
         this.watcher = new LifecycleWatcher(
            var1, this.options.getSessionTrackingIntervalMillis(), this.options.isEnableAutoSessionTracking(), this.options.isEnableAppLifecycleBreadcrumbs()
         );

         try {
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this.watcher);
            this.options.getLogger().log(SentryLevel.DEBUG, "AppLifecycleIntegration installed.");
            IntegrationUtils.addIntegrationToSdkVersion("AppLifecycle");
         } catch (Throwable var3) {
            this.watcher = null;
            this.options.getLogger().log(SentryLevel.ERROR, "AppLifecycleIntegration failed to get Lifecycle and could not be installed.", var3);
            return;
         }
      }
   }

   private void removeObserver() {
      LifecycleWatcher var1 = this.watcher;
      if (var1 != null) {
         ProcessLifecycleOwner.get().getLifecycle().removeObserver(var1);
         SentryAndroidOptions var2 = this.options;
         if (var2 != null) {
            var2.getLogger().log(SentryLevel.DEBUG, "AppLifecycleIntegration removed.");
         }
      }

      this.watcher = null;
   }

   @Override
   public void close() throws IOException {
      if (this.watcher != null) {
         if (AndroidMainThreadChecker.getInstance().isMainThread()) {
            this.removeObserver();
         } else {
            this.handler.post(new AppLifecycleIntegration$$ExternalSyntheticLambda0(this));
         }
      }
   }

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
      var3.getLogger().log(SentryLevel.DEBUG, "enableSessionTracking enabled: %s", this.options.isEnableAutoSessionTracking());
      this.options.getLogger().log(SentryLevel.DEBUG, "enableAppLifecycleBreadcrumbs enabled: %s", this.options.isEnableAppLifecycleBreadcrumbs());
      if (this.options.isEnableAutoSessionTracking() || this.options.isEnableAppLifecycleBreadcrumbs()) {
         try {
            Class.forName("androidx.lifecycle.DefaultLifecycleObserver");
            Class.forName("androidx.lifecycle.ProcessLifecycleOwner");
            if (AndroidMainThreadChecker.getInstance().isMainThread()) {
               this.addObserver(var1);
            } else {
               MainLooperHandler var4 = this.handler;
               AppLifecycleIntegration$$ExternalSyntheticLambda1 var8 = new AppLifecycleIntegration$$ExternalSyntheticLambda1(this, var1);
               var4.post(var8);
            }
         } catch (ClassNotFoundException var5) {
            var2.getLogger().log(SentryLevel.INFO, "androidx.lifecycle is not available, AppLifecycleIntegration won't be installed", var5);
         } catch (IllegalStateException var6) {
            var2.getLogger().log(SentryLevel.ERROR, "AppLifecycleIntegration could not be installed", var6);
         }
      }
   }
}
