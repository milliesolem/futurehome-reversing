package io.flutter.plugins.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import androidx.preference.PreferenceManager
import io.flutter.plugin.common.BinaryMessenger
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map.Entry

public class SharedPreferencesBackend(messenger: BinaryMessenger,
      context: Context,
      listEncoder: SharedPreferencesListEncoder = (new ListEncoder()) as SharedPreferencesListEncoder
   ) :
   SharedPreferencesAsyncApi {
   private final var messenger: BinaryMessenger
   private final var context: Context
   private final var listEncoder: SharedPreferencesListEncoder

   init {
      this.messenger = var1;
      this.context = var2;
      this.listEncoder = var3;

      try {
         SharedPreferencesAsyncApi.Companion.setUp(this.messenger, this, "shared_preferences");
      } catch (var4: Exception) {
         Log.e("SharedPreferencesPlugin", "Received exception while setting up SharedPreferencesBackend", var4);
      }
   }

   private fun createSharedPreferences(options: SharedPreferencesPigeonOptions): SharedPreferences {
      val var2: SharedPreferences;
      if (var1.getFileName() == null) {
         var2 = PreferenceManager.getDefaultSharedPreferences(this.context);
      } else {
         var2 = this.context.getSharedPreferences(var1.getFileName(), 0);
      }

      return var2;
   }

   public override fun clear(allowList: List<String>?, options: SharedPreferencesPigeonOptions) {
      val var10: SharedPreferences = this.createSharedPreferences(var2);
      val var3: Editor = var10.edit();
      val var6: java.util.Map = var10.getAll();
      val var7: ArrayList = new ArrayList();

      for (java.lang.String var4 : var6.keySet()) {
         val var8: Any = var6.get(var4);
         val var11: java.util.Set;
         if (var1 != null) {
            var11 = CollectionsKt.toSet(var1);
         } else {
            var11 = null;
         }

         if (SharedPreferencesPluginKt.preferencesFilter(var4, var8, var11)) {
            var7.add(var4);
         }
      }

      val var12: java.util.Iterator = var7.iterator();

      while (var12.hasNext()) {
         val var9: Any = var12.next();
         var3.remove(var9 as java.lang.String);
      }

      var3.apply();
   }

   public override fun getAll(allowList: List<String>?, options: SharedPreferencesPigeonOptions): Map<String, Any> {
      val var8: java.util.Map = this.createSharedPreferences(var2).getAll();
      val var3: HashMap = new HashMap();

      for (Entry var5 : var8.entrySet()) {
         val var7: java.lang.String = var5.getKey() as java.lang.String;
         var var6: Any = var5.getValue();
         val var9: java.util.Set;
         if (var1 != null) {
            var9 = CollectionsKt.toSet(var1);
         } else {
            var9 = null;
         }

         if (SharedPreferencesPluginKt.preferencesFilter(var7, var6, var9)) {
            var6 = var5.getValue();
            if (var6 != null) {
               val var10: Any = var5.getKey();
               val var11: Any = SharedPreferencesPluginKt.transformPref(var6, this.listEncoder);
               var3.put(var10, var11);
            }
         }
      }

      return var3;
   }

   public override fun getBool(key: String, options: SharedPreferencesPigeonOptions): Boolean? {
      val var4: SharedPreferences = this.createSharedPreferences(var2);
      val var3: java.lang.Boolean;
      if (var4.contains(var1)) {
         var3 = var4.getBoolean(var1, true);
      } else {
         var3 = null;
      }

      return var3;
   }

   public override fun getDouble(key: String, options: SharedPreferencesPigeonOptions): Double? {
      val var5: SharedPreferences = this.createSharedPreferences(var2);
      val var4: java.lang.Double;
      if (var5.contains(var1)) {
         val var3: Any = SharedPreferencesPluginKt.transformPref(var5.getString(var1, ""), this.listEncoder);
         var4 = var3 as java.lang.Double;
      } else {
         var4 = null;
      }

      return var4;
   }

   public override fun getInt(key: String, options: SharedPreferencesPigeonOptions): Long? {
      val var4: SharedPreferences = this.createSharedPreferences(var2);
      val var3: java.lang.Long;
      if (var4.contains(var1)) {
         var3 = var4.getLong(var1, 0L);
      } else {
         var3 = null;
      }

      return var3;
   }

   public override fun getKeys(allowList: List<String>?, options: SharedPreferencesPigeonOptions): List<String> {
      val var8: java.util.Map = this.createSharedPreferences(var2).getAll();
      val var3: java.util.Map = new LinkedHashMap();

      for (Entry var4 : var8.entrySet()) {
         var var9: Any = var4.getKey();
         val var7: java.lang.String = var9 as java.lang.String;
         val var6: Any = var4.getValue();
         if (var1 != null) {
            var9 = CollectionsKt.toSet(var1);
         } else {
            var9 = null;
         }

         if (SharedPreferencesPluginKt.preferencesFilter(var7, var6, (java.util.Set<java.lang.String>)var9)) {
            var3.put(var4.getKey(), var4.getValue());
         }
      }

      return CollectionsKt.toList(var3.keySet());
   }

   public override fun getPlatformEncodedStringList(key: String, options: SharedPreferencesPigeonOptions): List<String>? {
      val var5: SharedPreferences = this.createSharedPreferences(var2);
      val var3: Boolean = var5.contains(var1);
      var var9: java.util.List = null;
      if (var3) {
         val var6: java.lang.String = var5.getString(var1, "");
         var9 = null;
         if (StringsKt.startsWith$default(var6, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu", false, 2, null)) {
            var9 = null;
            if (!StringsKt.startsWith$default(var6, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!", false, 2, null)) {
               val var7: java.util.List = SharedPreferencesPluginKt.transformPref(var5.getString(var1, ""), this.listEncoder) as java.util.List;
               var9 = null;
               if (var7 != null) {
                  val var10: java.lang.Iterable = var7;
                  val var8: java.util.Collection = new ArrayList();

                  for (Object var11 : var10) {
                     if (var11 is java.lang.String) {
                        var8.add(var11);
                     }
                  }

                  var9 = var8 as java.util.List;
               }
            }
         }
      }

      return var9;
   }

   public override fun getString(key: String, options: SharedPreferencesPigeonOptions): String? {
      val var4: SharedPreferences = this.createSharedPreferences(var2);
      if (var4.contains(var1)) {
         var1 = var4.getString(var1, "");
      } else {
         var1 = null;
      }

      return var1;
   }

   public override fun getStringList(key: String, options: SharedPreferencesPigeonOptions): StringListResult? {
      val var5: SharedPreferences = this.createSharedPreferences(var2);
      if (var5.contains(var1)) {
         var1 = var5.getString(var1, "");
         val var4: StringListResult;
         if (StringsKt.startsWith$default(var1, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!", false, 2, null)) {
            var4 = new StringListResult(var1, StringListLookupResultType.JSON_ENCODED);
         } else if (StringsKt.startsWith$default(var1, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu", false, 2, null)) {
            var4 = new StringListResult(null, StringListLookupResultType.PLATFORM_ENCODED);
         } else {
            var4 = new StringListResult(null, StringListLookupResultType.UNEXPECTED_STRING);
         }

         return var4;
      } else {
         return null;
      }
   }

   public override fun setBool(key: String, value: Boolean, options: SharedPreferencesPigeonOptions) {
      this.createSharedPreferences(var3).edit().putBoolean(var1, var2).apply();
   }

   @Deprecated(message = "This is just for testing, use `setEncodedStringList`")
   public override fun setDeprecatedStringList(key: String, value: List<String>, options: SharedPreferencesPigeonOptions) {
      val var4: java.lang.String = this.listEncoder.encode(var2);
      val var5: StringBuilder = new StringBuilder("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu");
      var5.append(var4);
      this.createSharedPreferences(var3).edit().putString(var1, var5.toString()).apply();
   }

   public override fun setDouble(key: String, value: Double, options: SharedPreferencesPigeonOptions) {
      val var6: Editor = this.createSharedPreferences(var4).edit();
      val var5: StringBuilder = new StringBuilder("VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu");
      var5.append(var2);
      var6.putString(var1, var5.toString()).apply();
   }

   public override fun setEncodedStringList(key: String, value: String, options: SharedPreferencesPigeonOptions) {
      this.createSharedPreferences(var3).edit().putString(var1, var2).apply();
   }

   public override fun setInt(key: String, value: Long, options: SharedPreferencesPigeonOptions) {
      this.createSharedPreferences(var4).edit().putLong(var1, var2).apply();
   }

   public override fun setString(key: String, value: String, options: SharedPreferencesPigeonOptions) {
      this.createSharedPreferences(var3).edit().putString(var1, var2).apply();
   }

   public fun tearDown() {
      SharedPreferencesAsyncApi.Companion.setUp(this.messenger, null, "shared_preferences");
   }
}
