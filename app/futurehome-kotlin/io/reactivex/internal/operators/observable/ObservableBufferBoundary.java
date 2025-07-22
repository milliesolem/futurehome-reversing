package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends AbstractObservableWithUpstream<T, U> {
   final Function<? super Open, ? extends ObservableSource<? extends Close>> bufferClose;
   final ObservableSource<? extends Open> bufferOpen;
   final Callable<U> bufferSupplier;

   public ObservableBufferBoundary(
      ObservableSource<T> var1,
      ObservableSource<? extends Open> var2,
      Function<? super Open, ? extends ObservableSource<? extends Close>> var3,
      Callable<U> var4
   ) {
      super(var1);
      this.bufferOpen = var2;
      this.bufferClose = var3;
      this.bufferSupplier = var4;
   }

   @Override
   protected void subscribeActual(Observer<? super U> var1) {
      ObservableBufferBoundary.BufferBoundaryObserver var2 = new ObservableBufferBoundary.BufferBoundaryObserver<>(
         var1, this.bufferOpen, this.bufferClose, this.bufferSupplier
      );
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
   }

   static final class BufferBoundaryObserver<T, C extends Collection<? super T>, Open, Close> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = -8466418554264089604L;
      final Function<? super Open, ? extends ObservableSource<? extends Close>> bufferClose;
      final ObservableSource<? extends Open> bufferOpen;
      final Callable<C> bufferSupplier;
      Map<Long, C> buffers;
      volatile boolean cancelled;
      volatile boolean done;
      final Observer<? super C> downstream;
      final AtomicThrowable errors;
      long index;
      final CompositeDisposable observers;
      final SpscLinkedArrayQueue<C> queue;
      final AtomicReference<Disposable> upstream;

      BufferBoundaryObserver(
         Observer<? super C> var1,
         ObservableSource<? extends Open> var2,
         Function<? super Open, ? extends ObservableSource<? extends Close>> var3,
         Callable<C> var4
      ) {
         this.downstream = var1;
         this.bufferSupplier = var4;
         this.bufferOpen = var2;
         this.bufferClose = var3;
         this.queue = new SpscLinkedArrayQueue(Observable.bufferSize());
         this.observers = new CompositeDisposable();
         this.upstream = new AtomicReference<>();
         this.buffers = new LinkedHashMap<>();
         this.errors = new AtomicThrowable();
      }

      void boundaryError(Disposable var1, Throwable var2) {
         DisposableHelper.dispose(this.upstream);
         this.observers.delete(var1);
         this.onError(var2);
      }

      void close(ObservableBufferBoundary.BufferCloseObserver<T, C> param1, long param2) {
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.observers Lio/reactivex/disposables/CompositeDisposable;
         // 04: aload 1
         // 05: invokevirtual io/reactivex/disposables/CompositeDisposable.delete (Lio/reactivex/disposables/Disposable;)Z
         // 08: pop
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.observers Lio/reactivex/disposables/CompositeDisposable;
         // 0d: invokevirtual io/reactivex/disposables/CompositeDisposable.size ()I
         // 10: ifne 21
         // 13: aload 0
         // 14: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.upstream Ljava/util/concurrent/atomic/AtomicReference;
         // 17: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 1a: pop
         // 1b: bipush 1
         // 1c: istore 4
         // 1e: goto 24
         // 21: bipush 0
         // 22: istore 4
         // 24: aload 0
         // 25: monitorenter
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.buffers Ljava/util/Map;
         // 2a: astore 1
         // 2b: aload 1
         // 2c: ifnonnull 32
         // 2f: aload 0
         // 30: monitorexit
         // 31: return
         // 32: aload 0
         // 33: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 36: aload 1
         // 37: lload 2
         // 38: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
         // 3b: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 40: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // 43: pop
         // 44: aload 0
         // 45: monitorexit
         // 46: iload 4
         // 48: ifeq 50
         // 4b: aload 0
         // 4c: bipush 1
         // 4d: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.done Z
         // 50: aload 0
         // 51: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.drain ()V
         // 54: return
         // 55: astore 1
         // 56: aload 0
         // 57: monitorexit
         // 58: aload 1
         // 59: athrow
      }

      @Override
      public void dispose() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.upstream Ljava/util/concurrent/atomic/AtomicReference;
         // 04: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 07: ifeq 35
         // 0a: aload 0
         // 0b: bipush 1
         // 0c: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.cancelled Z
         // 0f: aload 0
         // 10: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.observers Lio/reactivex/disposables/CompositeDisposable;
         // 13: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose ()V
         // 16: aload 0
         // 17: monitorenter
         // 18: aload 0
         // 19: aconst_null
         // 1a: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.buffers Ljava/util/Map;
         // 1d: aload 0
         // 1e: monitorexit
         // 1f: aload 0
         // 20: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.getAndIncrement ()I
         // 23: ifeq 35
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 2a: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.clear ()V
         // 2d: goto 35
         // 30: astore 1
         // 31: aload 0
         // 32: monitorexit
         // 33: aload 1
         // 34: athrow
         // 35: return
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var5 = this.downstream;
            SpscLinkedArrayQueue var4 = this.queue;
            int var1 = 1;

            while (!this.cancelled) {
               boolean var3 = this.done;
               if (var3 && this.errors.get() != null) {
                  var4.clear();
                  var5.onError(this.errors.terminate());
                  return;
               }

               Collection var6 = (Collection)var4.poll();
               boolean var2;
               if (var6 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var3 && var2) {
                  var5.onComplete();
                  return;
               }

               if (var2) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else {
                  var5.onNext(var6);
               }
            }

            var4.clear();
         }
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.upstream.get());
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.observers Lio/reactivex/disposables/CompositeDisposable;
         // 04: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose ()V
         // 07: aload 0
         // 08: monitorenter
         // 09: aload 0
         // 0a: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.buffers Ljava/util/Map;
         // 0d: astore 1
         // 0e: aload 1
         // 0f: ifnonnull 15
         // 12: aload 0
         // 13: monitorexit
         // 14: return
         // 15: aload 1
         // 16: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
         // 1b: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
         // 20: astore 2
         // 21: aload 2
         // 22: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 27: ifeq 40
         // 2a: aload 2
         // 2b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 30: checkcast java/util/Collection
         // 33: astore 1
         // 34: aload 0
         // 35: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 38: aload 1
         // 39: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // 3c: pop
         // 3d: goto 21
         // 40: aload 0
         // 41: aconst_null
         // 42: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.buffers Ljava/util/Map;
         // 45: aload 0
         // 46: monitorexit
         // 47: aload 0
         // 48: bipush 1
         // 49: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.done Z
         // 4c: aload 0
         // 4d: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.drain ()V
         // 50: return
         // 51: astore 1
         // 52: aload 0
         // 53: monitorexit
         // 54: aload 1
         // 55: athrow
      }

      @Override
      public void onError(Throwable param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.errors Lio/reactivex/internal/util/AtomicThrowable;
         // 04: aload 1
         // 05: invokevirtual io/reactivex/internal/util/AtomicThrowable.addThrowable (Ljava/lang/Throwable;)Z
         // 08: ifeq 2c
         // 0b: aload 0
         // 0c: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.observers Lio/reactivex/disposables/CompositeDisposable;
         // 0f: invokevirtual io/reactivex/disposables/CompositeDisposable.dispose ()V
         // 12: aload 0
         // 13: monitorenter
         // 14: aload 0
         // 15: aconst_null
         // 16: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.buffers Ljava/util/Map;
         // 19: aload 0
         // 1a: monitorexit
         // 1b: aload 0
         // 1c: bipush 1
         // 1d: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.done Z
         // 20: aload 0
         // 21: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.drain ()V
         // 24: goto 30
         // 27: astore 1
         // 28: aload 0
         // 29: monitorexit
         // 2a: aload 1
         // 2b: athrow
         // 2c: aload 1
         // 2d: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
         // 30: return
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
         // 03: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.buffers Ljava/util/Map;
         // 06: astore 2
         // 07: aload 2
         // 08: ifnonnull 0e
         // 0b: aload 0
         // 0c: monitorexit
         // 0d: return
         // 0e: aload 2
         // 0f: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
         // 14: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
         // 19: astore 2
         // 1a: aload 2
         // 1b: invokeinterface java/util/Iterator.hasNext ()Z 1
         // 20: ifeq 36
         // 23: aload 2
         // 24: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
         // 29: checkcast java/util/Collection
         // 2c: aload 1
         // 2d: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
         // 32: pop
         // 33: goto 1a
         // 36: aload 0
         // 37: monitorexit
         // 38: return
         // 39: astore 1
         // 3a: aload 0
         // 3b: monitorexit
         // 3c: aload 1
         // 3d: athrow
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this.upstream, var1)) {
            ObservableBufferBoundary.BufferBoundaryObserver.BufferOpenObserver var2 = new ObservableBufferBoundary.BufferBoundaryObserver.BufferOpenObserver<>(
               this
            );
            this.observers.add(var2);
            this.bufferOpen.subscribe(var2);
         }
      }

      void open(Open param1) {
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
         // 01: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.bufferSupplier Ljava/util/concurrent/Callable;
         // 04: invokeinterface java/util/concurrent/Callable.call ()Ljava/lang/Object; 1
         // 09: ldc "The bufferSupplier returned a null Collection"
         // 0b: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 0e: checkcast java/util/Collection
         // 11: astore 4
         // 13: aload 0
         // 14: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.bufferClose Lio/reactivex/functions/Function;
         // 17: aload 1
         // 18: invokeinterface io/reactivex/functions/Function.apply (Ljava/lang/Object;)Ljava/lang/Object; 2
         // 1d: ldc "The bufferClose returned a null ObservableSource"
         // 1f: invokestatic io/reactivex/internal/functions/ObjectHelper.requireNonNull (Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
         // 22: checkcast io/reactivex/ObservableSource
         // 25: astore 1
         // 26: aload 0
         // 27: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.index J
         // 2a: lstore 2
         // 2b: aload 0
         // 2c: lconst_1
         // 2d: lload 2
         // 2e: ladd
         // 2f: putfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.index J
         // 32: aload 0
         // 33: monitorenter
         // 34: aload 0
         // 35: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.buffers Ljava/util/Map;
         // 38: astore 5
         // 3a: aload 5
         // 3c: ifnonnull 42
         // 3f: aload 0
         // 40: monitorexit
         // 41: return
         // 42: aload 5
         // 44: lload 2
         // 45: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
         // 48: aload 4
         // 4a: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
         // 4f: pop
         // 50: aload 0
         // 51: monitorexit
         // 52: new io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferCloseObserver
         // 55: dup
         // 56: aload 0
         // 57: lload 2
         // 58: invokespecial io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferCloseObserver.<init> (Lio/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver;J)V
         // 5b: astore 4
         // 5d: aload 0
         // 5e: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.observers Lio/reactivex/disposables/CompositeDisposable;
         // 61: aload 4
         // 63: invokevirtual io/reactivex/disposables/CompositeDisposable.add (Lio/reactivex/disposables/Disposable;)Z
         // 66: pop
         // 67: aload 1
         // 68: aload 4
         // 6a: invokeinterface io/reactivex/ObservableSource.subscribe (Lio/reactivex/Observer;)V 2
         // 6f: return
         // 70: astore 1
         // 71: aload 0
         // 72: monitorexit
         // 73: aload 1
         // 74: athrow
         // 75: astore 1
         // 76: aload 1
         // 77: invokestatic io/reactivex/exceptions/Exceptions.throwIfFatal (Ljava/lang/Throwable;)V
         // 7a: aload 0
         // 7b: getfield io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.upstream Ljava/util/concurrent/atomic/AtomicReference;
         // 7e: invokestatic io/reactivex/internal/disposables/DisposableHelper.dispose (Ljava/util/concurrent/atomic/AtomicReference;)Z
         // 81: pop
         // 82: aload 0
         // 83: aload 1
         // 84: invokevirtual io/reactivex/internal/operators/observable/ObservableBufferBoundary$BufferBoundaryObserver.onError (Ljava/lang/Throwable;)V
         // 87: return
      }

      void openComplete(ObservableBufferBoundary.BufferBoundaryObserver.BufferOpenObserver<Open> var1) {
         this.observers.delete(var1);
         if (this.observers.size() == 0) {
            DisposableHelper.dispose(this.upstream);
            this.done = true;
            this.drain();
         }
      }

      static final class BufferOpenObserver<Open> extends AtomicReference<Disposable> implements Observer<Open>, Disposable {
         private static final long serialVersionUID = -8498650778633225126L;
         final ObservableBufferBoundary.BufferBoundaryObserver<?, ?, Open, ?> parent;

         BufferOpenObserver(ObservableBufferBoundary.BufferBoundaryObserver<?, ?, Open, ?> var1) {
            this.parent = var1;
         }

         @Override
         public void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public boolean isDisposed() {
            boolean var1;
            if (this.get() == DisposableHelper.DISPOSED) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         @Override
         public void onComplete() {
            this.lazySet(DisposableHelper.DISPOSED);
            this.parent.openComplete(this);
         }

         @Override
         public void onError(Throwable var1) {
            this.lazySet(DisposableHelper.DISPOSED);
            this.parent.boundaryError(this, var1);
         }

         @Override
         public void onNext(Open var1) {
            this.parent.open((Open)var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }

   static final class BufferCloseObserver<T, C extends Collection<? super T>> extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
      private static final long serialVersionUID = -8498650778633225126L;
      final long index;
      final ObservableBufferBoundary.BufferBoundaryObserver<T, C, ?, ?> parent;

      BufferCloseObserver(ObservableBufferBoundary.BufferBoundaryObserver<T, C, ?, ?> var1, long var2) {
         this.parent = var1;
         this.index = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == DisposableHelper.DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onComplete() {
         if (this.get() != DisposableHelper.DISPOSED) {
            this.lazySet(DisposableHelper.DISPOSED);
            this.parent.close(this, this.index);
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.get() != DisposableHelper.DISPOSED) {
            this.lazySet(DisposableHelper.DISPOSED);
            this.parent.boundaryError(this, var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(Object var1) {
         var1 = this.get();
         if (var1 != DisposableHelper.DISPOSED) {
            this.lazySet(DisposableHelper.DISPOSED);
            var1.dispose();
            this.parent.close(this, this.index);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }
}
