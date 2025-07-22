package kotlin.sequences

internal class USequencesKt___USequencesKt {
   open fun USequencesKt___USequencesKt() {
   }

   @JvmStatic
   public fun Sequence<UByte>.sum(): UInt {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 = UInt.constructor-impl(var1 + UInt.constructor-impl((var2.next() as UByte).unbox-impl() and 255));
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<UInt>.sum(): UInt {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 = UInt.constructor-impl(var1 + (var2.next() as UInt).unbox-impl());
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<ULong>.sum(): ULong {
      val var3: java.util.Iterator = var0.iterator();
      var var1: Long = 0L;

      while (var3.hasNext()) {
         var1 = ULong.constructor-impl(var1 + (var3.next() as ULong).unbox-impl());
      }

      return var1;
   }

   @JvmStatic
   public fun Sequence<UShort>.sum(): UInt {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 = UInt.constructor-impl(var1 + UInt.constructor-impl((var2.next() as UShort).unbox-impl() and '\uffff'));
      }

      return var1;
   }
}
