package io.sentry.android.replay.util

import android.text.Layout
import android.text.Spanned
import android.text.style.ForegroundColorSpan

public class AndroidTextLayout(layout: Layout) : TextLayout {
   public open val dominantTextColor: Int?
      public open get() {
         if (this.layout.getText() !is Spanned) {
            return null;
         } else {
            val var8: java.lang.CharSequence = this.layout.getText();
            val var15: Spanned = var8 as Spanned;
            var var2: Int = this.layout.getText().length();
            var var1: Int = 0;
            val var11: Array<ForegroundColorSpan> = var15.getSpans(0, var2, ForegroundColorSpan.class) as Array<ForegroundColorSpan>;
            val var5: Int = var11.length;
            var2 = Integer.MIN_VALUE;
            var var16: Int = null;

            while (var1 < var5) {
               val var12: ForegroundColorSpan = var11[var1];
               var var9: java.lang.CharSequence = this.layout.getText();
               var var4: Int = (var9 as Spanned).getSpanStart(var12);
               var9 = this.layout.getText();
               val var6: Int = (var9 as Spanned).getSpanEnd(var12);
               var var3: Int = var2;
               var var18: Int = var16;
               if (var4 != -1) {
                  if (var6 == -1) {
                     var3 = var2;
                     var18 = var16;
                  } else {
                     var4 = var6 - var4;
                     var3 = var2;
                     var18 = var16;
                     if (var4 > var2) {
                        var18 = var12.getForegroundColor();
                        var3 = var4;
                     }
                  }
               }

               var1++;
               var2 = var3;
               var16 = var18;
            }

            var var19: Int = null;
            if (var16 != null) {
               var19 = ViewsKt.toOpaque(var16);
            }

            return var19;
         }
      }


   private final val layout: Layout

   public open val lineCount: Int
      public open get() {
         return this.layout.getLineCount();
      }


   init {
      this.layout = var1;
   }

   public override fun getEllipsisCount(line: Int): Int {
      return this.layout.getEllipsisCount(var1);
   }

   public override fun getLineBottom(line: Int): Int {
      return this.layout.getLineBottom(var1);
   }

   public override fun getLineStart(line: Int): Int {
      return this.layout.getLineStart(var1);
   }

   public override fun getLineTop(line: Int): Int {
      return this.layout.getLineTop(var1);
   }

   public override fun getLineVisibleEnd(line: Int): Int {
      return this.layout.getLineVisibleEnd(var1);
   }

   public override fun getPrimaryHorizontal(line: Int, offset: Int): Float {
      return this.layout.getPrimaryHorizontal(var2);
   }
}
