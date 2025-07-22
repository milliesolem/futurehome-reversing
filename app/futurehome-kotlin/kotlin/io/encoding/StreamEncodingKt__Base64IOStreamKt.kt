package kotlin.io.encoding

import java.io.InputStream
import java.io.OutputStream

internal class StreamEncodingKt__Base64IOStreamKt {
   open fun StreamEncodingKt__Base64IOStreamKt() {
   }

   @JvmStatic
   public fun InputStream.decodingWith(base64: Base64): InputStream {
      return new DecodeInputStream(var0, var1);
   }

   @JvmStatic
   public fun OutputStream.encodingWith(base64: Base64): OutputStream {
      return new EncodeOutputStream(var0, var1);
   }
}
