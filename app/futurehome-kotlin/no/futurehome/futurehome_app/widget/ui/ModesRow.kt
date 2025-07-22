package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.models.Mode
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.WidgetData

public class ModesRow(context: Context, widgetData: WidgetData) : Row {
   private final val context: Context
   private final val widgetData: WidgetData

   init {
      this.context = var1;
      this.widgetData = var2;
   }

   private fun getViewsForBrightness(currentMode: Mode): RemoteViews {
      val var3: RemoteViews;
      if (this.widgetData.isDark()) {
         val var2: Int = ModesRow.WhenMappings.$EnumSwitchMapping$0[var1.ordinal()];
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 3) {
                  if (var2 != 4) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var3 = new RemoteViews(this.context.getPackageName(), 2131492952);
               } else {
                  var3 = new RemoteViews(this.context.getPackageName(), 2131492950);
               }
            } else {
               var3 = new RemoteViews(this.context.getPackageName(), 2131492948);
            }
         } else {
            var3 = new RemoteViews(this.context.getPackageName(), 2131492946);
         }
      } else {
         val var4: Int = ModesRow.WhenMappings.$EnumSwitchMapping$0[var1.ordinal()];
         if (var4 != 1) {
            if (var4 != 2) {
               if (var4 != 3) {
                  if (var4 != 4) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var3 = new RemoteViews(this.context.getPackageName(), 2131492953);
               } else {
                  var3 = new RemoteViews(this.context.getPackageName(), 2131492951);
               }
            } else {
               var3 = new RemoteViews(this.context.getPackageName(), 2131492949);
            }
         } else {
            var3 = new RemoteViews(this.context.getPackageName(), 2131492947);
         }
      }

      return var3;
   }

   private fun setupLanguage(views: RemoteViews) {
      val var2: Int = ModesRow.WhenMappings.$EnumSwitchMapping$1[this.widgetData.getLanguage().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 != 5) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var1.setContentDescription(2131296512, this.context.getString(2131689618));
                  var1.setContentDescription(2131296511, this.context.getString(2131689613));
                  var1.setContentDescription(2131296513, this.context.getString(2131689621));
                  var1.setContentDescription(2131296514, this.context.getString(2131689617));
               } else {
                  var1.setContentDescription(2131296512, this.context.getString(2131689571));
                  var1.setContentDescription(2131296511, this.context.getString(2131689566));
                  var1.setContentDescription(2131296513, this.context.getString(2131689574));
                  var1.setContentDescription(2131296514, this.context.getString(2131689570));
               }
            } else {
               var1.setContentDescription(2131296512, this.context.getString(2131689533));
               var1.setContentDescription(2131296511, this.context.getString(2131689528));
               var1.setContentDescription(2131296513, this.context.getString(2131689536));
               var1.setContentDescription(2131296514, this.context.getString(2131689532));
            }
         } else {
            var1.setContentDescription(2131296512, this.context.getString(2131689597));
            var1.setContentDescription(2131296511, this.context.getString(2131689592));
            var1.setContentDescription(2131296513, this.context.getString(2131689600));
            var1.setContentDescription(2131296514, this.context.getString(2131689596));
         }
      } else {
         var1.setContentDescription(2131296512, this.context.getString(2131689550));
         var1.setContentDescription(2131296511, this.context.getString(2131689545));
         var1.setContentDescription(2131296513, this.context.getString(2131689553));
         var1.setContentDescription(2131296514, this.context.getString(2131689549));
      }
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      val var2: Mode = this.widgetData.getActiveMode();
      val var4: RemoteViews = this.getViewsForBrightness(var2);
      var var3: Intent = new Intent();
      var3.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeHome");
      var4.setOnClickFillInIntent(2131296512, var3);
      var3 = new Intent();
      var3.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeAway");
      var4.setOnClickFillInIntent(2131296511, var3);
      var3 = new Intent();
      var3.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeSleep");
      var4.setOnClickFillInIntent(2131296513, var3);
      var3 = new Intent();
      var3.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetModeVacation");
      var4.setOnClickFillInIntent(2131296514, var3);
      this.setupLanguage(var4);
      return var4;
   }
}
