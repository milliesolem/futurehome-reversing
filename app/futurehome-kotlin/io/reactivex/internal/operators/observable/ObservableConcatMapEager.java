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
import io.reactivex.internal.observers.InnerQueuedObserver;
import io.reactivex.internal.observers.InnerQueuedObserverSupport;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableConcatMapEager<T, R> extends AbstractObservableWithUpstream<T, R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
   final int maxConcurrency;
   final int prefetch;

   public ObservableConcatMapEager(
      ObservableSource<T> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2, ErrorMode var3, int var4, int var5
   ) {
      super(var1);
      this.mapper = var2;
      this.errorMode = var3;
      this.maxConcurrency = var4;
      this.prefetch = var5;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      this.source.subscribe(new ObservableConcatMapEager.ConcatMapEagerMainObserver<>(var1, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
   }

   static final class ConcatMapEagerMainObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable, InnerQueuedObserverSupport<R> {
      private static final long serialVersionUID = 8080567949447303262L;
      int activeCount;
      volatile boolean cancelled;
      InnerQueuedObserver<R> current;
      volatile boolean done;
      final Observer<? super R> downstream;
      final AtomicThrowable error;
      final ErrorMode errorMode;
      final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
      final int maxConcurrency;
      final ArrayDeque<InnerQueuedObserver<R>> observers;
      final int prefetch;
      SimpleQueue<T> queue;
      int sourceMode;
      Disposable upstream;

      ConcatMapEagerMainObserver(
         Observer<? super R> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2, int var3, int var4, ErrorMode var5
      ) {
         this.downstream = var1;
         this.mapper = var2;
         this.maxConcurrency = var3;
         this.prefetch = var4;
         this.errorMode = var5;
         this.error = new AtomicThrowable();
         this.observers = new ArrayDeque<>();
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.dispose();
            this.drainAndDispose();
         }
      }

      void disposeAll() {
         InnerQueuedObserver var1 = this.current;
         if (var1 != null) {
            var1.dispose();
         }

         while (true) {
            var1 = this.observers.poll();
            if (var1 == null) {
               return;
            }

            var1.dispose();
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void drain() {
         if (this.getAndIncrement() == 0) {
            SimpleQueue var6 = this.queue;
            ArrayDeque var8 = this.observers;
            Observer var7 = this.downstream;
            ErrorMode var9 = this.errorMode;
            int var1 = 1;

            label343:
            while (true) {
               int var2;
               for (var2 = this.activeCount; var2 != this.maxConcurrency; var2++) {
                  if (this.cancelled) {
                     var6.clear();
                     this.disposeAll();
                     return;
                  }

                  if (var9 == ErrorMode.IMMEDIATE && this.error.get() != null) {
                     var6.clear();
                     this.disposeAll();
                     var7.onError(this.error.terminate());
                     return;
                  }

                  Object var4;
                  try {
                     var4 = var6.poll();
                  } catch (Throwable var21) {
                     Exceptions.throwIfFatal(var21);
                     this.upstream.dispose();
                     var6.clear();
                     this.disposeAll();
                     this.error.addThrowable(var21);
                     var7.onError(this.error.terminate());
                     return;
                  }

                  if (var4 == null) {
                     break;
                  }

                  ObservableSource var5;
                  try {
                     var5 = ObjectHelper.requireNonNull(this.mapper.apply((T)var4), "The mapper returned a null ObservableSource");
                  } catch (Throwable var20) {
                     Exceptions.throwIfFatal(var20);
                     this.upstream.dispose();
                     var6.clear();
                     this.disposeAll();
                     this.error.addThrowable(var20);
                     var7.onError(this.error.terminate());
                     return;
                  }

                  var4 = new InnerQueuedObserver<>(this, this.prefetch);
                  var8.offer(var4);
                  var5.subscribe((Observer<? super T>)var4);
               }

               this.activeCount = var2;
               if (this.cancelled) {
                  var6.clear();
                  this.disposeAll();
                  return;
               }

               if (var9 == ErrorMode.IMMEDIATE && this.error.get() != null) {
                  var6.clear();
                  this.disposeAll();
                  var7.onError(this.error.terminate());
                  return;
               }

               InnerQueuedObserver var29 = this.current;
               InnerQueuedObserver var28 = var29;
               if (var29 == null) {
                  if (var9 == ErrorMode.BOUNDARY && this.error.get() != null) {
                     var6.clear();
                     this.disposeAll();
                     var7.onError(this.error.terminate());
                     return;
                  }

                  boolean var3 = this.done;
                  var28 = (InnerQueuedObserver)var8.poll();
                  boolean var23;
                  if (var28 == null) {
                     var23 = true;
                  } else {
                     var23 = false;
                  }

                  if (var3 && var23) {
                     if (this.error.get() != null) {
                        var6.clear();
                        this.disposeAll();
                        var7.onError(this.error.terminate());
                     } else {
                        var7.onComplete();
                     }

                     return;
                  }

                  if (!var23) {
                     this.current = var28;
                  }
               }

               if (var28 != null) {
                  SimpleQueue var30 = var28.queue();

                  while (true) {
                     if (this.cancelled) {
                        var6.clear();
                        this.disposeAll();
                        return;
                     }

                     boolean var26 = var28.isDone();
                     if (var9 == ErrorMode.IMMEDIATE && this.error.get() != null) {
                        var6.clear();
                        this.disposeAll();
                        var7.onError(this.error.terminate());
                        return;
                     }

                     Object var10;
                     try {
                        var10 = var30.poll();
                     } catch (Throwable var22) {
                        Exceptions.throwIfFatal(var22);
                        this.error.addThrowable(var22);
                        this.current = null;
                        this.activeCount--;
                        continue label343;
                     }

                     boolean var24;
                     if (var10 == null) {
                        var24 = true;
                     } else {
                        var24 = false;
                     }

                     if (var26 && var24) {
                        this.current = null;
                        this.activeCount--;
                        continue label343;
                     }

                     if (var24) {
                        break;
                     }

                     var7.onNext(var10);
                  }
               }

               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            }
         }
      }

      void drainAndDispose() {
         if (this.getAndIncrement() == 0) {
            do {
               this.queue.clear();
               this.disposeAll();
            } while (this.decrementAndGet() != 0);
         }
      }

      @Override
      public void innerComplete(InnerQueuedObserver<R> var1) {
         var1.setDone();
         this.drain();
      }

      @Override
      public void innerError(InnerQueuedObserver<R> var1, Throwable var2) {
         if (this.error.addThrowable(var2)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
               this.upstream.dispose();
            }

            var1.setDone();
            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      @Override
      public void innerNext(InnerQueuedObserver<R> var1, R var2) {
         var1.queue().offer(var2);
         this.drain();
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

            this.queue = new SpscLinkedArrayQueue<>(this.prefetch);
            this.downstream.onSubscribe(this);
         }
      }
   }
}
