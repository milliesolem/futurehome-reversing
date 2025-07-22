package io.sentry;

import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

public final class ShutdownHookIntegration implements Integration, Closeable {
   private final Runtime runtime;
   private Thread thread;

   public ShutdownHookIntegration() {
      this(Runtime.getRuntime());
   }

   public ShutdownHookIntegration(Runtime var1) {
      this.runtime = Objects.requireNonNull(var1, "Runtime is required");
   }

   private void handleShutdownInProgress(Runnable var1) {
      try {
         var1.run();
      } catch (IllegalStateException var3) {
         String var4 = var3.getMessage();
         if (var4 == null || !var4.equals("Shutdown in progress") && !var4.equals("VM already shutting down")) {
            throw var3;
         }
      }
   }

   @Override
   public void close() throws IOException {
      if (this.thread != null) {
         this.handleShutdownInProgress(new ShutdownHookIntegration$$ExternalSyntheticLambda2(this));
      }
   }

   Thread getHook() {
      return this.thread;
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      Objects.requireNonNull(var1, "Hub is required");
      Objects.requireNonNull(var2, "SentryOptions is required");
      if (var2.isEnableShutdownHook()) {
         this.thread = new Thread(new ShutdownHookIntegration$$ExternalSyntheticLambda0(var1, var2));
         this.handleShutdownInProgress(new ShutdownHookIntegration$$ExternalSyntheticLambda1(this, var2));
      } else {
         var2.getLogger().log(SentryLevel.INFO, "enableShutdownHook is disabled.");
      }
   }
}
