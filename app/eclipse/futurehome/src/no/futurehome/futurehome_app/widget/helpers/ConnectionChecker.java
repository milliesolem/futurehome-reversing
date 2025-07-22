package no.futurehome.futurehome_app.widget.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\030\0002\0020\001B\017\022\006\020\002\032\0020\003¢\006\004\b\004\020\005J\006\020\006\032\0020\007R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\b"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/ConnectionChecker;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "isConnected", "", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ConnectionChecker {
  private final Context context;
  
  public ConnectionChecker(Context paramContext) {
    this.context = paramContext;
  }
  
  public final boolean isConnected() {
    Object object = this.context.getSystemService("connectivity");
    Intrinsics.checkNotNull(object, "null cannot be cast to non-null type android.net.ConnectivityManager");
    object = ((ConnectivityManager)object).getActiveNetworkInfo();
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (object != null) {
      bool1 = bool2;
      if (object.isConnected() == true)
        bool1 = true; 
    } 
    return bool1;
  }
}
