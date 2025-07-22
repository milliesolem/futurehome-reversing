package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableSkipWhile<T> extends AbstractObservableWithUpstream<T, T> {
   final Predicate<? super T> predicate;

   public ObservableSkipWhile(ObservableSource<T> var1, Predicate<? super T> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableSkipWhile.SkipWhileObserver<>(var1, this.predicate));
   }

   static final class SkipWhileObserver<T> implements Observer<T>, Disposable {
      final Observer<? super T> downstream;
      boolean notSkipping;
      final Predicate<? super T> predicate;
      Disposable upstream;

      SkipWhileObserver(Observer<? super T> var1, Predicate<? super T> var2) {
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
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (this.notSkipping) {
            this.downstream.onNext((T)var1);
         } else {
            boolean var2;
            try {
               var2 = this.predicate.test((T)var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.dispose();
               this.downstream.onError(var4);
               return;
            }

            if (!var2) {
               this.notSkipping = true;
               this.downstream.onNext((T)var1);
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
