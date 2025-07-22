package dev.fluttercommunity.plus.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Network;
import android.net.ConnectivityManager.NetworkCallback;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0;
import io.flutter.plugin.common.EventChannel;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver implements EventChannel.StreamHandler {
   public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
   private final Connectivity connectivity;
   private final Context context;
   private EventChannel.EventSink events;
   private final Handler mainHandler = new Handler(Looper.getMainLooper());
   private NetworkCallback networkCallback;

   public ConnectivityBroadcastReceiver(Context var1, Connectivity var2) {
      this.context = var1;
      this.connectivity = var2;
   }

   private void sendEvent() {
      ConnectivityBroadcastReceiver$$ExternalSyntheticLambda1 var1 = new ConnectivityBroadcastReceiver$$ExternalSyntheticLambda1(this);
      this.mainHandler.post(var1);
   }

   private void sendEvent(String var1) {
      ConnectivityBroadcastReceiver$$ExternalSyntheticLambda2 var2 = new ConnectivityBroadcastReceiver$$ExternalSyntheticLambda2(this, var1);
      this.mainHandler.post(var2);
   }

   @Override
   public void onCancel(Object var1) {
      if (VERSION.SDK_INT >= 24) {
         if (this.networkCallback != null) {
            this.connectivity.getConnectivityManager().unregisterNetworkCallback(this.networkCallback);
            this.networkCallback = null;
         }
      } else {
         try {
            this.context.unregisterReceiver(this);
         } catch (Exception var2) {
         }
      }
   }

   @Override
   public void onListen(Object var1, EventChannel.EventSink var2) {
      this.events = var2;
      if (VERSION.SDK_INT >= 24) {
         this.networkCallback = new NetworkCallback(this) {
            final ConnectivityBroadcastReceiver this$0;

            {
               this.this$0 = var1;
            }

            public void onAvailable(Network var1) {
               this.this$0.sendEvent();
            }

            public void onLost(Network var1) {
               this.this$0.sendEvent("none");
            }
         };
         ExternalSyntheticApiModelOutline0.m(this.connectivity.getConnectivityManager(), this.networkCallback);
      } else {
         this.context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
      }
   }

   public void onReceive(Context var1, Intent var2) {
      EventChannel.EventSink var3 = this.events;
      if (var3 != null) {
         var3.success(this.connectivity.getNetworkType());
      }
   }
}
