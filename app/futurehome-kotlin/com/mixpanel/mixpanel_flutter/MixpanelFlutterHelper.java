package com.mixpanel.mixpanel_flutter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MixpanelFlutterHelper {
   public static Object fromJson(Object var0) throws JSONException {
      if (var0 == JSONObject.NULL) {
         return null;
      } else if (var0 instanceof JSONObject) {
         return toMap((JSONObject)var0);
      } else {
         Object var1 = var0;
         if (var0 instanceof JSONArray) {
            var1 = toList((JSONArray)var0);
         }

         return var1;
      }
   }

   public static JSONObject getMergedProperties(JSONObject var0, JSONObject var1) throws JSONException {
      if (var1 != null) {
         Iterator var2 = var1.keys();

         while (var2.hasNext()) {
            String var3 = (String)var2.next();
            var0.put(var3, var1.get(var3));
         }
      }

      return var0;
   }

   public static List toList(JSONArray var0) throws JSONException {
      ArrayList var2 = new ArrayList();

      for (int var1 = 0; var1 < var0.length(); var1++) {
         var2.add(fromJson(var0.get(var1)));
      }

      return var2;
   }

   public static Map<String, Object> toMap(JSONObject var0) throws JSONException {
      HashMap var1 = new HashMap();
      Iterator var3 = var0.keys();

      while (var3.hasNext()) {
         String var2 = (String)var3.next();
         var1.put(var2, fromJson(var0.get(var2)));
      }

      return var1;
   }
}
