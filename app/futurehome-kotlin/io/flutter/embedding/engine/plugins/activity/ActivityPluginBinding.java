package io.flutter.embedding.engine.plugins.activity;

import android.app.Activity;
import android.os.Bundle;
import io.flutter.plugin.common.PluginRegistry;

public interface ActivityPluginBinding {
   void addActivityResultListener(PluginRegistry.ActivityResultListener var1);

   void addOnNewIntentListener(PluginRegistry.NewIntentListener var1);

   void addOnSaveStateListener(ActivityPluginBinding.OnSaveInstanceStateListener var1);

   void addOnUserLeaveHintListener(PluginRegistry.UserLeaveHintListener var1);

   void addOnWindowFocusChangedListener(PluginRegistry.WindowFocusChangedListener var1);

   void addRequestPermissionsResultListener(PluginRegistry.RequestPermissionsResultListener var1);

   Activity getActivity();

   Object getLifecycle();

   void removeActivityResultListener(PluginRegistry.ActivityResultListener var1);

   void removeOnNewIntentListener(PluginRegistry.NewIntentListener var1);

   void removeOnSaveStateListener(ActivityPluginBinding.OnSaveInstanceStateListener var1);

   void removeOnUserLeaveHintListener(PluginRegistry.UserLeaveHintListener var1);

   void removeOnWindowFocusChangedListener(PluginRegistry.WindowFocusChangedListener var1);

   void removeRequestPermissionsResultListener(PluginRegistry.RequestPermissionsResultListener var1);

   public interface OnSaveInstanceStateListener {
      void onRestoreInstanceState(Bundle var1);

      void onSaveInstanceState(Bundle var1);
   }
}
