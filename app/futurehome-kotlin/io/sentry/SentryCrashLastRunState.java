package io.sentry;

public final class SentryCrashLastRunState {
   private static final SentryCrashLastRunState INSTANCE = new SentryCrashLastRunState();
   private Boolean crashedLastRun;
   private final Object crashedLastRunLock = new Object();
   private boolean readCrashedLastRun;

   private SentryCrashLastRunState() {
   }

   public static SentryCrashLastRunState getInstance() {
      return INSTANCE;
   }

   public Boolean isCrashedLastRun(String param1, boolean param2) {
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
      // 01: getfield io/sentry/SentryCrashLastRunState.crashedLastRunLock Ljava/lang/Object;
      // 04: astore 5
      // 06: aload 5
      // 08: monitorenter
      // 09: aload 0
      // 0a: getfield io/sentry/SentryCrashLastRunState.readCrashedLastRun Z
      // 0d: ifeq 1a
      // 10: aload 0
      // 11: getfield io/sentry/SentryCrashLastRunState.crashedLastRun Ljava/lang/Boolean;
      // 14: astore 1
      // 15: aload 5
      // 17: monitorexit
      // 18: aload 1
      // 19: areturn
      // 1a: aload 1
      // 1b: ifnonnull 23
      // 1e: aload 5
      // 20: monitorexit
      // 21: aconst_null
      // 22: areturn
      // 23: bipush 1
      // 24: istore 4
      // 26: aload 0
      // 27: bipush 1
      // 28: putfield io/sentry/SentryCrashLastRunState.readCrashedLastRun Z
      // 2b: new java/io/File
      // 2e: astore 6
      // 30: aload 6
      // 32: aload 1
      // 33: ldc "last_crash"
      // 35: invokespecial java/io/File.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 38: new java/io/File
      // 3b: astore 7
      // 3d: aload 7
      // 3f: aload 1
      // 40: ldc ".sentry-native/last_crash"
      // 42: invokespecial java/io/File.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 45: aload 6
      // 47: invokevirtual java/io/File.exists ()Z
      // 4a: istore 3
      // 4b: iload 3
      // 4c: ifeq 5b
      // 4f: aload 6
      // 51: invokevirtual java/io/File.delete ()Z
      // 54: pop
      // 55: iload 4
      // 57: istore 3
      // 58: goto 7a
      // 5b: aload 7
      // 5d: invokevirtual java/io/File.exists ()Z
      // 60: istore 3
      // 61: iload 3
      // 62: ifeq 78
      // 65: iload 4
      // 67: istore 3
      // 68: iload 2
      // 69: ifeq 7a
      // 6c: aload 7
      // 6e: invokevirtual java/io/File.delete ()Z
      // 71: pop
      // 72: iload 4
      // 74: istore 3
      // 75: goto 7a
      // 78: bipush 0
      // 79: istore 3
      // 7a: iload 3
      // 7b: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 7e: astore 1
      // 7f: aload 0
      // 80: aload 1
      // 81: putfield io/sentry/SentryCrashLastRunState.crashedLastRun Ljava/lang/Boolean;
      // 84: aload 5
      // 86: monitorexit
      // 87: aload 1
      // 88: areturn
      // 89: astore 1
      // 8a: aload 5
      // 8c: monitorexit
      // 8d: aload 1
      // 8e: athrow
      // 8f: astore 1
      // 90: goto 78
      // 93: astore 1
      // 94: iload 4
      // 96: istore 3
      // 97: goto 7a
   }

   public void reset() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SentryCrashLastRunState.crashedLastRunLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: bipush 0
      // 09: putfield io/sentry/SentryCrashLastRunState.readCrashedLastRun Z
      // 0c: aload 0
      // 0d: aconst_null
      // 0e: putfield io/sentry/SentryCrashLastRunState.crashedLastRun Ljava/lang/Boolean;
      // 11: aload 1
      // 12: monitorexit
      // 13: return
      // 14: astore 2
      // 15: aload 1
      // 16: monitorexit
      // 17: aload 2
      // 18: athrow
   }

   public void setCrashedLastRun(boolean param1) {
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
      // 01: getfield io/sentry/SentryCrashLastRunState.crashedLastRunLock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/SentryCrashLastRunState.readCrashedLastRun Z
      // 0b: ifne 1b
      // 0e: aload 0
      // 0f: iload 1
      // 10: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 13: putfield io/sentry/SentryCrashLastRunState.crashedLastRun Ljava/lang/Boolean;
      // 16: aload 0
      // 17: bipush 1
      // 18: putfield io/sentry/SentryCrashLastRunState.readCrashedLastRun Z
      // 1b: aload 3
      // 1c: monitorexit
      // 1d: return
      // 1e: astore 2
      // 1f: aload 3
      // 20: monitorexit
      // 21: aload 2
      // 22: athrow
   }
}
