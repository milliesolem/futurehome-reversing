package io.sentry.android.replay.video

import java.io.File

internal data class MuxerConfig(file: File, recordingWidth: Int, recordingHeight: Int, frameRate: Int, bitRate: Int, mimeType: String = "video/avc") {
   public final val bitRate: Int
   public final val file: File
   public final val frameRate: Int
   public final val mimeType: String

   public final var recordingHeight: Int
      internal set

   public final var recordingWidth: Int
      internal set

   init {
      this.file = var1;
      this.recordingWidth = var2;
      this.recordingHeight = var3;
      this.frameRate = var4;
      this.bitRate = var5;
      this.mimeType = var6;
   }

   public operator fun component1(): File {
      return this.file;
   }

   public operator fun component2(): Int {
      return this.recordingWidth;
   }

   public operator fun component3(): Int {
      return this.recordingHeight;
   }

   public operator fun component4(): Int {
      return this.frameRate;
   }

   public operator fun component5(): Int {
      return this.bitRate;
   }

   public operator fun component6(): String {
      return this.mimeType;
   }

   public fun copy(
      file: File = var0.file,
      recordingWidth: Int = var0.recordingWidth,
      recordingHeight: Int = var0.recordingHeight,
      frameRate: Int = var0.frameRate,
      bitRate: Int = var0.bitRate,
      mimeType: String = var0.mimeType
   ): MuxerConfig {
      return new MuxerConfig(var1, var2, var3, var4, var5, var6);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is MuxerConfig) {
         return false;
      } else {
         var1 = var1;
         if (!(this.file == var1.file)) {
            return false;
         } else if (this.recordingWidth != var1.recordingWidth) {
            return false;
         } else if (this.recordingHeight != var1.recordingHeight) {
            return false;
         } else if (this.frameRate != var1.frameRate) {
            return false;
         } else if (this.bitRate != var1.bitRate) {
            return false;
         } else {
            return this.mimeType == var1.mimeType;
         }
      }
   }

   public override fun hashCode(): Int {
      return ((((this.file.hashCode() * 31 + this.recordingWidth) * 31 + this.recordingHeight) * 31 + this.frameRate) * 31 + this.bitRate) * 31
         + this.mimeType.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("MuxerConfig(file=");
      var1.append(this.file);
      var1.append(", recordingWidth=");
      var1.append(this.recordingWidth);
      var1.append(", recordingHeight=");
      var1.append(this.recordingHeight);
      var1.append(", frameRate=");
      var1.append(this.frameRate);
      var1.append(", bitRate=");
      var1.append(this.bitRate);
      var1.append(", mimeType=");
      var1.append(this.mimeType);
      var1.append(')');
      return var1.toString();
   }
}
