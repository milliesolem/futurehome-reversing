package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.CollectionUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class Response implements JsonUnknown, JsonSerializable {
   public static final String TYPE = "response";
   private Long bodySize;
   private String cookies;
   private Object data;
   private Map<String, String> headers;
   private Integer statusCode;
   private Map<String, Object> unknown;

   public Response() {
   }

   public Response(Response var1) {
      this.cookies = var1.cookies;
      this.headers = CollectionUtils.newConcurrentHashMap(var1.headers);
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
      this.statusCode = var1.statusCode;
      this.bodySize = var1.bodySize;
      this.data = var1.data;
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

   public Map<String, String> getHeaders() {
      return this.headers;
   }

   public Integer getStatusCode() {
      return this.statusCode;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.cookies != null) {
         var1.name("cookies").value(this.cookies);
      }

      if (this.headers != null) {
         var1.name("headers").value(var2, this.headers);
      }

      if (this.statusCode != null) {
         var1.name("status_code").value(var2, this.statusCode);
      }

      if (this.bodySize != null) {
         var1.name("body_size").value(var2, this.bodySize);
      }

      if (this.data != null) {
         var1.name("data").value(var2, this.data);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.unknown.get(var6);
            var1.name(var6);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
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

   public void setHeaders(Map<String, String> var1) {
      this.headers = CollectionUtils.newConcurrentHashMap(var1);
   }

   public void setStatusCode(Integer var1) {
      this.statusCode = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Response> {
      public Response deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Response var7 = new Response();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -891699686:
                  if (var8.equals("status_code")) {
                     var3 = 0;
                  }
                  break;
               case 3076010:
                  if (var8.equals("data")) {
                     var3 = 1;
                  }
                  break;
               case 795307910:
                  if (var8.equals("headers")) {
                     var3 = 2;
                  }
                  break;
               case 952189583:
                  if (var8.equals("cookies")) {
                     var3 = 3;
                  }
                  break;
               case 1252988030:
                  if (var8.equals("body_size")) {
                     var3 = 4;
                  }
            }

            switch (var3) {
               case 0:
                  var7.statusCode = var1.nextIntegerOrNull();
                  break;
               case 1:
                  var7.data = var1.nextObjectOrNull();
                  break;
               case 2:
                  Map var9 = (Map)var1.nextObjectOrNull();
                  if (var9 != null) {
                     var7.headers = CollectionUtils.newConcurrentHashMap(var9);
                  }
                  break;
               case 3:
                  var7.cookies = var1.nextStringOrNull();
                  break;
               case 4:
                  var7.bodySize = var1.nextLongOrNull();
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
      public static final String BODY_SIZE = "body_size";
      public static final String COOKIES = "cookies";
      public static final String DATA = "data";
      public static final String HEADERS = "headers";
      public static final String STATUS_CODE = "status_code";
   }
}
