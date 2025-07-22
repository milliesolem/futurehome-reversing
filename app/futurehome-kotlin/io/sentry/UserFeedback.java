package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class UserFeedback implements JsonUnknown, JsonSerializable {
   private String comments;
   private String email;
   private final SentryId eventId;
   private String name;
   private Map<String, Object> unknown;

   public UserFeedback(SentryId var1) {
      this(var1, null, null, null);
   }

   public UserFeedback(SentryId var1, String var2, String var3, String var4) {
      this.eventId = var1;
      this.name = var2;
      this.email = var3;
      this.comments = var4;
   }

   public String getComments() {
      return this.comments;
   }

   public String getEmail() {
      return this.email;
   }

   public SentryId getEventId() {
      return this.eventId;
   }

   public String getName() {
      return this.name;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("event_id");
      this.eventId.serialize(var1, var2);
      if (this.name != null) {
         var1.name("name").value(this.name);
      }

      if (this.email != null) {
         var1.name("email").value(this.email);
      }

      if (this.comments != null) {
         var1.name("comments").value(this.comments);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            Object var4 = this.unknown.get(var5);
            var1.name(var5).value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setComments(String var1) {
      this.comments = var1;
   }

   public void setEmail(String var1) {
      this.email = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("UserFeedback{eventId=");
      var1.append(this.eventId);
      var1.append(", name='");
      var1.append(this.name);
      var1.append("', email='");
      var1.append(this.email);
      var1.append("', comments='");
      var1.append(this.comments);
      var1.append("'}");
      return var1.toString();
   }

   public static final class Deserializer implements JsonDeserializer<UserFeedback> {
      public UserFeedback deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         SentryId var9 = null;
         String var8 = null;
         String var7 = null;
         String var5 = var7;
         Object var6 = var7;

         while (var1.peek() == JsonToken.NAME) {
            String var11 = var1.nextName();
            var11.hashCode();
            int var4 = var11.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -602415628:
                  if (var11.equals("comments")) {
                     var3 = 0;
                  }
                  break;
               case 3373707:
                  if (var11.equals("name")) {
                     var3 = 1;
                  }
                  break;
               case 96619420:
                  if (var11.equals("email")) {
                     var3 = 2;
                  }
                  break;
               case 278118624:
                  if (var11.equals("event_id")) {
                     var3 = 3;
                  }
            }

            switch (var3) {
               case 0:
                  var5 = var1.nextStringOrNull();
                  break;
               case 1:
                  var8 = var1.nextStringOrNull();
                  break;
               case 2:
                  var7 = var1.nextStringOrNull();
                  break;
               case 3:
                  var9 = new SentryId.Deserializer().deserialize(var1, var2);
                  break;
               default:
                  Object var10 = var6;
                  if (var6 == null) {
                     var10 = new HashMap();
                  }

                  var1.nextUnknown(var2, (Map<String, Object>)var10, var11);
                  var6 = var10;
            }
         }

         var1.endObject();
         if (var9 != null) {
            UserFeedback var13 = new UserFeedback(var9, var8, var7, var5);
            var13.setUnknown((Map<String, Object>)var6);
            return var13;
         } else {
            IllegalStateException var12 = new IllegalStateException("Missing required field \"event_id\"");
            var2.log(SentryLevel.ERROR, "Missing required field \"event_id\"", var12);
            throw var12;
         }
      }
   }

   public static final class JsonKeys {
      public static final String COMMENTS = "comments";
      public static final String EMAIL = "email";
      public static final String EVENT_ID = "event_id";
      public static final String NAME = "name";
   }
}
