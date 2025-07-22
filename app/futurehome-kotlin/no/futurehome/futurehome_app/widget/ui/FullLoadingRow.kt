package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.helpers.BrightnessChecker
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.WidgetData

public class FullLoadingRow(context: Context, widgetData: WidgetData) : Row {
   private final val context: Context
   private final val widgetData: WidgetData

   init {
      this.context = var1;
      this.widgetData = var2;
   }

   private fun getViewsForBrightness(): RemoteViews {
      val var1: RemoteViews;
      if (BrightnessChecker.Companion.getInstance().isDark()) {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492940);
      } else {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492941);
      }

      return var1;
   }

   private fun setupLanguage(views: RemoteViews) {
      val var2: Int = FullLoadingRow.WhenMappings.$EnumSwitchMapping$0[this.widgetData.getLanguage().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 != 5) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var1.setTextViewText(2131296508, this.context.getString(2131689619));
               } else {
                  var1.setTextViewText(2131296508, this.context.getString(2131689572));
               }
            } else {
               var1.setTextViewText(2131296508, this.context.getString(2131689534));
            }
         } else {
            var1.setTextViewText(2131296508, this.context.getString(2131689598));
         }
      } else {
         var1.setTextViewText(2131296508, this.context.getString(2131689551));
      }
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      val var2: RemoteViews = this.getViewsForBrightness();
      this.setupLanguage(var2);
      return var2;
   }
}
