package no.futurehome.futurehome_app.widget.helpers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\000\f\n\002\030\002\n\002\020\000\n\002\b\004\030\000 \0042\0020\001:\001\004B\007¢\006\004\b\002\020\003¨\006\005"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/Logger;", "", "<init>", "()V", "Companion", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class Logger {
  public static final Companion Companion = new Companion(null);
  
  @Metadata(d1 = {"\000\032\n\002\030\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\020\016\n\002\b\003\b\003\030\0002\0020\001B\t\b\002¢\006\004\b\002\020\003J\026\020\004\032\0020\0052\006\020\006\032\0020\0072\006\020\b\032\0020\007J\026\020\t\032\0020\0052\006\020\006\032\0020\0072\006\020\b\032\0020\007¨\006\n"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/Logger$Companion;", "", "<init>", "()V", "d", "", "tag", "", "msg", "e", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
  public static final class Companion {
    private Companion() {}
    
    public final void d(String param1String1, String param1String2) {
      Intrinsics.checkNotNullParameter(param1String1, "tag");
      Intrinsics.checkNotNullParameter(param1String2, "msg");
    }
    
    public final void e(String param1String1, String param1String2) {
      Intrinsics.checkNotNullParameter(param1String1, "tag");
      Intrinsics.checkNotNullParameter(param1String2, "msg");
    }
  }
}
