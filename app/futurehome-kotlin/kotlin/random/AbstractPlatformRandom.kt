package kotlin.random

internal abstract class AbstractPlatformRandom : Random {
   public abstract val impl: java.util.Random

   public override fun nextBits(bitCount: Int): Int {
      return RandomKt.takeUpperBits(this.getImpl().nextInt(), var1);
   }

   public override fun nextBoolean(): Boolean {
      return this.getImpl().nextBoolean();
   }

   public override fun nextBytes(array: ByteArray): ByteArray {
      this.getImpl().nextBytes(var1);
      return var1;
   }

   public override fun nextDouble(): Double {
      return this.getImpl().nextDouble();
   }

   public override fun nextFloat(): Float {
      return this.getImpl().nextFloat();
   }

   public override fun nextInt(): Int {
      return this.getImpl().nextInt();
   }

   public override fun nextInt(until: Int): Int {
      return this.getImpl().nextInt(var1);
   }

   public override fun nextLong(): Long {
      return this.getImpl().nextLong();
   }
}
