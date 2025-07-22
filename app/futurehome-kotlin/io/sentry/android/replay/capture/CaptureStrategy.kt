package io.sentry.android.replay.capture

import android.graphics.Bitmap
import android.view.MotionEvent
import io.sentry.Breadcrumb
import io.sentry.DateUtils
import io.sentry.Hint
import io.sentry.IHub
import io.sentry.IScope
import io.sentry.ReplayRecording
import io.sentry.SentryOptions
import io.sentry.SentryReplayEvent
import io.sentry.SentryReplayEvent.ReplayType
import io.sentry.android.replay.GeneratedVideo
import io.sentry.android.replay.ReplayCache
import io.sentry.android.replay.ScreenshotRecorderConfig
import io.sentry.protocol.SentryId
import io.sentry.rrweb.RRWebBreadcrumbEvent
import io.sentry.rrweb.RRWebEvent
import io.sentry.rrweb.RRWebMetaEvent
import io.sentry.rrweb.RRWebOptionsEvent
import io.sentry.rrweb.RRWebVideoEvent
import j..util.List._EL
import java.io.File
import java.util.ArrayList
import java.util.Comparator
import java.util.Date
import java.util.Deque
import java.util.LinkedList
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Ref

internal interface CaptureStrategy {
   public var currentReplayId: SentryId
      internal final set

   public var currentSegment: Int
      internal final set

   public val replayCacheDir: File?

   public var replayType: ReplayType
      internal final set

   public var segmentTimestamp: Date?
      internal final set

   public abstract fun captureReplay(isTerminating: Boolean, onSegmentSent: (Date) -> Unit) {
   }

   public abstract fun convert(): CaptureStrategy {
   }

   public abstract fun onConfigurationChanged(recorderConfig: ScreenshotRecorderConfig) {
   }

   public open fun onScreenChanged(screen: String?) {
   }

   public abstract fun onScreenshotRecorded(bitmap: Bitmap? = ..., store: (ReplayCache, Long) -> Unit) {
   }

   public abstract fun onTouchEvent(event: MotionEvent) {
   }

   public abstract fun pause() {
   }

   public abstract fun resume() {
   }

   public abstract fun start(recorderConfig: ScreenshotRecorderConfig, segmentId: Int = ..., replayId: SentryId = ..., replayType: ReplayType? = ...) {
   }

   public abstract fun stop() {
   }

   public companion object {
      private const val BREADCRUMB_START_OFFSET: Long = 100L

      private fun buildReplay(
         options: SentryOptions,
         video: File,
         currentReplayId: SentryId,
         segmentTimestamp: Date,
         segmentId: Int,
         height: Int,
         width: Int,
         frameCount: Int,
         frameRate: Int,
         videoDuration: Long,
         replayType: ReplayType,
         screenAtStart: String?,
         breadcrumbs: List<Breadcrumb>,
         events: Deque<RRWebEvent>
      ): io.sentry.android.replay.capture.CaptureStrategy.ReplaySegment {
         val var18: Date = DateUtils.getDateTime(var4.getTime() + var10);
         val var17: SentryReplayEvent = new SentryReplayEvent();
         var17.setEventId(var3);
         var17.setReplayId(var3);
         var17.setSegmentId(var5);
         var17.setTimestamp(var18);
         var17.setReplayStartTimestamp(var4);
         var17.setReplayType(var12);
         var17.setVideoFile(var2);
         val var30: java.util.List = new ArrayList();
         val var19: java.util.Collection = var30;
         val var27: RRWebMetaEvent = new RRWebMetaEvent();
         var27.setTimestamp(var4.getTime());
         var27.setHeight(var6);
         var27.setWidth(var7);
         var19.add(var27);
         val var28: RRWebVideoEvent = new RRWebVideoEvent();
         var28.setTimestamp(var4.getTime());
         var28.setSegmentId(var5);
         var28.setDurationMs(var10);
         var28.setFrameCount(var8);
         var28.setSize(var2.length());
         var28.setFrameRate(var9);
         var28.setHeight(var6);
         var28.setWidth(var7);
         var28.setLeft(0);
         var28.setTop(0);
         var19.add(var28);
         val var20: LinkedList = new LinkedList();

         for (Breadcrumb var23 : var14) {
            if (var23.getTimestamp().getTime() + 100L >= var4.getTime() && var23.getTimestamp().getTime() < var18.getTime()) {
               val var21: RRWebEvent = var1.getReplayController().getBreadcrumbConverter().convert(var23);
               if (var21 != null) {
                  var19.add(var21);
                  val var16: Boolean = var21 is RRWebBreadcrumbEvent;
                  var var29: java.lang.String = null;
                  val var24: RRWebBreadcrumbEvent;
                  if (var16) {
                     var24 = var21 as RRWebBreadcrumbEvent;
                  } else {
                     var24 = null;
                  }

                  if (var24 != null) {
                     var29 = var24.getCategory();
                  }

                  if (var29 == "navigation") {
                     val var25: java.util.Map = (var21 as RRWebBreadcrumbEvent).getData();
                     val var26: Any = var25.get("to");
                     var20.add(var26 as java.lang.String);
                  }
               }
            }
         }

         if (var13 != null && !(CollectionsKt.firstOrNull(var20) == var13)) {
            _EL.addFirst(var20, var13);
         }

         this.rotateEvents$sentry_android_replay_release(var15, var18.getTime(), (new Function1<RRWebEvent, Unit>(var4, var30) {
            final java.util.List<RRWebEvent> $recordingPayload;
            final Date $segmentTimestamp;

            {
               super(1);
               this.$segmentTimestamp = var1;
               this.$recordingPayload = var2;
            }

            public final void invoke(RRWebEvent var1) {
               if (var1.getTimestamp() >= this.$segmentTimestamp.getTime()) {
                  this.$recordingPayload.add(var1);
               }
            }
         }) as (RRWebEvent?) -> Unit);
         if (var5 == 0) {
            var19.add(new RRWebOptionsEvent(var1));
         }

         val var22: ReplayRecording = new ReplayRecording();
         var22.setSegmentId(var5);
         var22.setPayload(CollectionsKt.sortedWith(var30, new Comparator() {
            @Override
            public final int compare(T var1, T var2) {
               return ComparisonsKt.compareValues((var1 as RRWebEvent).getTimestamp(), (var2 as RRWebEvent).getTimestamp());
            }
         }));
         var17.setUrls(var20);
         return new CaptureStrategy.ReplaySegment.Created(var17, var22);
      }

      @JvmStatic
      fun `createSegment$lambda$0`(var0: Ref.ObjectRef, var1: IScope) {
         var0.element = (T)(new ArrayList<Breadcrumb>(var1.getBreadcrumbs()));
      }

      public fun createSegment(
         hub: IHub?,
         options: SentryOptions,
         duration: Long,
         currentSegmentTimestamp: Date,
         replayId: SentryId,
         segmentId: Int,
         height: Int,
         width: Int,
         replayType: ReplayType,
         cache: ReplayCache?,
         frameRate: Int,
         bitRate: Int,
         screenAtStart: String?,
         breadcrumbs: List<Breadcrumb>?,
         events: Deque<RRWebEvent>
      ): io.sentry.android.replay.capture.CaptureStrategy.ReplaySegment {
         if (var11 != null) {
            val var17: GeneratedVideo = ReplayCache.createVideoOf$default(var11, var3, var5.getTime(), var7, var8, var9, var12, var13, null, 128, null);
            if (var17 != null) {
               val var19: File = var17.component1();
               var13 = var17.component2();
               var3 = var17.component3();
               if (var15 == null) {
                  val var21: Ref.ObjectRef = new Ref.ObjectRef();
                  var21.element = (T)CollectionsKt.emptyList();
                  if (var1 != null) {
                     var1.configureScope(new CaptureStrategy$Companion$$ExternalSyntheticLambda0(var21));
                  }

                  var15 = var21.element as java.util.List;
               }

               return this.buildReplay(var2, var19, var6, var5, var7, var8, var9, var13, var12, var3, var10, var14, var15, var16);
            }
         }

         return CaptureStrategy.ReplaySegment.Failed.INSTANCE;
      }

      internal fun rotateEvents(events: Deque<RRWebEvent>, until: Long, callback: ((RRWebEvent) -> Unit)? = ...) {
         val var6: java.util.Iterator = var1.iterator();

         while (var6.hasNext()) {
            val var5: RRWebEvent = var6.next() as RRWebEvent;
            if (var5.getTimestamp() < var2) {
               if (var4 != null) {
                  var4.invoke(var5);
               }

               var6.remove();
            }
         }
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun onScreenChanged(var0: CaptureStrategy, var1: java.lang.String) {
      }
   }

   public sealed class ReplaySegment protected constructor() {
      public data class Created(replay: SentryReplayEvent, recording: ReplayRecording) : CaptureStrategy.ReplaySegment() {
         public final val recording: ReplayRecording
         public final val replay: SentryReplayEvent

         init {
            this.replay = var1;
            this.recording = var2;
         }

         public fun capture(hub: IHub?, hint: Hint = new Hint()) {
            if (var1 != null) {
               val var3: SentryReplayEvent = this.replay;
               var2.setReplayRecording(this.recording);
               var1.captureReplay(var3, var2);
            }
         }

         public operator fun component1(): SentryReplayEvent {
            return this.replay;
         }

         public operator fun component2(): ReplayRecording {
            return this.recording;
         }

         public fun copy(replay: SentryReplayEvent = var0.replay, recording: ReplayRecording = var0.recording): io.sentry.android.replay.capture.CaptureStrategy.ReplaySegment.Created {
            return new CaptureStrategy.ReplaySegment.Created(var1, var2);
         }

         public override operator fun equals(other: Any?): Boolean {
            if (this === var1) {
               return true;
            } else if (var1 !is CaptureStrategy.ReplaySegment.Created) {
               return false;
            } else {
               var1 = var1;
               if (!(this.replay == var1.replay)) {
                  return false;
               } else {
                  return this.recording == var1.recording;
               }
            }
         }

         public override fun hashCode(): Int {
            return this.replay.hashCode() * 31 + this.recording.hashCode();
         }

         public fun setSegmentId(segmentId: Int) {
            this.replay.setSegmentId(var1);
            val var2: java.util.List = this.recording.getPayload();
            if (var2 != null) {
               for (RRWebEvent var4 : var2) {
                  if (var4 is RRWebVideoEvent) {
                     (var4 as RRWebVideoEvent).setSegmentId(var1);
                  }
               }
            }
         }

         public override fun toString(): String {
            val var1: StringBuilder = new StringBuilder("Created(replay=");
            var1.append(this.replay);
            var1.append(", recording=");
            var1.append(this.recording);
            var1.append(')');
            return var1.toString();
         }
      }

      public object Failed : CaptureStrategy.ReplaySegment()
   }
}
