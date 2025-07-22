package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.WidgetData

public class CriticalErrorRow(context: Context, widgetData: WidgetData) : Row {
   private final val context: Context
   private final val widgetData: WidgetData

   init {
      this.context = var1;
      this.widgetData = var2;
   }

   private fun getViewsForBrightness(): RemoteViews {
      val var1: RemoteViews;
      if (this.widgetData.isDark()) {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492936);
      } else {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492937);
      }

      return var1;
   }

   private fun setupLanguage(views: RemoteViews) {
      val var2: Int = CriticalErrorRow.WhenMappings.$EnumSwitchMapping$0[this.widgetData.getLanguage().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 != 5) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var1.setTextViewText(2131296504, this.context.getString(2131689615));
                  var1.setTextViewText(2131296503, this.context.getString(2131689624));
               } else {
                  var1.setTextViewText(2131296504, this.context.getString(2131689568));
                  var1.setTextViewText(2131296503, this.context.getString(2131689577));
               }
            } else {
               var1.setTextViewText(2131296504, this.context.getString(2131689530));
               var1.setTextViewText(2131296503, this.context.getString(2131689539));
            }
         } else {
            var1.setTextViewText(2131296504, this.context.getString(2131689594));
            var1.setTextViewText(2131296503, this.context.getString(2131689603));
         }
      } else {
         var1.setTextViewText(2131296504, this.context.getString(2131689547));
         var1.setTextViewText(2131296503, this.context.getString(2131689556));
      }
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      val var2: RemoteViews = this.getViewsForBrightness();
      val var3: Intent = new Intent();
      var3.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionOpenApp");
      var2.setOnClickFillInIntent(2131296503, var3);
      this.setupLanguage(var2);
      return var2;
   }
}
