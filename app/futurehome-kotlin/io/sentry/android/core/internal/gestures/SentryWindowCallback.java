package io.sentry.android.core.internal.gestures;

import android.content.Context;
import android.view.MotionEvent;
import android.view.Window.Callback;
import androidx.core.view.GestureDetectorCompat;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.SpanStatus;

public final class SentryWindowCallback extends WindowCallbackAdapter {
   private final Callback delegate;
   private final GestureDetectorCompat gestureDetector;
   private final SentryGestureListener gestureListener;
   private final SentryWindowCallback.MotionEventObtainer motionEventObtainer;
   private final SentryOptions options;

   public SentryWindowCallback(Callback var1, Context var2, SentryGestureListener var3, SentryOptions var4) {
      this(var1, new GestureDetectorCompat(var2, var3), var3, var4, new SentryWindowCallback.MotionEventObtainer() {});
   }

   SentryWindowCallback(
      Callback var1, GestureDetectorCompat var2, SentryGestureListener var3, SentryOptions var4, SentryWindowCallback.MotionEventObtainer var5
   ) {
      super(var1);
      this.delegate = var1;
      this.gestureListener = var3;
      this.options = var4;
      this.gestureDetector = var2;
      this.motionEventObtainer = var5;
   }

   private void handleTouchEvent(MotionEvent var1) {
      this.gestureDetector.onTouchEvent(var1);
      if (var1.getActionMasked() == 1) {
         this.gestureListener.onUp(var1);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public boolean dispatchTouchEvent(MotionEvent var1) {
      if (var1 != null) {
         MotionEvent var2 = this.motionEventObtainer.obtain(var1);

         try {
            this.handleTouchEvent(var2);
         } catch (Throwable var9) {
            Throwable var3 = var9;

            label42:
            try {
               if (this.options != null) {
                  this.options.getLogger().log(SentryLevel.ERROR, "Error dispatching touch event", var3);
               }
               break label42;
            } finally {
               var2.recycle();
            }
         }
      }

      return super.dispatchTouchEvent(var1);
   }

   public Callback getDelegate() {
      return this.delegate;
   }

   public void stopTracking() {
      this.gestureListener.stopTracing(SpanStatus.CANCELLED);
   }

   interface MotionEventObtainer {
      MotionEvent obtain(MotionEvent var1);
   }
}
