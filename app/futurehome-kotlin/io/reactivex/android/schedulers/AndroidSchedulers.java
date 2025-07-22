package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Build.VERSION;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import java.util.concurrent.Callable;

public final class AndroidSchedulers {
   private static final Scheduler MAIN_THREAD = RxAndroidPlugins.initMainThreadScheduler(new Callable<Scheduler>() {
      public Scheduler call() throws Exception {
         return AndroidSchedulers.MainHolder.DEFAULT;
      }
   });

   private AndroidSchedulers() {
      throw new AssertionError("No instances.");
   }

   public static Scheduler from(Looper var0) {
      return from(var0, false);
   }

   public static Scheduler from(Looper var0, boolean var1) {
      if (var0 != null) {
         boolean var2 = var1;
         if (var1) {
            var2 = var1;
            if (VERSION.SDK_INT < 22) {
               Message var3 = Message.obtain();

               try {
                  AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var3, true);
               } catch (NoSuchMethodError var5) {
                  var1 = false;
               }

               var3.recycle();
               var2 = var1;
            }
         }

         return new HandlerScheduler(new Handler(var0), var2);
      } else {
         throw new NullPointerException("looper == null");
      }
   }

   public static Scheduler mainThread() {
      return RxAndroidPlugins.onMainThreadScheduler(MAIN_THREAD);
   }

   private static final class MainHolder {
      static final Scheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()), false);
   }
}
