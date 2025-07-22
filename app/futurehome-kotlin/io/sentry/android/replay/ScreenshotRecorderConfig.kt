package io.sentry.android.replay

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build.VERSION
import android.view.WindowManager
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4
import io.sentry.SentryReplayOptions
import kotlin.math.MathKt

public data class ScreenshotRecorderConfig(recordingWidth: Int, recordingHeight: Int, scaleFactorX: Float, scaleFactorY: Float, frameRate: Int, bitRate: Int) {
   public final val bitRate: Int
   public final val frameRate: Int
   public final val recordingHeight: Int
   public final val recordingWidth: Int
   public final val scaleFactorX: Float
   public final val scaleFactorY: Float

   internal constructor(scaleFactorX: Float, scaleFactorY: Float) : this(0, 0, var1, var2, 0, 0)
   init {
      this.recordingWidth = var1;
      this.recordingHeight = var2;
      this.scaleFactorX = var3;
      this.scaleFactorY = var4;
      this.frameRate = var5;
      this.bitRate = var6;
   }

   public operator fun component1(): Int {
      return this.recordingWidth;
   }

   public operator fun component2(): Int {
      return this.recordingHeight;
   }

   public operator fun component3(): Float {
      return this.scaleFactorX;
   }

   public operator fun component4(): Float {
      return this.scaleFactorY;
   }

   public operator fun component5(): Int {
      return this.frameRate;
   }

   public operator fun component6(): Int {
      return this.bitRate;
   }

   public fun copy(
      recordingWidth: Int = var0.recordingWidth,
      recordingHeight: Int = var0.recordingHeight,
      scaleFactorX: Float = var0.scaleFactorX,
      scaleFactorY: Float = var0.scaleFactorY,
      frameRate: Int = var0.frameRate,
      bitRate: Int = var0.bitRate
   ): ScreenshotRecorderConfig {
      return new ScreenshotRecorderConfig(var1, var2, var3, var4, var5, var6);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is ScreenshotRecorderConfig) {
         return false;
      } else {
         var1 = var1;
         if (this.recordingWidth != var1.recordingWidth) {
            return false;
         } else if (this.recordingHeight != var1.recordingHeight) {
            return false;
         } else if (java.lang.Float.compare(this.scaleFactorX, var1.scaleFactorX) != 0) {
            return false;
         } else if (java.lang.Float.compare(this.scaleFactorY, var1.scaleFactorY) != 0) {
            return false;
         } else if (this.frameRate != var1.frameRate) {
            return false;
         } else {
            return this.bitRate == var1.bitRate;
         }
      }
   }

   public override fun hashCode(): Int {
      return (
               (
                        ((this.recordingWidth * 31 + this.recordingHeight) * 31 + java.lang.Float.floatToIntBits(this.scaleFactorX)) * 31
                           + java.lang.Float.floatToIntBits(this.scaleFactorY)
                     )
                     * 31
                  + this.frameRate
            )
            * 31
         + this.bitRate;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ScreenshotRecorderConfig(recordingWidth=");
      var1.append(this.recordingWidth);
      var1.append(", recordingHeight=");
      var1.append(this.recordingHeight);
      var1.append(", scaleFactorX=");
      var1.append(this.scaleFactorX);
      var1.append(", scaleFactorY=");
      var1.append(this.scaleFactorY);
      var1.append(", frameRate=");
      var1.append(this.frameRate);
      var1.append(", bitRate=");
      var1.append(this.bitRate);
      var1.append(')');
      return var1.toString();
   }

   public companion object {
      private fun Int.adjustToBlockSize(): Int {
         val var2: Int = var1 % 16;
         if (var1 % 16 <= 8) {
            var1 = var1 - var2;
         } else {
            var1 = var1 + (16 - var2);
         }

         return var1;
      }

      public fun from(context: Context, sessionReplay: SentryReplayOptions): ScreenshotRecorderConfig {
         var var5: Any = var1.getSystemService("window");
         var5 = var5 as WindowManager;
         if (VERSION.SDK_INT >= 30) {
            var5 = ExternalSyntheticApiModelOutline4.m(ExternalSyntheticApiModelOutline4.m((WindowManager)var5));
         } else {
            val var6: Point = new Point();
            var5.getDefaultDisplay().getRealSize(var6);
            var5 = new Rect(0, 0, var6.x, var6.y);
         }

         val var7: Pair = TuplesKt.to(
            this.adjustToBlockSize(MathKt.roundToInt((float)var5.height() / var1.getResources().getDisplayMetrics().density * var2.getQuality().sizeScale)),
            this.adjustToBlockSize(MathKt.roundToInt((float)var5.width() / var1.getResources().getDisplayMetrics().density * var2.getQuality().sizeScale))
         );
         val var3: Int = (var7.component1() as java.lang.Number).intValue();
         val var4: Int = (var7.component2() as java.lang.Number).intValue();
         return new ScreenshotRecorderConfig(
            var4, var3, (float)var4 / var5.width(), (float)var3 / var5.height(), var2.getFrameRate(), var2.getQuality().bitRate
         );
      }
   }
}
