package io.sentry.cache;

import io.sentry.JsonDeserializer;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import java.io.File;
import java.nio.charset.Charset;

final class CacheUtils {
   private static final Charset UTF_8 = Charset.forName("UTF-8");

   static void delete(SentryOptions var0, String var1, String var2) {
      File var3 = ensureCacheDir(var0, var1);
      if (var3 == null) {
         var0.getLogger().log(SentryLevel.INFO, "Cache dir is not set, cannot delete from scope cache");
      } else {
         File var4 = new File(var3, var2);
         if (var4.exists()) {
            var0.getLogger().log(SentryLevel.DEBUG, "Deleting %s from scope cache", var2);
            if (!var4.delete()) {
               var0.getLogger().log(SentryLevel.ERROR, "Failed to delete: %s", var4.getAbsolutePath());
            }
         }
      }
   }

   private static File ensureCacheDir(SentryOptions var0, String var1) {
      String var2 = var0.getCacheDirPath();
      if (var2 == null) {
         return null;
      } else {
         File var3 = new File(var2, var1);
         var3.mkdirs();
         return var3;
      }
   }

   static <T, R> T read(SentryOptions param0, String param1, String param2, Class<T> param3, JsonDeserializer<R> param4) {
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
      // 01: aload 1
      // 02: invokestatic io/sentry/cache/CacheUtils.ensureCacheDir (Lio/sentry/SentryOptions;Ljava/lang/String;)Ljava/io/File;
      // 05: astore 1
      // 06: aload 1
      // 07: ifnonnull 1e
      // 0a: aload 0
      // 0b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0e: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 11: ldc "Cache dir is not set, cannot read from scope cache"
      // 13: bipush 0
      // 14: anewarray 4
      // 17: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1c: aconst_null
      // 1d: areturn
      // 1e: new java/io/File
      // 21: dup
      // 22: aload 1
      // 23: aload 2
      // 24: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 27: astore 6
      // 29: aload 6
      // 2b: invokevirtual java/io/File.exists ()Z
      // 2e: ifeq ac
      // 31: new java/io/BufferedReader
      // 34: astore 1
      // 35: new java/io/InputStreamReader
      // 38: astore 7
      // 3a: new java/io/FileInputStream
      // 3d: astore 5
      // 3f: aload 5
      // 41: aload 6
      // 43: invokespecial java/io/FileInputStream.<init> (Ljava/io/File;)V
      // 46: aload 7
      // 48: aload 5
      // 4a: getstatic io/sentry/cache/CacheUtils.UTF_8 Ljava/nio/charset/Charset;
      // 4d: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 50: aload 1
      // 51: aload 7
      // 53: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 56: aload 4
      // 58: ifnonnull 6d
      // 5b: aload 0
      // 5c: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 5f: aload 1
      // 60: aload 3
      // 61: invokeinterface io/sentry/ISerializer.deserialize (Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object; 3
      // 66: astore 3
      // 67: aload 1
      // 68: invokevirtual java/io/Reader.close ()V
      // 6b: aload 3
      // 6c: areturn
      // 6d: aload 0
      // 6e: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 71: aload 1
      // 72: aload 3
      // 73: aload 4
      // 75: invokeinterface io/sentry/ISerializer.deserializeCollection (Ljava/io/Reader;Ljava/lang/Class;Lio/sentry/JsonDeserializer;)Ljava/lang/Object; 4
      // 7a: astore 3
      // 7b: aload 1
      // 7c: invokevirtual java/io/Reader.close ()V
      // 7f: aload 3
      // 80: areturn
      // 81: astore 3
      // 82: aload 1
      // 83: invokevirtual java/io/Reader.close ()V
      // 86: goto 8f
      // 89: astore 1
      // 8a: aload 3
      // 8b: aload 1
      // 8c: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 8f: aload 3
      // 90: athrow
      // 91: astore 1
      // 92: aload 0
      // 93: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 96: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 99: aload 1
      // 9a: ldc "Error reading entity from scope cache: %s"
      // 9c: bipush 1
      // 9d: anewarray 4
      // a0: dup
      // a1: bipush 0
      // a2: aload 2
      // a3: aastore
      // a4: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // a9: goto c2
      // ac: aload 0
      // ad: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // b0: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // b3: ldc "No entry stored for %s"
      // b5: bipush 1
      // b6: anewarray 4
      // b9: dup
      // ba: bipush 0
      // bb: aload 2
      // bc: aastore
      // bd: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // c2: aconst_null
      // c3: areturn
   }

   static <T> void store(SentryOptions param0, T param1, String param2, String param3) {
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
      // 01: aload 2
      // 02: invokestatic io/sentry/cache/CacheUtils.ensureCacheDir (Lio/sentry/SentryOptions;Ljava/lang/String;)Ljava/io/File;
      // 05: astore 2
      // 06: aload 2
      // 07: ifnonnull 1d
      // 0a: aload 0
      // 0b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0e: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 11: ldc "Cache dir is not set, cannot store in scope cache"
      // 13: bipush 0
      // 14: anewarray 4
      // 17: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1c: return
      // 1d: new java/io/File
      // 20: dup
      // 21: aload 2
      // 22: aload 3
      // 23: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 26: astore 4
      // 28: aload 4
      // 2a: invokevirtual java/io/File.exists ()Z
      // 2d: ifeq 68
      // 30: aload 0
      // 31: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 34: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 37: ldc "Overwriting %s in scope cache"
      // 39: bipush 1
      // 3a: anewarray 4
      // 3d: dup
      // 3e: bipush 0
      // 3f: aload 3
      // 40: aastore
      // 41: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 46: aload 4
      // 48: invokevirtual java/io/File.delete ()Z
      // 4b: ifne 68
      // 4e: aload 0
      // 4f: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 52: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 55: ldc "Failed to delete: %s"
      // 57: bipush 1
      // 58: anewarray 4
      // 5b: dup
      // 5c: bipush 0
      // 5d: aload 4
      // 5f: invokevirtual java/io/File.getAbsolutePath ()Ljava/lang/String;
      // 62: aastore
      // 63: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 68: new java/io/FileOutputStream
      // 6b: astore 2
      // 6c: aload 2
      // 6d: aload 4
      // 6f: invokespecial java/io/FileOutputStream.<init> (Ljava/io/File;)V
      // 72: new java/io/BufferedWriter
      // 75: astore 4
      // 77: new java/io/OutputStreamWriter
      // 7a: astore 5
      // 7c: aload 5
      // 7e: aload 2
      // 7f: getstatic io/sentry/cache/CacheUtils.UTF_8 Ljava/nio/charset/Charset;
      // 82: invokespecial java/io/OutputStreamWriter.<init> (Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      // 85: aload 4
      // 87: aload 5
      // 89: invokespecial java/io/BufferedWriter.<init> (Ljava/io/Writer;)V
      // 8c: aload 0
      // 8d: invokevirtual io/sentry/SentryOptions.getSerializer ()Lio/sentry/ISerializer;
      // 90: aload 1
      // 91: aload 4
      // 93: invokeinterface io/sentry/ISerializer.serialize (Ljava/lang/Object;Ljava/io/Writer;)V 3
      // 98: aload 4
      // 9a: invokevirtual java/io/Writer.close ()V
      // 9d: aload 2
      // 9e: invokevirtual java/io/OutputStream.close ()V
      // a1: goto df
      // a4: astore 1
      // a5: aload 4
      // a7: invokevirtual java/io/Writer.close ()V
      // aa: goto b5
      // ad: astore 4
      // af: aload 1
      // b0: aload 4
      // b2: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // b5: aload 1
      // b6: athrow
      // b7: astore 1
      // b8: aload 2
      // b9: invokevirtual java/io/OutputStream.close ()V
      // bc: goto c5
      // bf: astore 2
      // c0: aload 1
      // c1: aload 2
      // c2: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // c5: aload 1
      // c6: athrow
      // c7: astore 1
      // c8: aload 0
      // c9: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // cc: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // cf: aload 1
      // d0: ldc "Error persisting entity: %s"
      // d2: bipush 1
      // d3: anewarray 4
      // d6: dup
      // d7: bipush 0
      // d8: aload 3
      // d9: aastore
      // da: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // df: return
   }
}
