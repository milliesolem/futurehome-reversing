package kotlin.text

import java.util.Locale

internal class CharsKt__CharJVMKt {
   public final val category: CharCategory
      public final get() {
         return CharCategory.Companion.valueOf(Character.getType(var0));
      }


   public final val directionality: CharDirectionality
      public final get() {
         return CharDirectionality.Companion.valueOf(Character.getDirectionality(var0));
      }


   open fun CharsKt__CharJVMKt() {
   }

   @JvmStatic
   internal fun checkRadix(radix: Int): Int {
      if (2 <= var0 && var0 < 37) {
         return var0;
      } else {
         val var1: StringBuilder = new StringBuilder("radix ");
         var1.append(var0);
         var1.append(" was not in valid range ");
         var1.append(new IntRange(2, 36));
         throw new IllegalArgumentException(var1.toString());
      }
   }

   @JvmStatic
   internal fun digitOf(char: Char, radix: Int): Int {
      return Character.digit((int)var0, var1);
   }

   @JvmStatic
   public inline fun Char.isDefined(): Boolean {
      return Character.isDefined(var0);
   }

   @JvmStatic
   public inline fun Char.isDigit(): Boolean {
      return Character.isDigit(var0);
   }

   @JvmStatic
   public inline fun Char.isHighSurrogate(): Boolean {
      return Character.isHighSurrogate(var0);
   }

   @JvmStatic
   public inline fun Char.isISOControl(): Boolean {
      return Character.isISOControl(var0);
   }

   @JvmStatic
   public inline fun Char.isIdentifierIgnorable(): Boolean {
      return Character.isIdentifierIgnorable(var0);
   }

   @JvmStatic
   public inline fun Char.isJavaIdentifierPart(): Boolean {
      return Character.isJavaIdentifierPart(var0);
   }

   @JvmStatic
   public inline fun Char.isJavaIdentifierStart(): Boolean {
      return Character.isJavaIdentifierStart(var0);
   }

   @JvmStatic
   public inline fun Char.isLetter(): Boolean {
      return Character.isLetter(var0);
   }

   @JvmStatic
   public inline fun Char.isLetterOrDigit(): Boolean {
      return Character.isLetterOrDigit(var0);
   }

   @JvmStatic
   public inline fun Char.isLowSurrogate(): Boolean {
      return Character.isLowSurrogate(var0);
   }

   @JvmStatic
   public inline fun Char.isLowerCase(): Boolean {
      return Character.isLowerCase(var0);
   }

   @JvmStatic
   public inline fun Char.isTitleCase(): Boolean {
      return Character.isTitleCase(var0);
   }

   @JvmStatic
   public inline fun Char.isUpperCase(): Boolean {
      return Character.isUpperCase(var0);
   }

   @JvmStatic
   public fun Char.isWhitespace(): Boolean {
      val var1: Boolean;
      if (!Character.isWhitespace(var0) && !Character.isSpaceChar(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @JvmStatic
   public inline fun Char.lowercase(): String {
      var var1: java.lang.String = java.lang.String.valueOf(var0);
      var1 = var1.toLowerCase(Locale.ROOT);
      return var1;
   }

   @JvmStatic
   public fun Char.lowercase(locale: Locale): String {
      val var2: java.lang.String = java.lang.String.valueOf(var0);
      val var3: java.lang.String = var2.toLowerCase(var1);
      return var3;
   }

   @JvmStatic
   public inline fun Char.lowercaseChar(): Char {
      return Character.toLowerCase(var0);
   }

   @JvmStatic
   public fun Char.titlecase(locale: Locale): String {
      var var4: java.lang.String = CharsKt.uppercase(var0, var1);
      if (var4.length() > 1) {
         if (var0 != 329) {
            var0 = var4.charAt(0);
            val var5: java.lang.String = var4.substring(1);
            val var8: java.lang.String = var5.toLowerCase(Locale.ROOT);
            val var6: StringBuilder = new StringBuilder();
            var6.append(var0);
            var6.append(var8);
            var4 = var6.toString();
         }

         return var4;
      } else {
         var var2: java.lang.String = java.lang.String.valueOf(var0);
         var2 = var2.toUpperCase(Locale.ROOT);
         return if (!(var4 == var2)) var4 else java.lang.String.valueOf(Character.toTitleCase(var0));
      }
   }

   @JvmStatic
   public inline fun Char.titlecaseChar(): Char {
      return Character.toTitleCase(var0);
   }

   @Deprecated(message = "Use lowercaseChar() instead.", replaceWith = @ReplaceWith(expression = "lowercaseChar()", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.5")
   @JvmStatic
   public inline fun Char.toLowerCase(): Char {
      return Character.toLowerCase(var0);
   }

   @Deprecated(message = "Use titlecaseChar() instead.", replaceWith = @ReplaceWith(expression = "titlecaseChar()", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.5")
   @JvmStatic
   public inline fun Char.toTitleCase(): Char {
      return Character.toTitleCase(var0);
   }

   @Deprecated(message = "Use uppercaseChar() instead.", replaceWith = @ReplaceWith(expression = "uppercaseChar()", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.5")
   @JvmStatic
   public inline fun Char.toUpperCase(): Char {
      return Character.toUpperCase(var0);
   }

   @JvmStatic
   public inline fun Char.uppercase(): String {
      var var1: java.lang.String = java.lang.String.valueOf(var0);
      var1 = var1.toUpperCase(Locale.ROOT);
      return var1;
   }

   @JvmStatic
   public fun Char.uppercase(locale: Locale): String {
      val var2: java.lang.String = java.lang.String.valueOf(var0);
      val var3: java.lang.String = var2.toUpperCase(var1);
      return var3;
   }

   @JvmStatic
   public inline fun Char.uppercaseChar(): Char {
      return Character.toUpperCase(var0);
   }
}
