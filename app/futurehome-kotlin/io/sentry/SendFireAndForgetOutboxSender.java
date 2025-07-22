package io.sentry;

import io.sentry.util.Objects;

public final class SendFireAndForgetOutboxSender implements SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetFactory {
   private final SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetDirPath sendFireAndForgetDirPath;

   public SendFireAndForgetOutboxSender(SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForgetDirPath var1) {
      this.sendFireAndForgetDirPath = Objects.requireNonNull(var1, "SendFireAndForgetDirPath is required");
   }

   @Override
   public SendCachedEnvelopeFireAndForgetIntegration.SendFireAndForget create(IHub var1, SentryOptions var2) {
      Objects.requireNonNull(var1, "Hub is required");
      Objects.requireNonNull(var2, "SentryOptions is required");
      String var3 = this.sendFireAndForgetDirPath.getDirPath();
      if (var3 != null && this.hasValidPath(var3, var2.getLogger())) {
         return this.processDir(
            new OutboxSender(var1, var2.getEnvelopeReader(), var2.getSerializer(), var2.getLogger(), var2.getFlushTimeoutMillis(), var2.getMaxQueueSize()),
            var3,
            var2.getLogger()
         );
      } else {
         var2.getLogger().log(SentryLevel.ERROR, "No outbox dir path is defined in options.");
         return null;
      }
   }
}
