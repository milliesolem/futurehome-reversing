package io.sentry.protocol;

import java.util.Locale;

public enum TransactionNameSource {
   COMPONENT,
   CUSTOM,
   ROUTE,
   TASK,
   URL,
   VIEW;
   private static final TransactionNameSource[] $VALUES = $values();

   public String apiName() {
      return this.name().toLowerCase(Locale.ROOT);
   }
}
