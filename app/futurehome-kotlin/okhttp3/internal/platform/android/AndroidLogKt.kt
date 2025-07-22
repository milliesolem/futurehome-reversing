package okhttp3.internal.platform.android

import java.util.logging.Level
import java.util.logging.LogRecord

private final val androidLevel: Int
   private final get() {
      val var1: Byte;
      if (var0.getLevel().intValue() > Level.INFO.intValue()) {
         var1 = 5;
      } else if (var0.getLevel().intValue() == Level.INFO.intValue()) {
         var1 = 4;
      } else {
         var1 = 3;
      }

      return var1;
   }


@JvmSynthetic
fun `access$getAndroidLevel$p`(var0: LogRecord): Int {
   return getAndroidLevel(var0);
}
