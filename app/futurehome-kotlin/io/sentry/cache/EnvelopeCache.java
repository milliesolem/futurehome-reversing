package io.sentry.cache;

import io.sentry.DateUtils;
import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.Session;
import io.sentry.transport.NoOpEnvelopeCache;
import io.sentry.util.Objects;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class EnvelopeCache extends CacheStrategy implements IEnvelopeCache {
   public static final String CRASH_MARKER_FILE = "last_crash";
   public static final String NATIVE_CRASH_MARKER_FILE = ".sentry-native/last_crash";
   public static final String PREFIX_CURRENT_SESSION_FILE = "session";
   public static final String PREFIX_PREVIOUS_SESSION_FILE = "previous_session";
   public static final String STARTUP_CRASH_MARKER_FILE = "startup_crash";
   public static final String SUFFIX_ENVELOPE_FILE = ".envelope";
   static final String SUFFIX_SESSION_FILE = ".json";
   private final Map<SentryEnvelope, String> fileNameMap = new WeakHashMap<>();
   private final CountDownLatch previousSessionLatch = new CountDownLatch(1);

   public EnvelopeCache(SentryOptions var1, String var2, int var3) {
      super(var1, var2, var3);
   }

   private File[] allEnvelopeFiles() {
      if (this.isDirectoryValid()) {
         File[] var1 = this.directory.listFiles(new EnvelopeCache$$ExternalSyntheticLambda0());
         if (var1 != null) {
            return var1;
         }
      }

      return new File[0];
   }

   public static IEnvelopeCache create(SentryOptions var0) {
      String var2 = var0.getCacheDirPath();
      int var1 = var0.getMaxCacheItems();
      if (var2 == null) {
         var0.getLogger().log(SentryLevel.WARNING, "cacheDirPath is null, returning NoOpEnvelopeCache");
         return NoOpEnvelopeCache.getInstance();
      } else {
         return new EnvelopeCache(var0, var2, var1);
      }
   }

   public static File getCurrentSessionFile(String var0) {
      return new File(var0, "session.json");
   }

   private File getEnvelopeFile(SentryEnvelope param1) {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/cache/EnvelopeCache.fileNameMap Ljava/util/Map;
      // 06: aload 1
      // 07: invokeinterface java/util/Map.containsKey (Ljava/lang/Object;)Z 2
      // 0c: ifeq 20
      // 0f: aload 0
      // 10: getfield io/sentry/cache/EnvelopeCache.fileNameMap Ljava/util/Map;
      // 13: aload 1
      // 14: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 19: checkcast java/lang/String
      // 1c: astore 1
      // 1d: goto 4a
      // 20: new java/lang/StringBuilder
      // 23: astore 2
      // 24: aload 2
      // 25: invokespecial java/lang/StringBuilder.<init> ()V
      // 28: aload 2
      // 29: invokestatic java/util/UUID.randomUUID ()Ljava/util/UUID;
      // 2c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 2f: pop
      // 30: aload 2
      // 31: ldc ".envelope"
      // 33: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 36: pop
      // 37: aload 2
      // 38: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3b: astore 2
      // 3c: aload 0
      // 3d: getfield io/sentry/cache/EnvelopeCache.fileNameMap Ljava/util/Map;
      // 40: aload 1
      // 41: aload 2
      // 42: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 47: pop
      // 48: aload 2
      // 49: astore 1
      // 4a: new java/io/File
      // 4d: dup
      // 4e: aload 0
      // 4f: getfield io/sentry/cache/EnvelopeCache.directory Ljava/io/File;
      // 52: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 55: aload 1
      // 56: invokespecial java/io/File.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 59: astore 1
      // 5a: aload 0
      // 5b: monitorexit
      // 5c: aload 1
      // 5d: areturn
      // 5e: astore 1
      // 5f: aload 0
      // 60: monitorexit
      // 61: aload 1
      // 62: athrow
   }

   public static File getPreviousSessionFile(String var0) {
      return new File(var0, "previous_session.json");
   }

   private void tryEndPreviousSession(Hint param1) {
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
      // 000: aload 1
      // 001: invokestatic io/sentry/util/HintUtils.getSentrySdkHint (Lio/sentry/Hint;)Ljava/lang/Object;
      // 004: astore 1
      // 005: aload 1
      // 006: instanceof io/sentry/hints/AbnormalExit
      // 009: ifeq 126
      // 00c: aload 0
      // 00d: getfield io/sentry/cache/EnvelopeCache.directory Ljava/io/File;
      // 010: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 013: invokestatic io/sentry/cache/EnvelopeCache.getPreviousSessionFile (Ljava/lang/String;)Ljava/io/File;
      // 016: astore 4
      // 018: aload 4
      // 01a: invokevirtual java/io/File.exists ()Z
      // 01d: ifeq 110
      // 020: aload 0
      // 021: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 024: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 027: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 02a: ldc "Previous session is not ended, we'd need to end it."
      // 02c: bipush 0
      // 02d: anewarray 97
      // 030: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 035: new java/io/BufferedReader
      // 038: astore 3
      // 039: new java/io/InputStreamReader
      // 03c: astore 2
      // 03d: new java/io/FileInputStream
      // 040: astore 5
      // 042: aload 5
      // 044: aload 4
      // 046: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 049: aload 2
      // 04a: aload 5
      // 04c: getstatic io/sentry/cache/EnvelopeCache.UTF_8 Ljava/nio/charset/Charset;
      // 04f: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 052: aload 3
      // 053: aload 2
      // 054: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 057: aload 0
      // 058: getfield io/sentry/cache/EnvelopeCache.serializer Lio/sentry/util/LazyEvaluator;
      // 05b: invokevirtual io/sentry/util/LazyEvaluator.getValue ()Ljava/lang/Object;
      // 05e: checkcast io/sentry/ISerializer
      // 061: aload 3
      // 062: ldc io/sentry/Session
      // 064: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
      // 069: checkcast io/sentry/Session
      // 06c: astore 5
      // 06e: aload 5
      // 070: ifnull 0e2
      // 073: aload 1
      // 074: checkcast io/sentry/hints/AbnormalExit
      // 077: astore 6
      // 079: aload 6
      // 07b: invokeinterface io/sentry/hints/AbnormalExit.timestamp ()Ljava/lang/Long; 1
      // 080: astore 1
      // 081: aload 1
      // 082: ifnull 0be
      // 085: aload 1
      // 086: invokevirtual java/lang/Long.longValue ()J
      // 089: invokestatic io/sentry/DateUtils.getDateTime (J)Ljava/util/Date;
      // 08c: astore 2
      // 08d: aload 5
      // 08f: invokevirtual io/sentry/Session.getStarted ()Ljava/util/Date;
      // 092: astore 7
      // 094: aload 7
      // 096: ifnull 0a4
      // 099: aload 2
      // 09a: astore 1
      // 09b: aload 2
      // 09c: aload 7
      // 09e: invokevirtual java/util/Date.before (Ljava/util/Date;)Z
      // 0a1: ifeq 0c0
      // 0a4: aload 0
      // 0a5: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 0a8: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0ab: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 0ae: ldc "Abnormal exit happened before previous session start, not ending the session."
      // 0b0: bipush 0
      // 0b1: anewarray 97
      // 0b4: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0b9: aload 3
      // 0ba: invokevirtual java/io/Reader.close ()V
      // 0bd: return
      // 0be: aconst_null
      // 0bf: astore 1
      // 0c0: aload 6
      // 0c2: invokeinterface io/sentry/hints/AbnormalExit.mechanism ()Ljava/lang/String; 1
      // 0c7: astore 2
      // 0c8: aload 5
      // 0ca: getstatic io/sentry/Session$State.Abnormal Lio/sentry/Session$State;
      // 0cd: aconst_null
      // 0ce: bipush 1
      // 0cf: aload 2
      // 0d0: invokevirtual io/sentry/Session.update (Lio/sentry/Session$State;Ljava/lang/String;ZLjava/lang/String;)Z
      // 0d3: pop
      // 0d4: aload 5
      // 0d6: aload 1
      // 0d7: invokevirtual io/sentry/Session.end (Ljava/util/Date;)V
      // 0da: aload 0
      // 0db: aload 4
      // 0dd: aload 5
      // 0df: invokespecial io/sentry/cache/EnvelopeCache.writeSessionToDisk (Ljava/io/File;Lio/sentry/Session;)V
      // 0e2: aload 3
      // 0e3: invokevirtual java/io/Reader.close ()V
      // 0e6: goto 126
      // 0e9: astore 1
      // 0ea: aload 3
      // 0eb: invokevirtual java/io/Reader.close ()V
      // 0ee: goto 0f7
      // 0f1: astore 2
      // 0f2: aload 1
      // 0f3: aload 2
      // 0f4: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 0f7: aload 1
      // 0f8: athrow
      // 0f9: astore 1
      // 0fa: aload 0
      // 0fb: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 0fe: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 101: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 104: ldc_w "Error processing previous session."
      // 107: aload 1
      // 108: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 10d: goto 126
      // 110: aload 0
      // 111: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 114: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 117: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 11a: ldc_w "No previous session file to end."
      // 11d: bipush 0
      // 11e: anewarray 97
      // 121: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 126: return
   }

   private void updateCurrentSession(File param1, SentryEnvelope param2) {
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
      // 000: aload 2
      // 001: invokevirtual io/sentry/SentryEnvelope.getItems ()Ljava/lang/Iterable;
      // 004: astore 2
      // 005: aload 2
      // 006: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 00b: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 010: ifeq 0f0
      // 013: aload 2
      // 014: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 019: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 01e: checkcast io/sentry/SentryEnvelopeItem
      // 021: astore 3
      // 022: getstatic io/sentry/SentryItemType.Session Lio/sentry/SentryItemType;
      // 025: aload 3
      // 026: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 029: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 02c: invokevirtual io/sentry/SentryItemType.equals (Ljava/lang/Object;)Z
      // 02f: ifeq 0cd
      // 032: new java/io/BufferedReader
      // 035: astore 2
      // 036: new java/io/InputStreamReader
      // 039: astore 5
      // 03b: new java/io/ByteArrayInputStream
      // 03e: astore 4
      // 040: aload 4
      // 042: aload 3
      // 043: invokevirtual io/sentry/SentryEnvelopeItem.getData ()[B
      // 046: invokespecial java/io/ByteArrayInputStream.<init> ([B)V
      // 049: aload 5
      // 04b: aload 4
      // 04d: getstatic io/sentry/cache/EnvelopeCache.UTF_8 Ljava/nio/charset/Charset;
      // 050: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 053: aload 2
      // 054: aload 5
      // 056: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 059: aload 0
      // 05a: getfield io/sentry/cache/EnvelopeCache.serializer Lio/sentry/util/LazyEvaluator;
      // 05d: invokevirtual io/sentry/util/LazyEvaluator.getValue ()Ljava/lang/Object;
      // 060: checkcast io/sentry/ISerializer
      // 063: aload 2
      // 064: ldc io/sentry/Session
      // 066: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
      // 06b: checkcast io/sentry/Session
      // 06e: astore 4
      // 070: aload 4
      // 072: ifnonnull 098
      // 075: aload 0
      // 076: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 079: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 07c: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 07f: ldc_w "Item of type %s returned null by the parser."
      // 082: bipush 1
      // 083: anewarray 97
      // 086: dup
      // 087: bipush 0
      // 088: aload 3
      // 089: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 08c: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 08f: aastore
      // 090: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 095: goto 09f
      // 098: aload 0
      // 099: aload 1
      // 09a: aload 4
      // 09c: invokespecial io/sentry/cache/EnvelopeCache.writeSessionToDisk (Ljava/io/File;Lio/sentry/Session;)V
      // 09f: aload 2
      // 0a0: invokevirtual java/io/Reader.close ()V
      // 0a3: goto 10d
      // 0a6: astore 1
      // 0a7: aload 2
      // 0a8: invokevirtual java/io/Reader.close ()V
      // 0ab: goto 0b4
      // 0ae: astore 2
      // 0af: aload 1
      // 0b0: aload 2
      // 0b1: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 0b4: aload 1
      // 0b5: athrow
      // 0b6: astore 1
      // 0b7: aload 0
      // 0b8: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 0bb: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0be: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0c1: ldc_w "Item failed to process."
      // 0c4: aload 1
      // 0c5: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 0ca: goto 10d
      // 0cd: aload 0
      // 0ce: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 0d1: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0d4: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 0d7: ldc_w "Current envelope has a different envelope type %s"
      // 0da: bipush 1
      // 0db: anewarray 97
      // 0de: dup
      // 0df: bipush 0
      // 0e0: aload 3
      // 0e1: invokevirtual io/sentry/SentryEnvelopeItem.getHeader ()Lio/sentry/SentryEnvelopeItemHeader;
      // 0e4: invokevirtual io/sentry/SentryEnvelopeItemHeader.getType ()Lio/sentry/SentryItemType;
      // 0e7: aastore
      // 0e8: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 0ed: goto 10d
      // 0f0: aload 0
      // 0f1: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 0f4: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0f7: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 0fa: ldc_w "Current envelope %s is empty"
      // 0fd: bipush 1
      // 0fe: anewarray 97
      // 101: dup
      // 102: bipush 0
      // 103: aload 1
      // 104: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 107: aastore
      // 108: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 10d: return
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void writeCrashMarkerFile() {
      File var2 = new File(this.options.getCacheDirPath(), "last_crash");

      FileOutputStream var1;
      try {
         var1 = new FileOutputStream(var2);
      } catch (Throwable var42) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error writing the crash marker file to the disk", var42);
         return;
      }

      try {
         var1.write(DateUtils.getTimestamp(DateUtils.getCurrentDateTime()).getBytes(UTF_8));
         var1.flush();
      } catch (Throwable var44) {
         Throwable var46 = var44;

         try {
            var1.close();
         } catch (Throwable var43) {
            Throwable var45 = var43;

            label163:
            try {
               var46.addSuppressed(var45);
               break label163;
            } catch (Throwable var41) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error writing the crash marker file to the disk", var41);
               return;
            }
         }

         try {
            throw var46;
         } catch (Throwable var40) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error writing the crash marker file to the disk", var40);
            return;
         }
      }

      try {
         var1.close();
      } catch (Throwable var39) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error writing the crash marker file to the disk", var39);
         return;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void writeEnvelopeToDisk(File var1, SentryEnvelope var2) {
      if (var1.exists()) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Overwriting envelope to offline storage: %s", var1.getAbsolutePath());
         if (!var1.delete()) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to delete: %s", var1.getAbsolutePath());
         }
      }

      FileOutputStream var3;
      try {
         var3 = new FileOutputStream(var1);
      } catch (Throwable var43) {
         this.options.getLogger().log(SentryLevel.ERROR, var43, "Error writing Envelope %s to offline storage", var1.getAbsolutePath());
         return;
      }

      try {
         this.serializer.getValue().serialize(var2, var3);
      } catch (Throwable var45) {
         Throwable var46 = var45;

         try {
            var3.close();
         } catch (Throwable var44) {
            Throwable var47 = var44;

            label178:
            try {
               var46.addSuppressed(var47);
               break label178;
            } catch (Throwable var42) {
               this.options.getLogger().log(SentryLevel.ERROR, var42, "Error writing Envelope %s to offline storage", var1.getAbsolutePath());
               return;
            }
         }

         try {
            throw var46;
         } catch (Throwable var41) {
            this.options.getLogger().log(SentryLevel.ERROR, var41, "Error writing Envelope %s to offline storage", var1.getAbsolutePath());
            return;
         }
      }

      try {
         var3.close();
      } catch (Throwable var40) {
         this.options.getLogger().log(SentryLevel.ERROR, var40, "Error writing Envelope %s to offline storage", var1.getAbsolutePath());
         return;
      }
   }

   private void writeSessionToDisk(File param1, Session param2) {
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
      // 01: invokevirtual java/io/File.exists ()Z
      // 04: ifeq 48
      // 07: aload 0
      // 08: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 0b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0e: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 11: ldc_w "Overwriting session to offline storage: %s"
      // 14: bipush 1
      // 15: anewarray 97
      // 18: dup
      // 19: bipush 0
      // 1a: aload 2
      // 1b: invokevirtual io/sentry/Session.getSessionId ()Ljava/util/UUID;
      // 1e: aastore
      // 1f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 24: aload 1
      // 25: invokevirtual java/io/File.delete ()Z
      // 28: ifne 48
      // 2b: aload 0
      // 2c: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 2f: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 32: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 35: ldc_w "Failed to delete: %s"
      // 38: bipush 1
      // 39: anewarray 97
      // 3c: dup
      // 3d: bipush 0
      // 3e: aload 1
      // 3f: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 42: aastore
      // 43: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 48: new java/io/FileOutputStream
      // 4b: astore 3
      // 4c: aload 3
      // 4d: aload 1
      // 4e: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 51: new java/io/BufferedWriter
      // 54: astore 4
      // 56: new java/io/OutputStreamWriter
      // 59: astore 1
      // 5a: aload 1
      // 5b: aload 3
      // 5c: getstatic io/sentry/cache/EnvelopeCache.UTF_8 Ljava/nio/charset/Charset;
      // 5f: invokespecial java/io/OutputStreamWriter.<init> (Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      // 62: aload 4
      // 64: aload 1
      // 65: invokespecial java/io/BufferedWriter.<init> (Ljava/io/Writer;)V
      // 68: aload 0
      // 69: getfield io/sentry/cache/EnvelopeCache.serializer Lio/sentry/util/LazyEvaluator;
      // 6c: invokevirtual io/sentry/util/LazyEvaluator.getValue ()Ljava/lang/Object;
      // 6f: checkcast io/sentry/ISerializer
      // 72: aload 2
      // 73: aload 4
      // 75: invokeinterface io/sentry/ISerializer.serialize (Ljava/lang/Object;Ljava/io/Writer;)V 3
      // 7a: aload 4
      // 7c: invokevirtual java/io/Writer.close ()V
      // 7f: aload 3
      // 80: invokevirtual java/io/OutputStream.close ()V
      // 83: goto c8
      // 86: astore 1
      // 87: aload 4
      // 89: invokevirtual java/io/Writer.close ()V
      // 8c: goto 97
      // 8f: astore 4
      // 91: aload 1
      // 92: aload 4
      // 94: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 97: aload 1
      // 98: athrow
      // 99: astore 1
      // 9a: aload 3
      // 9b: invokevirtual java/io/OutputStream.close ()V
      // 9e: goto a7
      // a1: astore 3
      // a2: aload 1
      // a3: aload 3
      // a4: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // a7: aload 1
      // a8: athrow
      // a9: astore 1
      // aa: aload 0
      // ab: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // ae: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // b1: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // b4: aload 1
      // b5: ldc_w "Error writing Session to offline storage: %s"
      // b8: bipush 1
      // b9: anewarray 97
      // bc: dup
      // bd: bipush 0
      // be: aload 2
      // bf: invokevirtual io/sentry/Session.getSessionId ()Ljava/util/UUID;
      // c2: aastore
      // c3: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // c8: return
   }

   @Override
   public void discard(SentryEnvelope var1) {
      Objects.requireNonNull(var1, "Envelope is required.");
      File var2 = this.getEnvelopeFile(var1);
      if (var2.exists()) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Discarding envelope from cache: %s", var2.getAbsolutePath());
         if (!var2.delete()) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to delete envelope: %s", var2.getAbsolutePath());
         }
      } else {
         this.options.getLogger().log(SentryLevel.DEBUG, "Envelope was not cached: %s", var2.getAbsolutePath());
      }
   }

   public void flushPreviousSession() {
      this.previousSessionLatch.countDown();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public Iterator<SentryEnvelope> iterator() {
      File[] var5 = this.allEnvelopeFiles();
      ArrayList var4 = new ArrayList(var5.length);

      for (File var3 : var5) {
         BufferedInputStream var6;
         try {
            FileInputStream var7 = new FileInputStream(var3);
            var6 = new BufferedInputStream(var7);
         } catch (FileNotFoundException var36) {
            this.options
               .getLogger()
               .log(SentryLevel.DEBUG, "Envelope file '%s' disappeared while converting all cached files to envelopes.", var3.getAbsolutePath());
            continue;
         } catch (IOException var37) {
            this.options.getLogger().log(SentryLevel.ERROR, String.format("Error while reading cached envelope from file %s", var3.getAbsolutePath()), var37);
            continue;
         }

         try {
            var4.add(this.serializer.getValue().deserializeEnvelope(var6));
         } catch (Throwable var35) {
            Throwable var39 = var35;

            try {
               var6.close();
            } catch (Throwable var34) {
               Throwable var38 = var34;

               label104:
               try {
                  var39.addSuppressed(var38);
                  break label104;
               } catch (FileNotFoundException var32) {
                  this.options
                     .getLogger()
                     .log(SentryLevel.DEBUG, "Envelope file '%s' disappeared while converting all cached files to envelopes.", var3.getAbsolutePath());
                  continue;
               } catch (IOException var33) {
                  this.options
                     .getLogger()
                     .log(SentryLevel.ERROR, String.format("Error while reading cached envelope from file %s", var3.getAbsolutePath()), var33);
                  continue;
               }
            }

            try {
               throw var39;
            } catch (FileNotFoundException var28) {
               this.options
                  .getLogger()
                  .log(SentryLevel.DEBUG, "Envelope file '%s' disappeared while converting all cached files to envelopes.", var3.getAbsolutePath());
               continue;
            } catch (IOException var29) {
               this.options
                  .getLogger()
                  .log(SentryLevel.ERROR, String.format("Error while reading cached envelope from file %s", var3.getAbsolutePath()), var29);
               continue;
            }
         }

         try {
            var6.close();
         } catch (FileNotFoundException var30) {
            this.options
               .getLogger()
               .log(SentryLevel.DEBUG, "Envelope file '%s' disappeared while converting all cached files to envelopes.", var3.getAbsolutePath());
         } catch (IOException var31) {
            this.options.getLogger().log(SentryLevel.ERROR, String.format("Error while reading cached envelope from file %s", var3.getAbsolutePath()), var31);
         }
      }

      return var4.iterator();
   }

   @Override
   public void store(SentryEnvelope param1, Hint param2) {
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
      // 000: aload 1
      // 001: ldc_w "Envelope is required."
      // 004: invokestatic io/sentry/util/Objects.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      // 007: pop
      // 008: aload 0
      // 009: aload 0
      // 00a: invokespecial io/sentry/cache/EnvelopeCache.allEnvelopeFiles ()[Ljava/io/File;
      // 00d: invokevirtual io/sentry/cache/EnvelopeCache.rotateCacheIfNeeded ([Ljava/io/File;)V
      // 010: aload 0
      // 011: getfield io/sentry/cache/EnvelopeCache.directory Ljava/io/File;
      // 014: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 017: invokestatic io/sentry/cache/EnvelopeCache.getCurrentSessionFile (Ljava/lang/String;)Ljava/io/File;
      // 01a: astore 5
      // 01c: aload 0
      // 01d: getfield io/sentry/cache/EnvelopeCache.directory Ljava/io/File;
      // 020: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 023: invokestatic io/sentry/cache/EnvelopeCache.getPreviousSessionFile (Ljava/lang/String;)Ljava/io/File;
      // 026: astore 7
      // 028: aload 2
      // 029: ldc_w io/sentry/hints/SessionEnd
      // 02c: invokestatic io/sentry/util/HintUtils.hasType (Lio/sentry/Hint;Ljava/lang/Class;)Z
      // 02f: ifeq 050
      // 032: aload 5
      // 034: invokevirtual java/io/File.delete ()Z
      // 037: ifne 050
      // 03a: aload 0
      // 03b: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 03e: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 041: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 044: ldc_w "Current envelope doesn't exist."
      // 047: bipush 0
      // 048: anewarray 97
      // 04b: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 050: aload 2
      // 051: ldc io/sentry/hints/AbnormalExit
      // 053: invokestatic io/sentry/util/HintUtils.hasType (Lio/sentry/Hint;Ljava/lang/Class;)Z
      // 056: ifeq 05e
      // 059: aload 0
      // 05a: aload 2
      // 05b: invokespecial io/sentry/cache/EnvelopeCache.tryEndPreviousSession (Lio/sentry/Hint;)V
      // 05e: aload 2
      // 05f: ldc_w io/sentry/hints/SessionStart
      // 062: invokestatic io/sentry/util/HintUtils.hasType (Lio/sentry/Hint;Ljava/lang/Class;)Z
      // 065: ifeq 190
      // 068: aload 5
      // 06a: invokevirtual java/io/File.exists ()Z
      // 06d: ifeq 106
      // 070: aload 0
      // 071: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 074: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 077: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 07a: ldc_w "Current session is not ended, we'd need to end it."
      // 07d: bipush 0
      // 07e: anewarray 97
      // 081: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 086: new java/io/BufferedReader
      // 089: astore 6
      // 08b: new java/io/InputStreamReader
      // 08e: astore 8
      // 090: new java/io/FileInputStream
      // 093: astore 9
      // 095: aload 9
      // 097: aload 5
      // 099: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 09c: aload 8
      // 09e: aload 9
      // 0a0: getstatic io/sentry/cache/EnvelopeCache.UTF_8 Ljava/nio/charset/Charset;
      // 0a3: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 0a6: aload 6
      // 0a8: aload 8
      // 0aa: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 0ad: aload 0
      // 0ae: getfield io/sentry/cache/EnvelopeCache.serializer Lio/sentry/util/LazyEvaluator;
      // 0b1: invokevirtual io/sentry/util/LazyEvaluator.getValue ()Ljava/lang/Object;
      // 0b4: checkcast io/sentry/ISerializer
      // 0b7: aload 6
      // 0b9: ldc io/sentry/Session
      // 0bb: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
      // 0c0: checkcast io/sentry/Session
      // 0c3: astore 8
      // 0c5: aload 8
      // 0c7: ifnull 0d2
      // 0ca: aload 0
      // 0cb: aload 7
      // 0cd: aload 8
      // 0cf: invokespecial io/sentry/cache/EnvelopeCache.writeSessionToDisk (Ljava/io/File;Lio/sentry/Session;)V
      // 0d2: aload 6
      // 0d4: invokevirtual java/io/Reader.close ()V
      // 0d7: goto 106
      // 0da: astore 7
      // 0dc: aload 6
      // 0de: invokevirtual java/io/Reader.close ()V
      // 0e1: goto 0ed
      // 0e4: astore 6
      // 0e6: aload 7
      // 0e8: aload 6
      // 0ea: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 0ed: aload 7
      // 0ef: athrow
      // 0f0: astore 6
      // 0f2: aload 0
      // 0f3: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 0f6: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0f9: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0fc: ldc_w "Error processing session."
      // 0ff: aload 6
      // 101: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 106: aload 0
      // 107: aload 5
      // 109: aload 1
      // 10a: invokespecial io/sentry/cache/EnvelopeCache.updateCurrentSession (Ljava/io/File;Lio/sentry/SentryEnvelope;)V
      // 10d: new java/io/File
      // 110: dup
      // 111: aload 0
      // 112: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 115: invokevirtual io/sentry/SentryOptions.getCacheDirPath ()Ljava/lang/String;
      // 118: ldc ".sentry-native/last_crash"
      // 11a: invokespecial java/io/File.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 11d: invokevirtual java/io/File.exists ()Z
      // 120: istore 4
      // 122: iload 4
      // 124: istore 3
      // 125: iload 4
      // 127: ifne 185
      // 12a: new java/io/File
      // 12d: dup
      // 12e: aload 0
      // 12f: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 132: invokevirtual io/sentry/SentryOptions.getCacheDirPath ()Ljava/lang/String;
      // 135: ldc "last_crash"
      // 137: invokespecial java/io/File.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 13a: astore 5
      // 13c: iload 4
      // 13e: istore 3
      // 13f: aload 5
      // 141: invokevirtual java/io/File.exists ()Z
      // 144: ifeq 185
      // 147: aload 0
      // 148: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 14b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 14e: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 151: ldc_w "Crash marker file exists, crashedLastRun will return true."
      // 154: bipush 0
      // 155: anewarray 97
      // 158: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 15d: aload 5
      // 15f: invokevirtual java/io/File.delete ()Z
      // 162: ifne 183
      // 165: aload 0
      // 166: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 169: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 16c: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 16f: ldc_w "Failed to delete the crash marker file. %s."
      // 172: bipush 1
      // 173: anewarray 97
      // 176: dup
      // 177: bipush 0
      // 178: aload 5
      // 17a: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 17d: aastore
      // 17e: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 183: bipush 1
      // 184: istore 3
      // 185: invokestatic io/sentry/SentryCrashLastRunState.getInstance ()Lio/sentry/SentryCrashLastRunState;
      // 188: iload 3
      // 189: invokevirtual io/sentry/SentryCrashLastRunState.setCrashedLastRun (Z)V
      // 18c: aload 0
      // 18d: invokevirtual io/sentry/cache/EnvelopeCache.flushPreviousSession ()V
      // 190: aload 0
      // 191: aload 1
      // 192: invokespecial io/sentry/cache/EnvelopeCache.getEnvelopeFile (Lio/sentry/SentryEnvelope;)Ljava/io/File;
      // 195: astore 5
      // 197: aload 5
      // 199: invokevirtual java/io/File.exists ()Z
      // 19c: ifeq 1be
      // 19f: aload 0
      // 1a0: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 1a3: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 1a6: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 1a9: ldc_w "Not adding Envelope to offline storage because it already exists: %s"
      // 1ac: bipush 1
      // 1ad: anewarray 97
      // 1b0: dup
      // 1b1: bipush 0
      // 1b2: aload 5
      // 1b4: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 1b7: aastore
      // 1b8: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1bd: return
      // 1be: aload 0
      // 1bf: getfield io/sentry/cache/EnvelopeCache.options Lio/sentry/SentryOptions;
      // 1c2: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 1c5: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 1c8: ldc_w "Adding Envelope to offline storage: %s"
      // 1cb: bipush 1
      // 1cc: anewarray 97
      // 1cf: dup
      // 1d0: bipush 0
      // 1d1: aload 5
      // 1d3: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 1d6: aastore
      // 1d7: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1dc: aload 0
      // 1dd: aload 5
      // 1df: aload 1
      // 1e0: invokespecial io/sentry/cache/EnvelopeCache.writeEnvelopeToDisk (Ljava/io/File;Lio/sentry/SentryEnvelope;)V
      // 1e3: aload 2
      // 1e4: ldc_w io/sentry/UncaughtExceptionHandlerIntegration$UncaughtExceptionHint
      // 1e7: invokestatic io/sentry/util/HintUtils.hasType (Lio/sentry/Hint;Ljava/lang/Class;)Z
      // 1ea: ifeq 1f1
      // 1ed: aload 0
      // 1ee: invokespecial io/sentry/cache/EnvelopeCache.writeCrashMarkerFile ()V
      // 1f1: return
   }

   public boolean waitPreviousSessionFlush() {
      try {
         return this.previousSessionLatch.await(this.options.getSessionFlushTimeoutMillis(), TimeUnit.MILLISECONDS);
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         this.options.getLogger().log(SentryLevel.DEBUG, "Timed out waiting for previous session to flush.");
         return false;
      }
   }
}
