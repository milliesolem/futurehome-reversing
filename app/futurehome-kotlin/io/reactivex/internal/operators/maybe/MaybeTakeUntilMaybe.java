package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeTakeUntilMaybe<T, U> extends AbstractMaybeWithUpstream<T, T> {
   final MaybeSource<U> other;

   public MaybeTakeUntilMaybe(MaybeSource<T> var1, MaybeSource<U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeTakeUntilMaybe.TakeUntilMainMaybeObserver var2 = new MaybeTakeUntilMaybe.TakeUntilMainMaybeObserver(var1);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
      this.source.subscribe(var2);
   }

   static final class TakeUntilMainMaybeObserver<T, U> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = -2187421758664251153L;
      final MaybeObserver<? super T> downstream;
      final MaybeTakeUntilMaybe.TakeUntilMainMaybeObserver.TakeUntilOtherMaybeObserver<U> other;

      TakeUntilMainMaybeObserver(MaybeObserver<? super T> var1) {
         this.downstream = var1;
         this.other = new MaybeTakeUntilMaybe.TakeUntilMainMaybeObserver.TakeUntilOtherMaybeObserver<>(this);
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         DisposableHelper.dispose(this.other);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         DisposableHelper.dispose(this.other);
         if (this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.other);
         if (this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         DisposableHelper.dispose(this.other);
         if (this.getAndSet(DisposableHelper.DISPOSED) != DisposableHelper.DISPOSED) {
            this.downstream.onSuccess((T)var1);
         }
      }

      void otherComplete() {
         if (DisposableHelper.dispose(this)) {
            this.downstream.onComplete();
         }
      }

      void otherError(Throwable var1) {
         if (DisposableHelper.dispose(this)) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      static final class TakeUntilOtherMaybeObserver<U> extends AtomicReference<Disposable> implements MaybeObserver<U> {
         private static final long serialVersionUID = -1266041316834525931L;
         final MaybeTakeUntilMaybe.TakeUntilMainMaybeObserver<?, U> parent;

         TakeUntilOtherMaybeObserver(MaybeTakeUntilMaybe.TakeUntilMainMaybeObserver<?, U> var1) {
            this.parent = var1;
         }

         @Override
         public void onComplete() {
            this.parent.otherComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.otherError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(Object var1) {
            this.parent.otherComplete();
         }
      }
   }
}
