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

public final class NoOpWindowCallback implements Callback {
   public boolean dispatchGenericMotionEvent(MotionEvent var1) {
      return false;
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      return false;
   }

   public boolean dispatchKeyShortcutEvent(KeyEvent var1) {
      return false;
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      return false;
   }

   public boolean dispatchTouchEvent(MotionEvent var1) {
      return false;
   }

   public boolean dispatchTrackballEvent(MotionEvent var1) {
      return false;
   }

   public void onActionModeFinished(ActionMode var1) {
   }

   public void onActionModeStarted(ActionMode var1) {
   }

   public void onAttachedToWindow() {
   }

   public void onContentChanged() {
   }

   public boolean onCreatePanelMenu(int var1, Menu var2) {
      return false;
   }

   public View onCreatePanelView(int var1) {
      return null;
   }

   public void onDetachedFromWindow() {
   }

   public boolean onMenuItemSelected(int var1, MenuItem var2) {
      return false;
   }

   public boolean onMenuOpened(int var1, Menu var2) {
      return false;
   }

   public void onPanelClosed(int var1, Menu var2) {
   }

   public boolean onPreparePanel(int var1, View var2, Menu var3) {
      return false;
   }

   public boolean onSearchRequested() {
      return false;
   }

   public boolean onSearchRequested(SearchEvent var1) {
      return false;
   }

   public void onWindowAttributesChanged(LayoutParams var1) {
   }

   public void onWindowFocusChanged(boolean var1) {
   }

   public ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback var1) {
      return null;
   }

   public ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback var1, int var2) {
      return null;
   }
}
