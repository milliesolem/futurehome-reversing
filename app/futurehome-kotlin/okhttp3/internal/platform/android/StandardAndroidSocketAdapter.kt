package okhttp3.internal.platform.android

import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okhttp3.internal.platform.Platform

public class StandardAndroidSocketAdapter(sslSocketClass: Class<in SSLSocket>, sslSocketFactoryClass: Class<in SSLSocketFactory>, paramClass: Class<*>)
   : AndroidSocketAdapter {
   private final val paramClass: Class<*>
   private final val sslSocketFactoryClass: Class<in SSLSocketFactory>

   init {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketClass");
      Intrinsics.checkParameterIsNotNull(var2, "sslSocketFactoryClass");
      Intrinsics.checkParameterIsNotNull(var3, "paramClass");
      super(var1);
      this.sslSocketFactoryClass = var2;
      this.paramClass = var3;
   }

   public override fun matchesSocketFactory(sslSocketFactory: SSLSocketFactory): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return this.sslSocketFactoryClass.isInstance(var1);
   }

   public override fun trustManager(sslSocketFactory: SSLSocketFactory): X509TrustManager? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      val var2: Any = Util.readFieldOrNull(var1, this.paramClass, "sslParameters");
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      var var3: X509TrustManager = Util.readFieldOrNull(var2, X509TrustManager.class, "x509TrustManager");
      if (var3 == null) {
         var3 = Util.readFieldOrNull(var2, X509TrustManager.class, "trustManager");
      }

      return var3;
   }

   public companion object {
      public fun buildIfSupported(packageName: String = "com.android.org.conscrypt"): SocketAdapter? {
         Intrinsics.checkParameterIsNotNull(var1, "packageName");

         var var13: Class;
         try {
            val var2: StringBuilder = new StringBuilder();
            var2.append(var1);
            var2.append(".OpenSSLSocketImpl");
            var13 = Class.forName(var2.toString());
         } catch (var9: Exception) {
            Platform.Companion.get().log("unable to load android socket classes", 5, var9);
            return null as SocketAdapter;
         }

         var var17: StandardAndroidSocketAdapter;
         if (var13 != null) {
            var var14: Class;
            try {
               val var3: StringBuilder = new StringBuilder();
               var3.append(var1);
               var3.append(".OpenSSLSocketFactoryImpl");
               var14 = Class.forName(var3.toString());
            } catch (var7: Exception) {
               Platform.Companion.get().log("unable to load android socket classes", 5, var7);
               return null as SocketAdapter;
            }

            if (var14 != null) {
               try {
                  val var4: StringBuilder = new StringBuilder();
                  var4.append(var1);
                  var4.append(".SSLParametersImpl");
                  val var15: Class = Class.forName(var4.toString());
                  Intrinsics.checkExpressionValueIsNotNull(var15, "paramsClass");
                  var17 = new StandardAndroidSocketAdapter(var13, var14, var15);
               } catch (var5: Exception) {
                  Platform.Companion.get().log("unable to load android socket classes", 5, var5);
                  var17 = null;
               }
            } else {
               try {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<in javax.net.ssl.SSLSocketFactory>");
               } catch (var6: Exception) {
                  Platform.Companion.get().log("unable to load android socket classes", 5, var6);
                  var17 = null;
               }
            }
         } else {
            try {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<in javax.net.ssl.SSLSocket>");
            } catch (var8: Exception) {
               Platform.Companion.get().log("unable to load android socket classes", 5, var8);
               var17 = null;
            }
         }

         return var17;
      }
   }
}
