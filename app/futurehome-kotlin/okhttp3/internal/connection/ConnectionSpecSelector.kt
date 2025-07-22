package okhttp3.internal.connection

import java.io.IOException
import java.io.InterruptedIOException
import java.net.ProtocolException
import java.net.UnknownServiceException
import java.security.cert.CertificateException
import java.util.Arrays
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException
import javax.net.ssl.SSLSocket
import kotlin.jvm.internal.Intrinsics
import okhttp3.ConnectionSpec

internal class ConnectionSpecSelector(connectionSpecs: List<ConnectionSpec>) {
   private final val connectionSpecs: List<ConnectionSpec>
   private final var isFallback: Boolean
   private final var isFallbackPossible: Boolean
   private final var nextModeIndex: Int

   init {
      Intrinsics.checkParameterIsNotNull(var1, "connectionSpecs");
      super();
      this.connectionSpecs = var1;
   }

   private fun isFallbackPossible(socket: SSLSocket): Boolean {
      var var2: Int = this.nextModeIndex;

      for (int var3 = this.connectionSpecs.size(); var2 < var3; var2++) {
         if (this.connectionSpecs.get(var2).isCompatible(var1)) {
            return true;
         }
      }

      return false;
   }

   @Throws(java/io/IOException::class)
   public fun configureSecureSocket(sslSocket: SSLSocket): ConnectionSpec {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var5: Any = null;
      var var4: ConnectionSpec = null as ConnectionSpec;
      var var2: Int = this.nextModeIndex;
      val var3: Int = this.connectionSpecs.size();

      while (true) {
         var4 = (ConnectionSpec)var5;
         if (var2 >= var3) {
            break;
         }

         var4 = this.connectionSpecs.get(var2);
         if (var4.isCompatible(var1)) {
            this.nextModeIndex = var2 + 1;
            break;
         }

         var2++;
      }

      if (var4 == null) {
         val var9: StringBuilder = new StringBuilder("Unable to find acceptable protocols. isFallback=");
         var9.append(this.isFallback);
         var9.append(", modes=");
         var9.append(this.connectionSpecs);
         var9.append(", supported protocols=");
         val var6: Array<java.lang.String> = var1.getEnabledProtocols();
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         val var7: java.lang.String = Arrays.toString((Object[])var6);
         Intrinsics.checkExpressionValueIsNotNull(var7, "java.util.Arrays.toString(this)");
         var9.append(var7);
         throw (new UnknownServiceException(var9.toString())) as java.lang.Throwable;
      } else {
         this.isFallbackPossible = this.isFallbackPossible(var1);
         var4.apply$okhttp(var1, this.isFallback);
         return var4;
      }
   }

   public fun connectionFailed(e: IOException): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "e");
      var var2: Boolean = true;
      this.isFallback = true;
      if (!this.isFallbackPossible
         || var1 is ProtocolException
         || var1 is InterruptedIOException
         || var1 is SSLHandshakeException && var1.getCause() is CertificateException
         || var1 is SSLPeerUnverifiedException
         || var1 !is SSLException) {
         var2 = false;
      }

      return var2;
   }
}
