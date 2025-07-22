package io.flutter.plugin.common;

import java.nio.ByteBuffer;

public interface MethodCodec {
   Object decodeEnvelope(ByteBuffer var1);

   MethodCall decodeMethodCall(ByteBuffer var1);

   ByteBuffer encodeErrorEnvelope(String var1, String var2, Object var3);

   ByteBuffer encodeErrorEnvelopeWithStacktrace(String var1, String var2, Object var3, String var4);

   ByteBuffer encodeMethodCall(MethodCall var1);

   ByteBuffer encodeSuccessEnvelope(Object var1);
}
