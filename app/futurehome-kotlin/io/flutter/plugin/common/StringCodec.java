package io.flutter.plugin.common;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class StringCodec implements MessageCodec<String> {
   public static final StringCodec INSTANCE = new StringCodec();
   private static final Charset UTF8 = Charset.forName("UTF8");

   private StringCodec() {
   }

   public String decodeMessage(ByteBuffer var1) {
      if (var1 == null) {
         return null;
      } else {
         int var3 = var1.remaining();
         int var2;
         byte[] var5;
         if (var1.hasArray()) {
            byte[] var4 = var1.array();
            var2 = var1.arrayOffset();
            var5 = var4;
         } else {
            byte[] var6 = new byte[var3];
            var1.get(var6);
            var2 = 0;
            var5 = var6;
         }

         return new String(var5, var2, var3, UTF8);
      }
   }

   public ByteBuffer encodeMessage(String var1) {
      if (var1 == null) {
         return null;
      } else {
         byte[] var2 = var1.getBytes(UTF8);
         ByteBuffer var3 = ByteBuffer.allocateDirect(var2.length);
         var3.put(var2);
         return var3;
      }
   }
}
