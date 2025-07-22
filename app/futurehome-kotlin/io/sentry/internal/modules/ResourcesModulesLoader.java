package io.sentry.internal.modules;

import io.sentry.ILogger;
import io.sentry.util.ClassLoaderUtils;
import java.util.Map;

public final class ResourcesModulesLoader extends ModulesLoader {
   private final ClassLoader classLoader;

   public ResourcesModulesLoader(ILogger var1) {
      this(var1, ResourcesModulesLoader.class.getClassLoader());
   }

   ResourcesModulesLoader(ILogger var1, ClassLoader var2) {
      super(var1);
      this.classLoader = ClassLoaderUtils.classLoaderOrDefault(var2);
   }

   @Override
   protected Map<String, String> loadModules() {
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
      // 07: astore 1
      // 08: aload 0
      // 09: getfield io/sentry/internal/modules/ResourcesModulesLoader.classLoader Ljava/lang/ClassLoader;
      // 0c: ldc "sentry-external-modules.txt"
      // 0e: invokevirtual java/lang/ClassLoader.getResourceAsStream (Ljava/lang/String;)Ljava/io/InputStream;
      // 11: astore 2
      // 12: aload 2
      // 13: ifnonnull 37
      // 16: aload 0
      // 17: getfield io/sentry/internal/modules/ResourcesModulesLoader.logger Lio/sentry/ILogger;
      // 1a: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 1d: ldc "%s file was not found."
      // 1f: bipush 1
      // 20: anewarray 62
      // 23: dup
      // 24: bipush 0
      // 25: ldc "sentry-external-modules.txt"
      // 27: aastore
      // 28: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 2d: aload 2
      // 2e: ifnull 35
      // 31: aload 2
      // 32: invokevirtual java/io/InputStream.close ()V
      // 35: aload 1
      // 36: areturn
      // 37: aload 0
      // 38: aload 2
      // 39: invokevirtual io/sentry/internal/modules/ResourcesModulesLoader.parseStream (Ljava/io/InputStream;)Ljava/util/Map;
      // 3c: astore 3
      // 3d: aload 2
      // 3e: ifnull 45
      // 41: aload 2
      // 42: invokevirtual java/io/InputStream.close ()V
      // 45: aload 3
      // 46: areturn
      // 47: astore 3
      // 48: aload 2
      // 49: ifnull 59
      // 4c: aload 2
      // 4d: invokevirtual java/io/InputStream.close ()V
      // 50: goto 59
      // 53: astore 2
      // 54: aload 3
      // 55: aload 2
      // 56: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 59: aload 3
      // 5a: athrow
      // 5b: astore 2
      // 5c: aload 0
      // 5d: getfield io/sentry/internal/modules/ResourcesModulesLoader.logger Lio/sentry/ILogger;
      // 60: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 63: ldc "Access to resources failed."
      // 65: aload 2
      // 66: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 6b: goto 7e
      // 6e: astore 2
      // 6f: aload 0
      // 70: getfield io/sentry/internal/modules/ResourcesModulesLoader.logger Lio/sentry/ILogger;
      // 73: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 76: ldc "Access to resources denied."
      // 78: aload 2
      // 79: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 7e: aload 1
      // 7f: areturn
   }
}
