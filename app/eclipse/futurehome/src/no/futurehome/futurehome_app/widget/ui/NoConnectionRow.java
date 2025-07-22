package no.futurehome.futurehome_app.widget.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import no.futurehome.futurehome_app.widget.models.Language;
import no.futurehome.futurehome_app.widget.models.Row;
import no.futurehome.futurehome_app.widget.models.WidgetData;

@Metadata(d1 = {"\000.\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\002\b\002\030\0002\0020\001B\027\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\004\b\006\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\b\020\f\032\0020\tH\002J\020\020\r\032\0020\0162\006\020\017\032\0020\tH\002R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\020"}, d2 = {"Lno/futurehome/futurehome_app/widget/ui/NoConnectionRow;", "Lno/futurehome/futurehome_app/widget/models/Row;", "context", "Landroid/content/Context;", "widgetData", "Lno/futurehome/futurehome_app/widget/models/WidgetData;", "<init>", "(Landroid/content/Context;Lno/futurehome/futurehome_app/widget/models/WidgetData;)V", "getRemoteViews", "Landroid/widget/RemoteViews;", "rowIndex", "", "getViewsForBrightness", "setupLanguage", "", "views", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class NoConnectionRow implements Row {
  private final Context context;
  
  private final WidgetData widgetData;
  
  public NoConnectionRow(Context paramContext, WidgetData paramWidgetData) {
    this.context = paramContext;
    this.widgetData = paramWidgetData;
  }
  
  private final RemoteViews getViewsForBrightness() {
    RemoteViews remoteViews;
    if (this.widgetData.isDark()) {
      remoteViews = new RemoteViews(this.context.getPackageName(), 2131492954);
    } else {
      remoteViews = new RemoteViews(this.context.getPackageName(), 2131492955);
    } 
    return remoteViews;
  }
  
  private final void setupLanguage(RemoteViews paramRemoteViews) {
    Language language = this.widgetData.getLanguage();
    int i = WhenMappings.$EnumSwitchMapping$0[language.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i == 5) {
              paramRemoteViews.setTextViewText(2131296516, this.context.getString(2131689622));
              paramRemoteViews.setTextViewText(2131296515, this.context.getString(2131689627));
            } else {
              throw new NoWhenBranchMatchedException();
            } 
          } else {
            paramRemoteViews.setTextViewText(2131296516, this.context.getString(2131689575));
            paramRemoteViews.setTextViewText(2131296515, this.context.getString(2131689580));
          } 
        } else {
          paramRemoteViews.setTextViewText(2131296516, this.context.getString(2131689537));
          paramRemoteViews.setTextViewText(2131296515, this.context.getString(2131689542));
        } 
      } else {
        paramRemoteViews.setTextViewText(2131296516, this.context.getString(2131689601));
        paramRemoteViews.setTextViewText(2131296515, this.context.getString(2131689606));
      } 
    } else {
      paramRemoteViews.setTextViewText(2131296516, this.context.getString(2131689554));
      paramRemoteViews.setTextViewText(2131296515, this.context.getString(2131689559));
    } 
  }
  
  public RemoteViews getRemoteViews(int paramInt) {
    RemoteViews remoteViews = getViewsForBrightness();
    Intent intent = new Intent();
    intent.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionUpdate");
    remoteViews.setOnClickFillInIntent(2131296515, intent);
    setupLanguage(remoteViews);
    return remoteViews;
  }
}
