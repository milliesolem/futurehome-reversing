package okio

import java.io.IOException
import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

public open class AsyncTimeout : Timeout {
   private final var inQueue: Boolean
   private final var next: AsyncTimeout?
   private final var timeoutAt: Long

   @JvmStatic
   fun {
      val var2: ReentrantLock = new ReentrantLock();
      lock = var2;
      val var3: Condition = var2.newCondition();
      condition = var3;
      val var0: Long = TimeUnit.SECONDS.toMillis(60L);
      IDLE_TIMEOUT_MILLIS = var0;
      IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(var0);
   }

   private fun remainingNanos(now: Long): Long {
      return this.timeoutAt - var1;
   }

   internal fun `access$newTimeoutException`(cause: IOException?): IOException {
      return this.newTimeoutException(var1);
   }

   public fun enter() {
      val var1: Long = this.timeoutNanos();
      val var3: Boolean = this.hasDeadline();
      if (var1 != 0L || var3) {
         AsyncTimeout.Companion.access$scheduleTimeout(Companion, this, var1, var3);
      }
   }

   public fun exit(): Boolean {
      return AsyncTimeout.Companion.access$cancelScheduledTimeout(Companion, this);
   }

   protected open fun newTimeoutException(cause: IOException?): IOException {
      val var2: InterruptedIOException = new InterruptedIOException("timeout");
      if (var1 != null) {
         var2.initCause(var1);
      }

      return var2;
   }

   public fun sink(sink: Sink): Sink {
      return new Sink(this, var1) {
         final Sink $sink;
         final AsyncTimeout this$0;

         {
            this.this$0 = var1;
            this.$sink = var2;
         }

         @Override
         public void close() {
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
            // 01: getfield okio/AsyncTimeout$sink$1.this$0 Lokio/AsyncTimeout;
            // 04: astore 2
            // 05: aload 0
            // 06: getfield okio/AsyncTimeout$sink$1.$sink Lokio/Sink;
            // 09: astore 1
            // 0a: aload 2
            // 0b: invokevirtual okio/AsyncTimeout.enter ()V
            // 0e: aload 1
            // 0f: invokeinterface okio/Sink.close ()V 1
            // 14: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 17: astore 1
            // 18: aload 2
            // 19: invokevirtual okio/AsyncTimeout.exit ()Z
            // 1c: ifne 20
            // 1f: return
            // 20: aload 2
            // 21: aconst_null
            // 22: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 25: athrow
            // 26: astore 1
            // 27: goto 45
            // 2a: astore 1
            // 2b: aload 2
            // 2c: invokevirtual okio/AsyncTimeout.exit ()Z
            // 2f: ifne 3a
            // 32: aload 1
            // 33: checkcast java/lang/Throwable
            // 36: astore 1
            // 37: goto 43
            // 3a: aload 2
            // 3b: aload 1
            // 3c: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 3f: astore 1
            // 40: goto 32
            // 43: aload 1
            // 44: athrow
            // 45: aload 2
            // 46: invokevirtual okio/AsyncTimeout.exit ()Z
            // 49: pop
            // 4a: aload 1
            // 4b: athrow
         }

         @Override
         public void flush() {
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
            // 01: getfield okio/AsyncTimeout$sink$1.this$0 Lokio/AsyncTimeout;
            // 04: astore 2
            // 05: aload 0
            // 06: getfield okio/AsyncTimeout$sink$1.$sink Lokio/Sink;
            // 09: astore 1
            // 0a: aload 2
            // 0b: invokevirtual okio/AsyncTimeout.enter ()V
            // 0e: aload 1
            // 0f: invokeinterface okio/Sink.flush ()V 1
            // 14: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 17: astore 1
            // 18: aload 2
            // 19: invokevirtual okio/AsyncTimeout.exit ()Z
            // 1c: ifne 20
            // 1f: return
            // 20: aload 2
            // 21: aconst_null
            // 22: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 25: athrow
            // 26: astore 1
            // 27: goto 45
            // 2a: astore 1
            // 2b: aload 2
            // 2c: invokevirtual okio/AsyncTimeout.exit ()Z
            // 2f: ifne 3a
            // 32: aload 1
            // 33: checkcast java/lang/Throwable
            // 36: astore 1
            // 37: goto 43
            // 3a: aload 2
            // 3b: aload 1
            // 3c: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 3f: astore 1
            // 40: goto 32
            // 43: aload 1
            // 44: athrow
            // 45: aload 2
            // 46: invokevirtual okio/AsyncTimeout.exit ()Z
            // 49: pop
            // 4a: aload 1
            // 4b: athrow
         }

         public AsyncTimeout timeout() {
            return this.this$0;
         }

         @Override
         public java.lang.String toString() {
            val var1: StringBuilder = new StringBuilder("AsyncTimeout.sink(");
            var1.append(this.$sink);
            var1.append(')');
            return var1.toString();
         }

         @Override
         public void write(Buffer param1, long param2) {
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
            // 00: aload 1
            // 01: ldc "source"
            // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
            // 06: aload 1
            // 07: invokevirtual okio/Buffer.size ()J
            // 0a: lconst_0
            // 0b: lload 2
            // 0c: invokestatic okio/_SegmentedByteString.checkOffsetAndCount (JJJ)V
            // 0f: lconst_0
            // 10: lstore 4
            // 12: lload 2
            // 13: lconst_0
            // 14: lcmp
            // 15: ifle bd
            // 18: aload 1
            // 19: getfield okio/Buffer.head Lokio/Segment;
            // 1c: astore 8
            // 1e: aload 8
            // 20: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
            // 23: lload 4
            // 25: lstore 6
            // 27: lload 4
            // 29: ldc2_w 65536
            // 2c: lcmp
            // 2d: ifge 5d
            // 30: lload 4
            // 32: aload 8
            // 34: getfield okio/Segment.limit I
            // 37: aload 8
            // 39: getfield okio/Segment.pos I
            // 3c: isub
            // 3d: i2l
            // 3e: ladd
            // 3f: lstore 4
            // 41: lload 4
            // 43: lload 2
            // 44: lcmp
            // 45: iflt 4e
            // 48: lload 2
            // 49: lstore 6
            // 4b: goto 5d
            // 4e: aload 8
            // 50: getfield okio/Segment.next Lokio/Segment;
            // 53: astore 8
            // 55: aload 8
            // 57: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
            // 5a: goto 23
            // 5d: aload 0
            // 5e: getfield okio/AsyncTimeout$sink$1.this$0 Lokio/AsyncTimeout;
            // 61: astore 8
            // 63: aload 0
            // 64: getfield okio/AsyncTimeout$sink$1.$sink Lokio/Sink;
            // 67: astore 9
            // 69: aload 8
            // 6b: invokevirtual okio/AsyncTimeout.enter ()V
            // 6e: aload 9
            // 70: aload 1
            // 71: lload 6
            // 73: invokeinterface okio/Sink.write (Lokio/Buffer;J)V 4
            // 78: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 7b: astore 9
            // 7d: aload 8
            // 7f: invokevirtual okio/AsyncTimeout.exit ()Z
            // 82: ifne 8d
            // 85: lload 2
            // 86: lload 6
            // 88: lsub
            // 89: lstore 2
            // 8a: goto 0f
            // 8d: aload 8
            // 8f: aconst_null
            // 90: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 93: athrow
            // 94: astore 1
            // 95: goto b5
            // 98: astore 1
            // 99: aload 8
            // 9b: invokevirtual okio/AsyncTimeout.exit ()Z
            // 9e: ifne a9
            // a1: aload 1
            // a2: checkcast java/lang/Throwable
            // a5: astore 1
            // a6: goto b3
            // a9: aload 8
            // ab: aload 1
            // ac: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // af: astore 1
            // b0: goto a1
            // b3: aload 1
            // b4: athrow
            // b5: aload 8
            // b7: invokevirtual okio/AsyncTimeout.exit ()Z
            // ba: pop
            // bb: aload 1
            // bc: athrow
            // bd: return
         }
      };
   }

   public fun source(source: Source): Source {
      return new Source(this, var1) {
         final Source $source;
         final AsyncTimeout this$0;

         {
            this.this$0 = var1;
            this.$source = var2;
         }

         @Override
         public void close() {
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
            // 01: getfield okio/AsyncTimeout$source$1.this$0 Lokio/AsyncTimeout;
            // 04: astore 2
            // 05: aload 0
            // 06: getfield okio/AsyncTimeout$source$1.$source Lokio/Source;
            // 09: astore 1
            // 0a: aload 2
            // 0b: invokevirtual okio/AsyncTimeout.enter ()V
            // 0e: aload 1
            // 0f: invokeinterface okio/Source.close ()V 1
            // 14: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
            // 17: astore 1
            // 18: aload 2
            // 19: invokevirtual okio/AsyncTimeout.exit ()Z
            // 1c: ifne 20
            // 1f: return
            // 20: aload 2
            // 21: aconst_null
            // 22: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 25: athrow
            // 26: astore 1
            // 27: goto 45
            // 2a: astore 1
            // 2b: aload 2
            // 2c: invokevirtual okio/AsyncTimeout.exit ()Z
            // 2f: ifne 3a
            // 32: aload 1
            // 33: checkcast java/lang/Throwable
            // 36: astore 1
            // 37: goto 43
            // 3a: aload 2
            // 3b: aload 1
            // 3c: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 3f: astore 1
            // 40: goto 32
            // 43: aload 1
            // 44: athrow
            // 45: aload 2
            // 46: invokevirtual okio/AsyncTimeout.exit ()Z
            // 49: pop
            // 4a: aload 1
            // 4b: athrow
         }

         @Override
         public long read(Buffer param1, long param2) {
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
            // 00: aload 1
            // 01: ldc "sink"
            // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
            // 06: aload 0
            // 07: getfield okio/AsyncTimeout$source$1.this$0 Lokio/AsyncTimeout;
            // 0a: astore 4
            // 0c: aload 0
            // 0d: getfield okio/AsyncTimeout$source$1.$source Lokio/Source;
            // 10: astore 5
            // 12: aload 4
            // 14: invokevirtual okio/AsyncTimeout.enter ()V
            // 17: aload 5
            // 19: aload 1
            // 1a: lload 2
            // 1b: invokeinterface okio/Source.read (Lokio/Buffer;J)J 4
            // 20: lstore 2
            // 21: aload 4
            // 23: invokevirtual okio/AsyncTimeout.exit ()Z
            // 26: ifne 2b
            // 29: lload 2
            // 2a: lreturn
            // 2b: aload 4
            // 2d: aconst_null
            // 2e: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 31: athrow
            // 32: astore 1
            // 33: goto 53
            // 36: astore 1
            // 37: aload 4
            // 39: invokevirtual okio/AsyncTimeout.exit ()Z
            // 3c: ifne 47
            // 3f: aload 1
            // 40: checkcast java/lang/Throwable
            // 43: astore 1
            // 44: goto 51
            // 47: aload 4
            // 49: aload 1
            // 4a: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
            // 4d: astore 1
            // 4e: goto 3f
            // 51: aload 1
            // 52: athrow
            // 53: aload 4
            // 55: invokevirtual okio/AsyncTimeout.exit ()Z
            // 58: pop
            // 59: aload 1
            // 5a: athrow
         }

         public AsyncTimeout timeout() {
            return this.this$0;
         }

         @Override
         public java.lang.String toString() {
            val var1: StringBuilder = new StringBuilder("AsyncTimeout.source(");
            var1.append(this.$source);
            var1.append(')');
            return var1.toString();
         }
      };
   }

   protected open fun timedOut() {
   }

   public inline fun <T> withTimeout(block: () -> T): T {
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
      // 00: aload 1
      // 01: ldc "block"
      // 03: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
      // 06: aload 0
      // 07: invokevirtual okio/AsyncTimeout.enter ()V
      // 0a: aload 1
      // 0b: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 10: astore 1
      // 11: aload 0
      // 12: invokevirtual okio/AsyncTimeout.exit ()Z
      // 15: ifne 1a
      // 18: aload 1
      // 19: areturn
      // 1a: aload 0
      // 1b: aconst_null
      // 1c: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
      // 1f: athrow
      // 20: astore 1
      // 21: goto 3f
      // 24: astore 1
      // 25: aload 0
      // 26: invokevirtual okio/AsyncTimeout.exit ()Z
      // 29: ifne 34
      // 2c: aload 1
      // 2d: checkcast java/lang/Throwable
      // 30: astore 1
      // 31: goto 3d
      // 34: aload 0
      // 35: aload 1
      // 36: invokevirtual okio/AsyncTimeout.access$newTimeoutException (Ljava/io/IOException;)Ljava/io/IOException;
      // 39: astore 1
      // 3a: goto 2c
      // 3d: aload 1
      // 3e: athrow
      // 3f: aload 0
      // 40: invokevirtual okio/AsyncTimeout.exit ()Z
      // 43: pop
      // 44: aload 1
      // 45: athrow
   }

   public companion object {
      private final val IDLE_TIMEOUT_MILLIS: Long
      private final val IDLE_TIMEOUT_NANOS: Long
      private const val TIMEOUT_WRITE_SIZE: Int
      public final val condition: Condition
      private final var head: AsyncTimeout?
      public final val lock: ReentrantLock

      private fun cancelScheduledTimeout(node: AsyncTimeout): Boolean {
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
         // 00: getstatic okio/AsyncTimeout.Companion Lokio/AsyncTimeout$Companion;
         // 03: invokevirtual okio/AsyncTimeout$Companion.getLock ()Ljava/util/concurrent/locks/ReentrantLock;
         // 06: checkcast java/util/concurrent/locks/Lock
         // 09: astore 4
         // 0b: aload 4
         // 0d: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
         // 12: aload 1
         // 13: invokestatic okio/AsyncTimeout.access$getInQueue$p (Lokio/AsyncTimeout;)Z
         // 16: istore 2
         // 17: iload 2
         // 18: ifne 24
         // 1b: aload 4
         // 1d: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 22: bipush 0
         // 23: ireturn
         // 24: aload 1
         // 25: bipush 0
         // 26: invokestatic okio/AsyncTimeout.access$setInQueue$p (Lokio/AsyncTimeout;Z)V
         // 29: invokestatic okio/AsyncTimeout.access$getHead$cp ()Lokio/AsyncTimeout;
         // 2c: astore 3
         // 2d: aload 3
         // 2e: ifnull 57
         // 31: aload 3
         // 32: invokestatic okio/AsyncTimeout.access$getNext$p (Lokio/AsyncTimeout;)Lokio/AsyncTimeout;
         // 35: aload 1
         // 36: if_acmpne 4f
         // 39: aload 3
         // 3a: aload 1
         // 3b: invokestatic okio/AsyncTimeout.access$getNext$p (Lokio/AsyncTimeout;)Lokio/AsyncTimeout;
         // 3e: invokestatic okio/AsyncTimeout.access$setNext$p (Lokio/AsyncTimeout;Lokio/AsyncTimeout;)V
         // 41: aload 1
         // 42: aconst_null
         // 43: invokestatic okio/AsyncTimeout.access$setNext$p (Lokio/AsyncTimeout;Lokio/AsyncTimeout;)V
         // 46: aload 4
         // 48: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 4d: bipush 0
         // 4e: ireturn
         // 4f: aload 3
         // 50: invokestatic okio/AsyncTimeout.access$getNext$p (Lokio/AsyncTimeout;)Lokio/AsyncTimeout;
         // 53: astore 3
         // 54: goto 2d
         // 57: aload 4
         // 59: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 5e: bipush 1
         // 5f: ireturn
         // 60: astore 1
         // 61: aload 4
         // 63: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 68: aload 1
         // 69: athrow
      }

      private fun scheduleTimeout(node: AsyncTimeout, timeoutNanos: Long, hasDeadline: Boolean) {
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
         // 000: getstatic okio/AsyncTimeout.Companion Lokio/AsyncTimeout$Companion;
         // 003: invokevirtual okio/AsyncTimeout$Companion.getLock ()Ljava/util/concurrent/locks/ReentrantLock;
         // 006: checkcast java/util/concurrent/locks/Lock
         // 009: astore 9
         // 00b: aload 9
         // 00d: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
         // 012: aload 1
         // 013: invokestatic okio/AsyncTimeout.access$getInQueue$p (Lokio/AsyncTimeout;)Z
         // 016: ifne 108
         // 019: aload 1
         // 01a: bipush 1
         // 01b: invokestatic okio/AsyncTimeout.access$setInQueue$p (Lokio/AsyncTimeout;Z)V
         // 01e: invokestatic okio/AsyncTimeout.access$getHead$cp ()Lokio/AsyncTimeout;
         // 021: ifnonnull 047
         // 024: getstatic okio/AsyncTimeout.Companion Lokio/AsyncTimeout$Companion;
         // 027: astore 8
         // 029: new okio/AsyncTimeout
         // 02c: astore 8
         // 02e: aload 8
         // 030: invokespecial okio/AsyncTimeout.<init> ()V
         // 033: aload 8
         // 035: invokestatic okio/AsyncTimeout.access$setHead$cp (Lokio/AsyncTimeout;)V
         // 038: new okio/AsyncTimeout$Watchdog
         // 03b: astore 8
         // 03d: aload 8
         // 03f: invokespecial okio/AsyncTimeout$Watchdog.<init> ()V
         // 042: aload 8
         // 044: invokevirtual okio/AsyncTimeout$Watchdog.start ()V
         // 047: invokestatic java/lang/System.nanoTime ()J
         // 04a: lstore 6
         // 04c: lload 2
         // 04d: lconst_0
         // 04e: lcmp
         // 04f: istore 5
         // 051: iload 5
         // 053: ifeq 070
         // 056: iload 4
         // 058: ifeq 070
         // 05b: aload 1
         // 05c: lload 2
         // 05d: aload 1
         // 05e: invokevirtual okio/AsyncTimeout.deadlineNanoTime ()J
         // 061: lload 6
         // 063: lsub
         // 064: invokestatic java/lang/Math.min (JJ)J
         // 067: lload 6
         // 069: ladd
         // 06a: invokestatic okio/AsyncTimeout.access$setTimeoutAt$p (Lokio/AsyncTimeout;J)V
         // 06d: goto 08d
         // 070: iload 5
         // 072: ifeq 080
         // 075: aload 1
         // 076: lload 2
         // 077: lload 6
         // 079: ladd
         // 07a: invokestatic okio/AsyncTimeout.access$setTimeoutAt$p (Lokio/AsyncTimeout;J)V
         // 07d: goto 08d
         // 080: iload 4
         // 082: ifeq 0fe
         // 085: aload 1
         // 086: aload 1
         // 087: invokevirtual okio/AsyncTimeout.deadlineNanoTime ()J
         // 08a: invokestatic okio/AsyncTimeout.access$setTimeoutAt$p (Lokio/AsyncTimeout;J)V
         // 08d: aload 1
         // 08e: lload 6
         // 090: invokestatic okio/AsyncTimeout.access$remainingNanos (Lokio/AsyncTimeout;J)J
         // 093: lstore 2
         // 094: invokestatic okio/AsyncTimeout.access$getHead$cp ()Lokio/AsyncTimeout;
         // 097: astore 8
         // 099: aload 8
         // 09b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 09e: aload 8
         // 0a0: invokestatic okio/AsyncTimeout.access$getNext$p (Lokio/AsyncTimeout;)Lokio/AsyncTimeout;
         // 0a3: ifnull 0d0
         // 0a6: aload 8
         // 0a8: invokestatic okio/AsyncTimeout.access$getNext$p (Lokio/AsyncTimeout;)Lokio/AsyncTimeout;
         // 0ab: astore 10
         // 0ad: aload 10
         // 0af: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 0b2: lload 2
         // 0b3: aload 10
         // 0b5: lload 6
         // 0b7: invokestatic okio/AsyncTimeout.access$remainingNanos (Lokio/AsyncTimeout;J)J
         // 0ba: lcmp
         // 0bb: ifge 0c1
         // 0be: goto 0d0
         // 0c1: aload 8
         // 0c3: invokestatic okio/AsyncTimeout.access$getNext$p (Lokio/AsyncTimeout;)Lokio/AsyncTimeout;
         // 0c6: astore 8
         // 0c8: aload 8
         // 0ca: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
         // 0cd: goto 09e
         // 0d0: aload 1
         // 0d1: aload 8
         // 0d3: invokestatic okio/AsyncTimeout.access$getNext$p (Lokio/AsyncTimeout;)Lokio/AsyncTimeout;
         // 0d6: invokestatic okio/AsyncTimeout.access$setNext$p (Lokio/AsyncTimeout;Lokio/AsyncTimeout;)V
         // 0d9: aload 8
         // 0db: aload 1
         // 0dc: invokestatic okio/AsyncTimeout.access$setNext$p (Lokio/AsyncTimeout;Lokio/AsyncTimeout;)V
         // 0df: aload 8
         // 0e1: invokestatic okio/AsyncTimeout.access$getHead$cp ()Lokio/AsyncTimeout;
         // 0e4: if_acmpne 0f2
         // 0e7: getstatic okio/AsyncTimeout.Companion Lokio/AsyncTimeout$Companion;
         // 0ea: invokevirtual okio/AsyncTimeout$Companion.getCondition ()Ljava/util/concurrent/locks/Condition;
         // 0ed: invokeinterface java/util/concurrent/locks/Condition.signal ()V 1
         // 0f2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 0f5: astore 1
         // 0f6: aload 9
         // 0f8: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 0fd: return
         // 0fe: new java/lang/AssertionError
         // 101: astore 1
         // 102: aload 1
         // 103: invokespecial java/lang/AssertionError.<init> ()V
         // 106: aload 1
         // 107: athrow
         // 108: new java/lang/IllegalStateException
         // 10b: astore 1
         // 10c: aload 1
         // 10d: ldc "Unbalanced enter/exit"
         // 10f: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
         // 112: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // 115: aload 1
         // 116: athrow
         // 117: astore 1
         // 118: aload 9
         // 11a: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 11f: aload 1
         // 120: athrow
      }

      @Throws(java/lang/InterruptedException::class)
      internal fun awaitTimeout(): AsyncTimeout? {
         var var3: AsyncTimeout = AsyncTimeout.access$getHead$cp();
         var3 = AsyncTimeout.access$getNext$p(var3);
         if (var3 == null) {
            val var6: Long = System.nanoTime();
            this.getCondition().await(AsyncTimeout.access$getIDLE_TIMEOUT_MILLIS$cp(), TimeUnit.MILLISECONDS);
            val var5: AsyncTimeout = AsyncTimeout.access$getHead$cp();
            var3 = null;
            if (AsyncTimeout.access$getNext$p(var5) == null) {
               var3 = null;
               if (System.nanoTime() - var6 >= AsyncTimeout.access$getIDLE_TIMEOUT_NANOS$cp()) {
                  var3 = AsyncTimeout.access$getHead$cp();
               }
            }

            return var3;
         } else {
            val var1: Long = AsyncTimeout.access$remainingNanos(var3, System.nanoTime());
            if (var1 > 0L) {
               this.getCondition().await(var1, TimeUnit.NANOSECONDS);
               return null;
            } else {
               val var9: AsyncTimeout = AsyncTimeout.access$getHead$cp();
               AsyncTimeout.access$setNext$p(var9, AsyncTimeout.access$getNext$p(var3));
               AsyncTimeout.access$setNext$p(var3, null);
               return var3;
            }
         }
      }
   }

   private class Watchdog internal constructor() : Thread("Okio Watchdog") {
      init {
         this.setDaemon(true);
      }

      public override fun run() {
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
         // 00: getstatic okio/AsyncTimeout.Companion Lokio/AsyncTimeout$Companion;
         // 03: invokevirtual okio/AsyncTimeout$Companion.getLock ()Ljava/util/concurrent/locks/ReentrantLock;
         // 06: checkcast java/util/concurrent/locks/Lock
         // 09: astore 1
         // 0a: aload 1
         // 0b: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
         // 10: getstatic okio/AsyncTimeout.Companion Lokio/AsyncTimeout$Companion;
         // 13: invokevirtual okio/AsyncTimeout$Companion.awaitTimeout$okio ()Lokio/AsyncTimeout;
         // 16: astore 2
         // 17: aload 2
         // 18: invokestatic okio/AsyncTimeout.access$getHead$cp ()Lokio/AsyncTimeout;
         // 1b: if_acmpne 2d
         // 1e: getstatic okio/AsyncTimeout.Companion Lokio/AsyncTimeout$Companion;
         // 21: astore 2
         // 22: aconst_null
         // 23: invokestatic okio/AsyncTimeout.access$setHead$cp (Lokio/AsyncTimeout;)V
         // 26: aload 1
         // 27: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 2c: return
         // 2d: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
         // 30: astore 3
         // 31: aload 1
         // 32: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 37: aload 2
         // 38: ifnull 00
         // 3b: aload 2
         // 3c: invokevirtual okio/AsyncTimeout.timedOut ()V
         // 3f: goto 00
         // 42: astore 2
         // 43: aload 1
         // 44: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 49: aload 2
         // 4a: athrow
         // 4b: astore 1
         // 4c: goto 00
      }
   }
}
