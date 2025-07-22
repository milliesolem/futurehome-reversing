package io.sentry.android.core.performance;

import android.view.Window.Callback;
import io.sentry.android.core.internal.gestures.WindowCallbackAdapter;

public class WindowContentChangedCallback extends WindowCallbackAdapter {
   private final Runnable callback;

   public WindowContentChangedCallback(Callback var1, Runnable var2) {
      super(var1);
      this.callback = var2;
   }

   @Override
   public void onContentChanged() {
      super.onContentChanged();
      this.callback.run();
   }
}
