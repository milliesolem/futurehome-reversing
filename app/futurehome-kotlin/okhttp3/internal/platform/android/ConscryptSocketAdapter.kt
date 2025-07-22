package okhttp3.internal.platform.android

import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.internal.platform.ConscryptPlatform
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.android.DeferredSocketAdapter.Factory
import org.conscrypt.Conscrypt

public class ConscryptSocketAdapter : SocketAdapter {
   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (this.matchesSocket(var1)) {
         Conscrypt.setUseSessionTickets(var1, true);
         val var4: Array<Any> = Platform.Companion.alpnProtocolNames(var3).toArray(new java.lang.String[0]);
         if (var4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         Conscrypt.setApplicationProtocols(var1, var4 as Array<java.lang.String>);
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var2: java.lang.String;
      if (this.matchesSocket(var1)) {
         var2 = Conscrypt.getApplicationProtocol(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public override fun isSupported(): Boolean {
      return ConscryptPlatform.Companion.isSupported();
   }

   public override fun matchesSocket(sslSocket: SSLSocket): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return Conscrypt.isConscrypt(var1);
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
