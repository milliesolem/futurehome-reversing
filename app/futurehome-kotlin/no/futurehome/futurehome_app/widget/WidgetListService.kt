package no.futurehome.futurehome_app.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.RemoteViewsService.RemoteViewsFactory
import java.util.ArrayList
import no.futurehome.futurehome_app.widget.helpers.PrefsHelper
import no.futurehome.futurehome_app.widget.models.Row
import no.futurehome.futurehome_app.widget.models.SiteM
import no.futurehome.futurehome_app.widget.models.WidgetData
import no.futurehome.futurehome_app.widget.models.WidgetState
import no.futurehome.futurehome_app.widget.ui.CriticalErrorRow
import no.futurehome.futurehome_app.widget.ui.ErrorRow
import no.futurehome.futurehome_app.widget.ui.FullLoadingRow
import no.futurehome.futurehome_app.widget.ui.LoadingRow
import no.futurehome.futurehome_app.widget.ui.ModesRow
import no.futurehome.futurehome_app.widget.ui.NoConnectionRow
import no.futurehome.futurehome_app.widget.ui.NoHubRow
import no.futurehome.futurehome_app.widget.ui.NoSitesRow
import no.futurehome.futurehome_app.widget.ui.NoUserRow
import no.futurehome.futurehome_app.widget.ui.ShortcutsRow
import no.futurehome.futurehome_app.widget.ui.SiteRow
import no.futurehome.futurehome_app.widget.ui.TopRow

public class WidgetListService : RemoteViewsService {
   public open fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
      val var2: Context = this.getApplicationContext();
      return new WidgetListService.WidgetListItemFactory(var2);
   }

   public class WidgetListItemFactory(context: Context) : RemoteViewsFactory {
      private final val context: Context
      private final var viewRows: List<Row>

      init {
         this.context = var1;
         this.viewRows = CollectionsKt.emptyList();
      }

      private fun getState(): WidgetState {
         val var1: PrefsHelper = new PrefsHelper(this.context);
         return if (var1.getIsLoading()) WidgetState.Loading else var1.getWidgetState();
      }

      public open fun getCount(): Int {
         return this.viewRows.size();
      }

      public open fun getItemId(index: Int): Long {
         return var1;
      }

      public open fun getLoadingView(): RemoteViews {
         return new LoadingRow(this.context).getRemoteViews(0);
      }

      public open fun getViewAt(index: Int): RemoteViews {
         return if (var1 >= this.viewRows.size()) new LoadingRow(this.context).getRemoteViews(0) else this.viewRows.get(var1).getRemoteViews(var1);
      }

      public open fun getViewTypeCount(): Int {
         return 42;
      }

      public open fun hasStableIds(): Boolean {
         return false;
      }

      public open fun onCreate() {
      }

      public open fun onDataSetChanged() {
         val var5: WidgetState = this.getState();
         val var3: WidgetData = new PrefsHelper(this.context).getWidgetData();
         val var2: Int = WidgetListService.WidgetListItemFactory.WhenMappings.$EnumSwitchMapping$0[var5.ordinal()];
         var var1: Int = 0;
         switch (var2) {
            case 1:
               this.viewRows = CollectionsKt.listOf(new Row[]{new TopRow(this.context, var3, var5), new FullLoadingRow(this.context, var3)});
               break;
            case 2:
               this.viewRows = CollectionsKt.listOf(new Row[]{new TopRow(this.context, var3, var5), new NoConnectionRow(this.context, var3)});
               break;
            case 3:
               this.viewRows = CollectionsKt.listOf(new Row[]{new TopRow(this.context, var3, var5), new CriticalErrorRow(this.context, var3)});
               break;
            case 4:
               this.viewRows = CollectionsKt.listOf(new Row[]{new TopRow(this.context, var3, var5), new NoUserRow(this.context, var3)});
               break;
            case 5:
               this.viewRows = CollectionsKt.listOf(new Row[]{new TopRow(this.context, var3, var5), new ErrorRow(this.context, var3)});
               break;
            case 6:
               val var8: java.util.List = new ArrayList();
               var8.add(new TopRow(this.context, var3, var5));
               val var10: java.util.List = var3.getSites();

               for (SiteM var6 : var10) {
                  var8.add(new SiteRow(this.context, var3));
               }

               this.viewRows = var8;
               break;
            case 7:
               this.viewRows = CollectionsKt.listOf(new Row[]{new TopRow(this.context, var3, var5), new NoSitesRow(this.context, var3)});
               break;
            case 8:
            case 9:
               this.viewRows = CollectionsKt.listOf(new Row[]{new TopRow(this.context, var3, var5), new NoHubRow(this.context, var3)});
               break;
            case 10:
               val var4: java.util.List = new ArrayList();
               var4.add(new TopRow(this.context, var3, var5));
               var4.add(new ModesRow(this.context, var3));
               val var9: SiteM = var3.getActiveSite();

               for (int var7 = (int)((float)Math.ceil(var9.getShortcuts().size() / 2.0F)); var1 < var7; var1++) {
                  var4.add(new ShortcutsRow(this.context, var3));
               }

               this.viewRows = var4;
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }
      }

      public open fun onDestroy() {
      }
   }
}
