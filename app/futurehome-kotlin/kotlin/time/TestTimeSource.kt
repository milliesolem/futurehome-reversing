package kotlin.time

public class TestTimeSource : AbstractLongTimeSource(DurationUnit.NANOSECONDS) {
   private final var reading: Long

   private fun overflow(duration: Duration) {
      val var3: StringBuilder = new StringBuilder("TestTimeSource will overflow if its reading ");
      var3.append(this.reading);
      var3.append(DurationUnitKt.shortName(this.getUnit()));
      var3.append(" is advanced by ");
      var3.append(Duration.toString-impl(var1));
      var3.append('.');
      throw new IllegalStateException(var3.toString());
   }

   public operator fun plusAssign(duration: Duration) {
      val var7: Long = Duration.toLong-impl(var1, this.getUnit());
      if ((var7 - 1L or 1L) == java.lang.Long.MAX_VALUE) {
         val var3: Long = Duration.div-UwyO8pc(var1, 2);
         if ((1L or Duration.toLong-impl(var3, this.getUnit()) - 1L) == java.lang.Long.MAX_VALUE) {
            this.overflow-LRDsOJo(var1);
         } else {
            try {
               this.plusAssign-LRDsOJo(var3);
               this.plusAssign-LRDsOJo(Duration.minus-LRDsOJo(var1, var3));
            } catch (var10: IllegalStateException) {
               this.reading = this.reading;
               throw var10;
            }
         }
      } else {
         val var12: Long = this.reading + var7;
         if ((var7 xor this.reading) >= 0L && (this.reading xor this.reading + var7) < 0L) {
            this.overflow-LRDsOJo(var1);
         }

         this.reading = var12;
      }
   }

   protected override fun read(): Long {
      return this.reading;
   }
}
