package io.sentry;

import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class SentryLockReason implements JsonUnknown, JsonSerializable {
   public static final int ANY = 15;
   public static final int BLOCKED = 8;
   public static final int LOCKED = 1;
   public static final int SLEEPING = 4;
   public static final int WAITING = 2;
   private String address;
   private String className;
   private String packageName;
   private Long threadId;
   private int type;
   private Map<String, Object> unknown;

   public SentryLockReason() {
   }

   public SentryLockReason(SentryLockReason var1) {
      this.type = var1.type;
      this.address = var1.address;
      this.packageName = var1.packageName;
      this.className = var1.className;
      this.threadId = var1.threadId;
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return Objects.equals(this.address, var1.address);
      } else {
         return false;
      }
   }

   public String getAddress() {
      return this.address;
   }

   public String getClassName() {
      return this.className;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public Long getThreadId() {
      return this.threadId;
   }

   public int getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.address);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("type").value((long)this.type);
      if (this.address != null) {
         var1.name("address").value(this.address);
      }

      if (this.packageName != null) {
         var1.name("package_name").value(this.packageName);
      }

      if (this.className != null) {
         var1.name("class_name").value(this.className);
      }

      if (this.threadId != null) {
         var1.name("thread_id").value(this.threadId);
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

   public void setAddress(String var1) {
      this.address = var1;
   }

   public void setClassName(String var1) {
      this.className = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setThreadId(Long var1) {
      this.threadId = var1;
   }

   public void setType(int var1) {
      this.type = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryLockReason> {
      public SentryLockReason deserialize(ObjectReader var1, ILogger var2) throws Exception {
         SentryLockReason var7 = new SentryLockReason();
         var1.beginObject();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1877165340:
                  if (var8.equals("package_name")) {
                     var3 = 0;
                  }
                  break;
               case -1562235024:
                  if (var8.equals("thread_id")) {
                     var3 = 1;
                  }
                  break;
               case -1147692044:
                  if (var8.equals("address")) {
                     var3 = 2;
                  }
                  break;
               case -290474766:
                  if (var8.equals("class_name")) {
                     var3 = 3;
                  }
                  break;
               case 3575610:
                  if (var8.equals("type")) {
                     var3 = 4;
                  }
            }

            switch (var3) {
               case 0:
                  var7.packageName = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.threadId = var1.nextLongOrNull();
                  break;
               case 2:
                  var7.address = var1.nextStringOrNull();
                  break;
               case 3:
                  var7.className = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.type = var1.nextInt();
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
      public static final String ADDRESS = "address";
      public static final String CLASS_NAME = "class_name";
      public static final String PACKAGE_NAME = "package_name";
      public static final String THREAD_ID = "thread_id";
      public static final String TYPE = "type";
   }
}
