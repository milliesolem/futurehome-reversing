package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDelayWithSingle<T, U> extends Single<T> {
   final SingleSource<U> other;
   final SingleSource<T> source;

   public SingleDelayWithSingle(SingleSource<T> var1, SingleSource<U> var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.other.subscribe(new SingleDelayWithSingle.OtherObserver<>(var1, this.source));
   }

   static final class OtherObserver<T, U> extends AtomicReference<Disposable> implements SingleObserver<U>, Disposable {
      private static final long serialVersionUID = -8565274649390031272L;
      final SingleObserver<? super T> downstream;
      final SingleSource<T> source;

      OtherObserver(SingleObserver<? super T> var1, SingleSource<T> var2) {
         this.downstream = var1;
         this.source = var2;
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
      public void onSuccess(U var1) {
         this.source.subscribe(new ResumeSingleObserver<>(this, this.downstream));
      }
   }
}
