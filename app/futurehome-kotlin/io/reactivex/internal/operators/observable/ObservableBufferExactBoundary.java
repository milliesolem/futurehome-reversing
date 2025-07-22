package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableBufferExactBoundary<T, U extends Collection<? super T>, B> extends AbstractObservableWithUpstream<T, U> {
   final ObservableSource<B> boundary;
   final Callable<U> bufferSupplier;

   public ObservableBufferExactBoundary(ObservableSource<T> var1, ObservableSource<B> var2, Callable<U> var3) {
      super(var1);
      this.boundary = var2;
      this.bufferSupplier = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super U> var1) {
      this.source.subscribe(new ObservableBufferExactBoundary.BufferExactBoundaryObserver<>(new SerializedObserver(var1), this.bufferSupplier, this.boundary));
   }

   static final class BufferBoundaryObserver<T, U extends Collection<? super T>, B> extends DisposableObserver<B> {
      final ObservableBufferExactBoundary.BufferExactBoundaryObserver<T, U, B> parent;

      BufferBoundaryObserver(ObservableBufferExactBoundary.BufferExactBoundaryObserver<T, U, B> var1) {
         this.parent = var1;
      }

      @Override
      public void onComplete() {
         this.parent.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.onError(var1);
      }

      @Override
      public void onNext(B var1) {
         this.parent.next();
      }
   }

   static final class BufferExactBoundaryObserver<T, U extends Collection<? super T>, B> extends QueueDrainObserver<T, U, U> implements Observer<T>, Disposable {
      final ObservableSource<B> boundary;
      U buffer;
      final Callable<U> bufferSupplier;
      Disposable other;
      Disposable upstream;

      BufferExactBoundaryObserver(Observer<? super U> var1, Callable<U> var2, ObservableSource<B> var3) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.boundary = var3;
      }

      public void accept(Observer<? super U> var1, U var2) {
         this.downstream.onNext((U)var2);
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.other.dispose();
            this.upstream.dispose();
            if (this.enter()) {
               this.queue.clear();
            }
         }
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The buffer supplied is null"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 2
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.buffer Ljava/util/Collection;
         // 18: astore 1
         // 19: aload 1
         // 1a: ifnonnull 20
         // 1d: aload 0
         // 1e: monitorexit
         // 1f: return
         // 20: aload 0
         // 21: aload 2
         // 22: putfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.buffer Ljava/util/Collection;
         // 25: aload 0
         // 26: monitorexit
         // 27: aload 0
         // 28: aload 1
         // 29: bipush 0
         // 2a: aload 0
         // 2b: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.fastPathEmit (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
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
         // 3a: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.dispose ()V
         // 3d: aload 0
         // 3e: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.downstream Lio/reactivex/Observer;
         // 41: aload 1
         // 42: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 47: return
      }

      @Override
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.buffer Ljava/util/Collection;
         // 06: astore 1
         // 07: aload 1
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 0
         // 0f: aconst_null
         // 10: putfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.buffer Ljava/util/Collection;
         // 13: aload 0
         // 14: monitorexit
         // 15: aload 0
         // 16: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 19: aload 1
         // 1a: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 1f: pop
         // 20: aload 0
         // 21: bipush 1
         // 22: putfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.done Z
         // 25: aload 0
         // 26: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.enter ()Z
         // 29: ifeq 3a
         // 2c: aload 0
         // 2d: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 30: aload 0
         // 31: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.downstream Lio/reactivex/Observer;
         // 34: bipush 0
         // 35: aload 0
         // 36: aload 0
         // 37: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lio/reactivex/Observer;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/ObservableQueueDrain;)V
         // 3a: return
         // 3b: astore 1
         // 3c: aload 0
         // 3d: monitorexit
         // 3e: aload 1
         // 3f: athrow
      }

      @Override
      public void onError(Throwable var1) {
         this.dispose();
         this.downstream.onError(var1);
      }

      @Override
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver.buffer Ljava/util/Collection;
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
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            Collection var2;
            try {
               var2 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.cancelled = true;
               var1.dispose();
               EmptyDisposable.error(var4, this.downstream);
               return;
            }

            this.buffer = (U)var2;
            ObservableBufferExactBoundary.BufferBoundaryObserver var5 = new ObservableBufferExactBoundary.BufferBoundaryObserver<>(this);
            this.other = var5;
            this.downstream.onSubscribe(this);
            if (!this.cancelled) {
               this.boundary.subscribe(var5);
            }
         }
      }
   }
}
