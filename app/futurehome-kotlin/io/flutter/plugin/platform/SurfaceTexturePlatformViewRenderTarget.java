package io.flutter.plugin.platform;

import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.view.Surface;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.flutter.view.TextureRegistry;

public class SurfaceTexturePlatformViewRenderTarget implements PlatformViewRenderTarget {
   private static final String TAG = "SurfaceTexturePlatformViewRenderTarget";
   private int bufferHeight;
   private int bufferWidth = 0;
   private boolean shouldRecreateSurfaceForLowMemory;
   private Surface surface;
   private SurfaceTexture surfaceTexture;
   private final TextureRegistry.SurfaceTextureEntry surfaceTextureEntry;
   private final TextureRegistry.OnTrimMemoryListener trimMemoryListener;

   public SurfaceTexturePlatformViewRenderTarget(TextureRegistry.SurfaceTextureEntry var1) {
      this.bufferHeight = 0;
      this.shouldRecreateSurfaceForLowMemory = false;
      TextureRegistry.OnTrimMemoryListener var2 = new TextureRegistry.OnTrimMemoryListener(this) {
         final SurfaceTexturePlatformViewRenderTarget this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onTrimMemory(int var1) {
            if (var1 == 80 && VERSION.SDK_INT >= 29) {
               this.this$0.shouldRecreateSurfaceForLowMemory = true;
            }
         }
      };
      this.trimMemoryListener = var2;
      if (VERSION.SDK_INT >= 23) {
         this.surfaceTextureEntry = var1;
         this.surfaceTexture = var1.surfaceTexture();
         var1.setOnTrimMemoryListener(var2);
      } else {
         throw new UnsupportedOperationException(
            "Platform views cannot be displayed below API level 23You can prevent this issue by setting `minSdkVersion: 23` in build.gradle."
         );
      }
   }

   private void recreateSurfaceIfNeeded() {
      Surface var1 = this.surface;
      if (var1 == null || this.shouldRecreateSurfaceForLowMemory) {
         if (var1 != null) {
            var1.release();
            this.surface = null;
         }

         this.surface = this.createSurface();
         this.shouldRecreateSurfaceForLowMemory = false;
      }
   }

   protected Surface createSurface() {
      return new Surface(this.surfaceTexture);
   }

   @Override
   public int getHeight() {
      return this.bufferHeight;
   }

   @Override
   public long getId() {
      return this.surfaceTextureEntry.id();
   }

   @Override
   public Surface getSurface() {
      this.recreateSurfaceIfNeeded();
      SurfaceTexture var1 = this.surfaceTexture;
      return var1 != null && !AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1) ? this.surface : null;
   }

   @Override
   public int getWidth() {
      return this.bufferWidth;
   }

   @Override
   public boolean isReleased() {
      boolean var1;
      if (this.surfaceTexture == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void release() {
      this.surfaceTexture = null;
      Surface var1 = this.surface;
      if (var1 != null) {
         var1.release();
         this.surface = null;
      }
   }

   @Override
   public void resize(int var1, int var2) {
      this.bufferWidth = var1;
      this.bufferHeight = var2;
      SurfaceTexture var3 = this.surfaceTexture;
      if (var3 != null) {
         var3.setDefaultBufferSize(var1, var2);
      }
   }
}
