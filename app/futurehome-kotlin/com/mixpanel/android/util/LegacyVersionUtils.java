package com.mixpanel.android.util;

import java.io.File;

public class LegacyVersionUtils {
   private static final String DEFAULT_DIRECTORY_PREFIX = "MixpanelAPI.Images.";
   private static final String FILE_PREFIX = "MP_IMG_";

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static void removeLegacyResidualImageFiles(File var0) {
      label54: {
         File[] var3;
         try {
            if (!var0.isDirectory()) {
               break label54;
            }

            var3 = var0.listFiles();
         } catch (Exception var8) {
            return;
         }

         if (var3 != null) {
            int var2;
            try {
               var2 = var3.length;
            } catch (Exception var7) {
               return;
            }

            for (int var1 = 0; var1 < var2; var1++) {
               try {
                  removeLegacyResidualImageFiles(var3[var1]);
               } catch (Exception var6) {
                  return;
               }
            }
         }
      }

      try {
         if (!var0.getName().contains("MixpanelAPI.Images.") && !var0.getName().contains("MP_IMG_")) {
            return;
         }
      } catch (Exception var5) {
         return;
      }

      try {
         var0.delete();
      } catch (Exception var4) {
      }
   }
}
