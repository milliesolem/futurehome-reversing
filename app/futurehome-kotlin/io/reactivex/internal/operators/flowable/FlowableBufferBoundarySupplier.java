package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferBoundarySupplier<T, U extends Collection<? super T>, B> extends AbstractFlowableWithUpstream<T, U> {
   final Callable<? extends Publisher<B>> boundarySupplier;
   final Callable<U> bufferSupplier;

   public FlowableBufferBoundarySupplier(Flowable<T> var1, Callable<? extends Publisher<B>> var2, Callable<U> var3) {
      super(var1);
      this.boundarySupplier = var2;
      this.bufferSupplier = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      this.source
         .subscribe(
            new FlowableBufferBoundarySupplier.BufferBoundarySupplierSubscriber<>(new SerializedSubscriber(var1), this.bufferSupplier, this.boundarySupplier)
         );
   }

   static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
      boolean once;
      final FlowableBufferBoundarySupplier.BufferBoundarySupplierSubscriber<T, U, B> parent;

      BufferBoundarySubscriber(FlowableBufferBoundarySupplier.BufferBoundarySupplierSubscriber<T, U, B> var1) {
         this.parent = var1;
      }

      public void onComplete() {
         if (!this.once) {
            this.once = true;
            this.parent.next();
         }
      }

      public void onError(Throwable var1) {
         if (this.once) {
            RxJavaPlugins.onError(var1);
         } else {
            this.once = true;
            this.parent.onError(var1);
         }
      }

      public void onNext(B var1) {
         if (!this.once) {
            this.once = true;
            this.cancel();
            this.parent.next();
         }
      }
   }

   static final class BufferBoundarySupplierSubscriber<T, U extends Collection<? super T>, B>
      extends QueueDrainSubscriber<T, U, U>
      implements FlowableSubscriber<T>,
      Subscription,
      Disposable {
      final Callable<? extends Publisher<B>> boundarySupplier;
      U buffer;
      final Callable<U> bufferSupplier;
      final AtomicReference<Disposable> other = new AtomicReference<>();
      Subscription upstream;

      BufferBoundarySupplierSubscriber(Subscriber<? super U> var1, Callable<U> var2, Callable<? extends Publisher<B>> var3) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.boundarySupplier = var3;
      }

      public boolean accept(Subscriber<? super U> var1, U var2) {
         this.downstream.onNext(var2);
         return true;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            this.disposeOther();
            if (this.enter()) {
               this.queue.clear();
            }
         }
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.disposeOther();
      }

      void disposeOther() {
         DisposableHelper.dispose(this.other);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.other.get() == DisposableHelper.DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
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
         // 01: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The buffer supplied is null"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 3
         // 12: aload 0
         // 13: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.boundarySupplier Ljava/util/concurrent/Callable;
         // 16: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 1b: ldc "The boundary publisher supplied is null"
         // 1d: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 20: checkcast org/reactivestreams/Publisher
         // 23: astore 2
         // 24: new io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySubscriber
         // 27: dup
         // 28: aload 0
         // 29: invokespecial io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySubscriber.<init> (Lio/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber;)V
         // 2c: astore 1
         // 2d: aload 0
         // 2e: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.other Ljava/util/concurrent/atomic/AtomicReference;
         // 31: aload 1
         // 32: invokestatic io/reactivex/internal/disposables/DisposableHelper.replace (Ljava/util/concurrent/atomic/AtomicReference;Lio/reactivex/disposables/Disposable;)Z
         // 35: ifeq 66
         // 38: aload 0
         // 39: monitorenter
         // 3a: aload 0
         // 3b: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.buffer Ljava/util/Collection;
         // 3e: astore 4
         // 40: aload 4
         // 42: ifnonnull 48
         // 45: aload 0
         // 46: monitorexit
         // 47: return
         // 48: aload 0
         // 49: aload 3
         // 4a: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.buffer Ljava/util/Collection;
         // 4d: aload 0
         // 4e: monitorexit
         // 4f: aload 2
         // 50: aload 1
         // 51: invokeinterface org/reactivestreams/Publisher.subscribe (Lorg/reactivestreams/Subscriber;)V 2
         // 56: aload 0
         // 57: aload 4
         // 59: bipush 0
         // 5a: aload 0
         // 5b: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.fastPathEmitMax (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
         // 5e: goto 66
         // 61: astore 1
         // 62: aload 0
         // 63: monitorexit
         // 64: aload 1
         // 65: athrow
         // 66: return
         // 67: astore 1
         // 68: aload 1
         // 69: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 6c: aload 0
         // 6d: bipush 1
         // 6e: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.cancelled Z
         // 71: aload 0
         // 72: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.upstream Lorg/reactivestreams/Subscription;
         // 75: invokeinterface org/reactivestreams/Subscription.cancel ()V 1
         // 7a: aload 0
         // 7b: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 7e: aload 1
         // 7f: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 84: return
         // 85: astore 1
         // 86: aload 1
         // 87: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 8a: aload 0
         // 8b: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.cancel ()V
         // 8e: aload 0
         // 8f: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.downstream Lorg/reactivestreams/Subscriber;
         // 92: aload 1
         // 93: invokeinterface org/reactivestreams/Subscriber.onError (Ljava/lang/Throwable;)V 2
         // 98: return
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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.buffer Ljava/util/Collection;
         // 06: astore 1
         // 07: aload 1
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 0
         // 0f: aconst_null
         // 10: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.buffer Ljava/util/Collection;
         // 13: aload 0
         // 14: monitorexit
         // 15: aload 0
         // 16: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 19: aload 1
         // 1a: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 1f: pop
         // 20: aload 0
         // 21: bipush 1
         // 22: putfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.done Z
         // 25: aload 0
         // 26: invokevirtual io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.enter ()Z
         // 29: ifeq 3a
         // 2c: aload 0
         // 2d: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 30: aload 0
         // 31: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.downstream Lorg/reactivestreams/Subscriber;
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
         // 03: getfield io/reactivex/internal/operators/flowable/FlowableBufferBoundarySupplier$BufferBoundarySupplierSubscriber.buffer Ljava/util/Collection;
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
            Subscriber var2 = this.downstream;

            Collection var3;
            try {
               var3 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable var10) {
               Exceptions.throwIfFatal(var10);
               this.cancelled = true;
               var1.cancel();
               EmptySubscription.error(var10, var2);
               return;
            }

            this.buffer = (U)var3;

            Publisher var4;
            try {
               var4 = ObjectHelper.requireNonNull(this.boundarySupplier.call(), "The boundary publisher supplied is null");
            } catch (Throwable var9) {
               Exceptions.throwIfFatal(var9);
               this.cancelled = true;
               var1.cancel();
               EmptySubscription.error(var9, var2);
               return;
            }

            FlowableBufferBoundarySupplier.BufferBoundarySubscriber var11 = new FlowableBufferBoundarySupplier.BufferBoundarySubscriber<>(this);
            this.other.set(var11);
            var2.onSubscribe(this);
            if (!this.cancelled) {
               var1.request(Long.MAX_VALUE);
               var4.subscribe(var11);
            }
         }
      }

      public void request(long var1) {
         this.requested(var1);
      }
   }
}
