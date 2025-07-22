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

public final class RRWebVideoEvent extends RRWebEvent implements JsonUnknown, JsonSerializable {
   public static final String EVENT_TAG = "video";
   public static final String REPLAY_CONTAINER = "mp4";
   public static final String REPLAY_ENCODING = "h264";
   public static final String REPLAY_FRAME_RATE_TYPE_CONSTANT = "constant";
   public static final String REPLAY_FRAME_RATE_TYPE_VARIABLE = "variable";
   private String container;
   private Map<String, Object> dataUnknown;
   private long durationMs;
   private String encoding = "h264";
   private int frameCount;
   private int frameRate;
   private String frameRateType;
   private int height;
   private int left;
   private Map<String, Object> payloadUnknown;
   private int segmentId;
   private long size;
   private String tag;
   private int top;
   private Map<String, Object> unknown;
   private int width;

   public RRWebVideoEvent() {
      super(RRWebEventType.Custom);
      this.container = "mp4";
      this.frameRateType = "constant";
      this.tag = "video";
   }

   private void serializeData(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("tag").value(this.tag);
      var1.name("payload");
      this.serializePayload(var1, var2);
      Map var3 = this.dataUnknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var5 = this.dataUnknown.get(var6);
            var1.name(var6);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   private void serializePayload(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("segmentId").value((long)this.segmentId);
      var1.name("size").value(this.size);
      var1.name("duration").value(this.durationMs);
      var1.name("encoding").value(this.encoding);
      var1.name("container").value(this.container);
      var1.name("height").value((long)this.height);
      var1.name("width").value((long)this.width);
      var1.name("frameCount").value((long)this.frameCount);
      var1.name("frameRate").value((long)this.frameRate);
      var1.name("frameRateType").value(this.frameRateType);
      var1.name("left").value((long)this.left);
      var1.name("top").value((long)this.top);
      Map var3 = this.payloadUnknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.payloadUnknown.get(var4);
            var1.name(var4);
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
         if (this.segmentId != var1.segmentId
            || this.size != var1.size
            || this.durationMs != var1.durationMs
            || this.height != var1.height
            || this.width != var1.width
            || this.frameCount != var1.frameCount
            || this.frameRate != var1.frameRate
            || this.left != var1.left
            || this.top != var1.top
            || !Objects.equals(this.tag, var1.tag)
            || !Objects.equals(this.encoding, var1.encoding)
            || !Objects.equals(this.container, var1.container)
            || !Objects.equals(this.frameRateType, var1.frameRateType)) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getContainer() {
      return this.container;
   }

   public Map<String, Object> getDataUnknown() {
      return this.dataUnknown;
   }

   public long getDurationMs() {
      return this.durationMs;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public int getFrameCount() {
      return this.frameCount;
   }

   public int getFrameRate() {
      return this.frameRate;
   }

   public String getFrameRateType() {
      return this.frameRateType;
   }

   public int getHeight() {
      return this.height;
   }

   public int getLeft() {
      return this.left;
   }

   public Map<String, Object> getPayloadUnknown() {
      return this.payloadUnknown;
   }

   public int getSegmentId() {
      return this.segmentId;
   }

   public long getSize() {
      return this.size;
   }

   public String getTag() {
      return this.tag;
   }

   public int getTop() {
      return this.top;
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
      return Objects.hash(
         super.hashCode(),
         this.tag,
         this.segmentId,
         this.size,
         this.durationMs,
         this.encoding,
         this.container,
         this.height,
         this.width,
         this.frameCount,
         this.frameRateType,
         this.frameRate,
         this.left,
         this.top
      );
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      new RRWebEvent.Serializer().serialize(this, var1, var2);
      var1.name("data");
      this.serializeData(var1, var2);
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

   public void setContainer(String var1) {
      this.container = var1;
   }

   public void setDataUnknown(Map<String, Object> var1) {
      this.dataUnknown = var1;
   }

   public void setDurationMs(long var1) {
      this.durationMs = var1;
   }

   public void setEncoding(String var1) {
      this.encoding = var1;
   }

   public void setFrameCount(int var1) {
      this.frameCount = var1;
   }

   public void setFrameRate(int var1) {
      this.frameRate = var1;
   }

   public void setFrameRateType(String var1) {
      this.frameRateType = var1;
   }

   public void setHeight(int var1) {
      this.height = var1;
   }

   public void setLeft(int var1) {
      this.left = var1;
   }

   public void setPayloadUnknown(Map<String, Object> var1) {
      this.payloadUnknown = var1;
   }

   public void setSegmentId(int var1) {
      this.segmentId = var1;
   }

   public void setSize(long var1) {
      this.size = var1;
   }

   public void setTag(String var1) {
      this.tag = var1;
   }

   public void setTop(int var1) {
      this.top = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }

   public static final class Deserializer implements JsonDeserializer<RRWebVideoEvent> {
      private void deserializeData(RRWebVideoEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         var2.beginObject();
         ConcurrentHashMap var4 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var6 = var2.nextName();
            var6.hashCode();
            if (!var6.equals("payload")) {
               if (!var6.equals("tag")) {
                  ConcurrentHashMap var5 = var4;
                  if (var4 == null) {
                     var5 = new ConcurrentHashMap();
                  }

                  var2.nextUnknown(var3, var5, var6);
                  var4 = var5;
               } else {
                  var6 = var2.nextStringOrNull();
                  String var7 = var6;
                  if (var6 == null) {
                     var7 = "";
                  }

                  var1.tag = var7;
               }
            } else {
               this.deserializePayload(var1, var2, var3);
            }
         }

         var1.setDataUnknown(var4);
         var2.endObject();
      }

      private void deserializePayload(RRWebVideoEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         var2.beginObject();
         ConcurrentHashMap var15 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var16 = var2.nextName();
            var16.hashCode();
            int var11 = var16.hashCode();
            byte var6 = 0;
            byte var5 = 0;
            byte var8 = 0;
            byte var7 = 0;
            byte var9 = 0;
            byte var10 = 0;
            int var4 = -1;
            switch (var11) {
               case -1992012396:
                  if (var16.equals("duration")) {
                     var4 = 0;
                  }
                  break;
               case -1627805778:
                  if (var16.equals("segmentId")) {
                     var4 = 1;
                  }
                  break;
               case -1221029593:
                  if (var16.equals("height")) {
                     var4 = 2;
                  }
                  break;
               case -410956671:
                  if (var16.equals("container")) {
                     var4 = 3;
                  }
                  break;
               case -296512606:
                  if (var16.equals("frameCount")) {
                     var4 = 4;
                  }
                  break;
               case 115029:
                  if (var16.equals("top")) {
                     var4 = 5;
                  }
                  break;
               case 3317767:
                  if (var16.equals("left")) {
                     var4 = 6;
                  }
                  break;
               case 3530753:
                  if (var16.equals("size")) {
                     var4 = 7;
                  }
                  break;
               case 113126854:
                  if (var16.equals("width")) {
                     var4 = 8;
                  }
                  break;
               case 545057773:
                  if (var16.equals("frameRate")) {
                     var4 = 9;
                  }
                  break;
               case 1711222099:
                  if (var16.equals("encoding")) {
                     var4 = 10;
                  }
                  break;
               case 2135109831:
                  if (var16.equals("frameRateType")) {
                     var4 = 11;
                  }
            }

            String var14 = "";
            switch (var4) {
               case 0:
                  var1.durationMs = var2.nextLong();
                  break;
               case 1:
                  var1.segmentId = var2.nextInt();
                  break;
               case 2:
                  Integer var30 = var2.nextIntegerOrNull();
                  if (var30 == null) {
                     var4 = var9;
                  } else {
                     var4 = var30;
                  }

                  var1.height = var4;
                  break;
               case 3:
                  var16 = var2.nextStringOrNull();
                  if (var16 != null) {
                     var14 = var16;
                  }

                  var1.container = var14;
                  break;
               case 4:
                  Integer var29 = var2.nextIntegerOrNull();
                  if (var29 == null) {
                     var4 = var7;
                  } else {
                     var4 = var29;
                  }

                  var1.frameCount = var4;
                  break;
               case 5:
                  Integer var28 = var2.nextIntegerOrNull();
                  if (var28 == null) {
                     var4 = var8;
                  } else {
                     var4 = var28;
                  }

                  var1.top = var4;
                  break;
               case 6:
                  Integer var27 = var2.nextIntegerOrNull();
                  if (var27 == null) {
                     var4 = var5;
                  } else {
                     var4 = var27;
                  }

                  var1.left = var4;
                  break;
               case 7:
                  Long var26 = var2.nextLongOrNull();
                  long var12;
                  if (var26 == null) {
                     var12 = 0L;
                  } else {
                     var12 = var26;
                  }

                  var1.size = var12;
                  break;
               case 8:
                  Integer var25 = var2.nextIntegerOrNull();
                  if (var25 == null) {
                     var4 = var6;
                  } else {
                     var4 = var25;
                  }

                  var1.width = var4;
                  break;
               case 9:
                  Integer var24 = var2.nextIntegerOrNull();
                  if (var24 == null) {
                     var4 = var10;
                  } else {
                     var4 = var24;
                  }

                  var1.frameRate = var4;
                  break;
               case 10:
                  var16 = var2.nextStringOrNull();
                  if (var16 != null) {
                     var14 = var16;
                  }

                  var1.encoding = var14;
                  break;
               case 11:
                  var16 = var2.nextStringOrNull();
                  if (var16 != null) {
                     var14 = var16;
                  }

                  var1.frameRateType = var14;
                  break;
               default:
                  ConcurrentHashMap var23 = var15;
                  if (var15 == null) {
                     var23 = new ConcurrentHashMap();
                  }

                  var2.nextUnknown(var3, var23, var16);
                  var15 = var23;
            }
         }

         var1.setPayloadUnknown(var15);
         var2.endObject();
      }

      public RRWebVideoEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         RRWebVideoEvent var5 = new RRWebVideoEvent();
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
      public static final String CONTAINER = "container";
      public static final String DATA = "data";
      public static final String DURATION = "duration";
      public static final String ENCODING = "encoding";
      public static final String FRAME_COUNT = "frameCount";
      public static final String FRAME_RATE = "frameRate";
      public static final String FRAME_RATE_TYPE = "frameRateType";
      public static final String HEIGHT = "height";
      public static final String LEFT = "left";
      public static final String PAYLOAD = "payload";
      public static final String SEGMENT_ID = "segmentId";
      public static final String SIZE = "size";
      public static final String TOP = "top";
      public static final String WIDTH = "width";
   }
}
