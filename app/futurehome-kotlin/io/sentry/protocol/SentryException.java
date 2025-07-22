package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SentryException implements JsonUnknown, JsonSerializable {
   private Mechanism mechanism;
   private String module;
   private SentryStackTrace stacktrace;
   private Long threadId;
   private String type;
   private Map<String, Object> unknown;
   private String value;

   public Mechanism getMechanism() {
      return this.mechanism;
   }

   public String getModule() {
      return this.module;
   }

   public SentryStackTrace getStacktrace() {
      return this.stacktrace;
   }

   public Long getThreadId() {
      return this.threadId;
   }

   public String getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getValue() {
      return this.value;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.type != null) {
         var1.name("type").value(this.type);
      }

      if (this.value != null) {
         var1.name("value").value(this.value);
      }

      if (this.module != null) {
         var1.name("module").value(this.module);
      }

      if (this.threadId != null) {
         var1.name("thread_id").value(this.threadId);
      }

      if (this.stacktrace != null) {
         var1.name("stacktrace").value(var2, this.stacktrace);
      }

      if (this.mechanism != null) {
         var1.name("mechanism").value(var2, this.mechanism);
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

   public void setMechanism(Mechanism var1) {
      this.mechanism = var1;
   }

   public void setModule(String var1) {
      this.module = var1;
   }

   public void setStacktrace(SentryStackTrace var1) {
      this.stacktrace = var1;
   }

   public void setThreadId(Long var1) {
      this.threadId = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setValue(String var1) {
      this.value = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryException> {
      public SentryException deserialize(ObjectReader var1, ILogger var2) throws Exception {
         SentryException var7 = new SentryException();
         var1.beginObject();
         HashMap var6 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1562235024:
                  if (var8.equals("thread_id")) {
                     var3 = 0;
                  }
                  break;
               case -1068784020:
                  if (var8.equals("module")) {
                     var3 = 1;
                  }
                  break;
               case 3575610:
                  if (var8.equals("type")) {
                     var3 = 2;
                  }
                  break;
               case 111972721:
                  if (var8.equals("value")) {
                     var3 = 3;
                  }
                  break;
               case 1225089881:
                  if (var8.equals("mechanism")) {
                     var3 = 4;
                  }
                  break;
               case 2055832509:
                  if (var8.equals("stacktrace")) {
                     var3 = 5;
                  }
            }

            switch (var3) {
               case 0:
                  var7.threadId = var1.nextLongOrNull();
                  break;
               case 1:
                  var7.module = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.type = var1.nextStringOrNull();
                  break;
               case 3:
                  var7.value = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.mechanism = var1.nextOrNull(var2, new Mechanism.Deserializer());
                  break;
               case 5:
                  var7.stacktrace = var1.nextOrNull(var2, new SentryStackTrace.Deserializer());
                  break;
               default:
                  HashMap var5 = var6;
                  if (var6 == null) {
                     var5 = new HashMap();
                  }

                  var1.nextUnknown(var2, var5, var8);
                  var6 = var5;
            }
         }

         var1.endObject();
         var7.setUnknown(var6);
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String MECHANISM = "mechanism";
      public static final String MODULE = "module";
      public static final String STACKTRACE = "stacktrace";
      public static final String THREAD_ID = "thread_id";
      public static final String TYPE = "type";
      public static final String VALUE = "value";
   }
}
