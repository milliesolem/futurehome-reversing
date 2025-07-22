package io.sentry.android.replay

import io.sentry.SentryReplayEvent.ReplayType
import io.sentry.rrweb.RRWebEvent
import java.util.Date

internal data class LastSegmentData(recorderConfig: ScreenshotRecorderConfig,
   cache: ReplayCache,
   timestamp: Date,
   id: Int,
   duration: Long,
   replayType: ReplayType,
   screenAtStart: String?,
   events: List<RRWebEvent>
) {
   public final val cache: ReplayCache
   public final val duration: Long
   public final val events: List<RRWebEvent>
   public final val id: Int
   public final val recorderConfig: ScreenshotRecorderConfig
   public final val replayType: ReplayType
   public final val screenAtStart: String?
   public final val timestamp: Date

   init {
      this.recorderConfig = var1;
      this.cache = var2;
      this.timestamp = var3;
      this.id = var4;
      this.duration = var5;
      this.replayType = var7;
      this.screenAtStart = var8;
      this.events = var9;
   }

   public operator fun component1(): ScreenshotRecorderConfig {
      return this.recorderConfig;
   }

   public operator fun component2(): ReplayCache {
      return this.cache;
   }

   public operator fun component3(): Date {
      return this.timestamp;
   }

   public operator fun component4(): Int {
      return this.id;
   }

   public operator fun component5(): Long {
      return this.duration;
   }

   public operator fun component6(): ReplayType {
      return this.replayType;
   }

   public operator fun component7(): String? {
      return this.screenAtStart;
   }

   public operator fun component8(): List<RRWebEvent> {
      return this.events;
   }

   public fun copy(
      recorderConfig: ScreenshotRecorderConfig = var0.recorderConfig,
      cache: ReplayCache = var0.cache,
      timestamp: Date = var0.timestamp,
      id: Int = var0.id,
      duration: Long = var0.duration,
      replayType: ReplayType = var0.replayType,
      screenAtStart: String? = var0.screenAtStart,
      events: List<RRWebEvent> = var0.events
   ): LastSegmentData {
      return new LastSegmentData(var1, var2, var3, var4, var5, var7, var8, var9);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is LastSegmentData) {
         return false;
      } else {
         var1 = var1;
         if (!(this.recorderConfig == var1.recorderConfig)) {
            return false;
         } else if (!(this.cache == var1.cache)) {
            return false;
         } else if (!(this.timestamp == var1.timestamp)) {
            return false;
         } else if (this.id != var1.id) {
            return false;
         } else if (this.duration != var1.duration) {
            return false;
         } else if (this.replayType != var1.replayType) {
            return false;
         } else if (!(this.screenAtStart == var1.screenAtStart)) {
            return false;
         } else {
            return this.events == var1.events;
         }
      }
   }

   public override fun hashCode(): Int {
      val var5: Int = this.recorderConfig.hashCode();
      val var2: Int = this.cache.hashCode();
      val var3: Int = this.timestamp.hashCode();
      val var6: Int = this.id;
      val var7: Int = UByte$$ExternalSyntheticBackport0.m(this.duration);
      val var4: Int = this.replayType.hashCode();
      val var1: Int;
      if (this.screenAtStart == null) {
         var1 = 0;
      } else {
         var1 = this.screenAtStart.hashCode();
      }

      return ((((((var5 * 31 + var2) * 31 + var3) * 31 + var6) * 31 + var7) * 31 + var4) * 31 + var1) * 31 + this.events.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("LastSegmentData(recorderConfig=");
      var1.append(this.recorderConfig);
      var1.append(", cache=");
      var1.append(this.cache);
      var1.append(", timestamp=");
      var1.append(this.timestamp);
      var1.append(", id=");
      var1.append(this.id);
      var1.append(", duration=");
      var1.append(this.duration);
      var1.append(", replayType=");
      var1.append(this.replayType);
      var1.append(", screenAtStart=");
      var1.append(this.screenAtStart);
      var1.append(", events=");
      var1.append(this.events);
      var1.append(')');
      return var1.toString();
   }
}
