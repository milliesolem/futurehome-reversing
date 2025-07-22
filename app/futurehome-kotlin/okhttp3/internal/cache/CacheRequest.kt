package okhttp3.internal.cache

import okio.Sink

public interface CacheRequest {
   public abstract fun abort() {
   }

   @Throws(java/io/IOException::class)
   public abstract fun body(): Sink {
   }
}
