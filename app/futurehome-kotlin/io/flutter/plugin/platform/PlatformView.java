package io.flutter.plugin.platform;

import android.view.View;

public interface PlatformView {
   void dispose();

   View getView();

   void onFlutterViewAttached(View var1);

   void onFlutterViewDetached();

   void onInputConnectionLocked();

   void onInputConnectionUnlocked();
}
