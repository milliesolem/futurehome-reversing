package io.flutter.plugin.common;

import java.nio.ByteBuffer;

public interface MessageCodec<T> {
   T decodeMessage(ByteBuffer var1);

   ByteBuffer encodeMessage(T var1);
}
