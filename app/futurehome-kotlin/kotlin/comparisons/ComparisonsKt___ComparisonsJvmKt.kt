package kotlin.comparisons

internal class ComparisonsKt___ComparisonsJvmKt : ComparisonsKt__ComparisonsKt {
   open fun ComparisonsKt___ComparisonsJvmKt() {
   }

   @JvmStatic
   public inline fun maxOf(a: Byte, b: Byte): Byte {
      return (byte)Math.max((int)var0, (int)var1);
   }

   @JvmStatic
   public inline fun maxOf(a: Byte, b: Byte, c: Byte): Byte {
      return (byte)Math.max(var0, Math.max((int)var1, (int)var2));
   }

   @JvmStatic
   public fun maxOf(a: Byte, other: ByteArray): Byte {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = (byte)Math.max(var0, var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun maxOf(a: Double, b: Double): Double {
      return Math.max(var0, var2);
   }

   @JvmStatic
   public inline fun maxOf(a: Double, b: Double, c: Double): Double {
      return Math.max(var0, Math.max(var2, var4));
   }

   @JvmStatic
   public fun maxOf(a: Double, other: DoubleArray): Double {
      val var4: Int = var2.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var0 = Math.max(var0, var2[var3]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun maxOf(a: Float, b: Float): Float {
      return Math.max(var0, var1);
   }

   @JvmStatic
   public inline fun maxOf(a: Float, b: Float, c: Float): Float {
      return Math.max(var0, Math.max(var1, var2));
   }

   @JvmStatic
   public fun maxOf(a: Float, other: FloatArray): Float {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = Math.max(var0, var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun maxOf(a: Int, b: Int): Int {
      return Math.max(var0, var1);
   }

   @JvmStatic
   public inline fun maxOf(a: Int, b: Int, c: Int): Int {
      return Math.max(var0, Math.max(var1, var2));
   }

   @JvmStatic
   public fun maxOf(a: Int, other: IntArray): Int {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = Math.max(var0, var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun maxOf(a: Long, b: Long): Long {
      return Math.max(var0, var2);
   }

   @JvmStatic
   public inline fun maxOf(a: Long, b: Long, c: Long): Long {
      return Math.max(var0, Math.max(var2, var4));
   }

   @JvmStatic
   public fun maxOf(a: Long, other: LongArray): Long {
      val var4: Int = var2.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var0 = Math.max(var0, var2[var3]);
      }

      return var0;
   }

   @JvmStatic
   public fun <T : Comparable<T>> maxOf(a: T, b: T): T {
      if (var0.compareTo(var1) < 0) {
         var0 = var1;
      }

      return (T)var0;
   }

   @JvmStatic
   public fun <T : Comparable<T>> maxOf(a: T, b: T, c: T): T {
      return (T)ComparisonsKt.maxOf(var0, ComparisonsKt.maxOf(var1, var2));
   }

   @JvmStatic
   public fun <T : Comparable<T>> maxOf(a: T, vararg other: T): T {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = ComparisonsKt.maxOf(var0, var1[var2]);
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun maxOf(a: Short, b: Short): Short {
      return (short)Math.max((int)var0, (int)var1);
   }

   @JvmStatic
   public inline fun maxOf(a: Short, b: Short, c: Short): Short {
      return (short)Math.max(var0, Math.max((int)var1, (int)var2));
   }

   @JvmStatic
   public fun maxOf(a: Short, other: ShortArray): Short {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = (short)Math.max(var0, var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun minOf(a: Byte, b: Byte): Byte {
      return (byte)Math.min((int)var0, (int)var1);
   }

   @JvmStatic
   public inline fun minOf(a: Byte, b: Byte, c: Byte): Byte {
      return (byte)Math.min(var0, Math.min((int)var1, (int)var2));
   }

   @JvmStatic
   public fun minOf(a: Byte, other: ByteArray): Byte {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = (byte)Math.min(var0, var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun minOf(a: Double, b: Double): Double {
      return Math.min(var0, var2);
   }

   @JvmStatic
   public inline fun minOf(a: Double, b: Double, c: Double): Double {
      return Math.min(var0, Math.min(var2, var4));
   }

   @JvmStatic
   public fun minOf(a: Double, other: DoubleArray): Double {
      val var4: Int = var2.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var0 = Math.min(var0, var2[var3]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun minOf(a: Float, b: Float): Float {
      return Math.min(var0, var1);
   }

   @JvmStatic
   public inline fun minOf(a: Float, b: Float, c: Float): Float {
      return Math.min(var0, Math.min(var1, var2));
   }

   @JvmStatic
   public fun minOf(a: Float, other: FloatArray): Float {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = Math.min(var0, var1[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun minOf(a: Int, b: Int): Int {
      return Math.min(var0, var1);
   }

   @JvmStatic
   public inline fun minOf(a: Int, b: Int, c: Int): Int {
      return Math.min(var0, Math.min(var1, var2));
   }

   @JvmStatic
   public fun minOf(a: Int, other: IntArray): Int {
      val var4: Int = var1.length;
      var var2: Int = var0;

      for (int var5 = 0; var5 < var4; var5++) {
         var2 = Math.min(var2, var1[var5]);
      }

      return var2;
   }

   @JvmStatic
   public inline fun minOf(a: Long, b: Long): Long {
      return Math.min(var0, var2);
   }

   @JvmStatic
   public inline fun minOf(a: Long, b: Long, c: Long): Long {
      return Math.min(var0, Math.min(var2, var4));
   }

   @JvmStatic
   public fun minOf(a: Long, other: LongArray): Long {
      val var4: Int = var2.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var0 = Math.min(var0, var2[var3]);
      }

      return var0;
   }

   @JvmStatic
   public fun <T : Comparable<T>> minOf(a: T, b: T): T {
      if (var0.compareTo(var1) > 0) {
         var0 = var1;
      }

      return (T)var0;
   }

   @JvmStatic
   public fun <T : Comparable<T>> minOf(a: T, b: T, c: T): T {
      return (T)ComparisonsKt.minOf(var0, ComparisonsKt.minOf(var1, var2));
   }

   @JvmStatic
   public fun <T : Comparable<T>> minOf(a: T, vararg other: T): T {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = ComparisonsKt.minOf(var0, var1[var2]);
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun minOf(a: Short, b: Short): Short {
      return (short)Math.min((int)var0, (int)var1);
   }

   @JvmStatic
   public inline fun minOf(a: Short, b: Short, c: Short): Short {
      return (short)Math.min(var0, Math.min((int)var1, (int)var2));
   }

   @JvmStatic
   public fun minOf(a: Short, other: ShortArray): Short {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = (short)Math.min(var0, var1[var2]);
      }

      return var0;
   }
}
