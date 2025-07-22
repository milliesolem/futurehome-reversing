package okhttp3.internal.http1

import java.io.EOFException
import java.io.IOException
import java.net.ProtocolException
import java.net.Proxy.Type
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.CookieJar
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Response.Builder
import okhttp3.internal.Util
import okhttp3.internal.connection.RealConnection
import okhttp3.internal.http.ExchangeCodec
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.http.RequestLine
import okhttp3.internal.http.StatusLine
import okio.Buffer
import okio.BufferedSink
import okio.BufferedSource
import okio.ForwardingTimeout
import okio.Sink
import okio.Source
import okio.Timeout

public class Http1ExchangeCodec(client: OkHttpClient?, connection: RealConnection, source: BufferedSource, sink: BufferedSink) : ExchangeCodec {
   private final val client: OkHttpClient?
   public open val connection: RealConnection
   private final val headersReader: HeadersReader

   public final val isClosed: Boolean
      public final get() {
         val var1: Boolean;
         if (this.state == 6) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   private final val sink: BufferedSink
   private final val source: BufferedSource
   private final var state: Int
   private final var trailers: Headers?

   private final val isChunked: Boolean
      private final get() {
         return StringsKt.equals("chunked", var1.header("Transfer-Encoding"), true);
      }


   private final val isChunked: Boolean
      private final get() {
         return StringsKt.equals("chunked", Response.header$default(var1, "Transfer-Encoding", null, 2, null), true);
      }


   init {
      Intrinsics.checkParameterIsNotNull(var2, "connection");
      Intrinsics.checkParameterIsNotNull(var3, "source");
      Intrinsics.checkParameterIsNotNull(var4, "sink");
      super();
      this.client = var1;
      this.connection = var2;
      this.source = var3;
      this.sink = var4;
      this.headersReader = new HeadersReader(var3);
   }

   private fun detachTimeout(timeout: ForwardingTimeout) {
      val var2: Timeout = var1.delegate();
      var1.setDelegate(Timeout.NONE);
      var2.clearDeadline();
      var2.clearTimeout();
   }

   private fun newChunkedSink(): Sink {
      var var1: Boolean = true;
      if (this.state != 1) {
         var1 = false;
      }

      if (var1) {
         this.state = 2;
         return new Http1ExchangeCodec.ChunkedSink(this);
      } else {
         val var3: StringBuilder = new StringBuilder("state: ");
         var3.append(this.state);
         throw (new IllegalStateException(var3.toString().toString())) as java.lang.Throwable;
      }
   }

   private fun newChunkedSource(url: HttpUrl): Source {
      val var2: Boolean;
      if (this.state == 4) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         this.state = 5;
         return new Http1ExchangeCodec.ChunkedSource(this, var1);
      } else {
         val var3: StringBuilder = new StringBuilder("state: ");
         var3.append(this.state);
         throw (new IllegalStateException(var3.toString().toString())) as java.lang.Throwable;
      }
   }

   private fun newFixedLengthSource(length: Long): Source {
      val var3: Boolean;
      if (this.state == 4) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         this.state = 5;
         return new Http1ExchangeCodec.FixedLengthSource((long)this, var1);
      } else {
         val var4: StringBuilder = new StringBuilder("state: ");
         var4.append(this.state);
         throw (new IllegalStateException(var4.toString().toString())) as java.lang.Throwable;
      }
   }

   private fun newKnownLengthSink(): Sink {
      var var1: Boolean = true;
      if (this.state != 1) {
         var1 = false;
      }

      if (var1) {
         this.state = 2;
         return new Http1ExchangeCodec.KnownLengthSink(this);
      } else {
         val var3: StringBuilder = new StringBuilder("state: ");
         var3.append(this.state);
         throw (new IllegalStateException(var3.toString().toString())) as java.lang.Throwable;
      }
   }

   private fun newUnknownLengthSource(): Source {
      val var1: Boolean;
      if (this.state == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         this.state = 5;
         this.getConnection().noNewExchanges$okhttp();
         return new Http1ExchangeCodec.UnknownLengthSource(this);
      } else {
         val var2: StringBuilder = new StringBuilder("state: ");
         var2.append(this.state);
         throw (new IllegalStateException(var2.toString().toString())) as java.lang.Throwable;
      }
   }

   public override fun cancel() {
      this.getConnection().cancel();
   }

   public override fun createRequestBody(request: Request, contentLength: Long): Sink {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      if (var1.body() != null && var1.body().isDuplex()) {
         throw (new ProtocolException("Duplex connections are not supported for HTTP/1")) as java.lang.Throwable;
      } else {
         val var4: Sink;
         if (this.isChunked(var1)) {
            var4 = this.newChunkedSink();
         } else {
            if (var2 == -1L) {
               throw (new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!")) as java.lang.Throwable;
            }

            var4 = this.newKnownLengthSink();
         }

         return var4;
      }
   }

   public override fun finishRequest() {
      this.sink.flush();
   }

   public override fun flushRequest() {
      this.sink.flush();
   }

   public override fun openResponseBodySource(response: Response): Source {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      val var4: Source;
      if (!HttpHeaders.promisesBody(var1)) {
         var4 = this.newFixedLengthSource(0L);
      } else if (this.isChunked(var1)) {
         var4 = this.newChunkedSource(var1.request().url());
      } else {
         val var2: Long = Util.headersContentLength(var1);
         if (var2 != -1L) {
            var4 = this.newFixedLengthSource(var2);
         } else {
            var4 = this.newUnknownLengthSource();
         }
      }

      return var4;
   }

   public override fun readResponseHeaders(expectContinue: Boolean): Builder? {
      var var2: Boolean = true;
      if (this.state != 1) {
         if (this.state == 3) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      if (var2) {
         var var13: Response.Builder;
         var var14: StatusLine;
         try {
            var14 = StatusLine.Companion.parse(this.headersReader.readLine());
            var13 = new Response.Builder().protocol(var14.protocol).code(var14.code).message(var14.message).headers(this.headersReader.readHeaders());
         } catch (var9: EOFException) {
            val var7: java.lang.String = this.getConnection().route().address().url().redact();
            val var6: StringBuilder = new StringBuilder("unexpected end of stream on ");
            var6.append(var7);
            throw (new IOException(var6.toString(), var9)) as java.lang.Throwable;
         }

         if (var1) {
            try {
               if (var14.code == 100) {
                  return null;
               }
            } catch (var11: EOFException) {
               val var18: java.lang.String = this.getConnection().route().address().url().redact();
               val var15: StringBuilder = new StringBuilder("unexpected end of stream on ");
               var15.append(var18);
               throw (new IOException(var15.toString(), var11)) as java.lang.Throwable;
            }
         }

         try {
            if (var14.code == 100) {
               this.state = 3;
               return var13;
            }
         } catch (var10: EOFException) {
            val var19: java.lang.String = this.getConnection().route().address().url().redact();
            val var16: StringBuilder = new StringBuilder("unexpected end of stream on ");
            var16.append(var19);
            throw (new IOException(var16.toString(), var10)) as java.lang.Throwable;
         }

         try {
            this.state = 4;
            return var13;
         } catch (var8: EOFException) {
            val var20: java.lang.String = this.getConnection().route().address().url().redact();
            val var17: StringBuilder = new StringBuilder("unexpected end of stream on ");
            var17.append(var20);
            throw (new IOException(var17.toString(), var8)) as java.lang.Throwable;
         }
      } else {
         val var5: StringBuilder = new StringBuilder("state: ");
         var5.append(this.state);
         throw (new IllegalStateException(var5.toString().toString())) as java.lang.Throwable;
      }
   }

   public override fun reportedContentLength(response: Response): Long {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      val var2: Long;
      if (!HttpHeaders.promisesBody(var1)) {
         var2 = 0L;
      } else if (this.isChunked(var1)) {
         var2 = -1L;
      } else {
         var2 = Util.headersContentLength(var1);
      }

      return var2;
   }

   public fun skipConnectBody(response: Response) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      val var2: Long = Util.headersContentLength(var1);
      if (var2 != -1L) {
         val var4: Source = this.newFixedLengthSource(var2);
         Util.skipAll(var4, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
         var4.close();
      }
   }

   public override fun trailers(): Headers {
      val var1: Boolean;
      if (this.state == 6) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var var2: Headers = this.trailers;
         if (this.trailers == null) {
            var2 = Util.EMPTY_HEADERS;
         }

         return var2;
      } else {
         throw (new IllegalStateException("too early; can't read the trailers yet".toString())) as java.lang.Throwable;
      }
   }

   public fun writeRequest(headers: Headers, requestLine: String) {
      Intrinsics.checkParameterIsNotNull(var1, "headers");
      Intrinsics.checkParameterIsNotNull(var2, "requestLine");
      val var7: Boolean;
      if (this.state == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7) {
         val var6: StringBuilder = new StringBuilder("state: ");
         var6.append(this.state);
         throw (new IllegalStateException(var6.toString().toString())) as java.lang.Throwable;
      } else {
         this.sink.writeUtf8(var2).writeUtf8("\r\n");
         val var5: Int = var1.size();

         for (int var8 = 0; var8 < var5; var8++) {
            this.sink.writeUtf8(var1.name(var8)).writeUtf8(": ").writeUtf8(var1.value(var8)).writeUtf8("\r\n");
         }

         this.sink.writeUtf8("\r\n");
         this.state = 1;
      }
   }

   public override fun writeRequestHeaders(request: Request) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      val var2: RequestLine = RequestLine.INSTANCE;
      val var3: Type = this.getConnection().route().proxy().type();
      Intrinsics.checkExpressionValueIsNotNull(var3, "connection.route().proxy.type()");
      this.writeRequest(var1.headers(), var2.get(var1, var3));
   }

   private abstract inner class AbstractSource : Source {
      protected final var closed: Boolean
         internal set

      protected final val timeout: ForwardingTimeout

      open fun AbstractSource(var1: Http1ExchangeCodec) {
         this.this$0 = var1;
         this.timeout = new ForwardingTimeout(Http1ExchangeCodec.access$getSource$p(var1).timeout());
      }

      public override fun read(sink: Buffer, byteCount: Long): Long {
         Intrinsics.checkParameterIsNotNull(var1, "sink");

         try {
            return Http1ExchangeCodec.access$getSource$p(this.this$0).read(var1, var2);
         } catch (var4: IOException) {
            this.this$0.getConnection().noNewExchanges$okhttp();
            this.responseBodyComplete$okhttp();
            throw var4 as java.lang.Throwable;
         }
      }

      internal fun responseBodyComplete() {
         if (Http1ExchangeCodec.access$getState$p(this.this$0) != 6) {
            if (Http1ExchangeCodec.access$getState$p(this.this$0) == 5) {
               Http1ExchangeCodec.access$detachTimeout(this.this$0, this.timeout);
               Http1ExchangeCodec.access$setState$p(this.this$0, 6);
            } else {
               val var1: StringBuilder = new StringBuilder("state: ");
               var1.append(Http1ExchangeCodec.access$getState$p(this.this$0));
               throw (new IllegalStateException(var1.toString())) as java.lang.Throwable;
            }
         }
      }

      public override fun timeout(): Timeout {
         return this.timeout;
      }
   }

   private inner class ChunkedSink : Sink {
      private final var closed: Boolean
      private final val timeout: ForwardingTimeout

      init {
         this.this$0 = var1;
         this.timeout = new ForwardingTimeout(Http1ExchangeCodec.access$getSink$p(var1).timeout());
      }

      public override fun close() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.closed Z
         // 06: istore 1
         // 07: iload 1
         // 08: ifeq 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 0
         // 0f: bipush 1
         // 10: putfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.closed Z
         // 13: aload 0
         // 14: getfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.this$0 Lokhttp3/internal/http1/Http1ExchangeCodec;
         // 17: invokestatic okhttp3/internal/http1/Http1ExchangeCodec.access$getSink$p (Lokhttp3/internal/http1/Http1ExchangeCodec;)Lokio/BufferedSink;
         // 1a: ldc "0\r\n\r\n"
         // 1c: invokeinterface okio/BufferedSink.writeUtf8 (Ljava/lang/String;)Lokio/BufferedSink; 2
         // 21: pop
         // 22: aload 0
         // 23: getfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.this$0 Lokhttp3/internal/http1/Http1ExchangeCodec;
         // 26: aload 0
         // 27: getfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.timeout Lokio/ForwardingTimeout;
         // 2a: invokestatic okhttp3/internal/http1/Http1ExchangeCodec.access$detachTimeout (Lokhttp3/internal/http1/Http1ExchangeCodec;Lokio/ForwardingTimeout;)V
         // 2d: aload 0
         // 2e: getfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.this$0 Lokhttp3/internal/http1/Http1ExchangeCodec;
         // 31: bipush 3
         // 32: invokestatic okhttp3/internal/http1/Http1ExchangeCodec.access$setState$p (Lokhttp3/internal/http1/Http1ExchangeCodec;I)V
         // 35: aload 0
         // 36: monitorexit
         // 37: return
         // 38: astore 2
         // 39: aload 0
         // 3a: monitorexit
         // 3b: aload 2
         // 3c: athrow
      }

      public override fun flush() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.closed Z
         // 06: istore 1
         // 07: iload 1
         // 08: ifeq 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 0
         // 0f: getfield okhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink.this$0 Lokhttp3/internal/http1/Http1ExchangeCodec;
         // 12: invokestatic okhttp3/internal/http1/Http1ExchangeCodec.access$getSink$p (Lokhttp3/internal/http1/Http1ExchangeCodec;)Lokio/BufferedSink;
         // 15: invokeinterface okio/BufferedSink.flush ()V 1
         // 1a: aload 0
         // 1b: monitorexit
         // 1c: return
         // 1d: astore 2
         // 1e: aload 0
         // 1f: monitorexit
         // 20: aload 2
         // 21: athrow
      }

      public override fun timeout(): Timeout {
         return this.timeout;
      }

      public override fun write(source: Buffer, byteCount: Long) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         if (!this.closed) {
            if (var2 != 0L) {
               Http1ExchangeCodec.access$getSink$p(this.this$0).writeHexadecimalUnsignedLong(var2);
               Http1ExchangeCodec.access$getSink$p(this.this$0).writeUtf8("\r\n");
               Http1ExchangeCodec.access$getSink$p(this.this$0).write(var1, var2);
               Http1ExchangeCodec.access$getSink$p(this.this$0).writeUtf8("\r\n");
            }
         } else {
            throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
         }
      }
   }

   private inner class ChunkedSource internal constructor(url: HttpUrl) : Http1ExchangeCodec.AbstractSource {
      private final var bytesRemainingInChunk: Long
      private final var hasMoreChunks: Boolean
      private final val url: HttpUrl

      init {
         Intrinsics.checkParameterIsNotNull(var2, "url");
         this.this$0 = var1;
         super(var1);
         this.url = var2;
         this.bytesRemainingInChunk = -1L;
         this.hasMoreChunks = true;
      }

      private fun readChunkSize() {
         if (this.bytesRemainingInChunk != -1L) {
            Http1ExchangeCodec.access$getSource$p(this.this$0).readUtf8LineStrict();
         }

         var var2: java.lang.String;
         try {
            this.bytesRemainingInChunk = Http1ExchangeCodec.access$getSource$p(this.this$0).readHexadecimalUnsignedLong();
            var2 = Http1ExchangeCodec.access$getSource$p(this.this$0).readUtf8LineStrict();
         } catch (var7: NumberFormatException) {
            throw (new ProtocolException(var7.getMessage())) as java.lang.Throwable;
         }

         if (var2 == null) {
            try {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } catch (var6: NumberFormatException) {
               throw (new ProtocolException(var6.getMessage())) as java.lang.Throwable;
            }
         } else {
            label51: {
               var var3: java.lang.String;
               label50: {
                  var var1: Boolean;
                  try {
                     var3 = StringsKt.trim(var2).toString();
                     if (this.bytesRemainingInChunk < 0L) {
                        break label50;
                     }

                     if (var3.length() <= 0) {
                        break label51;
                     }

                     var1 = StringsKt.startsWith$default(var3, ";", false, 2, null);
                  } catch (var8: NumberFormatException) {
                     throw (new ProtocolException(var8.getMessage())) as java.lang.Throwable;
                  }

                  if (var1) {
                     break label51;
                  }
               }

               try {
                  val var15: StringBuilder = new StringBuilder("expected chunk size and optional extensions but was \"");
                  var15.append(this.bytesRemainingInChunk);
                  var15.append(var3);
                  var15.append('"');
                  throw (new ProtocolException(var15.toString())) as java.lang.Throwable;
               } catch (var5: NumberFormatException) {
                  throw (new ProtocolException(var5.getMessage())) as java.lang.Throwable;
               }
            }

            if (this.bytesRemainingInChunk == 0L) {
               this.hasMoreChunks = false;
               Http1ExchangeCodec.access$setTrailers$p(this.this$0, Http1ExchangeCodec.access$getHeadersReader$p(this.this$0).readHeaders());
               val var10: OkHttpClient = Http1ExchangeCodec.access$getClient$p(this.this$0);
               if (var10 == null) {
                  Intrinsics.throwNpe();
               }

               val var14: CookieJar = var10.cookieJar();
               val var11: HttpUrl = this.url;
               val var4: Headers = Http1ExchangeCodec.access$getTrailers$p(this.this$0);
               if (var4 == null) {
                  Intrinsics.throwNpe();
               }

               HttpHeaders.receiveHeaders(var14, var11, var4);
               this.responseBodyComplete$okhttp();
            }
         }
      }

      public override fun close() {
         if (!this.getClosed()) {
            if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
               this.this$0.getConnection().noNewExchanges$okhttp();
               this.responseBodyComplete$okhttp();
            }

            this.setClosed(true);
         }
      }

      public override fun read(sink: Buffer, byteCount: Long): Long {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         val var4: Boolean;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (!var4) {
            val var8: StringBuilder = new StringBuilder("byteCount < 0: ");
            var8.append(var2);
            throw (new IllegalArgumentException(var8.toString().toString())) as java.lang.Throwable;
         } else if (!this.getClosed()) {
            if (!this.hasMoreChunks) {
               return -1L;
            } else {
               if (this.bytesRemainingInChunk == 0L || this.bytesRemainingInChunk == -1L) {
                  this.readChunkSize();
                  if (!this.hasMoreChunks) {
                     return -1L;
                  }
               }

               var2 = super.read(var1, Math.min(var2, this.bytesRemainingInChunk));
               if (var2 != -1L) {
                  this.bytesRemainingInChunk -= var2;
                  return var2;
               } else {
                  this.this$0.getConnection().noNewExchanges$okhttp();
                  val var7: ProtocolException = new ProtocolException("unexpected end of stream");
                  this.responseBodyComplete$okhttp();
                  throw var7 as java.lang.Throwable;
               }
            }
         } else {
            throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
         }
      }
   }

   public companion object {
      private const val NO_CHUNK_YET: Long
      private const val STATE_CLOSED: Int
      private const val STATE_IDLE: Int
      private const val STATE_OPEN_REQUEST_BODY: Int
      private const val STATE_OPEN_RESPONSE_BODY: Int
      private const val STATE_READING_RESPONSE_BODY: Int
      private const val STATE_READ_RESPONSE_HEADERS: Int
      private const val STATE_WRITING_REQUEST_BODY: Int
   }

   private inner class FixedLengthSource internal constructor(bytesRemaining: Long) : Http1ExchangeCodec.AbstractSource(var1) {
      private final var bytesRemaining: Long

      init {
         this.this$0 = var1;
         this.bytesRemaining = var2;
         if (var2 == 0L) {
            this.responseBodyComplete$okhttp();
         }
      }

      public override fun close() {
         if (!this.getClosed()) {
            if (this.bytesRemaining != 0L && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
               this.this$0.getConnection().noNewExchanges$okhttp();
               this.responseBodyComplete$okhttp();
            }

            this.setClosed(true);
         }
      }

      public override fun read(sink: Buffer, byteCount: Long): Long {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         val var4: Boolean;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            if (!this.getClosed()) {
               if (this.bytesRemaining == 0L) {
                  return -1L;
               } else {
                  var2 = super.read(var1, Math.min(this.bytesRemaining, var2));
                  if (var2 != -1L) {
                     val var10: Long = this.bytesRemaining - var2;
                     this.bytesRemaining -= var2;
                     if (var10 == 0L) {
                        this.responseBodyComplete$okhttp();
                     }

                     return var2;
                  } else {
                     this.this$0.getConnection().noNewExchanges$okhttp();
                     val var8: ProtocolException = new ProtocolException("unexpected end of stream");
                     this.responseBodyComplete$okhttp();
                     throw var8 as java.lang.Throwable;
                  }
               }
            } else {
               throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
            }
         } else {
            val var7: StringBuilder = new StringBuilder("byteCount < 0: ");
            var7.append(var2);
            throw (new IllegalArgumentException(var7.toString().toString())) as java.lang.Throwable;
         }
      }
   }

   private inner class KnownLengthSink : Sink {
      private final var closed: Boolean
      private final val timeout: ForwardingTimeout

      init {
         this.this$0 = var1;
         this.timeout = new ForwardingTimeout(Http1ExchangeCodec.access$getSink$p(var1).timeout());
      }

      public override fun close() {
         if (!this.closed) {
            this.closed = true;
            Http1ExchangeCodec.access$detachTimeout(this.this$0, this.timeout);
            Http1ExchangeCodec.access$setState$p(this.this$0, 3);
         }
      }

      public override fun flush() {
         if (!this.closed) {
            Http1ExchangeCodec.access$getSink$p(this.this$0).flush();
         }
      }

      public override fun timeout(): Timeout {
         return this.timeout;
      }

      public override fun write(source: Buffer, byteCount: Long) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         if (!this.closed) {
            Util.checkOffsetAndCount(var1.size(), 0L, var2);
            Http1ExchangeCodec.access$getSink$p(this.this$0).write(var1, var2);
         } else {
            throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
         }
      }
   }

   private inner class UnknownLengthSource : Http1ExchangeCodec.AbstractSource(var1) {
      private final var inputExhausted: Boolean

      init {
         this.this$0 = var1;
      }

      public override fun close() {
         if (!this.getClosed()) {
            if (!this.inputExhausted) {
               this.responseBodyComplete$okhttp();
            }

            this.setClosed(true);
         }
      }

      public override fun read(sink: Buffer, byteCount: Long): Long {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         val var4: Boolean;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            if (!this.getClosed()) {
               if (this.inputExhausted) {
                  return -1L;
               } else {
                  var2 = super.read(var1, var2);
                  if (var2 == -1L) {
                     this.inputExhausted = true;
                     this.responseBodyComplete$okhttp();
                     return -1L;
                  } else {
                     return var2;
                  }
               }
            } else {
               throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
            }
         } else {
            val var5: StringBuilder = new StringBuilder("byteCount < 0: ");
            var5.append(var2);
            throw (new IllegalArgumentException(var5.toString().toString())) as java.lang.Throwable;
         }
      }
   }
}
