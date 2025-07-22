package okhttp3

import java.util.ArrayList
import java.util.UUID
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.Buffer
import okio.BufferedSink
import okio.ByteString

public class MultipartBody internal constructor(boundaryByteString: ByteString, type: MediaType, parts: List<okhttp3.MultipartBody.Part>) : RequestBody {
   public final val boundary: String
      public final get() {
         return this.boundaryByteString.utf8();
      }


   private final val boundaryByteString: ByteString
   private final var contentLength: Long
   private final val contentType: MediaType
   public final val parts: List<okhttp3.MultipartBody.Part>

   public final val size: Int
      public final get() {
         return this.parts.size();
      }


   public final val type: MediaType

   init {
      Intrinsics.checkParameterIsNotNull(var1, "boundaryByteString");
      Intrinsics.checkParameterIsNotNull(var2, "type");
      Intrinsics.checkParameterIsNotNull(var3, "parts");
      super();
      this.boundaryByteString = var1;
      this.type = var2;
      this.parts = var3;
      val var5: MediaType.Companion = MediaType.Companion;
      val var4: StringBuilder = new StringBuilder();
      var4.append(var2);
      var4.append("; boundary=");
      var4.append(this.boundary());
      this.contentType = var5.get(var4.toString());
      this.contentLength = -1L;
   }

   @Throws(java/io/IOException::class)
   private fun writeOrCountBytes(sink: BufferedSink?, countBytes: Boolean): Long {
      var var11: Buffer = null;
      val var12: Buffer = null as Buffer;
      if (var2) {
         var11 = new Buffer();
         var1 = var11;
      }

      val var5: Int = this.parts.size();
      var var7: Long = 0L;

      for (int var3 = 0; var3 < var5; var3++) {
         val var15: MultipartBody.Part = this.parts.get(var3);
         val var13: Headers = var15.headers();
         val var16: RequestBody = var15.body();
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         var1.write(DASHDASH);
         var1.write(this.boundaryByteString);
         var1.write(CRLF);
         if (var13 != null) {
            val var6: Int = var13.size();

            for (int var4 = 0; var4 < var6; var4++) {
               var1.writeUtf8(var13.name(var4)).write(COLONSPACE).writeUtf8(var13.value(var4)).write(CRLF);
            }
         }

         val var18: MediaType = var16.contentType();
         if (var18 != null) {
            var1.writeUtf8("Content-Type: ").writeUtf8(var18.toString()).write(CRLF);
         }

         val var9: Long = var16.contentLength();
         if (var9 != -1L) {
            var1.writeUtf8("Content-Length: ").writeDecimalLong(var9).write(CRLF);
         } else if (var2) {
            if (var11 == null) {
               Intrinsics.throwNpe();
            }

            var11.clear();
            return -1L;
         }

         val var19: ByteArray = CRLF;
         var1.write(CRLF);
         if (var2) {
            var7 += var9;
         } else {
            var16.writeTo(var1);
         }

         var1.write(var19);
      }

      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      val var17: ByteArray = DASHDASH;
      var1.write(DASHDASH);
      var1.write(this.boundaryByteString);
      var1.write(var17);
      var1.write(CRLF);
      var var14: Long = var7;
      if (var2) {
         if (var11 == null) {
            Intrinsics.throwNpe();
         }

         var14 = var7 + var11.size();
         var11.clear();
      }

      return var14;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "boundary", imports = []))
   public fun boundary(): String {
      return this.boundary();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "parts", imports = []))
   public fun parts(): List<okhttp3.MultipartBody.Part> {
      return this.parts;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = []))
   public fun size(): Int {
      return this.size();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "type", imports = []))
   public fun type(): MediaType {
      return this.type;
   }

   @Throws(java/io/IOException::class)
   public override fun contentLength(): Long {
      var var1: Long = this.contentLength;
      if (this.contentLength == -1L) {
         var1 = this.writeOrCountBytes(null, true);
         this.contentLength = var1;
      }

      return var1;
   }

   public override fun contentType(): MediaType {
      return this.contentType;
   }

   public fun part(index: Int): okhttp3.MultipartBody.Part {
      return this.parts.get(var1);
   }

   @Throws(java/io/IOException::class)
   public override fun writeTo(sink: BufferedSink) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      this.writeOrCountBytes(var1, false);
   }

   public class Builder  public constructor(boundary: String = UUID.randomUUID().toString()) {
      private final val boundary: ByteString
      private final val parts: MutableList<okhttp3.MultipartBody.Part>
      private final var type: MediaType

      fun Builder() {
         this(null, 1, null);
      }

      init {
         Intrinsics.checkParameterIsNotNull(var1, "boundary");
         super();
         this.boundary = ByteString.Companion.encodeUtf8(var1);
         this.type = MultipartBody.MIXED;
         this.parts = new ArrayList<>();
      }

      public fun addFormDataPart(name: String, value: String): okhttp3.MultipartBody.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: MultipartBody.Builder = this;
         this.addPart(MultipartBody.Part.Companion.createFormData(var1, var2));
         return this;
      }

      public fun addFormDataPart(name: String, filename: String?, body: RequestBody): okhttp3.MultipartBody.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var3, "body");
         val var4: MultipartBody.Builder = this;
         this.addPart(MultipartBody.Part.Companion.createFormData(var1, var2, var3));
         return this;
      }

      public fun addPart(headers: Headers?, body: RequestBody): okhttp3.MultipartBody.Builder {
         Intrinsics.checkParameterIsNotNull(var2, "body");
         val var3: MultipartBody.Builder = this;
         this.addPart(MultipartBody.Part.Companion.create(var1, var2));
         return this;
      }

      public fun addPart(part: okhttp3.MultipartBody.Part): okhttp3.MultipartBody.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "part");
         val var2: MultipartBody.Builder = this;
         this.parts.add(var1);
         return this;
      }

      public fun addPart(body: RequestBody): okhttp3.MultipartBody.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "body");
         val var2: MultipartBody.Builder = this;
         this.addPart(MultipartBody.Part.Companion.create(var1));
         return this;
      }

      public fun build(): MultipartBody {
         if (!this.parts.isEmpty()) {
            return new MultipartBody(this.boundary, this.type, Util.toImmutableList(this.parts));
         } else {
            throw (new IllegalStateException("Multipart body must have at least one part.".toString())) as java.lang.Throwable;
         }
      }

      public fun setType(type: MediaType): okhttp3.MultipartBody.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "type");
         val var2: MultipartBody.Builder = this;
         if (var1.type() == "multipart") {
            this.type = var1;
            return this;
         } else {
            val var3: StringBuilder = new StringBuilder("multipart != ");
            var3.append(var1);
            throw (new IllegalArgumentException(var3.toString().toString())) as java.lang.Throwable;
         }
      }
   }

   public companion object {
      public final val ALTERNATIVE: MediaType
      private final val COLONSPACE: ByteArray
      private final val CRLF: ByteArray
      private final val DASHDASH: ByteArray
      public final val DIGEST: MediaType
      public final val FORM: MediaType
      public final val MIXED: MediaType
      public final val PARALLEL: MediaType

      internal fun StringBuilder.appendQuotedString(key: String) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$appendQuotedString");
         Intrinsics.checkParameterIsNotNull(var2, "key");
         var1.append('"');
         val var5: Int = var2.length();

         for (int var4 = 0; var4 < var5; var4++) {
            val var3: Char = var2.charAt(var4);
            if (var3 == '\n') {
               var1.append("%0A");
            } else if (var3 == '\r') {
               var1.append("%0D");
            } else if (var3 == '"') {
               var1.append("%22");
            } else {
               var1.append(var3);
            }
         }

         var1.append('"');
      }
   }

   public class Part private constructor(headers: Headers?, body: RequestBody) {
      public final val body: RequestBody
      public final val headers: Headers?

      init {
         this.headers = var1;
         this.body = var2;
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "body", imports = []))
      public fun body(): RequestBody {
         return this.body;
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "headers", imports = []))
      public fun headers(): Headers? {
         return this.headers;
      }

      public companion object {
         public fun create(headers: Headers?, body: RequestBody): okhttp3.MultipartBody.Part {
            Intrinsics.checkParameterIsNotNull(var2, "body");
            var var5: java.lang.String;
            if (var1 != null) {
               var5 = var1.get("Content-Type");
            } else {
               var5 = null;
            }

            var var3: Boolean;
            if (var5 == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               if (var1 != null) {
                  var5 = var1.get("Content-Length");
               } else {
                  var5 = null;
               }

               if (var5 == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var3) {
                  return new MultipartBody.Part(var1, var2, null);
               } else {
                  throw (new IllegalArgumentException("Unexpected header: Content-Length".toString())) as java.lang.Throwable;
               }
            } else {
               throw (new IllegalArgumentException("Unexpected header: Content-Type".toString())) as java.lang.Throwable;
            }
         }

         public fun create(body: RequestBody): okhttp3.MultipartBody.Part {
            Intrinsics.checkParameterIsNotNull(var1, "body");
            val var2: MultipartBody.Part.Companion = this;
            return this.create(null, var1);
         }

         public fun createFormData(name: String, value: String): okhttp3.MultipartBody.Part {
            Intrinsics.checkParameterIsNotNull(var1, "name");
            Intrinsics.checkParameterIsNotNull(var2, "value");
            val var3: MultipartBody.Part.Companion = this;
            return this.createFormData(var1, null, RequestBody.Companion.create$default(RequestBody.Companion, var2, null, 1, null));
         }

         public fun createFormData(name: String, filename: String?, body: RequestBody): okhttp3.MultipartBody.Part {
            Intrinsics.checkParameterIsNotNull(var1, "name");
            Intrinsics.checkParameterIsNotNull(var3, "body");
            val var4: StringBuilder = new StringBuilder();
            var4.append("form-data; name=");
            MultipartBody.Companion.appendQuotedString$okhttp(var4, var1);
            if (var2 != null) {
               var4.append("; filename=");
               MultipartBody.Companion.appendQuotedString$okhttp(var4, var2);
            }

            var1 = var4.toString();
            Intrinsics.checkExpressionValueIsNotNull(var1, "StringBuilder().apply(builderAction).toString()");
            val var7: Headers = new Headers.Builder().addUnsafeNonAscii("Content-Disposition", var1).build();
            val var6: MultipartBody.Part.Companion = this;
            return this.create(var7, var3);
         }
      }
   }
}
