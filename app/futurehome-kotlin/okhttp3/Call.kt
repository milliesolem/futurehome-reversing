package okhttp3

import okio.Timeout

public interface Call : Cloneable {
   public abstract fun cancel() {
   }

   public abstract fun clone(): Call {
   }

   public abstract fun enqueue(responseCallback: Callback) {
   }

   @Throws(java/io/IOException::class)
   public abstract fun execute(): Response {
   }

   public abstract fun isCanceled(): Boolean {
   }

   public abstract fun isExecuted(): Boolean {
   }

   public abstract fun request(): Request {
   }

   public abstract fun timeout(): Timeout {
   }

   public interface Factory {
      public abstract fun newCall(request: Request): Call {
      }
   }
}
