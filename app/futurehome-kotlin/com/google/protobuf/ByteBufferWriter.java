package com.google.protobuf;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

final class ByteBufferWriter {
   private static final ThreadLocal<SoftReference<byte[]>> BUFFER = new ThreadLocal<>();
   private static final float BUFFER_REALLOCATION_THRESHOLD = 0.5F;
   private static final long CHANNEL_FIELD_OFFSET;
   private static final Class<?> FILE_OUTPUT_STREAM_CLASS;
   private static final int MAX_CACHED_BUFFER_SIZE = 16384;
   private static final int MIN_CACHED_BUFFER_SIZE = 1024;

   static {
      Class var0 = safeGetClass("java.io.FileOutputStream");
      FILE_OUTPUT_STREAM_CLASS = var0;
      CHANNEL_FIELD_OFFSET = getChannelFieldOffset(var0);
   }

   private ByteBufferWriter() {
   }

   static void clearCachedBuffer() {
      BUFFER.set(null);
   }

   private static byte[] getBuffer() {
      SoftReference var0 = BUFFER.get();
      byte[] var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = (byte[])var0.get();
      }

      return var1;
   }

   private static long getChannelFieldOffset(Class<?> var0) {
      if (var0 != null) {
         try {
            if (UnsafeUtil.hasUnsafeArrayOperations()) {
               return UnsafeUtil.objectFieldOffset(var0.getDeclaredField("channel"));
            }
         } finally {
            return -1L;
         }
      }

      return -1L;
   }

   private static byte[] getOrCreateBuffer(int var0) {
      var0 = Math.max(var0, 1024);
      byte[] var2 = getBuffer();
      if (var2 != null && !needToReallocate(var0, var2.length)) {
         return var2;
      } else {
         var2 = new byte[var0];
         byte[] var1 = var2;
         if (var0 <= 16384) {
            setBuffer(var2);
            var1 = var2;
         }

         return var1;
      }
   }

   private static boolean needToReallocate(int var0, int var1) {
      boolean var2;
      if (var1 < var0 && var1 < var0 * 0.5F) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static Class<?> safeGetClass(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var1) {
         return null;
      }
   }

   private static void setBuffer(byte[] var0) {
      BUFFER.set(new SoftReference<>(var0));
   }

   static void write(ByteBuffer param0, OutputStream param1) throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokevirtual java/nio/ByteBuffer.position ()I
      // 04: istore 3
      // 05: aload 0
      // 06: invokevirtual java/nio/ByteBuffer.hasArray ()Z
      // 09: ifeq 24
      // 0c: aload 1
      // 0d: aload 0
      // 0e: invokevirtual java/nio/ByteBuffer.array ()[B
      // 11: aload 0
      // 12: invokevirtual java/nio/ByteBuffer.arrayOffset ()I
      // 15: aload 0
      // 16: invokevirtual java/nio/ByteBuffer.position ()I
      // 19: iadd
      // 1a: aload 0
      // 1b: invokevirtual java/nio/ByteBuffer.remaining ()I
      // 1e: invokevirtual java/io/OutputStream.write ([BII)V
      // 21: goto 5b
      // 24: aload 0
      // 25: aload 1
      // 26: invokestatic com/google/protobuf/ByteBufferWriter.writeToChannel (Ljava/nio/ByteBuffer;Ljava/io/OutputStream;)Z
      // 29: ifne 5b
      // 2c: aload 0
      // 2d: invokevirtual java/nio/ByteBuffer.remaining ()I
      // 30: invokestatic com/google/protobuf/ByteBufferWriter.getOrCreateBuffer (I)[B
      // 33: astore 4
      // 35: aload 0
      // 36: invokevirtual java/nio/ByteBuffer.hasRemaining ()Z
      // 39: ifeq 5b
      // 3c: aload 0
      // 3d: invokevirtual java/nio/ByteBuffer.remaining ()I
      // 40: aload 4
      // 42: arraylength
      // 43: invokestatic java/lang/Math.min (II)I
      // 46: istore 2
      // 47: aload 0
      // 48: aload 4
      // 4a: bipush 0
      // 4b: iload 2
      // 4c: invokevirtual java/nio/ByteBuffer.get ([BII)Ljava/nio/ByteBuffer;
      // 4f: pop
      // 50: aload 1
      // 51: aload 4
      // 53: bipush 0
      // 54: iload 2
      // 55: invokevirtual java/io/OutputStream.write ([BII)V
      // 58: goto 35
      // 5b: aload 0
      // 5c: iload 3
      // 5d: invokestatic com/google/protobuf/Java8Compatibility.position (Ljava/nio/Buffer;I)V
      // 60: return
      // 61: astore 1
      // 62: aload 0
      // 63: iload 3
      // 64: invokestatic com/google/protobuf/Java8Compatibility.position (Ljava/nio/Buffer;I)V
      // 67: aload 1
      // 68: athrow
   }

   private static boolean writeToChannel(ByteBuffer var0, OutputStream var1) throws IOException {
      long var2 = CHANNEL_FIELD_OFFSET;
      if (var2 >= 0L && FILE_OUTPUT_STREAM_CLASS.isInstance(var1)) {
         try {
            var5 = (WritableByteChannel)UnsafeUtil.getObject(var1, var2);
         } catch (ClassCastException var4) {
            var5 = null;
         }

         if (var5 != null) {
            var5.write(var0);
            return true;
         }
      }

      return false;
   }
}
