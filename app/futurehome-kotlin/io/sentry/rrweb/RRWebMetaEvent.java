package io.sentry.rrweb;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class RRWebMetaEvent extends RRWebEvent implements JsonUnknown, JsonSerializable {
   private Map<String, Object> dataUnknown;
   private int height;
   private String href = "";
   private Map<String, Object> unknown;
   private int width;

   public RRWebMetaEvent() {
      super(RRWebEventType.Meta);
   }

   private void serializeData(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("href").value(this.href);
      var1.name("height").value((long)this.height);
      var1.name("width").value((long)this.width);
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

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 == null || this.getClass() != var1.getClass()) {
         return false;
      } else if (!super.equals(var1)) {
         return false;
      } else {
         var1 = var1;
         if (this.height != var1.height || this.width != var1.width || !Objects.equals(this.href, var1.href)) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<String, Object> getDataUnknown() {
      return this.dataUnknown;
   }

   public int getHeight() {
      return this.height;
   }

   public String getHref() {
      return this.href;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public int getWidth() {
      return this.width;
   }

   @Override
   public int hashCode() {
      return Objects.hash(super.hashCode(), this.href, this.height, this.width);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      new RRWebEvent.Serializer().serialize(this, var1, var2);
      var1.name("data");
      this.serializeData(var1, var2);
      var1.endObject();
   }

   public void setDataUnknown(Map<String, Object> var1) {
      this.dataUnknown = var1;
   }

   public void setHeight(int var1) {
      this.height = var1;
   }

   public void setHref(String var1) {
      this.href = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }

   public static final class Deserializer implements JsonDeserializer<RRWebMetaEvent> {
      private void deserializeData(RRWebMetaEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         var2.beginObject();
         ConcurrentHashMap var8 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var10 = var2.nextName();
            var10.hashCode();
            int var7 = var10.hashCode();
            byte var6 = 0;
            byte var5 = 0;
            int var4 = -1;
            switch (var7) {
               case -1221029593:
                  if (var10.equals("height")) {
                     var4 = 0;
                  }
                  break;
               case 3211051:
                  if (var10.equals("href")) {
                     var4 = 1;
                  }
                  break;
               case 113126854:
                  if (var10.equals("width")) {
                     var4 = 2;
                  }
            }

            switch (var4) {
               case 0:
                  Integer var15 = var2.nextIntegerOrNull();
                  if (var15 == null) {
                     var4 = var6;
                  } else {
                     var4 = var15;
                  }

                  var1.height = var4;
                  break;
               case 1:
                  var10 = var2.nextStringOrNull();
                  String var14 = var10;
                  if (var10 == null) {
                     var14 = "";
                  }

                  var1.href = var14;
                  break;
               case 2:
                  Integer var13 = var2.nextIntegerOrNull();
                  if (var13 == null) {
                     var4 = var5;
                  } else {
                     var4 = var13;
                  }

                  var1.width = var4;
                  break;
               default:
                  ConcurrentHashMap var9 = var8;
                  if (var8 == null) {
                     var9 = new ConcurrentHashMap();
                  }

                  var2.nextUnknown(var3, var9, var10);
                  var8 = var9;
            }
         }

         var1.setDataUnknown(var8);
         var2.endObject();
      }

      public RRWebMetaEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         RRWebMetaEvent var5 = new RRWebMetaEvent();
         RRWebEvent.Deserializer var6 = new RRWebEvent.Deserializer();
         HashMap var3 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("data")) {
               if (!var6.deserializeValue(var5, var7, var1, var2)) {
                  HashMap var4 = var3;
                  if (var3 == null) {
                     var4 = new HashMap();
                  }

                  var1.nextUnknown(var2, var4, var7);
                  var3 = var4;
               }
            } else {
               this.deserializeData(var5, var1, var2);
            }
         }

         var5.setUnknown(var3);
         var1.endObject();
         return var5;
      }
   }

   public static final class JsonKeys {
      public static final String DATA = "data";
      public static final String HEIGHT = "height";
      public static final String HREF = "href";
      public static final String WIDTH = "width";
   }
}
