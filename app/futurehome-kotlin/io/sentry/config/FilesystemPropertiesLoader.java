package io.sentry.config;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

final class FilesystemPropertiesLoader implements PropertiesLoader {
   private final String filePath;
   private final ILogger logger;

   public FilesystemPropertiesLoader(String var1, ILogger var2) {
      this.filePath = var1;
      this.logger = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public Properties load() {
      BufferedInputStream var1;
      try {
         File var3 = new File(this.filePath);
         if (!var3.isFile() || !var3.canRead()) {
            return null;
         }

         FileInputStream var2 = new FileInputStream(var3);
         var1 = new BufferedInputStream(var2);
      } catch (IOException var21) {
         this.logger.log(SentryLevel.ERROR, var21, "Failed to load Sentry configuration from file: %s", this.filePath);
         return null;
      }

      Properties var24;
      try {
         var24 = new Properties();
         var24.load(var1);
      } catch (Throwable var20) {
         Throwable var23 = var20;

         try {
            var1.close();
         } catch (Throwable var19) {
            Throwable var22 = var19;

            label73:
            try {
               var23.addSuppressed(var22);
               break label73;
            } catch (IOException var17) {
               this.logger.log(SentryLevel.ERROR, var17, "Failed to load Sentry configuration from file: %s", this.filePath);
               return null;
            }
         }

         try {
            throw var23;
         } catch (IOException var16) {
            this.logger.log(SentryLevel.ERROR, var16, "Failed to load Sentry configuration from file: %s", this.filePath);
            return null;
         }
      }

      try {
         var1.close();
         return var24;
      } catch (IOException var18) {
         this.logger.log(SentryLevel.ERROR, var18, "Failed to load Sentry configuration from file: %s", this.filePath);
         return null;
      }
   }
}
