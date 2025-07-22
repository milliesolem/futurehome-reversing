package no.futurehome.futurehome_app.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.widget.RemoteViews
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineScopeKt
import kotlinx.coroutines.Dispatchers
import no.futurehome.futurehome_app.widget.helpers.ApiHelper
import no.futurehome.futurehome_app.widget.helpers.BrightnessChecker
import no.futurehome.futurehome_app.widget.helpers.ConnectionChecker
import no.futurehome.futurehome_app.widget.helpers.PrefsHelper

public class WidgetUpdater(context: Context) {
   private final val context: Context
   private final val prefsHelper: PrefsHelper
   private final val appWidgetManager: AppWidgetManager
   private final val appWidgetIds: IntArray

   init {
      this.context = var1;
      this.prefsHelper = new PrefsHelper(var1);
      val var2: AppWidgetManager = AppWidgetManager.getInstance(var1);
      this.appWidgetManager = var2;
      val var3: IntArray = var2.getAppWidgetIds(new ComponentName(var1, FuturehomeWidgetProvider.class));
      this.appWidgetIds = var3;
   }

   private fun getViewsForBrightness(): RemoteViews {
      val var1: RemoteViews;
      if (BrightnessChecker.Companion.getInstance().isDark()) {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492944);
      } else {
         var1 = new RemoteViews(this.context.getPackageName(), 2131492945);
      }

      return var1;
   }

   private fun redrawWidgets() {
      BrightnessChecker.Companion.getInstance().updateTheme(this.context);
      val var3: IntArray = this.appWidgetIds;
      val var2: Int = this.appWidgetIds.length;

      for (int var1 = 0; var1 < var2; var1++) {
         this.updateWidgetWithId(var3[var1]);
      }

      this.appWidgetManager.notifyAppWidgetViewDataChanged(this.appWidgetIds, 2131296510);
   }

   private fun updateWidgetWithId(appWidgetId: Int) {
      val var3: RemoteViews = this.getViewsForBrightness();
      var3.setRemoteAdapter(2131296510, new Intent(this.context, WidgetListService::class.java));
      val var2: Intent = new Intent(this.context, FuturehomeWidgetProvider::class.java);
      var2.setAction("futurehome.widget.Action");
      val var4: PendingIntent;
      if (VERSION.SDK_INT >= 31) {
         var4 = PendingIntent.getBroadcast(this.context, 0, var2, 33554432);
      } else {
         var4 = PendingIntent.getBroadcast(this.context, 0, var2, 134217728);
      }

      var3.setPendingIntentTemplate(2131296510, var4);
      this.appWidgetManager.updateAppWidget(var1, var3);
   }

   public fun update(withFetch: Boolean) {
      if (!var1) {
         this.redrawWidgets();
      } else {
         this.prefsHelper.setIsLoading(true);
         this.redrawWidgets();
         BuildersKt.launch$default(
            CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()),
            null,
            null,
            (
               new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, null) {
                  int label;
                  final WidgetUpdater this$0;

                  {
                     super(2, var2x);
                     this.this$0 = var1;
                  }

                  @Override
                  public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                     return new <anonymous constructor>(this.this$0, var2);
                  }

                  public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                     return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                  }

                  @Override
                  public final Object invokeSuspend(Object var1) {
                     val var3: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                     if (this.label != 0) {
                        if (this.label != 1) {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }

                        ResultKt.throwOnFailure(var1);
                     } else {
                        ResultKt.throwOnFailure(var1);
                        new WidgetStateManager(
                              WidgetUpdater.access$getPrefsHelper$p(this.this$0),
                              new ConnectionChecker(WidgetUpdater.access$getContext$p(this.this$0)),
                              new ApiHelper(),
                              BrightnessChecker.Companion.getInstance()
                           )
                           .update();
                        val var8: CoroutineContext = Dispatchers.getMain();
                        var1 = (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this.this$0, null) {
                           int label;
                           final WidgetUpdater this$0;

                           {
                              super(2, var2x);
                              this.this$0 = var1;
                           }

                           @Override
                           public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                              return new <anonymous constructor>(this.this$0, var2);
                           }

                           public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                              return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                           }

                           @Override
                           public final Object invokeSuspend(Object var1) {
                              IntrinsicsKt.getCOROUTINE_SUSPENDED();
                              if (this.label == 0) {
                                 ResultKt.throwOnFailure(var1);
                                 WidgetUpdater.access$getPrefsHelper$p(this.this$0).setIsLoading(false);
                                 WidgetUpdater.access$redrawWidgets(this.this$0);
                                 return Unit.INSTANCE;
                              } else {
                                 throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                              }
                           }
                        }) as Function2;
                        val var5: Continuation = this;
                        this.label = 1;
                        if (BuildersKt.withContext(var8, var1, var5) === var3) {
                           return var3;
                        }
                     }

                     return Unit.INSTANCE;
                  }
               }
            ) as Function2,
            3,
            null
         );
      }
   }
}
