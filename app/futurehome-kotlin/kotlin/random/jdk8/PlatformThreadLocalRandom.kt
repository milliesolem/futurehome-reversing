package kotlin.random.jdk8

import j..util.concurrent.ThreadLocalRandom
import java.util.Random
import kotlin.random.AbstractPlatformRandom

internal class PlatformThreadLocalRandom : AbstractPlatformRandom {
   public open val impl: Random
      public open get() {
         val var1: ThreadLocalRandom = ThreadLocalRandom.current();
         return var1 as Random;
      }


   public override fun nextDouble(until: Double): Double {
      return ThreadLocalRandom.current().nextDouble(var1);
   }

   public override fun nextInt(from: Int, until: Int): Int {
      return ThreadLocalRandom.current().nextInt(var1, var2);
   }

   public override fun nextLong(until: Long): Long {
      return ThreadLocalRandom.current().nextLong(var1);
   }

   public override fun nextLong(from: Long, until: Long): Long {
      return ThreadLocalRandom.current().nextLong(var1, var3);
   }
}
