package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryLevel;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public final class SdkVersion implements JsonUnknown, JsonSerializable {
   private Set<String> deserializedIntegrations;
   private Set<SentryPackage> deserializedPackages;
   private String name;
   private Map<String, Object> unknown;
   private String version;

   public SdkVersion(String var1, String var2) {
      this.name = Objects.requireNonNull(var1, "name is required.");
      this.version = Objects.requireNonNull(var2, "version is required.");
   }

   public static SdkVersion updateSdkVersion(SdkVersion var0, String var1, String var2) {
      Objects.requireNonNull(var1, "name is required.");
      Objects.requireNonNull(var2, "version is required.");
      if (var0 == null) {
         var0 = new SdkVersion(var1, var2);
      } else {
         var0.setName(var1);
         var0.setVersion(var2);
      }

      return var0;
   }

   public void addIntegration(String var1) {
      SentryIntegrationPackageStorage.getInstance().addIntegration(var1);
   }

   public void addPackage(String var1, String var2) {
      SentryIntegrationPackageStorage.getInstance().addPackage(var1, var2);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!this.name.equals(var1.name) || !this.version.equals(var1.version)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public Set<String> getIntegrationSet() {
      Set var1 = this.deserializedIntegrations;
      if (var1 == null) {
         var1 = SentryIntegrationPackageStorage.getInstance().getIntegrations();
      }

      return var1;
   }

   @Deprecated
   public List<String> getIntegrations() {
      Set var1 = this.deserializedIntegrations;
      if (var1 == null) {
         var1 = SentryIntegrationPackageStorage.getInstance().getIntegrations();
      }

      return new CopyOnWriteArrayList<>(var1);
   }

   public String getName() {
      return this.name;
   }

   public Set<SentryPackage> getPackageSet() {
      Set var1 = this.deserializedPackages;
      if (var1 == null) {
         var1 = SentryIntegrationPackageStorage.getInstance().getPackages();
      }

      return var1;
   }

   @Deprecated
   public List<SentryPackage> getPackages() {
      Set var1 = this.deserializedPackages;
      if (var1 == null) {
         var1 = SentryIntegrationPackageStorage.getInstance().getPackages();
      }

      return new CopyOnWriteArrayList<>(var1);
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getVersion() {
      return this.version;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.name, this.version);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("name").value(this.name);
      var1.name("version").value(this.version);
      Set var4 = this.getPackageSet();
      Set var3 = this.getIntegrationSet();
      if (!var4.isEmpty()) {
         var1.name("packages").value(var2, var4);
      }

      if (!var3.isEmpty()) {
         var1.name("integrations").value(var2, var3);
      }

      Map var6 = this.unknown;
      if (var6 != null) {
         for (String var7 : var6.keySet()) {
            var4 = (Set)this.unknown.get(var7);
            var1.name(var7).value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setName(String var1) {
      this.name = Objects.requireNonNull(var1, "name is required.");
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setVersion(String var1) {
      this.version = Objects.requireNonNull(var1, "version is required.");
   }

   public static final class Deserializer implements JsonDeserializer<SdkVersion> {
      public SdkVersion deserialize(ObjectReader var1, ILogger var2) throws Exception {
         ArrayList var10 = new ArrayList();
         ArrayList var9 = new ArrayList();
         var1.beginObject();
         String var7 = null;
         String var6 = null;
         HashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var11 = var1.nextName();
            var11.hashCode();
            int var4 = var11.hashCode();
            byte var3 = -1;
            switch (var4) {
               case 3373707:
                  if (var11.equals("name")) {
                     var3 = 0;
                  }
                  break;
               case 351608024:
                  if (var11.equals("version")) {
                     var3 = 1;
                  }
                  break;
               case 750867693:
                  if (var11.equals("packages")) {
                     var3 = 2;
                  }
                  break;
               case 1487029535:
                  if (var11.equals("integrations")) {
                     var3 = 3;
                  }
            }

            switch (var3) {
               case 0:
                  var7 = var1.nextString();
                  break;
               case 1:
                  var6 = var1.nextString();
                  break;
               case 2:
                  List var16 = var1.nextListOrNull(var2, new SentryPackage.Deserializer());
                  if (var16 != null) {
                     var10.addAll(var16);
                  }
                  break;
               case 3:
                  List var15 = (List)var1.nextObjectOrNull();
                  if (var15 != null) {
                     var9.addAll(var15);
                  }
                  break;
               default:
                  HashMap var8 = var5;
                  if (var5 == null) {
                     var8 = new HashMap();
                  }

                  var1.nextUnknown(var2, var8, var11);
                  var5 = var8;
            }
         }

         var1.endObject();
         if (var7 == null) {
            IllegalStateException var12 = new IllegalStateException("Missing required field \"name\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"name\"", var12);
            throw var12;
         } else if (var6 != null) {
            SdkVersion var14 = new SdkVersion(var7, var6);
            var14.deserializedPackages = new CopyOnWriteArraySet<>(var10);
            var14.deserializedIntegrations = new CopyOnWriteArraySet<>(var9);
            var14.setUnknown(var5);
            return var14;
         } else {
            IllegalStateException var13 = new IllegalStateException("Missing required field \"version\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"version\"", var13);
            throw var13;
         }
      }
   }

   public static final class JsonKeys {
      public static final String INTEGRATIONS = "integrations";
      public static final String NAME = "name";
      public static final String PACKAGES = "packages";
      public static final String VERSION = "version";
   }
}
