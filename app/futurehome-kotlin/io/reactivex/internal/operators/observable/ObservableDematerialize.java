package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDematerialize<T, R> extends AbstractObservableWithUpstream<T, R> {
   final Function<? super T, ? extends Notification<R>> selector;

   public ObservableDematerialize(ObservableSource<T> var1, Function<? super T, ? extends Notification<R>> var2) {
      super(var1);
      this.selector = var2;
   }

   @Override
   public void subscribeActual(Observer<? super R> var1) {
      this.source.subscribe(new ObservableDematerialize.DematerializeObserver<>(var1, this.selector));
   }

   static final class DematerializeObserver<T, R> implements Observer<T>, Disposable {
      boolean done;
      final Observer<? super R> downstream;
      final Function<? super T, ? extends Notification<R>> selector;
      Disposable upstream;

      DematerializeObserver(Observer<? super R> var1, Function<? super T, ? extends Notification<R>> var2) {
         this.downstream = var1;
         this.selector = var2;
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
         if (this.done) {
            if (var1 instanceof Notification) {
               var1 = var1;
               if (var1.isOnError()) {
                  RxJavaPlugins.onError(var1.getError());
               }
            }
         } else {
            try {
               var1 = ObjectHelper.requireNonNull(this.selector.apply((T)var1), "The selector returned a null Notification");
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.upstream.dispose();
               this.onError(var3);
               return;
            }

            if (var1.isOnError()) {
               this.upstream.dispose();
               this.onError(var1.getError());
            } else if (var1.isOnComplete()) {
               this.upstream.dispose();
               this.onComplete();
            } else {
               this.downstream.onNext((R)var1.getValue());
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
