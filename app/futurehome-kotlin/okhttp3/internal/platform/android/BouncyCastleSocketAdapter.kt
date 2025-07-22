package okhttp3.internal.platform.android

import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.internal.platform.BouncyCastlePlatform
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.android.DeferredSocketAdapter.Factory
import org.bouncycastle.jsse.BCSSLParameters
import org.bouncycastle.jsse.BCSSLSocket

public class BouncyCastleSocketAdapter : SocketAdapter {
   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (this.matchesSocket(var1)) {
         val var4: BCSSLSocket = var1 as BCSSLSocket;
         val var5: BCSSLParameters = (var1 as BCSSLSocket).getParameters();
         Intrinsics.checkExpressionValueIsNotNull(var5, "sslParameters");
         val var6: Array<Any> = Platform.Companion.alpnProtocolNames(var3).toArray(new java.lang.String[0]);
         if (var6 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         var5.setApplicationProtocols(var6 as Array<java.lang.String>);
         var4.setParameters(var5);
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var2: java.lang.String = (var1 as BCSSLSocket).getApplicationProtocol();
      return if (var2 != null && !(var2 == "")) var2 else null;
   }

   public override fun isSupported(): Boolean {
      return BouncyCastlePlatform.Companion.isSupported();
   }

   public override fun matchesSocket(sslSocket: SSLSocket): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return var1 is BCSSLSocket;
   }

   override fun matchesSocketFactory(var1: SSLSocketFactory): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return SocketAdapter.DefaultImpls.matchesSocketFactory(this, var1);
   }

   override fun trustManager(var1: SSLSocketFactory): X509TrustManager {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return SocketAdapter.DefaultImpls.trustManager(this, var1);
   }

   public companion object {
      public final val factory: Factory
   }
}
