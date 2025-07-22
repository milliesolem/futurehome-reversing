package io.sentry.android.replay.capture

import android.graphics.Bitmap
import io.sentry.IHub
import io.sentry.IScope
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.SentryReplayEvent.ReplayType
import io.sentry.android.replay.ReplayCache
import io.sentry.android.replay.ScreenshotRecorderConfig
import io.sentry.android.replay.capture.CaptureStrategy.ReplaySegment
import io.sentry.android.replay.util.ExecutorsKt
import io.sentry.protocol.SentryId
import io.sentry.transport.ICurrentDateProvider
import io.sentry.util.FileUtils
import java.io.File
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.ScheduledExecutorService
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2

internal class SessionCaptureStrategy(options: SentryOptions,
   hub: IHub?,
   dateProvider: ICurrentDateProvider,
   executor: ScheduledExecutorService,
   replayCacheProvider: ((SentryId) -> ReplayCache)? = null
) : BaseCaptureStrategy(var1, var2, var3, var4, var5) {
   private final val dateProvider: ICurrentDateProvider
   private final val hub: IHub?
   private final val options: SentryOptions

   init {
      this.options = var1;
      this.hub = var2;
      this.dateProvider = var3;
   }

   private fun createCurrentSegment(taskName: String, onSegmentCreated: (ReplaySegment) -> Unit) {
      val var8: Long = this.dateProvider.getCurrentTimeMillis();
      val var13: Date = this.getSegmentTimestamp();
      if (var13 != null) {
         val var3: Int = this.getCurrentSegment();
         val var6: Long = var13.getTime();
         val var12: SentryId = this.getCurrentReplayId();
         val var5: Int = this.getRecorderConfig().getRecordingHeight();
         val var4: Int = this.getRecorderConfig().getRecordingWidth();
         val var11: ExecutorService = this.getReplayExecutor();
         val var14: SentryOptions = this.options;
         val var10: StringBuilder = new StringBuilder("SessionCaptureStrategy.");
         var10.append(var1);
         ExecutorsKt.submitSafely(
            var11, var14, var10.toString(), new SessionCaptureStrategy$$ExternalSyntheticLambda0(this, var8 - var6, var13, var12, var3, var5, var4, var2)
         );
      }
   }

   @JvmStatic
   fun `createCurrentSegment$lambda$4`(var0: SessionCaptureStrategy, var1: Long, var3: Date, var4: SentryId, var5: Int, var6: Int, var7: Int, var8: Function1) {
      var8.invoke(BaseCaptureStrategy.createSegmentInternal$default(var0, var1, var3, var4, var5, var6, var7, null, null, 0, 0, null, null, null, 8128, null));
   }

   @JvmStatic
   fun `onScreenshotRecorded$lambda$3`(var0: SessionCaptureStrategy, var1: Function2, var2: Long, var4: Int, var5: Int) {
      val var6: ReplayCache = var0.getCache();
      if (var6 != null) {
         var1.invoke(var6, var2);
      }

      val var7: Date = var0.getSegmentTimestamp();
      if (var7 == null) {
         var0.options.getLogger().log(SentryLevel.DEBUG, "Segment timestamp is not set, not recording frame");
      } else if (var0.isTerminating().get()) {
         var0.options.getLogger().log(SentryLevel.DEBUG, "Not capturing segment, because the app is terminating, will be captured on next launch");
      } else {
         var2 = var0.dateProvider.getCurrentTimeMillis();
         if (var2 - var7.getTime() >= var0.options.getSessionReplay().getSessionSegmentDuration()) {
            val var8: CaptureStrategy.ReplaySegment = BaseCaptureStrategy.createSegmentInternal$default(
               var0,
               var0.options.getSessionReplay().getSessionSegmentDuration(),
               var7,
               var0.getCurrentReplayId(),
               var0.getCurrentSegment(),
               var4,
               var5,
               null,
               null,
               0,
               0,
               null,
               null,
               null,
               8128,
               null
            );
            if (var8 is CaptureStrategy.ReplaySegment.Created) {
               val var9: CaptureStrategy.ReplaySegment.Created = var8 as CaptureStrategy.ReplaySegment.Created;
               CaptureStrategy.ReplaySegment.Created.capture$default(var8 as CaptureStrategy.ReplaySegment.Created, var0.hub, null, 2, null);
               var0.setCurrentSegment(var0.getCurrentSegment() + 1);
               var0.setSegmentTimestamp(var9.getReplay().getTimestamp());
            }
         }

         if (var2 - var0.getReplayStartTimestamp().get() >= var0.options.getSessionReplay().getSessionDuration()) {
            var0.options.getReplayController().stop();
            var0.options.getLogger().log(SentryLevel.INFO, "Session replay deadline exceeded (1h), stopping recording");
         }
      }
   }

   @JvmStatic
   fun `start$lambda$0`(var0: SessionCaptureStrategy, var1: IScope) {
      var1.setReplayId(var0.getCurrentReplayId());
      val var2: java.lang.String = var1.getScreen();
      var var3: java.lang.String = null;
      if (var2 != null) {
         var3 = StringsKt.substringAfterLast$default(var2, '.', null, 2, null);
      }

      var0.setScreenAtStart(var3);
   }

   @JvmStatic
   fun `stop$lambda$1`(var0: IScope) {
      var0.setReplayId(SentryId.EMPTY_ID);
   }

   public override fun captureReplay(isTerminating: Boolean, onSegmentSent: (Date) -> Unit) {
      this.options.getLogger().log(SentryLevel.DEBUG, "Replay is already running in 'session' mode, not capturing for event");
      this.isTerminating().set(var1);
   }

   public override fun convert(): CaptureStrategy {
      return this;
   }

   public override fun onConfigurationChanged(recorderConfig: ScreenshotRecorderConfig) {
      this.createCurrentSegment(
         "onConfigurationChanged",
         (
            new Function1<CaptureStrategy.ReplaySegment, Unit>(this) {
               final SessionCaptureStrategy this$0;

               {
                  super(1);
                  this.this$0 = var1;
               }

               public final void invoke(CaptureStrategy.ReplaySegment var1) {
                  if (var1 is CaptureStrategy.ReplaySegment.Created) {
                     val var3: CaptureStrategy.ReplaySegment.Created = var1 as CaptureStrategy.ReplaySegment.Created;
                     CaptureStrategy.ReplaySegment.Created.capture$default(
                        var1 as CaptureStrategy.ReplaySegment.Created, SessionCaptureStrategy.access$getHub$p(this.this$0), null, 2, null
                     );
                     this.this$0.setCurrentSegment(this.this$0.getCurrentSegment() + 1);
                     this.this$0.setSegmentTimestamp(var3.getReplay().getTimestamp());
                  }
               }
            }
         ) as (CaptureStrategy.ReplaySegment?) -> Unit
      );
      super.onConfigurationChanged(var1);
   }

   public override fun onScreenshotRecorded(bitmap: Bitmap?, store: (ReplayCache, Long) -> Unit) {
      ExecutorsKt.submitSafely(
         this.getReplayExecutor(),
         this.options,
         "SessionCaptureStrategy.add_frame",
         new SessionCaptureStrategy$$ExternalSyntheticLambda1(
            this, var2, this.dateProvider.getCurrentTimeMillis(), this.getRecorderConfig().getRecordingHeight(), this.getRecorderConfig().getRecordingWidth()
         )
      );
   }

   public override fun pause() {
      this.createCurrentSegment(
         "pause",
         (
            new Function1<CaptureStrategy.ReplaySegment, Unit>(this) {
               final SessionCaptureStrategy this$0;

               {
                  super(1);
                  this.this$0 = var1;
               }

               public final void invoke(CaptureStrategy.ReplaySegment var1) {
                  if (var1 is CaptureStrategy.ReplaySegment.Created) {
                     CaptureStrategy.ReplaySegment.Created.capture$default(
                        var1 as CaptureStrategy.ReplaySegment.Created, SessionCaptureStrategy.access$getHub$p(this.this$0), null, 2, null
                     );
                     this.this$0.setCurrentSegment(this.this$0.getCurrentSegment() + 1);
                  }
               }
            }
         ) as (CaptureStrategy.ReplaySegment?) -> Unit
      );
      super.pause();
   }

   public override fun start(recorderConfig: ScreenshotRecorderConfig, segmentId: Int, replayId: SentryId, replayType: ReplayType?) {
      super.start(var1, var2, var3, var4);
      if (this.hub != null) {
         this.hub.configureScope(new SessionCaptureStrategy$$ExternalSyntheticLambda2(this));
      }
   }

   public override fun stop() {
      val var1: ReplayCache = this.getCache();
      val var2: File;
      if (var1 != null) {
         var2 = var1.getReplayCacheDir$sentry_android_replay_release();
      } else {
         var2 = null;
      }

      this.createCurrentSegment(
         "stop",
         (
            new Function1<CaptureStrategy.ReplaySegment, Unit>(this, var2) {
               final File $replayCacheDir;
               final SessionCaptureStrategy this$0;

               {
                  super(1);
                  this.this$0 = var1;
                  this.$replayCacheDir = var2;
               }

               public final void invoke(CaptureStrategy.ReplaySegment var1) {
                  if (var1 is CaptureStrategy.ReplaySegment.Created) {
                     CaptureStrategy.ReplaySegment.Created.capture$default(
                        var1 as CaptureStrategy.ReplaySegment.Created, SessionCaptureStrategy.access$getHub$p(this.this$0), null, 2, null
                     );
                  }

                  FileUtils.deleteRecursively(this.$replayCacheDir);
               }
            }
         ) as (CaptureStrategy.ReplaySegment?) -> Unit
      );
      if (this.hub != null) {
         this.hub.configureScope(new SessionCaptureStrategy$$ExternalSyntheticLambda3());
      }

      super.stop();
   }

   internal companion object {
      private const val TAG: String
   }
}
