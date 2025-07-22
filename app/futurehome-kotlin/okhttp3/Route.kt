package okhttp3

import java.net.InetSocketAddress
import java.net.Proxy
import java.net.Proxy.Type
import kotlin.jvm.internal.Intrinsics

public class Route(address: Address, proxy: Proxy, socketAddress: InetSocketAddress) {
   public final val address: Address
   public final val proxy: Proxy
   public final val socketAddress: InetSocketAddress

   init {
      Intrinsics.checkParameterIsNotNull(var1, "address");
      Intrinsics.checkParameterIsNotNull(var2, "proxy");
      Intrinsics.checkParameterIsNotNull(var3, "socketAddress");
      super();
      this.address = var1;
      this.proxy = var2;
      this.socketAddress = var3;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "address", imports = []))
   public fun address(): Address {
      return this.address;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxy", imports = []))
   public fun proxy(): Proxy {
      return this.proxy;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "socketAddress", imports = []))
   public fun socketAddress(): InetSocketAddress {
      return this.socketAddress;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is Route
         && (var1 as Route).address == this.address
         && (var1 as Route).proxy == this.proxy
         && (var1 as Route).socketAddress == this.socketAddress;
   }

   public override fun hashCode(): Int {
      return ((527 + this.address.hashCode()) * 31 + this.proxy.hashCode()) * 31 + this.socketAddress.hashCode();
   }

   public fun requiresTunnel(): Boolean {
      val var1: Boolean;
      if (this.address.sslSocketFactory() != null && this.proxy.type() === Type.HTTP) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("Route{");
      var1.append(this.socketAddress);
      var1.append('}');
      return var1.toString();
   }
}
