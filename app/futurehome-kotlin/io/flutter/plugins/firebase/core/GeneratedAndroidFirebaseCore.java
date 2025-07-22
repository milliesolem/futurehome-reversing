package io.flutter.plugins.firebase.core;

import android.util.Log;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneratedAndroidFirebaseCore {
   protected static ArrayList<Object> wrapError(Throwable var0) {
      ArrayList var1 = new ArrayList(3);
      if (var0 instanceof GeneratedAndroidFirebaseCore.FlutterError) {
         GeneratedAndroidFirebaseCore.FlutterError var3 = (GeneratedAndroidFirebaseCore.FlutterError)var0;
         var1.add(var3.code);
         var1.add(var3.getMessage());
         var1.add(var3.details);
      } else {
         var1.add(var0.toString());
         var1.add(var0.getClass().getSimpleName());
         StringBuilder var2 = new StringBuilder("Cause: ");
         var2.append(var0.getCause());
         var2.append(", Stacktrace: ");
         var2.append(Log.getStackTraceString(var0));
         var1.add(var2.toString());
      }

      return var1;
   }

   public interface FirebaseAppHostApi {
      void delete(String var1, GeneratedAndroidFirebaseCore.Result<Void> var2);

      void setAutomaticDataCollectionEnabled(String var1, Boolean var2, GeneratedAndroidFirebaseCore.Result<Void> var3);

      void setAutomaticResourceManagementEnabled(String var1, Boolean var2, GeneratedAndroidFirebaseCore.Result<Void> var3);
   }

   public interface FirebaseCoreHostApi {
      void initializeApp(
         String var1,
         GeneratedAndroidFirebaseCore.PigeonFirebaseOptions var2,
         GeneratedAndroidFirebaseCore.Result<GeneratedAndroidFirebaseCore.PigeonInitializeResponse> var3
      );

      void initializeCore(GeneratedAndroidFirebaseCore.Result<List<GeneratedAndroidFirebaseCore.PigeonInitializeResponse>> var1);

      void optionsFromResource(GeneratedAndroidFirebaseCore.Result<GeneratedAndroidFirebaseCore.PigeonFirebaseOptions> var1);
   }

   private static class FirebaseCoreHostApiCodec extends StandardMessageCodec {
      public static final GeneratedAndroidFirebaseCore.FirebaseCoreHostApiCodec INSTANCE = new GeneratedAndroidFirebaseCore.FirebaseCoreHostApiCodec();

      @Override
      protected Object readValueOfType(byte var1, ByteBuffer var2) {
         if (var1 != -128) {
            return var1 != -127
               ? super.readValueOfType(var1, var2)
               : GeneratedAndroidFirebaseCore.PigeonInitializeResponse.fromList((ArrayList<Object>)this.readValue(var2));
         } else {
            return GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.fromList((ArrayList<Object>)this.readValue(var2));
         }
      }

      @Override
      protected void writeValue(ByteArrayOutputStream var1, Object var2) {
         if (var2 instanceof GeneratedAndroidFirebaseCore.PigeonFirebaseOptions) {
            var1.write(128);
            this.writeValue(var1, ((GeneratedAndroidFirebaseCore.PigeonFirebaseOptions)var2).toList());
         } else if (var2 instanceof GeneratedAndroidFirebaseCore.PigeonInitializeResponse) {
            var1.write(129);
            this.writeValue(var1, ((GeneratedAndroidFirebaseCore.PigeonInitializeResponse)var2).toList());
         } else {
            super.writeValue(var1, var2);
         }
      }
   }

   public static class FlutterError extends RuntimeException {
      public final String code;
      public final Object details;

      public FlutterError(String var1, String var2, Object var3) {
         super(var2);
         this.code = var1;
         this.details = var3;
      }
   }

   public static final class PigeonFirebaseOptions {
      private String androidClientId;
      private String apiKey;
      private String appGroupId;
      private String appId;
      private String authDomain;
      private String databaseURL;
      private String deepLinkURLScheme;
      private String iosBundleId;
      private String iosClientId;
      private String measurementId;
      private String messagingSenderId;
      private String projectId;
      private String storageBucket;
      private String trackingId;

      PigeonFirebaseOptions() {
      }

      static GeneratedAndroidFirebaseCore.PigeonFirebaseOptions fromList(ArrayList<Object> var0) {
         GeneratedAndroidFirebaseCore.PigeonFirebaseOptions var1 = new GeneratedAndroidFirebaseCore.PigeonFirebaseOptions();
         var1.setApiKey((String)var0.get(0));
         var1.setAppId((String)var0.get(1));
         var1.setMessagingSenderId((String)var0.get(2));
         var1.setProjectId((String)var0.get(3));
         var1.setAuthDomain((String)var0.get(4));
         var1.setDatabaseURL((String)var0.get(5));
         var1.setStorageBucket((String)var0.get(6));
         var1.setMeasurementId((String)var0.get(7));
         var1.setTrackingId((String)var0.get(8));
         var1.setDeepLinkURLScheme((String)var0.get(9));
         var1.setAndroidClientId((String)var0.get(10));
         var1.setIosClientId((String)var0.get(11));
         var1.setIosBundleId((String)var0.get(12));
         var1.setAppGroupId((String)var0.get(13));
         return var1;
      }

      public String getAndroidClientId() {
         return this.androidClientId;
      }

      public String getApiKey() {
         return this.apiKey;
      }

      public String getAppGroupId() {
         return this.appGroupId;
      }

      public String getAppId() {
         return this.appId;
      }

      public String getAuthDomain() {
         return this.authDomain;
      }

      public String getDatabaseURL() {
         return this.databaseURL;
      }

      public String getDeepLinkURLScheme() {
         return this.deepLinkURLScheme;
      }

      public String getIosBundleId() {
         return this.iosBundleId;
      }

      public String getIosClientId() {
         return this.iosClientId;
      }

      public String getMeasurementId() {
         return this.measurementId;
      }

      public String getMessagingSenderId() {
         return this.messagingSenderId;
      }

      public String getProjectId() {
         return this.projectId;
      }

      public String getStorageBucket() {
         return this.storageBucket;
      }

      public String getTrackingId() {
         return this.trackingId;
      }

      public void setAndroidClientId(String var1) {
         this.androidClientId = var1;
      }

      public void setApiKey(String var1) {
         if (var1 != null) {
            this.apiKey = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"apiKey\" is null.");
         }
      }

      public void setAppGroupId(String var1) {
         this.appGroupId = var1;
      }

      public void setAppId(String var1) {
         if (var1 != null) {
            this.appId = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"appId\" is null.");
         }
      }

      public void setAuthDomain(String var1) {
         this.authDomain = var1;
      }

      public void setDatabaseURL(String var1) {
         this.databaseURL = var1;
      }

      public void setDeepLinkURLScheme(String var1) {
         this.deepLinkURLScheme = var1;
      }

      public void setIosBundleId(String var1) {
         this.iosBundleId = var1;
      }

      public void setIosClientId(String var1) {
         this.iosClientId = var1;
      }

      public void setMeasurementId(String var1) {
         this.measurementId = var1;
      }

      public void setMessagingSenderId(String var1) {
         if (var1 != null) {
            this.messagingSenderId = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"messagingSenderId\" is null.");
         }
      }

      public void setProjectId(String var1) {
         if (var1 != null) {
            this.projectId = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"projectId\" is null.");
         }
      }

      public void setStorageBucket(String var1) {
         this.storageBucket = var1;
      }

      public void setTrackingId(String var1) {
         this.trackingId = var1;
      }

      public ArrayList<Object> toList() {
         ArrayList var1 = new ArrayList(14);
         var1.add(this.apiKey);
         var1.add(this.appId);
         var1.add(this.messagingSenderId);
         var1.add(this.projectId);
         var1.add(this.authDomain);
         var1.add(this.databaseURL);
         var1.add(this.storageBucket);
         var1.add(this.measurementId);
         var1.add(this.trackingId);
         var1.add(this.deepLinkURLScheme);
         var1.add(this.androidClientId);
         var1.add(this.iosClientId);
         var1.add(this.iosBundleId);
         var1.add(this.appGroupId);
         return var1;
      }

      public static final class Builder {
         private String androidClientId;
         private String apiKey;
         private String appGroupId;
         private String appId;
         private String authDomain;
         private String databaseURL;
         private String deepLinkURLScheme;
         private String iosBundleId;
         private String iosClientId;
         private String measurementId;
         private String messagingSenderId;
         private String projectId;
         private String storageBucket;
         private String trackingId;

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions build() {
            GeneratedAndroidFirebaseCore.PigeonFirebaseOptions var1 = new GeneratedAndroidFirebaseCore.PigeonFirebaseOptions();
            var1.setApiKey(this.apiKey);
            var1.setAppId(this.appId);
            var1.setMessagingSenderId(this.messagingSenderId);
            var1.setProjectId(this.projectId);
            var1.setAuthDomain(this.authDomain);
            var1.setDatabaseURL(this.databaseURL);
            var1.setStorageBucket(this.storageBucket);
            var1.setMeasurementId(this.measurementId);
            var1.setTrackingId(this.trackingId);
            var1.setDeepLinkURLScheme(this.deepLinkURLScheme);
            var1.setAndroidClientId(this.androidClientId);
            var1.setIosClientId(this.iosClientId);
            var1.setIosBundleId(this.iosBundleId);
            var1.setAppGroupId(this.appGroupId);
            return var1;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setAndroidClientId(String var1) {
            this.androidClientId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setApiKey(String var1) {
            this.apiKey = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setAppGroupId(String var1) {
            this.appGroupId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setAppId(String var1) {
            this.appId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setAuthDomain(String var1) {
            this.authDomain = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setDatabaseURL(String var1) {
            this.databaseURL = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setDeepLinkURLScheme(String var1) {
            this.deepLinkURLScheme = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setIosBundleId(String var1) {
            this.iosBundleId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setIosClientId(String var1) {
            this.iosClientId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setMeasurementId(String var1) {
            this.measurementId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setMessagingSenderId(String var1) {
            this.messagingSenderId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setProjectId(String var1) {
            this.projectId = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setStorageBucket(String var1) {
            this.storageBucket = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.Builder setTrackingId(String var1) {
            this.trackingId = var1;
            return this;
         }
      }
   }

   public static final class PigeonInitializeResponse {
      private Boolean isAutomaticDataCollectionEnabled;
      private String name;
      private GeneratedAndroidFirebaseCore.PigeonFirebaseOptions options;
      private Map<String, Object> pluginConstants;

      PigeonInitializeResponse() {
      }

      static GeneratedAndroidFirebaseCore.PigeonInitializeResponse fromList(ArrayList<Object> var0) {
         GeneratedAndroidFirebaseCore.PigeonInitializeResponse var2 = new GeneratedAndroidFirebaseCore.PigeonInitializeResponse();
         var2.setName((String)var0.get(0));
         Object var1 = var0.get(1);
         if (var1 == null) {
            var1 = null;
         } else {
            var1 = GeneratedAndroidFirebaseCore.PigeonFirebaseOptions.fromList((ArrayList<Object>)var1);
         }

         var2.setOptions((GeneratedAndroidFirebaseCore.PigeonFirebaseOptions)var1);
         var2.setIsAutomaticDataCollectionEnabled((Boolean)var0.get(2));
         var2.setPluginConstants((Map<String, Object>)var0.get(3));
         return var2;
      }

      public Boolean getIsAutomaticDataCollectionEnabled() {
         return this.isAutomaticDataCollectionEnabled;
      }

      public String getName() {
         return this.name;
      }

      public GeneratedAndroidFirebaseCore.PigeonFirebaseOptions getOptions() {
         return this.options;
      }

      public Map<String, Object> getPluginConstants() {
         return this.pluginConstants;
      }

      public void setIsAutomaticDataCollectionEnabled(Boolean var1) {
         this.isAutomaticDataCollectionEnabled = var1;
      }

      public void setName(String var1) {
         if (var1 != null) {
            this.name = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"name\" is null.");
         }
      }

      public void setOptions(GeneratedAndroidFirebaseCore.PigeonFirebaseOptions var1) {
         if (var1 != null) {
            this.options = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"options\" is null.");
         }
      }

      public void setPluginConstants(Map<String, Object> var1) {
         if (var1 != null) {
            this.pluginConstants = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"pluginConstants\" is null.");
         }
      }

      public ArrayList<Object> toList() {
         ArrayList var2 = new ArrayList(4);
         var2.add(this.name);
         GeneratedAndroidFirebaseCore.PigeonFirebaseOptions var1 = this.options;
         ArrayList var3;
         if (var1 == null) {
            var3 = null;
         } else {
            var3 = var1.toList();
         }

         var2.add(var3);
         var2.add(this.isAutomaticDataCollectionEnabled);
         var2.add(this.pluginConstants);
         return var2;
      }

      public static final class Builder {
         private Boolean isAutomaticDataCollectionEnabled;
         private String name;
         private GeneratedAndroidFirebaseCore.PigeonFirebaseOptions options;
         private Map<String, Object> pluginConstants;

         public GeneratedAndroidFirebaseCore.PigeonInitializeResponse build() {
            GeneratedAndroidFirebaseCore.PigeonInitializeResponse var1 = new GeneratedAndroidFirebaseCore.PigeonInitializeResponse();
            var1.setName(this.name);
            var1.setOptions(this.options);
            var1.setIsAutomaticDataCollectionEnabled(this.isAutomaticDataCollectionEnabled);
            var1.setPluginConstants(this.pluginConstants);
            return var1;
         }

         public GeneratedAndroidFirebaseCore.PigeonInitializeResponse.Builder setIsAutomaticDataCollectionEnabled(Boolean var1) {
            this.isAutomaticDataCollectionEnabled = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonInitializeResponse.Builder setName(String var1) {
            this.name = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonInitializeResponse.Builder setOptions(GeneratedAndroidFirebaseCore.PigeonFirebaseOptions var1) {
            this.options = var1;
            return this;
         }

         public GeneratedAndroidFirebaseCore.PigeonInitializeResponse.Builder setPluginConstants(Map<String, Object> var1) {
            this.pluginConstants = var1;
            return this;
         }
      }
   }

   public interface Result<T> {
      void error(Throwable var1);

      void success(T var1);
   }
}
