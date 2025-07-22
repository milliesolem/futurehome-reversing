package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableElementAtSingle<T> extends Single<T> implements FuseToObservable<T> {
   final T defaultValue;
   final long index;
   final ObservableSource<T> source;

   public ObservableElementAtSingle(ObservableSource<T> var1, long var2, T var4) {
      this.source = var1;
      this.index = var2;
      this.defaultValue = (T)var4;
   }

   @Override
   public Observable<T> fuseToObservable() {
      return RxJavaPlugins.onAssembly(new ObservableElementAt<>(this.source, this.index, this.defaultValue, true));
   }

   @Override
   public void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new ObservableElementAtSingle.ElementAtObserver<>(var1, this.index, this.defaultValue));
   }

   static final class ElementAtObserver<T> implements Observer<T>, Disposable {
      long count;
      final T defaultValue;
      boolean done;
      final SingleObserver<? super T> downstream;
      final long index;
      Disposable upstream;

      ElementAtObserver(SingleObserver<? super T> var1, long var2, T var4) {
         this.downstream = var1;
         this.index = var2;
         this.defaultValue = (T)var4;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Object var1 = this.defaultValue;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onError(new NoSuchElementException());
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.count;
            if (var2 == this.index) {
               this.done = true;
               this.upstream.dispose();
               this.downstream.onSuccess((T)var1);
            } else {
               this.count = var2 + 1L;
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
   }
}
