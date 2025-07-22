package okio

private const val FCOMMENT: Int = 4
private const val FEXTRA: Int = 2
private const val FHCRC: Int = 1
private const val FNAME: Int = 3
private const val SECTION_BODY: Byte = 1
private const val SECTION_DONE: Byte = 3
private const val SECTION_HEADER: Byte = 0
private const val SECTION_TRAILER: Byte = 2

private inline fun Int.getBit(bit: Int): Boolean {
   var var2: Boolean = true;
   if ((var0 shr var1 and 1) != 1) {
      var2 = false;
   }

   return var2;
}

public inline fun Source.gzip(): GzipSource {
   return new GzipSource(var0);
}
