package io.flutter.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.LocaleSpan;
import android.text.style.TtsSpan.Builder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener;
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0;
import io.flutter.Log;
import io.flutter.embedding.engine.systemchannels.AccessibilityChannel;
import io.flutter.plugin.platform.PlatformViewsAccessibilityDelegate;
import io.flutter.util.Predicate;
import io.flutter.util.ViewUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessibilityBridge extends AccessibilityNodeProvider {
   private static final int ACTION_SHOW_ON_SCREEN = 16908342;
   private static final int BOLD_TEXT_WEIGHT_ADJUSTMENT = 300;
   private static final float DEFAULT_TRANSITION_ANIMATION_SCALE = 1.0F;
   private static final float DISABLED_TRANSITION_ANIMATION_SCALE = 0.0F;
   private static int FIRST_RESOURCE_ID = 267386881;
   private static final int FOCUSABLE_FLAGS = AccessibilityBridge.Flag.HAS_CHECKED_STATE.value
      | AccessibilityBridge.Flag.IS_CHECKED.value
      | AccessibilityBridge.Flag.IS_SELECTED.value
      | AccessibilityBridge.Flag.IS_TEXT_FIELD.value
      | AccessibilityBridge.Flag.IS_FOCUSED.value
      | AccessibilityBridge.Flag.HAS_ENABLED_STATE.value
      | AccessibilityBridge.Flag.IS_ENABLED.value
      | AccessibilityBridge.Flag.IS_IN_MUTUALLY_EXCLUSIVE_GROUP.value
      | AccessibilityBridge.Flag.HAS_TOGGLED_STATE.value
      | AccessibilityBridge.Flag.IS_TOGGLED.value
      | AccessibilityBridge.Flag.IS_FOCUSABLE.value
      | AccessibilityBridge.Flag.IS_SLIDER.value;
   private static final int MIN_ENGINE_GENERATED_NODE_ID = 65536;
   private static final int ROOT_NODE_ID = 0;
   private static final int SCROLLABLE_ACTIONS = AccessibilityBridge.Action.SCROLL_RIGHT.value
      | AccessibilityBridge.Action.SCROLL_LEFT.value
      | AccessibilityBridge.Action.SCROLL_UP.value
      | AccessibilityBridge.Action.SCROLL_DOWN.value;
   private static final float SCROLL_EXTENT_FOR_INFINITY = 100000.0F;
   private static final float SCROLL_POSITION_CAP_FOR_INFINITY = 70000.0F;
   private static final String TAG = "AccessibilityBridge";
   static int systemAction = AccessibilityBridge.Action.DID_GAIN_ACCESSIBILITY_FOCUS.value
      & AccessibilityBridge.Action.DID_LOSE_ACCESSIBILITY_FOCUS.value
      & AccessibilityBridge.Action.SHOW_ON_SCREEN.value;
   private final AccessibilityChannel accessibilityChannel;
   private int accessibilityFeatureFlags;
   private AccessibilityBridge.SemanticsNode accessibilityFocusedSemanticsNode;
   private final AccessibilityManager accessibilityManager;
   private final AccessibilityChannel.AccessibilityMessageHandler accessibilityMessageHandler;
   private final AccessibilityStateChangeListener accessibilityStateChangeListener;
   private final AccessibilityViewEmbedder accessibilityViewEmbedder;
   private boolean accessibleNavigation;
   private final ContentObserver animationScaleObserver;
   private final ContentResolver contentResolver;
   private final Map<Integer, AccessibilityBridge.CustomAccessibilityAction> customAccessibilityActions;
   private Integer embeddedAccessibilityFocusedNodeId;
   private Integer embeddedInputFocusedNodeId;
   private final List<Integer> flutterNavigationStack;
   private final Map<Integer, AccessibilityBridge.SemanticsNode> flutterSemanticsTree = new HashMap<>();
   private AccessibilityBridge.SemanticsNode hoveredObject;
   private AccessibilityBridge.SemanticsNode inputFocusedSemanticsNode;
   private boolean isReleased;
   private AccessibilityBridge.SemanticsNode lastInputFocusedSemanticsNode;
   private Integer lastLeftFrameInset;
   private AccessibilityBridge.OnAccessibilityChangeListener onAccessibilityChangeListener;
   private final PlatformViewsAccessibilityDelegate platformViewsAccessibilityDelegate;
   private int previousRouteId;
   private final View rootAccessibilityView;
   private final TouchExplorationStateChangeListener touchExplorationStateChangeListener;

   public AccessibilityBridge(View var1, AccessibilityChannel var2, AccessibilityManager var3, ContentResolver var4, PlatformViewsAccessibilityDelegate var5) {
      this(var1, var2, var3, var4, new AccessibilityViewEmbedder(var1, 65536), var5);
   }

   public AccessibilityBridge(
      View var1,
      AccessibilityChannel var2,
      AccessibilityManager var3,
      ContentResolver var4,
      AccessibilityViewEmbedder var5,
      PlatformViewsAccessibilityDelegate var6
   ) {
      this.customAccessibilityActions = new HashMap<>();
      this.accessibilityFeatureFlags = 0;
      this.flutterNavigationStack = new ArrayList<>();
      this.previousRouteId = 0;
      this.lastLeftFrameInset = 0;
      this.accessibleNavigation = false;
      this.isReleased = false;
      this.accessibilityMessageHandler = new AccessibilityChannel.AccessibilityMessageHandler(this) {
         final AccessibilityBridge this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void announce(String var1) {
            this.this$0.rootAccessibilityView.announceForAccessibility(var1);
         }

         @Override
         public void onFocus(int var1) {
            this.this$0.sendAccessibilityEvent(var1, 8);
         }

         @Override
         public void onLongPress(int var1) {
            this.this$0.sendAccessibilityEvent(var1, 2);
         }

         @Override
         public void onTap(int var1) {
            this.this$0.sendAccessibilityEvent(var1, 1);
         }

         @Override
         public void onTooltip(String var1) {
            if (VERSION.SDK_INT < 28) {
               AccessibilityEvent var2x = this.this$0.obtainAccessibilityEvent(0, 32);
               var2x.getText().add(var1);
               this.this$0.sendAccessibilityEvent(var2x);
            }
         }

         @Override
         public void updateCustomAccessibilityActions(ByteBuffer var1, String[] var2x) {
            var1.order(ByteOrder.LITTLE_ENDIAN);
            this.this$0.updateCustomAccessibilityActions(var1, var2x);
         }

         @Override
         public void updateSemantics(ByteBuffer var1, String[] var2x, ByteBuffer[] var3x) {
            var1.order(ByteOrder.LITTLE_ENDIAN);
            int var5x = var3x.length;

            for (int var4x = 0; var4x < var5x; var4x++) {
               var3x[var4x].order(ByteOrder.LITTLE_ENDIAN);
            }

            this.this$0.updateSemantics(var1, var2x, var3x);
         }
      };
      AccessibilityStateChangeListener var8 = new AccessibilityStateChangeListener(this) {
         final AccessibilityBridge this$0;

         {
            this.this$0 = var1;
         }

         public void onAccessibilityStateChanged(boolean var1) {
            if (!this.this$0.isReleased) {
               if (var1) {
                  this.this$0.accessibilityChannel.setAccessibilityMessageHandler(this.this$0.accessibilityMessageHandler);
                  this.this$0.accessibilityChannel.onAndroidAccessibilityEnabled();
               } else {
                  this.this$0.setAccessibleNavigation(false);
                  this.this$0.accessibilityChannel.setAccessibilityMessageHandler(null);
                  this.this$0.accessibilityChannel.onAndroidAccessibilityDisabled();
               }

               if (this.this$0.onAccessibilityChangeListener != null) {
                  this.this$0.onAccessibilityChangeListener.onAccessibilityChanged(var1, this.this$0.accessibilityManager.isTouchExplorationEnabled());
               }
            }
         }
      };
      this.accessibilityStateChangeListener = var8;
      ContentObserver var7 = new ContentObserver(this, new Handler()) {
         final AccessibilityBridge this$0;

         {
            this.this$0 = var1;
         }

         public void onChange(boolean var1) {
            this.onChange(var1, null);
         }

         public void onChange(boolean var1, Uri var2x) {
            if (!this.this$0.isReleased) {
               if (Global.getFloat(this.this$0.contentResolver, "transition_animation_scale", 1.0F) == 0.0F) {
                  AccessibilityBridge.access$1176(this.this$0, AccessibilityBridge.AccessibilityFeature.DISABLE_ANIMATIONS.value);
               } else {
                  AccessibilityBridge.access$1172(this.this$0, ~AccessibilityBridge.AccessibilityFeature.DISABLE_ANIMATIONS.value);
               }

               this.this$0.sendLatestAccessibilityFlagsToFlutter();
            }
         }
      };
      this.animationScaleObserver = var7;
      this.rootAccessibilityView = var1;
      this.accessibilityChannel = var2;
      this.accessibilityManager = var3;
      this.contentResolver = var4;
      this.accessibilityViewEmbedder = var5;
      this.platformViewsAccessibilityDelegate = var6;
      var8.onAccessibilityStateChanged(var3.isEnabled());
      var3.addAccessibilityStateChangeListener(var8);
      TouchExplorationStateChangeListener var9 = new TouchExplorationStateChangeListener(this, var3) {
         final AccessibilityBridge this$0;
         final AccessibilityManager val$accessibilityManager;

         {
            this.this$0 = var1;
            this.val$accessibilityManager = var2x;
         }

         public void onTouchExplorationStateChanged(boolean var1) {
            if (!this.this$0.isReleased) {
               if (!var1) {
                  this.this$0.setAccessibleNavigation(false);
                  this.this$0.onTouchExplorationExit();
               }

               if (this.this$0.onAccessibilityChangeListener != null) {
                  this.this$0.onAccessibilityChangeListener.onAccessibilityChanged(this.val$accessibilityManager.isEnabled(), var1);
               }
            }
         }
      };
      this.touchExplorationStateChangeListener = var9;
      var9.onTouchExplorationStateChanged(var3.isTouchExplorationEnabled());
      var3.addTouchExplorationStateChangeListener(var9);
      var7.onChange(false);
      var4.registerContentObserver(Global.getUriFor("transition_animation_scale"), false, var7);
      if (VERSION.SDK_INT >= 31) {
         this.setBoldTextFlag();
      }

      var6.attachAccessibilityBridge(this);
   }

   private AccessibilityEvent createTextChangedEvent(int var1, String var2, String var3) {
      AccessibilityEvent var6 = this.obtainAccessibilityEvent(var1, 16);
      var6.setBeforeText(var2);
      var6.getText().add(var3);
      var1 = 0;

      while (var1 < var2.length() && var1 < var3.length() && var2.charAt(var1) == var3.charAt(var1)) {
         var1++;
      }

      if (var1 >= var2.length() && var1 >= var3.length()) {
         return null;
      } else {
         var6.setFromIndex(var1);
         int var5 = var2.length() - 1;

         int var4;
         for (var4 = var3.length() - 1; var5 >= var1 && var4 >= var1 && var2.charAt(var5) == var3.charAt(var4); var4--) {
            var5--;
         }

         var6.setRemovedCount(var5 - var1 + 1);
         var6.setAddedCount(var4 - var1 + 1);
         return var6;
      }
   }

   private boolean doesLayoutInDisplayCutoutModeRequireLeftInset() {
      Activity var4 = ViewUtils.getActivity(this.rootAccessibilityView.getContext());
      boolean var3 = false;
      boolean var2 = var3;
      if (var4 != null) {
         if (var4.getWindow() == null) {
            var2 = var3;
         } else {
            int var1 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var4.getWindow().getAttributes());
            if (var1 != 2 && var1 != 0) {
               return var3;
            }

            var2 = true;
         }
      }

      return var2;
   }

   private Rect getBoundsInScreen(Rect var1) {
      Rect var2 = new Rect(var1);
      int[] var3 = new int[2];
      this.rootAccessibilityView.getLocationOnScreen(var3);
      var2.offset(var3[0], var3[1]);
      return var2;
   }

   private AccessibilityBridge.CustomAccessibilityAction getOrCreateAccessibilityAction(int var1) {
      AccessibilityBridge.CustomAccessibilityAction var3 = this.customAccessibilityActions.get(var1);
      AccessibilityBridge.CustomAccessibilityAction var2 = var3;
      if (var3 == null) {
         var2 = new AccessibilityBridge.CustomAccessibilityAction();
         var2.id = var1;
         var2.resourceId = FIRST_RESOURCE_ID + var1;
         this.customAccessibilityActions.put(var1, var2);
      }

      return var2;
   }

   private AccessibilityBridge.SemanticsNode getOrCreateSemanticsNode(int var1) {
      AccessibilityBridge.SemanticsNode var3 = this.flutterSemanticsTree.get(var1);
      AccessibilityBridge.SemanticsNode var2 = var3;
      if (var3 == null) {
         var2 = new AccessibilityBridge.SemanticsNode(this);
         var2.id = var1;
         this.flutterSemanticsTree.put(var1, var2);
      }

      return var2;
   }

   private AccessibilityBridge.SemanticsNode getRootSemanticsNode() {
      return this.flutterSemanticsTree.get(0);
   }

   private void handleTouchExploration(float var1, float var2, boolean var3) {
      if (!this.flutterSemanticsTree.isEmpty()) {
         AccessibilityBridge.SemanticsNode var4 = this.getRootSemanticsNode().hitTest(new float[]{var1, var2, 0.0F, 1.0F}, var3);
         if (var4 != this.hoveredObject) {
            if (var4 != null) {
               this.sendAccessibilityEvent(var4.id, 128);
            }

            AccessibilityBridge.SemanticsNode var5 = this.hoveredObject;
            if (var5 != null) {
               this.sendAccessibilityEvent(var5.id, 256);
            }

            this.hoveredObject = var4;
         }
      }
   }

   private boolean isImportant(AccessibilityBridge.SemanticsNode var1) {
      boolean var3 = var1.hasFlag(AccessibilityBridge.Flag.SCOPES_ROUTE);
      boolean var2 = false;
      if (var3) {
         return false;
      } else if (var1.getValueLabelHint() != null) {
         return true;
      } else {
         if ((var1.actions & ~systemAction) != 0) {
            var2 = true;
         }

         return var2;
      }
   }

   private AccessibilityEvent obtainAccessibilityEvent(int var1, int var2) {
      AccessibilityEvent var3 = this.obtainAccessibilityEvent(var2);
      var3.setPackageName(this.rootAccessibilityView.getContext().getPackageName());
      var3.setSource(this.rootAccessibilityView, var1);
      return var3;
   }

   private void onTouchExplorationExit() {
      AccessibilityBridge.SemanticsNode var1 = this.hoveredObject;
      if (var1 != null) {
         this.sendAccessibilityEvent(var1.id, 256);
         this.hoveredObject = null;
      }
   }

   private void onWindowNameChange(AccessibilityBridge.SemanticsNode var1) {
      String var3 = var1.getRouteName();
      String var2 = var3;
      if (var3 == null) {
         var2 = " ";
      }

      if (VERSION.SDK_INT >= 28) {
         this.setAccessibilityPaneTitle(var2);
      } else {
         AccessibilityEvent var4 = this.obtainAccessibilityEvent(var1.id, 32);
         var4.getText().add(var2);
         this.sendAccessibilityEvent(var4);
      }
   }

   private boolean performCursorMoveAction(AccessibilityBridge.SemanticsNode var1, int var2, Bundle var3, boolean var4) {
      int var5 = var3.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
      boolean var8 = var3.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN");
      int var6 = var1.textSelectionBase;
      int var7 = var1.textSelectionExtent;
      this.predictCursorMovement(var1, var5, var4, var8);
      if (var6 != var1.textSelectionBase || var7 != var1.textSelectionExtent) {
         String var10;
         if (var1.value != null) {
            var10 = var1.value;
         } else {
            var10 = "";
         }

         AccessibilityEvent var9 = this.obtainAccessibilityEvent(var1.id, 8192);
         var9.getText().add(var10);
         var9.setFromIndex(var1.textSelectionBase);
         var9.setToIndex(var1.textSelectionExtent);
         var9.setItemCount(var10.length());
         this.sendAccessibilityEvent(var9);
      }

      if (var5 != 1) {
         if (var5 != 2) {
            if (var5 == 4 || var5 == 8 || var5 == 16) {
               return true;
            }
         } else {
            if (var4 && var1.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_FORWARD_BY_WORD)) {
               this.accessibilityChannel.dispatchSemanticsAction(var2, AccessibilityBridge.Action.MOVE_CURSOR_FORWARD_BY_WORD, var8);
               return true;
            }

            if (!var4 && var1.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_BACKWARD_BY_WORD)) {
               this.accessibilityChannel.dispatchSemanticsAction(var2, AccessibilityBridge.Action.MOVE_CURSOR_BACKWARD_BY_WORD, var8);
               return true;
            }
         }
      } else {
         if (var4 && var1.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_FORWARD_BY_CHARACTER)) {
            this.accessibilityChannel.dispatchSemanticsAction(var2, AccessibilityBridge.Action.MOVE_CURSOR_FORWARD_BY_CHARACTER, var8);
            return true;
         }

         if (!var4 && var1.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_BACKWARD_BY_CHARACTER)) {
            this.accessibilityChannel.dispatchSemanticsAction(var2, AccessibilityBridge.Action.MOVE_CURSOR_BACKWARD_BY_CHARACTER, var8);
            return true;
         }
      }

      return false;
   }

   private boolean performSetText(AccessibilityBridge.SemanticsNode var1, int var2, Bundle var3) {
      String var4;
      if (var3 != null && var3.containsKey("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE")) {
         var4 = var3.getString("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE");
      } else {
         var4 = "";
      }

      this.accessibilityChannel.dispatchSemanticsAction(var2, AccessibilityBridge.Action.SET_TEXT, var4);
      var1.value = var4;
      var1.valueAttributes = null;
      return true;
   }

   private void predictCursorMovement(AccessibilityBridge.SemanticsNode var1, int var2, boolean var3, boolean var4) {
      if (var1.textSelectionExtent >= 0 && var1.textSelectionBase >= 0) {
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 != 4) {
                  if (var2 == 8 || var2 == 16) {
                     if (var3) {
                        var1.textSelectionExtent = var1.value.length();
                     } else {
                        var1.textSelectionExtent = 0;
                     }
                  }
               } else if (var3 && var1.textSelectionExtent < var1.value.length()) {
                  Matcher var8 = Pattern.compile("(?!^)(\\n)").matcher(var1.value.substring(var1.textSelectionExtent));
                  if (var8.find()) {
                     AccessibilityBridge.SemanticsNode.access$2212(var1, var8.start(1));
                  } else {
                     var1.textSelectionExtent = var1.value.length();
                  }
               } else if (!var3 && var1.textSelectionExtent > 0) {
                  Matcher var7 = Pattern.compile("(?s:.*)(\\n)").matcher(var1.value.substring(0, var1.textSelectionExtent));
                  if (var7.find()) {
                     var1.textSelectionExtent = var7.start(1);
                  } else {
                     var1.textSelectionExtent = 0;
                  }
               }
            } else if (var3 && var1.textSelectionExtent < var1.value.length()) {
               Matcher var6 = Pattern.compile("\\p{L}(\\b)").matcher(var1.value.substring(var1.textSelectionExtent));
               var6.find();
               if (var6.find()) {
                  AccessibilityBridge.SemanticsNode.access$2212(var1, var6.start(1));
               } else {
                  var1.textSelectionExtent = var1.value.length();
               }
            } else if (!var3 && var1.textSelectionExtent > 0) {
               Matcher var5 = Pattern.compile("(?s:.*)(\\b)\\p{L}").matcher(var1.value.substring(0, var1.textSelectionExtent));
               if (var5.find()) {
                  var1.textSelectionExtent = var5.start(1);
               }
            }
         } else if (var3 && var1.textSelectionExtent < var1.value.length()) {
            AccessibilityBridge.SemanticsNode.access$2212(var1, 1);
         } else if (!var3 && var1.textSelectionExtent > 0) {
            AccessibilityBridge.SemanticsNode.access$2220(var1, 1);
         }

         if (!var4) {
            var1.textSelectionBase = var1.textSelectionExtent;
         }
      }
   }

   private void sendAccessibilityEvent(AccessibilityEvent var1) {
      if (this.accessibilityManager.isEnabled()) {
         this.rootAccessibilityView.getParent().requestSendAccessibilityEvent(this.rootAccessibilityView, var1);
      }
   }

   private void sendLatestAccessibilityFlagsToFlutter() {
      this.accessibilityChannel.setAccessibilityFeatures(this.accessibilityFeatureFlags);
   }

   private void sendWindowContentChangeEvent(int var1) {
      AccessibilityEvent var2 = this.obtainAccessibilityEvent(var1, 2048);
      var2.setContentChangeTypes(1);
      this.sendAccessibilityEvent(var2);
   }

   private void setAccessibilityPaneTitle(String var1) {
      AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.rootAccessibilityView, var1);
   }

   private void setAccessibleNavigation(boolean var1) {
      if (this.accessibleNavigation != var1) {
         this.accessibleNavigation = var1;
         if (var1) {
            this.accessibilityFeatureFlags = this.accessibilityFeatureFlags | AccessibilityBridge.AccessibilityFeature.ACCESSIBLE_NAVIGATION.value;
         } else {
            this.accessibilityFeatureFlags = this.accessibilityFeatureFlags & ~AccessibilityBridge.AccessibilityFeature.ACCESSIBLE_NAVIGATION.value;
         }

         this.sendLatestAccessibilityFlagsToFlutter();
      }
   }

   private void setBoldTextFlag() {
      View var2 = this.rootAccessibilityView;
      if (var2 != null && var2.getResources() != null) {
         int var1 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.rootAccessibilityView.getResources().getConfiguration());
         if (var1 != Integer.MAX_VALUE && var1 >= 300) {
            this.accessibilityFeatureFlags = this.accessibilityFeatureFlags | AccessibilityBridge.AccessibilityFeature.BOLD_TEXT.value;
         } else {
            this.accessibilityFeatureFlags = this.accessibilityFeatureFlags & ~AccessibilityBridge.AccessibilityFeature.BOLD_TEXT.value;
         }

         this.sendLatestAccessibilityFlagsToFlutter();
      }
   }

   private boolean shouldSetCollectionInfo(AccessibilityBridge.SemanticsNode var1) {
      boolean var2;
      if (var1.scrollChildren <= 0
         || !AccessibilityBridge.SemanticsNode.nullableHasAncestor(
               this.accessibilityFocusedSemanticsNode, new AccessibilityBridge$$ExternalSyntheticLambda4(var1)
            )
            && AccessibilityBridge.SemanticsNode.nullableHasAncestor(
               this.accessibilityFocusedSemanticsNode, new AccessibilityBridge$$ExternalSyntheticLambda3()
            )) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private void willRemoveSemanticsNode(AccessibilityBridge.SemanticsNode var1) {
      var1.parent = null;
      if (var1.platformViewId != -1) {
         Integer var2 = this.embeddedAccessibilityFocusedNodeId;
         if (var2 != null
            && this.accessibilityViewEmbedder.platformViewOfNode(var2) == this.platformViewsAccessibilityDelegate.getPlatformViewById(var1.platformViewId)) {
            this.sendAccessibilityEvent(this.embeddedAccessibilityFocusedNodeId, 65536);
            this.embeddedAccessibilityFocusedNodeId = null;
         }
      }

      if (var1.platformViewId != -1) {
         View var3 = this.platformViewsAccessibilityDelegate.getPlatformViewById(var1.platformViewId);
         if (var3 != null) {
            var3.setImportantForAccessibility(4);
         }
      }

      AccessibilityBridge.SemanticsNode var4 = this.accessibilityFocusedSemanticsNode;
      if (var4 == var1) {
         this.sendAccessibilityEvent(var4.id, 65536);
         this.accessibilityFocusedSemanticsNode = null;
      }

      if (this.inputFocusedSemanticsNode == var1) {
         this.inputFocusedSemanticsNode = null;
      }

      if (this.hoveredObject == var1) {
         this.hoveredObject = null;
      }
   }

   public AccessibilityNodeInfo createAccessibilityNodeInfo(int var1) {
      boolean var5 = true;
      this.setAccessibleNavigation(true);
      if (var1 >= 65536) {
         return this.accessibilityViewEmbedder.createAccessibilityNodeInfo(var1);
      } else if (var1 == -1) {
         AccessibilityNodeInfo var28 = this.obtainAccessibilityNodeInfo(this.rootAccessibilityView);
         this.rootAccessibilityView.onInitializeAccessibilityNodeInfo(var28);
         if (this.flutterSemanticsTree.containsKey(0)) {
            var28.addChild(this.rootAccessibilityView, 0);
         }

         if (VERSION.SDK_INT >= 24) {
            ExternalSyntheticApiModelOutline0.m$1(var28, false);
         }

         return var28;
      } else {
         AccessibilityBridge.SemanticsNode var12 = this.flutterSemanticsTree.get(var1);
         if (var12 == null) {
            return null;
         } else if (var12.platformViewId != -1 && this.platformViewsAccessibilityDelegate.usesVirtualDisplay(var12.platformViewId)) {
            View var27 = this.platformViewsAccessibilityDelegate.getPlatformViewById(var12.platformViewId);
            if (var27 == null) {
               return null;
            } else {
               Rect var33 = var12.getGlobalRect();
               return this.accessibilityViewEmbedder.getRootNode(var27, var12.id, var33);
            }
         } else {
            AccessibilityNodeInfo var11 = this.obtainAccessibilityNodeInfo(this.rootAccessibilityView, var1);
            if (VERSION.SDK_INT >= 24) {
               ExternalSyntheticApiModelOutline0.m$1(var11, this.isImportant(var12));
            }

            String var10 = "";
            var11.setViewIdResourceName("");
            if (var12.identifier != null) {
               var11.setViewIdResourceName(var12.identifier);
            }

            var11.setPackageName(this.rootAccessibilityView.getContext().getPackageName());
            var11.setClassName("android.view.View");
            var11.setSource(this.rootAccessibilityView, var1);
            var11.setFocusable(var12.isFocusable());
            AccessibilityBridge.SemanticsNode var8 = this.inputFocusedSemanticsNode;
            if (var8 != null) {
               boolean var4;
               if (var8.id == var1) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               var11.setFocused(var4);
            }

            var8 = this.accessibilityFocusedSemanticsNode;
            if (var8 != null) {
               boolean var16;
               if (var8.id == var1) {
                  var16 = true;
               } else {
                  var16 = false;
               }

               var11.setAccessibilityFocused(var16);
            }

            if (var12.hasFlag(AccessibilityBridge.Flag.IS_TEXT_FIELD)) {
               var11.setPassword(var12.hasFlag(AccessibilityBridge.Flag.IS_OBSCURED));
               if (!var12.hasFlag(AccessibilityBridge.Flag.IS_READ_ONLY)) {
                  var11.setClassName("android.widget.EditText");
               }

               var11.setEditable(var12.hasFlag(AccessibilityBridge.Flag.IS_READ_ONLY) ^ true);
               if (var12.textSelectionBase != -1 && var12.textSelectionExtent != -1) {
                  var11.setTextSelection(var12.textSelectionBase, var12.textSelectionExtent);
               }

               var8 = this.accessibilityFocusedSemanticsNode;
               if (var8 != null && var8.id == var1) {
                  var11.setLiveRegion(1);
               }

               byte var3;
               if (var12.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_FORWARD_BY_CHARACTER)) {
                  var11.addAction(256);
                  var3 = 1;
               } else {
                  var3 = 0;
               }

               if (var12.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_BACKWARD_BY_CHARACTER)) {
                  var11.addAction(512);
                  var3 = 1;
               }

               int var2 = var3;
               if (var12.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_FORWARD_BY_WORD)) {
                  var11.addAction(256);
                  var2 = var3 | 2;
               }

               var3 = var2;
               if (var12.hasAction(AccessibilityBridge.Action.MOVE_CURSOR_BACKWARD_BY_WORD)) {
                  var11.addAction(512);
                  var3 = var2 | 2;
               }

               var11.setMovementGranularities(var3);
               if (var12.maxValueLength >= 0) {
                  if (var12.value == null) {
                     var2 = 0;
                  } else {
                     var2 = var12.value.length();
                  }

                  var12.currentValueLength;
                  var12.maxValueLength;
                  var11.setMaxTextLength(var2 - var12.currentValueLength + var12.maxValueLength);
               }
            }

            if (var12.hasAction(AccessibilityBridge.Action.SET_SELECTION)) {
               var11.addAction(131072);
            }

            if (var12.hasAction(AccessibilityBridge.Action.COPY)) {
               var11.addAction(16384);
            }

            if (var12.hasAction(AccessibilityBridge.Action.CUT)) {
               var11.addAction(65536);
            }

            if (var12.hasAction(AccessibilityBridge.Action.PASTE)) {
               var11.addAction(32768);
            }

            if (var12.hasAction(AccessibilityBridge.Action.SET_TEXT)) {
               var11.addAction(2097152);
            }

            if (var12.hasFlag(AccessibilityBridge.Flag.IS_BUTTON) || var12.hasFlag(AccessibilityBridge.Flag.IS_LINK)) {
               var11.setClassName("android.widget.Button");
            }

            if (var12.hasFlag(AccessibilityBridge.Flag.IS_IMAGE)) {
               var11.setClassName("android.widget.ImageView");
            }

            if (var12.hasAction(AccessibilityBridge.Action.DISMISS)) {
               var11.setDismissable(true);
               var11.addAction(1048576);
            }

            if (var12.parent != null) {
               var11.setParent(this.rootAccessibilityView, var12.parent.id);
            } else {
               var11.setParent(this.rootAccessibilityView);
            }

            if (var12.previousNodeId != -1 && VERSION.SDK_INT >= 22) {
               ExternalSyntheticApiModelOutline0.m$1(var11, this.rootAccessibilityView, var12.previousNodeId);
            }

            Rect var13 = var12.getGlobalRect();
            if (var12.parent != null) {
               Rect var9 = var12.parent.getGlobalRect();
               Rect var21 = new Rect(var13);
               var21.offset(-var9.left, -var9.top);
               var11.setBoundsInParent(var21);
            } else {
               var11.setBoundsInParent(var13);
            }

            var11.setBoundsInScreen(this.getBoundsInScreen(var13));
            var11.setVisibleToUser(true);
            boolean var17;
            if (var12.hasFlag(AccessibilityBridge.Flag.HAS_ENABLED_STATE) && !var12.hasFlag(AccessibilityBridge.Flag.IS_ENABLED)) {
               var17 = false;
            } else {
               var17 = true;
            }

            var11.setEnabled(var17);
            if (var12.hasAction(AccessibilityBridge.Action.TAP)) {
               if (var12.onTapOverride != null) {
                  var11.addAction(new AccessibilityAction(16, var12.onTapOverride.hint));
                  var11.setClickable(true);
               } else {
                  var11.addAction(16);
                  var11.setClickable(true);
               }
            }

            if (var12.hasAction(AccessibilityBridge.Action.LONG_PRESS)) {
               if (var12.onLongPressOverride != null) {
                  var11.addAction(new AccessibilityAction(32, var12.onLongPressOverride.hint));
                  var11.setLongClickable(true);
               } else {
                  var11.addAction(32);
                  var11.setLongClickable(true);
               }
            }

            if (var12.hasAction(AccessibilityBridge.Action.SCROLL_LEFT)
               || var12.hasAction(AccessibilityBridge.Action.SCROLL_UP)
               || var12.hasAction(AccessibilityBridge.Action.SCROLL_RIGHT)
               || var12.hasAction(AccessibilityBridge.Action.SCROLL_DOWN)) {
               var11.setScrollable(true);
               if (var12.hasFlag(AccessibilityBridge.Flag.HAS_IMPLICIT_SCROLLING)) {
                  if (!var12.hasAction(AccessibilityBridge.Action.SCROLL_LEFT) && !var12.hasAction(AccessibilityBridge.Action.SCROLL_RIGHT)) {
                     if (this.shouldSetCollectionInfo(var12)) {
                        var11.setCollectionInfo(CollectionInfo.obtain(var12.scrollChildren, 0, false));
                     } else {
                        var11.setClassName("android.widget.ScrollView");
                     }
                  } else if (this.shouldSetCollectionInfo(var12)) {
                     var11.setCollectionInfo(CollectionInfo.obtain(0, var12.scrollChildren, false));
                  } else {
                     var11.setClassName("android.widget.HorizontalScrollView");
                  }
               }

               if (var12.hasAction(AccessibilityBridge.Action.SCROLL_LEFT) || var12.hasAction(AccessibilityBridge.Action.SCROLL_UP)) {
                  var11.addAction(4096);
               }

               if (var12.hasAction(AccessibilityBridge.Action.SCROLL_RIGHT) || var12.hasAction(AccessibilityBridge.Action.SCROLL_DOWN)) {
                  var11.addAction(8192);
               }
            }

            if (var12.hasAction(AccessibilityBridge.Action.INCREASE) || var12.hasAction(AccessibilityBridge.Action.DECREASE)) {
               var11.setClassName("android.widget.SeekBar");
               if (var12.hasAction(AccessibilityBridge.Action.INCREASE)) {
                  var11.addAction(4096);
               }

               if (var12.hasAction(AccessibilityBridge.Action.DECREASE)) {
                  var11.addAction(8192);
               }
            }

            if (var12.hasFlag(AccessibilityBridge.Flag.IS_LIVE_REGION)) {
               var11.setLiveRegion(1);
            }

            if (var12.hasFlag(AccessibilityBridge.Flag.IS_TEXT_FIELD)) {
               var11.setText(var12.getValue());
               if (VERSION.SDK_INT >= 28) {
                  ExternalSyntheticApiModelOutline0.m(var11, var12.getTextFieldHint());
               }
            } else if (!var12.hasFlag(AccessibilityBridge.Flag.SCOPES_ROUTE)) {
               CharSequence var22 = var12.getValueLabelHint();
               Object var29 = var22;
               if (VERSION.SDK_INT < 28) {
                  var29 = var22;
                  if (var12.tooltip != null) {
                     var29 = var10;
                     if (var22 != null) {
                        var29 = var22;
                     }

                     StringBuilder var23 = new StringBuilder();
                     var23.append(var29);
                     var23.append("\n");
                     var23.append(var12.tooltip);
                     var29 = var23.toString();
                  }
               }

               if (var29 != null) {
                  var11.setContentDescription((CharSequence)var29);
               }
            }

            if (VERSION.SDK_INT >= 28 && var12.tooltip != null) {
               ExternalSyntheticApiModelOutline0.m$1(var11, var12.tooltip);
            }

            boolean var7 = var12.hasFlag(AccessibilityBridge.Flag.HAS_CHECKED_STATE);
            boolean var6 = var12.hasFlag(AccessibilityBridge.Flag.HAS_TOGGLED_STATE);
            var17 = var5;
            if (!var7) {
               if (var6) {
                  var17 = var5;
               } else {
                  var17 = false;
               }
            }

            var11.setCheckable(var17);
            if (var7) {
               var11.setChecked(var12.hasFlag(AccessibilityBridge.Flag.IS_CHECKED));
               if (var12.hasFlag(AccessibilityBridge.Flag.IS_IN_MUTUALLY_EXCLUSIVE_GROUP)) {
                  var11.setClassName("android.widget.RadioButton");
               } else {
                  var11.setClassName("android.widget.CheckBox");
               }
            } else if (var6) {
               var11.setChecked(var12.hasFlag(AccessibilityBridge.Flag.IS_TOGGLED));
               var11.setClassName("android.widget.Switch");
            }

            var11.setSelected(var12.hasFlag(AccessibilityBridge.Flag.IS_SELECTED));
            if (VERSION.SDK_INT >= 28) {
               ExternalSyntheticApiModelOutline0.m$5(var11, var12.hasFlag(AccessibilityBridge.Flag.IS_HEADER));
            }

            var8 = this.accessibilityFocusedSemanticsNode;
            if (var8 != null && var8.id == var1) {
               var11.addAction(128);
            } else {
               var11.addAction(64);
            }

            if (var12.customAccessibilityActions != null) {
               for (AccessibilityBridge.CustomAccessibilityAction var31 : var12.customAccessibilityActions) {
                  var11.addAction(new AccessibilityAction(var31.resourceId, var31.label));
               }
            }

            for (AccessibilityBridge.SemanticsNode var34 : var12.childrenInTraversalOrder) {
               if (!var34.hasFlag(AccessibilityBridge.Flag.IS_HIDDEN)) {
                  if (var34.platformViewId != -1) {
                     View var26 = this.platformViewsAccessibilityDelegate.getPlatformViewById(var34.platformViewId);
                     if (!this.platformViewsAccessibilityDelegate.usesVirtualDisplay(var34.platformViewId)) {
                        var11.addChild(var26);
                        continue;
                     }
                  }

                  var11.addChild(this.rootAccessibilityView, var34.id);
               }
            }

            return var11;
         }
      }
   }

   public boolean externalViewRequestSendAccessibilityEvent(View var1, View var2, AccessibilityEvent var3) {
      if (!this.accessibilityViewEmbedder.requestSendAccessibilityEvent(var1, var2, var3)) {
         return false;
      } else {
         Integer var5 = this.accessibilityViewEmbedder.getRecordFlutterId(var1, var3);
         if (var5 == null) {
            return false;
         } else {
            int var4 = var3.getEventType();
            if (var4 != 8) {
               if (var4 != 128) {
                  if (var4 != 32768) {
                     if (var4 == 65536) {
                        this.embeddedInputFocusedNodeId = null;
                        this.embeddedAccessibilityFocusedNodeId = null;
                     }
                  } else {
                     this.embeddedAccessibilityFocusedNodeId = var5;
                     this.accessibilityFocusedSemanticsNode = null;
                  }
               } else {
                  this.hoveredObject = null;
               }
            } else {
               this.embeddedInputFocusedNodeId = var5;
               this.inputFocusedSemanticsNode = null;
            }

            return true;
         }
      }
   }

   public AccessibilityNodeInfo findFocus(int var1) {
      if (var1 != 1) {
         if (var1 != 2) {
            return null;
         }
      } else {
         AccessibilityBridge.SemanticsNode var2 = this.inputFocusedSemanticsNode;
         if (var2 != null) {
            return this.createAccessibilityNodeInfo(var2.id);
         }

         Integer var3 = this.embeddedInputFocusedNodeId;
         if (var3 != null) {
            return this.createAccessibilityNodeInfo(var3);
         }
      }

      AccessibilityBridge.SemanticsNode var4 = this.accessibilityFocusedSemanticsNode;
      if (var4 != null) {
         return this.createAccessibilityNodeInfo(var4.id);
      } else {
         Integer var5 = this.embeddedAccessibilityFocusedNodeId;
         return var5 != null ? this.createAccessibilityNodeInfo(var5) : null;
      }
   }

   public boolean getAccessibleNavigation() {
      return this.accessibleNavigation;
   }

   public int getHoveredObjectId() {
      return this.hoveredObject.id;
   }

   public boolean isAccessibilityEnabled() {
      return this.accessibilityManager.isEnabled();
   }

   public boolean isTouchExplorationEnabled() {
      return this.accessibilityManager.isTouchExplorationEnabled();
   }

   public AccessibilityEvent obtainAccessibilityEvent(int var1) {
      return AccessibilityEvent.obtain(var1);
   }

   public AccessibilityNodeInfo obtainAccessibilityNodeInfo(View var1) {
      return AccessibilityNodeInfo.obtain(var1);
   }

   public AccessibilityNodeInfo obtainAccessibilityNodeInfo(View var1, int var2) {
      return AccessibilityNodeInfo.obtain(var1, var2);
   }

   public boolean onAccessibilityHoverEvent(MotionEvent var1) {
      return this.onAccessibilityHoverEvent(var1, false);
   }

   public boolean onAccessibilityHoverEvent(MotionEvent var1, boolean var2) {
      if (!this.accessibilityManager.isTouchExplorationEnabled()) {
         return false;
      } else if (this.flutterSemanticsTree.isEmpty()) {
         return false;
      } else {
         AccessibilityBridge.SemanticsNode var3 = this.getRootSemanticsNode().hitTest(new float[]{var1.getX(), var1.getY(), 0.0F, 1.0F}, var2);
         if (var3 != null && var3.platformViewId != -1) {
            return var2 ? false : this.accessibilityViewEmbedder.onAccessibilityHoverEvent(var3.id, var1);
         } else {
            if (var1.getAction() != 9 && var1.getAction() != 7) {
               if (var1.getAction() != 10) {
                  StringBuilder var4 = new StringBuilder("unexpected accessibility hover event: ");
                  var4.append(var1);
                  Log.d("flutter", var4.toString());
                  return false;
               }

               this.onTouchExplorationExit();
            } else {
               this.handleTouchExploration(var1.getX(), var1.getY(), var2);
            }

            return true;
         }
      }
   }

   public boolean performAction(int var1, int var2, Bundle var3) {
      if (var1 >= 65536) {
         boolean var5 = this.accessibilityViewEmbedder.performAction(var1, var2, var3);
         if (var5 && var2 == 128) {
            this.embeddedAccessibilityFocusedNodeId = null;
         }

         return var5;
      } else {
         AccessibilityBridge.SemanticsNode var6 = this.flutterSemanticsTree.get(var1);
         if (var6 == null) {
            return false;
         } else {
            switch (var2) {
               case 16:
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.TAP);
                  return true;
               case 32:
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.LONG_PRESS);
                  return true;
               case 64:
                  if (this.accessibilityFocusedSemanticsNode == null) {
                     this.rootAccessibilityView.invalidate();
                  }

                  this.accessibilityFocusedSemanticsNode = var6;
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.DID_GAIN_ACCESSIBILITY_FOCUS);
                  HashMap var12 = new HashMap();
                  var12.put("type", "didGainFocus");
                  var12.put("nodeId", var6.id);
                  this.accessibilityChannel.channel.send(var12);
                  this.sendAccessibilityEvent(var1, 32768);
                  if (var6.hasAction(AccessibilityBridge.Action.INCREASE) || var6.hasAction(AccessibilityBridge.Action.DECREASE)) {
                     this.sendAccessibilityEvent(var1, 4);
                  }

                  return true;
               case 128:
                  AccessibilityBridge.SemanticsNode var10 = this.accessibilityFocusedSemanticsNode;
                  if (var10 != null && var10.id == var1) {
                     this.accessibilityFocusedSemanticsNode = null;
                  }

                  Integer var11 = this.embeddedAccessibilityFocusedNodeId;
                  if (var11 != null && var11 == var1) {
                     this.embeddedAccessibilityFocusedNodeId = null;
                  }

                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.DID_LOSE_ACCESSIBILITY_FOCUS);
                  this.sendAccessibilityEvent(var1, 65536);
                  return true;
               case 256:
                  return this.performCursorMoveAction(var6, var1, var3, true);
               case 512:
                  return this.performCursorMoveAction(var6, var1, var3, false);
               case 4096:
                  if (var6.hasAction(AccessibilityBridge.Action.SCROLL_UP)) {
                     this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.SCROLL_UP);
                  } else if (var6.hasAction(AccessibilityBridge.Action.SCROLL_LEFT)) {
                     this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.SCROLL_LEFT);
                  } else {
                     if (!var6.hasAction(AccessibilityBridge.Action.INCREASE)) {
                        return false;
                     }

                     var6.value = var6.increasedValue;
                     var6.valueAttributes = var6.increasedValueAttributes;
                     this.sendAccessibilityEvent(var1, 4);
                     this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.INCREASE);
                  }

                  return true;
               case 8192:
                  if (var6.hasAction(AccessibilityBridge.Action.SCROLL_DOWN)) {
                     this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.SCROLL_DOWN);
                  } else if (var6.hasAction(AccessibilityBridge.Action.SCROLL_RIGHT)) {
                     this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.SCROLL_RIGHT);
                  } else {
                     if (!var6.hasAction(AccessibilityBridge.Action.DECREASE)) {
                        return false;
                     }

                     var6.value = var6.decreasedValue;
                     var6.valueAttributes = var6.decreasedValueAttributes;
                     this.sendAccessibilityEvent(var1, 4);
                     this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.DECREASE);
                  }

                  return true;
               case 16384:
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.COPY);
                  return true;
               case 32768:
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.PASTE);
                  return true;
               case 65536:
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.CUT);
                  return true;
               case 131072:
                  HashMap var7 = new HashMap();
                  if (var3 != null && var3.containsKey("ACTION_ARGUMENT_SELECTION_START_INT") && var3.containsKey("ACTION_ARGUMENT_SELECTION_END_INT")) {
                     var7.put("base", var3.getInt("ACTION_ARGUMENT_SELECTION_START_INT"));
                     var7.put("extent", var3.getInt("ACTION_ARGUMENT_SELECTION_END_INT"));
                  } else {
                     var7.put("base", var6.textSelectionExtent);
                     var7.put("extent", var6.textSelectionExtent);
                  }

                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.SET_SELECTION, var7);
                  AccessibilityBridge.SemanticsNode var9 = this.flutterSemanticsTree.get(var1);
                  var9.textSelectionBase = (Integer)var7.get("base");
                  var9.textSelectionExtent = (Integer)var7.get("extent");
                  return true;
               case 1048576:
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.DISMISS);
                  return true;
               case 2097152:
                  return this.performSetText(var6, var1, var3);
               case 16908342:
                  this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.SHOW_ON_SCREEN);
                  return true;
               default:
                  int var4 = FIRST_RESOURCE_ID;
                  AccessibilityBridge.CustomAccessibilityAction var8 = this.customAccessibilityActions.get(var2 - var4);
                  if (var8 != null) {
                     this.accessibilityChannel.dispatchSemanticsAction(var1, AccessibilityBridge.Action.CUSTOM_ACTION, var8.id);
                     return true;
                  } else {
                     return false;
                  }
            }
         }
      }
   }

   public void release() {
      this.isReleased = true;
      this.platformViewsAccessibilityDelegate.detachAccessibilityBridge();
      this.setOnAccessibilityChangeListener(null);
      this.accessibilityManager.removeAccessibilityStateChangeListener(this.accessibilityStateChangeListener);
      this.accessibilityManager.removeTouchExplorationStateChangeListener(this.touchExplorationStateChangeListener);
      this.contentResolver.unregisterContentObserver(this.animationScaleObserver);
      this.accessibilityChannel.setAccessibilityMessageHandler(null);
   }

   public void reset() {
      this.flutterSemanticsTree.clear();
      AccessibilityBridge.SemanticsNode var1 = this.accessibilityFocusedSemanticsNode;
      if (var1 != null) {
         this.sendAccessibilityEvent(var1.id, 65536);
      }

      this.accessibilityFocusedSemanticsNode = null;
      this.hoveredObject = null;
      this.sendWindowContentChangeEvent(0);
   }

   public void sendAccessibilityEvent(int var1, int var2) {
      if (this.accessibilityManager.isEnabled()) {
         this.sendAccessibilityEvent(this.obtainAccessibilityEvent(var1, var2));
      }
   }

   public void setOnAccessibilityChangeListener(AccessibilityBridge.OnAccessibilityChangeListener var1) {
      this.onAccessibilityChangeListener = var1;
   }

   void updateCustomAccessibilityActions(ByteBuffer var1, String[] var2) {
      while (var1.hasRemaining()) {
         AccessibilityBridge.CustomAccessibilityAction var6 = this.getOrCreateAccessibilityAction(var1.getInt());
         var6.overrideId = var1.getInt();
         int var3 = var1.getInt();
         Object var5 = null;
         String var4;
         if (var3 == -1) {
            var4 = null;
         } else {
            var4 = var2[var3];
         }

         var6.label = var4;
         var3 = var1.getInt();
         if (var3 == -1) {
            var4 = (String)var5;
         } else {
            var4 = var2[var3];
         }

         var6.hint = var4;
      }
   }

   void updateSemantics(ByteBuffer var1, String[] var2, ByteBuffer[] var3) {
      ArrayList var9 = new ArrayList();

      while (var1.hasRemaining()) {
         AccessibilityBridge.SemanticsNode var10 = this.getOrCreateSemanticsNode(var1.getInt());
         var10.updateWith(var1, var2, var3);
         if (!var10.hasFlag(AccessibilityBridge.Flag.IS_HIDDEN)) {
            if (var10.hasFlag(AccessibilityBridge.Flag.IS_FOCUSED)) {
               this.inputFocusedSemanticsNode = var10;
            }

            if (var10.hadPreviousConfig) {
               var9.add(var10);
            }

            if (var10.platformViewId != -1 && !this.platformViewsAccessibilityDelegate.usesVirtualDisplay(var10.platformViewId)) {
               View var41 = this.platformViewsAccessibilityDelegate.getPlatformViewById(var10.platformViewId);
               if (var41 != null) {
                  var41.setImportantForAccessibility(0);
               }
            }
         }
      }

      HashSet var34 = new HashSet();
      AccessibilityBridge.SemanticsNode var27 = this.getRootSemanticsNode();
      ArrayList var42 = new ArrayList();
      if (var27 != null) {
         float[] var12 = new float[16];
         Matrix.setIdentityM(var12, 0);
         if (VERSION.SDK_INT >= 23) {
            boolean var8;
            if (VERSION.SDK_INT >= 28) {
               var8 = this.doesLayoutInDisplayCutoutModeRequireLeftInset();
            } else {
               var8 = true;
            }

            if (var8) {
               WindowInsets var11 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.rootAccessibilityView);
               if (var11 != null) {
                  if (!this.lastLeftFrameInset.equals(var11.getSystemWindowInsetLeft())) {
                     var27.globalGeometryDirty = true;
                     var27.inverseTransformDirty = true;
                  }

                  Integer var43 = var11.getSystemWindowInsetLeft();
                  this.lastLeftFrameInset = var43;
                  Matrix.translateM(var12, 0, var43.intValue(), 0.0F, 0.0F);
               }
            }
         }

         var27.updateRecursively(var12, var34, false);
         var27.collectRoutes(var42);
      }

      Iterator var44 = var42.iterator();
      AccessibilityBridge.SemanticsNode var13 = null;

      while (var44.hasNext()) {
         AccessibilityBridge.SemanticsNode var28 = (AccessibilityBridge.SemanticsNode)var44.next();
         if (!this.flutterNavigationStack.contains(var28.id)) {
            var13 = var28;
         }
      }

      AccessibilityBridge.SemanticsNode var29 = var13;
      if (var13 == null) {
         var29 = var13;
         if (var42.size() > 0) {
            var29 = (AccessibilityBridge.SemanticsNode)var42.get(var42.size() - 1);
         }
      }

      if (var29 != null && (var29.id != this.previousRouteId || var42.size() != this.flutterNavigationStack.size())) {
         this.previousRouteId = var29.id;
         this.onWindowNameChange(var29);
      }

      this.flutterNavigationStack.clear();

      for (AccessibilityBridge.SemanticsNode var30 : var42) {
         this.flutterNavigationStack.add(var30.id);
      }

      Iterator var15 = this.flutterSemanticsTree.entrySet().iterator();

      while (var15.hasNext()) {
         AccessibilityBridge.SemanticsNode var31 = (AccessibilityBridge.SemanticsNode)((Entry)var15.next()).getValue();
         if (!var34.contains(var31)) {
            this.willRemoveSemanticsNode(var31);
            var15.remove();
         }
      }

      this.sendWindowContentChangeEvent(0);

      for (AccessibilityBridge.SemanticsNode var35 : var9) {
         if (var35.didScroll()) {
            AccessibilityEvent var16 = this.obtainAccessibilityEvent(var35.id, 4096);
            float var5 = var35.scrollPosition;
            float var6 = var35.scrollExtentMax;
            float var4 = var5;
            if (Float.isInfinite(var35.scrollExtentMax)) {
               var4 = var5;
               if (var5 > 70000.0F) {
                  var4 = 70000.0F;
               }

               var6 = 100000.0F;
            }

            if (Float.isInfinite(var35.scrollExtentMin)) {
               var6 += 100000.0F;
               var5 = var4;
               if (var4 < -70000.0F) {
                  var5 = -70000.0F;
               }

               var4 = var5 + 100000.0F;
               var5 = var6;
            } else {
               var5 = var6 - var35.scrollExtentMin;
               var4 -= var35.scrollExtentMin;
            }

            if (var35.hadAction(AccessibilityBridge.Action.SCROLL_UP) || var35.hadAction(AccessibilityBridge.Action.SCROLL_DOWN)) {
               var16.setScrollY((int)var4);
               var16.setMaxScrollY((int)var5);
            } else if (var35.hadAction(AccessibilityBridge.Action.SCROLL_LEFT) || var35.hadAction(AccessibilityBridge.Action.SCROLL_RIGHT)) {
               var16.setScrollX((int)var4);
               var16.setMaxScrollX((int)var5);
            }

            if (var35.scrollChildren > 0) {
               var16.setItemCount(var35.scrollChildren);
               var16.setFromIndex(var35.scrollIndex);
               Iterator var32 = var35.childrenInHitTestOrder.iterator();
               int var7 = 0;

               while (var32.hasNext()) {
                  if (!((AccessibilityBridge.SemanticsNode)var32.next()).hasFlag(AccessibilityBridge.Flag.IS_HIDDEN)) {
                     var7++;
                  }
               }

               var16.setToIndex(var35.scrollIndex + var7 - 1);
            }

            this.sendAccessibilityEvent(var16);
         }

         if (var35.hasFlag(AccessibilityBridge.Flag.IS_LIVE_REGION) && var35.didChangeLabel()) {
            this.sendWindowContentChangeEvent(var35.id);
         }

         AccessibilityBridge.SemanticsNode var17 = this.accessibilityFocusedSemanticsNode;
         if (var17 != null
            && var17.id == var35.id
            && !var35.hadFlag(AccessibilityBridge.Flag.IS_SELECTED)
            && var35.hasFlag(AccessibilityBridge.Flag.IS_SELECTED)) {
            AccessibilityEvent var18 = this.obtainAccessibilityEvent(var35.id, 4);
            var18.getText().add(var35.label);
            this.sendAccessibilityEvent(var18);
         }

         label190: {
            AccessibilityBridge.SemanticsNode var19 = this.inputFocusedSemanticsNode;
            if (var19 != null && var19.id == var35.id) {
               AccessibilityBridge.SemanticsNode var20 = this.lastInputFocusedSemanticsNode;
               if (var20 == null || var20.id != this.inputFocusedSemanticsNode.id) {
                  this.lastInputFocusedSemanticsNode = this.inputFocusedSemanticsNode;
                  this.sendAccessibilityEvent(this.obtainAccessibilityEvent(var35.id, 8));
                  break label190;
               }
            }

            if (this.inputFocusedSemanticsNode == null) {
               this.lastInputFocusedSemanticsNode = null;
            }
         }

         AccessibilityBridge.SemanticsNode var21 = this.inputFocusedSemanticsNode;
         if (var21 != null
            && var21.id == var35.id
            && var35.hadFlag(AccessibilityBridge.Flag.IS_TEXT_FIELD)
            && var35.hasFlag(AccessibilityBridge.Flag.IS_TEXT_FIELD)) {
            AccessibilityBridge.SemanticsNode var22 = this.accessibilityFocusedSemanticsNode;
            if (var22 == null || var22.id == this.inputFocusedSemanticsNode.id) {
               String var23 = var35.previousValue;
               String var33 = "";
               String var24;
               if (var23 != null) {
                  var24 = var35.previousValue;
               } else {
                  var24 = "";
               }

               if (var35.value != null) {
                  var33 = var35.value;
               }

               AccessibilityEvent var25 = this.createTextChangedEvent(var35.id, var24, var33);
               if (var25 != null) {
                  this.sendAccessibilityEvent(var25);
               }

               if (var35.previousTextSelectionBase != var35.textSelectionBase || var35.previousTextSelectionExtent != var35.textSelectionExtent) {
                  AccessibilityEvent var26 = this.obtainAccessibilityEvent(var35.id, 8192);
                  var26.getText().add(var33);
                  var26.setFromIndex(var35.textSelectionBase);
                  var26.setToIndex(var35.textSelectionExtent);
                  var26.setItemCount(var33.length());
                  this.sendAccessibilityEvent(var26);
               }
            }
         }
      }
   }

   private static enum AccessibilityFeature {
      ACCESSIBLE_NAVIGATION(1),
      BOLD_TEXT(8),
      DISABLE_ANIMATIONS(4),
      HIGH_CONTRAST(32),
      INVERT_COLORS(2),
      ON_OFF_SWITCH_LABELS(64),
      REDUCE_MOTION(16);

      private static final AccessibilityBridge.AccessibilityFeature[] $VALUES = $values();
      final int value;

      private AccessibilityFeature(int var3) {
         this.value = var3;
      }
   }

   public static enum Action {
      COPY(4096),
      CUSTOM_ACTION(131072),
      CUT(8192),
      DECREASE(128),
      DID_GAIN_ACCESSIBILITY_FOCUS(32768),
      DID_LOSE_ACCESSIBILITY_FOCUS(65536),
      DISMISS(262144),
      FOCUS(4194304),
      INCREASE(64),
      LONG_PRESS(2),
      MOVE_CURSOR_BACKWARD_BY_CHARACTER(1024),
      MOVE_CURSOR_BACKWARD_BY_WORD(1048576),
      MOVE_CURSOR_FORWARD_BY_CHARACTER(512),
      MOVE_CURSOR_FORWARD_BY_WORD(524288),
      PASTE(16384),
      SCROLL_DOWN(32),
      SCROLL_LEFT(4),
      SCROLL_RIGHT(8),
      SCROLL_UP(16),
      SET_SELECTION(2048),
      SET_TEXT(2097152),
      SHOW_ON_SCREEN(256),
      TAP(1);

      private static final AccessibilityBridge.Action[] $VALUES = $values();
      public final int value;

      private Action(int var3) {
         this.value = var3;
      }
   }

   private static class CustomAccessibilityAction {
      private String hint;
      private int id;
      private String label;
      private int overrideId;
      private int resourceId = -1;

      CustomAccessibilityAction() {
         this.id = -1;
         this.overrideId = -1;
      }
   }

   static enum Flag {
      HAS_CHECKED_STATE(1),
      HAS_ENABLED_STATE(64),
      HAS_EXPANDED_STATE(67108864),
      HAS_IMPLICIT_SCROLLING(262144),
      HAS_TOGGLED_STATE(65536),
      IS_BUTTON(8),
      IS_CHECKED(2),
      IS_CHECK_STATE_MIXED(33554432),
      IS_ENABLED(128),
      IS_EXPANDED(134217728),
      IS_FOCUSABLE(2097152),
      IS_FOCUSED(32),
      IS_HEADER(512),
      IS_HIDDEN(8192),
      IS_IMAGE(16384),
      IS_IN_MUTUALLY_EXCLUSIVE_GROUP(256),
      IS_KEYBOARD_KEY(16777216),
      IS_LINK(4194304),
      IS_LIVE_REGION(32768),
      IS_MULTILINE(524288),
      IS_OBSCURED(1024),
      IS_READ_ONLY(1048576),
      IS_SELECTED(4),
      IS_SLIDER(8388608),
      IS_TEXT_FIELD(16),
      IS_TOGGLED(131072),
      NAMES_ROUTE(4096),
      SCOPES_ROUTE(2048);

      private static final AccessibilityBridge.Flag[] $VALUES = $values();
      final int value;

      private Flag(int var3) {
         this.value = var3;
      }
   }

   private static class LocaleStringAttribute extends AccessibilityBridge.StringAttribute {
      String locale;

      private LocaleStringAttribute() {
      }
   }

   public interface OnAccessibilityChangeListener {
      void onAccessibilityChanged(boolean var1, boolean var2);
   }

   private static class SemanticsNode {
      final AccessibilityBridge accessibilityBridge;
      private int actions;
      private float bottom;
      private List<AccessibilityBridge.SemanticsNode> childrenInHitTestOrder;
      private List<AccessibilityBridge.SemanticsNode> childrenInTraversalOrder;
      private int currentValueLength;
      private List<AccessibilityBridge.CustomAccessibilityAction> customAccessibilityActions;
      private String decreasedValue;
      private List<AccessibilityBridge.StringAttribute> decreasedValueAttributes;
      private int flags;
      private boolean globalGeometryDirty;
      private Rect globalRect;
      private float[] globalTransform;
      private boolean hadPreviousConfig;
      private String hint;
      private List<AccessibilityBridge.StringAttribute> hintAttributes;
      private int id = -1;
      private String identifier;
      private String increasedValue;
      private List<AccessibilityBridge.StringAttribute> increasedValueAttributes;
      private float[] inverseTransform;
      private boolean inverseTransformDirty;
      private String label;
      private List<AccessibilityBridge.StringAttribute> labelAttributes;
      private float left;
      private int maxValueLength;
      private AccessibilityBridge.CustomAccessibilityAction onLongPressOverride;
      private AccessibilityBridge.CustomAccessibilityAction onTapOverride;
      private AccessibilityBridge.SemanticsNode parent;
      private int platformViewId;
      private int previousActions;
      private int previousFlags;
      private String previousLabel;
      private int previousNodeId = -1;
      private float previousScrollExtentMax;
      private float previousScrollExtentMin;
      private float previousScrollPosition;
      private int previousTextSelectionBase;
      private int previousTextSelectionExtent;
      private String previousValue;
      private float right;
      private int scrollChildren;
      private float scrollExtentMax;
      private float scrollExtentMin;
      private int scrollIndex;
      private float scrollPosition;
      private AccessibilityBridge.TextDirection textDirection;
      private int textSelectionBase;
      private int textSelectionExtent;
      private String tooltip;
      private float top;
      private float[] transform;
      private String value;
      private List<AccessibilityBridge.StringAttribute> valueAttributes;

      SemanticsNode(AccessibilityBridge var1) {
         this.hadPreviousConfig = false;
         this.childrenInTraversalOrder = new ArrayList<>();
         this.childrenInHitTestOrder = new ArrayList<>();
         this.inverseTransformDirty = true;
         this.globalGeometryDirty = true;
         this.accessibilityBridge = var1;
      }

      private void collectRoutes(List<AccessibilityBridge.SemanticsNode> var1) {
         if (this.hasFlag(AccessibilityBridge.Flag.SCOPES_ROUTE)) {
            var1.add(this);
         }

         Iterator var2 = this.childrenInTraversalOrder.iterator();

         while (var2.hasNext()) {
            ((AccessibilityBridge.SemanticsNode)var2.next()).collectRoutes(var1);
         }
      }

      private SpannableString createSpannableString(String var1, List<AccessibilityBridge.StringAttribute> var2) {
         if (var1 == null) {
            return null;
         } else {
            SpannableString var5 = new SpannableString(var1);
            if (var2 != null) {
               for (AccessibilityBridge.StringAttribute var4 : var2) {
                  int var3 = <unrepresentable>.$SwitchMap$io$flutter$view$AccessibilityBridge$StringAttributeType[var4.type.ordinal()];
                  if (var3 != 1) {
                     if (var3 == 2) {
                        var5.setSpan(new LocaleSpan(Locale.forLanguageTag(((AccessibilityBridge.LocaleStringAttribute)var4).locale)), var4.start, var4.end, 0);
                     }
                  } else {
                     var5.setSpan(new Builder("android.type.verbatim").build(), var4.start, var4.end, 0);
                  }
               }
            }

            return var5;
         }
      }

      private boolean didChangeLabel() {
         String var2 = this.label;
         boolean var1 = false;
         if (var2 == null && this.previousLabel == null) {
            return false;
         } else {
            if (var2 != null) {
               String var3 = this.previousLabel;
               if (var3 != null && var2.equals(var3)) {
                  return var1;
               }
            }

            return true;
         }
      }

      private boolean didScroll() {
         boolean var1;
         if (!Float.isNaN(this.scrollPosition) && !Float.isNaN(this.previousScrollPosition) && this.previousScrollPosition != this.scrollPosition) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private void ensureInverseTransform() {
         if (this.inverseTransformDirty) {
            this.inverseTransformDirty = false;
            if (this.inverseTransform == null) {
               this.inverseTransform = new float[16];
            }

            if (!Matrix.invertM(this.inverseTransform, 0, this.transform, 0)) {
               Arrays.fill(this.inverseTransform, 0.0F);
            }
         }
      }

      private AccessibilityBridge.SemanticsNode getAncestor(Predicate<AccessibilityBridge.SemanticsNode> var1) {
         for (AccessibilityBridge.SemanticsNode var2 = this.parent; var2 != null; var2 = var2.parent) {
            if (var1.test(var2)) {
               return var2;
            }
         }

         return null;
      }

      private Rect getGlobalRect() {
         return this.globalRect;
      }

      private CharSequence getHint() {
         return this.createSpannableString(this.hint, this.hintAttributes);
      }

      private CharSequence getLabel() {
         return this.createSpannableString(this.label, this.labelAttributes);
      }

      private String getRouteName() {
         if (this.hasFlag(AccessibilityBridge.Flag.NAMES_ROUTE)) {
            String var1 = this.label;
            if (var1 != null && !var1.isEmpty()) {
               return this.label;
            }
         }

         Iterator var2 = this.childrenInTraversalOrder.iterator();

         while (var2.hasNext()) {
            String var3 = ((AccessibilityBridge.SemanticsNode)var2.next()).getRouteName();
            if (var3 != null && !var3.isEmpty()) {
               return var3;
            }
         }

         return null;
      }

      private List<AccessibilityBridge.StringAttribute> getStringAttributesFromBuffer(ByteBuffer var1, ByteBuffer[] var2) {
         int var4 = var1.getInt();
         if (var4 == -1) {
            return null;
         } else {
            ArrayList var8 = new ArrayList(var4);

            for (int var3 = 0; var3 < var4; var3++) {
               int var6 = var1.getInt();
               int var5 = var1.getInt();
               AccessibilityBridge.StringAttributeType var9 = AccessibilityBridge.StringAttributeType.values()[var1.getInt()];
               int var7 = <unrepresentable>.$SwitchMap$io$flutter$view$AccessibilityBridge$StringAttributeType[var9.ordinal()];
               if (var7 != 1) {
                  if (var7 == 2) {
                     ByteBuffer var10 = var2[var1.getInt()];
                     AccessibilityBridge.LocaleStringAttribute var11 = new AccessibilityBridge.LocaleStringAttribute();
                     var11.start = var6;
                     var11.end = var5;
                     var11.type = var9;
                     var11.locale = Charset.forName("UTF-8").decode(var10).toString();
                     var8.add(var11);
                  }
               } else {
                  var1.getInt();
                  AccessibilityBridge.SpellOutStringAttribute var12 = new AccessibilityBridge.SpellOutStringAttribute();
                  var12.start = var6;
                  var12.end = var5;
                  var12.type = var9;
                  var8.add(var12);
               }
            }

            return var8;
         }
      }

      private CharSequence getTextFieldHint() {
         CharSequence var6 = this.getLabel();
         CharSequence var5 = this.getHint();
         CharSequence var3 = null;
         int var1 = 0;

         while (var1 < 2) {
            CharSequence var4 = new CharSequence[]{var6, var5}[var1];
            CharSequence var2 = var3;
            if (var4 != null) {
               var2 = var3;
               if (var4.length() > 0) {
                  if (var3 != null && var3.length() != 0) {
                     var2 = TextUtils.concat(new CharSequence[]{var3, ", ", var4});
                  } else {
                     var2 = var4;
                  }
               }
            }

            var1++;
            var3 = var2;
         }

         return var3;
      }

      private CharSequence getValue() {
         return this.createSpannableString(this.value, this.valueAttributes);
      }

      private CharSequence getValueLabelHint() {
         CharSequence var6 = this.getValue();
         CharSequence var7 = this.getLabel();
         CharSequence var5 = this.getHint();
         CharSequence var3 = null;
         int var1 = 0;

         while (var1 < 3) {
            CharSequence var4 = new CharSequence[]{var6, var7, var5}[var1];
            CharSequence var2 = var3;
            if (var4 != null) {
               var2 = var3;
               if (var4.length() > 0) {
                  if (var3 != null && var3.length() != 0) {
                     var2 = TextUtils.concat(new CharSequence[]{var3, ", ", var4});
                  } else {
                     var2 = var4;
                  }
               }
            }

            var1++;
            var3 = var2;
         }

         return var3;
      }

      private boolean hadAction(AccessibilityBridge.Action var1) {
         int var2 = this.previousActions;
         boolean var3;
         if ((var1.value & var2) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      private boolean hadFlag(AccessibilityBridge.Flag var1) {
         int var2 = this.previousFlags;
         boolean var3;
         if ((var1.value & var2) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      private boolean hasAction(AccessibilityBridge.Action var1) {
         int var2 = this.actions;
         boolean var3;
         if ((var1.value & var2) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      private boolean hasFlag(AccessibilityBridge.Flag var1) {
         int var2 = this.flags;
         boolean var3;
         if ((var1.value & var2) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      private AccessibilityBridge.SemanticsNode hitTest(float[] var1, boolean var2) {
         float var4 = var1[3];
         boolean var7 = false;
         float var3 = var1[0] / var4;
         var4 = var1[1] / var4;
         float var5 = this.left;
         Object var9 = null;
         AccessibilityBridge.SemanticsNode var8 = (AccessibilityBridge.SemanticsNode)var9;
         if (!(var3 < var5)) {
            var8 = (AccessibilityBridge.SemanticsNode)var9;
            if (!(var3 >= this.right)) {
               var8 = (AccessibilityBridge.SemanticsNode)var9;
               if (!(var4 < this.top)) {
                  if (var4 >= this.bottom) {
                     var8 = (AccessibilityBridge.SemanticsNode)var9;
                  } else {
                     float[] var10 = new float[4];

                     for (AccessibilityBridge.SemanticsNode var11 : this.childrenInHitTestOrder) {
                        if (!var11.hasFlag(AccessibilityBridge.Flag.IS_HIDDEN)) {
                           var11.ensureInverseTransform();
                           Matrix.multiplyMV(var10, 0, var11.inverseTransform, 0, var1, 0);
                           var11 = var11.hitTest(var10, var2);
                           if (var11 != null) {
                              return var11;
                           }
                        }
                     }

                     boolean var6 = var7;
                     if (var2) {
                        var6 = var7;
                        if (this.platformViewId != -1) {
                           var6 = true;
                        }
                     }

                     if (!this.isFocusable() && !var6) {
                        return (AccessibilityBridge.SemanticsNode)var9;
                     }

                     var8 = this;
                  }
               }
            }
         }

         return var8;
      }

      private boolean isFocusable() {
         boolean var1 = this.hasFlag(AccessibilityBridge.Flag.SCOPES_ROUTE);
         boolean var2 = false;
         if (var1) {
            return false;
         } else if (this.hasFlag(AccessibilityBridge.Flag.IS_FOCUSABLE)) {
            return true;
         } else {
            if ((this.actions & ~AccessibilityBridge.SCROLLABLE_ACTIONS) == 0 && (this.flags & AccessibilityBridge.FOCUSABLE_FLAGS) == 0) {
               String var3 = this.label;
               if (var3 == null || var3.isEmpty()) {
                  var3 = this.value;
                  if (var3 == null || var3.isEmpty()) {
                     var3 = this.hint;
                     if (var3 == null) {
                        return var2;
                     }

                     if (var3.isEmpty()) {
                        return var2;
                     }
                  }
               }
            }

            return true;
         }
      }

      private void log(String var1, boolean var2) {
      }

      private float max(float var1, float var2, float var3, float var4) {
         return Math.max(var1, Math.max(var2, Math.max(var3, var4)));
      }

      private float min(float var1, float var2, float var3, float var4) {
         return Math.min(var1, Math.min(var2, Math.min(var3, var4)));
      }

      private static boolean nullableHasAncestor(AccessibilityBridge.SemanticsNode var0, Predicate<AccessibilityBridge.SemanticsNode> var1) {
         boolean var2;
         if (var0 != null && var0.getAncestor(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      private void transformPoint(float[] var1, float[] var2, float[] var3) {
         Matrix.multiplyMV(var1, 0, var2, 0, var3, 0);
         float var4 = var1[3];
         var1[0] /= var4;
         var1[1] /= var4;
         var1[2] /= var4;
         var1[3] = 0.0F;
      }

      private void updateRecursively(float[] var1, Set<AccessibilityBridge.SemanticsNode> var2, boolean var3) {
         var2.add(this);
         if (this.globalGeometryDirty) {
            var3 = true;
         }

         if (var3) {
            if (this.globalTransform == null) {
               this.globalTransform = new float[16];
            }

            if (this.transform == null) {
               this.transform = new float[16];
            }

            Matrix.multiplyMM(this.globalTransform, 0, var1, 0, this.transform, 0);
            float[] var5 = new float[]{0.0F, 0.0F, 0.0F, 1.0F};
            float[] var6 = new float[4];
            float[] var7 = new float[4];
            var1 = new float[4];
            float[] var8 = new float[4];
            var5[0] = this.left;
            var5[1] = this.top;
            this.transformPoint(var6, this.globalTransform, var5);
            var5[0] = this.right;
            var5[1] = this.top;
            this.transformPoint(var7, this.globalTransform, var5);
            var5[0] = this.right;
            var5[1] = this.bottom;
            this.transformPoint(var1, this.globalTransform, var5);
            var5[0] = this.left;
            var5[1] = this.bottom;
            this.transformPoint(var8, this.globalTransform, var5);
            if (this.globalRect == null) {
               this.globalRect = new Rect();
            }

            this.globalRect
               .set(
                  Math.round(this.min(var6[0], var7[0], var1[0], var8[0])),
                  Math.round(this.min(var6[1], var7[1], var1[1], var8[1])),
                  Math.round(this.max(var6[0], var7[0], var1[0], var8[0])),
                  Math.round(this.max(var6[1], var7[1], var1[1], var8[1]))
               );
            this.globalGeometryDirty = false;
         }

         Iterator var11 = this.childrenInTraversalOrder.iterator();
         int var4 = -1;

         while (var11.hasNext()) {
            AccessibilityBridge.SemanticsNode var10 = (AccessibilityBridge.SemanticsNode)var11.next();
            var10.previousNodeId = var4;
            var4 = var10.id;
            var10.updateRecursively(this.globalTransform, var2, var3);
         }
      }

      private void updateWith(ByteBuffer var1, String[] var2, ByteBuffer[] var3) {
         this.hadPreviousConfig = true;
         this.previousValue = this.value;
         this.previousLabel = this.label;
         this.previousFlags = this.flags;
         this.previousActions = this.actions;
         this.previousTextSelectionBase = this.textSelectionBase;
         this.previousTextSelectionExtent = this.textSelectionExtent;
         this.previousScrollPosition = this.scrollPosition;
         this.previousScrollExtentMax = this.scrollExtentMax;
         this.previousScrollExtentMin = this.scrollExtentMin;
         this.flags = var1.getInt();
         this.actions = var1.getInt();
         this.maxValueLength = var1.getInt();
         this.currentValueLength = var1.getInt();
         this.textSelectionBase = var1.getInt();
         this.textSelectionExtent = var1.getInt();
         this.platformViewId = var1.getInt();
         this.scrollChildren = var1.getInt();
         this.scrollIndex = var1.getInt();
         this.scrollPosition = var1.getFloat();
         this.scrollExtentMax = var1.getFloat();
         this.scrollExtentMin = var1.getFloat();
         int var4 = var1.getInt();
         String var7;
         if (var4 == -1) {
            var7 = null;
         } else {
            var7 = var2[var4];
         }

         this.identifier = var7;
         var4 = var1.getInt();
         if (var4 == -1) {
            var7 = null;
         } else {
            var7 = var2[var4];
         }

         this.label = var7;
         this.labelAttributes = this.getStringAttributesFromBuffer(var1, var3);
         var4 = var1.getInt();
         if (var4 == -1) {
            var7 = null;
         } else {
            var7 = var2[var4];
         }

         this.value = var7;
         this.valueAttributes = this.getStringAttributesFromBuffer(var1, var3);
         var4 = var1.getInt();
         if (var4 == -1) {
            var7 = null;
         } else {
            var7 = var2[var4];
         }

         this.increasedValue = var7;
         this.increasedValueAttributes = this.getStringAttributesFromBuffer(var1, var3);
         var4 = var1.getInt();
         if (var4 == -1) {
            var7 = null;
         } else {
            var7 = var2[var4];
         }

         this.decreasedValue = var7;
         this.decreasedValueAttributes = this.getStringAttributesFromBuffer(var1, var3);
         var4 = var1.getInt();
         if (var4 == -1) {
            var7 = null;
         } else {
            var7 = var2[var4];
         }

         this.hint = var7;
         this.hintAttributes = this.getStringAttributesFromBuffer(var1, var3);
         var4 = var1.getInt();
         String var8;
         if (var4 == -1) {
            var8 = null;
         } else {
            var8 = var2[var4];
         }

         this.tooltip = var8;
         this.textDirection = AccessibilityBridge.TextDirection.fromInt(var1.getInt());
         this.left = var1.getFloat();
         this.top = var1.getFloat();
         this.right = var1.getFloat();
         this.bottom = var1.getFloat();
         if (this.transform == null) {
            this.transform = new float[16];
         }

         byte var5 = 0;

         for (int var19 = 0; var19 < 16; var19++) {
            this.transform[var19] = var1.getFloat();
         }

         this.inverseTransformDirty = true;
         this.globalGeometryDirty = true;
         int var6 = var1.getInt();
         this.childrenInTraversalOrder.clear();
         this.childrenInHitTestOrder.clear();

         for (int var20 = 0; var20 < var6; var20++) {
            AccessibilityBridge.SemanticsNode var9 = this.accessibilityBridge.getOrCreateSemanticsNode(var1.getInt());
            var9.parent = this;
            this.childrenInTraversalOrder.add(var9);
         }

         for (int var21 = 0; var21 < var6; var21++) {
            AccessibilityBridge.SemanticsNode var10 = this.accessibilityBridge.getOrCreateSemanticsNode(var1.getInt());
            var10.parent = this;
            this.childrenInHitTestOrder.add(var10);
         }

         var6 = var1.getInt();
         if (var6 == 0) {
            this.customAccessibilityActions = null;
         } else {
            List var11 = this.customAccessibilityActions;
            if (var11 == null) {
               this.customAccessibilityActions = new ArrayList<>(var6);
               var4 = var5;
            } else {
               var11.clear();
               var4 = var5;
            }

            while (var4 < var6) {
               AccessibilityBridge.CustomAccessibilityAction var12 = this.accessibilityBridge.getOrCreateAccessibilityAction(var1.getInt());
               if (var12.overrideId == AccessibilityBridge.Action.TAP.value) {
                  this.onTapOverride = var12;
               } else if (var12.overrideId == AccessibilityBridge.Action.LONG_PRESS.value) {
                  this.onLongPressOverride = var12;
               } else {
                  this.customAccessibilityActions.add(var12);
               }

               this.customAccessibilityActions.add(var12);
               var4++;
            }
         }
      }
   }

   private static class SpellOutStringAttribute extends AccessibilityBridge.StringAttribute {
      private SpellOutStringAttribute() {
      }
   }

   private static class StringAttribute {
      int end;
      int start;
      AccessibilityBridge.StringAttributeType type;

      private StringAttribute() {
      }
   }

   private static enum StringAttributeType {
      LOCALE,
      SPELLOUT;
      private static final AccessibilityBridge.StringAttributeType[] $VALUES = $values();
   }

   private static enum TextDirection {
      LTR,
      RTL,
      UNKNOWN;
      private static final AccessibilityBridge.TextDirection[] $VALUES = $values();

      public static AccessibilityBridge.TextDirection fromInt(int var0) {
         if (var0 != 1) {
            return var0 != 2 ? UNKNOWN : LTR;
         } else {
            return RTL;
         }
      }
   }
}
