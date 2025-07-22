package no.futurehome.futurehome_app.widget.helpers

import org.json.JSONArray
import org.json.JSONObject

private const val TAG: String = "Opts"

public fun optBooleanFromJson(json: JSONObject, key: String): Boolean? {
   if (var0.isNull(var1)) {
      return null;
   } else {
      try {
         var6 = var0.getBoolean(var1);
      } catch (var5: Exception) {
         val var7: Logger.Companion = Logger.Companion;
         val var4: StringBuilder = new StringBuilder("optBooleanFromJson, exception: ");
         var4.append(var5);
         var7.e("Opts", var4.toString());
         var6 = null;
      }

      return var6;
   }
}

public fun optIntFromJson(json: JSONObject, key: String): Int? {
   if (var0.isNull(var1)) {
      return null;
   } else {
      try {
         var7 = var0.getInt(var1);
      } catch (var5: Exception) {
         val var8: Logger.Companion = Logger.Companion;
         val var6: StringBuilder = new StringBuilder("optIntFromJson, exception: ");
         var6.append(var5);
         var8.e("Opts", var6.toString());
         var7 = null;
      }

      return var7;
   }
}

public fun optJsonArrayFromJson(json: JSONObject, key: String): JSONArray? {
   if (var0.isNull(var1)) {
      return null;
   } else {
      try {
         var7 = var0.getJSONArray(var1);
      } catch (var5: Exception) {
         val var4: Logger.Companion = Logger.Companion;
         val var6: StringBuilder = new StringBuilder("optJsonArrayFromJson, exception: ");
         var6.append(var5);
         var4.e("Opts", var6.toString());
         var7 = null;
      }

      return var7;
   }
}

public fun optJsonFromJson(json: JSONObject, key: String): JSONObject? {
   if (var0.isNull(var1)) {
      return null;
   } else {
      try {
         var0 = var0.getJSONObject(var1);
      } catch (var5: Exception) {
         val var7: Logger.Companion = Logger.Companion;
         val var4: StringBuilder = new StringBuilder("optJsonFromJson, exception: ");
         var4.append(var5);
         var7.e("Opts", var4.toString());
         var0 = null;
      }

      return var0;
   }
}

public fun optLongFromJson(json: JSONObject, key: String): Long? {
   if (var0.isNull(var1)) {
      return null;
   } else {
      try {
         var6 = var0.getLong(var1);
      } catch (var5: Exception) {
         val var7: Logger.Companion = Logger.Companion;
         val var4: StringBuilder = new StringBuilder("optLongFromJson, exception: ");
         var4.append(var5);
         var7.e("Opts", var4.toString());
         var6 = null;
      }

      return var6;
   }
}

public fun optStringFromJson(json: JSONObject, key: String): String? {
   if (var0.isNull(var1)) {
      return null;
   } else {
      try {
         var7 = var0.getString(var1);
      } catch (var5: Exception) {
         val var6: Logger.Companion = Logger.Companion;
         val var4: StringBuilder = new StringBuilder("optStringFromJson, exception: ");
         var4.append(var5);
         var6.e("Opts", var4.toString());
         var7 = null;
      }

      return var7;
   }
}
