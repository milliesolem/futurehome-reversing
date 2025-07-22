package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimeout<T, U, V> extends AbstractObservableWithUpstream<T, T> {
   final ObservableSource<U> firstTimeoutIndicator;
   final Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator;
   final ObservableSource<? extends T> other;

   public ObservableTimeout(
      Observable<T> var1, ObservableSource<U> var2, Function<? super T, ? extends ObservableSource<V>> var3, ObservableSource<? extends T> var4
   ) {
      super(var1);
      this.firstTimeoutIndicator = var2;
      this.itemTimeoutIndicator = var3;
      this.other = var4;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      if (this.other == null) {
         ObservableTimeout.TimeoutObserver var2 = new ObservableTimeout.TimeoutObserver<>(var1, this.itemTimeoutIndicator);
         var1.onSubscribe(var2);
         var2.startFirstTimeout(this.firstTimeoutIndicator);
         this.source.subscribe(var2);
      } else {
         ObservableTimeout.TimeoutFallbackObserver var3 = new ObservableTimeout.TimeoutFallbackObserver<>(var1, this.itemTimeoutIndicator, this.other);
         var1.onSubscribe(var3);
         var3.startFirstTimeout(this.firstTimeoutIndicator);
         this.source.subscribe(var3);
      }
   }

   static final class TimeoutConsumer extends AtomicReference<Disposable> implements Observer<Object>, Disposable {
      private static final long serialVersionUID = 8708641127342403073L;
      final long idx;
      final ObservableTimeout.TimeoutSelectorSupport parent;

      TimeoutConsumer(long var1, ObservableTimeout.TimeoutSelectorSupport var3) {
         this.idx = var1;
         this.parent = var3;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed((Disposable)this.get());
      }

      @Override
      public void onComplete() {
         if (this.get() != DisposableHelper.DISPOSED) {
            this.lazySet(DisposableHelper.DISPOSED);
            this.parent.onTimeout(this.idx);
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.get() != DisposableHelper.DISPOSED) {
            this.lazySet(DisposableHelper.DISPOSED);
            this.parent.onTimeoutError(this.idx, var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(Object var1) {
         var1 = (Disposable)this.get();
         if (var1 != DisposableHelper.DISPOSED) {
            var1.dispose();
            this.lazySet(DisposableHelper.DISPOSED);
            this.parent.onTimeout(this.idx);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }

   static final class TimeoutFallbackObserver<T>
      extends AtomicReference<Disposable>
      implements Observer<T>,
      Disposable,
      ObservableTimeout.TimeoutSelectorSupport {
      private static final long serialVersionUID = -7508389464265974549L;
      final Observer<? super T> downstream;
      ObservableSource<? extends T> fallback;
      final AtomicLong index;
      final Function<? super T, ? extends ObservableSource<?>> itemTimeoutIndicator;
      final SequentialDisposable task;
      final AtomicReference<Disposable> upstream;

      TimeoutFallbackObserver(Observer<? super T> var1, Function<? super T, ? extends ObservableSource<?>> var2, ObservableSource<? extends T> var3) {
         this.downstream = var1;
         this.itemTimeoutIndicator = var2;
         this.task = new SequentialDisposable();
         this.fallback = var3;
         this.index = new AtomicLong();
         this.upstream = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         DisposableHelper.dispose(this);
         this.task.dispose();
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
            this.task.dispose();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
            this.task.dispose();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         long var2 = this.index.get();
         if (var2 != Long.MAX_VALUE) {
            AtomicLong var6 = this.index;
            long var4 = 1L + var2;
            if (var6.compareAndSet(var2, var4)) {
               Disposable var10 = this.task.get();
               if (var10 != null) {
                  var10.dispose();
               }

               this.downstream.onNext((T)var1);

               try {
                  var1 = ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply((T)var1), "The itemTimeoutIndicator returned a null ObservableSource.");
               } catch (Throwable var8) {
                  Exceptions.throwIfFatal(var8);
                  this.upstream.get().dispose();
                  this.index.getAndSet(Long.MAX_VALUE);
                  this.downstream.onError(var8);
                  return;
               }

               ObservableTimeout.TimeoutConsumer var11 = new ObservableTimeout.TimeoutConsumer(var4, this);
               if (this.task.replace(var11)) {
                  var1.subscribe(var11);
               }

               return;
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      @Override
      public void onTimeout(long var1) {
         if (this.index.compareAndSet(var1, Long.MAX_VALUE)) {
            DisposableHelper.dispose(this.upstream);
            ObservableSource var3 = this.fallback;
            this.fallback = null;
            var3.subscribe(new ObservableTimeoutTimed.FallbackObserver<>(this.downstream, this));
         }
      }

      @Override
      public void onTimeoutError(long var1, Throwable var3) {
         if (this.index.compareAndSet(var1, Long.MAX_VALUE)) {
            DisposableHelper.dispose(this);
            this.downstream.onError(var3);
         } else {
            RxJavaPlugins.onError(var3);
         }
      }

      void startFirstTimeout(ObservableSource<?> var1) {
         if (var1 != null) {
            ObservableTimeout.TimeoutConsumer var2 = new ObservableTimeout.TimeoutConsumer(0L, this);
            if (this.task.replace(var2)) {
               var1.subscribe(var2);
            }
         }
      }
   }

   static final class TimeoutObserver<T> extends AtomicLong implements Observer<T>, Disposable, ObservableTimeout.TimeoutSelectorSupport {
      private static final long serialVersionUID = 3764492702657003550L;
      final Observer<? super T> downstream;
      final Function<? super T, ? extends ObservableSource<?>> itemTimeoutIndicator;
      final SequentialDisposable task;
      final AtomicReference<Disposable> upstream;

      TimeoutObserver(Observer<? super T> var1, Function<? super T, ? extends ObservableSource<?>> var2) {
         this.downstream = var1;
         this.itemTimeoutIndicator = var2;
         this.task = new SequentialDisposable();
         this.upstream = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         this.task.dispose();
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.upstream.get());
      }

      @Override
      public void onComplete() {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         long var4 = this.get();
         if (var4 != Long.MAX_VALUE) {
            long var2 = 1L + var4;
            if (this.compareAndSet(var4, var2)) {
               Disposable var6 = this.task.get();
               if (var6 != null) {
                  var6.dispose();
               }

               this.downstream.onNext((T)var1);

               try {
                  var10 = ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply((T)var1), "The itemTimeoutIndicator returned a null ObservableSource.");
               } catch (Throwable var8) {
                  Exceptions.throwIfFatal(var8);
                  this.upstream.get().dispose();
                  this.getAndSet(Long.MAX_VALUE);
                  this.downstream.onError(var8);
                  return;
               }

               var1 = new ObservableTimeout.TimeoutConsumer(var2, this);
               if (this.task.replace(var1)) {
                  var10.subscribe(var1);
               }

               return;
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      @Override
      public void onTimeout(long var1) {
         if (this.compareAndSet(var1, Long.MAX_VALUE)) {
            DisposableHelper.dispose(this.upstream);
            this.downstream.onError(new TimeoutException());
         }
      }

      @Override
      public void onTimeoutError(long var1, Throwable var3) {
         if (this.compareAndSet(var1, Long.MAX_VALUE)) {
            DisposableHelper.dispose(this.upstream);
            this.downstream.onError(var3);
         } else {
            RxJavaPlugins.onError(var3);
         }
      }

      void startFirstTimeout(ObservableSource<?> var1) {
         if (var1 != null) {
            ObservableTimeout.TimeoutConsumer var2 = new ObservableTimeout.TimeoutConsumer(0L, this);
            if (this.task.replace(var2)) {
               var1.subscribe(var2);
            }
         }
      }
   }

   interface TimeoutSelectorSupport extends ObservableTimeoutTimed.TimeoutSupport {
      void onTimeoutError(long var1, Throwable var3);
   }
}
