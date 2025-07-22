package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class DisposableLambdaObserver<T> implements Observer<T>, Disposable {
   final Observer<? super T> downstream;
   final Action onDispose;
   final Consumer<? super Disposable> onSubscribe;
   Disposable upstream;

   public DisposableLambdaObserver(Observer<? super T> var1, Consumer<? super Disposable> var2, Action var3) {
      this.downstream = var1;
      this.onSubscribe = var2;
      this.onDispose = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void dispose() {
      Disposable var2 = this.upstream;
      if (var2 != DisposableHelper.DISPOSED) {
         this.upstream = DisposableHelper.DISPOSED;

         label24:
         try {
            this.onDispose.run();
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            RxJavaPlugins.onError(var4);
            break label24;
         }

         var2.dispose();
      }
   }

   @Override
   public boolean isDisposed() {
      return this.upstream.isDisposed();
   }

   @Override
   public void onComplete() {
      if (this.upstream != DisposableHelper.DISPOSED) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onComplete();
      }
   }

   @Override
   public void onError(Throwable var1) {
      if (this.upstream != DisposableHelper.DISPOSED) {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onError(var1);
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   @Override
   public void onNext(T var1) {
      this.downstream.onNext((T)var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSubscribe(Disposable var1) {
      try {
         this.onSubscribe.accept(var1);
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         var1.dispose();
         this.upstream = DisposableHelper.DISPOSED;
         EmptyDisposable.error(var4, this.downstream);
         return;
      }

      if (DisposableHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         this.downstream.onSubscribe(this);
      }
   }
}
