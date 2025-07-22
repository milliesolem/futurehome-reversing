package okhttp3.internal.connection

import java.io.IOException
import java.net.ProtocolException
import kotlin.jvm.internal.Intrinsics
import okhttp3.EventListener
import okhttp3.Headers
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.Response.Builder
import okhttp3.internal.http.ExchangeCodec
import okhttp3.internal.http.RealResponseBody
import okhttp3.internal.ws.RealWebSocket.Streams
import okio.Buffer
import okio.ForwardingSink
import okio.ForwardingSource
import okio.Okio
import okio.Sink
import okio.Source

public class Exchange(call: RealCall, eventListener: EventListener, finder: ExchangeFinder, codec: ExchangeCodec) {
   internal final val call: RealCall
   private final val codec: ExchangeCodec
   internal final val connection: RealConnection
   internal final val eventListener: EventListener
   internal final val finder: ExchangeFinder

   internal final val isCoalescedConnection: Boolean
      internal final get() {
         return this.finder.getAddress$okhttp().url().host() == this.connection.route().address().url().host() xor true;
      }


   internal final var isDuplex: Boolean
      private set

   init {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "eventListener");
      Intrinsics.checkParameterIsNotNull(var3, "finder");
      Intrinsics.checkParameterIsNotNull(var4, "codec");
      super();
      this.call = var1;
      this.eventListener = var2;
      this.finder = var3;
      this.codec = var4;
      this.connection = var4.getConnection();
   }

   private fun trackFailure(e: IOException) {
      this.finder.trackFailure(var1);
      this.codec.getConnection().trackFailure$okhttp(this.call, var1);
   }

   public fun <E : IOException?> bodyComplete(bytesRead: Long, responseDone: Boolean, requestDone: Boolean, e: E): E {
      if (var5 != null) {
         this.trackFailure(var5);
      }

      if (var4) {
         if (var5 != null) {
            this.eventListener.requestFailed(this.call, var5);
         } else {
            this.eventListener.requestBodyEnd(this.call, var1);
         }
      }

      if (var3) {
         if (var5 != null) {
            this.eventListener.responseFailed(this.call, var5);
         } else {
            this.eventListener.responseBodyEnd(this.call, var1);
         }
      }

      return (E)this.call.messageDone$okhttp(this, var4, var3, var5);
   }

   public fun cancel() {
      this.codec.cancel();
   }

   @Throws(java/io/IOException::class)
   public fun createRequestBody(request: Request, duplex: Boolean): Sink {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      this.isDuplex = var2;
      val var5: RequestBody = var1.body();
      if (var5 == null) {
         Intrinsics.throwNpe();
      }

      val var3: Long = var5.contentLength();
      this.eventListener.requestBodyStart(this.call);
      return new Exchange.RequestBodySink(this, (long)this.codec.createRequestBody(var1, var3), var3);
   }

   public fun detachWithViolence() {
      this.codec.cancel();
      this.call.messageDone$okhttp(this, true, true, null);
   }

   @Throws(java/io/IOException::class)
   public fun finishRequest() {
      try {
         this.codec.finishRequest();
      } catch (var2: IOException) {
         this.eventListener.requestFailed(this.call, var2);
         this.trackFailure(var2);
         throw var2 as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun flushRequest() {
      try {
         this.codec.flushRequest();
      } catch (var2: IOException) {
         this.eventListener.requestFailed(this.call, var2);
         this.trackFailure(var2);
         throw var2 as java.lang.Throwable;
      }
   }

   @Throws(java/net/SocketException::class)
   public fun newWebSocketStreams(): Streams {
      this.call.timeoutEarlyExit();
      return this.codec.getConnection().newWebSocketStreams$okhttp(this);
   }

   public fun noNewExchangesOnConnection() {
      this.codec.getConnection().noNewExchanges$okhttp();
   }

   public fun noRequestBody() {
      this.call.messageDone$okhttp(this, true, false, null);
   }

   @Throws(java/io/IOException::class)
   public fun openResponseBody(response: Response): ResponseBody {
      Intrinsics.checkParameterIsNotNull(var1, "response");

      try {
         val var4: java.lang.String = Response.header$default(var1, "Content-Type", null, 2, null);
         val var2: Long = this.codec.reportedContentLength(var1);
         return new RealResponseBody(var4, var2, Okio.buffer(new Exchange.ResponseBodySource(this, (long)this.codec.openResponseBodySource(var1), var2)));
      } catch (var6: IOException) {
         this.eventListener.responseFailed(this.call, var6);
         this.trackFailure(var6);
         throw var6 as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   public fun readResponseHeaders(expectContinue: Boolean): Builder? {
      var var2: Response.Builder;
      try {
         var2 = this.codec.readResponseHeaders(var1);
      } catch (var4: IOException) {
         this.eventListener.responseFailed(this.call, var4);
         this.trackFailure(var4);
         throw var4 as java.lang.Throwable;
      }

      if (var2 != null) {
         try {
            var2.initExchange$okhttp(this);
         } catch (var3: IOException) {
            this.eventListener.responseFailed(this.call, var3);
            this.trackFailure(var3);
            throw var3 as java.lang.Throwable;
         }
      }

      return var2;
   }

   public fun responseHeadersEnd(response: Response) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      this.eventListener.responseHeadersEnd(this.call, var1);
   }

   public fun responseHeadersStart() {
      this.eventListener.responseHeadersStart(this.call);
   }

   @Throws(java/io/IOException::class)
   public fun trailers(): Headers {
      return this.codec.trailers();
   }

   public fun webSocketUpgradeFailed() {
      this.bodyComplete(-1L, true, true, null);
   }

   @Throws(java/io/IOException::class)
   public fun writeRequestHeaders(request: Request) {
      Intrinsics.checkParameterIsNotNull(var1, "request");

      try {
         this.eventListener.requestHeadersStart(this.call);
         this.codec.writeRequestHeaders(var1);
         this.eventListener.requestHeadersEnd(this.call, var1);
      } catch (var2: IOException) {
         this.eventListener.requestFailed(this.call, var2);
         this.trackFailure(var2);
         throw var2 as java.lang.Throwable;
      }
   }

   private inner class RequestBodySink internal constructor(delegate: Sink, contentLength: Long) : ForwardingSink {
      private final var bytesReceived: Long
      private final var closed: Boolean
      private final var completed: Boolean
      private final val contentLength: Long

      init {
         Intrinsics.checkParameterIsNotNull(var2, "delegate");
         this.this$0 = var1;
         super(var2);
         this.contentLength = var3;
      }

      private fun <E : IOException?> complete(e: E): E {
         if (this.completed) {
            return (E)var1;
         } else {
            this.completed = true;
            return (E)this.this$0.bodyComplete(this.bytesReceived, false, true, var1);
         }
      }

      @Throws(java/io/IOException::class)
      public override fun close() {
         if (!this.closed) {
            this.closed = true;
            if (this.contentLength != -1L && this.bytesReceived != this.contentLength) {
               throw (new ProtocolException("unexpected end of stream")) as java.lang.Throwable;
            } else {
               try {
                  super.close();
                  this.complete(null);
               } catch (var4: IOException) {
                  throw this.complete(var4) as java.lang.Throwable;
               }
            }
         }
      }

      @Throws(java/io/IOException::class)
      public override fun flush() {
         try {
            super.flush();
         } catch (var2: IOException) {
            throw this.complete(var2) as java.lang.Throwable;
         }
      }

      @Throws(java/io/IOException::class)
      public override fun write(source: Buffer, byteCount: Long) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         if (!this.closed) {
            if (this.contentLength != -1L && this.bytesReceived + var2 > this.contentLength) {
               val var7: StringBuilder = new StringBuilder("expected ");
               var7.append(this.contentLength);
               var7.append(" bytes but received ");
               var7.append(this.bytesReceived + var2);
               throw (new ProtocolException(var7.toString())) as java.lang.Throwable;
            } else {
               try {
                  super.write(var1, var2);
                  this.bytesReceived += var2;
               } catch (var6: IOException) {
                  throw this.complete(var6) as java.lang.Throwable;
               }
            }
         } else {
            throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
         }
      }
   }

   internal inner class ResponseBodySource(delegate: Source, contentLength: Long) : ForwardingSource {
      private final var bytesReceived: Long
      private final var closed: Boolean
      private final var completed: Boolean
      private final val contentLength: Long
      private final var invokeStartEvent: Boolean

      init {
         Intrinsics.checkParameterIsNotNull(var2, "delegate");
         this.this$0 = var1;
         super(var2);
         this.contentLength = var3;
         this.invokeStartEvent = true;
         if (var3 == 0L) {
            this.complete(null);
         }
      }

      @Throws(java/io/IOException::class)
      public override fun close() {
         if (!this.closed) {
            this.closed = true;

            try {
               super.close();
               this.complete(null);
            } catch (var2: IOException) {
               throw this.complete(var2) as java.lang.Throwable;
            }
         }
      }

      public fun <E : IOException?> complete(e: E): E {
         if (this.completed) {
            return (E)var1;
         } else {
            this.completed = true;
            if (var1 == null && this.invokeStartEvent) {
               this.invokeStartEvent = false;
               this.this$0.getEventListener$okhttp().responseBodyStart(this.this$0.getCall$okhttp());
            }

            return (E)this.this$0.bodyComplete(this.bytesReceived, true, false, var1);
         }
      }

      @Throws(java/io/IOException::class)
      public override fun read(sink: Buffer, byteCount: Long): Long {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         if (this.closed) {
            throw (new IllegalStateException("closed".toString())) as java.lang.Throwable;
         } else {
            var var4: Long;
            try {
               var4 = this.delegate().read(var1, var2);
               if (this.invokeStartEvent) {
                  this.invokeStartEvent = false;
                  this.this$0.getEventListener$okhttp().responseBodyStart(this.this$0.getCall$okhttp());
               }
            } catch (var14: IOException) {
               throw this.complete(var14) as java.lang.Throwable;
            }

            if (var4 == -1L) {
               try {
                  this.complete(null);
                  return -1L;
               } catch (var9: IOException) {
                  throw this.complete(var9) as java.lang.Throwable;
               }
            } else {
               var var6: Long;
               try {
                  var2 = this.bytesReceived + var4;
                  var6 = this.contentLength;
               } catch (var13: IOException) {
                  throw this.complete(var13) as java.lang.Throwable;
               }

               if (var6 != -1L && var2 > var6) {
                  try {
                     val var8: StringBuilder = new StringBuilder("expected ");
                     var8.append(this.contentLength);
                     var8.append(" bytes but received ");
                     var8.append(var2);
                     throw (new ProtocolException(var8.toString())) as java.lang.Throwable;
                  } catch (var10: IOException) {
                     throw this.complete(var10) as java.lang.Throwable;
                  }
               } else {
                  try {
                     this.bytesReceived = var2;
                  } catch (var12: IOException) {
                     throw this.complete(var12) as java.lang.Throwable;
                  }

                  if (var2 == var6) {
                     try {
                        this.complete(null);
                     } catch (var11: IOException) {
                        throw this.complete(var11) as java.lang.Throwable;
                     }
                  }

                  return var4;
               }
            }
         }
      }
   }
}
