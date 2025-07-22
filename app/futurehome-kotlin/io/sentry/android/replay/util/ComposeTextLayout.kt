package io.sentry.android.replay.util

import androidx.compose.ui.text.TextLayoutResult
import kotlin.math.MathKt

internal class ComposeTextLayout(layout: TextLayoutResult, hasFillModifier: Boolean) : TextLayout {
   public open val dominantTextColor: Int?
      public open get() {
         return null;
      }


   private final val hasFillModifier: Boolean
   internal final val layout: TextLayoutResult

   public open val lineCount: Int
      public open get() {
         return this.layout.getLineCount();
      }


   init {
      this.layout = var1;
      this.hasFillModifier = var2;
   }

   public override fun getEllipsisCount(line: Int): Int {
      return this.layout.isLineEllipsized(var1);
   }

   public override fun getLineBottom(line: Int): Int {
      return MathKt.roundToInt(this.layout.getLineBottom(var1));
   }

   public override fun getLineStart(line: Int): Int {
      return this.layout.getLineStart(var1);
   }

   public override fun getLineTop(line: Int): Int {
      return MathKt.roundToInt(this.layout.getLineTop(var1));
   }

   public override fun getLineVisibleEnd(line: Int): Int {
      return this.layout.getLineEnd(var1, true);
   }

   public override fun getPrimaryHorizontal(line: Int, offset: Int): Float {
      val var4: Float = this.layout.getHorizontalPosition(var2, true);
      var var3: Float = var4;
      if (!this.hasFillModifier) {
         var3 = var4;
         if (this.getLineCount() == 1) {
            var3 = var4 - this.layout.getLineLeft(var1);
         }
      }

      return var3;
   }
}
