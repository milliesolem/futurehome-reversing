package okhttp3.internal.http

import java.net.Proxy.Type
import kotlin.jvm.internal.Intrinsics
import okhttp3.HttpUrl
import okhttp3.Request

public object RequestLine {
   private fun includeAuthorityInRequestLine(request: Request, proxyType: Type): Boolean {
      val var3: Boolean;
      if (!var1.isHttps() && var2 === Type.HTTP) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public fun get(request: Request, proxyType: Type): String {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      Intrinsics.checkParameterIsNotNull(var2, "proxyType");
      val var3: StringBuilder = new StringBuilder();
      var3.append(var1.method());
      var3.append(' ');
      val var4: RequestLine = INSTANCE;
      if (INSTANCE.includeAuthorityInRequestLine(var1, var2)) {
         var3.append(var1.url());
      } else {
         var3.append(var4.requestPath(var1.url()));
      }

      var3.append(" HTTP/1.1");
      val var5: java.lang.String = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var5, "StringBuilder().apply(builderAction).toString()");
      return var5;
   }

   public fun requestPath(url: HttpUrl): String {
      Intrinsics.checkParameterIsNotNull(var1, "url");
      val var2: java.lang.String = var1.encodedPath();
      val var3: java.lang.String = var1.encodedQuery();
      var var4: java.lang.String = var2;
      if (var3 != null) {
         val var5: StringBuilder = new StringBuilder();
         var5.append(var2);
         var5.append('?');
         var5.append(var3);
         var4 = var5.toString();
      }

      return var4;
   }
}
