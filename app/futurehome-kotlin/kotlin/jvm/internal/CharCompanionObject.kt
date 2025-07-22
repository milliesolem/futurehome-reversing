package kotlin.jvm.internal

internal object CharCompanionObject {
   public const val MIN_VALUE: Char = '\u0000'
   public const val MAX_VALUE: Char = '\uffff'
   public const val MIN_HIGH_SURROGATE: Char = '\ud800'
   public const val MAX_HIGH_SURROGATE: Char = '\udbff'
   public const val MIN_LOW_SURROGATE: Char = '\udc00'
   public const val MAX_LOW_SURROGATE: Char = '\udfff'
   public const val MIN_SURROGATE: Char = '\ud800'
   public const val MAX_SURROGATE: Char = '\udfff'
   public const val SIZE_BYTES: Int = 2
   public const val SIZE_BITS: Int = 16
}
