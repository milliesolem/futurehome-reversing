package no.futurehome.futurehome_app.widget.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import no.futurehome.futurehome_app.widget.models.Language;
import no.futurehome.futurehome_app.widget.models.Row;
import no.futurehome.futurehome_app.widget.models.WidgetData;

@Metadata(d1 = {"\000.\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\002\b\002\030\0002\0020\001B\027\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\004\b\006\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\b\020\f\032\0020\tH\002J\020\020\r\032\0020\0162\006\020\017\032\0020\tH\002R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\020"}, d2 = {"Lno/futurehome/futurehome_app/widget/ui/CriticalErrorRow;", "Lno/futurehome/futurehome_app/widget/models/Row;", "context", "Landroid/content/Context;", "widgetData", "Lno/futurehome/futurehome_app/widget/models/WidgetData;", "<init>", "(Landroid/content/Context;Lno/futurehome/futurehome_app/widget/models/WidgetData;)V", "getRemoteViews", "Landroid/widget/RemoteViews;", "rowIndex", "", "getViewsForBrightness", "setupLanguage", "", "views", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class CriticalErrorRow implements Row {
  private final Context context;
  
  private final WidgetData widgetData;
  
  public CriticalErrorRow(Context paramContext, WidgetData paramWidgetData) {
    this.context = paramContext;
    this.widgetData = paramWidgetData;
  }
  
  private final RemoteViews getViewsForBrightness() {
    RemoteViews remoteViews;
    if (this.widgetData.isDark()) {
      remoteViews = new RemoteViews(this.context.getPackageName(), 2131492936);
    } else {
      remoteViews = new RemoteViews(this.context.getPackageName(), 2131492937);
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
              paramRemoteViews.setTextViewText(2131296504, this.context.getString(2131689615));
              paramRemoteViews.setTextViewText(2131296503, this.context.getString(2131689624));
            } else {
              throw new NoWhenBranchMatchedException();
            } 
          } else {
            paramRemoteViews.setTextViewText(2131296504, this.context.getString(2131689568));
            paramRemoteViews.setTextViewText(2131296503, this.context.getString(2131689577));
          } 
        } else {
          paramRemoteViews.setTextViewText(2131296504, this.context.getString(2131689530));
          paramRemoteViews.setTextViewText(2131296503, this.context.getString(2131689539));
        } 
      } else {
        paramRemoteViews.setTextViewText(2131296504, this.context.getString(2131689594));
        paramRemoteViews.setTextViewText(2131296503, this.context.getString(2131689603));
      } 
    } else {
      paramRemoteViews.setTextViewText(2131296504, this.context.getString(2131689547));
      paramRemoteViews.setTextViewText(2131296503, this.context.getString(2131689556));
    } 
  }
  
  public RemoteViews getRemoteViews(int paramInt) {
    RemoteViews remoteViews = getViewsForBrightness();
    Intent intent = new Intent();
    intent.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionOpenApp");
    remoteViews.setOnClickFillInIntent(2131296503, intent);
    setupLanguage(remoteViews);
    return remoteViews;
  }
}
