package okhttp3.internal.ws

import kotlin.jvm.internal.Intrinsics
import okio.ByteString
import okio.Buffer.UnsafeCursor

public object WebSocketProtocol {
   internal const val ACCEPT_MAGIC: String = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"
   internal const val B0_FLAG_FIN: Int = 128
   internal const val B0_FLAG_RSV1: Int = 64
   internal const val B0_FLAG_RSV2: Int = 32
   internal const val B0_FLAG_RSV3: Int = 16
   internal const val B0_MASK_OPCODE: Int = 15
   internal const val B1_FLAG_MASK: Int = 128
   internal const val B1_MASK_LENGTH: Int = 127
   internal const val CLOSE_CLIENT_GOING_AWAY: Int = 1001
   internal const val CLOSE_MESSAGE_MAX: Long = 123L
   internal const val CLOSE_NO_STATUS_CODE: Int = 1005
   internal const val OPCODE_BINARY: Int = 2
   internal const val OPCODE_CONTINUATION: Int = 0
   internal const val OPCODE_CONTROL_CLOSE: Int = 8
   internal const val OPCODE_CONTROL_PING: Int = 9
   internal const val OPCODE_CONTROL_PONG: Int = 10
   internal const val OPCODE_FLAG_CONTROL: Int = 8
   internal const val OPCODE_TEXT: Int = 1
   internal const val PAYLOAD_BYTE_MAX: Long = 125L
   internal const val PAYLOAD_LONG: Int = 127
   internal const val PAYLOAD_SHORT: Int = 126
   internal const val PAYLOAD_SHORT_MAX: Long = 65535L

   public fun acceptHeader(key: String): String {
      Intrinsics.checkParameterIsNotNull(var1, "key");
      val var2: ByteString.Companion = ByteString.Companion;
      val var3: StringBuilder = new StringBuilder();
      var3.append(var1);
      var3.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
      return var2.encodeUtf8(var3.toString()).sha1().base64();
   }

   public fun closeCodeExceptionMessage(code: Int): String? {
      val var3: java.lang.String;
      if (var1 >= 1000 && var1 < 5000) {
         if ((1004 > var1 || 1006 < var1) && (1015 > var1 || 2999 < var1)) {
            var3 = null;
         } else {
            val var4: StringBuilder = new StringBuilder("Code ");
            var4.append(var1);
            var4.append(" is reserved and may not be used.");
            var3 = var4.toString();
         }
      } else {
         val var2: StringBuilder = new StringBuilder("Code must be in range [1000,5000): ");
         var2.append(var1);
         var3 = var2.toString();
      }

      return var3;
   }

   public fun toggleMask(cursor: UnsafeCursor, key: ByteArray) {
      Intrinsics.checkParameterIsNotNull(var1, "cursor");
      Intrinsics.checkParameterIsNotNull(var2, "key");
      val var6: Int = var2.length;
      var var9: Int = 0;

      do {
         val var8: ByteArray = var1.data;
         var var5: Int = var1.start;
         val var7: Int = var1.end;
         var var4: Int = var9;
         if (var1.data != null) {
            while (true) {
               var4 = var9;
               if (var5 >= var7) {
                  break;
               }

               var9 %= var6;
               var8[var5] ^= var2[var9];
               var5++;
               var9++;
            }
         }

         var9 = var4;
      } while (var1.next() != -1);
   }

   public fun validateCloseCode(code: Int) {
      val var2: java.lang.String = this.closeCodeExceptionMessage(var1);
      val var3: Boolean;
      if (var2 == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3) {
         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         throw (new IllegalArgumentException(var2.toString())) as java.lang.Throwable;
      }
   }
}
