package io.flutter.plugins.firebase.analytics;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.firebase.core.FlutterFirebasePlugin;
import io.flutter.plugins.firebase.core.FlutterFirebasePluginRegistry;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class FlutterFirebaseAnalyticsPlugin implements FlutterFirebasePlugin, MethodChannel.MethodCallHandler, FlutterPlugin {
   private FirebaseAnalytics analytics;
   private MethodChannel channel;

   private static Bundle createBundleFromMap(Map<String, Object> var0) {
      if (var0 == null) {
         return null;
      } else {
         Bundle var1 = new Bundle();

         for (Entry var6 : var0.entrySet()) {
            Object var3 = var6.getValue();
            String var7 = (String)var6.getKey();
            if (var3 instanceof String) {
               var1.putString(var7, (String)var3);
            } else if (var3 instanceof Integer) {
               var1.putLong(var7, ((Integer)var3).intValue());
            } else if (var3 instanceof Long) {
               var1.putLong(var7, (Long)var3);
            } else if (var3 instanceof Double) {
               var1.putDouble(var7, (Double)var3);
            } else if (var3 instanceof Boolean) {
               var1.putBoolean(var7, (Boolean)var3);
            } else if (var3 == null) {
               var1.putString(var7, null);
            } else if (!(var3 instanceof Iterable)) {
               if (!(var3 instanceof Map)) {
                  StringBuilder var8 = new StringBuilder("Unsupported value type: ");
                  var8.append(var3.getClass().getCanonicalName());
                  throw new IllegalArgumentException(var8.toString());
               }

               var1.putParcelable(var7, createBundleFromMap((Map<String, Object>)var3));
            } else {
               ArrayList var4 = new ArrayList();

               for (var3 : (Iterable)var3) {
                  if (!(var3 instanceof Map)) {
                     StringBuilder var9 = new StringBuilder("Unsupported value type: ");
                     var9.append(var3.getClass().getCanonicalName());
                     var9.append(" in list at key ");
                     var9.append(var7);
                     throw new IllegalArgumentException(var9.toString());
                  }

                  var4.add(createBundleFromMap((Map<String, Object>)var3));
               }

               var1.putParcelableArrayList(var7, var4);
            }
         }

         return var1;
      }
   }

   private Task<String> handleGetAppInstanceId() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda8(this, var1));
      return var1.getTask();
   }

   private Task<Long> handleGetSessionId() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda0(this, var1));
      return var1.getTask();
   }

   private Task<Void> handleLogEvent(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda5(this, var1, var2));
      return var2.getTask();
   }

   private Task<Void> handleResetAnalyticsData() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda12(this, var1));
      return var1.getTask();
   }

   private Task<Void> handleSetAnalyticsCollectionEnabled(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda11(this, var1, var2));
      return var2.getTask();
   }

   private Task<Void> handleSetSessionTimeoutDuration(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda10(this, var1, var2));
      return var2.getTask();
   }

   private Task<Void> handleSetUserId(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda2(this, var1, var2));
      return var2.getTask();
   }

   private Task<Void> handleSetUserProperty(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda4(this, var1, var2));
      return var2.getTask();
   }

   private void initInstance(BinaryMessenger var1, Context var2) {
      this.analytics = FirebaseAnalytics.getInstance(var2);
      MethodChannel var3 = new MethodChannel(var1, "plugins.flutter.io/firebase_analytics");
      this.channel = var3;
      var3.setMethodCallHandler(this);
      FlutterFirebasePluginRegistry.registerPlugin("plugins.flutter.io/firebase_analytics", this);
   }

   private Task<Void> setConsent(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda1(this, var1, var2));
      return var2.getTask();
   }

   private Task<Void> setDefaultEventParameters(Map<String, Object> var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda3(this, var1, var2));
      return var2.getTask();
   }

   @Override
   public Task<Void> didReinitializeFirebaseCore() {
      TaskCompletionSource var1 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda9(var1));
      return var1.getTask();
   }

   @Override
   public Task<Map<String, Object>> getPluginConstantsForFirebaseApp(FirebaseApp var1) {
      TaskCompletionSource var2 = new TaskCompletionSource();
      cachedThreadPool.execute(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda7(this, var2));
      return var2.getTask();
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.initInstance(var1.getBinaryMessenger(), var1.getApplicationContext());
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      MethodChannel var2 = this.channel;
      if (var2 != null) {
         var2.setMethodCallHandler(null);
         this.channel = null;
      }
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      String var5 = var1.method;
      var5.hashCode();
      int var4 = var5.hashCode();
      byte var3 = -1;
      switch (var4) {
         case -2090892968:
            if (var5.equals("Analytics#getAppInstanceId")) {
               var3 = 0;
            }
            break;
         case -1931910274:
            if (var5.equals("Analytics#resetAnalyticsData")) {
               var3 = 1;
            }
            break;
         case -1572470123:
            if (var5.equals("Analytics#setConsent")) {
               var3 = 2;
            }
            break;
         case -273201790:
            if (var5.equals("Analytics#setAnalyticsCollectionEnabled")) {
               var3 = 3;
            }
            break;
         case -99047480:
            if (var5.equals("Analytics#setDefaultEventParameters")) {
               var3 = 4;
            }
            break;
         case -45011405:
            if (var5.equals("Analytics#logEvent")) {
               var3 = 5;
            }
            break;
         case 179244440:
            if (var5.equals("Analytics#getSessionId")) {
               var3 = 6;
            }
            break;
         case 1083589925:
            if (var5.equals("Analytics#setUserProperty")) {
               var3 = 7;
            }
            break;
         case 1751063748:
            if (var5.equals("Analytics#setSessionTimeoutDuration")) {
               var3 = 8;
            }
            break;
         case 1992044651:
            if (var5.equals("Analytics#setUserId")) {
               var3 = 9;
            }
      }

      Task var6;
      switch (var3) {
         case 0:
            var6 = this.handleGetAppInstanceId();
            break;
         case 1:
            var6 = this.handleResetAnalyticsData();
            break;
         case 2:
            var6 = this.setConsent(var1.arguments());
            break;
         case 3:
            var6 = this.handleSetAnalyticsCollectionEnabled(var1.arguments());
            break;
         case 4:
            var6 = this.setDefaultEventParameters(var1.arguments());
            break;
         case 5:
            var6 = this.handleLogEvent(var1.arguments());
            break;
         case 6:
            var6 = this.handleGetSessionId();
            break;
         case 7:
            var6 = this.handleSetUserProperty(var1.arguments());
            break;
         case 8:
            var6 = this.handleSetSessionTimeoutDuration(var1.arguments());
            break;
         case 9:
            var6 = this.handleSetUserId(var1.arguments());
            break;
         default:
            var2.notImplemented();
            return;
      }

      var6.addOnCompleteListener(new FlutterFirebaseAnalyticsPlugin$$ExternalSyntheticLambda6(var2));
   }
}
