package io.flutter.plugin.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import io.flutter.plugin.platform.PlatformViewRegistry;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;
import io.flutter.view.TextureRegistry;

public interface PluginRegistry {
   @Deprecated
   boolean hasPlugin(String var1);

   @Deprecated
   PluginRegistry.Registrar registrarFor(String var1);

   @Deprecated
   <T> T valuePublishedByPlugin(String var1);

   public interface ActivityResultListener {
      boolean onActivityResult(int var1, int var2, Intent var3);
   }

   public interface NewIntentListener {
      boolean onNewIntent(Intent var1);
   }

   @Deprecated
   public interface PluginRegistrantCallback {
      void registerWith(PluginRegistry var1);
   }

   @Deprecated
   public interface Registrar {
      Context activeContext();

      Activity activity();

      PluginRegistry.Registrar addActivityResultListener(PluginRegistry.ActivityResultListener var1);

      PluginRegistry.Registrar addNewIntentListener(PluginRegistry.NewIntentListener var1);

      PluginRegistry.Registrar addRequestPermissionsResultListener(PluginRegistry.RequestPermissionsResultListener var1);

      PluginRegistry.Registrar addUserLeaveHintListener(PluginRegistry.UserLeaveHintListener var1);

      PluginRegistry.Registrar addViewDestroyListener(PluginRegistry.ViewDestroyListener var1);

      PluginRegistry.Registrar addWindowFocusChangedListener(PluginRegistry.WindowFocusChangedListener var1);

      Context context();

      String lookupKeyForAsset(String var1);

      String lookupKeyForAsset(String var1, String var2);

      BinaryMessenger messenger();

      PlatformViewRegistry platformViewRegistry();

      PluginRegistry.Registrar publish(Object var1);

      TextureRegistry textures();

      FlutterView view();
   }

   public interface RequestPermissionsResultListener {
      boolean onRequestPermissionsResult(int var1, String[] var2, int[] var3);
   }

   public interface UserLeaveHintListener {
      void onUserLeaveHint();
   }

   @Deprecated
   public interface ViewDestroyListener {
      boolean onViewDestroy(FlutterNativeView var1);
   }

   public interface WindowFocusChangedListener {
      void onWindowFocusChanged(boolean var1);
   }
}
