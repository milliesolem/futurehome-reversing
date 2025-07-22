package io.sentry.android.core;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.view.Window;
import android.view.Window.Callback;
import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.gestures.NoOpWindowCallback;
import io.sentry.android.core.internal.gestures.SentryGestureListener;
import io.sentry.android.core.internal.gestures.SentryWindowCallback;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

public final class UserInteractionIntegration implements Integration, Closeable, ActivityLifecycleCallbacks {
   private final Application application;
   private IHub hub;
   private final boolean isAndroidXAvailable;
   private SentryAndroidOptions options;

   public UserInteractionIntegration(Application var1, LoadClass var2) {
      this.application = Objects.requireNonNull(var1, "Application is required");
      this.isAndroidXAvailable = var2.isClassAvailable("androidx.core.view.GestureDetectorCompat", this.options);
   }

   private void startTracking(Activity var1) {
      Window var4 = var1.getWindow();
      if (var4 == null) {
         SentryAndroidOptions var5 = this.options;
         if (var5 != null) {
            var5.getLogger().log(SentryLevel.INFO, "Window was null in startTracking");
         }
      } else {
         if (this.hub != null && this.options != null) {
            Callback var3 = var4.getCallback();
            Object var2 = var3;
            if (var3 == null) {
               var2 = new NoOpWindowCallback();
            }

            if (var2 instanceof SentryWindowCallback) {
               return;
            }

            var4.setCallback(new SentryWindowCallback((Callback)var2, var1, new SentryGestureListener(var1, this.hub, this.options), this.options));
         }
      }
   }

   private void stopTracking(Activity var1) {
      Window var3 = var1.getWindow();
      if (var3 == null) {
         SentryAndroidOptions var4 = this.options;
         if (var4 != null) {
            var4.getLogger().log(SentryLevel.INFO, "Window was null in stopTracking");
         }
      } else {
         Callback var2 = var3.getCallback();
         if (var2 instanceof SentryWindowCallback) {
            SentryWindowCallback var5 = (SentryWindowCallback)var2;
            var5.stopTracking();
            if (var5.getDelegate() instanceof NoOpWindowCallback) {
               var3.setCallback(null);
            } else {
               var3.setCallback(var5.getDelegate());
            }
         }
      }
   }

   @Override
   public void close() throws IOException {
      this.application.unregisterActivityLifecycleCallbacks(this);
      SentryAndroidOptions var1 = this.options;
      if (var1 != null) {
         var1.getLogger().log(SentryLevel.DEBUG, "UserInteractionIntegration removed.");
      }
   }

   public void onActivityCreated(Activity var1, Bundle var2) {
   }

   public void onActivityDestroyed(Activity var1) {
   }

   public void onActivityPaused(Activity var1) {
      this.stopTracking(var1);
   }

   public void onActivityResumed(Activity var1) {
      this.startTracking(var1);
   }

   public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
   }

   public void onActivityStarted(Activity var1) {
   }

   public void onActivityStopped(Activity var1) {
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      SentryAndroidOptions var4;
      if (var2 instanceof SentryAndroidOptions) {
         var4 = (SentryAndroidOptions)var2;
      } else {
         var4 = null;
      }

      this.options = Objects.requireNonNull(var4, "SentryAndroidOptions is required");
      this.hub = Objects.requireNonNull(var1, "Hub is required");
      boolean var3;
      if (!this.options.isEnableUserInteractionBreadcrumbs() && !this.options.isEnableUserInteractionTracing()) {
         var3 = false;
      } else {
         var3 = true;
      }

      this.options.getLogger().log(SentryLevel.DEBUG, "UserInteractionIntegration enabled: %s", var3);
      if (var3) {
         if (this.isAndroidXAvailable) {
            this.application.registerActivityLifecycleCallbacks(this);
            this.options.getLogger().log(SentryLevel.DEBUG, "UserInteractionIntegration installed.");
            IntegrationUtils.addIntegrationToSdkVersion("UserInteraction");
         } else {
            var2.getLogger().log(SentryLevel.INFO, "androidx.core is not available, UserInteractionIntegration won't be installed");
         }
      }
   }
}
