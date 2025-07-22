package io.sentry;

import j..util.concurrent.ConcurrentHashMap;
import java.util.Map;

public final class SentrySpanStorage {
   private static volatile SentrySpanStorage INSTANCE;
   private final Map<String, ISpan> spans = new ConcurrentHashMap();

   private SentrySpanStorage() {
   }

   public static SentrySpanStorage getInstance() {
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
      // 00: getstatic io/sentry/SentrySpanStorage.INSTANCE Lio/sentry/SentrySpanStorage;
      // 03: ifnonnull 27
      // 06: ldc io/sentry/SentrySpanStorage
      // 08: monitorenter
      // 09: getstatic io/sentry/SentrySpanStorage.INSTANCE Lio/sentry/SentrySpanStorage;
      // 0c: ifnonnull 1b
      // 0f: new io/sentry/SentrySpanStorage
      // 12: astore 0
      // 13: aload 0
      // 14: invokespecial io/sentry/SentrySpanStorage.<init> ()V
      // 17: aload 0
      // 18: putstatic io/sentry/SentrySpanStorage.INSTANCE Lio/sentry/SentrySpanStorage;
      // 1b: ldc io/sentry/SentrySpanStorage
      // 1d: monitorexit
      // 1e: goto 27
      // 21: astore 0
      // 22: ldc io/sentry/SentrySpanStorage
      // 24: monitorexit
      // 25: aload 0
      // 26: athrow
      // 27: getstatic io/sentry/SentrySpanStorage.INSTANCE Lio/sentry/SentrySpanStorage;
      // 2a: areturn
   }

   public ISpan get(String var1) {
      return this.spans.get(var1);
   }

   public ISpan removeAndGet(String var1) {
      return this.spans.remove(var1);
   }

   public void store(String var1, ISpan var2) {
      this.spans.put(var1, var2);
   }
}
