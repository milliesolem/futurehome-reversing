package okio

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

public class Pipe(maxBufferSize: Long) {
   internal final val buffer: Buffer
   internal final var canceled: Boolean
   public final val condition: Condition
   internal final var foldedSink: Sink?
   public final val lock: ReentrantLock
   internal final val maxBufferSize: Long

   public final val sink: Sink
      public final get() {
         return this.sink;
      }


   internal final var sinkClosed: Boolean

   public final val source: Source
      public final get() {
         return this.source;
      }


   internal final var sourceClosed: Boolean

   init {
      this.maxBufferSize = var1;
      this.buffer = new Buffer();
      val var3: ReentrantLock = new ReentrantLock();
      this.lock = var3;
      val var4: Condition = var3.newCondition();
      this.condition = var4;
      if (var1 >= 1L) {
         this.sink = new Sink(this) {
            final Pipe this$0;
            private final Timeout timeout;

            {
               this.this$0 = var1;
               this.timeout = new Timeout();
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
               // 000: aload 0
               // 001: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 004: invokevirtual okio/Pipe.getLock ()Ljava/util/concurrent/locks/ReentrantLock;
               // 007: checkcast java/util/concurrent/locks/Lock
               // 00a: astore 7
               // 00c: aload 0
               // 00d: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 010: astore 8
               // 012: aload 7
               // 014: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
               // 019: aload 8
               // 01b: invokevirtual okio/Pipe.getSinkClosed$okio ()Z
               // 01e: istore 5
               // 020: iload 5
               // 022: ifeq 02d
               // 025: aload 7
               // 027: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 02c: return
               // 02d: aload 8
               // 02f: invokevirtual okio/Pipe.getFoldedSink$okio ()Lokio/Sink;
               // 032: astore 6
               // 034: aload 6
               // 036: ifnull 03c
               // 039: goto 076
               // 03c: aload 8
               // 03e: invokevirtual okio/Pipe.getSourceClosed$okio ()Z
               // 041: ifeq 063
               // 044: aload 8
               // 046: invokevirtual okio/Pipe.getBuffer$okio ()Lokio/Buffer;
               // 049: invokevirtual okio/Buffer.size ()J
               // 04c: lconst_0
               // 04d: lcmp
               // 04e: ifgt 054
               // 051: goto 063
               // 054: new java/io/IOException
               // 057: astore 6
               // 059: aload 6
               // 05b: ldc "source is closed"
               // 05d: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // 060: aload 6
               // 062: athrow
               // 063: aload 8
               // 065: bipush 1
               // 066: invokevirtual okio/Pipe.setSinkClosed$okio (Z)V
               // 069: aload 8
               // 06b: invokevirtual okio/Pipe.getCondition ()Ljava/util/concurrent/locks/Condition;
               // 06e: invokeinterface java/util/concurrent/locks/Condition.signalAll ()V 1
               // 073: aconst_null
               // 074: astore 6
               // 076: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 079: astore 8
               // 07b: aload 7
               // 07d: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 082: aload 6
               // 084: ifnull 187
               // 087: aload 0
               // 088: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 08b: astore 8
               // 08d: aload 6
               // 08f: invokeinterface okio/Sink.timeout ()Lokio/Timeout; 1
               // 094: astore 7
               // 096: aload 8
               // 098: invokevirtual okio/Pipe.sink ()Lokio/Sink;
               // 09b: invokeinterface okio/Sink.timeout ()Lokio/Timeout; 1
               // 0a0: astore 8
               // 0a2: aload 7
               // 0a4: invokevirtual okio/Timeout.timeoutNanos ()J
               // 0a7: lstore 1
               // 0a8: aload 7
               // 0aa: getstatic okio/Timeout.Companion Lokio/Timeout$Companion;
               // 0ad: aload 8
               // 0af: invokevirtual okio/Timeout.timeoutNanos ()J
               // 0b2: aload 7
               // 0b4: invokevirtual okio/Timeout.timeoutNanos ()J
               // 0b7: invokevirtual okio/Timeout$Companion.minTimeout (JJ)J
               // 0ba: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 0bd: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 0c0: pop
               // 0c1: aload 7
               // 0c3: invokevirtual okio/Timeout.hasDeadline ()Z
               // 0c6: ifeq 130
               // 0c9: aload 7
               // 0cb: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 0ce: lstore 3
               // 0cf: aload 8
               // 0d1: invokevirtual okio/Timeout.hasDeadline ()Z
               // 0d4: ifeq 0ea
               // 0d7: aload 7
               // 0d9: aload 7
               // 0db: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 0de: aload 8
               // 0e0: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 0e3: invokestatic java/lang/Math.min (JJ)J
               // 0e6: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 0e9: pop
               // 0ea: aload 6
               // 0ec: invokeinterface okio/Sink.close ()V 1
               // 0f1: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 0f4: astore 6
               // 0f6: aload 7
               // 0f8: lload 1
               // 0f9: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 0fc: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 0ff: pop
               // 100: aload 8
               // 102: invokevirtual okio/Timeout.hasDeadline ()Z
               // 105: ifeq 187
               // 108: aload 7
               // 10a: lload 3
               // 10b: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 10e: pop
               // 10f: goto 187
               // 112: astore 6
               // 114: aload 7
               // 116: lload 1
               // 117: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 11a: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 11d: pop
               // 11e: aload 8
               // 120: invokevirtual okio/Timeout.hasDeadline ()Z
               // 123: ifeq 12d
               // 126: aload 7
               // 128: lload 3
               // 129: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 12c: pop
               // 12d: aload 6
               // 12f: athrow
               // 130: aload 8
               // 132: invokevirtual okio/Timeout.hasDeadline ()Z
               // 135: ifeq 143
               // 138: aload 7
               // 13a: aload 8
               // 13c: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 13f: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 142: pop
               // 143: aload 6
               // 145: invokeinterface okio/Sink.close ()V 1
               // 14a: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 14d: astore 6
               // 14f: aload 7
               // 151: lload 1
               // 152: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 155: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 158: pop
               // 159: aload 8
               // 15b: invokevirtual okio/Timeout.hasDeadline ()Z
               // 15e: ifeq 187
               // 161: aload 7
               // 163: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
               // 166: pop
               // 167: goto 187
               // 16a: astore 6
               // 16c: aload 7
               // 16e: lload 1
               // 16f: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 172: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 175: pop
               // 176: aload 8
               // 178: invokevirtual okio/Timeout.hasDeadline ()Z
               // 17b: ifeq 184
               // 17e: aload 7
               // 180: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
               // 183: pop
               // 184: aload 6
               // 186: athrow
               // 187: return
               // 188: astore 6
               // 18a: aload 7
               // 18c: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 191: aload 6
               // 193: athrow
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
               // 000: aload 0
               // 001: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 004: invokevirtual okio/Pipe.getLock ()Ljava/util/concurrent/locks/ReentrantLock;
               // 007: checkcast java/util/concurrent/locks/Lock
               // 00a: astore 6
               // 00c: aload 0
               // 00d: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 010: astore 7
               // 012: aload 6
               // 014: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
               // 019: aload 7
               // 01b: invokevirtual okio/Pipe.getSinkClosed$okio ()Z
               // 01e: ifne 183
               // 021: aload 7
               // 023: invokevirtual okio/Pipe.getCanceled$okio ()Z
               // 026: ifne 174
               // 029: aload 7
               // 02b: invokevirtual okio/Pipe.getFoldedSink$okio ()Lokio/Sink;
               // 02e: astore 5
               // 030: aload 5
               // 032: ifnull 038
               // 035: goto 062
               // 038: aload 7
               // 03a: invokevirtual okio/Pipe.getSourceClosed$okio ()Z
               // 03d: ifeq 05f
               // 040: aload 7
               // 042: invokevirtual okio/Pipe.getBuffer$okio ()Lokio/Buffer;
               // 045: invokevirtual okio/Buffer.size ()J
               // 048: lconst_0
               // 049: lcmp
               // 04a: ifgt 050
               // 04d: goto 05f
               // 050: new java/io/IOException
               // 053: astore 5
               // 055: aload 5
               // 057: ldc "source is closed"
               // 059: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // 05c: aload 5
               // 05e: athrow
               // 05f: aconst_null
               // 060: astore 5
               // 062: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 065: astore 7
               // 067: aload 6
               // 069: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 06e: aload 5
               // 070: ifnull 173
               // 073: aload 0
               // 074: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 077: astore 7
               // 079: aload 5
               // 07b: invokeinterface okio/Sink.timeout ()Lokio/Timeout; 1
               // 080: astore 6
               // 082: aload 7
               // 084: invokevirtual okio/Pipe.sink ()Lokio/Sink;
               // 087: invokeinterface okio/Sink.timeout ()Lokio/Timeout; 1
               // 08c: astore 7
               // 08e: aload 6
               // 090: invokevirtual okio/Timeout.timeoutNanos ()J
               // 093: lstore 1
               // 094: aload 6
               // 096: getstatic okio/Timeout.Companion Lokio/Timeout$Companion;
               // 099: aload 7
               // 09b: invokevirtual okio/Timeout.timeoutNanos ()J
               // 09e: aload 6
               // 0a0: invokevirtual okio/Timeout.timeoutNanos ()J
               // 0a3: invokevirtual okio/Timeout$Companion.minTimeout (JJ)J
               // 0a6: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 0a9: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 0ac: pop
               // 0ad: aload 6
               // 0af: invokevirtual okio/Timeout.hasDeadline ()Z
               // 0b2: ifeq 11c
               // 0b5: aload 6
               // 0b7: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 0ba: lstore 3
               // 0bb: aload 7
               // 0bd: invokevirtual okio/Timeout.hasDeadline ()Z
               // 0c0: ifeq 0d6
               // 0c3: aload 6
               // 0c5: aload 6
               // 0c7: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 0ca: aload 7
               // 0cc: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 0cf: invokestatic java/lang/Math.min (JJ)J
               // 0d2: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 0d5: pop
               // 0d6: aload 5
               // 0d8: invokeinterface okio/Sink.flush ()V 1
               // 0dd: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 0e0: astore 5
               // 0e2: aload 6
               // 0e4: lload 1
               // 0e5: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 0e8: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 0eb: pop
               // 0ec: aload 7
               // 0ee: invokevirtual okio/Timeout.hasDeadline ()Z
               // 0f1: ifeq 173
               // 0f4: aload 6
               // 0f6: lload 3
               // 0f7: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 0fa: pop
               // 0fb: goto 173
               // 0fe: astore 5
               // 100: aload 6
               // 102: lload 1
               // 103: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 106: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 109: pop
               // 10a: aload 7
               // 10c: invokevirtual okio/Timeout.hasDeadline ()Z
               // 10f: ifeq 119
               // 112: aload 6
               // 114: lload 3
               // 115: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 118: pop
               // 119: aload 5
               // 11b: athrow
               // 11c: aload 7
               // 11e: invokevirtual okio/Timeout.hasDeadline ()Z
               // 121: ifeq 12f
               // 124: aload 6
               // 126: aload 7
               // 128: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 12b: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 12e: pop
               // 12f: aload 5
               // 131: invokeinterface okio/Sink.flush ()V 1
               // 136: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 139: astore 5
               // 13b: aload 6
               // 13d: lload 1
               // 13e: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 141: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 144: pop
               // 145: aload 7
               // 147: invokevirtual okio/Timeout.hasDeadline ()Z
               // 14a: ifeq 173
               // 14d: aload 6
               // 14f: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
               // 152: pop
               // 153: goto 173
               // 156: astore 5
               // 158: aload 6
               // 15a: lload 1
               // 15b: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 15e: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 161: pop
               // 162: aload 7
               // 164: invokevirtual okio/Timeout.hasDeadline ()Z
               // 167: ifeq 170
               // 16a: aload 6
               // 16c: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
               // 16f: pop
               // 170: aload 5
               // 172: athrow
               // 173: return
               // 174: new java/io/IOException
               // 177: astore 5
               // 179: aload 5
               // 17b: ldc "canceled"
               // 17d: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // 180: aload 5
               // 182: athrow
               // 183: new java/lang/IllegalStateException
               // 186: astore 5
               // 188: aload 5
               // 18a: ldc "closed"
               // 18c: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
               // 18f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
               // 192: aload 5
               // 194: athrow
               // 195: astore 5
               // 197: aload 6
               // 199: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 19e: aload 5
               // 1a0: athrow
            }

            @Override
            public Timeout timeout() {
               return this.timeout;
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
               // 000: aload 1
               // 001: ldc "source"
               // 003: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullParameter (Ljava/lang/Object;Ljava/lang/String;)V
               // 006: aload 0
               // 007: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 00a: invokevirtual okio/Pipe.getLock ()Ljava/util/concurrent/locks/ReentrantLock;
               // 00d: checkcast java/util/concurrent/locks/Lock
               // 010: astore 10
               // 012: aload 0
               // 013: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 016: astore 11
               // 018: aload 10
               // 01a: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
               // 01f: aload 11
               // 021: invokevirtual okio/Pipe.getSinkClosed$okio ()Z
               // 024: ifne 1e2
               // 027: aload 11
               // 029: invokevirtual okio/Pipe.getCanceled$okio ()Z
               // 02c: istore 8
               // 02e: iload 8
               // 030: ifne 1d6
               // 033: lload 2
               // 034: lconst_0
               // 035: lcmp
               // 036: ifle 0bb
               // 039: aload 11
               // 03b: invokevirtual okio/Pipe.getFoldedSink$okio ()Lokio/Sink;
               // 03e: astore 9
               // 040: aload 9
               // 042: ifnull 048
               // 045: goto 0be
               // 048: aload 11
               // 04a: invokevirtual okio/Pipe.getSourceClosed$okio ()Z
               // 04d: ifne 0af
               // 050: aload 11
               // 052: invokevirtual okio/Pipe.getMaxBufferSize$okio ()J
               // 055: aload 11
               // 057: invokevirtual okio/Pipe.getBuffer$okio ()Lokio/Buffer;
               // 05a: invokevirtual okio/Buffer.size ()J
               // 05d: lsub
               // 05e: lstore 4
               // 060: lload 4
               // 062: lconst_0
               // 063: lcmp
               // 064: ifne 08a
               // 067: aload 0
               // 068: getfield okio/Pipe$sink$1.timeout Lokio/Timeout;
               // 06b: aload 11
               // 06d: invokevirtual okio/Pipe.getCondition ()Ljava/util/concurrent/locks/Condition;
               // 070: invokevirtual okio/Timeout.awaitSignal (Ljava/util/concurrent/locks/Condition;)V
               // 073: aload 11
               // 075: invokevirtual okio/Pipe.getCanceled$okio ()Z
               // 078: ifne 07e
               // 07b: goto 033
               // 07e: new java/io/IOException
               // 081: astore 1
               // 082: aload 1
               // 083: ldc "canceled"
               // 085: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // 088: aload 1
               // 089: athrow
               // 08a: lload 4
               // 08c: lload 2
               // 08d: invokestatic java/lang/Math.min (JJ)J
               // 090: lstore 4
               // 092: aload 11
               // 094: invokevirtual okio/Pipe.getBuffer$okio ()Lokio/Buffer;
               // 097: aload 1
               // 098: lload 4
               // 09a: invokevirtual okio/Buffer.write (Lokio/Buffer;J)V
               // 09d: lload 2
               // 09e: lload 4
               // 0a0: lsub
               // 0a1: lstore 2
               // 0a2: aload 11
               // 0a4: invokevirtual okio/Pipe.getCondition ()Ljava/util/concurrent/locks/Condition;
               // 0a7: invokeinterface java/util/concurrent/locks/Condition.signalAll ()V 1
               // 0ac: goto 033
               // 0af: new java/io/IOException
               // 0b2: astore 1
               // 0b3: aload 1
               // 0b4: ldc "source is closed"
               // 0b6: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // 0b9: aload 1
               // 0ba: athrow
               // 0bb: aconst_null
               // 0bc: astore 9
               // 0be: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 0c1: astore 11
               // 0c3: aload 10
               // 0c5: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 0ca: aload 9
               // 0cc: ifnull 1d5
               // 0cf: aload 0
               // 0d0: getfield okio/Pipe$sink$1.this$0 Lokio/Pipe;
               // 0d3: astore 11
               // 0d5: aload 9
               // 0d7: invokeinterface okio/Sink.timeout ()Lokio/Timeout; 1
               // 0dc: astore 10
               // 0de: aload 11
               // 0e0: invokevirtual okio/Pipe.sink ()Lokio/Sink;
               // 0e3: invokeinterface okio/Sink.timeout ()Lokio/Timeout; 1
               // 0e8: astore 11
               // 0ea: aload 10
               // 0ec: invokevirtual okio/Timeout.timeoutNanos ()J
               // 0ef: lstore 4
               // 0f1: aload 10
               // 0f3: getstatic okio/Timeout.Companion Lokio/Timeout$Companion;
               // 0f6: aload 11
               // 0f8: invokevirtual okio/Timeout.timeoutNanos ()J
               // 0fb: aload 10
               // 0fd: invokevirtual okio/Timeout.timeoutNanos ()J
               // 100: invokevirtual okio/Timeout$Companion.minTimeout (JJ)J
               // 103: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 106: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 109: pop
               // 10a: aload 10
               // 10c: invokevirtual okio/Timeout.hasDeadline ()Z
               // 10f: ifeq 17d
               // 112: aload 10
               // 114: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 117: lstore 6
               // 119: aload 11
               // 11b: invokevirtual okio/Timeout.hasDeadline ()Z
               // 11e: ifeq 134
               // 121: aload 10
               // 123: aload 10
               // 125: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 128: aload 11
               // 12a: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 12d: invokestatic java/lang/Math.min (JJ)J
               // 130: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 133: pop
               // 134: aload 9
               // 136: aload 1
               // 137: lload 2
               // 138: invokeinterface okio/Sink.write (Lokio/Buffer;J)V 4
               // 13d: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 140: astore 1
               // 141: aload 10
               // 143: lload 4
               // 145: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 148: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 14b: pop
               // 14c: aload 11
               // 14e: invokevirtual okio/Timeout.hasDeadline ()Z
               // 151: ifeq 1d5
               // 154: aload 10
               // 156: lload 6
               // 158: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 15b: pop
               // 15c: goto 1d5
               // 15f: astore 1
               // 160: aload 10
               // 162: lload 4
               // 164: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 167: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 16a: pop
               // 16b: aload 11
               // 16d: invokevirtual okio/Timeout.hasDeadline ()Z
               // 170: ifeq 17b
               // 173: aload 10
               // 175: lload 6
               // 177: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 17a: pop
               // 17b: aload 1
               // 17c: athrow
               // 17d: aload 11
               // 17f: invokevirtual okio/Timeout.hasDeadline ()Z
               // 182: ifeq 190
               // 185: aload 10
               // 187: aload 11
               // 189: invokevirtual okio/Timeout.deadlineNanoTime ()J
               // 18c: invokevirtual okio/Timeout.deadlineNanoTime (J)Lokio/Timeout;
               // 18f: pop
               // 190: aload 9
               // 192: aload 1
               // 193: lload 2
               // 194: invokeinterface okio/Sink.write (Lokio/Buffer;J)V 4
               // 199: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
               // 19c: astore 1
               // 19d: aload 10
               // 19f: lload 4
               // 1a1: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 1a4: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 1a7: pop
               // 1a8: aload 11
               // 1aa: invokevirtual okio/Timeout.hasDeadline ()Z
               // 1ad: ifeq 1d5
               // 1b0: aload 10
               // 1b2: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
               // 1b5: pop
               // 1b6: goto 1d5
               // 1b9: astore 1
               // 1ba: aload 10
               // 1bc: lload 4
               // 1be: getstatic java/util/concurrent/TimeUnit.NANOSECONDS Ljava/util/concurrent/TimeUnit;
               // 1c1: invokevirtual okio/Timeout.timeout (JLjava/util/concurrent/TimeUnit;)Lokio/Timeout;
               // 1c4: pop
               // 1c5: aload 11
               // 1c7: invokevirtual okio/Timeout.hasDeadline ()Z
               // 1ca: ifeq 1d3
               // 1cd: aload 10
               // 1cf: invokevirtual okio/Timeout.clearDeadline ()Lokio/Timeout;
               // 1d2: pop
               // 1d3: aload 1
               // 1d4: athrow
               // 1d5: return
               // 1d6: new java/io/IOException
               // 1d9: astore 1
               // 1da: aload 1
               // 1db: ldc "canceled"
               // 1dd: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // 1e0: aload 1
               // 1e1: athrow
               // 1e2: new java/lang/IllegalStateException
               // 1e5: astore 1
               // 1e6: aload 1
               // 1e7: ldc "closed"
               // 1e9: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
               // 1ec: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
               // 1ef: aload 1
               // 1f0: athrow
               // 1f1: astore 1
               // 1f2: aload 10
               // 1f4: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 1f9: aload 1
               // 1fa: athrow
            }
         };
         this.source = new Source(this) {
            final Pipe this$0;
            private final Timeout timeout;

            {
               this.this$0 = var1;
               this.timeout = new Timeout();
            }

            @Override
            public void close() {
               label13: {
                  val var1: Lock = this.this$0.getLock();
                  val var2: Pipe = this.this$0;
                  var1.lock();

                  try {
                     var2.setSourceClosed$okio(true);
                     var2.getCondition().signalAll();
                  } catch (var3: java.lang.Throwable) {
                     var1.unlock();
                  }

                  var1.unlock();
               }
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
               // 07: getfield okio/Pipe$source$1.this$0 Lokio/Pipe;
               // 0a: invokevirtual okio/Pipe.getLock ()Ljava/util/concurrent/locks/ReentrantLock;
               // 0d: checkcast java/util/concurrent/locks/Lock
               // 10: astore 5
               // 12: aload 0
               // 13: getfield okio/Pipe$source$1.this$0 Lokio/Pipe;
               // 16: astore 6
               // 18: aload 5
               // 1a: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
               // 1f: aload 6
               // 21: invokevirtual okio/Pipe.getSourceClosed$okio ()Z
               // 24: ifne a4
               // 27: aload 6
               // 29: invokevirtual okio/Pipe.getCanceled$okio ()Z
               // 2c: istore 4
               // 2e: iload 4
               // 30: ifne 98
               // 33: aload 6
               // 35: invokevirtual okio/Pipe.getBuffer$okio ()Lokio/Buffer;
               // 38: invokevirtual okio/Buffer.size ()J
               // 3b: lconst_0
               // 3c: lcmp
               // 3d: ifne 7a
               // 40: aload 6
               // 42: invokevirtual okio/Pipe.getSinkClosed$okio ()Z
               // 45: istore 4
               // 47: iload 4
               // 49: ifeq 57
               // 4c: aload 5
               // 4e: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 53: ldc2_w -1
               // 56: lreturn
               // 57: aload 0
               // 58: getfield okio/Pipe$source$1.timeout Lokio/Timeout;
               // 5b: aload 6
               // 5d: invokevirtual okio/Pipe.getCondition ()Ljava/util/concurrent/locks/Condition;
               // 60: invokevirtual okio/Timeout.awaitSignal (Ljava/util/concurrent/locks/Condition;)V
               // 63: aload 6
               // 65: invokevirtual okio/Pipe.getCanceled$okio ()Z
               // 68: ifne 6e
               // 6b: goto 33
               // 6e: new java/io/IOException
               // 71: astore 1
               // 72: aload 1
               // 73: ldc "canceled"
               // 75: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // 78: aload 1
               // 79: athrow
               // 7a: aload 6
               // 7c: invokevirtual okio/Pipe.getBuffer$okio ()Lokio/Buffer;
               // 7f: aload 1
               // 80: lload 2
               // 81: invokevirtual okio/Buffer.read (Lokio/Buffer;J)J
               // 84: lstore 2
               // 85: aload 6
               // 87: invokevirtual okio/Pipe.getCondition ()Ljava/util/concurrent/locks/Condition;
               // 8a: invokeinterface java/util/concurrent/locks/Condition.signalAll ()V 1
               // 8f: aload 5
               // 91: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // 96: lload 2
               // 97: lreturn
               // 98: new java/io/IOException
               // 9b: astore 1
               // 9c: aload 1
               // 9d: ldc "canceled"
               // 9f: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
               // a2: aload 1
               // a3: athrow
               // a4: new java/lang/IllegalStateException
               // a7: astore 1
               // a8: aload 1
               // a9: ldc "closed"
               // ab: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
               // ae: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
               // b1: aload 1
               // b2: athrow
               // b3: astore 1
               // b4: aload 5
               // b6: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
               // bb: aload 1
               // bc: athrow
            }

            @Override
            public Timeout timeout() {
               return this.timeout;
            }
         };
      } else {
         val var5: StringBuilder = new StringBuilder("maxBufferSize < 1: ");
         var5.append(var1);
         throw new IllegalArgumentException(var5.toString().toString());
      }
   }

   private inline fun Sink.forward(block: (Sink) -> Unit) {
      val var8: Timeout = var1.timeout();
      val var7: Timeout = this.sink().timeout();
      val var5: Long = var8.timeoutNanos();
      var8.timeout(Timeout.Companion.minTimeout(var7.timeoutNanos(), var8.timeoutNanos()), TimeUnit.NANOSECONDS);
      label43:
      if (var8.hasDeadline()) {
         val var3: Long = var8.deadlineNanoTime();
         if (var7.hasDeadline()) {
            var8.deadlineNanoTime(Math.min(var8.deadlineNanoTime(), var7.deadlineNanoTime()));
         }

         try {
            var2.invoke(var1);
         } catch (var10: java.lang.Throwable) {
            var8.timeout(var5, TimeUnit.NANOSECONDS);
            if (var7.hasDeadline()) {
               var8.deadlineNanoTime(var3);
            }
         }

         var8.timeout(var5, TimeUnit.NANOSECONDS);
         if (var7.hasDeadline()) {
            var8.deadlineNanoTime(var3);
         }
      } else {
         label46: {
            if (var7.hasDeadline()) {
               var8.deadlineNanoTime(var7.deadlineNanoTime());
            }

            try {
               var2.invoke(var1);
            } catch (var9: java.lang.Throwable) {
               var8.timeout(var5, TimeUnit.NANOSECONDS);
               if (var7.hasDeadline()) {
                  var8.clearDeadline();
               }
            }

            var8.timeout(var5, TimeUnit.NANOSECONDS);
            if (var7.hasDeadline()) {
               var8.clearDeadline();
            }
         }
      }
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sink", imports = []))
   public fun sink(): Sink {
      return this.sink;
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "source", imports = []))
   public fun source(): Source {
      return this.source;
   }

   public fun cancel() {
      label13: {
         val var1: Lock = this.lock;
         this.lock.lock();

         try {
            this.canceled = true;
            this.buffer.clear();
            this.condition.signalAll();
         } catch (var3: java.lang.Throwable) {
            var1.unlock();
         }

         var1.unlock();
      }
   }

   @Throws(java/io/IOException::class)
   public fun fold(sink: Sink) {
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
      // 07: getfield okio/Pipe.lock Ljava/util/concurrent/locks/ReentrantLock;
      // 0a: checkcast java/util/concurrent/locks/Lock
      // 0d: astore 3
      // 0e: aload 3
      // 0f: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
      // 14: aload 0
      // 15: getfield okio/Pipe.foldedSink Lokio/Sink;
      // 18: ifnonnull d9
      // 1b: aload 0
      // 1c: getfield okio/Pipe.canceled Z
      // 1f: ifne c8
      // 22: aload 0
      // 23: getfield okio/Pipe.buffer Lokio/Buffer;
      // 26: invokevirtual okio/Buffer.exhausted ()Z
      // 29: ifeq 3d
      // 2c: aload 0
      // 2d: bipush 1
      // 2e: putfield okio/Pipe.sourceClosed Z
      // 31: aload 0
      // 32: aload 1
      // 33: putfield okio/Pipe.foldedSink Lokio/Sink;
      // 36: aload 3
      // 37: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 3c: return
      // 3d: aload 0
      // 3e: getfield okio/Pipe.sinkClosed Z
      // 41: istore 2
      // 42: new okio/Buffer
      // 45: astore 4
      // 47: aload 4
      // 49: invokespecial okio/Buffer.<init> ()V
      // 4c: aload 0
      // 4d: getfield okio/Pipe.buffer Lokio/Buffer;
      // 50: astore 5
      // 52: aload 4
      // 54: aload 5
      // 56: aload 5
      // 58: invokevirtual okio/Buffer.size ()J
      // 5b: invokevirtual okio/Buffer.write (Lokio/Buffer;J)V
      // 5e: aload 0
      // 5f: getfield okio/Pipe.condition Ljava/util/concurrent/locks/Condition;
      // 62: invokeinterface java/util/concurrent/locks/Condition.signalAll ()V 1
      // 67: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 6a: astore 5
      // 6c: aload 3
      // 6d: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // 72: aload 1
      // 73: aload 4
      // 75: aload 4
      // 77: invokevirtual okio/Buffer.size ()J
      // 7a: invokeinterface okio/Sink.write (Lokio/Buffer;J)V 4
      // 7f: iload 2
      // 80: ifeq 8c
      // 83: aload 1
      // 84: invokeinterface okio/Sink.close ()V 1
      // 89: goto 06
      // 8c: aload 1
      // 8d: invokeinterface okio/Sink.flush ()V 1
      // 92: goto 06
      // 95: astore 3
      // 96: aload 0
      // 97: getfield okio/Pipe.lock Ljava/util/concurrent/locks/ReentrantLock;
      // 9a: checkcast java/util/concurrent/locks/Lock
      // 9d: astore 1
      // 9e: aload 1
      // 9f: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
      // a4: aload 0
      // a5: bipush 1
      // a6: putfield okio/Pipe.sourceClosed Z
      // a9: aload 0
      // aa: getfield okio/Pipe.condition Ljava/util/concurrent/locks/Condition;
      // ad: invokeinterface java/util/concurrent/locks/Condition.signalAll ()V 1
      // b2: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // b5: astore 4
      // b7: aload 1
      // b8: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // bd: aload 3
      // be: athrow
      // bf: astore 3
      // c0: aload 1
      // c1: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // c6: aload 3
      // c7: athrow
      // c8: aload 0
      // c9: aload 1
      // ca: putfield okio/Pipe.foldedSink Lokio/Sink;
      // cd: new java/io/IOException
      // d0: astore 1
      // d1: aload 1
      // d2: ldc "canceled"
      // d4: invokespecial java/io/IOException.<init> (Ljava/lang/String;)V
      // d7: aload 1
      // d8: athrow
      // d9: new java/lang/IllegalStateException
      // dc: astore 1
      // dd: aload 1
      // de: ldc "sink already folded"
      // e0: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // e3: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // e6: aload 1
      // e7: athrow
      // e8: astore 1
      // e9: aload 3
      // ea: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
      // ef: aload 1
      // f0: athrow
   }
}
