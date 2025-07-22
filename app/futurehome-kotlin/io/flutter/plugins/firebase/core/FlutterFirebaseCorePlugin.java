package io.flutter.plugins.firebase.core;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlutterFirebaseCorePlugin
   implements FlutterPlugin,
   GeneratedAndroidFirebaseCore.FirebaseCoreHostApi,
   GeneratedAndroidFirebaseCore.FirebaseAppHostApi {
   public static Map<String, String> customAuthDomain = new HashMap<>();
   private Context applicationContext;
   private boolean coreInitialized = false;

   private Task<GeneratedAndroidFirebaseCore.PigeonInitializeResponse> firebaseAppToMap(FirebaseApp var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda6(this, var1, var2));
      return var2.getTask();
   }

   private GeneratedAndroidFirebaseCore.PigeonFirebaseOptions firebaseOptionsToMap(FirebaseOptions var1) {
      GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder var2 = new GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder();
      var2.setApiKey(var1.getApiKey());
      var2.setAppId(var1.getApplicationId());
      if (var1.getGcmSenderId() != null) {
         var2.setMessagingSenderId(var1.getGcmSenderId());
      }

      if (var1.getProjectId() != null) {
         var2.setProjectId(var1.getProjectId());
      }

      var2.setDatabaseURL(var1.getDatabaseUrl());
      var2.setStorageBucket(var1.getStorageBucket());
      var2.setTrackingId(var1.getGaTrackingId());
      return var2.build();
   }

   private <T> void listenToResponse(TaskCompletionSource<T> var1, GeneratedAndroidFirebaseCore.Result<T> var2) {
      var1.getTask().addOnCompleteListener(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda3(var2));
   }

   @Override
   public void delete(String var1, GeneratedAndroidFirebaseCore.Result<Void> var2) {
      TaskCompletionSource var3 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda0(var1, var3));
      this.listenToResponse(var3, var2);
   }

   @Override
   public void initializeApp(
      String var1,
      GeneratedAndroidFirebaseCore.PigeonFirebaseOptions var2,
      GeneratedAndroidFirebaseCore.Result<GeneratedAndroidFirebaseCore.PigeonInitializeResponse> var3
   ) {
      TaskCompletionSource var4 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda7(this, var2, var1, var4));
      this.listenToResponse(var4, var3);
   }

   @Override
   public void initializeCore(GeneratedAndroidFirebaseCore.Result<List<GeneratedAndroidFirebaseCore.PigeonInitializeResponse>> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda4(this, var2));
      this.listenToResponse(var2, var1);
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$_CC.setup(var1.getBinaryMessenger(), this);
      GeneratedAndroidFirebaseCore$FirebaseAppHostApi$_CC.setup(var1.getBinaryMessenger(), this);
      this.applicationContext = var1.getApplicationContext();
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.applicationContext = null;
      GeneratedAndroidFirebaseCore$FirebaseCoreHostApi$_CC.setup(var1.getBinaryMessenger(), null);
      GeneratedAndroidFirebaseCore$FirebaseAppHostApi$_CC.setup(var1.getBinaryMessenger(), null);
   }

   @Override
   public void optionsFromResource(GeneratedAndroidFirebaseCore.Result<GeneratedAndroidFirebaseCore.PigeonFirebaseOptions> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda1(this, var2));
      this.listenToResponse(var2, var1);
   }

   @Override
   public void setAutomaticDataCollectionEnabled(String var1, Boolean var2, GeneratedAndroidFirebaseCore.Result<Void> var3) {
      TaskCompletionSource var4 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda2(var1, var2, var4));
      this.listenToResponse(var4, var3);
   }

   @Override
   public void setAutomaticResourceManagementEnabled(String var1, Boolean var2, GeneratedAndroidFirebaseCore.Result<Void> var3) {
      TaskCompletionSource var4 = new TaskCompletionSource();
      FlutterFirebasePlugin.cachedThreadPool.execute(new FlutterFirebaseCorePlugin$$ExternalSyntheticLambda5(var1, var2, var4));
      this.listenToResponse(var4, var3);
   }
}
