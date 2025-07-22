package kotlin.text

internal class StringsKt__AppendableKt {
   open fun StringsKt__AppendableKt() {
   }

   @JvmStatic
   public fun <T : Appendable> T.append(vararg value: CharSequence?): T {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0.append(var1[var2]);
      }

      return (T)var0;
   }

   @JvmStatic
   internal fun <T> Appendable.appendElement(element: T, transform: ((T) -> CharSequence)?) {
      if (var2 != null) {
         var0.append(var2.invoke(var1) as java.lang.CharSequence);
      } else {
         val var3: Boolean;
         if (var1 == null) {
            var3 = true;
         } else {
            var3 = var1 is java.lang.CharSequence;
         }

         if (var3) {
            var0.append(var1 as java.lang.CharSequence);
         } else if (var1 is Character) {
            var0.append(var1 as Character);
         } else {
            var0.append(var1.toString());
         }
      }
   }

   @JvmStatic
   public inline fun Appendable.appendLine(): Appendable {
      return var0.append('\n');
   }

   @JvmStatic
   public inline fun Appendable.appendLine(value: Char): Appendable {
      return var0.append(var1).append('\n');
   }

   @JvmStatic
   public inline fun Appendable.appendLine(value: CharSequence?): Appendable {
      return var0.append(var1).append('\n');
   }

   @JvmStatic
   public fun <T : Appendable> T.appendRange(value: CharSequence, startIndex: Int, endIndex: Int): T {
      var0 = var0.append(var1, var2, var3);
      return (T)var0;
   }
}
