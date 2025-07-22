package io.sentry.config;

import io.sentry.ILogger;
import io.sentry.util.ClassLoaderUtils;
import java.util.Properties;

final class ClasspathPropertiesLoader implements PropertiesLoader {
   private final ClassLoader classLoader;
   private final String fileName;
   private final ILogger logger;

   public ClasspathPropertiesLoader(ILogger var1) {
      this("sentry.properties", ClasspathPropertiesLoader.class.getClassLoader(), var1);
   }

   public ClasspathPropertiesLoader(String var1, ClassLoader var2, ILogger var3) {
      this.fileName = var1;
      this.classLoader = ClassLoaderUtils.classLoaderOrDefault(var2);
      this.logger = var3;
   }

   @Override
   public Properties load() {
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
      // 01: getfield io/sentry/config/ClasspathPropertiesLoader.classLoader Ljava/lang/ClassLoader;
      // 04: aload 0
      // 05: getfield io/sentry/config/ClasspathPropertiesLoader.fileName Ljava/lang/String;
      // 08: invokevirtual java/lang/ClassLoader.getResourceAsStream (Ljava/lang/String;)Ljava/io/InputStream;
      // 0b: astore 1
      // 0c: aload 1
      // 0d: ifnull 58
      // 10: new java/io/BufferedInputStream
      // 13: astore 2
      // 14: aload 2
      // 15: aload 1
      // 16: invokespecial java/io/BufferedInputStream.<init> (Ljava/io/InputStream;)V
      // 19: new java/util/Properties
      // 1c: astore 3
      // 1d: aload 3
      // 1e: invokespecial java/util/Properties.<init> ()V
      // 21: aload 3
      // 22: aload 2
      // 23: invokevirtual java/util/Properties.load (Ljava/io/InputStream;)V
      // 26: aload 2
      // 27: invokevirtual java/io/BufferedInputStream.close ()V
      // 2a: aload 1
      // 2b: ifnull 32
      // 2e: aload 1
      // 2f: invokevirtual java/io/InputStream.close ()V
      // 32: aload 3
      // 33: areturn
      // 34: astore 3
      // 35: aload 2
      // 36: invokevirtual java/io/BufferedInputStream.close ()V
      // 39: goto 42
      // 3c: astore 2
      // 3d: aload 3
      // 3e: aload 2
      // 3f: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 42: aload 3
      // 43: athrow
      // 44: astore 2
      // 45: aload 1
      // 46: ifnull 56
      // 49: aload 1
      // 4a: invokevirtual java/io/InputStream.close ()V
      // 4d: goto 56
      // 50: astore 1
      // 51: aload 2
      // 52: aload 1
      // 53: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 56: aload 2
      // 57: athrow
      // 58: aload 1
      // 59: ifnull 60
      // 5c: aload 1
      // 5d: invokevirtual java/io/InputStream.close ()V
      // 60: aconst_null
      // 61: areturn
      // 62: astore 1
      // 63: aload 0
      // 64: getfield io/sentry/config/ClasspathPropertiesLoader.logger Lio/sentry/ILogger;
      // 67: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 6a: aload 1
      // 6b: ldc "Failed to load Sentry configuration from classpath resource: %s"
      // 6d: bipush 1
      // 6e: anewarray 4
      // 71: dup
      // 72: bipush 0
      // 73: aload 0
      // 74: getfield io/sentry/config/ClasspathPropertiesLoader.fileName Ljava/lang/String;
      // 77: aastore
      // 78: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 7d: aconst_null
      // 7e: areturn
   }
}
