package okhttp3.internal.proxy

import java.io.IOException
import java.net.Proxy
import java.net.ProxySelector
import java.net.SocketAddress
import java.net.URI

public object NullProxySelector : ProxySelector {
   public override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
   }

   public override fun select(uri: URI?): List<Proxy> {
      if (var1 != null) {
         return CollectionsKt.listOf(Proxy.NO_PROXY);
      } else {
         throw (new IllegalArgumentException("uri must not be null".toString())) as java.lang.Throwable;
      }
   }
}
