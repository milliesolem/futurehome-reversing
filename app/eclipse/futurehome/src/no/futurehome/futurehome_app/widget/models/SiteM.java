package no.futurehome.futurehome_app.widget.models;

import com.signify.hue.flutterreactiveble.utils.Duration$$ExternalSyntheticBackport0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import no.futurehome.futurehome_app.widget.helpers.JsonOptsKt;
import no.futurehome.futurehome_app.widget.helpers.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(d1 = {"\000,\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\003\n\002\020 \n\002\030\002\n\000\n\002\020\013\n\002\b\026\n\002\020\b\n\002\b\003\b\b\030\000 #2\0020\001:\001#B=\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003\022\f\020\006\032\b\022\004\022\0020\b0\007\022\006\020\t\032\0020\n\022\006\020\013\032\0020\n¢\006\004\b\f\020\rJ\t\020\027\032\0020\003HÆ\003J\t\020\030\032\0020\003HÆ\003J\t\020\031\032\0020\003HÆ\003J\017\020\032\032\b\022\004\022\0020\b0\007HÆ\003J\t\020\033\032\0020\nHÆ\003J\t\020\034\032\0020\nHÆ\003JK\020\035\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\005\032\0020\0032\016\b\002\020\006\032\b\022\004\022\0020\b0\0072\b\b\002\020\t\032\0020\n2\b\b\002\020\013\032\0020\nHÆ\001J\023\020\036\032\0020\n2\b\020\037\032\004\030\0010\001HÖ\003J\t\020 \032\0020!HÖ\001J\t\020\"\032\0020\003HÖ\001R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\016\020\017R\021\020\004\032\0020\003¢\006\b\n\000\032\004\b\020\020\017R\021\020\005\032\0020\003¢\006\b\n\000\032\004\b\021\020\017R\027\020\006\032\b\022\004\022\0020\b0\007¢\006\b\n\000\032\004\b\022\020\023R\021\020\t\032\0020\n¢\006\b\n\000\032\004\b\024\020\025R\021\020\013\032\0020\n¢\006\b\n\000\032\004\b\026\020\025¨\006$"}, d2 = {"Lno/futurehome/futurehome_app/widget/models/SiteM;", "", "id", "", "name", "address", "shortcuts", "", "Lno/futurehome/futurehome_app/widget/models/ShortcutM;", "hasHub", "", "hasOnlineHub", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZZ)V", "getId", "()Ljava/lang/String;", "getName", "getAddress", "getShortcuts", "()Ljava/util/List;", "getHasHub", "()Z", "getHasOnlineHub", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "Companion", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class SiteM {
  public static final Companion Companion = new Companion(null);
  
  private static final String TAG = "SiteM";
  
  private final String address;
  
  private final boolean hasHub;
  
  private final boolean hasOnlineHub;
  
  private final String id;
  
  private final String name;
  
  private final List<ShortcutM> shortcuts;
  
  public SiteM(String paramString1, String paramString2, String paramString3, List<ShortcutM> paramList, boolean paramBoolean1, boolean paramBoolean2) {
    this.id = paramString1;
    this.name = paramString2;
    this.address = paramString3;
    this.shortcuts = paramList;
    this.hasHub = paramBoolean1;
    this.hasOnlineHub = paramBoolean2;
  }
  
  public final String component1() {
    return this.id;
  }
  
  public final String component2() {
    return this.name;
  }
  
  public final String component3() {
    return this.address;
  }
  
  public final List<ShortcutM> component4() {
    return this.shortcuts;
  }
  
  public final boolean component5() {
    return this.hasHub;
  }
  
  public final boolean component6() {
    return this.hasOnlineHub;
  }
  
  public final SiteM copy(String paramString1, String paramString2, String paramString3, List<ShortcutM> paramList, boolean paramBoolean1, boolean paramBoolean2) {
    Intrinsics.checkNotNullParameter(paramString1, "id");
    Intrinsics.checkNotNullParameter(paramString2, "name");
    Intrinsics.checkNotNullParameter(paramString3, "address");
    Intrinsics.checkNotNullParameter(paramList, "shortcuts");
    return new SiteM(paramString1, paramString2, paramString3, paramList, paramBoolean1, paramBoolean2);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof SiteM))
      return false; 
    paramObject = paramObject;
    return !Intrinsics.areEqual(this.id, ((SiteM)paramObject).id) ? false : (!Intrinsics.areEqual(this.name, ((SiteM)paramObject).name) ? false : (!Intrinsics.areEqual(this.address, ((SiteM)paramObject).address) ? false : (!Intrinsics.areEqual(this.shortcuts, ((SiteM)paramObject).shortcuts) ? false : ((this.hasHub != ((SiteM)paramObject).hasHub) ? false : (!(this.hasOnlineHub != ((SiteM)paramObject).hasOnlineHub))))));
  }
  
  public final String getAddress() {
    return this.address;
  }
  
  public final boolean getHasHub() {
    return this.hasHub;
  }
  
  public final boolean getHasOnlineHub() {
    return this.hasOnlineHub;
  }
  
  public final String getId() {
    return this.id;
  }
  
  public final String getName() {
    return this.name;
  }
  
  public final List<ShortcutM> getShortcuts() {
    return this.shortcuts;
  }
  
  public int hashCode() {
    return ((((this.id.hashCode() * 31 + this.name.hashCode()) * 31 + this.address.hashCode()) * 31 + this.shortcuts.hashCode()) * 31 + Duration$$ExternalSyntheticBackport0.m(this.hasHub)) * 31 + Duration$$ExternalSyntheticBackport0.m(this.hasOnlineHub);
  }
  
  public String toString() {
    String str2 = this.id;
    String str3 = this.name;
    String str1 = this.address;
    List<ShortcutM> list = this.shortcuts;
    boolean bool1 = this.hasHub;
    boolean bool2 = this.hasOnlineHub;
    StringBuilder stringBuilder = new StringBuilder("SiteM(id=");
    stringBuilder.append(str2);
    stringBuilder.append(", name=");
    stringBuilder.append(str3);
    stringBuilder.append(", address=");
    stringBuilder.append(str1);
    stringBuilder.append(", shortcuts=");
    stringBuilder.append(list);
    stringBuilder.append(", hasHub=");
    stringBuilder.append(bool1);
    stringBuilder.append(", hasOnlineHub=");
    stringBuilder.append(bool2);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  @Metadata(d1 = {"\0004\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\b\003\030\0002\0020\001B\t\b\002¢\006\004\b\002\020\003J\020\020\006\032\004\030\0010\0072\006\020\b\032\0020\tJ\030\020\n\032\n\022\004\022\0020\f\030\0010\0132\006\020\b\032\0020\tH\002J\020\020\r\032\0020\0162\006\020\017\032\0020\020H\002R\016\020\004\032\0020\005XT¢\006\002\n\000¨\006\021"}, d2 = {"Lno/futurehome/futurehome_app/widget/models/SiteM$Companion;", "", "<init>", "()V", "TAG", "", "fromJson", "Lno/futurehome/futurehome_app/widget/models/SiteM;", "json", "Lorg/json/JSONObject;", "getShortcuts", "", "Lno/futurehome/futurehome_app/widget/models/ShortcutM;", "isSomeHubOnline", "", "gateways", "Lorg/json/JSONArray;", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
  public static final class Companion {
    private Companion() {}
    
    private final List<ShortcutM> getShortcuts(JSONObject param1JSONObject) {
      ArrayList<ShortcutM> arrayList = new ArrayList();
      JSONArray jSONArray = JsonOptsKt.optJsonArrayFromJson(param1JSONObject, "gateways");
      if (jSONArray == null)
        return arrayList; 
      int i = jSONArray.length();
      for (byte b = 0; b < i; b++) {
        Object object = jSONArray.get(b);
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type org.json.JSONObject");
        JSONArray jSONArray1 = JsonOptsKt.optJsonArrayFromJson((JSONObject)object, "applications");
        if (jSONArray1 != null) {
          int j = jSONArray1.length();
          for (byte b1 = 0; b1 < j; b1++) {
            object = jSONArray1.get(b1);
            Intrinsics.checkNotNull(object, "null cannot be cast to non-null type org.json.JSONObject");
            object = JsonOptsKt.optJsonFromJson((JSONObject)object, "config");
            if (object != null) {
              JSONArray jSONArray2 = JsonOptsKt.optJsonArrayFromJson((JSONObject)object, "shortcuts");
              if (jSONArray2 != null) {
                int k = jSONArray2.length();
                for (byte b2 = 0; b2 < k; b2++) {
                  object = jSONArray2.get(b2);
                  Intrinsics.checkNotNull(object, "null cannot be cast to non-null type org.json.JSONObject");
                  object = object;
                  Integer integer = JsonOptsKt.optIntFromJson((JSONObject)object, "id");
                  if (integer != null) {
                    int m = integer.intValue();
                    object = JsonOptsKt.optJsonFromJson((JSONObject)object, "client");
                    Integer integer1 = null;
                    if (object != null) {
                      object = JsonOptsKt.optStringFromJson((JSONObject)object, "name");
                    } else {
                      object = null;
                    } 
                    Iterator<ShortcutM> iterator = arrayList.iterator();
                    while (true) {
                      integer = integer1;
                      if (iterator.hasNext()) {
                        integer = (Integer)iterator.next();
                        if (((ShortcutM)integer).getId() == m)
                          break; 
                        continue;
                      } 
                      break;
                    } 
                    if (integer == null)
                      arrayList.add(new ShortcutM(m, (String)object)); 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      return arrayList;
    }
    
    private final boolean isSomeHubOnline(JSONArray param1JSONArray) {
      int i = param1JSONArray.length();
      byte b = 0;
      boolean bool = false;
      while (b < i) {
        Object object = param1JSONArray.get(b);
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type org.json.JSONObject");
        if (Intrinsics.areEqual(JsonOptsKt.optBooleanFromJson((JSONObject)object, "online"), Boolean.valueOf(true)))
          bool = true; 
        b++;
      } 
      return bool;
    }
    
    public final SiteM fromJson(JSONObject param1JSONObject) {
      String str1;
      boolean bool;
      Intrinsics.checkNotNullParameter(param1JSONObject, "json");
      String str6 = JsonOptsKt.optStringFromJson(param1JSONObject, "id");
      if (str6 == null) {
        Logger.Companion.e("SiteM", "fromJson, id is null");
        return null;
      } 
      String str5 = JsonOptsKt.optStringFromJson(param1JSONObject, "name");
      if (str5 == null) {
        Logger.Companion.e("SiteM", "fromJson, name is null");
        return null;
      } 
      JSONObject jSONObject = JsonOptsKt.optJsonFromJson(param1JSONObject, "address");
      if (jSONObject == null) {
        Logger.Companion.e("SiteM", "fromJson, addressJson is null");
        return null;
      } 
      String str3 = JsonOptsKt.optStringFromJson(jSONObject, "address");
      String str2 = str3;
      if (str3 == null)
        str2 = ""; 
      String str4 = JsonOptsKt.optStringFromJson(jSONObject, "city");
      str3 = str4;
      if (str4 == null)
        str3 = ""; 
      JSONArray jSONArray = JsonOptsKt.optJsonArrayFromJson(param1JSONObject, "gateways");
      if (jSONArray == null) {
        Logger.Companion.e("SiteM", "fromJson, gateways are null");
        return null;
      } 
      List<ShortcutM> list = getShortcuts(param1JSONObject);
      if (list == null) {
        Logger.Companion.e("SiteM", "fromJson, shortcuts are null");
        return null;
      } 
      if (!Intrinsics.areEqual(str3, "") && !Intrinsics.areEqual(str2, "")) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(", ");
        stringBuilder.append(str3);
        str1 = stringBuilder.toString();
      } else if (!Intrinsics.areEqual(str3, "")) {
        str1 = str3;
      } else if (!Intrinsics.areEqual(str2, "")) {
        str1 = str2;
      } else {
        str1 = "";
      } 
      if (jSONArray.length() > 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return new SiteM(str6, str5, str1, list, bool, isSomeHubOnline(jSONArray));
    }
  }
}
