package io.sentry;

import java.util.Date;

// $VF: synthetic class
public final class ObjectReader$_CC {
   public static Date dateOrNull(String var0, ILogger var1) {
      if (var0 == null) {
         return null;
      } else {
         try {
            return DateUtils.getDateTime(var0);
         } catch (Exception var4) {
            try {
               return DateUtils.getDateTimeWithMillisPrecision(var0);
            } catch (Exception var3) {
               var1.log(SentryLevel.ERROR, "Error when deserializing millis timestamp format.", var3);
               return null;
            }
         }
      }
   }
}
