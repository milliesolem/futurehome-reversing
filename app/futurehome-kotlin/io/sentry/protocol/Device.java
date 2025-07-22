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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class Device implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "device";
   private String[] archs;
   private Float batteryLevel;
   private Float batteryTemperature;
   private Date bootTime;
   private String brand;
   private Boolean charging;
   private String connectionType;
   private String cpuDescription;
   private Long externalFreeStorage;
   private Long externalStorageSize;
   private String family;
   private Long freeMemory;
   private Long freeStorage;
   private String id;
   @Deprecated
   private String language;
   private String locale;
   private Boolean lowMemory;
   private String manufacturer;
   private Long memorySize;
   private String model;
   private String modelId;
   private String name;
   private Boolean online;
   private Device.DeviceOrientation orientation;
   private Integer processorCount;
   private Double processorFrequency;
   private Float screenDensity;
   private Integer screenDpi;
   private Integer screenHeightPixels;
   private Integer screenWidthPixels;
   private Boolean simulator;
   private Long storageSize;
   private TimeZone timezone;
   private Map<String, Object> unknown;
   private Long usableMemory;

   public Device() {
   }

   Device(Device var1) {
      this.name = var1.name;
      this.manufacturer = var1.manufacturer;
      this.brand = var1.brand;
      this.family = var1.family;
      this.model = var1.model;
      this.modelId = var1.modelId;
      this.charging = var1.charging;
      this.online = var1.online;
      this.orientation = var1.orientation;
      this.simulator = var1.simulator;
      this.memorySize = var1.memorySize;
      this.freeMemory = var1.freeMemory;
      this.usableMemory = var1.usableMemory;
      this.lowMemory = var1.lowMemory;
      this.storageSize = var1.storageSize;
      this.freeStorage = var1.freeStorage;
      this.externalStorageSize = var1.externalStorageSize;
      this.externalFreeStorage = var1.externalFreeStorage;
      this.screenWidthPixels = var1.screenWidthPixels;
      this.screenHeightPixels = var1.screenHeightPixels;
      this.screenDensity = var1.screenDensity;
      this.screenDpi = var1.screenDpi;
      this.bootTime = var1.bootTime;
      this.id = var1.id;
      this.language = var1.language;
      this.connectionType = var1.connectionType;
      this.batteryTemperature = var1.batteryTemperature;
      this.batteryLevel = var1.batteryLevel;
      String[] var2 = var1.archs;
      Object var3 = null;
      if (var2 != null) {
         var2 = (String[])var2.clone();
      } else {
         var2 = null;
      }

      this.archs = var2;
      this.locale = var1.locale;
      TimeZone var4 = var1.timezone;
      TimeZone var6 = (TimeZone)var3;
      if (var4 != null) {
         var6 = (TimeZone)var4.clone();
      }

      this.timezone = var6;
      this.processorCount = var1.processorCount;
      this.processorFrequency = var1.processorFrequency;
      this.cpuDescription = var1.cpuDescription;
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
            || !Objects.equals(this.manufacturer, var1.manufacturer)
            || !Objects.equals(this.brand, var1.brand)
            || !Objects.equals(this.family, var1.family)
            || !Objects.equals(this.model, var1.model)
            || !Objects.equals(this.modelId, var1.modelId)
            || !Arrays.equals((Object[])this.archs, (Object[])var1.archs)
            || !Objects.equals(this.batteryLevel, var1.batteryLevel)
            || !Objects.equals(this.charging, var1.charging)
            || !Objects.equals(this.online, var1.online)
            || this.orientation != var1.orientation
            || !Objects.equals(this.simulator, var1.simulator)
            || !Objects.equals(this.memorySize, var1.memorySize)
            || !Objects.equals(this.freeMemory, var1.freeMemory)
            || !Objects.equals(this.usableMemory, var1.usableMemory)
            || !Objects.equals(this.lowMemory, var1.lowMemory)
            || !Objects.equals(this.storageSize, var1.storageSize)
            || !Objects.equals(this.freeStorage, var1.freeStorage)
            || !Objects.equals(this.externalStorageSize, var1.externalStorageSize)
            || !Objects.equals(this.externalFreeStorage, var1.externalFreeStorage)
            || !Objects.equals(this.screenWidthPixels, var1.screenWidthPixels)
            || !Objects.equals(this.screenHeightPixels, var1.screenHeightPixels)
            || !Objects.equals(this.screenDensity, var1.screenDensity)
            || !Objects.equals(this.screenDpi, var1.screenDpi)
            || !Objects.equals(this.bootTime, var1.bootTime)
            || !Objects.equals(this.id, var1.id)
            || !Objects.equals(this.language, var1.language)
            || !Objects.equals(this.locale, var1.locale)
            || !Objects.equals(this.connectionType, var1.connectionType)
            || !Objects.equals(this.batteryTemperature, var1.batteryTemperature)
            || !Objects.equals(this.processorCount, var1.processorCount)
            || !Objects.equals(this.processorFrequency, var1.processorFrequency)
            || !Objects.equals(this.cpuDescription, var1.cpuDescription)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String[] getArchs() {
      return this.archs;
   }

   public Float getBatteryLevel() {
      return this.batteryLevel;
   }

   public Float getBatteryTemperature() {
      return this.batteryTemperature;
   }

   public Date getBootTime() {
      Date var1 = this.bootTime;
      if (var1 != null) {
         var1 = (Date)var1.clone();
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getBrand() {
      return this.brand;
   }

   public String getConnectionType() {
      return this.connectionType;
   }

   public String getCpuDescription() {
      return this.cpuDescription;
   }

   public Long getExternalFreeStorage() {
      return this.externalFreeStorage;
   }

   public Long getExternalStorageSize() {
      return this.externalStorageSize;
   }

   public String getFamily() {
      return this.family;
   }

   public Long getFreeMemory() {
      return this.freeMemory;
   }

   public Long getFreeStorage() {
      return this.freeStorage;
   }

   public String getId() {
      return this.id;
   }

   public String getLanguage() {
      return this.language;
   }

   public String getLocale() {
      return this.locale;
   }

   public String getManufacturer() {
      return this.manufacturer;
   }

   public Long getMemorySize() {
      return this.memorySize;
   }

   public String getModel() {
      return this.model;
   }

   public String getModelId() {
      return this.modelId;
   }

   public String getName() {
      return this.name;
   }

   public Device.DeviceOrientation getOrientation() {
      return this.orientation;
   }

   public Integer getProcessorCount() {
      return this.processorCount;
   }

   public Double getProcessorFrequency() {
      return this.processorFrequency;
   }

   public Float getScreenDensity() {
      return this.screenDensity;
   }

   public Integer getScreenDpi() {
      return this.screenDpi;
   }

   public Integer getScreenHeightPixels() {
      return this.screenHeightPixels;
   }

   public Integer getScreenWidthPixels() {
      return this.screenWidthPixels;
   }

   public Long getStorageSize() {
      return this.storageSize;
   }

   public TimeZone getTimezone() {
      return this.timezone;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public Long getUsableMemory() {
      return this.usableMemory;
   }

   @Override
   public int hashCode() {
      return Objects.hash(
               this.name,
               this.manufacturer,
               this.brand,
               this.family,
               this.model,
               this.modelId,
               this.batteryLevel,
               this.charging,
               this.online,
               this.orientation,
               this.simulator,
               this.memorySize,
               this.freeMemory,
               this.usableMemory,
               this.lowMemory,
               this.storageSize,
               this.freeStorage,
               this.externalStorageSize,
               this.externalFreeStorage,
               this.screenWidthPixels,
               this.screenHeightPixels,
               this.screenDensity,
               this.screenDpi,
               this.bootTime,
               this.timezone,
               this.id,
               this.language,
               this.locale,
               this.connectionType,
               this.batteryTemperature,
               this.processorCount,
               this.processorFrequency,
               this.cpuDescription
            )
            * 31
         + Arrays.hashCode((Object[])this.archs);
   }

   public Boolean isCharging() {
      return this.charging;
   }

   public Boolean isLowMemory() {
      return this.lowMemory;
   }

   public Boolean isOnline() {
      return this.online;
   }

   public Boolean isSimulator() {
      return this.simulator;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.name != null) {
         var1.name("name").value(this.name);
      }

      if (this.manufacturer != null) {
         var1.name("manufacturer").value(this.manufacturer);
      }

      if (this.brand != null) {
         var1.name("brand").value(this.brand);
      }

      if (this.family != null) {
         var1.name("family").value(this.family);
      }

      if (this.model != null) {
         var1.name("model").value(this.model);
      }

      if (this.modelId != null) {
         var1.name("model_id").value(this.modelId);
      }

      if (this.archs != null) {
         var1.name("archs").value(var2, this.archs);
      }

      if (this.batteryLevel != null) {
         var1.name("battery_level").value(this.batteryLevel);
      }

      if (this.charging != null) {
         var1.name("charging").value(this.charging);
      }

      if (this.online != null) {
         var1.name("online").value(this.online);
      }

      if (this.orientation != null) {
         var1.name("orientation").value(var2, this.orientation);
      }

      if (this.simulator != null) {
         var1.name("simulator").value(this.simulator);
      }

      if (this.memorySize != null) {
         var1.name("memory_size").value(this.memorySize);
      }

      if (this.freeMemory != null) {
         var1.name("free_memory").value(this.freeMemory);
      }

      if (this.usableMemory != null) {
         var1.name("usable_memory").value(this.usableMemory);
      }

      if (this.lowMemory != null) {
         var1.name("low_memory").value(this.lowMemory);
      }

      if (this.storageSize != null) {
         var1.name("storage_size").value(this.storageSize);
      }

      if (this.freeStorage != null) {
         var1.name("free_storage").value(this.freeStorage);
      }

      if (this.externalStorageSize != null) {
         var1.name("external_storage_size").value(this.externalStorageSize);
      }

      if (this.externalFreeStorage != null) {
         var1.name("external_free_storage").value(this.externalFreeStorage);
      }

      if (this.screenWidthPixels != null) {
         var1.name("screen_width_pixels").value(this.screenWidthPixels);
      }

      if (this.screenHeightPixels != null) {
         var1.name("screen_height_pixels").value(this.screenHeightPixels);
      }

      if (this.screenDensity != null) {
         var1.name("screen_density").value(this.screenDensity);
      }

      if (this.screenDpi != null) {
         var1.name("screen_dpi").value(this.screenDpi);
      }

      if (this.bootTime != null) {
         var1.name("boot_time").value(var2, this.bootTime);
      }

      if (this.timezone != null) {
         var1.name("timezone").value(var2, this.timezone);
      }

      if (this.id != null) {
         var1.name("id").value(this.id);
      }

      if (this.language != null) {
         var1.name("language").value(this.language);
      }

      if (this.connectionType != null) {
         var1.name("connection_type").value(this.connectionType);
      }

      if (this.batteryTemperature != null) {
         var1.name("battery_temperature").value(this.batteryTemperature);
      }

      if (this.locale != null) {
         var1.name("locale").value(this.locale);
      }

      if (this.processorCount != null) {
         var1.name("processor_count").value(this.processorCount);
      }

      if (this.processorFrequency != null) {
         var1.name("processor_frequency").value(this.processorFrequency);
      }

      if (this.cpuDescription != null) {
         var1.name("cpu_description").value(this.cpuDescription);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var5);
            var1.name(var5).value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setArchs(String[] var1) {
      this.archs = var1;
   }

   public void setBatteryLevel(Float var1) {
      this.batteryLevel = var1;
   }

   public void setBatteryTemperature(Float var1) {
      this.batteryTemperature = var1;
   }

   public void setBootTime(Date var1) {
      this.bootTime = var1;
   }

   public void setBrand(String var1) {
      this.brand = var1;
   }

   public void setCharging(Boolean var1) {
      this.charging = var1;
   }

   public void setConnectionType(String var1) {
      this.connectionType = var1;
   }

   public void setCpuDescription(String var1) {
      this.cpuDescription = var1;
   }

   public void setExternalFreeStorage(Long var1) {
      this.externalFreeStorage = var1;
   }

   public void setExternalStorageSize(Long var1) {
      this.externalStorageSize = var1;
   }

   public void setFamily(String var1) {
      this.family = var1;
   }

   public void setFreeMemory(Long var1) {
      this.freeMemory = var1;
   }

   public void setFreeStorage(Long var1) {
      this.freeStorage = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setLanguage(String var1) {
      this.language = var1;
   }

   public void setLocale(String var1) {
      this.locale = var1;
   }

   public void setLowMemory(Boolean var1) {
      this.lowMemory = var1;
   }

   public void setManufacturer(String var1) {
      this.manufacturer = var1;
   }

   public void setMemorySize(Long var1) {
      this.memorySize = var1;
   }

   public void setModel(String var1) {
      this.model = var1;
   }

   public void setModelId(String var1) {
      this.modelId = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setOnline(Boolean var1) {
      this.online = var1;
   }

   public void setOrientation(Device.DeviceOrientation var1) {
      this.orientation = var1;
   }

   public void setProcessorCount(Integer var1) {
      this.processorCount = var1;
   }

   public void setProcessorFrequency(Double var1) {
      this.processorFrequency = var1;
   }

   public void setScreenDensity(Float var1) {
      this.screenDensity = var1;
   }

   public void setScreenDpi(Integer var1) {
      this.screenDpi = var1;
   }

   public void setScreenHeightPixels(Integer var1) {
      this.screenHeightPixels = var1;
   }

   public void setScreenWidthPixels(Integer var1) {
      this.screenWidthPixels = var1;
   }

   public void setSimulator(Boolean var1) {
      this.simulator = var1;
   }

   public void setStorageSize(Long var1) {
      this.storageSize = var1;
   }

   public void setTimezone(TimeZone var1) {
      this.timezone = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setUsableMemory(Long var1) {
      this.usableMemory = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Device> {
      public Device deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Device var7 = new Device();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -2076227591:
                  if (var8.equals("timezone")) {
                     var3 = 0;
                  }
                  break;
               case -2012489734:
                  if (var8.equals("boot_time")) {
                     var3 = 1;
                  }
                  break;
               case -1981332476:
                  if (var8.equals("simulator")) {
                     var3 = 2;
                  }
                  break;
               case -1969347631:
                  if (var8.equals("manufacturer")) {
                     var3 = 3;
                  }
                  break;
               case -1613589672:
                  if (var8.equals("language")) {
                     var3 = 4;
                  }
                  break;
               case -1608004830:
                  if (var8.equals("processor_count")) {
                     var3 = 5;
                  }
                  break;
               case -1439500848:
                  if (var8.equals("orientation")) {
                     var3 = 6;
                  }
                  break;
               case -1410521534:
                  if (var8.equals("battery_temperature")) {
                     var3 = 7;
                  }
                  break;
               case -1281860764:
                  if (var8.equals("family")) {
                     var3 = 8;
                  }
                  break;
               case -1097462182:
                  if (var8.equals("locale")) {
                     var3 = 9;
                  }
                  break;
               case -1012222381:
                  if (var8.equals("online")) {
                     var3 = 10;
                  }
                  break;
               case -877252910:
                  if (var8.equals("battery_level")) {
                     var3 = 11;
                  }
                  break;
               case -619038223:
                  if (var8.equals("model_id")) {
                     var3 = 12;
                  }
                  break;
               case -568274923:
                  if (var8.equals("screen_density")) {
                     var3 = 13;
                  }
                  break;
               case -417046774:
                  if (var8.equals("screen_dpi")) {
                     var3 = 14;
                  }
                  break;
               case -136523212:
                  if (var8.equals("free_memory")) {
                     var3 = 15;
                  }
                  break;
               case 3355:
                  if (var8.equals("id")) {
                     var3 = 16;
                  }
                  break;
               case 3373707:
                  if (var8.equals("name")) {
                     var3 = 17;
                  }
                  break;
               case 59142220:
                  if (var8.equals("low_memory")) {
                     var3 = 18;
                  }
                  break;
               case 93076189:
                  if (var8.equals("archs")) {
                     var3 = 19;
                  }
                  break;
               case 93997959:
                  if (var8.equals("brand")) {
                     var3 = 20;
                  }
                  break;
               case 104069929:
                  if (var8.equals("model")) {
                     var3 = 21;
                  }
                  break;
               case 115746789:
                  if (var8.equals("cpu_description")) {
                     var3 = 22;
                  }
                  break;
               case 244497903:
                  if (var8.equals("processor_frequency")) {
                     var3 = 23;
                  }
                  break;
               case 731866107:
                  if (var8.equals("connection_type")) {
                     var3 = 24;
                  }
                  break;
               case 817830969:
                  if (var8.equals("screen_width_pixels")) {
                     var3 = 25;
                  }
                  break;
               case 823882553:
                  if (var8.equals("external_storage_size")) {
                     var3 = 26;
                  }
                  break;
               case 897428293:
                  if (var8.equals("storage_size")) {
                     var3 = 27;
                  }
                  break;
               case 1331465768:
                  if (var8.equals("usable_memory")) {
                     var3 = 28;
                  }
                  break;
               case 1418777727:
                  if (var8.equals("memory_size")) {
                     var3 = 29;
                  }
                  break;
               case 1436115569:
                  if (var8.equals("charging")) {
                     var3 = 30;
                  }
                  break;
               case 1450613660:
                  if (var8.equals("external_free_storage")) {
                     var3 = 31;
                  }
                  break;
               case 1524159400:
                  if (var8.equals("free_storage")) {
                     var3 = 32;
                  }
                  break;
               case 1556284978:
                  if (var8.equals("screen_height_pixels")) {
                     var3 = 33;
                  }
            }

            switch (var3) {
               case 0:
                  var7.timezone = var1.nextTimeZoneOrNull(var2);
                  break;
               case 1:
                  if (var1.peek() == JsonToken.STRING) {
                     var7.bootTime = var1.nextDateOrNull(var2);
                  }
                  break;
               case 2:
                  var7.simulator = var1.nextBooleanOrNull();
                  break;
               case 3:
                  var7.manufacturer = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.language = var1.nextStringOrNull();
                  break;
               case 5:
                  var7.processorCount = var1.nextIntegerOrNull();
                  break;
               case 6:
                  var7.orientation = var1.nextOrNull(var2, new Device.DeviceOrientation.Deserializer());
                  break;
               case 7:
                  var7.batteryTemperature = var1.nextFloatOrNull();
                  break;
               case 8:
                  var7.family = var1.nextStringOrNull();
                  break;
               case 9:
                  var7.locale = var1.nextStringOrNull();
                  break;
               case 10:
                  var7.online = var1.nextBooleanOrNull();
                  break;
               case 11:
                  var7.batteryLevel = var1.nextFloatOrNull();
                  break;
               case 12:
                  var7.modelId = var1.nextStringOrNull();
                  break;
               case 13:
                  var7.screenDensity = var1.nextFloatOrNull();
                  break;
               case 14:
                  var7.screenDpi = var1.nextIntegerOrNull();
                  break;
               case 15:
                  var7.freeMemory = var1.nextLongOrNull();
                  break;
               case 16:
                  var7.id = var1.nextStringOrNull();
                  break;
               case 17:
                  var7.name = var1.nextStringOrNull();
                  break;
               case 18:
                  var7.lowMemory = var1.nextBooleanOrNull();
                  break;
               case 19:
                  List var10 = (List)var1.nextObjectOrNull();
                  if (var10 != null) {
                     String[] var9 = new String[var10.size()];
                     var10.toArray(var9);
                     var7.archs = var9;
                  }
                  break;
               case 20:
                  var7.brand = var1.nextStringOrNull();
                  break;
               case 21:
                  var7.model = var1.nextStringOrNull();
                  break;
               case 22:
                  var7.cpuDescription = var1.nextStringOrNull();
                  break;
               case 23:
                  var7.processorFrequency = var1.nextDoubleOrNull();
                  break;
               case 24:
                  var7.connectionType = var1.nextStringOrNull();
                  break;
               case 25:
                  var7.screenWidthPixels = var1.nextIntegerOrNull();
                  break;
               case 26:
                  var7.externalStorageSize = var1.nextLongOrNull();
                  break;
               case 27:
                  var7.storageSize = var1.nextLongOrNull();
                  break;
               case 28:
                  var7.usableMemory = var1.nextLongOrNull();
                  break;
               case 29:
                  var7.memorySize = var1.nextLongOrNull();
                  break;
               case 30:
                  var7.charging = var1.nextBooleanOrNull();
                  break;
               case 31:
                  var7.externalFreeStorage = var1.nextLongOrNull();
                  break;
               case 32:
                  var7.freeStorage = var1.nextLongOrNull();
                  break;
               case 33:
                  var7.screenHeightPixels = var1.nextIntegerOrNull();
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

   public static enum DeviceOrientation implements JsonSerializable {
      LANDSCAPE,
      PORTRAIT;
      private static final Device.DeviceOrientation[] $VALUES = $values();

      @Override
      public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
         var1.value(this.toString().toLowerCase(Locale.ROOT));
      }

      public static final class Deserializer implements JsonDeserializer<Device.DeviceOrientation> {
         public Device.DeviceOrientation deserialize(ObjectReader var1, ILogger var2) throws Exception {
            return Device.DeviceOrientation.valueOf(var1.nextString().toUpperCase(Locale.ROOT));
         }
      }
   }

   public static final class JsonKeys {
      public static final String ARCHS = "archs";
      public static final String BATTERY_LEVEL = "battery_level";
      public static final String BATTERY_TEMPERATURE = "battery_temperature";
      public static final String BOOT_TIME = "boot_time";
      public static final String BRAND = "brand";
      public static final String CHARGING = "charging";
      public static final String CONNECTION_TYPE = "connection_type";
      public static final String CPU_DESCRIPTION = "cpu_description";
      public static final String EXTERNAL_FREE_STORAGE = "external_free_storage";
      public static final String EXTERNAL_STORAGE_SIZE = "external_storage_size";
      public static final String FAMILY = "family";
      public static final String FREE_MEMORY = "free_memory";
      public static final String FREE_STORAGE = "free_storage";
      public static final String ID = "id";
      public static final String LANGUAGE = "language";
      public static final String LOCALE = "locale";
      public static final String LOW_MEMORY = "low_memory";
      public static final String MANUFACTURER = "manufacturer";
      public static final String MEMORY_SIZE = "memory_size";
      public static final String MODEL = "model";
      public static final String MODEL_ID = "model_id";
      public static final String NAME = "name";
      public static final String ONLINE = "online";
      public static final String ORIENTATION = "orientation";
      public static final String PROCESSOR_COUNT = "processor_count";
      public static final String PROCESSOR_FREQUENCY = "processor_frequency";
      public static final String SCREEN_DENSITY = "screen_density";
      public static final String SCREEN_DPI = "screen_dpi";
      public static final String SCREEN_HEIGHT_PIXELS = "screen_height_pixels";
      public static final String SCREEN_WIDTH_PIXELS = "screen_width_pixels";
      public static final String SIMULATOR = "simulator";
      public static final String STORAGE_SIZE = "storage_size";
      public static final String TIMEZONE = "timezone";
      public static final String USABLE_MEMORY = "usable_memory";
   }
}
