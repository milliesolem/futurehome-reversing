package io.sentry;

import io.sentry.backpressure.IBackpressureMonitor;
import io.sentry.backpressure.NoOpBackpressureMonitor;
import io.sentry.cache.IEnvelopeCache;
import io.sentry.clientreport.ClientReportRecorder;
import io.sentry.clientreport.IClientReportRecorder;
import io.sentry.clientreport.NoOpClientReportRecorder;
import io.sentry.internal.debugmeta.IDebugMetaLoader;
import io.sentry.internal.debugmeta.NoOpDebugMetaLoader;
import io.sentry.internal.gestures.GestureTargetLocator;
import io.sentry.internal.modules.IModulesLoader;
import io.sentry.internal.modules.NoOpModulesLoader;
import io.sentry.internal.viewhierarchy.ViewHierarchyExporter;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryTransaction;
import io.sentry.transport.ITransportGate;
import io.sentry.transport.NoOpEnvelopeCache;
import io.sentry.transport.NoOpTransportGate;
import io.sentry.util.LazyEvaluator;
import io.sentry.util.Platform;
import io.sentry.util.SampleRateUtils;
import io.sentry.util.StringUtils;
import io.sentry.util.thread.IMainThreadChecker;
import io.sentry.util.thread.NoOpMainThreadChecker;
import j..util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.net.ssl.SSLSocketFactory;

public class SentryOptions {
   static final SentryLevel DEFAULT_DIAGNOSTIC_LEVEL = SentryLevel.DEBUG;
   private static final String DEFAULT_ENVIRONMENT = "production";
   public static final String DEFAULT_PROPAGATION_TARGETS = ".*";
   private boolean attachServerName;
   private boolean attachStacktrace;
   private boolean attachThreads;
   private IBackpressureMonitor backpressureMonitor;
   private SentryOptions.BeforeBreadcrumbCallback beforeBreadcrumb;
   private SentryOptions.BeforeEmitMetricCallback beforeEmitMetricCallback;
   private SentryOptions.BeforeEnvelopeCallback beforeEnvelopeCallback;
   private SentryOptions.BeforeSendCallback beforeSend;
   private SentryOptions.BeforeSendReplayCallback beforeSendReplay;
   private SentryOptions.BeforeSendTransactionCallback beforeSendTransaction;
   private final Set<String> bundleIds;
   private String cacheDirPath;
   IClientReportRecorder clientReportRecorder;
   private IConnectionStatusProvider connectionStatusProvider;
   private int connectionTimeoutMillis;
   private final List<String> contextTags;
   private SentryOptions.Cron cron;
   private final LazyEvaluator<SentryDateProvider> dateProvider;
   private boolean debug;
   private IDebugMetaLoader debugMetaLoader;
   private final List<String> defaultTracePropagationTargets;
   private SentryLevel diagnosticLevel;
   private String dist;
   private String distinctId;
   private String dsn;
   private String dsnHash;
   private boolean enableAppStartProfiling;
   private boolean enableAutoSessionTracking;
   private boolean enableBackpressureHandling;
   private boolean enableDeduplication;
   private boolean enableDefaultTagsForMetrics;
   private boolean enableExternalConfiguration;
   private boolean enableMetrics;
   private boolean enablePrettySerializationOutput;
   private boolean enableScopePersistence;
   private boolean enableScreenTracking;
   private boolean enableShutdownHook;
   private boolean enableSpanLocalMetricAggregation;
   private boolean enableSpotlight;
   private boolean enableTimeToFullDisplayTracing;
   private Boolean enableTracing;
   private boolean enableUncaughtExceptionHandler;
   private boolean enableUserInteractionBreadcrumbs;
   private boolean enableUserInteractionTracing;
   private boolean enabled;
   private IEnvelopeCache envelopeDiskCache;
   private final LazyEvaluator<IEnvelopeReader> envelopeReader;
   private String environment;
   private final List<EventProcessor> eventProcessors;
   private ISentryExecutorService executorService;
   private final ExperimentalOptions experimental;
   private long flushTimeoutMillis;
   private FullyDisplayedReporter fullyDisplayedReporter;
   private final List<GestureTargetLocator> gestureTargetLocators;
   private Long idleTimeout;
   private List<String> ignoredCheckIns;
   private final Set<Class<? extends Throwable>> ignoredExceptionsForType;
   private final List<String> inAppExcludes;
   private final List<String> inAppIncludes;
   private Instrumenter instrumenter;
   private final List<Integration> integrations;
   private ILogger logger;
   private IMainThreadChecker mainThreadChecker;
   private long maxAttachmentSize;
   private int maxBreadcrumbs;
   private int maxCacheItems;
   private int maxDepth;
   private int maxQueueSize;
   private SentryOptions.RequestSize maxRequestBodySize;
   private int maxSpans;
   private long maxTraceFileSize;
   private IModulesLoader modulesLoader;
   private final List<IScopeObserver> observers;
   private final List<IOptionsObserver> optionsObservers;
   private final LazyEvaluator<Dsn> parsedDsn;
   private final List<IPerformanceCollector> performanceCollectors;
   private boolean printUncaughtStackTrace;
   private Double profilesSampleRate;
   private SentryOptions.ProfilesSamplerCallback profilesSampler;
   private int profilingTracesHz;
   private String proguardUuid;
   private SentryOptions.Proxy proxy;
   private int readTimeoutMillis;
   private String release;
   private ReplayController replayController;
   private Double sampleRate;
   private SdkVersion sdkVersion;
   private boolean sendClientReports;
   private boolean sendDefaultPii;
   private boolean sendModules;
   private String sentryClientName;
   private final LazyEvaluator<ISerializer> serializer;
   private String serverName;
   private long sessionFlushTimeoutMillis;
   private SentryReplayOptions sessionReplay;
   private long sessionTrackingIntervalMillis;
   private long shutdownTimeoutMillis;
   private String spotlightConnectionUrl;
   private SSLSocketFactory sslSocketFactory;
   private final Map<String, String> tags;
   private boolean traceOptionsRequests;
   private List<String> tracePropagationTargets;
   private boolean traceSampling;
   private Double tracesSampleRate;
   private SentryOptions.TracesSamplerCallback tracesSampler;
   private TransactionPerformanceCollector transactionPerformanceCollector;
   private ITransactionProfiler transactionProfiler;
   private ITransportFactory transportFactory;
   private ITransportGate transportGate;
   private final List<ViewHierarchyExporter> viewHierarchyExporters;

   public SentryOptions() {
      this(false);
   }

   private SentryOptions(boolean var1) {
      CopyOnWriteArrayList var3 = new CopyOnWriteArrayList();
      this.eventProcessors = var3;
      this.ignoredExceptionsForType = new CopyOnWriteArraySet<>();
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      this.integrations = var2;
      this.bundleIds = new CopyOnWriteArraySet<>();
      this.parsedDsn = new LazyEvaluator<>(new SentryOptions$$ExternalSyntheticLambda0(this));
      this.shutdownTimeoutMillis = 2000L;
      this.flushTimeoutMillis = 15000L;
      this.sessionFlushTimeoutMillis = 15000L;
      this.logger = NoOpLogger.getInstance();
      this.diagnosticLevel = DEFAULT_DIAGNOSTIC_LEVEL;
      this.serializer = new LazyEvaluator<>(new SentryOptions$$ExternalSyntheticLambda1(this));
      this.envelopeReader = new LazyEvaluator<>(new SentryOptions$$ExternalSyntheticLambda2(this));
      this.maxDepth = 100;
      this.maxCacheItems = 30;
      this.maxQueueSize = 30;
      this.maxBreadcrumbs = 100;
      this.inAppExcludes = new CopyOnWriteArrayList<>();
      this.inAppIncludes = new CopyOnWriteArrayList<>();
      this.transportFactory = NoOpTransportFactory.getInstance();
      this.transportGate = NoOpTransportGate.getInstance();
      this.attachStacktrace = true;
      this.enableAutoSessionTracking = true;
      this.sessionTrackingIntervalMillis = 30000L;
      this.attachServerName = true;
      this.enableUncaughtExceptionHandler = true;
      this.printUncaughtStackTrace = false;
      this.executorService = NoOpSentryExecutorService.getInstance();
      this.connectionTimeoutMillis = 5000;
      this.readTimeoutMillis = 5000;
      this.envelopeDiskCache = NoOpEnvelopeCache.getInstance();
      this.sendDefaultPii = false;
      this.observers = new CopyOnWriteArrayList<>();
      this.optionsObservers = new CopyOnWriteArrayList<>();
      this.tags = new ConcurrentHashMap();
      this.maxAttachmentSize = 20971520L;
      this.enableDeduplication = true;
      this.maxSpans = 1000;
      this.enableShutdownHook = true;
      this.maxRequestBodySize = SentryOptions.RequestSize.NONE;
      this.traceSampling = true;
      this.maxTraceFileSize = 5242880L;
      this.transactionProfiler = NoOpTransactionProfiler.getInstance();
      this.tracePropagationTargets = null;
      this.defaultTracePropagationTargets = Collections.singletonList(".*");
      this.idleTimeout = 3000L;
      this.contextTags = new CopyOnWriteArrayList<>();
      this.sendClientReports = true;
      this.clientReportRecorder = new ClientReportRecorder(this);
      this.modulesLoader = NoOpModulesLoader.getInstance();
      this.debugMetaLoader = NoOpDebugMetaLoader.getInstance();
      this.enableUserInteractionTracing = false;
      this.enableUserInteractionBreadcrumbs = true;
      this.instrumenter = Instrumenter.SENTRY;
      this.gestureTargetLocators = new ArrayList<>();
      this.viewHierarchyExporters = new ArrayList<>();
      this.mainThreadChecker = NoOpMainThreadChecker.getInstance();
      this.traceOptionsRequests = true;
      this.dateProvider = new LazyEvaluator<>(new SentryOptions$$ExternalSyntheticLambda3());
      this.performanceCollectors = new ArrayList<>();
      this.transactionPerformanceCollector = NoOpTransactionPerformanceCollector.getInstance();
      this.enableTimeToFullDisplayTracing = false;
      this.fullyDisplayedReporter = FullyDisplayedReporter.getInstance();
      this.connectionStatusProvider = new NoOpConnectionStatusProvider();
      this.enabled = true;
      this.enablePrettySerializationOutput = true;
      this.sendModules = true;
      this.enableSpotlight = false;
      this.enableScopePersistence = true;
      this.ignoredCheckIns = null;
      this.backpressureMonitor = NoOpBackpressureMonitor.getInstance();
      this.enableBackpressureHandling = true;
      this.enableAppStartProfiling = false;
      this.enableMetrics = false;
      this.enableDefaultTagsForMetrics = true;
      this.enableSpanLocalMetricAggregation = true;
      this.beforeEmitMetricCallback = null;
      this.profilingTracesHz = 101;
      this.cron = null;
      this.replayController = NoOpReplayController.getInstance();
      this.enableScreenTracking = true;
      SdkVersion var4 = this.createSdkVersion();
      this.experimental = new ExperimentalOptions(var1, var4);
      this.sessionReplay = new SentryReplayOptions(var1, var4);
      if (!var1) {
         this.executorService = new SentryExecutorService();
         var2.add(new UncaughtExceptionHandlerIntegration());
         var2.add(new ShutdownHookIntegration());
         var2.add(new SpotlightIntegration());
         var3.add(new MainEventProcessor(this));
         var3.add(new DuplicateEventDetectionEventProcessor(this));
         if (Platform.isJvm()) {
            var3.add(new SentryRuntimeEventProcessor());
         }

         this.setSentryClientName("sentry.java/7.22.1");
         this.setSdkVersion(var4);
         this.addPackageInfo();
      }
   }

   private void addPackageInfo() {
      SentryIntegrationPackageStorage.getInstance().addPackage("maven:io.sentry:sentry", "7.22.1");
   }

   private SdkVersion createSdkVersion() {
      SdkVersion var1 = new SdkVersion("sentry.java", "7.22.1");
      var1.setVersion("7.22.1");
      return var1;
   }

   public static SentryOptions empty() {
      return new SentryOptions(true);
   }

   public void addBundleId(String var1) {
      if (var1 != null) {
         var1 = var1.trim();
         if (!var1.isEmpty()) {
            this.bundleIds.add(var1);
         }
      }
   }

   public void addContextTag(String var1) {
      this.contextTags.add(var1);
   }

   public void addEventProcessor(EventProcessor var1) {
      this.eventProcessors.add(var1);
   }

   public void addIgnoredExceptionForType(Class<? extends Throwable> var1) {
      this.ignoredExceptionsForType.add(var1);
   }

   public void addInAppExclude(String var1) {
      this.inAppExcludes.add(var1);
   }

   public void addInAppInclude(String var1) {
      this.inAppIncludes.add(var1);
   }

   public void addIntegration(Integration var1) {
      this.integrations.add(var1);
   }

   public void addOptionsObserver(IOptionsObserver var1) {
      this.optionsObservers.add(var1);
   }

   public void addPerformanceCollector(IPerformanceCollector var1) {
      this.performanceCollectors.add(var1);
   }

   public void addScopeObserver(IScopeObserver var1) {
      this.observers.add(var1);
   }

   @Deprecated
   public void addTracingOrigin(String var1) {
      if (this.tracePropagationTargets == null) {
         this.tracePropagationTargets = new CopyOnWriteArrayList<>();
      }

      if (!var1.isEmpty()) {
         this.tracePropagationTargets.add(var1);
      }
   }

   boolean containsIgnoredExceptionForType(Throwable var1) {
      return this.ignoredExceptionsForType.contains(var1.getClass());
   }

   public IBackpressureMonitor getBackpressureMonitor() {
      return this.backpressureMonitor;
   }

   public SentryOptions.BeforeBreadcrumbCallback getBeforeBreadcrumb() {
      return this.beforeBreadcrumb;
   }

   public SentryOptions.BeforeEmitMetricCallback getBeforeEmitMetricCallback() {
      return this.beforeEmitMetricCallback;
   }

   public SentryOptions.BeforeEnvelopeCallback getBeforeEnvelopeCallback() {
      return this.beforeEnvelopeCallback;
   }

   public SentryOptions.BeforeSendCallback getBeforeSend() {
      return this.beforeSend;
   }

   public SentryOptions.BeforeSendReplayCallback getBeforeSendReplay() {
      return this.beforeSendReplay;
   }

   public SentryOptions.BeforeSendTransactionCallback getBeforeSendTransaction() {
      return this.beforeSendTransaction;
   }

   public Set<String> getBundleIds() {
      return this.bundleIds;
   }

   public String getCacheDirPath() {
      String var1 = this.cacheDirPath;
      if (var1 != null && !var1.isEmpty()) {
         if (this.dsnHash != null) {
            var1 = new File(this.cacheDirPath, this.dsnHash).getAbsolutePath();
         } else {
            var1 = this.cacheDirPath;
         }

         return var1;
      } else {
         return null;
      }
   }

   String getCacheDirPathWithoutDsn() {
      String var1 = this.cacheDirPath;
      return var1 != null && !var1.isEmpty() ? this.cacheDirPath : null;
   }

   public IClientReportRecorder getClientReportRecorder() {
      return this.clientReportRecorder;
   }

   public IConnectionStatusProvider getConnectionStatusProvider() {
      return this.connectionStatusProvider;
   }

   public int getConnectionTimeoutMillis() {
      return this.connectionTimeoutMillis;
   }

   public List<String> getContextTags() {
      return this.contextTags;
   }

   public SentryOptions.Cron getCron() {
      return this.cron;
   }

   public SentryDateProvider getDateProvider() {
      return this.dateProvider.getValue();
   }

   public IDebugMetaLoader getDebugMetaLoader() {
      return this.debugMetaLoader;
   }

   public SentryLevel getDiagnosticLevel() {
      return this.diagnosticLevel;
   }

   public String getDist() {
      return this.dist;
   }

   public String getDistinctId() {
      return this.distinctId;
   }

   public String getDsn() {
      return this.dsn;
   }

   @Deprecated
   public Boolean getEnableTracing() {
      return this.enableTracing;
   }

   public IEnvelopeCache getEnvelopeDiskCache() {
      return this.envelopeDiskCache;
   }

   public IEnvelopeReader getEnvelopeReader() {
      return this.envelopeReader.getValue();
   }

   public String getEnvironment() {
      String var1 = this.environment;
      if (var1 == null) {
         var1 = "production";
      }

      return var1;
   }

   public List<EventProcessor> getEventProcessors() {
      return this.eventProcessors;
   }

   public ISentryExecutorService getExecutorService() {
      return this.executorService;
   }

   public ExperimentalOptions getExperimental() {
      return this.experimental;
   }

   public long getFlushTimeoutMillis() {
      return this.flushTimeoutMillis;
   }

   public FullyDisplayedReporter getFullyDisplayedReporter() {
      return this.fullyDisplayedReporter;
   }

   public List<GestureTargetLocator> getGestureTargetLocators() {
      return this.gestureTargetLocators;
   }

   public Long getIdleTimeout() {
      return this.idleTimeout;
   }

   public List<String> getIgnoredCheckIns() {
      return this.ignoredCheckIns;
   }

   public Set<Class<? extends Throwable>> getIgnoredExceptionsForType() {
      return this.ignoredExceptionsForType;
   }

   public List<String> getInAppExcludes() {
      return this.inAppExcludes;
   }

   public List<String> getInAppIncludes() {
      return this.inAppIncludes;
   }

   public Instrumenter getInstrumenter() {
      return this.instrumenter;
   }

   public List<Integration> getIntegrations() {
      return this.integrations;
   }

   public ILogger getLogger() {
      return this.logger;
   }

   public IMainThreadChecker getMainThreadChecker() {
      return this.mainThreadChecker;
   }

   public long getMaxAttachmentSize() {
      return this.maxAttachmentSize;
   }

   public int getMaxBreadcrumbs() {
      return this.maxBreadcrumbs;
   }

   public int getMaxCacheItems() {
      return this.maxCacheItems;
   }

   public int getMaxDepth() {
      return this.maxDepth;
   }

   public int getMaxQueueSize() {
      return this.maxQueueSize;
   }

   public SentryOptions.RequestSize getMaxRequestBodySize() {
      return this.maxRequestBodySize;
   }

   public int getMaxSpans() {
      return this.maxSpans;
   }

   public long getMaxTraceFileSize() {
      return this.maxTraceFileSize;
   }

   public IModulesLoader getModulesLoader() {
      return this.modulesLoader;
   }

   public List<IOptionsObserver> getOptionsObservers() {
      return this.optionsObservers;
   }

   public String getOutboxPath() {
      String var1 = this.getCacheDirPath();
      return var1 == null ? null : new File(var1, "outbox").getAbsolutePath();
   }

   public List<IPerformanceCollector> getPerformanceCollectors() {
      return this.performanceCollectors;
   }

   public Double getProfilesSampleRate() {
      return this.profilesSampleRate;
   }

   public SentryOptions.ProfilesSamplerCallback getProfilesSampler() {
      return this.profilesSampler;
   }

   public String getProfilingTracesDirPath() {
      String var1 = this.getCacheDirPath();
      return var1 == null ? null : new File(var1, "profiling_traces").getAbsolutePath();
   }

   public int getProfilingTracesHz() {
      return this.profilingTracesHz;
   }

   public String getProguardUuid() {
      return this.proguardUuid;
   }

   public SentryOptions.Proxy getProxy() {
      return this.proxy;
   }

   public int getReadTimeoutMillis() {
      return this.readTimeoutMillis;
   }

   public String getRelease() {
      return this.release;
   }

   public ReplayController getReplayController() {
      return this.replayController;
   }

   public Double getSampleRate() {
      return this.sampleRate;
   }

   public List<IScopeObserver> getScopeObservers() {
      return this.observers;
   }

   public SdkVersion getSdkVersion() {
      return this.sdkVersion;
   }

   public String getSentryClientName() {
      return this.sentryClientName;
   }

   public ISerializer getSerializer() {
      return this.serializer.getValue();
   }

   public String getServerName() {
      return this.serverName;
   }

   public long getSessionFlushTimeoutMillis() {
      return this.sessionFlushTimeoutMillis;
   }

   public SentryReplayOptions getSessionReplay() {
      return this.sessionReplay;
   }

   public long getSessionTrackingIntervalMillis() {
      return this.sessionTrackingIntervalMillis;
   }

   @Deprecated
   public long getShutdownTimeout() {
      return this.shutdownTimeoutMillis;
   }

   public long getShutdownTimeoutMillis() {
      return this.shutdownTimeoutMillis;
   }

   public String getSpotlightConnectionUrl() {
      return this.spotlightConnectionUrl;
   }

   public SSLSocketFactory getSslSocketFactory() {
      return this.sslSocketFactory;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   public List<String> getTracePropagationTargets() {
      List var2 = this.tracePropagationTargets;
      List var1 = var2;
      if (var2 == null) {
         var1 = this.defaultTracePropagationTargets;
      }

      return var1;
   }

   public Double getTracesSampleRate() {
      return this.tracesSampleRate;
   }

   public SentryOptions.TracesSamplerCallback getTracesSampler() {
      return this.tracesSampler;
   }

   @Deprecated
   public List<String> getTracingOrigins() {
      return this.getTracePropagationTargets();
   }

   public TransactionPerformanceCollector getTransactionPerformanceCollector() {
      return this.transactionPerformanceCollector;
   }

   public ITransactionProfiler getTransactionProfiler() {
      return this.transactionProfiler;
   }

   public ITransportFactory getTransportFactory() {
      return this.transportFactory;
   }

   public ITransportGate getTransportGate() {
      return this.transportGate;
   }

   public final List<ViewHierarchyExporter> getViewHierarchyExporters() {
      return this.viewHierarchyExporters;
   }

   public boolean isAttachServerName() {
      return this.attachServerName;
   }

   public boolean isAttachStacktrace() {
      return this.attachStacktrace;
   }

   public boolean isAttachThreads() {
      return this.attachThreads;
   }

   public boolean isDebug() {
      return this.debug;
   }

   public boolean isEnableAppStartProfiling() {
      boolean var1;
      if (this.isProfilingEnabled() && this.enableAppStartProfiling) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isEnableAutoSessionTracking() {
      return this.enableAutoSessionTracking;
   }

   public boolean isEnableBackpressureHandling() {
      return this.enableBackpressureHandling;
   }

   public boolean isEnableDeduplication() {
      return this.enableDeduplication;
   }

   public boolean isEnableDefaultTagsForMetrics() {
      boolean var1;
      if (this.isEnableMetrics() && this.enableDefaultTagsForMetrics) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isEnableExternalConfiguration() {
      return this.enableExternalConfiguration;
   }

   public boolean isEnableMetrics() {
      return this.enableMetrics;
   }

   public boolean isEnablePrettySerializationOutput() {
      return this.enablePrettySerializationOutput;
   }

   public boolean isEnableScopePersistence() {
      return this.enableScopePersistence;
   }

   public boolean isEnableScreenTracking() {
      return this.enableScreenTracking;
   }

   public boolean isEnableShutdownHook() {
      return this.enableShutdownHook;
   }

   public boolean isEnableSpanLocalMetricAggregation() {
      boolean var1;
      if (this.isEnableMetrics() && this.enableSpanLocalMetricAggregation) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isEnableSpotlight() {
      return this.enableSpotlight;
   }

   public boolean isEnableTimeToFullDisplayTracing() {
      return this.enableTimeToFullDisplayTracing;
   }

   public boolean isEnableUncaughtExceptionHandler() {
      return this.enableUncaughtExceptionHandler;
   }

   public boolean isEnableUserInteractionBreadcrumbs() {
      return this.enableUserInteractionBreadcrumbs;
   }

   public boolean isEnableUserInteractionTracing() {
      return this.enableUserInteractionTracing;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isPrintUncaughtStackTrace() {
      return this.printUncaughtStackTrace;
   }

   public boolean isProfilingEnabled() {
      boolean var1;
      if ((this.getProfilesSampleRate() == null || !(this.getProfilesSampleRate() > 0.0)) && this.getProfilesSampler() == null) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isSendClientReports() {
      return this.sendClientReports;
   }

   public boolean isSendDefaultPii() {
      return this.sendDefaultPii;
   }

   public boolean isSendModules() {
      return this.sendModules;
   }

   public boolean isTraceOptionsRequests() {
      return this.traceOptionsRequests;
   }

   public boolean isTraceSampling() {
      return this.traceSampling;
   }

   public boolean isTracingEnabled() {
      Boolean var2 = this.enableTracing;
      if (var2 != null) {
         return var2;
      } else {
         boolean var1;
         if (this.getTracesSampleRate() == null && this.getTracesSampler() == null) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }
   }

   void loadLazyFields() {
      this.getSerializer();
      this.retrieveParsedDsn();
      this.getEnvelopeReader();
      this.getDateProvider();
   }

   public void merge(ExternalOptions var1) {
      if (var1.getDsn() != null) {
         this.setDsn(var1.getDsn());
      }

      if (var1.getEnvironment() != null) {
         this.setEnvironment(var1.getEnvironment());
      }

      if (var1.getRelease() != null) {
         this.setRelease(var1.getRelease());
      }

      if (var1.getDist() != null) {
         this.setDist(var1.getDist());
      }

      if (var1.getServerName() != null) {
         this.setServerName(var1.getServerName());
      }

      if (var1.getProxy() != null) {
         this.setProxy(var1.getProxy());
      }

      if (var1.getEnableUncaughtExceptionHandler() != null) {
         this.setEnableUncaughtExceptionHandler(var1.getEnableUncaughtExceptionHandler());
      }

      if (var1.getPrintUncaughtStackTrace() != null) {
         this.setPrintUncaughtStackTrace(var1.getPrintUncaughtStackTrace());
      }

      if (var1.getEnableTracing() != null) {
         this.setEnableTracing(var1.getEnableTracing());
      }

      if (var1.getTracesSampleRate() != null) {
         this.setTracesSampleRate(var1.getTracesSampleRate());
      }

      if (var1.getProfilesSampleRate() != null) {
         this.setProfilesSampleRate(var1.getProfilesSampleRate());
      }

      if (var1.getDebug() != null) {
         this.setDebug(var1.getDebug());
      }

      if (var1.getEnableDeduplication() != null) {
         this.setEnableDeduplication(var1.getEnableDeduplication());
      }

      if (var1.getSendClientReports() != null) {
         this.setSendClientReports(var1.getSendClientReports());
      }

      for (Entry var3 : new HashMap<>(var1.getTags()).entrySet()) {
         this.tags.put((String)var3.getKey(), (String)var3.getValue());
      }

      Iterator var4 = new ArrayList<>(var1.getInAppIncludes()).iterator();

      while (var4.hasNext()) {
         this.addInAppInclude((String)var4.next());
      }

      var4 = new ArrayList<>(var1.getInAppExcludes()).iterator();

      while (var4.hasNext()) {
         this.addInAppExclude((String)var4.next());
      }

      var4 = new HashSet<>(var1.getIgnoredExceptionsForType()).iterator();

      while (var4.hasNext()) {
         this.addIgnoredExceptionForType((Class<? extends Throwable>)var4.next());
      }

      if (var1.getTracePropagationTargets() != null) {
         this.setTracePropagationTargets(new ArrayList<>(var1.getTracePropagationTargets()));
      }

      var4 = new ArrayList<>(var1.getContextTags()).iterator();

      while (var4.hasNext()) {
         this.addContextTag((String)var4.next());
      }

      if (var1.getProguardUuid() != null) {
         this.setProguardUuid(var1.getProguardUuid());
      }

      if (var1.getIdleTimeout() != null) {
         this.setIdleTimeout(var1.getIdleTimeout());
      }

      var4 = var1.getBundleIds().iterator();

      while (var4.hasNext()) {
         this.addBundleId((String)var4.next());
      }

      if (var1.isEnabled() != null) {
         this.setEnabled(var1.isEnabled());
      }

      if (var1.isEnablePrettySerializationOutput() != null) {
         this.setEnablePrettySerializationOutput(var1.isEnablePrettySerializationOutput());
      }

      if (var1.isSendModules() != null) {
         this.setSendModules(var1.isSendModules());
      }

      if (var1.getIgnoredCheckIns() != null) {
         this.setIgnoredCheckIns(new ArrayList<>(var1.getIgnoredCheckIns()));
      }

      if (var1.isEnableBackpressureHandling() != null) {
         this.setEnableBackpressureHandling(var1.isEnableBackpressureHandling());
      }

      if (var1.getCron() != null) {
         if (this.getCron() == null) {
            this.setCron(var1.getCron());
         } else {
            if (var1.getCron().getDefaultCheckinMargin() != null) {
               this.getCron().setDefaultCheckinMargin(var1.getCron().getDefaultCheckinMargin());
            }

            if (var1.getCron().getDefaultMaxRuntime() != null) {
               this.getCron().setDefaultMaxRuntime(var1.getCron().getDefaultMaxRuntime());
            }

            if (var1.getCron().getDefaultTimezone() != null) {
               this.getCron().setDefaultTimezone(var1.getCron().getDefaultTimezone());
            }

            if (var1.getCron().getDefaultFailureIssueThreshold() != null) {
               this.getCron().setDefaultFailureIssueThreshold(var1.getCron().getDefaultFailureIssueThreshold());
            }

            if (var1.getCron().getDefaultRecoveryThreshold() != null) {
               this.getCron().setDefaultRecoveryThreshold(var1.getCron().getDefaultRecoveryThreshold());
            }
         }
      }
   }

   Dsn retrieveParsedDsn() throws IllegalArgumentException {
      return this.parsedDsn.getValue();
   }

   public void setAttachServerName(boolean var1) {
      this.attachServerName = var1;
   }

   public void setAttachStacktrace(boolean var1) {
      this.attachStacktrace = var1;
   }

   public void setAttachThreads(boolean var1) {
      this.attachThreads = var1;
   }

   public void setBackpressureMonitor(IBackpressureMonitor var1) {
      this.backpressureMonitor = var1;
   }

   public void setBeforeBreadcrumb(SentryOptions.BeforeBreadcrumbCallback var1) {
      this.beforeBreadcrumb = var1;
   }

   public void setBeforeEmitMetricCallback(SentryOptions.BeforeEmitMetricCallback var1) {
      this.beforeEmitMetricCallback = var1;
   }

   public void setBeforeEnvelopeCallback(SentryOptions.BeforeEnvelopeCallback var1) {
      this.beforeEnvelopeCallback = var1;
   }

   public void setBeforeSend(SentryOptions.BeforeSendCallback var1) {
      this.beforeSend = var1;
   }

   public void setBeforeSendReplay(SentryOptions.BeforeSendReplayCallback var1) {
      this.beforeSendReplay = var1;
   }

   public void setBeforeSendTransaction(SentryOptions.BeforeSendTransactionCallback var1) {
      this.beforeSendTransaction = var1;
   }

   public void setCacheDirPath(String var1) {
      this.cacheDirPath = var1;
   }

   public void setConnectionStatusProvider(IConnectionStatusProvider var1) {
      this.connectionStatusProvider = var1;
   }

   public void setConnectionTimeoutMillis(int var1) {
      this.connectionTimeoutMillis = var1;
   }

   public void setCron(SentryOptions.Cron var1) {
      this.cron = var1;
   }

   public void setDateProvider(SentryDateProvider var1) {
      this.dateProvider.setValue(var1);
   }

   public void setDebug(boolean var1) {
      this.debug = var1;
   }

   public void setDebugMetaLoader(IDebugMetaLoader var1) {
      if (var1 == null) {
         var1 = NoOpDebugMetaLoader.getInstance();
      }

      this.debugMetaLoader = (IDebugMetaLoader)var1;
   }

   public void setDiagnosticLevel(SentryLevel var1) {
      if (var1 == null) {
         var1 = DEFAULT_DIAGNOSTIC_LEVEL;
      }

      this.diagnosticLevel = var1;
   }

   public void setDist(String var1) {
      this.dist = var1;
   }

   public void setDistinctId(String var1) {
      this.distinctId = var1;
   }

   public void setDsn(String var1) {
      this.dsn = var1;
      this.parsedDsn.resetValue();
      this.dsnHash = StringUtils.calculateStringHash(this.dsn, this.logger);
   }

   public void setEnableAppStartProfiling(boolean var1) {
      this.enableAppStartProfiling = var1;
   }

   public void setEnableAutoSessionTracking(boolean var1) {
      this.enableAutoSessionTracking = var1;
   }

   public void setEnableBackpressureHandling(boolean var1) {
      this.enableBackpressureHandling = var1;
   }

   public void setEnableDeduplication(boolean var1) {
      this.enableDeduplication = var1;
   }

   public void setEnableDefaultTagsForMetrics(boolean var1) {
      this.enableDefaultTagsForMetrics = var1;
   }

   public void setEnableExternalConfiguration(boolean var1) {
      this.enableExternalConfiguration = var1;
   }

   public void setEnableMetrics(boolean var1) {
      this.enableMetrics = var1;
   }

   public void setEnablePrettySerializationOutput(boolean var1) {
      this.enablePrettySerializationOutput = var1;
   }

   public void setEnableScopePersistence(boolean var1) {
      this.enableScopePersistence = var1;
   }

   public void setEnableScreenTracking(boolean var1) {
      this.enableScreenTracking = var1;
   }

   public void setEnableShutdownHook(boolean var1) {
      this.enableShutdownHook = var1;
   }

   public void setEnableSpanLocalMetricAggregation(boolean var1) {
      this.enableSpanLocalMetricAggregation = var1;
   }

   public void setEnableSpotlight(boolean var1) {
      this.enableSpotlight = var1;
   }

   public void setEnableTimeToFullDisplayTracing(boolean var1) {
      this.enableTimeToFullDisplayTracing = var1;
   }

   @Deprecated
   public void setEnableTracing(Boolean var1) {
      this.enableTracing = var1;
   }

   public void setEnableUncaughtExceptionHandler(boolean var1) {
      this.enableUncaughtExceptionHandler = var1;
   }

   public void setEnableUserInteractionBreadcrumbs(boolean var1) {
      this.enableUserInteractionBreadcrumbs = var1;
   }

   public void setEnableUserInteractionTracing(boolean var1) {
      this.enableUserInteractionTracing = var1;
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public void setEnvelopeDiskCache(IEnvelopeCache var1) {
      if (var1 == null) {
         var1 = NoOpEnvelopeCache.getInstance();
      }

      this.envelopeDiskCache = (IEnvelopeCache)var1;
   }

   public void setEnvelopeReader(IEnvelopeReader var1) {
      LazyEvaluator var2 = this.envelopeReader;
      if (var1 == null) {
         var1 = NoOpEnvelopeReader.getInstance();
      }

      var2.setValue(var1);
   }

   public void setEnvironment(String var1) {
      this.environment = var1;
   }

   public void setExecutorService(ISentryExecutorService var1) {
      if (var1 != null) {
         this.executorService = var1;
      }
   }

   public void setFlushTimeoutMillis(long var1) {
      this.flushTimeoutMillis = var1;
   }

   public void setFullyDisplayedReporter(FullyDisplayedReporter var1) {
      this.fullyDisplayedReporter = var1;
   }

   public void setGestureTargetLocators(List<GestureTargetLocator> var1) {
      this.gestureTargetLocators.clear();
      this.gestureTargetLocators.addAll(var1);
   }

   public void setIdleTimeout(Long var1) {
      this.idleTimeout = var1;
   }

   public void setIgnoredCheckIns(List<String> var1) {
      if (var1 == null) {
         this.ignoredCheckIns = null;
      } else {
         ArrayList var2 = new ArrayList();

         for (String var3 : var1) {
            if (!var3.isEmpty()) {
               var2.add(var3);
            }
         }

         this.ignoredCheckIns = var2;
      }
   }

   public void setInstrumenter(Instrumenter var1) {
      this.instrumenter = var1;
   }

   public void setLogger(ILogger var1) {
      Object var2;
      if (var1 == null) {
         var2 = NoOpLogger.getInstance();
      } else {
         var2 = new DiagnosticLogger(this, var1);
      }

      this.logger = (ILogger)var2;
   }

   public void setMainThreadChecker(IMainThreadChecker var1) {
      this.mainThreadChecker = var1;
   }

   public void setMaxAttachmentSize(long var1) {
      this.maxAttachmentSize = var1;
   }

   public void setMaxBreadcrumbs(int var1) {
      this.maxBreadcrumbs = var1;
   }

   public void setMaxCacheItems(int var1) {
      this.maxCacheItems = var1;
   }

   public void setMaxDepth(int var1) {
      this.maxDepth = var1;
   }

   public void setMaxQueueSize(int var1) {
      if (var1 > 0) {
         this.maxQueueSize = var1;
      }
   }

   public void setMaxRequestBodySize(SentryOptions.RequestSize var1) {
      this.maxRequestBodySize = var1;
   }

   public void setMaxSpans(int var1) {
      this.maxSpans = var1;
   }

   public void setMaxTraceFileSize(long var1) {
      this.maxTraceFileSize = var1;
   }

   public void setModulesLoader(IModulesLoader var1) {
      if (var1 == null) {
         var1 = NoOpModulesLoader.getInstance();
      }

      this.modulesLoader = (IModulesLoader)var1;
   }

   public void setPrintUncaughtStackTrace(boolean var1) {
      this.printUncaughtStackTrace = var1;
   }

   public void setProfilesSampleRate(Double var1) {
      if (SampleRateUtils.isValidProfilesSampleRate(var1)) {
         this.profilesSampleRate = var1;
      } else {
         StringBuilder var2 = new StringBuilder("The value ");
         var2.append(var1);
         var2.append(" is not valid. Use null to disable or values between 0.0 and 1.0.");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void setProfilesSampler(SentryOptions.ProfilesSamplerCallback var1) {
      this.profilesSampler = var1;
   }

   @Deprecated
   public void setProfilingEnabled(boolean var1) {
      if (this.getProfilesSampleRate() == null) {
         Double var2;
         if (var1) {
            var2 = 1.0;
         } else {
            var2 = null;
         }

         this.setProfilesSampleRate(var2);
      }
   }

   public void setProfilingTracesHz(int var1) {
      this.profilingTracesHz = var1;
   }

   public void setProguardUuid(String var1) {
      this.proguardUuid = var1;
   }

   public void setProxy(SentryOptions.Proxy var1) {
      this.proxy = var1;
   }

   public void setReadTimeoutMillis(int var1) {
      this.readTimeoutMillis = var1;
   }

   public void setRelease(String var1) {
      this.release = var1;
   }

   public void setReplayController(ReplayController var1) {
      if (var1 == null) {
         var1 = NoOpReplayController.getInstance();
      }

      this.replayController = (ReplayController)var1;
   }

   public void setSampleRate(Double var1) {
      if (SampleRateUtils.isValidSampleRate(var1)) {
         this.sampleRate = var1;
      } else {
         StringBuilder var2 = new StringBuilder("The value ");
         var2.append(var1);
         var2.append(" is not valid. Use null to disable or values >= 0.0 and <= 1.0.");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void setSdkVersion(SdkVersion var1) {
      SdkVersion var2 = this.getSessionReplay().getSdkVersion();
      SdkVersion var3 = this.sdkVersion;
      if (var3 != null && var2 != null && var3.equals(var2)) {
         this.getSessionReplay().setSdkVersion(var1);
      }

      this.sdkVersion = var1;
   }

   public void setSendClientReports(boolean var1) {
      this.sendClientReports = var1;
      if (var1) {
         this.clientReportRecorder = new ClientReportRecorder(this);
      } else {
         this.clientReportRecorder = new NoOpClientReportRecorder();
      }
   }

   public void setSendDefaultPii(boolean var1) {
      this.sendDefaultPii = var1;
   }

   public void setSendModules(boolean var1) {
      this.sendModules = var1;
   }

   public void setSentryClientName(String var1) {
      this.sentryClientName = var1;
   }

   public void setSerializer(ISerializer var1) {
      LazyEvaluator var2 = this.serializer;
      if (var1 == null) {
         var1 = NoOpSerializer.getInstance();
      }

      var2.setValue(var1);
   }

   public void setServerName(String var1) {
      this.serverName = var1;
   }

   public void setSessionFlushTimeoutMillis(long var1) {
      this.sessionFlushTimeoutMillis = var1;
   }

   public void setSessionReplay(SentryReplayOptions var1) {
      this.sessionReplay = var1;
   }

   public void setSessionTrackingIntervalMillis(long var1) {
      this.sessionTrackingIntervalMillis = var1;
   }

   @Deprecated
   public void setShutdownTimeout(long var1) {
      this.shutdownTimeoutMillis = var1;
   }

   public void setShutdownTimeoutMillis(long var1) {
      this.shutdownTimeoutMillis = var1;
   }

   public void setSpotlightConnectionUrl(String var1) {
      this.spotlightConnectionUrl = var1;
   }

   public void setSslSocketFactory(SSLSocketFactory var1) {
      this.sslSocketFactory = var1;
   }

   public void setTag(String var1, String var2) {
      this.tags.put(var1, var2);
   }

   public void setTraceOptionsRequests(boolean var1) {
      this.traceOptionsRequests = var1;
   }

   public void setTracePropagationTargets(List<String> var1) {
      if (var1 == null) {
         this.tracePropagationTargets = null;
      } else {
         ArrayList var2 = new ArrayList();

         for (String var3 : var1) {
            if (!var3.isEmpty()) {
               var2.add(var3);
            }
         }

         this.tracePropagationTargets = var2;
      }
   }

   @Deprecated
   public void setTraceSampling(boolean var1) {
      this.traceSampling = var1;
   }

   public void setTracesSampleRate(Double var1) {
      if (SampleRateUtils.isValidTracesSampleRate(var1)) {
         this.tracesSampleRate = var1;
      } else {
         StringBuilder var2 = new StringBuilder("The value ");
         var2.append(var1);
         var2.append(" is not valid. Use null to disable or values between 0.0 and 1.0.");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void setTracesSampler(SentryOptions.TracesSamplerCallback var1) {
      this.tracesSampler = var1;
   }

   @Deprecated
   public void setTracingOrigins(List<String> var1) {
      this.setTracePropagationTargets(var1);
   }

   public void setTransactionPerformanceCollector(TransactionPerformanceCollector var1) {
      this.transactionPerformanceCollector = var1;
   }

   public void setTransactionProfiler(ITransactionProfiler var1) {
      if (this.transactionProfiler == NoOpTransactionProfiler.getInstance() && var1 != null) {
         this.transactionProfiler = var1;
      }
   }

   public void setTransportFactory(ITransportFactory var1) {
      if (var1 == null) {
         var1 = NoOpTransportFactory.getInstance();
      }

      this.transportFactory = (ITransportFactory)var1;
   }

   public void setTransportGate(ITransportGate var1) {
      if (var1 == null) {
         var1 = NoOpTransportGate.getInstance();
      }

      this.transportGate = (ITransportGate)var1;
   }

   public void setViewHierarchyExporters(List<ViewHierarchyExporter> var1) {
      this.viewHierarchyExporters.clear();
      this.viewHierarchyExporters.addAll(var1);
   }

   public interface BeforeBreadcrumbCallback {
      Breadcrumb execute(Breadcrumb var1, Hint var2);
   }

   public interface BeforeEmitMetricCallback {
      boolean execute(String var1, Map<String, String> var2);
   }

   public interface BeforeEnvelopeCallback {
      void execute(SentryEnvelope var1, Hint var2);
   }

   public interface BeforeSendCallback {
      SentryEvent execute(SentryEvent var1, Hint var2);
   }

   public interface BeforeSendReplayCallback {
      SentryReplayEvent execute(SentryReplayEvent var1, Hint var2);
   }

   public interface BeforeSendTransactionCallback {
      SentryTransaction execute(SentryTransaction var1, Hint var2);
   }

   public static final class Cron {
      private Long defaultCheckinMargin;
      private Long defaultFailureIssueThreshold;
      private Long defaultMaxRuntime;
      private Long defaultRecoveryThreshold;
      private String defaultTimezone;

      public Long getDefaultCheckinMargin() {
         return this.defaultCheckinMargin;
      }

      public Long getDefaultFailureIssueThreshold() {
         return this.defaultFailureIssueThreshold;
      }

      public Long getDefaultMaxRuntime() {
         return this.defaultMaxRuntime;
      }

      public Long getDefaultRecoveryThreshold() {
         return this.defaultRecoveryThreshold;
      }

      public String getDefaultTimezone() {
         return this.defaultTimezone;
      }

      public void setDefaultCheckinMargin(Long var1) {
         this.defaultCheckinMargin = var1;
      }

      public void setDefaultFailureIssueThreshold(Long var1) {
         this.defaultFailureIssueThreshold = var1;
      }

      public void setDefaultMaxRuntime(Long var1) {
         this.defaultMaxRuntime = var1;
      }

      public void setDefaultRecoveryThreshold(Long var1) {
         this.defaultRecoveryThreshold = var1;
      }

      public void setDefaultTimezone(String var1) {
         this.defaultTimezone = var1;
      }
   }

   public interface ProfilesSamplerCallback {
      Double sample(SamplingContext var1);
   }

   public static final class Proxy {
      private String host;
      private String pass;
      private String port;
      private Type type;
      private String user;

      public Proxy() {
         this(null, null, null, null, null);
      }

      public Proxy(String var1, String var2) {
         this(var1, var2, null, null, null);
      }

      public Proxy(String var1, String var2, String var3, String var4) {
         this(var1, var2, null, var3, var4);
      }

      public Proxy(String var1, String var2, Type var3) {
         this(var1, var2, var3, null, null);
      }

      public Proxy(String var1, String var2, Type var3, String var4, String var5) {
         this.host = var1;
         this.port = var2;
         this.type = var3;
         this.user = var4;
         this.pass = var5;
      }

      public String getHost() {
         return this.host;
      }

      public String getPass() {
         return this.pass;
      }

      public String getPort() {
         return this.port;
      }

      public Type getType() {
         return this.type;
      }

      public String getUser() {
         return this.user;
      }

      public void setHost(String var1) {
         this.host = var1;
      }

      public void setPass(String var1) {
         this.pass = var1;
      }

      public void setPort(String var1) {
         this.port = var1;
      }

      public void setType(Type var1) {
         this.type = var1;
      }

      public void setUser(String var1) {
         this.user = var1;
      }
   }

   public static enum RequestSize {
      ALWAYS,
      MEDIUM,
      NONE,
      SMALL;
      private static final SentryOptions.RequestSize[] $VALUES = $values();
   }

   public interface TracesSamplerCallback {
      Double sample(SamplingContext var1);
   }
}
