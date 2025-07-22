package io.sentry.internal.debugmeta;

import io.sentry.ILogger;
import io.sentry.util.ClassLoaderUtils;
import java.util.List;
import java.util.Properties;

public final class ResourcesDebugMetaLoader implements IDebugMetaLoader {
   private final ClassLoader classLoader;
   private final ILogger logger;

   public ResourcesDebugMetaLoader(ILogger var1) {
      this(var1, ResourcesDebugMetaLoader.class.getClassLoader());
   }

   ResourcesDebugMetaLoader(ILogger var1, ClassLoader var2) {
      this.logger = var1;
      this.classLoader = ClassLoaderUtils.classLoaderOrDefault(var2);
   }

   @Override
   public List<Properties> loadDebugMeta() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: new java/util/ArrayList
      // 03: dup
      // 04: invokespecial java/util/ArrayList.<init> ()V
      // 07: astore 2
      // 08: aload 0
      // 09: getfield io/sentry/internal/debugmeta/ResourcesDebugMetaLoader.classLoader Ljava/lang/ClassLoader;
      // 0c: getstatic io/sentry/util/DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME Ljava/lang/String;
      // 0f: invokevirtual java/lang/ClassLoader.getResources (Ljava/lang/String;)Ljava/util/Enumeration;
      // 12: astore 1
      // 13: aload 1
      // 14: invokeinterface java/util/Enumeration.hasMoreElements ()Z 1
      // 19: ifeq bb
      // 1c: aload 1
      // 1d: invokeinterface java/util/Enumeration.nextElement ()Ljava/lang/Object; 1
      // 22: checkcast java/net/URL
      // 25: astore 3
      // 26: aload 3
      // 27: invokevirtual java/net/URL.openStream ()Ljava/io/InputStream;
      // 2a: astore 5
      // 2c: new java/util/Properties
      // 2f: astore 4
      // 31: aload 4
      // 33: invokespecial java/util/Properties.<init> ()V
      // 36: aload 4
      // 38: aload 5
      // 3a: invokevirtual java/util/Properties.load (Ljava/io/InputStream;)V
      // 3d: aload 2
      // 3e: aload 4
      // 40: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 45: pop
      // 46: aload 0
      // 47: getfield io/sentry/internal/debugmeta/ResourcesDebugMetaLoader.logger Lio/sentry/ILogger;
      // 4a: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 4d: ldc "Debug Meta Data Properties loaded from %s"
      // 4f: bipush 1
      // 50: anewarray 4
      // 53: dup
      // 54: bipush 0
      // 55: aload 3
      // 56: aastore
      // 57: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 5c: aload 5
      // 5e: ifnull 13
      // 61: aload 5
      // 63: invokevirtual java/io/InputStream.close ()V
      // 66: goto 13
      // 69: astore 4
      // 6b: aload 5
      // 6d: ifnull 81
      // 70: aload 5
      // 72: invokevirtual java/io/InputStream.close ()V
      // 75: goto 81
      // 78: astore 5
      // 7a: aload 4
      // 7c: aload 5
      // 7e: invokevirtual java/lang/Throwable.addSuppressed (Ljava/lang/Throwable;)V
      // 81: aload 4
      // 83: athrow
      // 84: astore 4
      // 86: aload 0
      // 87: getfield io/sentry/internal/debugmeta/ResourcesDebugMetaLoader.logger Lio/sentry/ILogger;
      // 8a: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 8d: aload 4
      // 8f: ldc "%s file is malformed."
      // 91: bipush 1
      // 92: anewarray 4
      // 95: dup
      // 96: bipush 0
      // 97: aload 3
      // 98: aastore
      // 99: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // 9e: goto 13
      // a1: astore 1
      // a2: aload 0
      // a3: getfield io/sentry/internal/debugmeta/ResourcesDebugMetaLoader.logger Lio/sentry/ILogger;
      // a6: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // a9: aload 1
      // aa: ldc "Failed to load %s"
      // ac: bipush 1
      // ad: anewarray 4
      // b0: dup
      // b1: bipush 0
      // b2: getstatic io/sentry/util/DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME Ljava/lang/String;
      // b5: aastore
      // b6: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
      // bb: aload 2
      // bc: astore 1
      // bd: aload 2
      // be: invokeinterface java/util/List.isEmpty ()Z 1
      // c3: ifeq e0
      // c6: aload 0
      // c7: getfield io/sentry/internal/debugmeta/ResourcesDebugMetaLoader.logger Lio/sentry/ILogger;
      // ca: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // cd: ldc "No %s file was found."
      // cf: bipush 1
      // d0: anewarray 4
      // d3: dup
      // d4: bipush 0
      // d5: getstatic io/sentry/util/DebugMetaPropertiesApplier.DEBUG_META_PROPERTIES_FILENAME Ljava/lang/String;
      // d8: aastore
      // d9: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // de: aconst_null
      // df: astore 1
      // e0: aload 1
      // e1: areturn
   }
}
