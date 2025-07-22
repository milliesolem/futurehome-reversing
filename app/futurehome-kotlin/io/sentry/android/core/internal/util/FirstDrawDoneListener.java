package io.sentry.android.core.internal.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.Window.Callback;
import io.sentry.android.core.BuildInfoProvider;
import io.sentry.android.core.internal.gestures.NoOpWindowCallback;
import io.sentry.android.core.performance.WindowContentChangedCallback;
import java.util.concurrent.atomic.AtomicReference;

public class FirstDrawDoneListener implements OnDrawListener {
   private final Runnable callback;
   private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
   private final AtomicReference<View> viewReference;

   private FirstDrawDoneListener(View var1, Runnable var2) {
      this.viewReference = new AtomicReference<>(var1);
      this.callback = var2;
   }

   private static boolean isAliveAndAttached(View var0) {
      boolean var1;
      if (var0.getViewTreeObserver().isAlive() && var0.isAttachedToWindow()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static void registerForNextDraw(Activity var0, Runnable var1, BuildInfoProvider var2) {
      Window var4 = var0.getWindow();
      if (var4 != null) {
         View var5 = var4.peekDecorView();
         if (var5 != null) {
            registerForNextDraw(var5, var1, var2);
         } else {
            Callback var3 = var4.getCallback();
            Object var6;
            if (var3 != null) {
               var6 = var3;
            } else {
               var6 = new NoOpWindowCallback();
            }

            var4.setCallback(new WindowContentChangedCallback((Callback)var6, new FirstDrawDoneListener$$ExternalSyntheticLambda1(var4, var3, var1, var2)));
         }
      }
   }

   public static void registerForNextDraw(View var0, Runnable var1, BuildInfoProvider var2) {
      FirstDrawDoneListener var3 = new FirstDrawDoneListener(var0, var1);
      if (var2.getSdkInfoVersion() < 26 && !isAliveAndAttached(var0)) {
         var0.addOnAttachStateChangeListener(new OnAttachStateChangeListener(var3) {
            final FirstDrawDoneListener val$listener;

            {
               this.val$listener = var1;
            }

            public void onViewAttachedToWindow(View var1) {
               var1.getViewTreeObserver().addOnDrawListener(this.val$listener);
               var1.removeOnAttachStateChangeListener(this);
            }

            public void onViewDetachedFromWindow(View var1) {
               var1.removeOnAttachStateChangeListener(this);
            }
         });
      } else {
         var0.getViewTreeObserver().addOnDrawListener(var3);
      }
   }

   public void onDraw() {
      View var1 = this.viewReference.getAndSet(null);
      if (var1 != null) {
         var1.getViewTreeObserver().addOnGlobalLayoutListener(new FirstDrawDoneListener$$ExternalSyntheticLambda0(this, var1));
         this.mainThreadHandler.postAtFrontOfQueue(this.callback);
      }
   }
}
