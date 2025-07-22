package io.sentry;

import io.sentry.util.Platform;
import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.concurrent.RejectedExecutionException;

public final class SpotlightIntegration implements Integration, SentryOptions.BeforeEnvelopeCallback, Closeable {
   private ISentryExecutorService executorService;
   private ILogger logger = NoOpLogger.getInstance();
   private SentryOptions options;

   public SpotlightIntegration() {
      this.executorService = NoOpSentryExecutorService.getInstance();
   }

   private void closeAndDisconnect(HttpURLConnection var1) {
      try {
         var1.getInputStream().close();
      } catch (IOException var5) {
      } finally {
         var1.disconnect();
      }
   }

   private HttpURLConnection createConnection(String var1) throws Exception {
      HttpURLConnection var2 = (HttpURLConnection)URI.create(var1).toURL().openConnection();
      var2.setReadTimeout(1000);
      var2.setConnectTimeout(1000);
      var2.setRequestMethod("POST");
      var2.setDoOutput(true);
      var2.setRequestProperty("Content-Encoding", "gzip");
      var2.setRequestProperty("Content-Type", "application/x-sentry-envelope");
      var2.setRequestProperty("Accept", "application/json");
      var2.setRequestProperty("Connection", "close");
      var2.connect();
      return var2;
   }

   private void sendEnvelope(SentryEnvelope param1) {
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
      // 000: aload 0
      // 001: getfield io/sentry/SpotlightIntegration.options Lio/sentry/SentryOptions;
      // 004: ifnull 0e8
      // 007: aload 0
      // 008: aload 0
      // 009: invokevirtual io/sentry/SpotlightIntegration.getSpotlightConnectionUrl ()Ljava/lang/String;
      // 00c: invokespecial io/sentry/SpotlightIntegration.createConnection (Ljava/lang/String;)Ljava/net/HttpURLConnection;
      // 00f: astore 3
      // 010: aload 3
      // 011: invokevirtual java/net/HttpURLConnection.getOutputStream ()Ljava/io/OutputStream;
      // 014: astore 4
      // 016: new java/util/zip/GZIPOutputStream
      // 019: astore 5
      // 01b: aload 5
      // 01d: aload 4
      // 01f: invokespecial java/util/zip/GZIPOutputStream.<init> (Ljava/io/OutputStream;)V
      // 022: aload 0
      // 023: getfield io/sentry/SpotlightIntegration.options Lio/sentry/SentryOptions;
      // 026: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 029: aload 1
      // 02a: aload 5
      // 02c: invokeinterface io/sentry/ISerializer.serialize (Lio/sentry/SentryEnvelope;Ljava/io/OutputStream;)V 3
      // 031: aload 5
      // 033: invokevirtual java/util/zip/GZIPOutputStream.close ()V
      // 036: aload 4
      // 038: ifnull 040
      // 03b: aload 4
      // 03d: invokevirtual java/io/OutputStream.close ()V
      // 040: aload 3
      // 041: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 044: istore 2
      // 045: aload 0
      // 046: getfield io/sentry/SpotlightIntegration.logger Lio/sentry/ILogger;
      // 049: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 04c: ldc "Envelope sent to spotlight: %d"
      // 04e: bipush 1
      // 04f: anewarray 4
      // 052: dup
      // 053: bipush 0
      // 054: iload 2
      // 055: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 058: aastore
      // 059: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 05e: aload 0
      // 05f: aload 3
      // 060: invokespecial io/sentry/SpotlightIntegration.closeAndDisconnect (Ljava/net/HttpURLConnection;)V
      // 063: goto 104
      // 066: astore 1
      // 067: aload 5
      // 069: invokevirtual java/util/zip/GZIPOutputStream.close ()V
      // 06c: goto 077
      // 06f: astore 5
      // 071: aload 1
      // 072: aload 5
      // 074: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 077: aload 1
      // 078: athrow
      // 079: astore 1
      // 07a: aload 4
      // 07c: ifnull 08f
      // 07f: aload 4
      // 081: invokevirtual java/io/OutputStream.close ()V
      // 084: goto 08f
      // 087: astore 4
      // 089: aload 1
      // 08a: aload 4
      // 08c: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 08f: aload 1
      // 090: athrow
      // 091: astore 1
      // 092: aload 0
      // 093: getfield io/sentry/SpotlightIntegration.logger Lio/sentry/ILogger;
      // 096: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 099: ldc "An exception occurred while submitting the envelope to the Sentry server."
      // 09b: aload 1
      // 09c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 0a1: aload 3
      // 0a2: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 0a5: istore 2
      // 0a6: aload 0
      // 0a7: getfield io/sentry/SpotlightIntegration.logger Lio/sentry/ILogger;
      // 0aa: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 0ad: ldc "Envelope sent to spotlight: %d"
      // 0af: bipush 1
      // 0b0: anewarray 4
      // 0b3: dup
      // 0b4: bipush 0
      // 0b5: iload 2
      // 0b6: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 0b9: aastore
      // 0ba: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0bf: goto 05e
      // 0c2: astore 1
      // 0c3: aload 3
      // 0c4: invokevirtual java/net/HttpURLConnection.getResponseCode ()I
      // 0c7: istore 2
      // 0c8: aload 0
      // 0c9: getfield io/sentry/SpotlightIntegration.logger Lio/sentry/ILogger;
      // 0cc: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 0cf: ldc "Envelope sent to spotlight: %d"
      // 0d1: bipush 1
      // 0d2: anewarray 4
      // 0d5: dup
      // 0d6: bipush 0
      // 0d7: iload 2
      // 0d8: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 0db: aastore
      // 0dc: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0e1: aload 0
      // 0e2: aload 3
      // 0e3: invokespecial io/sentry/SpotlightIntegration.closeAndDisconnect (Ljava/net/HttpURLConnection;)V
      // 0e6: aload 1
      // 0e7: athrow
      // 0e8: new java/lang/IllegalArgumentException
      // 0eb: astore 1
      // 0ec: aload 1
      // 0ed: ldc "SentryOptions are required to send envelopes."
      // 0ef: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 0f2: aload 1
      // 0f3: athrow
      // 0f4: astore 1
      // 0f5: aload 0
      // 0f6: getfield io/sentry/SpotlightIntegration.logger Lio/sentry/ILogger;
      // 0f9: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0fc: ldc "An exception occurred while creating the connection to spotlight."
      // 0fe: aload 1
      // 0ff: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 104: return
   }

   @Override
   public void close() throws IOException {
      this.executorService.close(0L);
      SentryOptions var1 = this.options;
      if (var1 != null && var1.getBeforeEnvelopeCallback() == this) {
         this.options.setBeforeEnvelopeCallback(null);
      }
   }

   @Override
   public void execute(SentryEnvelope var1, Hint var2) {
      try {
         ISentryExecutorService var3 = this.executorService;
         SpotlightIntegration$$ExternalSyntheticLambda0 var5 = new SpotlightIntegration$$ExternalSyntheticLambda0(this, var1);
         var3.submit(var5);
      } catch (RejectedExecutionException var4) {
         this.logger.log(SentryLevel.WARNING, "Spotlight envelope submission rejected.", var4);
      }
   }

   public String getSpotlightConnectionUrl() {
      SentryOptions var1 = this.options;
      if (var1 != null && var1.getSpotlightConnectionUrl() != null) {
         return this.options.getSpotlightConnectionUrl();
      } else {
         return Platform.isAndroid() ? "http://10.0.2.2:8969/stream" : "http://localhost:8969/stream";
      }
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      this.options = var2;
      this.logger = var2.getLogger();
      if (var2.getBeforeEnvelopeCallback() == null && var2.isEnableSpotlight()) {
         this.executorService = new SentryExecutorService();
         var2.setBeforeEnvelopeCallback(this);
         this.logger.log(SentryLevel.DEBUG, "SpotlightIntegration enabled.");
      } else {
         this.logger.log(SentryLevel.DEBUG, "SpotlightIntegration is not enabled. BeforeEnvelopeCallback is already set or spotlight is not enabled.");
      }
   }
}
