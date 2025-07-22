package io.sentry.rrweb;

import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RRWebInteractionMoveEvent extends RRWebIncrementalSnapshotEvent implements JsonSerializable, JsonUnknown {
   private Map<String, Object> dataUnknown;
   private int pointerId;
   private List<RRWebInteractionMoveEvent.Position> positions;
   private Map<String, Object> unknown;

   public RRWebInteractionMoveEvent() {
      super(RRWebIncrementalSnapshotEvent.IncrementalSource.TouchMove);
   }

   private void serializeData(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      new RRWebIncrementalSnapshotEvent.Serializer().serialize(this, var1, var2);
      List var3 = this.positions;
      if (var3 != null && !var3.isEmpty()) {
         var1.name("positions").value(var2, this.positions);
      }

      var1.name("pointerId").value((long)this.pointerId);
      Map var6 = this.dataUnknown;
      if (var6 != null) {
         for (String var5 : var6.keySet()) {
            var3 = (List)this.dataUnknown.get(var5);
            var1.name(var5);
            var1.value(var2, var3);
         }
      }

      var1.endObject();
   }

   public Map<String, Object> getDataUnknown() {
      return this.dataUnknown;
   }

   public int getPointerId() {
      return this.pointerId;
   }

   public List<RRWebInteractionMoveEvent.Position> getPositions() {
      return this.positions;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      new RRWebEvent.Serializer().serialize(this, var1, var2);
      var1.name("data");
      this.serializeData(var1, var2);
      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var5 : var3.keySet()) {
            Object var4 = this.unknown.get(var5);
            var1.name(var5);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setDataUnknown(Map<String, Object> var1) {
      this.dataUnknown = var1;
   }

   public void setPointerId(int var1) {
      this.pointerId = var1;
   }

   public void setPositions(List<RRWebInteractionMoveEvent.Position> var1) {
      this.positions = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<RRWebInteractionMoveEvent> {
      private void deserializeData(RRWebInteractionMoveEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         RRWebIncrementalSnapshotEvent.Deserializer var6 = new RRWebIncrementalSnapshotEvent.Deserializer();
         var2.beginObject();
         HashMap var4 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var7 = var2.nextName();
            var7.hashCode();
            if (!var7.equals("pointerId")) {
               if (!var7.equals("positions")) {
                  if (!var6.deserializeValue(var1, var7, var2, var3)) {
                     HashMap var5 = var4;
                     if (var4 == null) {
                        var5 = new HashMap();
                     }

                     var2.nextUnknown(var3, var5, var7);
                     var4 = var5;
                  }
               } else {
                  var1.positions = var2.nextListOrNull(var3, new RRWebInteractionMoveEvent.Position.Deserializer());
               }
            } else {
               var1.pointerId = var2.nextInt();
            }
         }

         var1.setDataUnknown(var4);
         var2.endObject();
      }

      public RRWebInteractionMoveEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         RRWebInteractionMoveEvent var6 = new RRWebInteractionMoveEvent();
         RRWebEvent.Deserializer var5 = new RRWebEvent.Deserializer();
         HashMap var4 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var7 = var1.nextName();
            var7.hashCode();
            if (!var7.equals("data")) {
               if (!var5.deserializeValue(var6, var7, var1, var2)) {
                  HashMap var3 = var4;
                  if (var4 == null) {
                     var3 = new HashMap();
                  }

                  var1.nextUnknown(var2, var3, var7);
                  var4 = var3;
               }
            } else {
               this.deserializeData(var6, var1, var2);
            }
         }

         var6.setUnknown(var4);
         var1.endObject();
         return var6;
      }
   }

   public static final class JsonKeys {
      public static final String DATA = "data";
      public static final String POINTER_ID = "pointerId";
      public static final String POSITIONS = "positions";
   }

   public static final class Position implements JsonSerializable, JsonUnknown {
      private int id;
      private long timeOffset;
      private Map<String, Object> unknown;
      private float x;
      private float y;

      public int getId() {
         return this.id;
      }

      public long getTimeOffset() {
         return this.timeOffset;
      }

      @Override
      public Map<String, Object> getUnknown() {
         return this.unknown;
      }

      public float getX() {
         return this.x;
      }

      public float getY() {
         return this.y;
      }

      @Override
      public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
         var1.beginObject();
         var1.name("id").value((long)this.id);
         var1.name("x").value((double)this.x);
         var1.name("y").value((double)this.y);
         var1.name("timeOffset").value(this.timeOffset);
         Map var3 = this.unknown;
         if (var3 != null) {
            for (String var5 : var3.keySet()) {
               Object var4 = this.unknown.get(var5);
               var1.name(var5);
               var1.value(var2, var4);
            }
         }

         var1.endObject();
      }

      public void setId(int var1) {
         this.id = var1;
      }

      public void setTimeOffset(long var1) {
         this.timeOffset = var1;
      }

      @Override
      public void setUnknown(Map<String, Object> var1) {
         this.unknown = var1;
      }

      public void setX(float var1) {
         this.x = var1;
      }

      public void setY(float var1) {
         this.y = var1;
      }

      public static final class Deserializer implements JsonDeserializer<RRWebInteractionMoveEvent.Position> {
         public RRWebInteractionMoveEvent.Position deserialize(ObjectReader var1, ILogger var2) throws Exception {
            var1.beginObject();
            RRWebInteractionMoveEvent.Position var7 = new RRWebInteractionMoveEvent.Position();
            HashMap var6 = null;

            while (var1.peek() == JsonToken.NAME) {
               String var8 = var1.nextName();
               var8.hashCode();
               int var4 = var8.hashCode();
               byte var3 = -1;
               switch (var4) {
                  case 120:
                     if (var8.equals("x")) {
                        var3 = 0;
                     }
                     break;
                  case 121:
                     if (var8.equals("y")) {
                        var3 = 1;
                     }
                     break;
                  case 3355:
                     if (var8.equals("id")) {
                        var3 = 2;
                     }
                     break;
                  case 665490880:
                     if (var8.equals("timeOffset")) {
                        var3 = 3;
                     }
               }

               switch (var3) {
                  case 0:
                     var7.x = var1.nextFloat();
                     break;
                  case 1:
                     var7.y = var1.nextFloat();
                     break;
                  case 2:
                     var7.id = var1.nextInt();
                     break;
                  case 3:
                     var7.timeOffset = var1.nextLong();
                     break;
                  default:
                     HashMap var5 = var6;
                     if (var6 == null) {
                        var5 = new HashMap();
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
         public static final String ID = "id";
         public static final String TIME_OFFSET = "timeOffset";
         public static final String X = "x";
         public static final String Y = "y";
      }
   }
}
