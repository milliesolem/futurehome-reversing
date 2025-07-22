package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.observers.SerializedObserver;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferTimed<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
   final Callable<U> bufferSupplier;
   final int maxSize;
   final boolean restartTimerOnMaxSize;
   final Scheduler scheduler;
   final long timeskip;
   final long timespan;
   final TimeUnit unit;

   public ObservableBufferTimed(ObservableSource<T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, Callable<U> var8, int var9, boolean var10) {
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
   protected void subscribeActual(Observer<? super U> var1) {
      if (this.timespan == this.timeskip && this.maxSize == Integer.MAX_VALUE) {
         this.source
            .subscribe(
               new ObservableBufferTimed.BufferExactUnboundedObserver<>(
                  new SerializedObserver(var1), this.bufferSupplier, this.timespan, this.unit, this.scheduler
               )
            );
      } else {
         Scheduler.Worker var2 = this.scheduler.createWorker();
         if (this.timespan == this.timeskip) {
            this.source
               .subscribe(
                  new ObservableBufferTimed.BufferExactBoundedObserver<>(
                     new SerializedObserver(var1), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, var2
                  )
               );
         } else {
            this.source
               .subscribe(
                  new ObservableBufferTimed.BufferSkipBoundedObserver<>(
                     new SerializedObserver(var1), this.bufferSupplier, this.timespan, this.timeskip, this.unit, var2
                  )
               );
         }
      }
   }

   static final class BufferExactBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
      U buffer;
      final Callable<U> bufferSupplier;
      long consumerIndex;
      final int maxSize;
      long producerIndex;
      final boolean restartTimerOnMaxSize;
      Disposable timer;
      final long timespan;
      final TimeUnit unit;
      Disposable upstream;
      final Scheduler.Worker w;

      BufferExactBoundedObserver(Observer<? super U> var1, Callable<U> var2, long var3, TimeUnit var5, int var6, boolean var7, Scheduler.Worker var8) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.timespan = var3;
         this.unit = var5;
         this.maxSize = var6;
         this.restartTimerOnMaxSize = var7;
         this.w = var8;
      }

      public void accept(Observer<? super U> var1, U var2) {
         var1.onNext(var2);
      }

      @Override
      public void dispose() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.cancelled Z
         // 04: ifne 2d
         // 07: aload 0
         // 08: bipush 1
         // 09: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.cancelled Z
         // 0c: aload 0
         // 0d: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.upstream Lio/reactivex/disposables/Disposable;
         // 10: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
         // 15: aload 0
         // 16: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.w Lio/reactivex/Scheduler$Worker;
         // 19: invokevirtual io/reactivex/Scheduler$Worker.dispose ()V
         // 1c: aload 0
         // 1d: monitorenter
         // 1e: aload 0
         // 1f: aconst_null
         // 20: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 23: aload 0
         // 24: monitorexit
         // 25: goto 2d
         // 28: astore 1
         // 29: aload 0
         // 2a: monitorexit
         // 2b: aload 1
         // 2c: athrow
         // 2d: return
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.w Lio/reactivex/Scheduler$Worker;
         // 04: invokevirtual io/reactivex/Scheduler$Worker.dispose ()V
         // 07: aload 0
         // 08: monitorenter
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 0d: astore 1
         // 0e: aload 0
         // 0f: aconst_null
         // 10: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 13: aload 0
         // 14: monitorexit
         // 15: aload 1
         // 16: ifnull 3e
         // 19: aload 0
         // 1a: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 1d: aload 1
         // 1e: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 23: pop
         // 24: aload 0
         // 25: bipush 1
         // 26: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.done Z
         // 29: aload 0
         // 2a: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.enter ()Z
         // 2d: ifeq 3e
         // 30: aload 0
         // 31: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 34: aload 0
         // 35: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.downstream Lio/reactivex/Observer;
         // 38: bipush 0
         // 39: aload 0
         // 3a: aload 0
         // 3b: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lio/reactivex/Observer;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/ObservableQueueDrain;)V
         // 3e: return
         // 3f: astore 1
         // 40: aload 0
         // 41: monitorexit
         // 42: aload 1
         // 43: athrow
      }

      @Override
      public void onError(Throwable param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: aconst_null
         // 04: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 07: aload 0
         // 08: monitorexit
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.downstream Lio/reactivex/Observer;
         // 0d: aload 1
         // 0e: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 13: aload 0
         // 14: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.w Lio/reactivex/Scheduler$Worker;
         // 17: invokevirtual io/reactivex/Scheduler$Worker.dispose ()V
         // 1a: return
         // 1b: astore 1
         // 1c: aload 0
         // 1d: monitorexit
         // 1e: aload 1
         // 1f: athrow
      }

      @Override
      public void onNext(T param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
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
         // 21: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.maxSize I
         // 24: if_icmpge 2a
         // 27: aload 0
         // 28: monitorexit
         // 29: return
         // 2a: aload 0
         // 2b: aconst_null
         // 2c: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 2f: aload 0
         // 30: aload 0
         // 31: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.producerIndex J
         // 34: lconst_1
         // 35: ladd
         // 36: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.producerIndex J
         // 39: aload 0
         // 3a: monitorexit
         // 3b: aload 0
         // 3c: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.restartTimerOnMaxSize Z
         // 3f: ifeq 4b
         // 42: aload 0
         // 43: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.timer Lio/reactivex/disposables/Disposable;
         // 46: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
         // 4b: aload 0
         // 4c: aload 4
         // 4e: bipush 0
         // 4f: aload 0
         // 50: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.fastPathOrderedEmit (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
         // 53: aload 0
         // 54: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.bufferSupplier Ljava/util/concurrent/Callable;
         // 57: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 5c: ldc "The buffer supplied is null"
         // 5e: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 61: checkcast java/util/Collection
         // 64: astore 1
         // 65: aload 0
         // 66: monitorenter
         // 67: aload 0
         // 68: aload 1
         // 69: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 6c: aload 0
         // 6d: aload 0
         // 6e: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.consumerIndex J
         // 71: lconst_1
         // 72: ladd
         // 73: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.consumerIndex J
         // 76: aload 0
         // 77: monitorexit
         // 78: aload 0
         // 79: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.restartTimerOnMaxSize Z
         // 7c: ifeq 98
         // 7f: aload 0
         // 80: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.w Lio/reactivex/Scheduler$Worker;
         // 83: astore 1
         // 84: aload 0
         // 85: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.timespan J
         // 88: lstore 2
         // 89: aload 0
         // 8a: aload 1
         // 8b: aload 0
         // 8c: lload 2
         // 8d: lload 2
         // 8e: aload 0
         // 8f: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.unit Ljava/util/concurrent/TimeUnit;
         // 92: invokevirtual io/reactivex/Scheduler$Worker.schedulePeriodically (Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
         // 95: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.timer Lio/reactivex/disposables/Disposable;
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
         // a4: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.downstream Lio/reactivex/Observer;
         // a7: aload 1
         // a8: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // ad: aload 0
         // ae: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.dispose ()V
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
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            Collection var4;
            try {
               var4 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               var1.dispose();
               EmptyDisposable.error(var6, this.downstream);
               this.w.dispose();
               return;
            }

            this.buffer = (U)var4;
            this.downstream.onSubscribe(this);
            Scheduler.Worker var7 = this.w;
            long var2 = this.timespan;
            this.timer = var7.schedulePeriodically(this, var2, var2, this.unit);
         }
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The bufferSupplier returned a null buffer"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 2
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 18: astore 1
         // 19: aload 1
         // 1a: ifnull 3b
         // 1d: aload 0
         // 1e: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.producerIndex J
         // 21: aload 0
         // 22: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.consumerIndex J
         // 25: lcmp
         // 26: ifeq 2c
         // 29: goto 3b
         // 2c: aload 0
         // 2d: aload 2
         // 2e: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.buffer Ljava/util/Collection;
         // 31: aload 0
         // 32: monitorexit
         // 33: aload 0
         // 34: aload 1
         // 35: bipush 0
         // 36: aload 0
         // 37: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.fastPathOrderedEmit (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
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
         // 49: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.dispose ()V
         // 4c: aload 0
         // 4d: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver.downstream Lio/reactivex/Observer;
         // 50: aload 1
         // 51: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 56: return
      }
   }

   static final class BufferExactUnboundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
      U buffer;
      final Callable<U> bufferSupplier;
      final Scheduler scheduler;
      final AtomicReference<Disposable> timer = new AtomicReference<>();
      final long timespan;
      final TimeUnit unit;
      Disposable upstream;

      BufferExactUnboundedObserver(Observer<? super U> var1, Callable<U> var2, long var3, TimeUnit var5, Scheduler var6) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.timespan = var3;
         this.unit = var5;
         this.scheduler = var6;
      }

      public void accept(Observer<? super U> var1, U var2) {
         this.downstream.onNext((U)var2);
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.timer);
         this.upstream.dispose();
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

      @Override
      public void onComplete() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.buffer Ljava/util/Collection;
         // 06: astore 1
         // 07: aload 0
         // 08: aconst_null
         // 09: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.buffer Ljava/util/Collection;
         // 0c: aload 0
         // 0d: monitorexit
         // 0e: aload 1
         // 0f: ifnull 37
         // 12: aload 0
         // 13: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 16: aload 1
         // 17: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 1c: pop
         // 1d: aload 0
         // 1e: bipush 1
         // 1f: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.done Z
         // 22: aload 0
         // 23: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.enter ()Z
         // 26: ifeq 37
         // 29: aload 0
         // 2a: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 2d: aload 0
         // 2e: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.downstream Lio/reactivex/Observer;
         // 31: bipush 0
         // 32: aconst_null
         // 33: aload 0
         // 34: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lio/reactivex/Observer;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/ObservableQueueDrain;)V
         // 37: aload 0
         // 38: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.timer Ljava/util/concurrent/atomic/AtomicReference;
         // 3b: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 3e: pop
         // 3f: return
         // 40: astore 1
         // 41: aload 0
         // 42: monitorexit
         // 43: aload 1
         // 44: athrow
      }

      @Override
      public void onError(Throwable param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: aconst_null
         // 04: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.buffer Ljava/util/Collection;
         // 07: aload 0
         // 08: monitorexit
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.downstream Lio/reactivex/Observer;
         // 0d: aload 1
         // 0e: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 13: aload 0
         // 14: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.timer Ljava/util/concurrent/atomic/AtomicReference;
         // 17: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 1a: pop
         // 1b: return
         // 1c: astore 1
         // 1d: aload 0
         // 1e: monitorexit
         // 1f: aload 1
         // 20: athrow
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.buffer Ljava/util/Collection;
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

            try {
               var6 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable var5) {
               Exceptions.throwIfFatal(var5);
               this.dispose();
               EmptyDisposable.error(var5, this.downstream);
               return;
            }

            this.buffer = (U)var6;
            this.downstream.onSubscribe(this);
            if (!this.cancelled) {
               Scheduler var7 = this.scheduler;
               long var2 = this.timespan;
               var1 = var7.schedulePeriodicallyDirect(this, var2, var2, this.unit);
               if (!ExternalSyntheticBackportWithForwarding0.m(this.timer, null, var1)) {
                  var1.dispose();
               }
            }
         }
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The bufferSupplier returned a null buffer"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 1
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.buffer Ljava/util/Collection;
         // 18: astore 2
         // 19: aload 2
         // 1a: ifnull 22
         // 1d: aload 0
         // 1e: aload 1
         // 1f: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.buffer Ljava/util/Collection;
         // 22: aload 0
         // 23: monitorexit
         // 24: aload 2
         // 25: ifnonnull 31
         // 28: aload 0
         // 29: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.timer Ljava/util/concurrent/atomic/AtomicReference;
         // 2c: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 2f: pop
         // 30: return
         // 31: aload 0
         // 32: aload 2
         // 33: bipush 0
         // 34: aload 0
         // 35: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.fastPathEmit (Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
         // 38: return
         // 39: astore 1
         // 3a: aload 0
         // 3b: monitorexit
         // 3c: aload 1
         // 3d: athrow
         // 3e: astore 1
         // 3f: aload 1
         // 40: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 43: aload 0
         // 44: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.downstream Lio/reactivex/Observer;
         // 47: aload 1
         // 48: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 4d: aload 0
         // 4e: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver.dispose ()V
         // 51: return
      }
   }

   static final class BufferSkipBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
      final Callable<U> bufferSupplier;
      final List<U> buffers;
      final long timeskip;
      final long timespan;
      final TimeUnit unit;
      Disposable upstream;
      final Scheduler.Worker w;

      BufferSkipBoundedObserver(Observer<? super U> var1, Callable<U> var2, long var3, long var5, TimeUnit var7, Scheduler.Worker var8) {
         super(var1, new MpscLinkedQueue());
         this.bufferSupplier = var2;
         this.timespan = var3;
         this.timeskip = var5;
         this.unit = var7;
         this.w = var8;
         this.buffers = new LinkedList<>();
      }

      public void accept(Observer<? super U> var1, U var2) {
         var1.onNext(var2);
      }

      void clear() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.buffers Ljava/util/List;
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

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.clear();
            this.upstream.dispose();
            this.w.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: new java/util/ArrayList
         // 05: astore 1
         // 06: aload 1
         // 07: aload 0
         // 08: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.buffers Ljava/util/List;
         // 0b: invokespecial java/util/ArrayList.<init> (Ljava/util/Collection;)V
         // 0e: aload 0
         // 0f: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.buffers Ljava/util/List;
         // 12: invokeinterface java/util/List.clear ()V 1
         // 17: aload 0
         // 18: monitorexit
         // 19: aload 1
         // 1a: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
         // 1f: astore 1
         // 20: aload 1
         // 21: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 26: ifeq 41
         // 29: aload 1
         // 2a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 2f: checkcast java/util/Collection
         // 32: astore 2
         // 33: aload 0
         // 34: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 37: aload 2
         // 38: invokeinterface io/reactivex/internal/fuseable/SimplePlainQueue.offer (Ljava/lang/Object;)Z 2
         // 3d: pop
         // 3e: goto 20
         // 41: aload 0
         // 42: bipush 1
         // 43: putfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.done Z
         // 46: aload 0
         // 47: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.enter ()Z
         // 4a: ifeq 5e
         // 4d: aload 0
         // 4e: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.queue Lio/reactivex/internal/fuseable/SimplePlainQueue;
         // 51: aload 0
         // 52: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.downstream Lio/reactivex/Observer;
         // 55: bipush 0
         // 56: aload 0
         // 57: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.w Lio/reactivex/Scheduler$Worker;
         // 5a: aload 0
         // 5b: invokestatic io/reactivex/internal/util/QueueDrainHelper.drainLoop (Lio/reactivex/internal/fuseable/SimplePlainQueue;Lio/reactivex/Observer;ZLio/reactivex/disposables/Disposable;Lio/reactivex/internal/util/ObservableQueueDrain;)V
         // 5e: return
         // 5f: astore 1
         // 60: aload 0
         // 61: monitorexit
         // 62: aload 1
         // 63: athrow
      }

      @Override
      public void onError(Throwable var1) {
         this.done = true;
         this.clear();
         this.downstream.onError(var1);
         this.w.dispose();
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.buffers Ljava/util/List;
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
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;

            Collection var4;
            try {
               var4 = ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable var6) {
               Exceptions.throwIfFatal(var6);
               var1.dispose();
               EmptyDisposable.error(var6, this.downstream);
               this.w.dispose();
               return;
            }

            this.buffers.add((U)var4);
            this.downstream.onSubscribe(this);
            Scheduler.Worker var7 = this.w;
            long var2 = this.timeskip;
            var7.schedulePeriodically(this, var2, var2, this.unit);
            this.w.schedule(new ObservableBufferTimed.BufferSkipBoundedObserver.RemoveFromBufferEmit((U)this, var4), this.timespan, this.unit);
         }
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.bufferSupplier Ljava/util/concurrent/Callable;
         // 0c: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 11: ldc "The bufferSupplier returned a null buffer"
         // 13: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 16: checkcast java/util/Collection
         // 19: astore 1
         // 1a: aload 0
         // 1b: monitorenter
         // 1c: aload 0
         // 1d: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.cancelled Z
         // 20: ifeq 26
         // 23: aload 0
         // 24: monitorexit
         // 25: return
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.buffers Ljava/util/List;
         // 2a: aload 1
         // 2b: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
         // 30: pop
         // 31: aload 0
         // 32: monitorexit
         // 33: aload 0
         // 34: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.w Lio/reactivex/Scheduler$Worker;
         // 37: new io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBuffer
         // 3a: dup
         // 3b: aload 0
         // 3c: aload 1
         // 3d: invokespecial io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBuffer.<init> (Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;Ljava/util/Collection;)V
         // 40: aload 0
         // 41: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.timespan J
         // 44: aload 0
         // 45: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.unit Ljava/util/concurrent/TimeUnit;
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
         // 58: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.downstream Lio/reactivex/Observer;
         // 5b: aload 1
         // 5c: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 61: aload 0
         // 62: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.dispose ()V
         // 65: return
      }

      final class RemoveFromBuffer implements Runnable {
         private final U b;
         final ObservableBufferTimed.BufferSkipBoundedObserver this$0;

         RemoveFromBuffer(U var1, Collection var2) {
            this.this$0 = var1;
            this.b = (U)var2;
         }

         @Override
         public void run() {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            //
            // Bytecode:
            // 00: aload 0
            // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBuffer.this$0 Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;
            // 04: astore 1
            // 05: aload 1
            // 06: monitorenter
            // 07: aload 0
            // 08: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBuffer.this$0 Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;
            // 0b: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.buffers Ljava/util/List;
            // 0e: aload 0
            // 0f: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBuffer.b Ljava/util/Collection;
            // 12: invokeinterface java/util/List.remove (Ljava/lang/Object;)Z 2
            // 17: pop
            // 18: aload 1
            // 19: monitorexit
            // 1a: aload 0
            // 1b: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBuffer.this$0 Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;
            // 1e: astore 1
            // 1f: aload 1
            // 20: aload 0
            // 21: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBuffer.b Ljava/util/Collection;
            // 24: bipush 0
            // 25: aload 1
            // 26: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.w Lio/reactivex/Scheduler$Worker;
            // 29: invokestatic io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.access$000 (Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
            // 2c: return
            // 2d: astore 2
            // 2e: aload 1
            // 2f: monitorexit
            // 30: aload 2
            // 31: athrow
         }
      }

      final class RemoveFromBufferEmit implements Runnable {
         private final U buffer;
         final ObservableBufferTimed.BufferSkipBoundedObserver this$0;

         RemoveFromBufferEmit(U var1, Collection var2) {
            this.this$0 = var1;
            this.buffer = (U)var2;
         }

         @Override
         public void run() {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            //
            // Bytecode:
            // 00: aload 0
            // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBufferEmit.this$0 Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;
            // 04: astore 2
            // 05: aload 2
            // 06: monitorenter
            // 07: aload 0
            // 08: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBufferEmit.this$0 Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;
            // 0b: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.buffers Ljava/util/List;
            // 0e: aload 0
            // 0f: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBufferEmit.buffer Ljava/util/Collection;
            // 12: invokeinterface java/util/List.remove (Ljava/lang/Object;)Z 2
            // 17: pop
            // 18: aload 2
            // 19: monitorexit
            // 1a: aload 0
            // 1b: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBufferEmit.this$0 Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;
            // 1e: astore 1
            // 1f: aload 1
            // 20: aload 0
            // 21: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBufferEmit.buffer Ljava/util/Collection;
            // 24: bipush 0
            // 25: aload 1
            // 26: getfield io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.w Lio/reactivex/Scheduler$Worker;
            // 29: invokestatic io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver.access$100 (Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
            // 2c: return
            // 2d: astore 1
            // 2e: aload 2
            // 2f: monitorexit
            // 30: aload 1
            // 31: athrow
         }
      }
   }
}
