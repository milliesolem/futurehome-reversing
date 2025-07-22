package io.sentry;

import io.sentry.exception.InvalidSentryTraceHeaderException;
import io.sentry.protocol.SentryId;

public final class SentryTraceHeader {
   public static final String SENTRY_TRACE_HEADER = "sentry-trace";
   private final Boolean sampled;
   private final SpanId spanId;
   private final SentryId traceId;

   public SentryTraceHeader(SentryId var1, SpanId var2, Boolean var3) {
      this.traceId = var1;
      this.spanId = var2;
      this.sampled = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public SentryTraceHeader(String var1) throws InvalidSentryTraceHeaderException {
      String[] var2 = var1.split("-", -1);
      if (var2.length >= 2) {
         if (var2.length == 3) {
            this.sampled = "1".equals(var2[2]);
         } else {
            this.sampled = null;
         }

         try {
            SentryId var3 = new SentryId(var2[0]);
            this.traceId = var3;
            SpanId var6 = new SpanId(var2[1]);
            this.spanId = var6;
         } catch (Throwable var5) {
            throw new InvalidSentryTraceHeaderException(var1, var5);
         }
      } else {
         throw new InvalidSentryTraceHeaderException(var1);
      }
   }

   public String getName() {
      return "sentry-trace";
   }

   public SpanId getSpanId() {
      return this.spanId;
   }

   public SentryId getTraceId() {
      return this.traceId;
   }

   public String getValue() {
      Boolean var1 = this.sampled;
      if (var1 != null) {
         SentryId var3 = this.traceId;
         SpanId var2 = this.spanId;
         String var4;
         if (var1) {
            var4 = "1";
         } else {
            var4 = "0";
         }

         return String.format("%s-%s-%s", var3, var2, var4);
      } else {
         return String.format("%s-%s", this.traceId, this.spanId);
      }
   }

   public Boolean isSampled() {
      return this.sampled;
   }
}
