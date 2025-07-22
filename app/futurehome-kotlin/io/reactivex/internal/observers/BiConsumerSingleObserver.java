package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

public final class BiConsumerSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
   private static final long serialVersionUID = 4943102778943297569L;
   final BiConsumer<? super T, ? super Throwable> onCallback;

   public BiConsumerSingleObserver(BiConsumer<? super T, ? super Throwable> var1) {
      this.onCallback = var1;
   }

   @Override
   public void dispose() {
      DisposableHelper.dispose(this);
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
   public void onError(Throwable var1) {
      try {
         this.lazySet(DisposableHelper.DISPOSED);
         this.onCallback.accept(null, var1);
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
      try {
         this.lazySet(DisposableHelper.DISPOSED);
         this.onCallback.accept((T)var1, null);
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         RxJavaPlugins.onError(var3);
         return;
      }
   }
}
