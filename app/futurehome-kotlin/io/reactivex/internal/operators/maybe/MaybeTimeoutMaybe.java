package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeTimeoutMaybe<T, U> extends AbstractMaybeWithUpstream<T, T> {
   final MaybeSource<? extends T> fallback;
   final MaybeSource<U> other;

   public MaybeTimeoutMaybe(MaybeSource<T> var1, MaybeSource<U> var2, MaybeSource<? extends T> var3) {
      super(var1);
      this.other = var2;
      this.fallback = var3;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeTimeoutMaybe.TimeoutMainMaybeObserver var2 = new MaybeTimeoutMaybe.TimeoutMainMaybeObserver<>(var1, this.fallback);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
      this.source.subscribe(var2);
   }

   static final class TimeoutFallbackMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
      private static final long serialVersionUID = 8663801314800248617L;
      final MaybeObserver<? super T> downstream;

      TimeoutFallbackMaybeObserver(MaybeObserver<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }

   static final class TimeoutMainMaybeObserver<T, U> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = -5955289211445418871L;
      final MaybeObserver<? super T> downstream;
      final MaybeSource<? extends T> fallback;
      final MaybeTimeoutMaybe.TimeoutOtherMaybeObserver<T, U> other;
      final MaybeTimeoutMaybe.TimeoutFallbackMaybeObserver<T> otherObserver;

      TimeoutMainMaybeObserver(MaybeObserver<? super T> var1, MaybeSource<? extends T> var2) {
         this.downstream = var1;
         this.other = new MaybeTimeoutMaybe.TimeoutOtherMaybeObserver<>(this);
         this.fallback = var2;
         MaybeTimeoutMaybe.TimeoutFallbackMaybeObserver var3;
         if (var2 != null) {
            var3 = new MaybeTimeoutMaybe.TimeoutFallbackMaybeObserver(var1);
         } else {
            var3 = null;
         }

         this.otherObserver = var3;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         DisposableHelper.dispose(this.other);
         MaybeTimeoutMaybe.TimeoutFallbackMaybeObserver var1 = this.otherObserver;
         if (var1 != null) {
            DisposableHelper.dispose(var1);
         }
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

      public void otherComplete() {
         if (DisposableHelper.dispose(this)) {
            MaybeSource var1 = this.fallback;
            if (var1 == null) {
               this.downstream.onError(new TimeoutException());
            } else {
               var1.subscribe(this.otherObserver);
            }
         }
      }

      public void otherError(Throwable var1) {
         if (DisposableHelper.dispose(this)) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }
   }

   static final class TimeoutOtherMaybeObserver<T, U> extends AtomicReference<Disposable> implements MaybeObserver<Object> {
      private static final long serialVersionUID = 8663801314800248617L;
      final MaybeTimeoutMaybe.TimeoutMainMaybeObserver<T, U> parent;

      TimeoutOtherMaybeObserver(MaybeTimeoutMaybe.TimeoutMainMaybeObserver<T, U> var1) {
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
