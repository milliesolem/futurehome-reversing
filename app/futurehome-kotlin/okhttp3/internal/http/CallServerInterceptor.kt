package okhttp3.internal.http

import java.net.ProtocolException
import kotlin.jvm.internal.Intrinsics
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.Interceptor.Chain
import okhttp3.internal.Util
import okhttp3.internal.connection.Exchange
import okio.BufferedSink
import okio.Okio

public class CallServerInterceptor(forWebSocket: Boolean) : Interceptor {
   private final val forWebSocket: Boolean

   init {
      this.forWebSocket = var1;
   }

   @Throws(java/io/IOException::class)
   public override fun intercept(chain: Chain): Response {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      val var12: RealInterceptorChain = var1 as RealInterceptorChain;
      val var9: Exchange = (var1 as RealInterceptorChain).getExchange$okhttp();
      if (var9 == null) {
         Intrinsics.throwNpe();
      }

      val var10: Request = var12.getRequest$okhttp();
      val var11: RequestBody = var10.body();
      var var5: Long = System.currentTimeMillis();
      var9.writeRequestHeaders(var10);
      val var13: Response.Builder = null as Response.Builder;
      var var2: Boolean;
      var var14: Response.Builder;
      if (HttpMethod.permitsRequestBody(var10.method()) && var11 != null) {
         val var3: Boolean;
         val var7: Response.Builder;
         if (StringsKt.equals("100-continue", var10.header("Expect"), true)) {
            var9.flushRequest();
            var7 = var9.readResponseHeaders(true);
            var9.responseHeadersStart();
            var3 = false;
         } else {
            var7 = null;
            var3 = true;
         }

         if (var7 == null) {
            if (var11.isDuplex()) {
               var9.flushRequest();
               var11.writeTo(Okio.buffer(var9.createRequestBody(var10, true)));
               var14 = var7;
               var2 = var3;
            } else {
               val var15: BufferedSink = Okio.buffer(var9.createRequestBody(var10, false));
               var11.writeTo(var15);
               var15.close();
               var14 = var7;
               var2 = var3;
            }
         } else {
            var9.noRequestBody();
            var14 = var7;
            var2 = var3;
            if (!var9.getConnection$okhttp().isMultiplexed$okhttp()) {
               var9.noNewExchangesOnConnection();
               var14 = var7;
               var2 = var3;
            }
         }
      } else {
         var9.noRequestBody();
         var14 = null;
         var2 = 1;
      }

      if (var11 == null || !var11.isDuplex()) {
         var9.finishRequest();
      }

      var var24: Response.Builder = var14;
      var var22: Boolean = (boolean)var2;
      if (var14 == null) {
         val var16: Response.Builder = var9.readResponseHeaders(false);
         if (var16 == null) {
            Intrinsics.throwNpe();
         }

         var24 = var16;
         var22 = (boolean)var2;
         if (var2) {
            var9.responseHeadersStart();
            var22 = false;
            var24 = var16;
         }
      }

      var var17: Response = var24.request(var10)
         .handshake(var9.getConnection$okhttp().handshake())
         .sentRequestAtMillis(var5)
         .receivedResponseAtMillis(System.currentTimeMillis())
         .build();
      val var4: Int = var17.code();
      var2 = var4;
      if (var4 == 100) {
         val var18: Response.Builder = var9.readResponseHeaders(false);
         if (var18 == null) {
            Intrinsics.throwNpe();
         }

         if (var22) {
            var9.responseHeadersStart();
         }

         var17 = var18.request(var10)
            .handshake(var9.getConnection$okhttp().handshake())
            .sentRequestAtMillis(var5)
            .receivedResponseAtMillis(System.currentTimeMillis())
            .build();
         var2 = var17.code();
      }

      var9.responseHeadersEnd(var17);
      val var19: Response;
      if (this.forWebSocket && var2 == 101) {
         var19 = var17.newBuilder().body(Util.EMPTY_RESPONSE).build();
      } else {
         var19 = var17.newBuilder().body(var9.openResponseBody(var17)).build();
      }

      if (StringsKt.equals("close", var19.request().header("Connection"), true)
         || StringsKt.equals("close", Response.header$default(var19, "Connection", null, 2, null), true)) {
         var9.noNewExchangesOnConnection();
      }

      if (var2 == 204 || var2 == 205) {
         val var25: ResponseBody = var19.body();
         if (var25 != null) {
            var5 = var25.contentLength();
         } else {
            var5 = -1L;
         }

         if (var5 > 0L) {
            val var26: StringBuilder = new StringBuilder("HTTP ");
            var26.append(var2);
            var26.append(" had non-zero Content-Length: ");
            val var27: ResponseBody = var19.body();
            var var20: java.lang.Long = null;
            if (var27 != null) {
               var20 = var27.contentLength();
            }

            var26.append(var20);
            throw (new ProtocolException(var26.toString())) as java.lang.Throwable;
         }
      }

      return var19;
   }
}
