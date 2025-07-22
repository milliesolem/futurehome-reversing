package io.sentry.android.core.internal.gestures;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window.Callback;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0;

public class WindowCallbackAdapter implements Callback {
   private final Callback delegate;

   public WindowCallbackAdapter(Callback var1) {
      this.delegate = var1;
   }

   public boolean dispatchGenericMotionEvent(MotionEvent var1) {
      return this.delegate.dispatchGenericMotionEvent(var1);
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      return this.delegate.dispatchKeyEvent(var1);
   }

   public boolean dispatchKeyShortcutEvent(KeyEvent var1) {
      return this.delegate.dispatchKeyShortcutEvent(var1);
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      return this.delegate.dispatchPopulateAccessibilityEvent(var1);
   }

   public boolean dispatchTouchEvent(MotionEvent var1) {
      return this.delegate.dispatchTouchEvent(var1);
   }

   public boolean dispatchTrackballEvent(MotionEvent var1) {
      return this.delegate.dispatchTrackballEvent(var1);
   }

   public void onActionModeFinished(ActionMode var1) {
      this.delegate.onActionModeFinished(var1);
   }

   public void onActionModeStarted(ActionMode var1) {
      this.delegate.onActionModeStarted(var1);
   }

   public void onAttachedToWindow() {
      this.delegate.onAttachedToWindow();
   }

   public void onContentChanged() {
      this.delegate.onContentChanged();
   }

   public boolean onCreatePanelMenu(int var1, Menu var2) {
      return this.delegate.onCreatePanelMenu(var1, var2);
   }

   public View onCreatePanelView(int var1) {
      return this.delegate.onCreatePanelView(var1);
   }

   public void onDetachedFromWindow() {
      this.delegate.onDetachedFromWindow();
   }

   public boolean onMenuItemSelected(int var1, MenuItem var2) {
      return this.delegate.onMenuItemSelected(var1, var2);
   }

   public boolean onMenuOpened(int var1, Menu var2) {
      return this.delegate.onMenuOpened(var1, var2);
   }

   public void onPanelClosed(int var1, Menu var2) {
      this.delegate.onPanelClosed(var1, var2);
   }

   public boolean onPreparePanel(int var1, View var2, Menu var3) {
      return this.delegate.onPreparePanel(var1, var2, var3);
   }

   public boolean onSearchRequested() {
      return this.delegate.onSearchRequested();
   }

   public boolean onSearchRequested(SearchEvent var1) {
      return ExternalSyntheticApiModelOutline0.m(this.delegate, var1);
   }

   public void onWindowAttributesChanged(LayoutParams var1) {
      this.delegate.onWindowAttributesChanged(var1);
   }

   public void onWindowFocusChanged(boolean var1) {
      this.delegate.onWindowFocusChanged(var1);
   }

   public ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback var1) {
      return this.delegate.onWindowStartingActionMode(var1);
   }

   public ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback var1, int var2) {
      return ExternalSyntheticApiModelOutline0.m(this.delegate, var1, var2);
   }
}
