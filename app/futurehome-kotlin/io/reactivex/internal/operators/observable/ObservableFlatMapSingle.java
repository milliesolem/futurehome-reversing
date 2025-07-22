package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableFlatMapSingle<T, R> extends AbstractObservableWithUpstream<T, R> {
   final boolean delayErrors;
   final Function<? super T, ? extends SingleSource<? extends R>> mapper;

   public ObservableFlatMapSingle(ObservableSource<T> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, boolean var3) {
      super(var1);
      this.mapper = var2;
      this.delayErrors = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      this.source.subscribe(new ObservableFlatMapSingle.FlatMapSingleObserver<>(var1, this.mapper, this.delayErrors));
   }

   static final class FlatMapSingleObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = 8600231336733376951L;
      final AtomicInteger active;
      volatile boolean cancelled;
      final boolean delayErrors;
      final Observer<? super R> downstream;
      final AtomicThrowable errors;
      final Function<? super T, ? extends SingleSource<? extends R>> mapper;
      final AtomicReference<SpscLinkedArrayQueue<R>> queue;
      final CompositeDisposable set;
      Disposable upstream;

      FlatMapSingleObserver(Observer<? super R> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, boolean var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.delayErrors = var3;
         this.set = new CompositeDisposable();
         this.errors = new AtomicThrowable();
         this.active = new AtomicInteger(1);
         this.queue = new AtomicReference<>();
      }

      void clear() {
         SpscLinkedArrayQueue var1 = this.queue.get();
         if (var1 != null) {
            var1.clear();
         }
      }

      @Override
      public void dispose() {
         this.cancelled = true;
         this.upstream.dispose();
         this.set.dispose();
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         Observer var5 = this.downstream;
         AtomicInteger var6 = this.active;
         AtomicReference var7 = this.queue;
         int var1 = 1;

         while (!this.cancelled) {
            if (!this.delayErrors && this.errors.get() != null) {
               Throwable var12 = this.errors.terminate();
               this.clear();
               var5.onError(var12);
               return;
            }

            int var2 = var6.get();
            boolean var3 = false;
            boolean var8;
            if (var2 == 0) {
               var8 = 1;
            } else {
               var8 = 0;
            }

            SpscLinkedArrayQueue var4 = (SpscLinkedArrayQueue)var7.get();
            Object var10;
            if (var4 != null) {
               var10 = var4.poll();
            } else {
               var10 = null;
            }

            if (var10 == null) {
               var3 = true;
            }

            if (var8 && var3) {
               var10 = this.errors.terminate();
               if (var10 != null) {
                  var5.onError((Throwable)var10);
               } else {
                  var5.onComplete();
               }

               return;
            }

            if (var3) {
               var8 = this.addAndGet(-var1);
               var1 = var8;
               if (var8 == 0) {
                  return;
               }
            } else {
               var5.onNext(var10);
            }
         }

         this.clear();
      }

      SpscLinkedArrayQueue<R> getOrCreateQueue() {
         SpscLinkedArrayQueue var2;
         do {
            var2 = this.queue.get();
            if (var2 != null) {
               return var2;
            }

            var2 = new SpscLinkedArrayQueue(Observable.bufferSize());
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.queue, null, var2));

         return var2;
      }

      void innerError(ObservableFlatMapSingle.FlatMapSingleObserver<T, R>.InnerObserver var1, Throwable var2) {
         this.set.delete(var1);
         if (this.errors.addThrowable(var2)) {
            if (!this.delayErrors) {
               this.upstream.dispose();
               this.set.dispose();
            }

            this.active.decrementAndGet();
            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      void innerSuccess(ObservableFlatMapSingle.FlatMapSingleObserver<T, R>.InnerObserver param1, R param2) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.set Lio/reactivex/disposables/CompositeDisposable;
         // 04: aload 1
         // 05: invokevirtual io/reactivex/disposables/CompositeDisposable.delete (Lio/reactivex/disposables/Disposable;)Z
         // 08: pop
         // 09: aload 0
         // 0a: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.get ()I
         // 0d: ifne 76
         // 10: bipush 0
         // 11: istore 3
         // 12: aload 0
         // 13: bipush 0
         // 14: bipush 1
         // 15: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.compareAndSet (II)Z
         // 18: ifeq 76
         // 1b: aload 0
         // 1c: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.downstream Lio/reactivex/Observer;
         // 1f: aload 2
         // 20: invokeinterface io/reactivex/Observer.onNext (Ljava/lang/Object;)V 2
         // 25: aload 0
         // 26: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.active Ljava/util/concurrent/atomic/AtomicInteger;
         // 29: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
         // 2c: ifne 31
         // 2f: bipush 1
         // 30: istore 3
         // 31: aload 0
         // 32: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.queue Ljava/util/concurrent/atomic/AtomicReference;
         // 35: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
         // 38: checkcast io/reactivex/internal/queue/SpscLinkedArrayQueue
         // 3b: astore 1
         // 3c: iload 3
         // 3d: ifeq 6e
         // 40: aload 1
         // 41: ifnull 4b
         // 44: aload 1
         // 45: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.isEmpty ()Z
         // 48: ifeq 6e
         // 4b: aload 0
         // 4c: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.errors Lio/reactivex/internal/util/AtomicThrowable;
         // 4f: invokevirtual io/reactivex/internal/util/AtomicThrowable.terminate ()Ljava/lang/Throwable;
         // 52: astore 1
         // 53: aload 1
         // 54: ifnull 64
         // 57: aload 0
         // 58: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.downstream Lio/reactivex/Observer;
         // 5b: aload 1
         // 5c: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
         // 61: goto 6d
         // 64: aload 0
         // 65: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.downstream Lio/reactivex/Observer;
         // 68: invokeinterface io/reactivex/Observer.onComplete ()V 1
         // 6d: return
         // 6e: aload 0
         // 6f: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.decrementAndGet ()I
         // 72: ifne 95
         // 75: return
         // 76: aload 0
         // 77: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.getOrCreateQueue ()Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 7a: astore 1
         // 7b: aload 1
         // 7c: monitorenter
         // 7d: aload 1
         // 7e: aload 2
         // 7f: invokevirtual io/reactivex/internal/queue/SpscLinkedArrayQueue.offer (Ljava/lang/Object;)Z
         // 82: pop
         // 83: aload 1
         // 84: monitorexit
         // 85: aload 0
         // 86: getfield io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.active Ljava/util/concurrent/atomic/AtomicInteger;
         // 89: invokevirtual java/util/concurrent/atomic/AtomicInteger.decrementAndGet ()I
         // 8c: pop
         // 8d: aload 0
         // 8e: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.getAndIncrement ()I
         // 91: ifeq 95
         // 94: return
         // 95: aload 0
         // 96: invokevirtual io/reactivex/internal/operators/observable/ObservableFlatMapSingle$FlatMapSingleObserver.drainLoop ()V
         // 99: return
         // 9a: astore 2
         // 9b: aload 1
         // 9c: monitorexit
         // 9d: aload 2
         // 9e: athrow
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.active.decrementAndGet();
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.active.decrementAndGet();
         if (this.errors.addThrowable(var1)) {
            if (!this.delayErrors) {
               this.set.dispose();
            }

            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         SingleSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The mapper returned a null SingleSource");
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.upstream.dispose();
            this.onError(var4);
            return;
         }

         this.active.getAndIncrement();
         var1 = new ObservableFlatMapSingle.FlatMapSingleObserver.InnerObserver(this);
         if (!this.cancelled && this.set.add(var1)) {
            var2.subscribe(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      final class InnerObserver extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
         private static final long serialVersionUID = -502562646270949838L;
         final ObservableFlatMapSingle.FlatMapSingleObserver this$0;

         InnerObserver(ObservableFlatMapSingle.FlatMapSingleObserver var1) {
            this.this$0 = var1;
         }

         @Override
         public void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.get());
         }

         @Override
         public void onError(Throwable var1) {
            this.this$0.innerError(this, var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.this$0.innerSuccess(this, var1);
         }
      }
   }
}
