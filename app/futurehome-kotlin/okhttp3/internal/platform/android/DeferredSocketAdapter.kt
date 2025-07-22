package okhttp3.internal.platform.android

import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.jvm.internal.Intrinsics
import okhttp3.Protocol

public class DeferredSocketAdapter(socketAdapterFactory: okhttp3.internal.platform.android.DeferredSocketAdapter.Factory) : SocketAdapter {
   private final var delegate: SocketAdapter?
   private final val socketAdapterFactory: okhttp3.internal.platform.android.DeferredSocketAdapter.Factory

   init {
      Intrinsics.checkParameterIsNotNull(var1, "socketAdapterFactory");
      super();
      this.socketAdapterFactory = var1;
   }

   private fun getDelegate(sslSocket: SSLSocket): SocketAdapter? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okhttp3/internal/platform/android/DeferredSocketAdapter.delegate Lokhttp3/internal/platform/android/SocketAdapter;
      // 06: ifnonnull 24
      // 09: aload 0
      // 0a: getfield okhttp3/internal/platform/android/DeferredSocketAdapter.socketAdapterFactory Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;
      // 0d: aload 1
      // 0e: invokeinterface okhttp3/internal/platform/android/DeferredSocketAdapter$Factory.matchesSocket (Ljavax/net/ssl/SSLSocket;)Z 2
      // 13: ifeq 24
      // 16: aload 0
      // 17: aload 0
      // 18: getfield okhttp3/internal/platform/android/DeferredSocketAdapter.socketAdapterFactory Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;
      // 1b: aload 1
      // 1c: invokeinterface okhttp3/internal/platform/android/DeferredSocketAdapter$Factory.create (Ljavax/net/ssl/SSLSocket;)Lokhttp3/internal/platform/android/SocketAdapter; 2
      // 21: putfield okhttp3/internal/platform/android/DeferredSocketAdapter.delegate Lokhttp3/internal/platform/android/SocketAdapter;
      // 24: aload 0
      // 25: getfield okhttp3/internal/platform/android/DeferredSocketAdapter.delegate Lokhttp3/internal/platform/android/SocketAdapter;
      // 28: astore 1
      // 29: aload 0
      // 2a: monitorexit
      // 2b: aload 1
      // 2c: areturn
      // 2d: astore 1
      // 2e: aload 0
      // 2f: monitorexit
      // 30: aload 1
      // 31: athrow
   }

   public override fun configureTlsExtensions(sslSocket: SSLSocket, hostname: String?, protocols: List<Protocol>) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      val var4: SocketAdapter = this.getDelegate(var1);
      if (var4 != null) {
         var4.configureTlsExtensions(var1, var2, var3);
      }
   }

   public override fun getSelectedProtocol(sslSocket: SSLSocket): String? {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      val var2: SocketAdapter = this.getDelegate(var1);
      val var3: java.lang.String;
      if (var2 != null) {
         var3 = var2.getSelectedProtocol(var1);
      } else {
         var3 = null;
      }

      return var3;
   }

   public override fun isSupported(): Boolean {
      return true;
   }

   public override fun matchesSocket(sslSocket: SSLSocket): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return this.socketAdapterFactory.matchesSocket(var1);
   }

   override fun matchesSocketFactory(var1: SSLSocketFactory): Boolean {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return SocketAdapter.DefaultImpls.matchesSocketFactory(this, var1);
   }

   override fun trustManager(var1: SSLSocketFactory): X509TrustManager {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return SocketAdapter.DefaultImpls.trustManager(this, var1);
   }

   public interface Factory {
      public abstract fun create(sslSocket: SSLSocket): SocketAdapter {
      }

      public abstract fun matchesSocket(sslSocket: SSLSocket): Boolean {
      }
   }
}
