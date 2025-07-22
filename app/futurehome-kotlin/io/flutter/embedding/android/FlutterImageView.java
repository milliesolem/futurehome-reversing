package io.flutter.embedding.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.hardware.HardwareBuffer;
import android.media.Image;
import android.media.ImageReader;
import android.media.Image.Plane;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import androidx.core.graphics.ColorKt..ExternalSyntheticApiModelOutline0;
import io.flutter.Log;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.embedding.engine.renderer.RenderSurface;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Locale;

public class FlutterImageView extends View implements RenderSurface {
   private static final String TAG = "FlutterImageView";
   private Bitmap currentBitmap;
   private Image currentImage;
   private FlutterRenderer flutterRenderer;
   private ImageReader imageReader;
   private boolean isAttachedToFlutterRenderer = false;
   private FlutterImageView.SurfaceKind kind;

   public FlutterImageView(Context var1) {
      this(var1, 1, 1, FlutterImageView.SurfaceKind.background);
   }

   public FlutterImageView(Context var1, int var2, int var3, FlutterImageView.SurfaceKind var4) {
      this(var1, createImageReader(var2, var3), var4);
   }

   FlutterImageView(Context var1, ImageReader var2, FlutterImageView.SurfaceKind var3) {
      super(var1, null);
      this.imageReader = var2;
      this.kind = var3;
      this.init();
   }

   public FlutterImageView(Context var1, AttributeSet var2) {
      this(var1, 1, 1, FlutterImageView.SurfaceKind.background);
   }

   private void closeCurrentImage() {
      Image var1 = this.currentImage;
      if (var1 != null) {
         var1.close();
         this.currentImage = null;
      }
   }

   private static ImageReader createImageReader(int var0, int var1) {
      if (var0 <= 0) {
         logW("ImageReader width must be greater than 0, but given width=%d, set width=1", var0);
         var0 = 1;
      }

      if (var1 <= 0) {
         logW("ImageReader height must be greater than 0, but given height=%d, set height=1", var1);
         var1 = 1;
      }

      return VERSION.SDK_INT >= 29
         ? AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var0, var1, 1, 3, 768L)
         : ImageReader.newInstance(var0, var1, 1, 3);
   }

   private void init() {
      this.setAlpha(0.0F);
   }

   private static void logW(String var0, Object... var1) {
      Log.w("FlutterImageView", String.format(Locale.US, var0, var1));
   }

   private void updateCurrentBitmap() {
      if (VERSION.SDK_INT >= 29) {
         HardwareBuffer var3 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.currentImage);
         this.currentBitmap = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
            var3, ExternalSyntheticApiModelOutline0.m(ExternalSyntheticApiModelOutline0.m())
         );
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var3);
      } else {
         Plane[] var5 = this.currentImage.getPlanes();
         if (var5.length != 1) {
            return;
         }

         Plane var6 = var5[0];
         int var2 = var6.getRowStride() / var6.getPixelStride();
         int var1 = this.currentImage.getHeight();
         Bitmap var4 = this.currentBitmap;
         if (var4 == null || var4.getWidth() != var2 || this.currentBitmap.getHeight() != var1) {
            this.currentBitmap = Bitmap.createBitmap(var2, var1, Config.ARGB_8888);
         }

         ByteBuffer var7 = var6.getBuffer();
         ((Buffer)var7).rewind();
         this.currentBitmap.copyPixelsFromBuffer(var7);
      }
   }

   public boolean acquireLatestImage() {
      boolean var2 = this.isAttachedToFlutterRenderer;
      boolean var1 = false;
      if (!var2) {
         return false;
      } else {
         Image var3 = this.imageReader.acquireLatestImage();
         if (var3 != null) {
            this.closeCurrentImage();
            this.currentImage = var3;
            this.invalidate();
         }

         if (var3 != null) {
            var1 = true;
         }

         return var1;
      }
   }

   @Override
   public void attachToRenderer(FlutterRenderer var1) {
      if (<unrepresentable>.$SwitchMap$io$flutter$embedding$android$FlutterImageView$SurfaceKind[this.kind.ordinal()] == 1) {
         var1.swapSurface(this.imageReader.getSurface());
      }

      this.setAlpha(1.0F);
      this.flutterRenderer = var1;
      this.isAttachedToFlutterRenderer = true;
   }

   public void closeImageReader() {
      this.imageReader.close();
   }

   @Override
   public void detachFromRenderer() {
      if (this.isAttachedToFlutterRenderer) {
         this.setAlpha(0.0F);
         this.acquireLatestImage();
         this.currentBitmap = null;
         this.closeCurrentImage();
         this.invalidate();
         this.isAttachedToFlutterRenderer = false;
      }
   }

   @Override
   public FlutterRenderer getAttachedRenderer() {
      return this.flutterRenderer;
   }

   public ImageReader getImageReader() {
      return this.imageReader;
   }

   public Surface getSurface() {
      return this.imageReader.getSurface();
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      if (this.currentImage != null) {
         this.updateCurrentBitmap();
      }

      Bitmap var2 = this.currentBitmap;
      if (var2 != null) {
         var1.drawBitmap(var2, 0.0F, 0.0F, null);
      }
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      if (var1 != this.imageReader.getWidth() || var2 != this.imageReader.getHeight()) {
         if (this.kind == FlutterImageView.SurfaceKind.background && this.isAttachedToFlutterRenderer) {
            this.resizeIfNeeded(var1, var2);
            this.flutterRenderer.swapSurface(this.imageReader.getSurface());
         }
      }
   }

   @Override
   public void pause() {
   }

   public void resizeIfNeeded(int var1, int var2) {
      if (this.flutterRenderer != null) {
         if (var1 != this.imageReader.getWidth() || var2 != this.imageReader.getHeight()) {
            this.closeCurrentImage();
            this.closeImageReader();
            this.imageReader = createImageReader(var1, var2);
         }
      }
   }

   @Override
   public void resume() {
   }

   public static enum SurfaceKind {
      background,
      overlay;
      private static final FlutterImageView.SurfaceKind[] $VALUES = $values();
   }
}
