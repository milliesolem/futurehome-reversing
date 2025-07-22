package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatMap<T, U> extends AbstractObservableWithUpstream<T, U> {
   final int bufferSize;
   final ErrorMode delayErrors;
   final Function<? super T, ? extends ObservableSource<? extends U>> mapper;

   public ObservableConcatMap(ObservableSource<T> var1, Function<? super T, ? extends ObservableSource<? extends U>> var2, int var3, ErrorMode var4) {
      super(var1);
      this.mapper = var2;
      this.delayErrors = var4;
      this.bufferSize = Math.max(8, var3);
   }

   @Override
   public void subscribeActual(Observer<? super U> var1) {
      if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         if (this.delayErrors == ErrorMode.IMMEDIATE) {
            SerializedObserver var6 = new SerializedObserver(var1);
            this.source.subscribe(new ObservableConcatMap.SourceObserver<>(var6, this.mapper, this.bufferSize));
         } else {
            ObservableSource var4 = this.source;
            Function var5 = this.mapper;
            int var2 = this.bufferSize;
            boolean var3;
            if (this.delayErrors == ErrorMode.END) {
               var3 = true;
            } else {
               var3 = false;
            }

            var4.subscribe(new ObservableConcatMap.ConcatMapDelayErrorObserver<>(var1, var5, var2, var3));
         }
      }
   }

   static final class ConcatMapDelayErrorObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = -6951100001833242599L;
      volatile boolean active;
      final int bufferSize;
      volatile boolean cancelled;
      volatile boolean done;
      final Observer<? super R> downstream;
      final AtomicThrowable error;
      final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
      final ObservableConcatMap.ConcatMapDelayErrorObserver.DelayErrorInnerObserver<R> observer;
      SimpleQueue<T> queue;
      int sourceMode;
      final boolean tillTheEnd;
      Disposable upstream;

      ConcatMapDelayErrorObserver(Observer<? super R> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2, int var3, boolean var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.bufferSize = var3;
         this.tillTheEnd = var4;
         this.error = new AtomicThrowable();
         this.observer = new ObservableConcatMap.ConcatMapDelayErrorObserver.DelayErrorInnerObserver<>(var1, this);
      }

      @Override
      public void dispose() {
         this.cancelled = true;
         this.upstream.dispose();
         this.observer.dispose();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var3 = this.downstream;
            SimpleQueue var5 = this.queue;
            AtomicThrowable var4 = this.error;

            while (true) {
               if (!this.active) {
                  if (this.cancelled) {
                     var5.clear();
                     return;
                  }

                  if (!this.tillTheEnd && var4.get() != null) {
                     var5.clear();
                     this.cancelled = true;
                     var3.onError(var4.terminate());
                     return;
                  }

                  boolean var2 = this.done;

                  Object var6;
                  try {
                     var6 = (ObservableSource)var5.poll();
                  } catch (Throwable var17) {
                     Exceptions.throwIfFatal(var17);
                     this.cancelled = true;
                     this.upstream.dispose();
                     var4.addThrowable(var17);
                     var3.onError(var4.terminate());
                     return;
                  }

                  boolean var1;
                  if (var6 == null) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var2 && var1) {
                     this.cancelled = true;
                     Throwable var19 = var4.terminate();
                     if (var19 != null) {
                        var3.onError(var19);
                     } else {
                        var3.onComplete();
                     }

                     return;
                  }

                  if (!var1) {
                     try {
                        var6 = ObjectHelper.requireNonNull(this.mapper.apply((T)var6), "The mapper returned a null ObservableSource");
                     } catch (Throwable var16) {
                        Exceptions.throwIfFatal(var16);
                        this.cancelled = true;
                        this.upstream.dispose();
                        var5.clear();
                        var4.addThrowable(var16);
                        var3.onError(var4.terminate());
                        return;
                     }

                     if (var6 instanceof Callable) {
                        try {
                           var6 = (ObservableSource)((Callable)var6).call();
                        } catch (Throwable var18) {
                           Exceptions.throwIfFatal(var18);
                           var4.addThrowable(var18);
                           continue;
                        }

                        if (var6 != null && !this.cancelled) {
                           var3.onNext(var6);
                        }
                        continue;
                     }

                     this.active = true;
                     var6.subscribe(this.observer);
                  }
               }

               if (this.decrementAndGet() == 0) {
                  return;
               }
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (this.sourceMode == 0) {
            this.queue.offer((T)var1);
         }

         this.drain();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueDisposable) {
               QueueDisposable var3 = (QueueDisposable)var1;
               int var2 = var3.requestFusion(3);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  this.drain();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.downstream.onSubscribe(this);
                  return;
               }
            }

            this.queue = new SpscLinkedArrayQueue<>(this.bufferSize);
            this.downstream.onSubscribe(this);
         }
      }

      static final class DelayErrorInnerObserver<R> extends AtomicReference<Disposable> implements Observer<R> {
         private static final long serialVersionUID = 2620149119579502636L;
         final Observer<? super R> downstream;
         final ObservableConcatMap.ConcatMapDelayErrorObserver<?, R> parent;

         DelayErrorInnerObserver(Observer<? super R> var1, ObservableConcatMap.ConcatMapDelayErrorObserver<?, R> var2) {
            this.downstream = var1;
            this.parent = var2;
         }

         void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public void onComplete() {
            ObservableConcatMap.ConcatMapDelayErrorObserver var1 = this.parent;
            var1.active = false;
            var1.drain();
         }

         @Override
         public void onError(Throwable var1) {
            ObservableConcatMap.ConcatMapDelayErrorObserver var2 = this.parent;
            if (var2.error.addThrowable(var1)) {
               if (!var2.tillTheEnd) {
                  var2.upstream.dispose();
               }

               var2.active = false;
               var2.drain();
            } else {
               RxJavaPlugins.onError(var1);
            }
         }

         @Override
         public void onNext(R var1) {
            this.downstream.onNext((R)var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.replace(this, var1);
         }
      }
   }

   static final class SourceObserver<T, U> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = 8828587559905699186L;
      volatile boolean active;
      final int bufferSize;
      volatile boolean disposed;
      volatile boolean done;
      final Observer<? super U> downstream;
      int fusionMode;
      final ObservableConcatMap.SourceObserver.InnerObserver<U> inner;
      final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
      SimpleQueue<T> queue;
      Disposable upstream;

      SourceObserver(Observer<? super U> var1, Function<? super T, ? extends ObservableSource<? extends U>> var2, int var3) {
         this.downstream = var1;
         this.mapper = var2;
         this.bufferSize = var3;
         this.inner = new ObservableConcatMap.SourceObserver.InnerObserver<>(var1, this);
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.inner.dispose();
         this.upstream.dispose();
         if (this.getAndIncrement() == 0) {
            this.queue.clear();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            while (!this.disposed) {
               if (!this.active) {
                  boolean var2 = this.done;

                  Object var3;
                  try {
                     var3 = this.queue.poll();
                  } catch (Throwable var9) {
                     Exceptions.throwIfFatal(var9);
                     this.dispose();
                     this.queue.clear();
                     this.downstream.onError(var9);
                     return;
                  }

                  boolean var1;
                  if (var3 == null) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var2 && var1) {
                     this.disposed = true;
                     this.downstream.onComplete();
                     return;
                  }

                  if (!var1) {
                     try {
                        var3 = ObjectHelper.requireNonNull(this.mapper.apply((T)var3), "The mapper returned a null ObservableSource");
                     } catch (Throwable var8) {
                        Exceptions.throwIfFatal(var8);
                        this.dispose();
                        this.queue.clear();
                        this.downstream.onError(var8);
                        return;
                     }

                     this.active = true;
                     var3.subscribe(this.inner);
                  }
               }

               if (this.decrementAndGet() == 0) {
                  return;
               }
            }

            this.queue.clear();
         }
      }

      void innerComplete() {
         this.active = false;
         this.drain();
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.drain();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.dispose();
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            if (this.fusionMode == 0) {
               this.queue.offer((T)var1);
            }

            this.drain();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueDisposable) {
               QueueDisposable var3 = (QueueDisposable)var1;
               int var2 = var3.requestFusion(3);
               if (var2 == 1) {
                  this.fusionMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  this.drain();
                  return;
               }

               if (var2 == 2) {
                  this.fusionMode = var2;
                  this.queue = var3;
                  this.downstream.onSubscribe(this);
                  return;
               }
            }

            this.queue = new SpscLinkedArrayQueue<>(this.bufferSize);
            this.downstream.onSubscribe(this);
         }
      }

      static final class InnerObserver<U> extends AtomicReference<Disposable> implements Observer<U> {
         private static final long serialVersionUID = -7449079488798789337L;
         final Observer<? super U> downstream;
         final ObservableConcatMap.SourceObserver<?, ?> parent;

         InnerObserver(Observer<? super U> var1, ObservableConcatMap.SourceObserver<?, ?> var2) {
            this.downstream = var1;
            this.parent = var2;
         }

         void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public void onComplete() {
            this.parent.innerComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.dispose();
            this.downstream.onError(var1);
         }

         @Override
         public void onNext(U var1) {
            this.downstream.onNext((U)var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.replace(this, var1);
         }
      }
   }
}
