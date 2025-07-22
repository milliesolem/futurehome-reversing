package io.sentry.android.replay.gestures

import android.view.MotionEvent
import io.sentry.android.replay.ScreenshotRecorderConfig
import io.sentry.rrweb.RRWebIncrementalSnapshotEvent
import io.sentry.rrweb.RRWebInteractionEvent
import io.sentry.rrweb.RRWebInteractionMoveEvent
import io.sentry.rrweb.RRWebInteractionMoveEvent.Position
import io.sentry.transport.ICurrentDateProvider
import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.Map.Entry

public class ReplayGestureConverter(dateProvider: ICurrentDateProvider) {
   private final val currentPositions: LinkedHashMap<Int, ArrayList<Position>>
   private final val dateProvider: ICurrentDateProvider
   private final var lastCapturedMoveEvent: Long
   private final var touchMoveBaseline: Long

   init {
      this.dateProvider = var1;
      this.currentPositions = new LinkedHashMap<>(10);
   }

   public fun convert(event: MotionEvent, recorderConfig: ScreenshotRecorderConfig): List<RRWebIncrementalSnapshotEvent>? {
      var var3: Int = var1.getActionMasked();
      label86:
      if (var3 != 0) {
         if (var3 != 1) {
            if (var3 == 2) {
               val var5: Long = this.dateProvider.getCurrentTimeMillis();
               if (this.lastCapturedMoveEvent != 0L && this.lastCapturedMoveEvent + 50 > var5) {
                  return null;
               }

               this.lastCapturedMoveEvent = var5;
               var var27: java.util.Set = this.currentPositions.keySet();

               for (Integer var10 : var27) {
                  var3 = var1.findPointerIndex(var10);
                  if (var3 != -1) {
                     if (this.touchMoveBaseline == 0L) {
                        this.touchMoveBaseline = var5;
                     }

                     var10 = this.currentPositions.get(var10);
                     val var33: java.util.Collection = var10 as java.util.Collection;
                     val var11: RRWebInteractionMoveEvent.Position = new RRWebInteractionMoveEvent.Position();
                     var11.setX(var1.getX(var3) * var2.getScaleFactorX());
                     var11.setY(var1.getY(var3) * var2.getScaleFactorY());
                     var11.setId(0);
                     var11.setTimeOffset(var5 - this.touchMoveBaseline);
                     var33.add(var11);
                  }
               }

               val var23: Long = var5 - this.touchMoveBaseline;
               val var16: java.util.List;
               if (var5 - this.touchMoveBaseline > 500L) {
                  val var17: ArrayList = new ArrayList(this.currentPositions.size());

                  for (Entry var29 : this.currentPositions.entrySet()) {
                     var3 = (var29.getKey() as java.lang.Number).intValue();
                     val var35: ArrayList = var29.getValue() as ArrayList;
                     if (!var35.isEmpty()) {
                        val var30: java.util.Collection = var17;
                        val var34: RRWebInteractionMoveEvent = new RRWebInteractionMoveEvent();
                        var34.setTimestamp(var5);
                        val var12: java.lang.Iterable = var35;
                        val var36: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var35, 10));

                        for (RRWebInteractionMoveEvent.Position var13 : var12) {
                           var13.setTimeOffset(var13.getTimeOffset() - var23);
                           var36.add(var13);
                        }

                        var34.setPositions(var36 as MutableList<RRWebInteractionMoveEvent.Position>);
                        var34.setPointerId(var3);
                        var30.add(var34);
                        var27 = this.currentPositions.get(var3);
                        (var27 as ArrayList).clear();
                     }
                  }

                  this.touchMoveBaseline = 0L;
                  var16 = var17;
               } else {
                  var16 = null;
               }

               return var16;
            }

            if (var3 == 3) {
               this.currentPositions.clear();
               val var26: RRWebInteractionEvent = new RRWebInteractionEvent();
               var26.setTimestamp(this.dateProvider.getCurrentTimeMillis());
               var26.setX(var1.getX() * var2.getScaleFactorX());
               var26.setY(var1.getY() * var2.getScaleFactorY());
               var26.setId(0);
               var26.setPointerId(0);
               var26.setInteractionType(RRWebInteractionEvent.InteractionType.TouchCancel);
               return CollectionsKt.listOf(var26);
            }

            if (var3 == 5) {
               break label86;
            }

            if (var3 != 6) {
               return null;
            }
         }

         val var4: Int = var1.getPointerId(var1.getActionIndex());
         var3 = var1.findPointerIndex(var4);
         if (var3 == -1) {
            return null;
         }

         this.currentPositions.remove(var4);
         val var24: RRWebInteractionEvent = new RRWebInteractionEvent();
         var24.setTimestamp(this.dateProvider.getCurrentTimeMillis());
         var24.setX(var1.getX(var3) * var2.getScaleFactorX());
         var24.setY(var1.getY(var3) * var2.getScaleFactorY());
         var24.setId(0);
         var24.setPointerId(var4);
         var24.setInteractionType(RRWebInteractionEvent.InteractionType.TouchEnd);
         return CollectionsKt.listOf(var24);
      }

      val var22: Int = var1.getPointerId(var1.getActionIndex());
      var3 = var1.findPointerIndex(var22);
      if (var3 == -1) {
         return null;
      } else {
         this.currentPositions.put(var22, new ArrayList<>(10));
         val var25: RRWebInteractionEvent = new RRWebInteractionEvent();
         var25.setTimestamp(this.dateProvider.getCurrentTimeMillis());
         var25.setX(var1.getX(var3) * var2.getScaleFactorX());
         var25.setY(var1.getY(var3) * var2.getScaleFactorY());
         var25.setId(0);
         var25.setPointerId(var22);
         var25.setInteractionType(RRWebInteractionEvent.InteractionType.TouchStart);
         return CollectionsKt.listOf(var25);
      }
   }

   internal companion object {
      private const val CAPTURE_MOVE_EVENT_THRESHOLD: Int
      private const val TOUCH_MOVE_DEBOUNCE_THRESHOLD: Int
   }
}
