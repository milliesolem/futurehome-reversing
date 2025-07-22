package io.sentry;

import java.util.List;

public final class BaggageHeader {
   public static final String BAGGAGE_HEADER = "baggage";
   private final String value;

   public BaggageHeader(String var1) {
      this.value = var1;
   }

   public static BaggageHeader fromBaggageAndOutgoingHeader(Baggage var0, List<String> var1) {
      String var2 = var0.toHeaderString(Baggage.fromHeader(var1, true, var0.logger).getThirdPartyHeader());
      return var2.isEmpty() ? null : new BaggageHeader(var2);
   }

   public String getName() {
      return "baggage";
   }

   public String getValue() {
      return this.value;
   }
}
