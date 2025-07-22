package okio

import java.io.RandomAccessFile

internal class JvmFileHandle(readWrite: Boolean, randomAccessFile: RandomAccessFile) : FileHandle(var1) {
   private final val randomAccessFile: RandomAccessFile

   init {
      this.randomAccessFile = var2;
   }

   protected override fun protectedClose() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 06: invokevirtual java/io/RandomAccessFile.close ()V
      // 09: aload 0
      // 0a: monitorexit
      // 0b: return
      // 0c: astore 1
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aload 1
      // 10: athrow
   }

   protected override fun protectedFlush() {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 06: invokevirtual java/io/RandomAccessFile.getFD ()Ljava/io/FileDescriptor;
      // 09: invokevirtual java/io/FileDescriptor.sync ()V
      // 0c: aload 0
      // 0d: monitorexit
      // 0e: return
      // 0f: astore 1
      // 10: aload 0
      // 11: monitorexit
      // 12: aload 1
      // 13: athrow
   }

   protected override fun protectedRead(fileOffset: Long, array: ByteArray, arrayOffset: Int, byteCount: Int): Int {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 3
      // 03: ldc "array"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: aload 0
      // 09: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 0c: lload 1
      // 0d: invokevirtual java/io/RandomAccessFile.seek (J)V
      // 10: bipush 0
      // 11: istore 6
      // 13: iload 6
      // 15: iload 5
      // 17: if_icmpge 44
      // 1a: aload 0
      // 1b: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 1e: aload 3
      // 1f: iload 4
      // 21: iload 5
      // 23: iload 6
      // 25: isub
      // 26: invokevirtual java/io/RandomAccessFile.read ([BII)I
      // 29: istore 7
      // 2b: iload 7
      // 2d: bipush -1
      // 2e: if_icmpne 3a
      // 31: iload 6
      // 33: ifne 44
      // 36: aload 0
      // 37: monitorexit
      // 38: bipush -1
      // 39: ireturn
      // 3a: iload 6
      // 3c: iload 7
      // 3e: iadd
      // 3f: istore 6
      // 41: goto 13
      // 44: aload 0
      // 45: monitorexit
      // 46: iload 6
      // 48: ireturn
      // 49: astore 3
      // 4a: aload 0
      // 4b: monitorexit
      // 4c: aload 3
      // 4d: athrow
   }

   protected override fun protectedResize(size: Long) {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: invokevirtual okio/JvmFileHandle.size ()J
      // 06: lstore 6
      // 08: lload 1
      // 09: lload 6
      // 0b: lsub
      // 0c: lstore 4
      // 0e: lload 4
      // 10: lconst_0
      // 11: lcmp
      // 12: ifle 27
      // 15: lload 4
      // 17: l2i
      // 18: istore 3
      // 19: aload 0
      // 1a: lload 6
      // 1c: iload 3
      // 1d: newarray 8
      // 1f: bipush 0
      // 20: iload 3
      // 21: invokevirtual okio/JvmFileHandle.protectedWrite (J[BII)V
      // 24: goto 2f
      // 27: aload 0
      // 28: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 2b: lload 1
      // 2c: invokevirtual java/io/RandomAccessFile.setLength (J)V
      // 2f: aload 0
      // 30: monitorexit
      // 31: return
      // 32: astore 8
      // 34: aload 0
      // 35: monitorexit
      // 36: aload 8
      // 38: athrow
   }

   protected override fun protectedSize(): Long {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 06: invokevirtual java/io/RandomAccessFile.length ()J
      // 09: lstore 1
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: lload 1
      // 0d: lreturn
      // 0e: astore 3
      // 0f: aload 0
      // 10: monitorexit
      // 11: aload 3
      // 12: athrow
   }

   protected override fun protectedWrite(fileOffset: Long, array: ByteArray, arrayOffset: Int, byteCount: Int) {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 3
      // 03: ldc "array"
      // 05: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 08: aload 0
      // 09: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 0c: lload 1
      // 0d: invokevirtual java/io/RandomAccessFile.seek (J)V
      // 10: aload 0
      // 11: getfield okio/JvmFileHandle.randomAccessFile Ljava/io/RandomAccessFile;
      // 14: aload 3
      // 15: iload 4
      // 17: iload 5
      // 19: invokevirtual java/io/RandomAccessFile.write ([BII)V
      // 1c: aload 0
      // 1d: monitorexit
      // 1e: return
      // 1f: astore 3
      // 20: aload 0
      // 21: monitorexit
      // 22: aload 3
      // 23: athrow
   }
}
