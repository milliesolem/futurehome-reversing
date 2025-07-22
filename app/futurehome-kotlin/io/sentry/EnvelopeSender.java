package io.sentry;

import io.sentry.util.Objects;
import java.io.File;

public final class EnvelopeSender extends DirectoryProcessor implements IEnvelopeSender {
   private final IHub hub;
   private final ILogger logger;
   private final ISerializer serializer;

   public EnvelopeSender(IHub var1, ISerializer var2, ILogger var3, long var4, int var6) {
      super(var1, var3, var4, var6);
      this.hub = Objects.requireNonNull(var1, "Hub is required.");
      this.serializer = Objects.requireNonNull(var2, "Serializer is required.");
      this.logger = Objects.requireNonNull(var3, "Logger is required.");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void safeDelete(File var1, String var2) {
      try {
         if (!var1.delete()) {
            this.logger.log(SentryLevel.ERROR, "Failed to delete '%s' %s", var1.getAbsolutePath(), var2);
         }
      } catch (Throwable var5) {
         this.logger.log(SentryLevel.ERROR, var5, "Failed to delete '%s' %s", var1.getAbsolutePath(), var2);
         return;
      }
   }

   @Override
   protected boolean isRelevantFileName(String var1) {
      return var1.endsWith(".envelope");
   }

   @Override
   public void processEnvelopeFile(String var1, Hint var2) {
      Objects.requireNonNull(var1, "Path is required.");
      this.processFile(new File(var1), var2);
   }

   @Override
   protected void processFile(File param1, Hint param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 1
      // 001: invokevirtual java/io/File.isFile ()Z
      // 004: ifne 021
      // 007: aload 0
      // 008: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 00b: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 00e: ldc "'%s' is not a file."
      // 010: bipush 1
      // 011: anewarray 60
      // 014: dup
      // 015: bipush 0
      // 016: aload 1
      // 017: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 01a: aastore
      // 01b: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 020: return
      // 021: aload 0
      // 022: aload 1
      // 023: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 026: invokevirtual io/sentry/EnvelopeSender.isRelevantFileName (Ljava/lang/String;)Z
      // 029: ifne 046
      // 02c: aload 0
      // 02d: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 030: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 033: ldc "File '%s' doesn't match extension expected."
      // 035: bipush 1
      // 036: anewarray 60
      // 039: dup
      // 03a: bipush 0
      // 03b: aload 1
      // 03c: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 03f: aastore
      // 040: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 045: return
      // 046: aload 1
      // 047: invokevirtual java/io/File.getParentFile ()Ljava/io/File;
      // 04a: invokevirtual java/io/File.canWrite ()Z
      // 04d: ifne 06a
      // 050: aload 0
      // 051: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 054: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 057: ldc "File '%s' cannot be deleted so it will not be processed."
      // 059: bipush 1
      // 05a: anewarray 60
      // 05d: dup
      // 05e: bipush 0
      // 05f: aload 1
      // 060: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 063: aastore
      // 064: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 069: return
      // 06a: new java/io/BufferedInputStream
      // 06d: astore 3
      // 06e: new java/io/FileInputStream
      // 071: astore 4
      // 073: aload 4
      // 075: aload 1
      // 076: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 079: aload 3
      // 07a: aload 4
      // 07c: invokespecial java/io/BufferedInputStream.<init> (Ljava/io/InputStream;)V
      // 07f: aload 0
      // 080: getfield io/sentry/EnvelopeSender.serializer Lio/sentry/ISerializer;
      // 083: aload 3
      // 084: invokeinterface io/sentry/ISerializer.deserializeEnvelope (Ljava/io/InputStream;)Lio/sentry/SentryEnvelope; 2
      // 089: astore 4
      // 08b: aload 4
      // 08d: ifnonnull 0ac
      // 090: aload 0
      // 091: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 094: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 097: ldc "Failed to deserialize cached envelope %s"
      // 099: bipush 1
      // 09a: anewarray 60
      // 09d: dup
      // 09e: bipush 0
      // 09f: aload 1
      // 0a0: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 0a3: aastore
      // 0a4: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0a9: goto 0b9
      // 0ac: aload 0
      // 0ad: getfield io/sentry/EnvelopeSender.hub Lio/sentry/IHub;
      // 0b0: aload 4
      // 0b2: aload 2
      // 0b3: invokeinterface io/sentry/IHub.captureEnvelope (Lio/sentry/SentryEnvelope;Lio/sentry/Hint;)Lio/sentry/protocol/SentryId; 3
      // 0b8: pop
      // 0b9: aload 0
      // 0ba: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 0bd: astore 4
      // 0bf: new io/sentry/EnvelopeSender$$ExternalSyntheticLambda0
      // 0c2: astore 5
      // 0c4: aload 5
      // 0c6: aload 0
      // 0c7: invokespecial io/sentry/EnvelopeSender$$ExternalSyntheticLambda0.<init> (Lio/sentry/EnvelopeSender;)V
      // 0ca: aload 2
      // 0cb: ldc io/sentry/hints/Flushable
      // 0cd: aload 4
      // 0cf: aload 5
      // 0d1: invokestatic io/sentry/util/HintUtils.runIfHasTypeLogIfNot (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/ILogger;Lio/sentry/util/HintUtils$SentryConsumer;)V
      // 0d4: aload 3
      // 0d5: invokevirtual java/io/InputStream.close ()V
      // 0d8: ldc io/sentry/hints/Retryable
      // 0da: astore 5
      // 0dc: aload 0
      // 0dd: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 0e0: astore 3
      // 0e1: new io/sentry/EnvelopeSender$$ExternalSyntheticLambda1
      // 0e4: dup
      // 0e5: aload 0
      // 0e6: aload 1
      // 0e7: invokespecial io/sentry/EnvelopeSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/EnvelopeSender;Ljava/io/File;)V
      // 0ea: astore 4
      // 0ec: aload 5
      // 0ee: astore 1
      // 0ef: goto 1be
      // 0f2: astore 4
      // 0f4: aload 3
      // 0f5: invokevirtual java/io/InputStream.close ()V
      // 0f8: goto 102
      // 0fb: astore 3
      // 0fc: aload 4
      // 0fe: aload 3
      // 0ff: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 102: aload 4
      // 104: athrow
      // 105: astore 4
      // 107: aload 0
      // 108: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 10b: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 10e: aload 4
      // 110: ldc "Failed to capture cached envelope %s"
      // 112: bipush 1
      // 113: anewarray 60
      // 116: dup
      // 117: bipush 0
      // 118: aload 1
      // 119: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 11c: aastore
      // 11d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 122: aload 0
      // 123: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 126: astore 5
      // 128: new io/sentry/EnvelopeSender$$ExternalSyntheticLambda2
      // 12b: astore 3
      // 12c: aload 3
      // 12d: aload 0
      // 12e: aload 4
      // 130: aload 1
      // 131: invokespecial io/sentry/EnvelopeSender$$ExternalSyntheticLambda2.<init> (Lio/sentry/EnvelopeSender;Ljava/lang/Throwable;Ljava/io/File;)V
      // 134: aload 2
      // 135: ldc io/sentry/hints/Retryable
      // 137: aload 5
      // 139: aload 3
      // 13a: invokestatic io/sentry/util/HintUtils.runIfHasTypeLogIfNot (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/ILogger;Lio/sentry/util/HintUtils$SentryConsumer;)V
      // 13d: ldc io/sentry/hints/Retryable
      // 13f: astore 5
      // 141: aload 0
      // 142: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 145: astore 3
      // 146: new io/sentry/EnvelopeSender$$ExternalSyntheticLambda1
      // 149: dup
      // 14a: aload 0
      // 14b: aload 1
      // 14c: invokespecial io/sentry/EnvelopeSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/EnvelopeSender;Ljava/io/File;)V
      // 14f: astore 4
      // 151: aload 5
      // 153: astore 1
      // 154: goto 1be
      // 157: astore 3
      // 158: aload 0
      // 159: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 15c: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 15f: aload 3
      // 160: ldc "I/O on file '%s' failed."
      // 162: bipush 1
      // 163: anewarray 60
      // 166: dup
      // 167: bipush 0
      // 168: aload 1
      // 169: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 16c: aastore
      // 16d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 172: ldc io/sentry/hints/Retryable
      // 174: astore 5
      // 176: aload 0
      // 177: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 17a: astore 3
      // 17b: new io/sentry/EnvelopeSender$$ExternalSyntheticLambda1
      // 17e: dup
      // 17f: aload 0
      // 180: aload 1
      // 181: invokespecial io/sentry/EnvelopeSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/EnvelopeSender;Ljava/io/File;)V
      // 184: astore 4
      // 186: aload 5
      // 188: astore 1
      // 189: goto 1be
      // 18c: astore 3
      // 18d: aload 0
      // 18e: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 191: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 194: aload 3
      // 195: ldc "File '%s' cannot be found."
      // 197: bipush 1
      // 198: anewarray 60
      // 19b: dup
      // 19c: bipush 0
      // 19d: aload 1
      // 19e: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 1a1: aastore
      // 1a2: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 1a7: ldc io/sentry/hints/Retryable
      // 1a9: astore 5
      // 1ab: aload 0
      // 1ac: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 1af: astore 3
      // 1b0: new io/sentry/EnvelopeSender$$ExternalSyntheticLambda1
      // 1b3: dup
      // 1b4: aload 0
      // 1b5: aload 1
      // 1b6: invokespecial io/sentry/EnvelopeSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/EnvelopeSender;Ljava/io/File;)V
      // 1b9: astore 4
      // 1bb: aload 5
      // 1bd: astore 1
      // 1be: aload 2
      // 1bf: aload 1
      // 1c0: aload 3
      // 1c1: aload 4
      // 1c3: invokestatic io/sentry/util/HintUtils.runIfHasTypeLogIfNot (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/ILogger;Lio/sentry/util/HintUtils$SentryConsumer;)V
      // 1c6: return
      // 1c7: astore 3
      // 1c8: aload 2
      // 1c9: ldc io/sentry/hints/Retryable
      // 1cb: aload 0
      // 1cc: getfield io/sentry/EnvelopeSender.logger Lio/sentry/ILogger;
      // 1cf: new io/sentry/EnvelopeSender$$ExternalSyntheticLambda1
      // 1d2: dup
      // 1d3: aload 0
      // 1d4: aload 1
      // 1d5: invokespecial io/sentry/EnvelopeSender$$ExternalSyntheticLambda1.<init> (Lio/sentry/EnvelopeSender;Ljava/io/File;)V
      // 1d8: invokestatic io/sentry/util/HintUtils.runIfHasTypeLogIfNot (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/ILogger;Lio/sentry/util/HintUtils$SentryConsumer;)V
      // 1db: aload 3
      // 1dc: athrow
   }
}
