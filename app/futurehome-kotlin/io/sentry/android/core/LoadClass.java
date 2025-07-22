package io.sentry.android.core;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;

public final class LoadClass {
   public boolean isClassAvailable(String var1, ILogger var2) {
      boolean var3;
      if (this.loadClass(var1, var2) != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isClassAvailable(String var1, SentryOptions var2) {
      ILogger var3;
      if (var2 != null) {
         var3 = var2.getLogger();
      } else {
         var3 = null;
      }

      return this.isClassAvailable(var1, var3);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public Class<?> loadClass(String var1, ILogger var2) {
      ClassNotFoundException var14;
      label53: {
         UnsatisfiedLinkError var15;
         try {
            try {
               return Class.forName(var1);
            } catch (ClassNotFoundException var9) {
               var14 = var9;
               break label53;
            } catch (UnsatisfiedLinkError var10) {
               var15 = var10;
            }
         } catch (Throwable var11) {
            if (var2 != null) {
               SentryLevel var3 = SentryLevel.ERROR;
               StringBuilder var5 = new StringBuilder("Failed to initialize ");
               var5.append(var1);
               var2.log(var3, var5.toString(), var11);
            }

            return null;
         }

         if (var2 != null) {
            SentryLevel var12 = SentryLevel.ERROR;
            StringBuilder var4 = new StringBuilder("Failed to load (UnsatisfiedLinkError) ");
            var4.append(var1);
            var2.log(var12, var4.toString(), var15);
         }

         return null;
      }

      if (var2 != null) {
         SentryLevel var13 = SentryLevel.DEBUG;
         StringBuilder var16 = new StringBuilder("Class not available:");
         var16.append(var1);
         var2.log(var13, var16.toString(), var14);
      }

      return null;
   }
}
