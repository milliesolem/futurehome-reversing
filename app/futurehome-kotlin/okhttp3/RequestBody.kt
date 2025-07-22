package okhttp3

import java.io.Closeable
import java.io.File
import java.nio.charset.Charset
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.BufferedSink
import okio.ByteString
import okio.Okio
import okio.Source

public abstract class RequestBody {
   @Throws(java/io/IOException::class)
   public open fun contentLength(): Long {
      return -1L;
   }

   public abstract fun contentType(): MediaType? {
   }

   public open fun isDuplex(): Boolean {
      return false;
   }

   public open fun isOneShot(): Boolean {
      return false;
   }

   @Throws(java/io/IOException::class)
   public abstract fun writeTo(sink: BufferedSink) {
   }

   public companion object {
      public fun File.asRequestBody(contentType: MediaType? = ...): RequestBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$asRequestBody");
         return new RequestBody(var1, var2) {
            final MediaType $contentType;
            final File $this_asRequestBody;

            {
               this.$this_asRequestBody = var1;
               this.$contentType = var2;
            }

            @Override
            public long contentLength() {
               return this.$this_asRequestBody.length();
            }

            @Override
            public MediaType contentType() {
               return this.$contentType;
            }

            @Override
            public void writeTo(BufferedSink var1) {
               label18: {
                  Intrinsics.checkParameterIsNotNull(var1, "sink");
                  val var2: Closeable = Okio.source(this.$this_asRequestBody);
                  var var3: java.lang.Throwable = null as java.lang.Throwable;

                  try {
                     var1.writeAll(var2 as Source);
                  } catch (var5: java.lang.Throwable) {
                     var3 = var5;

                     try {
                        throw var3;
                     } catch (var4: java.lang.Throwable) {
                        CloseableKt.closeFinally(var2, var5);
                     }
                  }

                  CloseableKt.closeFinally(var2, null);
               }
            }
         };
      }

      public fun String.toRequestBody(contentType: MediaType? = ...): RequestBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toRequestBody");
         var var3: Charset = Charsets.UTF_8;
         var var4: MediaType = var2;
         if (var2 != null) {
            val var5: Charset = MediaType.charset$default(var2, null, 1, null);
            var3 = var5;
            var4 = var2;
            if (var5 == null) {
               var3 = Charsets.UTF_8;
               val var9: MediaType.Companion = MediaType.Companion;
               val var8: StringBuilder = new StringBuilder();
               var8.append(var2);
               var8.append("; charset=utf-8");
               var4 = var9.parse(var8.toString());
            }
         }

         val var6: ByteArray = var1.getBytes(var3);
         Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.String).getBytes(charset)");
         val var7: RequestBody.Companion = this;
         return this.create(var6, var4, 0, var6.length);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'file' argument first to fix Java", replaceWith = @ReplaceWith(expression = "file.asRequestBody(contentType)", imports = ["okhttp3.RequestBody.Companion.asRequestBody"]))
      public fun create(contentType: MediaType?, file: File): RequestBody {
         Intrinsics.checkParameterIsNotNull(var2, "file");
         val var3: RequestBody.Companion = this;
         return this.create(var2, var1);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toRequestBody(contentType)", imports = ["okhttp3.RequestBody.Companion.toRequestBody"]))
      public fun create(contentType: MediaType?, content: String): RequestBody {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         val var3: RequestBody.Companion = this;
         return this.create(var2, var1);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toRequestBody(contentType)", imports = ["okhttp3.RequestBody.Companion.toRequestBody"]))
      public fun create(contentType: MediaType?, content: ByteString): RequestBody {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         val var3: RequestBody.Companion = this;
         return this.create(var2, var1);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toRequestBody(contentType, offset, byteCount)", imports = ["okhttp3.RequestBody.Companion.toRequestBody"]))
      fun create(var1: MediaType, var2: ByteArray): RequestBody {
         return create$default(this, var1, var2, 0, 0, 12, null);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toRequestBody(contentType, offset, byteCount)", imports = ["okhttp3.RequestBody.Companion.toRequestBody"]))
      fun create(var1: MediaType, var2: ByteArray, var3: Int): RequestBody {
         return create$default(this, var1, var2, var3, 0, 8, null);
      }

      @Deprecated(level = DeprecationLevel.WARNING, message = "Moved to extension function. Put the 'content' argument first to fix Java", replaceWith = @ReplaceWith(expression = "content.toRequestBody(contentType, offset, byteCount)", imports = ["okhttp3.RequestBody.Companion.toRequestBody"]))
      public fun create(contentType: MediaType?, content: ByteArray, offset: Int = 0, byteCount: Int = var2.length): RequestBody {
         Intrinsics.checkParameterIsNotNull(var2, "content");
         val var5: RequestBody.Companion = this;
         return this.create(var2, var1, var3, var4);
      }

      public fun ByteString.toRequestBody(contentType: MediaType? = ...): RequestBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toRequestBody");
         return new RequestBody(var1, var2) {
            final MediaType $contentType;
            final ByteString $this_toRequestBody;

            {
               this.$this_toRequestBody = var1;
               this.$contentType = var2;
            }

            @Override
            public long contentLength() {
               return this.$this_toRequestBody.size();
            }

            @Override
            public MediaType contentType() {
               return this.$contentType;
            }

            @Override
            public void writeTo(BufferedSink var1) {
               Intrinsics.checkParameterIsNotNull(var1, "sink");
               var1.write(this.$this_toRequestBody);
            }
         };
      }

      fun create(var1: ByteArray): RequestBody {
         return create$default(this, var1, null, 0, 0, 7, null);
      }

      fun create(var1: ByteArray, var2: MediaType): RequestBody {
         return create$default(this, var1, var2, 0, 0, 6, null);
      }

      fun create(var1: ByteArray, var2: MediaType, var3: Int): RequestBody {
         return create$default(this, var1, var2, var3, 0, 4, null);
      }

      public fun ByteArray.toRequestBody(contentType: MediaType? = ..., offset: Int = ..., byteCount: Int = ...): RequestBody {
         Intrinsics.checkParameterIsNotNull(var1, "$this$toRequestBody");
         Util.checkOffsetAndCount((long)var1.length, (long)var3, (long)var4);
         return new RequestBody(var1, var2, var4, var3) {
            final int $byteCount;
            final MediaType $contentType;
            final int $offset;
            final byte[] $this_toRequestBody;

            {
               this.$this_toRequestBody = var1;
               this.$contentType = var2;
               this.$byteCount = var3;
               this.$offset = var4;
            }

            @Override
            public long contentLength() {
               return this.$byteCount;
            }

            @Override
            public MediaType contentType() {
               return this.$contentType;
            }

            @Override
            public void writeTo(BufferedSink var1) {
               Intrinsics.checkParameterIsNotNull(var1, "sink");
               var1.write(this.$this_toRequestBody, this.$offset, this.$byteCount);
            }
         };
      }
   }
}
