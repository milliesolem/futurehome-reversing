package io.sentry.android.core.internal.util;

import java.util.ArrayList;
import java.util.List;

public final class CpuInfoUtils {
   static final String CPUINFO_MAX_FREQ_PATH = "cpufreq/cpuinfo_max_freq";
   private static final String SYSTEM_CPU_PATH = "/sys/devices/system/cpu";
   private static final CpuInfoUtils instance = new CpuInfoUtils();
   private final List<Integer> cpuMaxFrequenciesMhz = new ArrayList<>();

   private CpuInfoUtils() {
   }

   public static CpuInfoUtils getInstance() {
      return instance;
   }

   final void clear() {
      this.cpuMaxFrequenciesMhz.clear();
   }

   String getSystemCpuPath() {
      return "/sys/devices/system/cpu";
   }

   public List<Integer> readMaxFrequencies() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/android/core/internal/util/CpuInfoUtils.cpuMaxFrequenciesMhz Ljava/util/List;
      // 06: invokeinterface java/util/List.isEmpty ()Z 1
      // 0b: ifne 19
      // 0e: aload 0
      // 0f: getfield io/sentry/android/core/internal/util/CpuInfoUtils.cpuMaxFrequenciesMhz Ljava/util/List;
      // 12: astore 6
      // 14: aload 0
      // 15: monitorexit
      // 16: aload 6
      // 18: areturn
      // 19: new java/io/File
      // 1c: astore 6
      // 1e: aload 6
      // 20: aload 0
      // 21: invokevirtual io/sentry/android/core/internal/util/CpuInfoUtils.getSystemCpuPath ()Ljava/lang/String;
      // 24: invokespecial java/io/File.<init> (Ljava/lang/String;)V
      // 27: aload 6
      // 29: invokevirtual java/io/File.listFiles ()[Ljava/io/File;
      // 2c: astore 6
      // 2e: aload 6
      // 30: ifnonnull 41
      // 33: new java/util/ArrayList
      // 36: dup
      // 37: invokespecial java/util/ArrayList.<init> ()V
      // 3a: astore 6
      // 3c: aload 0
      // 3d: monitorexit
      // 3e: aload 6
      // 40: areturn
      // 41: aload 6
      // 43: arraylength
      // 44: istore 2
      // 45: bipush 0
      // 46: istore 1
      // 47: iload 1
      // 48: iload 2
      // 49: if_icmpge b8
      // 4c: aload 6
      // 4e: iload 1
      // 4f: aaload
      // 50: astore 8
      // 52: aload 8
      // 54: invokevirtual java/io/File.getName ()Ljava/lang/String;
      // 57: ldc "cpu[0-9]+"
      // 59: invokevirtual java/lang/String.matches (Ljava/lang/String;)Z
      // 5c: ifne 62
      // 5f: goto b2
      // 62: new java/io/File
      // 65: astore 7
      // 67: aload 7
      // 69: aload 8
      // 6b: ldc "cpufreq/cpuinfo_max_freq"
      // 6d: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 70: aload 7
      // 72: invokevirtual java/io/File.exists ()Z
      // 75: ifeq b2
      // 78: aload 7
      // 7a: invokevirtual java/io/File.canRead ()Z
      // 7d: istore 3
      // 7e: iload 3
      // 7f: ifne 85
      // 82: goto b2
      // 85: aload 7
      // 87: invokestatic io/sentry/util/FileUtils.readText (Ljava/io/File;)Ljava/lang/String;
      // 8a: astore 7
      // 8c: aload 7
      // 8e: ifnonnull 94
      // 91: goto b2
      // 94: aload 7
      // 96: invokevirtual java/lang/String.trim ()Ljava/lang/String;
      // 99: invokestatic java/lang/Long.parseLong (Ljava/lang/String;)J
      // 9c: lstore 4
      // 9e: aload 0
      // 9f: getfield io/sentry/android/core/internal/util/CpuInfoUtils.cpuMaxFrequenciesMhz Ljava/util/List;
      // a2: lload 4
      // a4: ldc2_w 1000
      // a7: ldiv
      // a8: l2i
      // a9: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // ac: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // b1: pop
      // b2: iinc 1 1
      // b5: goto 47
      // b8: aload 0
      // b9: getfield io/sentry/android/core/internal/util/CpuInfoUtils.cpuMaxFrequenciesMhz Ljava/util/List;
      // bc: astore 6
      // be: aload 0
      // bf: monitorexit
      // c0: aload 6
      // c2: areturn
      // c3: astore 6
      // c5: aload 0
      // c6: monitorexit
      // c7: aload 6
      // c9: athrow
      // ca: astore 7
      // cc: goto b2
   }

   public void setCpuMaxFrequencies(List<Integer> var1) {
      this.cpuMaxFrequenciesMhz.clear();
      this.cpuMaxFrequenciesMhz.addAll(var1);
   }
}
