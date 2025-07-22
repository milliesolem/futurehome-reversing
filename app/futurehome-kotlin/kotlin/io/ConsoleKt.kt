package kotlin.io

import java.io.InputStream
import java.nio.charset.Charset

public inline fun print(message: Byte) {
   System.out.print(java.lang.Byte.valueOf(var0));
}

public inline fun print(message: Char) {
   System.out.print(var0);
}

public inline fun print(message: Double) {
   System.out.print(var0);
}

public inline fun print(message: Float) {
   System.out.print(var0);
}

public inline fun print(message: Int) {
   System.out.print(var0);
}

public inline fun print(message: Long) {
   System.out.print(var0);
}

public inline fun print(message: Any?) {
   System.out.print(var0);
}

public inline fun print(message: Short) {
   System.out.print(java.lang.Short.valueOf(var0));
}

public inline fun print(message: Boolean) {
   System.out.print(var0);
}

public inline fun print(message: CharArray) {
   System.out.print(var0);
}

public inline fun println() {
   System.out.println();
}

public inline fun println(message: Byte) {
   System.out.println(java.lang.Byte.valueOf(var0));
}

public inline fun println(message: Char) {
   System.out.println(var0);
}

public inline fun println(message: Double) {
   System.out.println(var0);
}

public inline fun println(message: Float) {
   System.out.println(var0);
}

public inline fun println(message: Int) {
   System.out.println(var0);
}

public inline fun println(message: Long) {
   System.out.println(var0);
}

public inline fun println(message: Any?) {
   System.out.println(var0);
}

public inline fun println(message: Short) {
   System.out.println(java.lang.Short.valueOf(var0));
}

public inline fun println(message: Boolean) {
   System.out.println(var0);
}

public inline fun println(message: CharArray) {
   System.out.println(var0);
}

public fun readLine(): String? {
   val var1: LineReader = LineReader.INSTANCE;
   val var2: InputStream = System.in;
   val var0: Charset = Charset.defaultCharset();
   return var1.readLine(var2, var0);
}

public fun readln(): String {
   val var0: java.lang.String = readlnOrNull();
   if (var0 != null) {
      return var0;
   } else {
      throw new ReadAfterEOFException("EOF has already been reached");
   }
}

public fun readlnOrNull(): String? {
   return readLine();
}
