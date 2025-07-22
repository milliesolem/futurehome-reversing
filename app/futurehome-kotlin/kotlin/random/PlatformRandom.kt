package kotlin.random

import java.io.Serializable

private class PlatformRandom(impl: java.util.Random) : AbstractPlatformRandom, Serializable {
   public open val impl: java.util.Random

   init {
      this.impl = var1;
   }

   private companion object {
      private const val serialVersionUID: Long
   }
}
