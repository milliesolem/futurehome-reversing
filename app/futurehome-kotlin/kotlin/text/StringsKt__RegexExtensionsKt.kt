package kotlin.text

internal class StringsKt__RegexExtensionsKt : StringsKt__RegexExtensionsJVMKt {
   open fun StringsKt__RegexExtensionsKt() {
   }

   @JvmStatic
   public inline fun String.toRegex(): Regex {
      return new Regex(var0);
   }

   @JvmStatic
   public inline fun String.toRegex(options: Set<RegexOption>): Regex {
      return new Regex(var0, var1);
   }

   @JvmStatic
   public inline fun String.toRegex(option: RegexOption): Regex {
      return new Regex(var0, var1);
   }
}
