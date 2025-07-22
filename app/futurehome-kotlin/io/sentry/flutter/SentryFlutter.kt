package io.sentry.flutter

import android.util.Log
import io.sentry.Hint
import io.sentry.ReplayRecording
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.SentryReplayEvent
import io.sentry.SentryReplayOptions
import io.sentry.android.core.SentryAndroidOptions
import io.sentry.protocol.SdkVersion
import io.sentry.rrweb.RRWebEvent
import io.sentry.rrweb.RRWebOptionsEvent
import java.net.Proxy.Type
import java.util.LinkedHashMap
import java.util.Locale
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Ref

public class SentryFlutter {
   public final var autoPerformanceTracingEnabled: Boolean
      internal set

   private fun updateReplayOptions(options: SentryAndroidOptions, data: Map<String, Any>) {
      val var6: SentryReplayOptions = var1.getSessionReplay();
      var var4: Any = var2.get("quality");
      if (var4 is java.lang.String) {
         var4 = var4 as java.lang.String;
      } else {
         var4 = null;
      }

      if (var4 == "low") {
         var4 = SentryReplayOptions.SentryReplayQuality.LOW;
      } else if (var4 == "high") {
         var4 = SentryReplayOptions.SentryReplayQuality.HIGH;
      } else {
         var4 = SentryReplayOptions.SentryReplayQuality.MEDIUM;
      }

      var6.setQuality((SentryReplayOptions.SentryReplayQuality)var4);
      var4 = var2.get("sessionSampleRate");
      if (var4 is java.lang.Number) {
         var4 = var4 as java.lang.Number;
      } else {
         var4 = null;
      }

      if (var4 != null) {
         var4 = var4.doubleValue();
      } else {
         var4 = null;
      }

      var6.setSessionSampleRate((java.lang.Double)var4);
      var4 = var2.get("onErrorSampleRate");
      if (var4 is java.lang.Number) {
         var4 = var4 as java.lang.Number;
      } else {
         var4 = null;
      }

      if (var4 != null) {
         var4 = var4.doubleValue();
      } else {
         var4 = null;
      }

      var6.setOnErrorSampleRate((java.lang.Double)var4);
      var6.setTrackOrientationChange(false);
      var4 = var2.get("tags");
      var2 = null;
      if (var4 is java.util.Map) {
         var2 = var4 as java.util.Map;
      }

      var4 = var2;
      if (var2 == null) {
         var4 = MapsKt.emptyMap();
      }

      var1.setBeforeSendReplay(new SentryFlutter$$ExternalSyntheticLambda0((java.util.Map)var4));
   }

   @JvmStatic
   fun `updateReplayOptions$lambda$4`(var0: java.util.Map, var1: SentryReplayEvent, var2: Hint): SentryReplayEvent {
      val var7: ReplayRecording = var2.getReplayRecording();
      if (var7 != null) {
         val var8: java.util.List = var7.getPayload();
         if (var8 != null) {
            var var3: java.util.Iterator = var8.iterator();

            do {
               if (!var3.hasNext()) {
                  var9 = null;
                  break;
               }

               var9 = var3.next();
            } while (!((RRWebEvent)var9 instanceof RRWebOptionsEvent));

            val var10: RRWebEvent = var9 as RRWebEvent;
            if (var9 as RRWebEvent != null) {
               val var11: java.util.Map = (var10 as RRWebOptionsEvent).getOptionsPayload();
               val var4: LinkedHashMap = new LinkedHashMap();

               for (Entry var6 : var11.entrySet()) {
                  val var12: java.lang.String = var6.getKey() as java.lang.String;
                  if (StringsKt.contains$default(var12, "mask", false, 2, null)) {
                     var4.put(var6.getKey(), var6.getValue());
                  }
               }

               var3 = var4.entrySet().iterator();

               while (var3.hasNext()) {
                  var11.remove((var3.next() as Entry).getKey() as java.lang.String);
               }

               var11.putAll(var0);
            }
         }
      }

      return var1;
   }

   public fun updateOptions(options: SentryAndroidOptions, data: Map<String, Any>) {
      SentryFlutterKt.access$getIfNotNull(var2, "dsn", (new Function1<java.lang.String, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.lang.String var1) {
            this.$options.setDsn(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "debug", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setDebug(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "environment", (new Function1<java.lang.String, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.lang.String var1) {
            this.$options.setEnvironment(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "release", (new Function1<java.lang.String, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.lang.String var1) {
            this.$options.setRelease(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "dist", (new Function1<java.lang.String, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.lang.String var1) {
            this.$options.setDist(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "enableAutoSessionTracking", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setEnableAutoSessionTracking(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "autoSessionTrackingIntervalMillis", (new Function1<java.lang.Long, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(long var1) {
            this.$options.setSessionTrackingIntervalMillis(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "anrTimeoutIntervalMillis", (new Function1<java.lang.Long, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(long var1) {
            this.$options.setAnrTimeoutIntervalMillis(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "attachThreads", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setAttachThreads(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "attachStacktrace", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setAttachStacktrace(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "enableAutoNativeBreadcrumbs", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setEnableActivityLifecycleBreadcrumbs(var1);
            this.$options.setEnableAppLifecycleBreadcrumbs(var1);
            this.$options.setEnableSystemEventBreadcrumbs(var1);
            this.$options.setEnableAppComponentBreadcrumbs(var1);
            this.$options.setEnableUserInteractionBreadcrumbs(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "maxBreadcrumbs", (new Function1<Integer, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(int var1) {
            this.$options.setMaxBreadcrumbs(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "maxCacheItems", (new Function1<Integer, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(int var1) {
            this.$options.setMaxCacheItems(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "diagnosticLevel", (new Function1<java.lang.String, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.lang.String var1) {
            if (this.$options.isDebug()) {
               val var2: Locale = Locale.ROOT;
               var1 = var1.toUpperCase(var2);
               this.$options.setDiagnosticLevel(SentryLevel.valueOf(var1));
            }
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "anrEnabled", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setAnrEnabled(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "sendDefaultPii", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setSendDefaultPii(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "enableNdkScopeSync", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setEnableScopeSync(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "proguardUuid", (new Function1<java.lang.String, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.lang.String var1) {
            this.$options.setProguardUuid(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "enableSpotlight", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setEnableSpotlight(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "spotlightUrl", (new Function1<java.lang.String, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.lang.String var1) {
            this.$options.setSpotlightConnectionUrl(var1);
         }
      }) as Function1);
      var var4: java.lang.Boolean = (java.lang.Boolean)var2.get("enableNativeCrashHandling");
      if (var4 is java.lang.Boolean) {
         var4 = var4;
      } else {
         var4 = null;
      }

      val var3: Boolean;
      if (var4 != null) {
         var3 = var4;
      } else {
         var3 = true;
      }

      if (!var3) {
         var1.setEnableUncaughtExceptionHandler(false);
         var1.setAnrEnabled(false);
      }

      SentryFlutterKt.access$getIfNotNull(var2, "enableAutoPerformanceTracing", (new Function1<java.lang.Boolean, Unit>(this) {
         final SentryFlutter this$0;

         {
            super(1);
            this.this$0 = var1;
         }

         public final void invoke(boolean var1) {
            if (var1) {
               this.this$0.setAutoPerformanceTracingEnabled(true);
            }
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "sendClientReports", (new Function1<java.lang.Boolean, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(boolean var1) {
            this.$options.setSendClientReports(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "maxAttachmentSize", (new Function1<java.lang.Long, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(long var1) {
            this.$options.setMaxAttachmentSize(var1);
         }
      }) as Function1);
      val var6: Ref.ObjectRef = new Ref.ObjectRef();
      var6.element = (T)var1.getSdkVersion();
      if (var6.element == null) {
         var6.element = (T)(new SdkVersion("sentry.java.android.flutter", "7.22.1"));
      } else {
         (var6.element as SdkVersion).setName("sentry.java.android.flutter");
      }

      var1.setSdkVersion(var6.element as SdkVersion);
      var1.setSentryClientName("sentry.java.android.flutter/7.22.1");
      var1.setNativeSdkName("sentry.native.android.flutter");
      SentryFlutterKt.access$getIfNotNull(
         var2,
         "sdk",
         (
            new Function1<java.util.Map<java.lang.String, ? extends Object>, Unit>(var6) {
               final Ref.ObjectRef<SdkVersion> $sdkVersion;

               {
                  super(1);
                  this.$sdkVersion = var1;
               }

               public final void invoke(java.util.Map<java.lang.String, ? extends Object> var1) {
                  SentryFlutterKt.access$getIfNotNull(
                     var1, "integrations", (new Function1<java.util.List<? extends java.lang.String>, Unit>(this.$sdkVersion) {
                        final Ref.ObjectRef<SdkVersion> $sdkVersion;

                        {
                           super(1);
                           this.$sdkVersion = var1;
                        }

                        public final void invoke(java.util.List<java.lang.String> var1) {
                           val var2: java.lang.Iterable = var1;
                           val var4: Ref.ObjectRef = this.$sdkVersion;

                           for (java.lang.String var3 : var2) {
                              (var4.element as SdkVersion).addIntegration(var3);
                           }
                        }
                     }) as Function1
                  );
                  SentryFlutterKt.access$getIfNotNull(
                     var1,
                     "packages",
                     (new Function1<java.util.List<? extends java.util.Map<java.lang.String, ? extends java.lang.String>>, Unit>(this.$sdkVersion) {
                        final Ref.ObjectRef<SdkVersion> $sdkVersion;

                        {
                           super(1);
                           this.$sdkVersion = var1;
                        }

                        public final void invoke(java.util.List<? extends java.util.Map<java.lang.String, java.lang.String>> var1) {
                           val var2: java.lang.Iterable = var1;
                           val var6: Ref.ObjectRef = this.$sdkVersion;

                           for (java.util.Map var4 : var2) {
                              val var3: SdkVersion = var6.element as SdkVersion;
                              var var5: Any = var4.get("name");
                              var5 = var5 as java.lang.String;
                              val var8: Any = var4.get("version");
                              var3.addPackage((java.lang.String)var5, var8 as java.lang.String);
                           }
                        }
                     }) as Function1
                  );
               }
            }
         ) as Function1
      );
      var1.setBeforeSend(new BeforeSendCallbackImpl());
      SentryFlutterKt.access$getIfNotNull(var2, "connectionTimeoutMillis", (new Function1<Integer, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(int var1) {
            this.$options.setConnectionTimeoutMillis(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "readTimeoutMillis", (new Function1<Integer, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(int var1) {
            this.$options.setReadTimeoutMillis(var1);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "proxy", (new Function1<java.util.Map<java.lang.String, ? extends Object>, Unit>(var1) {
         final SentryAndroidOptions $options;

         {
            super(1);
            this.$options = var1;
         }

         public final void invoke(java.util.Map<java.lang.String, ? extends Object> var1) {
            val var6: SentryAndroidOptions = this.$options;
            val var5: SentryOptions.Proxy = new SentryOptions.Proxy();
            var var3: java.lang.String = (java.lang.String)var1.get("host");
            if (var3 is java.lang.String) {
               var3 = var3;
            } else {
               var3 = null;
            }

            var5.setHost((java.lang.String)var3);
            var3 = var1.get("port");
            if (var3 is Int) {
               var3 = var3 as Int;
            } else {
               var3 = null;
            }

            if (var3 != null) {
               var3 = java.lang.String.valueOf((var3 as java.lang.Number).intValue());
            } else {
               var3 = null;
            }

            var5.setPort((java.lang.String)var3);
            var3 = var1.get("type");
            if (var3 is java.lang.String) {
               var3 = var3 as java.lang.String;
            } else {
               var3 = null;
            }

            if (var3 != null) {
               try {
                  val var7: Locale = Locale.ROOT;
                  var3 = var3.toUpperCase(var7);
                  var17 = Type.valueOf(var3);
               } catch (var8: IllegalArgumentException) {
                  val var16: StringBuilder = new StringBuilder("Could not parse `type` from proxy json: ");
                  var16.append(var1);
                  Log.w("Sentry", var16.toString());
                  var17 = null;
               }

               var5.setType(var17);
            }

            var3 = (java.lang.String)var1.get("user");
            if (var3 is java.lang.String) {
               var3 = var3;
            } else {
               var3 = null;
            }

            var5.setUser(var3);
            var3 = (java.lang.String)var1.get("pass");
            var var9: java.lang.String = null;
            if (var3 is java.lang.String) {
               var9 = var3;
            }

            var5.setPass(var9);
            var6.setProxy(var5);
         }
      }) as Function1);
      SentryFlutterKt.access$getIfNotNull(var2, "replay", (new Function1<java.util.Map<java.lang.String, ? extends Object>, Unit>(this, var1, var2) {
         final java.util.Map<java.lang.String, Object> $data;
         final SentryAndroidOptions $options;
         final SentryFlutter this$0;

         {
            super(1);
            this.this$0 = var1;
            this.$options = var2;
            this.$data = var3;
         }

         public final void invoke(java.util.Map<java.lang.String, ? extends Object> var1) {
            SentryFlutter.access$updateReplayOptions(this.this$0, this.$options, var1);
            SentryFlutterKt.access$getIfNotNull(this.$data, "sdk", (new Function1<java.util.Map<java.lang.String, ? extends Object>, Unit>(this.$options) {
               final SentryAndroidOptions $options;

               {
                  super(1);
                  this.$options = var1;
               }

               public final void invoke(java.util.Map<java.lang.String, ? extends Object> var1) {
                  val var2: SentryReplayOptions = this.$options.getSessionReplay();
                  var var3: Any = var1.get("name");
                  var3 = var3 as java.lang.String;
                  val var4: Any = var1.get("version");
                  var2.setSdkVersion(new SdkVersion((java.lang.String)var3, var4 as java.lang.String));
               }
            }) as Function1);
         }
      }) as Function1);
   }

   public companion object {
      internal const val ANDROID_SDK: String
      internal const val FLUTTER_SDK: String
      internal const val NATIVE_SDK: String
   }
}
