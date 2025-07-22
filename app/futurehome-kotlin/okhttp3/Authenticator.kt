package okhttp3

import kotlin.jvm.internal.Intrinsics

public interface Authenticator {
   @Throws(java/io/IOException::class)
   public abstract fun authenticate(route: Route?, response: Response): Request? {
   }

   public companion object {
      public final val JAVA_NET_AUTHENTICATOR: Authenticator
      public final val NONE: Authenticator

      private class AuthenticatorNone : Authenticator {
         public override fun authenticate(route: Route?, response: Response): Request? {
            Intrinsics.checkParameterIsNotNull(var2, "response");
            return null;
         }
      }
   }
}
