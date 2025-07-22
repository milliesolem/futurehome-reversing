package no.futurehome.futurehome_app.widget;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import no.futurehome.futurehome_app.widget.helpers.ApiHelper;
import no.futurehome.futurehome_app.widget.helpers.BrightnessChecker;
import no.futurehome.futurehome_app.widget.helpers.ConnectionChecker;
import no.futurehome.futurehome_app.widget.helpers.GetActiveModeResult;
import no.futurehome.futurehome_app.widget.helpers.PrefsHelper;
import no.futurehome.futurehome_app.widget.models.AuthCredentials;
import no.futurehome.futurehome_app.widget.models.Mode;
import no.futurehome.futurehome_app.widget.models.RuntimeEnv;
import no.futurehome.futurehome_app.widget.models.SiteM;
import no.futurehome.futurehome_app.widget.models.WidgetData;
import no.futurehome.futurehome_app.widget.models.WidgetState;

@Metadata(d1 = {"\000\\\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\030\0002\0020\001B'\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t¢\006\004\b\n\020\013J\006\020\021\032\0020\022J\b\020\023\032\0020\022H\002J\b\020\024\032\0020\022H\002J\b\020\025\032\0020\026H\002J\b\020\027\032\0020\022H\002J\b\020\030\032\0020\031H\002J\b\020\032\032\0020\033H\002J\b\020\034\032\0020\035H\002J\b\020\036\032\0020\037H\002J\020\020 \032\0020\0222\006\020!\032\0020\"H\002R\016\020\002\032\0020\003X\004¢\006\002\n\000R\016\020\004\032\0020\005X\004¢\006\002\n\000R\016\020\006\032\0020\007X\004¢\006\002\n\000R\016\020\b\032\0020\tX\004¢\006\002\n\000R\036\020\016\032\0020\r2\006\020\f\032\0020\r@BX\016¢\006\b\n\000\032\004\b\017\020\020¨\006#"}, d2 = {"Lno/futurehome/futurehome_app/widget/WidgetStateManager;", "", "prefsHelper", "Lno/futurehome/futurehome_app/widget/helpers/PrefsHelper;", "connectionChecker", "Lno/futurehome/futurehome_app/widget/helpers/ConnectionChecker;", "apiHelper", "Lno/futurehome/futurehome_app/widget/helpers/ApiHelper;", "brightnessChecker", "Lno/futurehome/futurehome_app/widget/helpers/BrightnessChecker;", "<init>", "(Lno/futurehome/futurehome_app/widget/helpers/PrefsHelper;Lno/futurehome/futurehome_app/widget/helpers/ConnectionChecker;Lno/futurehome/futurehome_app/widget/helpers/ApiHelper;Lno/futurehome/futurehome_app/widget/helpers/BrightnessChecker;)V", "value", "Lno/futurehome/futurehome_app/widget/models/WidgetData;", "data", "getData", "()Lno/futurehome/futurehome_app/widget/models/WidgetData;", "update", "", "setupLanguage", "setupIsDark", "setupConnection", "Lno/futurehome/futurehome_app/widget/SetupConnectionError;", "setupShowSites", "setupAuth", "Lno/futurehome/futurehome_app/widget/SetupAuthError;", "setupSites", "Lno/futurehome/futurehome_app/widget/SetupSitesError;", "setupSiteTokenHash", "Lno/futurehome/futurehome_app/widget/SetupSiteTokenHashError;", "setupActiveMode", "Lno/futurehome/futurehome_app/widget/SetupActiveModeError;", "saveToPrefsWithState", "state", "Lno/futurehome/futurehome_app/widget/models/WidgetState;", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public final class WidgetStateManager {
  private final ApiHelper apiHelper;
  
  private final BrightnessChecker brightnessChecker;
  
  private final ConnectionChecker connectionChecker;
  
  private WidgetData data;
  
  private final PrefsHelper prefsHelper;
  
  public WidgetStateManager(PrefsHelper paramPrefsHelper, ConnectionChecker paramConnectionChecker, ApiHelper paramApiHelper, BrightnessChecker paramBrightnessChecker) {
    this.prefsHelper = paramPrefsHelper;
    this.connectionChecker = paramConnectionChecker;
    this.apiHelper = paramApiHelper;
    this.brightnessChecker = paramBrightnessChecker;
    this.data = new WidgetData(false, null, false, false, null, null, null, null, 255, null);
  }
  
  private final void saveToPrefsWithState(WidgetState paramWidgetState) {
    this.prefsHelper.setWidgetData(this.data);
    this.prefsHelper.setWidgetState(paramWidgetState);
  }
  
  private final SetupActiveModeError setupActiveMode() {
    SiteM siteM = this.data.getActiveSite();
    if (siteM != null) {
      String str = siteM.getId();
      if (str != null) {
        String str1 = this.data.getSiteTokenHash();
        if (str1 == null)
          return SetupActiveModeError.NoSiteTokenHash; 
        RuntimeEnv runtimeEnv = this.prefsHelper.getRuntimeEnv();
        if (runtimeEnv == null)
          return SetupActiveModeError.NoRuntimeEnv; 
        GetActiveModeResult getActiveModeResult = this.apiHelper.getActiveMode(str, str1, runtimeEnv);
        switch (WhenMappings.$EnumSwitchMapping$4[getActiveModeResult.ordinal()]) {
          default:
            throw new NoWhenBranchMatchedException();
          case 6:
            this.data.setActiveMode(Mode.VACATION);
            return SetupActiveModeError.None;
          case 5:
            this.data.setActiveMode(Mode.SLEEP);
            return SetupActiveModeError.None;
          case 4:
            this.data.setActiveMode(Mode.AWAY);
            return SetupActiveModeError.None;
          case 3:
            this.data.setActiveMode(Mode.HOME);
            return SetupActiveModeError.None;
          case 2:
            return SetupActiveModeError.HubOffline;
          case 1:
            break;
        } 
        return SetupActiveModeError.FetchError;
      } 
    } 
    return SetupActiveModeError.NoActiveSiteId;
  }
  
  private final SetupAuthError setupAuth() {
    RuntimeEnv runtimeEnv = this.prefsHelper.getRuntimeEnv();
    if (runtimeEnv == null)
      return SetupAuthError.NoRuntimeEnv; 
    AuthCredentials authCredentials = this.prefsHelper.getAuthCredentials();
    if (authCredentials == null)
      return SetupAuthError.NoAuthCredentials; 
    Boolean bool = this.apiHelper.checkAccessToken(authCredentials.getAccessTokenHash(), runtimeEnv);
    if (bool != null) {
      if (!bool.booleanValue()) {
        authCredentials = this.apiHelper.refreshAccessToken(authCredentials.getRefreshToken(), authCredentials.getScope(), authCredentials.getTokenEndpoint(), runtimeEnv);
        if (authCredentials == null)
          return SetupAuthError.RefreshAuthTokenError; 
        this.prefsHelper.setAuthCredentials(authCredentials);
      } 
      return SetupAuthError.None;
    } 
    return SetupAuthError.CheckAccessTokenError;
  }
  
  private final SetupConnectionError setupConnection() {
    SetupConnectionError setupConnectionError;
    boolean bool = this.connectionChecker.isConnected();
    this.data.setConnected(bool);
    if (bool) {
      setupConnectionError = SetupConnectionError.None;
    } else {
      setupConnectionError = SetupConnectionError.NoConnection;
    } 
    return setupConnectionError;
  }
  
  private final void setupIsDark() {
    this.data.setDark(this.brightnessChecker.isDark());
  }
  
  private final void setupLanguage() {
    this.data.setLanguage(this.prefsHelper.getLanguage());
  }
  
  private final void setupShowSites() {
    this.data.setShowSites(this.prefsHelper.getShowSites());
  }
  
  private final SetupSiteTokenHashError setupSiteTokenHash() {
    RuntimeEnv runtimeEnv = this.prefsHelper.getRuntimeEnv();
    if (runtimeEnv == null)
      return SetupSiteTokenHashError.NoRuntimeEnv; 
    SiteM siteM = this.data.getActiveSite();
    if (siteM != null) {
      String str = siteM.getId();
      if (str != null) {
        AuthCredentials authCredentials = this.prefsHelper.getAuthCredentials();
        if (authCredentials == null)
          return SetupSiteTokenHashError.NoAuthCredentials; 
        String str1 = this.apiHelper.getSiteTokenHash(str, authCredentials.getAccessTokenHash(), runtimeEnv);
        if (str1 == null)
          return SetupSiteTokenHashError.FetchError; 
        this.data.setSiteTokenHash(str1);
        return SetupSiteTokenHashError.None;
      } 
    } 
    return SetupSiteTokenHashError.NoActiveSiteId;
  }
  
  private final SetupSitesError setupSites() {
    SiteM siteM1;
    RuntimeEnv runtimeEnv = this.prefsHelper.getRuntimeEnv();
    if (runtimeEnv == null)
      return SetupSitesError.NoRuntimeEnv; 
    AuthCredentials authCredentials = this.prefsHelper.getAuthCredentials();
    if (authCredentials == null)
      return SetupSitesError.NoAuthCredentials; 
    List<SiteM> list = this.apiHelper.getSites(authCredentials.getAccessTokenHash(), runtimeEnv);
    if (list == null)
      return SetupSitesError.FetchError; 
    this.data.setSites(list);
    if (list.isEmpty())
      return SetupSitesError.UserHasNoSites; 
    String str = this.prefsHelper.getActiveSiteId();
    runtimeEnv = null;
    authCredentials = null;
    if (str != null) {
      AuthCredentials authCredentials1;
      Iterator<SiteM> iterator = list.iterator();
      while (true) {
        authCredentials1 = authCredentials;
        if (iterator.hasNext()) {
          authCredentials1 = (AuthCredentials)iterator.next();
          if (Intrinsics.areEqual(((SiteM)authCredentials1).getId(), str))
            break; 
          continue;
        } 
        break;
      } 
      siteM1 = (SiteM)authCredentials1;
    } 
    SiteM siteM2 = siteM1;
    if (siteM1 == null)
      siteM2 = list.get(0); 
    this.data.setActiveSite(siteM2);
    this.prefsHelper.setActiveSiteId(siteM2.getId());
    return !siteM2.getHasHub() ? SetupSitesError.SiteHasNoHub : SetupSitesError.None;
  }
  
  public final WidgetData getData() {
    return this.data;
  }
  
  public final void update() {
    this.data = new WidgetData(false, null, false, false, null, null, null, null, 255, null);
    setupLanguage();
    setupIsDark();
    if (setupConnection() == SetupConnectionError.NoConnection) {
      saveToPrefsWithState(WidgetState.NoConnection);
      return;
    } 
    SetupAuthError setupAuthError = setupAuth();
    int i = WhenMappings.$EnumSwitchMapping$0[setupAuthError.ordinal()];
    if (i != 1) {
      if (i != 2 && i != 3) {
        if (i == 4 || i == 5) {
          saveToPrefsWithState(WidgetState.OpenAppError);
          return;
        } 
        throw new NoWhenBranchMatchedException();
      } 
      saveToPrefsWithState(WidgetState.NoUser);
      return;
    } 
    SetupSitesError setupSitesError = setupSites();
    switch (WhenMappings.$EnumSwitchMapping$1[setupSitesError.ordinal()]) {
      default:
        throw new NoWhenBranchMatchedException();
      case 6:
        saveToPrefsWithState(WidgetState.NoSites);
        return;
      case 5:
        saveToPrefsWithState(WidgetState.GeneralError);
        return;
      case 3:
      case 4:
        saveToPrefsWithState(WidgetState.NoUser);
        return;
      case 1:
      case 2:
        break;
    } 
    setupShowSites();
    if (this.data.getShowSites()) {
      saveToPrefsWithState(WidgetState.PresentingSites);
      return;
    } 
    SiteM siteM = this.data.getActiveSite();
    if (siteM != null && !siteM.getHasHub()) {
      saveToPrefsWithState(WidgetState.NoHub);
      return;
    } 
    siteM = this.data.getActiveSite();
    if (siteM != null && !siteM.getHasOnlineHub()) {
      saveToPrefsWithState(WidgetState.HubOffline);
      return;
    } 
    SetupSiteTokenHashError setupSiteTokenHashError = setupSiteTokenHash();
    i = WhenMappings.$EnumSwitchMapping$2[setupSiteTokenHashError.ordinal()];
    if (i != 1) {
      if (i != 2 && i != 3 && i != 4) {
        if (i == 5) {
          saveToPrefsWithState(WidgetState.GeneralError);
          return;
        } 
        throw new NoWhenBranchMatchedException();
      } 
      saveToPrefsWithState(WidgetState.NoUser);
      return;
    } 
    SetupActiveModeError setupActiveModeError = setupActiveMode();
    switch (WhenMappings.$EnumSwitchMapping$3[setupActiveModeError.ordinal()]) {
      default:
        throw new NoWhenBranchMatchedException();
      case 6:
        saveToPrefsWithState(WidgetState.HubOffline);
        return;
      case 5:
        saveToPrefsWithState(WidgetState.GeneralError);
        return;
      case 2:
      case 3:
      case 4:
        saveToPrefsWithState(WidgetState.NoUser);
        return;
      case 1:
        break;
    } 
    saveToPrefsWithState(WidgetState.PresentingModesAndShortcuts);
  }
}
