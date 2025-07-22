package no.futurehome.futurehome_app.widget.helpers

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.google.gson.Gson
import no.futurehome.futurehome_app.widget.models.AuthCredentials
import no.futurehome.futurehome_app.widget.models.Language
import no.futurehome.futurehome_app.widget.models.LanguageKt
import no.futurehome.futurehome_app.widget.models.RuntimeEnv
import no.futurehome.futurehome_app.widget.models.WidgetData
import no.futurehome.futurehome_app.widget.models.WidgetState
import no.futurehome.futurehome_app.widget.models.WidgetStateKt

public class PrefsHelper(context: Context) {
   private final val widgetPrefs: SharedPreferences
   private final val appPrefs: SharedPreferences

   init {
      val var2: SharedPreferences = var1.getSharedPreferences("futurehome.widget.WidgetPrefs", 0);
      this.widgetPrefs = var2;
      val var3: SharedPreferences = var1.getSharedPreferences("FlutterSharedPreferences", 0);
      this.appPrefs = var3;
   }

   public fun getActiveSiteId(): String? {
      return this.widgetPrefs.getString("futurehome.widget.ActiveSiteId", null);
   }

   public fun getAuthCredentials(): AuthCredentials? {
      val var1: java.lang.String = this.appPrefs.getString("flutter.authCredentials", null);
      if (var1 == null) {
         Logger.Companion.d("PrefsHelper", "getAuthCredentials, authCredentials are null");
         return null;
      } else {
         return AuthCredentials.Companion.fromPrefsString(var1);
      }
   }

   public fun getIsLoading(): Boolean {
      return this.widgetPrefs.getBoolean("futurehome.widget.IsLoading", false);
   }

   public fun getLanguage(): Language {
      val var1: java.lang.String = this.appPrefs.getString("flutter.language", null);
      if (var1 == null) {
         Logger.Companion.d("PrefsHelper", "getLanguage, language is null");
         return Language.EN;
      } else {
         return LanguageKt.languageForString(StringsKt.trim(var1, new char[]{'"'}));
      }
   }

   public fun getRuntimeEnv(): RuntimeEnv? {
      var var1: java.lang.String = this.appPrefs.getString("flutter.runtimeEnv", null);
      if (var1 == null) {
         Logger.Companion.d("PrefsHelper", "runtimeEnvString, runtimeEnvString is null -> returning default prod env");
         return RuntimeEnv.Companion.getProd();
      } else {
         var1 = StringsKt.trim(var1, new char[]{'"'});
         val var5: RuntimeEnv;
         if (var1 == "prod") {
            var5 = RuntimeEnv.Companion.getProd();
         } else {
            if (!(var1 == "beta")) {
               val var2: Logger.Companion = Logger.Companion;
               val var3: StringBuilder = new StringBuilder("runtimeEnvString, trimmedString has unknown value: ");
               var3.append(var1);
               var2.e("PrefsHelper", var3.toString());
               return null;
            }

            var5 = RuntimeEnv.Companion.getBeta();
         }

         return var5;
      }
   }

   public fun getShowSites(): Boolean {
      return this.widgetPrefs.getBoolean("futurehome.widget.ShowSites", false);
   }

   public fun getWidgetData(): WidgetData {
      var var1: java.lang.String = this.widgetPrefs.getString("futurehome.widget.WidgetData", null);
      if (var1 == null) {
         return new WidgetData(false, null, false, false, null, null, null, null, 255, null);
      } else {
         var1 = (java.lang.String)new Gson().fromJson(var1, WidgetData.class);
         return var1 as WidgetData;
      }
   }

   public fun getWidgetState(): WidgetState {
      var var1: java.lang.String = "";
      val var3: java.lang.String = this.widgetPrefs.getString("futurehome.widget.WidgetState", "");
      if (var3 != null) {
         var1 = var3;
      }

      return WidgetStateKt.stringToWidgetState(var1);
   }

   public fun setActiveSiteId(newValue: String) {
      val var2: Editor = this.widgetPrefs.edit();
      var2.putString("futurehome.widget.ActiveSiteId", var1);
      var2.commit();
   }

   public fun setAuthCredentials(authCredits: AuthCredentials) {
      val var3: java.lang.String = var1.toPrefsJsonString();
      val var2: Editor = this.appPrefs.edit();
      var2.putString("flutter.authCredentials", var3);
      var2.commit();
   }

   public fun setIsLoading(value: Boolean) {
      val var2: Editor = this.widgetPrefs.edit();
      var2.putBoolean("futurehome.widget.IsLoading", var1);
      var2.commit();
   }

   public fun setShowSites(newValue: Boolean) {
      val var2: Editor = this.widgetPrefs.edit();
      var2.putBoolean("futurehome.widget.ShowSites", var1);
      var2.commit();
   }

   public fun setWidgetData(value: WidgetData) {
      val var2: Editor = this.widgetPrefs.edit();
      var2.putString("futurehome.widget.WidgetData", new Gson().toJson(var1));
      var2.commit();
   }

   public fun setWidgetState(state: WidgetState) {
      val var2: Editor = this.widgetPrefs.edit();
      var2.putString("futurehome.widget.WidgetState", WidgetStateKt.widgetStateToString(var1));
      var2.commit();
   }

   public companion object {
      private const val TAG: String
   }
}
