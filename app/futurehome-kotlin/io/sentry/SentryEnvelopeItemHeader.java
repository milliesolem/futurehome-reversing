package io.sentry;

import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public final class SentryEnvelopeItemHeader implements JsonSerializable, JsonUnknown {
   private final String attachmentType;
   private final String contentType;
   private final String fileName;
   private final Callable<Integer> getLength;
   private final int length;
   private final SentryItemType type;
   private Map<String, Object> unknown;

   public SentryEnvelopeItemHeader(SentryItemType var1, int var2, String var3, String var4, String var5) {
      this.type = Objects.requireNonNull(var1, "type is required");
      this.contentType = var3;
      this.length = var2;
      this.fileName = var4;
      this.getLength = null;
      this.attachmentType = var5;
   }

   SentryEnvelopeItemHeader(SentryItemType var1, Callable<Integer> var2, String var3, String var4) {
      this(var1, var2, var3, var4, null);
   }

   SentryEnvelopeItemHeader(SentryItemType var1, Callable<Integer> var2, String var3, String var4, String var5) {
      this.type = Objects.requireNonNull(var1, "type is required");
      this.contentType = var3;
      this.length = -1;
      this.fileName = var4;
      this.getLength = var2;
      this.attachmentType = var5;
   }

   public String getAttachmentType() {
      return this.attachmentType;
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getFileName() {
      return this.fileName;
   }

   public int getLength() {
      Callable var2 = this.getLength;
      if (var2 != null) {
         try {
            return (Integer)var2.call();
         } finally {
            ;
         }
      } else {
         return this.length;
      }
   }

   public SentryItemType getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.contentType != null) {
         var1.name("content_type").value(this.contentType);
      }

      if (this.fileName != null) {
         var1.name("filename").value(this.fileName);
      }

      var1.name("type").value(var2, this.type);
      if (this.attachmentType != null) {
         var1.name("attachment_type").value(this.attachmentType);
      }

      var1.name("length").value((long)this.getLength());
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

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryEnvelopeItemHeader> {
      private Exception missingRequiredFieldException(String var1, ILogger var2) {
         StringBuilder var3 = new StringBuilder("Missing required field \"");
         var3.append(var1);
         var3.append("\"");
         var1 = var3.toString();
         IllegalStateException var5 = new IllegalStateException(var1);
         var2.log(SentryLevel.ERROR, var1, var5);
         return var5;
      }

      public SentryEnvelopeItemHeader deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         HashMap var9 = null;
         SentryItemType var10 = null;
         String var8 = null;
         String var6 = var8;
         String var7 = var8;
         int var4 = 0;

         while (var1.peek() == JsonToken.NAME) {
            String var12 = var1.nextName();
            var12.hashCode();
            int var5 = var12.hashCode();
            byte var3 = -1;
            switch (var5) {
               case -1106363674:
                  if (var12.equals("length")) {
                     var3 = 0;
                  }
                  break;
               case -734768633:
                  if (var12.equals("filename")) {
                     var3 = 1;
                  }
                  break;
               case -672977706:
                  if (var12.equals("attachment_type")) {
                     var3 = 2;
                  }
                  break;
               case 3575610:
                  if (var12.equals("type")) {
                     var3 = 3;
                  }
                  break;
               case 831846208:
                  if (var12.equals("content_type")) {
                     var3 = 4;
                  }
            }

            switch (var3) {
               case 0:
                  var4 = var1.nextInt();
                  break;
               case 1:
                  var6 = var1.nextStringOrNull();
                  break;
               case 2:
                  var7 = var1.nextStringOrNull();
                  break;
               case 3:
                  var10 = var1.nextOrNull(var2, new SentryItemType.Deserializer());
                  break;
               case 4:
                  var8 = var1.nextStringOrNull();
                  break;
               default:
                  HashMap var11 = var9;
                  if (var9 == null) {
                     var11 = new HashMap();
                  }

                  var1.nextUnknown(var2, var11, var12);
                  var9 = var11;
            }
         }

         if (var10 != null) {
            SentryEnvelopeItemHeader var13 = new SentryEnvelopeItemHeader(var10, var4, var8, var6, var7);
            var13.setUnknown(var9);
            var1.endObject();
            return var13;
         } else {
            throw this.missingRequiredFieldException("type", var2);
         }
      }
   }

   public static final class JsonKeys {
      public static final String ATTACHMENT_TYPE = "attachment_type";
      public static final String CONTENT_TYPE = "content_type";
      public static final String FILENAME = "filename";
      public static final String LENGTH = "length";
      public static final String TYPE = "type";
   }
}
