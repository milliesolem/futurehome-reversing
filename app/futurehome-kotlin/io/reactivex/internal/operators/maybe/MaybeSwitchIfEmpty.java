package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeSwitchIfEmpty<T> extends AbstractMaybeWithUpstream<T, T> {
   final MaybeSource<? extends T> other;

   public MaybeSwitchIfEmpty(MaybeSource<T> var1, MaybeSource<? extends T> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeSwitchIfEmpty.SwitchIfEmptyMaybeObserver<>(var1, this.other));
   }

   static final class SwitchIfEmptyMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = -2223459372976438024L;
      final MaybeObserver<? super T> downstream;
      final MaybeSource<? extends T> other;

      SwitchIfEmptyMaybeObserver(MaybeObserver<? super T> var1, MaybeSource<? extends T> var2) {
         this.downstream = var1;
         this.other = var2;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         Disposable var1 = this.get();
         if (var1 != DisposableHelper.DISPOSED && this.compareAndSet(var1, null)) {
            this.other.subscribe(new MaybeSwitchIfEmpty.SwitchIfEmptyMaybeObserver.OtherMaybeObserver<>(this.downstream, this));
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }

      static final class OtherMaybeObserver<T> implements MaybeObserver<T> {
         final MaybeObserver<? super T> downstream;
         final AtomicReference<Disposable> parent;

         OtherMaybeObserver(MaybeObserver<? super T> var1, AtomicReference<Disposable> var2) {
            this.downstream = var1;
            this.parent = var2;
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
            DisposableHelper.setOnce(this.parent, var1);
         }

         @Override
         public void onSuccess(T var1) {
            this.downstream.onSuccess((T)var1);
         }
      }
   }
}
