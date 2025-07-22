package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.SiteM
import no.futurehome.futurehome_app.widget.models.WidgetData

public class SiteRow(context: Context, widgetData: WidgetData) : Row {
   private final val context: Context
   private final val widgetData: WidgetData

   init {
      this.context = var1;
      this.widgetData = var2;
   }

   private fun getViewsForBrightness(isActive: Boolean): RemoteViews {
      val var2: RemoteViews;
      if (var1) {
         var2 = new RemoteViews(this.context.getPackageName(), 2131492978);
      } else if (this.widgetData.isDark()) {
         var2 = new RemoteViews(this.context.getPackageName(), 2131492979);
      } else {
         var2 = new RemoteViews(this.context.getPackageName(), 2131492980);
      }

      return var2;
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      val var2: java.util.List = this.widgetData.getSites();
      val var3: SiteM = var2.get(var1 - 1) as SiteM;
      val var6: SiteM = this.widgetData.getActiveSite();
      val var7: RemoteViews = this.getViewsForBrightness(var6.getId() == var3.getId());
      var7.setTextViewText(2131296527, var3.getName());
      if (!(var3.getAddress() == "")) {
         val var4: java.lang.String = var3.getAddress();
         val var5: StringBuilder = new StringBuilder(", ");
         var5.append(var4);
         var7.setTextViewText(2131296525, var5.toString());
      } else {
         var7.setTextViewText(2131296525, "");
      }

      val var9: Intent = new Intent();
      var9.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionSetSite");
      var9.putExtra("futurehome.widget.SiteId", var3.getId());
      var7.setOnClickFillInIntent(2131296526, var9);
      val var10: java.lang.String = var3.getName();
      val var8: java.lang.String = var3.getAddress();
      val var11: StringBuilder = new StringBuilder("site: ");
      var11.append(var10);
      var11.append(" ");
      var11.append(var8);
      var7.setContentDescription(2131296526, var11.toString());
      return var7;
   }
}
