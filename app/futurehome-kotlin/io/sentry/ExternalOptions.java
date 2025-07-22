package io.sentry;

import io.sentry.config.PropertiesProvider;
import j..util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public final class ExternalOptions {
   private static final String PROXY_PORT_DEFAULT = "80";
   private Set<String> bundleIds;
   private final List<String> contextTags;
   private SentryOptions.Cron cron;
   private Boolean debug;
   private String dist;
   private String dsn;
   private Boolean enableBackpressureHandling;
   private Boolean enableDeduplication;
   private Boolean enablePrettySerializationOutput;
   private Boolean enableTracing;
   private Boolean enableUncaughtExceptionHandler;
   private Boolean enabled;
   private String environment;
   private Long idleTimeout;
   private List<String> ignoredCheckIns;
   private final Set<Class<? extends Throwable>> ignoredExceptionsForType;
   private final List<String> inAppExcludes;
   private final List<String> inAppIncludes;
   private SentryOptions.RequestSize maxRequestBodySize;
   private Boolean printUncaughtStackTrace;
   private Double profilesSampleRate;
   private String proguardUuid;
   private SentryOptions.Proxy proxy;
   private String release;
   private Boolean sendClientReports;
   private Boolean sendModules;
   private String serverName;
   private final Map<String, String> tags = new ConcurrentHashMap();
   private List<String> tracePropagationTargets;
   private Double tracesSampleRate;

   public ExternalOptions() {
      this.inAppExcludes = new CopyOnWriteArrayList<>();
      this.inAppIncludes = new CopyOnWriteArrayList<>();
      this.tracePropagationTargets = null;
      this.contextTags = new CopyOnWriteArrayList<>();
      this.ignoredExceptionsForType = new CopyOnWriteArraySet<>();
      this.bundleIds = new CopyOnWriteArraySet<>();
   }

   public static ExternalOptions from(PropertiesProvider var0, ILogger var1) {
      ExternalOptions var4 = new ExternalOptions();
      var4.setDsn(var0.getProperty("dsn"));
      var4.setEnvironment(var0.getProperty("environment"));
      var4.setRelease(var0.getProperty("release"));
      var4.setDist(var0.getProperty("dist"));
      var4.setServerName(var0.getProperty("servername"));
      var4.setEnableUncaughtExceptionHandler(var0.getBooleanProperty("uncaught.handler.enabled"));
      var4.setPrintUncaughtStackTrace(var0.getBooleanProperty("uncaught.handler.print-stacktrace"));
      var4.setEnableTracing(var0.getBooleanProperty("enable-tracing"));
      var4.setTracesSampleRate(var0.getDoubleProperty("traces-sample-rate"));
      var4.setProfilesSampleRate(var0.getDoubleProperty("profiles-sample-rate"));
      var4.setDebug(var0.getBooleanProperty("debug"));
      var4.setEnableDeduplication(var0.getBooleanProperty("enable-deduplication"));
      var4.setSendClientReports(var0.getBooleanProperty("send-client-reports"));
      String var2 = var0.getProperty("max-request-body-size");
      if (var2 != null) {
         var4.setMaxRequestBodySize(SentryOptions.RequestSize.valueOf(var2.toUpperCase(Locale.ROOT)));
      }

      for (Entry var10 : var0.getMap("tags").entrySet()) {
         var4.setTag((String)var10.getKey(), (String)var10.getValue());
      }

      String var6 = var0.getProperty("proxy.host");
      String var5 = var0.getProperty("proxy.user");
      String var20 = var0.getProperty("proxy.pass");
      var2 = var0.getProperty("proxy.port", "80");
      if (var6 != null) {
         var4.setProxy(new SentryOptions.Proxy(var6, var2, var5, var20));
      }

      Iterator var12 = var0.getList("in-app-includes").iterator();

      while (var12.hasNext()) {
         var4.addInAppInclude((String)var12.next());
      }

      Iterator var13 = var0.getList("in-app-excludes").iterator();

      while (var13.hasNext()) {
         var4.addInAppExclude((String)var13.next());
      }

      List var14;
      if (var0.getProperty("trace-propagation-targets") != null) {
         var14 = var0.getList("trace-propagation-targets");
      } else {
         var14 = null;
      }

      List var21 = var14;
      if (var14 == null) {
         var21 = var14;
         if (var0.getProperty("tracing-origins") != null) {
            var21 = var0.getList("tracing-origins");
         }
      }

      if (var21 != null) {
         Iterator var15 = var21.iterator();

         while (var15.hasNext()) {
            var4.addTracePropagationTarget((String)var15.next());
         }
      }

      Iterator var16 = var0.getList("context-tags").iterator();

      while (var16.hasNext()) {
         var4.addContextTag((String)var16.next());
      }

      var4.setProguardUuid(var0.getProperty("proguard-uuid"));
      Iterator var17 = var0.getList("bundle-ids").iterator();

      while (var17.hasNext()) {
         var4.addBundleId((String)var17.next());
      }

      var4.setIdleTimeout(var0.getLongProperty("idle-timeout"));
      var4.setEnabled(var0.getBooleanProperty("enabled"));
      var4.setEnablePrettySerializationOutput(var0.getBooleanProperty("enable-pretty-serialization-output"));
      var4.setSendModules(var0.getBooleanProperty("send-modules"));
      var4.setIgnoredCheckIns(var0.getList("ignored-checkins"));
      var4.setEnableBackpressureHandling(var0.getBooleanProperty("enable-backpressure-handling"));

      for (String var18 : var0.getList("ignored-exceptions-for-type")) {
         try {
            Class var24 = Class.forName(var18);
            if (Throwable.class.isAssignableFrom(var24)) {
               var4.addIgnoredExceptionForType(var24);
            } else {
               var1.log(SentryLevel.WARNING, "Skipping setting %s as ignored-exception-for-type. Reason: %s does not extend Throwable", var18, var18);
            }
         } catch (ClassNotFoundException var7) {
            var1.log(SentryLevel.WARNING, "Skipping setting %s as ignored-exception-for-type. Reason: %s class is not found", var18, var18);
         }
      }

      Long var25 = var0.getLongProperty("cron.default-checkin-margin");
      Long var23 = var0.getLongProperty("cron.default-max-runtime");
      String var9 = var0.getProperty("cron.default-timezone");
      Long var19 = var0.getLongProperty("cron.default-failure-issue-threshold");
      Long var8 = var0.getLongProperty("cron.default-recovery-threshold");
      if (var25 != null || var23 != null || var9 != null || var19 != null || var8 != null) {
         SentryOptions.Cron var26 = new SentryOptions.Cron();
         var26.setDefaultCheckinMargin(var25);
         var26.setDefaultMaxRuntime(var23);
         var26.setDefaultTimezone(var9);
         var26.setDefaultFailureIssueThreshold(var19);
         var26.setDefaultRecoveryThreshold(var8);
         var4.setCron(var26);
      }

      return var4;
   }

   public void addBundleId(String var1) {
      this.bundleIds.add(var1);
   }

   public void addContextTag(String var1) {
      this.contextTags.add(var1);
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

   public void addTracePropagationTarget(String var1) {
      if (this.tracePropagationTargets == null) {
         this.tracePropagationTargets = new CopyOnWriteArrayList<>();
      }

      if (!var1.isEmpty()) {
         this.tracePropagationTargets.add(var1);
      }
   }

   @Deprecated
   public void addTracingOrigin(String var1) {
      this.addTracePropagationTarget(var1);
   }

   public Set<String> getBundleIds() {
      return this.bundleIds;
   }

   public List<String> getContextTags() {
      return this.contextTags;
   }

   public SentryOptions.Cron getCron() {
      return this.cron;
   }

   public Boolean getDebug() {
      return this.debug;
   }

   public String getDist() {
      return this.dist;
   }

   public String getDsn() {
      return this.dsn;
   }

   public Boolean getEnableDeduplication() {
      return this.enableDeduplication;
   }

   public Boolean getEnableTracing() {
      return this.enableTracing;
   }

   public Boolean getEnableUncaughtExceptionHandler() {
      return this.enableUncaughtExceptionHandler;
   }

   public String getEnvironment() {
      return this.environment;
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

   public SentryOptions.RequestSize getMaxRequestBodySize() {
      return this.maxRequestBodySize;
   }

   public Boolean getPrintUncaughtStackTrace() {
      return this.printUncaughtStackTrace;
   }

   public Double getProfilesSampleRate() {
      return this.profilesSampleRate;
   }

   public String getProguardUuid() {
      return this.proguardUuid;
   }

   public SentryOptions.Proxy getProxy() {
      return this.proxy;
   }

   public String getRelease() {
      return this.release;
   }

   public Boolean getSendClientReports() {
      return this.sendClientReports;
   }

   public String getServerName() {
      return this.serverName;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   public List<String> getTracePropagationTargets() {
      return this.tracePropagationTargets;
   }

   public Double getTracesSampleRate() {
      return this.tracesSampleRate;
   }

   @Deprecated
   public List<String> getTracingOrigins() {
      return this.tracePropagationTargets;
   }

   public Boolean isEnableBackpressureHandling() {
      return this.enableBackpressureHandling;
   }

   public Boolean isEnablePrettySerializationOutput() {
      return this.enablePrettySerializationOutput;
   }

   public Boolean isEnabled() {
      return this.enabled;
   }

   public Boolean isSendModules() {
      return this.sendModules;
   }

   public void setCron(SentryOptions.Cron var1) {
      this.cron = var1;
   }

   public void setDebug(Boolean var1) {
      this.debug = var1;
   }

   public void setDist(String var1) {
      this.dist = var1;
   }

   public void setDsn(String var1) {
      this.dsn = var1;
   }

   public void setEnableBackpressureHandling(Boolean var1) {
      this.enableBackpressureHandling = var1;
   }

   public void setEnableDeduplication(Boolean var1) {
      this.enableDeduplication = var1;
   }

   public void setEnablePrettySerializationOutput(Boolean var1) {
      this.enablePrettySerializationOutput = var1;
   }

   public void setEnableTracing(Boolean var1) {
      this.enableTracing = var1;
   }

   public void setEnableUncaughtExceptionHandler(Boolean var1) {
      this.enableUncaughtExceptionHandler = var1;
   }

   public void setEnabled(Boolean var1) {
      this.enabled = var1;
   }

   public void setEnvironment(String var1) {
      this.environment = var1;
   }

   public void setIdleTimeout(Long var1) {
      this.idleTimeout = var1;
   }

   public void setIgnoredCheckIns(List<String> var1) {
      this.ignoredCheckIns = var1;
   }

   public void setMaxRequestBodySize(SentryOptions.RequestSize var1) {
      this.maxRequestBodySize = var1;
   }

   public void setPrintUncaughtStackTrace(Boolean var1) {
      this.printUncaughtStackTrace = var1;
   }

   public void setProfilesSampleRate(Double var1) {
      this.profilesSampleRate = var1;
   }

   public void setProguardUuid(String var1) {
      this.proguardUuid = var1;
   }

   public void setProxy(SentryOptions.Proxy var1) {
      this.proxy = var1;
   }

   public void setRelease(String var1) {
      this.release = var1;
   }

   public void setSendClientReports(Boolean var1) {
      this.sendClientReports = var1;
   }

   public void setSendModules(Boolean var1) {
      this.sendModules = var1;
   }

   public void setServerName(String var1) {
      this.serverName = var1;
   }

   public void setTag(String var1, String var2) {
      this.tags.put(var1, var2);
   }

   public void setTracesSampleRate(Double var1) {
      this.tracesSampleRate = var1;
   }
}
