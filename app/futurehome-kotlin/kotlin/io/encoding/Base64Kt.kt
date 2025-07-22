package kotlin.io.encoding

private final val base64EncodeMap: ByteArray
private final val base64DecodeMap: IntArray
private final val base64UrlEncodeMap: ByteArray
private final val base64UrlDecodeMap: IntArray

@JvmSynthetic
fun `access$getBase64DecodeMap$p`(): IntArray {
   return base64DecodeMap;
}

@JvmSynthetic
fun `access$getBase64EncodeMap$p`(): ByteArray {
   return base64EncodeMap;
}

@JvmSynthetic
fun `access$getBase64UrlDecodeMap$p`(): IntArray {
   return base64UrlDecodeMap;
}

@JvmSynthetic
fun `access$getBase64UrlEncodeMap$p`(): ByteArray {
   return base64UrlEncodeMap;
}

internal fun isInMimeAlphabet(symbol: Int): Boolean {
   return var0 >= 0 && var0 < base64DecodeMap.length && base64DecodeMap[var0] != -1;
}
