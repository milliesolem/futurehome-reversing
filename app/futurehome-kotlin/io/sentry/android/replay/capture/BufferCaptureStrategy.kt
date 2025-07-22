package io.sentry.android.replay.capture

import android.graphics.Bitmap
import android.view.MotionEvent
import io.sentry.DateUtils
import io.sentry.IHub
import io.sentry.IScope
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.SentryReplayEvent
import io.sentry.android.replay.ReplayCache
import io.sentry.android.replay.ScreenshotRecorderConfig
import io.sentry.android.replay.capture.CaptureStrategy.ReplaySegment
import io.sentry.android.replay.capture.CaptureStrategy.ReplaySegment.Created
import io.sentry.android.replay.util.ExecutorsKt
import io.sentry.android.replay.util.SamplingKt
import io.sentry.protocol.SentryId
import io.sentry.transport.ICurrentDateProvider
import io.sentry.util.FileUtils
import io.sentry.util.Random
import java.io.File
import java.util.ArrayList
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.ScheduledExecutorService
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref

internal class BufferCaptureStrategy(options: SentryOptions,
   hub: IHub?,
   dateProvider: ICurrentDateProvider,
   random: Random,
   executor: ScheduledExecutorService,
   replayCacheProvider: ((SentryId) -> ReplayCache)? = null
) : BaseCaptureStrategy(var1, var2, var3, var5, var6) {
   private final val bufferedSegments: MutableList<Created>
   private final val dateProvider: ICurrentDateProvider
   private final val hub: IHub?
   private final val options: SentryOptions
   private final val random: Random

   init {
      this.options = var1;
      this.hub = var2;
      this.dateProvider = var3;
      this.random = var4;
      this.bufferedSegments = new ArrayList<>();
   }

   private fun MutableList<Created>.capture() {
      var var2: CaptureStrategy.ReplaySegment.Created = CollectionsKt.removeFirstOrNull(var1);

      while (var2 != null) {
         CaptureStrategy.ReplaySegment.Created.capture$default(var2, this.hub, null, 2, null);
         var2 = CollectionsKt.removeFirstOrNull(var1);
         Thread.sleep(100L);
      }
   }

   @JvmStatic
   fun `captureReplay$lambda$1`(var0: BufferCaptureStrategy, var1: IScope) {
      var1.setReplayId(var0.getCurrentReplayId());
   }

   private fun createCurrentSegment(taskName: String, onSegmentCreated: (ReplaySegment) -> Unit) {
      var var6: Long;
      var var17: Date;
      label14: {
         val var8: Long = this.options.getSessionReplay().getErrorReplayDuration();
         var6 = this.dateProvider.getCurrentTimeMillis();
         val var10: ReplayCache = this.getCache();
         if (var10 != null) {
            val var16: java.util.List = var10.getFrames$sentry_android_replay_release();
            if (var16 != null && var16.isEmpty() xor true) {
               val var18: ReplayCache = this.getCache();
               var17 = DateUtils.getDateTime(CollectionsKt.first(var18.getFrames$sentry_android_replay_release()).getTimestamp());
               break label14;
            }
         }

         var17 = DateUtils.getDateTime(var6 - var8);
      }

      val var4: Int = this.getCurrentSegment();
      val var15: Long = var17.getTime();
      val var11: SentryId = this.getCurrentReplayId();
      val var3: Int = this.getRecorderConfig().getRecordingHeight();
      val var5: Int = this.getRecorderConfig().getRecordingWidth();
      val var12: ExecutorService = this.getReplayExecutor();
      val var14: SentryOptions = this.options;
      val var13: StringBuilder = new StringBuilder("BufferCaptureStrategy.");
      var13.append(var1);
      ExecutorsKt.submitSafely(
         var12, var14, var13.toString(), new BufferCaptureStrategy$$ExternalSyntheticLambda0(this, var6 - var15, var17, var11, var4, var3, var5, var2)
      );
   }

   @JvmStatic
   fun `createCurrentSegment$lambda$4`(var0: BufferCaptureStrategy, var1: Long, var3: Date, var4: SentryId, var5: Int, var6: Int, var7: Int, var8: Function1) {
      var8.invoke(BaseCaptureStrategy.createSegmentInternal$default(var0, var1, var3, var4, var5, var6, var7, null, null, 0, 0, null, null, null, 8128, null));
   }

   private fun deleteFile(file: File?) {
      if (var1 != null) {
         try {
            if (!var1.delete()) {
               this.options.getLogger().log(SentryLevel.ERROR, "Failed to delete replay segment: %s", var1.getAbsolutePath());
            }
         } catch (var3: java.lang.Throwable) {
            this.options.getLogger().log(SentryLevel.ERROR, var3, "Failed to delete replay segment: %s", var1.getAbsolutePath());
            return;
         }
      }
   }

   @JvmStatic
   fun `onScreenshotRecorded$lambda$2`(var0: BufferCaptureStrategy, var1: Function2, var2: Long) {
      val var4: ReplayCache = var0.getCache();
      if (var4 != null) {
         var1.invoke(var4, var2);
      }

      var2 = var0.dateProvider.getCurrentTimeMillis() - var0.options.getSessionReplay().getErrorReplayDuration();
      val var5: ReplayCache = var0.getCache();
      val var6: java.lang.String;
      if (var5 != null) {
         var6 = var5.rotate(var2);
      } else {
         var6 = null;
      }

      var0.setScreenAtStart(var6);
      var0.rotate(var0.bufferedSegments, var2);
   }

   private fun MutableList<Created>.rotate(bufferLimit: Long) {
      val var5: Ref.BooleanRef = new Ref.BooleanRef();
      CollectionsKt.removeAll(var1, (new Function1<CaptureStrategy.ReplaySegment.Created, java.lang.Boolean>(var2, this, var5) {
         final long $bufferLimit;
         final Ref.BooleanRef $removed;
         final BufferCaptureStrategy this$0;

         {
            super(1);
            this.$bufferLimit = var1;
            this.this$0 = var3;
            this.$removed = var4;
         }

         public final java.lang.Boolean invoke(CaptureStrategy.ReplaySegment.Created var1) {
            if (var1.getReplay().getTimestamp().getTime() < this.$bufferLimit) {
               this.this$0.setCurrentSegment(this.this$0.getCurrentSegment() - 1);
               BufferCaptureStrategy.access$deleteFile(this.this$0, var1.getReplay().getVideoFile());
               this.$removed.element = true;
               return true;
            } else {
               return false;
            }
         }
      }) as Function1);
      if (var5.element) {
         val var7: java.util.Iterator = var1.iterator();

         for (int var4 = 0; var7.hasNext(); var4++) {
            val var6: Any = var7.next();
            if (var4 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            (var6 as CaptureStrategy.ReplaySegment.Created).setSegmentId(var4);
         }
      }
   }

   @JvmStatic
   fun `stop$lambda$0`(var0: File) {
      FileUtils.deleteRecursively(var0);
   }

   public override fun captureReplay(isTerminating: Boolean, onSegmentSent: (Date) -> Unit) {
      if (!SamplingKt.sample(this.random, this.options.getSessionReplay().getOnErrorSampleRate())) {
         this.options.getLogger().log(SentryLevel.INFO, "Replay wasn't sampled by onErrorSampleRate, not capturing for event");
      } else {
         if (this.hub != null) {
            this.hub.configureScope(new BufferCaptureStrategy$$ExternalSyntheticLambda2(this));
         }

         if (var1) {
            this.isTerminating().set(true);
            this.options.getLogger().log(SentryLevel.DEBUG, "Not capturing replay for crashed event, will be captured on next launch");
         } else {
            this.createCurrentSegment(
               "capture_replay",
               (
                  new Function1<CaptureStrategy.ReplaySegment, Unit>(this, var2) {
                     final Function1<Date, Unit> $onSegmentSent;
                     final BufferCaptureStrategy this$0;

                     {
                        super(1);
                        this.this$0 = var1;
                        this.$onSegmentSent = var2;
                     }

                     public final void invoke(CaptureStrategy.ReplaySegment var1) {
                        BufferCaptureStrategy.access$capture(this.this$0, BufferCaptureStrategy.access$getBufferedSegments$p(this.this$0));
                        if (var1 is CaptureStrategy.ReplaySegment.Created) {
                           val var4: CaptureStrategy.ReplaySegment.Created = var1 as CaptureStrategy.ReplaySegment.Created;
                           CaptureStrategy.ReplaySegment.Created.capture$default(
                              var1 as CaptureStrategy.ReplaySegment.Created, BufferCaptureStrategy.access$getHub$p(this.this$0), null, 2, null
                           );
                           val var3: Function1 = this.$onSegmentSent;
                           val var5: Date = var4.getReplay().getTimestamp();
                           var3.invoke(var5);
                        }
                     }
                  }
               ) as (CaptureStrategy.ReplaySegment?) -> Unit
            );
         }
      }
   }

   public override fun convert(): CaptureStrategy {
      if (this.isTerminating().get()) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Not converting to session mode, because the process is about to terminate");
         return this;
      } else {
         val var1: SessionCaptureStrategy = new SessionCaptureStrategy(this.options, this.hub, this.dateProvider, this.getReplayExecutor(), null, 16, null);
         var1.start(this.getRecorderConfig(), this.getCurrentSegment(), this.getCurrentReplayId(), SentryReplayEvent.ReplayType.BUFFER);
         return var1;
      }
   }

   public override fun onConfigurationChanged(recorderConfig: ScreenshotRecorderConfig) {
      this.createCurrentSegment("configuration_changed", (new Function1<CaptureStrategy.ReplaySegment, Unit>(this) {
         final BufferCaptureStrategy this$0;

         {
            super(1);
            this.this$0 = var1;
         }

         public final void invoke(CaptureStrategy.ReplaySegment var1) {
            if (var1 is CaptureStrategy.ReplaySegment.Created) {
               BufferCaptureStrategy.access$getBufferedSegments$p(this.this$0).add(var1);
               this.this$0.setCurrentSegment(this.this$0.getCurrentSegment() + 1);
            }
         }
      }) as (CaptureStrategy.ReplaySegment?) -> Unit);
      super.onConfigurationChanged(var1);
   }

   public override fun onScreenshotRecorded(bitmap: Bitmap?, store: (ReplayCache, Long) -> Unit) {
      ExecutorsKt.submitSafely(
         this.getReplayExecutor(),
         this.options,
         "BufferCaptureStrategy.add_frame",
         new BufferCaptureStrategy$$ExternalSyntheticLambda1(this, var2, this.dateProvider.getCurrentTimeMillis())
      );
   }

   public override fun onTouchEvent(event: MotionEvent) {
      super.onTouchEvent(var1);
      CaptureStrategy.Companion.rotateEvents$sentry_android_replay_release$default(
         CaptureStrategy.Companion,
         this.getCurrentEvents(),
         this.dateProvider.getCurrentTimeMillis() - this.options.getSessionReplay().getErrorReplayDuration(),
         null,
         4,
         null
      );
   }

   public override fun pause() {
      this.createCurrentSegment("pause", (new Function1<CaptureStrategy.ReplaySegment, Unit>(this) {
         final BufferCaptureStrategy this$0;

         {
            super(1);
            this.this$0 = var1;
         }

         public final void invoke(CaptureStrategy.ReplaySegment var1) {
            if (var1 is CaptureStrategy.ReplaySegment.Created) {
               BufferCaptureStrategy.access$getBufferedSegments$p(this.this$0).add(var1);
               this.this$0.setCurrentSegment(this.this$0.getCurrentSegment() + 1);
            }
         }
      }) as (CaptureStrategy.ReplaySegment?) -> Unit);
      super.pause();
   }

   public override fun stop() {
      val var1: ReplayCache = this.getCache();
      val var2: File;
      if (var1 != null) {
         var2 = var1.getReplayCacheDir$sentry_android_replay_release();
      } else {
         var2 = null;
      }

      ExecutorsKt.submitSafely(this.getReplayExecutor(), this.options, "BufferCaptureStrategy.stop", new BufferCaptureStrategy$$ExternalSyntheticLambda3(var2));
      super.stop();
   }

   internal companion object {
      private const val ENVELOPE_PROCESSING_DELAY: Long
      private const val TAG: String
   }
}
