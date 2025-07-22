package io.sentry.flutter

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.os.Looper
import android.os.Build.VERSION
import android.util.Log
import android.view.Display
import android.view.Window
import android.view.WindowManager
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.sentry.Breadcrumb
import io.sentry.DateUtils
import io.sentry.HubAdapter
import io.sentry.IScope
import io.sentry.Sentry
import io.sentry.SentryDate
import io.sentry.SentryOptions
import io.sentry.SentryReplayOptions
import io.sentry.android.core.ActivityFramesTracker
import io.sentry.android.core.InternalSentrySdk
import io.sentry.android.core.LoadClass
import io.sentry.android.core.SentryAndroid
import io.sentry.android.core.SentryAndroidOptions
import io.sentry.android.core.performance.ActivityLifecycleTimeSpan
import io.sentry.android.core.performance.AppStartMetrics
import io.sentry.android.core.performance.TimeSpan
import io.sentry.android.replay.Recorder
import io.sentry.android.replay.ReplayIntegration
import io.sentry.android.replay.ScreenshotRecorderConfig
import io.sentry.protocol.DebugImage
import io.sentry.protocol.MeasurementValue
import io.sentry.protocol.SentryId
import io.sentry.protocol.User
import io.sentry.transport.CurrentDateProvider
import io.sentry.transport.ICurrentDateProvider
import java.io.File
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Arrays
import java.util.LinkedHashMap
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Intrinsics
import kotlin.math.MathKt

public class SentryFlutterPlugin : FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {
   private final var activity: WeakReference<Activity>?
   private final lateinit var channel: MethodChannel
   private final lateinit var context: Context
   private final var framesTracker: ActivityFramesTracker?
   private final var pluginRegistrationTime: Long?
   private final lateinit var replay: ReplayIntegration
   private final var replayConfig: ScreenshotRecorderConfig = new ScreenshotRecorderConfig(16, 16, 1.0F, 1.0F, 1, 75000)
   private final lateinit var sentryFlutter: SentryFlutter

   private fun addBreadcrumb(breadcrumb: Map<String, Any?>?, result: Result) {
      if (var1 != null) {
         val var3: SentryOptions = HubAdapter.getInstance().getOptions();
         Sentry.addBreadcrumb(Breadcrumb.fromMap(var1, var3));
      }

      var2.success("");
   }

   private fun addReplayScreenshot(path: String?, timestamp: Long?, result: Result) {
      if (var1 != null && var2 != null) {
         var var4: ReplayIntegration = this.replay;
         if (this.replay == null) {
            Intrinsics.throwUninitializedPropertyAccessException("replay");
            var4 = null;
         }

         var4.onScreenshotRecorded(new File(var1), var2);
         var3.success("");
      } else {
         var3.error("5", "Arguments are null", null);
      }
   }

   private fun TimeSpan.addToMap(map: MutableMap<String, Any?>) {
      if (var1.getStartTimestamp() != null) {
         val var3: java.lang.String = var1.getDescription();
         if (var3 != null) {
            var2.put(
               var3,
               MapsKt.mapOf(
                  new Pair[]{
                     TuplesKt.to("startTimestampMsSinceEpoch", var1.getStartTimestampMs()),
                     TuplesKt.to("stopTimestampMsSinceEpoch", var1.getProjectedStopTimestampMs())
                  }
               )
            );
         }
      }
   }

   private fun beginNativeFrames(result: Result) {
      var var2: SentryFlutter = this.sentryFlutter;
      if (this.sentryFlutter == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sentryFlutter");
         var2 = null;
      }

      if (!var2.getAutoPerformanceTracingEnabled()) {
         var1.success(null);
      } else {
         if (this.activity != null) {
            val var6: Activity = this.activity.get();
            if (var6 != null && this.framesTracker != null) {
               this.framesTracker.addActivity(var6);
            }
         }

         var1.success(null);
      }
   }

   private fun captureEnvelope(call: MethodCall, result: Result) {
      if (!Sentry.isEnabled()) {
         var2.error("1", "The Sentry Android SDK is disabled", null);
      } else {
         val var5: java.util.List = var1.arguments();
         var var6: java.util.List = var5;
         if (var5 == null) {
            var6 = CollectionsKt.emptyList();
         }

         if (!var6.isEmpty()) {
            val var8: ByteArray = CollectionsKt.first(var6);
            var var3: Boolean = true;
            val var7: Any = var6.get(1);
            val var4: Boolean = var7 as java.lang.Boolean;
            if (var8 != null) {
               if (var8.length != 0) {
                  var3 = false;
               }

               if (!var3) {
                  if (InternalSentrySdk.captureEnvelope(var8, var4) != null) {
                     var2.success("");
                  } else {
                     var2.error("2", "Failed to capture envelope", null);
                  }

                  return;
               }
            }
         }

         var2.error("3", "Envelope is null or empty", null);
      }
   }

   private fun captureReplay(isCrash: Boolean?, result: Result) {
      if (var1 == null) {
         var2.error("5", "Arguments are null", null);
      } else {
         var var3: ReplayIntegration = this.replay;
         if (this.replay == null) {
            Intrinsics.throwUninitializedPropertyAccessException("replay");
            var3 = null;
         }

         var3.captureReplay(var1);
         var var6: ReplayIntegration = this.replay;
         if (this.replay == null) {
            Intrinsics.throwUninitializedPropertyAccessException("replay");
            var6 = null;
         }

         var2.success(var6.getReplayId().toString());
      }
   }

   private fun clearBreadcrumbs(result: Result) {
      Sentry.clearBreadcrumbs();
      var1.success("");
   }

   private fun closeNativeSdk(result: Result) {
      HubAdapter.getInstance().close();
      if (this.framesTracker != null) {
         this.framesTracker.stop();
      }

      this.framesTracker = null;
      var1.success("");
   }

   private fun displayRefreshRate(result: Result) {
      var var3: Int = null;
      if (VERSION.SDK_INT >= 30) {
         var var6: Display;
         label25: {
            if (this.activity != null) {
               val var5: Activity = this.activity.get();
               if (var5 != null) {
                  var6 = Share$$ExternalSyntheticApiModelOutline0.m(var5);
                  break label25;
               }
            }

            var6 = null;
         }

         if (var6 != null) {
            var3 = (int)var6.getRefreshRate();
         }
      } else {
         var var11: Display;
         label32: {
            if (this.activity != null) {
               val var8: Activity = this.activity.get();
               if (var8 != null) {
                  val var9: Window = var8.getWindow();
                  if (var9 != null) {
                     val var10: WindowManager = var9.getWindowManager();
                     if (var10 != null) {
                        var11 = var10.getDefaultDisplay();
                        break label32;
                     }
                  }
               }
            }

            var11 = null;
         }

         if (var11 != null) {
            var3 = (int)var11.getRefreshRate();
         }
      }

      var1.success(var3);
   }

   private fun endNativeFrames(id: String?, result: Result) {
      val var13: Activity;
      if (this.activity != null) {
         var13 = this.activity.get();
      } else {
         var13 = null;
      }

      var var7: SentryFlutter = this.sentryFlutter;
      if (this.sentryFlutter == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sentryFlutter");
         var7 = null;
      }

      if (var7.getAutoPerformanceTracingEnabled() && var13 != null && var1 != null) {
         val var9: SentryId = new SentryId(var1);
         if (this.framesTracker != null) {
            this.framesTracker.setMetrics(var13, var9);
         }

         val var10: java.util.Map;
         if (this.framesTracker != null) {
            var10 = this.framesTracker.takeMetrics(var9);
         } else {
            var10 = null;
         }

         var var3: Int;
         label70: {
            if (var10 != null) {
               val var15: MeasurementValue = var10.get("frames_total") as MeasurementValue;
               if (var15 != null) {
                  val var16: java.lang.Number = var15.getValue();
                  if (var16 != null) {
                     var3 = var16.intValue();
                     break label70;
                  }
               }
            }

            var3 = 0;
         }

         var var4: Int;
         label64: {
            if (var10 != null) {
               val var17: MeasurementValue = var10.get("frames_slow") as MeasurementValue;
               if (var17 != null) {
                  val var18: java.lang.Number = var17.getValue();
                  if (var18 != null) {
                     var4 = var18.intValue();
                     break label64;
                  }
               }
            }

            var4 = 0;
         }

         var var5: Int;
         label58: {
            if (var10 != null) {
               val var11: MeasurementValue = var10.get("frames_frozen") as MeasurementValue;
               if (var11 != null) {
                  val var12: java.lang.Number = var11.getValue();
                  if (var12 != null) {
                     var5 = var12.intValue();
                     break label58;
                  }
               }
            }

            var5 = 0;
         }

         if (var3 == 0 && var4 == 0 && var5 == 0) {
            var2.success(null);
         } else {
            var2.success(MapsKt.mapOf(new Pair[]{TuplesKt.to("totalFrames", var3), TuplesKt.to("slowFrames", var4), TuplesKt.to("frozenFrames", var5)}));
         }
      } else {
         if (var1 == null) {
            Log.w("Sentry", "Parameter id cannot be null when calling endNativeFrames.");
         }

         var2.success(null);
      }
   }

   private fun fetchNativeAppStart(result: Result) {
      var var5: SentryFlutter = this.sentryFlutter;
      if (this.sentryFlutter == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sentryFlutter");
         var5 = null;
      }

      if (!var5.getAutoPerformanceTracingEnabled()) {
         var1.success(null);
      } else {
         val var7: AppStartMetrics = AppStartMetrics.getInstance();
         if (var7.isAppLaunchedInForeground() && var7.getAppStartTimeSpan().getDurationMs() <= 60000L) {
            var var8: TimeSpan = var7.getAppStartTimeSpan();
            val var10: SentryDate = var8.getStartTimestamp();
            val var4: Boolean;
            if (var7.getAppStartType() === AppStartMetrics.AppStartType.COLD) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (var10 == null) {
               Log.w("Sentry", "App start won't be sent due to missing appStartTime");
               var1.success(null);
            } else {
               val var12: java.util.Map = MapsKt.mutableMapOf(
                  new Pair[]{
                     TuplesKt.to("pluginRegistrationTime", this.pluginRegistrationTime),
                     TuplesKt.to("appStartTime", DateUtils.nanosToMillis((double)var10.nanoTimestamp())),
                     TuplesKt.to("isColdStart", var4)
                  }
               );
               val var11: java.util.Map = new LinkedHashMap();
               val var9: TimeSpan = new TimeSpan();
               var9.setDescription("Process Initialization");
               var9.setStartUnixTimeMs(var8.getStartTimestampMs());
               var9.setStartedAt(var8.getStartUptimeMs());
               var9.setStoppedAt(var7.getClassLoadedUptimeMs());
               this.addToMap(var9, var11);
               var8 = var7.getApplicationOnCreateTimeSpan();
               this.addToMap(var8, var11);
               val var16: java.util.List = var7.getContentProviderOnCreateTimeSpans();

               for (TimeSpan var20 : var16) {
                  this.addToMap(var20, var11);
               }

               val var13: java.util.List = var7.getActivityLifecycleTimeSpans();

               for (ActivityLifecycleTimeSpan var21 : var13) {
                  var8 = var21.getOnCreate();
                  this.addToMap(var8, var11);
                  var8 = var21.getOnStart();
                  this.addToMap(var8, var11);
               }

               var12.put("nativeSpanTimes", var11);
               var1.success(var12);
            }
         } else {
            Log.w("Sentry", "Invalid app start data: app not launched in foreground or app start took too long (>60s)");
            var1.success(null);
         }
      }
   }

   private fun initNativeSdk(call: MethodCall, result: Result) {
      var var3: Context = null;
      if (this.context == null) {
         var2.error("1", "Context is null", null);
      } else {
         val var6: java.util.Map = var1.arguments();
         var var5: java.util.Map = var6;
         if (var6 == null) {
            var5 = MapsKt.emptyMap();
         }

         if (var5.isEmpty()) {
            var2.error("4", "Arguments is null or empty", null);
         } else {
            if (this.context == null) {
               Intrinsics.throwUninitializedPropertyAccessException("context");
            } else {
               var3 = this.context;
            }

            SentryAndroid.init(var3, new SentryFlutterPlugin$$ExternalSyntheticLambda4(this, var5));
            var2.success("");
         }
      }
   }

   @JvmStatic
   fun `initNativeSdk$lambda$0`(var0: SentryFlutterPlugin, var1: java.util.Map, var2: SentryAndroidOptions) {
      var var3: SentryFlutter = var0.sentryFlutter;
      if (var0.sentryFlutter == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sentryFlutter");
         var3 = null;
      }

      var3.updateOptions(var2, var1);
      var var6: SentryFlutter = var0.sentryFlutter;
      if (var0.sentryFlutter == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sentryFlutter");
         var6 = null;
      }

      if (var6.getAutoPerformanceTracingEnabled()) {
         var0.framesTracker = new ActivityFramesTracker(new LoadClass(), var2);
      }

      var0.setupReplay(var2);
   }

   private fun loadContexts(result: Result) {
      val var5: SentryOptions = HubAdapter.getInstance().getOptions();
      val var2: Boolean = var5 is SentryAndroidOptions;
      var var3: Context = null;
      if (!var2) {
         var1.success(null);
      } else {
         val var6: IScope = InternalSentrySdk.getCurrentScope();
         if (this.context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
         } else {
            var3 = this.context;
         }

         val var7: java.util.Map = InternalSentrySdk.serializeScope(var3, var5 as SentryAndroidOptions, var6);
         var1.success(var7);
      }
   }

   private fun loadImageList(call: MethodCall, result: Result) {
      val var4: SentryOptions = HubAdapter.getInstance().getOptions();
      val var6: SentryAndroidOptions = var4 as SentryAndroidOptions;
      val var12: java.util.List = var1.arguments();
      var var7: java.util.List = var12;
      if (var12 == null) {
         var7 = CollectionsKt.emptyList();
      }

      val var9: java.util.List;
      if (var7.isEmpty()) {
         val var16: java.util.List = var6.getDebugImagesLoader().loadDebugImages();
         var var8: java.util.List = null;
         if (var16 != null) {
            var8 = CollectionsKt.toList(var16);
         }

         var9 = this.serialize(var8);
      } else {
         val var10: java.util.Set = var6.getDebugImagesLoader().loadDebugImagesForAddresses(CollectionsKt.toSet(var7));
         var var14: java.util.List = null;
         if (var10 != null) {
            val var15: java.util.Collection = var10;
            var var11: java.util.Collection = var10;
            if (var15.isEmpty()) {
               var11 = var6.getDebugImagesLoader().loadDebugImages();
            }

            var14 = null;
            if (var11 != null) {
               var14 = CollectionsKt.toList(var11);
            }
         }

         var9 = this.serialize(var14);
      }

      var2.success(var9);
   }

   private fun removeContexts(key: String?, result: Result) {
      if (var1 == null) {
         var2.success("");
      } else {
         Sentry.configureScope(new SentryFlutterPlugin$$ExternalSyntheticLambda3(var1, var2));
      }
   }

   @JvmStatic
   fun `removeContexts$lambda$7`(var0: java.lang.String, var1: MethodChannel.Result, var2: IScope) {
      var2.removeContexts(var0);
      var1.success("");
   }

   private fun removeExtra(key: String?, result: Result) {
      if (var1 == null) {
         var2.success("");
      } else {
         Sentry.removeExtra(var1);
         var2.success("");
      }
   }

   private fun removeTag(key: String?, result: Result) {
      if (var1 == null) {
         var2.success("");
      } else {
         Sentry.removeTag(var1);
         var2.success("");
      }
   }

   private fun List<DebugImage>?.serialize(): List<Map<String, Any?>>? {
      if (var1 != null) {
         val var3: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));
         val var5: java.util.Iterator = var1.iterator();

         while (var5.hasNext()) {
            var3.add(this.serialize(var5.next() as DebugImage));
         }

         var1 = var3 as java.util.List;
      } else {
         var1 = null;
      }

      return var1;
   }

   private fun DebugImage.serialize(): Map<String, Any?> {
      return MapsKt.mapOf(
         new Pair[]{
            TuplesKt.to("image_addr", var1.getImageAddr()),
            TuplesKt.to("image_size", var1.getImageSize()),
            TuplesKt.to("code_file", var1.getCodeFile()),
            TuplesKt.to("type", var1.getType()),
            TuplesKt.to("debug_id", var1.getDebugId()),
            TuplesKt.to("code_id", var1.getCodeId()),
            TuplesKt.to("debug_file", var1.getDebugFile())
         }
      );
   }

   private fun setContexts(key: String?, value: Any?, result: Result) {
      if (var1 != null && var2 != null) {
         Sentry.configureScope(new SentryFlutterPlugin$$ExternalSyntheticLambda2(var1, var2, var3));
      } else {
         var3.success("");
      }
   }

   @JvmStatic
   fun `setContexts$lambda$6`(var0: java.lang.String, var1: Any, var2: MethodChannel.Result, var3: IScope) {
      var3.setContexts(var0, var1);
      var2.success("");
   }

   private fun setExtra(key: String?, value: String?, result: Result) {
      if (var1 != null && var2 != null) {
         Sentry.setExtra(var1, var2);
         var3.success("");
      } else {
         var3.success("");
      }
   }

   private fun setReplayConfig(call: MethodCall, result: Result) {
      var var16: java.lang.Double = var1.argument("width");
      if (var16 is java.lang.Double) {
         var16 = var16;
      } else {
         var16 = null;
      }

      var var5: Double = 0.0;
      var var3: Double;
      if (var16 != null) {
         var3 = var16;
      } else {
         var3 = 0.0;
      }

      var16 = var1.argument("height");
      if (var16 is java.lang.Double) {
         var16 = var16;
      } else {
         var16 = null;
      }

      if (var16 != null) {
         var5 = var16;
      }

      if (var3 < var5) {
         val var30: SentryFlutterPlugin.Companion = Companion;
         val var7: Double = SentryFlutterPlugin.Companion.access$adjustReplaySizeToBlockSize(Companion, var3);
         var3 = SentryFlutterPlugin.Companion.access$adjustReplaySizeToBlockSize(var30, var5 * (var7 / var3));
         var5 = var7;
      } else {
         val var31: SentryFlutterPlugin.Companion = Companion;
         val var26: Double = SentryFlutterPlugin.Companion.access$adjustReplaySizeToBlockSize(Companion, var5);
         var5 = SentryFlutterPlugin.Companion.access$adjustReplaySizeToBlockSize(var31, var3 * (var26 / var5));
         var3 = var26;
      }

      var var32: Context = this.context;
      if (this.context == null) {
         Intrinsics.throwUninitializedPropertyAccessException("context");
         var32 = null;
      }

      var16 = (java.lang.Double)var32.getSystemService("window");
      val var38: WindowManager = var16 as WindowManager;
      val var34: Rect;
      if (VERSION.SDK_INT >= 30) {
         var34 = Share$$ExternalSyntheticApiModelOutline0.m(Share$$ExternalSyntheticApiModelOutline0.m(var38));
      } else {
         val var35: Point = new Point();
         var38.getDefaultDisplay().getRealSize(var35);
         var34 = new Rect(0, 0, var35.x, var35.y);
      }

      val var14: Int = MathKt.roundToInt(var5);
      val var13: Int = MathKt.roundToInt(var3);
      val var10: Float = (float)var5 / var34.width();
      val var9: Float = (float)var3 / var34.height();
      var16 = var1.argument("frameRate");
      val var37: Int;
      if (var16 is Int) {
         var37 = var16 as Int;
      } else {
         var37 = null;
      }

      val var11: Int;
      if (var37 != null) {
         var11 = var37;
      } else {
         var11 = 0;
      }

      var var19: Int = var1.argument("bitRate");
      if (var19 is Int) {
         var19 = var19;
      } else {
         var19 = null;
      }

      val var12: Int;
      if (var19 != null) {
         var12 = var19;
      } else {
         var12 = 0;
      }

      val var21: ScreenshotRecorderConfig = new ScreenshotRecorderConfig(var14, var13, var10, var9, var11, var12);
      this.replayConfig = var21;
      val var22: java.lang.String = java.lang.String.format(
         "Configuring replay: %dx%d at %d FPS, %d BPS",
         Arrays.copyOf(
            new Object[]{var21.getRecordingWidth(), this.replayConfig.getRecordingHeight(), this.replayConfig.getFrameRate(), this.replayConfig.getBitRate()},
            4
         )
      );
      Log.i("Sentry", var22);
      var var23: ReplayIntegration = this.replay;
      if (this.replay == null) {
         Intrinsics.throwUninitializedPropertyAccessException("replay");
         var23 = null;
      }

      var23.onConfigurationChanged(new Configuration());
      var2.success("");
   }

   private fun setTag(key: String?, value: String?, result: Result) {
      if (var1 != null && var2 != null) {
         Sentry.setTag(var1, var2);
         var3.success("");
      } else {
         var3.success("");
      }
   }

   private fun setUser(user: Map<String, Any?>?, result: Result) {
      if (var1 != null) {
         val var3: SentryOptions = HubAdapter.getInstance().getOptions();
         Sentry.setUser(User.fromMap(var1, var3));
      } else {
         Sentry.setUser(null);
      }

      var2.success("");
   }

   private fun setupReplay(options: SentryAndroidOptions) {
      val var3: java.util.List = var1.getIntegrations();
      CollectionsKt.removeAll(var3, <unrepresentable>.INSTANCE);
      val var6: java.lang.String = var1.getCacheDirPath();
      val var4: SentryReplayOptions = var1.getSessionReplay();
      val var2: Boolean;
      if (!var4.isSessionReplayEnabled() && !var4.isSessionReplayForErrorsEnabled()) {
         var2 = false;
      } else {
         var2 = true;
      }

      if (var6 != null && var2) {
         var var7: Context = this.context;
         if (this.context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
            var7 = null;
         }

         val var5: ICurrentDateProvider = CurrentDateProvider.getInstance();
         val var8: ReplayIntegration = new ReplayIntegration(
            var7,
            var5,
            (new Function0<Recorder>(this) {
               final SentryFlutterPlugin this$0;

               {
                  super(0);
                  this.this$0 = var1;
               }

               public final Recorder invoke() {
                  val var3: MethodChannel = SentryFlutterPlugin.access$getChannel$p(this.this$0);
                  var var2: ReplayIntegration = null;
                  var var1: MethodChannel = var3;
                  if (var3 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("channel");
                     var1 = null;
                  }

                  val var4: ReplayIntegration = SentryFlutterPlugin.access$getReplay$p(this.this$0);
                  if (var4 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("replay");
                  } else {
                     var2 = var4;
                  }

                  return new SentryFlutterReplayRecorder(var1, var2);
               }
            }) as () -> Recorder,
            (
               new Function1<java.lang.Boolean, ScreenshotRecorderConfig>(this) {
                  final SentryFlutterPlugin this$0;

                  {
                     super(1);
                     this.this$0 = var1;
                  }

                  public final ScreenshotRecorderConfig invoke(boolean var1) {
                     val var2: java.lang.String = java.lang.String.format(
                        "Replay configuration requested. Returning: %dx%d at %d FPS, %d BPS",
                        Arrays.copyOf(
                           new Object[]{
                              SentryFlutterPlugin.access$getReplayConfig$p(this.this$0).getRecordingWidth(),
                              SentryFlutterPlugin.access$getReplayConfig$p(this.this$0).getRecordingHeight(),
                              SentryFlutterPlugin.access$getReplayConfig$p(this.this$0).getFrameRate(),
                              SentryFlutterPlugin.access$getReplayConfig$p(this.this$0).getBitRate()
                           },
                           4
                        )
                     );
                     Log.i("Sentry", var2);
                     return SentryFlutterPlugin.access$getReplayConfig$p(this.this$0);
                  }
               }
            ) as (java.lang.Boolean?) -> ScreenshotRecorderConfig,
            null
         );
         this.replay = var8;
         var8.setBreadcrumbConverter(new SentryFlutterReplayBreadcrumbConverter());
         var var9: ReplayIntegration = this.replay;
         if (this.replay == null) {
            Intrinsics.throwUninitializedPropertyAccessException("replay");
            var9 = null;
         }

         var1.addIntegration(var9);
         var var10: ReplayIntegration = this.replay;
         if (this.replay == null) {
            Intrinsics.throwUninitializedPropertyAccessException("replay");
            var10 = null;
         }

         var1.setReplayController(var10);
      } else {
         var1.setReplayController(null);
      }
   }

   public override fun onAttachedToActivity(binding: ActivityPluginBinding) {
      this.activity = new WeakReference<>(var1.getActivity());
   }

   public override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
      this.pluginRegistrationTime = System.currentTimeMillis();
      val var2: Context = var1.getApplicationContext();
      this.context = var2;
      val var3: MethodChannel = new MethodChannel(var1.getBinaryMessenger(), "sentry_flutter");
      this.channel = var3;
      var3.setMethodCallHandler(this);
      this.sentryFlutter = new SentryFlutter();
   }

   public override fun onDetachedFromActivity() {
      this.activity = null;
      this.framesTracker = null;
   }

   public override fun onDetachedFromActivityForConfigChanges() {
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      if (this.channel != null) {
         var var3: MethodChannel = this.channel;
         if (this.channel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            var3 = null;
         }

         var3.setMethodCallHandler(null);
      }
   }

   public override fun onMethodCall(call: MethodCall, result: Result) {
      val var3: java.lang.String = var1.method;
      if (var1.method != null) {
         switch (var1.method.hashCode()) {
            case -1446499610:
               if (var3.equals("beginNativeFrames")) {
                  this.beginNativeFrames(var2);
                  return;
               }
               break;
            case -905799720:
               if (var3.equals("setTag")) {
                  this.setTag(var1.argument("key"), var1.argument("value"), var2);
                  return;
               }
               break;
            case -853417589:
               if (var3.equals("closeNativeSdk")) {
                  this.closeNativeSdk(var2);
                  return;
               }
               break;
            case -535605191:
               if (var3.equals("displayRefreshRate")) {
                  this.displayRefreshRate(var2);
                  return;
               }
               break;
            case -366888622:
               if (var3.equals("fetchNativeAppStart")) {
                  this.fetchNativeAppStart(var2);
                  return;
               }
               break;
            case -317432340:
               if (var3.equals("removeExtra")) {
                  this.removeExtra(var1.argument("key"), var2);
                  return;
               }
               break;
            case 89815947:
               if (var3.equals("setReplayConfig")) {
                  this.setReplayConfig(var1, var2);
                  return;
               }
               break;
            case 98996078:
               if (var3.equals("addReplayScreenshot")) {
                  this.addReplayScreenshot(var1.argument("path"), var1.argument("timestamp"), var2);
                  return;
               }
               break;
            case 145462582:
               if (var3.equals("captureEnvelope")) {
                  this.captureEnvelope(var1, var2);
                  return;
               }
               break;
            case 263988819:
               if (var3.equals("loadImageList")) {
                  this.loadImageList(var1, var2);
                  return;
               }
               break;
            case 545314163:
               if (var3.equals("initNativeSdk")) {
                  this.initNativeSdk(var1, var2);
                  return;
               }
               break;
            case 716465066:
               if (var3.equals("loadContexts")) {
                  this.loadContexts(var2);
                  return;
               }
               break;
            case 783581208:
               if (var3.equals("endNativeFrames")) {
                  this.endNativeFrames(var1.argument("id"), var2);
                  return;
               }
               break;
            case 1126756228:
               if (var3.equals("addBreadcrumb")) {
                  this.addBreadcrumb(var1.argument("breadcrumb"), var2);
                  return;
               }
               break;
            case 1282363510:
               if (var3.equals("removeTag")) {
                  this.removeTag(var1.argument("key"), var2);
                  return;
               }
               break;
            case 1391678670:
               if (var3.equals("setExtra")) {
                  this.setExtra(var1.argument("key"), var1.argument("value"), var2);
                  return;
               }
               break;
            case 1422008102:
               if (var3.equals("setContexts")) {
                  this.setContexts(var1.argument("key"), var1.argument("value"), var2);
                  return;
               }
               break;
            case 1725209040:
               if (var3.equals("nativeCrash")) {
                  SentryFlutterPlugin.Companion.access$crash(Companion);
                  return;
               }
               break;
            case 1838399555:
               if (var3.equals("clearBreadcrumbs")) {
                  this.clearBreadcrumbs(var2);
                  return;
               }
               break;
            case 1919151821:
               if (var3.equals("captureReplay")) {
                  this.captureReplay(var1.argument("isCrash"), var2);
                  return;
               }
               break;
            case 1985026893:
               if (var3.equals("setUser")) {
                  this.setUser(var1.argument("user"), var2);
                  return;
               }
               break;
            case 2133203272:
               if (var3.equals("removeContexts")) {
                  this.removeContexts(var1.argument("key"), var2);
                  return;
               }
            default:
         }
      }

      var2.notImplemented();
   }

   public override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
   }

   public companion object {
      private const val NATIVE_CRASH_WAIT_TIME: Long

      private fun Double.adjustReplaySizeToBlockSize(): Double {
         val var3: Double = 16;
         val var5: Double = var1 % 16;
         if (var1 % 16 <= 8.0) {
            var1 = var1 - var5;
         } else {
            var1 = var1 + (var3 - var5);
         }

         return var1;
      }

      private fun crash() {
         val var1: RuntimeException = new RuntimeException("FlutterSentry Native Integration: Sample RuntimeException");
         val var2: Thread = Looper.getMainLooper().getThread();
         var2.getUncaughtExceptionHandler().uncaughtException(var2, var1);
         var2.join(500L);
      }
   }
}
