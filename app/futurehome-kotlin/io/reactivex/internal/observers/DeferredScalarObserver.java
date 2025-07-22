package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public abstract class DeferredScalarObserver<T, R> extends DeferredScalarDisposable<R> implements Observer<T> {
   private static final long serialVersionUID = -266195175408988651L;
   protected Disposable upstream;

   public DeferredScalarObserver(Observer<? super R> var1) {
      super(var1);
   }

   @Override
   public void dispose() {
      super.dispose();
      this.upstream.dispose();
   }

   @Override
   public void onComplete() {
      Object var1 = this.value;
      if (var1 != null) {
         this.value = null;
         this.complete((R)var1);
      } else {
         this.complete();
      }
   }

   @Override
   public void onError(Throwable var1) {
      this.value = null;
      this.error(var1);
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (DisposableHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         this.downstream.onSubscribe(this);
      }
   }
}
