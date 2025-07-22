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
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferBoundarySupplier<T, U extends Collection<? super T>, B> extends AbstractObservableWithUpstream<T, U> {
   final Callable<? extends ObservableSource<B>> boundarySupplier;
   final Callable<U> bufferSupplier;

   public ObservableBufferBoundarySupplier(ObservableSource<T> var1, Callable<? extends ObservableSource<B>> var2, Callable<U> var3) {
      super(var1);
      this.boundarySupplier = var2;
      this.bufferSupplier = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super U> var1) {
      this.source
         .subscribe(
            new ObservableBufferBoundarySupplier.BufferBoundarySupplierObserver<>(new SerializedObserver(var1), this.bufferSupplier, this.boundarySupplier)
         );
   }

   static final class BufferBoundaryObserver<T, U extends Collection<? super T>, B> extends DisposableObserver<B> {
      boolean once;
      final ObservableBufferBoundarySupplier.BufferBoundarySupplierObserver<T, U, B> parent;

      BufferBoundaryObserver(ObservableBufferBoundarySupplier.BufferBoundarySupplierObserver<T, U, B> var1) {
         this.parent = var1;
      }

      @Override
      public void onComplete() {
         if (!this.once) {
            this.once = true;
            this.parent.next();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.once) {
            RxJavaPlugins.onError(var1);
         } else {
            this.once = true;
            this.parent.onError(var1);
         }
      }

      @Override
      public void onNext(B var1) {
         if (!this.once) {
            this.once = true;
            this.dispose();
            this.parent.next();
         }
      }
   }

   static final class BufferBoundarySupplierObserver<T, U extends Collection<? super T>, B>
      extends QueueDrainObserver<T, U, U>
      implements Observer<T>,
      Disposable {
      final Callable<? extends ObservableSource<B>> boundarySupplier;
      U buffer;
      final Callable<U> bufferSupplier;
      final AtomicReference<Disposable> other = new AtomicReference<>();
      Disposable upstream;

      BufferBoundarySupplierObserver(Observer<? super U> var1, Callable<U> var2, Callable<? extends ObservableSource<B>> var3) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.boundarySupplier = var3;
      }

      public void accept(Observer<? super U> var1, U var2) {
         this.downstream.onNext((U)var2);
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.dispose();
            this.disposeOther();
            if (this.enter()) {
               this.queue.clear();
            }
         }
      }

      void disposeOther() {
         DisposableHelper.dispose(this.other);
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The buffer supplied is null"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 2
         // 12: aload 0
         // 13: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.boundarySupplier Ljava/util/concurrent/Callable;
         // 16: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 1b: ldc "The boundary ObservableSource supplied is null"
         // 1d: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 20: checkcast io/reactivex/ObservableSource
         // 23: astore 1
         // 24: new io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundaryObserver
         // 27: dup
         // 28: aload 0
         // 29: invokespecial io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundaryObserver.<init> (Lio/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver;)V
         // 2c: astore 4
         // 2e: aload 0
         // 2f: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.other Ljava/util/concurrent/atomic/AtomicReference;
         // 32: aload 4
         // 34: invokestatic io/reactivex/internal/disposables/DisposableHelper.replace (Ljava/util/concurrent/atomic/AtomicReference;Lio/reactivex/disposables/Disposable;)Z
         // 37: ifeq 66
         // 3a: aload 0
         // 3b: monitorenter
         // 3c: aload 0
         // 3d: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.buffer Ljava/util/Collection;
         // 40: astore 3
         // 41: aload 3
         // 42: ifnonnull 48
         // 45: aload 0
         // 46: monitorexit
         // 47: return
         // 48: aload 0
         // 49: aload 2
         // 4a: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.buffer Ljava/util/Collection;
         // 4d: aload 0
         // 4e: monitorexit
         // 4f: aload 1
         // 50: aload 4
         // 52: invokeinterface io/reactivex/ObservableSource.subscribe (Lio/reactivex/Observer;)V 2
         // 57: aload 0
         // 58: aload 3
         // 59: bipush 0
         // 5a: aload 0
         // 5b: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.fastPathEmit (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
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
         // 6e: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.cancelled Z
         // 71: aload 0
         // 72: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.upstream Lio/reactivex/disposables/Disposable;
         // 75: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
         // 7a: aload 0
         // 7b: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.downstream Lio/reactivex/Observer;
         // 7e: aload 1
         // 7f: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 84: return
         // 85: astore 1
         // 86: aload 1
         // 87: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 8a: aload 0
         // 8b: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.dispose ()V
         // 8e: aload 0
         // 8f: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.downstream Lio/reactivex/Observer;
         // 92: aload 1
         // 93: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 98: return
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.buffer Ljava/util/Collection;
         // 06: astore 1
         // 07: aload 1
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 0
         // 0f: aconst_null
         // 10: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.buffer Ljava/util/Collection;
         // 13: aload 0
         // 14: monitorexit
         // 15: aload 0
         // 16: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 19: aload 1
         // 1a: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 1f: pop
         // 20: aload 0
         // 21: bipush 1
         // 22: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.done Z
         // 25: aload 0
         // 26: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.enter ()Z
         // 29: ifeq 3a
         // 2c: aload 0
         // 2d: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 30: aload 0
         // 31: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.downstream Lio/reactivex/Observer;
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver.buffer Ljava/util/Collection;
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
            Observer var2 = this.downstream;

            Collection var3;
            try {
               var3 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable var9) {
               Exceptions.throwIfFatal(var9);
               this.cancelled = true;
               var1.dispose();
               EmptyDisposable.error(var9, var2);
               return;
            }

            this.buffer = (U)var3;

            try {
               var11 = ObjectHelper.requireNonNull(this.boundarySupplier.call(), "The boundary ObservableSource supplied is null");
            } catch (Throwable var8) {
               Exceptions.throwIfFatal(var8);
               this.cancelled = true;
               var1.dispose();
               EmptyDisposable.error(var8, var2);
               return;
            }

            ObservableBufferBoundarySupplier.BufferBoundaryObserver var10 = new ObservableBufferBoundarySupplier.BufferBoundaryObserver<>(this);
            this.other.set(var10);
            var2.onSubscribe(this);
            if (!this.cancelled) {
               var11.subscribe(var10);
            }
         }
      }
   }
}
