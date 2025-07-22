package io.sentry;

import io.sentry.util.Objects;

public final class SamplingContext {
   private final CustomSamplingContext customSamplingContext;
   private final TransactionContext transactionContext;

   public SamplingContext(TransactionContext var1, CustomSamplingContext var2) {
      this.transactionContext = Objects.requireNonNull(var1, "transactionContexts is required");
      this.customSamplingContext = var2;
   }

   public CustomSamplingContext getCustomSamplingContext() {
      return this.customSamplingContext;
   }

   public TransactionContext getTransactionContext() {
      return this.transactionContext;
   }
}
