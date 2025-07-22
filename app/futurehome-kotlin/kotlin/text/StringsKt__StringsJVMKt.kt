package kotlin.text

import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.nio.charset.CodingErrorAction
import java.util.Arrays
import java.util.Comparator
import java.util.Locale
import java.util.regex.Pattern
import kotlin.String.Companion

internal class StringsKt__StringsJVMKt : StringsKt__StringNumberConversionsKt {
   public final val CASE_INSENSITIVE_ORDER: Comparator<String>
      public final get() {
         val var1: Comparator = java.lang.String.CASE_INSENSITIVE_ORDER;
         return var1;
      }


   open fun StringsKt__StringsJVMKt() {
   }

   @JvmStatic
   public inline fun String(stringBuffer: StringBuffer): String {
      return new java.lang.String(var0);
   }

   @JvmStatic
   public inline fun String(stringBuilder: StringBuilder): String {
      return new java.lang.String(var0);
   }

   @JvmStatic
   public inline fun String(bytes: ByteArray): String {
      return new java.lang.String(var0, Charsets.UTF_8);
   }

   @JvmStatic
   public inline fun String(bytes: ByteArray, offset: Int, length: Int): String {
      return new java.lang.String(var0, var1, var2, Charsets.UTF_8);
   }

   @JvmStatic
   public inline fun String(bytes: ByteArray, offset: Int, length: Int, charset: Charset): String {
      return new java.lang.String(var0, var1, var2, var3);
   }

   @JvmStatic
   public inline fun String(bytes: ByteArray, charset: Charset): String {
      return new java.lang.String(var0, var1);
   }

   @JvmStatic
   public inline fun String(chars: CharArray): String {
      return new java.lang.String(var0);
   }

   @JvmStatic
   public inline fun String(chars: CharArray, offset: Int, length: Int): String {
      return new java.lang.String(var0, var1, var2);
   }

   @JvmStatic
   public inline fun String(codePoints: IntArray, offset: Int, length: Int): String {
      return new java.lang.String(var0, var1, var2);
   }

   @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }", imports = ["java.util.Locale"]))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public fun String.capitalize(): String {
      val var1: Locale = Locale.getDefault();
      return StringsKt.capitalize(var0, var1);
   }

   @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public fun String.capitalize(locale: Locale): String {
      var var4: java.lang.String = var0;
      if (var0.length() > 0) {
         val var2: Char = var0.charAt(0);
         var4 = var0;
         if (Character.isLowerCase(var2)) {
            val var8: StringBuilder = new StringBuilder();
            val var3: Char = Character.toTitleCase(var2);
            if (var3 != Character.toUpperCase(var2)) {
               var8.append(var3);
            } else {
               val var5: java.lang.String = var0.substring(0, 1);
               val var7: java.lang.String = var5.toUpperCase(var1);
               var8.append(var7);
            }

            var0 = var0.substring(1);
            var8.append(var0);
            var4 = var8.toString();
         }
      }

      return var4;
   }

   @JvmStatic
   public inline fun String.codePointAt(index: Int): Int {
      return var0.codePointAt(var1);
   }

   @JvmStatic
   public inline fun String.codePointBefore(index: Int): Int {
      return var0.codePointBefore(var1);
   }

   @JvmStatic
   public inline fun String.codePointCount(beginIndex: Int, endIndex: Int): Int {
      return var0.codePointCount(var1, var2);
   }

   @JvmStatic
   public fun String.compareTo(other: String, ignoreCase: Boolean = false): Int {
      return if (var2) var0.compareToIgnoreCase(var1) else var0.compareTo(var1);
   }

   @JvmStatic
   public fun CharArray.concatToString(): String {
      return new java.lang.String(var0);
   }

   @JvmStatic
   public fun CharArray.concatToString(startIndex: Int = 0, endIndex: Int = var0.length): String {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length);
      return new java.lang.String(var0, var1, var2 - var1);
   }

   @JvmStatic
   public infix fun CharSequence?.contentEquals(other: CharSequence?): Boolean {
      val var2: Boolean;
      if (var0 is java.lang.String && var1 != null) {
         var2 = (var0 as java.lang.String).contentEquals(var1);
      } else {
         var2 = StringsKt.contentEqualsImpl(var0, var1);
      }

      return var2;
   }

   @JvmStatic
   public fun CharSequence?.contentEquals(other: CharSequence?, ignoreCase: Boolean): Boolean {
      if (var2) {
         var2 = StringsKt.contentEqualsIgnoreCaseImpl(var0, var1);
      } else {
         var2 = StringsKt.contentEquals(var0, var1);
      }

      return var2;
   }

   @JvmStatic
   public inline fun String.contentEquals(charSequence: CharSequence): Boolean {
      return var0.contentEquals(var1);
   }

   @JvmStatic
   public inline fun String.contentEquals(stringBuilder: StringBuffer): Boolean {
      return var0.contentEquals(var1);
   }

   @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { it.lowercase(Locale.getDefault()) }", imports = ["java.util.Locale"]))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public fun String.decapitalize(): String {
      var var1: java.lang.String = var0;
      if (var0.length() > 0) {
         var1 = var0;
         if (!Character.isLowerCase(var0.charAt(0))) {
            val var5: StringBuilder = new StringBuilder();
            val var3: java.lang.String = var0.substring(0, 1);
            val var2: Locale = Locale.getDefault();
            val var6: java.lang.String = var3.toLowerCase(var2);
            var5.append(var6);
            var0 = var0.substring(1);
            var5.append(var0);
            var1 = var5.toString();
         }
      }

      return var1;
   }

   @Deprecated(message = "Use replaceFirstChar instead.", replaceWith = @ReplaceWith(expression = "replaceFirstChar { it.lowercase(locale) }", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public fun String.decapitalize(locale: Locale): String {
      var var2: java.lang.String = var0;
      if (var0.length() > 0) {
         var2 = var0;
         if (!Character.isLowerCase(var0.charAt(0))) {
            val var6: StringBuilder = new StringBuilder();
            val var3: java.lang.String = var0.substring(0, 1);
            val var5: java.lang.String = var3.toLowerCase(var1);
            var6.append(var5);
            var0 = var0.substring(1);
            var6.append(var0);
            var2 = var6.toString();
         }
      }

      return var2;
   }

   @JvmStatic
   public fun ByteArray.decodeToString(): String {
      return new java.lang.String(var0, Charsets.UTF_8);
   }

   @JvmStatic
   public fun ByteArray.decodeToString(startIndex: Int = 0, endIndex: Int = var0.length, throwOnInvalidSequence: Boolean = false): String {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length);
      if (!var3) {
         return new java.lang.String(var0, var1, var2 - var1, Charsets.UTF_8);
      } else {
         val var4: java.lang.String = Charsets.UTF_8
            .newDecoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT)
            .decode(ByteBuffer.wrap(var0, var1, var2 - var1))
            .toString();
         return var4;
      }
   }

   @JvmStatic
   public fun String.encodeToByteArray(): ByteArray {
      val var1: ByteArray = var0.getBytes(Charsets.UTF_8);
      return var1;
   }

   @JvmStatic
   public fun String.encodeToByteArray(startIndex: Int = 0, endIndex: Int = var0.length(), throwOnInvalidSequence: Boolean = false): ByteArray {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
      if (!var3) {
         val var10: java.lang.String = var0.substring(var1, var2);
         val var7: Charset = Charsets.UTF_8;
         val var8: ByteArray = var10.getBytes(var7);
         return var8;
      } else {
         val var4: ByteBuffer = Charsets.UTF_8
            .newEncoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT)
            .encode(CharBuffer.wrap(var0, var1, var2));
         if (var4.hasArray() && var4.arrayOffset() == 0) {
            var1 = var4.remaining();
            val var5: ByteArray = var4.array();
            if (var1 == var5.length) {
               val var11: ByteArray = var4.array();
               return var11;
            }
         }

         val var6: ByteArray = new byte[var4.remaining()];
         var4.get(var6);
         return var6;
      }
   }

   @JvmStatic
   public fun String.endsWith(suffix: String, ignoreCase: Boolean = false): Boolean {
      return if (!var2) var0.endsWith(var1) else StringsKt.regionMatches(var0, var0.length() - var1.length(), var1, 0, var1.length(), true);
   }

   @JvmStatic
   public fun String?.equals(other: String?, ignoreCase: Boolean = false): Boolean {
      if (var0 == null) {
         if (var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      } else {
         if (!var2) {
            var2 = var0.equals(var1);
         } else {
            var2 = var0.equalsIgnoreCase(var1);
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun String.format(locale: Locale?, vararg args: Any?): String {
      var0 = java.lang.String.format(var1, var0, Arrays.copyOf(var2, var2.length));
      return var0;
   }

   @JvmStatic
   public inline fun String.format(vararg args: Any?): String {
      var0 = java.lang.String.format(var0, Arrays.copyOf(var1, var1.length));
      return var0;
   }

   @JvmStatic
   public inline fun Companion.format(format: String, vararg args: Any?): String {
      val var3: java.lang.String = java.lang.String.format(var1, Arrays.copyOf(var2, var2.length));
      return var3;
   }

   @JvmStatic
   public inline fun Companion.format(locale: Locale?, format: String, vararg args: Any?): String {
      val var4: java.lang.String = java.lang.String.format(var1, var2, Arrays.copyOf(var3, var3.length));
      return var4;
   }

   @JvmStatic
   public inline fun String.intern(): String {
      var0 = var0.intern();
      return var0;
   }

   @JvmStatic
   public inline fun String.lowercase(): String {
      var0 = var0.toLowerCase(Locale.ROOT);
      return var0;
   }

   @JvmStatic
   public inline fun String.lowercase(locale: Locale): String {
      var0 = var0.toLowerCase(var1);
      return var0;
   }

   @JvmStatic
   internal inline fun String.nativeIndexOf(ch: Char, fromIndex: Int): Int {
      return var0.indexOf(var1, var2);
   }

   @JvmStatic
   internal inline fun String.nativeIndexOf(str: String, fromIndex: Int): Int {
      return var0.indexOf(var1, var2);
   }

   @JvmStatic
   internal inline fun String.nativeLastIndexOf(ch: Char, fromIndex: Int): Int {
      return var0.lastIndexOf(var1, var2);
   }

   @JvmStatic
   internal inline fun String.nativeLastIndexOf(str: String, fromIndex: Int): Int {
      return var0.lastIndexOf(var1, var2);
   }

   @JvmStatic
   public inline fun String.offsetByCodePoints(index: Int, codePointOffset: Int): Int {
      return var0.offsetByCodePoints(var1, var2);
   }

   @JvmStatic
   public fun CharSequence.regionMatches(thisOffset: Int, other: CharSequence, otherOffset: Int, length: Int, ignoreCase: Boolean = false): Boolean {
      return if (var0 is java.lang.String && var2 is java.lang.String)
         StringsKt.regionMatches(var0 as java.lang.String, var1, var2 as java.lang.String, var3, var4, var5)
         else
         StringsKt.regionMatchesImpl(var0, var1, var2, var3, var4, var5);
   }

   @JvmStatic
   public fun String.regionMatches(thisOffset: Int, other: String, otherOffset: Int, length: Int, ignoreCase: Boolean = false): Boolean {
      if (!var5) {
         var5 = var0.regionMatches(var1, var2, var3, var4);
      } else {
         var5 = var0.regionMatches(var5, var1, var2, var3, var4);
      }

      return var5;
   }

   @JvmStatic
   public fun CharSequence.repeat(n: Int): String {
      if (var1 < 0) {
         val var8: StringBuilder = new StringBuilder("Count 'n' must be non-negative, but was ");
         var8.append(var1);
         var8.append('.');
         throw new IllegalArgumentException(var8.toString().toString());
      } else {
         var var5: java.lang.String = "";
         if (var1 != 0) {
            var var3: Int = 1;
            if (var1 != 1) {
               val var4: Int = var0.length();
               var5 = "";
               if (var4 != 0) {
                  if (var4 != 1) {
                     val var10: StringBuilder = new StringBuilder(var0.length() * var1);
                     if (1 <= var1) {
                        while (true) {
                           var10.append(var0);
                           if (var3 == var1) {
                              break;
                           }

                           var3++;
                        }
                     }

                     var5 = var10.toString();
                  } else {
                     var3 = 0;
                     val var2: Char = var0.charAt(0);

                     for (var7 = new char[var1]; var3 < var1; var3++) {
                        var7[var3] = var2;
                     }

                     var5 = new java.lang.String(var7);
                  }
               }
            } else {
               var5 = var0.toString();
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun String.replace(oldChar: Char, newChar: Char, ignoreCase: Boolean = false): String {
      if (!var3) {
         var0 = var0.replace(var1, var2);
         return var0;
      } else {
         val var7: StringBuilder = new StringBuilder(var0.length());
         val var8: java.lang.CharSequence = var0;

         for (int var6 = 0; var6 < var8.length(); var6++) {
            val var5: Char = var8.charAt(var6);
            var var4: Char = var5;
            if (CharsKt.equals(var5, var1, var3)) {
               var4 = var2;
            }

            var7.append(var4);
         }

         return var7.toString();
      }
   }

   @JvmStatic
   public fun String.replace(oldValue: String, newValue: String, ignoreCase: Boolean = false): String {
      val var10: java.lang.CharSequence = var0;
      var var5: Int = 0;
      var var4: Int = StringsKt.indexOf(var10, var1, 0, var3);
      if (var4 < 0) {
         return var0;
      } else {
         val var8: Int = var1.length();
         val var9: Int = RangesKt.coerceAtLeast(var8, 1);
         var var6: Int = var0.length() - var8 + var2.length();
         if (var6 < 0) {
            throw new OutOfMemoryError();
         } else {
            val var11: StringBuilder = new StringBuilder(var6);

            val var7: Int;
            do {
               var11.append(var10, var5, var4);
               var11.append(var2);
               var6 = var4 + var8;
               if (var4 >= var0.length()) {
                  break;
               }

               var7 = StringsKt.indexOf(var10, var1, var4 + var9, var3);
               var5 = var6;
               var4 = var7;
            } while (var7 > 0);

            var11.append(var10, var6, var0.length());
            var0 = var11.toString();
            return var0;
         }
      }
   }

   @JvmStatic
   public fun String.replaceFirst(oldChar: Char, newChar: Char, ignoreCase: Boolean = false): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.indexOf$default(var0, var1, 0, var3, 2, null);
      if (var4 >= 0) {
         var0 = StringsKt.replaceRange(var5, var4, var4 + 1, java.lang.String.valueOf(var2)).toString();
      }

      return var0;
   }

   @JvmStatic
   public fun String.replaceFirst(oldValue: String, newValue: String, ignoreCase: Boolean = false): String {
      val var5: java.lang.CharSequence = var0;
      val var4: Int = StringsKt.indexOf$default(var0, var1, 0, var3, 2, null);
      if (var4 >= 0) {
         var0 = StringsKt.replaceRange(var5, var4, var1.length() + var4, var2).toString();
      }

      return var0;
   }

   @JvmStatic
   public fun CharSequence.split(regex: Pattern, limit: Int = 0): List<String> {
      StringsKt.requireNonNegativeLimit(var2);
      var var3: Int = var2;
      if (var2 == 0) {
         var3 = -1;
      }

      val var4: Array<java.lang.String> = var1.split(var0, var3);
      return ArraysKt.asList(var4);
   }

   @JvmStatic
   public fun String.startsWith(prefix: String, startIndex: Int, ignoreCase: Boolean = false): Boolean {
      return if (!var3) var0.startsWith(var1, var2) else StringsKt.regionMatches(var0, var2, var1, 0, var1.length(), var3);
   }

   @JvmStatic
   public fun String.startsWith(prefix: String, ignoreCase: Boolean = false): Boolean {
      return if (!var2) var0.startsWith(var1) else StringsKt.regionMatches(var0, 0, var1, 0, var1.length(), var2);
   }

   @JvmStatic
   public inline fun String.substring(startIndex: Int): String {
      var0 = var0.substring(var1);
      return var0;
   }

   @JvmStatic
   public inline fun String.substring(startIndex: Int, endIndex: Int): String {
      var0 = var0.substring(var1, var2);
      return var0;
   }

   @JvmStatic
   public inline fun String.toByteArray(charset: Charset = Charsets.UTF_8): ByteArray {
      val var2: ByteArray = var0.getBytes(var1);
      return var2;
   }

   @JvmStatic
   public inline fun String.toCharArray(): CharArray {
      val var1: CharArray = var0.toCharArray();
      return var1;
   }

   @JvmStatic
   public fun String.toCharArray(startIndex: Int = 0, endIndex: Int = var0.length()): CharArray {
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
      val var3: CharArray = new char[var2 - var1];
      var0.getChars(var1, var2, var3, 0);
      return var3;
   }

   @JvmStatic
   public inline fun String.toCharArray(destination: CharArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length()): CharArray {
      var0.getChars(var3, var4, var1, var2);
      return var1;
   }

   @Deprecated(message = "Use lowercase() instead.", replaceWith = @ReplaceWith(expression = "lowercase(Locale.getDefault())", imports = ["java.util.Locale"]))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.5")
   @JvmStatic
   public inline fun String.toLowerCase(): String {
      var0 = var0.toLowerCase();
      return var0;
   }

   @Deprecated(message = "Use lowercase() instead.", replaceWith = @ReplaceWith(expression = "lowercase(locale)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.5")
   @JvmStatic
   public inline fun String.toLowerCase(locale: Locale): String {
      var0 = var0.toLowerCase(var1);
      return var0;
   }

   @JvmStatic
   public inline fun String.toPattern(flags: Int = 0): Pattern {
      val var2: Pattern = Pattern.compile(var0, var1);
      return var2;
   }

   @Deprecated(message = "Use uppercase() instead.", replaceWith = @ReplaceWith(expression = "uppercase(Locale.getDefault())", imports = ["java.util.Locale"]))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.5")
   @JvmStatic
   public inline fun String.toUpperCase(): String {
      var0 = var0.toUpperCase();
      return var0;
   }

   @Deprecated(message = "Use uppercase() instead.", replaceWith = @ReplaceWith(expression = "uppercase(locale)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.5")
   @JvmStatic
   public inline fun String.toUpperCase(locale: Locale): String {
      var0 = var0.toUpperCase(var1);
      return var0;
   }

   @JvmStatic
   public inline fun String.uppercase(): String {
      var0 = var0.toUpperCase(Locale.ROOT);
      return var0;
   }

   @JvmStatic
   public inline fun String.uppercase(locale: Locale): String {
      var0 = var0.toUpperCase(var1);
      return var0;
   }
}
