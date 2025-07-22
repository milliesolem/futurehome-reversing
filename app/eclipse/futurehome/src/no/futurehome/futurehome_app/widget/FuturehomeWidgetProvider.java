package no.futurehome.futurehome_app.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\000,\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\025\n\002\b\002\n\002\030\002\n\000\030\0002\0020\001B\007¢\006\004\b\002\020\003J \020\004\032\0020\0052\006\020\006\032\0020\0072\006\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\030\020\f\032\0020\0052\006\020\006\032\0020\0072\006\020\r\032\0020\016H\026¨\006\017"}, d2 = {"Lno/futurehome/futurehome_app/widget/FuturehomeWidgetProvider;", "Landroid/appwidget/AppWidgetProvider;", "<init>", "()V", "onUpdate", "", "context", "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "onReceive", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class FuturehomeWidgetProvider extends AppWidgetProvider {
  public void onReceive(Context paramContext, Intent paramIntent) {
    Intrinsics.checkNotNullParameter(paramContext, "context");
    Intrinsics.checkNotNullParameter(paramIntent, "intent");
    if (Intrinsics.areEqual(paramIntent.getAction(), "futurehome.widget.Action"))
      (new WidgetActionHandler(paramContext)).handle(paramIntent); 
    super.onReceive(paramContext, paramIntent);
  }
  
  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfint) {
    Intrinsics.checkNotNullParameter(paramContext, "context");
    Intrinsics.checkNotNullParameter(paramAppWidgetManager, "appWidgetManager");
    Intrinsics.checkNotNullParameter(paramArrayOfint, "appWidgetIds");
    (new WidgetUpdater(paramContext)).update(true);
  }
}
