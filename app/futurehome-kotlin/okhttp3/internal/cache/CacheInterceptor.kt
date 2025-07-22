package okhttp3.internal.cache

import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.Cache
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.Interceptor.Chain
import okhttp3.internal.Util
import okhttp3.internal.connection.RealCall
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.http.HttpMethod
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import okio.BufferedSink
import okio.BufferedSource
import okio.Okio
import okio.Sink
import okio.Source
import okio.Timeout

public class CacheInterceptor(cache: Cache?) : Interceptor {
   internal final val cache: Cache?

   init {
      this.cache = var1;
   }

   @Throws(java/io/IOException::class)
   private fun cacheWritingResponse(cacheRequest: CacheRequest?, response: Response): Response {
      if (var1 == null) {
         return var2;
      } else {
         val var6: Sink = var1.body();
         val var5: ResponseBody = var2.body();
         if (var5 == null) {
            Intrinsics.throwNpe();
         }

         return var2.newBuilder()
            .body(
               new RealResponseBody(
                  Response.header$default(var2, "Content-Type", null, 2, null),
                  var2.body().contentLength(),
                  Okio.buffer(new Source(var5.source(), var1, Okio.buffer(var6)) {
                     final BufferedSink $cacheBody;
                     final CacheRequest $cacheRequest;
                     final BufferedSource $source;
                     private boolean cacheRequestClosed;

                     {
                        this.$source = var1;
                        this.$cacheRequest = var2;
                        this.$cacheBody = var3;
                     }

                     @Override
                     public void close() throws IOException {
                        if (!this.cacheRequestClosed && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                           this.cacheRequestClosed = true;
                           this.$cacheRequest.abort();
                        }

                        this.$source.close();
                     }

                     @Override
                     public long read(Buffer var1, long var2) throws IOException {
                        Intrinsics.checkParameterIsNotNull(var1, "sink");

                        try {
                           var2 = this.$source.read(var1, var2);
                        } catch (var4: IOException) {
                           if (!this.cacheRequestClosed) {
                              this.cacheRequestClosed = true;
                              this.$cacheRequest.abort();
                           }

                           throw var4 as java.lang.Throwable;
                        }

                        if (var2 == -1L) {
                           if (!this.cacheRequestClosed) {
                              this.cacheRequestClosed = true;
                              this.$cacheBody.close();
                           }

                           return -1L;
                        } else {
                           var1.copyTo(this.$cacheBody.getBuffer(), var1.size() - var2, var2);
                           this.$cacheBody.emitCompleteSegments();
                           return var2;
                        }
                     }

                     @Override
                     public Timeout timeout() {
                        return this.$source.timeout();
                     }
                  })
               )
            )
            .build();
      }
   }

   @Throws(java/io/IOException::class)
   public override fun intercept(chain: Chain): Response {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      val var4: Call = var1.call();
      var var3: Response;
      if (this.cache != null) {
         var3 = this.cache.get$okhttp(var1.request());
      } else {
         var3 = null;
      }

      val var19: CacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), var1.request(), var3).compute();
      val var6: Request = var19.getNetworkRequest();
      val var5: Response = var19.getCacheResponse();
      if (this.cache != null) {
         this.cache.trackResponse$okhttp(var19);
      }

      val var20: Call;
      if (var4 !is RealCall) {
         var20 = null;
      } else {
         var20 = var4;
      }

      label118: {
         val var21: RealCall = var20 as RealCall;
         if (var20 as RealCall != null) {
            var22 = var21.getEventListener$okhttp();
            if (var22 != null) {
               break label118;
            }
         }

         var22 = EventListener.NONE;
      }

      if (var3 != null && var5 == null) {
         val var30: ResponseBody = var3.body();
         if (var30 != null) {
            Util.closeQuietly(var30);
         }
      }

      if (var6 == null && var5 == null) {
         val var18: Response = new Response.Builder()
            .request(var1.request())
            .protocol(Protocol.HTTP_1_1)
            .code(504)
            .message("Unsatisfiable Request (only-if-cached)")
            .body(Util.EMPTY_RESPONSE)
            .sentRequestAtMillis(-1L)
            .receivedResponseAtMillis(System.currentTimeMillis())
            .build();
         var22.satisfactionFailure(var4, var18);
         return var18;
      } else if (var6 == null) {
         if (var5 == null) {
            Intrinsics.throwNpe();
         }

         val var17: Response = var5.newBuilder().cacheResponse(CacheInterceptor.Companion.access$stripBody(Companion, var5)).build();
         var22.cacheHit(var4, var17);
         return var17;
      } else {
         label189: {
            if (var5 != null) {
               var22.cacheConditionalHit(var4, var5);
            } else if (this.cache != null) {
               var22.cacheMiss(var4);
            }

            val var31: Response = null as Response;

            try {
               ;
            } catch (var9: java.lang.Throwable) {
               if (var3 != null) {
                  val var23: ResponseBody = var3.body();
                  if (var23 != null) {
                     Util.closeQuietly(var23);
                  }
               }
            }

            if (var1 == null && var3 != null) {
               val var24: ResponseBody = var3.body();
               if (var24 != null) {
                  Util.closeQuietly(var24);
               }
            }

            if (var5 != null) {
               if (var1 != null && var1.code() == 304) {
                  val var29: Response.Builder = var5.newBuilder();
                  val var27: CacheInterceptor.Companion = Companion;
                  var3 = var29.headers(CacheInterceptor.Companion.access$combine(Companion, var5.headers(), var1.headers()))
                     .sentRequestAtMillis(var1.sentRequestAtMillis())
                     .receivedResponseAtMillis(var1.receivedResponseAtMillis())
                     .cacheResponse(CacheInterceptor.Companion.access$stripBody(var27, var5))
                     .networkResponse(CacheInterceptor.Companion.access$stripBody(var27, var1))
                     .build();
                  val var15: ResponseBody = var1.body();
                  if (var15 == null) {
                     Intrinsics.throwNpe();
                  }

                  var15.close();
                  if (this.cache == null) {
                     Intrinsics.throwNpe();
                  }

                  this.cache.trackConditionalCacheHit$okhttp();
                  this.cache.update$okhttp(var5, var3);
                  var22.cacheHit(var4, var3);
                  return var3;
               }

               val var25: ResponseBody = var5.body();
               if (var25 != null) {
                  Util.closeQuietly(var25);
               }
            }

            if (var1 == null) {
               Intrinsics.throwNpe();
            }

            val var32: Response.Builder = var1.newBuilder();
            val var26: CacheInterceptor.Companion = Companion;
            val var13: Response = var32.cacheResponse(CacheInterceptor.Companion.access$stripBody(Companion, var5))
               .networkResponse(CacheInterceptor.Companion.access$stripBody(var26, var1))
               .build();
            if (this.cache != null) {
               if (HttpHeaders.promisesBody(var13) && CacheStrategy.Companion.isCacheable(var13, var6)) {
                  val var14: Response = this.cacheWritingResponse(this.cache.put$okhttp(var13), var13);
                  if (var5 != null) {
                     var22.cacheMiss(var4);
                  }

                  return var14;
               }

               if (HttpMethod.INSTANCE.invalidatesCache(var6.method())) {
                  try {
                     this.cache.remove$okhttp(var6);
                  } catch (var8: IOException) {
                  }
               }
            }

            return var13;
         }
      }
   }

   public companion object {
      private fun combine(cachedHeaders: Headers, networkHeaders: Headers): Headers {
         val var6: Headers.Builder = new Headers.Builder();
         var var5: Int = var1.size();

         for (int var3 = 0; var3 < var5; var3++) {
            val var7: java.lang.String = var1.name(var3);
            val var8: java.lang.String = var1.value(var3);
            if (!StringsKt.equals("Warning", var7, true) || !StringsKt.startsWith$default(var8, "1", false, 2, null)) {
               val var9: CacheInterceptor.Companion = this;
               if (this.isContentSpecificHeader(var7) || !this.isEndToEnd(var7) || var2.get(var7) == null) {
                  var6.addLenient$okhttp(var7, var8);
               }
            }
         }

         var5 = var2.size();

         for (int var11 = 0; var11 < var5; var11++) {
            val var13: java.lang.String = var2.name(var11);
            val var10: CacheInterceptor.Companion = this;
            if (!this.isContentSpecificHeader(var13) && this.isEndToEnd(var13)) {
               var6.addLenient$okhttp(var13, var2.value(var11));
            }
         }

         return var6.build();
      }

      private fun isContentSpecificHeader(fieldName: String): Boolean {
         var var2: Boolean = true;
         if (!StringsKt.equals("Content-Length", var1, true)) {
            var2 = true;
            if (!StringsKt.equals("Content-Encoding", var1, true)) {
               if (StringsKt.equals("Content-Type", var1, true)) {
                  var2 = true;
               } else {
                  var2 = false;
               }
            }
         }

         return var2;
      }

      private fun isEndToEnd(fieldName: String): Boolean {
         var var2: Boolean = true;
         if (StringsKt.equals("Connection", var1, true)
            || StringsKt.equals("Keep-Alive", var1, true)
            || StringsKt.equals("Proxy-Authenticate", var1, true)
            || StringsKt.equals("Proxy-Authorization", var1, true)
            || StringsKt.equals("TE", var1, true)
            || StringsKt.equals("Trailers", var1, true)
            || StringsKt.equals("Transfer-Encoding", var1, true)
            || StringsKt.equals("Upgrade", var1, true)) {
            var2 = false;
         }

         return var2;
      }

      private fun stripBody(response: Response?): Response? {
         val var3: ResponseBody;
         if (var1 != null) {
            var3 = var1.body();
         } else {
            var3 = null;
         }

         var var2: Response = var1;
         if (var3 != null) {
            var2 = var1.newBuilder().body(null).build();
         }

         return var2;
      }
   }
}
