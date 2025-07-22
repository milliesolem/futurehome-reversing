package io.sentry;

import io.sentry.rrweb.RRWebBreadcrumbEvent;
import io.sentry.rrweb.RRWebEvent;
import io.sentry.rrweb.RRWebEventType;
import io.sentry.rrweb.RRWebIncrementalSnapshotEvent;
import io.sentry.rrweb.RRWebInteractionEvent;
import io.sentry.rrweb.RRWebInteractionMoveEvent;
import io.sentry.rrweb.RRWebMetaEvent;
import io.sentry.rrweb.RRWebSpanEvent;
import io.sentry.rrweb.RRWebVideoEvent;
import io.sentry.util.MapObjectReader;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class ReplayRecording implements JsonUnknown, JsonSerializable {
   private List<? extends RRWebEvent> payload;
   private Integer segmentId;
   private Map<String, Object> unknown;

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.segmentId, var1.segmentId) || !Objects.equals(this.payload, var1.payload)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public List<? extends RRWebEvent> getPayload() {
      return this.payload;
   }

   public Integer getSegmentId() {
      return this.segmentId;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.segmentId, this.payload);
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.segmentId != null) {
         var1.name("segment_id").value(this.segmentId);
      }

      Map var3 = this.unknown;
      if (var3 != null) {
         for (String var6 : var3.keySet()) {
            Object var4 = this.unknown.get(var6);
            var1.name(var6).value(var2, var4);
         }
      }

      var1.endObject();
      var1.setLenient(true);
      if (this.segmentId != null) {
         var1.jsonValue("\n");
      }

      List var7 = this.payload;
      if (var7 != null) {
         var1.value(var2, var7);
      }

      var1.setLenient(false);
   }

   public void setPayload(List<? extends RRWebEvent> var1) {
      this.payload = var1;
   }

   public void setSegmentId(Integer var1) {
      this.segmentId = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<ReplayRecording> {
      public ReplayRecording deserialize(ObjectReader var1, ILogger var2) throws Exception {
         ReplayRecording var9 = new ReplayRecording();
         var1.beginObject();
         Entry var8 = null;
         HashMap var6 = null;
         Integer var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var10 = var1.nextName();
            var10.hashCode();
            if (!var10.equals("segment_id")) {
               HashMap var7 = var6;
               if (var6 == null) {
                  var7 = new HashMap();
               }

               var1.nextUnknown(var2, var7, var10);
               var6 = var7;
            } else {
               var5 = var1.nextIntegerOrNull();
            }
         }

         var1.endObject();
         var1.setLenient(true);
         List var30 = (List)var1.nextObjectOrNull();
         var1.setLenient(false);
         ArrayList var15 = var8;
         if (var30 != null) {
            ArrayList var25 = new ArrayList(var30.size());
            Iterator var31 = var30.iterator();

            while (true) {
               var15 = var25;
               if (!var31.hasNext()) {
                  break;
               }

               Map var16 = (Map)var31.next();
               if (var16 instanceof Map) {
                  Map var11 = var16;
                  MapObjectReader var13 = new MapObjectReader(var11);

                  for (var8 : var11.entrySet()) {
                     var16 = (String)var8.getKey();
                     var8 = var8.getValue();
                     if (var16.equals("type")) {
                        RRWebEventType var14 = RRWebEventType.values()[(Integer)var8];
                        int var3 = <unrepresentable>.$SwitchMap$io$sentry$rrweb$RRWebEventType[var14.ordinal()];
                        if (var3 != 1) {
                           if (var3 != 2) {
                              if (var3 != 3) {
                                 var2.log(SentryLevel.DEBUG, "Unsupported rrweb event type %s", var14);
                              } else {
                                 Map var28 = (Map)var11.get("data");
                                 var16 = var28;
                                 if (var28 == null) {
                                    var16 = Collections.emptyMap();
                                 }

                                 String var19 = (String)var16.get("tag");
                                 if (var19 != null) {
                                    var19.hashCode();
                                    int var4 = var19.hashCode();
                                    byte var23 = -1;
                                    switch (var4) {
                                       case -226040934:
                                          if (var19.equals("performanceSpan")) {
                                             var23 = 0;
                                          }
                                          break;
                                       case 112202875:
                                          if (var19.equals("video")) {
                                             var23 = 1;
                                          }
                                          break;
                                       case 1106718723:
                                          if (var19.equals("breadcrumb")) {
                                             var23 = 2;
                                          }
                                    }

                                    switch (var23) {
                                       case 0:
                                          var25.add(new RRWebSpanEvent.Deserializer().deserialize(var13, var2));
                                          break;
                                       case 1:
                                          var25.add(new RRWebVideoEvent.Deserializer().deserialize(var13, var2));
                                          break;
                                       case 2:
                                          var25.add(new RRWebBreadcrumbEvent.Deserializer().deserialize(var13, var2));
                                          break;
                                       default:
                                          var2.log(SentryLevel.DEBUG, "Unsupported rrweb event type %s", var14);
                                    }
                                 }
                              }
                           } else {
                              var25.add(new RRWebMetaEvent.Deserializer().deserialize(var13, var2));
                           }
                        } else {
                           Map var29 = (Map)var11.get("data");
                           var16 = var29;
                           if (var29 == null) {
                              var16 = Collections.emptyMap();
                           }

                           Integer var21 = (Integer)var16.get("source");
                           if (var21 != null) {
                              RRWebIncrementalSnapshotEvent.IncrementalSource var22 = RRWebIncrementalSnapshotEvent.IncrementalSource.values()[var21];
                              var3 = <unrepresentable>.$SwitchMap$io$sentry$rrweb$RRWebIncrementalSnapshotEvent$IncrementalSource[var22.ordinal()];
                              if (var3 != 1) {
                                 if (var3 != 2) {
                                    var2.log(SentryLevel.DEBUG, "Unsupported rrweb incremental snapshot type %s", var22);
                                 } else {
                                    var25.add(new RRWebInteractionMoveEvent.Deserializer().deserialize(var13, var2));
                                 }
                              } else {
                                 var25.add(new RRWebInteractionEvent.Deserializer().deserialize(var13, var2));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var9.setSegmentId(var5);
         var9.setPayload(var15);
         var9.setUnknown(var6);
         return var9;
      }
   }

   public static final class JsonKeys {
      public static final String SEGMENT_ID = "segment_id";
   }
}
