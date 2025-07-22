package io.flutter.plugins.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class FlutterFirebaseMessagingBackgroundExecutor implements MethodChannel.MethodCallHandler {
   private static final String CALLBACK_HANDLE_KEY = "callback_handle";
   private static final String TAG = "FLTFireBGExecutor";
   private static final String USER_CALLBACK_HANDLE_KEY = "user_callback_handle";
   private MethodChannel backgroundChannel;
   private FlutterEngine backgroundFlutterEngine;
   private final AtomicBoolean isCallbackDispatcherReady = new AtomicBoolean(false);

   private long getPluginCallbackHandle() {
      return ContextHolder.getApplicationContext().getSharedPreferences("io.flutter.firebase.messaging.callback", 0).getLong("callback_handle", 0L);
   }

   private long getUserCallbackHandle() {
      return ContextHolder.getApplicationContext().getSharedPreferences("io.flutter.firebase.messaging.callback", 0).getLong("user_callback_handle", 0L);
   }

   private void initializeMethodChannel(BinaryMessenger var1) {
      MethodChannel var2 = new MethodChannel(var1, "plugins.flutter.io/firebase_messaging_background");
      this.backgroundChannel = var2;
      var2.setMethodCallHandler(this);
   }

   private void onInitialized() {
      this.isCallbackDispatcherReady.set(true);
      FlutterFirebaseMessagingBackgroundService.onInitialized();
   }

   public static void setCallbackDispatcher(long var0) {
      Context var2 = ContextHolder.getApplicationContext();
      if (var2 == null) {
         Log.e("FLTFireBGExecutor", "Context is null, cannot continue.");
      } else {
         var2.getSharedPreferences("io.flutter.firebase.messaging.callback", 0).edit().putLong("callback_handle", var0).apply();
      }
   }

   public static void setUserCallbackHandle(long var0) {
      ContextHolder.getApplicationContext()
         .getSharedPreferences("io.flutter.firebase.messaging.callback", 0)
         .edit()
         .putLong("user_callback_handle", var0)
         .apply();
   }

   public void executeDartCallbackInBackgroundIsolate(Intent var1, CountDownLatch var2) {
      if (this.backgroundFlutterEngine == null) {
         Log.i("FLTFireBGExecutor", "A background message could not be handled in Dart as no onBackgroundMessage handler has been registered.");
      } else {
         MethodChannel.Result var9;
         if (var2 != null) {
            var9 = new MethodChannel.Result(this, var2) {
               final FlutterFirebaseMessagingBackgroundExecutor this$0;
               final CountDownLatch val$latch;

               {
                  this.this$0 = var1;
                  this.val$latch = var2x;
               }

               @Override
               public void error(String var1, String var2x, Object var3) {
                  this.val$latch.countDown();
               }

               @Override
               public void notImplemented() {
                  this.val$latch.countDown();
               }

               @Override
               public void success(Object var1) {
                  this.val$latch.countDown();
               }
            };
         } else {
            var9 = null;
         }

         byte[] var3 = var1.getByteArrayExtra("notification");
         if (var3 != null) {
            Parcel var8 = Parcel.obtain();

            try {
               var8.unmarshall(var3, 0, var3.length);
               var8.setDataPosition(0);
               Map var5 = FlutterFirebaseMessagingUtils.remoteMessageToMap((RemoteMessage)RemoteMessage.CREATOR.createFromParcel(var8));
               MethodChannel var4 = this.backgroundChannel;
               HashMap var10 = new HashMap<String, Object>(this, var5) {
                  final FlutterFirebaseMessagingBackgroundExecutor this$0;
                  final Map val$remoteMessageMap;

                  {
                     this.this$0 = var1;
                     this.val$remoteMessageMap = var2x;
                     this.put("userCallbackHandle", var1.getUserCallbackHandle());
                     this.put("message", var2x);
                  }
               };
               var4.invokeMethod("MessagingBackground#onMessage", var10, var9);
            } finally {
               var8.recycle();
            }
         } else {
            Log.e("FLTFireBGExecutor", "RemoteMessage byte array not found in Intent.");
         }
      }
   }

   boolean isDartBackgroundHandlerRegistered() {
      boolean var1;
      if (this.getPluginCallbackHandle() != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isNotRunning() {
      return this.isCallbackDispatcherReady.get() ^ true;
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      String var5 = var1.method;

      try {
         if (var5.equals("MessagingBackground#initialized")) {
            this.onInitialized();
            var2.success(true);
         } else {
            var2.notImplemented();
         }
      } catch (PluginRegistrantException var4) {
         StringBuilder var3 = new StringBuilder("Flutter FCM error: ");
         var3.append(var4.getMessage());
         var2.error("error", var3.toString(), null);
      }
   }

   public void startBackgroundIsolate() {
      if (this.isNotRunning()) {
         long var1 = this.getPluginCallbackHandle();
         if (var1 != 0L) {
            this.startBackgroundIsolate(var1, null);
         }
      }
   }

   public void startBackgroundIsolate(long var1, FlutterShellArgs var3) {
      if (this.backgroundFlutterEngine != null) {
         Log.e("FLTFireBGExecutor", "Background isolate already started.");
      } else {
         FlutterLoader var5 = FlutterInjector.instance().flutterLoader();
         Handler var4 = new Handler(Looper.getMainLooper());
         var4.post(new FlutterFirebaseMessagingBackgroundExecutor$$ExternalSyntheticLambda1(this, var5, var4, var3, var1));
      }
   }
}
