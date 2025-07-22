package io.flutter.util;

import androidx.tracing.Trace;

public final class TraceSection implements AutoCloseable {
   private TraceSection(String var1) {
      begin(var1);
   }

   public static void begin(String var0) {
      Trace.beginSection(cropSectionName(var0));
   }

   public static void beginAsyncSection(String var0, int var1) {
      Trace.beginAsyncSection(cropSectionName(var0), var1);
   }

   private static String cropSectionName(String var0) {
      if (var0.length() >= 124) {
         StringBuilder var1 = new StringBuilder();
         var1.append(var0.substring(0, 124));
         var1.append("...");
         var0 = var1.toString();
      }

      return var0;
   }

   public static void end() throws RuntimeException {
      Trace.endSection();
   }

   public static void endAsyncSection(String var0, int var1) {
      Trace.endAsyncSection(cropSectionName(var0), var1);
   }

   public static TraceSection scoped(String var0) {
      return new TraceSection(var0);
   }

   @Override
   public void close() {
      end();
   }
}
