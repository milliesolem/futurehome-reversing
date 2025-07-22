package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferExactBoundary<T, U extends Collection<? super T>, B> extends AbstractFlowableWithUpstream<T, U> {
   final Publisher<B> boundary;
   final Callable<U> bufferSupplier;

   public FlowableBufferExactBoundary(Flowable<T> var1, Publisher<B> var2, Callable<U> var3) {
      super(var1);
      this.boundary = var2;
      this.bufferSupplier = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      this.source
         .subscribe(new FlowableBufferExactBoundary.BufferExactBoundarySubscriber<>(new SerializedSubscriber(var1), this.bufferSupplier, this.boundary));
   }

   static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
      final FlowableBufferExactBoundary.BufferExactBoundarySubscriber<T, U, B> parent;

      BufferBoundarySubscriber(FlowableBufferExactBoundary.BufferExactBoundarySubscriber<T, U, B> var1) {
         this.parent = var1;
      }

      public void onComplete() {
         this.parent.onComplete();
      }

      public void onError(Throwable var1) {
         this.parent.onError(var1);
      }

      public void onNext(B var1) {
         this.parent.next();
      }
   }

   static final class BufferExactBoundarySubscriber<T, U extends Collection<? super T>, B>
      extends QueueDrainSubscriber<T, U, U>
      implements FlowableSubscriber<T>,
      Subscription,
      Disposable {
      final Publisher<B> boundary;
      U buffer;
      final Callable<U> bufferSupplier;
      Disposable other;
      Subscription upstream;

      BufferExactBoundarySubscriber(Subscriber<? super U> var1, Callable<U> var2, Publisher<B> var3) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.boundary = var3;
      }

      public boolean accept(Subscriber<? super U> var1, U var2) {
         this.downstream.onNext(var2);
         return true;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.other.dispose();
            this.upstream.cancel();
            if (this.enter()) {
               this.queue.clear();
            }
         }
      }

      @Override
      public void dispose() {
         this.cancel();
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      void next() {
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The buffer supplied is null"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 2
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.buffer Ljava/util/Collection;
         // 18: astore 1
         // 19: aload 1
         // 1a: ifnonnull 20
         // 1d: aload 0
         // 1e: monitorexit
         // 1f: return
         // 20: aload 0
         // 21: aload 2
         // 22: putfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.buffer Ljava/util/Collection;
         // 25: aload 0
         // 26: monitorexit
         // 27: aload 0
         // 28: aload 1
         // 29: bipush 0
         // 2a: aload 0
         // 2b: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.fastPathEmitMax (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
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
         // 3a: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.cancel ()V
         // 3d: aload 0
         // 3e: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 41: aload 1
         // 42: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 47: return
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
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.buffer Ljava/util/Collection;
         // 06: astore 1
         // 07: aload 1
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 0
         // 0f: aconst_null
         // 10: putfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.buffer Ljava/util/Collection;
         // 13: aload 0
         // 14: monitorexit
         // 15: aload 0
         // 16: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 19: aload 1
         // 1a: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 1f: pop
         // 20: aload 0
         // 21: bipush 1
         // 22: putfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.done Z
         // 25: aload 0
         // 26: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.enter ()Z
         // 29: ifeq 3a
         // 2c: aload 0
         // 2d: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 30: aload 0
         // 31: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 34: bipush 0
         // 35: aload 0
         // 36: aload 0
         // 37: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainMaxLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lorg/reactivestreams/Subscriber;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/QueueDrain;)V
         // 3a: return
         // 3b: astore 1
         // 3c: aload 0
         // 3d: monitorexit
         // 3e: aload 1
         // 3f: athrow
      }

      public void onError(Throwable var1) {
         this.cancel();
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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferExactBoundary$BufferExactBoundarySubscriber.buffer Ljava/util/Collection;
         // 06: astore 2
         // 07: aload 2
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 2
         // 0f: aload 1
         // 10: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
         // 15: pop
         // 16: aload 0
         // 17: monitorexit
         // 18: return
         // 19: astore 1
         // 1a: aload 0
         // 1b: monitorexit
         // 1c: aload 1
         // 1d: athrow
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            Collection var2;
            try {
               var2 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.cancelled = true;
               var1.cancel();
               EmptySubscription.error(var4, this.downstream);
               return;
            }

            this.buffer = (U)var2;
            FlowableBufferExactBoundary.BufferBoundarySubscriber var5 = new FlowableBufferExactBoundary.BufferBoundarySubscriber<>(this);
            this.other = var5;
            this.downstream.onSubscribe(this);
            if (!this.cancelled) {
               var1.request(Long.MAX_VALUE);
               this.boundary.subscribe(var5);
            }
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }
   }
}
