package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.WidgetData

public class NoUserRow(context: Context, widgetData: WidgetData) : Row {
   private final val context: Context
   private final val widgetData: WidgetData

   init {
      this.context = var1;
      this.widgetData = var2;
   }

   private fun getViewsForBrightness(): RemoteViews {
      val var1: RemoteViews;
      if (this.widgetData.isDark()) {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492960);
      } else {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492961);
      }

      return var1;
   }

   private fun setupLanguage(views: RemoteViews) {
      val var2: Int = NoUserRow.WhenMappings.$EnumSwitchMapping$0[this.widgetData.getLanguage().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 != 5) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var1.setTextViewText(2131296521, this.context.getString(2131689620));
               } else {
                  var1.setTextViewText(2131296521, this.context.getString(2131689573));
               }
            } else {
               var1.setTextViewText(2131296521, this.context.getString(2131689535));
            }
         } else {
            var1.setTextViewText(2131296521, this.context.getString(2131689599));
         }
      } else {
         var1.setTextViewText(2131296521, this.context.getString(2131689552));
      }
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      val var3: RemoteViews = this.getViewsForBrightness();
      val var2: Intent = new Intent();
      var2.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionMoveToAuth");
      var3.setOnClickFillInIntent(2131296521, var2);
      this.setupLanguage(var3);
      return var3;
   }
}
