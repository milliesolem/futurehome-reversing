package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.ArrayDeque;

public final class ObservableSkipLast<T> extends AbstractObservableWithUpstream<T, T> {
   final int skip;

   public ObservableSkipLast(ObservableSource<T> var1, int var2) {
      super(var1);
      this.skip = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableSkipLast.SkipLastObserver<>(var1, this.skip));
   }

   static final class SkipLastObserver<T> extends ArrayDeque<T> implements Observer<T>, Disposable {
      private static final long serialVersionUID = -3807491841935125653L;
      final Observer<? super T> downstream;
      final int skip;
      Disposable upstream;

      SkipLastObserver(Observer<? super T> var1, int var2) {
         super(var2);
         this.downstream = var1;
         this.skip = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
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
      public void onNext(T var1) {
         if (this.skip == this.size()) {
            this.downstream.onNext(this.poll());
         }

         this.offer((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
