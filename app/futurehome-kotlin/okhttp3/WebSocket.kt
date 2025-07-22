package okhttp3

import okio.ByteString

public interface WebSocket {
   public abstract fun cancel() {
   }

   public abstract fun close(code: Int, reason: String?): Boolean {
   }

   public abstract fun queueSize(): Long {
   }

   public abstract fun request(): Request {
   }

   public abstract fun send(text: String): Boolean {
   }

   public abstract fun send(bytes: ByteString): Boolean {
   }

   public interface Factory {
      public abstract fun newWebSocket(request: Request, listener: WebSocketListener): WebSocket {
      }
   }
}
