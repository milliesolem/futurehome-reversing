package io.sentry;

import io.sentry.protocol.SentryPackage;
import io.sentry.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class SentryIntegrationPackageStorage {
   private static volatile SentryIntegrationPackageStorage INSTANCE;
   private final Set<String> integrations = new CopyOnWriteArraySet<>();
   private final Set<SentryPackage> packages = new CopyOnWriteArraySet<>();

   private SentryIntegrationPackageStorage() {
   }

   public static SentryIntegrationPackageStorage getInstance() {
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
      // 00: getstatic io/sentry/SentryIntegrationPackageStorage.INSTANCE Lio/sentry/SentryIntegrationPackageStorage;
      // 03: ifnonnull 27
      // 06: ldc io/sentry/SentryIntegrationPackageStorage
      // 08: monitorenter
      // 09: getstatic io/sentry/SentryIntegrationPackageStorage.INSTANCE Lio/sentry/SentryIntegrationPackageStorage;
      // 0c: ifnonnull 1b
      // 0f: new io/sentry/SentryIntegrationPackageStorage
      // 12: astore 0
      // 13: aload 0
      // 14: invokespecial io/sentry/SentryIntegrationPackageStorage.<init> ()V
      // 17: aload 0
      // 18: putstatic io/sentry/SentryIntegrationPackageStorage.INSTANCE Lio/sentry/SentryIntegrationPackageStorage;
      // 1b: ldc io/sentry/SentryIntegrationPackageStorage
      // 1d: monitorexit
      // 1e: goto 27
      // 21: astore 0
      // 22: ldc io/sentry/SentryIntegrationPackageStorage
      // 24: monitorexit
      // 25: aload 0
      // 26: athrow
      // 27: getstatic io/sentry/SentryIntegrationPackageStorage.INSTANCE Lio/sentry/SentryIntegrationPackageStorage;
      // 2a: areturn
   }

   public void addIntegration(String var1) {
      Objects.requireNonNull(var1, "integration is required.");
      this.integrations.add(var1);
   }

   public void addPackage(String var1, String var2) {
      Objects.requireNonNull(var1, "name is required.");
      Objects.requireNonNull(var2, "version is required.");
      SentryPackage var3 = new SentryPackage(var1, var2);
      this.packages.add(var3);
   }

   public void clearStorage() {
      this.integrations.clear();
      this.packages.clear();
   }

   public Set<String> getIntegrations() {
      return this.integrations;
   }

   public Set<SentryPackage> getPackages() {
      return this.packages;
   }
}
