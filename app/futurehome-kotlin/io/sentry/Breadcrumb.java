package io.sentry;

import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.util.UrlUtils;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public final class Breadcrumb implements JsonUnknown, JsonSerializable {
   private String category;
   private Map<String, Object> data = new ConcurrentHashMap();
   private SentryLevel level;
   private String message;
   private String origin;
   private Date timestamp;
   private final Long timestampMs;
   private String type;
   private Map<String, Object> unknown;

   public Breadcrumb() {
      this(System.currentTimeMillis());
   }

   public Breadcrumb(long var1) {
      this.timestampMs = var1;
      this.timestamp = null;
   }

   Breadcrumb(Breadcrumb var1) {
      this.timestamp = var1.timestamp;
      this.timestampMs = var1.timestampMs;
      this.message = var1.message;
      this.type = var1.type;
      this.category = var1.category;
      this.origin = var1.origin;
      Map var2 = CollectionUtils.newConcurrentHashMap(var1.data);
      if (var2 != null) {
         this.data = var2;
      }

      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
      this.level = var1.level;
   }

   public Breadcrumb(String var1) {
      this();
      this.message = var1;
   }

   public Breadcrumb(Date var1) {
      this.timestamp = var1;
      this.timestampMs = null;
   }

   public static Breadcrumb debug(String var0) {
      Breadcrumb var1 = new Breadcrumb();
      var1.setType("debug");
      var1.setMessage(var0);
      var1.setLevel(SentryLevel.DEBUG);
      return var1;
   }

   public static Breadcrumb error(String var0) {
      Breadcrumb var1 = new Breadcrumb();
      var1.setType("error");
      var1.setMessage(var0);
      var1.setLevel(SentryLevel.ERROR);
      return var1;
   }

   public static Breadcrumb fromMap(Map<String, Object> var0, SentryOptions var1) {
      Date var9 = DateUtils.getCurrentDateTime();
      ConcurrentHashMap var12 = new ConcurrentHashMap();
      Iterator var13 = var0.entrySet().iterator();
      String var6 = null;
      String var5 = null;
      String var4 = var5;
      String var16 = var5;
      Entry var7 = var5;
      Object var8 = var5;

      label109:
      while (true) {
         Object var11 = var7;
         if (!var13.hasNext()) {
            Breadcrumb var17 = new Breadcrumb(var9);
            var17.message = var6;
            var17.type = var5;
            var17.data = var12;
            var17.category = var4;
            var17.origin = var16;
            var17.level = var7;
            var17.setUnknown((Map<String, Object>)var8);
            return var17;
         }

         var7 = (Entry)var13.next();
         Iterator var10 = (Iterator)var7.getValue();
         String var14 = (String)var7.getKey();
         var14.hashCode();
         int var3 = var14.hashCode();
         byte var2 = -1;
         switch (var3) {
            case -1008619738:
               if (var14.equals("origin")) {
                  var2 = 0;
               }
               break;
            case 3076010:
               if (var14.equals("data")) {
                  var2 = 1;
               }
               break;
            case 3575610:
               if (var14.equals("type")) {
                  var2 = 2;
               }
               break;
            case 50511102:
               if (var14.equals("category")) {
                  var2 = 3;
               }
               break;
            case 55126294:
               if (var14.equals("timestamp")) {
                  var2 = 4;
               }
               break;
            case 102865796:
               if (var14.equals("level")) {
                  var2 = 5;
               }
               break;
            case 954925063:
               if (var14.equals("message")) {
                  var2 = 6;
               }
         }

         switch (var2) {
            case 0:
               if (var10 instanceof String) {
                  var16 = (String)var10;
                  var7 = var7;
               } else {
                  var16 = null;
                  var7 = var7;
               }
               break;
            case 1:
               if (var10 instanceof Map) {
                  var10 = (Map)var10;
               } else {
                  var10 = null;
               }

               var7 = var7;
               if (var10 == null) {
                  break;
               }

               var10 = var10.entrySet().iterator();

               while (true) {
                  var7 = (Entry)var11;
                  if (!var10.hasNext()) {
                     continue label109;
                  }

                  var7 = (Entry)var10.next();
                  if (var7.getKey() instanceof String && var7.getValue() != null) {
                     var12.put((String)var7.getKey(), var7.getValue());
                  } else {
                     var1.getLogger().log(SentryLevel.WARNING, "Invalid key or null value in data map.");
                  }
               }
            case 2:
               if (var10 instanceof String) {
                  var5 = (String)var10;
                  var7 = var7;
               } else {
                  var5 = null;
                  var7 = var7;
               }
               break;
            case 3:
               if (var10 instanceof String) {
                  var4 = (String)var10;
                  var7 = var7;
               } else {
                  var4 = null;
                  var7 = var7;
               }
               break;
            case 4:
               var7 = var7;
               if (var10 instanceof String) {
                  var10 = ObjectReader$_CC.dateOrNull((String)var10, var1.getLogger());
                  var7 = (Entry)var11;
                  if (var10 != null) {
                     var9 = (Date)var10;
                     var7 = (Entry)var11;
                  }
               }
               break;
            case 5:
               if (var10 instanceof String) {
                  var10 = (String)var10;
               } else {
                  var10 = null;
               }

               var7 = var7;
               if (var10 != null) {
                  try {
                     var7 = SentryLevel.valueOf(var10.toUpperCase(Locale.ROOT));
                  } catch (Exception var15) {
                     var7 = (Entry)var11;
                  }
               }
               break;
            case 6:
               if (var10 instanceof String) {
                  var6 = (String)var10;
                  var7 = var7;
               } else {
                  var6 = null;
                  var7 = var7;
               }
               break;
            default:
               var10 = var8;
               if (var8 == null) {
                  var10 = new ConcurrentHashMap();
               }

               var10.put((String)var7.getKey(), var7.getValue());
               var7 = var7;
               var8 = var10;
         }
      }
   }

   public static Breadcrumb graphqlDataFetcher(String var0, String var1, String var2, String var3) {
      Breadcrumb var4 = new Breadcrumb();
      var4.setType("graphql");
      var4.setCategory("graphql.fetcher");
      if (var0 != null) {
         var4.setData("path", var0);
      }

      if (var1 != null) {
         var4.setData("field", var1);
      }

      if (var2 != null) {
         var4.setData("type", var2);
      }

      if (var3 != null) {
         var4.setData("object_type", var3);
      }

      return var4;
   }

   public static Breadcrumb graphqlDataLoader(Iterable<?> var0, Class<?> var1, Class<?> var2, String var3) {
      Breadcrumb var4 = new Breadcrumb();
      var4.setType("graphql");
      var4.setCategory("graphql.data_loader");
      ArrayList var5 = new ArrayList();
      Iterator var6 = var0.iterator();

      while (var6.hasNext()) {
         var5.add(var6.next().toString());
      }

      var4.setData("keys", var5);
      if (var1 != null) {
         var4.setData("key_type", var1.getName());
      }

      if (var2 != null) {
         var4.setData("value_type", var2.getName());
      }

      if (var3 != null) {
         var4.setData("name", var3);
      }

      return var4;
   }

   public static Breadcrumb graphqlOperation(String var0, String var1, String var2) {
      Breadcrumb var3 = new Breadcrumb();
      var3.setType("graphql");
      if (var0 != null) {
         var3.setData("operation_name", var0);
      }

      if (var1 != null) {
         var3.setData("operation_type", var1);
         var3.setCategory(var1);
      } else {
         var3.setCategory("graphql.operation");
      }

      if (var2 != null) {
         var3.setData("operation_id", var2);
      }

      return var3;
   }

   public static Breadcrumb http(String var0, String var1) {
      Breadcrumb var2 = new Breadcrumb();
      UrlUtils.UrlDetails var3 = UrlUtils.parse(var0);
      var2.setType("http");
      var2.setCategory("http");
      if (var3.getUrl() != null) {
         var2.setData("url", var3.getUrl());
      }

      var2.setData("method", var1.toUpperCase(Locale.ROOT));
      if (var3.getQuery() != null) {
         var2.setData("http.query", var3.getQuery());
      }

      if (var3.getFragment() != null) {
         var2.setData("http.fragment", var3.getFragment());
      }

      return var2;
   }

   public static Breadcrumb http(String var0, String var1, Integer var2) {
      Breadcrumb var3 = http(var0, var1);
      if (var2 != null) {
         var3.setData("status_code", var2);
      }

      return var3;
   }

   public static Breadcrumb info(String var0) {
      Breadcrumb var1 = new Breadcrumb();
      var1.setType("info");
      var1.setMessage(var0);
      var1.setLevel(SentryLevel.INFO);
      return var1;
   }

   public static Breadcrumb navigation(String var0, String var1) {
      Breadcrumb var2 = new Breadcrumb();
      var2.setCategory("navigation");
      var2.setType("navigation");
      var2.setData("from", var0);
      var2.setData("to", var1);
      return var2;
   }

   public static Breadcrumb query(String var0) {
      Breadcrumb var1 = new Breadcrumb();
      var1.setType("query");
      var1.setMessage(var0);
      return var1;
   }

   public static Breadcrumb transaction(String var0) {
      Breadcrumb var1 = new Breadcrumb();
      var1.setType("default");
      var1.setCategory("sentry.transaction");
      var1.setMessage(var0);
      return var1;
   }

   public static Breadcrumb ui(String var0, String var1) {
      Breadcrumb var2 = new Breadcrumb();
      var2.setType("default");
      StringBuilder var3 = new StringBuilder("ui.");
      var3.append(var0);
      var2.setCategory(var3.toString());
      var2.setMessage(var1);
      return var2;
   }

   public static Breadcrumb user(String var0, String var1) {
      Breadcrumb var2 = new Breadcrumb();
      var2.setType("user");
      var2.setCategory(var0);
      var2.setMessage(var1);
      return var2;
   }

   public static Breadcrumb userInteraction(String var0, String var1, String var2) {
      return userInteraction(var0, var1, var2, Collections.emptyMap());
   }

   public static Breadcrumb userInteraction(String var0, String var1, String var2, String var3, Map<String, Object> var4) {
      Breadcrumb var5 = new Breadcrumb();
      var5.setType("user");
      StringBuilder var6 = new StringBuilder("ui.");
      var6.append(var0);
      var5.setCategory(var6.toString());
      if (var1 != null) {
         var5.setData("view.id", var1);
      }

      if (var2 != null) {
         var5.setData("view.class", var2);
      }

      if (var3 != null) {
         var5.setData("view.tag", var3);
      }

      for (Entry var7 : var4.entrySet()) {
         var5.getData().put((String)var7.getKey(), var7.getValue());
      }

      var5.setLevel(SentryLevel.INFO);
      return var5;
   }

   public static Breadcrumb userInteraction(String var0, String var1, String var2, Map<String, Object> var3) {
      return userInteraction(var0, var1, var2, null, var3);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (this.getTimestamp().getTime() != var1.getTimestamp().getTime()
            || !Objects.equals(this.message, var1.message)
            || !Objects.equals(this.type, var1.type)
            || !Objects.equals(this.category, var1.category)
            || !Objects.equals(this.origin, var1.origin)
            || this.level != var1.level) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getCategory() {
      return this.category;
   }

   public Object getData(String var1) {
      return this.data.get(var1);
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   public SentryLevel getLevel() {
      return this.level;
   }

   public String getMessage() {
      return this.message;
   }

   public String getOrigin() {
      return this.origin;
   }

   public Date getTimestamp() {
      Date var1 = this.timestamp;
      if (var1 != null) {
         return (Date)var1.clone();
      } else {
         Long var2 = this.timestampMs;
         if (var2 != null) {
            var1 = DateUtils.getDateTime(var2);
            this.timestamp = var1;
            return var1;
         } else {
            throw new IllegalStateException("No timestamp set for breadcrumb");
         }
      }
   }

   public String getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.timestamp, this.message, this.type, this.category, this.origin, this.level);
   }

   public void removeData(String var1) {
      this.data.remove(var1);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("timestamp").value(var2, this.getTimestamp());
      if (this.message != null) {
         var1.name("message").value(this.message);
      }

      if (this.type != null) {
         var1.name("type").value(this.type);
      }

      var1.name("data").value(var2, this.data);
      if (this.category != null) {
         var1.name("category").value(this.category);
      }

      if (this.origin != null) {
         var1.name("origin").value(this.origin);
      }

      if (this.level != null) {
         var1.name("level").value(var2, this.level);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setCategory(String var1) {
      this.category = var1;
   }

   public void setData(String var1, Object var2) {
      this.data.put(var1, var2);
   }

   public void setLevel(SentryLevel var1) {
      this.level = var1;
   }

   public void setMessage(String var1) {
      this.message = var1;
   }

   public void setOrigin(String var1) {
      this.origin = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<Breadcrumb> {
      public Breadcrumb deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Date var12 = DateUtils.getCurrentDateTime();
         Object var11 = new ConcurrentHashMap();
         String var10 = null;
         String var9 = null;
         String var8 = null;
         String var6 = var8;
         String var5 = var8;
         Object var7 = var8;

         while (true) {
            Object var14 = var5;
            if (var1.peek() != JsonToken.NAME) {
               Breadcrumb var16 = new Breadcrumb(var12);
               var16.message = var10;
               var16.type = var9;
               var16.data = (Map<String, Object>)var11;
               var16.category = var8;
               var16.origin = var6;
               var16.level = var5;
               var16.setUnknown((Map<String, Object>)var7);
               var1.endObject();
               return var16;
            }

            var5 = var1.nextName();
            var5.hashCode();
            int var4 = var5.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1008619738:
                  if (var5.equals("origin")) {
                     var3 = 0;
                  }
                  break;
               case 3076010:
                  if (var5.equals("data")) {
                     var3 = 1;
                  }
                  break;
               case 3575610:
                  if (var5.equals("type")) {
                     var3 = 2;
                  }
                  break;
               case 50511102:
                  if (var5.equals("category")) {
                     var3 = 3;
                  }
                  break;
               case 55126294:
                  if (var5.equals("timestamp")) {
                     var3 = 4;
                  }
                  break;
               case 102865796:
                  if (var5.equals("level")) {
                     var3 = 5;
                  }
                  break;
               case 954925063:
                  if (var5.equals("message")) {
                     var3 = 6;
                  }
            }

            switch (var3) {
               case 0:
                  var6 = var1.nextStringOrNull();
                  var5 = var5;
                  break;
               case 1:
                  Map var20 = CollectionUtils.newConcurrentHashMap((Map)var1.nextObjectOrNull());
                  var5 = var5;
                  if (var20 != null) {
                     var11 = var20;
                     var5 = (String)var14;
                  }
                  break;
               case 2:
                  var9 = var1.nextStringOrNull();
                  var5 = var5;
                  break;
               case 3:
                  var8 = var1.nextStringOrNull();
                  var5 = var5;
                  break;
               case 4:
                  Date var19 = var1.nextDateOrNull(var2);
                  var5 = var5;
                  if (var19 != null) {
                     var12 = var19;
                     var5 = (String)var14;
                  }
                  break;
               case 5:
                  try {
                     SentryLevel.Deserializer var18 = new SentryLevel.Deserializer();
                     var5 = var18.deserialize(var1, var2);
                  } catch (Exception var15) {
                     var2.log(SentryLevel.ERROR, var15, "Error when deserializing SentryLevel");
                     var5 = (String)var14;
                  }
                  break;
               case 6:
                  var10 = var1.nextStringOrNull();
                  var5 = var5;
                  break;
               default:
                  Object var13 = var7;
                  if (var7 == null) {
                     var13 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, (Map<String, Object>)var13, var5);
                  var5 = var5;
                  var7 = var13;
            }
         }
      }
   }

   public static final class JsonKeys {
      public static final String CATEGORY = "category";
      public static final String DATA = "data";
      public static final String LEVEL = "level";
      public static final String MESSAGE = "message";
      public static final String ORIGIN = "origin";
      public static final String TIMESTAMP = "timestamp";
      public static final String TYPE = "type";
   }
}
