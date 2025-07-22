package okhttp3

import java.io.IOException
import kotlin.jvm.internal.Intrinsics

public enum class Protocol(protocol: String) {
   H2_PRIOR_KNOWLEDGE,
   HTTP_1_0,
   HTTP_1_1,
   HTTP_2,
   QUIC,
   @Deprecated(message = "OkHttp has dropped support for SPDY. Prefer {@link #HTTP_2}.")
   SPDY_3
   private final val protocol: String
   @JvmStatic
   private Protocol[] $VALUES;
   @JvmStatic
   public Protocol.Companion Companion = new Protocol.Companion(null);

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @JvmStatic
   fun {
      val var2: Protocol = new Protocol("http/1.0");
      HTTP_1_0 = var2;
      val var4: Protocol = new Protocol("http/1.1");
      HTTP_1_1 = var4;
      val var5: Protocol = new Protocol("spdy/3.1");
      SPDY_3 = var5;
      val var3: Protocol = new Protocol("h2");
      HTTP_2 = var3;
      val var0: Protocol = new Protocol("h2_prior_knowledge");
      H2_PRIOR_KNOWLEDGE = var0;
      val var1: Protocol = new Protocol("quic");
      QUIC = var1;
      $VALUES = new Protocol[]{var2, var4, var5, var3, var0, var1};
   }

   init {
      this.protocol = var3;
   }

   public override fun toString(): String {
      return this.protocol;
   }

   public companion object {
      @Throws(java/io/IOException::class)
      public fun get(protocol: String): Protocol {
         Intrinsics.checkParameterIsNotNull(var1, "protocol");
         val var3: Protocol;
         if (var1 == Protocol.access$getProtocol$p(Protocol.HTTP_1_0)) {
            var3 = Protocol.HTTP_1_0;
         } else if (var1 == Protocol.access$getProtocol$p(Protocol.HTTP_1_1)) {
            var3 = Protocol.HTTP_1_1;
         } else if (var1 == Protocol.access$getProtocol$p(Protocol.H2_PRIOR_KNOWLEDGE)) {
            var3 = Protocol.H2_PRIOR_KNOWLEDGE;
         } else if (var1 == Protocol.access$getProtocol$p(Protocol.HTTP_2)) {
            var3 = Protocol.HTTP_2;
         } else if (var1 == Protocol.access$getProtocol$p(Protocol.SPDY_3)) {
            var3 = Protocol.SPDY_3;
         } else {
            if (!(var1 == Protocol.access$getProtocol$p(Protocol.QUIC))) {
               val var2: StringBuilder = new StringBuilder("Unexpected protocol: ");
               var2.append(var1);
               throw (new IOException(var2.toString())) as java.lang.Throwable;
            }

            var3 = Protocol.QUIC;
         }

         return var3;
      }
   }
}
