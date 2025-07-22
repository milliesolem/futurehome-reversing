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
import java.util.Map;

public final class RRWebInteractionEvent extends RRWebIncrementalSnapshotEvent implements JsonSerializable, JsonUnknown {
   private static final int POINTER_TYPE_TOUCH = 2;
   private Map<String, Object> dataUnknown;
   private int id;
   private RRWebInteractionEvent.InteractionType interactionType;
   private int pointerId;
   private int pointerType = 2;
   private Map<String, Object> unknown;
   private float x;
   private float y;

   public RRWebInteractionEvent() {
      super(RRWebIncrementalSnapshotEvent.IncrementalSource.MouseInteraction);
   }

   private void serializeData(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      new RRWebIncrementalSnapshotEvent.Serializer().serialize(this, var1, var2);
      var1.name("type").value(var2, this.interactionType);
      var1.name("id").value((long)this.id);
      var1.name("x").value((double)this.x);
      var1.name("y").value((double)this.y);
      var1.name("pointerType").value((long)this.pointerType);
      var1.name("pointerId").value((long)this.pointerId);
      Map var3 = this.dataUnknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.dataUnknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public Map<String, Object> getDataUnknown() {
      return this.dataUnknown;
   }

   public int getId() {
      return this.id;
   }

   public RRWebInteractionEvent.InteractionType getInteractionType() {
      return this.interactionType;
   }

   public int getPointerId() {
      return this.pointerId;
   }

   public int getPointerType() {
      return this.pointerType;
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

   public void setDataUnknown(Map<String, Object> var1) {
      this.dataUnknown = var1;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public void setInteractionType(RRWebInteractionEvent.InteractionType var1) {
      this.interactionType = var1;
   }

   public void setPointerId(int var1) {
      this.pointerId = var1;
   }

   public void setPointerType(int var1) {
      this.pointerType = var1;
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

   public static final class Deserializer implements JsonDeserializer<RRWebInteractionEvent> {
      private void deserializeData(RRWebInteractionEvent var1, ObjectReader var2, ILogger var3) throws Exception {
         RRWebIncrementalSnapshotEvent.Deserializer var8 = new RRWebIncrementalSnapshotEvent.Deserializer();
         var2.beginObject();
         HashMap var7 = null;

         while (var2.peek() == JsonToken.NAME) {
            String var9 = var2.nextName();
            var9.hashCode();
            int var5 = var9.hashCode();
            byte var4 = -1;
            switch (var5) {
               case 120:
                  if (var9.equals("x")) {
                     var4 = 0;
                  }
                  break;
               case 121:
                  if (var9.equals("y")) {
                     var4 = 1;
                  }
                  break;
               case 3355:
                  if (var9.equals("id")) {
                     var4 = 2;
                  }
                  break;
               case 3575610:
                  if (var9.equals("type")) {
                     var4 = 3;
                  }
                  break;
               case 768858903:
                  if (var9.equals("pointerType")) {
                     var4 = 4;
                  }
                  break;
               case 1565043768:
                  if (var9.equals("pointerId")) {
                     var4 = 5;
                  }
            }

            switch (var4) {
               case 0:
                  var1.x = var2.nextFloat();
                  break;
               case 1:
                  var1.y = var2.nextFloat();
                  break;
               case 2:
                  var1.id = var2.nextInt();
                  break;
               case 3:
                  var1.interactionType = var2.nextOrNull(var3, new RRWebInteractionEvent.InteractionType.Deserializer());
                  break;
               case 4:
                  var1.pointerType = var2.nextInt();
                  break;
               case 5:
                  var1.pointerId = var2.nextInt();
                  break;
               default:
                  if (!var8.deserializeValue(var1, var9, var2, var3)) {
                     HashMap var6 = var7;
                     if (var7 == null) {
                        var6 = new HashMap();
                     }

                     var2.nextUnknown(var3, var6, var9);
                     var7 = var6;
                  }
            }
         }

         var1.setDataUnknown(var7);
         var2.endObject();
      }

      public RRWebInteractionEvent deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         RRWebInteractionEvent var6 = new RRWebInteractionEvent();
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

   public static enum InteractionType implements JsonSerializable {
      Blur,
      Click,
      ContextMenu,
      DblClick,
      Focus,
      MouseDown,
      MouseUp,
      TouchCancel,
      TouchEnd,
      TouchMove_Departed,
      TouchStart;
      private static final RRWebInteractionEvent.InteractionType[] $VALUES = $values();

      @Override
      public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
         var1.value((long)this.ordinal());
      }

      public static final class Deserializer implements JsonDeserializer<RRWebInteractionEvent.InteractionType> {
         public RRWebInteractionEvent.InteractionType deserialize(ObjectReader var1, ILogger var2) throws Exception {
            return RRWebInteractionEvent.InteractionType.values()[var1.nextInt()];
         }
      }
   }

   public static final class JsonKeys {
      public static final String DATA = "data";
      public static final String ID = "id";
      public static final String POINTER_ID = "pointerId";
      public static final String POINTER_TYPE = "pointerType";
      public static final String TYPE = "type";
      public static final String X = "x";
      public static final String Y = "y";
   }
}
