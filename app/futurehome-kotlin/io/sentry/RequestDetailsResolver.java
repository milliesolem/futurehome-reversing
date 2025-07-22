package io.sentry;

import io.sentry.util.Objects;
import java.net.URI;
import java.util.HashMap;

final class RequestDetailsResolver {
   private static final String SENTRY_AUTH = "X-Sentry-Auth";
   private static final String USER_AGENT = "User-Agent";
   private final SentryOptions options;

   public RequestDetailsResolver(SentryOptions var1) {
      this.options = Objects.requireNonNull(var1, "options is required");
   }

   RequestDetails resolve() {
      Dsn var3 = this.options.retrieveParsedDsn();
      URI var2 = var3.getSentryUri();
      StringBuilder var1 = new StringBuilder();
      var1.append(var2.getPath());
      var1.append("/envelope/");
      String var9 = var2.resolve(var1.toString()).toString();
      String var5 = var3.getPublicKey();
      String var4 = var3.getSecretKey();
      StringBuilder var10 = new StringBuilder("Sentry sentry_version=7,sentry_client=");
      var10.append(this.options.getSentryClientName());
      var10.append(",sentry_key=");
      var10.append(var5);
      String var6;
      if (var4 != null && var4.length() > 0) {
         var1 = new StringBuilder(",sentry_secret=");
         var1.append(var4);
         var6 = var1.toString();
      } else {
         var6 = "";
      }

      var10.append(var6);
      String var8 = var10.toString();
      var4 = this.options.getSentryClientName();
      HashMap var11 = new HashMap();
      var11.put("User-Agent", var4);
      var11.put("X-Sentry-Auth", var8);
      return new RequestDetails(var9, var11);
   }
}
