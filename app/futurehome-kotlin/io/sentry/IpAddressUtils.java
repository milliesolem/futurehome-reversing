package io.sentry;

import java.util.Arrays;
import java.util.List;

public final class IpAddressUtils {
   public static final String DEFAULT_IP_ADDRESS = "{{auto}}";
   private static final List<String> DEFAULT_IP_ADDRESS_VALID_VALUES = Arrays.asList("{{auto}}", "{{ auto }}");

   private IpAddressUtils() {
   }

   public static boolean isDefault(String var0) {
      boolean var1;
      if (var0 != null && DEFAULT_IP_ADDRESS_VALID_VALUES.contains(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
