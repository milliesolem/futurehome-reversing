package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeSwitchIfEmptySingle<T> extends Single<T> implements HasUpstreamMaybeSource<T> {
   final SingleSource<? extends T> other;
   final MaybeSource<T> source;

   public MaybeSwitchIfEmptySingle(MaybeSource<T> var1, SingleSource<? extends T> var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   public MaybeSource<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new MaybeSwitchIfEmptySingle.SwitchIfEmptyMaybeObserver<>(var1, this.other));
   }

   static final class SwitchIfEmptyMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
      private static final long serialVersionUID = 4603919676453758899L;
      final SingleObserver<? super T> downstream;
      final SingleSource<? extends T> other;

      SwitchIfEmptyMaybeObserver(SingleObserver<? super T> var1, SingleSource<? extends T> var2) {
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
            this.other.subscribe(new MaybeSwitchIfEmptySingle.SwitchIfEmptyMaybeObserver.OtherSingleObserver<>(this.downstream, this));
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

      static final class OtherSingleObserver<T> implements SingleObserver<T> {
         final SingleObserver<? super T> downstream;
         final AtomicReference<Disposable> parent;

         OtherSingleObserver(SingleObserver<? super T> var1, AtomicReference<Disposable> var2) {
            this.downstream = var1;
            this.parent = var2;
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
