package io.flutter.plugins.firebase.messaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.util.Log;
import com.google.firebase.messaging.RemoteMessage;
import java.util.HashMap;

public class FlutterFirebaseMessagingReceiver extends BroadcastReceiver {
   private static final String TAG = "FLTFireMsgReceiver";
   static HashMap<String, RemoteMessage> notifications = new HashMap<>();

   public void onReceive(Context var1, Intent var2) {
      Log.d("FLTFireMsgReceiver", "broadcast received for message");
      if (ContextHolder.getApplicationContext() == null) {
         Context var4;
         if (var1.getApplicationContext() != null) {
            var4 = var1.getApplicationContext();
         } else {
            var4 = var1;
         }

         ContextHolder.setApplicationContext(var4);
      }

      if (var2.getExtras() == null) {
         Log.d("FLTFireMsgReceiver", "broadcast received but intent contained no extras to process RemoteMessage. Operation cancelled.");
      } else {
         RemoteMessage var7 = new RemoteMessage(var2.getExtras());
         if (var7.getNotification() != null) {
            notifications.put(var7.getMessageId(), var7);
            FlutterFirebaseMessagingStore.getInstance().storeFirebaseMessage(var7);
         }

         if (FlutterFirebaseMessagingUtils.isApplicationForeground(var1)) {
            FlutterFirebaseRemoteMessageLiveData.getInstance().postRemoteMessage(var7);
         } else {
            Intent var5 = new Intent(var1, FlutterFirebaseMessagingBackgroundService.class);
            Parcel var6 = Parcel.obtain();
            boolean var3 = false;
            var7.writeToParcel(var6, 0);
            var5.putExtra("notification", var6.marshall());
            if (var7.getOriginalPriority() == 1) {
               var3 = true;
            }

            FlutterFirebaseMessagingBackgroundService.enqueueMessageProcessing(var1, var5, var3);
         }
      }
   }
}
