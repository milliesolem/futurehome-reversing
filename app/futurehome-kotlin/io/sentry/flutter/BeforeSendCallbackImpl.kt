package io.sentry.flutter

import io.sentry.Hint
import io.sentry.SentryEvent
import io.sentry.SentryOptions
import io.sentry.protocol.SdkVersion

private class BeforeSendCallbackImpl : SentryOptions.BeforeSendCallback {
   private fun setEventEnvironmentTag(event: SentryEvent, origin: String = "android", environment: String) {
      var1.setTag("event.origin", var2);
      var1.setTag("event.environment", var3);
   }

   public override fun execute(event: SentryEvent, hint: Hint): SentryEvent {
      val var4: SdkVersion = var1.getSdk();
      if (var4 != null) {
         val var5: java.lang.String = var4.getName();
         val var3: Int = var5.hashCode();
         if (var3 != -1079289216) {
            if (var3 != 214992565) {
               if (var3 == 1378491996 && var5.equals("sentry.dart.flutter")) {
                  this.setEventEnvironmentTag(var1, "flutter", "dart");
               }
            } else if (var5.equals("sentry.native.android.flutter")) {
               setEventEnvironmentTag$default(this, var1, null, "native", 2, null);
            }
         } else if (var5.equals("sentry.java.android.flutter")) {
            setEventEnvironmentTag$default(this, var1, null, "java", 2, null);
         }
      }

      return var1;
   }
}
