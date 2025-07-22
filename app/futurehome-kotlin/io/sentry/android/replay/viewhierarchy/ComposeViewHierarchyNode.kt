package io.sentry.android.replay.viewhierarchy

import android.graphics.Rect
import android.view.View
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorKt
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LayoutCoordinatesKt
import androidx.compose.ui.node.LayoutNode
import androidx.compose.ui.node.Owner
import androidx.compose.ui.semantics.AccessibilityAction
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsConfiguration
import androidx.compose.ui.semantics.SemanticsConfigurationKt
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.replay.SentryReplayModifiers
import io.sentry.android.replay.util.ComposeTextLayout
import io.sentry.android.replay.util.NodesKt
import io.sentry.android.replay.util.TextAttributes
import io.sentry.android.replay.util.TextLayout
import io.sentry.android.replay.util.ViewsKt
import java.lang.ref.WeakReference
import java.util.ArrayList
import kotlin.jvm.functions.Function1

internal object ComposeViewHierarchyNode {
   private final var _rootCoordinates: WeakReference<LayoutCoordinates>?

   private fun fromComposeNode(node: LayoutNode, parent: ViewHierarchyNode?, distance: Int, isComposeRoot: Boolean, options: SentryOptions): ViewHierarchyNode? {
      if (var1.isPlaced() && var1.isAttached()) {
         if (var4) {
            _rootCoordinates = new WeakReference<>(LayoutCoordinatesKt.findRootCoordinates(var1.getCoordinates()));
         }

         val var17: SemanticsConfiguration = var1.getCollapsedSemantics$ui_release();
         val var15: LayoutCoordinates = var1.getCoordinates();
         val var42: LayoutCoordinates;
         if (_rootCoordinates != null) {
            var42 = _rootCoordinates.get();
         } else {
            var42 = null;
         }

         val var47: Rect = NodesKt.boundsInWindow(var15, var42);
         if (!var1.getOuterCoordinator$ui_release().isTransparent()
            && (var17 == null || !var17.contains(SemanticsProperties.INSTANCE.getInvisibleToUser()))
            && var47.height() > 0
            && var47.width() > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         var var9: Boolean;
         if (var17 != null && var17.contains(SemanticsActions.INSTANCE.getSetText())) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         var var6: Float = 0.0F;
         val var18: ViewHierarchyNode;
         if ((var17 == null || !var17.contains(SemanticsProperties.INSTANCE.getText())) && !var9) {
            val var46: Painter = NodesKt.findPainter(var1);
            if (var46 != null) {
               if (var4 && this.shouldMask(var1, true, var5)) {
                  var9 = (boolean)1;
               } else {
                  var9 = (boolean)0;
               }

               if (var2 != null) {
                  var2.setImportantForCaptureToAncestors(true);
               }

               val var33: Float = var47.left;
               val var31: Float = var47.top;
               val var38: Int = var1.getWidth();
               val var11: Int = var1.getHeight();
               if (var2 != null) {
                  var6 = var2.getElevation();
               }

               val var40: Boolean;
               if (var9 && NodesKt.isMaskable(var46)) {
                  var40 = true;
               } else {
                  var40 = false;
               }

               var18 = new ViewHierarchyNode.ImageViewHierarchyNode(var33, var31, var38, var11, var6, var3, var2, var40, true, var4, var47);
            } else {
               val var41: Boolean;
               if (var4 && this.shouldMask(var1, false, var5)) {
                  var41 = true;
               } else {
                  var41 = false;
               }

               val var32: Float = var47.left;
               val var34: Float = var47.top;
               val var39: Int = var1.getWidth();
               var9 = var1.getHeight();
               if (var2 != null) {
                  var6 = var2.getElevation();
               } else {
                  var6 = 0.0F;
               }

               var18 = new ViewHierarchyNode.GenericViewHierarchyNode(var32, var34, var39, var9, var6, var3, var2, var41, false, var4, var47);
            }
         } else {
            val var12: Boolean;
            if (var4 && this.shouldMask(var1, false, var5)) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (var2 != null) {
               var2.setImportantForCaptureToAncestors(true);
            }

            val var16: java.util.List = new ArrayList();
            if (var17 != null) {
               val var20: AccessibilityAction = SemanticsConfigurationKt.getOrNull(var17, SemanticsActions.INSTANCE.getGetTextLayoutResult()) as AccessibilityAction;
               if (var20 != null) {
                  val var21: Function1 = var20.getAction() as Function1;
                  if (var21 != null) {
                     val var22: java.lang.Boolean = var21.invoke(var16) as java.lang.Boolean;
                  }
               }
            }

            var var13: Boolean;
            label102: {
               val var23: TextAttributes = NodesKt.findTextAttributes(var1);
               var43 = var23.component1-QN2ZGVo();
               var13 = var23.component2();
               val var24: TextLayoutResult = CollectionsKt.firstOrNull(var16);
               if (var24 != null) {
                  val var25: TextLayoutInput = var24.getLayoutInput();
                  if (var25 != null) {
                     val var26: TextStyle = var25.getStyle();
                     if (var26 != null) {
                        var27 = Color.box-impl(var26.getColor-0d7_KjU());
                        break label102;
                     }
                  }
               }

               var27 = null;
            }

            if (var27 != null && var27.unbox-impl() == Color.Companion.getUnspecified-0d7_KjU()) {
               var27 = var43;
            }

            val var44: ComposeTextLayout;
            if (!var16.isEmpty() && !var9) {
               var44 = new ComposeTextLayout(CollectionsKt.first(var16), var13);
            } else {
               var44 = null;
            }

            val var45: TextLayout = var44;
            val var28: Int;
            if (var27 != null) {
               var28 = ViewsKt.toOpaque(ColorKt.toArgb-8_81llA(var27.unbox-impl()));
            } else {
               var28 = null;
            }

            val var7: Float = var47.left;
            val var8: Float = var47.top;
            var9 = var1.getWidth();
            val var10: Int = var1.getHeight();
            if (var2 != null) {
               var6 = var2.getElevation();
            } else {
               var6 = 0.0F;
            }

            var18 = new ViewHierarchyNode.TextViewHierarchyNode(
               var45, var28, 0, 0, var7, var8, var9, var10, var6, var3, var2, var12, true, var4, var47, 12, null
            );
         }

         return var18;
      } else {
         return null;
      }
   }

   private fun LayoutNode.getProxyClassName(isImage: Boolean): String {
      val var4: java.lang.String;
      if (var2) {
         var4 = "android.widget.ImageView";
      } else {
         val var3: SemanticsConfiguration = var1.getCollapsedSemantics$ui_release();
         if (var3 == null || !var3.contains(SemanticsProperties.INSTANCE.getText())) {
            val var5: SemanticsConfiguration = var1.getCollapsedSemantics$ui_release();
            if (var5 == null || !var5.contains(SemanticsActions.INSTANCE.getSetText())) {
               return "android.view.View";
            }
         }

         var4 = "android.widget.TextView";
      }

      return var4;
   }

   private fun LayoutNode.shouldMask(isImage: Boolean, options: SentryOptions): Boolean {
      val var4: SemanticsConfiguration = var1.getCollapsedSemantics$ui_release();
      val var6: java.lang.String;
      if (var4 != null) {
         var6 = SemanticsConfigurationKt.getOrNull(var4, SentryReplayModifiers.INSTANCE.getSentryPrivacy()) as java.lang.String;
      } else {
         var6 = null;
      }

      if (var6 == "unmask") {
         return false;
      } else if (var6 == "mask") {
         return true;
      } else {
         val var5: java.lang.String = this.getProxyClassName(var1, var2);
         return !var3.getSessionReplay().getUnmaskViewClasses().contains(var5) && var3.getSessionReplay().getMaskViewClasses().contains(var5);
      }
   }

   private fun LayoutNode.traverse(parentNode: ViewHierarchyNode, isComposeRoot: Boolean, options: SentryOptions) {
      val var10: java.util.List = var1.getChildren$ui_release();
      if (!var10.isEmpty()) {
         val var9: ArrayList = new ArrayList(var10.size());
         val var6: Int = var10.size();

         for (int var5 = 0; var5 < var6; var5++) {
            val var8: LayoutNode = var10.get(var5) as LayoutNode;
            val var7: ViewHierarchyNode = this.fromComposeNode(var8, var2, var5, var3, var4);
            if (var7 != null) {
               var9.add(var7);
               this.traverse(var8, var7, false, var4);
            }
         }

         var2.setChildren(var9);
      }
   }

   public fun fromView(view: View, parent: ViewHierarchyNode?, options: SentryOptions): Boolean {
      val var4: java.lang.String = var1.getClass().getName();
      val var6: java.lang.CharSequence = var4;
      val var5: java.lang.CharSequence = "AndroidComposeView";
      var var20: Owner = null;
      if (!StringsKt.contains$default(var6, var5, false, 2, null)) {
         return false;
      } else if (var2 == null) {
         return false;
      } else {
         try {
            if (var1 is Owner) {
               var20 = var1 as Owner;
            }
         } catch (var9: java.lang.Throwable) {
            var3.getLogger()
               .log(
                  SentryLevel.ERROR,
                  var9,
                  "Error traversing Compose tree. Most likely you're using an unsupported version of\nandroidx.compose.ui:ui. The minimum supported version is 1.5.0. If it's a newer\nversion, please open a github issue with the version you're using, so we can add\nsupport for it."
               );
            return false;
         }

         if (var20 != null) {
            try {
               var19 = var20.getRoot();
            } catch (var8: java.lang.Throwable) {
               var3.getLogger()
                  .log(
                     SentryLevel.ERROR,
                     var8,
                     "Error traversing Compose tree. Most likely you're using an unsupported version of\nandroidx.compose.ui:ui. The minimum supported version is 1.5.0. If it's a newer\nversion, please open a github issue with the version you're using, so we can add\nsupport for it."
                  );
               return false;
            }

            if (var19 != null) {
               try {
                  this.traverse(var19, var2, true, var3);
                  return true;
               } catch (var7: java.lang.Throwable) {
                  var3.getLogger()
                     .log(
                        SentryLevel.ERROR,
                        var7,
                        "Error traversing Compose tree. Most likely you're using an unsupported version of\nandroidx.compose.ui:ui. The minimum supported version is 1.5.0. If it's a newer\nversion, please open a github issue with the version you're using, so we can add\nsupport for it."
                     );
                  return false;
               }
            }
         }

         return false;
      }
   }
}
