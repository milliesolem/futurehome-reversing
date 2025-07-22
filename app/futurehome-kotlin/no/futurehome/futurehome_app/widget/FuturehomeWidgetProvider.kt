package no.futurehome.futurehome_app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent

public class FuturehomeWidgetProvider : AppWidgetProvider {
   public open fun onReceive(context: Context, intent: Intent) {
      if (var2.getAction() == "futurehome.widget.Action") {
         new WidgetActionHandler(var1).handle(var2);
      }

      super.onReceive(var1, var2);
   }

   public open fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
      new WidgetUpdater(var1).update(true);
   }
}
