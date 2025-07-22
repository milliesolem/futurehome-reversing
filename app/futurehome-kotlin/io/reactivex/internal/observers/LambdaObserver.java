package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.Functions;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class LambdaObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable, LambdaConsumerIntrospection {
   private static final long serialVersionUID = -7251123623727029452L;
   final Action onComplete;
   final Consumer<? super Throwable> onError;
   final Consumer<? super T> onNext;
   final Consumer<? super Disposable> onSubscribe;

   public LambdaObserver(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3, Consumer<? super Disposable> var4) {
      this.onNext = var1;
      this.onError = var2;
      this.onComplete = var3;
      this.onSubscribe = var4;
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this);
   }

   @Override
   public boolean hasCustomOnError() {
      boolean var1;
      if (this.onError != Functions.ON_ERROR_MISSING) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.get() == DisposableHelper.DISPOSED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onComplete() {
      if (!this.isDisposed()) {
         this.lazySet(DisposableHelper.DISPOSED);

         try {
            this.onComplete.run();
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onError(Throwable var1) {
      if (!this.isDisposed()) {
         this.lazySet(DisposableHelper.DISPOSED);

         try {
            this.onError.accept(var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            RxJavaPlugins.onError(new CompositeException(var1, var4));
            return;
         }
      } else {
         RxJavaPlugins.onError(var1);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onNext(T var1) {
      if (!this.isDisposed()) {
         try {
            this.onNext.accept((T)var1);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.get().dispose();
            this.onError(var3);
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSubscribe(Disposable var1) {
      if (DisposableHelper.setOnce(this, var1)) {
         try {
            this.onSubscribe.accept(this);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1.dispose();
            this.onError(var4);
            return;
         }
      }
   }
}
