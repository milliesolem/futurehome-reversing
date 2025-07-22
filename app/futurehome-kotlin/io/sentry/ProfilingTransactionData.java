package io.sentry;

import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class ProfilingTransactionData implements JsonUnknown, JsonSerializable {
   private String id;
   private String name;
   private Long relativeEndCpuMs;
   private Long relativeEndNs;
   private Long relativeStartCpuMs;
   private Long relativeStartNs;
   private String traceId;
   private Map<String, Object> unknown;

   public ProfilingTransactionData() {
      NoOpTransaction var1 = NoOpTransaction.getInstance();
      Long var2 = 0L;
      this(var1, var2, var2);
   }

   public ProfilingTransactionData(ITransaction var1, Long var2, Long var3) {
      this.id = var1.getEventId().toString();
      this.traceId = var1.getSpanContext().getTraceId().toString();
      String var4;
      if (var1.getName().isEmpty()) {
         var4 = "unknown";
      } else {
         var4 = var1.getName();
      }

      this.name = var4;
      this.relativeStartNs = var2;
      this.relativeStartCpuMs = var3;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!this.id.equals(var1.id)
            || !this.traceId.equals(var1.traceId)
            || !this.name.equals(var1.name)
            || !this.relativeStartNs.equals(var1.relativeStartNs)
            || !this.relativeStartCpuMs.equals(var1.relativeStartCpuMs)
            || !Objects.equals(this.relativeEndCpuMs, var1.relativeEndCpuMs)
            || !Objects.equals(this.relativeEndNs, var1.relativeEndNs)
            || !Objects.equals(this.unknown, var1.unknown)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public Long getRelativeEndCpuMs() {
      return this.relativeEndCpuMs;
   }

   public Long getRelativeEndNs() {
      return this.relativeEndNs;
   }

   public Long getRelativeStartCpuMs() {
      return this.relativeStartCpuMs;
   }

   public Long getRelativeStartNs() {
      return this.relativeStartNs;
   }

   public String getTraceId() {
      return this.traceId;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.id, this.traceId, this.name, this.relativeStartNs, this.relativeEndNs, this.relativeStartCpuMs, this.relativeEndCpuMs, this.unknown
      );
   }

   public void notifyFinish(Long var1, Long var2, Long var3, Long var4) {
      if (this.relativeEndNs == null) {
         this.relativeEndNs = var1 - var2;
         this.relativeStartNs = this.relativeStartNs - var2;
         this.relativeEndCpuMs = var3 - var4;
         this.relativeStartCpuMs = this.relativeStartCpuMs - var4;
      }
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("id").value(var2, this.id);
      var1.name("trace_id").value(var2, this.traceId);
      var1.name("name").value(var2, this.name);
      var1.name("relative_start_ns").value(var2, this.relativeStartNs);
      var1.name("relative_end_ns").value(var2, this.relativeEndNs);
      var1.name("relative_cpu_start_ms").value(var2, this.relativeStartCpuMs);
      var1.name("relative_cpu_end_ms").value(var2, this.relativeEndCpuMs);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setRelativeEndNs(Long var1) {
      this.relativeEndNs = var1;
   }

   public void setRelativeStartNs(Long var1) {
      this.relativeStartNs = var1;
   }

   public void setTraceId(String var1) {
      this.traceId = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ProfilingTransactionData> {
      public ProfilingTransactionData deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         ProfilingTransactionData var7 = new ProfilingTransactionData();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -112372011:
                  if (var8.equals("relative_start_ns")) {
                     var3 = 0;
                  }
                  break;
               case -84607876:
                  if (var8.equals("relative_end_ns")) {
                     var3 = 1;
                  }
                  break;
               case 3355:
                  if (var8.equals("id")) {
                     var3 = 2;
                  }
                  break;
               case 3373707:
                  if (var8.equals("name")) {
                     var3 = 3;
                  }
                  break;
               case 1270300245:
                  if (var8.equals("trace_id")) {
                     var3 = 4;
                  }
                  break;
               case 1566648660:
                  if (var8.equals("relative_cpu_end_ms")) {
                     var3 = 5;
                  }
                  break;
               case 1902256621:
                  if (var8.equals("relative_cpu_start_ms")) {
                     var3 = 6;
                  }
            }

            switch (var3) {
               case 0:
                  Long var15 = var1.nextLongOrNull();
                  if (var15 != null) {
                     var7.relativeStartNs = var15;
                  }
                  break;
               case 1:
                  Long var14 = var1.nextLongOrNull();
                  if (var14 != null) {
                     var7.relativeEndNs = var14;
                  }
                  break;
               case 2:
                  String var13 = var1.nextStringOrNull();
                  if (var13 != null) {
                     var7.id = var13;
                  }
                  break;
               case 3:
                  String var12 = var1.nextStringOrNull();
                  if (var12 != null) {
                     var7.name = var12;
                  }
                  break;
               case 4:
                  String var11 = var1.nextStringOrNull();
                  if (var11 != null) {
                     var7.traceId = var11;
                  }
                  break;
               case 5:
                  Long var10 = var1.nextLongOrNull();
                  if (var10 != null) {
                     var7.relativeEndCpuMs = var10;
                  }
                  break;
               case 6:
                  Long var9 = var1.nextLongOrNull();
                  if (var9 != null) {
                     var7.relativeStartCpuMs = var9;
                  }
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
      public static final String END_CPU_MS = "relative_cpu_end_ms";
      public static final String END_NS = "relative_end_ns";
      public static final String ID = "id";
      public static final String NAME = "name";
      public static final String START_CPU_MS = "relative_cpu_start_ms";
      public static final String START_NS = "relative_start_ns";
      public static final String TRACE_ID = "trace_id";
   }
}
