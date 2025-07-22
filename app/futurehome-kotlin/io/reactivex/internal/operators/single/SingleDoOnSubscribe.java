package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class SingleDoOnSubscribe<T> extends Single<T> {
   final Consumer<? super Disposable> onSubscribe;
   final SingleSource<T> source;

   public SingleDoOnSubscribe(SingleSource<T> var1, Consumer<? super Disposable> var2) {
      this.source = var1;
      this.onSubscribe = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoOnSubscribe.DoOnSubscribeSingleObserver<>(var1, this.onSubscribe));
   }

   static final class DoOnSubscribeSingleObserver<T> implements SingleObserver<T> {
      boolean done;
      final SingleObserver<? super T> downstream;
      final Consumer<? super Disposable> onSubscribe;

      DoOnSubscribeSingleObserver(SingleObserver<? super T> var1, Consumer<? super Disposable> var2) {
         this.downstream = var1;
         this.onSubscribe = var2;
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSubscribe(Disposable var1) {
         try {
            this.onSubscribe.accept(var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.done = true;
            var1.dispose();
            EmptyDisposable.error(var4, this.downstream);
            return;
         }

         this.downstream.onSubscribe(var1);
      }

      @Override
      public void onSuccess(T var1) {
         if (!this.done) {
            this.downstream.onSuccess((T)var1);
         }
      }
   }
}
