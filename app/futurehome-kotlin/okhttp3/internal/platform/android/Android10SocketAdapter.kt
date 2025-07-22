package okhttp3.internal.platform.android

import android.os.Build.VERSION
import java.io.IOException
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.internal.platform.Platform
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

public class Android10SocketAdapter : SocketAdapter {
   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");

      try {
         NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var1, true);
         var8 = var1.getSSLParameters();
         Intrinsics.checkExpressionValueIsNotNull(var8, "sslParameters");
         var9 = Platform.Companion.alpnProtocolNames(var3).toArray(new java.lang.String[0]);
      } catch (var6: IllegalArgumentException) {
         throw (new IOException("Android internal error", var6)) as java.lang.Throwable;
      }

      if (var9 != null) {
         try {
            NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var8, var9 as Array<java.lang.String>);
            var1.setSSLParameters(var8);
         } catch (var4: IllegalArgumentException) {
            throw (new IOException("Android internal error", var4)) as java.lang.Throwable;
         }
      } else {
         try {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         } catch (var5: IllegalArgumentException) {
            throw (new IOException("Android internal error", var5)) as java.lang.Throwable;
         }
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var2: java.lang.String = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var1);
      return if (var2 != null && !(var2 == "")) var2 else null;
   }

   public override fun isSupported(): Boolean {
      return Companion.isSupported();
   }

   public override fun matchesSocket(sslSocket: SSLSocket): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var1);
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
      public fun buildIfSupported(): SocketAdapter? {
         val var1: Android10SocketAdapter.Companion = this;
         val var2: SocketAdapter;
         if (this.isSupported()) {
            var2 = new Android10SocketAdapter();
         } else {
            var2 = null;
         }

         return var2;
      }

      public fun isSupported(): Boolean {
         val var1: Boolean;
         if (Platform.Companion.isAndroid() && VERSION.SDK_INT >= 29) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }
}
