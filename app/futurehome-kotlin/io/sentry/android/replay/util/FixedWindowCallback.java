package io.sentry.android.replay.util;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window.Callback;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0;
import java.util.List;

public class FixedWindowCallback implements Callback {
   public final Callback delegate;

   public FixedWindowCallback(Callback var1) {
      this.delegate = var1;
   }

   public boolean dispatchGenericMotionEvent(MotionEvent var1) {
      Callback var2 = this.delegate;
      return var2 == null ? false : var2.dispatchGenericMotionEvent(var1);
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      Callback var2 = this.delegate;
      return var2 == null ? false : var2.dispatchKeyEvent(var1);
   }

   public boolean dispatchKeyShortcutEvent(KeyEvent var1) {
      Callback var2 = this.delegate;
      return var2 == null ? false : var2.dispatchKeyShortcutEvent(var1);
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      Callback var2 = this.delegate;
      return var2 == null ? false : var2.dispatchPopulateAccessibilityEvent(var1);
   }

   public boolean dispatchTouchEvent(MotionEvent var1) {
      Callback var2 = this.delegate;
      return var2 == null ? false : var2.dispatchTouchEvent(var1);
   }

   public boolean dispatchTrackballEvent(MotionEvent var1) {
      Callback var2 = this.delegate;
      return var2 == null ? false : var2.dispatchTrackballEvent(var1);
   }

   public void onActionModeFinished(ActionMode var1) {
      Callback var2 = this.delegate;
      if (var2 != null) {
         var2.onActionModeFinished(var1);
      }
   }

   public void onActionModeStarted(ActionMode var1) {
      Callback var2 = this.delegate;
      if (var2 != null) {
         var2.onActionModeStarted(var1);
      }
   }

   public void onAttachedToWindow() {
      Callback var1 = this.delegate;
      if (var1 != null) {
         var1.onAttachedToWindow();
      }
   }

   public void onContentChanged() {
      Callback var1 = this.delegate;
      if (var1 != null) {
         var1.onContentChanged();
      }
   }

   public boolean onCreatePanelMenu(int var1, Menu var2) {
      Callback var3 = this.delegate;
      return var3 == null ? false : var3.onCreatePanelMenu(var1, var2);
   }

   public View onCreatePanelView(int var1) {
      Callback var2 = this.delegate;
      return var2 == null ? null : var2.onCreatePanelView(var1);
   }

   public void onDetachedFromWindow() {
      Callback var1 = this.delegate;
      if (var1 != null) {
         var1.onDetachedFromWindow();
      }
   }

   public boolean onMenuItemSelected(int var1, MenuItem var2) {
      Callback var3 = this.delegate;
      return var3 == null ? false : var3.onMenuItemSelected(var1, var2);
   }

   public boolean onMenuOpened(int var1, Menu var2) {
      Callback var3 = this.delegate;
      return var3 == null ? false : var3.onMenuOpened(var1, var2);
   }

   public void onPanelClosed(int var1, Menu var2) {
      Callback var3 = this.delegate;
      if (var3 != null) {
         var3.onPanelClosed(var1, var2);
      }
   }

   public void onPointerCaptureChanged(boolean var1) {
      Callback var2 = this.delegate;
      if (var2 != null) {
         ExternalSyntheticApiModelOutline0.m(var2, var1);
      }
   }

   public boolean onPreparePanel(int var1, View var2, Menu var3) {
      Callback var4 = this.delegate;
      return var4 == null ? false : var4.onPreparePanel(var1, var2, var3);
   }

   public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> var1, Menu var2, int var3) {
      Callback var4 = this.delegate;
      if (var4 != null) {
         ExternalSyntheticApiModelOutline0.m(var4, var1, var2, var3);
      }
   }

   public boolean onSearchRequested() {
      Callback var1 = this.delegate;
      return var1 == null ? false : var1.onSearchRequested();
   }

   public boolean onSearchRequested(SearchEvent var1) {
      Callback var2 = this.delegate;
      return var2 == null ? false : ExternalSyntheticApiModelOutline0.m(var2, var1);
   }

   public void onWindowAttributesChanged(LayoutParams var1) {
      Callback var2 = this.delegate;
      if (var2 != null) {
         var2.onWindowAttributesChanged(var1);
      }
   }

   public void onWindowFocusChanged(boolean var1) {
      Callback var2 = this.delegate;
      if (var2 != null) {
         var2.onWindowFocusChanged(var1);
      }
   }

   public ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback var1) {
      Callback var2 = this.delegate;
      return var2 == null ? null : var2.onWindowStartingActionMode(var1);
   }

   public ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback var1, int var2) {
      Callback var3 = this.delegate;
      return var3 == null ? null : ExternalSyntheticApiModelOutline0.m(var3, var1, var2);
   }
}
