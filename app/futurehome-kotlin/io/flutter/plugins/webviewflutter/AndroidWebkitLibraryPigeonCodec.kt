package io.flutter.plugins.webviewflutter

import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private open class AndroidWebkitLibraryPigeonCodec : StandardMessageCodec {
   protected override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
      if (var1 == -127) {
         val var10: java.lang.Long = this.readValue(var2) as java.lang.Long;
         var var8: FileChooserMode = null;
         if (var10 != null) {
            var8 = FileChooserMode.Companion.ofRaw((int)var10.longValue());
         }

         return var8;
      } else if (var1 == -126) {
         val var11: java.lang.Long = this.readValue(var2) as java.lang.Long;
         var var7: ConsoleMessageLevel = null;
         if (var11 != null) {
            var7 = ConsoleMessageLevel.Companion.ofRaw((int)var11.longValue());
         }

         return var7;
      } else {
         return super.readValueOfType(var1, var2);
      }
   }

   protected override fun writeValue(stream: ByteArrayOutputStream, value: Any?) {
      if (var2 is FileChooserMode) {
         var1.write(129);
         this.writeValue(var1, (var2 as FileChooserMode).getRaw());
      } else if (var2 is ConsoleMessageLevel) {
         var1.write(130);
         this.writeValue(var1, (var2 as ConsoleMessageLevel).getRaw());
      } else {
         super.writeValue(var1, var2);
      }
   }
}
