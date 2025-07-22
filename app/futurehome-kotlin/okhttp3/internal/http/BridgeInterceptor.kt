package okhttp3.internal.http

import kotlin.jvm.internal.Intrinsics
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.Interceptor.Chain
import okhttp3.internal.Util
import okio.GzipSource
import okio.Okio

public class BridgeInterceptor(cookieJar: CookieJar) : Interceptor {
   private final val cookieJar: CookieJar

   init {
      Intrinsics.checkParameterIsNotNull(var1, "cookieJar");
      super();
      this.cookieJar = var1;
   }

   private fun cookieHeader(cookies: List<Cookie>): String {
      val var3: StringBuilder = new StringBuilder();
      val var5: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         var var4: Any = var5.next();
         if (var2 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var4 = var4 as Cookie;
         if (var2 > 0) {
            var3.append("; ");
         }

         var3.append(((Cookie)var4).name());
         var3.append('=');
         var3.append(((Cookie)var4).value());
      }

      val var6: java.lang.String = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var6, "StringBuilder().apply(builderAction).toString()");
      return var6;
   }

   @Throws(java/io/IOException::class)
   public override fun intercept(chain: Chain): Response {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      val var6: Request = var1.request();
      val var7: Request.Builder = var6.newBuilder();
      val var9: RequestBody = var6.body();
      if (var9 != null) {
         val var8: MediaType = var9.contentType();
         if (var8 != null) {
            var7.header("Content-Type", var8.toString());
         }

         val var4: Long = var9.contentLength();
         if (var4 != -1L) {
            var7.header("Content-Length", java.lang.String.valueOf(var4));
            var7.removeHeader("Transfer-Encoding");
         } else {
            var7.header("Transfer-Encoding", "chunked");
            var7.removeHeader("Content-Length");
         }
      }

      if (var6.header("Host") == null) {
         var7.header("Host", Util.toHostHeader$default(var6.url(), false, 1, null));
      }

      if (var6.header("Connection") == null) {
         var7.header("Connection", "Keep-Alive");
      }

      var var2: Boolean = false;
      if (var6.header("Accept-Encoding") == null) {
         var2 = false;
         if (var6.header("Range") == null) {
            var7.header("Accept-Encoding", "gzip");
            var2 = true;
         }
      }

      val var15: java.util.List = this.cookieJar.loadForRequest(var6.url());
      if (!var15.isEmpty()) {
         var7.header("Cookie", this.cookieHeader(var15));
      }

      if (var6.header("User-Agent") == null) {
         var7.header("User-Agent", "okhttp/4.8.0");
      }

      val var10: Response = var1.proceed(var7.build());
      HttpHeaders.receiveHeaders(this.cookieJar, var6.url(), var10.headers());
      val var11: Response.Builder = var10.newBuilder().request(var6);
      if (var2 && StringsKt.equals("gzip", Response.header$default(var10, "Content-Encoding", null, 2, null), true) && HttpHeaders.promisesBody(var10)) {
         val var12: ResponseBody = var10.body();
         if (var12 != null) {
            val var13: GzipSource = new GzipSource(var12.source());
            var11.headers(var10.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build());
            var11.body(new RealResponseBody(Response.header$default(var10, "Content-Type", null, 2, null), -1L, Okio.buffer(var13)));
         }
      }

      return var11.build();
   }
}
