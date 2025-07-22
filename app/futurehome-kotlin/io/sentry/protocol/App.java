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
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class App implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "app";
   private String appBuild;
   private String appIdentifier;
   private String appName;
   private Date appStartTime;
   private String appVersion;
   private String buildType;
   private String deviceAppHash;
   private Boolean inForeground;
   private Map<String, String> permissions;
   private String startType;
   private Map<String, Object> unknown;
   private List<String> viewNames;

   public App() {
   }

   App(App var1) {
      this.appBuild = var1.appBuild;
      this.appIdentifier = var1.appIdentifier;
      this.appName = var1.appName;
      this.appStartTime = var1.appStartTime;
      this.appVersion = var1.appVersion;
      this.buildType = var1.buildType;
      this.deviceAppHash = var1.deviceAppHash;
      this.permissions = CollectionUtils.newConcurrentHashMap(var1.permissions);
      this.inForeground = var1.inForeground;
      this.viewNames = CollectionUtils.newArrayList(var1.viewNames);
      this.startType = var1.startType;
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.appIdentifier, var1.appIdentifier)
            || !Objects.equals(this.appStartTime, var1.appStartTime)
            || !Objects.equals(this.deviceAppHash, var1.deviceAppHash)
            || !Objects.equals(this.buildType, var1.buildType)
            || !Objects.equals(this.appName, var1.appName)
            || !Objects.equals(this.appVersion, var1.appVersion)
            || !Objects.equals(this.appBuild, var1.appBuild)
            || !Objects.equals(this.permissions, var1.permissions)
            || !Objects.equals(this.inForeground, var1.inForeground)
            || !Objects.equals(this.viewNames, var1.viewNames)
            || !Objects.equals(this.startType, var1.startType)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getAppBuild() {
      return this.appBuild;
   }

   public String getAppIdentifier() {
      return this.appIdentifier;
   }

   public String getAppName() {
      return this.appName;
   }

   public Date getAppStartTime() {
      Date var1 = this.appStartTime;
      if (var1 != null) {
         var1 = (Date)var1.clone();
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getAppVersion() {
      return this.appVersion;
   }

   public String getBuildType() {
      return this.buildType;
   }

   public String getDeviceAppHash() {
      return this.deviceAppHash;
   }

   public Boolean getInForeground() {
      return this.inForeground;
   }

   public Map<String, String> getPermissions() {
      return this.permissions;
   }

   public String getStartType() {
      return this.startType;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public List<String> getViewNames() {
      return this.viewNames;
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.appIdentifier,
         this.appStartTime,
         this.deviceAppHash,
         this.buildType,
         this.appName,
         this.appVersion,
         this.appBuild,
         this.permissions,
         this.inForeground,
         this.viewNames,
         this.startType
      );
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.appIdentifier != null) {
         var1.name("app_identifier").value(this.appIdentifier);
      }

      if (this.appStartTime != null) {
         var1.name("app_start_time").value(var2, this.appStartTime);
      }

      if (this.deviceAppHash != null) {
         var1.name("device_app_hash").value(this.deviceAppHash);
      }

      if (this.buildType != null) {
         var1.name("build_type").value(this.buildType);
      }

      if (this.appName != null) {
         var1.name("app_name").value(this.appName);
      }

      if (this.appVersion != null) {
         var1.name("app_version").value(this.appVersion);
      }

      if (this.appBuild != null) {
         var1.name("app_build").value(this.appBuild);
      }

      Map var3 = this.permissions;
      if (var3 != null && !var3.isEmpty()) {
         var1.name("permissions").value(var2, this.permissions);
      }

      if (this.inForeground != null) {
         var1.name("in_foreground").value(this.inForeground);
      }

      if (this.viewNames != null) {
         var1.name("view_names").value(var2, this.viewNames);
      }

      if (this.startType != null) {
         var1.name("start_type").value(this.startType);
      }

      var3 = this.unknown;
      if (var3 != null) {
         for (String var7 : var3.keySet()) {
            Object var5 = this.unknown.get(var7);
            var1.name(var7).value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setAppBuild(String var1) {
      this.appBuild = var1;
   }

   public void setAppIdentifier(String var1) {
      this.appIdentifier = var1;
   }

   public void setAppName(String var1) {
      this.appName = var1;
   }

   public void setAppStartTime(Date var1) {
      this.appStartTime = var1;
   }

   public void setAppVersion(String var1) {
      this.appVersion = var1;
   }

   public void setBuildType(String var1) {
      this.buildType = var1;
   }

   public void setDeviceAppHash(String var1) {
      this.deviceAppHash = var1;
   }

   public void setInForeground(Boolean var1) {
      this.inForeground = var1;
   }

   public void setPermissions(Map<String, String> var1) {
      this.permissions = var1;
   }

   public void setStartType(String var1) {
      this.startType = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setViewNames(List<String> var1) {
      this.viewNames = var1;
   }

   public static final class Deserializer implements JsonDeserializer<App> {
      public App deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         App var7 = new App();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1898053579:
                  if (var8.equals("device_app_hash")) {
                     var3 = 0;
                  }
                  break;
               case -1573129993:
                  if (var8.equals("start_type")) {
                     var3 = 1;
                  }
                  break;
               case -1524619986:
                  if (var8.equals("view_names")) {
                     var3 = 2;
                  }
                  break;
               case -901870406:
                  if (var8.equals("app_version")) {
                     var3 = 3;
                  }
                  break;
               case -650544995:
                  if (var8.equals("in_foreground")) {
                     var3 = 4;
                  }
                  break;
               case -470395285:
                  if (var8.equals("build_type")) {
                     var3 = 5;
                  }
                  break;
               case 746297735:
                  if (var8.equals("app_identifier")) {
                     var3 = 6;
                  }
                  break;
               case 791585128:
                  if (var8.equals("app_start_time")) {
                     var3 = 7;
                  }
                  break;
               case 1133704324:
                  if (var8.equals("permissions")) {
                     var3 = 8;
                  }
                  break;
               case 1167648233:
                  if (var8.equals("app_name")) {
                     var3 = 9;
                  }
                  break;
               case 1826866896:
                  if (var8.equals("app_build")) {
                     var3 = 10;
                  }
            }

            switch (var3) {
               case 0:
                  var7.deviceAppHash = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.startType = var1.nextStringOrNull();
                  break;
               case 2:
                  List var9 = (List)var1.nextObjectOrNull();
                  if (var9 != null) {
                     var7.setViewNames(var9);
                  }
                  break;
               case 3:
                  var7.appVersion = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.inForeground = var1.nextBooleanOrNull();
                  break;
               case 5:
                  var7.buildType = var1.nextStringOrNull();
                  break;
               case 6:
                  var7.appIdentifier = var1.nextStringOrNull();
                  break;
               case 7:
                  var7.appStartTime = var1.nextDateOrNull(var2);
                  break;
               case 8:
                  var7.permissions = CollectionUtils.newConcurrentHashMap((Map<String, String>)var1.nextObjectOrNull());
                  break;
               case 9:
                  var7.appName = var1.nextStringOrNull();
                  break;
               case 10:
                  var7.appBuild = var1.nextStringOrNull();
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
      public static final String APP_BUILD = "app_build";
      public static final String APP_IDENTIFIER = "app_identifier";
      public static final String APP_NAME = "app_name";
      public static final String APP_PERMISSIONS = "permissions";
      public static final String APP_START_TIME = "app_start_time";
      public static final String APP_VERSION = "app_version";
      public static final String BUILD_TYPE = "build_type";
      public static final String DEVICE_APP_HASH = "device_app_hash";
      public static final String IN_FOREGROUND = "in_foreground";
      public static final String START_TYPE = "start_type";
      public static final String VIEW_NAMES = "view_names";
   }
}
