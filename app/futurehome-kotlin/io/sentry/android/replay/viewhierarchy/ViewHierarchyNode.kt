package io.sentry.android.replay.viewhierarchy

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Layout
import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import io.sentry.SentryOptions
import io.sentry.android.replay.R
import io.sentry.android.replay.util.AndroidTextLayout
import io.sentry.android.replay.util.TextLayout
import io.sentry.android.replay.util.ViewsKt
import java.util.Locale
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.Ref

public sealed class ViewHierarchyNode protected constructor(x: Float,
   y: Float,
   width: Int,
   height: Int,
   elevation: Float,
   distance: Int,
   parent: ViewHierarchyNode? = null,
   shouldMask: Boolean = false,
   isImportantForContentCapture: Boolean = false,
   isVisible: Boolean = false,
   visibleRect: Rect? = null
) {
   public final var children: List<ViewHierarchyNode>?
      internal set

   public final val distance: Int
   public final val elevation: Float
   public final val height: Int

   public final var isImportantForContentCapture: Boolean
      internal set

   public final val isVisible: Boolean
   public final val parent: ViewHierarchyNode?
   public final val shouldMask: Boolean
   public final val visibleRect: Rect?
   public final val width: Int
   public final val x: Float
   public final val y: Float

   init {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
      this.elevation = var5;
      this.distance = var6;
      this.parent = var7;
      this.shouldMask = var8;
      this.isImportantForContentCapture = var9;
      this.isVisible = var10;
      this.visibleRect = var11;
   }

   private fun findLCA(node: ViewHierarchyNode, otherNode: ViewHierarchyNode): io.sentry.android.replay.viewhierarchy.ViewHierarchyNode.LCAResult {
      var var4: ViewHierarchyNode;
      if (this == var1) {
         var4 = this;
      } else {
         var4 = null;
      }

      var var5: ViewHierarchyNode;
      if (this == var2) {
         var5 = this;
      } else {
         var5 = null;
      }

      val var9: java.util.List = this.children;
      var var7: ViewHierarchyNode = var4;
      var var6: ViewHierarchyNode = var5;
      if (this.children != null) {
         val var14: java.util.Iterator = var9.iterator();

         while (true) {
            var7 = var4;
            var6 = var5;
            if (!var14.hasNext()) {
               break;
            }

            var7 = var14.next() as ViewHierarchyNode;
            val var10: ViewHierarchyNode.LCAResult = var7.findLCA(var1, var2);
            if (var10.getLca() != null) {
               return var10;
            }

            var6 = var4;
            if (var10.getNodeSubtree() != null) {
               var6 = var7;
            }

            var4 = var6;
            if (var10.getOtherNodeSubtree() != null) {
               var5 = var7;
               var4 = var6;
            }
         }
      }

      var1 = null;
      if (var7 != null) {
         var1 = null;
         if (var6 != null) {
            var1 = this;
         }
      }

      return new ViewHierarchyNode.LCAResult(var1, var7, var6);
   }

   public fun isObscured(node: ViewHierarchyNode): Boolean {
      if (this.parent == null) {
         if (var1.visibleRect == null) {
            return false;
         } else {
            val var2: Ref.BooleanRef = new Ref.BooleanRef();
            this.traverse(
               (
                  new Function1<ViewHierarchyNode, java.lang.Boolean>(var2, var1, this) {
                     final Ref.BooleanRef $isObscured;
                     final ViewHierarchyNode $node;
                     final ViewHierarchyNode this$0;

                     {
                        super(1);
                        this.$isObscured = var1;
                        this.$node = var2;
                        this.this$0 = var3;
                     }

                     public final java.lang.Boolean invoke(ViewHierarchyNode var1) {
                        val var4: Rect = var1.getVisibleRect();
                        var var2: Boolean = false;
                        val var3: java.lang.Boolean = false;
                        if (var4 == null
                           || this.$isObscured.element
                           || !var1.isVisible()
                           || !var1.isImportantForContentCapture()
                           || !var1.getVisibleRect().contains(this.$node.getVisibleRect())) {
                           return var3;
                        } else if (var1.getElevation() > this.$node.getElevation()) {
                           this.$isObscured.element = true;
                           return var3;
                        } else {
                           if (var1.getElevation() == this.$node.getElevation()) {
                              val var8: ViewHierarchyNode.LCAResult = ViewHierarchyNode.access$findLCA(this.this$0, this.$node, var1);
                              val var5: ViewHierarchyNode = var8.component1();
                              val var7: ViewHierarchyNode = var8.component2();
                              val var9: ViewHierarchyNode = var8.component3();
                              if (!(var5 == var1) && var9 != null && var7 != null) {
                                 val var6: Ref.BooleanRef = this.$isObscured;
                                 if (var9.getDistance() > var7.getDistance()) {
                                    var2 = true;
                                 }

                                 var6.element = var2;
                                 return this.$isObscured.element xor true;
                              }
                           }

                           return true;
                        }
                     }
                  }
               ) as (ViewHierarchyNode?) -> java.lang.Boolean
            );
            return var2.element;
         }
      } else {
         throw new IllegalArgumentException("This method should be called on the root node of the view hierarchy.".toString());
      }
   }

   public fun setImportantForCaptureToAncestors(isImportant: Boolean) {
      for (ViewHierarchyNode var2 = this.parent; var2 != null; var2 = var2.parent) {
         var2.isImportantForContentCapture = var1;
      }
   }

   public fun traverse(callback: (ViewHierarchyNode) -> Boolean) {
      if (var1.invoke(this) as java.lang.Boolean) {
         val var2: java.util.List = this.children;
         if (this.children != null) {
            val var3: java.util.Iterator = var2.iterator();

            while (var3.hasNext()) {
               (var3.next() as ViewHierarchyNode).traverse(var1);
            }
         }
      }
   }

   public companion object {
      private const val SENTRY_MASK_TAG: String
      private const val SENTRY_UNMASK_TAG: String

      private fun Class<*>.isAssignableFrom(set: Set<String>): Boolean {
         while (var1 != null) {
            if (var2.contains(var1.getName())) {
               return true;
            }

            var1 = var1.getSuperclass();
         }

         return false;
      }

      private fun View.isMaskContainer(options: SentryOptions): Boolean {
         val var3: java.lang.String = var2.getSessionReplay().getMaskViewContainerClass();
         return var3 != null && var1.getClass().getName() == var3;
      }

      private fun ViewParent.isUnmaskContainer(options: SentryOptions): Boolean {
         val var3: java.lang.String = var2.getSessionReplay().getUnmaskViewContainerClass();
         return var3 != null && var1.getClass().getName() == var3;
      }

      private fun View.shouldMask(options: SentryOptions): Boolean {
         var var3: java.lang.String = (java.lang.String)var1.getTag();
         if (var3 is java.lang.String) {
            var3 = var3;
         } else {
            var3 = null;
         }

         if (var3 != null) {
            var3 = var3.toLowerCase(Locale.ROOT);
            if (var3 != null && StringsKt.contains$default(var3, "sentry-unmask", false, 2, null)) {
               return false;
            }
         }

         if (!(var1.getTag(R.id.sentry_privacy) == "unmask")) {
            var3 = (java.lang.String)var1.getTag();
            if (var3 is java.lang.String) {
               var3 = var3;
            } else {
               var3 = null;
            }

            if (var3 != null) {
               var3 = var3.toLowerCase(Locale.ROOT);
               if (var3 != null && StringsKt.contains$default(var3, "sentry-mask", false, 2, null)) {
                  return true;
               }
            }

            if (!(var1.getTag(R.id.sentry_privacy) == "mask")) {
               if (!this.isMaskContainer(var1, var2) && var1.getParent() != null) {
                  val var12: ViewParent = var1.getParent();
                  if (this.isUnmaskContainer(var12, var2)) {
                     return false;
                  }
               }

               val var4: Class = var1.getClass();
               val var13: java.util.Set = var2.getSessionReplay().getUnmaskViewClasses();
               if (this.isAssignableFrom(var4, var13)) {
                  return false;
               } else {
                  val var5: Class = var1.getClass();
                  val var6: java.util.Set = var2.getSessionReplay().getMaskViewClasses();
                  return this.isAssignableFrom(var5, var6);
               }
            } else {
               return true;
            }
         } else {
            return false;
         }
      }

      public fun fromView(view: View, parent: ViewHierarchyNode?, distance: Int, options: SentryOptions): ViewHierarchyNode {
         val var17: Pair = ViewsKt.isVisibleToUser(var1);
         val var15: Boolean = var17.component1() as java.lang.Boolean;
         val var37: Rect = var17.component2() as Rect;
         val var14: Boolean;
         if (var15 && this.shouldMask(var1, var4)) {
            var14 = true;
         } else {
            var14 = false;
         }

         val var16: Boolean = var1 is TextView;
         var var5: Float = 0.0F;
         if (var16) {
            if (var2 != null) {
               var2.setImportantForCaptureToAncestors(true);
            }

            val var22: TextView = var1 as TextView;
            val var20: Layout = (var1 as TextView).getLayout();
            val var21: AndroidTextLayout;
            if (var20 != null) {
               var21 = new AndroidTextLayout(var20);
            } else {
               var21 = null;
            }

            val var35: Int = ViewsKt.toOpaque(var22.getCurrentTextColor());
            val var12: Int = var22.getTotalPaddingLeft();
            val var13: Int = ViewsKt.getTotalPaddingTopSafe(var22);
            val var29: Float = var22.getX();
            val var26: Float = var22.getY();
            val var33: Int = var22.getWidth();
            val var11: Int = var22.getHeight();
            if (var2 != null) {
               var5 = var2.getElevation();
            }

            return new ViewHierarchyNode.TextViewHierarchyNode(
               var21, var35, var12, var13, var29, var26, var33, var11, var5 + var22.getElevation(), var3, var2, var14, true, var15, var37
            );
         } else if (var1 !is ImageView) {
            val var28: Float = var1.getX();
            val var30: Float = var1.getY();
            val var32: Int = var1.getWidth();
            val var34: Int = var1.getHeight();
            var5 = 0.0F;
            if (var2 != null) {
               var5 = var2.getElevation();
            }

            return new ViewHierarchyNode.GenericViewHierarchyNode(
               var28, var30, var32, var34, var5 + var1.getElevation(), var3, var2, var14, false, var15, var37
            );
         } else {
            if (var2 != null) {
               var2.setImportantForCaptureToAncestors(true);
            }

            val var18: ImageView = var1 as ImageView;
            val var25: Float = (var1 as ImageView).getX();
            val var8: Float = (var1 as ImageView).getY();
            val var9: Int = var18.getWidth();
            val var10: Int = var18.getHeight();
            var5 = 0.0F;
            if (var2 != null) {
               var5 = var2.getElevation();
            }

            val var27: Float = var18.getElevation();
            if (var14) {
               val var19: Drawable = var18.getDrawable();
               if (var19 != null && ViewsKt.isMaskable(var19)) {
                  return new ViewHierarchyNode.ImageViewHierarchyNode(var25, var8, var9, var10, var27 + var5, var3, var2, true, true, var15, var37);
               }
            }

            return new ViewHierarchyNode.ImageViewHierarchyNode(var25, var8, var9, var10, var27 + var5, var3, var2, false, true, var15, var37);
         }
      }
   }

   public class GenericViewHierarchyNode(x: Float,
      y: Float,
      width: Int,
      height: Int,
      elevation: Float,
      distance: Int,
      parent: ViewHierarchyNode? = null,
      shouldMask: Boolean = false,
      isImportantForContentCapture: Boolean = false,
      isVisible: Boolean = false,
      visibleRect: Rect? = null
   ) : ViewHierarchyNode(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11)

   public class ImageViewHierarchyNode(x: Float,
      y: Float,
      width: Int,
      height: Int,
      elevation: Float,
      distance: Int,
      parent: ViewHierarchyNode? = null,
      shouldMask: Boolean = false,
      isImportantForContentCapture: Boolean = false,
      isVisible: Boolean = false,
      visibleRect: Rect? = null
   ) : ViewHierarchyNode(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11)

   private data class LCAResult(lca: ViewHierarchyNode?, nodeSubtree: ViewHierarchyNode?, otherNodeSubtree: ViewHierarchyNode?) {
      public final val lca: ViewHierarchyNode?

      public final var nodeSubtree: ViewHierarchyNode?
         internal set

      public final var otherNodeSubtree: ViewHierarchyNode?
         internal set

      init {
         this.lca = var1;
         this.nodeSubtree = var2;
         this.otherNodeSubtree = var3;
      }

      public operator fun component1(): ViewHierarchyNode? {
         return this.lca;
      }

      public operator fun component2(): ViewHierarchyNode? {
         return this.nodeSubtree;
      }

      public operator fun component3(): ViewHierarchyNode? {
         return this.otherNodeSubtree;
      }

      public fun copy(
         lca: ViewHierarchyNode? = var0.lca,
         nodeSubtree: ViewHierarchyNode? = var0.nodeSubtree,
         otherNodeSubtree: ViewHierarchyNode? = var0.otherNodeSubtree
      ): io.sentry.android.replay.viewhierarchy.ViewHierarchyNode.LCAResult {
         return new ViewHierarchyNode.LCAResult(var1, var2, var3);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === var1) {
            return true;
         } else if (var1 !is ViewHierarchyNode.LCAResult) {
            return false;
         } else {
            var1 = var1;
            if (!(this.lca == var1.lca)) {
               return false;
            } else if (!(this.nodeSubtree == var1.nodeSubtree)) {
               return false;
            } else {
               return this.otherNodeSubtree == var1.otherNodeSubtree;
            }
         }
      }

      public override fun hashCode(): Int {
         var var3: Int = 0;
         val var1: Int;
         if (this.lca == null) {
            var1 = 0;
         } else {
            var1 = this.lca.hashCode();
         }

         val var2: Int;
         if (this.nodeSubtree == null) {
            var2 = 0;
         } else {
            var2 = this.nodeSubtree.hashCode();
         }

         if (this.otherNodeSubtree != null) {
            var3 = this.otherNodeSubtree.hashCode();
         }

         return (var1 * 31 + var2) * 31 + var3;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("LCAResult(lca=");
         var1.append(this.lca);
         var1.append(", nodeSubtree=");
         var1.append(this.nodeSubtree);
         var1.append(", otherNodeSubtree=");
         var1.append(this.otherNodeSubtree);
         var1.append(')');
         return var1.toString();
      }
   }

   public class TextViewHierarchyNode(layout: TextLayout? = null,
      dominantColor: Int? = null,
      paddingLeft: Int = 0,
      paddingTop: Int = 0,
      x: Float,
      y: Float,
      width: Int,
      height: Int,
      elevation: Float,
      distance: Int,
      parent: ViewHierarchyNode? = null,
      shouldMask: Boolean = false,
      isImportantForContentCapture: Boolean = false,
      isVisible: Boolean = false,
      visibleRect: Rect? = null
   ) : ViewHierarchyNode(var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15) {
      public final val dominantColor: Int?
      public final val layout: TextLayout?
      public final val paddingLeft: Int
      public final val paddingTop: Int

      init {
         this.layout = var1;
         this.dominantColor = var2;
         this.paddingLeft = var3;
         this.paddingTop = var4;
      }
   }
}
