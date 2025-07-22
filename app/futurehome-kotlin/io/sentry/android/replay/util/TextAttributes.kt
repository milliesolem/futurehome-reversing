package io.sentry.android.replay.util

import androidx.compose.ui.graphics.Color

internal data class TextAttributes(color: Color?, hasFillModifier: Boolean) : TextAttributes(var1, var2) {
   public final val color: Color?
   public final val hasFillModifier: Boolean

   fun TextAttributes(var1: Color, var2: Boolean) {
      this.color = var1;
      this.hasFillModifier = var2;
   }

   public operator fun component1(): Color? {
      return this.color;
   }

   public operator fun component2(): Boolean {
      return this.hasFillModifier;
   }

   public fun copy(color: Color? = ..., hasFillModifier: Boolean = ...): TextAttributes {
      return new TextAttributes(var1, var2, null);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is TextAttributes) {
         return false;
      } else {
         var1 = var1;
         if (!(this.color == var1.color)) {
            return false;
         } else {
            return this.hasFillModifier == var1.hasFillModifier;
         }
      }
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.color == null) {
         var1 = 0;
      } else {
         var1 = Color.hashCode-impl(this.color.unbox-impl());
      }

      var var2: Byte = this.hasFillModifier;
      if (this.hasFillModifier != 0) {
         var2 = 1;
      }

      return var1 * 31 + var2;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("TextAttributes(color=");
      var1.append(this.color);
      var1.append(", hasFillModifier=");
      var1.append(this.hasFillModifier);
      var1.append(')');
      return var1.toString();
   }
}
