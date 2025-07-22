package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.util.Calendar
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.SiteM
import no.futurehome.futurehome_app.widget.models.WidgetData
import no.futurehome.futurehome_app.widget.models.WidgetState

public class TopRow(context: Context, widgetData: WidgetData, widgetState: WidgetState) : Row {
   private final val context: Context
   private final val widgetData: WidgetData
   private final val widgetState: WidgetState

   init {
      this.context = var1;
      this.widgetData = var2;
      this.widgetState = var3;
   }

   private fun getEmpty(): RemoteViews {
      val var1: RemoteViews;
      if (this.widgetData.isDark()) {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492982);
      } else {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492983);
      }

      val var2: Intent = new Intent();
      var2.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionUpdate");
      var1.setOnClickFillInIntent(2131296530, var2);
      val var3: Calendar = Calendar.getInstance();
      val var5: java.lang.String = StringsKt.padStart(java.lang.String.valueOf(var3.get(11)), 2, '0');
      val var4: java.lang.String = StringsKt.padStart(java.lang.String.valueOf(var3.get(12)), 2, '0');
      val var6: StringBuilder = new StringBuilder(" ");
      var6.append(var5);
      var6.append(":");
      var6.append(var4);
      var1.setTextViewText(2131296530, var6.toString());
      this.setupLanguageInEmpty(var1);
      return var1;
   }

   private fun getEmptyWithLogo(): RemoteViews {
      val var1: RemoteViews = this.getEmpty();
      var1.setViewVisibility(2131296529, 0);
      return var1;
   }

   private fun getEmptyWithoutLogo(): RemoteViews {
      val var1: RemoteViews = this.getEmpty();
      var1.setViewVisibility(2131296529, 4);
      return var1;
   }

   private fun getRegular(currentSite: SiteM?, showSites: Boolean): RemoteViews {
      val var3: RemoteViews;
      if (this.widgetData.isDark()) {
         var3 = new RemoteViews(this.context.getPackageName(), 2131492981);
      } else {
         var3 = new RemoteViews(this.context.getPackageName(), 2131492984);
      }

      if (var2) {
         val var4: Intent = new Intent();
         var4.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionHideSites");
         var3.setOnClickFillInIntent(2131296528, var4);
      } else {
         val var8: Intent = new Intent();
         var8.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionShowSites");
         var3.setOnClickFillInIntent(2131296528, var8);
      }

      label20: {
         val var9: Intent = new Intent();
         var9.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionUpdate");
         var3.setOnClickFillInIntent(2131296530, var9);
         val var5: Calendar = Calendar.getInstance();
         val var10: java.lang.String = StringsKt.padStart(java.lang.String.valueOf(var5.get(11)), 2, '0');
         val var12: java.lang.String = StringsKt.padStart(java.lang.String.valueOf(var5.get(12)), 2, '0');
         val var6: StringBuilder = new StringBuilder(" ");
         var6.append(var10);
         var6.append(":");
         var6.append(var12);
         var3.setTextViewText(2131296530, var6.toString());
         if (var1 != null) {
            val var11: java.lang.String = var1.getName();
            var7 = var11;
            if (var11 != null) {
               break label20;
            }
         }

         var7 = "...";
      }

      var3.setTextViewText(2131296524, var7);
      this.setupLanguageInRegular(var3);
      return var3;
   }

   private fun setupLanguageInEmpty(views: RemoteViews) {
      val var2: Int = TopRow.WhenMappings.$EnumSwitchMapping$1[this.widgetData.getLanguage().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 != 5) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var1.setContentDescription(2131296530, this.context.getString(2131689628));
               } else {
                  var1.setContentDescription(2131296530, this.context.getString(2131689581));
               }
            } else {
               var1.setContentDescription(2131296530, this.context.getString(2131689543));
            }
         } else {
            var1.setContentDescription(2131296530, this.context.getString(2131689607));
         }
      } else {
         var1.setContentDescription(2131296530, this.context.getString(2131689560));
      }
   }

   private fun setupLanguageInRegular(views: RemoteViews) {
      val var2: Int = TopRow.WhenMappings.$EnumSwitchMapping$1[this.widgetData.getLanguage().ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 != 5) {
                     throw new NoWhenBranchMatchedException();
                  }

                  var1.setContentDescription(2131296530, this.context.getString(2131689628));
                  var1.setContentDescription(2131296528, this.context.getString(2131689628));
               } else {
                  var1.setContentDescription(2131296530, this.context.getString(2131689581));
                  var1.setContentDescription(2131296528, this.context.getString(2131689581));
               }
            } else {
               var1.setContentDescription(2131296530, this.context.getString(2131689543));
               var1.setContentDescription(2131296528, this.context.getString(2131689543));
            }
         } else {
            var1.setContentDescription(2131296530, this.context.getString(2131689607));
            var1.setContentDescription(2131296528, this.context.getString(2131689607));
         }
      } else {
         var1.setContentDescription(2131296530, this.context.getString(2131689560));
         var1.setContentDescription(2131296528, this.context.getString(2131689560));
      }
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      var var3: RemoteViews;
      switch (TopRow.WhenMappings.$EnumSwitchMapping$0[this.widgetState.ordinal()]) {
         case 1:
         case 2:
         case 3:
            var3 = this.getEmptyWithoutLogo();
            break;
         case 4:
         case 5:
            var3 = this.getEmptyWithLogo();
            break;
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
            var3 = this.getRegular(this.widgetData.getActiveSite(), this.widgetData.getShowSites());
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var3;
   }
}
