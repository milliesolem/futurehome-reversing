package kotlinx.coroutines.stream

import j..util.stream.Stream
import kotlinx.atomicfu.AtomicBoolean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

private class StreamFlow<T>(stream: Stream<Any>) : Flow<T> {
   private final val stream: Stream<Any>
   private final val consumed: AtomicBoolean

   init {
      this.stream = var1;
      this.consumed = 0;
   }

   public override suspend fun collect(collector: FlowCollector<Any>) {
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
      // 000: aload 2
      // 001: instanceof kotlinx/coroutines/stream/StreamFlow$collect$1
      // 004: ifeq 028
      // 007: aload 2
      // 008: checkcast kotlinx/coroutines/stream/StreamFlow$collect$1
      // 00b: astore 4
      // 00d: aload 4
      // 00f: getfield kotlinx/coroutines/stream/StreamFlow$collect$1.label I
      // 012: ldc -2147483648
      // 014: iand
      // 015: ifeq 028
      // 018: aload 4
      // 01a: aload 4
      // 01c: getfield kotlinx/coroutines/stream/StreamFlow$collect$1.label I
      // 01f: ldc -2147483648
      // 021: iadd
      // 022: putfield kotlinx/coroutines/stream/StreamFlow$collect$1.label I
      // 025: goto 033
      // 028: new kotlinx/coroutines/stream/StreamFlow$collect$1
      // 02b: dup
      // 02c: aload 0
      // 02d: aload 2
      // 02e: invokespecial kotlinx/coroutines/stream/StreamFlow$collect$1.<init> (Lkotlinx/coroutines/stream/StreamFlow;Lkotlin/coroutines/Continuation;)V
      // 031: astore 4
      // 033: aload 4
      // 035: getfield kotlinx/coroutines/stream/StreamFlow$collect$1.result Ljava/lang/Object;
      // 038: astore 8
      // 03a: invokestatic kotlin/coroutines/intrinsics/IntrinsicsKt.getCOROUTINE_SUSPENDED ()Ljava/lang/Object;
      // 03d: astore 7
      // 03f: aload 4
      // 041: getfield kotlinx/coroutines/stream/StreamFlow$collect$1.label I
      // 044: istore 3
      // 045: iload 3
      // 046: ifeq 083
      // 049: iload 3
      // 04a: bipush 1
      // 04b: if_icmpne 079
      // 04e: aload 4
      // 050: getfield kotlinx/coroutines/stream/StreamFlow$collect$1.L$2 Ljava/lang/Object;
      // 053: checkcast java/util/Iterator
      // 056: astore 6
      // 058: aload 4
      // 05a: getfield kotlinx/coroutines/stream/StreamFlow$collect$1.L$1 Ljava/lang/Object;
      // 05d: checkcast kotlinx/coroutines/flow/FlowCollector
      // 060: astore 5
      // 062: aload 4
      // 064: getfield kotlinx/coroutines/stream/StreamFlow$collect$1.L$0 Ljava/lang/Object;
      // 067: checkcast kotlinx/coroutines/stream/StreamFlow
      // 06a: astore 2
      // 06b: aload 2
      // 06c: astore 1
      // 06d: aload 8
      // 06f: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 072: goto 0a4
      // 075: astore 2
      // 076: goto 106
      // 079: new java/lang/IllegalStateException
      // 07c: dup
      // 07d: ldc "call to 'resume' before 'invoke' with coroutine"
      // 07f: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 082: athrow
      // 083: aload 8
      // 085: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 088: getstatic kotlinx/coroutines/stream/StreamFlow.consumed$FU Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
      // 08b: aload 0
      // 08c: bipush 0
      // 08d: bipush 1
      // 08e: invokevirtual java/util/concurrent/atomic/AtomicIntegerFieldUpdater.compareAndSet (Ljava/lang/Object;II)Z
      // 091: ifeq 111
      // 094: aload 0
      // 095: getfield kotlinx/coroutines/stream/StreamFlow.stream Lj$/util/stream/Stream;
      // 098: invokeinterface j$/util/stream/Stream.iterator ()Ljava/util/Iterator; 1
      // 09d: astore 6
      // 09f: aload 0
      // 0a0: astore 2
      // 0a1: aload 1
      // 0a2: astore 5
      // 0a4: aload 2
      // 0a5: astore 1
      // 0a6: aload 6
      // 0a8: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0ad: ifeq 0f6
      // 0b0: aload 2
      // 0b1: astore 1
      // 0b2: aload 6
      // 0b4: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b9: astore 8
      // 0bb: aload 2
      // 0bc: astore 1
      // 0bd: aload 4
      // 0bf: aload 2
      // 0c0: putfield kotlinx/coroutines/stream/StreamFlow$collect$1.L$0 Ljava/lang/Object;
      // 0c3: aload 2
      // 0c4: astore 1
      // 0c5: aload 4
      // 0c7: aload 5
      // 0c9: putfield kotlinx/coroutines/stream/StreamFlow$collect$1.L$1 Ljava/lang/Object;
      // 0cc: aload 2
      // 0cd: astore 1
      // 0ce: aload 4
      // 0d0: aload 6
      // 0d2: putfield kotlinx/coroutines/stream/StreamFlow$collect$1.L$2 Ljava/lang/Object;
      // 0d5: aload 2
      // 0d6: astore 1
      // 0d7: aload 4
      // 0d9: bipush 1
      // 0da: putfield kotlinx/coroutines/stream/StreamFlow$collect$1.label I
      // 0dd: aload 2
      // 0de: astore 1
      // 0df: aload 5
      // 0e1: aload 8
      // 0e3: aload 4
      // 0e5: invokeinterface kotlinx/coroutines/flow/FlowCollector.emit (Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object; 3
      // 0ea: astore 8
      // 0ec: aload 8
      // 0ee: aload 7
      // 0f0: if_acmpne 0a4
      // 0f3: aload 7
      // 0f5: areturn
      // 0f6: aload 2
      // 0f7: getfield kotlinx/coroutines/stream/StreamFlow.stream Lj$/util/stream/Stream;
      // 0fa: invokeinterface j$/util/stream/Stream.close ()V 1
      // 0ff: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 102: areturn
      // 103: astore 2
      // 104: aload 0
      // 105: astore 1
      // 106: aload 1
      // 107: getfield kotlinx/coroutines/stream/StreamFlow.stream Lj$/util/stream/Stream;
      // 10a: invokeinterface j$/util/stream/Stream.close ()V 1
      // 10f: aload 2
      // 110: athrow
      // 111: new java/lang/IllegalStateException
      // 114: dup
      // 115: ldc "Stream.consumeAsFlow can be collected only once"
      // 117: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 11a: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
      // 11d: athrow
   }
}
