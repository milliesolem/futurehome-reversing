package io.flutter.plugins.firebase.firebaseremoteconfig;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.ConfigUpdateListenerRegistration;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder;
import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.firebase.core.FlutterFirebasePlugin;
import io.flutter.plugins.firebase.core.FlutterFirebasePluginRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class FirebaseRemoteConfigPlugin implements FlutterFirebasePlugin, MethodChannel.MethodCallHandler, FlutterPlugin, EventChannel.StreamHandler {
   static final String EVENT_CHANNEL = "plugins.flutter.io/firebase_remote_config_updated";
   static final String METHOD_CHANNEL = "plugins.flutter.io/firebase_remote_config";
   static final String TAG = "FRCPlugin";
   private MethodChannel channel;
   private EventChannel eventChannel;
   private final Map<String, ConfigUpdateListenerRegistration> listenersMap = new HashMap<>();
   private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

   private Map<String, Object> createRemoteConfigValueMap(FirebaseRemoteConfigValue var1) {
      HashMap var2 = new HashMap();
      var2.put("value", var1.asByteArray());
      var2.put("source", this.mapValueSource(var1.getSource()));
      return var2;
   }

   private Map<String, Object> getConfigProperties(FirebaseRemoteConfig var1) {
      HashMap var2 = new HashMap();
      var2.put("fetchTimeout", var1.getInfo().getConfigSettings().getFetchTimeoutInSeconds());
      var2.put("minimumFetchInterval", var1.getInfo().getConfigSettings().getMinimumFetchIntervalInSeconds());
      var2.put("lastFetchTime", var1.getInfo().getFetchTimeMillis());
      var2.put("lastFetchStatus", this.mapLastFetchStatus(var1.getInfo().getLastFetchStatus()));
      StringBuilder var3 = new StringBuilder("Sending fetchTimeout: ");
      var3.append(var2.get("fetchTimeout"));
      Log.d("FRCPlugin", var3.toString());
      return var2;
   }

   private FirebaseRemoteConfig getRemoteConfig(Map<String, Object> var1) {
      return FirebaseRemoteConfig.getInstance(FirebaseApp.getInstance(Objects.requireNonNull((String)var1.get("appName"))));
   }

   private String mapLastFetchStatus(int var1) {
      if (var1 != -1) {
         if (var1 != 0) {
            return var1 != 2 ? "failure" : "throttled";
         } else {
            return "noFetchYet";
         }
      } else {
         return "success";
      }
   }

   private String mapValueSource(int var1) {
      if (var1 != 1) {
         return var1 != 2 ? "static" : "remote";
      } else {
         return "default";
      }
   }

   private Map<String, Object> parseParameters(Map<String, FirebaseRemoteConfigValue> var1) {
      HashMap var2 = new HashMap();

      for (String var3 : var1.keySet()) {
         var2.put(var3, this.createRemoteConfigValueMap(Objects.requireNonNull((FirebaseRemoteConfigValue)var1.get(var3))));
      }

      return var2;
   }

   private void removeEventListeners() {
      Iterator var1 = this.listenersMap.values().iterator();

      while (var1.hasNext()) {
         ((ConfigUpdateListenerRegistration)var1.next()).remove();
      }

      this.listenersMap.clear();
   }

   private Task<Void> setCustomSignals(FirebaseRemoteConfig var1, Map<String, Object> var2) {
      TaskCompletionSource var3 = new TaskCompletionSource();
      cachedThreadPool.execute(new FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda1(var2, var1, var3));
      return var3.getTask();
   }

   private void setupChannel(BinaryMessenger var1) {
      FlutterFirebasePluginRegistry.registerPlugin("plugins.flutter.io/firebase_remote_config", this);
      MethodChannel var2 = new MethodChannel(var1, "plugins.flutter.io/firebase_remote_config");
      this.channel = var2;
      var2.setMethodCallHandler(this);
      EventChannel var3 = new EventChannel(var1, "plugins.flutter.io/firebase_remote_config_updated");
      this.eventChannel = var3;
      var3.setStreamHandler(this);
   }

   private void tearDownChannel() {
      this.channel.setMethodCallHandler(null);
      this.channel = null;
      this.eventChannel.setStreamHandler(null);
      this.eventChannel = null;
      this.removeEventListeners();
   }

   @Override
   public Task<Void> didReinitializeFirebaseCore() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda0(this, var1));
      return var1.getTask();
   }

   @Override
   public Task<Map<String, Object>> getPluginConstantsForFirebaseApp(FirebaseApp var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda2(this, var1, var2));
      return var2.getTask();
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.setupChannel(var1.getBinaryMessenger());
   }

   @Override
   public void onCancel(Object var1) {
      var1 = var1;
      if (var1 != null) {
         String var4 = Objects.requireNonNull((String)var1.get("appName"));
         ConfigUpdateListenerRegistration var2 = this.listenersMap.get(var4);
         if (var2 != null) {
            var2.remove();
            this.listenersMap.remove(var4);
         }
      }
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.tearDownChannel();
   }

   @Override
   public void onListen(Object var1, EventChannel.EventSink var2) {
      Map var3 = (Map)var1;
      var1 = this.getRemoteConfig(var3);
      String var5 = Objects.requireNonNull((String)var3.get("appName"));
      this.listenersMap.put(var5, var1.addOnConfigUpdateListener(new ConfigUpdateListener(this, var2) {
         final FirebaseRemoteConfigPlugin this$0;
         final EventChannel.EventSink val$events;

         {
            this.this$0 = var1;
            this.val$events = var2x;
         }

         public void onError(FirebaseRemoteConfigException var1) {
            this.val$events.error("firebase_remote_config", var1.getMessage(), null);
         }

         public void onUpdate(ConfigUpdate var1) {
            ArrayList var2x = new ArrayList(var1.getUpdatedKeys());
            this.this$0.mainThreadHandler.post(new FirebaseRemoteConfigPlugin$1$$ExternalSyntheticLambda0(this.val$events, var2x));
         }
      }));
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      FirebaseRemoteConfig var5 = this.getRemoteConfig(var1.arguments());
      String var6 = var1.method;
      var6.hashCode();
      int var4 = var6.hashCode();
      int var3 = -1;
      switch (var4) {
         case -1164253005:
            if (var6.equals("RemoteConfig#setCustomSignals")) {
               var3 = 0;
            }
            break;
         case -1145383109:
            if (var6.equals("RemoteConfig#ensureInitialized")) {
               var3 = 1;
            }
            break;
         case -967766516:
            if (var6.equals("RemoteConfig#setConfigSettings")) {
               var3 = 2;
            }
            break;
         case -824350930:
            if (var6.equals("RemoteConfig#getProperties")) {
               var3 = 3;
            }
            break;
         case 2948543:
            if (var6.equals("RemoteConfig#fetch")) {
               var3 = 4;
            }
            break;
         case 47629262:
            if (var6.equals("RemoteConfig#activate")) {
               var3 = 5;
            }
            break;
         case 120001542:
            if (var6.equals("RemoteConfig#getAll")) {
               var3 = 6;
            }
            break;
         case 198105259:
            if (var6.equals("RemoteConfig#fetchAndActivate")) {
               var3 = 7;
            }
            break;
         case 1069772825:
            if (var6.equals("RemoteConfig#setDefaults")) {
               var3 = 8;
            }
      }

      Task var7;
      switch (var3) {
         case 0:
            var7 = this.setCustomSignals(var5, Objects.requireNonNull(var1.argument("customSignals")));
            break;
         case 1:
            var7 = Tasks.whenAll(new Task[]{var5.ensureInitialized()});
            break;
         case 2:
            var4 = Objects.requireNonNull(var1.argument("fetchTimeout"));
            var3 = Objects.requireNonNull(var1.argument("minimumFetchInterval"));
            var7 = var5.setConfigSettingsAsync(new Builder().setFetchTimeoutInSeconds(var4).setMinimumFetchIntervalInSeconds(var3).build());
            break;
         case 3:
            var7 = Tasks.forResult(this.getConfigProperties(var5));
            break;
         case 4:
            var7 = var5.fetch();
            break;
         case 5:
            var7 = var5.activate();
            break;
         case 6:
            var7 = Tasks.forResult(this.parseParameters(var5.getAll()));
            break;
         case 7:
            var7 = var5.fetchAndActivate();
            break;
         case 8:
            var7 = var5.setDefaultsAsync(Objects.requireNonNull(var1.argument("defaults")));
            break;
         default:
            var2.notImplemented();
            return;
      }

      var7.addOnCompleteListener(new FirebaseRemoteConfigPlugin$$ExternalSyntheticLambda3(var2));
   }
}
