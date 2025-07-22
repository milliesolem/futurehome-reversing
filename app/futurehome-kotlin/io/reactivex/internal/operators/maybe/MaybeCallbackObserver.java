package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
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

public final class MaybeCallbackObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, LambdaConsumerIntrospection {
   private static final long serialVersionUID = -6076952298809384986L;
   final Action onComplete;
   final Consumer<? super Throwable> onError;
   final Consumer<? super T> onSuccess;

   public MaybeCallbackObserver(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      this.onSuccess = var1;
      this.onError = var2;
      this.onComplete = var3;
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
      return DisposableHelper.isDisposed(this.get());
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onComplete() {
      this.lazySet(DisposableHelper.DISPOSED);

      try {
         this.onComplete.run();
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         RxJavaPlugins.onError(var3);
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onError(Throwable var1) {
      this.lazySet(DisposableHelper.DISPOSED);

      try {
         this.onError.accept(var1);
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         RxJavaPlugins.onError(new CompositeException(var1, var4));
         return;
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      DisposableHelper.setOnce(this, var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSuccess(T var1) {
      this.lazySet(DisposableHelper.DISPOSED);

      try {
         this.onSuccess.accept((T)var1);
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         RxJavaPlugins.onError(var3);
         return;
      }
   }
}
