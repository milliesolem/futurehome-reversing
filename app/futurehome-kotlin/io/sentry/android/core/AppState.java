package io.sentry.android.core;

public final class AppState {
   private static AppState instance = new AppState();
   private Boolean inBackground = null;

   private AppState() {
   }

   public static AppState getInstance() {
      return instance;
   }

   public Boolean isInBackground() {
      return this.inBackground;
   }

   void resetInstance() {
      instance = new AppState();
   }

   void setInBackground(boolean param1) {
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
      // 03: iload 1
      // 04: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 07: putfield io/sentry/android/core/AppState.inBackground Ljava/lang/Boolean;
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: return
      // 0d: astore 2
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 2
      // 11: athrow
   }
}
