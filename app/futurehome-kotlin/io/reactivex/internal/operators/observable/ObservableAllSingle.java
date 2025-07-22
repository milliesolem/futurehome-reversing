package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableAllSingle<T> extends Single<Boolean> implements FuseToObservable<Boolean> {
   final Predicate<? super T> predicate;
   final ObservableSource<T> source;

   public ObservableAllSingle(ObservableSource<T> var1, Predicate<? super T> var2) {
      this.source = var1;
      this.predicate = var2;
   }

   @Override
   public Observable<Boolean> fuseToObservable() {
      return RxJavaPlugins.onAssembly(new ObservableAll<>(this.source, this.predicate));
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Boolean> var1) {
      this.source.subscribe(new ObservableAllSingle.AllObserver<>(var1, this.predicate));
   }

   static final class AllObserver<T> implements Observer<T>, Disposable {
      boolean done;
      final SingleObserver<? super Boolean> downstream;
      final Predicate<? super T> predicate;
      Disposable upstream;

      AllObserver(SingleObserver<? super Boolean> var1, Predicate<? super T> var2) {
         this.downstream = var1;
         this.predicate = var2;
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
            this.downstream.onSuccess(true);
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
            boolean var2;
            try {
               var2 = this.predicate.test((T)var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.dispose();
               this.onError(var4);
               return;
            }

            if (!var2) {
               this.done = true;
               this.upstream.dispose();
               this.downstream.onSuccess(false);
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
