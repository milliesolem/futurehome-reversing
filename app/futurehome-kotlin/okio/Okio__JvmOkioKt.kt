package okio

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.nio.file.OpenOption
import java.security.MessageDigest
import java.util.Arrays
import java.util.logging.Logger
import javax.crypto.Cipher
import javax.crypto.Mac
import kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0
import okio.internal.ResourceFileSystem
import okio.internal.ZipFilesKt

@JvmSynthetic
internal class Okio__JvmOkioKt {
   private final val logger: Logger = Logger.getLogger("okio.Okio")

   internal final val isAndroidGetsocknameError: Boolean
      internal final get() {
         val var4: java.lang.Throwable = var0.getCause();
         var var2: Boolean = false;
         if (var4 != null) {
            val var5: java.lang.String = var0.getMessage();
            val var1: Boolean;
            if (var5 != null) {
               var1 = StringsKt.contains$default(var5, "getsockname failed", false, 2, null);
            } else {
               var1 = false;
            }

            var2 = false;
            if (var1) {
               var2 = true;
            }
         }

         return var2;
      }


   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   public fun File.appendingSink(): Sink {
      return Okio.sink(new FileOutputStream(var0, true));
   }

   @JvmStatic
   public fun ClassLoader.asResourceFileSystem(): FileSystem {
      return new ResourceFileSystem(var0, true);
   }

   @JvmStatic
   public fun Sink.cipherSink(cipher: Cipher): CipherSink {
      return new CipherSink(Okio.buffer(var0), var1);
   }

   @JvmStatic
   public fun Source.cipherSource(cipher: Cipher): CipherSource {
      return new CipherSource(Okio.buffer(var0), var1);
   }

   @JvmStatic
   public fun Sink.hashingSink(digest: MessageDigest): HashingSink {
      return new HashingSink(var0, var1);
   }

   @JvmStatic
   public fun Sink.hashingSink(mac: Mac): HashingSink {
      return new HashingSink(var0, var1);
   }

   @JvmStatic
   public fun Source.hashingSource(digest: MessageDigest): HashingSource {
      return new HashingSource(var0, var1);
   }

   @JvmStatic
   public fun Source.hashingSource(mac: Mac): HashingSource {
      return new HashingSource(var0, var1);
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   public fun FileSystem.openZip(zipPath: Path): FileSystem {
      return ZipFilesKt.openZip$default(var1, var0, null, 4, null);
   }

   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   fun sink(var0: File): Sink {
      return Okio.sink$default(var0, false, 1, null);
   }

   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   public fun File.sink(append: Boolean = false): Sink {
      return Okio.sink(new FileOutputStream(var0, var1));
   }

   @JvmStatic
   public fun OutputStream.sink(): Sink {
      return new OutputStreamSink(var0, new Timeout());
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   public fun Socket.sink(): Sink {
      val var1: SocketAsyncTimeout = new SocketAsyncTimeout(var0);
      val var2: OutputStream = var0.getOutputStream();
      return var1.sink(new OutputStreamSink(var2, var1));
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   public fun java.nio.file.Path.sink(vararg options: OpenOption): Sink {
      val var2: OutputStream = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0, Arrays.copyOf(var1, var1.length));
      return Okio.sink(var2);
   }

   @Throws(java/io/FileNotFoundException::class)
   @JvmStatic
   public fun File.source(): Source {
      return new InputStreamSource(new FileInputStream(var0), Timeout.NONE);
   }

   @JvmStatic
   public fun InputStream.source(): Source {
      return new InputStreamSource(var0, new Timeout());
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   public fun Socket.source(): Source {
      val var1: SocketAsyncTimeout = new SocketAsyncTimeout(var0);
      val var2: InputStream = var0.getInputStream();
      return var1.source(new InputStreamSource(var2, var1));
   }

   @Throws(java/io/IOException::class)
   @JvmStatic
   public fun java.nio.file.Path.source(vararg options: OpenOption): Source {
      val var2: InputStream = PathTreeWalk$$ExternalSyntheticApiModelOutline0.m(var0, Arrays.copyOf(var1, var1.length));
      return Okio.source(var2);
   }
}
