package io.sentry.transport;

import java.net.Authenticator;

final class AuthenticatorWrapper {
   private static final AuthenticatorWrapper instance = new AuthenticatorWrapper();

   private AuthenticatorWrapper() {
   }

   public static AuthenticatorWrapper getInstance() {
      return instance;
   }

   public void setDefault(Authenticator var1) {
      Authenticator.setDefault(var1);
   }
}
