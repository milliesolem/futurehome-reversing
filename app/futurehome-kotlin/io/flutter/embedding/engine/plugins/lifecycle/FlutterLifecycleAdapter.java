package io.flutter.embedding.engine.plugins.lifecycle;

import androidx.lifecycle.Lifecycle;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

public class FlutterLifecycleAdapter {
   public static Lifecycle getActivityLifecycle(ActivityPluginBinding var0) {
      return ((HiddenLifecycleReference)var0.getLifecycle()).getLifecycle();
   }
}
