package io.flutter.embedding.engine;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlutterShellArgs {
   public static final String ARG_CACHE_SKSL = "--cache-sksl";
   public static final String ARG_DART_FLAGS = "--dart-flags";
   public static final String ARG_DISABLE_IMPELLER = "--enable-impeller=false";
   public static final String ARG_DISABLE_SERVICE_AUTH_CODES = "--disable-service-auth-codes";
   public static final String ARG_DUMP_SHADER_SKP_ON_SHADER_COMPILATION = "--dump-skp-on-shader-compilation";
   public static final String ARG_ENABLE_DART_PROFILING = "--enable-dart-profiling";
   public static final String ARG_ENABLE_IMPELLER = "--enable-impeller=true";
   public static final String ARG_ENABLE_SOFTWARE_RENDERING = "--enable-software-rendering";
   public static final String ARG_ENABLE_VULKAN_VALIDATION = "--enable-vulkan-validation";
   public static final String ARG_ENDLESS_TRACE_BUFFER = "--endless-trace-buffer";
   public static final String ARG_KEY_CACHE_SKSL = "cache-sksl";
   public static final String ARG_KEY_DART_FLAGS = "dart-flags";
   public static final String ARG_KEY_DISABLE_SERVICE_AUTH_CODES = "disable-service-auth-codes";
   public static final String ARG_KEY_DUMP_SHADER_SKP_ON_SHADER_COMPILATION = "dump-skp-on-shader-compilation";
   public static final String ARG_KEY_ENABLE_DART_PROFILING = "enable-dart-profiling";
   public static final String ARG_KEY_ENABLE_SOFTWARE_RENDERING = "enable-software-rendering";
   public static final String ARG_KEY_ENABLE_VULKAN_VALIDATION = "enable-vulkan-validation";
   public static final String ARG_KEY_ENDLESS_TRACE_BUFFER = "endless-trace-buffer";
   public static final String ARG_KEY_OBSERVATORY_PORT = "observatory-port";
   public static final String ARG_KEY_PURGE_PERSISTENT_CACHE = "purge-persistent-cache";
   public static final String ARG_KEY_SKIA_DETERMINISTIC_RENDERING = "skia-deterministic-rendering";
   public static final String ARG_KEY_START_PAUSED = "start-paused";
   public static final String ARG_KEY_TOGGLE_IMPELLER = "enable-impeller";
   public static final String ARG_KEY_TRACE_SKIA = "trace-skia";
   public static final String ARG_KEY_TRACE_SKIA_ALLOWLIST = "trace-skia-allowlist";
   public static final String ARG_KEY_TRACE_STARTUP = "trace-startup";
   public static final String ARG_KEY_TRACE_SYSTRACE = "trace-systrace";
   public static final String ARG_KEY_TRACE_TO_FILE = "trace-to-file";
   public static final String ARG_KEY_USE_TEST_FONTS = "use-test-fonts";
   public static final String ARG_KEY_VERBOSE_LOGGING = "verbose-logging";
   public static final String ARG_KEY_VM_SERVICE_PORT = "vm-service-port";
   public static final String ARG_PURGE_PERSISTENT_CACHE = "--purge-persistent-cache";
   public static final String ARG_SKIA_DETERMINISTIC_RENDERING = "--skia-deterministic-rendering";
   public static final String ARG_START_PAUSED = "--start-paused";
   public static final String ARG_TRACE_SKIA = "--trace-skia";
   public static final String ARG_TRACE_SKIA_ALLOWLIST = "--trace-skia-allowlist=";
   public static final String ARG_TRACE_STARTUP = "--trace-startup";
   public static final String ARG_TRACE_SYSTRACE = "--trace-systrace";
   public static final String ARG_TRACE_TO_FILE = "--trace-to-file";
   public static final String ARG_USE_TEST_FONTS = "--use-test-fonts";
   public static final String ARG_VERBOSE_LOGGING = "--verbose-logging";
   public static final String ARG_VM_SERVICE_PORT = "--vm-service-port=";
   private Set<String> args;

   public FlutterShellArgs(List<String> var1) {
      this.args = new HashSet<>(var1);
   }

   public FlutterShellArgs(Set<String> var1) {
      this.args = new HashSet<>(var1);
   }

   public FlutterShellArgs(String[] var1) {
      this.args = new HashSet<>(Arrays.asList(var1));
   }

   public static FlutterShellArgs fromIntent(Intent var0) {
      ArrayList var2 = new ArrayList();
      if (var0.getBooleanExtra("trace-startup", false)) {
         var2.add("--trace-startup");
      }

      if (var0.getBooleanExtra("start-paused", false)) {
         var2.add("--start-paused");
      }

      int var1 = var0.getIntExtra("vm-service-port", 0);
      if (var1 > 0) {
         StringBuilder var3 = new StringBuilder("--vm-service-port=");
         var3.append(Integer.toString(var1));
         var2.add(var3.toString());
      } else {
         var1 = var0.getIntExtra("observatory-port", 0);
         if (var1 > 0) {
            StringBuilder var6 = new StringBuilder("--vm-service-port=");
            var6.append(Integer.toString(var1));
            var2.add(var6.toString());
         }
      }

      if (var0.getBooleanExtra("disable-service-auth-codes", false)) {
         var2.add("--disable-service-auth-codes");
      }

      if (var0.getBooleanExtra("endless-trace-buffer", false)) {
         var2.add("--endless-trace-buffer");
      }

      if (var0.getBooleanExtra("use-test-fonts", false)) {
         var2.add("--use-test-fonts");
      }

      if (var0.getBooleanExtra("enable-dart-profiling", false)) {
         var2.add("--enable-dart-profiling");
      }

      if (var0.getBooleanExtra("enable-software-rendering", false)) {
         var2.add("--enable-software-rendering");
      }

      if (var0.getBooleanExtra("skia-deterministic-rendering", false)) {
         var2.add("--skia-deterministic-rendering");
      }

      if (var0.getBooleanExtra("trace-skia", false)) {
         var2.add("--trace-skia");
      }

      String var7 = var0.getStringExtra("trace-skia-allowlist");
      if (var7 != null) {
         StringBuilder var4 = new StringBuilder("--trace-skia-allowlist=");
         var4.append(var7);
         var2.add(var4.toString());
      }

      if (var0.getBooleanExtra("trace-systrace", false)) {
         var2.add("--trace-systrace");
      }

      if (var0.hasExtra("trace-to-file")) {
         StringBuilder var8 = new StringBuilder("--trace-to-file=");
         var8.append(var0.getStringExtra("trace-to-file"));
         var2.add(var8.toString());
      }

      if (var0.hasExtra("enable-impeller")) {
         if (var0.getBooleanExtra("enable-impeller", false)) {
            var2.add("--enable-impeller=true");
         } else {
            var2.add("--enable-impeller=false");
         }
      }

      if (var0.getBooleanExtra("enable-vulkan-validation", false)) {
         var2.add("--enable-vulkan-validation");
      }

      if (var0.getBooleanExtra("dump-skp-on-shader-compilation", false)) {
         var2.add("--dump-skp-on-shader-compilation");
      }

      if (var0.getBooleanExtra("cache-sksl", false)) {
         var2.add("--cache-sksl");
      }

      if (var0.getBooleanExtra("purge-persistent-cache", false)) {
         var2.add("--purge-persistent-cache");
      }

      if (var0.getBooleanExtra("verbose-logging", false)) {
         var2.add("--verbose-logging");
      }

      if (var0.hasExtra("dart-flags")) {
         StringBuilder var9 = new StringBuilder("--dart-flags=");
         var9.append(var0.getStringExtra("dart-flags"));
         var2.add(var9.toString());
      }

      return new FlutterShellArgs(var2);
   }

   public void add(String var1) {
      this.args.add(var1);
   }

   public void remove(String var1) {
      this.args.remove(var1);
   }

   public String[] toArray() {
      String[] var1 = new String[this.args.size()];
      return this.args.toArray(var1);
   }
}
