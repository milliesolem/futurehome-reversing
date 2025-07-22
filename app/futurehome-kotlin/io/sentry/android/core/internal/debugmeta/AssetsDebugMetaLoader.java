package io.sentry.android.core.internal.debugmeta;

import android.content.Context;
import android.content.res.AssetManager;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.android.core.ContextUtils;
import io.sentry.internal.debugmeta.IDebugMetaLoader;
import io.sentry.util.DebugMetaPropertiesApplier;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public final class AssetsDebugMetaLoader implements IDebugMetaLoader {
   private final Context context;
   private final ILogger logger;

   public AssetsDebugMetaLoader(Context var1, ILogger var2) {
      this.context = ContextUtils.getApplicationContext(var1);
      this.logger = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public List<Properties> loadDebugMeta() {
      AssetManager var2 = this.context.getAssets();

      BufferedInputStream var1;
      try {
         var1 = new BufferedInputStream(var2.open(DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME));
      } catch (FileNotFoundException var42) {
         this.logger.log(SentryLevel.INFO, var42, "%s file was not found.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
         return null;
      } catch (IOException var43) {
         this.logger.log(SentryLevel.ERROR, "Error getting Proguard UUIDs.", var43);
         return null;
      } catch (RuntimeException var44) {
         this.logger.log(SentryLevel.ERROR, var44, "%s file is malformed.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
         return null;
      }

      try {
         Properties var47 = new Properties();
         var47.load(var1);
         var48 = Collections.singletonList(var47);
      } catch (Throwable var41) {
         Throwable var46 = var41;

         try {
            var1.close();
         } catch (Throwable var40) {
            Throwable var45 = var40;

            label92:
            try {
               var46.addSuppressed(var45);
               break label92;
            } catch (FileNotFoundException var37) {
               this.logger.log(SentryLevel.INFO, var37, "%s file was not found.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
               return null;
            } catch (IOException var38) {
               this.logger.log(SentryLevel.ERROR, "Error getting Proguard UUIDs.", var38);
               return null;
            } catch (RuntimeException var39) {
               this.logger.log(SentryLevel.ERROR, var39, "%s file is malformed.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
               return null;
            }
         }

         try {
            throw var46;
         } catch (FileNotFoundException var31) {
            this.logger.log(SentryLevel.INFO, var31, "%s file was not found.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
            return null;
         } catch (IOException var32) {
            this.logger.log(SentryLevel.ERROR, "Error getting Proguard UUIDs.", var32);
            return null;
         } catch (RuntimeException var33) {
            this.logger.log(SentryLevel.ERROR, var33, "%s file is malformed.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
            return null;
         }
      }

      try {
         var1.close();
         return var48;
      } catch (FileNotFoundException var34) {
         this.logger.log(SentryLevel.INFO, var34, "%s file was not found.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
      } catch (IOException var35) {
         this.logger.log(SentryLevel.ERROR, "Error getting Proguard UUIDs.", var35);
      } catch (RuntimeException var36) {
         this.logger.log(SentryLevel.ERROR, var36, "%s file is malformed.", DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME);
      }

      return null;
   }
}
