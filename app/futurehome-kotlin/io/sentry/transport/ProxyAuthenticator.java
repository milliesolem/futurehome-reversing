package io.sentry.transport;

import io.sentry.util.Objects;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Authenticator.RequestorType;

final class ProxyAuthenticator extends Authenticator {
   private final String password;
   private final String user;

   ProxyAuthenticator(String var1, String var2) {
      this.user = Objects.requireNonNull(var1, "user is required");
      this.password = Objects.requireNonNull(var2, "password is required");
   }

   String getPassword() {
      return this.password;
   }

   @Override
   protected PasswordAuthentication getPasswordAuthentication() {
      return this.getRequestorType() == RequestorType.PROXY ? new PasswordAuthentication(this.user, this.password.toCharArray()) : null;
   }

   String getUser() {
      return this.user;
   }
}
