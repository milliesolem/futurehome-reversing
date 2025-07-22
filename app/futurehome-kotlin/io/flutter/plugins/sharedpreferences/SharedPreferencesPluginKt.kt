package io.flutter.plugins.sharedpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.PreferenceDataStoreDelegateKt
import androidx.datastore.preferences.core.Preferences
import kotlin.jvm.internal.PropertyReference1Impl
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KProperty

public const val TAG: String = "SharedPreferencesPlugin"
public const val SHARED_PREFERENCES_NAME: String = "FlutterSharedPreferences"
public const val LIST_PREFIX: String = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu"
public const val JSON_LIST_PREFIX: String = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!"
public const val DOUBLE_PREFIX: String = "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu"

private final val sharedPreferencesDataStore: DataStore<Preferences> by PreferenceDataStoreDelegateKt.preferencesDataStore$default(
   "FlutterSharedPreferences", null, null, null, 14, null
)
   private final get() {
      return sharedPreferencesDataStore$delegate.getValue(var0, $$delegatedProperties[0]) as DataStore<Preferences>;
   }

KProperty<Object>[] $$delegatedProperties = new KProperty[]{
   Reflection.property1(
      new PropertyReference1Impl(
         SharedPreferencesPluginKt.class,
         "sharedPreferencesDataStore",
         "getSharedPreferencesDataStore(Landroid/content/Context;)Landroidx/datastore/core/DataStore;",
         1
      )
   )
};

@JvmSynthetic
fun `access$getSharedPreferencesDataStore`(var0: Context): DataStore {
   return getSharedPreferencesDataStore(var0);
}

internal fun preferencesFilter(key: String, value: Any?, allowList: Set<String>?): Boolean {
   if (var2 != null) {
      return var2.contains(var0);
   } else {
      val var3: Boolean;
      if (var1 !is java.lang.Boolean && var1 !is java.lang.Long && var1 !is java.lang.String && var1 !is java.lang.Double) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }
}

internal fun transformPref(value: Any?, listEncoder: SharedPreferencesListEncoder): Any? {
   var var2: Any = var0;
   if (var0 is java.lang.String) {
      val var3: java.lang.String = var0;
      if (StringsKt.startsWith$default(var0, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu", false, 2, null)) {
         if (!StringsKt.startsWith$default(var3, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBhIGxpc3Qu!", false, 2, null)) {
            val var5: java.lang.String = var3.substring(40);
            var0 = var1.decode(var5);
         }

         return var0;
      }

      var2 = var0;
      if (StringsKt.startsWith$default(var3, "VGhpcyBpcyB0aGUgcHJlZml4IGZvciBEb3VibGUu", false, 2, null)) {
         var0 = var3.substring(40);
         var2 = java.lang.Double.parseDouble(var0);
      }
   }

   return var2;
}
