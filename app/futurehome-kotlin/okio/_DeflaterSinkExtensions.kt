package okio

import java.util.zip.Deflater

public inline fun Sink.deflate(deflater: Deflater = new Deflater()): DeflaterSink {
   return new DeflaterSink(var0, var1);
}

@JvmSynthetic
fun `deflate$default`(var0: Sink, var1: Deflater, var2: Int, var3: Any): DeflaterSink {
   if ((var2 and 1) != 0) {
      var1 = new Deflater();
   }

   return new DeflaterSink(var0, var1);
}
