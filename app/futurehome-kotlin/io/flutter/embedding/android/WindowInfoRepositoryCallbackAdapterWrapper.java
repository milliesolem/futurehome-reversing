package io.flutter.embedding.android;

import android.app.Activity;
import androidx.core.util.Consumer;
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter;
import androidx.window.layout.WindowLayoutInfo;
import java.util.concurrent.Executor;

public class WindowInfoRepositoryCallbackAdapterWrapper {
   final WindowInfoTrackerCallbackAdapter adapter;

   public WindowInfoRepositoryCallbackAdapterWrapper(WindowInfoTrackerCallbackAdapter var1) {
      this.adapter = var1;
   }

   public void addWindowLayoutInfoListener(Activity var1, Executor var2, Consumer<WindowLayoutInfo> var3) {
      this.adapter.addWindowLayoutInfoListener(var1, var2, var3);
   }

   public void removeWindowLayoutInfoListener(Consumer<WindowLayoutInfo> var1) {
      this.adapter.removeWindowLayoutInfoListener(var1);
   }
}
