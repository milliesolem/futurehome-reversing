package okhttp3

import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.Buffer
import okio.BufferedSource
import okio.ByteString

public abstract class ResponseBody : Closeable {
   private final var reader: Reader?

   private fun charset(): Charset {
      val var1: MediaType = this.contentType();
      if (var1 != null) {
         val var2: Charset = var1.charset(Charsets.UTF_8);
         if (var2 != null) {
            return var2;
         }
      }

      return Charsets.UTF_8;
   }

   private inline fun <T : Any> consumeSource(consumer: (BufferedSource) -> T, sizeMapper: (T) -> Int): T {
      val var4: Long = this.contentLength();
      label27:
      if (var4 <= Integer.MAX_VALUE) {
         val var6: Closeable = this.source();
         val var7: java.lang.Throwable = null as java.lang.Throwable;

         try {
            var16 = var1.invoke(var6);
         } catch (var9: java.lang.Throwable) {
            val var15: java.lang.Throwable = var9;

            try {
               throw var15;
            } catch (var8: java.lang.Throwable) {
               CloseableKt.closeFinally(var6, var9);
            }
         }

         CloseableKt.closeFinally(var6, null);
         val var3: Int = (var2.invoke(var16) as java.lang.Number).intValue();
         if (var4 != -1L && var4 != var3) {
            val var17: StringBuilder = new StringBuilder("Content-Length (");
            var17.append(var4);
            var17.append(") and stream length (");
            var17.append(var3);
            var17.append(") disagree");
            throw (new IOException(var17.toString())) as java.lang.Throwable;
         } else {
            return (T)var16;
         }
      } else {
         val var14: StringBuilder = new StringBuilder("Cannot buffer entire body for content length: ");
         var14.append(var4);
         throw (new IOException(var14.toString())) as java.lang.Throwable;
      }
   }

   public fun byteStream(): InputStream {
      return this.source().inputStream();
   }

   @Throws(java/io/IOException::class)
   public fun byteString(): ByteString {
      val var2: Long = this.contentLength();
      label27:
      if (var2 <= Integer.MAX_VALUE) {
         val var13: Closeable = this.source();
         val var5: java.lang.Throwable = null as java.lang.Throwable;

         try {
            var15 = (var13 as BufferedSource).readByteString();
         } catch (var8: java.lang.Throwable) {
            val var6: java.lang.Throwable = var8;

            try {
               throw var6;
            } catch (var7: java.lang.Throwable) {
               CloseableKt.closeFinally(var13, var8);
            }
         }

         CloseableKt.closeFinally(var13, null);
         val var1: Int = var15.size();
         if (var2 != -1L && var2 != var1) {
            val var14: StringBuilder = new StringBuilder("Content-Length (");
            var14.append(var2);
            var14.append(") and stream length (");
            var14.append(var1);
            var14.append(") disagree");
            throw (new IOException(var14.toString())) as java.lang.Throwable;
         } else {
            return var15;
         }
      } else {
         val var4: StringBuilder = new StringBuilder("Cannot buffer entire body for content length: ");
         var4.append(var2);
         throw (new IOException(var4.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun bytes(): ByteArray {
      val var2: Long = this.contentLength();
      label27:
      if (var2 <= Integer.MAX_VALUE) {
         val var13: Closeable = this.source();
         val var5: java.lang.Throwable = null as java.lang.Throwable;

         try {
            var15 = (var13 as BufferedSource).readByteArray();
         } catch (var8: java.lang.Throwable) {
            val var6: java.lang.Throwable = var8;

            try {
               throw var6;
            } catch (var7: java.lang.Throwable) {
               CloseableKt.closeFinally(var13, var8);
            }
         }

         CloseableKt.closeFinally(var13, null);
         val var1: Int = var15.length;
         if (var2 != -1L && var2 != var15.length) {
            val var14: StringBuilder = new StringBuilder("Content-Length (");
            var14.append(var2);
            var14.append(") and stream length (");
            var14.append(var1);
            var14.append(") disagree");
            throw (new IOException(var14.toString())) as java.lang.Throwable;
         } else {
            return var15;
         }
      } else {
         val var4: StringBuilder = new StringBuilder("Cannot buffer entire body for content length: ");
         var4.append(var2);
         throw (new IOException(var4.toString())) as java.lang.Throwable;
      }
   }

   public fun charStream(): Reader {
      var var1: Reader = this.reader;
      if (this.reader == null) {
         var1 = new ResponseBody.BomAwareReader(this.source(), this.charset());
         this.reader = var1;
      }

      return var1;
   }

   public override fun close() {
      Util.closeQuietly(this.source());
   }

   public abstract fun contentLength(): Long {
   }

   public abstract fun contentType(): MediaType? {
   }

   public abstract fun source(): BufferedSource {
   }

   @Throws(java/io/IOException::class)
   public fun string(): String {
      label18: {
         val var1: Closeable = this.source();
         val var2: java.lang.Throwable = null as java.lang.Throwable;

         try {
            var11 = (var1 as BufferedSource).readString(Util.readBomAsCharset(var1 as BufferedSource, this.charset()));
         } catch (var5: java.lang.Throwable) {
            val var3: java.lang.Throwable = var5;

            try {
               throw var3;
            } catch (var4: java.lang.Throwable) {
               CloseableKt.closeFinally(var1, var5);
            }
         }

         CloseableKt.closeFinally(var1, null);
         return var11;
      }
   }

   internal class BomAwareReader(source: BufferedSource, charset: Charset) : Reader {
      private final val charset: Charset
      private final var closed: Boolean
      private final var delegate: Reader?
      private final val source: BufferedSource

      init {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         Intrinsics.checkParameterIsNotNull(var2, "charset");
         super();
         this.source = var1;
         this.charset = var2;
      }

      @Throws(java/io/IOException::class)
      public override fun close() {
         this.closed = true;
         if (this.delegate != null) {
            this.delegate.close();
         } else {
            val var2: ResponseBody.BomAwareReader = this;
            this.source.close();
         }
      }

      @Throws(java/io/IOException::class)
      public override fun read(cbuf: CharArray, off: Int, len: Int): Int {
         Intrinsics.checkParameterIsNotNull(var1, "cbuf");
         if (!this.closed) {
            var var4: Reader = this.delegate;
            if (this.delegate == null) {
               var4 = new InputStreamReader(this.source.inputStream(), Util.readBomAsCharset(this.source, this.charset));
               this.delegate = var4;
            }

            return var4.read(var1, var2, var3);
         } else {
            throw (new IOException("Stream closed")) as java.lang.Throwable;
         }
      }
   }

   public companion object {
      public fun String.toResponseBody(contentType: MediaType? = ...): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toResponseBody");
         var var3: Charset = Charsets.UTF_8;
         var var4: MediaType = var2;
         if (var2 != null) {
            val var5: Charset = MediaType.charset$default(var2, null, 1, null);
            var3 = var5;
            var4 = var2;
            if (var5 == null) {
               var3 = Charsets.UTF_8;
               val var8: MediaType.Companion = MediaType.Companion;
               val var9: StringBuilder = new StringBuilder();
               var9.append(var2);
               var9.append("; charset=utf-8");
               var4 = var8.parse(var9.toString());
            }
         }

         val var7: Buffer = new Buffer().writeString(var1, var3);
         val var6: ResponseBody.Companion = this;
         return this.create(var7, var4, var7.size());
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.asResponseBody(contentType, contentLength)", imports = ["okhttp3.ResponseBody.Companion.asResponseBody"]))
      public fun create(contentType: MediaType?, contentLength: Long, content: BufferedSource): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var4, "content");
         val var5: ResponseBody.Companion = this;
         return this.create(var4, var1, var2);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = ["okhttp3.ResponseBody.Companion.toResponseBody"]))
      public fun create(contentType: MediaType?, content: String): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         val var3: ResponseBody.Companion = this;
         return this.create(var2, var1);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = ["okhttp3.ResponseBody.Companion.toResponseBody"]))
      public fun create(contentType: MediaType?, content: ByteString): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         val var3: ResponseBody.Companion = this;
         return this.create(var2, var1);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toResponseBody(contentType)", imports = ["okhttp3.ResponseBody.Companion.toResponseBody"]))
      public fun create(contentType: MediaType?, content: ByteArray): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         val var3: ResponseBody.Companion = this;
         return this.create(var2, var1);
      }

      public fun BufferedSource.asResponseBody(contentType: MediaType? = ..., contentLength: Long = ...): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$asResponseBody");
         return new ResponseBody(var1, var2, var3) {
            final long $contentLength;
            final MediaType $contentType;
            final BufferedSource $this_asResponseBody;

            {
               this.$this_asResponseBody = var1;
               this.$contentType = var2;
               this.$contentLength = var3;
            }

            @Override
            public long contentLength() {
               return this.$contentLength;
            }

            @Override
            public MediaType contentType() {
               return this.$contentType;
            }

            @Override
            public BufferedSource source() {
               return this.$this_asResponseBody;
            }
         };
      }

      public fun ByteString.toResponseBody(contentType: MediaType? = ...): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toResponseBody");
         val var3: ResponseBody.Companion = this;
         return this.create(new Buffer().write(var1), var2, (long)var1.size());
      }

      public fun ByteArray.toResponseBody(contentType: MediaType? = ...): ResponseBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toResponseBody");
         val var3: ResponseBody.Companion = this;
         return this.create(new Buffer().write(var1), var2, (long)var1.length);
      }
   }
}
