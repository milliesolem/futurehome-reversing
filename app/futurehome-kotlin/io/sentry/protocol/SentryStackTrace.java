package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class SentryStackTrace implements JsonUnknown, JsonSerializable {
   private List<SentryStackFrame> frames;
   private Map<String, String> registers;
   private Boolean snapshot;
   private Map<String, Object> unknown;

   public SentryStackTrace() {
   }

   public SentryStackTrace(List<SentryStackFrame> var1) {
      this.frames = var1;
   }

   public List<SentryStackFrame> getFrames() {
      return this.frames;
   }

   public Map<String, String> getRegisters() {
      return this.registers;
   }

   public Boolean getSnapshot() {
      return this.snapshot;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.frames != null) {
         var1.name("frames").value(var2, this.frames);
      }

      if (this.registers != null) {
         var1.name("registers").value(var2, this.registers);
      }

      if (this.snapshot != null) {
         var1.name("snapshot").value(this.snapshot);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.unknown.get(var6);
            var1.name(var6);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setFrames(List<SentryStackFrame> var1) {
      this.frames = var1;
   }

   public void setRegisters(Map<String, String> var1) {
      this.registers = var1;
   }

   public void setSnapshot(Boolean var1) {
      this.snapshot = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryStackTrace> {
      public SentryStackTrace deserialize(ObjectReader var1, ILogger var2) throws Exception {
         SentryStackTrace var7 = new SentryStackTrace();
         var1.beginObject();
         ConcurrentHashMap var6 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1266514778:
                  if (var8.equals("frames")) {
                     var3 = 0;
                  }
                  break;
               case 78226992:
                  if (var8.equals("registers")) {
                     var3 = 1;
                  }
                  break;
               case 284874180:
                  if (var8.equals("snapshot")) {
                     var3 = 2;
                  }
            }

            switch (var3) {
               case 0:
                  var7.frames = var1.nextListOrNull(var2, new SentryStackFrame.Deserializer());
                  break;
               case 1:
                  var7.registers = CollectionUtils.newConcurrentHashMap((Map<String, String>)var1.nextObjectOrNull());
                  break;
               case 2:
                  var7.snapshot = var1.nextBooleanOrNull();
                  break;
               default:
                  ConcurrentHashMap var5 = var6;
                  if (var6 == null) {
                     var5 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var5, var8);
                  var6 = var5;
            }
         }

         var7.setUnknown(var6);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String FRAMES = "frames";
      public static final String REGISTERS = "registers";
      public static final String SNAPSHOT = "snapshot";
   }
}
