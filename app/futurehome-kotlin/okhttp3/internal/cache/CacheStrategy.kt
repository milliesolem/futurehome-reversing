package okhttp3.internal.cache

import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.jvm.internal.Intrinsics
import okhttp3.CacheControl
import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.Util
import okhttp3.internal.http.DatesKt

public class CacheStrategy internal constructor(networkRequest: Request?, cacheResponse: Response?) {
   public final val cacheResponse: Response?
   public final val networkRequest: Request?

   init {
      this.networkRequest = var1;
      this.cacheResponse = var2;
   }

   public companion object {
      public fun isCacheable(response: Response, request: Request): Boolean {
         Intrinsics.checkParameterIsNotNull(var1, "response");
         Intrinsics.checkParameterIsNotNull(var2, "request");
         val var3: Int = var1.code();
         label56:
         if (var3 != 200 && var3 != 410 && var3 != 414 && var3 != 501 && var3 != 203 && var3 != 204) {
            if (var3 != 307) {
               if (var3 == 308 || var3 == 404 || var3 == 405) {
                  break label56;
               }

               switch (var3) {
                  case 300:
                  case 301:
                     break label56;
                  case 302:
                     break;
                  default:
                     return false;
               }
            }

            if (Response.header$default(var1, "Expires", null, 2, null) == null
               && var1.cacheControl().maxAgeSeconds() == -1
               && !var1.cacheControl().isPublic()
               && !var1.cacheControl().isPrivate()) {
               return false;
            }
         }

         var var4: Boolean = false;
         if (!var1.cacheControl().noStore()) {
            var4 = false;
            if (!var2.cacheControl().noStore()) {
               var4 = true;
            }
         }

         return var4;
      }
   }

   public class Factory(nowMillis: Long, request: Request, cacheResponse: Response?) {
      private final var ageSeconds: Int
      private final val cacheResponse: Response?
      private final var etag: String?
      private final var expires: Date?
      private final var lastModified: Date?
      private final var lastModifiedString: String?
      private final val nowMillis: Long
      private final var receivedResponseMillis: Long
      internal final val request: Request
      private final var sentRequestMillis: Long
      private final var servedDate: Date?
      private final var servedDateString: String?

      init {
         Intrinsics.checkParameterIsNotNull(var3, "request");
         super();
         this.nowMillis = var1;
         this.request = var3;
         this.cacheResponse = var4;
         this.ageSeconds = -1;
         if (var4 != null) {
            this.sentRequestMillis = var4.sentRequestAtMillis();
            this.receivedResponseMillis = var4.receivedResponseAtMillis();
            val var8: Headers = var4.headers();
            val var6: Int = var8.size();

            for (int var5 = 0; var5 < var6; var5++) {
               val var9: java.lang.String = var8.name(var5);
               val var7: java.lang.String = var8.value(var5);
               if (StringsKt.equals(var9, "Date", true)) {
                  this.servedDate = DatesKt.toHttpDateOrNull(var7);
                  this.servedDateString = var7;
               } else if (StringsKt.equals(var9, "Expires", true)) {
                  this.expires = DatesKt.toHttpDateOrNull(var7);
               } else if (StringsKt.equals(var9, "Last-Modified", true)) {
                  this.lastModified = DatesKt.toHttpDateOrNull(var7);
                  this.lastModifiedString = var7;
               } else if (StringsKt.equals(var9, "ETag", true)) {
                  this.etag = var7;
               } else if (StringsKt.equals(var9, "Age", true)) {
                  this.ageSeconds = Util.toNonNegativeInt(var7, -1);
               }
            }
         }
      }

      private fun cacheResponseAge(): Long {
         var var1: Long = 0L;
         if (this.servedDate != null) {
            var1 = Math.max(0L, this.receivedResponseMillis - this.servedDate.getTime());
         }

         var var3: Long = var1;
         if (this.ageSeconds != -1) {
            var3 = Math.max(var1, TimeUnit.SECONDS.toMillis((long)this.ageSeconds));
         }

         return var3 + (this.receivedResponseMillis - this.sentRequestMillis) + (this.nowMillis - this.receivedResponseMillis);
      }

      private fun computeCandidate(): CacheStrategy {
         if (this.cacheResponse == null) {
            return new CacheStrategy(this.request, null);
         } else if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
            return new CacheStrategy(this.request, null);
         } else if (!CacheStrategy.Companion.isCacheable(this.cacheResponse, this.request)) {
            return new CacheStrategy(this.request, null);
         } else {
            val var13: CacheControl = this.request.cacheControl();
            if (!var13.noCache() && !this.hasConditions(this.request)) {
               val var12: CacheControl = this.cacheResponse.cacheControl();
               val var10: Long = this.cacheResponseAge();
               var var4: Long = this.computeFreshnessLifetime();
               var var2: Long = var4;
               if (var13.maxAgeSeconds() != -1) {
                  var2 = Math.min(var4, TimeUnit.SECONDS.toMillis((long)var13.maxAgeSeconds()));
               }

               var var6: Long;
               if (var13.minFreshSeconds() != -1) {
                  var6 = TimeUnit.SECONDS.toMillis((long)var13.minFreshSeconds());
               } else {
                  var6 = 0L;
               }

               var4 = 0L;
               if (!var12.mustRevalidate()) {
                  var4 = 0L;
                  if (var13.maxStaleSeconds() != -1) {
                     var4 = TimeUnit.SECONDS.toMillis((long)var13.maxStaleSeconds());
                  }
               }

               if (!var12.noCache()) {
                  var6 = var6 + var10;
                  if (var6 + var10 < var4 + var2) {
                     val var18: Response.Builder = this.cacheResponse.newBuilder();
                     if (var6 >= var2) {
                        var18.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                     }

                     if (var10 > 86400000L && this.isFreshnessLifetimeHeuristic()) {
                        var18.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                     }

                     return new CacheStrategy(null, var18.build());
                  }
               }

               var var17: java.lang.String = this.etag;
               val var19: java.lang.String;
               if (this.etag != null) {
                  var19 = "If-None-Match";
               } else {
                  if (this.lastModified != null) {
                     var17 = this.lastModifiedString;
                  } else {
                     if (this.servedDate == null) {
                        return new CacheStrategy(this.request, null);
                     }

                     var17 = this.servedDateString;
                  }

                  var19 = "If-Modified-Since";
               }

               val var14: Headers.Builder = this.request.headers().newBuilder();
               if (var17 == null) {
                  Intrinsics.throwNpe();
               }

               var14.addLenient$okhttp(var19, var17);
               return new CacheStrategy(this.request.newBuilder().headers(var14.build()).build(), this.cacheResponse);
            } else {
               return new CacheStrategy(this.request, null);
            }
         }
      }

      private fun computeFreshnessLifetime(): Long {
         if (this.cacheResponse == null) {
            Intrinsics.throwNpe();
         }

         val var12: CacheControl = this.cacheResponse.cacheControl();
         if (var12.maxAgeSeconds() != -1) {
            return TimeUnit.SECONDS.toMillis((long)var12.maxAgeSeconds());
         } else {
            var var1: Long = 0L;
            if (this.expires != null) {
               var var10: Long;
               if (this.servedDate != null) {
                  var10 = this.servedDate.getTime();
               } else {
                  var10 = this.receivedResponseMillis;
               }

               var10 = this.expires.getTime() - var10;
               if (var10 > 0L) {
                  var1 = var10;
               }

               return var1;
            } else {
               var var3: Long = 0L;
               if (this.lastModified != null) {
                  var3 = 0L;
                  if (this.cacheResponse.request().url().query() == null) {
                     if (this.servedDate != null) {
                        var3 = this.servedDate.getTime();
                     } else {
                        var3 = this.sentRequestMillis;
                     }

                     if (this.lastModified == null) {
                        Intrinsics.throwNpe();
                     }

                     val var5: Long = var3 - this.lastModified.getTime();
                     var3 = 0L;
                     if (var5 > 0L) {
                        var3 = var5 / 10;
                     }
                  }
               }

               return var3;
            }
         }
      }

      private fun hasConditions(request: Request): Boolean {
         val var2: Boolean;
         if (var1.header("If-Modified-Since") == null && var1.header("If-None-Match") == null) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      private fun isFreshnessLifetimeHeuristic(): Boolean {
         if (this.cacheResponse == null) {
            Intrinsics.throwNpe();
         }

         val var1: Boolean;
         if (this.cacheResponse.cacheControl().maxAgeSeconds() == -1 && this.expires == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public fun compute(): CacheStrategy {
         val var2: CacheStrategy = this.computeCandidate();
         var var1: CacheStrategy = var2;
         if (var2.getNetworkRequest() != null) {
            var1 = var2;
            if (this.request.cacheControl().onlyIfCached()) {
               var1 = new CacheStrategy(null, null);
            }
         }

         return var1;
      }
   }
}
