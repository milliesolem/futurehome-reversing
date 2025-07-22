package io.sentry;

import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MonitorConfig implements JsonUnknown, JsonSerializable {
   private Long checkinMargin;
   private Long failureIssueThreshold;
   private Long maxRuntime;
   private Long recoveryThreshold;
   private MonitorSchedule schedule;
   private String timezone;
   private Map<String, Object> unknown;

   public MonitorConfig(MonitorSchedule var1) {
      this.schedule = var1;
      SentryOptions.Cron var2 = HubAdapter.getInstance().getOptions().getCron();
      if (var2 != null) {
         this.checkinMargin = var2.getDefaultCheckinMargin();
         this.maxRuntime = var2.getDefaultMaxRuntime();
         this.timezone = var2.getDefaultTimezone();
         this.failureIssueThreshold = var2.getDefaultFailureIssueThreshold();
         this.recoveryThreshold = var2.getDefaultRecoveryThreshold();
      }
   }

   public Long getCheckinMargin() {
      return this.checkinMargin;
   }

   public Long getFailureIssueThreshold() {
      return this.failureIssueThreshold;
   }

   public Long getMaxRuntime() {
      return this.maxRuntime;
   }

   public Long getRecoveryThreshold() {
      return this.recoveryThreshold;
   }

   public MonitorSchedule getSchedule() {
      return this.schedule;
   }

   public String getTimezone() {
      return this.timezone;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("schedule");
      this.schedule.serialize(var1, var2);
      if (this.checkinMargin != null) {
         var1.name("checkin_margin").value(this.checkinMargin);
      }

      if (this.maxRuntime != null) {
         var1.name("max_runtime").value(this.maxRuntime);
      }

      if (this.timezone != null) {
         var1.name("timezone").value(this.timezone);
      }

      if (this.failureIssueThreshold != null) {
         var1.name("failure_issue_threshold").value(this.failureIssueThreshold);
      }

      if (this.recoveryThreshold != null) {
         var1.name("recovery_threshold").value(this.recoveryThreshold);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var5);
            var1.name(var5).value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setCheckinMargin(Long var1) {
      this.checkinMargin = var1;
   }

   public void setFailureIssueThreshold(Long var1) {
      this.failureIssueThreshold = var1;
   }

   public void setMaxRuntime(Long var1) {
      this.maxRuntime = var1;
   }

   public void setRecoveryThreshold(Long var1) {
      this.recoveryThreshold = var1;
   }

   public void setSchedule(MonitorSchedule var1) {
      this.schedule = var1;
   }

   public void setTimezone(String var1) {
      this.timezone = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<MonitorConfig> {
      public MonitorConfig deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         MonitorSchedule var11 = null;
         Long var10 = null;
         Long var9 = null;
         Object var5 = var9;
         Long var6 = var9;
         Long var7 = var9;
         Object var8 = var9;

         while (var1.peek() == JsonToken.NAME) {
            String var13 = var1.nextName();
            var13.hashCode();
            int var4 = var13.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -2076227591:
                  if (var13.equals("timezone")) {
                     var3 = 0;
                  }
                  break;
               case -905406976:
                  if (var13.equals("checkin_margin")) {
                     var3 = 1;
                  }
                  break;
               case -697920873:
                  if (var13.equals("schedule")) {
                     var3 = 2;
                  }
                  break;
               case -607475647:
                  if (var13.equals("recovery_threshold")) {
                     var3 = 3;
                  }
                  break;
               case 1581873149:
                  if (var13.equals("max_runtime")) {
                     var3 = 4;
                  }
                  break;
               case 2138521552:
                  if (var13.equals("failure_issue_threshold")) {
                     var3 = 5;
                  }
            }

            switch (var3) {
               case 0:
                  var5 = var1.nextStringOrNull();
                  break;
               case 1:
                  var10 = var1.nextLongOrNull();
                  break;
               case 2:
                  var11 = new MonitorSchedule.Deserializer().deserialize(var1, var2);
                  break;
               case 3:
                  var7 = var1.nextLongOrNull();
                  break;
               case 4:
                  var9 = var1.nextLongOrNull();
                  break;
               case 5:
                  var6 = var1.nextLongOrNull();
                  break;
               default:
                  Object var12 = var8;
                  if (var8 == null) {
                     var12 = new HashMap();
                  }

                  var1.nextUnknown(var2, (Map<String, Object>)var12, var13);
                  var8 = var12;
            }
         }

         var1.endObject();
         if (var11 != null) {
            MonitorConfig var15 = new MonitorConfig(var11);
            var15.setCheckinMargin(var10);
            var15.setMaxRuntime(var9);
            var15.setTimezone((String)var5);
            var15.setFailureIssueThreshold(var6);
            var15.setRecoveryThreshold(var7);
            var15.setUnknown((Map<String, Object>)var8);
            return var15;
         } else {
            IllegalStateException var14 = new IllegalStateException("Missing required field \"schedule\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"schedule\"", var14);
            throw var14;
         }
      }
   }

   public static final class JsonKeys {
      public static final String CHECKIN_MARGIN = "checkin_margin";
      public static final String FAILURE_ISSUE_THRESHOLD = "failure_issue_threshold";
      public static final String MAX_RUNTIME = "max_runtime";
      public static final String RECOVERY_THRESHOLD = "recovery_threshold";
      public static final String SCHEDULE = "schedule";
      public static final String TIMEZONE = "timezone";
   }
}
