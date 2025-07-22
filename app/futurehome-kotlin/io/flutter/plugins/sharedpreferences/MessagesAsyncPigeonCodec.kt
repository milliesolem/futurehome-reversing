package io.flutter.plugins.sharedpreferences

import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private open class MessagesAsyncPigeonCodec : StandardMessageCodec {
   protected override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
      var var5: SharedPreferencesPigeonOptions = null;
      if (var1 == -127) {
         val var14: java.lang.Long = this.readValue(var2) as java.lang.Long;
         var var12: StringListLookupResultType = null;
         if (var14 != null) {
            var12 = StringListLookupResultType.Companion.ofRaw((int)var14.longValue());
         }

         return var12;
      } else if (var1 == -126) {
         var var10: Any = this.readValue(var2);
         if (var10 is java.util.List) {
            var10 = var10 as java.util.List;
         } else {
            var10 = null;
         }

         if (var10 != null) {
            var5 = SharedPreferencesPigeonOptions.Companion.fromList((java.util.List<? extends Object>)var10);
         }

         return var5;
      } else if (var1 == -125) {
         var var8: Any = this.readValue(var2);
         if (var8 is java.util.List) {
            var8 = var8 as java.util.List;
         } else {
            var8 = null;
         }

         var var13: StringListResult = null;
         if (var8 != null) {
            var13 = StringListResult.Companion.fromList((java.util.List<? extends Object>)var8);
         }

         return var13;
      } else {
         return super.readValueOfType(var1, var2);
      }
   }

   protected override fun writeValue(stream: ByteArrayOutputStream, value: Any?) {
      if (var2 is StringListLookupResultType) {
         var1.write(129);
         this.writeValue(var1, (var2 as StringListLookupResultType).getRaw());
      } else if (var2 is SharedPreferencesPigeonOptions) {
         var1.write(130);
         this.writeValue(var1, (var2 as SharedPreferencesPigeonOptions).toList());
      } else if (var2 is StringListResult) {
         var1.write(131);
         this.writeValue(var1, (var2 as StringListResult).toList());
      } else {
         super.writeValue(var1, var2);
      }
   }
}
