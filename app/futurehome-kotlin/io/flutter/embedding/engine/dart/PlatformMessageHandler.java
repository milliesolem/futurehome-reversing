package io.flutter.embedding.engine.dart;

import java.nio.ByteBuffer;

public interface PlatformMessageHandler {
   void handleMessageFromDart(String var1, ByteBuffer var2, int var3, long var4);

   void handlePlatformMessageResponse(int var1, ByteBuffer var2);
}
