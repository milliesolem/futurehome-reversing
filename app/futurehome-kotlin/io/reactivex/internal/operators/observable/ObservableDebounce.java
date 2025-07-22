package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableDebounce<T, U> extends AbstractObservableWithUpstream<T, T> {
   final Function<? super T, ? extends ObservableSource<U>> debounceSelector;

   public ObservableDebounce(ObservableSource<T> var1, Function<? super T, ? extends ObservableSource<U>> var2) {
      super(var1);
      this.debounceSelector = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableDebounce.DebounceObserver<>(new SerializedObserver<>(var1), this.debounceSelector));
   }

   static final class DebounceObserver<T, U> implements Observer<T>, Disposable {
      final Function<? super T, ? extends ObservableSource<U>> debounceSelector;
      final AtomicReference<Disposable> debouncer = new AtomicReference<>();
      boolean done;
      final Observer<? super T> downstream;
      volatile long index;
      Disposable upstream;

      DebounceObserver(Observer<? super T> var1, Function<? super T, ? extends ObservableSource<U>> var2) {
         this.downstream = var1;
         this.debounceSelector = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         DisposableHelper.dispose(this.debouncer);
      }

      void emit(long var1, T var3) {
         if (var1 == this.index) {
            this.downstream.onNext((T)var3);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Disposable var1 = this.debouncer.get();
            if (var1 != DisposableHelper.DISPOSED) {
               ObservableDebounce.DebounceObserver.DebounceInnerObserver var2 = (ObservableDebounce.DebounceObserver.DebounceInnerObserver)var1;
               if (var2 != null) {
                  var2.emit();
               }

               DisposableHelper.dispose(this.debouncer);
               this.downstream.onComplete();
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.debouncer);
         this.downstream.onError(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.index + 1L;
            this.index = var2;
            Disposable var4 = this.debouncer.get();
            if (var4 != null) {
               var4.dispose();
            }

            ObservableSource var5;
            try {
               var5 = ObjectHelper.requireNonNull(this.debounceSelector.apply((T)var1), "The ObservableSource supplied is null");
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.dispose();
               this.downstream.onError(var7);
               return;
            }

            var1 = new ObservableDebounce.DebounceObserver.DebounceInnerObserver<>(this, var2, (T)var1);
            if (ExternalSyntheticBackportWithForwarding0.m(this.debouncer, var4, var1)) {
               var5.subscribe(var1);
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      static final class DebounceInnerObserver<T, U> extends DisposableObserver<U> {
         boolean done;
         final long index;
         final AtomicBoolean once = new AtomicBoolean();
         final ObservableDebounce.DebounceObserver<T, U> parent;
         final T value;

         DebounceInnerObserver(ObservableDebounce.DebounceObserver<T, U> var1, long var2, T var4) {
            this.parent = var1;
            this.index = var2;
            this.value = (T)var4;
         }

         void emit() {
            if (this.once.compareAndSet(false, true)) {
               this.parent.emit(this.index, this.value);
            }
         }

         @Override
         public void onComplete() {
            if (!this.done) {
               this.done = true;
               this.emit();
            }
         }

         @Override
         public void onError(Throwable var1) {
            if (this.done) {
               RxJavaPlugins.onError(var1);
            } else {
               this.done = true;
               this.parent.onError(var1);
            }
         }

         @Override
         public void onNext(U var1) {
            if (!this.done) {
               this.done = true;
               this.dispose();
               this.emit();
            }
         }
      }
   }
}
