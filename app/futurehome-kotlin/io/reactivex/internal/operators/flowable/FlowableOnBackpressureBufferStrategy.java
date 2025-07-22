package io.reactivex.internal.operators.flowable;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Action;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureBufferStrategy<T> extends AbstractFlowableWithUpstream<T, T> {
   final long bufferSize;
   final Action onOverflow;
   final BackpressureOverflowStrategy strategy;

   public FlowableOnBackpressureBufferStrategy(Flowable<T> var1, long var2, Action var4, BackpressureOverflowStrategy var5) {
      super(var1);
      this.bufferSize = var2;
      this.onOverflow = var4;
      this.strategy = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source
         .subscribe(new FlowableOnBackpressureBufferStrategy.OnBackpressureBufferStrategySubscriber<>(var1, this.onOverflow, this.strategy, this.bufferSize));
   }

   static final class OnBackpressureBufferStrategySubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 3240706908776709697L;
      final long bufferSize;
      volatile boolean cancelled;
      final Deque<T> deque;
      volatile boolean done;
      final Subscriber<? super T> downstream;
      Throwable error;
      final Action onOverflow;
      final AtomicLong requested;
      final BackpressureOverflowStrategy strategy;
      Subscription upstream;

      OnBackpressureBufferStrategySubscriber(Subscriber<? super T> var1, Action var2, BackpressureOverflowStrategy var3, long var4) {
         this.downstream = var1;
         this.onOverflow = var2;
         this.strategy = var3;
         this.bufferSize = var4;
         this.requested = new AtomicLong();
         this.deque = new ArrayDeque<>();
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         if (this.getAndIncrement() == 0) {
            this.clear(this.deque);
         }
      }

      void clear(Deque<T> param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 0: aload 1
         // 1: monitorenter
         // 2: aload 1
         // 3: invokeinterface java/util/Deque.clear ()V 1
         // 8: aload 1
         // 9: monitorexit
         // a: return
         // b: astore 2
         // c: aload 1
         // d: monitorexit
         // e: aload 2
         // f: athrow
      }

      void drain() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 000: aload 0
         // 001: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.getAndIncrement ()I
         // 004: ifeq 008
         // 007: return
         // 008: aload 0
         // 009: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.deque Ljava/util/Deque;
         // 00c: astore 10
         // 00e: aload 0
         // 00f: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 012: astore 11
         // 014: bipush 1
         // 015: istore 1
         // 016: aload 0
         // 017: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.requested Ljava/util/concurrent/atomic/AtomicLong;
         // 01a: invokevirtual java/util/concurrent/atomic/AtomicLong.get ()J
         // 01d: lstore 6
         // 01f: lconst_0
         // 020: lstore 4
         // 022: lload 4
         // 024: lload 6
         // 026: lcmp
         // 027: istore 3
         // 028: iload 3
         // 029: ifeq 0a8
         // 02c: aload 0
         // 02d: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.cancelled Z
         // 030: ifeq 03a
         // 033: aload 0
         // 034: aload 10
         // 036: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.clear (Ljava/util/Deque;)V
         // 039: return
         // 03a: aload 0
         // 03b: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.done Z
         // 03e: istore 8
         // 040: aload 10
         // 042: monitorenter
         // 043: aload 10
         // 045: invokeinterface java/util/Deque.poll ()Ljava/lang/Object; 1
         // 04a: astore 12
         // 04c: aload 10
         // 04e: monitorexit
         // 04f: aload 12
         // 051: ifnonnull 059
         // 054: bipush 1
         // 055: istore 2
         // 056: goto 05b
         // 059: bipush 0
         // 05a: istore 2
         // 05b: iload 8
         // 05d: ifeq 087
         // 060: aload 0
         // 061: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.error Ljava/lang/Throwable;
         // 064: astore 13
         // 066: aload 13
         // 068: ifnull 07b
         // 06b: aload 0
         // 06c: aload 10
         // 06e: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.clear (Ljava/util/Deque;)V
         // 071: aload 11
         // 073: aload 13
         // 075: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 07a: return
         // 07b: iload 2
         // 07c: ifeq 087
         // 07f: aload 11
         // 081: invokeinterface org/reactivestreams/Subscriber.onComplete ()V 1
         // 086: return
         // 087: iload 2
         // 088: ifeq 08e
         // 08b: goto 0a8
         // 08e: aload 11
         // 090: aload 12
         // 092: invokeinterface org/reactivestreams/Subscriber.onNext (Ljava/lang/Object;)V 2
         // 097: lload 4
         // 099: lconst_1
         // 09a: ladd
         // 09b: lstore 4
         // 09d: goto 022
         // 0a0: astore 11
         // 0a2: aload 10
         // 0a4: monitorexit
         // 0a5: aload 11
         // 0a7: athrow
         // 0a8: iload 3
         // 0a9: ifne 104
         // 0ac: aload 0
         // 0ad: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.cancelled Z
         // 0b0: ifeq 0ba
         // 0b3: aload 0
         // 0b4: aload 10
         // 0b6: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.clear (Ljava/util/Deque;)V
         // 0b9: return
         // 0ba: aload 0
         // 0bb: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.done Z
         // 0be: istore 9
         // 0c0: aload 10
         // 0c2: monitorenter
         // 0c3: aload 10
         // 0c5: invokeinterface java/util/Deque.isEmpty ()Z 1
         // 0ca: istore 8
         // 0cc: aload 10
         // 0ce: monitorexit
         // 0cf: iload 9
         // 0d1: ifeq 104
         // 0d4: aload 0
         // 0d5: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.error Ljava/lang/Throwable;
         // 0d8: astore 12
         // 0da: aload 12
         // 0dc: ifnull 0ef
         // 0df: aload 0
         // 0e0: aload 10
         // 0e2: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.clear (Ljava/util/Deque;)V
         // 0e5: aload 11
         // 0e7: aload 12
         // 0e9: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 0ee: return
         // 0ef: iload 8
         // 0f1: ifeq 104
         // 0f4: aload 11
         // 0f6: invokeinterface org/reactivestreams/Subscriber.onComplete ()V 1
         // 0fb: return
         // 0fc: astore 11
         // 0fe: aload 10
         // 100: monitorexit
         // 101: aload 11
         // 103: athrow
         // 104: lload 4
         // 106: lconst_0
         // 107: lcmp
         // 108: ifeq 115
         // 10b: aload 0
         // 10c: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.requested Ljava/util/concurrent/atomic/AtomicLong;
         // 10f: lload 4
         // 111: invokestatic io/reactivex/internal/util/BackpressureHelper.produced (Ljava/util/concurrent/atomic/AtomicLong;J)J
         // 114: pop2
         // 115: aload 0
         // 116: iload 1
         // 117: ineg
         // 118: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.addAndGet (I)I
         // 11b: istore 2
         // 11c: iload 2
         // 11d: istore 1
         // 11e: iload 2
         // 11f: ifne 016
         // 122: return
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            this.drain();
         }
      }

      public void onNext(T param1) {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.done Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.deque Ljava/util/Deque;
         // 0c: astore 9
         // 0e: aload 9
         // 10: monitorenter
         // 11: aload 9
         // 13: invokeinterface java/util/Deque.size ()I 1
         // 18: i2l
         // 19: lstore 7
         // 1b: aload 0
         // 1c: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.bufferSize J
         // 1f: lstore 5
         // 21: bipush 0
         // 22: istore 3
         // 23: lload 7
         // 25: lload 5
         // 27: lcmp
         // 28: ifne 75
         // 2b: getstatic io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$1.$SwitchMap$io$reactivex$BackpressureOverflowStrategy [I
         // 2e: aload 0
         // 2f: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.strategy Lio/reactivex/BackpressureOverflowStrategy;
         // 32: invokevirtual io/reactivex/BackpressureOverflowStrategy.ordinal ()I
         // 35: iaload
         // 36: istore 4
         // 38: bipush 1
         // 39: istore 2
         // 3a: iload 4
         // 3c: bipush 1
         // 3d: if_icmpeq 5d
         // 40: iload 4
         // 42: bipush 2
         // 43: if_icmpeq 49
         // 46: goto 80
         // 49: aload 9
         // 4b: invokeinterface java/util/Deque.poll ()Ljava/lang/Object; 1
         // 50: pop
         // 51: aload 9
         // 53: aload 1
         // 54: invokeinterface java/util/Deque.offer (Ljava/lang/Object;)Z 2
         // 59: pop
         // 5a: goto 6e
         // 5d: aload 9
         // 5f: invokeinterface java/util/Deque.pollLast ()Ljava/lang/Object; 1
         // 64: pop
         // 65: aload 9
         // 67: aload 1
         // 68: invokeinterface java/util/Deque.offer (Ljava/lang/Object;)Z 2
         // 6d: pop
         // 6e: bipush 0
         // 6f: istore 2
         // 70: bipush 1
         // 71: istore 3
         // 72: goto 80
         // 75: aload 9
         // 77: aload 1
         // 78: invokeinterface java/util/Deque.offer (Ljava/lang/Object;)Z 2
         // 7d: pop
         // 7e: bipush 0
         // 7f: istore 2
         // 80: aload 9
         // 82: monitorexit
         // 83: iload 3
         // 84: ifeq af
         // 87: aload 0
         // 88: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.onOverflow Lio/reactivex/functions/Action;
         // 8b: astore 1
         // 8c: aload 1
         // 8d: ifnull ce
         // 90: aload 1
         // 91: invokeinterface io/reactivex/functions/Action.run ()V 1
         // 96: goto ce
         // 99: astore 1
         // 9a: aload 1
         // 9b: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 9e: aload 0
         // 9f: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.upstream Lorg/reactivestreams/Subscription;
         // a2: invokeinterface org/reactivestreams/Subscription.cancel ()V 1
         // a7: aload 0
         // a8: aload 1
         // a9: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.onError (Ljava/lang/Throwable;)V
         // ac: goto ce
         // af: iload 2
         // b0: ifeq ca
         // b3: aload 0
         // b4: getfield io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.upstream Lorg/reactivestreams/Subscription;
         // b7: invokeinterface org/reactivestreams/Subscription.cancel ()V 1
         // bc: aload 0
         // bd: new io/reactivex/exceptions/MissingBackpressureException
         // c0: dup
         // c1: invokespecial io/reactivex/exceptions/MissingBackpressureException.<init> ()V
         // c4: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.onError (Ljava/lang/Throwable;)V
         // c7: goto ce
         // ca: aload 0
         // cb: invokevirtual io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber.drain ()V
         // ce: return
         // cf: astore 1
         // d0: aload 9
         // d2: monitorexit
         // d3: aload 1
         // d4: athrow
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }
   }
}
