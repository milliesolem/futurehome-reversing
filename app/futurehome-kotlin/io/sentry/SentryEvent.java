package io.sentry;

import io.sentry.protocol.Message;
import io.sentry.protocol.SentryException;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryThread;
import io.sentry.util.CollectionUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SentryEvent extends SentryBaseEvent implements JsonUnknown, JsonSerializable {
   private SentryValues<SentryException> exception;
   private List<String> fingerprint;
   private SentryLevel level;
   private String logger;
   private Message message;
   private Map<String, String> modules;
   private SentryValues<SentryThread> threads;
   private Date timestamp;
   private String transaction;
   private Map<String, Object> unknown;

   public SentryEvent() {
      this(new SentryId(), DateUtils.getCurrentDateTime());
   }

   SentryEvent(SentryId var1, Date var2) {
      super(var1);
      this.timestamp = var2;
   }

   public SentryEvent(Throwable var1) {
      this();
      this.throwable = var1;
   }

   public SentryEvent(Date var1) {
      this(new SentryId(), var1);
   }

   public List<SentryException> getExceptions() {
      SentryValues var1 = this.exception;
      List var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getValues();
      }

      return var2;
   }

   public List<String> getFingerprints() {
      return this.fingerprint;
   }

   public SentryLevel getLevel() {
      return this.level;
   }

   public String getLogger() {
      return this.logger;
   }

   public Message getMessage() {
      return this.message;
   }

   public String getModule(String var1) {
      Map var2 = this.modules;
      return var2 != null ? (String)var2.get(var1) : null;
   }

   Map<String, String> getModules() {
      return this.modules;
   }

   public List<SentryThread> getThreads() {
      SentryValues var1 = this.threads;
      return var1 != null ? var1.getValues() : null;
   }

   public Date getTimestamp() {
      return (Date)this.timestamp.clone();
   }

   public String getTransaction() {
      return this.transaction;
   }

   public SentryException getUnhandledException() {
      SentryValues var1 = this.exception;
      if (var1 != null) {
         for (SentryException var2 : var1.getValues()) {
            if (var2.getMechanism() != null && var2.getMechanism().isHandled() != null && !var2.getMechanism().isHandled()) {
               return var2;
            }
         }
      }

      return null;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public boolean isCrashed() {
      boolean var1;
      if (this.getUnhandledException() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isErrored() {
      SentryValues var2 = this.exception;
      boolean var1;
      if (var2 != null && !var2.getValues().isEmpty()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void removeModule(String var1) {
      Map var2 = this.modules;
      if (var2 != null) {
         var2.remove(var1);
      }
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("timestamp").value(var2, this.timestamp);
      if (this.message != null) {
         var1.name("message").value(var2, this.message);
      }

      if (this.logger != null) {
         var1.name("logger").value(this.logger);
      }

      SentryValues var3 = this.threads;
      if (var3 != null && !var3.getValues().isEmpty()) {
         var1.name("threads");
         var1.beginObject();
         var1.name("values").value(var2, this.threads.getValues());
         var1.endObject();
      }

      var3 = this.exception;
      if (var3 != null && !var3.getValues().isEmpty()) {
         var1.name("exception");
         var1.beginObject();
         var1.name("values").value(var2, this.exception.getValues());
         var1.endObject();
      }

      if (this.level != null) {
         var1.name("level").value(var2, this.level);
      }

      if (this.transaction != null) {
         var1.name("transaction").value(this.transaction);
      }

      if (this.fingerprint != null) {
         var1.name("fingerprint").value(var2, this.fingerprint);
      }

      if (this.modules != null) {
         var1.name("modules").value(var2, this.modules);
      }

      new SentryBaseEvent.Serializer().serialize(this, var1, var2);
      Map var7 = this.unknown;
      if (var7 != null) {
         for (String var4 : var7.keySet()) {
            var3 = (SentryValues)this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setExceptions(List<SentryException> var1) {
      this.exception = new SentryValues<>(var1);
   }

   public void setFingerprints(List<String> var1) {
      ArrayList var2;
      if (var1 != null) {
         var2 = new ArrayList(var1);
      } else {
         var2 = null;
      }

      this.fingerprint = var2;
   }

   public void setLevel(SentryLevel var1) {
      this.level = var1;
   }

   public void setLogger(String var1) {
      this.logger = var1;
   }

   public void setMessage(Message var1) {
      this.message = var1;
   }

   public void setModule(String var1, String var2) {
      if (this.modules == null) {
         this.modules = new HashMap<>();
      }

      this.modules.put(var1, var2);
   }

   public void setModules(Map<String, String> var1) {
      this.modules = CollectionUtils.newHashMap(var1);
   }

   public void setThreads(List<SentryThread> var1) {
      this.threads = new SentryValues<>(var1);
   }

   public void setTimestamp(Date var1) {
      this.timestamp = var1;
   }

   public void setTransaction(String var1) {
      this.transaction = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryEvent> {
      public SentryEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         SentryEvent var8 = new SentryEvent();
         SentryBaseEvent.Deserializer var7 = new SentryBaseEvent.Deserializer();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var9 = var1.nextName();
            var9.hashCode();
            int var4 = var9.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1375934236:
                  if (var9.equals("fingerprint")) {
                     var3 = 0;
                  }
                  break;
               case -1337936983:
                  if (var9.equals("threads")) {
                     var3 = 1;
                  }
                  break;
               case -1097337456:
                  if (var9.equals("logger")) {
                     var3 = 2;
                  }
                  break;
               case 55126294:
                  if (var9.equals("timestamp")) {
                     var3 = 3;
                  }
                  break;
               case 102865796:
                  if (var9.equals("level")) {
                     var3 = 4;
                  }
                  break;
               case 954925063:
                  if (var9.equals("message")) {
                     var3 = 5;
                  }
                  break;
               case 1227433863:
                  if (var9.equals("modules")) {
                     var3 = 6;
                  }
                  break;
               case 1481625679:
                  if (var9.equals("exception")) {
                     var3 = 7;
                  }
                  break;
               case 2141246174:
                  if (var9.equals("transaction")) {
                     var3 = 8;
                  }
            }

            switch (var3) {
               case 0:
                  List var11 = (List)var1.nextObjectOrNull();
                  if (var11 != null) {
                     var8.fingerprint = var11;
                  }
                  break;
               case 1:
                  var1.beginObject();
                  var1.nextName();
                  var8.threads = new SentryValues<>(var1.nextListOrNull(var2, new SentryThread.Deserializer()));
                  var1.endObject();
                  break;
               case 2:
                  var8.logger = var1.nextStringOrNull();
                  break;
               case 3:
                  Date var10 = var1.nextDateOrNull(var2);
                  if (var10 != null) {
                     var8.timestamp = var10;
                  }
                  break;
               case 4:
                  var8.level = var1.nextOrNull(var2, new SentryLevel.Deserializer());
                  break;
               case 5:
                  var8.message = var1.nextOrNull(var2, new Message.Deserializer());
                  break;
               case 6:
                  var8.modules = CollectionUtils.newConcurrentHashMap((Map<String, String>)var1.nextObjectOrNull());
                  break;
               case 7:
                  var1.beginObject();
                  var1.nextName();
                  var8.exception = new SentryValues<>(var1.nextListOrNull(var2, new SentryException.Deserializer()));
                  var1.endObject();
                  break;
               case 8:
                  var8.transaction = var1.nextStringOrNull();
                  break;
               default:
                  if (!var7.deserializeValue(var8, var9, var1, var2)) {
                     ConcurrentHashMap var6 = var5;
                     if (var5 == null) {
                        var6 = new ConcurrentHashMap();
                     }

                     var1.nextUnknown(var2, var6, var9);
                     var5 = var6;
                  }
            }
         }

         var8.setUnknown(var5);
         var1.endObject();
         return var8;
      }
   }

   public static final class JsonKeys {
      public static final String EXCEPTION = "exception";
      public static final String FINGERPRINT = "fingerprint";
      public static final String LEVEL = "level";
      public static final String LOGGER = "logger";
      public static final String MESSAGE = "message";
      public static final String MODULES = "modules";
      public static final String THREADS = "threads";
      public static final String TIMESTAMP = "timestamp";
      public static final String TRANSACTION = "transaction";
   }
}
