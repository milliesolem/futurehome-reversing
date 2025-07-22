package io.sentry.android.replay

import java.io.File

public data class GeneratedVideo(video: File, frameCount: Int, duration: Long) {
   public final val duration: Long
   public final val frameCount: Int
   public final val video: File

   init {
      this.video = var1;
      this.frameCount = var2;
      this.duration = var3;
   }

   public operator fun component1(): File {
      return this.video;
   }

   public operator fun component2(): Int {
      return this.frameCount;
   }

   public operator fun component3(): Long {
      return this.duration;
   }

   public fun copy(video: File = var0.video, frameCount: Int = var0.frameCount, duration: Long = var0.duration): GeneratedVideo {
      return new GeneratedVideo(var1, var2, var3);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is GeneratedVideo) {
         return false;
      } else {
         var1 = var1;
         if (!(this.video == var1.video)) {
            return false;
         } else if (this.frameCount != var1.frameCount) {
            return false;
         } else {
            return this.duration == var1.duration;
         }
      }
   }

   public override fun hashCode(): Int {
      return (this.video.hashCode() * 31 + this.frameCount) * 31 + UByte$$ExternalSyntheticBackport0.m(this.duration);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("GeneratedVideo(video=");
      var1.append(this.video);
      var1.append(", frameCount=");
      var1.append(this.frameCount);
      var1.append(", duration=");
      var1.append(this.duration);
      var1.append(')');
      return var1.toString();
   }
}
