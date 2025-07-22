package io.sentry;

public class SpanOptions {
   private boolean isIdle;
   private boolean trimEnd;
   private boolean trimStart = false;

   public SpanOptions() {
      this.trimEnd = false;
      this.isIdle = false;
   }

   public boolean isIdle() {
      return this.isIdle;
   }

   public boolean isTrimEnd() {
      return this.trimEnd;
   }

   public boolean isTrimStart() {
      return this.trimStart;
   }

   public void setIdle(boolean var1) {
      this.isIdle = var1;
   }

   public void setTrimEnd(boolean var1) {
      this.trimEnd = var1;
   }

   public void setTrimStart(boolean var1) {
      this.trimStart = var1;
   }
}
