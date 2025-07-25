package io.flutter.app;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Bundle;
import io.flutter.plugin.common.PluginRegistry;

public interface FlutterActivityEvents extends ComponentCallbacks2, PluginRegistry.ActivityResultListener, PluginRegistry.RequestPermissionsResultListener {
   boolean onBackPressed();

   void onCreate(Bundle var1);

   void onDestroy();

   void onNewIntent(Intent var1);

   void onPause();

   void onPostResume();

   void onResume();

   void onStart();

   void onStop();

   void onUserLeaveHint();

   void onWindowFocusChanged(boolean var1);
}
