package okhttp3.internal.http2

import java.io.IOException
import java.net.ProtocolException
import java.util.ArrayList
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.Response.Builder
import okhttp3.internal.Util
import okhttp3.internal.connection.RealConnection
import okhttp3.internal.http.ExchangeCodec
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.http.RealInterceptorChain
import okhttp3.internal.http.RequestLine
import okhttp3.internal.http.StatusLine
import okio.Sink
import okio.Source

public class Http2ExchangeCodec(client: OkHttpClient, connection: RealConnection, chain: RealInterceptorChain, http2Connection: Http2Connection) : ExchangeCodec {
   private final var canceled: Boolean
   private final val chain: RealInterceptorChain
   public open val connection: RealConnection
   private final val http2Connection: Http2Connection
   private final val protocol: Protocol
   private final var stream: Http2Stream?

   init {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "connection");
      Intrinsics.checkParameterIsNotNull(var3, "chain");
      Intrinsics.checkParameterIsNotNull(var4, "http2Connection");
      super();
      this.connection = var2;
      this.chain = var3;
      this.http2Connection = var4;
      val var5: Protocol;
      if (var1.protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
         var5 = Protocol.H2_PRIOR_KNOWLEDGE;
      } else {
         var5 = Protocol.HTTP_2;
      }

      this.protocol = var5;
   }

   public override fun cancel() {
      this.canceled = true;
      if (this.stream != null) {
         this.stream.closeLater(ErrorCode.CANCEL);
      }
   }

   public override fun createRequestBody(request: Request, contentLength: Long): Sink {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      if (this.stream == null) {
         Intrinsics.throwNpe();
      }

      return this.stream.getSink();
   }

   public override fun finishRequest() {
      if (this.stream == null) {
         Intrinsics.throwNpe();
      }

      this.stream.getSink().close();
   }

   public override fun flushRequest() {
      this.http2Connection.flush();
   }

   public override fun openResponseBodySource(response: Response): Source {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      if (this.stream == null) {
         Intrinsics.throwNpe();
      }

      return this.stream.getSource$okhttp();
   }

   public override fun readResponseHeaders(expectContinue: Boolean): Builder? {
      if (this.stream == null) {
         Intrinsics.throwNpe();
      }

      val var3: Response.Builder = Companion.readHttp2HeadersList(this.stream.takeHeaders(), this.protocol);
      var var5: Response.Builder = var3;
      if (var1) {
         var5 = var3;
         if (var3.getCode$okhttp() == 100) {
            var5 = null;
         }
      }

      return var5;
   }

   public override fun reportedContentLength(response: Response): Long {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      val var2: Long;
      if (!HttpHeaders.promisesBody(var1)) {
         var2 = 0L;
      } else {
         var2 = Util.headersContentLength(var1);
      }

      return var2;
   }

   public override fun trailers(): Headers {
      if (this.stream == null) {
         Intrinsics.throwNpe();
      }

      return this.stream.trailers();
   }

   public override fun writeRequestHeaders(request: Request) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      if (this.stream == null) {
         val var2: Boolean;
         if (var1.body() != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         this.stream = this.http2Connection.newStream(Companion.http2HeadersList(var1), var2);
         if (this.canceled) {
            if (this.stream == null) {
               Intrinsics.throwNpe();
            }

            this.stream.closeLater(ErrorCode.CANCEL);
            throw (new IOException("Canceled")) as java.lang.Throwable;
         } else {
            if (this.stream == null) {
               Intrinsics.throwNpe();
            }

            this.stream.readTimeout().timeout((long)this.chain.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
            if (this.stream == null) {
               Intrinsics.throwNpe();
            }

            this.stream.writeTimeout().timeout((long)this.chain.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
         }
      }
   }

   public companion object {
      private const val CONNECTION: String
      private const val ENCODING: String
      private const val HOST: String
      private final val HTTP_2_SKIPPED_REQUEST_HEADERS: List<String>
      private final val HTTP_2_SKIPPED_RESPONSE_HEADERS: List<String>
      private const val KEEP_ALIVE: String
      private const val PROXY_CONNECTION: String
      private const val TE: String
      private const val TRANSFER_ENCODING: String
      private const val UPGRADE: String

      public fun http2HeadersList(request: Request): List<Header> {
         Intrinsics.checkParameterIsNotNull(var1, "request");
         val var5: Headers = var1.headers();
         val var4: ArrayList = new ArrayList(var5.size() + 4);
         var4.add(new Header(Header.TARGET_METHOD, var1.method()));
         var4.add(new Header(Header.TARGET_PATH, RequestLine.INSTANCE.requestPath(var1.url())));
         val var6: java.lang.String = var1.header("Host");
         if (var6 != null) {
            var4.add(new Header(Header.TARGET_AUTHORITY, var6));
         }

         var4.add(new Header(Header.TARGET_SCHEME, var1.url().scheme()));
         val var3: Int = var5.size();

         for (int var2 = 0; var2 < var3; var2++) {
            val var7: java.lang.String = var5.name(var2);
            val var9: Locale = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
            if (var7 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            val var8: java.lang.String = var7.toLowerCase(var9);
            Intrinsics.checkExpressionValueIsNotNull(var8, "(this as java.lang.String).toLowerCase(locale)");
            if (!Http2ExchangeCodec.access$getHTTP_2_SKIPPED_REQUEST_HEADERS$cp().contains(var8) || var8 == "te" && var5.value(var2) == "trailers") {
               var4.add(new Header(var8, var5.value(var2)));
            }
         }

         return var4;
      }

      public fun readHttp2HeadersList(headerBlock: Headers, protocol: Protocol): Builder {
         Intrinsics.checkParameterIsNotNull(var1, "headerBlock");
         Intrinsics.checkParameterIsNotNull(var2, "protocol");
         var var5: StatusLine = null;
         var var6: StatusLine = null as StatusLine;
         val var7: Headers.Builder = new Headers.Builder();
         val var4: Int = var1.size();
         var var3: Int = 0;

         while (var3 < var4) {
            val var9: java.lang.String = var1.name(var3);
            val var8: java.lang.String = var1.value(var3);
            if (var9 == ":status") {
               val var10: StatusLine.Companion = StatusLine.Companion;
               val var11: StringBuilder = new StringBuilder("HTTP/1.1 ");
               var11.append(var8);
               var6 = var10.parse(var11.toString());
            } else {
               var6 = var5;
               if (!Http2ExchangeCodec.access$getHTTP_2_SKIPPED_RESPONSE_HEADERS$cp().contains(var9)) {
                  var7.addLenient$okhttp(var9, var8);
                  var6 = var5;
               }
            }

            var3++;
            var5 = var6;
         }

         if (var5 != null) {
            return new Response.Builder().protocol(var2).code(var5.code).message(var5.message).headers(var7.build());
         } else {
            throw (new ProtocolException("Expected ':status' header not present")) as java.lang.Throwable;
         }
      }
   }
}
