package no.futurehome.futurehome_app.widget.models;

import com.signify.hue.flutterreactiveble.utils.Duration$$ExternalSyntheticBackport0;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import no.futurehome.futurehome_app.widget.helpers.JsonOptsKt;
import no.futurehome.futurehome_app.widget.helpers.Logger;
import org.json.JSONObject;

@Metadata(d1 = {"\000*\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\005\n\002\020\t\n\002\b\023\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\003\b\b\030\000 \"2\0020\001:\001\"B7\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003\022\006\020\006\032\0020\003\022\006\020\007\032\0020\003\022\006\020\b\032\0020\t¢\006\004\b\n\020\013J\006\020\024\032\0020\003J\t\020\025\032\0020\003HÆ\003J\t\020\026\032\0020\003HÆ\003J\t\020\027\032\0020\003HÆ\003J\t\020\030\032\0020\003HÆ\003J\t\020\031\032\0020\003HÆ\003J\t\020\032\032\0020\tHÆ\003JE\020\033\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\005\032\0020\0032\b\b\002\020\006\032\0020\0032\b\b\002\020\007\032\0020\0032\b\b\002\020\b\032\0020\tHÆ\001J\023\020\034\032\0020\0352\b\020\036\032\004\030\0010\001HÖ\003J\t\020\037\032\0020 HÖ\001J\t\020!\032\0020\003HÖ\001R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\f\020\rR\021\020\004\032\0020\003¢\006\b\n\000\032\004\b\016\020\rR\021\020\005\032\0020\003¢\006\b\n\000\032\004\b\017\020\rR\021\020\006\032\0020\003¢\006\b\n\000\032\004\b\020\020\rR\021\020\007\032\0020\003¢\006\b\n\000\032\004\b\021\020\rR\021\020\b\032\0020\t¢\006\b\n\000\032\004\b\022\020\023¨\006#"}, d2 = {"Lno/futurehome/futurehome_app/widget/models/AuthCredentials;", "", "accessToken", "", "refreshToken", "tokenEndpoint", "accessTokenHash", "scope", "expiresAtMillis", "", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getAccessToken", "()Ljava/lang/String;", "getRefreshToken", "getTokenEndpoint", "getAccessTokenHash", "getScope", "getExpiresAtMillis", "()J", "toPrefsJsonString", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "Companion", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class AuthCredentials {
  public static final Companion Companion = new Companion(null);
  
  private static final String TAG = "AuthCredentials";
  
  public static final int expirationGrace = 300000;
  
  private final String accessToken;
  
  private final String accessTokenHash;
  
  private final long expiresAtMillis;
  
  private final String refreshToken;
  
  private final String scope;
  
  private final String tokenEndpoint;
  
  public AuthCredentials(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong) {
    this.accessToken = paramString1;
    this.refreshToken = paramString2;
    this.tokenEndpoint = paramString3;
    this.accessTokenHash = paramString4;
    this.scope = paramString5;
    this.expiresAtMillis = paramLong;
  }
  
  public final String component1() {
    return this.accessToken;
  }
  
  public final String component2() {
    return this.refreshToken;
  }
  
  public final String component3() {
    return this.tokenEndpoint;
  }
  
  public final String component4() {
    return this.accessTokenHash;
  }
  
  public final String component5() {
    return this.scope;
  }
  
  public final long component6() {
    return this.expiresAtMillis;
  }
  
  public final AuthCredentials copy(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong) {
    Intrinsics.checkNotNullParameter(paramString1, "accessToken");
    Intrinsics.checkNotNullParameter(paramString2, "refreshToken");
    Intrinsics.checkNotNullParameter(paramString3, "tokenEndpoint");
    Intrinsics.checkNotNullParameter(paramString4, "accessTokenHash");
    Intrinsics.checkNotNullParameter(paramString5, "scope");
    return new AuthCredentials(paramString1, paramString2, paramString3, paramString4, paramString5, paramLong);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof AuthCredentials))
      return false; 
    paramObject = paramObject;
    return !Intrinsics.areEqual(this.accessToken, ((AuthCredentials)paramObject).accessToken) ? false : (!Intrinsics.areEqual(this.refreshToken, ((AuthCredentials)paramObject).refreshToken) ? false : (!Intrinsics.areEqual(this.tokenEndpoint, ((AuthCredentials)paramObject).tokenEndpoint) ? false : (!Intrinsics.areEqual(this.accessTokenHash, ((AuthCredentials)paramObject).accessTokenHash) ? false : (!Intrinsics.areEqual(this.scope, ((AuthCredentials)paramObject).scope) ? false : (!(this.expiresAtMillis != ((AuthCredentials)paramObject).expiresAtMillis))))));
  }
  
  public final String getAccessToken() {
    return this.accessToken;
  }
  
  public final String getAccessTokenHash() {
    return this.accessTokenHash;
  }
  
  public final long getExpiresAtMillis() {
    return this.expiresAtMillis;
  }
  
  public final String getRefreshToken() {
    return this.refreshToken;
  }
  
  public final String getScope() {
    return this.scope;
  }
  
  public final String getTokenEndpoint() {
    return this.tokenEndpoint;
  }
  
  public int hashCode() {
    return ((((this.accessToken.hashCode() * 31 + this.refreshToken.hashCode()) * 31 + this.tokenEndpoint.hashCode()) * 31 + this.accessTokenHash.hashCode()) * 31 + this.scope.hashCode()) * 31 + Duration$$ExternalSyntheticBackport0.m(this.expiresAtMillis);
  }
  
  public final String toPrefsJsonString() {
    String str2 = this.accessToken;
    StringBuilder stringBuilder3 = new StringBuilder("{\"access_token\":\"");
    stringBuilder3.append(str2);
    stringBuilder3.append("\",");
    str2 = stringBuilder3.toString();
    String str5 = this.refreshToken;
    stringBuilder3 = new StringBuilder();
    stringBuilder3.append(str2);
    stringBuilder3.append("\"refresh_token\":\"");
    stringBuilder3.append(str5);
    stringBuilder3.append("\",");
    String str3 = stringBuilder3.toString();
    str2 = this.accessTokenHash;
    StringBuilder stringBuilder4 = new StringBuilder();
    stringBuilder4.append(str3);
    stringBuilder4.append("\"access_token_hash\":\"");
    stringBuilder4.append(str2);
    stringBuilder4.append("\",");
    str3 = stringBuilder4.toString();
    String str4 = this.tokenEndpoint;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str3);
    stringBuilder1.append("\"token_endpoint\":\"");
    stringBuilder1.append(str4);
    stringBuilder1.append("\",");
    str3 = stringBuilder1.toString();
    str4 = this.scope;
    stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str3);
    stringBuilder1.append("\"scope\":\"");
    stringBuilder1.append(str4);
    stringBuilder1.append("\",");
    String str1 = stringBuilder1.toString();
    long l = this.expiresAtMillis;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str1);
    stringBuilder2.append("\"expires_at_millis\":");
    stringBuilder2.append(l);
    str1 = stringBuilder2.toString();
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str1);
    stringBuilder2.append("}");
    return stringBuilder2.toString();
  }
  
  public String toString() {
    String str4 = this.accessToken;
    String str3 = this.refreshToken;
    String str1 = this.tokenEndpoint;
    String str5 = this.accessTokenHash;
    String str2 = this.scope;
    long l = this.expiresAtMillis;
    StringBuilder stringBuilder = new StringBuilder("AuthCredentials(accessToken=");
    stringBuilder.append(str4);
    stringBuilder.append(", refreshToken=");
    stringBuilder.append(str3);
    stringBuilder.append(", tokenEndpoint=");
    stringBuilder.append(str1);
    stringBuilder.append(", accessTokenHash=");
    stringBuilder.append(str5);
    stringBuilder.append(", scope=");
    stringBuilder.append(str2);
    stringBuilder.append(", expiresAtMillis=");
    stringBuilder.append(l);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  @Metadata(d1 = {"\000 \n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\005\b\003\030\0002\0020\001B\t\b\002¢\006\004\b\002\020\003J\020\020\b\032\004\030\0010\t2\006\020\n\032\0020\005J\030\020\013\032\004\030\0010\t2\006\020\f\032\0020\0052\006\020\r\032\0020\005R\016\020\004\032\0020\005XT¢\006\002\n\000R\016\020\006\032\0020\007XT¢\006\002\n\000¨\006\016"}, d2 = {"Lno/futurehome/futurehome_app/widget/models/AuthCredentials$Companion;", "", "<init>", "()V", "TAG", "", "expirationGrace", "", "fromPrefsString", "Lno/futurehome/futurehome_app/widget/models/AuthCredentials;", "authCredentialsString", "fromResponseString", "responseString", "tokenEndpoint", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
  public static final class Companion {
    private Companion() {}
    
    public final AuthCredentials fromPrefsString(String param1String) {
      Intrinsics.checkNotNullParameter(param1String, "authCredentialsString");
      param1String = StringsKt.replace$default(StringsKt.trim(param1String, new char[] { '"' }), "\\", "", false, 4, (Object)null);
      try {
        JSONObject jSONObject = new JSONObject(param1String);
        param1String = JsonOptsKt.optStringFromJson(jSONObject, "access_token");
        if (param1String == null) {
          Logger.Companion.e("AuthCredentials", "fromPrefsString, accessToken is null");
          return null;
        } 
        String str4 = JsonOptsKt.optStringFromJson(jSONObject, "refresh_token");
        if (str4 == null) {
          Logger.Companion.e("AuthCredentials", "fromPrefsString, refreshToken is null");
          return null;
        } 
        String str3 = JsonOptsKt.optStringFromJson(jSONObject, "access_token_hash");
        if (str3 == null) {
          Logger.Companion.e("AuthCredentials", "fromPrefsString, accessTokenHash is null");
          return null;
        } 
        String str1 = JsonOptsKt.optStringFromJson(jSONObject, "token_endpoint");
        if (str1 == null) {
          Logger.Companion.e("AuthCredentials", "fromPrefsString, tokenEndpoint is null");
          return null;
        } 
        String str2 = JsonOptsKt.optStringFromJson(jSONObject, "scope");
        if (str2 == null) {
          Logger.Companion.e("AuthCredentials", "fromPrefsString, scope is null");
          return null;
        } 
        Long long_ = JsonOptsKt.optLongFromJson(jSONObject, "expires_at_millis");
        if (long_ == null) {
          Logger.Companion.e("AuthCredentials", "fromPrefsString, expiresAtMillis is null");
          return null;
        } 
        return new AuthCredentials(param1String, str4, str1, str3, str2, long_.longValue());
      } catch (Exception exception) {
        Logger.Companion companion = Logger.Companion;
        StringBuilder stringBuilder = new StringBuilder("fromPrefsString, error: creating JSON from string: ");
        stringBuilder.append(param1String);
        companion.e("AuthCredentials", stringBuilder.toString());
        return null;
      } 
    }
    
    public final AuthCredentials fromResponseString(String param1String1, String param1String2) {
      Intrinsics.checkNotNullParameter(param1String1, "responseString");
      Intrinsics.checkNotNullParameter(param1String2, "tokenEndpoint");
      try {
        JSONObject jSONObject = new JSONObject(param1String1);
        String str2 = JsonOptsKt.optStringFromJson(jSONObject, "access_token");
        if (str2 == null) {
          Logger.Companion.e("AuthCredentials", "fromResponseString, accessToken is null");
          return null;
        } 
        String str3 = JsonOptsKt.optStringFromJson(jSONObject, "refresh_token");
        if (str3 == null) {
          Logger.Companion.e("AuthCredentials", "fromResponseString, refreshToken is null");
          return null;
        } 
        String str1 = JsonOptsKt.optStringFromJson(jSONObject, "access_token_hash");
        if (str1 == null) {
          Logger.Companion.e("AuthCredentials", "fromResponseString, accessTokenHash is null");
          return null;
        } 
        param1String1 = JsonOptsKt.optStringFromJson(jSONObject, "scope");
        if (param1String1 == null) {
          Logger.Companion.e("AuthCredentials", "fromResponseString, scope is null");
          return null;
        } 
        Long long_ = JsonOptsKt.optLongFromJson(jSONObject, "expires_in");
        if (long_ == null) {
          Logger.Companion.e("AuthCredentials", "fromResponseString, expiresIn is null");
          return null;
        } 
        return new AuthCredentials(str2, str3, param1String2, str1, param1String1, System.currentTimeMillis() + long_.longValue() * 1000L - 300000L);
      } catch (Exception exception) {
        Logger.Companion companion = Logger.Companion;
        StringBuilder stringBuilder = new StringBuilder("fromResponseString, creating JSON from string: ");
        stringBuilder.append(param1String1);
        companion.e("AuthCredentials", stringBuilder.toString());
        return null;
      } 
    }
  }
}
