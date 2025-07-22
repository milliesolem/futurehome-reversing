package okio

import java.util.zip.Inflater

public inline fun Source.inflate(inflater: Inflater = new Inflater()): InflaterSource {
   return new InflaterSource(var0, var1);
}

@JvmSynthetic
fun `inflate$default`(var0: Source, var1: Inflater, var2: Int, var3: Any): InflaterSource {
   if ((var2 and 1) != 0) {
      var1 = new Inflater();
   }

   return new InflaterSource(var0, var1);
}
