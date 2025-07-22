package io.flutter.plugin.platform;

import android.app.AlertDialog;
import android.app.Presentation;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.MutableContextWrapper;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnFocusChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import io.flutter.Log;

class SingleViewPresentation extends Presentation {
   private static final String TAG = "PlatformViewsController";
   private final AccessibilityEventsDelegate accessibilityEventsDelegate;
   private FrameLayout container;
   private final OnFocusChangeListener focusChangeListener;
   private final Context outerContext;
   private SingleViewPresentation.AccessibilityDelegatingFrameLayout rootView;
   private boolean startFocused = false;
   private final SingleViewPresentation.PresentationState state;
   private int viewId;

   public SingleViewPresentation(
      Context var1, Display var2, AccessibilityEventsDelegate var3, SingleViewPresentation.PresentationState var4, OnFocusChangeListener var5, boolean var6
   ) {
      super(new SingleViewPresentation.ImmContext(var1), var2);
      this.accessibilityEventsDelegate = var3;
      this.state = var4;
      this.focusChangeListener = var5;
      this.outerContext = var1;
      this.getWindow().setFlags(8, 8);
      this.startFocused = var6;
   }

   public SingleViewPresentation(Context var1, Display var2, PlatformView var3, AccessibilityEventsDelegate var4, int var5, OnFocusChangeListener var6) {
      super(new SingleViewPresentation.ImmContext(var1), var2);
      this.accessibilityEventsDelegate = var4;
      this.viewId = var5;
      this.focusChangeListener = var6;
      this.outerContext = var1;
      SingleViewPresentation.PresentationState var7 = new SingleViewPresentation.PresentationState();
      this.state = var7;
      var7.platformView = var3;
      this.getWindow().setFlags(8, 8);
      this.getWindow().setType(2030);
   }

   public SingleViewPresentation.PresentationState detachState() {
      FrameLayout var1 = this.container;
      if (var1 != null) {
         var1.removeAllViews();
      }

      SingleViewPresentation.AccessibilityDelegatingFrameLayout var2 = this.rootView;
      if (var2 != null) {
         var2.removeAllViews();
      }

      return this.state;
   }

   public PlatformView getView() {
      return this.state.platformView;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
      if (this.state.fakeWindowViewGroup == null) {
         this.state.fakeWindowViewGroup = new SingleViewFakeWindowViewGroup(this.getContext());
      }

      if (this.state.windowManagerHandler == null) {
         WindowManager var3 = (WindowManager)this.getContext().getSystemService("window");
         this.state.windowManagerHandler = new WindowManagerHandler(var3, this.state.fakeWindowViewGroup);
      }

      this.container = new FrameLayout(this.getContext());
      SingleViewPresentation.PresentationContext var2 = new SingleViewPresentation.PresentationContext(
         this.getContext(), this.state.windowManagerHandler, this.outerContext
      );
      View var4 = this.state.platformView.getView();
      if (var4.getContext() instanceof MutableContextWrapper) {
         ((MutableContextWrapper)var4.getContext()).setBaseContext(var2);
      } else {
         StringBuilder var5 = new StringBuilder("Unexpected platform view context for view ID ");
         var5.append(this.viewId);
         var5.append(
            "; some functionality may not work correctly. When constructing a platform view in the factory, ensure that the view returned from PlatformViewFactory#create returns the provided context from getContext(). If you are unable to associate the view with that context, consider using Hybrid Composition instead."
         );
         Log.w("PlatformViewsController", var5.toString());
      }

      this.container.addView(var4);
      SingleViewPresentation.AccessibilityDelegatingFrameLayout var6 = new SingleViewPresentation.AccessibilityDelegatingFrameLayout(
         this.getContext(), this.accessibilityEventsDelegate, var4
      );
      this.rootView = var6;
      var6.addView(this.container);
      this.rootView.addView(this.state.fakeWindowViewGroup);
      var4.setOnFocusChangeListener(this.focusChangeListener);
      this.rootView.setFocusableInTouchMode(true);
      if (this.startFocused) {
         var4.requestFocus();
      } else {
         this.rootView.requestFocus();
      }

      this.setContentView(this.rootView);
   }

   private static class AccessibilityDelegatingFrameLayout extends FrameLayout {
      private final AccessibilityEventsDelegate accessibilityEventsDelegate;
      private final View embeddedView;

      public AccessibilityDelegatingFrameLayout(Context var1, AccessibilityEventsDelegate var2, View var3) {
         super(var1);
         this.accessibilityEventsDelegate = var2;
         this.embeddedView = var3;
      }

      public boolean requestSendAccessibilityEvent(View var1, AccessibilityEvent var2) {
         return this.accessibilityEventsDelegate.requestSendAccessibilityEvent(this.embeddedView, var1, var2);
      }
   }

   private static class ImmContext extends ContextWrapper {
      private final InputMethodManager inputMethodManager;

      ImmContext(Context var1) {
         this(var1, null);
      }

      private ImmContext(Context var1, InputMethodManager var2) {
         super(var1);
         if (var2 == null) {
            var2 = (InputMethodManager)var1.getSystemService("input_method");
         }

         this.inputMethodManager = var2;
      }

      public Context createDisplayContext(Display var1) {
         return new SingleViewPresentation.ImmContext(super.createDisplayContext(var1), this.inputMethodManager);
      }

      public Object getSystemService(String var1) {
         return "input_method".equals(var1) ? this.inputMethodManager : super.getSystemService(var1);
      }
   }

   private static class PresentationContext extends ContextWrapper {
      private final Context flutterAppWindowContext;
      private WindowManager windowManager;
      private final WindowManagerHandler windowManagerHandler;

      PresentationContext(Context var1, WindowManagerHandler var2, Context var3) {
         super(var1);
         this.windowManagerHandler = var2;
         this.flutterAppWindowContext = var3;
      }

      private WindowManager getWindowManager() {
         if (this.windowManager == null) {
            this.windowManager = this.windowManagerHandler;
         }

         return this.windowManager;
      }

      private boolean isCalledFromAlertDialog() {
         StackTraceElement[] var2 = Thread.currentThread().getStackTrace();

         for (int var1 = 0; var1 < var2.length && var1 < 11; var1++) {
            if (var2[var1].getClassName().equals(AlertDialog.class.getCanonicalName()) && var2[var1].getMethodName().equals("<init>")) {
               return true;
            }
         }

         return false;
      }

      public Object getSystemService(String var1) {
         if ("window".equals(var1)) {
            return this.isCalledFromAlertDialog() ? this.flutterAppWindowContext.getSystemService(var1) : this.getWindowManager();
         } else {
            return super.getSystemService(var1);
         }
      }
   }

   static class PresentationState {
      private SingleViewFakeWindowViewGroup fakeWindowViewGroup;
      private PlatformView platformView;
      private WindowManagerHandler windowManagerHandler;
   }
}
