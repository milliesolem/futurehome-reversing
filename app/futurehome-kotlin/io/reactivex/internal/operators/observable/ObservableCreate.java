package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCreate<T> extends Observable<T> {
   final ObservableOnSubscribe<T> source;

   public ObservableCreate(ObservableOnSubscribe<T> var1) {
      this.source = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      ObservableCreate.CreateEmitter var2 = new ObservableCreate.CreateEmitter(var1);
      var1.onSubscribe(var2);

      try {
         this.source.subscribe(var2);
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         var2.onError(var4);
         return;
      }
   }

   static final class CreateEmitter<T> extends AtomicReference<Disposable> implements ObservableEmitter<T>, Disposable {
      private static final long serialVersionUID = -3434801548987643227L;
      final Observer<? super T> observer;

      CreateEmitter(Observer<? super T> var1) {
         this.observer = var1;
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
      public void onComplete() {
         if (!this.isDisposed()) {
            try {
               this.observer.onComplete();
            } finally {
               this.dispose();
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (!this.tryOnError(var1)) {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (var1 == null) {
            this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
         } else {
            if (!this.isDisposed()) {
               this.observer.onNext((T)var1);
            }
         }
      }

      @Override
      public ObservableEmitter<T> serialize() {
         return new ObservableCreate.SerializedEmitter<>(this);
      }

      @Override
      public void setCancellable(Cancellable var1) {
         this.setDisposable(new CancellableDisposable(var1));
      }

      @Override
      public void setDisposable(Disposable var1) {
         DisposableHelper.set(this, var1);
      }

      @Override
      public String toString() {
         return String.format("%s{%s}", this.getClass().getSimpleName(), super.toString());
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         Object var2 = var1;
         if (var1 == null) {
            var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
         }

         if (!this.isDisposed()) {
            try {
               this.observer.onError((Throwable)var2);
            } finally {
               this.dispose();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   static final class SerializedEmitter<T> extends AtomicInteger implements ObservableEmitter<T> {
      private static final long serialVersionUID = 4883307006032401862L;
      volatile boolean done;
      final ObservableEmitter<T> emitter;
      final AtomicThrowable error;
      final SpscLinkedArrayQueue<T> queue;

      SerializedEmitter(ObservableEmitter<T> var1) {
         this.emitter = var1;
         this.error = new AtomicThrowable();
         this.queue = new SpscLinkedArrayQueue<>(16);
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         ObservableEmitter var6 = this.emitter;
         SpscLinkedArrayQueue var4 = this.queue;
         AtomicThrowable var7 = this.error;
         int var1 = 1;

         while (!var6.isDisposed()) {
            if (var7.get() != null) {
               var4.clear();
               var6.onError(var7.terminate());
               return;
            }

            boolean var3 = this.done;
            Object var5 = var4.poll();
            boolean var2;
            if (var5 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var3 && var2) {
               var6.onComplete();
               return;
            }

            if (var2) {
               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var6.onNext(var5);
            }
         }

         var4.clear();
      }

      @Override
      public boolean isDisposed() {
         return this.emitter.isDisposed();
      }

      @Override
      public void onComplete() {
         if (!this.emitter.isDisposed() && !this.done) {
            this.done = true;
            this.drain();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (!this.tryOnError(var1)) {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.emitter Lio/reactivex/ObservableEmitter;
         // 04: invokeinterface io/reactivex/ObservableEmitter.isDisposed ()Z 1
         // 09: ifne 6d
         // 0c: aload 0
         // 0d: getfield io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.done Z
         // 10: ifeq 16
         // 13: goto 6d
         // 16: aload 1
         // 17: ifnonnull 28
         // 1a: aload 0
         // 1b: new java/lang/NullPointerException
         // 1e: dup
         // 1f: ldc "onNext called with null. Null values are generally not allowed in 2.x operators and sources."
         // 21: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
         // 24: invokevirtual io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.onError (Ljava/lang/Throwable;)V
         // 27: return
         // 28: aload 0
         // 29: invokevirtual io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.get ()I
         // 2c: ifne 4a
         // 2f: aload 0
         // 30: bipush 0
         // 31: bipush 1
         // 32: invokevirtual io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.compareAndSet (II)Z
         // 35: ifeq 4a
         // 38: aload 0
         // 39: getfield io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.emitter Lio/reactivex/ObservableEmitter;
         // 3c: aload 1
         // 3d: invokeinterface io/reactivex/ObservableEmitter.onNext (Ljava/lang/Object;)V 2
         // 42: aload 0
         // 43: invokevirtual io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.decrementAndGet ()I
         // 46: ifne 63
         // 49: return
         // 4a: aload 0
         // 4b: getfield io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.queue Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
         // 4e: astore 2
         // 4f: aload 2
         // 50: monitorenter
         // 51: aload 2
         // 52: aload 1
         // 53: invokeinterface io/reactivex/internal/fuseable/SimpleQueue.offer (Ljava/lang/Object;)Z 2
         // 58: pop
         // 59: aload 2
         // 5a: monitorexit
         // 5b: aload 0
         // 5c: invokevirtual io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.getAndIncrement ()I
         // 5f: ifeq 63
         // 62: return
         // 63: aload 0
         // 64: invokevirtual io/reactivex/internal/operators/observable/ObservableCreate$SerializedEmitter.drainLoop ()V
         // 67: return
         // 68: astore 1
         // 69: aload 2
         // 6a: monitorexit
         // 6b: aload 1
         // 6c: athrow
         // 6d: return
      }

      @Override
      public ObservableEmitter<T> serialize() {
         return this;
      }

      @Override
      public void setCancellable(Cancellable var1) {
         this.emitter.setCancellable(var1);
      }

      @Override
      public void setDisposable(Disposable var1) {
         this.emitter.setDisposable(var1);
      }

      @Override
      public String toString() {
         return this.emitter.toString();
      }

      @Override
      public boolean tryOnError(Throwable var1) {
         if (!this.emitter.isDisposed() && !this.done) {
            Object var2 = var1;
            if (var1 == null) {
               var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }

            if (this.error.addThrowable((Throwable)var2)) {
               this.done = true;
               this.drain();
               return true;
            }
         }

         return false;
      }
   }
}
