package io.flutter.embedding.engine.plugins;

import java.util.Set;

public interface PluginRegistry {
   void add(FlutterPlugin var1);

   void add(Set<FlutterPlugin> var1);

   FlutterPlugin get(Class<? extends FlutterPlugin> var1);

   boolean has(Class<? extends FlutterPlugin> var1);

   void remove(Class<? extends FlutterPlugin> var1);

   void remove(Set<Class<? extends FlutterPlugin>> var1);

   void removeAll();
}
