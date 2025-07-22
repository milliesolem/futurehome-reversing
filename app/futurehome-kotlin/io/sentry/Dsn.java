package io.sentry;

import io.sentry.util.Objects;
import java.net.URI;

final class Dsn {
   private final String path;
   private final String projectId;
   private final String publicKey;
   private final String secretKey;
   private final URI sentryUri;

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   Dsn(String var1) throws IllegalArgumentException {
      String var5;
      label1113: {
         URI var6;
         try {
            Objects.requireNonNull(var1, "The DSN is required.");
            URI var3 = new URI(var1);
            var6 = var3.normalize();
            var5 = var6.getScheme();
            if (!"http".equalsIgnoreCase(var5) && !"https".equalsIgnoreCase(var5)) {
               break label1113;
            }
         } catch (Throwable var217) {
            throw new IllegalArgumentException(var217);
         }

         try {
            var1 = var6.getUserInfo();
         } catch (Throwable var212) {
            throw new IllegalArgumentException(var212);
         }

         label1091:
         if (var1 != null) {
            try {
               if (var1.isEmpty()) {
                  break label1091;
               }

               var1 = var1.split(":", -1);
            } catch (Throwable var216) {
               throw new IllegalArgumentException(var216);
            }

            String var229 = var1[0];

            try {
               this.publicKey = var229;
            } catch (Throwable var210) {
               throw new IllegalArgumentException(var210);
            }

            label1073:
            if (var229 != null) {
               label1080: {
                  label1079: {
                     try {
                        if (var229.isEmpty()) {
                           break label1073;
                        }

                        if (var1.length > 1) {
                           break label1079;
                        }
                     } catch (Throwable var215) {
                        throw new IllegalArgumentException(var215);
                     }

                     var1 = null;
                     break label1080;
                  }

                  var1 = var1[1];
               }

               try {
                  this.secretKey = var1;
                  var1 = var6.getPath();
               } catch (Throwable var208) {
                  throw new IllegalArgumentException(var208);
               }

               var229 = var1;

               try {
                  if (var1.endsWith("/")) {
                     var229 = var1.substring(0, var1.length() - 1);
                  }
               } catch (Throwable var214) {
                  throw new IllegalArgumentException(var214);
               }

               int var2;
               String var4;
               try {
                  var2 = var229.lastIndexOf("/") + 1;
                  var4 = var229.substring(0, var2);
               } catch (Throwable var207) {
                  throw new IllegalArgumentException(var207);
               }

               var1 = var4;

               try {
                  if (!var4.endsWith("/")) {
                     StringBuilder var223 = new StringBuilder();
                     var223.append(var4);
                     var223.append("/");
                     var1 = var223.toString();
                  }
               } catch (Throwable var206) {
                  throw new IllegalArgumentException(var206);
               }

               try {
                  this.path = var1;
                  var4 = var229.substring(var2);
                  this.projectId = var4;
                  if (!var4.isEmpty()) {
                     var229 = var6.getHost();
                     var2 = var6.getPort();
                     StringBuilder var234 = new StringBuilder();
                     var234.append(var1);
                     var234.append("api/");
                     var234.append(var4);
                     URI var7 = new URI(var5, null, var229, var2, var234.toString(), null, null);
                     this.sentryUri = var7;
                     return;
                  }
               } catch (Throwable var213) {
                  throw new IllegalArgumentException(var213);
               }

               try {
                  IllegalArgumentException var224 = new IllegalArgumentException("Invalid DSN: A Project Id is required.");
                  throw var224;
               } catch (Throwable var205) {
                  throw new IllegalArgumentException(var205);
               }
            }

            try {
               IllegalArgumentException var225 = new IllegalArgumentException("Invalid DSN: No public key provided.");
               throw var225;
            } catch (Throwable var209) {
               throw new IllegalArgumentException(var209);
            }
         }

         try {
            IllegalArgumentException var226 = new IllegalArgumentException("Invalid DSN: No public key provided.");
            throw var226;
         } catch (Throwable var211) {
            throw new IllegalArgumentException(var211);
         }
      }

      try {
         StringBuilder var227 = new StringBuilder("Invalid DSN scheme: ");
         var227.append(var5);
         IllegalArgumentException var232 = new IllegalArgumentException(var227.toString());
         throw var232;
      } catch (Throwable var204) {
         throw new IllegalArgumentException(var204);
      }
   }

   public String getPath() {
      return this.path;
   }

   public String getProjectId() {
      return this.projectId;
   }

   public String getPublicKey() {
      return this.publicKey;
   }

   public String getSecretKey() {
      return this.secretKey;
   }

   URI getSentryUri() {
      return this.sentryUri;
   }
}
