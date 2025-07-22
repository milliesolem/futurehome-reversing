package io.reactivex.internal.operators.single;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDelayWithCompletable<T> extends Single<T> {
   final CompletableSource other;
   final SingleSource<T> source;

   public SingleDelayWithCompletable(SingleSource<T> var1, CompletableSource var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.other.subscribe(new SingleDelayWithCompletable.OtherObserver<>(var1, this.source));
   }

   static final class OtherObserver<T> extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
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
      public void onComplete() {
         this.source.subscribe(new ResumeSingleObserver<>(this, this.downstream));
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
   }
}
