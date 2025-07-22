package kotlin

public final val code: Int
   public final inline get() {
      return var0;
   }


public inline fun Char(code: Int): Char {
   if (var0 >= 0 && var0 <= 65535) {
      return (char)var0;
   } else {
      val var1: StringBuilder = new StringBuilder("Invalid Char code: ");
      var1.append(var0);
      throw new IllegalArgumentException(var1.toString());
   }
}
