package io.flutter.plugin.common;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class BinaryCodec implements MessageCodec<ByteBuffer> {
   public static final BinaryCodec INSTANCE = new BinaryCodec();
   public static final BinaryCodec INSTANCE_DIRECT = new BinaryCodec(true);
   private final boolean returnsDirectByteBufferFromDecoding;

   private BinaryCodec() {
      this.returnsDirectByteBufferFromDecoding = false;
   }

   private BinaryCodec(boolean var1) {
      this.returnsDirectByteBufferFromDecoding = var1;
   }

   public ByteBuffer decodeMessage(ByteBuffer var1) {
      if (var1 == null) {
         return var1;
      } else if (this.returnsDirectByteBufferFromDecoding) {
         return var1;
      } else {
         ByteBuffer var2 = ByteBuffer.allocate(var1.capacity());
         var2.put(var1);
         ((Buffer)var2).rewind();
         return var2;
      }
   }

   public ByteBuffer encodeMessage(ByteBuffer var1) {
      return var1;
   }
}
