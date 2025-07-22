package okhttp3.internal.connection

import kotlin.jvm.internal.Intrinsics
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.Interceptor.Chain
import okhttp3.internal.http.RealInterceptorChain

public object ConnectInterceptor : Interceptor {
   @Throws(java/io/IOException::class)
   public override fun intercept(chain: Chain): Response {
      Intrinsics.checkParameterIsNotNull(var1, "chain");
      return RealInterceptorChain.copy$okhttp$default(
            var1 as RealInterceptorChain,
            0,
            (var1 as RealInterceptorChain).getCall$okhttp().initExchange$okhttp(var1 as RealInterceptorChain),
            null,
            0,
            0,
            0,
            61,
            null
         )
         .proceed((var1 as RealInterceptorChain).getRequest$okhttp());
   }
}
