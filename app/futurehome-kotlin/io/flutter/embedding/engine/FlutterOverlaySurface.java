package io.flutter.embedding.engine;

import android.view.Surface;

public class FlutterOverlaySurface {
   private final int id;
   private final Surface surface;

   public FlutterOverlaySurface(int var1, Surface var2) {
      this.id = var1;
      this.surface = var2;
   }

   public int getId() {
      return this.id;
   }

   public Surface getSurface() {
      return this.surface;
   }
}
