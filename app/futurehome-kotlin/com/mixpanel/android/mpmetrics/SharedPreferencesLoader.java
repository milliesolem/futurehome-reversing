package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class SharedPreferencesLoader {
   private final Executor mExecutor = Executors.newSingleThreadExecutor();

   public SharedPreferencesLoader() {
   }

   public Future<SharedPreferences> loadPreferences(Context var1, String var2, SharedPreferencesLoader.OnPrefsLoadedListener var3) {
      FutureTask var4 = new FutureTask<>(new SharedPreferencesLoader.LoadSharedPreferences(var1, var2, var3));
      this.mExecutor.execute(var4);
      return var4;
   }

   private static class LoadSharedPreferences implements Callable<SharedPreferences> {
      private final Context mContext;
      private final SharedPreferencesLoader.OnPrefsLoadedListener mListener;
      private final String mPrefsName;

      public LoadSharedPreferences(Context var1, String var2, SharedPreferencesLoader.OnPrefsLoadedListener var3) {
         this.mContext = var1;
         this.mPrefsName = var2;
         this.mListener = var3;
      }

      public SharedPreferences call() {
         SharedPreferences var2 = this.mContext.getSharedPreferences(this.mPrefsName, 0);
         SharedPreferencesLoader.OnPrefsLoadedListener var1 = this.mListener;
         if (var1 != null) {
            var1.onPrefsLoaded(var2);
         }

         return var2;
      }
   }

   interface OnPrefsLoadedListener {
      void onPrefsLoaded(SharedPreferences var1);
   }
}
