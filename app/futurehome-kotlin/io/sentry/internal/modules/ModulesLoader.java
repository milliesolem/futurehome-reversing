package io.sentry.internal.modules;

import io.sentry.ILogger;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

public abstract class ModulesLoader implements IModulesLoader {
   public static final String EXTERNAL_MODULES_FILENAME = "sentry-external-modules.txt";
   private static final Charset UTF_8 = Charset.forName("UTF-8");
   private Map<String, String> cachedModules = null;
   protected final ILogger logger;

   public ModulesLoader(ILogger var1) {
      this.logger = var1;
   }

   @Override
   public Map<String, String> getOrLoadModules() {
      Map var1 = this.cachedModules;
      if (var1 != null) {
         return var1;
      } else {
         var1 = this.loadModules();
         this.cachedModules = var1;
         return var1;
      }
   }

   protected abstract Map<String, String> loadModules();

   protected Map<String, String> parseStream(InputStream param1) {
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
      // 00: new java/util/TreeMap
      // 03: dup
      // 04: invokespecial java/util/TreeMap.<init> ()V
      // 07: astore 3
      // 08: new java/io/BufferedReader
      // 0b: astore 4
      // 0d: new java/io/InputStreamReader
      // 10: astore 5
      // 12: aload 5
      // 14: aload 1
      // 15: getstatic io/sentry/internal/modules/ModulesLoader.UTF_8 Ljava/nio/charset/Charset;
      // 18: invokespecial java/io/InputStreamReader.<init> (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      // 1b: aload 4
      // 1d: aload 5
      // 1f: invokespecial java/io/BufferedReader.<init> (Ljava/io/Reader;)V
      // 22: aload 4
      // 24: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 27: astore 1
      // 28: aload 1
      // 29: ifnull 50
      // 2c: aload 1
      // 2d: bipush 58
      // 2f: invokevirtual java/lang/String.lastIndexOf (I)I
      // 32: istore 2
      // 33: aload 3
      // 34: aload 1
      // 35: bipush 0
      // 36: iload 2
      // 37: invokevirtual java/lang/String.substring (II)Ljava/lang/String;
      // 3a: aload 1
      // 3b: iload 2
      // 3c: bipush 1
      // 3d: iadd
      // 3e: invokevirtual java/lang/String.substring (I)Ljava/lang/String;
      // 41: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 46: pop
      // 47: aload 4
      // 49: invokevirtual java/io/BufferedReader.readLine ()Ljava/lang/String;
      // 4c: astore 1
      // 4d: goto 28
      // 50: aload 0
      // 51: getfield io/sentry/internal/modules/ModulesLoader.logger Lio/sentry/ILogger;
      // 54: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 57: ldc "Extracted %d modules from resources."
      // 59: bipush 1
      // 5a: anewarray 4
      // 5d: dup
      // 5e: bipush 0
      // 5f: aload 3
      // 60: invokeinterface java/util/Map.size ()I 1
      // 65: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 68: aastore
      // 69: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 6e: aload 4
      // 70: invokevirtual java/io/BufferedReader.close ()V
      // 73: goto b5
      // 76: astore 1
      // 77: aload 4
      // 79: invokevirtual java/io/BufferedReader.close ()V
      // 7c: goto 87
      // 7f: astore 4
      // 81: aload 1
      // 82: aload 4
      // 84: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 87: aload 1
      // 88: athrow
      // 89: astore 1
      // 8a: aload 0
      // 8b: getfield io/sentry/internal/modules/ModulesLoader.logger Lio/sentry/ILogger;
      // 8e: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 91: aload 1
      // 92: ldc "%s file is malformed."
      // 94: bipush 1
      // 95: anewarray 4
      // 98: dup
      // 99: bipush 0
      // 9a: ldc "sentry-external-modules.txt"
      // 9c: aastore
      // 9d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // a2: goto b5
      // a5: astore 1
      // a6: aload 0
      // a7: getfield io/sentry/internal/modules/ModulesLoader.logger Lio/sentry/ILogger;
      // aa: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // ad: ldc "Error extracting modules."
      // af: aload 1
      // b0: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // b5: aload 3
      // b6: areturn
   }
}
