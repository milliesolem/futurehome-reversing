package io.flutter.embedding.android;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.InputDevice.MotionRange;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class AndroidTouchProcessor {
   static final int BYTES_PER_FIELD = 8;
   static final int DEFAULT_HORIZONTAL_SCROLL_FACTOR = 48;
   static final int DEFAULT_VERTICAL_SCROLL_FACTOR = 48;
   private static final Matrix IDENTITY_TRANSFORM = new Matrix();
   private static final int IMPLICIT_VIEW_ID = 0;
   static final int POINTER_DATA_FIELD_COUNT = 36;
   private static final int POINTER_DATA_FLAG_BATCHED = 1;
   private static final String TAG = "AndroidTouchProcessor";
   private int cachedVerticalScrollFactor;
   private final MotionEventTracker motionEventTracker;
   private final Map<Integer, float[]> ongoingPans = new HashMap<>();
   private final FlutterRenderer renderer;
   private final boolean trackMotionEvents;

   public AndroidTouchProcessor(FlutterRenderer var1, boolean var2) {
      this.renderer = var1;
      this.motionEventTracker = MotionEventTracker.getInstance();
      this.trackMotionEvents = var2;
   }

   private void addPointerForIndex(MotionEvent var1, int var2, int var3, int var4, Matrix var5, ByteBuffer var6) {
      this.addPointerForIndex(var1, var2, var3, var4, var5, var6, null);
   }

   private void addPointerForIndex(MotionEvent var1, int var2, int var3, int var4, Matrix var5, ByteBuffer var6, Context var7) {
      int var18 = -1;
      if (var3 != -1) {
         int var20 = var1.getPointerId(var2);
         int var21 = this.getPointerDeviceTypeForToolType(var1.getToolType(var2));
         float var16 = var1.getX(var2);
         float var17 = var1.getY(var2);
         float[] var29 = new float[]{var16, var17};
         var5.mapPoints(var29);
         long var24 = 0L;
         long var22;
         if (var21 == 1) {
            var22 = var1.getButtonState() & 31;
            if (var22 == 0L && var1.getSource() == 8194 && var3 == 4) {
               this.ongoingPans.put(var20, var29);
            }
         } else if (var21 == 2) {
            var22 = var1.getButtonState() >> 4 & 15;
         } else {
            var22 = 0L;
         }

         boolean var28 = this.ongoingPans.containsKey(var20);
         if (var28) {
            var18 = this.getPointerChangeForPanZoom(var3);
            if (var18 == -1) {
               return;
            }
         }

         if (this.trackMotionEvents) {
            var24 = this.motionEventTracker.track(var1).getId();
         }

         byte var19;
         if (var1.getActionMasked() == 8) {
            var19 = 1;
         } else {
            var19 = 0;
         }

         long var26 = var1.getEventTime();
         var6.putLong(var24);
         var6.putLong(var26 * 1000L);
         if (var28) {
            var6.putLong(var18);
            var6.putLong(4L);
         } else {
            var6.putLong(var3);
            var6.putLong(var21);
         }

         var6.putLong(var19);
         var6.putLong(var20);
         var6.putLong(0L);
         if (var28) {
            float[] var31 = this.ongoingPans.get(var20);
            var6.putDouble(var31[0]);
            var6.putDouble(var31[1]);
         } else {
            var6.putDouble(var29[0]);
            var6.putDouble(var29[1]);
         }

         double var8;
         double var10;
         label81: {
            var6.putDouble(0.0);
            var6.putDouble(0.0);
            var6.putLong(var22);
            var6.putLong(0L);
            var6.putLong(0L);
            var6.putDouble(var1.getPressure(var2));
            if (var1.getDevice() != null) {
               MotionRange var32 = var1.getDevice().getMotionRange(2);
               if (var32 != null) {
                  var8 = var32.getMin();
                  var10 = var32.getMax();
                  break label81;
               }
            }

            var8 = 0.0;
            var10 = 1.0;
         }

         var6.putDouble(var8);
         var6.putDouble(var10);
         if (var21 == 2) {
            var6.putDouble(var1.getAxisValue(24, var2));
            var6.putDouble(0.0);
         } else {
            var6.putDouble(0.0);
            var6.putDouble(0.0);
         }

         var6.putDouble(var1.getSize(var2));
         var6.putDouble(var1.getToolMajor(var2));
         var6.putDouble(var1.getToolMinor(var2));
         var6.putDouble(0.0);
         var6.putDouble(0.0);
         var6.putDouble(var1.getAxisValue(8, var2));
         if (var21 == 2) {
            var6.putDouble(var1.getAxisValue(25, var2));
         } else {
            var6.putDouble(0.0);
         }

         var6.putLong(var4);
         if (var19 == 1) {
            if (var7 != null) {
               var10 = this.getHorizontalScrollFactor(var7);
               var8 = this.getVerticalScrollFactor(var7);
            } else {
               var10 = 48.0;
               var8 = 48.0;
            }

            double var14 = -var1.getAxisValue(10, var2);
            double var12 = -var1.getAxisValue(9, var2);
            var6.putDouble(var10 * var14);
            var6.putDouble(var8 * var12);
         } else {
            var6.putDouble(0.0);
            var6.putDouble(0.0);
         }

         if (var28) {
            float[] var30 = this.ongoingPans.get(var20);
            var6.putDouble(var29[0] - var30[0]);
            var6.putDouble(var29[1] - var30[1]);
         } else {
            var6.putDouble(0.0);
            var6.putDouble(0.0);
         }

         var6.putDouble(0.0);
         var6.putDouble(0.0);
         var6.putDouble(1.0);
         var6.putDouble(0.0);
         var6.putLong(0L);
         if (var28 && var18 == 9) {
            this.ongoingPans.remove(var20);
         }
      }
   }

   private float getHorizontalScrollFactor(Context var1) {
      return VERSION.SDK_INT >= 26
         ? AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(ViewConfiguration.get(var1))
         : this.getVerticalScrollFactorPre26(var1);
   }

   private int getPointerChangeForAction(int var1) {
      if (var1 == 0) {
         return 4;
      } else if (var1 == 1) {
         return 6;
      } else if (var1 == 5) {
         return 4;
      } else if (var1 == 6) {
         return 6;
      } else if (var1 == 2) {
         return 5;
      } else if (var1 == 7) {
         return 3;
      } else if (var1 == 3) {
         return 0;
      } else {
         return var1 == 8 ? 3 : -1;
      }
   }

   private int getPointerChangeForPanZoom(int var1) {
      if (var1 == 4) {
         return 7;
      } else if (var1 == 5) {
         return 8;
      } else {
         return var1 != 6 && var1 != 0 ? -1 : 9;
      }
   }

   private int getPointerDeviceTypeForToolType(int var1) {
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               return var1 != 4 ? 5 : 3;
            } else {
               return 1;
            }
         } else {
            return 2;
         }
      } else {
         return 0;
      }
   }

   private float getVerticalScrollFactor(Context var1) {
      return VERSION.SDK_INT >= 26 ? this.getVerticalScrollFactorAbove26(var1) : this.getVerticalScrollFactorPre26(var1);
   }

   private float getVerticalScrollFactorAbove26(Context var1) {
      return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(ViewConfiguration.get(var1));
   }

   private int getVerticalScrollFactorPre26(Context var1) {
      if (this.cachedVerticalScrollFactor == 0) {
         TypedValue var2 = new TypedValue();
         if (!var1.getTheme().resolveAttribute(16842829, var2, true)) {
            return 48;
         }

         this.cachedVerticalScrollFactor = (int)var2.getDimension(var1.getResources().getDisplayMetrics());
      }

      return this.cachedVerticalScrollFactor;
   }

   public boolean onGenericMotionEvent(MotionEvent var1, Context var2) {
      boolean var4 = var1.isFromSource(2);
      boolean var3;
      if (var1.getActionMasked() != 7 && var1.getActionMasked() != 8) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      if (var4 && var3) {
         var3 = this.getPointerChangeForAction(var1.getActionMasked());
         ByteBuffer var5 = ByteBuffer.allocateDirect(var1.getPointerCount() * 288);
         var5.order(ByteOrder.LITTLE_ENDIAN);
         this.addPointerForIndex(var1, var1.getActionIndex(), var3, 0, IDENTITY_TRANSFORM, var5, var2);
         if (var5.position() % 288 == 0) {
            this.renderer.dispatchPointerDataPacket(var5, var5.position());
            return true;
         } else {
            throw new AssertionError("Packet position is not on field boundary.");
         }
      } else {
         return false;
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      return this.onTouchEvent(var1, IDENTITY_TRANSFORM);
   }

   public boolean onTouchEvent(MotionEvent var1, Matrix var2) {
      int var4 = var1.getActionMasked();
      int var6 = this.getPointerChangeForAction(var1.getActionMasked());
      boolean var3;
      if (var4 != 0 && var4 != 5) {
         var3 = false;
      } else {
         var3 = true;
      }

      boolean var11;
      if (var3 || var4 != 1 && var4 != 6) {
         var11 = false;
      } else {
         var11 = true;
      }

      int var5 = this.getPointerDeviceTypeForToolType(var1.getToolType(var1.getActionIndex()));
      byte var12;
      if (var11 && var5 == 0) {
         var12 = 1;
      } else {
         var12 = 0;
      }

      int var7 = var1.getPointerCount();
      ByteBuffer var8 = ByteBuffer.allocateDirect((var7 + var12) * 288);
      var8.order(ByteOrder.LITTLE_ENDIAN);
      if (var3) {
         this.addPointerForIndex(var1, var1.getActionIndex(), var6, 0, var2, var8);
      } else if (var11) {
         for (int var9 = 0; var9 < var7; var9++) {
            if (var9 != var1.getActionIndex() && var1.getToolType(var9) == 1) {
               this.addPointerForIndex(var1, var9, 5, 1, var2, var8);
            }
         }

         this.addPointerForIndex(var1, var1.getActionIndex(), var6, 0, var2, var8);
         if (var12 != 0) {
            this.addPointerForIndex(var1, var1.getActionIndex(), 2, 0, var2, var8);
         }
      } else {
         for (int var10 = 0; var10 < var7; var10++) {
            this.addPointerForIndex(var1, var10, var6, 0, var2, var8);
         }
      }

      if (var8.position() % 288 == 0) {
         this.renderer.dispatchPointerDataPacket(var8, var8.position());
         return true;
      } else {
         throw new AssertionError("Packet position is not on field boundary");
      }
   }

   public @interface PointerChange {
      int ADD = 1;
      int CANCEL = 0;
      int DOWN = 4;
      int HOVER = 3;
      int MOVE = 5;
      int PAN_ZOOM_END = 9;
      int PAN_ZOOM_START = 7;
      int PAN_ZOOM_UPDATE = 8;
      int REMOVE = 2;
      int UP = 6;
   }

   public @interface PointerDeviceKind {
      int INVERTED_STYLUS = 3;
      int MOUSE = 1;
      int STYLUS = 2;
      int TOUCH = 0;
      int TRACKPAD = 4;
      int UNKNOWN = 5;
   }

   public @interface PointerSignalKind {
      int NONE = 0;
      int SCALE = 3;
      int SCROLL = 1;
      int SCROLL_INERTIA_CANCEL = 2;
      int UNKNOWN = 4;
   }
}
