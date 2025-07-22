package okio

import java.io.Closeable
import java.io.Flushable

public interface Sink : Closeable, Flushable {
   @Throws(java/io/IOException::class)
   public abstract override fun close() {
   }

   @Throws(java/io/IOException::class)
   public abstract override fun flush() {
   }

   public abstract fun timeout(): Timeout {
   }

   @Throws(java/io/IOException::class)
   public abstract fun write(source: Buffer, byteCount: Long) {
   }
}
