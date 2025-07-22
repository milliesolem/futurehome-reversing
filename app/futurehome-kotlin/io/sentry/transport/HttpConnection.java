package io.sentry.transport;

import io.sentry.ILogger;
import io.sentry.RequestDetails;
import io.sentry.SentryEnvelope;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLConnection;
import java.net.Proxy.Type;
import java.nio.charset.Charset;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

final class HttpConnection {
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private final SentryOptions options;
   private final Proxy proxy;
   private final RateLimiter rateLimiter;
   private final RequestDetails requestDetails;

   HttpConnection(SentryOptions var1, RequestDetails var2, AuthenticatorWrapper var3, RateLimiter var4) {
      this.requestDetails = var2;
      this.options = var1;
      this.rateLimiter = var4;
      Proxy var6 = this.resolveProxy(var1.getProxy());
      this.proxy = var6;
      if (var6 != null && var1.getProxy() != null) {
         String var7 = var1.getProxy().getUser();
         String var5 = var1.getProxy().getPass();
         if (var7 != null && var5 != null) {
            var3.setDefault(new ProxyAuthenticator(var7, var5));
         }
      }
   }

   public HttpConnection(SentryOptions var1, RequestDetails var2, RateLimiter var3) {
      this(var1, var2, AuthenticatorWrapper.getInstance(), var3);
   }

   private void closeAndDisconnect(HttpURLConnection var1) {
      try {
         var1.getInputStream().close();
      } catch (IOException var5) {
      } finally {
         var1.disconnect();
      }
   }

   private HttpURLConnection createConnection() throws IOException {
      HttpURLConnection var1 = this.open();

      for (Entry var3 : this.requestDetails.getHeaders().entrySet()) {
         var1.setRequestProperty((String)var3.getKey(), (String)var3.getValue());
      }

      var1.setRequestMethod("POST");
      var1.setDoOutput(true);
      var1.setRequestProperty("Content-Encoding", "gzip");
      var1.setRequestProperty("Content-Type", "application/x-sentry-envelope");
      var1.setRequestProperty("Accept", "application/json");
      var1.setRequestProperty("Connection", "close");
      var1.setConnectTimeout(this.options.getConnectionTimeoutMillis());
      var1.setReadTimeout(this.options.getReadTimeoutMillis());
      SSLSocketFactory var4 = this.options.getSslSocketFactory();
      if (var1 instanceof HttpsURLConnection && var4 != null) {
         ((HttpsURLConnection)var1).setSSLSocketFactory(var4);
      }

      var1.connect();
      return var1;
   }

   private String getErrorMessageFromStream(HttpURLConnection param1) {
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
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokevirtual java/net/HttpURLConnection.getErrorStream ()Ljava/io/InputStream;
      // 04: astore 1
      // 05: new java/io/BufferedReader
      // 08: astore 3
      // 09: new java/io/InputStreamReader
      // 0c: astore 4
      // 0e: aload 4
      // 10: aload 1
      // 11: getstatic io/sentry/transport/HttpConnection.UTF_8 Ljava/nio/charset/Charset;
      // 14: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 17: aload 3
      // 18: aload 4
      // 1a: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 1d: new java/lang/StringBuilder
      // 20: astore 4
      // 22: aload 4
      // 24: invokespecial java/lang/StringBuilder.<init> ()V
      // 27: bipush 1
      // 28: istore 2
      // 29: aload 3
      // 2a: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 2d: astore 5
      // 2f: aload 5
      // 31: ifnull 4d
      // 34: iload 2
      // 35: ifne 40
      // 38: aload 4
      // 3a: ldc "\n"
      // 3c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3f: pop
      // 40: aload 4
      // 42: aload 5
      // 44: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 47: pop
      // 48: bipush 0
      // 49: istore 2
      // 4a: goto 29
      // 4d: aload 4
      // 4f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 52: astore 4
      // 54: aload 3
      // 55: invokevirtual java/io/BufferedReader.close ()V
      // 58: aload 1
      // 59: ifnull 60
      // 5c: aload 1
      // 5d: invokevirtual java/io/InputStream.close ()V
      // 60: aload 4
      // 62: areturn
      // 63: astore 4
      // 65: aload 3
      // 66: invokevirtual java/io/BufferedReader.close ()V
      // 69: goto 73
      // 6c: astore 3
      // 6d: aload 4
      // 6f: aload 3
      // 70: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 73: aload 4
      // 75: athrow
      // 76: astore 3
      // 77: aload 1
      // 78: ifnull 88
      // 7b: aload 1
      // 7c: invokevirtual java/io/InputStream.close ()V
      // 7f: goto 88
      // 82: astore 1
      // 83: aload 3
      // 84: aload 1
      // 85: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 88: aload 3
      // 89: athrow
      // 8a: astore 1
      // 8b: ldc "Failed to obtain error message while analyzing send failure."
      // 8d: areturn
   }

   private boolean isSuccessfulResponseCode(int var1) {
      boolean var2;
      if (var1 == 200) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private TransportResult readAndLog(HttpURLConnection var1) {
      try {
         int var2 = var1.getResponseCode();
         this.updateRetryAfterLimits(var1, var2);
         if (this.isSuccessfulResponseCode(var2)) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Envelope sent successfully.");
            return TransportResult.success();
         }

         this.options.getLogger().log(SentryLevel.ERROR, "Request failed, API returned %s", var2);
         if (this.options.isDebug()) {
            String var3 = this.getErrorMessageFromStream(var1);
            this.options.getLogger().log(SentryLevel.ERROR, "%s", var3);
         }

         return TransportResult.error(var2);
      } catch (IOException var6) {
         this.options.getLogger().log(SentryLevel.ERROR, var6, "Error reading and logging the response stream");
      } finally {
         this.closeAndDisconnect(var1);
      }

      return TransportResult.error();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private Proxy resolveProxy(SentryOptions.Proxy var1) {
      if (var1 != null) {
         String var3 = var1.getPort();
         String var4 = var1.getHost();
         if (var3 != null && var4 != null) {
            Type var11;
            label34: {
               try {
                  if (var1.getType() != null) {
                     var11 = var1.getType();
                     break label34;
                  }
               } catch (NumberFormatException var8) {
                  ILogger var13 = this.options.getLogger();
                  SentryLevel var2 = SentryLevel.ERROR;
                  StringBuilder var5 = new StringBuilder("Failed to parse Sentry Proxy port: ");
                  var5.append(var1.getPort());
                  var5.append(". Proxy is ignored");
                  var13.log(var2, var8, var5.toString());
                  return null;
               }

               try {
                  var11 = Type.HTTP;
               } catch (NumberFormatException var7) {
                  ILogger var14 = this.options.getLogger();
                  SentryLevel var10 = SentryLevel.ERROR;
                  StringBuilder var17 = new StringBuilder("Failed to parse Sentry Proxy port: ");
                  var17.append(var1.getPort());
                  var17.append(". Proxy is ignored");
                  var14.log(var10, var7, var17.toString());
                  return null;
               }
            }

            try {
               InetSocketAddress var19 = new InetSocketAddress(var4, Integer.parseInt(var3));
               var16 = new Proxy(var11, var19);
            } catch (NumberFormatException var6) {
               ILogger var15 = this.options.getLogger();
               SentryLevel var12 = SentryLevel.ERROR;
               StringBuilder var18 = new StringBuilder("Failed to parse Sentry Proxy port: ");
               var18.append(var1.getPort());
               var18.append(". Proxy is ignored");
               var15.log(var12, var6, var18.toString());
               return null;
            }

            return var16;
         }
      }

      return null;
   }

   Proxy getProxy() {
      return this.proxy;
   }

   HttpURLConnection open() throws IOException {
      URLConnection var1;
      if (this.proxy == null) {
         var1 = this.requestDetails.getUrl().openConnection();
      } else {
         var1 = this.requestDetails.getUrl().openConnection(this.proxy);
      }

      return (HttpURLConnection)var1;
   }

   public TransportResult send(SentryEnvelope param1) throws IOException {
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
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokespecial io/sentry/transport/HttpConnection.createConnection ()Ljava/net/HttpURLConnection;
      // 04: astore 2
      // 05: aload 2
      // 06: invokevirtual java/net/HttpURLConnection.getOutputStream ()Ljava/io/OutputStream;
      // 09: astore 3
      // 0a: new java/util/zip/GZIPOutputStream
      // 0d: astore 4
      // 0f: aload 4
      // 11: aload 3
      // 12: invokespecial java/util/zip/GZIPOutputStream.<init> (Ljava/io/OutputStream;)V
      // 15: aload 0
      // 16: getfield io/sentry/transport/HttpConnection.options Lio/sentry/SentryOptions;
      // 19: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 1c: aload 1
      // 1d: aload 4
      // 1f: invokeinterface io/sentry/ISerializer.serialize (Lio/sentry/SentryEnvelope;Ljava/io/OutputStream;)V 3
      // 24: aload 4
      // 26: invokevirtual java/util/zip/GZIPOutputStream.close ()V
      // 29: aload 3
      // 2a: ifnull 73
      // 2d: aload 3
      // 2e: invokevirtual java/io/OutputStream.close ()V
      // 31: goto 73
      // 34: astore 1
      // 35: aload 4
      // 37: invokevirtual java/util/zip/GZIPOutputStream.close ()V
      // 3a: goto 45
      // 3d: astore 4
      // 3f: aload 1
      // 40: aload 4
      // 42: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 45: aload 1
      // 46: athrow
      // 47: astore 1
      // 48: aload 3
      // 49: ifnull 59
      // 4c: aload 3
      // 4d: invokevirtual java/io/OutputStream.close ()V
      // 50: goto 59
      // 53: astore 3
      // 54: aload 1
      // 55: aload 3
      // 56: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 59: aload 1
      // 5a: athrow
      // 5b: astore 1
      // 5c: aload 0
      // 5d: getfield io/sentry/transport/HttpConnection.options Lio/sentry/SentryOptions;
      // 60: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 63: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 66: aload 1
      // 67: ldc_w "An exception occurred while submitting the envelope to the Sentry server."
      // 6a: bipush 0
      // 6b: anewarray 4
      // 6e: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 73: aload 0
      // 74: aload 2
      // 75: invokespecial io/sentry/transport/HttpConnection.readAndLog (Ljava/net/HttpURLConnection;)Lio/sentry/transport/TransportResult;
      // 78: areturn
      // 79: astore 1
      // 7a: aload 0
      // 7b: aload 2
      // 7c: invokespecial io/sentry/transport/HttpConnection.readAndLog (Ljava/net/HttpURLConnection;)Lio/sentry/transport/TransportResult;
      // 7f: pop
      // 80: aload 1
      // 81: athrow
   }

   public void updateRetryAfterLimits(HttpURLConnection var1, int var2) {
      String var3 = var1.getHeaderField("Retry-After");
      String var4 = var1.getHeaderField("X-Sentry-Rate-Limits");
      this.rateLimiter.updateRetryAfterLimits(var4, var3, var2);
   }
}
