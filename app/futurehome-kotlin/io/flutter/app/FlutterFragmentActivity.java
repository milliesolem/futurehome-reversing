package io.flutter.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

@Deprecated
public class FlutterFragmentActivity extends FragmentActivity implements FlutterView.Provider, PluginRegistry, FlutterActivityDelegate.ViewFactory {
   private final FlutterActivityDelegate delegate;
   private final FlutterActivityEvents eventDelegate;
   private final PluginRegistry pluginRegistry;
   private final FlutterView.Provider viewProvider;

   public FlutterFragmentActivity() {
      FlutterActivityDelegate var1 = new FlutterActivityDelegate(this, this);
      this.delegate = var1;
      this.eventDelegate = var1;
      this.viewProvider = var1;
      this.pluginRegistry = var1;
   }

   @Override
   public FlutterNativeView createFlutterNativeView() {
      return null;
   }

   @Override
   public FlutterView createFlutterView(Context var1) {
      return null;
   }

   @Override
   public FlutterView getFlutterView() {
      return this.viewProvider.getFlutterView();
   }

   @Override
   public final boolean hasPlugin(String var1) {
      return this.pluginRegistry.hasPlugin(var1);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if (!this.eventDelegate.onActivityResult(var1, var2, var3)) {
         super.onActivityResult(var1, var2, var3);
      }
   }

   public void onBackPressed() {
      if (!this.eventDelegate.onBackPressed()) {
         super.onBackPressed();
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.eventDelegate.onConfigurationChanged(var1);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.eventDelegate.onCreate(var1);
   }

   protected void onDestroy() {
      this.eventDelegate.onDestroy();
      super.onDestroy();
   }

   public void onLowMemory() {
      this.eventDelegate.onLowMemory();
   }

   protected void onNewIntent(Intent var1) {
      super.onNewIntent(var1);
      this.eventDelegate.onNewIntent(var1);
   }

   protected void onPause() {
      super.onPause();
      this.eventDelegate.onPause();
   }

   protected void onPostResume() {
      super.onPostResume();
      this.eventDelegate.onPostResume();
   }

   public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      super.onRequestPermissionsResult(var1, var2, var3);
      this.eventDelegate.onRequestPermissionsResult(var1, var2, var3);
   }

   protected void onStart() {
      super.onStart();
      this.eventDelegate.onStart();
   }

   protected void onStop() {
      this.eventDelegate.onStop();
      super.onStop();
   }

   public void onTrimMemory(int var1) {
      super.onTrimMemory(var1);
      this.eventDelegate.onTrimMemory(var1);
   }

   public void onUserLeaveHint() {
      this.eventDelegate.onUserLeaveHint();
   }

   public void onWindowFocusChanged(boolean var1) {
      super.onWindowFocusChanged(var1);
      this.eventDelegate.onWindowFocusChanged(var1);
   }

   @Override
   public final PluginRegistry.Registrar registrarFor(String var1) {
      return this.pluginRegistry.registrarFor(var1);
   }

   @Override
   public boolean retainFlutterNativeView() {
      return false;
   }

   @Override
   public final <T> T valuePublishedByPlugin(String var1) {
      return this.pluginRegistry.valuePublishedByPlugin(var1);
   }
}
