package okio

import java.io.Closeable
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.nio.file.OpenOption
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.Mac

// $VF: Class flags could not be determined
internal class Okio {
   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   fun appendingSink(var0: File): Sink {
      return Okio__JvmOkioKt.appendingSink(var0);
   }

   @JvmStatic
   fun asResourceFileSystem(var0: ClassLoader): FileSystem {
      return Okio__JvmOkioKt.asResourceFileSystem(var0);
   }

   @JvmStatic
   fun blackhole(): Sink {
      return Okio__OkioKt.blackhole();
   }

   @JvmStatic
   fun buffer(var0: Sink): BufferedSink {
      return Okio__OkioKt.buffer(var0);
   }

   @JvmStatic
   fun buffer(var0: Source): BufferedSource {
      return Okio__OkioKt.buffer(var0);
   }

   @JvmStatic
   fun cipherSink(var0: Sink, var1: Cipher): CipherSink {
      return Okio__JvmOkioKt.cipherSink(var0, var1);
   }

   @JvmStatic
   fun cipherSource(var0: Source, var1: Cipher): CipherSource {
      return Okio__JvmOkioKt.cipherSource(var0, var1);
   }

   @JvmStatic
   fun hashingSink(var0: Sink, var1: MessageDigest): HashingSink {
      return Okio__JvmOkioKt.hashingSink(var0, var1);
   }

   @JvmStatic
   fun hashingSink(var0: Sink, var1: Mac): HashingSink {
      return Okio__JvmOkioKt.hashingSink(var0, var1);
   }

   @JvmStatic
   fun hashingSource(var0: Source, var1: MessageDigest): HashingSource {
      return Okio__JvmOkioKt.hashingSource(var0, var1);
   }

   @JvmStatic
   fun hashingSource(var0: Source, var1: Mac): HashingSource {
      return Okio__JvmOkioKt.hashingSource(var0, var1);
   }

   @JvmStatic
   fun isAndroidGetsocknameError(var0: AssertionError): Boolean {
      return Okio__JvmOkioKt.isAndroidGetsocknameError(var0);
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   fun openZip(var0: FileSystem, var1: Path): FileSystem {
      return Okio__JvmOkioKt.openZip(var0, var1);
   }

   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   fun sink(var0: File): Sink {
      return Okio__JvmOkioKt.sink(var0);
   }

   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   fun sink(var0: File, var1: Boolean): Sink {
      return Okio__JvmOkioKt.sink(var0, var1);
   }

   @JvmStatic
   fun sink(var0: OutputStream): Sink {
      return Okio__JvmOkioKt.sink(var0);
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   fun sink(var0: Socket): Sink {
      return Okio__JvmOkioKt.sink(var0);
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   fun sink(var0: java.nio.file.Path, vararg var1: OpenOption): Sink {
      return Okio__JvmOkioKt.sink(var0, var1);
   }

   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   fun source(var0: File): Source {
      return Okio__JvmOkioKt.source(var0);
   }

   @JvmStatic
   fun source(var0: InputStream): Source {
      return Okio__JvmOkioKt.source(var0);
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   fun source(var0: Socket): Source {
      return Okio__JvmOkioKt.source(var0);
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   fun source(var0: java.nio.file.Path, vararg var1: OpenOption): Source {
      return Okio__JvmOkioKt.source(var0, var1);
   }

   @JvmStatic
   fun <T extends Closeable, R> use(var0: T, var1: (T?) -> R): R {
      return Okio__OkioKt.use(var0, var1);
   }
}
