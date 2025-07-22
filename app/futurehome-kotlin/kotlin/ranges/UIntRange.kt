package kotlin.ranges

public class UIntRange(start: UInt, endInclusive: UInt) : UIntRange(var1, var2), ClosedRange<UInt>, OpenEndRange<UInt> {
   public open val start: UInt
      public open get() {
         return this.getFirst-pVg5ArA();
      }


   public open val endInclusive: UInt
      public open get() {
         return this.getLast-pVg5ArA();
      }


   @Deprecated(
      message = "Can throw an exception when it's impossible to represent the value with UInt type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw."
   )
   public open val endExclusive: UInt
      public open get() {
         if (this.getLast-pVg5ArA() != -1) {
            return UInt.constructor-impl(this.getLast-pVg5ArA() + 1);
         } else {
            throw new IllegalStateException("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.".toString());
         }
      }


   fun UIntRange(var1: Int, var2: Int) {
      super(var1, var2, 1, null);
   }

   public open operator fun contains(value: UInt): Boolean {
      val var2: Boolean;
      if (UByte$$ExternalSyntheticBackport0.m$2(this.getFirst-pVg5ArA(), var1) <= 0 && UByte$$ExternalSyntheticBackport0.m$2(var1, this.getLast-pVg5ArA()) <= 0
         )
       {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is UIntRange) {
         if (this.isEmpty() && (var1 as UIntRange).isEmpty()) {
            return true;
         }

         if (this.getFirst-pVg5ArA() == (var1 as UIntRange).getFirst-pVg5ArA() && this.getLast-pVg5ArA() == (var1 as UIntRange).getLast-pVg5ArA()) {
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
         var1 = this.getFirst-pVg5ArA() * 31 + this.getLast-pVg5ArA();
      }

      return var1;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (UByte$$ExternalSyntheticBackport0.m$2(this.getFirst-pVg5ArA(), this.getLast-pVg5ArA()) > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(UInt.toString-impl(this.getFirst-pVg5ArA()));
      var1.append("..");
      var1.append(UInt.toString-impl(this.getLast-pVg5ArA()));
      return var1.toString();
   }

   public companion object {
      public final val EMPTY: UIntRange
   }
}
