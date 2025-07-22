package io.flutter.embedding.engine.plugins.shim;

import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.PluginRegistry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ShimPluginRegistry implements PluginRegistry {
   private static final String TAG = "ShimPluginRegistry";
   private final FlutterEngine flutterEngine;
   private final Map<String, Object> pluginMap = new HashMap<>();
   private final ShimPluginRegistry.ShimRegistrarAggregate shimRegistrarAggregate;

   public ShimPluginRegistry(FlutterEngine var1) {
      this.flutterEngine = var1;
      ShimPluginRegistry.ShimRegistrarAggregate var2 = new ShimPluginRegistry.ShimRegistrarAggregate();
      this.shimRegistrarAggregate = var2;
      var1.getPlugins().add(var2);
   }

   @Override
   public boolean hasPlugin(String var1) {
      return this.pluginMap.containsKey(var1);
   }

   @Override
   public PluginRegistry.Registrar registrarFor(String var1) {
      StringBuilder var2 = new StringBuilder("Creating plugin Registrar for '");
      var2.append(var1);
      var2.append("'");
      Log.v("ShimPluginRegistry", var2.toString());
      if (!this.pluginMap.containsKey(var1)) {
         this.pluginMap.put(var1, null);
         ShimRegistrar var3 = new ShimRegistrar(var1, this.pluginMap);
         this.shimRegistrarAggregate.addPlugin(var3);
         return var3;
      } else {
         var2 = new StringBuilder("Plugin key ");
         var2.append(var1);
         var2.append(" is already in use");
         throw new IllegalStateException(var2.toString());
      }
   }

   @Override
   public <T> T valuePublishedByPlugin(String var1) {
      return (T)this.pluginMap.get(var1);
   }

   private static class ShimRegistrarAggregate implements FlutterPlugin, ActivityAware {
      private ActivityPluginBinding activityPluginBinding;
      private FlutterPlugin.FlutterPluginBinding flutterPluginBinding;
      private final Set<ShimRegistrar> shimRegistrars = new HashSet<>();

      private ShimRegistrarAggregate() {
      }

      public void addPlugin(ShimRegistrar var1) {
         this.shimRegistrars.add(var1);
         FlutterPlugin.FlutterPluginBinding var2 = this.flutterPluginBinding;
         if (var2 != null) {
            var1.onAttachedToEngine(var2);
         }

         ActivityPluginBinding var3 = this.activityPluginBinding;
         if (var3 != null) {
            var1.onAttachedToActivity(var3);
         }
      }

      @Override
      public void onAttachedToActivity(ActivityPluginBinding var1) {
         this.activityPluginBinding = var1;
         Iterator var2 = this.shimRegistrars.iterator();

         while (var2.hasNext()) {
            ((ShimRegistrar)var2.next()).onAttachedToActivity(var1);
         }
      }

      @Override
      public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
         this.flutterPluginBinding = var1;
         Iterator var2 = this.shimRegistrars.iterator();

         while (var2.hasNext()) {
            ((ShimRegistrar)var2.next()).onAttachedToEngine(var1);
         }
      }

      @Override
      public void onDetachedFromActivity() {
         Iterator var1 = this.shimRegistrars.iterator();

         while (var1.hasNext()) {
            ((ShimRegistrar)var1.next()).onDetachedFromActivity();
         }

         this.activityPluginBinding = null;
      }

      @Override
      public void onDetachedFromActivityForConfigChanges() {
         Iterator var1 = this.shimRegistrars.iterator();

         while (var1.hasNext()) {
            ((ShimRegistrar)var1.next()).onDetachedFromActivity();
         }

         this.activityPluginBinding = null;
      }

      @Override
      public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
         Iterator var2 = this.shimRegistrars.iterator();

         while (var2.hasNext()) {
            ((ShimRegistrar)var2.next()).onDetachedFromEngine(var1);
         }

         this.flutterPluginBinding = null;
         this.activityPluginBinding = null;
      }

      @Override
      public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
         this.activityPluginBinding = var1;
         Iterator var2 = this.shimRegistrars.iterator();

         while (var2.hasNext()) {
            ((ShimRegistrar)var2.next()).onReattachedToActivityForConfigChanges(var1);
         }
      }
   }
}
