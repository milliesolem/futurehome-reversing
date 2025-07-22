package io.flutter.plugin.platform;

import android.media.ImageReader;
import android.media.ImageReader.Builder;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Handler;
import android.os.Build.VERSION;
import android.view.Surface;
import io.flutter.Log;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.flutter.view.TextureRegistry;

public class ImageReaderPlatformViewRenderTarget implements PlatformViewRenderTarget {
   private static final int MAX_IMAGES = 4;
   private static final String TAG = "ImageReaderPlatformViewRenderTarget";
   private int bufferHeight;
   private int bufferWidth = 0;
   private final Handler onImageAvailableHandler;
   private final OnImageAvailableListener onImageAvailableListener;
   private ImageReader reader;
   private TextureRegistry.ImageTextureEntry textureEntry;

   public ImageReaderPlatformViewRenderTarget(TextureRegistry.ImageTextureEntry var1) {
      this.bufferHeight = 0;
      this.onImageAvailableHandler = new Handler();
      this.onImageAvailableListener = new OnImageAvailableListener(this) {
         final ImageReaderPlatformViewRenderTarget this$0;

         {
            this.this$0 = var1;
         }

         public void onImageAvailable(ImageReader var1) {
            try {
               var5 = var1.acquireLatestImage();
            } catch (IllegalStateException var3) {
               StringBuilder var4 = new StringBuilder("onImageAvailable acquireLatestImage failed: ");
               var4.append(var3.toString());
               Log.e("ImageReaderPlatformViewRenderTarget", var4.toString());
               var5 = null;
            }

            if (var5 != null) {
               this.this$0.textureEntry.pushImage(var5);
            }
         }
      };
      if (VERSION.SDK_INT >= 29) {
         this.textureEntry = var1;
      } else {
         throw new UnsupportedOperationException("ImageReaderPlatformViewRenderTarget requires API version 29+");
      }
   }

   private void closeReader() {
      if (this.reader != null) {
         this.textureEntry.pushImage(null);
         this.reader.close();
         this.reader = null;
      }
   }

   protected ImageReader createImageReader() {
      if (VERSION.SDK_INT >= 33) {
         return this.createImageReader33();
      } else if (VERSION.SDK_INT >= 29) {
         return this.createImageReader29();
      } else {
         throw new UnsupportedOperationException("ImageReaderPlatformViewRenderTarget requires API version 29+");
      }
   }

   protected ImageReader createImageReader29() {
      ImageReader var1 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.bufferWidth, this.bufferHeight, 34, 4, 256L);
      var1.setOnImageAvailableListener(this.onImageAvailableListener, this.onImageAvailableHandler);
      return var1;
   }

   protected ImageReader createImageReader33() {
      AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m();
      Builder var1 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.bufferWidth, this.bufferHeight);
      AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 4);
      AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var1, 34);
      AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1, 256L);
      ImageReader var2 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);
      var2.setOnImageAvailableListener(this.onImageAvailableListener, this.onImageAvailableHandler);
      return var2;
   }

   @Override
   public int getHeight() {
      return this.bufferHeight;
   }

   @Override
   public long getId() {
      return this.textureEntry.id();
   }

   @Override
   public Surface getSurface() {
      return this.reader.getSurface();
   }

   @Override
   public int getWidth() {
      return this.bufferWidth;
   }

   @Override
   public boolean isReleased() {
      boolean var1;
      if (this.textureEntry == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void release() {
      this.closeReader();
      this.textureEntry = null;
   }

   @Override
   public void resize(int var1, int var2) {
      if (this.reader == null || this.bufferWidth != var1 || this.bufferHeight != var2) {
         this.closeReader();
         this.bufferWidth = var1;
         this.bufferHeight = var2;
         this.reader = this.createImageReader();
      }
   }
}
