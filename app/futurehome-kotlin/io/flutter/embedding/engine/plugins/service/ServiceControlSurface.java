package io.flutter.embedding.engine.plugins.service;

import android.app.Service;
import androidx.lifecycle.Lifecycle;

public interface ServiceControlSurface {
   void attachToService(Service var1, Lifecycle var2, boolean var3);

   void detachFromService();

   void onMoveToBackground();

   void onMoveToForeground();
}
