package no.futurehome.futurehome_app.widget.helpers;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import no.futurehome.futurehome_app.widget.models.AuthCredentials;
import no.futurehome.futurehome_app.widget.models.Mode;
import no.futurehome.futurehome_app.widget.models.ModeKt;
import no.futurehome.futurehome_app.widget.models.RuntimeEnv;
import no.futurehome.futurehome_app.widget.models.SiteM;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

@Metadata(d1 = {"\000P\n\002\030\002\n\002\020\000\n\002\b\003\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\030\000  2\0020\001:\001 B\007¢\006\004\b\002\020\003J\036\020\006\032\n\022\004\022\0020\b\030\0010\0072\006\020\t\032\0020\n2\006\020\013\032\0020\fJ(\020\r\032\004\030\0010\0162\006\020\017\032\0020\n2\006\020\020\032\0020\n2\006\020\021\032\0020\n2\006\020\013\032\0020\fJ\035\020\022\032\004\030\0010\0232\006\020\t\032\0020\n2\006\020\013\032\0020\f¢\006\002\020\024J \020\025\032\004\030\0010\n2\006\020\026\032\0020\n2\006\020\t\032\0020\n2\006\020\013\032\0020\fJ\036\020\027\032\0020\0302\006\020\026\032\0020\n2\006\020\031\032\0020\n2\006\020\013\032\0020\fJ&\020\032\032\0020\0232\006\020\033\032\0020\0342\006\020\026\032\0020\n2\006\020\031\032\0020\n2\006\020\013\032\0020\fJ&\020\035\032\0020\0232\006\020\036\032\0020\0372\006\020\026\032\0020\n2\006\020\031\032\0020\n2\006\020\013\032\0020\fR\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006!"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/ApiHelper;", "", "<init>", "()V", "client", "Lokhttp3/OkHttpClient;", "getSites", "", "Lno/futurehome/futurehome_app/widget/models/SiteM;", "accessTokenHash", "", "runtimeEnv", "Lno/futurehome/futurehome_app/widget/models/RuntimeEnv;", "refreshAccessToken", "Lno/futurehome/futurehome_app/widget/models/AuthCredentials;", "refreshToken", "scope", "tokenEndpoint", "checkAccessToken", "", "(Ljava/lang/String;Lno/futurehome/futurehome_app/widget/models/RuntimeEnv;)Ljava/lang/Boolean;", "getSiteTokenHash", "siteId", "getActiveMode", "Lno/futurehome/futurehome_app/widget/helpers/GetActiveModeResult;", "siteTokenHash", "setActiveMode", "mode", "Lno/futurehome/futurehome_app/widget/models/Mode;", "triggerShortcut", "shortcutId", "", "Companion", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ApiHelper {
  public static final Companion Companion = new Companion(null);
  
  private static final String TAG = "ApiHelper";
  
  private final OkHttpClient client = new OkHttpClient();
  
  public final Boolean checkAccessToken(String paramString, RuntimeEnv paramRuntimeEnv) {
    Intrinsics.checkNotNullParameter(paramString, "accessTokenHash");
    Intrinsics.checkNotNullParameter(paramRuntimeEnv, "runtimeEnv");
    Request.Builder builder = (new Request.Builder()).url(paramRuntimeEnv.getHeimdallCheckEndpoint());
    StringBuilder stringBuilder = new StringBuilder("Bearer ");
    stringBuilder.append(paramString);
    Request request = builder.addHeader("Authorization", stringBuilder.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).head().build();
    paramString = null;
    try {
      Boolean bool;
      Response response = this.client.newCall(request).execute();
      int i = response.code();
      if (i != 200) {
        if (i == 401)
          bool = Boolean.valueOf(false); 
      } else {
        bool = Boolean.valueOf(true);
      } 
      return bool;
    } catch (Exception exception) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder1 = new StringBuilder("checkAccessToken, call error: ");
      stringBuilder1.append(exception);
      companion.e("ApiHelper", stringBuilder1.toString());
      return null;
    } 
  }
  
  public final GetActiveModeResult getActiveMode(String paramString1, String paramString2, RuntimeEnv paramRuntimeEnv) {
    Intrinsics.checkNotNullParameter(paramString1, "siteId");
    Intrinsics.checkNotNullParameter(paramString2, "siteTokenHash");
    Intrinsics.checkNotNullParameter(paramRuntimeEnv, "runtimeEnv");
    Request.Builder builder = (new Request.Builder()).url(paramRuntimeEnv.getBifrostGetModeEndpoint(paramString1)).addHeader("Content-Type", "application/json");
    StringBuilder stringBuilder = new StringBuilder("Bearer ");
    stringBuilder.append(paramString2);
    Request request = builder.addHeader("Authorization", stringBuilder.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).get().build();
    try {
      Response response = this.client.newCall(request).execute();
      ResponseBody responseBody = response.body();
      if (responseBody != null) {
        String str = responseBody.string();
      } else {
        responseBody = null;
      } 
      if (responseBody == null) {
        Logger.Companion.e("ApiHelper", "getActiveMode, response body is null");
        return GetActiveModeResult.FetchError;
      } 
      try {
        JSONObject jSONObject = new JSONObject((String)responseBody);
        if (response.code() != 200) {
          if (response.code() == 500 && Intrinsics.areEqual(JsonOptsKt.optStringFromJson(jSONObject, "cause"), "HUB_TIMEOUT")) {
            Logger.Companion.d("ApiHelper", "getActiveMode, hub timeout");
            return GetActiveModeResult.HubTimeout;
          } 
          companion = Logger.Companion;
          int i = response.code();
          StringBuilder stringBuilder2 = new StringBuilder("getActiveMode, response code: ");
          stringBuilder2.append(i);
          stringBuilder2.append(", res: ");
          stringBuilder2.append(response);
          stringBuilder2.append(", body: ");
          stringBuilder2.append((String)responseBody);
          companion.e("ApiHelper", stringBuilder2.toString());
          return GetActiveModeResult.FetchError;
        } 
        String str = JsonOptsKt.optStringFromJson((JSONObject)companion, "mode");
        if (str == null) {
          Logger.Companion.e("ApiHelper", "getActiveMode, response has no value for mode key");
          return GetActiveModeResult.FetchError;
        } 
        switch (str.hashCode()) {
          case 109522647:
            if (!str.equals("sleep"))
              break; 
            return GetActiveModeResult.SleepMode;
          case 3208415:
            if (str.equals("home"))
              return GetActiveModeResult.HomeMode; 
            break;
          case 3007214:
            if (!str.equals("away"))
              break; 
            return GetActiveModeResult.AwayMode;
          case -1685839139:
            if (!str.equals("vacation"))
              break; 
            return GetActiveModeResult.VacationMode;
        } 
        Logger.Companion companion = Logger.Companion;
        StringBuilder stringBuilder1 = new StringBuilder("getActiveMode, unknown mode: ");
        stringBuilder1.append(str);
        companion.e("ApiHelper", stringBuilder1.toString());
        return GetActiveModeResult.FetchError;
      } catch (Exception exception) {
        Logger.Companion companion = Logger.Companion;
        StringBuilder stringBuilder1 = new StringBuilder("getActiveMode, getting JSON from String failed, error: ");
        stringBuilder1.append(exception);
        companion.e("ApiHelper", stringBuilder1.toString());
        return GetActiveModeResult.FetchError;
      } 
    } catch (Exception exception) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder1 = new StringBuilder("getActiveMode, call error: ");
      stringBuilder1.append(exception);
      companion.e("ApiHelper", stringBuilder1.toString());
      return GetActiveModeResult.FetchError;
    } 
  }
  
  public final String getSiteTokenHash(String paramString1, String paramString2, RuntimeEnv paramRuntimeEnv) {
    Intrinsics.checkNotNullParameter(paramString1, "siteId");
    Intrinsics.checkNotNullParameter(paramString2, "accessTokenHash");
    Intrinsics.checkNotNullParameter(paramRuntimeEnv, "runtimeEnv");
    Request.Builder builder = (new Request.Builder()).url(paramRuntimeEnv.getHeimdallGetSiteTokenHash(paramString1)).addHeader("Content-Type", "application/json");
    StringBuilder stringBuilder = new StringBuilder("Bearer ");
    stringBuilder.append(paramString2);
    Request request = builder.addHeader("Authorization", stringBuilder.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).get().build();
    try {
      Response response = this.client.newCall(request).execute();
      if (response.code() != 200) {
        Logger.Companion companion = Logger.Companion;
        int i = response.code();
        ResponseBody responseBody1 = response.body();
        if (responseBody1 != null) {
          String str = responseBody1.string();
        } else {
          responseBody1 = null;
        } 
        StringBuilder stringBuilder1 = new StringBuilder("getSiteTokenHash, response code: ");
        stringBuilder1.append(i);
        stringBuilder1.append(", res: ");
        stringBuilder1.append(response);
        stringBuilder1.append(", body: ");
        stringBuilder1.append((String)responseBody1);
        companion.e("ApiHelper", stringBuilder1.toString());
        return null;
      } 
      ResponseBody responseBody = response.body();
      if (responseBody != null) {
        String str = responseBody.string();
      } else {
        responseBody = null;
      } 
      if (responseBody == null) {
        Logger.Companion.e("ApiHelper", "getSiteTokenHash, response body is null");
        return null;
      } 
      try {
        JSONObject jSONObject = new JSONObject((String)responseBody);
        String str = JsonOptsKt.optStringFromJson(jSONObject, "site_token_hash");
        if (str == null) {
          Logger.Companion.e("ApiHelper", "getSiteTokenHash, response has no value for site_token_hash key");
          return null;
        } 
        return str;
      } catch (Exception exception) {
        Logger.Companion companion = Logger.Companion;
        StringBuilder stringBuilder1 = new StringBuilder("getSiteTokenHash, getting JSON from String failed, error: ");
        stringBuilder1.append(exception);
        companion.e("ApiHelper", stringBuilder1.toString());
        return null;
      } 
    } catch (Exception exception) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder1 = new StringBuilder("getSiteTokenHash, call error: ");
      stringBuilder1.append(exception);
      companion.e("ApiHelper", stringBuilder1.toString());
      return null;
    } 
  }
  
  public final List<SiteM> getSites(String paramString, RuntimeEnv paramRuntimeEnv) {
    Intrinsics.checkNotNullParameter(paramString, "accessTokenHash");
    Intrinsics.checkNotNullParameter(paramRuntimeEnv, "runtimeEnv");
    RequestBody requestBody = RequestBody.Companion.create("{   \"query\": \"{ \\n       sites { \\n          name \\n          id \\n          address { \\n               address \\n               city \\n               postalCode \\n          } \\n          gateways { \\n               online \\n               applications { \\n                   config\\n               }\\n          } \\n       } \\n   }\"}", MediaType.Companion.get("application/json; charset=utf-8"));
    Request.Builder builder = (new Request.Builder()).url(paramRuntimeEnv.getNiflheimEndpoint()).addHeader("Content-Type", "application/json");
    StringBuilder stringBuilder = new StringBuilder("Bearer ");
    stringBuilder.append(paramString);
    Request request = builder.addHeader("Authorization", stringBuilder.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).post(requestBody).build();
    try {
      Response response = this.client.newCall(request).execute();
      if (response.code() != 200) {
        Logger.Companion companion = Logger.Companion;
        int i = response.code();
        ResponseBody responseBody1 = response.body();
        if (responseBody1 != null) {
          String str = responseBody1.string();
        } else {
          responseBody1 = null;
        } 
        stringBuilder = new StringBuilder("getAvailableSites, response code: ");
        stringBuilder.append(i);
        stringBuilder.append(", res: ");
        stringBuilder.append(response);
        stringBuilder.append(", body: ");
        stringBuilder.append((String)responseBody1);
        companion.e("ApiHelper", stringBuilder.toString());
        return null;
      } 
      ResponseBody responseBody = response.body();
      if (responseBody != null) {
        String str = responseBody.string();
      } else {
        responseBody = null;
      } 
      if (responseBody == null) {
        Logger.Companion.e("ApiHelper", "getAvailableSites, response body is null");
        return null;
      } 
      try {
        StringBuilder stringBuilder1;
        JSONObject jSONObject2 = new JSONObject((String)responseBody);
        if (jSONObject2.has("errors")) {
          Logger.Companion companion = Logger.Companion;
          stringBuilder1 = new StringBuilder("getAvailableSites, response contains errors, res: ");
          stringBuilder1.append(response);
          stringBuilder1.append(", body: ");
          stringBuilder1.append((String)responseBody);
          companion.e("ApiHelper", stringBuilder1.toString());
          return null;
        } 
        if (!stringBuilder1.has("data")) {
          Logger.Companion.e("ApiHelper", "resJson doesn't contain data key");
          return null;
        } 
        JSONObject jSONObject1 = stringBuilder1.getJSONObject("data");
        Intrinsics.checkNotNullExpressionValue(jSONObject1, "getJSONObject(...)");
        if (!jSONObject1.has("sites")) {
          Logger.Companion.e("ApiHelper", "resJson doesn't contain sites key");
          return null;
        } 
        ArrayList<SiteM> arrayList = new ArrayList();
        Object object = jSONObject1.opt("sites");
        Intrinsics.checkNotNullExpressionValue(object, "opt(...)");
        if (Intrinsics.areEqual(object, JSONObject.NULL))
          return arrayList; 
        if (!(object instanceof org.json.JSONArray)) {
          Logger.Companion.e("ApiHelper", "sites key isn't a json array");
          return null;
        } 
        object = object;
        int i = object.length();
        byte b = 0;
        while (b < i) {
          SiteM.Companion companion = SiteM.Companion;
          Object object1 = object.get(b);
          Intrinsics.checkNotNull(object1, "null cannot be cast to non-null type org.json.JSONObject");
          object1 = companion.fromJson((JSONObject)object1);
          if (object1 != null) {
            arrayList.add(object1);
            b++;
            continue;
          } 
          Logger.Companion.e("ApiHelper", "getAvailableSites, error: SiteM is equal to null");
          return null;
        } 
        return arrayList;
      } catch (Exception exception) {
        Logger.Companion companion = Logger.Companion;
        StringBuilder stringBuilder1 = new StringBuilder("getting JSON from String failed, error: ");
        stringBuilder1.append(exception);
        companion.e("ApiHelper", stringBuilder1.toString());
        return null;
      } 
    } catch (Exception exception) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder1 = new StringBuilder("getAvailableSites, call error: ");
      stringBuilder1.append(exception);
      companion.e("ApiHelper", stringBuilder1.toString());
      return null;
    } 
  }
  
  public final AuthCredentials refreshAccessToken(String paramString1, String paramString2, String paramString3, RuntimeEnv paramRuntimeEnv) {
    Intrinsics.checkNotNullParameter(paramString1, "refreshToken");
    Intrinsics.checkNotNullParameter(paramString2, "scope");
    Intrinsics.checkNotNullParameter(paramString3, "tokenEndpoint");
    Intrinsics.checkNotNullParameter(paramRuntimeEnv, "runtimeEnv");
    FormBody formBody = (new FormBody.Builder(null, 1, null)).add("grant_type", "refresh_token").add("refresh_token", paramString1).add("scope", paramString2).add("client_id", paramRuntimeEnv.getClientId()).add("client_secret", paramRuntimeEnv.getClientSecret()).build();
    Request request = (new Request.Builder()).url(paramString3).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier()).post(formBody).build();
    try {
      Logger.Companion companion;
      Response response = this.client.newCall(request).execute();
      if (response.code() != 200) {
        companion = Logger.Companion;
        int i = response.code();
        ResponseBody responseBody1 = response.body();
        if (responseBody1 != null) {
          String str = responseBody1.string();
        } else {
          responseBody1 = null;
        } 
        StringBuilder stringBuilder = new StringBuilder("refreshAccessToken, response code: ");
        stringBuilder.append(i);
        stringBuilder.append(", res: ");
        stringBuilder.append(response);
        stringBuilder.append(", body: ");
        stringBuilder.append((String)responseBody1);
        companion.e("ApiHelper", stringBuilder.toString());
        return null;
      } 
      ResponseBody responseBody = response.body();
      if (responseBody != null) {
        String str = responseBody.string();
      } else {
        responseBody = null;
      } 
      if (responseBody == null) {
        Logger.Companion.e("ApiHelper", "refreshAccessToken, response body is null");
        return null;
      } 
      AuthCredentials authCredentials = AuthCredentials.Companion.fromResponseString((String)responseBody, (String)companion);
      if (authCredentials == null) {
        Logger.Companion.e("ApiHelper", "refreshAccessToken, AuthCredentials.fromResponseString() returned null");
        return null;
      } 
      return authCredentials;
    } catch (Exception exception) {
      Logger.Companion companion = Logger.Companion;
      StringBuilder stringBuilder = new StringBuilder("refreshAccessToken, call error: ");
      stringBuilder.append(exception);
      companion.e("ApiHelper", stringBuilder.toString());
      return null;
    } 
  }
  
  public final boolean setActiveMode(Mode paramMode, String paramString1, String paramString2, RuntimeEnv paramRuntimeEnv) {
    Intrinsics.checkNotNullParameter(paramMode, "mode");
    Intrinsics.checkNotNullParameter(paramString1, "siteId");
    Intrinsics.checkNotNullParameter(paramString2, "siteTokenHash");
    Intrinsics.checkNotNullParameter(paramRuntimeEnv, "runtimeEnv");
    String str = paramRuntimeEnv.getBifrostChangeModeEndpoint(paramString1, ModeKt.modeToString(paramMode));
    Request.Builder builder1 = (new Request.Builder()).url(str).addHeader("Content-Type", "application/json");
    StringBuilder stringBuilder = new StringBuilder("Bearer ");
    stringBuilder.append(paramString2);
    Request.Builder builder2 = builder1.addHeader("Authorization", stringBuilder.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier());
    RequestBody.Companion companion = RequestBody.Companion;
    stringBuilder = null;
    Request request = builder2.put(companion.create(new byte[0], (MediaType)null, 0, 0)).build();
    try {
      Response response = this.client.newCall(request).execute();
      if (response.code() != 200) {
        String str1;
        Logger.Companion companion1 = Logger.Companion;
        int i = response.code();
        ResponseBody responseBody = response.body();
        if (responseBody != null)
          str1 = responseBody.string(); 
        StringBuilder stringBuilder1 = new StringBuilder("setActiveMode, response code: ");
        stringBuilder1.append(i);
        stringBuilder1.append(", res: ");
        stringBuilder1.append(response);
        stringBuilder1.append(", body: ");
        stringBuilder1.append(str1);
        companion1.e("ApiHelper", stringBuilder1.toString());
        return false;
      } 
      return true;
    } catch (Exception exception) {
      Logger.Companion companion1 = Logger.Companion;
      stringBuilder = new StringBuilder("setActiveMode, call error: ");
      stringBuilder.append(exception);
      companion1.e("ApiHelper", stringBuilder.toString());
      return false;
    } 
  }
  
  public final boolean triggerShortcut(int paramInt, String paramString1, String paramString2, RuntimeEnv paramRuntimeEnv) {
    Intrinsics.checkNotNullParameter(paramString1, "siteId");
    Intrinsics.checkNotNullParameter(paramString2, "siteTokenHash");
    Intrinsics.checkNotNullParameter(paramRuntimeEnv, "runtimeEnv");
    paramString1 = paramRuntimeEnv.getBifrostTriggerShortcutEndpoint(paramString1, String.valueOf(paramInt));
    Request.Builder builder1 = (new Request.Builder()).url(paramString1).addHeader("Content-Type", "application/json");
    StringBuilder stringBuilder = new StringBuilder("Bearer ");
    stringBuilder.append(paramString2);
    Request.Builder builder2 = builder1.addHeader("Authorization", stringBuilder.toString()).addHeader("x-fh-app-id", TracingIdentifierKt.getTracingIdentifier());
    RequestBody.Companion companion = RequestBody.Companion;
    builder1 = null;
    Request request = builder2.put(companion.create(new byte[0], (MediaType)null, 0, 0)).build();
    try {
      Response response = this.client.newCall(request).execute();
      if (response.code() != 200) {
        String str;
        Logger.Companion companion1 = Logger.Companion;
        paramInt = response.code();
        ResponseBody responseBody = response.body();
        if (responseBody != null)
          str = responseBody.string(); 
        StringBuilder stringBuilder1 = new StringBuilder("triggerShortcut, response code: ");
        stringBuilder1.append(paramInt);
        stringBuilder1.append(", res: ");
        stringBuilder1.append(response);
        stringBuilder1.append(", body: ");
        stringBuilder1.append(str);
        companion1.e("ApiHelper", stringBuilder1.toString());
        return false;
      } 
      return true;
    } catch (Exception exception) {
      Logger.Companion companion1 = Logger.Companion;
      StringBuilder stringBuilder1 = new StringBuilder("triggerShortcut, call error: ");
      stringBuilder1.append(exception);
      companion1.e("ApiHelper", stringBuilder1.toString());
      return false;
    } 
  }
  
  @Metadata(d1 = {"\000\022\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\016\n\000\b\003\030\0002\0020\001B\t\b\002¢\006\004\b\002\020\003R\016\020\004\032\0020\005XT¢\006\002\n\000¨\006\006"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/ApiHelper$Companion;", "", "<init>", "()V", "TAG", "", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
  public static final class Companion {
    private Companion() {}
  }
}
