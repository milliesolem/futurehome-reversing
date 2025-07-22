package io.sentry;

import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

public final class MonitorContexts extends ConcurrentHashMap<String, Object> implements JsonSerializable {
   private static final long serialVersionUID = 3987329379811822556L;

   public MonitorContexts() {
   }

   public MonitorContexts(MonitorContexts var1) {
      for (Entry var3 : var1.entrySet()) {
         if (var3 != null) {
            Object var4 = var3.getValue();
            if ("trace".equals(var3.getKey()) && var4 instanceof SpanContext) {
               this.setTrace(new SpanContext((SpanContext)var4));
            } else {
               this.put((String)var3.getKey(), var4);
            }
         }
      }
   }

   private <T> T toContextType(String var1, Class<T> var2) {
      Object var3 = this.get(var1);
      if (var2.isInstance(var3)) {
         var3 = var2.cast(var3);
      } else {
         var3 = null;
      }

      return (T)var3;
   }

   public SpanContext getTrace() {
      return this.toContextType("trace", SpanContext.class);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      ArrayList var3 = Collections.list(this.keys());
      Collections.sort(var3);

      for (String var6 : var3) {
         Object var5 = this.get(var6);
         if (var5 != null) {
            var1.name(var6).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setTrace(SpanContext var1) {
      Objects.requireNonNull(var1, "traceContext is required");
      this.put("trace", var1);
   }

   public static final class Deserializer implements JsonDeserializer<MonitorContexts> {
      public MonitorContexts deserialize(ObjectReader var1, ILogger var2) throws Exception {
         MonitorContexts var3 = new MonitorContexts();
         var1.beginObject();

         while (var1.peek() == JsonToken.NAME) {
            String var5 = var1.nextName();
            var5.hashCode();
            if (!var5.equals("trace")) {
               Object var4 = var1.nextObjectOrNull();
               if (var4 != null) {
                  var3.put(var5, var4);
               }
            } else {
               var3.setTrace(new SpanContext.Deserializer().deserialize(var1, var2));
            }
         }

         var1.endObject();
         return var3;
      }
   }
}
