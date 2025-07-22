package kotlin.random

import java.io.Serializable

internal class XorWowRandom internal constructor(x: Int, y: Int, z: Int, w: Int, v: Int, addend: Int) : Random, Serializable {
   private final var x: Int
   private final var y: Int
   private final var z: Int
   private final var w: Int
   private final var v: Int
   private final var addend: Int

   internal constructor(seed1: Int, seed2: Int) : this(var1, var2, 0, 0, var1.inv(), var1 shl 10 xor var2 ushr 4)
   init {
      this.x = var1;
      this.y = var2;
      this.z = var3;
      this.w = var4;
      this.v = var5;
      this.addend = var6;
      if ((var1 or var2 or var3 or var4 or var5) == 0) {
         throw new IllegalArgumentException("Initial state must have at least one non-zero element.".toString());
      } else {
         for (int var7 = 0; var7 < 64; var7++) {
            this.nextInt();
         }
      }
   }

   public override fun nextBits(bitCount: Int): Int {
      return RandomKt.takeUpperBits(this.nextInt(), var1);
   }

   public override fun nextInt(): Int {
      var var2: Int = this.x xor this.x ushr 2;
      this.x = this.y;
      this.y = this.z;
      this.z = this.w;
      val var3: Int = this.v;
      this.w = this.v;
      val var4: Int = var2 xor var2 shl 1 xor var3 xor var3 shl 4;
      this.v = var2 xor var2 shl 1 xor var3 xor var3 shl 4;
      var2 = this.addend + 362437;
      this.addend += 362437;
      return var4 + var2;
   }

   private companion object {
      private const val serialVersionUID: Long
   }
}
