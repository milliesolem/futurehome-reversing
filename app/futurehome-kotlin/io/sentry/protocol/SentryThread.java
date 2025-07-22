package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLockReason;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SentryThread implements JsonUnknown, JsonSerializable {
   private Boolean crashed;
   private Boolean current;
   private Boolean daemon;
   private Map<String, SentryLockReason> heldLocks;
   private Long id;
   private Boolean main;
   private String name;
   private Integer priority;
   private SentryStackTrace stacktrace;
   private String state;
   private Map<String, Object> unknown;

   public Map<String, SentryLockReason> getHeldLocks() {
      return this.heldLocks;
   }

   public Long getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public Integer getPriority() {
      return this.priority;
   }

   public SentryStackTrace getStacktrace() {
      return this.stacktrace;
   }

   public String getState() {
      return this.state;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public Boolean isCrashed() {
      return this.crashed;
   }

   public Boolean isCurrent() {
      return this.current;
   }

   public Boolean isDaemon() {
      return this.daemon;
   }

   public Boolean isMain() {
      return this.main;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.id != null) {
         var1.name("id").value(this.id);
      }

      if (this.priority != null) {
         var1.name("priority").value(this.priority);
      }

      if (this.name != null) {
         var1.name("name").value(this.name);
      }

      if (this.state != null) {
         var1.name("state").value(this.state);
      }

      if (this.crashed != null) {
         var1.name("crashed").value(this.crashed);
      }

      if (this.current != null) {
         var1.name("current").value(this.current);
      }

      if (this.daemon != null) {
         var1.name("daemon").value(this.daemon);
      }

      if (this.main != null) {
         var1.name("main").value(this.main);
      }

      if (this.stacktrace != null) {
         var1.name("stacktrace").value(var2, this.stacktrace);
      }

      if (this.heldLocks != null) {
         var1.name("held_locks").value(var2, this.heldLocks);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var4 = this.unknown.get(var6);
            var1.name(var6);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setCrashed(Boolean var1) {
      this.crashed = var1;
   }

   public void setCurrent(Boolean var1) {
      this.current = var1;
   }

   public void setDaemon(Boolean var1) {
      this.daemon = var1;
   }

   public void setHeldLocks(Map<String, SentryLockReason> var1) {
      this.heldLocks = var1;
   }

   public void setId(Long var1) {
      this.id = var1;
   }

   public void setMain(Boolean var1) {
      this.main = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setPriority(Integer var1) {
      this.priority = var1;
   }

   public void setStacktrace(SentryStackTrace var1) {
      this.stacktrace = var1;
   }

   public void setState(String var1) {
      this.state = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryThread> {
      public SentryThread deserialize(ObjectReader var1, ILogger var2) throws Exception {
         SentryThread var7 = new SentryThread();
         var1.beginObject();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1339353468:
                  if (var8.equals("daemon")) {
                     var3 = 0;
                  }
                  break;
               case -1165461084:
                  if (var8.equals("priority")) {
                     var3 = 1;
                  }
                  break;
               case -502917346:
                  if (var8.equals("held_locks")) {
                     var3 = 2;
                  }
                  break;
               case 3355:
                  if (var8.equals("id")) {
                     var3 = 3;
                  }
                  break;
               case 3343801:
                  if (var8.equals("main")) {
                     var3 = 4;
                  }
                  break;
               case 3373707:
                  if (var8.equals("name")) {
                     var3 = 5;
                  }
                  break;
               case 109757585:
                  if (var8.equals("state")) {
                     var3 = 6;
                  }
                  break;
               case 1025385094:
                  if (var8.equals("crashed")) {
                     var3 = 7;
                  }
                  break;
               case 1126940025:
                  if (var8.equals("current")) {
                     var3 = 8;
                  }
                  break;
               case 2055832509:
                  if (var8.equals("stacktrace")) {
                     var3 = 9;
                  }
            }

            switch (var3) {
               case 0:
                  var7.daemon = var1.nextBooleanOrNull();
                  break;
               case 1:
                  var7.priority = var1.nextIntegerOrNull();
                  break;
               case 2:
                  Map var9 = var1.nextMapOrNull(var2, new SentryLockReason.Deserializer());
                  if (var9 != null) {
                     var7.heldLocks = new HashMap<>(var9);
                  }
                  break;
               case 3:
                  var7.id = var1.nextLongOrNull();
                  break;
               case 4:
                  var7.main = var1.nextBooleanOrNull();
                  break;
               case 5:
                  var7.name = var1.nextStringOrNull();
                  break;
               case 6:
                  var7.state = var1.nextStringOrNull();
                  break;
               case 7:
                  var7.crashed = var1.nextBooleanOrNull();
                  break;
               case 8:
                  var7.current = var1.nextBooleanOrNull();
                  break;
               case 9:
                  var7.stacktrace = var1.nextOrNull(var2, new SentryStackTrace.Deserializer());
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
      public static final String CRASHED = "crashed";
      public static final String CURRENT = "current";
      public static final String DAEMON = "daemon";
      public static final String HELD_LOCKS = "held_locks";
      public static final String ID = "id";
      public static final String MAIN = "main";
      public static final String NAME = "name";
      public static final String PRIORITY = "priority";
      public static final String STACKTRACE = "stacktrace";
      public static final String STATE = "state";
   }
}
