package io.sentry.metrics;

import io.sentry.MeasurementUnit;
import io.sentry.util.Random;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public final class MetricsHelper {
   public static final long FLUSHER_SLEEP_TIME_MS = 5000L;
   private static long FLUSH_SHIFT_MS = (long)(new Random().nextFloat() * 10000.0F);
   public static final int MAX_TOTAL_WEIGHT = 100000;
   private static final Pattern NAME_PATTERN = Pattern.compile("[^\\w\\-.]+");
   private static final int ROLLUP_IN_SECONDS = 10;
   private static final char TAGS_ESCAPE_CHAR = '\\';
   private static final char TAGS_KEY_VALUE_DELIMITER = '=';
   private static final char TAGS_PAIR_DELIMITER = ',';
   private static final Pattern TAG_KEY_PATTERN = Pattern.compile("[^\\w\\-./]+");
   private static final Pattern UNIT_PATTERN = Pattern.compile("\\W+");

   public static double convertNanosTo(MeasurementUnit.Duration var0, long var1) {
      switch (<unrepresentable>.$SwitchMap$io$sentry$MeasurementUnit$Duration[var0.ordinal()]) {
         case 1:
            return var1;
         case 2:
            return var1 / 1000.0;
         case 3:
            return var1 / 1000000.0;
         case 4:
            return var1 / 1.0E9;
         case 5:
            return var1 / 6.0E10;
         case 6:
            return var1 / 3.6E12;
         case 7:
            return var1 / 8.64E13;
         case 8:
            return var1 / 8.64E13 / 7.0;
         default:
            StringBuilder var3 = new StringBuilder("Unknown Duration unit: ");
            var3.append(var0.name());
            throw new IllegalArgumentException(var3.toString());
      }
   }

   public static void encodeMetrics(long var0, Collection<Metric> var2, StringBuilder var3) {
      for (Metric var6 : var2) {
         var3.append(sanitizeName(var6.getKey()));
         var3.append("@");
         var3.append(sanitizeUnit(getUnitName(var6.getUnit())));

         for (Object var7 : var6.serialize()) {
            var3.append(":");
            var3.append(var7);
         }

         var3.append("|");
         var3.append(var6.getType().statsdCode);
         Map var9 = var6.getTags();
         if (var9 != null) {
            var3.append("|#");
            Iterator var12 = var9.entrySet().iterator();
            boolean var4 = true;

            while (var12.hasNext()) {
               Entry var11 = (Entry)var12.next();
               String var10 = sanitizeTagKey((String)var11.getKey());
               if (var4) {
                  var4 = false;
               } else {
                  var3.append(",");
               }

               var3.append(var10);
               var3.append(":");
               var3.append(sanitizeTagValue((String)var11.getValue()));
            }
         }

         var3.append("|T");
         var3.append(var0);
         var3.append("\n");
      }
   }

   private static String escapeString(String var0) {
      StringBuilder var3 = new StringBuilder(var0.length());

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var1 = var0.charAt(var2);
         if (var1 == ',' || var1 == '=') {
            var3.append('\\');
         }

         var3.append(var1);
      }

      return var3.toString();
   }

   public static long getCutoffTimestampMs(long var0) {
      return var0 - 10000L - FLUSH_SHIFT_MS;
   }

   public static String getExportKey(MetricType var0, String var1, MeasurementUnit var2) {
      String var3 = getUnitName(var2);
      return String.format("%s:%s@%s", var0.statsdCode, var1, var3);
   }

   public static String getMetricBucketKey(MetricType var0, String var1, MeasurementUnit var2, Map<String, String> var3) {
      String var4 = var0.statsdCode;
      String var5 = getTagsKey(var3);
      return String.format("%s_%s_%s_%s", var4, var1, getUnitName(var2), var5);
   }

   private static String getTagsKey(Map<String, String> var0) {
      if (var0 != null && !var0.isEmpty()) {
         StringBuilder var1 = new StringBuilder();

         for (Entry var3 : var0.entrySet()) {
            String var4 = escapeString((String)var3.getKey());
            String var5 = escapeString((String)var3.getValue());
            if (var1.length() > 0) {
               var1.append(',');
            }

            var1.append(var4);
            var1.append('=');
            var1.append(var5);
         }

         return var1.toString();
      } else {
         return "";
      }
   }

   public static long getTimeBucketKey(long var0) {
      long var2 = var0 / 1000L / 10L * 10L;
      return var0 >= 0L ? var2 : var2 - 1L;
   }

   private static String getUnitName(MeasurementUnit var0) {
      String var1;
      if (var0 != null) {
         var1 = var0.apiName();
      } else {
         var1 = "none";
      }

      return var1;
   }

   public static Map<String, String> mergeTags(Map<String, String> var0, Map<String, String> var1) {
      if (var0 == null) {
         return Collections.unmodifiableMap(var1);
      } else {
         HashMap var4 = new HashMap(var0);

         for (Entry var2 : var1.entrySet()) {
            String var3 = (String)var2.getKey();
            if (!var4.containsKey(var3)) {
               var4.put(var3, (String)var2.getValue());
            }
         }

         return var4;
      }
   }

   public static String sanitizeName(String var0) {
      return NAME_PATTERN.matcher(var0).replaceAll("_");
   }

   public static String sanitizeTagKey(String var0) {
      return TAG_KEY_PATTERN.matcher(var0).replaceAll("");
   }

   public static String sanitizeTagValue(String var0) {
      StringBuilder var3 = new StringBuilder(var0.length());

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var1 = var0.charAt(var2);
         if (var1 == '\n') {
            var3.append("\\n");
         } else if (var1 == '\r') {
            var3.append("\\r");
         } else if (var1 == '\t') {
            var3.append("\\t");
         } else if (var1 == '\\') {
            var3.append("\\\\");
         } else if (var1 == '|') {
            var3.append("\\u{7c}");
         } else if (var1 == ',') {
            var3.append("\\u{2c}");
         } else {
            var3.append(var1);
         }
      }

      return var3.toString();
   }

   public static String sanitizeUnit(String var0) {
      return UNIT_PATTERN.matcher(var0).replaceAll("");
   }

   public static void setFlushShiftMs(long var0) {
      FLUSH_SHIFT_MS = var0;
   }
}
