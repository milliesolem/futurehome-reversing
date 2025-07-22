package kotlin.io

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Reader
import java.io.Writer
import java.nio.charset.Charset
import java.util.NoSuchElementException

public inline fun InputStream.buffered(bufferSize: Int = 8192): BufferedInputStream {
   val var2: BufferedInputStream;
   if (var0 is BufferedInputStream) {
      var2 = var0 as BufferedInputStream;
   } else {
      var2 = new BufferedInputStream(var0, var1);
   }

   return var2;
}

public inline fun OutputStream.buffered(bufferSize: Int = 8192): BufferedOutputStream {
   val var2: BufferedOutputStream;
   if (var0 is BufferedOutputStream) {
      var2 = var0 as BufferedOutputStream;
   } else {
      var2 = new BufferedOutputStream(var0, var1);
   }

   return var2;
}

@JvmSynthetic
fun `buffered$default`(var0: InputStream, var1: Int, var2: Int, var3: Any): BufferedInputStream {
   if ((var2 and 1) != 0) {
      var1 = 8192;
   }

   val var4: BufferedInputStream;
   if (var0 is BufferedInputStream) {
      var4 = var0 as BufferedInputStream;
   } else {
      var4 = new BufferedInputStream(var0, var1);
   }

   return var4;
}

@JvmSynthetic
fun `buffered$default`(var0: OutputStream, var1: Int, var2: Int, var3: Any): BufferedOutputStream {
   if ((var2 and 1) != 0) {
      var1 = 8192;
   }

   val var4: BufferedOutputStream;
   if (var0 is BufferedOutputStream) {
      var4 = var0 as BufferedOutputStream;
   } else {
      var4 = new BufferedOutputStream(var0, var1);
   }

   return var4;
}

public inline fun InputStream.bufferedReader(charset: Charset = Charsets.UTF_8): BufferedReader {
   val var2: Reader = new InputStreamReader(var0, var1);
   val var3: BufferedReader;
   if (var2 is BufferedReader) {
      var3 = var2 as BufferedReader;
   } else {
      var3 = new BufferedReader(var2, 8192);
   }

   return var3;
}

@JvmSynthetic
fun `bufferedReader$default`(var0: InputStream, var1: Charset, var2: Int, var3: Any): BufferedReader {
   if ((var2 and 1) != 0) {
      var1 = Charsets.UTF_8;
   }

   val var4: Reader = new InputStreamReader(var0, var1);
   val var5: BufferedReader;
   if (var4 is BufferedReader) {
      var5 = var4 as BufferedReader;
   } else {
      var5 = new BufferedReader(var4, 8192);
   }

   return var5;
}

public inline fun OutputStream.bufferedWriter(charset: Charset = Charsets.UTF_8): BufferedWriter {
   val var2: Writer = new OutputStreamWriter(var0, var1);
   val var3: BufferedWriter;
   if (var2 is BufferedWriter) {
      var3 = var2 as BufferedWriter;
   } else {
      var3 = new BufferedWriter(var2, 8192);
   }

   return var3;
}

@JvmSynthetic
fun `bufferedWriter$default`(var0: OutputStream, var1: Charset, var2: Int, var3: Any): BufferedWriter {
   if ((var2 and 1) != 0) {
      var1 = Charsets.UTF_8;
   }

   val var4: Writer = new OutputStreamWriter(var0, var1);
   val var5: BufferedWriter;
   if (var4 is BufferedWriter) {
      var5 = var4 as BufferedWriter;
   } else {
      var5 = new BufferedWriter(var4, 8192);
   }

   return var5;
}

public inline fun String.byteInputStream(charset: Charset = Charsets.UTF_8): ByteArrayInputStream {
   val var2: ByteArray = var0.getBytes(var1);
   return new ByteArrayInputStream(var2);
}

@JvmSynthetic
fun `byteInputStream$default`(var0: java.lang.String, var1: Charset, var2: Int, var3: Any): ByteArrayInputStream {
   if ((var2 and 1) != 0) {
      var1 = Charsets.UTF_8;
   }

   val var4: ByteArray = var0.getBytes(var1);
   return new ByteArrayInputStream(var4);
}

public fun InputStream.copyTo(out: OutputStream, bufferSize: Int = 8192): Long {
   val var5: ByteArray = new byte[var2];
   var2 = var0.read(var5);

   var var3: Long;
   for (var3 = 0L; var2 >= 0; var2 = var0.read(var5)) {
      var1.write(var5, 0, var2);
      var3 += var2;
   }

   return var3;
}

@JvmSynthetic
fun `copyTo$default`(var0: InputStream, var1: OutputStream, var2: Int, var3: Int, var4: Any): Long {
   if ((var3 and 2) != 0) {
      var2 = 8192;
   }

   return copyTo(var0, var1, var2);
}

public inline fun ByteArray.inputStream(): ByteArrayInputStream {
   return new ByteArrayInputStream(var0);
}

public inline fun ByteArray.inputStream(offset: Int, length: Int): ByteArrayInputStream {
   return new ByteArrayInputStream(var0, var1, var2);
}

public operator fun BufferedInputStream.iterator(): ByteIterator {
   return new ByteIterator(var0) {
      final BufferedInputStream $this_iterator;
      private boolean finished;
      private int nextByte;
      private boolean nextPrepared;

      {
         this.$this_iterator = var1;
         this.nextByte = -1;
      }

      private final void prepareNext() {
         if (!this.nextPrepared && !this.finished) {
            val var1: Int = this.$this_iterator.read();
            this.nextByte = var1;
            var var2: Boolean = true;
            this.nextPrepared = true;
            if (var1 != -1) {
               var2 = false;
            }

            this.finished = var2;
         }
      }

      public final boolean getFinished() {
         return this.finished;
      }

      public final int getNextByte() {
         return this.nextByte;
      }

      public final boolean getNextPrepared() {
         return this.nextPrepared;
      }

      @Override
      public boolean hasNext() {
         this.prepareNext();
         return this.finished xor true;
      }

      @Override
      public byte nextByte() {
         this.prepareNext();
         if (!this.finished) {
            val var1: Byte = (byte)this.nextByte;
            this.nextPrepared = false;
            return var1;
         } else {
            throw new NoSuchElementException("Input stream is over.");
         }
      }

      public final void setFinished(boolean var1) {
         this.finished = var1;
      }

      public final void setNextByte(int var1) {
         this.nextByte = var1;
      }

      public final void setNextPrepared(boolean var1) {
         this.nextPrepared = var1;
      }
   };
}

public fun InputStream.readBytes(): ByteArray {
   val var1: ByteArrayOutputStream = new ByteArrayOutputStream(Math.max(8192, var0.available()));
   copyTo$default(var0, var1, 0, 2, null);
   val var2: ByteArray = var1.toByteArray();
   return var2;
}

@Deprecated(message = "Use readBytes() overload without estimatedSize parameter", replaceWith = @ReplaceWith(expression = "readBytes()", imports = []))
@DeprecatedSinceKotlin(errorSince = "1.5", warningSince = "1.3")
public fun InputStream.readBytes(estimatedSize: Int = 8192): ByteArray {
   val var2: ByteArrayOutputStream = new ByteArrayOutputStream(Math.max(var1, var0.available()));
   copyTo$default(var0, var2, 0, 2, null);
   val var3: ByteArray = var2.toByteArray();
   return var3;
}

@JvmSynthetic
fun `readBytes$default`(var0: InputStream, var1: Int, var2: Int, var3: Any): ByteArray {
   if ((var2 and 1) != 0) {
      var1 = 8192;
   }

   return readBytes(var0, var1);
}

public inline fun InputStream.reader(charset: Charset = Charsets.UTF_8): InputStreamReader {
   return new InputStreamReader(var0, var1);
}

@JvmSynthetic
fun `reader$default`(var0: InputStream, var1: Charset, var2: Int, var3: Any): InputStreamReader {
   if ((var2 and 1) != 0) {
      var1 = Charsets.UTF_8;
   }

   return new InputStreamReader(var0, var1);
}

public inline fun OutputStream.writer(charset: Charset = Charsets.UTF_8): OutputStreamWriter {
   return new OutputStreamWriter(var0, var1);
}

@JvmSynthetic
fun `writer$default`(var0: OutputStream, var1: Charset, var2: Int, var3: Any): OutputStreamWriter {
   if ((var2 and 1) != 0) {
      var1 = Charsets.UTF_8;
   }

   return new OutputStreamWriter(var0, var1);
}
