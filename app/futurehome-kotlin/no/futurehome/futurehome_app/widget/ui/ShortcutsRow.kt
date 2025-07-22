package no.futurehome.futurehome_app.widget.ui

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.ShortcutM
import no.futurehome.futurehome_app.widget.models.SiteM
import no.futurehome.futurehome_app.widget.models.WidgetData

public class ShortcutsRow(context: Context, widgetData: WidgetData) : Row {
   private final val context: Context
   private final val widgetData: WidgetData

   init {
      this.context = var1;
      this.widgetData = var2;
   }

   private fun getShortcutsRow(shortcutRowIndex: Int): RemoteViews {
      val var2: RemoteViews = this.getViewsForBrightness(var1);
      var var3: SiteM = this.widgetData.getActiveSite();
      val var9: java.util.List = var3.getShortcuts();
      val var8: Int = var1 * 2;
      val var6: ShortcutM = var9.get(var1 * 2) as ShortcutM;
      val var4: java.lang.String = var6.getNameForLanguage(this.context, this.widgetData.getLanguage());
      var3 = this.widgetData.getActiveSite();
      val var5: ShortcutM = var3.getShortcuts().get(var8 + 1);
      val var11: java.lang.String = var5.getNameForLanguage(this.context, this.widgetData.getLanguage());
      var2.setTextViewText(2131296507, var4);
      val var7: Intent = new Intent();
      var7.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionExecuteShortcut");
      var7.putExtra("futurehome.widget.ShortcutId", var6.getId());
      var2.setOnClickFillInIntent(2131296507, var7);
      val var14: StringBuilder = new StringBuilder("shortcut: ");
      var14.append(var4);
      var2.setContentDescription(2131296507, var14.toString());
      var2.setTextViewText(2131296522, var11);
      val var12: Intent = new Intent();
      var12.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionExecuteShortcut");
      var12.putExtra("futurehome.widget.ShortcutId", var5.getId());
      var2.setOnClickFillInIntent(2131296522, var12);
      val var13: StringBuilder = new StringBuilder("shortcut: ");
      var13.append(var11);
      var2.setContentDescription(2131296507, var13.toString());
      return var2;
   }

   private fun getSingleShortcutRow(shortcutRowIndex: Int): RemoteViews {
      val var2: RemoteViews = this.getSingleViewForBrightness(var1);
      val var3: SiteM = this.widgetData.getActiveSite();
      val var5: ShortcutM = var3.getShortcuts().get(var1 * 2);
      val var6: java.lang.String = var5.getNameForLanguage(this.context, this.widgetData.getLanguage());
      var2.setTextViewText(2131296523, var6);
      val var4: Intent = new Intent();
      var4.putExtra("futurehome.widget.ActionName", "futurehome.widget.ActionExecuteShortcut");
      var4.putExtra("futurehome.widget.ShortcutId", var5.getId());
      var2.setOnClickFillInIntent(2131296523, var4);
      var2.setViewVisibility(2131296523, 0);
      val var7: StringBuilder = new StringBuilder("shortcut: ");
      var7.append(var6);
      var2.setContentDescription(2131296523, var7.toString());
      return var2;
   }

   private fun getSingleViewForBrightness(shortcutRowIndex: Int): RemoteViews {
      val var2: RemoteViews;
      if (this.widgetData.isDark()) {
         var1 = var1 % 4;
         if (var1 % 4 != 0) {
            if (var1 != 1) {
               if (var1 != 2) {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492968);
               } else {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492966);
               }
            } else {
               var2 = new RemoteViews(this.context.getPackageName(), 2131492964);
            }
         } else {
            var2 = new RemoteViews(this.context.getPackageName(), 2131492962);
         }
      } else {
         var1 = var1 % 4;
         if (var1 % 4 != 0) {
            if (var1 != 1) {
               if (var1 != 2) {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492969);
               } else {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492967);
               }
            } else {
               var2 = new RemoteViews(this.context.getPackageName(), 2131492965);
            }
         } else {
            var2 = new RemoteViews(this.context.getPackageName(), 2131492963);
         }
      }

      return var2;
   }

   private fun getViewsForBrightness(shortcutRowIndex: Int): RemoteViews {
      val var2: RemoteViews;
      if (this.widgetData.isDark()) {
         var1 = var1 % 4;
         if (var1 % 4 != 0) {
            if (var1 != 1) {
               if (var1 != 2) {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492976);
               } else {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492974);
               }
            } else {
               var2 = new RemoteViews(this.context.getPackageName(), 2131492972);
            }
         } else {
            var2 = new RemoteViews(this.context.getPackageName(), 2131492970);
         }
      } else {
         var1 = var1 % 4;
         if (var1 % 4 != 0) {
            if (var1 != 1) {
               if (var1 != 2) {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492977);
               } else {
                  var2 = new RemoteViews(this.context.getPackageName(), 2131492975);
               }
            } else {
               var2 = new RemoteViews(this.context.getPackageName(), 2131492973);
            }
         } else {
            var2 = new RemoteViews(this.context.getPackageName(), 2131492971);
         }
      }

      return var2;
   }

   public override fun getRemoteViews(rowIndex: Int): RemoteViews {
      var1 = var1 - 2;
      val var2: SiteM = this.widgetData.getActiveSite();
      return if (var2.getShortcuts().size() - 1 < var1 * 2 + 1) this.getSingleShortcutRow(var1) else this.getShortcutsRow(var1);
   }
}
