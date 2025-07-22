package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.LambdaConsumerIntrospection;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class CallbackCompletableObserver
   extends AtomicReference<Disposable>
   implements CompletableObserver,
   Disposable,
   Consumer<Throwable>,
   LambdaConsumerIntrospection {
   private static final long serialVersionUID = -4361286194466301354L;
   final Action onComplete;
   final Consumer<? super Throwable> onError;

   public CallbackCompletableObserver(Action var1) {
      this.onError = this;
      this.onComplete = var1;
   }

   public CallbackCompletableObserver(Consumer<? super Throwable> var1, Action var2) {
      this.onError = var1;
      this.onComplete = var2;
   }

   public void accept(Throwable var1) {
      RxJavaPlugins.onError(new OnErrorNotImplementedException(var1));
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this);
   }

   @Override
   public boolean hasCustomOnError() {
      boolean var1;
      if (this.onError != this) {
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
      label17:
      try {
         this.onComplete.run();
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         RxJavaPlugins.onError(var3);
         break label17;
      }

      this.lazySet(DisposableHelper.DISPOSED);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onError(Throwable var1) {
      label17:
      try {
         this.onError.accept(var1);
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         RxJavaPlugins.onError(var3);
         break label17;
      }

      this.lazySet(DisposableHelper.DISPOSED);
   }

   @Override
   public void onSubscribe(Disposable var1) {
      DisposableHelper.setOnce(this, var1);
   }
}
