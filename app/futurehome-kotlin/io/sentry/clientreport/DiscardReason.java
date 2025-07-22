package io.sentry.clientreport;

public enum DiscardReason {
   BACKPRESSURE("backpressure"),
   BEFORE_SEND("before_send"),
   CACHE_OVERFLOW("cache_overflow"),
   EVENT_PROCESSOR("event_processor"),
   NETWORK_ERROR("network_error"),
   QUEUE_OVERFLOW("queue_overflow"),
   RATELIMIT_BACKOFF("ratelimit_backoff"),
   SAMPLE_RATE("sample_rate");

   private static final DiscardReason[] $VALUES = $values();
   private final String reason;

   private DiscardReason(String var3) {
      this.reason = var3;
   }

   public String getReason() {
      return this.reason;
   }
}
