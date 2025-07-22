package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.helpers.BrightnessChecker
import no.futurehome.futurehome_app.widget.helpers.PrefsHelper
import no.futurehome.futurehome_app.widget.models.Row

public class LoadingRow(context: Context) : Row {
   private final val context: Context

   init {
      this.context = var1;
   }

   private fun getViewsForBrightness(): RemoteViews {
      val var1: RemoteViews;
      if (BrightnessChecker.Companion.getInstance().isDark()) {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492942);
      } else {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492943);
      }

      return var1;
   }

   private fun setupLanguage(views: RemoteViews) {
      val var2: Int = LoadingRow.WhenMappings.$EnumSwitchMapping$0[new PrefsHelper(this.context).getLanguage().ordinal()];
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
