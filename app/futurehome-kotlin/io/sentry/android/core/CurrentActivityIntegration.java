package io.sentry.android.core;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SentryOptions;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

public final class CurrentActivityIntegration implements Integration, Closeable, ActivityLifecycleCallbacks {
   private final Application application;

   public CurrentActivityIntegration(Application var1) {
      this.application = Objects.requireNonNull(var1, "Application is required");
   }

   private void cleanCurrentActivity(Activity var1) {
      if (CurrentActivityHolder.getInstance().getActivity() == var1) {
         CurrentActivityHolder.getInstance().clearActivity();
      }
   }

   private void setCurrentActivity(Activity var1) {
      CurrentActivityHolder.getInstance().setActivity(var1);
   }

   @Override
   public void close() throws IOException {
      this.application.unregisterActivityLifecycleCallbacks(this);
      CurrentActivityHolder.getInstance().clearActivity();
   }

   public void onActivityCreated(Activity var1, Bundle var2) {
      this.setCurrentActivity(var1);
   }

   public void onActivityDestroyed(Activity var1) {
      this.cleanCurrentActivity(var1);
   }

   public void onActivityPaused(Activity var1) {
      this.cleanCurrentActivity(var1);
   }

   public void onActivityResumed(Activity var1) {
      this.setCurrentActivity(var1);
   }

   public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
   }

   public void onActivityStarted(Activity var1) {
      this.setCurrentActivity(var1);
   }

   public void onActivityStopped(Activity var1) {
      this.cleanCurrentActivity(var1);
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      this.application.registerActivityLifecycleCallbacks(this);
   }
}
