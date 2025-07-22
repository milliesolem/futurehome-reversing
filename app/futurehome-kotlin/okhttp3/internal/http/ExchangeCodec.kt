package okhttp3.internal.http

import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import okhttp3.Response.Builder
import okhttp3.internal.connection.RealConnection
import okio.Sink
import okio.Source

public interface ExchangeCodec {
   public val connection: RealConnection

   public abstract fun cancel() {
   }

   @Throws(java/io/IOException::class)
   public abstract fun createRequestBody(request: Request, contentLength: Long): Sink {
   }

   @Throws(java/io/IOException::class)
   public abstract fun finishRequest() {
   }

   @Throws(java/io/IOException::class)
   public abstract fun flushRequest() {
   }

   @Throws(java/io/IOException::class)
   public abstract fun openResponseBodySource(response: Response): Source {
   }

   @Throws(java/io/IOException::class)
   public abstract fun readResponseHeaders(expectContinue: Boolean): Builder? {
   }

   @Throws(java/io/IOException::class)
   public abstract fun reportedContentLength(response: Response): Long {
   }

   @Throws(java/io/IOException::class)
   public abstract fun trailers(): Headers {
   }

   @Throws(java/io/IOException::class)
   public abstract fun writeRequestHeaders(request: Request) {
   }

   public companion object {
      public const val DISCARD_STREAM_TIMEOUT_MILLIS: Int = 100
   }
}
