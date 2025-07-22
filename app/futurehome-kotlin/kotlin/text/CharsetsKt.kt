package kotlin.text

import java.nio.charset.Charset

public inline fun charset(charsetName: String): Charset {
   val var1: Charset = Charset.forName(var0);
   return var1;
}
