package io.reactivex.internal.operators.maybe;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.plugins.RxJavaPlugins;

public final class MaybeIgnoreElementCompletable<T> extends Completable implements FuseToMaybe<T> {
   final MaybeSource<T> source;

   public MaybeIgnoreElementCompletable(MaybeSource<T> var1) {
      this.source = var1;
   }

   @Override
   public Maybe<T> fuseToMaybe() {
      return RxJavaPlugins.onAssembly(new MaybeIgnoreElement<>(this.source));
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new MaybeIgnoreElementCompletable.IgnoreMaybeObserver<>(var1));
   }

   static final class IgnoreMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      final CompletableObserver downstream;
      Disposable upstream;

      IgnoreMaybeObserver(CompletableObserver var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onError(var1);
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
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onComplete();
      }
   }
}
