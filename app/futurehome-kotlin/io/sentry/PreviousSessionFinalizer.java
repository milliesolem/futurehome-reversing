package io.sentry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

final class PreviousSessionFinalizer implements Runnable {
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private final IHub hub;
   private final SentryOptions options;

   PreviousSessionFinalizer(SentryOptions var1, IHub var2) {
      this.options = var1;
      this.hub = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Date getTimestampFromCrashMarkerFile(File var1) {
      BufferedReader var2;
      try {
         FileInputStream var4 = new FileInputStream(var1);
         InputStreamReader var3 = new InputStreamReader(var4, UTF_8);
         var2 = new BufferedReader(var3);
      } catch (IOException var33) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error reading the crash marker file.", var33);
         return null;
      } catch (IllegalArgumentException var34) {
         this.options.getLogger().log(SentryLevel.ERROR, var34, "Error converting the crash timestamp.");
         return null;
      }

      try {
         String var36 = var2.readLine();
         this.options.getLogger().log(SentryLevel.DEBUG, "Crash marker file has %s timestamp.", var36);
         var37 = DateUtils.getDateTime(var36);
      } catch (Throwable var32) {
         Throwable var35 = var32;

         try {
            var2.close();
         } catch (Throwable var31) {
            Throwable var38 = var31;

            label79:
            try {
               var35.addSuppressed(var38);
               break label79;
            } catch (IOException var29) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error reading the crash marker file.", var29);
               return null;
            } catch (IllegalArgumentException var30) {
               this.options.getLogger().log(SentryLevel.ERROR, var30, "Error converting the crash timestamp.");
               return null;
            }
         }

         try {
            throw var35;
         } catch (IOException var25) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error reading the crash marker file.", var25);
            return null;
         } catch (IllegalArgumentException var26) {
            this.options.getLogger().log(SentryLevel.ERROR, var26, "Error converting the crash timestamp.");
            return null;
         }
      }

      try {
         var2.close();
         return var37;
      } catch (IOException var27) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error reading the crash marker file.", var27);
      } catch (IllegalArgumentException var28) {
         this.options.getLogger().log(SentryLevel.ERROR, var28, "Error converting the crash timestamp.");
      }

      return null;
   }

   @Override
   public void run() {
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
      // 001: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 004: invokevirtual io/sentry/SentryOptions.getCacheDirPath ()Ljava/lang/String;
      // 007: astore 3
      // 008: aload 3
      // 009: ifnonnull 022
      // 00c: aload 0
      // 00d: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 010: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 013: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 016: ldc "Cache dir is not set, not finalizing the previous session."
      // 018: bipush 0
      // 019: anewarray 4
      // 01c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 021: return
      // 022: aload 0
      // 023: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 026: invokevirtual io/sentry/SentryOptions.isEnableAutoSessionTracking ()Z
      // 029: ifne 042
      // 02c: aload 0
      // 02d: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 030: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 033: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 036: ldc "Session tracking is disabled, bailing from previous session finalizer."
      // 038: bipush 0
      // 039: anewarray 4
      // 03c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 041: return
      // 042: aload 0
      // 043: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 046: invokevirtual io/sentry/SentryOptions.getEnvelopeDiskCache ()Lio/sentry/cache/IEnvelopeCache;
      // 049: astore 2
      // 04a: aload 2
      // 04b: instanceof io/sentry/cache/EnvelopeCache
      // 04e: ifeq 071
      // 051: aload 2
      // 052: checkcast io/sentry/cache/EnvelopeCache
      // 055: invokevirtual io/sentry/cache/EnvelopeCache.waitPreviousSessionFlush ()Z
      // 058: ifne 071
      // 05b: aload 0
      // 05c: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 05f: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 062: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 065: ldc "Timed out waiting to flush previous session to its own file in session finalizer."
      // 067: bipush 0
      // 068: anewarray 4
      // 06b: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 070: return
      // 071: aload 3
      // 072: invokestatic io/sentry/cache/EnvelopeCache.getPreviousSessionFile (Ljava/lang/String;)Ljava/io/File;
      // 075: astore 3
      // 076: aload 0
      // 077: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 07a: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 07d: astore 5
      // 07f: aload 3
      // 080: invokevirtual java/io/File.exists ()Z
      // 083: ifeq 1cf
      // 086: aload 0
      // 087: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 08a: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 08d: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 090: ldc "Current session is not ended, we'd need to end it."
      // 092: bipush 0
      // 093: anewarray 4
      // 096: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 09b: new java/io/BufferedReader
      // 09e: astore 4
      // 0a0: new java/io/InputStreamReader
      // 0a3: astore 2
      // 0a4: new java/io/FileInputStream
      // 0a7: astore 6
      // 0a9: aload 6
      // 0ab: aload 3
      // 0ac: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 0af: aload 2
      // 0b0: aload 6
      // 0b2: getstatic io/sentry/PreviousSessionFinalizer.UTF_8 Ljava/nio/charset/Charset;
      // 0b5: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 0b8: aload 4
      // 0ba: aload 2
      // 0bb: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 0be: aload 5
      // 0c0: aload 4
      // 0c2: ldc io/sentry/Session
      // 0c4: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
      // 0c9: checkcast io/sentry/Session
      // 0cc: astore 6
      // 0ce: aload 6
      // 0d0: ifnonnull 0f2
      // 0d3: aload 0
      // 0d4: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 0d7: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0da: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0dd: ldc "Stream from path %s resulted in a null envelope."
      // 0df: bipush 1
      // 0e0: anewarray 4
      // 0e3: dup
      // 0e4: bipush 0
      // 0e5: aload 3
      // 0e6: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 0e9: aastore
      // 0ea: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0ef: goto 185
      // 0f2: new java/io/File
      // 0f5: astore 7
      // 0f7: aload 7
      // 0f9: aload 0
      // 0fa: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 0fd: invokevirtual io/sentry/SentryOptions.getCacheDirPath ()Ljava/lang/String;
      // 100: ldc ".sentry-native/last_crash"
      // 102: invokespecial java/io/File.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 105: aload 7
      // 107: invokevirtual java/io/File.exists ()Z
      // 10a: istore 1
      // 10b: aconst_null
      // 10c: astore 2
      // 10d: iload 1
      // 10e: ifeq 15d
      // 111: aload 0
      // 112: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 115: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 118: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 11b: ldc "Crash marker file exists, last Session is gonna be Crashed."
      // 11d: bipush 0
      // 11e: anewarray 4
      // 121: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 126: aload 0
      // 127: aload 7
      // 129: invokespecial io/sentry/PreviousSessionFinalizer.getTimestampFromCrashMarkerFile (Ljava/io/File;)Ljava/util/Date;
      // 12c: astore 2
      // 12d: aload 7
      // 12f: invokevirtual java/io/File.delete ()Z
      // 132: ifne 152
      // 135: aload 0
      // 136: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 139: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 13c: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 13f: ldc "Failed to delete the crash marker file. %s."
      // 141: bipush 1
      // 142: anewarray 4
      // 145: dup
      // 146: bipush 0
      // 147: aload 7
      // 149: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 14c: aastore
      // 14d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 152: aload 6
      // 154: getstatic io/sentry/Session$State.Crashed Lio/sentry/Session$State;
      // 157: aconst_null
      // 158: bipush 1
      // 159: invokevirtual io/sentry/Session.update (Lio/sentry/Session$State;Ljava/lang/String;Z)Z
      // 15c: pop
      // 15d: aload 6
      // 15f: invokevirtual io/sentry/Session.getAbnormalMechanism ()Ljava/lang/String;
      // 162: ifnonnull 16b
      // 165: aload 6
      // 167: aload 2
      // 168: invokevirtual io/sentry/Session.end (Ljava/util/Date;)V
      // 16b: aload 5
      // 16d: aload 6
      // 16f: aload 0
      // 170: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 173: invokevirtual io/sentry/SentryOptions.getSdkVersion ()Lio/sentry/protocol/SdkVersion;
      // 176: invokestatic io/sentry/SentryEnvelope.from (Lio/sentry/ISerializer;Lio/sentry/Session;Lio/sentry/protocol/SdkVersion;)Lio/sentry/SentryEnvelope;
      // 179: astore 2
      // 17a: aload 0
      // 17b: getfield io/sentry/PreviousSessionFinalizer.hub Lio/sentry/IHub;
      // 17e: aload 2
      // 17f: invokeinterface io/sentry/IHub.captureEnvelope (Lio/sentry/SentryEnvelope;)Lio/sentry/protocol/SentryId; 2
      // 184: pop
      // 185: aload 4
      // 187: invokevirtual java/io/Reader.close ()V
      // 18a: goto 1b3
      // 18d: astore 2
      // 18e: aload 4
      // 190: invokevirtual java/io/Reader.close ()V
      // 193: goto 19e
      // 196: astore 4
      // 198: aload 2
      // 199: aload 4
      // 19b: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 19e: aload 2
      // 19f: athrow
      // 1a0: astore 2
      // 1a1: aload 0
      // 1a2: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 1a5: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 1a8: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 1ab: ldc "Error processing previous session."
      // 1ad: aload 2
      // 1ae: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 1b3: aload 3
      // 1b4: invokevirtual java/io/File.delete ()Z
      // 1b7: ifne 1cf
      // 1ba: aload 0
      // 1bb: getfield io/sentry/PreviousSessionFinalizer.options Lio/sentry/SentryOptions;
      // 1be: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 1c1: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 1c4: ldc "Failed to delete the previous session file."
      // 1c6: bipush 0
      // 1c7: anewarray 4
      // 1ca: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1cf: return
   }
}
