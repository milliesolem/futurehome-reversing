package kotlin.ranges

public class ULongRange(start: ULong, endInclusive: ULong) : ULongRange(var1, var3), ClosedRange<ULong>, OpenEndRange<ULong> {
   public open val start: ULong
      public open get() {
         return this.getFirst-s-VKNKU();
      }


   public open val endInclusive: ULong
      public open get() {
         return this.getLast-s-VKNKU();
      }


   @Deprecated(
      message = "Can throw an exception when it's impossible to represent the value with ULong type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw."
   )
   public open val endExclusive: ULong
      public open get() {
         if (this.getLast-s-VKNKU() != -1L) {
            return ULong.constructor-impl(this.getLast-s-VKNKU() + ULong.constructor-impl((long)1 and 4294967295L));
         } else {
            throw new IllegalStateException("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.".toString());
         }
      }


   fun ULongRange(var1: Long, var3: Long) {
      super(var1, var3, 1L, null);
   }

   public open operator fun contains(value: ULong): Boolean {
      val var3: Boolean;
      if (UByte$$ExternalSyntheticBackport0.m(this.getFirst-s-VKNKU(), var1) <= 0 && UByte$$ExternalSyntheticBackport0.m(var1, this.getLast-s-VKNKU()) <= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is ULongRange) {
         if (this.isEmpty() && (var1 as ULongRange).isEmpty()) {
            return true;
         }

         if (this.getFirst-s-VKNKU() == (var1 as ULongRange).getFirst-s-VKNKU() && this.getLast-s-VKNKU() == (var1 as ULongRange).getLast-s-VKNKU()) {
            return true;
         }
      }

      return false;
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.isEmpty()) {
         var1 = -1;
      } else {
         var1 = (int)ULong.constructor-impl(this.getLast-s-VKNKU() xor ULong.constructor-impl(this.getLast-s-VKNKU() ushr 32))
            + (int)ULong.constructor-impl(this.getFirst-s-VKNKU() xor ULong.constructor-impl(this.getFirst-s-VKNKU() ushr 32)) * 31;
      }

      return var1;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (UByte$$ExternalSyntheticBackport0.m(this.getFirst-s-VKNKU(), this.getLast-s-VKNKU()) > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(ULong.toString-impl(this.getFirst-s-VKNKU()));
      var1.append("..");
      var1.append(ULong.toString-impl(this.getLast-s-VKNKU()));
      return var1.toString();
   }

   public companion object {
      public final val EMPTY: ULongRange
   }
}
