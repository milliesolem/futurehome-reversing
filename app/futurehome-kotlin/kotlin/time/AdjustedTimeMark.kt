package kotlin.time

private class AdjustedTimeMark(mark: TimeMark, adjustment: Duration) : AdjustedTimeMark(var1, var2), TimeMark {
   public final val mark: TimeMark
   public final val adjustment: Duration

   fun AdjustedTimeMark(var1: TimeMark, var2: Long) {
      this.mark = var1;
      this.adjustment = var2;
   }

   public override fun elapsedNow(): Duration {
      return Duration.minus-LRDsOJo(this.mark.elapsedNow-UwyO8pc(), this.adjustment);
   }

   override fun hasNotPassedNow(): Boolean {
      return TimeMark.DefaultImpls.hasNotPassedNow(this);
   }

   override fun hasPassedNow(): Boolean {
      return TimeMark.DefaultImpls.hasPassedNow(this);
   }

   override fun `minus-LRDsOJo`(var1: Long): TimeMark {
      return TimeMark.DefaultImpls.minus-LRDsOJo(this, var1);
   }

   public override operator fun plus(duration: Duration): TimeMark {
      return new AdjustedTimeMark(this.mark, Duration.plus-LRDsOJo(this.adjustment, var1), null);
   }
}
