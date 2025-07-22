package okhttp3.internal.connection

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.SocketAddress
import java.net.SocketException
import java.net.URI
import java.net.UnknownHostException
import java.net.Proxy.Type
import java.util.ArrayList
import java.util.NoSuchElementException
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.Intrinsics
import okhttp3.Address
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.HttpUrl
import okhttp3.Route
import okhttp3.internal.Util

public class RouteSelector(address: Address, routeDatabase: RouteDatabase, call: Call, eventListener: EventListener) {
   private final val address: Address
   private final val call: Call
   private final val eventListener: EventListener
   private final var inetSocketAddresses: List<InetSocketAddress>
   private final var nextProxyIndex: Int
   private final val postponedRoutes: MutableList<Route>
   private final var proxies: List<Proxy>
   private final val routeDatabase: RouteDatabase

   init {
      Intrinsics.checkParameterIsNotNull(var1, "address");
      Intrinsics.checkParameterIsNotNull(var2, "routeDatabase");
      Intrinsics.checkParameterIsNotNull(var3, "call");
      Intrinsics.checkParameterIsNotNull(var4, "eventListener");
      super();
      this.address = var1;
      this.routeDatabase = var2;
      this.call = var3;
      this.eventListener = var4;
      this.proxies = CollectionsKt.emptyList();
      this.inetSocketAddresses = CollectionsKt.emptyList();
      this.postponedRoutes = new ArrayList<>();
      this.resetNextProxy(var1.url(), var1.proxy());
   }

   private fun hasNextProxy(): Boolean {
      val var1: Boolean;
      if (this.nextProxyIndex < this.proxies.size()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Throws(java/io/IOException::class)
   private fun nextProxy(): Proxy {
      if (this.hasNextProxy()) {
         val var4: Proxy = this.proxies.get(this.nextProxyIndex++);
         this.resetNextInetSocketAddress(var4);
         return var4;
      } else {
         val var2: StringBuilder = new StringBuilder("No route to ");
         var2.append(this.address.url().host());
         var2.append("; exhausted proxy configurations: ");
         var2.append(this.proxies);
         throw (new SocketException(var2.toString())) as java.lang.Throwable;
      }
   }

   @Throws(java/io/IOException::class)
   private fun resetNextInetSocketAddress(proxy: Proxy) {
      val var4: java.util.List = new ArrayList();
      this.inetSocketAddresses = var4;
      val var2: Int;
      val var3: java.lang.String;
      if (var1.type() != Type.DIRECT && var1.type() != Type.SOCKS) {
         val var12: SocketAddress = var1.address();
         if (var12 !is InetSocketAddress) {
            val var11: StringBuilder = new StringBuilder("Proxy.address() is not an InetSocketAddress: ");
            var11.append(var12.getClass());
            throw (new IllegalArgumentException(var11.toString().toString())) as java.lang.Throwable;
         }

         var3 = Companion.getSocketHost(var12 as InetSocketAddress);
         var2 = (var12 as InetSocketAddress).getPort();
      } else {
         var3 = this.address.url().host();
         var2 = this.address.url().port();
      }

      if (1 <= var2 && 65535 >= var2) {
         if (var1.type() === Type.SOCKS) {
            var4.add(InetSocketAddress.createUnresolved(var3, var2));
         } else {
            this.eventListener.dnsStart(this.call, var3);
            val var8: java.util.List = this.address.dns().lookup(var3);
            if (var8.isEmpty()) {
               val var10: StringBuilder = new StringBuilder();
               var10.append(this.address.dns());
               var10.append(" returned no addresses for ");
               var10.append(var3);
               throw (new UnknownHostException(var10.toString())) as java.lang.Throwable;
            }

            this.eventListener.dnsEnd(this.call, var3, var8);

            for (InetAddress var13 : var8) {
               var4.add(new InetSocketAddress(var13, var2));
            }
         }
      } else {
         val var7: StringBuilder = new StringBuilder("No route to ");
         var7.append(var3);
         var7.append(':');
         var7.append(var2);
         var7.append("; port is out of range");
         throw (new SocketException(var7.toString())) as java.lang.Throwable;
      }
   }

   private fun resetNextProxy(url: HttpUrl, proxy: Proxy?) {
      val var3: Function0 = new Function0<java.util.List<? extends Proxy>>(this, var2, var1) {
         final Proxy $proxy;
         final HttpUrl $url;
         final RouteSelector this$0;

         {
            super(0);
            this.this$0 = var1;
            this.$proxy = var2;
            this.$url = var3;
         }

         public final java.util.List<Proxy> invoke() {
            if (this.$proxy != null) {
               return CollectionsKt.listOf(this.$proxy);
            } else {
               val var3: URI = this.$url.uri();
               if (var3.getHost() == null) {
                  return Util.immutableListOf(Proxy.NO_PROXY);
               } else {
                  val var4: java.util.List = RouteSelector.access$getAddress$p(this.this$0).proxySelector().select(var3);
                  return if (var4 as java.util.Collection != null && !var4.isEmpty()) Util.toImmutableList(var4) else Util.immutableListOf(Proxy.NO_PROXY);
               }
            }
         }
      };
      this.eventListener.proxySelectStart(this.call, var1);
      val var4: java.util.List = var3.invoke();
      this.proxies = var4;
      this.nextProxyIndex = 0;
      this.eventListener.proxySelectEnd(this.call, var1, var4);
   }

   public operator fun hasNext(): Boolean {
      val var1: Boolean;
      if (!this.hasNextProxy() && this.postponedRoutes.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @Throws(java/io/IOException::class)
   public operator fun next(): okhttp3.internal.connection.RouteSelector.Selection {
      if (!this.hasNext()) {
         throw (new NoSuchElementException()) as java.lang.Throwable;
      } else {
         val var1: java.util.List = new ArrayList();

         while (this.hasNextProxy()) {
            val var2: Proxy = this.nextProxy();

            for (InetSocketAddress var4 : this.inetSocketAddresses) {
               val var5: Route = new Route(this.address, var2, var4);
               if (this.routeDatabase.shouldPostpone(var5)) {
                  this.postponedRoutes.add(var5);
               } else {
                  var1.add(var5);
               }
            }

            if (!var1.isEmpty()) {
               break;
            }
         }

         if (var1.isEmpty()) {
            CollectionsKt.addAll(var1, this.postponedRoutes);
            this.postponedRoutes.clear();
         }

         return new RouteSelector.Selection(var1);
      }
   }

   public companion object {
      public final val socketHost: String
         public final get() {
            Intrinsics.checkParameterIsNotNull(var1, "$this$socketHost");
            val var2: InetAddress = var1.getAddress();
            if (var2 != null) {
               val var4: java.lang.String = var2.getHostAddress();
               Intrinsics.checkExpressionValueIsNotNull(var4, "address.hostAddress");
               return var4;
            } else {
               val var3: java.lang.String = var1.getHostName();
               Intrinsics.checkExpressionValueIsNotNull(var3, "hostName");
               return var3;
            }
         }

   }

   public class Selection(routes: List<Route>) {
      private final var nextRouteIndex: Int
      public final val routes: List<Route>

      init {
         Intrinsics.checkParameterIsNotNull(var1, "routes");
         super();
         this.routes = var1;
      }

      public operator fun hasNext(): Boolean {
         val var1: Boolean;
         if (this.nextRouteIndex < this.routes.size()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public operator fun next(): Route {
         if (this.hasNext()) {
            return this.routes.get(this.nextRouteIndex++);
         } else {
            throw (new NoSuchElementException()) as java.lang.Throwable;
         }
      }
   }
}
