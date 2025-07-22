package io.reactivex.internal.operators.mixed;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatMapMaybe<T, R> extends Observable<R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
   final int prefetch;
   final Observable<T> source;

   public ObservableConcatMapMaybe(Observable<T> var1, Function<? super T, ? extends MaybeSource<? extends R>> var2, ErrorMode var3, int var4) {
      this.source = var1;
      this.mapper = var2;
      this.errorMode = var3;
      this.prefetch = var4;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      if (!ScalarXMapZHelper.tryAsMaybe(this.source, this.mapper, var1)) {
         this.source.subscribe(new ObservableConcatMapMaybe.ConcatMapMaybeMainObserver<>(var1, this.mapper, this.prefetch, this.errorMode));
      }
   }

   static final class ConcatMapMaybeMainObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
      static final int STATE_ACTIVE = 1;
      static final int STATE_INACTIVE = 0;
      static final int STATE_RESULT_VALUE = 2;
      private static final long serialVersionUID = -9140123220065488293L;
      volatile boolean cancelled;
      volatile boolean done;
      final Observer<? super R> downstream;
      final ErrorMode errorMode;
      final AtomicThrowable errors;
      final ObservableConcatMapMaybe.ConcatMapMaybeMainObserver.ConcatMapMaybeObserver<R> inner;
      R item;
      final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
      final SimplePlainQueue<T> queue;
      volatile int state;
      Disposable upstream;

      ConcatMapMaybeMainObserver(Observer<? super R> var1, Function<? super T, ? extends MaybeSource<? extends R>> var2, int var3, ErrorMode var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.errorMode = var4;
         this.errors = new AtomicThrowable();
         this.inner = new ObservableConcatMapMaybe.ConcatMapMaybeMainObserver.ConcatMapMaybeObserver<>(this);
         this.queue = new SpscLinkedArrayQueue<>(var3);
      }

      @Override
      public void dispose() {
         this.cancelled = true;
         this.upstream.dispose();
         this.inner.dispose();
         if (this.getAndIncrement() == 0) {
            this.queue.clear();
            this.item = null;
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var5 = this.downstream;
            ErrorMode var8 = this.errorMode;
            SimplePlainQueue var7 = this.queue;
            AtomicThrowable var6 = this.errors;
            int var1 = 1;

            while (true) {
               if (this.cancelled) {
                  var7.clear();
                  this.item = null;
               } else {
                  int var3 = this.state;
                  if (var6.get() != null && (var8 == ErrorMode.IMMEDIATE || var8 == ErrorMode.BOUNDARY && var3 == 0)) {
                     var7.clear();
                     this.item = null;
                     var5.onError(var6.terminate());
                     return;
                  }

                  boolean var2 = false;
                  if (var3 == 0) {
                     boolean var4 = this.done;
                     MaybeSource var9 = (MaybeSource)var7.poll();
                     if (var9 == null) {
                        var2 = true;
                     }

                     if (var4 && var2) {
                        Throwable var13 = var6.terminate();
                        if (var13 == null) {
                           var5.onComplete();
                        } else {
                           var5.onError(var13);
                        }

                        return;
                     }

                     if (!var2) {
                        try {
                           var9 = ObjectHelper.requireNonNull(this.mapper.apply((T)var9), "The mapper returned a null MaybeSource");
                        } catch (Throwable var11) {
                           Exceptions.throwIfFatal(var11);
                           this.upstream.dispose();
                           var7.clear();
                           var6.addThrowable(var11);
                           var5.onError(var6.terminate());
                           return;
                        }

                        this.state = 1;
                        var9.subscribe(this.inner);
                     }
                  } else if (var3 == 2) {
                     Object var15 = this.item;
                     this.item = null;
                     var5.onNext(var15);
                     this.state = 0;
                     continue;
                  }
               }

               int var12 = this.addAndGet(-var1);
               var1 = var12;
               if (var12 == 0) {
                  return;
               }
            }
         }
      }

      void innerComplete() {
         this.state = 0;
         this.drain();
      }

      void innerError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.errorMode != ErrorMode.END) {
               this.upstream.dispose();
            }

            this.state = 0;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void innerSuccess(R var1) {
         this.item = (R)var1;
         this.state = 2;
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
         if (this.errors.addThrowable(var1)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
               this.inner.dispose();
            }

            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         this.queue.offer((T)var1);
         this.drain();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      static final class ConcatMapMaybeObserver<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
         private static final long serialVersionUID = -3051469169682093892L;
         final ObservableConcatMapMaybe.ConcatMapMaybeMainObserver<?, R> parent;

         ConcatMapMaybeObserver(ObservableConcatMapMaybe.ConcatMapMaybeMainObserver<?, R> var1) {
            this.parent = var1;
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
            this.parent.innerError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.replace(this, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.parent.innerSuccess((R)var1);
         }
      }
   }
}
