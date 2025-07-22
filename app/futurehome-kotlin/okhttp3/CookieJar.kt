package okhttp3

import kotlin.jvm.internal.Intrinsics

public interface CookieJar {
   public abstract fun loadForRequest(url: HttpUrl): List<Cookie> {
   }

   public abstract fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
   }

   public companion object {
      public final val NO_COOKIES: CookieJar

      private class NoCookies : CookieJar {
         public override fun loadForRequest(url: HttpUrl): List<Cookie> {
            Intrinsics.checkParameterIsNotNull(var1, "url");
            return CollectionsKt.emptyList();
         }

         public override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            Intrinsics.checkParameterIsNotNull(var1, "url");
            Intrinsics.checkParameterIsNotNull(var2, "cookies");
         }
      }
   }
}
