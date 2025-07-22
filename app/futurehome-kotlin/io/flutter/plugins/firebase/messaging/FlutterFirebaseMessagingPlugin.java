package io.flutter.plugins.firebase.messaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.RemoteMessage;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.firebase.core.FlutterFirebasePlugin;
import io.flutter.plugins.firebase.core.FlutterFirebasePluginRegistry;
import java.util.HashMap;
import java.util.Map;

public class FlutterFirebaseMessagingPlugin
   implements FlutterFirebasePlugin,
   MethodChannel.MethodCallHandler,
   PluginRegistry.NewIntentListener,
   FlutterPlugin,
   ActivityAware {
   private MethodChannel channel;
   private final HashMap<String, Boolean> consumedInitialMessages = new HashMap<>();
   private RemoteMessage initialMessage;
   private Map<String, Object> initialMessageNotification;
   private final LiveData<RemoteMessage> liveDataRemoteMessage = FlutterFirebaseRemoteMessageLiveData.getInstance();
   private final LiveData<String> liveDataToken = FlutterFirebaseTokenLiveData.getInstance();
   private Activity mainActivity;
   FlutterFirebasePermissionManager permissionManager;
   private Observer<RemoteMessage> remoteMessageObserver;
   private Observer<String> tokenObserver;

   private Boolean checkPermissions() {
      boolean var1;
      if (ExternalSyntheticApiModelOutline0.m(ContextHolder.getApplicationContext(), "android.permission.POST_NOTIFICATIONS") == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private Task<Void> deleteToken() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda12(var1));
      return var1.getTask();
   }

   private Map<String, Object> getExceptionDetails(Exception var1) {
      HashMap var2 = new HashMap();
      var2.put("code", "unknown");
      if (var1 != null) {
         var2.put("message", var1.getMessage());
      } else {
         var2.put("message", "An unknown error has occurred.");
      }

      return var2;
   }

   private Task<Map<String, Object>> getInitialMessage() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda0(this, var1));
      return var1.getTask();
   }

   private Task<Map<String, Integer>> getPermissions() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda15(this, var1));
      return var1.getTask();
   }

   private Task<Map<String, Object>> getToken() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda11(this, var1));
      return var1.getTask();
   }

   private void initInstance(BinaryMessenger var1) {
      MethodChannel var2 = new MethodChannel(var1, "plugins.flutter.io/firebase_messaging");
      this.channel = var2;
      var2.setMethodCallHandler(this);
      this.permissionManager = new FlutterFirebasePermissionManager();
      this.remoteMessageObserver = new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda13(this);
      this.tokenObserver = new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda14(this);
      this.liveDataRemoteMessage.observeForever(this.remoteMessageObserver);
      this.liveDataToken.observeForever(this.tokenObserver);
      FlutterFirebasePluginRegistry.registerPlugin("plugins.flutter.io/firebase_messaging", this);
   }

   private Task<Map<String, Integer>> requestPermissions() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda9(this, var1));
      return var1.getTask();
   }

   private Task<Void> sendMessage(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda10(var1, var2));
      return var2.getTask();
   }

   private Task<Map<String, Object>> setAutoInitEnabled(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda5(this, var1, var2));
      return var2.getTask();
   }

   private Task<Void> setDeliveryMetricsExportToBigQuery(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda2(var1, var2));
      return var2.getTask();
   }

   private Task<Void> subscribeToTopic(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda7(var1, var2));
      return var2.getTask();
   }

   private Map<String, Object> uncheckedCastToMap(Object var1) {
      return (Map<String, Object>)var1;
   }

   private Task<Void> unsubscribeFromTopic(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda1(var1, var2));
      return var2.getTask();
   }

   @Override
   public Task<Void> didReinitializeFirebaseCore() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda6(var1));
      return var1.getTask();
   }

   @Override
   public Task<Map<String, Object>> getPluginConstantsForFirebaseApp(FirebaseApp var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda8(var1, var2));
      return var2.getTask();
   }

   @Override
   public void onAttachedToActivity(ActivityPluginBinding var1) {
      var1.addOnNewIntentListener(this);
      var1.addRequestPermissionsResultListener(this.permissionManager);
      Activity var2 = var1.getActivity();
      this.mainActivity = var2;
      if (var2.getIntent() != null && this.mainActivity.getIntent().getExtras() != null && (this.mainActivity.getIntent().getFlags() & 1048576) != 1048576) {
         this.onNewIntent(this.mainActivity.getIntent());
      }
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      ContextHolder.setApplicationContext(var1.getApplicationContext());
      this.initInstance(var1.getBinaryMessenger());
   }

   @Override
   public void onDetachedFromActivity() {
      this.mainActivity = null;
   }

   @Override
   public void onDetachedFromActivityForConfigChanges() {
      this.mainActivity = null;
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.liveDataToken.removeObserver(this.tokenObserver);
      this.liveDataRemoteMessage.removeObserver(this.remoteMessageObserver);
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      String var9 = var1.method;
      var9.hashCode();
      int var4 = var9.hashCode();
      byte var3 = -1;
      switch (var4) {
         case -1704007366:
            if (var9.equals("Messaging#getInitialMessage")) {
               var3 = 0;
            }
            break;
         case -1661255137:
            if (var9.equals("Messaging#setAutoInitEnabled")) {
               var3 = 1;
            }
            break;
         case -657665041:
            if (var9.equals("Messaging#deleteToken")) {
               var3 = 2;
            }
            break;
         case 421314579:
            if (var9.equals("Messaging#unsubscribeFromTopic")) {
               var3 = 3;
            }
            break;
         case 506322569:
            if (var9.equals("Messaging#subscribeToTopic")) {
               var3 = 4;
            }
            break;
         case 607887267:
            if (var9.equals("Messaging#setDeliveryMetricsExportToBigQuery")) {
               var3 = 5;
            }
            break;
         case 928431066:
            if (var9.equals("Messaging#startBackgroundIsolate")) {
               var3 = 6;
            }
            break;
         case 1165917248:
            if (var9.equals("Messaging#sendMessage")) {
               var3 = 7;
            }
            break;
         case 1231338975:
            if (var9.equals("Messaging#requestPermission")) {
               var3 = 8;
            }
            break;
         case 1243856389:
            if (var9.equals("Messaging#getNotificationSettings")) {
               var3 = 9;
            }
            break;
         case 1459336962:
            if (var9.equals("Messaging#getToken")) {
               var3 = 10;
            }
      }

      Task var10;
      switch (var3) {
         case 0:
            var10 = this.getInitialMessage();
            break;
         case 1:
            var10 = this.setAutoInitEnabled(var1.arguments());
            break;
         case 2:
            var10 = this.deleteToken();
            break;
         case 3:
            var10 = this.unsubscribeFromTopic(var1.arguments());
            break;
         case 4:
            var10 = this.subscribeToTopic(var1.arguments());
            break;
         case 5:
            var10 = this.setDeliveryMetricsExportToBigQuery(var1.arguments());
            break;
         case 6:
            Map var14 = (Map)var1.arguments;
            Object var11 = var14.get("pluginCallbackHandle");
            Object var15 = var14.get("userCallbackHandle");
            long var5;
            if (var11 instanceof Long) {
               var5 = (Long)var11;
            } else {
               if (!(var11 instanceof Integer)) {
                  throw new IllegalArgumentException("Expected 'Long' or 'Integer' type for 'pluginCallbackHandle'.");
               }

               var5 = ((Integer)var11).intValue();
               Long.valueOf(var5).getClass();
            }

            long var7;
            if (var15 instanceof Long) {
               var7 = (Long)var15;
            } else {
               if (!(var15 instanceof Integer)) {
                  throw new IllegalArgumentException("Expected 'Long' or 'Integer' type for 'userCallbackHandle'.");
               }

               var7 = ((Integer)var15).intValue();
               Long.valueOf(var7).getClass();
            }

            var11 = this.mainActivity;
            if (var11 != null) {
               var11 = FlutterShellArgs.fromIntent(var11.getIntent());
            } else {
               var11 = null;
            }

            FlutterFirebaseMessagingBackgroundService.setCallbackDispatcher(var5);
            FlutterFirebaseMessagingBackgroundService.setUserCallbackHandle(var7);
            FlutterFirebaseMessagingBackgroundService.startBackgroundIsolate(var5, (FlutterShellArgs)var11);
            var10 = Tasks.forResult(null);
            break;
         case 7:
            var10 = this.sendMessage(var1.arguments());
            break;
         case 8:
            if (VERSION.SDK_INT >= 33) {
               var10 = this.requestPermissions();
            } else {
               var10 = this.getPermissions();
            }
            break;
         case 9:
            var10 = this.getPermissions();
            break;
         case 10:
            var10 = this.getToken();
            break;
         default:
            var2.notImplemented();
            return;
      }

      var10.addOnCompleteListener(new FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda16(this, var2));
   }

   @Override
   public boolean onNewIntent(Intent var1) {
      if (var1.getExtras() == null) {
         return false;
      } else {
         String var3 = var1.getExtras().getString("google.message_id");
         String var2 = var3;
         if (var3 == null) {
            var2 = var1.getExtras().getString("message_id");
         }

         if (var2 == null) {
            return false;
         } else {
            Map var8;
            label30: {
               var6 = FlutterFirebaseMessagingReceiver.notifications.get(var2);
               if (var6 == null) {
                  var8 = FlutterFirebaseMessagingStore.getInstance().getFirebaseMessageMap(var2);
                  if (var8 != null) {
                     var6 = FlutterFirebaseMessagingUtils.getRemoteMessageForArguments(var8);
                     var8 = FlutterFirebaseMessagingUtils.getRemoteMessageNotificationForArguments(var8);
                     break label30;
                  }
               }

               var8 = null;
            }

            if (var6 == null) {
               return false;
            } else {
               this.initialMessage = var6;
               this.initialMessageNotification = var8;
               FlutterFirebaseMessagingReceiver.notifications.remove(var2);
               Map var5 = FlutterFirebaseMessagingUtils.remoteMessageToMap(var6);
               if (var6.getNotification() == null) {
                  Map var7 = this.initialMessageNotification;
                  if (var7 != null) {
                     var5.put("notification", var7);
                  }
               }

               this.channel.invokeMethod("Messaging#onMessageOpenedApp", var5);
               this.mainActivity.setIntent(var1);
               return true;
            }
         }
      }
   }

   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
      var1.addOnNewIntentListener(this);
      this.mainActivity = var1.getActivity();
   }
}
