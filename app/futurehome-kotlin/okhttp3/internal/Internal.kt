package okhttp3.internal

import javax.net.ssl.SSLSocket
import kotlin.jvm.internal.Intrinsics
import okhttp3.Cache
import okhttp3.ConnectionSpec
import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import okhttp3.Headers.Builder

public fun addHeaderLenient(builder: Builder, line: String): Builder {
   Intrinsics.checkParameterIsNotNull(var0, "builder");
   Intrinsics.checkParameterIsNotNull(var1, "line");
   return var0.addLenient$okhttp(var1);
}

public fun addHeaderLenient(builder: Builder, name: String, value: String): Builder {
   Intrinsics.checkParameterIsNotNull(var0, "builder");
   Intrinsics.checkParameterIsNotNull(var1, "name");
   Intrinsics.checkParameterIsNotNull(var2, "value");
   return var0.addLenient$okhttp(var1, var2);
}

public fun applyConnectionSpec(connectionSpec: ConnectionSpec, sslSocket: SSLSocket, isFallback: Boolean) {
   Intrinsics.checkParameterIsNotNull(var0, "connectionSpec");
   Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
   var0.apply$okhttp(var1, var2);
}

public fun cacheGet(cache: Cache, request: Request): Response? {
   Intrinsics.checkParameterIsNotNull(var0, "cache");
   Intrinsics.checkParameterIsNotNull(var1, "request");
   return var0.get$okhttp(var1);
}

public fun cookieToString(cookie: Cookie, forObsoleteRfc2965: Boolean): String {
   Intrinsics.checkParameterIsNotNull(var0, "cookie");
   return var0.toString$okhttp(var1);
}

public fun parseCookie(currentTimeMillis: Long, url: HttpUrl, setCookie: String): Cookie? {
   Intrinsics.checkParameterIsNotNull(var2, "url");
   Intrinsics.checkParameterIsNotNull(var3, "setCookie");
   return Cookie.Companion.parse$okhttp(var0, var2, var3);
}
