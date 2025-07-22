package io.flutter.embedding.android;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import io.flutter.Log;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.embedding.engine.renderer.RenderSurface;

public class FlutterTextureView extends TextureView implements RenderSurface {
   private static final String TAG = "FlutterTextureView";
   private FlutterRenderer flutterRenderer;
   private boolean isPaused;
   private boolean isSurfaceAvailableForRendering = false;
   private Surface renderSurface;
   private final SurfaceTextureListener surfaceTextureListener;

   public FlutterTextureView(Context var1) {
      this(var1, null);
   }

   public FlutterTextureView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.isPaused = false;
      this.surfaceTextureListener = new SurfaceTextureListener(this) {
         final FlutterTextureView this$0;

         {
            this.this$0 = var1;
         }

         public void onSurfaceTextureAvailable(SurfaceTexture var1, int var2x, int var3) {
            Log.v("FlutterTextureView", "SurfaceTextureListener.onSurfaceTextureAvailable()");
            this.this$0.isSurfaceAvailableForRendering = true;
            if (this.this$0.shouldNotify()) {
               this.this$0.connectSurfaceToRenderer();
            }
         }

         public boolean onSurfaceTextureDestroyed(SurfaceTexture var1) {
            Log.v("FlutterTextureView", "SurfaceTextureListener.onSurfaceTextureDestroyed()");
            this.this$0.isSurfaceAvailableForRendering = false;
            if (this.this$0.shouldNotify()) {
               this.this$0.disconnectSurfaceFromRenderer();
            }

            if (this.this$0.renderSurface != null) {
               this.this$0.renderSurface.release();
               this.this$0.renderSurface = null;
            }

            return true;
         }

         public void onSurfaceTextureSizeChanged(SurfaceTexture var1, int var2x, int var3) {
            Log.v("FlutterTextureView", "SurfaceTextureListener.onSurfaceTextureSizeChanged()");
            if (this.this$0.shouldNotify()) {
               this.this$0.changeSurfaceSize(var2x, var3);
            }
         }

         public void onSurfaceTextureUpdated(SurfaceTexture var1) {
         }
      };
      this.init();
   }

   private void changeSurfaceSize(int var1, int var2) {
      if (this.flutterRenderer != null) {
         StringBuilder var3 = new StringBuilder("Notifying FlutterRenderer that Android surface size has changed to ");
         var3.append(var1);
         var3.append(" x ");
         var3.append(var2);
         Log.v("FlutterTextureView", var3.toString());
         this.flutterRenderer.surfaceChanged(var1, var2);
      } else {
         throw new IllegalStateException("changeSurfaceSize() should only be called when flutterRenderer is non-null.");
      }
   }

   private void connectSurfaceToRenderer() {
      if (this.flutterRenderer != null && this.getSurfaceTexture() != null) {
         Surface var1 = this.renderSurface;
         if (var1 != null) {
            var1.release();
            this.renderSurface = null;
         }

         var1 = new Surface(this.getSurfaceTexture());
         this.renderSurface = var1;
         this.flutterRenderer.startRenderingToSurface(var1, this.isPaused);
      } else {
         throw new IllegalStateException("connectSurfaceToRenderer() should only be called when flutterRenderer and getSurfaceTexture() are non-null.");
      }
   }

   private void disconnectSurfaceFromRenderer() {
      FlutterRenderer var1 = this.flutterRenderer;
      if (var1 != null) {
         var1.stopRenderingToSurface();
         Surface var2 = this.renderSurface;
         if (var2 != null) {
            var2.release();
            this.renderSurface = null;
         }
      } else {
         throw new IllegalStateException("disconnectSurfaceFromRenderer() should only be called when flutterRenderer is non-null.");
      }
   }

   private void init() {
      this.setSurfaceTextureListener(this.surfaceTextureListener);
   }

   private boolean shouldNotify() {
      boolean var1;
      if (this.flutterRenderer != null && !this.isPaused) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void attachToRenderer(FlutterRenderer var1) {
      Log.v("FlutterTextureView", "Attaching to FlutterRenderer.");
      if (this.flutterRenderer != null) {
         Log.v("FlutterTextureView", "Already connected to a FlutterRenderer. Detaching from old one and attaching to new one.");
         this.flutterRenderer.stopRenderingToSurface();
      }

      this.flutterRenderer = var1;
      this.resume();
   }

   @Override
   public void detachFromRenderer() {
      if (this.flutterRenderer != null) {
         if (this.getWindowToken() != null) {
            Log.v("FlutterTextureView", "Disconnecting FlutterRenderer from Android surface.");
            this.disconnectSurfaceFromRenderer();
         }

         this.flutterRenderer = null;
      } else {
         Log.w("FlutterTextureView", "detachFromRenderer() invoked when no FlutterRenderer was attached.");
      }
   }

   @Override
   public FlutterRenderer getAttachedRenderer() {
      return this.flutterRenderer;
   }

   boolean isSurfaceAvailableForRendering() {
      return this.isSurfaceAvailableForRendering;
   }

   @Override
   public void pause() {
      if (this.flutterRenderer == null) {
         Log.w("FlutterTextureView", "pause() invoked when no FlutterRenderer was attached.");
      } else {
         this.isPaused = true;
      }
   }

   @Override
   public void resume() {
      if (this.flutterRenderer == null) {
         Log.w("FlutterTextureView", "resume() invoked when no FlutterRenderer was attached.");
      } else {
         if (this.isSurfaceAvailableForRendering()) {
            Log.v("FlutterTextureView", "Surface is available for rendering. Connecting FlutterRenderer to Android surface.");
            this.connectSurfaceToRenderer();
         }

         this.isPaused = false;
      }
   }

   public void setRenderSurface(Surface var1) {
      this.renderSurface = var1;
   }
}
