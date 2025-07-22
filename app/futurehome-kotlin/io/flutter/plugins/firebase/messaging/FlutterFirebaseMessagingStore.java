package io.flutter.plugins.firebase.messaging;

import android.content.SharedPreferences;
import com.google.firebase.messaging.RemoteMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlutterFirebaseMessagingStore {
   private static final String KEY_NOTIFICATION_IDS = "notification_ids";
   private static final int MAX_SIZE_NOTIFICATIONS = 100;
   private static final String PREFERENCES_FILE = "io.flutter.plugins.firebase.messaging";
   private static FlutterFirebaseMessagingStore instance;
   private final String DELIMITER = ",";
   private SharedPreferences preferences;

   public static FlutterFirebaseMessagingStore getInstance() {
      if (instance == null) {
         instance = new FlutterFirebaseMessagingStore();
      }

      return instance;
   }

   private SharedPreferences getPreferences() {
      if (this.preferences == null) {
         this.preferences = ContextHolder.getApplicationContext().getSharedPreferences("io.flutter.plugins.firebase.messaging", 0);
      }

      return this.preferences;
   }

   private Map<String, Object> jsonObjectToMap(JSONObject var1) throws JSONException {
      HashMap var4 = new HashMap();
      Iterator var5 = var1.keys();

      while (var5.hasNext()) {
         String var6 = (String)var5.next();
         Object var3 = var1.get(var6);
         Object var2;
         if (var3 instanceof JSONArray) {
            var2 = this.jsonArrayToList((JSONArray)var3);
         } else {
            var2 = var3;
            if (var3 instanceof JSONObject) {
               var2 = this.jsonObjectToMap((JSONObject)var3);
            }
         }

         var4.put(var6, var2);
      }

      return var4;
   }

   public Map<String, Object> getFirebaseMessageMap(String var1) {
      String var4 = this.getPreferencesStringValue(var1, null);
      if (var4 != null) {
         try {
            HashMap var2 = new HashMap(1);
            JSONObject var3 = new JSONObject(var4);
            Map var6 = this.jsonObjectToMap(var3);
            var6.put("to", var1);
            var2.put("message", var6);
            return var2;
         } catch (JSONException var5) {
            var5.printStackTrace();
         }
      }

      return null;
   }

   public String getPreferencesStringValue(String var1, String var2) {
      return this.getPreferences().getString(var1, var2);
   }

   public List<Object> jsonArrayToList(JSONArray var1) throws JSONException {
      ArrayList var5 = new ArrayList();

      for (int var2 = 0; var2 < var1.length(); var2++) {
         Object var4 = var1.get(var2);
         Object var3;
         if (var4 instanceof JSONArray) {
            var3 = this.jsonArrayToList((JSONArray)var4);
         } else {
            var3 = var4;
            if (var4 instanceof JSONObject) {
               var3 = this.jsonObjectToMap((JSONObject)var4);
            }
         }

         var5.add(var3);
      }

      return var5;
   }

   public void removeFirebaseMessage(String var1) {
      this.getPreferences().edit().remove(var1).apply();
      String var3 = this.getPreferencesStringValue("notification_ids", "");
      if (!var3.isEmpty()) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(",");
         this.setPreferencesStringValue("notification_ids", var3.replace(var2.toString(), ""));
      }
   }

   public void setPreferencesStringValue(String var1, String var2) {
      this.getPreferences().edit().putString(var1, var2).apply();
   }

   public void storeFirebaseMessage(RemoteMessage var1) {
      String var2 = new JSONObject(FlutterFirebaseMessagingUtils.remoteMessageToMap(var1)).toString();
      this.setPreferencesStringValue(var1.getMessageId(), var2);
      var2 = this.getPreferencesStringValue("notification_ids", "");
      StringBuilder var3 = new StringBuilder();
      var3.append(var2);
      var3.append(var1.getMessageId());
      var3.append(",");
      var2 = var3.toString();
      ArrayList var8 = new ArrayList<>(Arrays.asList(var2.split(",")));
      String var4 = var2;
      if (var8.size() > 100) {
         String var9 = (String)var8.get(0);
         this.getPreferences().edit().remove(var9).apply();
         StringBuilder var5 = new StringBuilder();
         var5.append(var9);
         var5.append(",");
         var4 = var2.replace(var5.toString(), "");
      }

      this.setPreferencesStringValue("notification_ids", var4);
   }
}
