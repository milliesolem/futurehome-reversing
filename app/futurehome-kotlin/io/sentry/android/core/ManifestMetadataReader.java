package io.sentry.android.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import io.sentry.ILogger;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryLevel;
import io.sentry.protocol.SdkVersion;
import io.sentry.util.Objects;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

final class ManifestMetadataReader {
   static final String ANR_ATTACH_THREAD_DUMPS = "io.sentry.anr.attach-thread-dumps";
   static final String ANR_ENABLE = "io.sentry.anr.enable";
   static final String ANR_REPORT_DEBUG = "io.sentry.anr.report-debug";
   static final String ANR_TIMEOUT_INTERVAL_MILLIS = "io.sentry.anr.timeout-interval-millis";
   static final String ATTACH_SCREENSHOT = "io.sentry.attach-screenshot";
   static final String ATTACH_THREADS = "io.sentry.attach-threads";
   static final String ATTACH_VIEW_HIERARCHY = "io.sentry.attach-view-hierarchy";
   static final String AUTO_INIT = "io.sentry.auto-init";
   static final String AUTO_SESSION_TRACKING_ENABLE = "io.sentry.auto-session-tracking.enable";
   static final String BREADCRUMBS_ACTIVITY_LIFECYCLE_ENABLE = "io.sentry.breadcrumbs.activity-lifecycle";
   static final String BREADCRUMBS_APP_COMPONENTS_ENABLE = "io.sentry.breadcrumbs.app-components";
   static final String BREADCRUMBS_APP_LIFECYCLE_ENABLE = "io.sentry.breadcrumbs.app-lifecycle";
   static final String BREADCRUMBS_NETWORK_EVENTS_ENABLE = "io.sentry.breadcrumbs.network-events";
   static final String BREADCRUMBS_SYSTEM_EVENTS_ENABLE = "io.sentry.breadcrumbs.system-events";
   static final String BREADCRUMBS_USER_INTERACTION_ENABLE = "io.sentry.breadcrumbs.user-interaction";
   static final String CLIENT_REPORTS_ENABLE = "io.sentry.send-client-reports";
   static final String COLLECT_ADDITIONAL_CONTEXT = "io.sentry.additional-context";
   static final String DEBUG = "io.sentry.debug";
   static final String DEBUG_LEVEL = "io.sentry.debug.level";
   static final String DSN = "io.sentry.dsn";
   static final String ENABLE_APP_START_PROFILING = "io.sentry.profiling.enable-app-start";
   static final String ENABLE_METRICS = "io.sentry.enable-metrics";
   static final String ENABLE_PERFORMANCE_V2 = "io.sentry.performance-v2.enable";
   static final String ENABLE_ROOT_CHECK = "io.sentry.enable-root-check";
   static final String ENABLE_SCOPE_PERSISTENCE = "io.sentry.enable-scope-persistence";
   static final String ENABLE_SENTRY = "io.sentry.enabled";
   static final String ENVIRONMENT = "io.sentry.environment";
   static final String IDLE_TIMEOUT = "io.sentry.traces.idle-timeout";
   static final String MAX_BREADCRUMBS = "io.sentry.max-breadcrumbs";
   static final String NDK_ENABLE = "io.sentry.ndk.enable";
   static final String NDK_SCOPE_SYNC_ENABLE = "io.sentry.ndk.scope-sync.enable";
   static final String PERFORM_FRAMES_TRACKING = "io.sentry.traces.frames-tracking";
   static final String PROFILES_SAMPLE_RATE = "io.sentry.traces.profiling.sample-rate";
   static final String PROGUARD_UUID = "io.sentry.proguard-uuid";
   static final String RELEASE = "io.sentry.release";
   static final String REPLAYS_ERROR_SAMPLE_RATE = "io.sentry.session-replay.on-error-sample-rate";
   static final String REPLAYS_MASK_ALL_IMAGES = "io.sentry.session-replay.mask-all-images";
   static final String REPLAYS_MASK_ALL_TEXT = "io.sentry.session-replay.mask-all-text";
   static final String REPLAYS_SESSION_SAMPLE_RATE = "io.sentry.session-replay.session-sample-rate";
   static final String SAMPLE_RATE = "io.sentry.sample-rate";
   static final String SDK_NAME = "io.sentry.sdk.name";
   static final String SDK_VERSION = "io.sentry.sdk.version";
   static final String SEND_DEFAULT_PII = "io.sentry.send-default-pii";
   static final String SEND_MODULES = "io.sentry.send-modules";
   static final String SENTRY_GRADLE_PLUGIN_INTEGRATIONS = "io.sentry.gradle-plugin-integrations";
   static final String SESSION_TRACKING_ENABLE = "io.sentry.session-tracking.enable";
   static final String SESSION_TRACKING_TIMEOUT_INTERVAL_MILLIS = "io.sentry.session-tracking.timeout-interval-millis";
   static final String TRACES_ACTIVITY_AUTO_FINISH_ENABLE = "io.sentry.traces.activity.auto-finish.enable";
   static final String TRACES_ACTIVITY_ENABLE = "io.sentry.traces.activity.enable";
   static final String TRACES_PROFILING_ENABLE = "io.sentry.traces.profiling.enable";
   static final String TRACES_SAMPLE_RATE = "io.sentry.traces.sample-rate";
   static final String TRACES_UI_ENABLE = "io.sentry.traces.user-interaction.enable";
   static final String TRACE_PROPAGATION_TARGETS = "io.sentry.traces.trace-propagation-targets";
   static final String TRACE_SAMPLING = "io.sentry.traces.trace-sampling";
   @Deprecated
   static final String TRACING_ENABLE = "io.sentry.traces.enable";
   @Deprecated
   static final String TRACING_ORIGINS = "io.sentry.traces.tracing-origins";
   static final String TTFD_ENABLE = "io.sentry.traces.time-to-full-display.enable";
   static final String UNCAUGHT_EXCEPTION_HANDLER_ENABLE = "io.sentry.uncaught-exception-handler.enable";

   private ManifestMetadataReader() {
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static void applyMetadata(Context var0, SentryAndroidOptions var1, BuildInfoProvider var2) {
      Objects.requireNonNull(var0, "The application context is required.");
      Objects.requireNonNull(var1, "The options object is required.");

      Bundle var6;
      ILogger var7;
      try {
         var6 = getMetadata(var0, var1.getLogger(), var2);
         var7 = var1.getLogger();
      } catch (Throwable var819) {
         var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var819);
         return;
      }

      if (var6 != null) {
         label3817: {
            try {
               var1.setDebug(readBool(var6, var7, "io.sentry.debug", var1.isDebug()));
               if (!var1.isDebug()) {
                  break label3817;
               }

               var820 = readString(var6, var7, "io.sentry.debug.level", var1.getDiagnosticLevel().name().toLowerCase(Locale.ROOT));
            } catch (Throwable var818) {
               var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var818);
               return;
            }

            if (var820 != null) {
               try {
                  var1.setDiagnosticLevel(SentryLevel.valueOf(var820.toUpperCase(Locale.ROOT)));
               } catch (Throwable var809) {
                  var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var809);
                  return;
               }
            }
         }

         try {
            var1.setAnrEnabled(readBool(var6, var7, "io.sentry.anr.enable", var1.isAnrEnabled()));
            var1.setEnableAutoSessionTracking(
               readBool(
                  var6,
                  var7,
                  "io.sentry.auto-session-tracking.enable",
                  readBool(var6, var7, "io.sentry.session-tracking.enable", var1.isEnableAutoSessionTracking())
               )
            );
            if (var1.getSampleRate() == null) {
               Double var821 = readDouble(var6, var7, "io.sentry.sample-rate");
               if (var821 != -1.0) {
                  var1.setSampleRate(var821);
               }
            }
         } catch (Throwable var817) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var817);
            return;
         }

         boolean var5;
         try {
            var1.setAnrReportInDebug(readBool(var6, var7, "io.sentry.anr.report-debug", var1.isAnrReportInDebug()));
            var1.setAnrTimeoutIntervalMillis(readLong(var6, var7, "io.sentry.anr.timeout-interval-millis", var1.getAnrTimeoutIntervalMillis()));
            var1.setAttachAnrThreadDump(readBool(var6, var7, "io.sentry.anr.attach-thread-dumps", var1.isAttachAnrThreadDump()));
            var822 = readString(var6, var7, "io.sentry.dsn", var1.getDsn());
            var5 = readBool(var6, var7, "io.sentry.enabled", var1.isEnabled());
         } catch (Throwable var808) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var808);
            return;
         }

         label3837: {
            label3796:
            if (var5) {
               if (var822 != null) {
                  try {
                     if (var822.isEmpty()) {
                        break label3796;
                     }
                  } catch (Throwable var816) {
                     var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var816);
                     return;
                  }
               }

               if (var822 == null) {
                  try {
                     var1.getLogger().log(SentryLevel.FATAL, "DSN is required. Use empty string to disable SDK.");
                  } catch (Throwable var807) {
                     var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var807);
                     return;
                  }
               }
               break label3837;
            }

            try {
               var1.getLogger().log(SentryLevel.DEBUG, "Sentry enabled flag set to false or DSN is empty: disabling sentry-android");
            } catch (Throwable var806) {
               var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var806);
               return;
            }
         }

         try {
            var1.setEnabled(var5);
            var1.setDsn(var822);
            var1.setEnableNdk(readBool(var6, var7, "io.sentry.ndk.enable", var1.isEnableNdk()));
            var1.setEnableScopeSync(readBool(var6, var7, "io.sentry.ndk.scope-sync.enable", var1.isEnableScopeSync()));
            var1.setRelease(readString(var6, var7, "io.sentry.release", var1.getRelease()));
            var1.setEnvironment(readString(var6, var7, "io.sentry.environment", var1.getEnvironment()));
            var1.setSessionTrackingIntervalMillis(
               readLong(var6, var7, "io.sentry.session-tracking.timeout-interval-millis", var1.getSessionTrackingIntervalMillis())
            );
            var1.setMaxBreadcrumbs((int)readLong(var6, var7, "io.sentry.max-breadcrumbs", var1.getMaxBreadcrumbs()));
            var1.setEnableActivityLifecycleBreadcrumbs(
               readBool(var6, var7, "io.sentry.breadcrumbs.activity-lifecycle", var1.isEnableActivityLifecycleBreadcrumbs())
            );
            var1.setEnableAppLifecycleBreadcrumbs(readBool(var6, var7, "io.sentry.breadcrumbs.app-lifecycle", var1.isEnableAppLifecycleBreadcrumbs()));
            var1.setEnableSystemEventBreadcrumbs(readBool(var6, var7, "io.sentry.breadcrumbs.system-events", var1.isEnableSystemEventBreadcrumbs()));
            var1.setEnableAppComponentBreadcrumbs(readBool(var6, var7, "io.sentry.breadcrumbs.app-components", var1.isEnableAppComponentBreadcrumbs()));
            var1.setEnableUserInteractionBreadcrumbs(readBool(var6, var7, "io.sentry.breadcrumbs.user-interaction", var1.isEnableUserInteractionBreadcrumbs()));
            var1.setEnableNetworkEventBreadcrumbs(readBool(var6, var7, "io.sentry.breadcrumbs.network-events", var1.isEnableNetworkEventBreadcrumbs()));
            var1.setEnableUncaughtExceptionHandler(readBool(var6, var7, "io.sentry.uncaught-exception-handler.enable", var1.isEnableUncaughtExceptionHandler()));
            var1.setAttachThreads(readBool(var6, var7, "io.sentry.attach-threads", var1.isAttachThreads()));
            var1.setAttachScreenshot(readBool(var6, var7, "io.sentry.attach-screenshot", var1.isAttachScreenshot()));
            var1.setAttachViewHierarchy(readBool(var6, var7, "io.sentry.attach-view-hierarchy", var1.isAttachViewHierarchy()));
            var1.setSendClientReports(readBool(var6, var7, "io.sentry.send-client-reports", var1.isSendClientReports()));
            var1.setCollectAdditionalContext(readBool(var6, var7, "io.sentry.additional-context", var1.isCollectAdditionalContext()));
            if (var1.getEnableTracing() == null) {
               var1.setEnableTracing(readBoolNullable(var6, var7, "io.sentry.traces.enable", null));
            }
         } catch (Throwable var815) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var815);
            return;
         }

         try {
            if (var1.getTracesSampleRate() == null) {
               Double var823 = readDouble(var6, var7, "io.sentry.traces.sample-rate");
               if (var823 != -1.0) {
                  var1.setTracesSampleRate(var823);
               }
            }
         } catch (Throwable var805) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var805);
            return;
         }

         try {
            var1.setTraceSampling(readBool(var6, var7, "io.sentry.traces.trace-sampling", var1.isTraceSampling()));
            var1.setEnableAutoActivityLifecycleTracing(readBool(var6, var7, "io.sentry.traces.activity.enable", var1.isEnableAutoActivityLifecycleTracing()));
            var1.setEnableActivityLifecycleTracingAutoFinish(
               readBool(var6, var7, "io.sentry.traces.activity.auto-finish.enable", var1.isEnableActivityLifecycleTracingAutoFinish())
            );
            var1.setProfilingEnabled(readBool(var6, var7, "io.sentry.traces.profiling.enable", var1.isProfilingEnabled()));
            if (var1.getProfilesSampleRate() == null) {
               Double var824 = readDouble(var6, var7, "io.sentry.traces.profiling.sample-rate");
               if (var824 != -1.0) {
                  var1.setProfilesSampleRate(var824);
               }
            }
         } catch (Throwable var814) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var814);
            return;
         }

         long var3;
         try {
            var1.setEnableUserInteractionTracing(readBool(var6, var7, "io.sentry.traces.user-interaction.enable", var1.isEnableUserInteractionTracing()));
            var1.setEnableTimeToFullDisplayTracing(
               readBool(var6, var7, "io.sentry.traces.time-to-full-display.enable", var1.isEnableTimeToFullDisplayTracing())
            );
            var3 = readLong(var6, var7, "io.sentry.traces.idle-timeout", -1L);
         } catch (Throwable var804) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var804);
            return;
         }

         if (var3 != -1L) {
            try {
               var1.setIdleTimeout(var3);
            } catch (Throwable var803) {
               var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var803);
               return;
            }
         }

         try {
            var831 = readList(var6, var7, "io.sentry.traces.trace-propagation-targets");
            var5 = var6.containsKey("io.sentry.traces.trace-propagation-targets");
         } catch (Throwable var802) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var802);
            return;
         }

         var825 = var831;
         label3776:
         if (!var5) {
            if (var831 != null) {
               var825 = var831;

               try {
                  if (!var831.isEmpty()) {
                     break label3776;
                  }
               } catch (Throwable var813) {
                  var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var813);
                  return;
               }
            }

            try {
               var825 = readList(var6, var7, "io.sentry.traces.tracing-origins");
            } catch (Throwable var801) {
               var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var801);
               return;
            }
         }

         label3768: {
            label3767: {
               try {
                  if (!var6.containsKey("io.sentry.traces.trace-propagation-targets") && !var6.containsKey("io.sentry.traces.tracing-origins")) {
                     break label3767;
                  }
               } catch (Throwable var812) {
                  var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var812);
                  return;
               }

               if (var825 == null) {
                  try {
                     var1.setTracePropagationTargets(Collections.emptyList());
                     break label3768;
                  } catch (Throwable var800) {
                     var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var800);
                     return;
                  }
               }
            }

            if (var825 != null) {
               try {
                  var1.setTracePropagationTargets(var825);
               } catch (Throwable var799) {
                  var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var799);
                  return;
               }
            }
         }

         try {
            var1.setEnableFramesTracking(readBool(var6, var7, "io.sentry.traces.frames-tracking", true));
            var1.setProguardUuid(readString(var6, var7, "io.sentry.proguard-uuid", var1.getProguardUuid()));
            var832 = var1.getSdkVersion();
         } catch (Throwable var798) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var798);
            return;
         }

         SdkVersion var826 = var832;
         if (var832 == null) {
            try {
               var826 = new SdkVersion("", "");
            } catch (Throwable var797) {
               var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var797);
               return;
            }
         }

         try {
            var826.setName(readStringNotNull(var6, var7, "io.sentry.sdk.name", var826.getName()));
            var826.setVersion(readStringNotNull(var6, var7, "io.sentry.sdk.version", var826.getVersion()));
            var1.setSdkVersion(var826);
            var1.setSendDefaultPii(readBool(var6, var7, "io.sentry.send-default-pii", var1.isSendDefaultPii()));
            var827 = readList(var6, var7, "io.sentry.gradle-plugin-integrations");
         } catch (Throwable var796) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var796);
            return;
         }

         if (var827 != null) {
            try {
               var828 = var827.iterator();
            } catch (Throwable var795) {
               var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var795);
               return;
            }

            while (true) {
               try {
                  if (!var828.hasNext()) {
                     break;
                  }

                  String var833 = (String)var828.next();
                  SentryIntegrationPackageStorage.getInstance().addIntegration(var833);
               } catch (Throwable var811) {
                  var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var811);
                  return;
               }
            }
         }

         try {
            var1.setEnableRootCheck(readBool(var6, var7, "io.sentry.enable-root-check", var1.isEnableRootCheck()));
            var1.setSendModules(readBool(var6, var7, "io.sentry.send-modules", var1.isSendModules()));
            var1.setEnablePerformanceV2(readBool(var6, var7, "io.sentry.performance-v2.enable", var1.isEnablePerformanceV2()));
            var1.setEnableAppStartProfiling(readBool(var6, var7, "io.sentry.profiling.enable-app-start", var1.isEnableAppStartProfiling()));
            var1.setEnableScopePersistence(readBool(var6, var7, "io.sentry.enable-scope-persistence", var1.isEnableScopePersistence()));
            var1.setEnableMetrics(readBool(var6, var7, "io.sentry.enable-metrics", var1.isEnableMetrics()));
            if (var1.getSessionReplay().getSessionSampleRate() == null) {
               Double var829 = readDouble(var6, var7, "io.sentry.session-replay.session-sample-rate");
               if (var829 != -1.0) {
                  var1.getSessionReplay().setSessionSampleRate(var829);
               }
            }
         } catch (Throwable var794) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var794);
            return;
         }

         try {
            if (var1.getSessionReplay().getOnErrorSampleRate() == null) {
               Double var830 = readDouble(var6, var7, "io.sentry.session-replay.on-error-sample-rate");
               if (var830 != -1.0) {
                  var1.getSessionReplay().setOnErrorSampleRate(var830);
               }
            }
         } catch (Throwable var810) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var810);
            return;
         }

         try {
            var1.getSessionReplay().setMaskAllText(readBool(var6, var7, "io.sentry.session-replay.mask-all-text", true));
            var1.getSessionReplay().setMaskAllImages(readBool(var6, var7, "io.sentry.session-replay.mask-all-images", true));
         } catch (Throwable var793) {
            var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var793);
            return;
         }
      }

      try {
         var1.getLogger().log(SentryLevel.INFO, "Retrieving configuration from AndroidManifest.xml");
      } catch (Throwable var792) {
         var1.getLogger().log(SentryLevel.ERROR, "Failed to read configuration from android manifest metadata.", var792);
         return;
      }
   }

   private static Bundle getMetadata(Context var0, ILogger var1, BuildInfoProvider var2) {
      if (var2 == null) {
         var2 = new BuildInfoProvider(var1);
      }

      ApplicationInfo var3 = ContextUtils.getApplicationInfo(var0, var2);
      Bundle var4;
      if (var3 != null) {
         var4 = var3.metaData;
      } else {
         var4 = null;
      }

      return var4;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static boolean isAutoInit(Context var0, ILogger var1) {
      Objects.requireNonNull(var0, "The application context is required.");
      boolean var3 = true;

      try {
         var10 = getMetadata(var0, var1, null);
      } catch (Throwable var9) {
         var1.log(SentryLevel.ERROR, "Failed to read auto-init from android manifest metadata.", var9);
         return var3;
      }

      boolean var2 = var3;
      if (var10 != null) {
         try {
            var2 = readBool(var10, var1, "io.sentry.auto-init", true);
         } catch (Throwable var8) {
            var1.log(SentryLevel.ERROR, "Failed to read auto-init from android manifest metadata.", var8);
            return var3;
         }
      }

      return var2;
   }

   private static boolean readBool(Bundle var0, ILogger var1, String var2, boolean var3) {
      var3 = var0.getBoolean(var2, var3);
      SentryLevel var4 = SentryLevel.DEBUG;
      StringBuilder var5 = new StringBuilder();
      var5.append(var2);
      var5.append(" read: ");
      var5.append(var3);
      var1.log(var4, var5.toString());
      return var3;
   }

   private static Boolean readBoolNullable(Bundle var0, ILogger var1, String var2, Boolean var3) {
      if (var0.getSerializable(var2) != null) {
         boolean var4;
         if (var3 == null) {
            var4 = false;
         } else {
            var4 = true;
         }

         var4 = var0.getBoolean(var2, var4);
         SentryLevel var8 = SentryLevel.DEBUG;
         StringBuilder var7 = new StringBuilder();
         var7.append(var2);
         var7.append(" read: ");
         var7.append(var4);
         var1.log(var8, var7.toString());
         return var4;
      } else {
         SentryLevel var5 = SentryLevel.DEBUG;
         StringBuilder var6 = new StringBuilder();
         var6.append(var2);
         var6.append(" used default ");
         var6.append(var3);
         var1.log(var5, var6.toString());
         return var3;
      }
   }

   private static Double readDouble(Bundle var0, ILogger var1, String var2) {
      Double var5 = Float.valueOf(var0.getFloat(var2, var0.getInt(var2, -1))).doubleValue();
      SentryLevel var4 = SentryLevel.DEBUG;
      StringBuilder var3 = new StringBuilder();
      var3.append(var2);
      var3.append(" read: ");
      var3.append(var5);
      var1.log(var4, var3.toString());
      return var5;
   }

   private static List<String> readList(Bundle var0, ILogger var1, String var2) {
      String var4 = var0.getString(var2);
      SentryLevel var3 = SentryLevel.DEBUG;
      StringBuilder var5 = new StringBuilder();
      var5.append(var2);
      var5.append(" read: ");
      var5.append(var4);
      var1.log(var3, var5.toString());
      return var4 != null ? Arrays.asList(var4.split(",", -1)) : null;
   }

   private static long readLong(Bundle var0, ILogger var1, String var2, long var3) {
      var3 = var0.getInt(var2, (int)var3);
      SentryLevel var5 = SentryLevel.DEBUG;
      StringBuilder var6 = new StringBuilder();
      var6.append(var2);
      var6.append(" read: ");
      var6.append(var3);
      var1.log(var5, var6.toString());
      return var3;
   }

   private static String readString(Bundle var0, ILogger var1, String var2, String var3) {
      String var4 = var0.getString(var2, var3);
      SentryLevel var5 = SentryLevel.DEBUG;
      StringBuilder var6 = new StringBuilder();
      var6.append(var2);
      var6.append(" read: ");
      var6.append(var4);
      var1.log(var5, var6.toString());
      return var4;
   }

   private static String readStringNotNull(Bundle var0, ILogger var1, String var2, String var3) {
      String var5 = var0.getString(var2, var3);
      SentryLevel var4 = SentryLevel.DEBUG;
      StringBuilder var6 = new StringBuilder();
      var6.append(var2);
      var6.append(" read: ");
      var6.append(var5);
      var1.log(var4, var6.toString());
      return var5;
   }
}
