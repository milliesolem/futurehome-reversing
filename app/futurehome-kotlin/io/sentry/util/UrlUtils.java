package io.sentry.util;

import io.sentry.ISpan;
import io.sentry.protocol.Request;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UrlUtils {
   private static final Pattern AUTH_REGEX = Pattern.compile("(.+://)(.*@)(.*)");
   public static final String SENSITIVE_DATA_SUBSTITUTE = "[Filtered]";

   private static String baseUrlOnly(String var0) {
      int var1 = var0.indexOf("?");
      if (var1 >= 0) {
         return var0.substring(0, var1).trim();
      } else {
         var1 = var0.indexOf("#");
         String var2 = var0;
         if (var1 >= 0) {
            var2 = var0.substring(0, var1).trim();
         }

         return var2;
      }
   }

   private static String extractBaseUrl(String var0, int var1, int var2) {
      if (var1 >= 0) {
         return var0.substring(0, var1).trim();
      } else {
         String var3 = var0;
         if (var2 >= 0) {
            var3 = var0.substring(0, var2).trim();
         }

         return var3;
      }
   }

   private static String extractFragment(String var0, int var1) {
      return var1 > 0 ? var0.substring(var1 + 1).trim() : null;
   }

   private static String extractQuery(String var0, int var1, int var2) {
      if (var1 > 0) {
         return var2 > 0 && var2 > var1 ? var0.substring(var1 + 1, var2).trim() : var0.substring(var1 + 1).trim();
      } else {
         return null;
      }
   }

   private static boolean isAbsoluteUrl(String var0) {
      return var0.contains("://");
   }

   public static UrlUtils.UrlDetails parse(String var0) {
      return isAbsoluteUrl(var0) ? splitAbsoluteUrl(var0) : splitRelativeUrl(var0);
   }

   public static UrlUtils.UrlDetails parseNullable(String var0) {
      return var0 == null ? null : parse(var0);
   }

   private static UrlUtils.UrlDetails splitAbsoluteUrl(String var0) {
      try {
         String var2 = urlWithAuthRemoved(var0);
         URL var1 = new URL(var0);
         var0 = baseUrlOnly(var2);
         return var0.contains("#") ? new UrlUtils.UrlDetails(null, null, null) : new UrlUtils.UrlDetails(var0, var1.getQuery(), var1.getRef());
      } catch (MalformedURLException var3) {
         return new UrlUtils.UrlDetails(null, null, null);
      }
   }

   private static UrlUtils.UrlDetails splitRelativeUrl(String var0) {
      int var2 = var0.indexOf("?");
      int var1 = var0.indexOf("#");
      return new UrlUtils.UrlDetails(extractBaseUrl(var0, var2, var1), extractQuery(var0, var2, var1), extractFragment(var0, var1));
   }

   private static String urlWithAuthRemoved(String var0) {
      Matcher var2 = AUTH_REGEX.matcher(var0);
      String var1 = var0;
      if (var2.matches()) {
         var1 = var0;
         if (var2.groupCount() == 3) {
            if (var2.group(2).contains(":")) {
               var0 = "[Filtered]:[Filtered]@";
            } else {
               var0 = "[Filtered]@";
            }

            StringBuilder var4 = new StringBuilder();
            var4.append(var2.group(1));
            var4.append(var0);
            var4.append(var2.group(3));
            var1 = var4.toString();
         }
      }

      return var1;
   }

   public static final class UrlDetails {
      private final String fragment;
      private final String query;
      private final String url;

      public UrlDetails(String var1, String var2, String var3) {
         this.url = var1;
         this.query = var2;
         this.fragment = var3;
      }

      public void applyToRequest(Request var1) {
         if (var1 != null) {
            var1.setUrl(this.url);
            var1.setQueryString(this.query);
            var1.setFragment(this.fragment);
         }
      }

      public void applyToSpan(ISpan var1) {
         if (var1 != null) {
            String var2 = this.query;
            if (var2 != null) {
               var1.setData("http.query", var2);
            }

            var2 = this.fragment;
            if (var2 != null) {
               var1.setData("http.fragment", var2);
            }
         }
      }

      public String getFragment() {
         return this.fragment;
      }

      public String getQuery() {
         return this.query;
      }

      public String getUrl() {
         return this.url;
      }

      public String getUrlOrFallback() {
         String var2 = this.url;
         String var1 = var2;
         if (var2 == null) {
            var1 = "unknown";
         }

         return var1;
      }
   }
}
