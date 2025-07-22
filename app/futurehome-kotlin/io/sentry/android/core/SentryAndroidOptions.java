package io.sentry.android.core;

import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.util.SentryFrameMetricsCollector;
import io.sentry.protocol.SdkVersion;

public final class SentryAndroidOptions extends SentryOptions {
   private boolean anrEnabled = true;
   private boolean anrReportInDebug;
   private long anrTimeoutIntervalMillis = 5000L;
   private boolean attachAnrThreadDump;
   private boolean attachScreenshot;
   private boolean attachViewHierarchy;
   private SentryAndroidOptions.BeforeCaptureCallback beforeScreenshotCaptureCallback;
   private SentryAndroidOptions.BeforeCaptureCallback beforeViewHierarchyCaptureCallback;
   private boolean collectAdditionalContext;
   private IDebugImagesLoader debugImagesLoader;
   private boolean enableActivityLifecycleBreadcrumbs;
   private boolean enableActivityLifecycleTracingAutoFinish;
   private boolean enableAppComponentBreadcrumbs;
   private boolean enableAppLifecycleBreadcrumbs;
   private boolean enableAutoActivityLifecycleTracing;
   private boolean enableFramesTracking;
   private boolean enableNdk;
   private boolean enableNetworkEventBreadcrumbs;
   private boolean enablePerformanceV2;
   private boolean enableRootCheck;
   private boolean enableScopeSync;
   private boolean enableSystemEventBreadcrumbs;
   private SentryFrameMetricsCollector frameMetricsCollector;
   private String nativeSdkName;
   private NdkHandlerStrategy ndkHandlerStrategy;
   private boolean reportHistoricalAnrs;
   private final long startupCrashDurationThresholdMillis;
   private long startupCrashFlushTimeoutMillis;

   public SentryAndroidOptions() {
      this.anrReportInDebug = false;
      this.enableActivityLifecycleBreadcrumbs = true;
      this.enableAppLifecycleBreadcrumbs = true;
      this.enableSystemEventBreadcrumbs = true;
      this.enableAppComponentBreadcrumbs = true;
      this.enableNetworkEventBreadcrumbs = true;
      this.enableAutoActivityLifecycleTracing = true;
      this.enableActivityLifecycleTracingAutoFinish = true;
      this.debugImagesLoader = NoOpDebugImagesLoader.getInstance();
      this.collectAdditionalContext = true;
      this.startupCrashFlushTimeoutMillis = 5000L;
      this.startupCrashDurationThresholdMillis = 2000L;
      this.enableFramesTracking = true;
      this.nativeSdkName = null;
      this.enableRootCheck = true;
      this.enableNdk = true;
      this.ndkHandlerStrategy = NdkHandlerStrategy.SENTRY_HANDLER_STRATEGY_DEFAULT;
      this.enableScopeSync = true;
      this.reportHistoricalAnrs = false;
      this.attachAnrThreadDump = false;
      this.enablePerformanceV2 = false;
      this.setSentryClientName("sentry.java.android/7.22.1");
      this.setSdkVersion(this.createSdkVersion());
      this.setAttachServerName(false);
   }

   private SdkVersion createSdkVersion() {
      SdkVersion var1 = SdkVersion.updateSdkVersion(this.getSdkVersion(), "sentry.java.android", "7.22.1");
      var1.addPackage("maven:io.sentry:sentry-android-core", "7.22.1");
      return var1;
   }

   public void enableAllAutoBreadcrumbs(boolean var1) {
      this.enableActivityLifecycleBreadcrumbs = var1;
      this.enableAppComponentBreadcrumbs = var1;
      this.enableSystemEventBreadcrumbs = var1;
      this.enableAppLifecycleBreadcrumbs = var1;
      this.enableNetworkEventBreadcrumbs = var1;
      this.setEnableUserInteractionBreadcrumbs(var1);
   }

   public long getAnrTimeoutIntervalMillis() {
      return this.anrTimeoutIntervalMillis;
   }

   public SentryAndroidOptions.BeforeCaptureCallback getBeforeScreenshotCaptureCallback() {
      return this.beforeScreenshotCaptureCallback;
   }

   public SentryAndroidOptions.BeforeCaptureCallback getBeforeViewHierarchyCaptureCallback() {
      return this.beforeViewHierarchyCaptureCallback;
   }

   public IDebugImagesLoader getDebugImagesLoader() {
      return this.debugImagesLoader;
   }

   public SentryFrameMetricsCollector getFrameMetricsCollector() {
      return this.frameMetricsCollector;
   }

   public String getNativeSdkName() {
      return this.nativeSdkName;
   }

   public int getNdkHandlerStrategy() {
      return this.ndkHandlerStrategy.getValue();
   }

   @Deprecated
   public int getProfilingTracesIntervalMillis() {
      return 0;
   }

   public long getStartupCrashDurationThresholdMillis() {
      return 2000L;
   }

   long getStartupCrashFlushTimeoutMillis() {
      return this.startupCrashFlushTimeoutMillis;
   }

   public boolean isAnrEnabled() {
      return this.anrEnabled;
   }

   public boolean isAnrReportInDebug() {
      return this.anrReportInDebug;
   }

   public boolean isAttachAnrThreadDump() {
      return this.attachAnrThreadDump;
   }

   public boolean isAttachScreenshot() {
      return this.attachScreenshot;
   }

   public boolean isAttachViewHierarchy() {
      return this.attachViewHierarchy;
   }

   public boolean isCollectAdditionalContext() {
      return this.collectAdditionalContext;
   }

   public boolean isEnableActivityLifecycleBreadcrumbs() {
      return this.enableActivityLifecycleBreadcrumbs;
   }

   public boolean isEnableActivityLifecycleTracingAutoFinish() {
      return this.enableActivityLifecycleTracingAutoFinish;
   }

   public boolean isEnableAppComponentBreadcrumbs() {
      return this.enableAppComponentBreadcrumbs;
   }

   public boolean isEnableAppLifecycleBreadcrumbs() {
      return this.enableAppLifecycleBreadcrumbs;
   }

   public boolean isEnableAutoActivityLifecycleTracing() {
      return this.enableAutoActivityLifecycleTracing;
   }

   public boolean isEnableFramesTracking() {
      return this.enableFramesTracking;
   }

   public boolean isEnableNdk() {
      return this.enableNdk;
   }

   public boolean isEnableNetworkEventBreadcrumbs() {
      return this.enableNetworkEventBreadcrumbs;
   }

   public boolean isEnablePerformanceV2() {
      return this.enablePerformanceV2;
   }

   public boolean isEnableRootCheck() {
      return this.enableRootCheck;
   }

   public boolean isEnableScopeSync() {
      return this.enableScopeSync;
   }

   public boolean isEnableSystemEventBreadcrumbs() {
      return this.enableSystemEventBreadcrumbs;
   }

   public boolean isReportHistoricalAnrs() {
      return this.reportHistoricalAnrs;
   }

   public void setAnrEnabled(boolean var1) {
      this.anrEnabled = var1;
   }

   public void setAnrReportInDebug(boolean var1) {
      this.anrReportInDebug = var1;
   }

   public void setAnrTimeoutIntervalMillis(long var1) {
      this.anrTimeoutIntervalMillis = var1;
   }

   public void setAttachAnrThreadDump(boolean var1) {
      this.attachAnrThreadDump = var1;
   }

   public void setAttachScreenshot(boolean var1) {
      this.attachScreenshot = var1;
   }

   public void setAttachViewHierarchy(boolean var1) {
      this.attachViewHierarchy = var1;
   }

   public void setBeforeScreenshotCaptureCallback(SentryAndroidOptions.BeforeCaptureCallback var1) {
      this.beforeScreenshotCaptureCallback = var1;
   }

   public void setBeforeViewHierarchyCaptureCallback(SentryAndroidOptions.BeforeCaptureCallback var1) {
      this.beforeViewHierarchyCaptureCallback = var1;
   }

   public void setCollectAdditionalContext(boolean var1) {
      this.collectAdditionalContext = var1;
   }

   public void setDebugImagesLoader(IDebugImagesLoader var1) {
      if (var1 == null) {
         var1 = NoOpDebugImagesLoader.getInstance();
      }

      this.debugImagesLoader = (IDebugImagesLoader)var1;
   }

   public void setEnableActivityLifecycleBreadcrumbs(boolean var1) {
      this.enableActivityLifecycleBreadcrumbs = var1;
   }

   public void setEnableActivityLifecycleTracingAutoFinish(boolean var1) {
      this.enableActivityLifecycleTracingAutoFinish = var1;
   }

   public void setEnableAppComponentBreadcrumbs(boolean var1) {
      this.enableAppComponentBreadcrumbs = var1;
   }

   public void setEnableAppLifecycleBreadcrumbs(boolean var1) {
      this.enableAppLifecycleBreadcrumbs = var1;
   }

   public void setEnableAutoActivityLifecycleTracing(boolean var1) {
      this.enableAutoActivityLifecycleTracing = var1;
   }

   public void setEnableFramesTracking(boolean var1) {
      this.enableFramesTracking = var1;
   }

   public void setEnableNdk(boolean var1) {
      this.enableNdk = var1;
   }

   public void setEnableNetworkEventBreadcrumbs(boolean var1) {
      this.enableNetworkEventBreadcrumbs = var1;
   }

   public void setEnablePerformanceV2(boolean var1) {
      this.enablePerformanceV2 = var1;
   }

   public void setEnableRootCheck(boolean var1) {
      this.enableRootCheck = var1;
   }

   public void setEnableScopeSync(boolean var1) {
      this.enableScopeSync = var1;
   }

   public void setEnableSystemEventBreadcrumbs(boolean var1) {
      this.enableSystemEventBreadcrumbs = var1;
   }

   public void setFrameMetricsCollector(SentryFrameMetricsCollector var1) {
      this.frameMetricsCollector = var1;
   }

   public void setNativeHandlerStrategy(NdkHandlerStrategy var1) {
      this.ndkHandlerStrategy = var1;
   }

   public void setNativeSdkName(String var1) {
      this.nativeSdkName = var1;
   }

   @Deprecated
   public void setProfilingTracesIntervalMillis(int var1) {
   }

   public void setReportHistoricalAnrs(boolean var1) {
      this.reportHistoricalAnrs = var1;
   }

   void setStartupCrashFlushTimeoutMillis(long var1) {
      this.startupCrashFlushTimeoutMillis = var1;
   }

   public interface BeforeCaptureCallback {
      boolean execute(SentryEvent var1, Hint var2, boolean var3);
   }
}
