package com.mixpanel.mixpanel_flutter;

import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

public class MixpanelMessageCodec extends StandardMessageCodec {
   static final int DATE_TIME = 128;
   static final int URI = 129;
   static final Charset UTF8 = Charset.forName("UTF8");
   static final MixpanelMessageCodec instance = new MixpanelMessageCodec();

   @Override
   protected Object readValueOfType(byte var1, ByteBuffer var2) {
      if (var1 == -128) {
         return new Date(var2.getLong());
      } else {
         if (var1 == -127) {
            String var3 = new String(readBytes(var2), UTF8);

            try {
               return new URI(var3);
            } catch (URISyntaxException var4) {
            }
         }

         return super.readValueOfType(var1, var2);
      }
   }

   @Override
   protected void writeValue(ByteArrayOutputStream var1, Object var2) {
      if (var2 instanceof Date) {
         var1.write(128);
         writeLong(var1, ((Date)var2).getTime());
      } else if (var2 instanceof URI) {
         var1.write(129);
         writeBytes(var1, ((URI)var2).toString().getBytes(UTF8));
      } else {
         super.writeValue(var1, var2);
      }
   }
}
