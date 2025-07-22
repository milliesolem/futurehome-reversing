package io.flutter.plugins.webviewflutter;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

public class WebViewFlutterPlugin implements FlutterPlugin, ActivityAware {
   private FlutterPlugin.FlutterPluginBinding pluginBinding;
   private ProxyApiRegistrar proxyApiRegistrar;

   public AndroidWebkitLibraryPigeonInstanceManager getInstanceManager() {
      return this.proxyApiRegistrar.getInstanceManager();
   }

   @Override
   public void onAttachedToActivity(ActivityPluginBinding var1) {
      ProxyApiRegistrar var2 = this.proxyApiRegistrar;
      if (var2 != null) {
         var2.setContext(var1.getActivity());
      }
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.pluginBinding = var1;
      this.proxyApiRegistrar = new ProxyApiRegistrar(
         var1.getBinaryMessenger(),
         var1.getApplicationContext(),
         new FlutterAssetManager.PluginBindingFlutterAssetManager(var1.getApplicationContext().getAssets(), var1.getFlutterAssets())
      );
      var1.getPlatformViewRegistry().registerViewFactory("plugins.flutter.io/webview", new FlutterViewFactory(this.proxyApiRegistrar.getInstanceManager()));
      this.proxyApiRegistrar.setUp();
   }

   @Override
   public void onDetachedFromActivity() {
      this.proxyApiRegistrar.setContext(this.pluginBinding.getApplicationContext());
   }

   @Override
   public void onDetachedFromActivityForConfigChanges() {
      this.proxyApiRegistrar.setContext(this.pluginBinding.getApplicationContext());
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      ProxyApiRegistrar var2 = this.proxyApiRegistrar;
      if (var2 != null) {
         var2.tearDown();
         this.proxyApiRegistrar.getInstanceManager().stopFinalizationListener();
         this.proxyApiRegistrar = null;
      }
   }

   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
      this.proxyApiRegistrar.setContext(var1.getActivity());
   }
}
