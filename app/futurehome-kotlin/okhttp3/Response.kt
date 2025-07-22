package okhttp3

import java.io.Closeable
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.connection.Exchange
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import okio.BufferedSource

public class Response internal constructor(request: Request,
      protocol: Protocol,
      message: String,
      code: Int,
      handshake: Handshake?,
      headers: Headers,
      body: ResponseBody?,
      networkResponse: Response?,
      cacheResponse: Response?,
      priorResponse: Response?,
      sentRequestAtMillis: Long,
      receivedResponseAtMillis: Long,
      exchange: Exchange?
   ) :
   Closeable {
   public final val body: ResponseBody?

   public final val cacheControl: CacheControl
      public final get() {
         var var1: CacheControl = this.lazyCacheControl;
         if (this.lazyCacheControl == null) {
            var1 = CacheControl.Companion.parse(this.headers);
            this.lazyCacheControl = var1;
         }

         return var1;
      }


   public final val cacheResponse: Response?
   public final val code: Int
   internal final val exchange: Exchange?
   public final val handshake: Handshake?
   public final val headers: Headers

   public final val isRedirect: Boolean
      public final get() {
         if (this.code != 307 && this.code != 308) {
            switch (this.code) {
               case 300:
               case 301:
               case 302:
               case 303:
                  break;
               default:
                  return false;
            }
         }

         return true;
      }


   public final val isSuccessful: Boolean
      public final get() {
         val var2: Boolean;
         if (200 <= this.code && 299 >= this.code) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }


   private final var lazyCacheControl: CacheControl?
   public final val message: String
   public final val networkResponse: Response?
   public final val priorResponse: Response?
   public final val protocol: Protocol
   public final val receivedResponseAtMillis: Long
   public final val request: Request
   public final val sentRequestAtMillis: Long

   init {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      Intrinsics.checkParameterIsNotNull(var2, "protocol");
      Intrinsics.checkParameterIsNotNull(var3, "message");
      Intrinsics.checkParameterIsNotNull(var6, "headers");
      super();
      this.request = var1;
      this.protocol = var2;
      this.message = var3;
      this.code = var4;
      this.handshake = var5;
      this.headers = var6;
      this.body = var7;
      this.networkResponse = var8;
      this.cacheResponse = var9;
      this.priorResponse = var10;
      this.sentRequestAtMillis = var11;
      this.receivedResponseAtMillis = var13;
      this.exchange = var15;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "body", imports = []))
   public fun body(): ResponseBody? {
      return this.body;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cacheControl", imports = []))
   public fun cacheControl(): CacheControl {
      return this.cacheControl();
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cacheResponse", imports = []))
   public fun cacheResponse(): Response? {
      return this.cacheResponse;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "code", imports = []))
   public fun code(): Int {
      return this.code;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "handshake", imports = []))
   public fun handshake(): Handshake? {
      return this.handshake;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "headers", imports = []))
   public fun headers(): Headers {
      return this.headers;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "message", imports = []))
   public fun message(): String {
      return this.message;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "networkResponse", imports = []))
   public fun networkResponse(): Response? {
      return this.networkResponse;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "priorResponse", imports = []))
   public fun priorResponse(): Response? {
      return this.priorResponse;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "protocol", imports = []))
   public fun protocol(): Protocol {
      return this.protocol;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "receivedResponseAtMillis", imports = []))
   public fun receivedResponseAtMillis(): Long {
      return this.receivedResponseAtMillis;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "request", imports = []))
   public fun request(): Request {
      return this.request;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sentRequestAtMillis", imports = []))
   public fun sentRequestAtMillis(): Long {
      return this.sentRequestAtMillis;
   }

   public fun challenges(): List<Challenge> {
      val var2: java.lang.String;
      if (this.code != 401) {
         if (this.code != 407) {
            return CollectionsKt.emptyList();
         }

         var2 = "Proxy-Authenticate";
      } else {
         var2 = "WWW-Authenticate";
      }

      return HttpHeaders.parseChallenges(this.headers, var2);
   }

   public override fun close() {
      if (this.body != null) {
         this.body.close();
      } else {
         throw (new IllegalStateException("response is not eligible for a body and must not be closed".toString())) as java.lang.Throwable;
      }
   }

   fun header(var1: java.lang.String): java.lang.String {
      return header$default(this, var1, null, 2, null);
   }

   public fun header(name: String, defaultValue: String? = null): String? {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      var1 = this.headers.get(var1);
      if (var1 != null) {
         var2 = var1;
      }

      return var2;
   }

   public fun headers(name: String): List<String> {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return this.headers.values(var1);
   }

   public fun newBuilder(): okhttp3.Response.Builder {
      return new Response.Builder(this);
   }

   @Throws(java/io/IOException::class)
   public fun peekBody(byteCount: Long): ResponseBody {
      if (this.body == null) {
         Intrinsics.throwNpe();
      }

      val var4: BufferedSource = this.body.source().peek();
      val var5: Buffer = new Buffer();
      var4.request(var1);
      var5.write(var4, Math.min(var1, var4.getBuffer().size()));
      return ResponseBody.Companion.create(var5, this.body.contentType(), var5.size());
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("Response{protocol=");
      var1.append(this.protocol);
      var1.append(", code=");
      var1.append(this.code);
      var1.append(", message=");
      var1.append(this.message);
      var1.append(", url=");
      var1.append(this.request.url());
      var1.append('}');
      return var1.toString();
   }

   @Throws(java/io/IOException::class)
   public fun trailers(): Headers {
      if (this.exchange != null) {
         return this.exchange.trailers();
      } else {
         throw (new IllegalStateException("trailers not available".toString())) as java.lang.Throwable;
      }
   }

   public open class Builder {
      internal final var body: ResponseBody?
      internal final var cacheResponse: Response?
      internal final var code: Int = -1
      internal final var exchange: Exchange?
      internal final var handshake: Handshake?
      internal final var headers: Headers.Builder = new Headers.Builder()
      internal final var message: String?
      internal final var networkResponse: Response?
      internal final var priorResponse: Response?
      internal final var protocol: Protocol?
      internal final var receivedResponseAtMillis: Long
      internal final var request: Request?
      internal final var sentRequestAtMillis: Long


      internal constructor(response: Response) : Intrinsics.checkParameterIsNotNull(var1, "response") {
         super();
         this.code = -1;
         this.request = var1.request();
         this.protocol = var1.protocol();
         this.code = var1.code();
         this.message = var1.message();
         this.handshake = var1.handshake();
         this.headers = var1.headers().newBuilder();
         this.body = var1.body();
         this.networkResponse = var1.networkResponse();
         this.cacheResponse = var1.cacheResponse();
         this.priorResponse = var1.priorResponse();
         this.sentRequestAtMillis = var1.sentRequestAtMillis();
         this.receivedResponseAtMillis = var1.receivedResponseAtMillis();
         this.exchange = var1.exchange();
      }

      private fun checkPriorResponse(response: Response?) {
         if (var1 != null) {
            val var2: Boolean;
            if (var1.body() == null) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (!var2) {
               throw (new IllegalArgumentException("priorResponse.body != null".toString())) as java.lang.Throwable;
            }
         }
      }

      private fun checkSupportResponse(name: String, response: Response?) {
         if (var2 != null) {
            var var3: Boolean;
            if (var2.body() == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (!var3) {
               val var9: StringBuilder = new StringBuilder();
               var9.append(var1);
               var9.append(".body != null");
               throw (new IllegalArgumentException(var9.toString().toString())) as java.lang.Throwable;
            }

            if (var2.networkResponse() == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (!var3) {
               val var8: StringBuilder = new StringBuilder();
               var8.append(var1);
               var8.append(".networkResponse != null");
               throw (new IllegalArgumentException(var8.toString().toString())) as java.lang.Throwable;
            }

            if (var2.cacheResponse() == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (!var3) {
               val var7: StringBuilder = new StringBuilder();
               var7.append(var1);
               var7.append(".cacheResponse != null");
               throw (new IllegalArgumentException(var7.toString().toString())) as java.lang.Throwable;
            }

            if (var2.priorResponse() == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (!var3) {
               val var6: StringBuilder = new StringBuilder();
               var6.append(var1);
               var6.append(".priorResponse != null");
               throw (new IllegalArgumentException(var6.toString().toString())) as java.lang.Throwable;
            }
         }
      }

      public open fun addHeader(name: String, value: String): okhttp3.Response.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Response.Builder = this;
         this.headers.add(var1, var2);
         return this;
      }

      public open fun body(body: ResponseBody?): okhttp3.Response.Builder {
         val var2: Response.Builder = this;
         this.body = var1;
         return this;
      }

      public open fun build(): Response {
         val var1: Boolean;
         if (this.code >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            if (this.request != null) {
               if (this.protocol != null) {
                  if (this.message != null) {
                     return new Response(
                        this.request,
                        this.protocol,
                        this.message,
                        this.code,
                        this.handshake,
                        this.headers.build(),
                        this.body,
                        this.networkResponse,
                        this.cacheResponse,
                        this.priorResponse,
                        this.sentRequestAtMillis,
                        this.receivedResponseAtMillis,
                        this.exchange
                     );
                  } else {
                     throw (new IllegalStateException("message == null".toString())) as java.lang.Throwable;
                  }
               } else {
                  throw (new IllegalStateException("protocol == null".toString())) as java.lang.Throwable;
               }
            } else {
               throw (new IllegalStateException("request == null".toString())) as java.lang.Throwable;
            }
         } else {
            val var3: StringBuilder = new StringBuilder("code < 0: ");
            var3.append(this.code);
            throw (new IllegalStateException(var3.toString().toString())) as java.lang.Throwable;
         }
      }

      public open fun cacheResponse(cacheResponse: Response?): okhttp3.Response.Builder {
         val var2: Response.Builder = this;
         this.checkSupportResponse("cacheResponse", var1);
         this.cacheResponse = var1;
         return this;
      }

      public open fun code(code: Int): okhttp3.Response.Builder {
         val var2: Response.Builder = this;
         this.code = var1;
         return this;
      }

      public open fun handshake(handshake: Handshake?): okhttp3.Response.Builder {
         val var2: Response.Builder = this;
         this.handshake = var1;
         return this;
      }

      public open fun header(name: String, value: String): okhttp3.Response.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         val var3: Response.Builder = this;
         this.headers.set(var1, var2);
         return this;
      }

      public open fun headers(headers: Headers): okhttp3.Response.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         val var2: Response.Builder = this;
         this.headers = var1.newBuilder();
         return this;
      }

      internal fun initExchange(deferredTrailers: Exchange) {
         Intrinsics.checkParameterIsNotNull(var1, "deferredTrailers");
         this.exchange = var1;
      }

      public open fun message(message: String): okhttp3.Response.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "message");
         val var2: Response.Builder = this;
         this.message = var1;
         return this;
      }

      public open fun networkResponse(networkResponse: Response?): okhttp3.Response.Builder {
         val var2: Response.Builder = this;
         this.checkSupportResponse("networkResponse", var1);
         this.networkResponse = var1;
         return this;
      }

      public open fun priorResponse(priorResponse: Response?): okhttp3.Response.Builder {
         val var2: Response.Builder = this;
         this.checkPriorResponse(var1);
         this.priorResponse = var1;
         return this;
      }

      public open fun protocol(protocol: Protocol): okhttp3.Response.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "protocol");
         val var2: Response.Builder = this;
         this.protocol = var1;
         return this;
      }

      public open fun receivedResponseAtMillis(receivedResponseAtMillis: Long): okhttp3.Response.Builder {
         val var3: Response.Builder = this;
         this.receivedResponseAtMillis = var1;
         return this;
      }

      public open fun removeHeader(name: String): okhttp3.Response.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         val var2: Response.Builder = this;
         this.headers.removeAll(var1);
         return this;
      }

      public open fun request(request: Request): okhttp3.Response.Builder {
         Intrinsics.checkParameterIsNotNull(var1, "request");
         val var2: Response.Builder = this;
         this.request = var1;
         return this;
      }

      public open fun sentRequestAtMillis(sentRequestAtMillis: Long): okhttp3.Response.Builder {
         val var3: Response.Builder = this;
         this.sentRequestAtMillis = var1;
         return this;
      }
   }
}
