package io.sentry.internal.modules;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.util.ClassLoaderUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ManifestModulesLoader extends ModulesLoader {
   private final Pattern NAME_AND_VERSION;
   private final Pattern URL_LIB_PATTERN = Pattern.compile(".*/(.+)!/META-INF/MANIFEST.MF");
   private final ClassLoader classLoader;

   public ManifestModulesLoader(ILogger var1) {
      this(ManifestModulesLoader.class.getClassLoader(), var1);
   }

   ManifestModulesLoader(ClassLoader var1, ILogger var2) {
      super(var2);
      this.NAME_AND_VERSION = Pattern.compile("(.*?)-(\\d+\\.\\d+.*).jar");
      this.classLoader = ClassLoaderUtils.classLoaderOrDefault(var1);
   }

   private ManifestModulesLoader.Module convertOriginalNameToModule(String var1) {
      if (var1 == null) {
         return null;
      } else {
         Matcher var2 = this.NAME_AND_VERSION.matcher(var1);
         return var2.matches() && var2.groupCount() == 2 ? new ManifestModulesLoader.Module(var2.group(1), var2.group(2)) : null;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private List<ManifestModulesLoader.Module> detectModulesViaManifestFiles() {
      ArrayList var1 = new ArrayList();

      Enumeration var3;
      try {
         var3 = this.classLoader.getResources("META-INF/MANIFEST.MF");
      } catch (Throwable var15) {
         this.logger.log(SentryLevel.ERROR, "Unable to detect modules via manifest files.", var15);
         return var1;
      }

      while (true) {
         ManifestModulesLoader.Module var2;
         try {
            if (!var3.hasMoreElements()) {
               break;
            }

            var2 = this.convertOriginalNameToModule(this.extractDependencyNameFromUrl((URL)var3.nextElement()));
         } catch (Throwable var14) {
            this.logger.log(SentryLevel.ERROR, "Unable to detect modules via manifest files.", var14);
            break;
         }

         if (var2 != null) {
            try {
               var1.add(var2);
            } catch (Throwable var13) {
               this.logger.log(SentryLevel.ERROR, "Unable to detect modules via manifest files.", var13);
               break;
            }
         }
      }

      return var1;
   }

   private String extractDependencyNameFromUrl(URL var1) {
      String var2 = var1.toString();
      Matcher var3 = this.URL_LIB_PATTERN.matcher(var2);
      return var3.matches() && var3.groupCount() == 1 ? var3.group(1) : null;
   }

   @Override
   protected Map<String, String> loadModules() {
      HashMap var1 = new HashMap();

      for (ManifestModulesLoader.Module var2 : this.detectModulesViaManifestFiles()) {
         var1.put(var2.name, var2.version);
      }

      return var1;
   }

   private static final class Module {
      private final String name;
      private final String version;

      public Module(String var1, String var2) {
         this.name = var1;
         this.version = var2;
      }
   }
}
