package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class SentryReplayEvent extends SentryBaseEvent implements JsonUnknown, JsonSerializable {
   public static final String REPLAY_EVENT_TYPE = "replay_event";
   public static final long REPLAY_VIDEO_MAX_SIZE = 10485760L;
   private List<String> errorIds;
   private SentryId replayId = new SentryId();
   private Date replayStartTimestamp;
   private SentryReplayEvent.ReplayType replayType;
   private int segmentId;
   private Date timestamp;
   private List<String> traceIds;
   private String type = "replay_event";
   private Map<String, Object> unknown;
   private List<String> urls;
   private File videoFile;

   public SentryReplayEvent() {
      this.replayType = SentryReplayEvent.ReplayType.SESSION;
      this.errorIds = new ArrayList<>();
      this.traceIds = new ArrayList<>();
      this.urls = new ArrayList<>();
      this.timestamp = DateUtils.getCurrentDateTime();
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (this.segmentId != var1.segmentId
            || !Objects.equals(this.type, var1.type)
            || this.replayType != var1.replayType
            || !Objects.equals(this.replayId, var1.replayId)
            || !Objects.equals(this.urls, var1.urls)
            || !Objects.equals(this.errorIds, var1.errorIds)
            || !Objects.equals(this.traceIds, var1.traceIds)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public List<String> getErrorIds() {
      return this.errorIds;
   }

   public SentryId getReplayId() {
      return this.replayId;
   }

   public Date getReplayStartTimestamp() {
      return this.replayStartTimestamp;
   }

   public SentryReplayEvent.ReplayType getReplayType() {
      return this.replayType;
   }

   public int getSegmentId() {
      return this.segmentId;
   }

   public Date getTimestamp() {
      return this.timestamp;
   }

   public List<String> getTraceIds() {
      return this.traceIds;
   }

   public String getType() {
      return this.type;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public List<String> getUrls() {
      return this.urls;
   }

   public File getVideoFile() {
      return this.videoFile;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.type, this.replayType, this.replayId, this.segmentId, this.urls, this.errorIds, this.traceIds);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("type").value(this.type);
      var1.name("replay_type").value(var2, this.replayType);
      var1.name("segment_id").value((long)this.segmentId);
      var1.name("timestamp").value(var2, this.timestamp);
      if (this.replayId != null) {
         var1.name("replay_id").value(var2, this.replayId);
      }

      if (this.replayStartTimestamp != null) {
         var1.name("replay_start_timestamp").value(var2, this.replayStartTimestamp);
      }

      if (this.urls != null) {
         var1.name("urls").value(var2, this.urls);
      }

      if (this.errorIds != null) {
         var1.name("error_ids").value(var2, this.errorIds);
      }

      if (this.traceIds != null) {
         var1.name("trace_ids").value(var2, this.traceIds);
      }

      new SentryBaseEvent.Serializer().serialize(this, var1, var2);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            var3 = (Map)this.unknown.get(var5);
            var1.name(var5).value(var2, var3);
         }
      }

      var1.endObject();
   }

   public void setErrorIds(List<String> var1) {
      this.errorIds = var1;
   }

   public void setReplayId(SentryId var1) {
      this.replayId = var1;
   }

   public void setReplayStartTimestamp(Date var1) {
      this.replayStartTimestamp = var1;
   }

   public void setReplayType(SentryReplayEvent.ReplayType var1) {
      this.replayType = var1;
   }

   public void setSegmentId(int var1) {
      this.segmentId = var1;
   }

   public void setTimestamp(Date var1) {
      this.timestamp = var1;
   }

   public void setTraceIds(List<String> var1) {
      this.traceIds = var1;
   }

   public void setType(String var1) {
      this.type = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public void setUrls(List<String> var1) {
      this.urls = var1;
   }

   public void setVideoFile(File var1) {
      this.videoFile = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryReplayEvent> {
      public SentryReplayEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         SentryBaseEvent.Deserializer var17 = new SentryBaseEvent.Deserializer();
         SentryReplayEvent var16 = new SentryReplayEvent();
         var1.beginObject();
         String var14 = null;
         SentryReplayEvent.ReplayType var13 = null;
         Integer var12 = null;
         Object var5 = var12;
         Object var6 = var12;
         Object var7 = var12;
         Object var8 = var12;
         Object var9 = var12;
         Object var10 = var12;
         Object var11 = var12;

         while (var1.peek() == JsonToken.NAME) {
            String var18 = var1.nextName();
            var18.hashCode();
            int var4 = var18.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -454767501:
                  if (var18.equals("replay_id")) {
                     var3 = 0;
                  }
                  break;
               case -264026847:
                  if (var18.equals("replay_start_timestamp")) {
                     var3 = 1;
                  }
                  break;
               case 3575610:
                  if (var18.equals("type")) {
                     var3 = 2;
                  }
                  break;
               case 3598564:
                  if (var18.equals("urls")) {
                     var3 = 3;
                  }
                  break;
               case 55126294:
                  if (var18.equals("timestamp")) {
                     var3 = 4;
                  }
                  break;
               case 329864193:
                  if (var18.equals("error_ids")) {
                     var3 = 5;
                  }
                  break;
               case 724602046:
                  if (var18.equals("trace_ids")) {
                     var3 = 6;
                  }
                  break;
               case 1055447186:
                  if (var18.equals("replay_type")) {
                     var3 = 7;
                  }
                  break;
               case 1077649831:
                  if (var18.equals("segment_id")) {
                     var3 = 8;
                  }
            }

            switch (var3) {
               case 0:
                  var7 = var1.nextOrNull(var2, new SentryId.Deserializer());
                  break;
               case 1:
                  var8 = var1.nextDateOrNull(var2);
                  break;
               case 2:
                  var14 = var1.nextStringOrNull();
                  break;
               case 3:
                  var9 = (List)var1.nextObjectOrNull();
                  break;
               case 4:
                  var5 = var1.nextDateOrNull(var2);
                  break;
               case 5:
                  var10 = (List)var1.nextObjectOrNull();
                  break;
               case 6:
                  var11 = (List)var1.nextObjectOrNull();
                  break;
               case 7:
                  var13 = var1.nextOrNull(var2, new SentryReplayEvent.ReplayType.Deserializer());
                  break;
               case 8:
                  var12 = var1.nextIntegerOrNull();
                  break;
               default:
                  if (!var17.deserializeValue(var16, var18, var1, var2)) {
                     Object var15 = var6;
                     if (var6 == null) {
                        var15 = new HashMap();
                     }

                     var1.nextUnknown(var2, (Map<String, Object>)var15, var18);
                     var6 = var15;
                  }
            }
         }

         var1.endObject();
         if (var14 != null) {
            var16.setType(var14);
         }

         if (var13 != null) {
            var16.setReplayType(var13);
         }

         if (var12 != null) {
            var16.setSegmentId(var12);
         }

         if (var5 != null) {
            var16.setTimestamp((Date)var5);
         }

         var16.setReplayId((SentryId)var7);
         var16.setReplayStartTimestamp((Date)var8);
         var16.setUrls((List<String>)var9);
         var16.setErrorIds((List<String>)var10);
         var16.setTraceIds((List<String>)var11);
         var16.setUnknown((Map<String, Object>)var6);
         return var16;
      }
   }

   public static final class JsonKeys {
      public static final String ERROR_IDS = "error_ids";
      public static final String REPLAY_ID = "replay_id";
      public static final String REPLAY_START_TIMESTAMP = "replay_start_timestamp";
      public static final String REPLAY_TYPE = "replay_type";
      public static final String SEGMENT_ID = "segment_id";
      public static final String TIMESTAMP = "timestamp";
      public static final String TRACE_IDS = "trace_ids";
      public static final String TYPE = "type";
      public static final String URLS = "urls";
   }

   public static enum ReplayType implements JsonSerializable {
      BUFFER,
      SESSION;
      private static final SentryReplayEvent.ReplayType[] $VALUES = $values();

      @Override
      public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
         var1.value(this.name().toLowerCase(Locale.ROOT));
      }

      public static final class Deserializer implements JsonDeserializer<SentryReplayEvent.ReplayType> {
         public SentryReplayEvent.ReplayType deserialize(ObjectReader var1, ILogger var2) throws Exception {
            return SentryReplayEvent.ReplayType.valueOf(var1.nextString().toUpperCase(Locale.ROOT));
         }
      }
   }
}
