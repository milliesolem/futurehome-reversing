package io.flutter.plugin.platform;

import android.view.Surface;
import io.flutter.view.TextureRegistry;

public class SurfaceProducerPlatformViewRenderTarget implements PlatformViewRenderTarget {
   private static final String TAG = "SurfaceProducerRenderTarget";
   private TextureRegistry.SurfaceProducer producer;

   public SurfaceProducerPlatformViewRenderTarget(TextureRegistry.SurfaceProducer var1) {
      this.producer = var1;
   }

   @Override
   public int getHeight() {
      return this.producer.getHeight();
   }

   @Override
   public long getId() {
      return this.producer.id();
   }

   @Override
   public Surface getSurface() {
      return this.producer.getSurface();
   }

   @Override
   public int getWidth() {
      return this.producer.getWidth();
   }

   @Override
   public boolean isReleased() {
      boolean var1;
      if (this.producer == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void release() {
      this.producer.release();
      this.producer = null;
   }

   @Override
   public void resize(int var1, int var2) {
      this.producer.setSize(var1, var2);
   }

   @Override
   public void scheduleFrame() {
      this.producer.scheduleFrame();
   }
}
