package io.flutter.embedding.engine.plugins.service;

public interface ServiceAware {
   void onAttachedToService(ServicePluginBinding var1);

   void onDetachedFromService();

   public interface OnModeChangeListener {
      void onMoveToBackground();

      void onMoveToForeground();
   }
}
