package io.sentry.android.core;

import android.app.Activity;
import java.lang.ref.WeakReference;

public class CurrentActivityHolder {
   private static final CurrentActivityHolder instance = new CurrentActivityHolder();
   private WeakReference<Activity> currentActivity;

   private CurrentActivityHolder() {
   }

   public static CurrentActivityHolder getInstance() {
      return instance;
   }

   public void clearActivity() {
      this.currentActivity = null;
   }

   public Activity getActivity() {
      WeakReference var1 = this.currentActivity;
      return var1 != null ? (Activity)var1.get() : null;
   }

   public void setActivity(Activity var1) {
      WeakReference var2 = this.currentActivity;
      if (var2 == null || var2.get() != var1) {
         this.currentActivity = new WeakReference<>(var1);
      }
   }
}
