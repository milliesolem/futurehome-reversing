package io.sentry;

import io.sentry.util.Objects;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public final class RequestDetails {
   private final Map<String, String> headers;
   private final URL url;

   public RequestDetails(String var1, Map<String, String> var2) {
      Objects.requireNonNull(var1, "url is required");
      Objects.requireNonNull(var2, "headers is required");

      try {
         this.url = URI.create(var1).toURL();
      } catch (MalformedURLException var3) {
         throw new IllegalArgumentException("Failed to compose the Sentry's server URL.", var3);
      }

      this.headers = var2;
   }

   public Map<String, String> getHeaders() {
      return this.headers;
   }

   public URL getUrl() {
      return this.url;
   }
}
