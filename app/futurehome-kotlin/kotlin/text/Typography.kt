package kotlin.text

public object Typography {
   public const val quote: Char = '"'
   public const val dollar: Char = '$'
   public const val amp: Char = '&'
   public const val less: Char = '<'
   public const val greater: Char = '>'
   public const val nbsp: Char = ' '
   public const val times: Char = '×'
   public const val cent: Char = '¢'
   public const val pound: Char = '£'
   public const val section: Char = '§'
   public const val copyright: Char = '©'
   public const val leftGuillemet: Char = '«'
   public const val rightGuillemet: Char = '»'
   public const val registered: Char = '®'
   public const val degree: Char = '°'
   public const val plusMinus: Char = '±'
   public const val paragraph: Char = '¶'
   public const val middleDot: Char = '·'
   public const val half: Char = '½'
   public const val ndash: Char = '–'
   public const val mdash: Char = '—'
   public const val leftSingleQuote: Char = '‘'
   public const val rightSingleQuote: Char = '’'
   public const val lowSingleQuote: Char = '‚'
   public const val leftDoubleQuote: Char = '“'
   public const val rightDoubleQuote: Char = '”'
   public const val lowDoubleQuote: Char = '„'
   public const val dagger: Char = '†'
   public const val doubleDagger: Char = '‡'
   public const val bullet: Char = '•'
   public const val ellipsis: Char = '…'
   public const val prime: Char = '′'
   public const val doublePrime: Char = '″'
   public const val euro: Char = '€'
   public const val tm: Char = '™'
   public const val almostEqual: Char = '≈'
   public const val notEqual: Char = '≠'
   public const val lessOrEqual: Char = '≤'
   public const val greaterOrEqual: Char = '≥'

   @Deprecated(
      message = "This constant has a typo in the name. Use leftGuillemet instead.",
      replaceWith = @ReplaceWith(
         expression = "Typography.leftGuillemet",
         imports = {}
      )
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.6"
   )
   public const val leftGuillemete: Char = '«'

   @Deprecated(
      message = "This constant has a typo in the name. Use rightGuillemet instead.",
      replaceWith = @ReplaceWith(
         expression = "Typography.rightGuillemet",
         imports = {}
      )
   )
   @DeprecatedSinceKotlin(
      warningSince = "1.6"
   )
   public const val rightGuillemete: Char = '»'
}
