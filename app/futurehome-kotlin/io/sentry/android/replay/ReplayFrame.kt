package io.sentry.android.replay

import java.io.File

internal data class ReplayFrame(screenshot: File, timestamp: Long, screen: String? = null) {
   public final val screen: String?
   public final val screenshot: File
   public final val timestamp: Long

   init {
      this.screenshot = var1;
      this.timestamp = var2;
      this.screen = var4;
   }

   public operator fun component1(): File {
      return this.screenshot;
   }

   public operator fun component2(): Long {
      return this.timestamp;
   }

   public operator fun component3(): String? {
      return this.screen;
   }

   public fun copy(screenshot: File = var0.screenshot, timestamp: Long = var0.timestamp, screen: String? = var0.screen): ReplayFrame {
      return new ReplayFrame(var1, var2, var4);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is ReplayFrame) {
         return false;
      } else {
         var1 = var1;
         if (!(this.screenshot == var1.screenshot)) {
            return false;
         } else if (this.timestamp != var1.timestamp) {
            return false;
         } else {
            return this.screen == var1.screen;
         }
      }
   }

   public override fun hashCode(): Int {
      val var2: Int = this.screenshot.hashCode();
      val var3: Int = UByte$$ExternalSyntheticBackport0.m(this.timestamp);
      val var1: Int;
      if (this.screen == null) {
         var1 = 0;
      } else {
         var1 = this.screen.hashCode();
      }

      return (var2 * 31 + var3) * 31 + var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ReplayFrame(screenshot=");
      var1.append(this.screenshot);
      var1.append(", timestamp=");
      var1.append(this.timestamp);
      var1.append(", screen=");
      var1.append(this.screen);
      var1.append(')');
      return var1.toString();
   }
}
