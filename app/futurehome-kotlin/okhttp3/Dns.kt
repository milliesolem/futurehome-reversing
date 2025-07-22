package okhttp3

import java.net.InetAddress
import java.net.UnknownHostException
import kotlin.jvm.internal.Intrinsics

public interface Dns {
   @Throws(java/net/UnknownHostException::class)
   public abstract fun lookup(hostname: String): List<InetAddress> {
   }

   public companion object {
      public final val SYSTEM: Dns

      private class DnsSystem : Dns {
         public override fun lookup(hostname: String): List<InetAddress> {
            Intrinsics.checkParameterIsNotNull(var1, "hostname");

            try {
               val var2: Array<InetAddress> = InetAddress.getAllByName(var1);
               Intrinsics.checkExpressionValueIsNotNull(var2, "InetAddress.getAllByName(hostname)");
               return ArraysKt.toList(var2);
            } catch (var4: NullPointerException) {
               val var3: StringBuilder = new StringBuilder("Broken system behaviour for dns lookup of ");
               var3.append(var1);
               val var5: UnknownHostException = new UnknownHostException(var3.toString());
               var5.initCause(var4);
               throw var5 as java.lang.Throwable;
            }
         }
      }
   }
}
