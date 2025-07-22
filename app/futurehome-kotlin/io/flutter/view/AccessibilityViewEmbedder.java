package io.flutter.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.AccessibilityRecord;
import androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0;
import io.flutter.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class AccessibilityViewEmbedder {
   private static final String TAG = "AccessibilityBridge";
   private final Map<View, Rect> embeddedViewToDisplayBounds;
   private final SparseArray<AccessibilityViewEmbedder.ViewAndId> flutterIdToOrigin;
   private int nextFlutterId;
   private final Map<AccessibilityViewEmbedder.ViewAndId, Integer> originToFlutterId;
   private final AccessibilityViewEmbedder.ReflectionAccessors reflectionAccessors = new AccessibilityViewEmbedder.ReflectionAccessors();
   private final View rootAccessibilityView;

   AccessibilityViewEmbedder(View var1, int var2) {
      this.flutterIdToOrigin = new SparseArray();
      this.rootAccessibilityView = var1;
      this.nextFlutterId = var2;
      this.originToFlutterId = new HashMap<>();
      this.embeddedViewToDisplayBounds = new HashMap<>();
   }

   private void addChildrenToFlutterNode(AccessibilityNodeInfo var1, View var2, AccessibilityNodeInfo var3) {
      for (int var4 = 0; var4 < var1.getChildCount(); var4++) {
         Long var7 = this.reflectionAccessors.getChildId(var1, var4);
         if (var7 != null) {
            int var6 = AccessibilityViewEmbedder.ReflectionAccessors.getVirtualNodeId(var7);
            AccessibilityViewEmbedder.ViewAndId var8 = new AccessibilityViewEmbedder.ViewAndId(var2, var6);
            int var5;
            if (this.originToFlutterId.containsKey(var8)) {
               var5 = this.originToFlutterId.get(var8);
            } else {
               var5 = this.nextFlutterId++;
               this.cacheVirtualIdMappings(var2, var6, var5);
            }

            var3.addChild(this.rootAccessibilityView, var5);
         }
      }
   }

   private void cacheVirtualIdMappings(View var1, int var2, int var3) {
      AccessibilityViewEmbedder.ViewAndId var4 = new AccessibilityViewEmbedder.ViewAndId(var1, var2);
      this.originToFlutterId.put(var4, var3);
      this.flutterIdToOrigin.put(var3, var4);
   }

   private AccessibilityNodeInfo convertToFlutterNode(AccessibilityNodeInfo var1, int var2, View var3) {
      AccessibilityNodeInfo var4 = AccessibilityNodeInfo.obtain(this.rootAccessibilityView, var2);
      var4.setPackageName(this.rootAccessibilityView.getContext().getPackageName());
      var4.setSource(this.rootAccessibilityView, var2);
      var4.setClassName(var1.getClassName());
      Rect var5 = this.embeddedViewToDisplayBounds.get(var3);
      this.copyAccessibilityFields(var1, var4);
      this.setFlutterNodesTranslateBounds(var1, var5, var4);
      this.addChildrenToFlutterNode(var1, var3, var4);
      this.setFlutterNodeParent(var1, var3, var4);
      return var4;
   }

   private void copyAccessibilityFields(AccessibilityNodeInfo var1, AccessibilityNodeInfo var2) {
      var2.setAccessibilityFocused(var1.isAccessibilityFocused());
      var2.setCheckable(var1.isCheckable());
      var2.setChecked(var1.isChecked());
      var2.setContentDescription(var1.getContentDescription());
      var2.setEnabled(var1.isEnabled());
      var2.setClickable(var1.isClickable());
      var2.setFocusable(var1.isFocusable());
      var2.setFocused(var1.isFocused());
      var2.setLongClickable(var1.isLongClickable());
      var2.setMovementGranularities(var1.getMovementGranularities());
      var2.setPassword(var1.isPassword());
      var2.setScrollable(var1.isScrollable());
      var2.setSelected(var1.isSelected());
      var2.setText(var1.getText());
      var2.setVisibleToUser(var1.isVisibleToUser());
      var2.setEditable(var1.isEditable());
      var2.setCanOpenPopup(var1.canOpenPopup());
      var2.setCollectionInfo(var1.getCollectionInfo());
      var2.setCollectionItemInfo(var1.getCollectionItemInfo());
      var2.setContentInvalid(var1.isContentInvalid());
      var2.setDismissable(var1.isDismissable());
      var2.setInputType(var1.getInputType());
      var2.setLiveRegion(var1.getLiveRegion());
      var2.setMultiLine(var1.isMultiLine());
      var2.setRangeInfo(var1.getRangeInfo());
      var2.setError(var1.getError());
      var2.setMaxTextLength(var1.getMaxTextLength());
      if (VERSION.SDK_INT >= 23) {
         ExternalSyntheticApiModelOutline0.m$3(var2, ExternalSyntheticApiModelOutline0.m$2(var1));
      }

      if (VERSION.SDK_INT >= 24) {
         ExternalSyntheticApiModelOutline0.m(var2, ExternalSyntheticApiModelOutline0.m(var1));
         ExternalSyntheticApiModelOutline0.m$1(var2, ExternalSyntheticApiModelOutline0.m$4(var1));
      }

      if (VERSION.SDK_INT >= 26) {
         ExternalSyntheticApiModelOutline0.m(var2, ExternalSyntheticApiModelOutline0.m(var1));
         ExternalSyntheticApiModelOutline0.m(var2, ExternalSyntheticApiModelOutline0.m$1(var1));
         ExternalSyntheticApiModelOutline0.m(var2, ExternalSyntheticApiModelOutline0.m$3(var1));
      }
   }

   private void setFlutterNodeParent(AccessibilityNodeInfo var1, View var2, AccessibilityNodeInfo var3) {
      Long var5 = this.reflectionAccessors.getParentNodeId(var1);
      if (var5 != null) {
         int var4 = AccessibilityViewEmbedder.ReflectionAccessors.getVirtualNodeId(var5);
         Integer var6 = this.originToFlutterId.get(new AccessibilityViewEmbedder.ViewAndId(var2, var4));
         if (var6 != null) {
            var3.setParent(this.rootAccessibilityView, var6);
         }
      }
   }

   private void setFlutterNodesTranslateBounds(AccessibilityNodeInfo var1, Rect var2, AccessibilityNodeInfo var3) {
      Rect var4 = new Rect();
      var1.getBoundsInParent(var4);
      var3.setBoundsInParent(var4);
      var4 = new Rect();
      var1.getBoundsInScreen(var4);
      var4.offset(var2.left, var2.top);
      var3.setBoundsInScreen(var4);
   }

   public AccessibilityNodeInfo createAccessibilityNodeInfo(int var1) {
      AccessibilityViewEmbedder.ViewAndId var2 = (AccessibilityViewEmbedder.ViewAndId)this.flutterIdToOrigin.get(var1);
      if (var2 == null) {
         return null;
      } else if (!this.embeddedViewToDisplayBounds.containsKey(var2.view)) {
         return null;
      } else if (var2.view.getAccessibilityNodeProvider() == null) {
         return null;
      } else {
         AccessibilityNodeInfo var3 = var2.view.getAccessibilityNodeProvider().createAccessibilityNodeInfo(var2.id);
         return var3 == null ? null : this.convertToFlutterNode(var3, var1, var2.view);
      }
   }

   public Integer getRecordFlutterId(View var1, AccessibilityRecord var2) {
      Long var4 = this.reflectionAccessors.getRecordSourceNodeId(var2);
      if (var4 == null) {
         return null;
      } else {
         int var3 = AccessibilityViewEmbedder.ReflectionAccessors.getVirtualNodeId(var4);
         return this.originToFlutterId.get(new AccessibilityViewEmbedder.ViewAndId(var1, var3));
      }
   }

   public AccessibilityNodeInfo getRootNode(View var1, int var2, Rect var3) {
      AccessibilityNodeInfo var4 = var1.createAccessibilityNodeInfo();
      Long var5 = this.reflectionAccessors.getSourceNodeId(var4);
      if (var5 == null) {
         return null;
      } else {
         this.embeddedViewToDisplayBounds.put(var1, var3);
         this.cacheVirtualIdMappings(var1, AccessibilityViewEmbedder.ReflectionAccessors.getVirtualNodeId(var5), var2);
         return this.convertToFlutterNode(var4, var2, var1);
      }
   }

   public boolean onAccessibilityHoverEvent(int var1, MotionEvent var2) {
      AccessibilityViewEmbedder.ViewAndId var4 = (AccessibilityViewEmbedder.ViewAndId)this.flutterIdToOrigin.get(var1);
      var1 = 0;
      if (var4 == null) {
         return false;
      } else {
         Rect var6 = this.embeddedViewToDisplayBounds.get(var4.view);
         int var3 = var2.getPointerCount();
         PointerProperties[] var5 = new PointerProperties[var3];

         PointerCoords[] var7;
         for (var7 = new PointerCoords[var3]; var1 < var2.getPointerCount(); var1++) {
            PointerProperties var8 = new PointerProperties();
            var5[var1] = var8;
            var2.getPointerProperties(var1, var8);
            PointerCoords var11 = new PointerCoords();
            var2.getPointerCoords(var1, var11);
            PointerCoords var12 = new PointerCoords(var11);
            var7[var1] = var12;
            var12.x = var12.x - var6.left;
            PointerCoords var13 = var7[var1];
            var13.y = var13.y - var6.top;
         }

         var2 = MotionEvent.obtain(
            var2.getDownTime(),
            var2.getEventTime(),
            var2.getAction(),
            var2.getPointerCount(),
            var5,
            var7,
            var2.getMetaState(),
            var2.getButtonState(),
            var2.getXPrecision(),
            var2.getYPrecision(),
            var2.getDeviceId(),
            var2.getEdgeFlags(),
            var2.getSource(),
            var2.getFlags()
         );
         return var4.view.dispatchGenericMotionEvent(var2);
      }
   }

   public boolean performAction(int var1, int var2, Bundle var3) {
      AccessibilityViewEmbedder.ViewAndId var4 = (AccessibilityViewEmbedder.ViewAndId)this.flutterIdToOrigin.get(var1);
      if (var4 == null) {
         return false;
      } else {
         AccessibilityNodeProvider var5 = var4.view.getAccessibilityNodeProvider();
         return var5 == null ? false : var5.performAction(var4.id, var2, var3);
      }
   }

   public View platformViewOfNode(int var1) {
      AccessibilityViewEmbedder.ViewAndId var2 = (AccessibilityViewEmbedder.ViewAndId)this.flutterIdToOrigin.get(var1);
      return var2 == null ? null : var2.view;
   }

   public boolean requestSendAccessibilityEvent(View var1, View var2, AccessibilityEvent var3) {
      AccessibilityEvent var8 = AccessibilityEvent.obtain(var3);
      Long var6 = this.reflectionAccessors.getRecordSourceNodeId(var3);
      if (var6 == null) {
         return false;
      } else {
         int var5 = AccessibilityViewEmbedder.ReflectionAccessors.getVirtualNodeId(var6);
         Integer var7 = this.originToFlutterId.get(new AccessibilityViewEmbedder.ViewAndId(var1, var5));
         Integer var12 = var7;
         if (var7 == null) {
            int var4 = this.nextFlutterId++;
            var12 = var4;
            var12.getClass();
            this.cacheVirtualIdMappings(var1, var5, var4);
         }

         var8.setSource(this.rootAccessibilityView, var12);
         var8.setClassName(var3.getClassName());
         var8.setPackageName(var3.getPackageName());

         for (int var10 = 0; var10 < var8.getRecordCount(); var10++) {
            AccessibilityRecord var9 = var8.getRecord(var10);
            var6 = this.reflectionAccessors.getRecordSourceNodeId(var9);
            if (var6 == null) {
               return false;
            }

            AccessibilityViewEmbedder.ViewAndId var14 = new AccessibilityViewEmbedder.ViewAndId(
               var1, AccessibilityViewEmbedder.ReflectionAccessors.getVirtualNodeId(var6)
            );
            if (!this.originToFlutterId.containsKey(var14)) {
               return false;
            }

            var5 = this.originToFlutterId.get(var14);
            var9.setSource(this.rootAccessibilityView, var5);
         }

         return this.rootAccessibilityView.getParent().requestSendAccessibilityEvent(var2, var8);
      }
   }

   private static class ReflectionAccessors {
      private final Field childNodeIdsField;
      private final Method getChildId;
      private final Method getParentNodeId;
      private final Method getRecordSourceNodeId;
      private final Method getSourceNodeId;
      private final Method longArrayGetIndex;

      private ReflectionAccessors() {
         Method var6 = null;

         Method var4;
         try {
            var4 = AccessibilityNodeInfo.class.getMethod("getSourceNodeId", null);
         } catch (NoSuchMethodException var11) {
            Log.w("AccessibilityBridge", "can't invoke AccessibilityNodeInfo#getSourceNodeId with reflection");
            var4 = null;
         }

         Method var5;
         try {
            var5 = AccessibilityRecord.class.getMethod("getSourceNodeId", null);
         } catch (NoSuchMethodException var10) {
            Log.w("AccessibilityBridge", "can't invoke AccessibiiltyRecord#getSourceNodeId with reflection");
            var5 = null;
         }

         Object var3;
         Method var13;
         Field var14;
         if (VERSION.SDK_INT <= 26) {
            try {
               var13 = AccessibilityNodeInfo.class.getMethod("getParentNodeId", null);
            } catch (NoSuchMethodException var9) {
               Log.w("AccessibilityBridge", "can't invoke getParentNodeId with reflection");
               var13 = null;
            }

            try {
               var2 = AccessibilityNodeInfo.class.getMethod("getChildId", int.class);
            } catch (NoSuchMethodException var8) {
               Log.w("AccessibilityBridge", "can't invoke getChildId with reflection");
               var2 = null;
            }

            var3 = null;
            var6 = var13;
            Object var7 = null;
            var13 = var2;
            var14 = (Field)var7;
         } else {
            label39: {
               try {
                  var14 = AccessibilityNodeInfo.class.getDeclaredField("mChildNodeIds");
                  var14.setAccessible(true);
                  var3 = Class.forName("android.util.LongArray").getMethod("get", int.class);
               } catch (ClassNotFoundException | NoSuchMethodException | NullPointerException | NoSuchFieldException var12) {
                  Log.w("AccessibilityBridge", "can't access childNodeIdsField with reflection");
                  var13 = null;
                  var14 = null;
                  var3 = var14;
                  break label39;
               }

               var13 = null;
            }
         }

         this.getSourceNodeId = var4;
         this.getParentNodeId = var6;
         this.getRecordSourceNodeId = var5;
         this.getChildId = var13;
         this.childNodeIdsField = var14;
         this.longArrayGetIndex = (Method)var3;
      }

      private Long getChildId(AccessibilityNodeInfo var1, int var2) {
         Method var3 = this.getChildId;
         if (var3 != null || this.childNodeIdsField != null && this.longArrayGetIndex != null) {
            if (var3 != null) {
               try {
                  return (Long)var3.invoke(var1, var2);
               } catch (IllegalAccessException var4) {
                  Log.w("AccessibilityBridge", "Failed to access getChildId method.", var4);
               } catch (InvocationTargetException var5) {
                  Log.w("AccessibilityBridge", "The getChildId method threw an exception when invoked.", var5);
               }
            } else {
               try {
                  Long var10 = (Long)this.longArrayGetIndex.invoke(this.childNodeIdsField.get(var1), var2);
                  var10;
                  return var10;
               } catch (IllegalAccessException var6) {
                  Log.w("AccessibilityBridge", "Failed to access longArrayGetIndex method or the childNodeId field.", var6);
                  return null;
               } catch (InvocationTargetException var7) {
                  var9 = var7;
               } catch (ArrayIndexOutOfBoundsException var8) {
                  var9 = var8;
               }

               Log.w("AccessibilityBridge", "The longArrayGetIndex method threw an exception when invoked.", (Throwable)var9);
            }

            return null;
         } else {
            return null;
         }
      }

      private Long getParentNodeId(AccessibilityNodeInfo var1) {
         Method var2 = this.getParentNodeId;
         if (var2 != null) {
            try {
               Long var5 = (Long)var2.invoke(var1, null);
               var5;
               return var5;
            } catch (IllegalAccessException var3) {
               Log.w("AccessibilityBridge", "Failed to access getParentNodeId method.", var3);
            } catch (InvocationTargetException var4) {
               Log.w("AccessibilityBridge", "The getParentNodeId method threw an exception when invoked.", var4);
            }
         }

         return yoinkParentIdFromParcel(var1);
      }

      private Long getRecordSourceNodeId(AccessibilityRecord var1) {
         Method var2 = this.getRecordSourceNodeId;
         if (var2 == null) {
            return null;
         } else {
            try {
               return (Long)var2.invoke(var1, null);
            } catch (IllegalAccessException var3) {
               Log.w("AccessibilityBridge", "Failed to access the getRecordSourceNodeId method.", var3);
            } catch (InvocationTargetException var4) {
               Log.w("AccessibilityBridge", "The getRecordSourceNodeId method threw an exception when invoked.", var4);
            }

            return null;
         }
      }

      private Long getSourceNodeId(AccessibilityNodeInfo var1) {
         Method var2 = this.getSourceNodeId;
         if (var2 == null) {
            return null;
         } else {
            try {
               return (Long)var2.invoke(var1, null);
            } catch (IllegalAccessException var3) {
               Log.w("AccessibilityBridge", "Failed to access getSourceNodeId method.", var3);
            } catch (InvocationTargetException var4) {
               Log.w("AccessibilityBridge", "The getSourceNodeId method threw an exception when invoked.", var4);
            }

            return null;
         }
      }

      private static int getVirtualNodeId(long var0) {
         return (int)(var0 >> 32);
      }

      private static boolean isBitSet(long var0, int var2) {
         boolean var3;
         if ((var0 & 1L << var2) != 0L) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      private static Long yoinkParentIdFromParcel(AccessibilityNodeInfo var0) {
         int var1 = VERSION.SDK_INT;
         Object var4 = null;
         if (var1 < 26) {
            Log.w("AccessibilityBridge", "Unexpected Android version. Unable to find the parent ID.");
            return null;
         } else {
            var0 = AccessibilityNodeInfo.obtain(var0);
            Parcel var5 = Parcel.obtain();
            var5.setDataPosition(0);
            var0.writeToParcel(var5, 0);
            var5.setDataPosition(0);
            long var2 = var5.readLong();
            if (isBitSet(var2, 0)) {
               var5.readInt();
            }

            if (isBitSet(var2, 1)) {
               var5.readLong();
            }

            if (isBitSet(var2, 2)) {
               var5.readInt();
            }

            Long var7 = (Long)var4;
            if (isBitSet(var2, 3)) {
               var7 = var5.readLong();
            }

            var5.recycle();
            return var7;
         }
      }
   }

   private static class ViewAndId {
      final int id;
      final View view;

      private ViewAndId(View var1, int var2) {
         this.view = var1;
         this.id = var2;
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof AccessibilityViewEmbedder.ViewAndId)) {
            return false;
         } else {
            var1 = var1;
            if (this.id != var1.id || !this.view.equals(var1.view)) {
               var2 = false;
            }

            return var2;
         }
      }

      @Override
      public int hashCode() {
         return (this.view.hashCode() + 31) * 31 + this.id;
      }
   }
}
