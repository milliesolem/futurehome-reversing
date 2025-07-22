package io.sentry.flutter

import android.os.Handler
import android.os.Looper
import android.util.Log
import io.flutter.plugin.common.MethodChannel
import io.sentry.android.replay.Recorder
import io.sentry.android.replay.ReplayIntegration
import io.sentry.android.replay.ScreenshotRecorderConfig
import java.io.File

internal class SentryFlutterReplayRecorder(channel: MethodChannel, integration: ReplayIntegration) : Recorder {
   private final val channel: MethodChannel
   private final val integration: ReplayIntegration

   init {
      this.channel = var1;
      this.integration = var2;
   }

   @JvmStatic
   fun `pause$lambda$2`(var0: SentryFlutterReplayRecorder) {
      try {
         var0.channel.invokeMethod("ReplayRecorder.pause", null);
      } catch (var1: Exception) {
         Log.w("Sentry", "Failed to pause replay recorder", var1);
      }
   }

   @JvmStatic
   fun `resume$lambda$1`(var0: SentryFlutterReplayRecorder) {
      try {
         var0.channel.invokeMethod("ReplayRecorder.resume", null);
      } catch (var1: Exception) {
         Log.w("Sentry", "Failed to resume replay recorder", var1);
      }
   }

   @JvmStatic
   fun `start$lambda$0`(var0: SentryFlutterReplayRecorder, var1: java.lang.String, var2: ScreenshotRecorderConfig) {
      try {
         var0.channel
            .invokeMethod(
               "ReplayRecorder.start",
               MapsKt.mapOf(
                  new Pair[]{
                     TuplesKt.to("directory", var1),
                     TuplesKt.to("width", var2.getRecordingWidth()),
                     TuplesKt.to("height", var2.getRecordingHeight()),
                     TuplesKt.to("frameRate", var2.getFrameRate()),
                     TuplesKt.to("replayId", var0.integration.getReplayId().toString())
                  }
               )
            );
      } catch (var3: Exception) {
         Log.w("Sentry", "Failed to start replay recorder", var3);
      }
   }

   @JvmStatic
   fun `stop$lambda$3`(var0: SentryFlutterReplayRecorder) {
      try {
         var0.channel.invokeMethod("ReplayRecorder.stop", null);
      } catch (var1: Exception) {
         Log.w("Sentry", "Failed to stop replay recorder", var1);
      }
   }

   public override fun close() {
      this.stop();
   }

   public override fun pause() {
      new Handler(Looper.getMainLooper()).post(new SentryFlutterReplayRecorder$$ExternalSyntheticLambda0(this));
   }

   public override fun resume() {
      new Handler(Looper.getMainLooper()).post(new SentryFlutterReplayRecorder$$ExternalSyntheticLambda3(this));
   }

   public override fun start(recorderConfig: ScreenshotRecorderConfig) {
      if (var1.getRecordingHeight() > 16 || var1.getRecordingWidth() > 16) {
         val var2: File = this.integration.getReplayCacheDir();
         val var3: java.lang.String;
         if (var2 != null) {
            var3 = var2.getAbsolutePath();
         } else {
            var3 = null;
         }

         if (var3 == null) {
            Log.w("Sentry", "Replay cache directory is null, can't start replay recorder.");
         } else {
            new Handler(Looper.getMainLooper()).post(new SentryFlutterReplayRecorder$$ExternalSyntheticLambda2(this, var3, var1));
         }
      }
   }

   public override fun stop() {
      new Handler(Looper.getMainLooper()).post(new SentryFlutterReplayRecorder$$ExternalSyntheticLambda1(this));
   }
}
