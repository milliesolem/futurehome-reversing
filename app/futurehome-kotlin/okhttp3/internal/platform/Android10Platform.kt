package okhttp3.internal.platform

import android.os.Build.VERSION
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner
import okhttp3.internal.platform.android.SocketAdapter
import okhttp3.internal.tls.CertificateChainCleaner
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

public class Android10Platform : Platform {
   private final val socketAdapters: List<SocketAdapter>

   @JvmStatic
   fun {
      val var0: Boolean;
      if (Platform.Companion.isAndroid() && VERSION.SDK_INT >= 29) {
         var0 = true;
      } else {
         var0 = false;
      }

      isSupported = var0;
   }

   public override fun buildCertificateChainCleaner(trustManager: X509TrustManager): CertificateChainCleaner {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");
      val var2: AndroidCertificateChainCleaner = AndroidCertificateChainCleaner.Companion.buildIfSupported(var1);
      val var3: CertificateChainCleaner;
      if (var2 != null) {
         var3 = var2;
      } else {
         var3 = super.buildCertificateChainCleaner(var1);
      }

      return var3;
   }

   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      val var5: java.util.Iterator = this.socketAdapters.iterator();

      var var4: Any;
      do {
         if (!var5.hasNext()) {
            var4 = null;
            break;
         }

         var4 = (SocketAdapter)var5.next();
      } while (!var4.matchesSocket(var1));

      var4 = var4;
      if (var4 != null) {
         var4.configureTlsExtensions(var1, var2, var3);
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var5: java.util.Iterator = this.socketAdapters.iterator();

      var var3: Any;
      do {
         if (!var5.hasNext()) {
            var3 = null;
            break;
         }

         var3 = (java.lang.String)var5.next();
      } while (!((SocketAdapter)var3).matchesSocket(var1));

      val var7: SocketAdapter = var3 as SocketAdapter;
      var3 = null;
      if (var7 != null) {
         var3 = var7.getSelectedProtocol(var1);
      }

      return var3;
   }

   public override fun isCleartextTrafficPermitted(hostname: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(), var1);
   }

   public override fun trustManager(sslSocketFactory: SSLSocketFactory): X509TrustManager? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      val var5: java.util.Iterator = this.socketAdapters.iterator();

      var var3: Any;
      do {
         if (!var5.hasNext()) {
            var3 = null;
            break;
         }

         var3 = (X509TrustManager)var5.next();
      } while (!((SocketAdapter)var3).matchesSocketFactory(var1));

      val var7: SocketAdapter = var3 as SocketAdapter;
      var3 = null;
      if (var7 != null) {
         var3 = var7.trustManager(var1);
      }

      return var3;
   }

   public companion object {
      public final val isSupported: Boolean

      public fun buildIfSupported(): Platform? {
         val var1: Android10Platform.Companion = this;
         val var2: Platform;
         if (this.isSupported()) {
            var2 = new Android10Platform();
         } else {
            var2 = null;
         }

         return var2;
      }
   }
}
