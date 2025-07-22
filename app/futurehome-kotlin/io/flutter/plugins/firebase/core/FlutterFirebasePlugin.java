package io.flutter.plugins.firebase.core;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface FlutterFirebasePlugin {
   ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

   Task<Void> didReinitializeFirebaseCore();

   Task<Map<String, Object>> getPluginConstantsForFirebaseApp(FirebaseApp var1);
}
