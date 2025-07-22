package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferTimed<T, U extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, U> {
   final Callable<U> bufferSupplier;
   final int maxSize;
   final boolean restartTimerOnMaxSize;
   final Scheduler scheduler;
   final long timeskip;
   final long timespan;
   final TimeUnit unit;

   public FlowableBufferTimed(Flowable<T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, Callable<U> var8, int var9, boolean var10) {
      super(var1);
      this.timespan = var2;
      this.timeskip = var4;
      this.unit = var6;
      this.scheduler = var7;
      this.bufferSupplier = var8;
      this.maxSize = var9;
      this.restartTimerOnMaxSize = var10;
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      if (this.timespan == this.timeskip && this.maxSize == Integer.MAX_VALUE) {
         this.source
            .subscribe(
               new FlowableBufferTimed.BufferExactUnboundedSubscriber<>(
                  new SerializedSubscriber(var1), this.bufferSupplier, this.timespan, this.unit, this.scheduler
               )
            );
      } else {
         Scheduler.Worker var2 = this.scheduler.createWorker();
         if (this.timespan == this.timeskip) {
            this.source
               .subscribe(
                  new FlowableBufferTimed.BufferExactBoundedSubscriber<>(
                     new SerializedSubscriber(var1), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, var2
                  )
               );
         } else {
            this.source
               .subscribe(
                  new FlowableBufferTimed.BufferSkipBoundedSubscriber<>(
                     new SerializedSubscriber(var1), this.bufferSupplier, this.timespan, this.timeskip, this.unit, var2
                  )
               );
         }
      }
   }

   static final class BufferExactBoundedSubscriber<T, U extends Collection<? super T>>
      extends QueueDrainSubscriber<T, U, U>
      implements Subscription,
      Runnable,
      Disposable {
      U buffer;
      final Callable<U> bufferSupplier;
      long consumerIndex;
      final int maxSize;
      long producerIndex;
      final boolean restartTimerOnMaxSize;
      Disposable timer;
      final long timespan;
      final TimeUnit unit;
      Subscription upstream;
      final Scheduler.Worker w;

      BufferExactBoundedSubscriber(Subscriber<? super U> var1, Callable<U> var2, long var3, TimeUnit var5, int var6, boolean var7, Scheduler.Worker var8) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.timespan = var3;
         this.unit = var5;
         this.maxSize = var6;
         this.restartTimerOnMaxSize = var7;
         this.w = var8;
      }

      public boolean accept(Subscriber<? super U> var1, U var2) {
         var1.onNext(var2);
         return true;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.dispose();
         }
      }

      @Override
      public void dispose() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: aconst_null
         // 04: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 07: aload 0
         // 08: monitorexit
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.upstream Lorg/reactivestreams/Subscription;
         // 0d: invokeinterface org/reactivestreams/Subscription.cancel ()V 1
         // 12: aload 0
         // 13: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.w Lio/reactivex/Scheduler$Worker;
         // 16: invokevirtual io/reactivex/Scheduler$Worker.dispose ()V
         // 19: return
         // 1a: astore 1
         // 1b: aload 0
         // 1c: monitorexit
         // 1d: aload 1
         // 1e: athrow
      }

      @Override
      public boolean isDisposed() {
         return this.w.isDisposed();
      }

      public void onComplete() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 06: astore 1
         // 07: aload 0
         // 08: aconst_null
         // 09: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 0c: aload 0
         // 0d: monitorexit
         // 0e: aload 1
         // 0f: ifnull 3e
         // 12: aload 0
         // 13: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 16: aload 1
         // 17: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 1c: pop
         // 1d: aload 0
         // 1e: bipush 1
         // 1f: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.done Z
         // 22: aload 0
         // 23: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.enter ()Z
         // 26: ifeq 37
         // 29: aload 0
         // 2a: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 2d: aload 0
         // 2e: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 31: bipush 0
         // 32: aload 0
         // 33: aload 0
         // 34: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainMaxLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lorg/reactivestreams/Subscriber;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/QueueDrain;)V
         // 37: aload 0
         // 38: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.w Lio/reactivex/Scheduler$Worker;
         // 3b: invokevirtual io/reactivex/Scheduler$Worker.dispose ()V
         // 3e: return
         // 3f: astore 1
         // 40: aload 0
         // 41: monitorexit
         // 42: aload 1
         // 43: athrow
      }

      public void onError(Throwable param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: aconst_null
         // 04: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 07: aload 0
         // 08: monitorexit
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 0d: aload 1
         // 0e: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 13: aload 0
         // 14: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.w Lio/reactivex/Scheduler$Worker;
         // 17: invokevirtual io/reactivex/Scheduler$Worker.dispose ()V
         // 1a: return
         // 1b: astore 1
         // 1c: aload 0
         // 1d: monitorexit
         // 1e: aload 1
         // 1f: athrow
      }

      public void onNext(T param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 06: astore 4
         // 08: aload 4
         // 0a: ifnonnull 10
         // 0d: aload 0
         // 0e: monitorexit
         // 0f: return
         // 10: aload 4
         // 12: aload 1
         // 13: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
         // 18: pop
         // 19: aload 4
         // 1b: invokeinterface java/util/Collection.size ()I 1
         // 20: aload 0
         // 21: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.maxSize I
         // 24: if_icmpge 2a
         // 27: aload 0
         // 28: monitorexit
         // 29: return
         // 2a: aload 0
         // 2b: aconst_null
         // 2c: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 2f: aload 0
         // 30: aload 0
         // 31: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.producerIndex J
         // 34: lconst_1
         // 35: ladd
         // 36: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.producerIndex J
         // 39: aload 0
         // 3a: monitorexit
         // 3b: aload 0
         // 3c: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.restartTimerOnMaxSize Z
         // 3f: ifeq 4b
         // 42: aload 0
         // 43: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.timer Lio/reactivex/disposables/Disposable;
         // 46: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
         // 4b: aload 0
         // 4c: aload 4
         // 4e: bipush 0
         // 4f: aload 0
         // 50: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.fastPathOrderedEmitMax (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
         // 53: aload 0
         // 54: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.bufferSupplier Ljava/util/concurrent/Callable;
         // 57: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 5c: ldc "The supplied buffer is null"
         // 5e: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 61: checkcast java/util/Collection
         // 64: astore 1
         // 65: aload 0
         // 66: monitorenter
         // 67: aload 0
         // 68: aload 1
         // 69: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 6c: aload 0
         // 6d: aload 0
         // 6e: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.consumerIndex J
         // 71: lconst_1
         // 72: ladd
         // 73: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.consumerIndex J
         // 76: aload 0
         // 77: monitorexit
         // 78: aload 0
         // 79: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.restartTimerOnMaxSize Z
         // 7c: ifeq 98
         // 7f: aload 0
         // 80: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.w Lio/reactivex/Scheduler$Worker;
         // 83: astore 1
         // 84: aload 0
         // 85: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.timespan J
         // 88: lstore 2
         // 89: aload 0
         // 8a: aload 1
         // 8b: aload 0
         // 8c: lload 2
         // 8d: lload 2
         // 8e: aload 0
         // 8f: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.unit Ljava/util/concurrent/TimeUnit;
         // 92: invokevirtual io/reactivex/Scheduler$Worker.schedulePeriodically (Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
         // 95: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.timer Lio/reactivex/disposables/Disposable;
         // 98: return
         // 99: astore 1
         // 9a: aload 0
         // 9b: monitorexit
         // 9c: aload 1
         // 9d: athrow
         // 9e: astore 1
         // 9f: aload 1
         // a0: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // a3: aload 0
         // a4: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.cancel ()V
         // a7: aload 0
         // a8: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // ab: aload 1
         // ac: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // b1: return
         // b2: astore 1
         // b3: aload 0
         // b4: monitorexit
         // b5: aload 1
         // b6: athrow
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            Collection var4;
            try {
               var4 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               this.w.dispose();
               var1.cancel();
               EmptySubscription.error(var6, this.downstream);
               return;
            }

            this.buffer = (U)var4;
            this.downstream.onSubscribe(this);
            Scheduler.Worker var7 = this.w;
            long var2 = this.timespan;
            this.timer = var7.schedulePeriodically(this, var2, var2, this.unit);
            var1.request(Long.MAX_VALUE);
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }

      @Override
      public void run() {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The supplied buffer is null"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 2
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 18: astore 1
         // 19: aload 1
         // 1a: ifnull 3b
         // 1d: aload 0
         // 1e: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.producerIndex J
         // 21: aload 0
         // 22: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.consumerIndex J
         // 25: lcmp
         // 26: ifeq 2c
         // 29: goto 3b
         // 2c: aload 0
         // 2d: aload 2
         // 2e: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.buffer Ljava/util/Collection;
         // 31: aload 0
         // 32: monitorexit
         // 33: aload 0
         // 34: aload 1
         // 35: bipush 0
         // 36: aload 0
         // 37: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.fastPathOrderedEmitMax (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
         // 3a: return
         // 3b: aload 0
         // 3c: monitorexit
         // 3d: return
         // 3e: astore 1
         // 3f: aload 0
         // 40: monitorexit
         // 41: aload 1
         // 42: athrow
         // 43: astore 1
         // 44: aload 1
         // 45: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 48: aload 0
         // 49: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.cancel ()V
         // 4c: aload 0
         // 4d: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactBoundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 50: aload 1
         // 51: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 56: return
      }
   }

   static final class BufferExactUnboundedSubscriber<T, U extends Collection<? super T>>
      extends QueueDrainSubscriber<T, U, U>
      implements Subscription,
      Runnable,
      Disposable {
      U buffer;
      final Callable<U> bufferSupplier;
      final Scheduler scheduler;
      final AtomicReference<Disposable> timer = new AtomicReference<>();
      final long timespan;
      final TimeUnit unit;
      Subscription upstream;

      BufferExactUnboundedSubscriber(Subscriber<? super U> var1, Callable<U> var2, long var3, TimeUnit var5, Scheduler var6) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.timespan = var3;
         this.unit = var5;
         this.scheduler = var6;
      }

      public boolean accept(Subscriber<? super U> var1, U var2) {
         this.downstream.onNext(var2);
         return true;
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         DisposableHelper.dispose(this.timer);
      }

      @Override
      public void dispose() {
         this.cancel();
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.timer.get() == DisposableHelper.DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.timer Ljava/util/concurrent/atomic/AtomicReference;
         // 04: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 07: pop
         // 08: aload 0
         // 09: monitorenter
         // 0a: aload 0
         // 0b: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.buffer Ljava/util/Collection;
         // 0e: astore 1
         // 0f: aload 1
         // 10: ifnonnull 16
         // 13: aload 0
         // 14: monitorexit
         // 15: return
         // 16: aload 0
         // 17: aconst_null
         // 18: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.buffer Ljava/util/Collection;
         // 1b: aload 0
         // 1c: monitorexit
         // 1d: aload 0
         // 1e: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 21: aload 1
         // 22: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 27: pop
         // 28: aload 0
         // 29: bipush 1
         // 2a: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.done Z
         // 2d: aload 0
         // 2e: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.enter ()Z
         // 31: ifeq 42
         // 34: aload 0
         // 35: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 38: aload 0
         // 39: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 3c: bipush 0
         // 3d: aconst_null
         // 3e: aload 0
         // 3f: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainMaxLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lorg/reactivestreams/Subscriber;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/QueueDrain;)V
         // 42: return
         // 43: astore 1
         // 44: aload 0
         // 45: monitorexit
         // 46: aload 1
         // 47: athrow
      }

      public void onError(Throwable param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.timer Ljava/util/concurrent/atomic/AtomicReference;
         // 04: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 07: pop
         // 08: aload 0
         // 09: monitorenter
         // 0a: aload 0
         // 0b: aconst_null
         // 0c: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.buffer Ljava/util/Collection;
         // 0f: aload 0
         // 10: monitorexit
         // 11: aload 0
         // 12: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 15: aload 1
         // 16: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 1b: return
         // 1c: astore 1
         // 1d: aload 0
         // 1e: monitorexit
         // 1f: aload 1
         // 20: athrow
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
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.buffer Ljava/util/Collection;
         // 06: astore 2
         // 07: aload 2
         // 08: ifnull 13
         // 0b: aload 2
         // 0c: aload 1
         // 0d: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
         // 12: pop
         // 13: aload 0
         // 14: monitorexit
         // 15: return
         // 16: astore 1
         // 17: aload 0
         // 18: monitorexit
         // 19: aload 1
         // 1a: athrow
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            Collection var4;
            try {
               var4 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               this.cancel();
               EmptySubscription.error(var6, this.downstream);
               return;
            }

            this.buffer = (U)var4;
            this.downstream.onSubscribe(this);
            if (!this.cancelled) {
               var1.request(Long.MAX_VALUE);
               Scheduler var7 = this.scheduler;
               long var2 = this.timespan;
               Disposable var8 = var7.schedulePeriodicallyDirect(this, var2, var2, this.unit);
               if (!ExternalSyntheticBackportWithForwarding0.m(this.timer, null, var8)) {
                  var8.dispose();
               }
            }
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }

      @Override
      public void run() {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The supplied buffer is null"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 2
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.buffer Ljava/util/Collection;
         // 18: astore 1
         // 19: aload 1
         // 1a: ifnonnull 20
         // 1d: aload 0
         // 1e: monitorexit
         // 1f: return
         // 20: aload 0
         // 21: aload 2
         // 22: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.buffer Ljava/util/Collection;
         // 25: aload 0
         // 26: monitorexit
         // 27: aload 0
         // 28: aload 1
         // 29: bipush 0
         // 2a: aload 0
         // 2b: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.fastPathEmitMax (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
         // 2e: return
         // 2f: astore 1
         // 30: aload 0
         // 31: monitorexit
         // 32: aload 1
         // 33: athrow
         // 34: astore 1
         // 35: aload 1
         // 36: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 39: aload 0
         // 3a: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.cancel ()V
         // 3d: aload 0
         // 3e: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferExactUnboundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 41: aload 1
         // 42: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 47: return
      }
   }

   static final class BufferSkipBoundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Subscription, Runnable {
      final Callable<U> bufferSupplier;
      final List<U> buffers;
      final long timeskip;
      final long timespan;
      final TimeUnit unit;
      Subscription upstream;
      final Scheduler.Worker w;

      BufferSkipBoundedSubscriber(Subscriber<? super U> var1, Callable<U> var2, long var3, long var5, TimeUnit var7, Scheduler.Worker var8) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.timespan = var3;
         this.timeskip = var5;
         this.unit = var7;
         this.w = var8;
         this.buffers = new LinkedList<>();
      }

      public boolean accept(Subscriber<? super U> var1, U var2) {
         var1.onNext(var2);
         return true;
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         this.w.dispose();
         this.clear();
      }

      void clear() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.buffers Ljava/util/List;
         // 06: invokeinterface java/util/List.clear ()V 1
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: astore 1
         // 0f: aload 0
         // 10: monitorexit
         // 11: aload 1
         // 12: athrow
      }

      public void onComplete() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: new java/util/ArrayList
         // 05: astore 1
         // 06: aload 1
         // 07: aload 0
         // 08: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.buffers Ljava/util/List;
         // 0b: invokespecial java/util/ArrayList.<init> (Ljava/util/Collection;)V
         // 0e: aload 0
         // 0f: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.buffers Ljava/util/List;
         // 12: invokeinterface java/util/List.clear ()V 1
         // 17: aload 0
         // 18: monitorexit
         // 19: aload 1
         // 1a: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
         // 1f: astore 2
         // 20: aload 2
         // 21: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 26: ifeq 41
         // 29: aload 2
         // 2a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 2f: checkcast java/util/Collection
         // 32: astore 1
         // 33: aload 0
         // 34: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 37: aload 1
         // 38: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 3d: pop
         // 3e: goto 20
         // 41: aload 0
         // 42: bipush 1
         // 43: putfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.done Z
         // 46: aload 0
         // 47: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.enter ()Z
         // 4a: ifeq 5e
         // 4d: aload 0
         // 4e: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 51: aload 0
         // 52: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 55: bipush 0
         // 56: aload 0
         // 57: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.w Lio/reactivex/Scheduler$Worker;
         // 5a: aload 0
         // 5b: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainMaxLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lorg/reactivestreams/Subscriber;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/QueueDrain;)V
         // 5e: return
         // 5f: astore 1
         // 60: aload 0
         // 61: monitorexit
         // 62: aload 1
         // 63: athrow
      }

      public void onError(Throwable var1) {
         this.done = true;
         this.w.dispose();
         this.clear();
         this.downstream.onError(var1);
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
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.buffers Ljava/util/List;
         // 06: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
         // 0b: astore 2
         // 0c: aload 2
         // 0d: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 12: ifeq 28
         // 15: aload 2
         // 16: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 1b: checkcast java/util/Collection
         // 1e: aload 1
         // 1f: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
         // 24: pop
         // 25: goto 0c
         // 28: aload 0
         // 29: monitorexit
         // 2a: return
         // 2b: astore 1
         // 2c: aload 0
         // 2d: monitorexit
         // 2e: aload 1
         // 2f: athrow
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            Collection var4;
            try {
               var4 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               this.w.dispose();
               var1.cancel();
               EmptySubscription.error(var6, this.downstream);
               return;
            }

            this.buffers.add((U)var4);
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
            Scheduler.Worker var7 = this.w;
            long var2 = this.timeskip;
            var7.schedulePeriodically(this, var2, var2, this.unit);
            this.w.schedule(new FlowableBufferTimed.BufferSkipBoundedSubscriber.RemoveFromBuffer((U)this, var4), this.timespan, this.unit);
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }

      @Override
      public void run() {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.bufferSupplier Ljava/util/concurrent/Callable;
         // 0c: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 11: ldc "The supplied buffer is null"
         // 13: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 16: checkcast java/util/Collection
         // 19: astore 1
         // 1a: aload 0
         // 1b: monitorenter
         // 1c: aload 0
         // 1d: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.cancelled Z
         // 20: ifeq 26
         // 23: aload 0
         // 24: monitorexit
         // 25: return
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.buffers Ljava/util/List;
         // 2a: aload 1
         // 2b: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
         // 30: pop
         // 31: aload 0
         // 32: monitorexit
         // 33: aload 0
         // 34: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.w Lio/reactivex/Scheduler$Worker;
         // 37: new io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer
         // 3a: dup
         // 3b: aload 0
         // 3c: aload 1
         // 3d: invokespecial io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer.<init> (Lio/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber;Ljava/util/Collection;)V
         // 40: aload 0
         // 41: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.timespan J
         // 44: aload 0
         // 45: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.unit Ljava/util/concurrent/TimeUnit;
         // 48: invokevirtual io/reactivex/Scheduler$Worker.schedule (Ljava/lang/Runnable;JLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
         // 4b: pop
         // 4c: return
         // 4d: astore 1
         // 4e: aload 0
         // 4f: monitorexit
         // 50: aload 1
         // 51: athrow
         // 52: astore 1
         // 53: aload 1
         // 54: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 57: aload 0
         // 58: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.cancel ()V
         // 5b: aload 0
         // 5c: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 5f: aload 1
         // 60: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 65: return
      }

      final class RemoveFromBuffer implements Runnable {
         private final U buffer;
         final FlowableBufferTimed.BufferSkipBoundedSubscriber this$0;

         RemoveFromBuffer(U var1, Collection var2) {
            this.this$0 = var1;
            this.buffer = (U)var2;
         }

         @Override
         public void run() {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
            //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
            //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
            //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
            //
            // Bytecode:
            // 00: aload 0
            // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer.this$0 Lio/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber;
            // 04: astore 1
            // 05: aload 1
            // 06: monitorenter
            // 07: aload 0
            // 08: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer.this$0 Lio/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber;
            // 0b: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.buffers Ljava/util/List;
            // 0e: aload 0
            // 0f: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer.buffer Ljava/util/Collection;
            // 12: invokeinterface java/util/List.remove (Ljava/lang/Object;)Z 2
            // 17: pop
            // 18: aload 1
            // 19: monitorexit
            // 1a: aload 0
            // 1b: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer.this$0 Lio/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber;
            // 1e: astore 1
            // 1f: aload 1
            // 20: aload 0
            // 21: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer.buffer Ljava/util/Collection;
            // 24: bipush 0
            // 25: aload 1
            // 26: getfield io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.w Lio/reactivex/Scheduler$Worker;
            // 29: invokestatic io/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber.access$000 (Lio/reactivex/internal/operators/flowable/FlowableBufferTimed$BufferSkipBoundedSubscriber;Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
            // 2c: return
            // 2d: astore 2
            // 2e: aload 1
            // 2f: monitorexit
            // 30: aload 2
            // 31: athrow
         }
      }
   }
}
