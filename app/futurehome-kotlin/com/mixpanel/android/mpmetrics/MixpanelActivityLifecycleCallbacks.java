package com.mixpanel.android.mpmetrics;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

class MixpanelActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
   public static final int CHECK_DELAY = 500;
   private static Double sStartSessionTime;
   private Runnable check;
   private final MPConfig mConfig;
   private WeakReference<Activity> mCurrentActivity;
   private final Handler mHandler = new Handler(Looper.getMainLooper());
   private boolean mIsForeground = false;
   private final MixpanelAPI mMpInstance;
   private boolean mPaused = true;

   public MixpanelActivityLifecycleCallbacks(MixpanelAPI var1, MPConfig var2) {
      this.mMpInstance = var1;
      this.mConfig = var2;
      if (sStartSessionTime == null) {
         sStartSessionTime = (double)System.currentTimeMillis();
      }
   }

   protected boolean isInForeground() {
      return this.mIsForeground;
   }

   public void onActivityCreated(Activity var1, Bundle var2) {
   }

   public void onActivityDestroyed(Activity var1) {
   }

   public void onActivityPaused(Activity var1) {
      this.mPaused = true;
      Runnable var3 = this.check;
      if (var3 != null) {
         this.mHandler.removeCallbacks(var3);
      }

      this.mCurrentActivity = null;
      Handler var4 = this.mHandler;
      Runnable var2 = new Runnable(this) {
         final MixpanelActivityLifecycleCallbacks this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void run() {
            if (this.this$0.mIsForeground && this.this$0.mPaused) {
               this.this$0.mIsForeground = false;

               try {
                  double var1x = System.currentTimeMillis() - MixpanelActivityLifecycleCallbacks.sStartSessionTime;
                  if (var1x >= this.this$0.mConfig.getMinimumSessionDuration()
                     && var1x < this.this$0.mConfig.getSessionTimeoutDuration()
                     && this.this$0.mMpInstance.getTrackAutomaticEvents()) {
                     var1x = Math.round(var1x / 1000.0 * 10.0) / 10.0;
                     JSONObject var3x = new JSONObject();
                     var3x.put("$ae_session_length", var1x);
                     this.this$0.mMpInstance.getPeople().increment("$ae_total_app_sessions", 1.0);
                     this.this$0.mMpInstance.getPeople().increment("$ae_total_app_session_length", var1x);
                     this.this$0.mMpInstance.track("$ae_session", var3x, true);
                  }
               } catch (JSONException var4x) {
                  var4x.printStackTrace();
               }

               this.this$0.mMpInstance.onBackground();
            }
         }
      };
      this.check = var2;
      var4.postDelayed(var2, 500L);
   }

   public void onActivityResumed(Activity var1) {
      this.mCurrentActivity = new WeakReference<>(var1);
      this.mPaused = false;
      boolean var2 = this.mIsForeground;
      this.mIsForeground = true;
      Runnable var3 = this.check;
      if (var3 != null) {
         this.mHandler.removeCallbacks(var3);
      }

      if (!var2) {
         sStartSessionTime = (double)System.currentTimeMillis();
         this.mMpInstance.onForeground();
      }
   }

   public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
   }

   public void onActivityStarted(Activity var1) {
   }

   public void onActivityStopped(Activity var1) {
   }
}
