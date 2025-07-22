package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import io.reactivex.internal.observers.DeferredScalarDisposable;

public final class MaybeToObservable<T> extends Observable<T> implements HasUpstreamMaybeSource<T> {
   final MaybeSource<T> source;

   public MaybeToObservable(MaybeSource<T> var1) {
      this.source = var1;
   }

   public static <T> MaybeObserver<T> create(Observer<? super T> var0) {
      return new MaybeToObservable.MaybeToObservableObserver<>(var0);
   }

   @Override
   public MaybeSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(create(var1));
   }

   static final class MaybeToObservableObserver<T> extends DeferredScalarDisposable<T> implements MaybeObserver<T> {
      private static final long serialVersionUID = 7603343402964826922L;
      Disposable upstream;

      MaybeToObservableObserver(Observer<? super T> var1) {
         super(var1);
      }

      @Override
      public void dispose() {
         super.dispose();
         this.upstream.dispose();
      }

      @Override
      public void onComplete() {
         this.complete();
      }

      @Override
      public void onError(Throwable var1) {
         this.error(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.complete((T)var1);
      }
   }
}
