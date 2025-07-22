package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public class SpanContext implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "trace";
   protected String description;
   protected String op;
   protected String origin;
   private final SpanId parentSpanId;
   private transient TracesSamplingDecision samplingDecision;
   private final SpanId spanId;
   protected SpanStatus status;
   protected Map<String, String> tags = new ConcurrentHashMap();
   private final SentryId traceId;
   private Map<String, Object> unknown;

   public SpanContext(SpanContext var1) {
      this.origin = "manual";
      this.traceId = var1.traceId;
      this.spanId = var1.spanId;
      this.parentSpanId = var1.parentSpanId;
      this.samplingDecision = var1.samplingDecision;
      this.op = var1.op;
      this.description = var1.description;
      this.status = var1.status;
      Map var2 = CollectionUtils.newConcurrentHashMap(var1.tags);
      if (var2 != null) {
         this.tags = var2;
      }
   }

   public SpanContext(SentryId var1, SpanId var2, SpanId var3, String var4, String var5, TracesSamplingDecision var6, SpanStatus var7, String var8) {
      this.origin = "manual";
      this.traceId = Objects.requireNonNull(var1, "traceId is required");
      this.spanId = Objects.requireNonNull(var2, "spanId is required");
      this.op = Objects.requireNonNull(var4, "operation is required");
      this.parentSpanId = var3;
      this.samplingDecision = var6;
      this.description = var5;
      this.status = var7;
      this.origin = var8;
   }

   public SpanContext(SentryId var1, SpanId var2, String var3, SpanId var4, TracesSamplingDecision var5) {
      this(var1, var2, var4, var3, null, var5, null, "manual");
   }

   public SpanContext(String var1) {
      this(new SentryId(), new SpanId(), var1, null, null);
   }

   public SpanContext(String var1, TracesSamplingDecision var2) {
      this(new SentryId(), new SpanId(), var1, null, var2);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SpanContext)) {
         return false;
      } else {
         var1 = var1;
         if (!this.traceId.equals(var1.traceId)
            || !this.spanId.equals(var1.spanId)
            || !Objects.equals(this.parentSpanId, var1.parentSpanId)
            || !this.op.equals(var1.op)
            || !Objects.equals(this.description, var1.description)
            || this.status != var1.status) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getDescription() {
      return this.description;
   }

   public String getOperation() {
      return this.op;
   }

   public String getOrigin() {
      return this.origin;
   }

   public SpanId getParentSpanId() {
      return this.parentSpanId;
   }

   public Boolean getProfileSampled() {
      TracesSamplingDecision var1 = this.samplingDecision;
      return var1 == null ? null : var1.getProfileSampled();
   }

   public Boolean getSampled() {
      TracesSamplingDecision var1 = this.samplingDecision;
      return var1 == null ? null : var1.getSampled();
   }

   public TracesSamplingDecision getSamplingDecision() {
      return this.samplingDecision;
   }

   public SpanId getSpanId() {
      return this.spanId;
   }

   public SpanStatus getStatus() {
      return this.status;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   public SentryId getTraceId() {
      return this.traceId;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.traceId, this.spanId, this.parentSpanId, this.op, this.description, this.status);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("trace_id");
      this.traceId.serialize(var1, var2);
      var1.name("span_id");
      this.spanId.serialize(var1, var2);
      if (this.parentSpanId != null) {
         var1.name("parent_span_id");
         this.parentSpanId.serialize(var1, var2);
      }

      var1.name("op").value(this.op);
      if (this.description != null) {
         var1.name("description").value(this.description);
      }

      if (this.status != null) {
         var1.name("status").value(var2, this.status);
      }

      if (this.origin != null) {
         var1.name("origin").value(var2, this.origin);
      }

      if (!this.tags.isEmpty()) {
         var1.name("tags").value(var2, this.tags);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public void setOperation(String var1) {
      this.op = Objects.requireNonNull(var1, "operation is required");
   }

   public void setOrigin(String var1) {
      this.origin = var1;
   }

   public void setSampled(Boolean var1) {
      if (var1 == null) {
         this.setSamplingDecision(null);
      } else {
         this.setSamplingDecision(new TracesSamplingDecision(var1));
      }
   }

   public void setSampled(Boolean var1, Boolean var2) {
      if (var1 == null) {
         this.setSamplingDecision(null);
      } else if (var2 == null) {
         this.setSamplingDecision(new TracesSamplingDecision(var1));
      } else {
         this.setSamplingDecision(new TracesSamplingDecision(var1, null, var2, null));
      }
   }

   public void setSamplingDecision(TracesSamplingDecision var1) {
      this.samplingDecision = var1;
   }

   public void setStatus(SpanStatus var1) {
      this.status = var1;
   }

   public void setTag(String var1, String var2) {
      Objects.requireNonNull(var1, "name is required");
      Objects.requireNonNull(var2, "value is required");
      this.tags.put(var1, var2);
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SpanContext> {
      public SpanContext deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Object var14 = null;
         SentryId var13 = null;
         SpanId var12 = null;
         Object var6 = var12;
         Object var7 = var12;
         Object var8 = var12;
         Object var9 = var12;
         Object var10 = var12;
         SpanId var11 = var12;
         String var5 = (String)var14;

         while (var1.peek() == JsonToken.NAME) {
            String var15 = var1.nextName();
            var15.hashCode();
            int var4 = var15.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -2011840976:
                  if (var15.equals("span_id")) {
                     var3 = 0;
                  }
                  break;
               case -1757797477:
                  if (var15.equals("parent_span_id")) {
                     var3 = 1;
                  }
                  break;
               case -1724546052:
                  if (var15.equals("description")) {
                     var3 = 2;
                  }
                  break;
               case -1008619738:
                  if (var15.equals("origin")) {
                     var3 = 3;
                  }
                  break;
               case -892481550:
                  if (var15.equals("status")) {
                     var3 = 4;
                  }
                  break;
               case 3553:
                  if (var15.equals("op")) {
                     var3 = 5;
                  }
                  break;
               case 3552281:
                  if (var15.equals("tags")) {
                     var3 = 6;
                  }
                  break;
               case 1270300245:
                  if (var15.equals("trace_id")) {
                     var3 = 7;
                  }
            }

            switch (var3) {
               case 0:
                  var12 = new SpanId.Deserializer().deserialize(var1, var2);
                  break;
               case 1:
                  var11 = var1.nextOrNull(var2, new SpanId.Deserializer());
                  break;
               case 2:
                  var7 = var1.nextString();
                  break;
               case 3:
                  var9 = var1.nextString();
                  break;
               case 4:
                  var8 = var1.nextOrNull(var2, new SpanStatus.Deserializer());
                  break;
               case 5:
                  var5 = var1.nextString();
                  break;
               case 6:
                  var10 = CollectionUtils.newConcurrentHashMap((Map)var1.nextObjectOrNull());
                  break;
               case 7:
                  var13 = new SentryId.Deserializer().deserialize(var1, var2);
                  break;
               default:
                  var14 = var6;
                  if (var6 == null) {
                     var14 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, (Map<String, Object>)var14, var15);
                  var6 = var14;
            }
         }

         if (var13 == null) {
            IllegalStateException var16 = new IllegalStateException("Missing required field \"trace_id\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"trace_id\"", var16);
            throw var16;
         } else if (var12 != null) {
            if (var5 == null) {
               var5 = "";
            }

            SpanContext var18 = new SpanContext(var13, var12, var5, var11, null);
            var18.setDescription((String)var7);
            var18.setStatus((SpanStatus)var8);
            var18.setOrigin((String)var9);
            if (var10 != null) {
               var18.tags = (Map<String, String>)var10;
            }

            var18.setUnknown((Map<String, Object>)var6);
            var1.endObject();
            return var18;
         } else {
            IllegalStateException var17 = new IllegalStateException("Missing required field \"span_id\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"span_id\"", var17);
            throw var17;
         }
      }
   }

   public static final class JsonKeys {
      public static final String DESCRIPTION = "description";
      public static final String OP = "op";
      public static final String ORIGIN = "origin";
      public static final String PARENT_SPAN_ID = "parent_span_id";
      public static final String SPAN_ID = "span_id";
      public static final String STATUS = "status";
      public static final String TAGS = "tags";
      public static final String TRACE_ID = "trace_id";
   }
}
