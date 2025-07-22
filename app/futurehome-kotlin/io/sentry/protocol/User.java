package io.sentry.protocol;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class User implements JsonUnknown, JsonSerializable {
   private Map<String, String> data;
   private String email;
   private Geo geo;
   private String id;
   private String ipAddress;
   private String name;
   @Deprecated
   private String segment;
   private Map<String, Object> unknown;
   private String username;

   public User() {
   }

   public User(User var1) {
      this.email = var1.email;
      this.username = var1.username;
      this.id = var1.id;
      this.ipAddress = var1.ipAddress;
      this.segment = var1.segment;
      this.name = var1.name;
      this.geo = var1.geo;
      this.data = CollectionUtils.newConcurrentHashMap(var1.data);
      this.unknown = CollectionUtils.newConcurrentHashMap(var1.unknown);
   }

   public static User fromMap(Map<String, Object> var0, SentryOptions var1) {
      User var5 = new User();
      Iterator var6 = var0.entrySet().iterator();
      ConcurrentHashMap var9 = null;

      while (var6.hasNext()) {
         Entry var7 = (Entry)var6.next();
         Map var4 = (Map)var7.getValue();
         String var8 = (String)var7.getKey();
         var8.hashCode();
         int var3 = var8.hashCode();
         byte var2 = -1;
         switch (var3) {
            case -265713450:
               if (var8.equals("username")) {
                  var2 = 0;
               }
               break;
            case 3355:
               if (var8.equals("id")) {
                  var2 = 1;
               }
               break;
            case 102225:
               if (var8.equals("geo")) {
                  var2 = 2;
               }
               break;
            case 3076010:
               if (var8.equals("data")) {
                  var2 = 3;
               }
               break;
            case 3373707:
               if (var8.equals("name")) {
                  var2 = 4;
               }
               break;
            case 96619420:
               if (var8.equals("email")) {
                  var2 = 5;
               }
               break;
            case 106069776:
               if (var8.equals("other")) {
                  var2 = 6;
               }
               break;
            case 1480014044:
               if (var8.equals("ip_address")) {
                  var2 = 7;
               }
               break;
            case 1973722931:
               if (var8.equals("segment")) {
                  var2 = 8;
               }
         }

         switch (var2) {
            case 0:
               if (var4 instanceof String) {
                  var4 = (String)var4;
               } else {
                  var4 = null;
               }

               var5.username = (String)var4;
               break;
            case 1:
               if (var4 instanceof String) {
                  var4 = (String)var4;
               } else {
                  var4 = null;
               }

               var5.id = (String)var4;
               break;
            case 2:
               if (var4 instanceof Map) {
                  var4 = var4;
               } else {
                  var4 = null;
               }

               if (var4 == null) {
                  break;
               }

               ConcurrentHashMap var26 = new ConcurrentHashMap();

               for (Entry var20 : var4.entrySet()) {
                  if (var20.getKey() instanceof String && var20.getValue() != null) {
                     var26.put((String)var20.getKey(), var20.getValue());
                  } else {
                     var1.getLogger().log(SentryLevel.WARNING, "Invalid key type in gep map.");
                  }
               }

               var5.geo = Geo.fromMap(var26);
               break;
            case 3:
               if (var4 instanceof Map) {
                  var4 = var4;
               } else {
                  var4 = null;
               }

               if (var4 == null) {
                  break;
               }

               ConcurrentHashMap var25 = new ConcurrentHashMap();

               for (Entry var28 : var4.entrySet()) {
                  if (var28.getKey() instanceof String && var28.getValue() != null) {
                     var25.put((String)var28.getKey(), var28.getValue().toString());
                  } else {
                     var1.getLogger().log(SentryLevel.WARNING, "Invalid key or null value in data map.");
                  }
               }

               var5.data = var25;
               break;
            case 4:
               if (var4 instanceof String) {
                  var4 = (String)var4;
               } else {
                  var4 = null;
               }

               var5.name = (String)var4;
               break;
            case 5:
               if (var4 instanceof String) {
                  var4 = (String)var4;
               } else {
                  var4 = null;
               }

               var5.email = (String)var4;
               break;
            case 6:
               if (var4 instanceof Map) {
                  var4 = var4;
               } else {
                  var4 = null;
               }

               if (var4 == null) {
                  break;
               }

               Map var23 = var5.data;
               if (var23 != null && !var23.isEmpty()) {
                  break;
               }

               ConcurrentHashMap var24 = new ConcurrentHashMap();

               for (Entry var27 : var4.entrySet()) {
                  if (var27.getKey() instanceof String && var27.getValue() != null) {
                     var24.put((String)var27.getKey(), var27.getValue().toString());
                  } else {
                     var1.getLogger().log(SentryLevel.WARNING, "Invalid key or null value in other map.");
                  }
               }

               var5.data = var24;
               break;
            case 7:
               if (var4 instanceof String) {
                  var4 = (String)var4;
               } else {
                  var4 = null;
               }

               var5.ipAddress = (String)var4;
               break;
            case 8:
               String var11;
               if (var4 instanceof String) {
                  var11 = (String)var4;
               } else {
                  var11 = null;
               }

               var5.segment = var11;
               break;
            default:
               var4 = var9;
               if (var9 == null) {
                  var4 = new ConcurrentHashMap();
               }

               var4.put((String)var7.getKey(), var7.getValue());
               var9 = var4;
         }
      }

      var5.unknown = var9;
      return var5;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.email, var1.email)
            || !Objects.equals(this.id, var1.id)
            || !Objects.equals(this.username, var1.username)
            || !Objects.equals(this.segment, var1.segment)
            || !Objects.equals(this.ipAddress, var1.ipAddress)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public Map<String, String> getData() {
      return this.data;
   }

   public String getEmail() {
      return this.email;
   }

   public Geo getGeo() {
      return this.geo;
   }

   public String getId() {
      return this.id;
   }

   public String getIpAddress() {
      return this.ipAddress;
   }

   public String getName() {
      return this.name;
   }

   @Deprecated
   public Map<String, String> getOthers() {
      return this.getData();
   }

   @Deprecated
   public String getSegment() {
      return this.segment;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getUsername() {
      return this.username;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.email, this.id, this.username, this.segment, this.ipAddress);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.email != null) {
         var1.name("email").value(this.email);
      }

      if (this.id != null) {
         var1.name("id").value(this.id);
      }

      if (this.username != null) {
         var1.name("username").value(this.username);
      }

      if (this.segment != null) {
         var1.name("segment").value(this.segment);
      }

      if (this.ipAddress != null) {
         var1.name("ip_address").value(this.ipAddress);
      }

      if (this.name != null) {
         var1.name("name").value(this.name);
      }

      if (this.geo != null) {
         var1.name("geo");
         this.geo.serialize(var1, var2);
      }

      if (this.data != null) {
         var1.name("data").value(var2, this.data);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setData(Map<String, String> var1) {
      this.data = CollectionUtils.newConcurrentHashMap(var1);
   }

   public void setEmail(String var1) {
      this.email = var1;
   }

   public void setGeo(Geo var1) {
      this.geo = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setIpAddress(String var1) {
      this.ipAddress = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   @Deprecated
   public void setOthers(Map<String, String> var1) {
      this.setData(var1);
   }

   @Deprecated
   public void setSegment(String var1) {
      this.segment = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setUsername(String var1) {
      this.username = var1;
   }

   public static final class Deserializer implements JsonDeserializer<User> {
      public User deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         User var7 = new User();
         ConcurrentHashMap var6 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var8 = var1.nextName();
            var8.hashCode();
            int var4 = var8.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -265713450:
                  if (var8.equals("username")) {
                     var3 = 0;
                  }
                  break;
               case 3355:
                  if (var8.equals("id")) {
                     var3 = 1;
                  }
                  break;
               case 102225:
                  if (var8.equals("geo")) {
                     var3 = 2;
                  }
                  break;
               case 3076010:
                  if (var8.equals("data")) {
                     var3 = 3;
                  }
                  break;
               case 3373707:
                  if (var8.equals("name")) {
                     var3 = 4;
                  }
                  break;
               case 96619420:
                  if (var8.equals("email")) {
                     var3 = 5;
                  }
                  break;
               case 106069776:
                  if (var8.equals("other")) {
                     var3 = 6;
                  }
                  break;
               case 1480014044:
                  if (var8.equals("ip_address")) {
                     var3 = 7;
                  }
                  break;
               case 1973722931:
                  if (var8.equals("segment")) {
                     var3 = 8;
                  }
            }

            switch (var3) {
               case 0:
                  var7.username = var1.nextStringOrNull();
                  break;
               case 1:
                  var7.id = var1.nextStringOrNull();
                  break;
               case 2:
                  var7.geo = new Geo.Deserializer().deserialize(var1, var2);
                  break;
               case 3:
                  var7.data = CollectionUtils.newConcurrentHashMap((Map<String, String>)var1.nextObjectOrNull());
                  break;
               case 4:
                  var7.name = var1.nextStringOrNull();
                  break;
               case 5:
                  var7.email = var1.nextStringOrNull();
                  break;
               case 6:
                  if (var7.data == null || var7.data.isEmpty()) {
                     var7.data = CollectionUtils.newConcurrentHashMap((Map<String, String>)var1.nextObjectOrNull());
                  }
                  break;
               case 7:
                  var7.ipAddress = var1.nextStringOrNull();
                  break;
               case 8:
                  var7.segment = var1.nextStringOrNull();
                  break;
               default:
                  ConcurrentHashMap var5 = var6;
                  if (var6 == null) {
                     var5 = new ConcurrentHashMap();
                  }

                  var1.nextUnknown(var2, var5, var8);
                  var6 = var5;
            }
         }

         var7.setUnknown(var6);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String DATA = "data";
      public static final String EMAIL = "email";
      public static final String GEO = "geo";
      public static final String ID = "id";
      public static final String IP_ADDRESS = "ip_address";
      public static final String NAME = "name";
      public static final String OTHER = "other";
      public static final String SEGMENT = "segment";
      public static final String USERNAME = "username";
   }
}
