package kotlin.text

internal class CharsKt__CharKt : CharsKt__CharJVMKt {
   open fun CharsKt__CharKt() {
   }

   @JvmStatic
   public fun Int.digitToChar(): Char {
      if (var0 >= 0 && var0 < 10) {
         return (char)(var0 + 48);
      } else {
         val var1: StringBuilder = new StringBuilder("Int ");
         var1.append(var0);
         var1.append(" is not a decimal digit");
         throw new IllegalArgumentException(var1.toString());
      }
   }

   @JvmStatic
   public fun Int.digitToChar(radix: Int): Char {
      if (2 > var1 || var1 >= 37) {
         val var4: StringBuilder = new StringBuilder("Invalid radix: ");
         var4.append(var1);
         var4.append(". Valid radix values are in range 2..36");
         throw new IllegalArgumentException(var4.toString());
      } else if (var0 >= 0 && var0 < var1) {
         if (var0 < 10) {
            var0 = var0 + 48;
         } else {
            var0 = (char)(var0 + 65) - '\n';
         }

         return (char)var0;
      } else {
         val var2: StringBuilder = new StringBuilder("Digit ");
         var2.append(var0);
         var2.append(" does not represent a valid digit in radix ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   @JvmStatic
   public fun Char.digitToInt(): Int {
      val var1: Int = CharsKt.digitOf(var0, 10);
      if (var1 >= 0) {
         return var1;
      } else {
         val var2: StringBuilder = new StringBuilder("Char ");
         var2.append(var0);
         var2.append(" is not a decimal digit");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   @JvmStatic
   public fun Char.digitToInt(radix: Int): Int {
      val var2: Int = CharsKt.digitToIntOrNull(var0, var1);
      if (var2 != null) {
         return var2;
      } else {
         val var3: StringBuilder = new StringBuilder("Char ");
         var3.append(var0);
         var3.append(" is not a digit in the given radix=");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun Char.digitToIntOrNull(): Int? {
      var var1: Int = CharsKt.digitOf(var0, 10);
      if (var1.intValue() < 0) {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public fun Char.digitToIntOrNull(radix: Int): Int? {
      CharsKt.checkRadix(var1);
      var var2: Int = CharsKt.digitOf(var0, var1);
      if (var2.intValue() < 0) {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun Char.equals(other: Char, ignoreCase: Boolean = false): Boolean {
      if (var0 == var1) {
         return true;
      } else if (!var2) {
         return false;
      } else {
         var0 = Character.toUpperCase(var0);
         var1 = Character.toUpperCase(var1);
         var2 = true;
         if (var0 != var1) {
            if (Character.toLowerCase(var0) == Character.toLowerCase(var1)) {
               var2 = true;
            } else {
               var2 = false;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun Char.isSurrogate(): Boolean {
      var var1: Boolean = false;
      if ('\ud800' <= var0) {
         var1 = false;
         if (var0 < '\ue000') {
            var1 = true;
         }
      }

      return var1;
   }

   @JvmStatic
   public inline operator fun Char.plus(other: String): String {
      val var2: StringBuilder = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return var2.toString();
   }

   @JvmStatic
   public fun Char.titlecase(): String {
      return _OneToManyTitlecaseMappingsKt.titlecaseImpl(var0);
   }
}
