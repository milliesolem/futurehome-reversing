package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeDetach<T> extends AbstractMaybeWithUpstream<T, T> {
   public MaybeDetach(MaybeSource<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeDetach.DetachMaybeObserver<>(var1));
   }

   static final class DetachMaybeObserver<T> implements MaybeObserver<T>, Disposable {
      MaybeObserver<? super T> downstream;
      Disposable upstream;

      DetachMaybeObserver(MaybeObserver<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.downstream = null;
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
         MaybeObserver var1 = this.downstream;
         if (var1 != null) {
            this.downstream = null;
            var1.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         MaybeObserver var2 = this.downstream;
         if (var2 != null) {
            this.downstream = null;
            var2.onError(var1);
         }
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
         MaybeObserver var2 = this.downstream;
         if (var2 != null) {
            this.downstream = null;
            var2.onSuccess(var1);
         }
      }
   }
}
