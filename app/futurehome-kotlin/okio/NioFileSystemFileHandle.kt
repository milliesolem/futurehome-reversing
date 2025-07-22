package okio

import java.nio.channels.FileChannel

internal class NioFileSystemFileHandle(readWrite: Boolean, fileChannel: FileChannel) : FileHandle(var1) {
   private final val fileChannel: FileChannel

   init {
      this.fileChannel = var2;
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
      // 03: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 06: invokevirtual java/nio/channels/FileChannel.close ()V
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
      // 03: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 06: bipush 1
      // 07: invokevirtual java/nio/channels/FileChannel.force (Z)V
      // 0a: aload 0
      // 0b: monitorexit
      // 0c: return
      // 0d: astore 1
      // 0e: aload 0
      // 0f: monitorexit
      // 10: aload 1
      // 11: athrow
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
      // 09: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 0c: lload 1
      // 0d: invokevirtual java/nio/channels/FileChannel.position (J)Ljava/nio/channels/FileChannel;
      // 10: pop
      // 11: aload 3
      // 12: iload 4
      // 14: iload 5
      // 16: invokestatic java/nio/ByteBuffer.wrap ([BII)Ljava/nio/ByteBuffer;
      // 19: astore 3
      // 1a: bipush 0
      // 1b: istore 4
      // 1d: iload 4
      // 1f: iload 5
      // 21: if_icmpge 47
      // 24: aload 0
      // 25: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 28: aload 3
      // 29: invokevirtual java/nio/channels/FileChannel.read (Ljava/nio/ByteBuffer;)I
      // 2c: istore 6
      // 2e: iload 6
      // 30: bipush -1
      // 31: if_icmpne 3d
      // 34: iload 4
      // 36: ifne 47
      // 39: aload 0
      // 3a: monitorexit
      // 3b: bipush -1
      // 3c: ireturn
      // 3d: iload 4
      // 3f: iload 6
      // 41: iadd
      // 42: istore 4
      // 44: goto 1d
      // 47: aload 0
      // 48: monitorexit
      // 49: iload 4
      // 4b: ireturn
      // 4c: astore 3
      // 4d: aload 0
      // 4e: monitorexit
      // 4f: aload 3
      // 50: athrow
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
      // 03: invokevirtual okio/NioFileSystemFileHandle.size ()J
      // 06: lstore 4
      // 08: lload 1
      // 09: lload 4
      // 0b: lsub
      // 0c: lstore 6
      // 0e: lload 6
      // 10: lconst_0
      // 11: lcmp
      // 12: ifle 27
      // 15: lload 6
      // 17: l2i
      // 18: istore 3
      // 19: aload 0
      // 1a: lload 4
      // 1c: iload 3
      // 1d: newarray 8
      // 1f: bipush 0
      // 20: iload 3
      // 21: invokevirtual okio/NioFileSystemFileHandle.protectedWrite (J[BII)V
      // 24: goto 30
      // 27: aload 0
      // 28: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 2b: lload 1
      // 2c: invokevirtual java/nio/channels/FileChannel.truncate (J)Ljava/nio/channels/FileChannel;
      // 2f: pop
      // 30: aload 0
      // 31: monitorexit
      // 32: return
      // 33: astore 8
      // 35: aload 0
      // 36: monitorexit
      // 37: aload 8
      // 39: athrow
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
      // 03: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 06: invokevirtual java/nio/channels/FileChannel.size ()J
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
      // 09: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 0c: lload 1
      // 0d: invokevirtual java/nio/channels/FileChannel.position (J)Ljava/nio/channels/FileChannel;
      // 10: pop
      // 11: aload 3
      // 12: iload 4
      // 14: iload 5
      // 16: invokestatic java/nio/ByteBuffer.wrap ([BII)Ljava/nio/ByteBuffer;
      // 19: astore 3
      // 1a: aload 0
      // 1b: getfield okio/NioFileSystemFileHandle.fileChannel Ljava/nio/channels/FileChannel;
      // 1e: aload 3
      // 1f: invokevirtual java/nio/channels/FileChannel.write (Ljava/nio/ByteBuffer;)I
      // 22: pop
      // 23: aload 0
      // 24: monitorexit
      // 25: return
      // 26: astore 3
      // 27: aload 0
      // 28: monitorexit
      // 29: aload 3
      // 2a: athrow
   }
}
