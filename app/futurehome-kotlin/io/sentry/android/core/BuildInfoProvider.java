package io.sentry.android.core;

import android.os.Build;
import android.os.Build.VERSION;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.util.Objects;

public final class BuildInfoProvider {
   final ILogger logger;

   public BuildInfoProvider(ILogger var1) {
      this.logger = Objects.requireNonNull(var1, "The ILogger object is required.");
   }

   public String getBuildTags() {
      return Build.TAGS;
   }

   public String getManufacturer() {
      return Build.MANUFACTURER;
   }

   public String getModel() {
      return Build.MODEL;
   }

   public int getSdkInfoVersion() {
      return VERSION.SDK_INT;
   }

   public String getVersionRelease() {
      return VERSION.RELEASE;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public Boolean isEmulator() {
      label85: {
         try {
            if ((!Build.BRAND.startsWith("generic") || !Build.DEVICE.startsWith("generic"))
               && !Build.FINGERPRINT.startsWith("generic")
               && !Build.FINGERPRINT.startsWith("unknown")
               && !Build.HARDWARE.contains("goldfish")
               && !Build.HARDWARE.contains("ranchu")
               && !Build.MODEL.contains("google_sdk")
               && !Build.MODEL.contains("Emulator")
               && !Build.MODEL.contains("Android SDK built for x86")
               && !Build.MANUFACTURER.contains("Genymotion")
               && !Build.PRODUCT.contains("sdk_google")
               && !Build.PRODUCT.contains("google_sdk")
               && !Build.PRODUCT.contains("sdk")
               && !Build.PRODUCT.contains("sdk_x86")
               && !Build.PRODUCT.contains("vbox86p")
               && !Build.PRODUCT.contains("emulator")
               && !Build.PRODUCT.contains("simulator")) {
               break label85;
            }
         } catch (Throwable var4) {
            this.logger.log(SentryLevel.ERROR, "Error checking whether application is running in an emulator.", var4);
            return null;
         }

         boolean var1 = true;
         return var1;
      }

      boolean var5 = false;
      return var5;
   }
}
