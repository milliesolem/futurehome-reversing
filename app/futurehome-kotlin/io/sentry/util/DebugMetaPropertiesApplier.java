package io.sentry.util;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public final class DebugMetaPropertiesApplier {
   public static String DEBUG_META_PROPERTIES_FILENAME;

   private static void applyBundleIds(SentryOptions var0, List<Properties> var1) {
      if (var0.getBundleIds().isEmpty()) {
         Iterator var7 = var1.iterator();

         while (var7.hasNext()) {
            String var4 = ((Properties)var7.next()).getProperty("io.sentry.bundle-ids");
            ILogger var5 = var0.getLogger();
            SentryLevel var6 = SentryLevel.DEBUG;
            int var2 = 0;
            var5.log(var6, "Bundle IDs found: %s", var4);
            if (var4 != null) {
               String[] var8 = var4.split(",", -1);

               for (int var3 = var8.length; var2 < var3; var2++) {
                  var0.addBundleId(var8[var2]);
               }
            }
         }
      }
   }

   private static void applyProguardUuid(SentryOptions var0, List<Properties> var1) {
      if (var0.getProguardUuid() == null) {
         Iterator var2 = var1.iterator();

         while (var2.hasNext()) {
            String var3 = getProguardUuid((Properties)var2.next());
            if (var3 != null) {
               var0.getLogger().log(SentryLevel.DEBUG, "Proguard UUID found: %s", var3);
               var0.setProguardUuid(var3);
               break;
            }
         }
      }
   }

   public static void applyToOptions(SentryOptions var0, List<Properties> var1) {
      if (var1 != null) {
         applyBundleIds(var0, var1);
         applyProguardUuid(var0, var1);
      }
   }

   public static String getProguardUuid(Properties var0) {
      return var0.getProperty("io.sentry.ProguardUuids");
   }
}
