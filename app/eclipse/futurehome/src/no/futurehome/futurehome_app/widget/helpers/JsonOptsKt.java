package no.futurehome.futurehome_app.widget.helpers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(d1 = {"\0000\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\013\n\002\b\002\032\030\020\002\032\004\030\0010\0012\006\020\003\032\0020\0042\006\020\005\032\0020\001\032\035\020\006\032\004\030\0010\0072\006\020\003\032\0020\0042\006\020\005\032\0020\001¢\006\002\020\b\032\030\020\t\032\004\030\0010\0042\006\020\003\032\0020\0042\006\020\005\032\0020\001\032\030\020\n\032\004\030\0010\0132\006\020\003\032\0020\0042\006\020\005\032\0020\001\032\035\020\f\032\004\030\0010\r2\006\020\003\032\0020\0042\006\020\005\032\0020\001¢\006\002\020\016\032\035\020\017\032\004\030\0010\0202\006\020\003\032\0020\0042\006\020\005\032\0020\001¢\006\002\020\021\"\016\020\000\032\0020\001XT¢\006\002\n\000¨\006\022"}, d2 = {"TAG", "", "optStringFromJson", "json", "Lorg/json/JSONObject;", "key", "optLongFromJson", "", "(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/Long;", "optJsonFromJson", "optJsonArrayFromJson", "Lorg/json/JSONArray;", "optIntFromJson", "", "(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/Integer;", "optBooleanFromJson", "", "(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/Boolean;", "app_release"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class JsonOptsKt {
  private static final String TAG = "Opts";
  
  public static final Boolean optBooleanFromJson(JSONObject paramJSONObject, String paramString) {
    Intrinsics.checkNotNullParameter(paramJSONObject, "json");
    Intrinsics.checkNotNullParameter(paramString, "key");
    boolean bool = paramJSONObject.isNull(paramString);
    Exception exception2 = null;
    if (bool)
      return null; 
    try {
      Boolean bool1 = Boolean.valueOf(paramJSONObject.getBoolean(paramString));
    } catch (Exception exception1) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder = new StringBuilder("optBooleanFromJson, exception: ");
      stringBuilder.append(exception1);
      companion.e("Opts", stringBuilder.toString());
      exception1 = exception2;
    } 
    return (Boolean)exception1;
  }
  
  public static final Integer optIntFromJson(JSONObject paramJSONObject, String paramString) {
    StringBuilder stringBuilder1;
    Intrinsics.checkNotNullParameter(paramJSONObject, "json");
    Intrinsics.checkNotNullParameter(paramString, "key");
    boolean bool = paramJSONObject.isNull(paramString);
    StringBuilder stringBuilder2 = null;
    if (bool)
      return null; 
    try {
      Integer integer = Integer.valueOf(paramJSONObject.getInt(paramString));
    } catch (Exception exception) {
      Logger.Companion companion = Logger.Companion;
      stringBuilder1 = new StringBuilder("optIntFromJson, exception: ");
      stringBuilder1.append(exception);
      companion.e("Opts", stringBuilder1.toString());
      stringBuilder1 = stringBuilder2;
    } 
    return (Integer)stringBuilder1;
  }
  
  public static final JSONArray optJsonArrayFromJson(JSONObject paramJSONObject, String paramString) {
    StringBuilder stringBuilder1;
    Intrinsics.checkNotNullParameter(paramJSONObject, "json");
    Intrinsics.checkNotNullParameter(paramString, "key");
    boolean bool = paramJSONObject.isNull(paramString);
    StringBuilder stringBuilder2 = null;
    if (bool)
      return null; 
    try {
      JSONArray jSONArray = paramJSONObject.getJSONArray(paramString);
    } catch (Exception exception) {
      Logger.Companion companion = Logger.Companion;
      stringBuilder1 = new StringBuilder("optJsonArrayFromJson, exception: ");
      stringBuilder1.append(exception);
      companion.e("Opts", stringBuilder1.toString());
      stringBuilder1 = stringBuilder2;
    } 
    return (JSONArray)stringBuilder1;
  }
  
  public static final JSONObject optJsonFromJson(JSONObject paramJSONObject, String paramString) {
    Intrinsics.checkNotNullParameter(paramJSONObject, "json");
    Intrinsics.checkNotNullParameter(paramString, "key");
    boolean bool = paramJSONObject.isNull(paramString);
    Exception exception2 = null;
    if (bool)
      return null; 
    try {
      paramJSONObject = paramJSONObject.getJSONObject(paramString);
    } catch (Exception exception1) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder = new StringBuilder("optJsonFromJson, exception: ");
      stringBuilder.append(exception1);
      companion.e("Opts", stringBuilder.toString());
      exception1 = exception2;
    } 
    return (JSONObject)exception1;
  }
  
  public static final Long optLongFromJson(JSONObject paramJSONObject, String paramString) {
    Intrinsics.checkNotNullParameter(paramJSONObject, "json");
    Intrinsics.checkNotNullParameter(paramString, "key");
    boolean bool = paramJSONObject.isNull(paramString);
    Exception exception2 = null;
    if (bool)
      return null; 
    try {
      Long long_ = Long.valueOf(paramJSONObject.getLong(paramString));
    } catch (Exception exception1) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder = new StringBuilder("optLongFromJson, exception: ");
      stringBuilder.append(exception1);
      companion.e("Opts", stringBuilder.toString());
      exception1 = exception2;
    } 
    return (Long)exception1;
  }
  
  public static final String optStringFromJson(JSONObject paramJSONObject, String paramString) {
    Logger.Companion companion1;
    Intrinsics.checkNotNullParameter(paramJSONObject, "json");
    Intrinsics.checkNotNullParameter(paramString, "key");
    boolean bool = paramJSONObject.isNull(paramString);
    Logger.Companion companion2 = null;
    if (bool)
      return null; 
    try {
      String str = paramJSONObject.getString(paramString);
    } catch (Exception exception) {
      companion1 = Logger.Companion;
      StringBuilder stringBuilder = new StringBuilder("optStringFromJson, exception: ");
      stringBuilder.append(exception);
      companion1.e("Opts", stringBuilder.toString());
      companion1 = companion2;
    } 
    return (String)companion1;
  }
}
