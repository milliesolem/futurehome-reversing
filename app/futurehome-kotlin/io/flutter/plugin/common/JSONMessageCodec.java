package io.flutter.plugin.common;

import java.nio.ByteBuffer;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class JSONMessageCodec implements MessageCodec<Object> {
   public static final JSONMessageCodec INSTANCE = new JSONMessageCodec();

   private JSONMessageCodec() {
   }

   @Override
   public Object decodeMessage(ByteBuffer var1) {
      if (var1 == null) {
         return null;
      } else {
         try {
            String var2 = StringCodec.INSTANCE.decodeMessage(var1);
            JSONTokener var4 = new JSONTokener(var2);
            var2 = (String)var4.nextValue();
            if (!var4.more()) {
               return var2;
            } else {
               IllegalArgumentException var5 = new IllegalArgumentException("Invalid JSON");
               throw var5;
            }
         } catch (JSONException var3) {
            throw new IllegalArgumentException("Invalid JSON", var3);
         }
      }
   }

   @Override
   public ByteBuffer encodeMessage(Object var1) {
      if (var1 == null) {
         return null;
      } else {
         var1 = JSONUtil.wrap(var1);
         return var1 instanceof String
            ? StringCodec.INSTANCE.encodeMessage(JSONObject.quote((String)var1))
            : StringCodec.INSTANCE.encodeMessage(var1.toString());
      }
   }
}
