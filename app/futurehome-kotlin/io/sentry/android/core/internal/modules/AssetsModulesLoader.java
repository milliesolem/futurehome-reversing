package io.sentry.android.core.internal.modules;

import android.content.Context;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.android.core.ContextUtils;
import io.sentry.internal.modules.ModulesLoader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public final class AssetsModulesLoader extends ModulesLoader {
   private final Context context;

   public AssetsModulesLoader(Context var1, ILogger var2) {
      super(var2);
      this.context = ContextUtils.getApplicationContext(var1);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected Map<String, String> loadModules() {
      TreeMap var1 = new TreeMap();

      InputStream var3;
      try {
         var3 = this.context.getAssets().open("sentry-external-modules.txt");
      } catch (FileNotFoundException var32) {
         this.logger.log(SentryLevel.INFO, "%s file was not found.", "sentry-external-modules.txt");
         return var1;
      } catch (IOException var33) {
         this.logger.log(SentryLevel.ERROR, "Error extracting modules.", var33);
         return var1;
      }

      Map var34;
      try {
         var34 = this.parseStream(var3);
      } catch (Throwable var31) {
         Throwable var2 = var31;
         if (var3 != null) {
            try {
               var3.close();
            } catch (Throwable var28) {
               Throwable var35 = var28;

               label90:
               try {
                  var2.addSuppressed(var35);
                  break label90;
               } catch (FileNotFoundException var26) {
                  this.logger.log(SentryLevel.INFO, "%s file was not found.", "sentry-external-modules.txt");
                  return var1;
               } catch (IOException var27) {
                  this.logger.log(SentryLevel.ERROR, "Error extracting modules.", var27);
                  return var1;
               }
            }
         }

         try {
            throw var2;
         } catch (FileNotFoundException var24) {
            this.logger.log(SentryLevel.INFO, "%s file was not found.", "sentry-external-modules.txt");
            return var1;
         } catch (IOException var25) {
            this.logger.log(SentryLevel.ERROR, "Error extracting modules.", var25);
            return var1;
         }
      }

      if (var3 == null) {
         return var34;
      } else {
         try {
            var3.close();
            return var34;
         } catch (FileNotFoundException var29) {
            this.logger.log(SentryLevel.INFO, "%s file was not found.", "sentry-external-modules.txt");
         } catch (IOException var30) {
            this.logger.log(SentryLevel.ERROR, "Error extracting modules.", var30);
         }

         return var1;
      }
   }
}
