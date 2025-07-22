package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import io.reactivex.plugins.RxJavaPlugins;

public final class MaybeIsEmptySingle<T> extends Single<Boolean> implements HasUpstreamMaybeSource<T>, FuseToMaybe<Boolean> {
   final MaybeSource<T> source;

   public MaybeIsEmptySingle(MaybeSource<T> var1) {
      this.source = var1;
   }

   @Override
   public Maybe<Boolean> fuseToMaybe() {
      return RxJavaPlugins.onAssembly(new MaybeIsEmpty<>(this.source));
   }

   @Override
   public MaybeSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Boolean> var1) {
      this.source.subscribe(new MaybeIsEmptySingle.IsEmptyMaybeObserver<>(var1));
   }

   static final class IsEmptyMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      final SingleObserver<? super Boolean> downstream;
      Disposable upstream;

      IsEmptyMaybeObserver(SingleObserver<? super Boolean> var1) {
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
         this.downstream.onSuccess(true);
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
         this.downstream.onSuccess(false);
      }
   }
}
