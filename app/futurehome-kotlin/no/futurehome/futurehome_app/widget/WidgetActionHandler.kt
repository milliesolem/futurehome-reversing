package no.futurehome.futurehome_app.widget

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.BuildersKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineScopeKt
import kotlinx.coroutines.Dispatchers
import no.futurehome.futurehome_app.widget.helpers.ApiHelper
import no.futurehome.futurehome_app.widget.helpers.ConnectionChecker
import no.futurehome.futurehome_app.widget.helpers.Logger
import no.futurehome.futurehome_app.widget.helpers.PrefsHelper
import no.futurehome.futurehome_app.widget.models.AuthCredentials
import no.futurehome.futurehome_app.widget.models.Mode
import no.futurehome.futurehome_app.widget.models.RuntimeEnv

public class WidgetActionHandler(context: Context) {
   private final val context: Context

   init {
      this.context = var1;
   }

   private fun executeShortcut(intent: Intent): Boolean {
      val var2: Int = var1.getIntExtra("futurehome.widget.ShortcutId", -1);
      if (var2 == -1) {
         Logger.Companion.e("WidgetActionHandler", "handleExecuteShortcut, no shortcutId specified");
         return false;
      } else {
         val var4: PrefsHelper = new PrefsHelper(this.context);
         val var3: ApiHelper = new ApiHelper();
         val var6: java.lang.String = var4.getActiveSiteId();
         if (var6 == null) {
            Logger.Companion.e("WidgetActionHandler", "handleExecuteShortcut, siteId is null");
            return false;
         } else {
            val var5: AuthCredentials = var4.getAuthCredentials();
            if (var5 == null) {
               Logger.Companion.e("WidgetActionHandler", "handleExecuteShortcut, authCredentials is null");
               return false;
            } else {
               val var7: RuntimeEnv = var4.getRuntimeEnv();
               if (var7 == null) {
                  Logger.Companion.e("WidgetActionHandler", "handleExecuteShortcut, runtimeEnv is null");
                  return false;
               } else {
                  val var8: java.lang.String = var3.getSiteTokenHash(var6, var5.getAccessTokenHash(), var7);
                  if (var8 == null) {
                     Logger.Companion.e("WidgetActionHandler", "handleExecuteShortcut, siteTokenHash is null");
                     return false;
                  } else {
                     return var3.triggerShortcut(var2, var6, var8, var7);
                  }
               }
            }
         }
      }
   }

   private fun handleExecuteShortcut(intent: Intent) {
      this.setIsLoading(true);
      new WidgetUpdater(this.context).update(false);
      BuildersKt.launch$default(
         CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()),
         null,
         null,
         (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, var1, null) {
            final Intent $intent;
            int label;
            final WidgetActionHandler this$0;

            {
               super(2, var3);
               this.this$0 = var1;
               this.$intent = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               return new <anonymous constructor>(this.this$0, this.$intent, var2);
            }

            public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label != 0) {
                  if (this.label != 1) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var1);
               } else {
                  ResultKt.throwOnFailure(var1);
                  val var3: Boolean = WidgetActionHandler.access$executeShortcut(this.this$0, this.$intent);
                  WidgetActionHandler.access$setIsLoading(this.this$0, false);
                  val var5: CoroutineContext = Dispatchers.getMain();
                  val var6: Function2 = (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var3, this.this$0, null) {
                     final boolean $success;
                     int label;
                     final WidgetActionHandler this$0;

                     {
                        super(2, var3x);
                        this.$success = var1;
                        this.this$0 = var2x;
                     }

                     @Override
                     public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                        return new <anonymous constructor>(this.$success, this.this$0, var2);
                     }

                     public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                        return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                     }

                     @Override
                     public final Object invokeSuspend(Object var1) {
                        IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        if (this.label == 0) {
                           ResultKt.throwOnFailure(var1);
                           if (this.$success) {
                              WidgetActionHandler.access$showToast(this.this$0, "Success");
                           } else {
                              WidgetActionHandler.access$showToast(this.this$0, "Something went wrong.");
                           }

                           new WidgetUpdater(WidgetActionHandler.access$getContext$p(this.this$0)).update(true);
                           return Unit.INSTANCE;
                        } else {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                     }
                  }) as Function2;
                  var1 = this;
                  this.label = 1;
                  if (BuildersKt.withContext(var5, var6, var1) === var4) {
                     return var4;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as Function2,
         3,
         null
      );
   }

   private fun handleHideSites() {
      new PrefsHelper(this.context).setShowSites(false);
      new WidgetUpdater(this.context).update(true);
   }

   private fun handleOpenApp() {
      var var1: Intent;
      try {
         var1 = this.context.getPackageManager().getLaunchIntentForPackage("no.futurehome.futurehome_app");
      } catch (var5: Exception) {
         val var2: Logger.Companion = Logger.Companion;
         val var3: StringBuilder = new StringBuilder("handleOpenApp, exception: ");
         var3.append(var5);
         var2.e("WidgetActionHandler", var3.toString());
         return;
      }

      if (var1 != null) {
         try {
            var1.setFlags(268435456);
            ContextCompat.startActivity(this.context, var1, null);
         } catch (var4: Exception) {
            val var6: Logger.Companion = Logger.Companion;
            val var7: StringBuilder = new StringBuilder("handleOpenApp, exception: ");
            var7.append(var4);
            var6.e("WidgetActionHandler", var7.toString());
         }
      }
   }

   private fun handleSetMode(mode: Mode) {
      this.setIsLoading(true);
      new WidgetUpdater(this.context).update(false);
      BuildersKt.launch$default(
         CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()),
         null,
         null,
         (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(this, var1, null) {
            final Mode $mode;
            int label;
            final WidgetActionHandler this$0;

            {
               super(2, var3);
               this.this$0 = var1;
               this.$mode = var2x;
            }

            @Override
            public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
               return new <anonymous constructor>(this.this$0, this.$mode, var2);
            }

            public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
               return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               val var4: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
               if (this.label != 0) {
                  if (this.label != 1) {
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  ResultKt.throwOnFailure(var1);
               } else {
                  ResultKt.throwOnFailure(var1);
                  val var3: Boolean = WidgetActionHandler.access$setMode(this.this$0, this.$mode);
                  WidgetActionHandler.access$setIsLoading(this.this$0, false);
                  val var5: CoroutineContext = Dispatchers.getMain();
                  val var6: Function2 = (new Function2<CoroutineScope, Continuation<? super Unit>, Object>(var3, this.this$0, null) {
                     final boolean $success;
                     int label;
                     final WidgetActionHandler this$0;

                     {
                        super(2, var3x);
                        this.$success = var1;
                        this.this$0 = var2x;
                     }

                     @Override
                     public final Continuation<Unit> create(Object var1, Continuation<?> var2) {
                        return new <anonymous constructor>(this.$success, this.this$0, var2);
                     }

                     public final Object invoke(CoroutineScope var1, Continuation<? super Unit> var2x) {
                        return (this.create(var1, var2x) as <unrepresentable>).invokeSuspend(Unit.INSTANCE);
                     }

                     @Override
                     public final Object invokeSuspend(Object var1) {
                        IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        if (this.label == 0) {
                           ResultKt.throwOnFailure(var1);
                           if (!this.$success) {
                              WidgetActionHandler.access$showToast(this.this$0, "Something went wrong.");
                           }

                           new WidgetUpdater(WidgetActionHandler.access$getContext$p(this.this$0)).update(true);
                           return Unit.INSTANCE;
                        } else {
                           throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                     }
                  }) as Function2;
                  var1 = this;
                  this.label = 1;
                  if (BuildersKt.withContext(var5, var6, var1) === var4) {
                     return var4;
                  }
               }

               return Unit.INSTANCE;
            }
         }) as Function2,
         3,
         null
      );
   }

   private fun handleSetSite(intent: Intent) {
      this.setActiveSite(var1);
      new WidgetUpdater(this.context).update(true);
   }

   private fun handleShowSites() {
      new PrefsHelper(this.context).setShowSites(true);
      new WidgetUpdater(this.context).update(true);
   }

   private fun setActiveSite(intent: Intent) {
      if (!var1.hasExtra("futurehome.widget.SiteId")) {
         Logger.Companion.e("WidgetActionHandler", "handleSetSite, no siteId");
      } else {
         val var3: java.lang.String = var1.getStringExtra("futurehome.widget.SiteId");
         if (var3 == null) {
            Logger.Companion.e("WidgetActionHandler", "handleSetSite, siteId shouldn't be null");
         } else {
            val var2: PrefsHelper = new PrefsHelper(this.context);
            var2.setActiveSiteId(var3);
            var2.setShowSites(false);
         }
      }
   }

   private fun setIsLoading(value: Boolean) {
      new PrefsHelper(this.context).setIsLoading(var1);
   }

   private fun setMode(mode: Mode): Boolean {
      val var4: PrefsHelper = new PrefsHelper(this.context);
      val var2: ApiHelper = new ApiHelper();
      val var3: java.lang.String = var4.getActiveSiteId();
      if (var3 == null) {
         Logger.Companion.e("WidgetActionHandler", "setMode, siteId is null");
         return false;
      } else {
         val var5: AuthCredentials = var4.getAuthCredentials();
         if (var5 == null) {
            Logger.Companion.e("WidgetActionHandler", "setMode, authCredentials is null");
            return false;
         } else {
            val var6: RuntimeEnv = var4.getRuntimeEnv();
            if (var6 == null) {
               Logger.Companion.e("WidgetActionHandler", "setMode, runtimeEnv is null");
               return false;
            } else {
               val var7: java.lang.String = var2.getSiteTokenHash(var3, var5.getAccessTokenHash(), var6);
               if (var7 == null) {
                  Logger.Companion.e("WidgetActionHandler", "setMode, siteTokenHash is null");
                  return false;
               } else {
                  return var2.setActiveMode(var1, var3, var7, var6);
               }
            }
         }
      }
   }

   private fun showToast(msg: String) {
      Toast.makeText(this.context, var1, 0).show();
   }

   public fun handle(intent: Intent) {
      val var2: java.lang.String = var1.getStringExtra("futurehome.widget.ActionName");
      if (var2 == null) {
         Logger.Companion.e("WidgetActionHandler", "No action specified");
      } else if (!new ConnectionChecker(this.context).isConnected()) {
         new WidgetUpdater(this.context).update(true);
      } else {
         switch (var2.hashCode()) {
            case -1330826553:
               if (var2.equals("futurehome.widget.ActionExecuteShortcut")) {
                  this.handleExecuteShortcut(var1);
               }
               break;
            case -1126535544:
               if (var2.equals("futurehome.widget.ActionMoveToAuth")) {
                  this.handleOpenApp();
               }
               break;
            case -667822378:
               if (var2.equals("futurehome.widget.ActionHideSites")) {
                  this.handleHideSites();
               }
               break;
            case -247933272:
               if (var2.equals("futurehome.widget.ActionMoveToAddSite")) {
                  this.handleOpenApp();
               }
               break;
            case 362494734:
               if (var2.equals("futurehome.widget.ActionSetModeVacation")) {
                  this.handleSetMode(Mode.VACATION);
               }
               break;
            case 450427573:
               if (var2.equals("futurehome.widget.ActionSetSite")) {
                  this.handleSetSite(var1);
               }
               break;
            case 870135291:
               if (var2.equals("futurehome.widget.ActionShowSites")) {
                  this.handleShowSites();
               }
               break;
            case 1497213795:
               if (var2.equals("futurehome.widget.ActionOpenApp")) {
                  this.handleOpenApp();
               }
               break;
            case 1744052061:
               if (var2.equals("futurehome.widget.ActionUpdate")) {
                  new WidgetUpdater(this.context).update(true);
               }
               break;
            case 1755147334:
               if (var2.equals("futurehome.widget.ActionSetModeSleep")) {
                  this.handleSetMode(Mode.SLEEP);
               }
               break;
            case 1857207199:
               if (var2.equals("futurehome.widget.ActionSetModeAway")) {
                  this.handleSetMode(Mode.AWAY);
               }
               break;
            case 1857408400:
               if (var2.equals("futurehome.widget.ActionSetModeHome")) {
                  this.handleSetMode(Mode.HOME);
               }
            default:
         }
      }
   }

   public companion object {
      private const val TAG: String
   }
}
