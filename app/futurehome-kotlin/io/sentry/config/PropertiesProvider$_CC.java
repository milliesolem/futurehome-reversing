package io.sentry.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// $VF: synthetic class
public final class PropertiesProvider$_CC {
   public static Boolean $default$getBooleanProperty(PropertiesProvider var0, String var1) {
      String var2 = var0.getProperty(var1);
      Boolean var3;
      if (var2 != null) {
         var3 = Boolean.valueOf(var2);
      } else {
         var3 = null;
      }

      return var3;
   }

   public static Double $default$getDoubleProperty(PropertiesProvider var0, String var1) {
      String var3 = var0.getProperty(var1);
      if (var3 != null) {
         try {
            return Double.valueOf(var3);
         } catch (NumberFormatException var2) {
         }
      }

      return null;
   }

   public static List $default$getList(PropertiesProvider var0, String var1) {
      String var2 = var0.getProperty(var1);
      List var3;
      if (var2 != null) {
         var3 = Arrays.asList(var2.split(","));
      } else {
         var3 = Collections.emptyList();
      }

      return var3;
   }

   public static Long $default$getLongProperty(PropertiesProvider var0, String var1) {
      String var3 = var0.getProperty(var1);
      if (var3 != null) {
         try {
            return Long.valueOf(var3);
         } catch (NumberFormatException var2) {
         }
      }

      return null;
   }

   public static String $default$getProperty(PropertiesProvider var0, String var1, String var2) {
      String var3 = var0.getProperty(var1);
      if (var3 != null) {
         var2 = var3;
      }

      return var2;
   }
}
