package kotlin.text

internal class StringsKt__StringBuilderJVMKt : StringsKt__RegexExtensionsKt {
   open fun StringsKt__StringBuilderJVMKt() {
   }

   @JvmStatic
   public inline fun StringBuilder.append(value: Byte): StringBuilder {
      var0.append((int)var1);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.append(value: Short): StringBuilder {
      var0.append((int)var1);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Byte): StringBuilder {
      var0.append((int)var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Double): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Float): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Int): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Long): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: StringBuffer?): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: StringBuilder?): StringBuilder {
      var0.append(var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendLine(value: Short): StringBuilder {
      var0.append((int)var1);
      var0.append('\n');
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendRange(value: CharSequence, startIndex: Int, endIndex: Int): StringBuilder {
      var0.append(var1, var2, var3);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.appendRange(value: CharArray, startIndex: Int, endIndex: Int): StringBuilder {
      var0.append(var1, var2, var3 - var2);
      return var0;
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine()", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public fun Appendable.appendln(): Appendable {
      var0 = var0.append(SystemProperties.LINE_SEPARATOR);
      return var0;
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun Appendable.appendln(value: Char): Appendable {
      var0 = var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun Appendable.appendln(value: CharSequence?): Appendable {
      var0 = var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine()", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public fun StringBuilder.appendln(): StringBuilder {
      var0.append(SystemProperties.LINE_SEPARATOR);
      return var0;
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Byte): StringBuilder {
      var0.append((int)var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Char): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Double): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Float): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Int): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Long): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: CharSequence?): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Any?): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: String?): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: StringBuffer?): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: StringBuilder?): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Short): StringBuilder {
      var0.append((int)var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: Boolean): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @Deprecated(message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = []))
   @DeprecatedSinceKotlin(errorSince = "2.1", warningSince = "1.4")
   @JvmStatic
   public inline fun StringBuilder.appendln(value: CharArray): StringBuilder {
      var0.append(var1);
      return StringsKt.appendln(var0);
   }

   @JvmStatic
   public fun StringBuilder.clear(): StringBuilder {
      var0.setLength(0);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.deleteAt(index: Int): StringBuilder {
      var0 = var0.deleteCharAt(var1);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.deleteRange(startIndex: Int, endIndex: Int): StringBuilder {
      var0 = var0.delete(var1, var2);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.insert(index: Int, value: Byte): StringBuilder {
      var0 = var0.insert(var1, (int)var2);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.insert(index: Int, value: Short): StringBuilder {
      var0 = var0.insert(var1, (int)var2);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.insertRange(index: Int, value: CharSequence, startIndex: Int, endIndex: Int): StringBuilder {
      var0 = var0.insert(var1, var2, var3, var4);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.insertRange(index: Int, value: CharArray, startIndex: Int, endIndex: Int): StringBuilder {
      var0 = var0.insert(var1, var2, var3, var4 - var3);
      return var0;
   }

   @JvmStatic
   public inline operator fun StringBuilder.set(index: Int, value: Char) {
      var0.setCharAt(var1, var2);
   }

   @JvmStatic
   public inline fun StringBuilder.setRange(startIndex: Int, endIndex: Int, value: String): StringBuilder {
      var0 = var0.replace(var1, var2, var3);
      return var0;
   }

   @JvmStatic
   public inline fun StringBuilder.toCharArray(destination: CharArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length()) {
      var0.getChars(var3, var4, var1, var2);
   }
}
