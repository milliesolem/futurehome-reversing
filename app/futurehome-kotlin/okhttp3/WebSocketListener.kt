package okhttp3

import kotlin.jvm.internal.Intrinsics
import okio.ByteString

public abstract class WebSocketListener {
   public open fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
      Intrinsics.checkParameterIsNotNull(var1, "webSocket");
      Intrinsics.checkParameterIsNotNull(var3, "reason");
   }

   public open fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
      Intrinsics.checkParameterIsNotNull(var1, "webSocket");
      Intrinsics.checkParameterIsNotNull(var3, "reason");
   }

   public open fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
      Intrinsics.checkParameterIsNotNull(var1, "webSocket");
      Intrinsics.checkParameterIsNotNull(var2, "t");
   }

   public open fun onMessage(webSocket: WebSocket, text: String) {
      Intrinsics.checkParameterIsNotNull(var1, "webSocket");
      Intrinsics.checkParameterIsNotNull(var2, "text");
   }

   public open fun onMessage(webSocket: WebSocket, bytes: ByteString) {
      Intrinsics.checkParameterIsNotNull(var1, "webSocket");
      Intrinsics.checkParameterIsNotNull(var2, "bytes");
   }

   public open fun onOpen(webSocket: WebSocket, response: Response) {
      Intrinsics.checkParameterIsNotNull(var1, "webSocket");
      Intrinsics.checkParameterIsNotNull(var2, "response");
   }
}
