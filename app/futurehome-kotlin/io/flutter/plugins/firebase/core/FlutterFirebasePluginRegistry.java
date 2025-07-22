package io.flutter.plugins.firebase.core;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import java.util.Map;
import java.util.WeakHashMap;

public class FlutterFirebasePluginRegistry {
   private static final Map<String, FlutterFirebasePlugin> registeredPlugins = new WeakHashMap<>();

   static Task<Void> didReinitializeFirebaseCore() {
      TaskCompletionSource var0 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebasePluginRegistry$$ExternalSyntheticLambda0(var0));
      return var0.getTask();
   }

   static Task<Map<String, Object>> getPluginConstantsForFirebaseApp(FirebaseApp var0) {
      TaskCompletionSource var1 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebasePluginRegistry$$ExternalSyntheticLambda1(var0, var1));
      return var1.getTask();
   }

   public static void registerPlugin(String var0, FlutterFirebasePlugin var1) {
      registeredPlugins.put(var0, var1);
   }
}
