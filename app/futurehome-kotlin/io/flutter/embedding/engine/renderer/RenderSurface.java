package io.flutter.embedding.engine.renderer;

public interface RenderSurface {
   void attachToRenderer(FlutterRenderer var1);

   void detachFromRenderer();

   FlutterRenderer getAttachedRenderer();

   void pause();

   void resume();
}
