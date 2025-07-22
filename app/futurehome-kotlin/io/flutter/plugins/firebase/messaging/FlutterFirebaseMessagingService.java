package io.flutter.plugins.firebase.messaging;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FlutterFirebaseMessagingService extends FirebaseMessagingService {
   public void onMessageReceived(RemoteMessage var1) {
   }

   public void onNewToken(String var1) {
      FlutterFirebaseTokenLiveData.getInstance().postToken(var1);
   }
}
