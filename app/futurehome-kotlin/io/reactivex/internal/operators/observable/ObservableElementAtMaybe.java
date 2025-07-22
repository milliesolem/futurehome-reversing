package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableElementAtMaybe<T> extends Maybe<T> implements FuseToObservable<T> {
   final long index;
   final ObservableSource<T> source;

   public ObservableElementAtMaybe(ObservableSource<T> var1, long var2) {
      this.source = var1;
      this.index = var2;
   }

   @Override
   public Observable<T> fuseToObservable() {
      return RxJavaPlugins.onAssembly(new ObservableElementAt<>(this.source, this.index, null, false));
   }

   @Override
   public void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new ObservableElementAtMaybe.ElementAtObserver<>(var1, this.index));
   }

   static final class ElementAtObserver<T> implements Observer<T>, Disposable {
      long count;
      boolean done;
      final MaybeObserver<? super T> downstream;
      final long index;
      Disposable upstream;

      ElementAtObserver(MaybeObserver<? super T> var1, long var2) {
         this.downstream = var1;
         this.index = var2;
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
            this.downstream.onComplete();
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
