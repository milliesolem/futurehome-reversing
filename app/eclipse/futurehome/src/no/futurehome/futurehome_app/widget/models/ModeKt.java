package no.futurehome.futurehome_app.widget.models;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\000\016\n\000\n\002\020\016\n\000\n\002\030\002\n\000\032\016\020\000\032\0020\0012\006\020\002\032\0020\003¨\006\004"}, d2 = {"modeToString", "", "mode", "Lno/futurehome/futurehome_app/widget/models/Mode;", "app_release"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class ModeKt {
  public static final String modeToString(Mode paramMode) {
    String str;
    Intrinsics.checkNotNullParameter(paramMode, "mode");
    int i = WhenMappings.$EnumSwitchMapping$0[paramMode.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i == 4) {
            str = "vacation";
          } else {
            throw new NoWhenBranchMatchedException();
          } 
        } else {
          str = "sleep";
        } 
      } else {
        str = "away";
      } 
    } else {
      str = "home";
    } 
    return str;
  }
}
