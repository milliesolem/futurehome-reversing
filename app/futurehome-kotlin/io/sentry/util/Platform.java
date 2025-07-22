package io.sentry.util;

public final class Platform {
   static boolean isAndroid;
   static boolean isJavaNinePlus;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: ldc "The Android Project"
      // 02: ldc "java.vendor"
      // 04: invokestatic java/lang/System.getProperty (Ljava/lang/String;)Ljava/lang/String;
      // 07: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 0a: putstatic io/sentry/util/Platform.isAndroid Z
      // 0d: goto 15
      // 10: astore 1
      // 11: bipush 0
      // 12: putstatic io/sentry/util/Platform.isAndroid Z
      // 15: ldc "java.specification.version"
      // 17: invokestatic java/lang/System.getProperty (Ljava/lang/String;)Ljava/lang/String;
      // 1a: astore 1
      // 1b: aload 1
      // 1c: ifnull 3b
      // 1f: aload 1
      // 20: invokestatic java/lang/Double.valueOf (Ljava/lang/String;)Ljava/lang/Double;
      // 23: invokevirtual java/lang/Double.doubleValue ()D
      // 26: ldc2_w 9.0
      // 29: dcmpl
      // 2a: iflt 32
      // 2d: bipush 1
      // 2e: istore 0
      // 2f: goto 34
      // 32: bipush 0
      // 33: istore 0
      // 34: iload 0
      // 35: putstatic io/sentry/util/Platform.isJavaNinePlus Z
      // 38: goto 47
      // 3b: bipush 0
      // 3c: putstatic io/sentry/util/Platform.isJavaNinePlus Z
      // 3f: goto 47
      // 42: astore 1
      // 43: bipush 0
      // 44: putstatic io/sentry/util/Platform.isJavaNinePlus Z
      // 47: return
   }

   public static boolean isAndroid() {
      return isAndroid;
   }

   public static boolean isJavaNinePlus() {
      return isJavaNinePlus;
   }

   public static boolean isJvm() {
      return isAndroid ^ true;
   }
}
