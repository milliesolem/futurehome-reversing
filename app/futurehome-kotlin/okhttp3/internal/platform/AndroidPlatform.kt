package okhttp3.internal.platform

import android.os.Build.VERSION
import android.security.NetworkSecurityPolicy
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.net.InetSocketAddress
import java.net.Socket
import java.security.cert.TrustAnchor
import java.security.cert.X509Certificate
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.internal.platform.android.AndroidCertificateChainCleaner
import okhttp3.internal.platform.android.CloseGuard
import okhttp3.internal.platform.android.SocketAdapter
import okhttp3.internal.tls.CertificateChainCleaner
import okhttp3.internal.tls.TrustRootIndex
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

public class AndroidPlatform : Platform {
   private final val closeGuard: CloseGuard
   private final val socketAdapters: List<SocketAdapter>

   @JvmStatic
   fun {
      val var1: Boolean = Platform.Companion.isAndroid();
      var var0: Boolean = false;
      if (var1 && VERSION.SDK_INT < 30) {
         var0 = true;
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

   public override fun buildTrustRootIndex(trustManager: X509TrustManager): TrustRootIndex {
      Intrinsics.checkParameterIsNotNull(var1, "trustManager");

      var var6: TrustRootIndex;
      try {
         val var2: Method = var1.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", X509Certificate.class);
         Intrinsics.checkExpressionValueIsNotNull(var2, "method");
         var2.setAccessible(true);
         var6 = new AndroidPlatform.CustomTrustRootIndex(var1, var2);
      } catch (var4: NoSuchMethodException) {
         return super.buildTrustRootIndex(var1);
      }

      return var6;
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

   @Throws(java/io/IOException::class)
   public override fun connectSocket(socket: Socket, address: InetSocketAddress, connectTimeout: Int) {
      Intrinsics.checkParameterIsNotNull(var1, "socket");
      Intrinsics.checkParameterIsNotNull(var2, "address");

      try {
         var1.connect(var2, var3);
      } catch (var4: ClassCastException) {
         if (VERSION.SDK_INT == 26) {
            throw (new IOException("Exception in connect", var4)) as java.lang.Throwable;
         } else {
            throw var4 as java.lang.Throwable;
         }
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

   public override fun getStackTraceForCloseable(closer: String): Any? {
      Intrinsics.checkParameterIsNotNull(var1, "closer");
      return this.closeGuard.createAndOpen(var1);
   }

   public override fun isCleartextTrafficPermitted(hostname: String): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      val var2: Boolean;
      if (VERSION.SDK_INT >= 24) {
         var2 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(), var1);
      } else if (VERSION.SDK_INT >= 23) {
         val var3: NetworkSecurityPolicy = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m();
         Intrinsics.checkExpressionValueIsNotNull(var3, "NetworkSecurityPolicy.getInstance()");
         var2 = NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(var3);
      } else {
         var2 = true;
      }

      return var2;
   }

   public override fun logCloseableLeak(message: String, stackTrace: Any?) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      if (!this.closeGuard.warnIfOpen(var2)) {
         Platform.log$default(this, var1, 5, null, 4, null);
      }
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
         val var1: AndroidPlatform.Companion = this;
         val var2: Platform;
         if (this.isSupported()) {
            var2 = new AndroidPlatform();
         } else {
            var2 = null;
         }

         return var2;
      }
   }

   internal data class CustomTrustRootIndex(trustManager: X509TrustManager, findByIssuerAndSignatureMethod: Method) : TrustRootIndex {
      private final val findByIssuerAndSignatureMethod: Method
      private final val trustManager: X509TrustManager

      init {
         Intrinsics.checkParameterIsNotNull(var1, "trustManager");
         Intrinsics.checkParameterIsNotNull(var2, "findByIssuerAndSignatureMethod");
         super();
         this.trustManager = var1;
         this.findByIssuerAndSignatureMethod = var2;
      }

      private operator fun component1(): X509TrustManager {
         return this.trustManager;
      }

      private operator fun component2(): Method {
         return this.findByIssuerAndSignatureMethod;
      }

      public fun copy(trustManager: X509TrustManager = var0.trustManager, findByIssuerAndSignatureMethod: Method = var0.findByIssuerAndSignatureMethod): okhttp3.internal.platform.AndroidPlatform.CustomTrustRootIndex {
         Intrinsics.checkParameterIsNotNull(var1, "trustManager");
         Intrinsics.checkParameterIsNotNull(var2, "findByIssuerAndSignatureMethod");
         return new AndroidPlatform.CustomTrustRootIndex(var1, var2);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this != var1) {
            if (var1 !is AndroidPlatform.CustomTrustRootIndex) {
               return false;
            }

            if (!(this.trustManager == (var1 as AndroidPlatform.CustomTrustRootIndex).trustManager)
               || !(this.findByIssuerAndSignatureMethod == (var1 as AndroidPlatform.CustomTrustRootIndex).findByIssuerAndSignatureMethod)) {
               return false;
            }
         }

         return true;
      }

      public override fun findByIssuerAndSignature(cert: X509Certificate): X509Certificate? {
         Intrinsics.checkParameterIsNotNull(var1, "cert");

         try {
            var9 = this.findByIssuerAndSignatureMethod.invoke(this.trustManager, var1);
         } catch (var6: IllegalAccessException) {
            throw (new AssertionError("unable to get issues and signature", var6)) as java.lang.Throwable;
         } catch (var7: InvocationTargetException) {
            return null;
         }

         if (var9 != null) {
            try {
               var1 = (var9 as TrustAnchor).getTrustedCert();
            } catch (var2: IllegalAccessException) {
               throw (new AssertionError("unable to get issues and signature", var2)) as java.lang.Throwable;
            } catch (var3: InvocationTargetException) {
               var1 = null;
            }
         } else {
            try {
               throw new TypeCastException("null cannot be cast to non-null type java.security.cert.TrustAnchor");
            } catch (var4: IllegalAccessException) {
               throw (new AssertionError("unable to get issues and signature", var4)) as java.lang.Throwable;
            } catch (var5: InvocationTargetException) {
               var1 = null;
            }
         }

         return var1;
      }

      public override fun hashCode(): Int {
         var var2: Int = 0;
         val var1: Int;
         if (this.trustManager != null) {
            var1 = this.trustManager.hashCode();
         } else {
            var1 = 0;
         }

         if (this.findByIssuerAndSignatureMethod != null) {
            var2 = this.findByIssuerAndSignatureMethod.hashCode();
         }

         return var1 * 31 + var2;
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("CustomTrustRootIndex(trustManager=");
         var1.append(this.trustManager);
         var1.append(", findByIssuerAndSignatureMethod=");
         var1.append(this.findByIssuerAndSignatureMethod);
         var1.append(")");
         return var1.toString();
      }
   }
}
