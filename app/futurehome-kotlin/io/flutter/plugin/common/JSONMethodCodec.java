package io.flutter.plugin.common;

import java.nio.ByteBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JSONMethodCodec implements MethodCodec {
   public static final JSONMethodCodec INSTANCE = new JSONMethodCodec();

   private JSONMethodCodec() {
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public Object decodeEnvelope(ByteBuffer var1) {
      Object var2;
      StringBuilder var3;
      JSONArray var14;
      label42: {
         label44: {
            try {
               var10 = JSONMessageCodec.INSTANCE.decodeMessage(var1);
               if (!(var10 instanceof JSONArray)) {
                  break label44;
               }

               var14 = (JSONArray)var10;
               if (var14.length() == 1) {
                  return this.unwrapNull(var14.opt(0));
               }
            } catch (JSONException var9) {
               throw new IllegalArgumentException("Invalid JSON", var9);
            }

            try {
               if (var14.length() != 3) {
                  break label44;
               }

               var3 = (StringBuilder)var14.get(0);
               var2 = this.unwrapNull(var14.opt(1));
               var14 = (JSONArray)this.unwrapNull(var14.opt(2));
               if (!(var3 instanceof String)) {
                  break label44;
               }
            } catch (JSONException var8) {
               throw new IllegalArgumentException("Invalid JSON", var8);
            }

            if (var2 == null) {
               break label42;
            }

            try {
               if (var2 instanceof String) {
                  break label42;
               }
            } catch (JSONException var7) {
               throw new IllegalArgumentException("Invalid JSON", var7);
            }
         }

         try {
            var3 = new StringBuilder("Invalid envelope: ");
            var3.append(var10);
            var2 = new IllegalArgumentException(var3.toString());
            throw var2;
         } catch (JSONException var6) {
            throw new IllegalArgumentException("Invalid JSON", var6);
         }
      }

      try {
         FlutterException var11 = new FlutterException((String)var3, (String)var2, var14);
         throw var11;
      } catch (JSONException var5) {
         throw new IllegalArgumentException("Invalid JSON", var5);
      }
   }

   @Override
   public MethodCall decodeMethodCall(ByteBuffer var1) {
      try {
         Object var5 = JSONMessageCodec.INSTANCE.decodeMessage(var1);
         if (var5 instanceof JSONObject) {
            JSONObject var3 = (JSONObject)var5;
            Object var2 = var3.get("method");
            Object var7 = this.unwrapNull(var3.opt("args"));
            if (var2 instanceof String) {
               return new MethodCall((String)var2, var7);
            }
         }

         StringBuilder var6 = new StringBuilder("Invalid method call: ");
         var6.append(var5);
         IllegalArgumentException var8 = new IllegalArgumentException(var6.toString());
         throw var8;
      } catch (JSONException var4) {
         throw new IllegalArgumentException("Invalid JSON", var4);
      }
   }

   @Override
   public ByteBuffer encodeErrorEnvelope(String var1, String var2, Object var3) {
      return JSONMessageCodec.INSTANCE.encodeMessage(new JSONArray().put(var1).put(JSONUtil.wrap(var2)).put(JSONUtil.wrap(var3)));
   }

   @Override
   public ByteBuffer encodeErrorEnvelopeWithStacktrace(String var1, String var2, Object var3, String var4) {
      return JSONMessageCodec.INSTANCE.encodeMessage(new JSONArray().put(var1).put(JSONUtil.wrap(var2)).put(JSONUtil.wrap(var3)).put(JSONUtil.wrap(var4)));
   }

   @Override
   public ByteBuffer encodeMethodCall(MethodCall var1) {
      try {
         JSONObject var2 = new JSONObject();
         var2.put("method", var1.method);
         var2.put("args", JSONUtil.wrap(var1.arguments));
         return JSONMessageCodec.INSTANCE.encodeMessage(var2);
      } catch (JSONException var3) {
         throw new IllegalArgumentException("Invalid JSON", var3);
      }
   }

   @Override
   public ByteBuffer encodeSuccessEnvelope(Object var1) {
      return JSONMessageCodec.INSTANCE.encodeMessage(new JSONArray().put(JSONUtil.wrap(var1)));
   }

   Object unwrapNull(Object var1) {
      Object var2 = var1;
      if (var1 == JSONObject.NULL) {
         var2 = null;
      }

      return var2;
   }
}
