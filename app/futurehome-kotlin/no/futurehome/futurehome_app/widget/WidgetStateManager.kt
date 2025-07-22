package no.futurehome.futurehome_app.widget

import no.futurehome.futurehome_app.widget.helpers.ApiHelper
import no.futurehome.futurehome_app.widget.helpers.BrightnessChecker
import no.futurehome.futurehome_app.widget.helpers.ConnectionChecker
import no.futurehome.futurehome_app.widget.helpers.PrefsHelper
import no.futurehome.futurehome_app.widget.models.AuthCredentials
import no.futurehome.futurehome_app.widget.models.Mode
import no.futurehome.futurehome_app.widget.models.RuntimeEnv
import no.futurehome.futurehome_app.widget.models.SiteM
import no.futurehome.futurehome_app.widget.models.WidgetData
import no.futurehome.futurehome_app.widget.models.WidgetState

public class WidgetStateManager(prefsHelper: PrefsHelper, connectionChecker: ConnectionChecker, apiHelper: ApiHelper, brightnessChecker: BrightnessChecker) {
   private final val prefsHelper: PrefsHelper
   private final val connectionChecker: ConnectionChecker
   private final val apiHelper: ApiHelper
   private final val brightnessChecker: BrightnessChecker

   public final var data: WidgetData
      private set

   init {
      this.prefsHelper = var1;
      this.connectionChecker = var2;
      this.apiHelper = var3;
      this.brightnessChecker = var4;
      this.data = new WidgetData(false, null, false, false, null, null, null, null, 255, null);
   }

   private fun saveToPrefsWithState(state: WidgetState) {
      this.prefsHelper.setWidgetData(this.data);
      this.prefsHelper.setWidgetState(var1);
   }

   private fun setupActiveMode(): SetupActiveModeError {
      val var1: SiteM = this.data.getActiveSite();
      if (var1 != null) {
         val var3: java.lang.String = var1.getId();
         if (var3 != null) {
            val var2: java.lang.String = this.data.getSiteTokenHash();
            if (var2 == null) {
               return SetupActiveModeError.NoSiteTokenHash;
            }

            val var4: RuntimeEnv = this.prefsHelper.getRuntimeEnv();
            if (var4 == null) {
               return SetupActiveModeError.NoRuntimeEnv;
            }

            switch (WidgetStateManager.WhenMappings.$EnumSwitchMapping$4[this.apiHelper.getActiveMode(var3, var2, var4).ordinal()]) {
               case 1:
                  return SetupActiveModeError.FetchError;
               case 2:
                  return SetupActiveModeError.HubOffline;
               case 3:
                  this.data.setActiveMode(Mode.HOME);
                  return SetupActiveModeError.None;
               case 4:
                  this.data.setActiveMode(Mode.AWAY);
                  return SetupActiveModeError.None;
               case 5:
                  this.data.setActiveMode(Mode.SLEEP);
                  return SetupActiveModeError.None;
               case 6:
                  this.data.setActiveMode(Mode.VACATION);
                  return SetupActiveModeError.None;
               default:
                  throw new NoWhenBranchMatchedException();
            }
         }
      }

      return SetupActiveModeError.NoActiveSiteId;
   }

   private fun setupAuth(): SetupAuthError {
      val var2: RuntimeEnv = this.prefsHelper.getRuntimeEnv();
      if (var2 == null) {
         return SetupAuthError.NoRuntimeEnv;
      } else {
         var var1: AuthCredentials = this.prefsHelper.getAuthCredentials();
         if (var1 == null) {
            return SetupAuthError.NoAuthCredentials;
         } else {
            val var3: java.lang.Boolean = this.apiHelper.checkAccessToken(var1.getAccessTokenHash(), var2);
            if (var3 != null) {
               if (!var3) {
                  var1 = this.apiHelper.refreshAccessToken(var1.getRefreshToken(), var1.getScope(), var1.getTokenEndpoint(), var2);
                  if (var1 == null) {
                     return SetupAuthError.RefreshAuthTokenError;
                  }

                  this.prefsHelper.setAuthCredentials(var1);
               }

               return SetupAuthError.None;
            } else {
               return SetupAuthError.CheckAccessTokenError;
            }
         }
      }
   }

   private fun setupConnection(): SetupConnectionError {
      val var1: Boolean = this.connectionChecker.isConnected();
      this.data.setConnected(var1);
      val var2: SetupConnectionError;
      if (var1) {
         var2 = SetupConnectionError.None;
      } else {
         var2 = SetupConnectionError.NoConnection;
      }

      return var2;
   }

   private fun setupIsDark() {
      this.data.setDark(this.brightnessChecker.isDark());
   }

   private fun setupLanguage() {
      this.data.setLanguage(this.prefsHelper.getLanguage());
   }

   private fun setupShowSites() {
      this.data.setShowSites(this.prefsHelper.getShowSites());
   }

   private fun setupSiteTokenHash(): SetupSiteTokenHashError {
      val var1: RuntimeEnv = this.prefsHelper.getRuntimeEnv();
      if (var1 == null) {
         return SetupSiteTokenHashError.NoRuntimeEnv;
      } else {
         val var2: SiteM = this.data.getActiveSite();
         if (var2 != null) {
            val var5: java.lang.String = var2.getId();
            if (var5 != null) {
               val var3: AuthCredentials = this.prefsHelper.getAuthCredentials();
               if (var3 == null) {
                  return SetupSiteTokenHashError.NoAuthCredentials;
               }

               val var4: java.lang.String = this.apiHelper.getSiteTokenHash(var5, var3.getAccessTokenHash(), var1);
               if (var4 == null) {
                  return SetupSiteTokenHashError.FetchError;
               }

               this.data.setSiteTokenHash(var4);
               return SetupSiteTokenHashError.None;
            }
         }

         return SetupSiteTokenHashError.NoActiveSiteId;
      }
   }

   private fun setupSites(): SetupSitesError {
      var var1: RuntimeEnv = this.prefsHelper.getRuntimeEnv();
      if (var1 == null) {
         return SetupSitesError.NoRuntimeEnv;
      } else {
         var var2: AuthCredentials = this.prefsHelper.getAuthCredentials();
         if (var2 == null) {
            return SetupSitesError.NoAuthCredentials;
         } else {
            val var3: java.util.List = this.apiHelper.getSites(var2.getAccessTokenHash(), var1);
            if (var3 == null) {
               return SetupSitesError.FetchError;
            } else {
               this.data.setSites(var3);
               if (var3.isEmpty()) {
                  return SetupSitesError.UserHasNoSites;
               } else {
                  val var4: java.lang.String = this.prefsHelper.getActiveSiteId();
                  var var6: SiteM = null;
                  var2 = null;
                  if (var4 != null) {
                     val var5: java.util.Iterator = var3.iterator();

                     do {
                        var1 = var2;
                        if (!var5.hasNext()) {
                           break;
                        }

                        var1 = (RuntimeEnv)var5.next();
                     } while (!(((SiteM)var1).getId() == var4));

                     var6 = var1 as SiteM;
                  }

                  var var9: SiteM = var6;
                  if (var6 == null) {
                     var9 = var3.get(0) as SiteM;
                  }

                  this.data.setActiveSite(var9);
                  this.prefsHelper.setActiveSiteId(var9.getId());
                  return if (!var9.getHasHub()) SetupSitesError.SiteHasNoHub else SetupSitesError.None;
               }
            }
         }
      }
   }

   public fun update() {
      this.data = new WidgetData(false, null, false, false, null, null, null, null, 255, null);
      this.setupLanguage();
      this.setupIsDark();
      if (this.setupConnection() === SetupConnectionError.NoConnection) {
         this.saveToPrefsWithState(WidgetState.NoConnection);
      } else {
         var var1: Int = WidgetStateManager.WhenMappings.$EnumSwitchMapping$0[this.setupAuth().ordinal()];
         if (var1 != 1) {
            if (var1 == 2 || var1 == 3) {
               this.saveToPrefsWithState(WidgetState.NoUser);
            } else if (var1 != 4 && var1 != 5) {
               throw new NoWhenBranchMatchedException();
            } else {
               this.saveToPrefsWithState(WidgetState.OpenAppError);
            }
         } else {
            switch (WidgetStateManager.WhenMappings.$EnumSwitchMapping$1[this.setupSites().ordinal()]) {
               case 1:
               case 2:
                  this.setupShowSites();
                  if (this.data.getShowSites()) {
                     this.saveToPrefsWithState(WidgetState.PresentingSites);
                     return;
                  } else {
                     var var5: SiteM = this.data.getActiveSite();
                     if (var5 != null && !var5.getHasHub()) {
                        this.saveToPrefsWithState(WidgetState.NoHub);
                        return;
                     } else {
                        var5 = this.data.getActiveSite();
                        if (var5 != null && !var5.getHasOnlineHub()) {
                           this.saveToPrefsWithState(WidgetState.HubOffline);
                           return;
                        } else {
                           var1 = WidgetStateManager.WhenMappings.$EnumSwitchMapping$2[this.setupSiteTokenHash().ordinal()];
                           if (var1 != 1) {
                              if (var1 != 2 && var1 != 3 && var1 != 4) {
                                 if (var1 == 5) {
                                    this.saveToPrefsWithState(WidgetState.GeneralError);
                                    return;
                                 }

                                 throw new NoWhenBranchMatchedException();
                              }

                              this.saveToPrefsWithState(WidgetState.NoUser);
                              return;
                           } else {
                              switch (WidgetStateManager.WhenMappings.$EnumSwitchMapping$3[this.setupActiveMode().ordinal()]) {
                                 case 1:
                                    this.saveToPrefsWithState(WidgetState.PresentingModesAndShortcuts);
                                    return;
                                 case 2:
                                 case 3:
                                 case 4:
                                    this.saveToPrefsWithState(WidgetState.NoUser);
                                    return;
                                 case 5:
                                    this.saveToPrefsWithState(WidgetState.GeneralError);
                                    return;
                                 case 6:
                                    this.saveToPrefsWithState(WidgetState.HubOffline);
                                    return;
                                 default:
                                    throw new NoWhenBranchMatchedException();
                              }
                           }
                        }
                     }
                  }
               case 3:
               case 4:
                  this.saveToPrefsWithState(WidgetState.NoUser);
                  return;
               case 5:
                  this.saveToPrefsWithState(WidgetState.GeneralError);
                  return;
               case 6:
                  this.saveToPrefsWithState(WidgetState.NoSites);
                  return;
               default:
                  throw new NoWhenBranchMatchedException();
            }
         }
      }
   }
}
