package okhttp3.internal.platform

import javax.net.ssl.SSLParameters
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

public open class Jdk9Platform : Platform {
   @JvmStatic
   fun {
      val var1: java.lang.String = System.getProperty("java.specification.version");
      val var3: Int;
      if (var1 != null) {
         var3 = StringsKt.toIntOrNull(var1);
      } else {
         var3 = null;
      }

      var var0: Boolean;
      label22: {
         var0 = true;
         if (var3 != null) {
            if (var3 >= 9) {
               break label22;
            }
         } else {
            try {
               SSLSocket::class.java.getMethod("getApplicationProtocol", null);
               break label22;
            } catch (var2: NoSuchMethodException) {
            }
         }

         var0 = false;
      }

      isAvailable = var0;
   }

   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      val var4: SSLParameters = var1.getSSLParameters();
      var3 = Platform.Companion.alpnProtocolNames(var3);
      Intrinsics.checkExpressionValueIsNotNull(var4, "sslParameters");
      val var6: Array<Any> = var3.toArray(new java.lang.String[0]);
      if (var6 != null) {
         NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var4, var6 as Array<java.lang.String>);
         var1.setSSLParameters(var4);
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");

      try {
         var6 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var1);
      } catch (var5: UnsupportedOperationException) {
         return null;
      }

      if (var6 == null) {
         var6 = null;
      } else {
         var var2: Boolean;
         try {
            var2 = var6 == "";
         } catch (var4: UnsupportedOperationException) {
            return null;
         }

         if (var2) {
            var6 = null;
         }
      }

      return var6;
   }

   public override fun trustManager(sslSocketFactory: SSLSocketFactory): X509TrustManager? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      throw (new UnsupportedOperationException("clientBuilder.sslSocketFactory(SSLSocketFactory) not supported on JDK 9+")) as java.lang.Throwable;
   }

   public companion object {
      public final val isAvailable: Boolean

      public fun buildIfSupported(): Jdk9Platform? {
         val var1: Jdk9Platform.Companion = this;
         val var2: Jdk9Platform;
         if (this.isAvailable()) {
            var2 = new Jdk9Platform();
         } else {
            var2 = null;
         }

         return var2;
      }
   }
}
