package okio

import java.io.IOException
import java.io.InterruptedIOException
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

public class Throttler internal constructor(allocatedUntil: Long) {
   private final var allocatedUntil: Long
   private final var bytesPerSecond: Long
   public final val condition: Condition
   public final val lock: ReentrantLock
   private final var maxByteCount: Long
   private final var waitByteCount: Long

   public constructor() : this(System.nanoTime())
   init {
      this.allocatedUntil = var1;
      this.waitByteCount = 8192L;
      this.maxByteCount = 262144L;
      val var3: ReentrantLock = new ReentrantLock();
      this.lock = var3;
      val var4: Condition = var3.newCondition();
      this.condition = var4;
   }

   private fun Long.bytesToNanos(): Long {
      return var1 * 1000000000L / this.bytesPerSecond;
   }

   private fun Long.nanosToBytes(): Long {
      return var1 * this.bytesPerSecond / 1000000000L;
   }

   internal fun byteCountOrWaitNanos(now: Long, byteCount: Long): Long {
      if (this.bytesPerSecond == 0L) {
         return var3;
      } else {
         var var5: Long = Math.max(this.allocatedUntil - var1, 0L);
         val var9: Long = this.maxByteCount - this.nanosToBytes(var5);
         if (var9 >= var3) {
            this.allocatedUntil = var1 + var5 + this.bytesToNanos(var3);
            return var3;
         } else if (var9 >= this.waitByteCount) {
            this.allocatedUntil = var1 + this.bytesToNanos(this.maxByteCount);
            return var9;
         } else {
            var3 = Math.min(this.waitByteCount, var3);
            var5 = var5 + this.bytesToNanos(var3 - this.maxByteCount);
            if (var5 == 0L) {
               this.allocatedUntil = var1 + this.bytesToNanos(this.maxByteCount);
               return var3;
            } else {
               return -var5;
            }
         }
      }
   }

   fun bytesPerSecond(var1: Long) {
      bytesPerSecond$default(this, var1, 0L, 0L, 6, null);
   }

   fun bytesPerSecond(var1: Long, var3: Long) {
      bytesPerSecond$default(this, var1, var3, 0L, 4, null);
   }

   public fun bytesPerSecond(bytesPerSecond: Long, waitByteCount: Long = var0.waitByteCount, maxByteCount: Long = var0.maxByteCount) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield okio/Throttler.lock Ljava/util/concurrent/locks/ReentrantLock;
      // 04: checkcast java/util/concurrent/locks/Lock
      // 07: astore 7
      // 09: aload 7
      // 0b: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
      // 10: lload 1
      // 11: lconst_0
      // 12: lcmp
      // 13: iflt 6d
      // 16: lload 3
      // 17: lconst_0
      // 18: lcmp
      // 19: ifle 5b
      // 1c: lload 5
      // 1e: lload 3
      // 1f: lcmp
      // 20: iflt 49
      // 23: aload 0
      // 24: lload 1
      // 25: putfield okio/Throttler.bytesPerSecond J
      // 28: aload 0
      // 29: lload 3
      // 2a: putfield okio/Throttler.waitByteCount J
      // 2d: aload 0
      // 2e: lload 5
      // 30: putfield okio/Throttler.maxByteCount J
      // 33: aload 0
      // 34: getfield okio/Throttler.condition Ljava/util/concurrent/locks/Condition;
      // 37: invokeinterface java/util/concurrent/locks/Condition.signalAll ()V 1
      // 3c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 3f: astore 8
      // 41: aload 7
      // 43: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 48: return
      // 49: new java/lang/IllegalArgumentException
      // 4c: astore 8
      // 4e: aload 8
      // 50: ldc "Failed requirement."
      // 52: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 55: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 58: aload 8
      // 5a: athrow
      // 5b: new java/lang/IllegalArgumentException
      // 5e: astore 8
      // 60: aload 8
      // 62: ldc "Failed requirement."
      // 64: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 67: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 6a: aload 8
      // 6c: athrow
      // 6d: new java/lang/IllegalArgumentException
      // 70: astore 8
      // 72: aload 8
      // 74: ldc "Failed requirement."
      // 76: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 79: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 7c: aload 8
      // 7e: athrow
      // 7f: astore 8
      // 81: aload 7
      // 83: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 88: aload 8
      // 8a: athrow
   }

   public fun sink(sink: Sink): Sink {
      return new ForwardingSink(var1, this) {
         final Throttler this$0;

         {
            super(var1);
            this.this$0 = var2;
         }

         @Override
         public void write(Buffer var1, long var2) throws IOException {
            while (var2 > 0L) {
               var var4: Long;
               try {
                  var4 = this.this$0.take$okio(var2);
                  super.write(var1, var4);
               } catch (var6: InterruptedException) {
                  Thread.currentThread().interrupt();
                  throw new InterruptedIOException("interrupted");
               }

               var2 -= var4;
            }
         }
      };
   }

   public fun source(source: Source): Source {
      return new ForwardingSource(var1, this) {
         final Throttler this$0;

         {
            super(var1);
            this.this$0 = var2;
         }

         @Override
         public long read(Buffer var1, long var2) {
            try {
               return super.read(var1, this.this$0.take$okio(var2));
            } catch (var4: InterruptedException) {
               Thread.currentThread().interrupt();
               throw new InterruptedIOException("interrupted");
            }
         }
      };
   }

   internal fun take(byteCount: Long): Long {
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
      //
      // Bytecode:
      // 00: lload 1
      // 01: lconst_0
      // 02: lcmp
      // 03: ifle 49
      // 06: aload 0
      // 07: getfield okio/Throttler.lock Ljava/util/concurrent/locks/ReentrantLock;
      // 0a: checkcast java/util/concurrent/locks/Lock
      // 0d: astore 6
      // 0f: aload 6
      // 11: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
      // 16: aload 0
      // 17: invokestatic java/lang/System.nanoTime ()J
      // 1a: lload 1
      // 1b: invokevirtual okio/Throttler.byteCountOrWaitNanos$okio (JJ)J
      // 1e: lstore 3
      // 1f: lload 3
      // 20: lconst_0
      // 21: lcmp
      // 22: iflt 2e
      // 25: aload 6
      // 27: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 2c: lload 3
      // 2d: lreturn
      // 2e: aload 0
      // 2f: getfield okio/Throttler.condition Ljava/util/concurrent/locks/Condition;
      // 32: lload 3
      // 33: lneg
      // 34: invokeinterface java/util/concurrent/locks/Condition.awaitNanos (J)J 3
      // 39: pop2
      // 3a: goto 16
      // 3d: astore 5
      // 3f: aload 6
      // 41: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 46: aload 5
      // 48: athrow
      // 49: new java/lang/IllegalArgumentException
      // 4c: dup
      // 4d: ldc "Failed requirement."
      // 4f: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 52: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 55: athrow
   }
}
