package kotlin.jvm.internal

internal object DoubleCompanionObject {
   public const val MIN_VALUE: Double = java.lang.Double.MIN_VALUE
   public const val MAX_VALUE: Double = java.lang.Double.MAX_VALUE
   public const val POSITIVE_INFINITY: Double = java.lang.Double.POSITIVE_INFINITY
   public const val NEGATIVE_INFINITY: Double = java.lang.Double.NEGATIVE_INFINITY
   public const val NaN: Double = java.lang.Double.NaN
   public const val SIZE_BYTES: Int = 8
   public const val SIZE_BITS: Int = 64

   public fun getMAX_VALUE(): Double {
      return java.lang.Double.MAX_VALUE;
   }

   public fun getMIN_VALUE(): Double {
      return java.lang.Double.MIN_VALUE;
   }

   public fun getNEGATIVE_INFINITY(): Double {
      return java.lang.Double.NEGATIVE_INFINITY;
   }

   public fun getNaN(): Double {
      return java.lang.Double.NaN;
   }

   public fun getPOSITIVE_INFINITY(): Double {
      return java.lang.Double.POSITIVE_INFINITY;
   }
}
