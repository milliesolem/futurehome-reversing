package okhttp3.internal.platform.android

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol
import okhttp3.internal.platform.AndroidPlatform
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.android.DeferredSocketAdapter.Factory

public open class AndroidSocketAdapter(sslSocketClass: Class<in SSLSocket>) : SocketAdapter {
   private final val getAlpnSelectedProtocol: Method
   private final val setAlpnProtocols: Method
   private final val setHostname: Method
   private final val setUseSessionTickets: Method
   private final val sslSocketClass: Class<in SSLSocket>

   @JvmStatic
   fun {
      val var0: AndroidSocketAdapter.Companion = new AndroidSocketAdapter.Companion(null);
      Companion = var0;
      playProviderFactory = var0.factory("com.google.android.gms.org.conscrypt");
   }

   init {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketClass");
      super();
      this.sslSocketClass = var1;
      val var2: Method = var1.getDeclaredMethod("setUseSessionTickets", boolean.class);
      Intrinsics.checkExpressionValueIsNotNull(var2, "sslSocketClass.getDeclar…:class.javaPrimitiveType)");
      this.setUseSessionTickets = var2;
      this.setHostname = var1.getMethod("setHostname", java.lang.String.class);
      this.getAlpnSelectedProtocol = var1.getMethod("getAlpnSelectedProtocol", null);
      this.setAlpnProtocols = var1.getMethod("setAlpnProtocols", byte[].class);
   }

   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (this.matchesSocket(var1)) {
         try {
            this.setUseSessionTickets.invoke(var1, true);
         } catch (var8: IllegalAccessException) {
            throw (new AssertionError(var8)) as java.lang.Throwable;
         } catch (var9: InvocationTargetException) {
            throw (new AssertionError(var9)) as java.lang.Throwable;
         }

         if (var2 != null) {
            try {
               this.setHostname.invoke(var1, var2);
            } catch (var6: IllegalAccessException) {
               throw (new AssertionError(var6)) as java.lang.Throwable;
            } catch (var7: InvocationTargetException) {
               throw (new AssertionError(var7)) as java.lang.Throwable;
            }
         }

         try {
            this.setAlpnProtocols.invoke(var1, Platform.Companion.concatLengthPrefixed(var3));
         } catch (var4: IllegalAccessException) {
            throw (new AssertionError(var4)) as java.lang.Throwable;
         } catch (var5: InvocationTargetException) {
            throw (new AssertionError(var5)) as java.lang.Throwable;
         }
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      if (!this.matchesSocket(var1)) {
         return null;
      } else {
         var var4: ByteArray;
         try {
            var4 = this.getAlpnSelectedProtocol.invoke(var1, null) as ByteArray;
         } catch (var9: NullPointerException) {
            if (var9.getMessage() == "ssl == null") {
               return null;
            }

            throw var9 as java.lang.Throwable;
         } catch (var10: IllegalAccessException) {
            throw (new AssertionError(var10)) as java.lang.Throwable;
         } catch (var11: InvocationTargetException) {
            throw (new AssertionError(var11)) as java.lang.Throwable;
         }

         if (var4 == null) {
            return null;
         } else {
            try {
               val var5: Charset = StandardCharsets.UTF_8;
               Intrinsics.checkExpressionValueIsNotNull(StandardCharsets.UTF_8, "StandardCharsets.UTF_8");
               return new java.lang.String(var4, var5);
            } catch (var6: NullPointerException) {
               if (!(var6.getMessage() == "ssl == null")) {
                  throw var6 as java.lang.Throwable;
               } else {
                  return null;
               }
            } catch (var7: IllegalAccessException) {
               throw (new AssertionError(var7)) as java.lang.Throwable;
            } catch (var8: InvocationTargetException) {
               throw (new AssertionError(var8)) as java.lang.Throwable;
            }
         }
      }
   }

   public override fun isSupported(): Boolean {
      return AndroidPlatform.Companion.isSupported();
   }

   public override fun matchesSocket(sslSocket: SSLSocket): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return this.sslSocketClass.isInstance(var1);
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
      public final val playProviderFactory: Factory

      private fun build(actualSSLSocketClass: Class<in SSLSocket>): AndroidSocketAdapter {
         var var2: Class = var1;

         while (var2 != null && !(var2.getSimpleName() == "OpenSSLSocketImpl")) {
            var2 = var2.getSuperclass();
            if (var2 == null) {
               val var3: StringBuilder = new StringBuilder("No OpenSSLSocketImpl superclass of socket of type ");
               var3.append(var1);
               throw (new AssertionError(var3.toString())) as java.lang.Throwable;
            }
         }

         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         return new AndroidSocketAdapter(var2);
      }

      public fun factory(packageName: String): Factory {
         Intrinsics.checkParameterIsNotNull(var1, "packageName");
         return new DeferredSocketAdapter.Factory(var1) {
            final java.lang.String $packageName;

            {
               this.$packageName = var1;
            }

            @Override
            public SocketAdapter create(SSLSocket var1) {
               Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
               return AndroidSocketAdapter.Companion.access$build(AndroidSocketAdapter.Companion, var1.getClass());
            }

            @Override
            public boolean matchesSocket(SSLSocket var1) {
               Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
               val var2: java.lang.String = var1.getClass().getName();
               Intrinsics.checkExpressionValueIsNotNull(var2, "sslSocket.javaClass.name");
               val var3: StringBuilder = new StringBuilder();
               var3.append(this.$packageName);
               var3.append('.');
               return StringsKt.startsWith$default(var2, var3.toString(), false, 2, null);
            }
         };
      }
   }
}
