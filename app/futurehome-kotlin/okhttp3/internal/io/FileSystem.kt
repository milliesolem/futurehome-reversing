package okhttp3.internal.io

import java.io.File
import okio.Sink
import okio.Source

public interface FileSystem {
   @Throws(java/io/FileNotFoundException::class)
   public abstract fun appendingSink(file: File): Sink {
   }

   @Throws(java/io/IOException::class)
   public abstract fun delete(file: File) {
   }

   @Throws(java/io/IOException::class)
   public abstract fun deleteContents(directory: File) {
   }

   public abstract fun exists(file: File): Boolean {
   }

   @Throws(java/io/IOException::class)
   public abstract fun rename(from: File, to: File) {
   }

   @Throws(java/io/FileNotFoundException::class)
   public abstract fun sink(file: File): Sink {
   }

   public abstract fun size(file: File): Long {
   }

   @Throws(java/io/FileNotFoundException::class)
   public abstract fun source(file: File): Source {
   }

   public companion object {
      public final val SYSTEM: FileSystem
   }
}
