package okhttp3.internal.platform.android

import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol

public interface SocketAdapter {
   public abstract fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
   }

   public abstract fun getSelectedProtocol(sslSocket: SSLSocket): String? {
   }

   public abstract fun isSupported(): Boolean {
   }

   public abstract fun matchesSocket(sslSocket: SSLSocket): Boolean {
   }

   public open fun matchesSocketFactory(sslSocketFactory: SSLSocketFactory): Boolean {
   }

   public open fun trustManager(sslSocketFactory: SSLSocketFactory): X509TrustManager? {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun matchesSocketFactory(var0: SocketAdapter, var1: SSLSocketFactory): Boolean {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
         return false;
      }

      @JvmStatic
      fun trustManager(var0: SocketAdapter, var1: SSLSocketFactory): X509TrustManager {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
         return null;
      }
   }
}
