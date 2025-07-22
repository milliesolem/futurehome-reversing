package io.sentry;

import io.sentry.profilemeasurements.ProfileMeasurement;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public final class ProfilingTraceData implements JsonUnknown, JsonSerializable {
   private static final String DEFAULT_ENVIRONMENT = "production";
   public static final String TRUNCATION_REASON_BACKGROUNDED = "backgrounded";
   public static final String TRUNCATION_REASON_NORMAL = "normal";
   public static final String TRUNCATION_REASON_TIMEOUT = "timeout";
   private int androidApiLevel;
   private String buildId;
   private String cpuArchitecture;
   private List<Integer> deviceCpuFrequencies = new ArrayList<>();
   private final Callable<List<Integer>> deviceCpuFrequenciesReader;
   private boolean deviceIsEmulator;
   private String deviceLocale;
   private String deviceManufacturer;
   private String deviceModel;
   private String deviceOsBuildNumber;
   private String deviceOsName;
   private String deviceOsVersion;
   private String devicePhysicalMemoryBytes;
   private String durationNs;
   private String environment;
   private final Map<String, ProfileMeasurement> measurementsMap;
   private String platform;
   private String profileId;
   private String release;
   private String sampledProfile = null;
   private Date timestamp;
   private final File traceFile;
   private String traceId;
   private String transactionId;
   private String transactionName;
   private List<ProfilingTransactionData> transactions;
   private String truncationReason;
   private Map<String, Object> unknown;
   private String versionCode;

   private ProfilingTraceData() {
      this(new File("dummy"), NoOpTransaction.getInstance());
   }

   public ProfilingTraceData(File var1, ITransaction var2) {
      this(
         var1,
         DateUtils.getCurrentDateTime(),
         new ArrayList<>(),
         var2.getName(),
         var2.getEventId().toString(),
         var2.getSpanContext().getTraceId().toString(),
         "0",
         0,
         "",
         new ProfilingTraceData$$ExternalSyntheticLambda0(),
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         "normal",
         new HashMap<>()
      );
   }

   public ProfilingTraceData(
      File var1,
      Date var2,
      List<ProfilingTransactionData> var3,
      String var4,
      String var5,
      String var6,
      String var7,
      int var8,
      String var9,
      Callable<List<Integer>> var10,
      String var11,
      String var12,
      String var13,
      Boolean var14,
      String var15,
      String var16,
      String var17,
      String var18,
      String var19,
      Map<String, ProfileMeasurement> var20
   ) {
      this.traceFile = var1;
      this.timestamp = var2;
      this.cpuArchitecture = var9;
      this.deviceCpuFrequenciesReader = var10;
      this.androidApiLevel = var8;
      this.deviceLocale = Locale.getDefault().toString();
      String var26 = "";
      String var22;
      if (var11 != null) {
         var22 = var11;
      } else {
         var22 = "";
      }

      this.deviceManufacturer = var22;
      if (var12 == null) {
         var12 = "";
      }

      this.deviceModel = var12;
      if (var13 == null) {
         var13 = "";
      }

      this.deviceOsVersion = var13;
      boolean var21;
      if (var14 != null) {
         var21 = var14;
      } else {
         var21 = false;
      }

      this.deviceIsEmulator = var21;
      if (var15 == null) {
         var15 = "0";
      }

      this.devicePhysicalMemoryBytes = var15;
      this.deviceOsBuildNumber = "";
      this.deviceOsName = "android";
      this.platform = "android";
      if (var16 == null) {
         var16 = "";
      }

      this.buildId = var16;
      this.transactions = var3;
      String var23;
      if (var4.isEmpty()) {
         var23 = "unknown";
      } else {
         var23 = var4;
      }

      this.transactionName = var23;
      this.durationNs = var7;
      this.versionCode = "";
      String var24 = var26;
      if (var17 != null) {
         var24 = var17;
      }

      this.release = var24;
      this.transactionId = var5;
      this.traceId = var6;
      this.profileId = UUID.randomUUID().toString();
      String var25;
      if (var18 != null) {
         var25 = var18;
      } else {
         var25 = "production";
      }

      this.environment = var25;
      this.truncationReason = var19;
      if (!this.isTruncationReasonValid()) {
         this.truncationReason = "normal";
      }

      this.measurementsMap = var20;
   }

   private boolean isTruncationReasonValid() {
      boolean var1;
      if (!this.truncationReason.equals("normal") && !this.truncationReason.equals("timeout") && !this.truncationReason.equals("backgrounded")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public int getAndroidApiLevel() {
      return this.androidApiLevel;
   }

   public String getBuildId() {
      return this.buildId;
   }

   public String getCpuArchitecture() {
      return this.cpuArchitecture;
   }

   public List<Integer> getDeviceCpuFrequencies() {
      return this.deviceCpuFrequencies;
   }

   public String getDeviceLocale() {
      return this.deviceLocale;
   }

   public String getDeviceManufacturer() {
      return this.deviceManufacturer;
   }

   public String getDeviceModel() {
      return this.deviceModel;
   }

   public String getDeviceOsBuildNumber() {
      return this.deviceOsBuildNumber;
   }

   public String getDeviceOsName() {
      return this.deviceOsName;
   }

   public String getDeviceOsVersion() {
      return this.deviceOsVersion;
   }

   public String getDevicePhysicalMemoryBytes() {
      return this.devicePhysicalMemoryBytes;
   }

   public String getDurationNs() {
      return this.durationNs;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public Map<String, ProfileMeasurement> getMeasurementsMap() {
      return this.measurementsMap;
   }

   public String getPlatform() {
      return this.platform;
   }

   public String getProfileId() {
      return this.profileId;
   }

   public String getRelease() {
      return this.release;
   }

   public String getSampledProfile() {
      return this.sampledProfile;
   }

   public Date getTimestamp() {
      return this.timestamp;
   }

   public File getTraceFile() {
      return this.traceFile;
   }

   public String getTraceId() {
      return this.traceId;
   }

   public String getTransactionId() {
      return this.transactionId;
   }

   public String getTransactionName() {
      return this.transactionName;
   }

   public List<ProfilingTransactionData> getTransactions() {
      return this.transactions;
   }

   public String getTruncationReason() {
      return this.truncationReason;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public boolean isDeviceIsEmulator() {
      return this.deviceIsEmulator;
   }

   public void readDeviceCpuFrequencies() {
      try {
         this.deviceCpuFrequencies = this.deviceCpuFrequenciesReader.call();
      } finally {
         return;
      }
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("android_api_level").value(var2, this.androidApiLevel);
      var1.name("device_locale").value(var2, this.deviceLocale);
      var1.name("device_manufacturer").value(this.deviceManufacturer);
      var1.name("device_model").value(this.deviceModel);
      var1.name("device_os_build_number").value(this.deviceOsBuildNumber);
      var1.name("device_os_name").value(this.deviceOsName);
      var1.name("device_os_version").value(this.deviceOsVersion);
      var1.name("device_is_emulator").value(this.deviceIsEmulator);
      var1.name("architecture").value(var2, this.cpuArchitecture);
      var1.name("device_cpu_frequencies").value(var2, this.deviceCpuFrequencies);
      var1.name("device_physical_memory_bytes").value(this.devicePhysicalMemoryBytes);
      var1.name("platform").value(this.platform);
      var1.name("build_id").value(this.buildId);
      var1.name("transaction_name").value(this.transactionName);
      var1.name("duration_ns").value(this.durationNs);
      var1.name("version_name").value(this.release);
      var1.name("version_code").value(this.versionCode);
      if (!this.transactions.isEmpty()) {
         var1.name("transactions").value(var2, this.transactions);
      }

      var1.name("transaction_id").value(this.transactionId);
      var1.name("trace_id").value(this.traceId);
      var1.name("profile_id").value(this.profileId);
      var1.name("environment").value(this.environment);
      var1.name("truncation_reason").value(this.truncationReason);
      if (this.sampledProfile != null) {
         var1.name("sampled_profile").value(this.sampledProfile);
      }

      var1.name("measurements").value(var2, this.measurementsMap);
      var1.name("timestamp").value(var2, this.timestamp);
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

   public void setAndroidApiLevel(int var1) {
      this.androidApiLevel = var1;
   }

   public void setBuildId(String var1) {
      this.buildId = var1;
   }

   public void setCpuArchitecture(String var1) {
      this.cpuArchitecture = var1;
   }

   public void setDeviceCpuFrequencies(List<Integer> var1) {
      this.deviceCpuFrequencies = var1;
   }

   public void setDeviceIsEmulator(boolean var1) {
      this.deviceIsEmulator = var1;
   }

   public void setDeviceLocale(String var1) {
      this.deviceLocale = var1;
   }

   public void setDeviceManufacturer(String var1) {
      this.deviceManufacturer = var1;
   }

   public void setDeviceModel(String var1) {
      this.deviceModel = var1;
   }

   public void setDeviceOsBuildNumber(String var1) {
      this.deviceOsBuildNumber = var1;
   }

   public void setDeviceOsVersion(String var1) {
      this.deviceOsVersion = var1;
   }

   public void setDevicePhysicalMemoryBytes(String var1) {
      this.devicePhysicalMemoryBytes = var1;
   }

   public void setDurationNs(String var1) {
      this.durationNs = var1;
   }

   public void setEnvironment(String var1) {
      this.environment = var1;
   }

   public void setProfileId(String var1) {
      this.profileId = var1;
   }

   public void setRelease(String var1) {
      this.release = var1;
   }

   public void setSampledProfile(String var1) {
      this.sampledProfile = var1;
   }

   public void setTimestamp(Date var1) {
      this.timestamp = var1;
   }

   public void setTraceId(String var1) {
      this.traceId = var1;
   }

   public void setTransactionId(String var1) {
      this.transactionId = var1;
   }

   public void setTransactionName(String var1) {
      this.transactionName = var1;
   }

   public void setTransactions(List<ProfilingTransactionData> var1) {
      this.transactions = var1;
   }

   public void setTruncationReason(String var1) {
      this.truncationReason = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ProfilingTraceData> {
      public ProfilingTraceData deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         ConcurrentHashMap var5 = null;
         ProfilingTraceData var7 = new ProfilingTraceData();

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -2133529830:
                  if (var8.equals("device_manufacturer")) {
                     var3 = 0;
                  }
                  break;
               case -1981468849:
                  if (var8.equals("android_api_level")) {
                     var3 = 1;
                  }
                  break;
               case -1430655860:
                  if (var8.equals("build_id")) {
                     var3 = 2;
                  }
                  break;
               case -1172160413:
                  if (var8.equals("device_locale")) {
                     var3 = 3;
                  }
                  break;
               case -1102636175:
                  if (var8.equals("profile_id")) {
                     var3 = 4;
                  }
                  break;
               case -716656436:
                  if (var8.equals("device_os_build_number")) {
                     var3 = 5;
                  }
                  break;
               case -591076352:
                  if (var8.equals("device_model")) {
                     var3 = 6;
                  }
                  break;
               case -512511455:
                  if (var8.equals("device_is_emulator")) {
                     var3 = 7;
                  }
                  break;
               case -478065584:
                  if (var8.equals("duration_ns")) {
                     var3 = 8;
                  }
                  break;
               case -362243017:
                  if (var8.equals("measurements")) {
                     var3 = 9;
                  }
                  break;
               case -332426004:
                  if (var8.equals("device_physical_memory_bytes")) {
                     var3 = 10;
                  }
                  break;
               case -212264198:
                  if (var8.equals("device_cpu_frequencies")) {
                     var3 = 11;
                  }
                  break;
               case -102985484:
                  if (var8.equals("version_code")) {
                     var3 = 12;
                  }
                  break;
               case -102670958:
                  if (var8.equals("version_name")) {
                     var3 = 13;
                  }
                  break;
               case -85904877:
                  if (var8.equals("environment")) {
                     var3 = 14;
                  }
                  break;
               case 55126294:
                  if (var8.equals("timestamp")) {
                     var3 = 15;
                  }
                  break;
               case 508853068:
                  if (var8.equals("transaction_name")) {
                     var3 = 16;
                  }
                  break;
               case 796476189:
                  if (var8.equals("device_os_name")) {
                     var3 = 17;
                  }
                  break;
               case 839674195:
                  if (var8.equals("architecture")) {
                     var3 = 18;
                  }
                  break;
               case 1010584092:
                  if (var8.equals("transaction_id")) {
                     var3 = 19;
                  }
                  break;
               case 1052553990:
                  if (var8.equals("device_os_version")) {
                     var3 = 20;
                  }
                  break;
               case 1163928186:
                  if (var8.equals("truncation_reason")) {
                     var3 = 21;
                  }
                  break;
               case 1270300245:
                  if (var8.equals("trace_id")) {
                     var3 = 22;
                  }
                  break;
               case 1874684019:
                  if (var8.equals("platform")) {
                     var3 = 23;
                  }
                  break;
               case 1953158756:
                  if (var8.equals("sampled_profile")) {
                     var3 = 24;
                  }
                  break;
               case 1954122069:
                  if (var8.equals("transactions")) {
                     var3 = 25;
                  }
            }

            switch (var3) {
               case 0:
                  String var34 = var1.nextStringOrNull();
                  if (var34 != null) {
                     var7.deviceManufacturer = var34;
                  }
                  break;
               case 1:
                  Integer var33 = var1.nextIntegerOrNull();
                  if (var33 != null) {
                     var7.androidApiLevel = var33;
                  }
                  break;
               case 2:
                  String var32 = var1.nextStringOrNull();
                  if (var32 != null) {
                     var7.buildId = var32;
                  }
                  break;
               case 3:
                  String var31 = var1.nextStringOrNull();
                  if (var31 != null) {
                     var7.deviceLocale = var31;
                  }
                  break;
               case 4:
                  String var30 = var1.nextStringOrNull();
                  if (var30 != null) {
                     var7.profileId = var30;
                  }
                  break;
               case 5:
                  String var29 = var1.nextStringOrNull();
                  if (var29 != null) {
                     var7.deviceOsBuildNumber = var29;
                  }
                  break;
               case 6:
                  String var28 = var1.nextStringOrNull();
                  if (var28 != null) {
                     var7.deviceModel = var28;
                  }
                  break;
               case 7:
                  Boolean var27 = var1.nextBooleanOrNull();
                  if (var27 != null) {
                     var7.deviceIsEmulator = var27;
                  }
                  break;
               case 8:
                  String var26 = var1.nextStringOrNull();
                  if (var26 != null) {
                     var7.durationNs = var26;
                  }
                  break;
               case 9:
                  Map var25 = var1.nextMapOrNull(var2, new ProfileMeasurement.Deserializer());
                  if (var25 != null) {
                     var7.measurementsMap.putAll(var25);
                  }
                  break;
               case 10:
                  String var24 = var1.nextStringOrNull();
                  if (var24 != null) {
                     var7.devicePhysicalMemoryBytes = var24;
                  }
                  break;
               case 11:
                  List var23 = (List)var1.nextObjectOrNull();
                  if (var23 != null) {
                     var7.deviceCpuFrequencies = var23;
                  }
                  break;
               case 12:
                  String var22 = var1.nextStringOrNull();
                  if (var22 != null) {
                     var7.versionCode = var22;
                  }
                  break;
               case 13:
                  String var21 = var1.nextStringOrNull();
                  if (var21 != null) {
                     var7.release = var21;
                  }
                  break;
               case 14:
                  String var20 = var1.nextStringOrNull();
                  if (var20 != null) {
                     var7.environment = var20;
                  }
                  break;
               case 15:
                  Date var19 = var1.nextDateOrNull(var2);
                  if (var19 != null) {
                     var7.timestamp = var19;
                  }
                  break;
               case 16:
                  String var18 = var1.nextStringOrNull();
                  if (var18 != null) {
                     var7.transactionName = var18;
                  }
                  break;
               case 17:
                  String var17 = var1.nextStringOrNull();
                  if (var17 != null) {
                     var7.deviceOsName = var17;
                  }
                  break;
               case 18:
                  String var16 = var1.nextStringOrNull();
                  if (var16 != null) {
                     var7.cpuArchitecture = var16;
                  }
                  break;
               case 19:
                  String var15 = var1.nextStringOrNull();
                  if (var15 != null) {
                     var7.transactionId = var15;
                  }
                  break;
               case 20:
                  String var14 = var1.nextStringOrNull();
                  if (var14 != null) {
                     var7.deviceOsVersion = var14;
                  }
                  break;
               case 21:
                  String var13 = var1.nextStringOrNull();
                  if (var13 != null) {
                     var7.truncationReason = var13;
                  }
                  break;
               case 22:
                  String var12 = var1.nextStringOrNull();
                  if (var12 != null) {
                     var7.traceId = var12;
                  }
                  break;
               case 23:
                  String var11 = var1.nextStringOrNull();
                  if (var11 != null) {
                     var7.platform = var11;
                  }
                  break;
               case 24:
                  String var10 = var1.nextStringOrNull();
                  if (var10 != null) {
                     var7.sampledProfile = var10;
                  }
                  break;
               case 25:
                  List var9 = var1.nextListOrNull(var2, new ProfilingTransactionData.Deserializer());
                  if (var9 != null) {
                     var7.transactions.addAll(var9);
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
      public static final String ANDROID_API_LEVEL = "android_api_level";
      public static final String ARCHITECTURE = "architecture";
      public static final String BUILD_ID = "build_id";
      public static final String DEVICE_CPU_FREQUENCIES = "device_cpu_frequencies";
      public static final String DEVICE_IS_EMULATOR = "device_is_emulator";
      public static final String DEVICE_LOCALE = "device_locale";
      public static final String DEVICE_MANUFACTURER = "device_manufacturer";
      public static final String DEVICE_MODEL = "device_model";
      public static final String DEVICE_OS_BUILD_NUMBER = "device_os_build_number";
      public static final String DEVICE_OS_NAME = "device_os_name";
      public static final String DEVICE_OS_VERSION = "device_os_version";
      public static final String DEVICE_PHYSICAL_MEMORY_BYTES = "device_physical_memory_bytes";
      public static final String DURATION_NS = "duration_ns";
      public static final String ENVIRONMENT = "environment";
      public static final String MEASUREMENTS = "measurements";
      public static final String PLATFORM = "platform";
      public static final String PROFILE_ID = "profile_id";
      public static final String RELEASE = "version_name";
      public static final String SAMPLED_PROFILE = "sampled_profile";
      public static final String TIMESTAMP = "timestamp";
      public static final String TRACE_ID = "trace_id";
      public static final String TRANSACTION_ID = "transaction_id";
      public static final String TRANSACTION_LIST = "transactions";
      public static final String TRANSACTION_NAME = "transaction_name";
      public static final String TRUNCATION_REASON = "truncation_reason";
      public static final String VERSION_CODE = "version_code";
   }
}
