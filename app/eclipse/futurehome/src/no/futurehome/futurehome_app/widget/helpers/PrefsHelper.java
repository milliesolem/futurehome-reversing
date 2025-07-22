package no.futurehome.futurehome_app.widget.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import no.futurehome.futurehome_app.widget.models.AuthCredentials;
import no.futurehome.futurehome_app.widget.models.Language;
import no.futurehome.futurehome_app.widget.models.LanguageKt;
import no.futurehome.futurehome_app.widget.models.RuntimeEnv;
import no.futurehome.futurehome_app.widget.models.WidgetData;
import no.futurehome.futurehome_app.widget.models.WidgetState;
import no.futurehome.futurehome_app.widget.models.WidgetStateKt;

@Metadata(d1 = {"\000V\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\002\n\002\b\002\n\002\020\016\n\002\b\005\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\002\030\000 #2\0020\001:\001#B\017\022\006\020\002\032\0020\003¢\006\004\b\004\020\005J\006\020\t\032\0020\nJ\016\020\013\032\0020\f2\006\020\r\032\0020\nJ\b\020\016\032\004\030\0010\017J\016\020\020\032\0020\f2\006\020\r\032\0020\017J\006\020\021\032\0020\nJ\016\020\022\032\0020\f2\006\020\023\032\0020\nJ\006\020\024\032\0020\025J\016\020\026\032\0020\f2\006\020\023\032\0020\025J\016\020\027\032\0020\f2\006\020\030\032\0020\031J\006\020\032\032\0020\031J\b\020\033\032\004\030\0010\034J\016\020\035\032\0020\f2\006\020\036\032\0020\034J\006\020\037\032\0020 J\b\020!\032\004\030\0010\"R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\b\032\0020\007X\004¢\006\002\n\000¨\006$"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/PrefsHelper;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "widgetPrefs", "Landroid/content/SharedPreferences;", "appPrefs", "getShowSites", "", "setShowSites", "", "newValue", "getActiveSiteId", "", "setActiveSiteId", "getIsLoading", "setIsLoading", "value", "getWidgetData", "Lno/futurehome/futurehome_app/widget/models/WidgetData;", "setWidgetData", "setWidgetState", "state", "Lno/futurehome/futurehome_app/widget/models/WidgetState;", "getWidgetState", "getAuthCredentials", "Lno/futurehome/futurehome_app/widget/models/AuthCredentials;", "setAuthCredentials", "authCredits", "getLanguage", "Lno/futurehome/futurehome_app/widget/models/Language;", "getRuntimeEnv", "Lno/futurehome/futurehome_app/widget/models/RuntimeEnv;", "Companion", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class PrefsHelper {
  public static final Companion Companion = new Companion(null);
  
  private static final String TAG = "PrefsHelper";
  
  private final SharedPreferences appPrefs;
  
  private final SharedPreferences widgetPrefs;
  
  public PrefsHelper(Context paramContext) {
    SharedPreferences sharedPreferences2 = paramContext.getSharedPreferences("futurehome.widget.WidgetPrefs", 0);
    Intrinsics.checkNotNullExpressionValue(sharedPreferences2, "getSharedPreferences(...)");
    this.widgetPrefs = sharedPreferences2;
    SharedPreferences sharedPreferences1 = paramContext.getSharedPreferences("FlutterSharedPreferences", 0);
    Intrinsics.checkNotNullExpressionValue(sharedPreferences1, "getSharedPreferences(...)");
    this.appPrefs = sharedPreferences1;
  }
  
  public final String getActiveSiteId() {
    return this.widgetPrefs.getString("futurehome.widget.ActiveSiteId", null);
  }
  
  public final AuthCredentials getAuthCredentials() {
    String str = this.appPrefs.getString("flutter.authCredentials", null);
    if (str == null) {
      Logger.Companion.d("PrefsHelper", "getAuthCredentials, authCredentials are null");
      return null;
    } 
    return AuthCredentials.Companion.fromPrefsString(str);
  }
  
  public final boolean getIsLoading() {
    return this.widgetPrefs.getBoolean("futurehome.widget.IsLoading", false);
  }
  
  public final Language getLanguage() {
    String str = this.appPrefs.getString("flutter.language", null);
    if (str == null) {
      Logger.Companion.d("PrefsHelper", "getLanguage, language is null");
      return Language.EN;
    } 
    return LanguageKt.languageForString(StringsKt.trim(str, new char[] { '"' }));
  }
  
  public final RuntimeEnv getRuntimeEnv() {
    RuntimeEnv runtimeEnv;
    String str = this.appPrefs.getString("flutter.runtimeEnv", null);
    if (str == null) {
      Logger.Companion.d("PrefsHelper", "runtimeEnvString, runtimeEnvString is null -> returning default prod env");
      return RuntimeEnv.Companion.getProd();
    } 
    str = StringsKt.trim(str, new char[] { '"' });
    if (Intrinsics.areEqual(str, "prod")) {
      runtimeEnv = RuntimeEnv.Companion.getProd();
    } else {
      if (Intrinsics.areEqual(runtimeEnv, "beta"))
        return RuntimeEnv.Companion.getBeta(); 
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder = new StringBuilder("runtimeEnvString, trimmedString has unknown value: ");
      stringBuilder.append((String)runtimeEnv);
      companion.e("PrefsHelper", stringBuilder.toString());
      return null;
    } 
    return runtimeEnv;
  }
  
  public final boolean getShowSites() {
    return this.widgetPrefs.getBoolean("futurehome.widget.ShowSites", false);
  }
  
  public final WidgetData getWidgetData() {
    String str = this.widgetPrefs.getString("futurehome.widget.WidgetData", null);
    if (str == null)
      return new WidgetData(false, null, false, false, null, null, null, null, 255, null); 
    Object object = (new Gson()).fromJson(str, WidgetData.class);
    Intrinsics.checkNotNullExpressionValue(object, "fromJson(...)");
    return (WidgetData)object;
  }
  
  public final WidgetState getWidgetState() {
    SharedPreferences sharedPreferences = this.widgetPrefs;
    String str1 = "";
    String str2 = sharedPreferences.getString("futurehome.widget.WidgetState", "");
    if (str2 != null)
      str1 = str2; 
    return WidgetStateKt.stringToWidgetState(str1);
  }
  
  public final void setActiveSiteId(String paramString) {
    Intrinsics.checkNotNullParameter(paramString, "newValue");
    SharedPreferences.Editor editor = this.widgetPrefs.edit();
    editor.putString("futurehome.widget.ActiveSiteId", paramString);
    editor.commit();
  }
  
  public final void setAuthCredentials(AuthCredentials paramAuthCredentials) {
    Intrinsics.checkNotNullParameter(paramAuthCredentials, "authCredits");
    String str = paramAuthCredentials.toPrefsJsonString();
    SharedPreferences.Editor editor = this.appPrefs.edit();
    editor.putString("flutter.authCredentials", str);
    editor.commit();
  }
  
  public final void setIsLoading(boolean paramBoolean) {
    SharedPreferences.Editor editor = this.widgetPrefs.edit();
    editor.putBoolean("futurehome.widget.IsLoading", paramBoolean);
    editor.commit();
  }
  
  public final void setShowSites(boolean paramBoolean) {
    SharedPreferences.Editor editor = this.widgetPrefs.edit();
    editor.putBoolean("futurehome.widget.ShowSites", paramBoolean);
    editor.commit();
  }
  
  public final void setWidgetData(WidgetData paramWidgetData) {
    Intrinsics.checkNotNullParameter(paramWidgetData, "value");
    SharedPreferences.Editor editor = this.widgetPrefs.edit();
    editor.putString("futurehome.widget.WidgetData", (new Gson()).toJson(paramWidgetData));
    editor.commit();
  }
  
  public final void setWidgetState(WidgetState paramWidgetState) {
    Intrinsics.checkNotNullParameter(paramWidgetState, "state");
    SharedPreferences.Editor editor = this.widgetPrefs.edit();
    editor.putString("futurehome.widget.WidgetState", WidgetStateKt.widgetStateToString(paramWidgetState));
    editor.commit();
  }
  
  @Metadata(d1 = {"\000\022\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\016\n\000\b\003\030\0002\0020\001B\t\b\002¢\006\004\b\002\020\003R\016\020\004\032\0020\005XT¢\006\002\n\000¨\006\006"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/PrefsHelper$Companion;", "", "<init>", "()V", "TAG", "", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
  public static final class Companion {
    private Companion() {}
  }
}
