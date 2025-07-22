package no.futurehome.futurehome_app.widget.models;

import android.content.Context;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\000.\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\b\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\013\n\002\b\004\b\b\030\0002\0020\001B\031\022\006\020\002\032\0020\003\022\b\020\004\032\004\030\0010\005¢\006\004\b\006\020\007J\026\020\f\032\0020\0052\006\020\r\032\0020\0162\006\020\017\032\0020\020J\t\020\021\032\0020\003HÆ\003J\013\020\022\032\004\030\0010\005HÆ\003J\037\020\023\032\0020\0002\b\b\002\020\002\032\0020\0032\n\b\002\020\004\032\004\030\0010\005HÆ\001J\023\020\024\032\0020\0252\b\020\026\032\004\030\0010\001HÖ\003J\t\020\027\032\0020\003HÖ\001J\t\020\030\032\0020\005HÖ\001R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\b\020\tR\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\n\020\013¨\006\031"}, d2 = {"Lno/futurehome/futurehome_app/widget/models/ShortcutM;", "", "id", "", "name", "", "<init>", "(ILjava/lang/String;)V", "getId", "()I", "getName", "()Ljava/lang/String;", "getNameForLanguage", "context", "Landroid/content/Context;", "language", "Lno/futurehome/futurehome_app/widget/models/Language;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ShortcutM {
  private final int id;
  
  private final String name;
  
  public ShortcutM(int paramInt, String paramString) {
    this.id = paramInt;
    this.name = paramString;
  }
  
  public final int component1() {
    return this.id;
  }
  
  public final String component2() {
    return this.name;
  }
  
  public final ShortcutM copy(int paramInt, String paramString) {
    return new ShortcutM(paramInt, paramString);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof ShortcutM))
      return false; 
    paramObject = paramObject;
    return (this.id != ((ShortcutM)paramObject).id) ? false : (!!Intrinsics.areEqual(this.name, ((ShortcutM)paramObject).name));
  }
  
  public final int getId() {
    return this.id;
  }
  
  public final String getName() {
    return this.name;
  }
  
  public final String getNameForLanguage(Context paramContext, Language paramLanguage) {
    String str1;
    Intrinsics.checkNotNullParameter(paramContext, "context");
    Intrinsics.checkNotNullParameter(paramLanguage, "language");
    String str2 = this.name;
    if (str2 != null)
      return str2; 
    int i = WhenMappings.$EnumSwitchMapping$0[paramLanguage.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i == 5) {
              String str = paramContext.getString(2131689625);
              i = this.id;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(str);
              stringBuilder.append(" ");
              stringBuilder.append(i);
              str1 = stringBuilder.toString();
            } else {
              throw new NoWhenBranchMatchedException();
            } 
          } else {
            String str = str1.getString(2131689540);
            i = this.id;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(" ");
            stringBuilder.append(i);
            str1 = stringBuilder.toString();
          } 
        } else {
          str1 = str1.getString(2131689604);
          i = this.id;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str1);
          stringBuilder.append(" ");
          stringBuilder.append(i);
          str1 = stringBuilder.toString();
        } 
      } else {
        String str = str1.getString(2131689578);
        i = this.id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" ");
        stringBuilder.append(i);
        str1 = stringBuilder.toString();
      } 
    } else {
      str1 = str1.getString(2131689557);
      i = this.id;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append(" ");
      stringBuilder.append(i);
      str1 = stringBuilder.toString();
    } 
    return str1;
  }
  
  public int hashCode() {
    int i;
    int j = this.id;
    String str = this.name;
    if (str == null) {
      i = 0;
    } else {
      i = str.hashCode();
    } 
    return j * 31 + i;
  }
  
  public String toString() {
    int i = this.id;
    String str = this.name;
    StringBuilder stringBuilder = new StringBuilder("ShortcutM(id=");
    stringBuilder.append(i);
    stringBuilder.append(", name=");
    stringBuilder.append(str);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}
