package io.sentry.android.core.internal.gestures;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import io.sentry.Breadcrumb;
import io.sentry.Hint;
import io.sentry.IHub;
import io.sentry.ILogger;
import io.sentry.IScope;
import io.sentry.ITransaction;
import io.sentry.SentryLevel;
import io.sentry.SpanContext;
import io.sentry.SpanStatus;
import io.sentry.TransactionContext;
import io.sentry.TransactionOptions;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.internal.gestures.UiElement;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.util.TracingUtils;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;

public final class SentryGestureListener implements OnGestureListener {
   private static final String TRACE_ORIGIN = "auto.ui.gesture_listener";
   static final String UI_ACTION = "ui.action";
   private SentryGestureListener.GestureType activeEventType;
   private ITransaction activeTransaction;
   private UiElement activeUiElement = null;
   private final WeakReference<Activity> activityRef;
   private final IHub hub;
   private final SentryAndroidOptions options;
   private final SentryGestureListener.ScrollState scrollState;

   public SentryGestureListener(Activity var1, IHub var2, SentryAndroidOptions var3) {
      this.activeTransaction = null;
      this.activeEventType = SentryGestureListener.GestureType.Unknown;
      this.scrollState = new SentryGestureListener.ScrollState();
      this.activityRef = new WeakReference<>(var1);
      this.hub = var2;
      this.options = var3;
   }

   private void addBreadcrumb(UiElement var1, SentryGestureListener.GestureType var2, Map<String, Object> var3, MotionEvent var4) {
      if (this.options.isEnableUserInteractionBreadcrumbs()) {
         String var5 = getGestureType(var2);
         Hint var6 = new Hint();
         var6.set("android:motionEvent", var4);
         var6.set("android:view", var1.getView());
         this.hub.addBreadcrumb(Breadcrumb.userInteraction(var5, var1.getResourceName(), var1.getClassName(), var1.getTag(), var3), var6);
      }
   }

   private View ensureWindowDecorView(String var1) {
      Activity var2 = this.activityRef.get();
      if (var2 == null) {
         ILogger var9 = this.options.getLogger();
         SentryLevel var13 = SentryLevel.DEBUG;
         StringBuilder var11 = new StringBuilder("Activity is null in ");
         var11.append(var1);
         var11.append(". No breadcrumb captured.");
         var9.log(var13, var11.toString());
         return null;
      } else {
         Window var5 = var2.getWindow();
         if (var5 == null) {
            ILogger var10 = this.options.getLogger();
            SentryLevel var8 = SentryLevel.DEBUG;
            StringBuilder var12 = new StringBuilder("Window is null in ");
            var12.append(var1);
            var12.append(". No breadcrumb captured.");
            var10.log(var8, var12.toString());
            return null;
         } else {
            View var6 = var5.getDecorView();
            if (var6 == null) {
               ILogger var3 = this.options.getLogger();
               SentryLevel var4 = SentryLevel.DEBUG;
               StringBuilder var7 = new StringBuilder("DecorView is null in ");
               var7.append(var1);
               var7.append(". No breadcrumb captured.");
               var3.log(var4, var7.toString());
               return null;
            } else {
               return var6;
            }
         }
      }
   }

   private String getActivityName(Activity var1) {
      return var1.getClass().getSimpleName();
   }

   private static String getGestureType(SentryGestureListener.GestureType var0) {
      int var1 = <unrepresentable>.$SwitchMap$io$sentry$android$core$internal$gestures$SentryGestureListener$GestureType[var0.ordinal()];
      String var2;
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               var2 = "unknown";
            } else {
               var2 = "swipe";
            }
         } else {
            var2 = "scroll";
         }
      } else {
         var2 = "click";
      }

      return var2;
   }

   private void startTracing(UiElement var1, SentryGestureListener.GestureType var2) {
      boolean var3;
      if (var2 == this.activeEventType && var1.equals(this.activeUiElement)) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var2 != SentryGestureListener.GestureType.Click && var3) {
         var3 = false;
      } else {
         var3 = true;
      }

      if (this.options.isTracingEnabled() && this.options.isEnableUserInteractionTracing()) {
         Activity var5 = this.activityRef.get();
         if (var5 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Activity is null, no transaction captured.");
         } else {
            String var4 = var1.getIdentifier();
            ITransaction var6 = this.activeTransaction;
            if (var6 != null) {
               if (!var3 && !var6.isFinished()) {
                  ILogger var8 = this.options.getLogger();
                  SentryLevel var15 = SentryLevel.DEBUG;
                  StringBuilder var7 = new StringBuilder("The view with id: ");
                  var7.append(var4);
                  var7.append(" already has an ongoing transaction assigned. Rescheduling finish");
                  var8.log(var15, var7.toString());
                  if (this.options.getIdleTimeout() != null) {
                     this.activeTransaction.scheduleFinish();
                  }

                  return;
               }

               this.stopTracing(SpanStatus.OK);
            }

            StringBuilder var16 = new StringBuilder();
            var16.append(this.getActivityName(var5));
            var16.append(".");
            var16.append(var4);
            var4 = var16.toString();
            StringBuilder var12 = new StringBuilder("ui.action.");
            var12.append(getGestureType(var2));
            String var17 = var12.toString();
            TransactionOptions var13 = new TransactionOptions();
            var13.setWaitForChildren(true);
            var13.setDeadlineTimeout(30000L);
            var13.setIdleTimeout(this.options.getIdleTimeout());
            var13.setTrimEnd(true);
            ITransaction var11 = this.hub.startTransaction(new TransactionContext(var4, TransactionNameSource.COMPONENT, var17), var13);
            SpanContext var14 = var11.getSpanContext();
            StringBuilder var18 = new StringBuilder("auto.ui.gesture_listener.");
            var18.append(var1.getOrigin());
            var14.setOrigin(var18.toString());
            this.hub.configureScope(new SentryGestureListener$$ExternalSyntheticLambda3(this, var11));
            this.activeTransaction = var11;
            this.activeUiElement = var1;
            this.activeEventType = var2;
         }
      } else {
         if (var3) {
            TracingUtils.startNewTrace(this.hub);
            this.activeUiElement = var1;
            this.activeEventType = var2;
         }
      }
   }

   void applyScope(IScope var1, ITransaction var2) {
      var1.withTransaction(new SentryGestureListener$$ExternalSyntheticLambda0(this, var1, var2));
   }

   void clearScope(IScope var1) {
      var1.withTransaction(new SentryGestureListener$$ExternalSyntheticLambda2(this, var1));
   }

   public boolean onDown(MotionEvent var1) {
      if (var1 == null) {
         return false;
      } else {
         this.scrollState.reset();
         this.scrollState.startX = var1.getX();
         this.scrollState.startY = var1.getY();
         return false;
      }
   }

   public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
      this.scrollState.type = SentryGestureListener.GestureType.Swipe;
      return false;
   }

   public void onLongPress(MotionEvent var1) {
   }

   public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4) {
      View var8 = this.ensureWindowDecorView("onScroll");
      if (var8 != null && var1 != null && this.scrollState.type == SentryGestureListener.GestureType.Unknown) {
         UiElement var6 = ViewUtils.findTarget(this.options, var8, var1.getX(), var1.getY(), UiElement.Type.SCROLLABLE);
         if (var6 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Unable to find scroll target. No breadcrumb captured.");
            return false;
         }

         ILogger var7 = this.options.getLogger();
         SentryLevel var5 = SentryLevel.DEBUG;
         StringBuilder var9 = new StringBuilder("Scroll target found: ");
         var9.append(var6.getIdentifier());
         var7.log(var5, var9.toString());
         this.scrollState.setTarget(var6);
         this.scrollState.type = SentryGestureListener.GestureType.Scroll;
      }

      return false;
   }

   public void onShowPress(MotionEvent var1) {
   }

   public boolean onSingleTapUp(MotionEvent var1) {
      View var2 = this.ensureWindowDecorView("onSingleTapUp");
      if (var2 != null && var1 != null) {
         UiElement var3 = ViewUtils.findTarget(this.options, var2, var1.getX(), var1.getY(), UiElement.Type.CLICKABLE);
         if (var3 == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Unable to find click target. No breadcrumb captured.");
            return false;
         }

         this.addBreadcrumb(var3, SentryGestureListener.GestureType.Click, Collections.emptyMap(), var1);
         this.startTracing(var3, SentryGestureListener.GestureType.Click);
      }

      return false;
   }

   public void onUp(MotionEvent var1) {
      View var3 = this.ensureWindowDecorView("onUp");
      UiElement var2 = this.scrollState.target;
      if (var3 != null && var2 != null) {
         if (this.scrollState.type == SentryGestureListener.GestureType.Unknown) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Unable to define scroll type. No breadcrumb captured.");
            return;
         }

         String var4 = this.scrollState.calculateDirection(var1);
         this.addBreadcrumb(var2, this.scrollState.type, Collections.singletonMap("direction", var4), var1);
         this.startTracing(var2, this.scrollState.type);
         this.scrollState.reset();
      }
   }

   void stopTracing(SpanStatus var1) {
      ITransaction var2 = this.activeTransaction;
      if (var2 != null) {
         if (var2.getStatus() == null) {
            this.activeTransaction.finish(var1);
         } else {
            this.activeTransaction.finish();
         }
      }

      this.hub.configureScope(new SentryGestureListener$$ExternalSyntheticLambda1(this));
      this.activeTransaction = null;
      if (this.activeUiElement != null) {
         this.activeUiElement = null;
      }

      this.activeEventType = SentryGestureListener.GestureType.Unknown;
   }

   private static enum GestureType {
      Click,
      Scroll,
      Swipe,
      Unknown;
      private static final SentryGestureListener.GestureType[] $VALUES = $values();
   }

   private static final class ScrollState {
      private float startX;
      private float startY;
      private UiElement target;
      private SentryGestureListener.GestureType type = SentryGestureListener.GestureType.Unknown;

      private ScrollState() {
         this.startX = 0.0F;
         this.startY = 0.0F;
      }

      private String calculateDirection(MotionEvent var1) {
         float var2 = var1.getX() - this.startX;
         float var3 = var1.getY() - this.startY;
         String var4;
         if (Math.abs(var2) > Math.abs(var3)) {
            if (var2 > 0.0F) {
               var4 = "right";
            } else {
               var4 = "left";
            }
         } else if (var3 > 0.0F) {
            var4 = "down";
         } else {
            var4 = "up";
         }

         return var4;
      }

      private void reset() {
         this.target = null;
         this.type = SentryGestureListener.GestureType.Unknown;
         this.startX = 0.0F;
         this.startY = 0.0F;
      }

      private void setTarget(UiElement var1) {
         this.target = var1;
      }
   }
}
