package io.sentry.android.core;

public enum NdkHandlerStrategy {
   SENTRY_HANDLER_STRATEGY_CHAIN_AT_START(1),
   SENTRY_HANDLER_STRATEGY_DEFAULT(0);

   private static final NdkHandlerStrategy[] $VALUES = $values();
   private final int value;

   private NdkHandlerStrategy(int var3) {
      this.value = var3;
   }

   public int getValue() {
      return this.value;
   }
}
