package io.flutter.plugin.platform;

import android.view.Surface;

public interface PlatformViewRenderTarget {
   int getHeight();

   long getId();

   Surface getSurface();

   int getWidth();

   boolean isReleased();

   void release();

   void resize(int var1, int var2);

   void scheduleFrame();
}
