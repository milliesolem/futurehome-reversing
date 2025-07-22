package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class MetricSummary implements JsonUnknown, JsonSerializable {
   private int count;
   private double max;
   private double min;
   private double sum;
   private Map<String, String> tags;
   private Map<String, Object> unknown;

   public MetricSummary() {
   }

   public MetricSummary(double var1, double var3, double var5, int var7, Map<String, String> var8) {
      this.tags = var8;
      this.min = var1;
      this.max = var3;
      this.count = var7;
      this.sum = var5;
      this.unknown = null;
   }

   public int getCount() {
      return this.count;
   }

   public double getMax() {
      return this.max;
   }

   public double getMin() {
      return this.min;
   }

   public double getSum() {
      return this.sum;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("min").value(this.min);
      var1.name("max").value(this.max);
      var1.name("sum").value(this.sum);
      var1.name("count").value((long)this.count);
      if (this.tags != null) {
         var1.name("tags");
         var1.value(var2, this.tags);
      }

      var1.endObject();
   }

   public void setCount(int var1) {
      this.count = var1;
   }

   public void setMax(double var1) {
      this.max = var1;
   }

   public void setMin(double var1) {
      this.min = var1;
   }

   public void setSum(double var1) {
      this.sum = var1;
   }

   public void setTags(Map<String, String> var1) {
      this.tags = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<MetricSummary> {
      public MetricSummary deserialize(ObjectReader var1, ILogger var2) throws Exception {
         MetricSummary var7 = new MetricSummary();
         var1.beginObject();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case 107876:
                  if (var8.equals("max")) {
                     var3 = 0;
                  }
                  break;
               case 108114:
                  if (var8.equals("min")) {
                     var3 = 1;
                  }
                  break;
               case 114251:
                  if (var8.equals("sum")) {
                     var3 = 2;
                  }
                  break;
               case 3552281:
                  if (var8.equals("tags")) {
                     var3 = 3;
                  }
                  break;
               case 94851343:
                  if (var8.equals("count")) {
                     var3 = 4;
                  }
            }

            switch (var3) {
               case 0:
                  var7.setMax(var1.nextDouble());
                  break;
               case 1:
                  var7.setMin(var1.nextDouble());
                  break;
               case 2:
                  var7.setSum(var1.nextDouble());
                  break;
               case 3:
                  var7.tags = CollectionUtils.newConcurrentHashMap((Map<String, String>)var1.nextObjectOrNull());
                  break;
               case 4:
                  var7.setCount(var1.nextInt());
                  break;
               default:
                  ConcurrentHashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var7.setUnknown(var5);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String COUNT = "count";
      public static final String MAX = "max";
      public static final String MIN = "min";
      public static final String SUM = "sum";
      public static final String TAGS = "tags";
   }
}
