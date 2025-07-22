package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class Request implements JsonUnknown, JsonSerializable {
   private String apiTarget;
   private Long bodySize;
   private String cookies;
   private Object data;
   private Map<String, String> env;
   private String fragment;
   private Map<String, String> headers;
   private String method;
   private Map<String, String> other;
   private String queryString;
   private Map<String, Object> unknown;
   private String url;

   public Request() {
   }

   public Request(Request var1) {
      this.url = var1.url;
      this.cookies = var1.cookies;
      this.method = var1.method;
      this.queryString = var1.queryString;
      this.headers = CollectionUtils.newConcurrentHashMap(var1.headers);
      this.env = CollectionUtils.newConcurrentHashMap(var1.env);
      this.other = CollectionUtils.newConcurrentHashMap(var1.other);
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
      this.data = var1.data;
      this.fragment = var1.fragment;
      this.bodySize = var1.bodySize;
      this.apiTarget = var1.apiTarget;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.url, var1.url)
            || !Objects.equals(this.method, var1.method)
            || !Objects.equals(this.queryString, var1.queryString)
            || !Objects.equals(this.cookies, var1.cookies)
            || !Objects.equals(this.headers, var1.headers)
            || !Objects.equals(this.env, var1.env)
            || !Objects.equals(this.bodySize, var1.bodySize)
            || !Objects.equals(this.fragment, var1.fragment)
            || !Objects.equals(this.apiTarget, var1.apiTarget)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getApiTarget() {
      return this.apiTarget;
   }

   public Long getBodySize() {
      return this.bodySize;
   }

   public String getCookies() {
      return this.cookies;
   }

   public Object getData() {
      return this.data;
   }

   public Map<String, String> getEnvs() {
      return this.env;
   }

   public String getFragment() {
      return this.fragment;
   }

   public Map<String, String> getHeaders() {
      return this.headers;
   }

   public String getMethod() {
      return this.method;
   }

   public Map<String, String> getOthers() {
      return this.other;
   }

   public String getQueryString() {
      return this.queryString;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getUrl() {
      return this.url;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.url, this.method, this.queryString, this.cookies, this.headers, this.env, this.bodySize, this.fragment, this.apiTarget);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.url != null) {
         var1.name("url").value(this.url);
      }

      if (this.method != null) {
         var1.name("method").value(this.method);
      }

      if (this.queryString != null) {
         var1.name("query_string").value(this.queryString);
      }

      if (this.data != null) {
         var1.name("data").value(var2, this.data);
      }

      if (this.cookies != null) {
         var1.name("cookies").value(this.cookies);
      }

      if (this.headers != null) {
         var1.name("headers").value(var2, this.headers);
      }

      if (this.env != null) {
         var1.name("env").value(var2, this.env);
      }

      if (this.other != null) {
         var1.name("other").value(var2, this.other);
      }

      if (this.fragment != null) {
         var1.name("fragment").value(var2, this.fragment);
      }

      if (this.bodySize != null) {
         var1.name("body_size").value(var2, this.bodySize);
      }

      if (this.apiTarget != null) {
         var1.name("api_target").value(var2, this.apiTarget);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var4 = this.unknown.get(var6);
            var1.name(var6);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setApiTarget(String var1) {
      this.apiTarget = var1;
   }

   public void setBodySize(Long var1) {
      this.bodySize = var1;
   }

   public void setCookies(String var1) {
      this.cookies = var1;
   }

   public void setData(Object var1) {
      this.data = var1;
   }

   public void setEnvs(Map<String, String> var1) {
      this.env = CollectionUtils.newConcurrentHashMap(var1);
   }

   public void setFragment(String var1) {
      this.fragment = var1;
   }

   public void setHeaders(Map<String, String> var1) {
      this.headers = CollectionUtils.newConcurrentHashMap(var1);
   }

   public void setMethod(String var1) {
      this.method = var1;
   }

   public void setOthers(Map<String, String> var1) {
      this.other = CollectionUtils.newConcurrentHashMap(var1);
   }

   public void setQueryString(String var1) {
      this.queryString = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setUrl(String var1) {
      this.url = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Request> {
      public Request deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Request var7 = new Request();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1650269616:
                  if (var8.equals("fragment")) {
                     var3 = 0;
                  }
                  break;
               case -1077554975:
                  if (var8.equals("method")) {
                     var3 = 1;
                  }
                  break;
               case 100589:
                  if (var8.equals("env")) {
                     var3 = 2;
                  }
                  break;
               case 116079:
                  if (var8.equals("url")) {
                     var3 = 3;
                  }
                  break;
               case 3076010:
                  if (var8.equals("data")) {
                     var3 = 4;
                  }
                  break;
               case 106069776:
                  if (var8.equals("other")) {
                     var3 = 5;
                  }
                  break;
               case 795307910:
                  if (var8.equals("headers")) {
                     var3 = 6;
                  }
                  break;
               case 952189583:
                  if (var8.equals("cookies")) {
                     var3 = 7;
                  }
                  break;
               case 1252988030:
                  if (var8.equals("body_size")) {
                     var3 = 8;
                  }
                  break;
               case 1595298664:
                  if (var8.equals("query_string")) {
                     var3 = 9;
                  }
                  break;
               case 1980646230:
                  if (var8.equals("api_target")) {
                     var3 = 10;
                  }
            }

            switch (var3) {
               case 0:
                  var7.fragment = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.method = var1.nextStringOrNull();
                  break;
               case 2:
                  Map var11 = (Map)var1.nextObjectOrNull();
                  if (var11 != null) {
                     var7.env = CollectionUtils.newConcurrentHashMap(var11);
                  }
                  break;
               case 3:
                  var7.url = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.data = var1.nextObjectOrNull();
                  break;
               case 5:
                  Map var10 = (Map)var1.nextObjectOrNull();
                  if (var10 != null) {
                     var7.other = CollectionUtils.newConcurrentHashMap(var10);
                  }
                  break;
               case 6:
                  Map var9 = (Map)var1.nextObjectOrNull();
                  if (var9 != null) {
                     var7.headers = CollectionUtils.newConcurrentHashMap(var9);
                  }
                  break;
               case 7:
                  var7.cookies = var1.nextStringOrNull();
                  break;
               case 8:
                  var7.bodySize = var1.nextLongOrNull();
                  break;
               case 9:
                  var7.queryString = var1.nextStringOrNull();
                  break;
               case 10:
                  var7.apiTarget = var1.nextStringOrNull();
                  break;
               default:
                  ConcurrentHashMap var6 = var5;
                  if (var5 == null) {
                     var6 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var6, var8);
                  var5 = var6;
            }
         }

         var7.setUnknown(var5);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String API_TARGET = "api_target";
      public static final String BODY_SIZE = "body_size";
      public static final String COOKIES = "cookies";
      public static final String DATA = "data";
      public static final String ENV = "env";
      public static final String FRAGMENT = "fragment";
      public static final String HEADERS = "headers";
      public static final String METHOD = "method";
      public static final String OTHER = "other";
      public static final String QUERY_STRING = "query_string";
      public static final String URL = "url";
   }
}
