package io.sentry;

import io.sentry.protocol.SentryRuntime;
import io.sentry.protocol.SentryTransaction;

final class SentryRuntimeEventProcessor implements EventProcessor {
   private final String javaVendor;
   private final String javaVersion;

   public SentryRuntimeEventProcessor() {
      this(System.getProperty("java.version"), System.getProperty("java.vendor"));
   }

   public SentryRuntimeEventProcessor(String var1, String var2) {
      this.javaVersion = var1;
      this.javaVendor = var2;
   }

   private <T extends SentryBaseEvent> T process(T var1) {
      if (var1.getContexts().getRuntime() == null) {
         var1.getContexts().setRuntime(new SentryRuntime());
      }

      SentryRuntime var2 = var1.getContexts().getRuntime();
      if (var2 != null && var2.getName() == null && var2.getVersion() == null) {
         var2.setName(this.javaVendor);
         var2.setVersion(this.javaVersion);
      }

      return (T)var1;
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      return this.process(var1);
   }

   @Override
   public SentryTransaction process(SentryTransaction var1, Hint var2) {
      return this.process(var1);
   }
}
