package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

public final class MaybeCount<T> extends Single<Long> implements HasUpstreamMaybeSource<T> {
   final MaybeSource<T> source;

   public MaybeCount(MaybeSource<T> var1) {
      this.source = var1;
   }

   @Override
   public MaybeSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Long> var1) {
      this.source.subscribe(new MaybeCount.CountMaybeObserver(var1));
   }

   static final class CountMaybeObserver implements MaybeObserver<Object>, Disposable {
      final SingleObserver<? super Long> downstream;
      Disposable upstream;

      CountMaybeObserver(SingleObserver<? super Long> var1) {
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
         this.downstream.onSuccess(0L);
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
      public void onSuccess(Object var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onSuccess(1L);
      }
   }
}
