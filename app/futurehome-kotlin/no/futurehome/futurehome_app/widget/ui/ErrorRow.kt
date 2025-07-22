package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.WidgetData

public class ErrorRow(context: Context, widgetData: WidgetData) : Row {
   private final val context: Context
   private final val widgetData: WidgetData

   init {
      this.context = var1;
      this.widgetData = var2;
   }

   private fun getViewsForBrightness(): RemoteViews {
      val var1: RemoteViews;
      if (this.widgetData.isDark()) {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492938);
      } else {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492939);
      }

      return var1;
   }

   private fun setupLanguage(views: RemoteViews) {
      val var2: Int = ErrorRow.WhenMappings.$EnumSwitchMapping$0[this.widgetData.getLanguage().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 != 5) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var1.setTextViewText(2131296506, this.context.getString(2131689616));
                  var1.setTextViewText(2131296505, this.context.getString(2131689627));
               } else {
                  var1.setTextViewText(2131296506, this.context.getString(2131689569));
                  var1.setTextViewText(2131296505, this.context.getString(2131689580));
               }
            } else {
               var1.setTextViewText(2131296506, this.context.getString(2131689531));
               var1.setTextViewText(2131296505, this.context.getString(2131689542));
            }
         } else {
            var1.setTextViewText(2131296506, this.context.getString(2131689595));
            var1.setTextViewText(2131296505, this.context.getString(2131689606));
         }
      } else {
         var1.setTextViewText(2131296506, this.context.getString(2131689548));
         var1.setTextViewText(2131296505, this.context.getString(2131689559));
      }
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      val var2: RemoteViews = this.getViewsForBrightness();
      val var3: Intent = new Intent();
      var3.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionUpdate");
      var2.setOnClickFillInIntent(2131296505, var3);
      this.setupLanguage(var2);
      return var2;
   }
}
