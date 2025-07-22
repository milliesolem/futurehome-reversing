package no.futurehome.futurehome_app.widget.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import no.futurehome.futurehome_app.widget.models.Language;
import no.futurehome.futurehome_app.widget.models.Mode;
import no.futurehome.futurehome_app.widget.models.Row;
import no.futurehome.futurehome_app.widget.models.WidgetData;

@Metadata(d1 = {"\0004\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\002\030\0002\0020\001B\027\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005¢\006\004\b\006\020\007J\020\020\b\032\0020\t2\006\020\n\032\0020\013H\026J\020\020\f\032\0020\t2\006\020\r\032\0020\016H\002J\020\020\017\032\0020\0202\006\020\021\032\0020\tH\002R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\022"}, d2 = {"Lno/futurehome/futurehome_app/widget/ui/ModesRow;", "Lno/futurehome/futurehome_app/widget/models/Row;", "context", "Landroid/content/Context;", "widgetData", "Lno/futurehome/futurehome_app/widget/models/WidgetData;", "<init>", "(Landroid/content/Context;Lno/futurehome/futurehome_app/widget/models/WidgetData;)V", "getRemoteViews", "Landroid/widget/RemoteViews;", "rowIndex", "", "getViewsForBrightness", "currentMode", "Lno/futurehome/futurehome_app/widget/models/Mode;", "setupLanguage", "", "views", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class ModesRow implements Row {
  private final Context context;
  
  private final WidgetData widgetData;
  
  public ModesRow(Context paramContext, WidgetData paramWidgetData) {
    this.context = paramContext;
    this.widgetData = paramWidgetData;
  }
  
  private final RemoteViews getViewsForBrightness(Mode paramMode) {
    RemoteViews remoteViews;
    if (this.widgetData.isDark()) {
      int i = WhenMappings.$EnumSwitchMapping$0[paramMode.ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i == 4) {
              remoteViews = new RemoteViews(this.context.getPackageName(), 2131492952);
            } else {
              throw new NoWhenBranchMatchedException();
            } 
          } else {
            remoteViews = new RemoteViews(this.context.getPackageName(), 2131492950);
          } 
        } else {
          remoteViews = new RemoteViews(this.context.getPackageName(), 2131492948);
        } 
      } else {
        remoteViews = new RemoteViews(this.context.getPackageName(), 2131492946);
      } 
    } else {
      int i = WhenMappings.$EnumSwitchMapping$0[remoteViews.ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i == 4) {
              remoteViews = new RemoteViews(this.context.getPackageName(), 2131492953);
            } else {
              throw new NoWhenBranchMatchedException();
            } 
          } else {
            remoteViews = new RemoteViews(this.context.getPackageName(), 2131492951);
          } 
        } else {
          remoteViews = new RemoteViews(this.context.getPackageName(), 2131492949);
        } 
      } else {
        remoteViews = new RemoteViews(this.context.getPackageName(), 2131492947);
      } 
    } 
    return remoteViews;
  }
  
  private final void setupLanguage(RemoteViews paramRemoteViews) {
    Language language = this.widgetData.getLanguage();
    int i = WhenMappings.$EnumSwitchMapping$1[language.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i == 5) {
              paramRemoteViews.setContentDescription(2131296512, this.context.getString(2131689618));
              paramRemoteViews.setContentDescription(2131296511, this.context.getString(2131689613));
              paramRemoteViews.setContentDescription(2131296513, this.context.getString(2131689621));
              paramRemoteViews.setContentDescription(2131296514, this.context.getString(2131689617));
            } else {
              throw new NoWhenBranchMatchedException();
            } 
          } else {
            paramRemoteViews.setContentDescription(2131296512, this.context.getString(2131689571));
            paramRemoteViews.setContentDescription(2131296511, this.context.getString(2131689566));
            paramRemoteViews.setContentDescription(2131296513, this.context.getString(2131689574));
            paramRemoteViews.setContentDescription(2131296514, this.context.getString(2131689570));
          } 
        } else {
          paramRemoteViews.setContentDescription(2131296512, this.context.getString(2131689533));
          paramRemoteViews.setContentDescription(2131296511, this.context.getString(2131689528));
          paramRemoteViews.setContentDescription(2131296513, this.context.getString(2131689536));
          paramRemoteViews.setContentDescription(2131296514, this.context.getString(2131689532));
        } 
      } else {
        paramRemoteViews.setContentDescription(2131296512, this.context.getString(2131689597));
        paramRemoteViews.setContentDescription(2131296511, this.context.getString(2131689592));
        paramRemoteViews.setContentDescription(2131296513, this.context.getString(2131689600));
        paramRemoteViews.setContentDescription(2131296514, this.context.getString(2131689596));
      } 
    } else {
      paramRemoteViews.setContentDescription(2131296512, this.context.getString(2131689550));
      paramRemoteViews.setContentDescription(2131296511, this.context.getString(2131689545));
      paramRemoteViews.setContentDescription(2131296513, this.context.getString(2131689553));
      paramRemoteViews.setContentDescription(2131296514, this.context.getString(2131689549));
    } 
  }
  
  public RemoteViews getRemoteViews(int paramInt) {
    Mode mode = this.widgetData.getActiveMode();
    Intrinsics.checkNotNull(mode);
    RemoteViews remoteViews = getViewsForBrightness(mode);
    Intent intent = new Intent();
    intent.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeHome");
    remoteViews.setOnClickFillInIntent(2131296512, intent);
    intent = new Intent();
    intent.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeAway");
    remoteViews.setOnClickFillInIntent(2131296511, intent);
    intent = new Intent();
    intent.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeSleep");
    remoteViews.setOnClickFillInIntent(2131296513, intent);
    intent = new Intent();
    intent.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeVacation");
    remoteViews.setOnClickFillInIntent(2131296514, intent);
    setupLanguage(remoteViews);
    return remoteViews;
  }
}
