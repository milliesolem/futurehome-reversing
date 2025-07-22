package io.sentry;

public enum DataCategory {
   All("__all__"),
   Attachment("attachment"),
   Default("default"),
   Error("error"),
   MetricBucket("metric_bucket"),
   Monitor("monitor"),
   Profile("profile"),
   Replay("replay"),
   Security("security"),
   Session("session"),
   Span("span"),
   Transaction("transaction"),
   Unknown("unknown"),
   UserReport("user_report");

   private static final DataCategory[] $VALUES = $values();
   private final String category;

   private DataCategory(String var3) {
      this.category = var3;
   }

   public String getCategory() {
      return this.category;
   }
}
