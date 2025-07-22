package io.sentry;

import io.sentry.exception.InvalidSentryTraceHeaderException;
import io.sentry.protocol.SentryId;
import java.util.Arrays;
import java.util.List;

public final class PropagationContext {
   private Baggage baggage;
   private SpanId parentSpanId;
   private Boolean sampled;
   private SpanId spanId;
   private SentryId traceId;

   public PropagationContext() {
      this(new SentryId(), new SpanId(), null, null, null);
   }

   public PropagationContext(PropagationContext var1) {
      this(var1.getTraceId(), var1.getSpanId(), var1.getParentSpanId(), cloneBaggage(var1.getBaggage()), var1.isSampled());
   }

   public PropagationContext(SentryId var1, SpanId var2, SpanId var3, Baggage var4, Boolean var5) {
      this.traceId = var1;
      this.spanId = var2;
      this.parentSpanId = var3;
      this.baggage = var4;
      this.sampled = var5;
   }

   private static Baggage cloneBaggage(Baggage var0) {
      return var0 != null ? new Baggage(var0) : null;
   }

   public static PropagationContext fromHeaders(ILogger var0, String var1, String var2) {
      return fromHeaders(var0, var1, Arrays.asList(var2));
   }

   public static PropagationContext fromHeaders(ILogger var0, String var1, List<String> var2) {
      if (var1 == null) {
         return new PropagationContext();
      } else {
         try {
            SentryTraceHeader var3 = new SentryTraceHeader(var1);
            return fromHeaders(var3, Baggage.fromHeader(var2, var0), null);
         } catch (InvalidSentryTraceHeaderException var4) {
            var0.log(SentryLevel.DEBUG, var4, "Failed to parse Sentry trace header: %s", var4.getMessage());
            return new PropagationContext();
         }
      }
   }

   public static PropagationContext fromHeaders(SentryTraceHeader var0, Baggage var1, SpanId var2) {
      SpanId var3 = var2;
      if (var2 == null) {
         var3 = new SpanId();
      }

      return new PropagationContext(var0.getTraceId(), var3, var0.getSpanId(), var1, var0.isSampled());
   }

   public Baggage getBaggage() {
      return this.baggage;
   }

   public SpanId getParentSpanId() {
      return this.parentSpanId;
   }

   public SpanId getSpanId() {
      return this.spanId;
   }

   public SentryId getTraceId() {
      return this.traceId;
   }

   public Boolean isSampled() {
      return this.sampled;
   }

   public void setBaggage(Baggage var1) {
      this.baggage = var1;
   }

   public void setParentSpanId(SpanId var1) {
      this.parentSpanId = var1;
   }

   public void setSampled(Boolean var1) {
      this.sampled = var1;
   }

   public void setSpanId(SpanId var1) {
      this.spanId = var1;
   }

   public void setTraceId(SentryId var1) {
      this.traceId = var1;
   }

   public SpanContext toSpanContext() {
      SpanContext var1 = new SpanContext(this.traceId, this.spanId, "default", null, null);
      var1.setOrigin("auto");
      return var1;
   }

   public TraceContext traceContext() {
      Baggage var1 = this.baggage;
      return var1 != null ? var1.toTraceContext() : null;
   }
}
