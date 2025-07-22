package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class CheckIn implements JsonUnknown, JsonSerializable {
   private final SentryId checkInId;
   private final MonitorContexts contexts = new MonitorContexts();
   private Double duration;
   private String environment;
   private MonitorConfig monitorConfig;
   private String monitorSlug;
   private String release;
   private String status;
   private Map<String, Object> unknown;

   public CheckIn(SentryId var1, String var2, CheckInStatus var3) {
      this(var1, var2, var3.apiName());
   }

   public CheckIn(SentryId var1, String var2, String var3) {
      SentryId var4 = var1;
      if (var1 == null) {
         var4 = new SentryId();
      }

      this.checkInId = var4;
      this.monitorSlug = var2;
      this.status = var3;
   }

   public CheckIn(String var1, CheckInStatus var2) {
      this(null, var1, var2.apiName());
   }

   public SentryId getCheckInId() {
      return this.checkInId;
   }

   public MonitorContexts getContexts() {
      return this.contexts;
   }

   public Double getDuration() {
      return this.duration;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public MonitorConfig getMonitorConfig() {
      return this.monitorConfig;
   }

   public String getMonitorSlug() {
      return this.monitorSlug;
   }

   public String getRelease() {
      return this.release;
   }

   public String getStatus() {
      return this.status;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("check_in_id");
      this.checkInId.serialize(var1, var2);
      var1.name("monitor_slug").value(this.monitorSlug);
      var1.name("status").value(this.status);
      if (this.duration != null) {
         var1.name("duration").value(this.duration);
      }

      if (this.release != null) {
         var1.name("release").value(this.release);
      }

      if (this.environment != null) {
         var1.name("environment").value(this.environment);
      }

      if (this.monitorConfig != null) {
         var1.name("monitor_config");
         this.monitorConfig.serialize(var1, var2);
      }

      if (this.contexts != null) {
         var1.name("contexts");
         this.contexts.serialize(var1, var2);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.unknown.get(var6);
            var1.name(var6).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setDuration(Double var1) {
      this.duration = var1;
   }

   public void setEnvironment(String var1) {
      this.environment = var1;
   }

   public void setMonitorConfig(MonitorConfig var1) {
      this.monitorConfig = var1;
   }

   public void setMonitorSlug(String var1) {
      this.monitorSlug = var1;
   }

   public void setRelease(String var1) {
      this.release = var1;
   }

   public void setStatus(CheckInStatus var1) {
      this.status = var1.apiName();
   }

   public void setStatus(String var1) {
      this.status = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<CheckIn> {
      public CheckIn deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         SentryId var13 = null;
         String var12 = null;
         String var11 = null;
         Object var5 = var11;
         Object var6 = var11;
         String var7 = var11;
         String var8 = var11;
         Object var9 = var11;
         Object var10 = var11;

         while (var1.peek() == JsonToken.NAME) {
            String var15 = var1.nextName();
            var15.hashCode();
            int var4 = var15.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -2075530809:
                  if (var15.equals("monitor_config")) {
                     var3 = 0;
                  }
                  break;
               case -1992012396:
                  if (var15.equals("duration")) {
                     var3 = 1;
                  }
                  break;
               case -892481550:
                  if (var15.equals("status")) {
                     var3 = 2;
                  }
                  break;
               case -567312220:
                  if (var15.equals("contexts")) {
                     var3 = 3;
                  }
                  break;
               case -85904877:
                  if (var15.equals("environment")) {
                     var3 = 4;
                  }
                  break;
               case 1090594823:
                  if (var15.equals("release")) {
                     var3 = 5;
                  }
                  break;
               case 1101887614:
                  if (var15.equals("check_in_id")) {
                     var3 = 6;
                  }
                  break;
               case 1732390512:
                  if (var15.equals("monitor_slug")) {
                     var3 = 7;
                  }
            }

            switch (var3) {
               case 0:
                  var9 = new MonitorConfig.Deserializer().deserialize(var1, var2);
                  break;
               case 1:
                  var6 = var1.nextDoubleOrNull();
                  break;
               case 2:
                  var11 = var1.nextStringOrNull();
                  break;
               case 3:
                  var10 = new MonitorContexts.Deserializer().deserialize(var1, var2);
                  break;
               case 4:
                  var8 = var1.nextStringOrNull();
                  break;
               case 5:
                  var7 = var1.nextStringOrNull();
                  break;
               case 6:
                  var13 = new SentryId.Deserializer().deserialize(var1, var2);
                  break;
               case 7:
                  var12 = var1.nextStringOrNull();
                  break;
               default:
                  Object var14 = var5;
                  if (var5 == null) {
                     var14 = new HashMap();
                  }

                  var1.nextUnknown(var2, (Map<String, Object>)var14, var15);
                  var5 = var14;
            }
         }

         var1.endObject();
         if (var13 == null) {
            IllegalStateException var16 = new IllegalStateException("Missing required field \"check_in_id\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"check_in_id\"", var16);
            throw var16;
         } else if (var12 == null) {
            IllegalStateException var17 = new IllegalStateException("Missing required field \"monitor_slug\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"monitor_slug\"", var17);
            throw var17;
         } else if (var11 != null) {
            CheckIn var19 = new CheckIn(var13, var12, var11);
            var19.setDuration((Double)var6);
            var19.setRelease(var7);
            var19.setEnvironment(var8);
            var19.setMonitorConfig((MonitorConfig)var9);
            var19.getContexts().putAll((Map)var10);
            var19.setUnknown((Map<String, Object>)var5);
            return var19;
         } else {
            IllegalStateException var18 = new IllegalStateException("Missing required field \"status\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"status\"", var18);
            throw var18;
         }
      }
   }

   public static final class JsonKeys {
      public static final String CHECK_IN_ID = "check_in_id";
      public static final String CONTEXTS = "contexts";
      public static final String DURATION = "duration";
      public static final String ENVIRONMENT = "environment";
      public static final String MONITOR_CONFIG = "monitor_config";
      public static final String MONITOR_SLUG = "monitor_slug";
      public static final String RELEASE = "release";
      public static final String STATUS = "status";
   }
}
