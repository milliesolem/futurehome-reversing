package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableCollect<T, U> extends AbstractObservableWithUpstream<T, U> {
   final BiConsumer<? super U, ? super T> collector;
   final Callable<? extends U> initialSupplier;

   public ObservableCollect(ObservableSource<T> var1, Callable<? extends U> var2, BiConsumer<? super U, ? super T> var3) {
      super(var1);
      this.initialSupplier = var2;
      this.collector = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Observer<? super U> var1) {
      Object var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value");
      } catch (Throwable var4) {
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(new ObservableCollect.CollectObserver<>(var1, var2, this.collector));
   }

   static final class CollectObserver<T, U> implements Observer<T>, Disposable {
      final BiConsumer<? super U, ? super T> collector;
      boolean done;
      final Observer<? super U> downstream;
      final U u;
      Disposable upstream;

      CollectObserver(Observer<? super U> var1, U var2, BiConsumer<? super U, ? super T> var3) {
         this.downstream = var1;
         this.collector = var3;
         this.u = (U)var2;
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
         if (!this.done) {
            this.done = true;
            this.downstream.onNext(this.u);
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            try {
               this.collector.accept(this.u, (T)var1);
            } catch (Throwable var3) {
               this.upstream.dispose();
               this.onError(var3);
               return;
            }
         }
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
