package kotlin.random

import java.io.Serializable

public abstract class Random {
   public abstract fun nextBits(bitCount: Int): Int {
   }

   public open fun nextBoolean(): Boolean {
      var var1: Boolean = true;
      if (this.nextBits(1) == 0) {
         var1 = false;
      }

      return var1;
   }

   public open fun nextBytes(size: Int): ByteArray {
      return this.nextBytes(new byte[var1]);
   }

   public open fun nextBytes(array: ByteArray): ByteArray {
      return this.nextBytes(var1, 0, var1.length);
   }

   public open fun nextBytes(array: ByteArray, fromIndex: Int = 0, toIndex: Int = var1.length): ByteArray {
      if (var2 < 0 || var2 > var1.length || var3 < 0 || var3 > var1.length) {
         val var8: StringBuilder = new StringBuilder("fromIndex (");
         var8.append(var2);
         var8.append(") or toIndex (");
         var8.append(var3);
         var8.append(") are out of range: 0..");
         var8.append(var1.length);
         var8.append('.');
         throw new IllegalArgumentException(var8.toString().toString());
      } else if (var2 > var3) {
         val var9: StringBuilder = new StringBuilder("fromIndex (");
         var9.append(var2);
         var9.append(") must be not greater than toIndex (");
         var9.append(var3);
         var9.append(").");
         throw new IllegalArgumentException(var9.toString().toString());
      } else {
         var var6: Int = (var3 - var2) / 4;

         for (int var4 = 0; var4 < var6; var4++) {
            val var7: Int = this.nextInt();
            var1[var2] = (byte)var7;
            var1[var2 + 1] = (byte)(var7 ushr 8);
            var1[var2 + 2] = (byte)(var7 ushr 16);
            var1[var2 + 3] = (byte)(var7 ushr 24);
            var2 += 4;
         }

         val var11: Int = var3 - var2;
         var6 = this.nextBits((var3 - var2) * 8);

         for (int var10 = 0; var10 < var11; var10++) {
            var1[var2 + var10] = (byte)(var6 ushr var10 * 8);
         }

         return var1;
      }
   }

   public open fun nextDouble(): Double {
      return PlatformRandomKt.doubleFromParts(this.nextBits(26), this.nextBits(27));
   }

   public open fun nextDouble(until: Double): Double {
      return this.nextDouble(0.0, var1);
   }

   public open fun nextDouble(from: Double, until: Double): Double {
      RandomKt.checkRangeBounds(var1, var3);
      var var5: Double = var3 - var1;
      if (java.lang.Double.isInfinite(var3 - var1)
         && !java.lang.Double.isInfinite(var1)
         && !java.lang.Double.isNaN(var1)
         && !java.lang.Double.isInfinite(var3)
         && !java.lang.Double.isNaN(var3)) {
         var5 = this.nextDouble();
         var1 = var1 + var5 * (var3 / 2 - var1 / 2) + var5 * (var3 / 2 - var1 / 2);
      } else {
         var1 = var1 + this.nextDouble() * var5;
      }

      var5 = var1;
      if (var1 >= var3) {
         var5 = Math.nextAfter(var3, java.lang.Double.NEGATIVE_INFINITY);
      }

      return var5;
   }

   public open fun nextFloat(): Float {
      return this.nextBits(24) / 1.6777216E7F;
   }

   public open fun nextInt(): Int {
      return this.nextBits(32);
   }

   public open fun nextInt(until: Int): Int {
      return this.nextInt(0, var1);
   }

   public open fun nextInt(from: Int, until: Int): Int {
      RandomKt.checkRangeBounds(var1, var2);
      var var3: Int = var2 - var1;
      if (var2 - var1 <= 0 && var2 - var1 != Integer.MIN_VALUE) {
         do {
            var3 = this.nextInt();
         } while (var1 > var3 || var3 >= var2);

         return var3;
      } else {
         val var4: Int;
         if ((-var3 and var3) == var3) {
            var2 = this.nextBits(RandomKt.fastLog2(var3));
         } else {
            do {
               var4 = this.nextInt() ushr 1;
               var2 = var4 % var3;
            } while (var4 - var4 % var3 + (var3 - 1) < 0);
         }

         return var1 + var2;
      }
   }

   public open fun nextLong(): Long {
      return ((long)this.nextInt() shl 32) + this.nextInt();
   }

   public open fun nextLong(until: Long): Long {
      return this.nextLong(0L, var1);
   }

   public open fun nextLong(from: Long, until: Long): Long {
      RandomKt.checkRangeBounds(var1, var3);
      var var7: Long = var3 - var1;
      if (var3 - var1 > 0L) {
         val var9: Long;
         if ((-var7 and var7) == var7) {
            var var5: Int = (int)var7;
            val var6: Int = (int)(var7 ushr 32);
            if (var5 != 0) {
               var5 = this.nextBits(RandomKt.fastLog2(var5));
            } else {
               if (var6 != 1) {
                  return var1 + ((long)this.nextBits(RandomKt.fastLog2(var6)) shl 32) + (this.nextInt() and 4294967295L);
               }

               var5 = this.nextInt();
            }

            var3 = var5 and 4294967295L;
         } else {
            do {
               var9 = this.nextLong() ushr 1;
               var3 = var9 % var7;
            } while (var9 - var9 % var7 + (var7 - 1L) < 0L);
         }

         return var1 + var3;
      } else {
         do {
            var7 = this.nextLong();
         } while (var1 > var7 || var7 >= var3);

         return var7;
      }
   }

   public companion object Default : Random, Serializable {
      private final val defaultRandom: Random

      private fun writeReplace(): Any {
         return Random.Default.Serialized.INSTANCE;
      }

      public override fun nextBits(bitCount: Int): Int {
         return Random.access$getDefaultRandom$cp().nextBits(var1);
      }

      public override fun nextBoolean(): Boolean {
         return Random.access$getDefaultRandom$cp().nextBoolean();
      }

      public override fun nextBytes(size: Int): ByteArray {
         return Random.access$getDefaultRandom$cp().nextBytes(var1);
      }

      public override fun nextBytes(array: ByteArray): ByteArray {
         return Random.access$getDefaultRandom$cp().nextBytes(var1);
      }

      public override fun nextBytes(array: ByteArray, fromIndex: Int, toIndex: Int): ByteArray {
         return Random.access$getDefaultRandom$cp().nextBytes(var1, var2, var3);
      }

      public override fun nextDouble(): Double {
         return Random.access$getDefaultRandom$cp().nextDouble();
      }

      public override fun nextDouble(until: Double): Double {
         return Random.access$getDefaultRandom$cp().nextDouble(var1);
      }

      public override fun nextDouble(from: Double, until: Double): Double {
         return Random.access$getDefaultRandom$cp().nextDouble(var1, var3);
      }

      public override fun nextFloat(): Float {
         return Random.access$getDefaultRandom$cp().nextFloat();
      }

      public override fun nextInt(): Int {
         return Random.access$getDefaultRandom$cp().nextInt();
      }

      public override fun nextInt(until: Int): Int {
         return Random.access$getDefaultRandom$cp().nextInt(var1);
      }

      public override fun nextInt(from: Int, until: Int): Int {
         return Random.access$getDefaultRandom$cp().nextInt(var1, var2);
      }

      public override fun nextLong(): Long {
         return Random.access$getDefaultRandom$cp().nextLong();
      }

      public override fun nextLong(until: Long): Long {
         return Random.access$getDefaultRandom$cp().nextLong(var1);
      }

      public override fun nextLong(from: Long, until: Long): Long {
         return Random.access$getDefaultRandom$cp().nextLong(var1, var3);
      }

      private object Serialized : Serializable {
         private const val serialVersionUID: Long = 0L

         private fun readResolve(): Any {
            return Random.Default;
         }
      }
   }
}
