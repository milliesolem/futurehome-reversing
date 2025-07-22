package io.sentry.android.replay.util

public interface TextLayout {
   public val dominantTextColor: Int?
   public val lineCount: Int

   public abstract fun getEllipsisCount(line: Int): Int {
   }

   public abstract fun getLineBottom(line: Int): Int {
   }

   public abstract fun getLineStart(line: Int): Int {
   }

   public abstract fun getLineTop(line: Int): Int {
   }

   public abstract fun getLineVisibleEnd(line: Int): Int {
   }

   public abstract fun getPrimaryHorizontal(line: Int, offset: Int): Float {
   }
}
