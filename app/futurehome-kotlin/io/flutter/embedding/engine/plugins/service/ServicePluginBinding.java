package io.flutter.embedding.engine.plugins.service;

import android.app.Service;

public interface ServicePluginBinding {
   void addOnModeChangeListener(ServiceAware.OnModeChangeListener var1);

   Object getLifecycle();

   Service getService();

   void removeOnModeChangeListener(ServiceAware.OnModeChangeListener var1);
}
