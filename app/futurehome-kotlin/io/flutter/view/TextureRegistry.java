package io.flutter.view;

import android.graphics.SurfaceTexture;
import android.media.Image;
import android.view.Surface;

public interface TextureRegistry {
   TextureRegistry.ImageTextureEntry createImageTexture();

   TextureRegistry.SurfaceProducer createSurfaceProducer();

   TextureRegistry.SurfaceTextureEntry createSurfaceTexture();

   void onTrimMemory(int var1);

   TextureRegistry.SurfaceTextureEntry registerSurfaceTexture(SurfaceTexture var1);

   public interface GLTextureConsumer {
      SurfaceTexture getSurfaceTexture();
   }

   public interface ImageConsumer {
      Image acquireLatestImage();
   }

   public interface ImageTextureEntry extends TextureRegistry.TextureEntry {
      void pushImage(Image var1);
   }

   public interface OnFrameConsumedListener {
      void onFrameConsumed();
   }

   public interface OnTrimMemoryListener {
      void onTrimMemory(int var1);
   }

   public interface SurfaceProducer extends TextureRegistry.TextureEntry {
      int getHeight();

      Surface getSurface();

      int getWidth();

      boolean handlesCropAndRotation();

      void scheduleFrame();

      void setCallback(TextureRegistry.SurfaceProducer.Callback var1);

      void setSize(int var1, int var2);

      public interface Callback {
         void onSurfaceAvailable();

         @Deprecated(
            forRemoval = true,
            since = "Flutter 3.27"
         )
         void onSurfaceCreated();

         void onSurfaceDestroyed();
      }
   }

   public interface SurfaceTextureEntry extends TextureRegistry.TextureEntry {
      void setOnFrameConsumedListener(TextureRegistry.OnFrameConsumedListener var1);

      void setOnTrimMemoryListener(TextureRegistry.OnTrimMemoryListener var1);

      SurfaceTexture surfaceTexture();
   }

   public interface TextureEntry {
      long id();

      void release();
   }
}
