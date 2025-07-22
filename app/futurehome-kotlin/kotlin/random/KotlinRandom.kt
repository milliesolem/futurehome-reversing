package kotlin.random

private class KotlinRandom(impl: Random) : java.util.Random {
   public final val impl: Random
   private final var seedInitialized: Boolean

   init {
      this.impl = var1;
   }

   protected override fun next(bits: Int): Int {
      return this.impl.nextBits(var1);
   }

   public override fun nextBoolean(): Boolean {
      return this.impl.nextBoolean();
   }

   public override fun nextBytes(bytes: ByteArray) {
      this.impl.nextBytes(var1);
   }

   public override fun nextDouble(): Double {
      return this.impl.nextDouble();
   }

   public override fun nextFloat(): Float {
      return this.impl.nextFloat();
   }

   public override fun nextInt(): Int {
      return this.impl.nextInt();
   }

   public override fun nextInt(bound: Int): Int {
      return this.impl.nextInt(var1);
   }

   public override fun nextLong(): Long {
      return this.impl.nextLong();
   }

   public override fun setSeed(seed: Long) {
      if (!this.seedInitialized) {
         this.seedInitialized = true;
      } else {
         throw new UnsupportedOperationException("Setting seed is not supported.");
      }
   }

   private companion object {
      private const val serialVersionUID: Long
   }
}
