package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class Gpu implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "gpu";
   private String apiType;
   private Integer id;
   private Integer memorySize;
   private Boolean multiThreadedRendering;
   private String name;
   private String npotSupport;
   private Map<String, Object> unknown;
   private String vendorId;
   private String vendorName;
   private String version;

   public Gpu() {
   }

   Gpu(Gpu var1) {
      this.name = var1.name;
      this.id = var1.id;
      this.vendorId = var1.vendorId;
      this.vendorName = var1.vendorName;
      this.memorySize = var1.memorySize;
      this.apiType = var1.apiType;
      this.multiThreadedRendering = var1.multiThreadedRendering;
      this.version = var1.version;
      this.npotSupport = var1.npotSupport;
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.name, var1.name)
            || !Objects.equals(this.id, var1.id)
            || !Objects.equals(this.vendorId, var1.vendorId)
            || !Objects.equals(this.vendorName, var1.vendorName)
            || !Objects.equals(this.memorySize, var1.memorySize)
            || !Objects.equals(this.apiType, var1.apiType)
            || !Objects.equals(this.multiThreadedRendering, var1.multiThreadedRendering)
            || !Objects.equals(this.version, var1.version)
            || !Objects.equals(this.npotSupport, var1.npotSupport)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getApiType() {
      return this.apiType;
   }

   public Integer getId() {
      return this.id;
   }

   public Integer getMemorySize() {
      return this.memorySize;
   }

   public String getName() {
      return this.name;
   }

   public String getNpotSupport() {
      return this.npotSupport;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getVendorId() {
      return this.vendorId;
   }

   public String getVendorName() {
      return this.vendorName;
   }

   public String getVersion() {
      return this.version;
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.name, this.id, this.vendorId, this.vendorName, this.memorySize, this.apiType, this.multiThreadedRendering, this.version, this.npotSupport
      );
   }

   public Boolean isMultiThreadedRendering() {
      return this.multiThreadedRendering;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.name != null) {
         var1.name("name").value(this.name);
      }

      if (this.id != null) {
         var1.name("id").value(this.id);
      }

      if (this.vendorId != null) {
         var1.name("vendor_id").value(this.vendorId);
      }

      if (this.vendorName != null) {
         var1.name("vendor_name").value(this.vendorName);
      }

      if (this.memorySize != null) {
         var1.name("memory_size").value(this.memorySize);
      }

      if (this.apiType != null) {
         var1.name("api_type").value(this.apiType);
      }

      if (this.multiThreadedRendering != null) {
         var1.name("multi_threaded_rendering").value(this.multiThreadedRendering);
      }

      if (this.version != null) {
         var1.name("version").value(this.version);
      }

      if (this.npotSupport != null) {
         var1.name("npot_support").value(this.npotSupport);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setApiType(String var1) {
      this.apiType = var1;
   }

   public void setId(Integer var1) {
      this.id = var1;
   }

   public void setMemorySize(Integer var1) {
      this.memorySize = var1;
   }

   public void setMultiThreadedRendering(Boolean var1) {
      this.multiThreadedRendering = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setNpotSupport(String var1) {
      this.npotSupport = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVendorId(String var1) {
      this.vendorId = var1;
   }

   public void setVendorName(String var1) {
      this.vendorName = var1;
   }

   public void setVersion(String var1) {
      this.version = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Gpu> {
      public Gpu deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Gpu var7 = new Gpu();
         ConcurrentHashMap var6 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1421884745:
                  if (var8.equals("npot_support")) {
                     var3 = 0;
                  }
                  break;
               case -1085970574:
                  if (var8.equals("vendor_id")) {
                     var3 = 1;
                  }
                  break;
               case -1009234244:
                  if (var8.equals("multi_threaded_rendering")) {
                     var3 = 2;
                  }
                  break;
               case 3355:
                  if (var8.equals("id")) {
                     var3 = 3;
                  }
                  break;
               case 3373707:
                  if (var8.equals("name")) {
                     var3 = 4;
                  }
                  break;
               case 59480866:
                  if (var8.equals("vendor_name")) {
                     var3 = 5;
                  }
                  break;
               case 351608024:
                  if (var8.equals("version")) {
                     var3 = 6;
                  }
                  break;
               case 967446079:
                  if (var8.equals("api_type")) {
                     var3 = 7;
                  }
                  break;
               case 1418777727:
                  if (var8.equals("memory_size")) {
                     var3 = 8;
                  }
            }

            switch (var3) {
               case 0:
                  var7.npotSupport = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.vendorId = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.multiThreadedRendering = var1.nextBooleanOrNull();
                  break;
               case 3:
                  var7.id = var1.nextIntegerOrNull();
                  break;
               case 4:
                  var7.name = var1.nextStringOrNull();
                  break;
               case 5:
                  var7.vendorName = var1.nextStringOrNull();
                  break;
               case 6:
                  var7.version = var1.nextStringOrNull();
                  break;
               case 7:
                  var7.apiType = var1.nextStringOrNull();
                  break;
               case 8:
                  var7.memorySize = var1.nextIntegerOrNull();
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
      public static final String API_TYPE = "api_type";
      public static final String ID = "id";
      public static final String MEMORY_SIZE = "memory_size";
      public static final String MULTI_THREADED_RENDERING = "multi_threaded_rendering";
      public static final String NAME = "name";
      public static final String NPOT_SUPPORT = "npot_support";
      public static final String VENDOR_ID = "vendor_id";
      public static final String VENDOR_NAME = "vendor_name";
      public static final String VERSION = "version";
   }
}
