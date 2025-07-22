package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.Map;

public final class TraceContext implements JsonUnknown, JsonSerializable {
   private final String environment;
   private final String publicKey;
   private final String release;
   private final SentryId replayId;
   private final String sampleRate;
   private final String sampled;
   private final SentryId traceId;
   private final String transaction;
   private Map<String, Object> unknown;
   private final String userId;
   private final String userSegment;

   TraceContext(SentryId var1, String var2) {
      this(var1, var2, null, null, null, null, null, null, null);
   }

   TraceContext(SentryId var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, SentryId var9) {
      this(var1, var2, var3, var4, var5, null, var6, var7, var8, var9);
   }

   @Deprecated
   TraceContext(SentryId var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, SentryId var10) {
      this.traceId = var1;
      this.publicKey = var2;
      this.release = var3;
      this.environment = var4;
      this.userId = var5;
      this.userSegment = var6;
      this.transaction = var7;
      this.sampleRate = var8;
      this.sampled = var9;
      this.replayId = var10;
   }

   private static String getUserId(SentryOptions var0, User var1) {
      return var0.isSendDefaultPii() && var1 != null ? var1.getId() : null;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public String getPublicKey() {
      return this.publicKey;
   }

   public String getRelease() {
      return this.release;
   }

   public SentryId getReplayId() {
      return this.replayId;
   }

   public String getSampleRate() {
      return this.sampleRate;
   }

   public String getSampled() {
      return this.sampled;
   }

   public SentryId getTraceId() {
      return this.traceId;
   }

   public String getTransaction() {
      return this.transaction;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public String getUserId() {
      return this.userId;
   }

   @Deprecated
   public String getUserSegment() {
      return this.userSegment;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("trace_id").value(var2, this.traceId);
      var1.name("public_key").value(this.publicKey);
      if (this.release != null) {
         var1.name("release").value(this.release);
      }

      if (this.environment != null) {
         var1.name("environment").value(this.environment);
      }

      if (this.userId != null) {
         var1.name("user_id").value(this.userId);
      }

      if (this.userSegment != null) {
         var1.name("user_segment").value(this.userSegment);
      }

      if (this.transaction != null) {
         var1.name("transaction").value(this.transaction);
      }

      if (this.sampleRate != null) {
         var1.name("sample_rate").value(this.sampleRate);
      }

      if (this.sampled != null) {
         var1.name("sampled").value(this.sampled);
      }

      if (this.replayId != null) {
         var1.name("replay_id").value(var2, this.replayId);
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

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<TraceContext> {
      private Exception missingRequiredFieldException(String var1, ILogger var2) {
         StringBuilder var3 = new StringBuilder("Missing required field \"");
         var3.append(var1);
         var3.append("\"");
         var1 = var3.toString();
         IllegalStateException var5 = new IllegalStateException(var1);
         var2.log(SentryLevel.ERROR, var1, var5);
         return var5;
      }

      public TraceContext deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         TraceContext.TraceContextUser var15 = null;
         String var5 = null;
         SentryId var14 = null;
         Object var7 = var14;
         Object var12 = var14;
         Object var11 = var14;
         Object var10 = var14;
         Object var9 = var14;
         Object var8 = var14;
         Object var16 = var14;
         SentryId var13 = var14;
         Object var6 = var14;

         while (var1.peek() == JsonToken.NAME) {
            String var17 = var1.nextName();
            var17.hashCode();
            int var4 = var17.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -795593025:
                  if (var17.equals("user_segment")) {
                     var3 = 0;
                  }
                  break;
               case -454767501:
                  if (var17.equals("replay_id")) {
                     var3 = 1;
                  }
                  break;
               case -147132913:
                  if (var17.equals("user_id")) {
                     var3 = 2;
                  }
                  break;
               case -85904877:
                  if (var17.equals("environment")) {
                     var3 = 3;
                  }
                  break;
               case 3599307:
                  if (var17.equals("user")) {
                     var3 = 4;
                  }
                  break;
               case 153193045:
                  if (var17.equals("sample_rate")) {
                     var3 = 5;
                  }
                  break;
               case 1090594823:
                  if (var17.equals("release")) {
                     var3 = 6;
                  }
                  break;
               case 1270300245:
                  if (var17.equals("trace_id")) {
                     var3 = 7;
                  }
                  break;
               case 1864843258:
                  if (var17.equals("sampled")) {
                     var3 = 8;
                  }
                  break;
               case 1904812937:
                  if (var17.equals("public_key")) {
                     var3 = 9;
                  }
                  break;
               case 2141246174:
                  if (var17.equals("transaction")) {
                     var3 = 10;
                  }
            }

            Object var33;
            Object var34;
            label105: {
               label104: {
                  Object var29;
                  switch (var3) {
                     case 0:
                        var7 = var1.nextStringOrNull();
                        var29 = var10;
                        var33 = var9;
                        var34 = var8;
                        break;
                     case 1:
                        var29 = new SentryId.Deserializer().deserialize(var1, var2);
                        var13 = (SentryId)var10;
                        var10 = var9;
                        var9 = var8;
                        var8 = var29;
                        break label104;
                     case 2:
                        var5 = var1.nextStringOrNull();
                        var29 = var10;
                        var33 = var9;
                        var34 = var8;
                        break;
                     case 3:
                        var29 = var1.nextStringOrNull();
                        var33 = var9;
                        var34 = var8;
                        break;
                     case 4:
                        var15 = var1.nextOrNull(var2, new TraceContext.TraceContextUser.Deserializer());
                        var29 = var10;
                        var33 = var9;
                        var34 = var8;
                        break;
                     case 5:
                        var34 = var1.nextStringOrNull();
                        var29 = var10;
                        var33 = var9;
                        break;
                     case 6:
                        var11 = var1.nextStringOrNull();
                        var29 = var10;
                        var33 = var9;
                        var34 = var8;
                        break;
                     case 7:
                        var14 = new SentryId.Deserializer().deserialize(var1, var2);
                        var34 = var8;
                        var33 = var9;
                        var29 = var10;
                        break;
                     case 8:
                        var16 = var1.nextStringOrNull();
                        var30 = var10;
                        var33 = var9;
                        var34 = var8;
                        break label105;
                     case 9:
                        var12 = var1.nextString();
                        var29 = var10;
                        var33 = var9;
                        var34 = var8;
                        break;
                     case 10:
                        var33 = var1.nextStringOrNull();
                        var29 = var10;
                        var34 = var8;
                        break;
                     default:
                        Object var20 = var6;
                        if (var6 == null) {
                           var20 = new ConcurrentHashMap();
                        }

                        var1.nextUnknown(var2, (Map<String, Object>)var20, var17);
                        var29 = var10;
                        var33 = var9;
                        var34 = var8;
                        var6 = var20;
                  }

                  var8 = var13;
                  var9 = var34;
                  var10 = var33;
                  var13 = (SentryId)var29;
               }

               var30 = var13;
               var33 = var10;
               var34 = var9;
               var13 = (SentryId)var8;
            }

            var10 = var30;
            var9 = var33;
            var8 = var34;
         }

         if (var14 != null) {
            if (var12 == null) {
               throw this.missingRequiredFieldException("public_key", var2);
            } else {
               String var32;
               label91: {
                  var32 = var5;
                  if (var15 != null) {
                     String var21 = var5;
                     if (var5 == null) {
                        var21 = var15.getId();
                     }

                     var32 = var21;
                     if (var7 == null) {
                        var5 = var15.getSegment();
                        var32 = var21;
                        var22 = var5;
                        break label91;
                     }
                  }

                  var22 = var7;
               }

               TraceContext var23 = new TraceContext(
                  var14, (String)var12, (String)var11, (String)var10, var32, (String)var22, (String)var9, (String)var8, (String)var16, var13
               );
               var23.setUnknown((Map<String, Object>)var6);
               var1.endObject();
               return var23;
            }
         } else {
            throw this.missingRequiredFieldException("trace_id", var2);
         }
      }
   }

   public static final class JsonKeys {
      public static final String ENVIRONMENT = "environment";
      public static final String PUBLIC_KEY = "public_key";
      public static final String RELEASE = "release";
      public static final String REPLAY_ID = "replay_id";
      public static final String SAMPLED = "sampled";
      public static final String SAMPLE_RATE = "sample_rate";
      public static final String TRACE_ID = "trace_id";
      public static final String TRANSACTION = "transaction";
      public static final String USER = "user";
      public static final String USER_ID = "user_id";
      public static final String USER_SEGMENT = "user_segment";
   }

   @Deprecated
   private static final class TraceContextUser implements JsonUnknown {
      private String id;
      private String segment;
      private Map<String, Object> unknown;

      private TraceContextUser(String var1, String var2) {
         this.id = var1;
         this.segment = var2;
      }

      public String getId() {
         return this.id;
      }

      @Deprecated
      public String getSegment() {
         return this.segment;
      }

      @Override
      public Map<String, Object> getUnknown() {
         return this.unknown;
      }

      @Override
      public void setUnknown(Map<String, Object> var1) {
         this.unknown = var1;
      }

      public static final class Deserializer implements JsonDeserializer<TraceContext.TraceContextUser> {
         public TraceContext.TraceContextUser deserialize(ObjectReader var1, ILogger var2) throws Exception {
            var1.beginObject();
            String var5 = null;
            String var4 = null;
            Object var3 = var4;

            while (var1.peek() == JsonToken.NAME) {
               String var7 = var1.nextName();
               var7.hashCode();
               if (!var7.equals("id")) {
                  if (!var7.equals("segment")) {
                     Object var6 = var3;
                     if (var3 == null) {
                        var6 = new ConcurrentHashMap();
                     }

                     var1.nextUnknown(var2, (Map<String, Object>)var6, var7);
                     var3 = var6;
                  } else {
                     var4 = var1.nextStringOrNull();
                  }
               } else {
                  var5 = var1.nextStringOrNull();
               }
            }

            TraceContext.TraceContextUser var8 = new TraceContext.TraceContextUser(var5, var4);
            var8.setUnknown((Map<String, Object>)var3);
            var1.endObject();
            return var8;
         }
      }

      public static final class JsonKeys {
         public static final String ID = "id";
         public static final String SEGMENT = "segment";
      }
   }
}
