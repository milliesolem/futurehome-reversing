package io.sentry;

import io.sentry.vendor.gson.internal.bind.util.ISO8601Utils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {
   private DateUtils() {
   }

   public static long dateToNanos(Date var0) {
      return millisToNanos(var0.getTime());
   }

   public static double dateToSeconds(Date var0) {
      return millisToSeconds(var0.getTime());
   }

   public static Date getCurrentDateTime() {
      return Calendar.getInstance(ISO8601Utils.TIMEZONE_UTC).getTime();
   }

   public static Date getDateTime(long var0) {
      Calendar var2 = Calendar.getInstance(ISO8601Utils.TIMEZONE_UTC);
      var2.setTimeInMillis(var0);
      return var2.getTime();
   }

   public static Date getDateTime(String var0) throws IllegalArgumentException {
      try {
         ParsePosition var3 = new ParsePosition(0);
         return ISO8601Utils.parse(var0, var3);
      } catch (ParseException var2) {
         StringBuilder var1 = new StringBuilder("timestamp is not ISO format ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   public static Date getDateTimeWithMillisPrecision(String var0) throws IllegalArgumentException {
      try {
         BigDecimal var3 = new BigDecimal(var0);
         return getDateTime(var3.setScale(3, RoundingMode.DOWN).movePointRight(3).longValue());
      } catch (NumberFormatException var2) {
         StringBuilder var1 = new StringBuilder("timestamp is not millis format ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   public static String getTimestamp(Date var0) {
      return ISO8601Utils.format(var0, true);
   }

   public static long millisToNanos(long var0) {
      return var0 * 1000000L;
   }

   public static double millisToSeconds(double var0) {
      return var0 / 1000.0;
   }

   public static Date nanosToDate(long var0) {
      double var2 = var0;
      Double.valueOf(var2).getClass();
      return getDateTime(Double.valueOf(nanosToMillis(var2)).longValue());
   }

   public static double nanosToMillis(double var0) {
      return var0 / 1000000.0;
   }

   public static double nanosToSeconds(long var0) {
      double var2 = var0;
      Double.valueOf(var2).getClass();
      return var2 / 1.0E9;
   }

   public static long secondsToNanos(long var0) {
      return var0 * 1000000000L;
   }

   public static Date toUtilDate(SentryDate var0) {
      return var0 == null ? null : toUtilDateNotNull(var0);
   }

   public static Date toUtilDateNotNull(SentryDate var0) {
      return nanosToDate(var0.nanoTimestamp());
   }
}
