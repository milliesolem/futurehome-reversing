package io.flutter.embedding.engine;

import java.util.HashMap;
import java.util.Map;

public class FlutterEngineCache {
   private static FlutterEngineCache instance;
   private final Map<String, FlutterEngine> cachedEngines = new HashMap<>();

   FlutterEngineCache() {
   }

   public static FlutterEngineCache getInstance() {
      if (instance == null) {
         instance = new FlutterEngineCache();
      }

      return instance;
   }

   public void clear() {
      this.cachedEngines.clear();
   }

   public boolean contains(String var1) {
      return this.cachedEngines.containsKey(var1);
   }

   public FlutterEngine get(String var1) {
      return this.cachedEngines.get(var1);
   }

   public void put(String var1, FlutterEngine var2) {
      if (var2 != null) {
         this.cachedEngines.put(var1, var2);
      } else {
         this.cachedEngines.remove(var1);
      }
   }

   public void remove(String var1) {
      this.put(var1, null);
   }
}
