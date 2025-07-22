package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObserverResourceWrapper<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
   private static final long serialVersionUID = -8612022020200669122L;
   final Observer<? super T> downstream;
   final AtomicReference<Disposable> upstream = new AtomicReference<>();

   public ObserverResourceWrapper(Observer<? super T> var1) {
      this.downstream = var1;
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this.upstream);
      DisposableHelper.dispose(this);
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.upstream.get() == DisposableHelper.DISPOSED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      this.dispose();
      this.downstream.onComplete();
   }

   @Override
   public void onError(Throwable var1) {
      this.dispose();
      this.downstream.onError(var1);
   }

   @Override
   public void onNext(T var1) {
      this.downstream.onNext((T)var1);
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (DisposableHelper.setOnce(this.upstream, var1)) {
         this.downstream.onSubscribe(this);
      }
   }

   public void setResource(Disposable var1) {
      DisposableHelper.set(this, var1);
   }
}
