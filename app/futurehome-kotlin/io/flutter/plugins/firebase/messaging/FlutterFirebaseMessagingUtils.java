package io.flutter.plugins.firebase.messaging;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Builder;
import com.google.firebase.messaging.RemoteMessage.Notification;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

class FlutterFirebaseMessagingUtils {
   static final String EXTRA_REMOTE_MESSAGE = "notification";
   static final String IS_AUTO_INIT_ENABLED = "isAutoInitEnabled";
   static final int JOB_ID = 2020;
   private static final String KEY_COLLAPSE_KEY = "collapseKey";
   private static final String KEY_DATA = "data";
   private static final String KEY_FROM = "from";
   private static final String KEY_MESSAGE_ID = "messageId";
   private static final String KEY_MESSAGE_TYPE = "messageType";
   private static final String KEY_SENT_TIME = "sentTime";
   private static final String KEY_TO = "to";
   private static final String KEY_TTL = "ttl";
   static final String SHARED_PREFERENCES_KEY = "io.flutter.firebase.messaging.callback";

   static FirebaseMessaging getFirebaseMessagingForArguments(Map<String, Object> var0) {
      return FirebaseMessaging.getInstance();
   }

   static RemoteMessage getRemoteMessageForArguments(Map<String, Object> var0) {
      Map var5 = Objects.requireNonNull((Map)var0.get("message"));
      Builder var3 = new Builder(Objects.requireNonNull((String)var5.get("to")));
      String var6 = (String)var5.get("collapseKey");
      String var4 = (String)var5.get("messageId");
      String var1 = (String)var5.get("messageType");
      Integer var2 = (Integer)var5.get("ttl");
      var5 = (Map)var5.get("data");
      if (var6 != null) {
         var3.setCollapseKey(var6);
      }

      if (var1 != null) {
         var3.setMessageType(var1);
      }

      if (var4 != null) {
         var3.setMessageId(var4);
      }

      if (var2 != null) {
         var3.setTtl(var2);
      }

      if (var5 != null) {
         var3.setData(var5);
      }

      return var3.build();
   }

   static Map<String, Object> getRemoteMessageNotificationForArguments(Map<String, Object> var0) {
      var0 = Objects.requireNonNull((Map)var0.get("message"));
      return var0.get("notification") == null ? null : (Map)var0.get("notification");
   }

   static boolean isApplicationForeground(Context var0) {
      KeyguardManager var1 = (KeyguardManager)var0.getSystemService("keyguard");
      if (var1 != null && var1.isKeyguardLocked()) {
         return false;
      } else {
         ActivityManager var4 = (ActivityManager)var0.getSystemService("activity");
         if (var4 == null) {
            return false;
         } else {
            List var5 = var4.getRunningAppProcesses();
            if (var5 == null) {
               return false;
            } else {
               String var3 = var0.getPackageName();

               for (RunningAppProcessInfo var6 : var5) {
                  if (var6.importance == 100 && var6.processName.equals(var3)) {
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   private static Map<String, Object> remoteMessageNotificationToMap(Notification var0) {
      HashMap var2 = new HashMap();
      HashMap var1 = new HashMap();
      if (var0.getTitle() != null) {
         var2.put("title", var0.getTitle());
      }

      if (var0.getTitleLocalizationKey() != null) {
         var2.put("titleLocKey", var0.getTitleLocalizationKey());
      }

      if (var0.getTitleLocalizationArgs() != null) {
         var2.put("titleLocArgs", Arrays.asList(var0.getTitleLocalizationArgs()));
      }

      if (var0.getBody() != null) {
         var2.put("body", var0.getBody());
      }

      if (var0.getBodyLocalizationKey() != null) {
         var2.put("bodyLocKey", var0.getBodyLocalizationKey());
      }

      if (var0.getBodyLocalizationArgs() != null) {
         var2.put("bodyLocArgs", Arrays.asList(var0.getBodyLocalizationArgs()));
      }

      if (var0.getChannelId() != null) {
         var1.put("channelId", var0.getChannelId());
      }

      if (var0.getClickAction() != null) {
         var1.put("clickAction", var0.getClickAction());
      }

      if (var0.getColor() != null) {
         var1.put("color", var0.getColor());
      }

      if (var0.getIcon() != null) {
         var1.put("smallIcon", var0.getIcon());
      }

      if (var0.getImageUrl() != null) {
         var1.put("imageUrl", var0.getImageUrl().toString());
      }

      if (var0.getLink() != null) {
         var1.put("link", var0.getLink().toString());
      }

      if (var0.getNotificationCount() != null) {
         var1.put("count", var0.getNotificationCount());
      }

      if (var0.getNotificationPriority() != null) {
         var1.put("priority", var0.getNotificationPriority());
      }

      if (var0.getSound() != null) {
         var1.put("sound", var0.getSound());
      }

      if (var0.getTicker() != null) {
         var1.put("ticker", var0.getTicker());
      }

      if (var0.getVisibility() != null) {
         var1.put("visibility", var0.getVisibility());
      }

      if (var0.getTag() != null) {
         var1.put("tag", var0.getTag());
      }

      var2.put("android", var1);
      return var2;
   }

   static Map<String, Object> remoteMessageToMap(RemoteMessage var0) {
      HashMap var3 = new HashMap();
      HashMap var2 = new HashMap();
      if (var0.getCollapseKey() != null) {
         var3.put("collapseKey", var0.getCollapseKey());
      }

      if (var0.getFrom() != null) {
         var3.put("from", var0.getFrom());
      }

      if (var0.getTo() != null) {
         var3.put("to", var0.getTo());
      }

      if (var0.getMessageId() != null) {
         var3.put("messageId", var0.getMessageId());
      }

      if (var0.getMessageType() != null) {
         var3.put("messageType", var0.getMessageType());
      }

      if (!var0.getData().isEmpty()) {
         for (Entry var4 : var0.getData().entrySet()) {
            var2.put((String)var4.getKey(), var4.getValue());
         }
      }

      var3.put("data", var2);
      var3.put("ttl", var0.getTtl());
      var3.put("sentTime", var0.getSentTime());
      if (var0.getNotification() != null) {
         var3.put("notification", remoteMessageNotificationToMap(var0.getNotification()));
      }

      return var3;
   }
}
