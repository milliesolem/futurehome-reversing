package kotlin.io

import java.io.ByteArrayOutputStream

private class ExposingBufferByteArrayOutputStream(size: Int) : ByteArrayOutputStream(var1) {
   public final val buffer: ByteArray
      public final get() {
         val var1: ByteArray = this.buf;
         return var1;
      }

}
